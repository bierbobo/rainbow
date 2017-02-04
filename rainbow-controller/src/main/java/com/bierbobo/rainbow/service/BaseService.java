package com.bierbobo.rainbow.service;

import java.util.List;

/**
 * Created by lifubo on 2016/11/28.
 */
public interface BaseService<CommonResult,QueryParam,Id> {

    List<CommonResult> queryList(QueryParam queryParam);

    CommonResult query(QueryParam queryParam);

    CommonResult queryById(Id id);

    boolean insert(QueryParam queryParam);

    boolean update(QueryParam queryParam);

    boolean delete(QueryParam queryParam);


}
