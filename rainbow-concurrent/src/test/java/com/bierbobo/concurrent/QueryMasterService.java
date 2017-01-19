package com.bierbobo.concurrent;

import com.bierbobo.rainbow.domain.common.CommonResult;
import com.bierbobo.rainbow.domain.vo.QueryVO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lifubo on 2016/11/2.
 */
public class QueryMasterService extends ConcurrentQueryMaster<QueryVO,Entity> {


    private List<ConcurrentQuerySlave>  slaveList;

    public void setSlaveList(List<ConcurrentQuerySlave> slaveList) {
        this.slaveList = slaveList;
    }


    @Override
    protected List<ConcurrentQuerySlave> getSlaveList() {
       return slaveList;
    }


    @Override
    protected CommonResult<List<Entity>> queryMasterData(QueryVO queryParams) {

        CommonResult<List<Entity>> dataPage = new CommonResult<List<Entity>>();

        List<Entity> entityList = new ArrayList<>();
        Entity entity=new Entity();
        entity.setId("1");
        entity.setName("name");
        entityList.add(entity);

        dataPage.setData(entityList);

        return dataPage;

    }
}
