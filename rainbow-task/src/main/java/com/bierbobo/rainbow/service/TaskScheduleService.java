package com.bierbobo.rainbow.service;

import com.bierbobo.rainbow.dao.TaskDAO;
import com.bierbobo.rainbow.domain.common.CommonResult;
import com.bierbobo.rainbow.domain.common.TaskScheduleEnum;
import com.bierbobo.rainbow.domain.entity.Task;
import com.bierbobo.rainbow.domain.task.TaskGroupTypeParam;
import com.bierbobo.rainbow.domain.task.TaskStateEnum;
import com.bierbobo.rainbow.domain.vo.QueryTaskParam;
import com.bierbobo.rainbow.util.JSONUtil;
import com.bierbobo.rainbow.util.NetworkUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lifubo on 2017/1/19.
 */
public abstract class TaskScheduleService {

    private static String LOGGER_BEGIN = "【任务统一处理框架】";
    Logger logger = Logger.getLogger(TaskScheduleService.class);

    @Value("${taskNumMultiple}")
    private  int taskNumMultiple; //并发处理的任务数
    private String businessType;
    private Integer taskNum; //每次任务的处理数量
    private TaskScheduleEnum taskScheduleEnum; //任务的执行类型：串行 、并行
    private TaskDAO taskDao;


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
    public boolean excuteBeforeTask(){
        return true;
    }

    /**
     * 生成每次执行任务所用的查询条件
     * @return
     */
    public QueryTaskParam generateQueryTaskParam(){
        return new QueryTaskParam(businessType,taskNumMultiple);
    }

    @Transactional
    public abstract List<Task> excuteTask(List<Task> taskList);


    public void excuteAfterTask(List<Task> taskList){

    }

    public abstract <T> void registerExtraInfo(List<T> param);


    //  注册任务----------------


    public CommonResult<Map<String,Task>> checkTaskDomain(List<Task> records,boolean flag){

        CommonResult<Map<String,Task>> taskResult = new CommonResult<Map<String,Task>>(false,0L,null);
        if(records==null||records.size()==0){
            taskResult.setMessage("任务记录为空");
            return taskResult;
        }
        if(flag){
            for(Task temp:records){
                if(temp.getEffectiveUpdateTimeSpace()==null){
                    taskResult.setMessage("effectiveUpdateTimeSpace为空");
                    return taskResult;
                }
            }
        }
        for(Task temp:records){
            if(temp.getBusinessType()==null||temp.getBusinessType().trim().length()==0){
                taskResult.setMessage("businessType为空");
                return taskResult;
            }
            if(temp.getBusinessKey()==null||temp.getBusinessKey().trim().length()==0){
                taskResult.setMessage("businessKey为空");
                return taskResult;
            }
        }
        taskResult.setSuccess(true);
        return taskResult;
    }


    public  Map<String,Task> registerTasks(List<Task> noRecordedTask){

        Map<String,Task> resultMap = new HashMap<String, Task>();

        if(noRecordedTask.size()>0){
            for(Task record : noRecordedTask){
                try{
                    taskDao.recordTask(record);
                    resultMap.put(record.getUuid(),record);
                }catch(DuplicateKeyException e){
                    logger.info(LOGGER_BEGIN+"注册任务时发生主键冲突，param:"+ JSONUtil.cutParams(record));
                }
            }
        }

        return resultMap;
    }

    public Map<Integer,List<Task>> groupTaskByRecorded(List<Task> records){
        Map<String,TaskGroupTypeParam> selTaskParamMap = groupTaskByBusinessType(records);
        //按查询的结果分组任务
        List<String> recordedTaskKeys = new ArrayList<String>();
        for(Map.Entry<String,TaskGroupTypeParam> m : selTaskParamMap.entrySet()){
            TaskGroupTypeParam tempSelTaskParam = selTaskParamMap.get(m.getKey());
            recordedTaskKeys.addAll(taskDao.selectBusinessKeysGrType(tempSelTaskParam));
        }
        //需要插入的records组
        List<Task> noRecordedTask = new ArrayList<Task>();
        List<Task> recordedTask = new ArrayList<Task>();
        for(Task temp : records){
            if(recordedTaskKeys.contains(temp.getBusinessKey())){
                recordedTask.add(temp);
            }else{
                noRecordedTask.add(temp);
            }
        }
        Map<Integer,List<Task>> returnMap = new HashMap<Integer,List<Task>>();
        returnMap.put(0,noRecordedTask);
        returnMap.put(1,recordedTask);
        return returnMap;
    }



