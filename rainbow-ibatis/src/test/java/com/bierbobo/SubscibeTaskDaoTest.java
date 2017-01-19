package com.bierbobo;

import com.alibaba.fastjson.JSON;
import com.bierbobo.rainbow.dao.SubscibeTaskDao;
import com.bierbobo.rainbow.domain.SubscibeTask;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lifubo on 2016/11/9.
 */
public class SubscibeTaskDaoTest extends BIIPBaseTest {

//    @Resource
    @Autowired
    private SubscibeTaskDao subscibeTaskDao;


    @Test
    public void curd() {

        List<SubscibeTask> subscibeTasks = null;
        SubscibeTask subscibeTask =null;
        String json =null;


        json = "{\"id\":\"111\",\"name\":\"111\",\"templateId\":\"111\",\"templateName\":\"111\",\"pushMode\":\"111\",\"pushTime\":\"111\",\"exportType\":\"111\",\"describe\":\"111\",\"nextExecDate\":\"111\",\"creator\":\"111\"," +
                "\"updator\":\"111\",\"createTime\":\"111\",\"updateTime\":\"111\",\"creatorName\":\"111\",\"userRole\":\"111\",\"email\":\"111\",\"state\":\"0\",\"serverIp\":\"111\",\"message\":\"111\",}";

        //查询所有
        subscibeTasks= subscibeTaskDao.querySubscibeTaskList(null);
        System.out.println(subscibeTasks);



/*

        // 创建
        subscibeTask = JSON.parseObject(json, SubscibeTask.beanClass);

        subscibeTaskDao.insertSubscibeTask(subscibeTask);

        subscibeTask.setId("222");
        subscibeTaskDao.insertSubscibeTask(subscibeTask);

        subscibeTask.setId("333");
        subscibeTaskDao.insertSubscibeTask(subscibeTask);


        //修改
        json = "{\"id\":\"222\",\"name\":\"111\",\"templateId\":\"111\",\"templateName\":\"111\",\"pushMode\":\"111\",\"pushTime\":\"111\",\"exportType\":\"111\",\"describe\":\"111\",\"nextExecDate\":\"111\",\"creator\":\"111\"," +
                "\"updator\":\"111\",\"createTime\":\"111\",\"updateTime\":\"111\",\"creatorName\":\"111\",\"userRole\":\"111\",\"email\":\"111\",\"state\":\"0\",\"serverIp\":\"111\",\"message\":\"111\",}";

        subscibeTask = JSON.parseObject(json, SubscibeTask.beanClass);
        subscibeTaskDao.updateSubscibeTask(subscibeTask);

        //删除
        subscibeTask.setId("111");
        subscibeTaskDao.deleteSubscibeTask(subscibeTask);


        //查询单个
        subscibeTask.setId("222");
        SubscibeTask template1 = subscibeTaskDao.querySubscibeTask(subscibeTask);
        System.out.println(template1);



        List<Integer> source=new ArrayList<Integer>(){{
            this.add(0);
        }};
        subscibeTask.setSourceStateList(source);
        subscibeTask.setTaskNum(5);
        subscibeTasks = subscibeTaskDao.queryNextExecSubscibeTaskList(subscibeTask);
        System.out.println(subscibeTasks);


        final SubscibeTask task=subscibeTask;
        List<SubscibeTask> subscibeTaskList=new ArrayList<SubscibeTask>(){{
            this.add(task);
        }};
        subscibeTaskDao.updateTaskStateAndMsg(subscibeTaskList);


*/


    }
}
