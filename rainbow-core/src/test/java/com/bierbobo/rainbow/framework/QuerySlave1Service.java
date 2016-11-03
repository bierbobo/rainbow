package com.bierbobo.rainbow.framework;

import com.bierbobo.rainbow.domain.common.CommonResult;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by lifubo on 2016/11/2.
 */
public class QuerySlave1Service extends ConcurrentQuerySlave<Entity,String,SlaveEntity> {



    @Override
    public Map<String,SlaveEntity> querySlave(CommonResult<List<Entity>> data) {

        Map<String,SlaveEntity> slaveEntityMap=new HashMap<>();

        SlaveEntity slaveEntity=new SlaveEntity();
        slaveEntity.setName1("name1");
        slaveEntity.setAge1("age1");
        slaveEntity.setDesc1("desc1");

        slaveEntityMap.put("1",slaveEntity);


        return slaveEntityMap;
    }


    @Override
    public void setMasterData(CopyOnWriteArrayList<Entity> masterData, Map<String,SlaveEntity> slaveData) {


        for (Entity entity : masterData) {

            SlaveEntity slaveEntity = slaveData.get(entity.getId());
            entity.setName1(slaveEntity.getName1());
            entity.setAge1(slaveEntity.getAge1());
            entity.setDesc1(slaveEntity.getDesc1());

        }

    }



}
