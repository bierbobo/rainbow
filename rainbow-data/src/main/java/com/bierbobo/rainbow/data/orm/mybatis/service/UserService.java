package com.bierbobo.rainbow.data.orm.mybatis.service;

import java.util.List;


import com.bierbobo.rainbow.data.orm.mybatis.dao.UserMapper;
import com.bierbobo.rainbow.data.orm.mybatis.domain.UserBean;
import com.bierbobo.rainbow.data.orm.mybatis.main.DBTools;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    public void insertUser() throws Exception{
        UserBean user = new UserBean("张三"+System.currentTimeMillis(), "1314520", 7000.0);
        userMapper.insertUser(user);
    }

    /**
     * 查询所有的用户
     */
    public   void selectAllUser(){

        try {
            List<UserBean> user=userMapper.selectAllUser();
            System.out.println(user.toString());
        } catch (Exception e) {
            e.printStackTrace();

        }
    }


    /**
     * 删除用户
     */
    public  void deleteUser(){
        try {
            userMapper.deleteUser(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}