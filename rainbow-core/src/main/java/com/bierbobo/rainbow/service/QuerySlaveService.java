package com.bierbobo.rainbow.service;

import com.bierbobo.rainbow.domain.common.CommonResult;
import com.bierbobo.rainbow.domain.common.Page;
import com.bierbobo.rainbow.domain.entity.Entity;
import com.bierbobo.rainbow.domain.entity.SlaveEntity;
import com.bierbobo.rainbow.framework.ConcurrentQuerySlave;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by lifubo on 2016/11/2.
 */
public class QuerySlaveService extends ConcurrentQuerySlave<Entity,String,SlaveEntity> {

    @Override
    public Map<String, SlaveEntity> querySlave(CommonResult<List<Entity>> data) {
        return null;
    }

    @Override
    public void setMasterData(CopyOnWriteArrayList<Entity> masterData, Map<String, SlaveEntity> slaveData) {

        for (Entity entity : masterData) {

            // set some things
            entity.setThings("");

        }

    }

}
