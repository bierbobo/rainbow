package com.bierbobo.rainbow.data.nosql.es.repository;


import org.springframework.data.repository.Repository;

public interface OrderEsCommonRepository {
    /**
     * 创建索引
     * @return
     */
    public boolean createOrderIndex();
}