package com.bierbobo.rainbow.data.orm.jdbc;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * Created by lifubo on 2017/4/27.
 */
public class Main {

    public static void main(String[] args) {


        String paths[] = {"classpath:spring/spring-jdbc.xml"};
        ApplicationContext context = new ClassPathXmlApplicationContext(paths);
        JdbcTemplate jdbcTemplate = (JdbcTemplate) context.getBean("jdbcTemplate");
        System.out.println(jdbcTemplate);


        String sql="select  column_name, ORDINAL_POSITION as columnPosition , DATA_TYPE as column_type, COLUMN_KEY as columnKey,  column_comment    from  information_schema.columns  where table_name='wil_rule_sku_limit'";
        List<TableColumn> entities = jdbcTemplate.query(sql,   new BeanPropertyRowMapper(TableColumn.class));
        for (TableColumn entity : entities) {
            System.out.println(entity);
        }



    }
}
