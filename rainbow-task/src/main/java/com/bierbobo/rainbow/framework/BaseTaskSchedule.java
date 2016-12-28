package com.bierbobo.rainbow.framework;

import com.bierbobo.rainbow.dao.TaskDAO;
import com.bierbobo.rainbow.domain.common.CommonResult;
import com.bierbobo.rainbow.domain.common.TaskScheduleEnum;
import com.bierbobo.rainbow.domain.entity.Task;
import com.bierbobo.rainbow.domain.task.QueryTaskParam;
import com.bierbobo.rainbow.domain.task.TaskStateEnum;
import com.bierbobo.rainbow.util.NetworkUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by lifubo on 2016/10/19.
 */
public abstract class BaseTaskSchedule {

    private static String LOGGER_BEGIN = "【任务统一处理框架】";
    Logger logger = Logger.getLogger(BaseTaskSchedule.class);

    private String businessType;

    private Integer taskNum; //每次任务的处理数量

    @Value("${taskNumMultiple}")
    private  int taskNumMultiple; //并发处理的任务数

    private TaskScheduleEnum taskScheduleEnum; //任务的执行类型：串行 、并行

//    @Resource
    private TaskDAO taskDao;


    //处理采销大表导出Excel数据,每次在数据库读取 taskNumMultiple 条数据 然后开启 taskNumMultiple 线程执行
    private static final int corePoolSize = 10;
    private static final int maximumPoolSize = 100;
    private static final int keepAliveTime = 60;

