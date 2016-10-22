package com.bierbobo.dao;





import com.bierbobo.domain.database.Task;
import com.bierbobo.domain.task.NewTaskOfBIDataTaskParam;
import com.bierbobo.domain.task.QueryTaskParam;
import com.bierbobo.domain.task.RepeatTaskStateParam;
import com.bierbobo.domain.task.TaskGroupTypeParam;

import java.util.List;

public interface TaskDAO {


    public List<Task> selectTaskList(QueryTaskParam queryTaskParam);
    public List<Task> updateTaskStateAndMsg(List<Task> updateTaskParam);





    int deleteByPrimaryKey(String businessType, String businessKey);

    void insert(Task record);

    void insertSelective(Task record);

    Task selectByPrimaryKey(String businessType, String businessKey);

    int updateByPrimaryKeySelective(Task record);

    int updateByPrimaryKey(Task record);
    
//    public Integer insertUnduplicated(RegisterTaskDomain records);
    public void recordTask(Task record);
//    public void updateRecordedTask(Task recordedTask);

    public List<Task> selectTaskListGrType(TaskGroupTypeParam updatedTaskParam);
    
    List<Task> selectBIDataTaskGrName(TaskGroupTypeParam taskGroupTypeParam);
    
    public int hasNewTaskOfBIDataTask(NewTaskOfBIDataTaskParam newTaskOfBIDataTaskParam);
    
    public List<String> selectBusinessKeysGrType(TaskGroupTypeParam tempSelTaskParam);
    
    public Integer repeatTaskStateByBusinessType(RepeatTaskStateParam param);
    
    
}