    //按businessType把任务分组
    private Map<String,TaskGroupTypeParam> groupTaskByBusinessType(List<Task> records){
        Map<String,TaskGroupTypeParam> selTaskParamMap = new HashMap<String,TaskGroupTypeParam>();
        for(Task temp :records){
            //按business_type维度封装查询条件【注册任务之前现在task表中查看相应的任务在task表中是否存在】
            if(selTaskParamMap.keySet().contains(temp.getBusinessType())){
                selTaskParamMap.get(temp.getBusinessType()).getBusinessKeys().add(temp.getBusinessKey());
            }else{
                TaskGroupTypeParam selTaskParam = new TaskGroupTypeParam();
                List<String> businessKeys = new ArrayList<String>();
                businessKeys.add(temp.getBusinessKey());
                selTaskParam.setBusinessKeys(businessKeys);
                selTaskParam.setBusinessType(temp.getBusinessType());
                selTaskParamMap.put(temp.getBusinessType(),selTaskParam);
            }
        }
        return selTaskParamMap;
    }



    //获取已经注册过的并且需要把任务状态更新为待执行的任务
    public List<Task> getNeedUpdateTask(List<Task> existTask){
        //按businessType+effectiveUpdateTimeSpace的维度把任务分组
        Map<String,TaskGroupTypeParam> selTaskParamMap = groupTaskForNeedUpdate(existTask);
        List<Task> returnList = new ArrayList<Task>();
        for(Map.Entry<String, TaskGroupTypeParam> m : selTaskParamMap.entrySet()){
            TaskGroupTypeParam tempSelTaskParam = selTaskParamMap.get(m.getKey());
            List<Integer> sourceStateList = new ArrayList<Integer>();
            sourceStateList.add(TaskStateEnum.COMPLETE_TASK.getState());
            sourceStateList.add(TaskStateEnum.FAIL_TASK.getState());
            tempSelTaskParam.setSourceStateList(sourceStateList);
            returnList.addAll(taskDao.selectTaskListGrType(tempSelTaskParam));
        }
        return returnList;
    }

    //按businessType+effectiveUpdateTimeSpace的维度把任务分组
    private Map<String,TaskGroupTypeParam> groupTaskForNeedUpdate(List<Task> records){
        Map<String,TaskGroupTypeParam> selTaskParamMap = new HashMap<String,TaskGroupTypeParam>();
        for(Task temp :records){
            //按businessType_effectiveUpdateTimeSpace维度封装查询条件
            String mapKey = temp.getBusinessType()+"_"+temp.getEffectiveUpdateTimeSpace();
            if(selTaskParamMap.keySet().contains(mapKey)){
                selTaskParamMap.get(mapKey).getBusinessKeys().add(temp.getBusinessKey());
            }else{
                TaskGroupTypeParam selTaskParam = new TaskGroupTypeParam();
                List<String> businessKeys = new ArrayList<String>();
                businessKeys.add(temp.getBusinessKey());
                selTaskParam.setBusinessKeys(businessKeys);
                selTaskParam.setBusinessType(temp.getBusinessType());
                selTaskParam.setEffectiveUpdateTimeSpace(temp.getEffectiveUpdateTimeSpace());
                selTaskParamMap.put(mapKey,selTaskParam);
            }
        }
        return selTaskParamMap;
    }




    //2=======


    /**

     1.查询任务
     2.设置任务新状态、原状态、服务器ip
     3.根据主键（任务类型、任务key）与原状态 更新 新状态
     4.更新成功的加入到封锁对象中

     * @param queryTaskParam
     * @return
     */
    public CommonResult<List<Task>> getTaskAndLock(QueryTaskParam queryTaskParam){

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


    public List<Task> getTask(QueryTaskParam queryTaskParam ){

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
    public List<Task> updateTaskStateAndMsg(List<Task> records){

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


    protected List<Integer> getSourceStateList(){
        List<Integer> sourceStateList = new ArrayList<Integer>();
        sourceStateList.add(TaskStateEnum.NEW_TASK.getState());
        return sourceStateList;
    }




}
