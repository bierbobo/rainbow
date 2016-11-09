package com.bierbobo.rainbow.service.concurrentQuery;

import com.bierbobo.rainbow.domain.common.CommonResult;
import com.bierbobo.rainbow.domain.common.Page;
import com.bierbobo.rainbow.domain.entity.Entity;
import com.bierbobo.rainbow.domain.vo.QueryVO;
import com.bierbobo.rainbow.framework.ConcurrentQueryMaster;
import com.bierbobo.rainbow.framework.ConcurrentQuerySlave;

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