    private static final ThreadPoolExecutor pool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime,
            TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(100), new ThreadPoolExecutor.CallerRunsPolicy());


    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {

        this.businessType = businessType;
    }

    public TaskScheduleEnum getTaskScheduleEnum() {
        return taskScheduleEnum;
    }

    public void setTaskScheduleEnum(TaskScheduleEnum taskScheduleEnum) {
        this.taskScheduleEnum = taskScheduleEnum;
    }

    public Integer getTaskNum() {
        return taskNum;
    }

    public void setTaskNum(Integer taskNum) {
        this.taskNum = taskNum;
    }

    /**
     * 任务执行前的判断
     * @return
     */
    protected boolean excuteBeforeTask(){
        return true;
    }

    /**
     * 生成每次执行任务所用的查询条件
     * @return
     */
    protected QueryTaskParam generateQueryTaskParam(){
        return new QueryTaskParam(businessType,taskNumMultiple);
    }


    protected void excuteAfterTask(List<Task> taskList){

    }

    @Transactional
    protected abstract List<Task> excuteTask(List<Task> taskList);

    //public abstract <T> void registerExtraInfo(List<T> param);



    public Boolean run(){

        if (!excuteBeforeTask()) {
            logger.info(LOGGER_BEGIN+"任务类型为"+businessType+"的任务前置判断失败!");
            return true;
        }

        //生成查询条件
        QueryTaskParam queryTaskParam=this.generateQueryTaskParam();

        do{

            //抓去任务list
            List<Task> updatedTaskList = new ArrayList<Task>();
            while(updatedTaskList.size() < getTaskNum()){
                CommonResult<List<Task>> updatedResult = getTaskAndLock(queryTaskParam);
                if(updatedResult.isSuccess() && updatedResult.getCode().equals(new Long(210))){
                    break;
                }else if(updatedResult.isSuccess() && updatedResult.getCode().equals(new Long(200))){
                    updatedTaskList.addAll(updatedResult.getData());
                }
            }

            if(updatedTaskList.size()==0){
                logger.info(LOGGER_BEGIN+"任务类型为"+businessType+"的任务当前没有锁定的任务");
                return true;
            }

            logger.info("【线程信息】【name】"+Thread.currentThread().getName()+"【id】"+Thread.currentThread().getId());
            logger.info(LOGGER_BEGIN+"本次共锁定任务类型为"+businessType+"的任务"+updatedTaskList.size()+"条,business_key:["+getBusinessKey(updatedTaskList)+"]");


            if (taskScheduleEnum == TaskScheduleEnum.SERIAL) {
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
                        List<Task> excutedTaskList = BaseTaskSchedule.this.excuteTask(list);

                        //2.更新任务状态为完成
                        for(Task task : excutedTaskList){
                            task.setMessage(task.getMessage());
                            task.setServerIp(NetworkUtil.getLocalIP());//处理不更新serverIp
                            List<Integer> sourceStateList = new ArrayList<Integer>();
                            sourceStateList.add(TaskStateEnum.PROCESSING_TASK.getState());
                            task.setSourceStateList(sourceStateList);
                        }
                        List<Task> completedTaskList = updateTaskStateAndMsg(excutedTaskList);


                        //3.后置任务捕获异常，不影响整个任务执行的结果
                        try{
                            excuteAfterTask(completedTaskList);
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
        List<Task> excutedTaskList = this.excuteTask(updatedTaskList);

        //2.更新任务状态为完成
        for(Task task : excutedTaskList){
            task.setMessage(task.getMessage());
            task.setServerIp(NetworkUtil.getLocalIP());//处理不更新serverIp
            List<Integer> sourceStateList = new ArrayList<Integer>();
            sourceStateList.add(TaskStateEnum.PROCESSING_TASK.getState());
            task.setSourceStateList(sourceStateList);
        }
        List<Task> completedTaskList = this.updateTaskStateAndMsg(excutedTaskList);

        //3.后置任务捕获异常，不影响整个任务执行的结果
        try{
            excuteAfterTask(completedTaskList);
        }catch(Exception ex){
            logger.error(LOGGER_BEGIN+"任务执行，后置任务执行失败，错误信息：", ex);
        }

    }



    private CommonResult<List<Task>> getTaskAndLock(QueryTaskParam queryTaskParam){

        CommonResult<List<Task>> result = new CommonResult<List<Task>>();
        result.setSuccess(false);

        //获取指定数量的任务
        List<Task> taskList = this.getTask(queryTaskParam);
        if(taskList==null||taskList.size()==0){
            logger.info(LOGGER_BEGIN+"任务类型为"+businessType+"的任务当前没有待执行的任务");
            result.setSuccess(true);
            result.setCode(210L);
            result.setData(taskList);
            return result;
        }

        for(Task task : taskList){
            task.setState(TaskStateEnum.PROCESSING_TASK.getState());
            task.setSourceStateList(this.getSourceStateList());
            //封装更新的状态为锁定，serverIp为当前服务器Ip
            task.setServerIp(NetworkUtil.getLocalIP());
        }

        //封装更新任务的对象：【锁定】
        List<Task> resultList = new ArrayList<Task>();
        resultList.addAll(updateTaskStateAndMsg(taskList));
        result.setSuccess(true);
        result.setCode(200L);
        result.setData(resultList);

        return result;
    }


    protected List<Task> getTask(QueryTaskParam queryTaskParam ){

        List<Task> taskList = taskDao.selectTaskList(queryTaskParam);
        if(taskList!=null&&taskList.size()>0){
            logger.info(LOGGER_BEGIN+"获取到任务数据，共获取"+taskList.size()+"条任务,businessType:"+businessType);
        }else{
            logger.info(LOGGER_BEGIN+"没有获取到任务数据,businessType:"+businessType);
            taskList = new ArrayList<Task>();
        }
        return taskList;
    }


    protected List<Integer> getSourceStateList(){
        List<Integer> sourceStateList = new ArrayList<Integer>();
        sourceStateList.add(TaskStateEnum.NEW_TASK.getState());
        return sourceStateList;
    }


    @Transactional
    private List<Task> updateTaskStateAndMsg(List<Task> records){

        List<Task> resultList =null;
        try{
            resultList = taskDao.updateTaskStateAndMsg(records);
            if(resultList==null||resultList.size()==0){
                resultList = new ArrayList<Task>();
                logger.info(LOGGER_BEGIN+"任务数据没有更新,任务businessType:"+businessType);
            }else{
                logger.info(LOGGER_BEGIN+"任务数据更新,共更新"+resultList.size()+"条任务,businessType:"+businessType);
            }
        }catch(Exception ex){
            logger.error(LOGGER_BEGIN+"任务数据更新失败,事务回滚,本次任务businessType:"+businessType,ex);
            throw new RuntimeException(ex);
        }
        return resultList;
    }


    private String getBusinessKey(List<Task> taskList){
        StringBuffer StrB = new StringBuffer();
        for(Task task : taskList){
            StrB.append(task.getBusinessKey()+",");
        }
        return StrB.toString();
    }

}
