package com.bierbobo.service;

import com.bierbobo.concurrent.ConcurrentQueryMaster;
import com.bierbobo.concurrent.ConcurrentQuerySlave;
import com.bierbobo.domain.entity.Entity;
import com.bierbobo.rainbow.domain.common.CommonResult;

import com.bierbobo.rainbow.domain.vo.QueryVO;

import java.util.List;

/**
 * Created by lifubo on 2016/11/2.
 */
public class QueryMasterService extends ConcurrentQueryMaster<QueryVO,Entity> {


    @Override
    public List<ConcurrentQuerySlave> getSlaveList() {
        return null;
    }


    @Override
    public CommonResult<List<Entity>> queryMasterData(QueryVO queryParams) {

        CommonResult<List<Entity>> dataPage = new CommonResult<List<Entity>>();

//        dataPage.setPageNo(queryParams.getPageNo());
//        dataPage.setPageSize(queryParams.getPageSize());

        List<Entity> entityList = null;
        dataPage.setData(entityList);

        return dataPage;

    }
}
