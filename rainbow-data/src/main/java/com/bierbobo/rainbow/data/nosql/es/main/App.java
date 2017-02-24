package com.bierbobo.rainbow.data.nosql.es.main;

import com.bierbobo.rainbow.data.nosql.es.repository.UserRepository;
import com.bierbobo.rainbow.data.orm.mybatis.dao.UserMapper;
import com.bierbobo.rainbow.data.orm.mybatis.domain.UserBean;
import com.bierbobo.rainbow.data.orm.mybatis.main.DBTools;
import org.apache.ibatis.session.SqlSession;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception {

        springRun();
//        mainRun();

    }


    private static void springRun() throws Exception {
        String paths[] = {"classpath:spring/applicationContext.xml"};
        ApplicationContext context = new ClassPathXmlApplicationContext(paths);
        UserRepository userRepository = (UserRepository) context.getBean("userRepository");
//       userRepository.createOrderIndex();

        userRepository.findAll();
    }



    private static void mainRun() throws Exception {

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
