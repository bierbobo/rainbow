package com.bierbobo.concurrent;

import com.bierbobo.rainbow.domain.common.CommonResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by lifubo on 2016/11/3.
 */
public class QuerySlave3Service extends ConcurrentQuerySlave<Entity,String,SlaveEntity> {



    @Override
    public Map<String,SlaveEntity> querySlave(CommonResult<List<Entity>> data) {

        Map<String,SlaveEntity> slaveEntityMap=new HashMap<>();

        SlaveEntity slaveEntity=new SlaveEntity();
        slaveEntity.setName1("name1");
        slaveEntity.setAge1("age1");
        slaveEntity.setDesc1("desc1");

        slaveEntityMap.put("1",slaveEntity);


        return null;
    }


    @Override
    public void setMasterData(CopyOnWriteArrayList<Entity> masterData, Map<String,SlaveEntity> slaveData) {

        Map<String,SlaveEntity> slaveData1=slaveData;

        for (Entity entity : masterData) {

            Object o = slaveData.get(entity.getId());

            // set some things
            //entity.setThings("");

        }

    }

}
