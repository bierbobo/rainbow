package com.bierbobo.rainbow.framework;

import com.bierbobo.rainbow.dao.TaskDAO;
import com.bierbobo.rainbow.domain.common.CommonResult;
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

/**
 * Created by lifubo on 2016/10/19.
 */
public abstract class BaseTaskSchedule {


    private String businessType;
    private Integer taskNum; //每个任务配置的独立数量

    @Value("${taskNumMultiple}")
    private  int taskNumMultiple; //并发处理的任务数

    @Resource
    private TaskDAO taskDao;


    private static String LOGGER_BEGIN = "【任务统一处理框架】";
    Logger logger = Logger.getLogger(BaseTaskSchedule.class);


    public Integer getTaskNum() {
        return taskNum;
    }

    public void setTaskNum(Integer taskNum) {
        this.taskNum = taskNum;
    }

    public Boolean run(){


        //抓去任务list
        final List<Task> updatedTaskList = new ArrayList<Task>();
        while(updatedTaskList.size() < getTaskNum()){
            CommonResult<List<Task>> updatedResult = getTaskAndLock();
            if(updatedResult.isSuccess() && updatedResult.getCode().equals(new Long(210))){
                break;
            }else if(updatedResult.isSuccess() && updatedResult.getCode().equals(new Long(200))){
                updatedTaskList.addAll(updatedResult.getData());
            }
        }



        return true;
    }



    private CommonResult<List<Task>> getTaskAndLock(){

        CommonResult<List<Task>> result = new CommonResult<List<Task>>();
        result.setSuccess(false);

        List<Task> resultList = new ArrayList<Task>();
        QueryTaskParam queryTaskParam = new QueryTaskParam(businessType,taskNumMultiple);

        //获取指定数量的任务
        List<Task> taskList = this.getTask(queryTaskParam);
        if(taskList==null||taskList.size()==0){
            logger.info(LOGGER_BEGIN+"任务类型为"+businessType+"的任务当前没有待执行的任务");
            result.setSuccess(true);
            result.setCode(210L);
            result.setData(resultList);
            return result;
        }

        for(Task task : taskList){
            task.setState(TaskStateEnum.PROCESSING_TASK.getState());

            List<Integer> sourceStateList = new ArrayList<Integer>();
            sourceStateList.add(TaskStateEnum.NEW_TASK.getState());
            task.setSourceStateList(sourceStateList);

            //封装更新的状态为锁定，serverIp为当前服务器Ip
            task.setServerIp(NetworkUtil.getLocalIP());
        }

        //封装更新任务的对象：【锁定】
        resultList.addAll(updateTaskStateAndMsg(taskList));
        result.setSuccess(true);
        result.setCode(200L);
        result.setData(resultList);
        return result;
    }


    private List<Task> getTask(QueryTaskParam queryTaskParam){

        List<Task> taskList = taskDao.selectTaskList(queryTaskParam);
        if(taskList!=null&&taskList.size()>0){
            logger.info(LOGGER_BEGIN+"获取到任务数据，共获取"+taskList.size()+"条任务,businessType:"+businessType);
        }else{
            logger.info(LOGGER_BEGIN+"没有获取到任务数据,businessType:"+businessType);
            taskList = new ArrayList<Task>();
        }
        return taskList;
    }



    @Transactional
    private List<Task> updateTaskStateAndMsg(List<Task> records){

        List<Task> resultList =null;
        try{
            resultList = taskDao.updateTaskStateAndMsg(records);
            if(resultList==null||resultList.size()==0){
                resultList = new ArrayList<Task>();
                logger.info(LOGGER_BEGIN+"任务数据没有更新,任务businessType:"+records.get(0).getBusinessType());
            }else{
                logger.info(LOGGER_BEGIN+"任务数据更新,共更新"+resultList.size()+"条任务,businessType:"+records.get(0).getBusinessType());
            }
        }catch(Exception ex){
            logger.error(LOGGER_BEGIN+"任务数据更新失败,事务回滚,本次任务businessType:"+records.get(0).getBusinessType(),ex);
            throw new RuntimeException(ex);
        }
        return resultList;
    }





    public static void main(String[] args) {

        List<String> returnList = new ArrayList<String>();
        returnList.addAll(null);
        System.out.println(returnList);

    }


}
