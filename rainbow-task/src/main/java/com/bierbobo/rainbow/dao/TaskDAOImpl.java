package com.bierbobo.rainbow.dao;

import com.bierbobo.rainbow.domain.entity.Task;
import com.bierbobo.rainbow.domain.vo.QueryTaskParam;
import com.bierbobo.rainbow.domain.vo.TaskGroupParam;
import com.bierbobo.rainbow.util.JSONUtil;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lifubo on 2017/1/20.
 */
public class TaskDAOImpl extends SqlMapClientDaoSupport implements TaskDAO {

    private static String LOGGER_BEGIN = "【Biip任务统一任务框架】";

    public List<Task> selectNewTaskList(QueryTaskParam queryTaskParam){
        return getSqlMapClientTemplate().queryForList("task.selectNewTaskList",queryTaskParam);
    }

    @Override
    public List<Task> selectTaskByTaskGroupParam(TaskGroupParam taskGroupParam) {
        return getSqlMapClientTemplate().queryForList("task.selectTaskByTaskGroupParam", taskGroupParam);
    }

    @Override
    public List<String> selectKeysByTypeAndKeys(TaskGroupParam taskGroupParam) {
        return getSqlMapClientTemplate().queryForList("task.selectKeysByTypeAndKeys", taskGroupParam);
    }

    public List<Task> insertTask(List<Task> taskList) {

        List<Task> returnTaskList = new ArrayList<Task>();

        for(int i = 0; i < taskList.size(); i++){
            Task task=taskList.get(i);
            try{
                getSqlMapClientTemplate().insert("task.insertTask",task);
                returnTaskList.add(task);
            }catch(DuplicateKeyException e){
                logger.info(LOGGER_BEGIN+"注册任务时发生主键冲突，param:"+ JSONUtil.cutParams(task));
            }
        }
        return returnTaskList;
    }

    public List<Task> updateTaskStateAndMsg(List<Task> taskList){

        final List<Task> returnTaskList = new ArrayList<Task>();
        for(int i = 0; i < taskList.size(); i++){
            Task temp = taskList.get(i);
            int rows = getSqlMapClientTemplate().update("task.updateTaskStateAndMsg",temp);
            if(rows == 1){
                returnTaskList.add(temp);
            }
        }
        return returnTaskList;
    }


}
