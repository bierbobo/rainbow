package com.bierbobo.rainbow.mybatis.service;

import java.util.List;

import com.bierbobo.rainbow.mybatis.dao.UserMapper;
import com.bierbobo.rainbow.mybatis.domain.UserBean;
import org.apache.ibatis.session.SqlSession;



public class UserService {


    public static void main(String[] args) {
          insertUser();
//        deleteUser();
//        selectUserById();
        selectAllUser();
    }

    
    /**
     * 新增用户
     */
    private static void insertUser() {
        SqlSession session = DBTools.getSession();
        UserMapper mapper = session.getMapper(UserMapper.class);
        UserBean user = new UserBean("张三", "1314520", 7000.0);
        try {
            mapper.insertUser(user);
            System.out.println(user.toString());
             session.commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.rollback();
        }
    }
    
    
    /**
     * 删除用户
     */
    private static void deleteUser(){
        SqlSession session=DBTools.getSession();
        UserMapper mapper=session.getMapper(UserMapper.class);
        try {
            mapper.deleteUser(1);
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.rollback();
        }
    }
    
    
    /**
     * 根据id查询用户
     */
    private static void selectUserById(){
        SqlSession session=DBTools.getSession();
        UserMapper mapper=session.getMapper(UserMapper.class);
        try {
        UserBean user=    mapper.selectUserById(2);
        System.out.println(user.toString());
            
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.rollback();
        }
    }
    
    /**
     * 查询所有的用户
     */
    private static void selectAllUser(){
        SqlSession session=DBTools.getSession();
        UserMapper mapper=session.getMapper(UserMapper.class);
        try {
        List<UserBean> user=mapper.selectAllUser();
        System.out.println(user.toString());
        session.commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.rollback();
        }
    }
    

}