package com.bierbobo.rainbow.data.nosql.es.repository;

import com.bierbobo.rainbow.data.nosql.es.domain.UserBean;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface UserRepository extends ElasticsearchRepository<UserBean, String> {

    /**
     * spring data提供的根据方法名称的查询方式
     * @param userName
     * @return
     */
    public UserBean findByUsername(String userName);

    /**
     * 使用Query注解指定查询语句
     * @param userName
     * @return
     */
    //双引号和不加引号都可，不能是单引号
    @Query("{bool : {must : [ {term : {userName : ?}}  ]}}")
//    @Query("{\"bool\" : {\"must\" : [ {\"term\" : {\"skuName\" : \"?0\"}}  ]}}")
    //注意：需要替换的参数？需要加双引号；需要指定参数下标，从0开始
    public UserBean findByName(String userName);

    //还有分页、排序等API




}