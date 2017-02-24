package com.bierbobo.rainbow.data.nosql.es.repository;

public interface OrderEsCommonRepository {
    /**
     * 创建索引
     * @return
     */
    public boolean createOrderIndex();
}