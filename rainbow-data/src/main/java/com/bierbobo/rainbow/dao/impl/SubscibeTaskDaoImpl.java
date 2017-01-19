package com.bierbobo.rainbow.dao.impl;


import com.bierbobo.rainbow.dao.SubscibeTaskDao;
import com.bierbobo.rainbow.domain.SubscibeTask;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lifubo on 2016/11/4.
 */
@Repository
public class SubscibeTaskDaoImpl extends SqlMapClientDaoSupport implements SubscibeTaskDao {


    @Override
    public List<SubscibeTask> querySubscibeTaskList(SubscibeTask subscibeTask) {
        return (List<SubscibeTask>)getSqlMapClientTemplate().queryForList("subscibe_task.list",subscibeTask);
    }

    @Override
    public SubscibeTask querySubscibeTask(SubscibeTask subscibeTask) {
        return (SubscibeTask) getSqlMapClientTemplate().queryForObject("subscibe_task.queryById", subscibeTask);
    }

    @Override
    public void insertSubscibeTask(SubscibeTask subscibeTask) {
        getSqlMapClientTemplate().insert("subscibe_task.insert", subscibeTask);
    }

    @Override
    public int updateSubscibeTask(SubscibeTask subscibeTask) {
        return getSqlMapClientTemplate().update("subscibe_task.update", subscibeTask);
    }

    @Override
    public int deleteSubscibeTask(SubscibeTask subscibeTask) {
        return getSqlMapClientTemplate().delete("subscibe_task.delete", subscibeTask);
    }


    @Override
    public List<SubscibeTask> queryNextExecSubscibeTaskList(SubscibeTask subscibeTask) {
        return getSqlMapClientTemplate().queryForList("subscibe_task.queryNextExecSubscibeTaskList", subscibeTask);
    }

    @Override
    public List<SubscibeTask> updateTaskStateAndMsg(List<SubscibeTask> subscibeTasks) {

        final List<SubscibeTask> returnTaskList = new ArrayList<SubscibeTask>();
        for(int i = 0; i < subscibeTasks.size(); i++){
            SubscibeTask temp = subscibeTasks.get(i);
            int rows = getSqlMapClientTemplate().update("subscibe_task.updateTaskStateAndMsg",temp);
            if(rows == 1){
                returnTaskList.add(temp);
            }
        }
        return returnTaskList;

    }
}
