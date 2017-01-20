package com.bierbobo.rainbow.taskschedule;


import com.bierbobo.rainbow.domain.common.CommonResult;
import com.bierbobo.rainbow.domain.common.Constants;
import com.bierbobo.rainbow.domain.common.TaskScheduleEnum;
import com.bierbobo.rainbow.domain.entity.Task;
import com.bierbobo.rainbow.domain.vo.QueryTaskParam;
import com.bierbobo.rainbow.domain.task.TaskStateEnum;
import com.bierbobo.rainbow.service.TaskScheduleService;
import com.bierbobo.rainbow.util.JSONUtil;
import com.bierbobo.rainbow.util.NetworkUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by lifubo on 2016/10/19.
 */
public class BaseTaskSchedule {

    private static String LOGGER_BEGIN = "【任务统一处理框架】";
    Logger logger = Logger.getLogger(BaseTaskSchedule.class);
    private TaskScheduleService taskScheduleService;


    //处理采销大表导出Excel数据,每次在数据库读取 taskNumMultiple 条数据 然后开启 taskNumMultiple 线程执行

    private static final int corePoolSize = 10;
    private static final int maximumPoolSize = 100;
    private static final int keepAliveTime = 60;

    private ThreadPoolExecutor pool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime,
            TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(100), new ThreadPoolExecutor.CallerRunsPolicy());


    public ThreadPoolExecutor getPool() {
        return pool;
    }

    public void setPool(ThreadPoolExecutor pool) {
        this.pool = pool;
    }

    /**
     任务注册方法【支持批量】
     1.检查任务的配置与参数信息
     2.对任务按照是否存在进行分组
     3.不存在的任务：进行注册插入
     4.存在的任务：根据影响时间，查询出需要修改的任务,更新任务
     5.对任务进行后续处理


     * @param records   要注册的任务
     * @param flag      true的时候更新已有的任务[此时task的effectiveUpdateTimeSpace字段必填]；
     * @param param     后置任务使用的参数[后置任务主要考虑事务管理]
     * @param <T>
     * @return          Map<String,Task>:其中key为uuid；task对象中也包含uuid
     */
    @Transactional
    public <T> CommonResult<Map<String,Task>> registerTask(List<Task> records,boolean flag,List<T> param){

        //任务参数校验
        CommonResult<Map<String,Task>> result = taskScheduleService.checkTaskDomain(records, flag);
        if(!result.isSuccess()){
            return result;
        }

        Map<String,Task> resultMap = new HashMap<String, Task>();
        Map<String,List<Task>> groupedTask =taskScheduleService.groupTaskByRecorded(records);
        List<Task> noRecordedTask = groupedTask.get(Constants.TASK_NO_EXIST);//task表中不存在的任务
        List<Task> recordedTask = groupedTask.get(Constants.TASK_EXIST);//task表中存在的任务

        List<Task> insertTasks = taskScheduleService.insertTask(noRecordedTask);
        if( CollectionUtils.isNotEmpty(insertTasks)){
            for(Task task : insertTasks){
                resultMap.put(task.getUuid(), task);
            }
        }

        //针对指定需要更新的任务，进行任务更新并且反查任务的uuid
        if(flag){
            if(CollectionUtils.isNotEmpty(recordedTask)){
                List<Task> needUpdateTask =taskScheduleService.getNeedUpdateTask(recordedTask);
                if( CollectionUtils.isNotEmpty(needUpdateTask)){
                    List<Task> updateTasks =taskScheduleService.updateTaskStateAndMsg(needUpdateTask);
                    if( CollectionUtils.isNotEmpty(updateTasks)){
                        for(Task task : updateTasks){
                            resultMap.put(task.getUuid(), task);
                        }
                    }
                }
            }
        }

        taskScheduleService.registerExtraInfo(param);
        result.setSuccess(true);
        result.setMessage("任务添加完成");
        result.setData(resultMap);
        logger.info(LOGGER_BEGIN+"任务添加/更新/不更新成功,共"+records.size()+"条任务,param:"+JSONUtil.cutParams(records));
        return result;
    }





    /**
        1.前置判断
        2.生成查询条件： 默认任务类型与并发数
        3.根据查询条件锁定任务
             1.查询任务
             2.设置任务新状态、原状态、服务器ip
             3.根据主键（任务类型、任务key）与原状态 更新 新状态
             4.更新成功的加入到封锁对象中
        4.根据任务的运行方式，进行并行与串行运行

     *
     * @return
     */
    public Boolean run(){

        if (!taskScheduleService.excuteBeforeTask()) {
            logger.info(LOGGER_BEGIN+"任务类型为"+taskScheduleService.getBusinessType()+"的任务前置判断失败!");
            return true;
        }

        //生成查询条件
        QueryTaskParam queryTaskParam=taskScheduleService.generateQueryTaskParam();

        do{

            //抓去任务list
            List<Task> updatedTaskList = new ArrayList<Task>();
            while(updatedTaskList.size() <taskScheduleService.getTaskNum()){
                CommonResult<List<Task>> updatedResult = taskScheduleService.getTaskAndLock(queryTaskParam);
                if(updatedResult.isSuccess() && updatedResult.getCode().equals(new Long(210))){
                    break;
                }else if(updatedResult.isSuccess() && updatedResult.getCode().equals(new Long(200))){
                    updatedTaskList.addAll(updatedResult.getData());
                }
            }

            if(updatedTaskList.size()==0){
                logger.info(LOGGER_BEGIN+"任务类型为"+taskScheduleService.getBusinessType()+"的任务当前没有锁定的任务");
                return true;
            }

            logger.info("【线程信息】【name】"+Thread.currentThread().getName()+"【id】"+Thread.currentThread().getId());
            logger.info(LOGGER_BEGIN+"本次共锁定任务类型为"+taskScheduleService.getBusinessType()+"的任务"+updatedTaskList.size()+"条,business_key:["+getBusinessKey(updatedTaskList)+"]");


            if (taskScheduleService.getTaskScheduleEnum() == TaskScheduleEnum.SERIAL) {
                serialRun(updatedTaskList);
            }else{
                concurrentRun(updatedTaskList);
            }

        }while (true);


    }




    /**
     * 多线程执行任务
     *
     * @param updatedTaskList
     */
    private void concurrentRun(List<Task> updatedTaskList) {

        CountDownLatch countDownLatch = new CountDownLatch(updatedTaskList.size());
        for (int i = 0; i < updatedTaskList.size(); i++) {
            int index = i;
            pool.execute(new Runnable() {
                @Override
                public void run() {
                    try{
                        logger.info("多线程定时任务执行开始.....");


                        List<Task> list = new ArrayList<Task>();
                        list.add(updatedTaskList.get(index));

                        //1.执行任务
                        List<Task> excutedTaskList = taskScheduleService.excuteTask(list);

                        //2.更新任务状态为完成
                        for(Task task : excutedTaskList){
                            task.setMessage(task.getMessage());
                            task.setServerIp(NetworkUtil.getLocalIP());//处理不更新serverIp
                            List<Integer> sourceStateList = new ArrayList<Integer>();
                            sourceStateList.add(TaskStateEnum.PROCESSING_TASK.getState());
                            task.setSourceStateList(sourceStateList);
                        }
                        List<Task> completedTaskList = taskScheduleService.updateTaskStateAndMsg(excutedTaskList);


                        //3.后置任务捕获异常，不影响整个任务执行的结果
                        try{
                            taskScheduleService.excuteAfterTask(completedTaskList);
                        }catch(Exception ex){
                            logger.error(LOGGER_BEGIN+"任务执行，后置任务执行失败，错误信息：", ex);
                        }

                    }catch (Exception e) {
                        logger.error("线程执行异常", e);
                    }
                    countDownLatch.countDown();
                    logger.info("还有" + countDownLatch.getCount() + "个多线程定时任务执行");
                }
            });
        }

        try {
            countDownLatch.await();
            logger.info("多线程定时任务执行完毕.....");
        } catch (InterruptedException e) {
            throw new RuntimeException(e.getMessage(),e);
        }

    }

    /**
     * 单线程执行任务
     *
     * @param updatedTaskList
     */
    private void serialRun(List<Task> updatedTaskList) {

        //1.执行任务
        List<Task> excutedTaskList = taskScheduleService.excuteTask(updatedTaskList);

        //2.更新任务状态为完成
        for(Task task : excutedTaskList){
            task.setMessage(task.getMessage());
            task.setServerIp(NetworkUtil.getLocalIP());//处理不更新serverIp
            List<Integer> sourceStateList = new ArrayList<Integer>();
            sourceStateList.add(TaskStateEnum.PROCESSING_TASK.getState());
            task.setSourceStateList(sourceStateList);
        }
        List<Task> completedTaskList = taskScheduleService.updateTaskStateAndMsg(excutedTaskList);

        //3.后置任务捕获异常，不影响整个任务执行的结果
        try{
            taskScheduleService.excuteAfterTask(completedTaskList);
        }catch(Exception ex){
            logger.error(LOGGER_BEGIN+"任务执行，后置任务执行失败，错误信息：", ex);
        }
    }


    private String getBusinessKey(List<Task> taskList){
        StringBuffer StrB = new StringBuffer();
        for(Task task : taskList){
            StrB.append(task.getBusinessKey()+",");
        }
        return StrB.toString();
    }





}
