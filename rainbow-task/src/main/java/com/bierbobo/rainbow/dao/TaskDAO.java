package com.bierbobo.rainbow.dao;





import com.bierbobo.rainbow.domain.entity.Task;

import com.bierbobo.rainbow.domain.vo.QueryTaskParam;

import com.bierbobo.rainbow.domain.task.TaskGroupTypeParam;

import java.util.List;

public interface TaskDAO {


    public List<Task> selectTaskList(QueryTaskParam queryTaskParam);


    /**
     *
     *
     *
     * @param updateTaskParam
     * @return
     */
    public List<Task> updateTaskStateAndMsg(List<Task> updateTaskParam);


    public void recordTask(Task record);
    public List<Task> selectTaskListGrType(TaskGroupTypeParam updatedTaskParam);
    public List<String> selectBusinessKeysGrType(TaskGroupTypeParam tempSelTaskParam);


    int deleteByPrimaryKey(String businessType, String businessKey);

    void insert(Task record);

    void insertSelective(Task record);

    Task selectByPrimaryKey(String businessType, String businessKey);

    int updateByPrimaryKeySelective(Task record);

    int updateByPrimaryKey(Task record);
    
//    public Integer insertUnduplicated(RegisterTaskDomain records);



    List<Task> selectBIDataTaskGrName(TaskGroupTypeParam taskGroupTypeParam);


}