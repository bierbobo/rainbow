package com.bierbobo.rainbow;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

/**
 * Created by lifubo on 2016/10/23.
 */
public class Test {


    public static void main(String[] args) {

        ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext("/spring/elasticsearchRepository.xml");
        ElasticsearchTemplate elasticsearchTemplate = (ElasticsearchTemplate) appContext.getBean("elasticsearchTemplate");
        System.out.println(elasticsearchTemplate);
    }
}
