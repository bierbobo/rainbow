package com.bierbobo.rainbow.dao;



import com.bierbobo.rainbow.domain.SubscibeTask;

import java.util.List;

/**
 * Created by lifubo on 2016/10/28.
 */
public interface SubscibeTaskDao {

    List<SubscibeTask> querySubscibeTaskList(SubscibeTask subscibeTask);

    SubscibeTask querySubscibeTask(SubscibeTask subscibeTask);

    void insertSubscibeTask(SubscibeTask subscibeTask);

    int updateSubscibeTask(SubscibeTask subscibeTask);

    int deleteSubscibeTask(SubscibeTask subscibeTask);

    List<SubscibeTask> queryNextExecSubscibeTaskList(SubscibeTask subscibeTask);

    List<SubscibeTask> updateTaskStateAndMsg(List<SubscibeTask> records);
}
