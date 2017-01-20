package com.bierbobo.rainbow.dao;





import com.bierbobo.rainbow.domain.entity.Task;

import com.bierbobo.rainbow.domain.vo.QueryTaskParam;

import com.bierbobo.rainbow.domain.vo.TaskGroupParam;

import java.util.List;

public interface TaskDAO {


    public List<Task> selectNewTaskList(QueryTaskParam queryTaskParam);

    /**
     * 按businessType  keys     effectiveUpdateTimeSpace   state
     * @param updatedTaskParam
     * @return
     */
    public List<Task> selectTaskByTaskGroupParam(TaskGroupParam updatedTaskParam);


    /**
     * 按businessType  和 keys  的维度把任务分组
     * @param
     * @return
     */
    public List<String> selectKeysByTypeAndKeys(TaskGroupParam tempSelTaskParam);


    public List<Task> updateTaskStateAndMsg(List<Task> updateTaskParam);

    public List<Task> insertTask(List<Task> taskList);




/*

    int deleteByPrimaryKey(String businessType, String businessKey);

    void insert(Task record);

    void insertSelective(Task record);

    Task selectByPrimaryKey(String businessType, String businessKey);

    int updateByPrimaryKeySelective(Task record);

    int updateByPrimaryKey(Task record);
    
//    public Integer insertUnduplicated(RegisterTaskDomain records);



    List<Task> selectBIDataTaskGrName(TaskGroupTypeParam taskGroupTypeParam);
*/


}