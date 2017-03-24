package com.bierbobo.rainbow.data.nosql.es.repository;


import com.bierbobo.rainbow.data.nosql.es.domain.UserIndex;
import org.springframework.data.repository.Repository;

public interface UserRepository2 extends  Repository<UserIndex, String>{
    /**
     * 创建索引
     * @return
     */
    public boolean createIndex();
    public boolean deleteIndex();



}