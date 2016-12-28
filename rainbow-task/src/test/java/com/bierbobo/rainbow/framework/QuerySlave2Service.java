package com.bierbobo.rainbow.framework;

import com.bierbobo.rainbow.domain.common.CommonResult;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by lifubo on 2016/11/3.
 */
public class QuerySlave2Service extends ConcurrentQuerySlave<Entity,String,SlaveEntity> {



    @Override
    public Map<String,SlaveEntity> querySlave(CommonResult<List<Entity>> data) {

        Map<String,SlaveEntity> slaveEntityMap=new HashMap<>();

        SlaveEntity slaveEntity=new SlaveEntity();
        slaveEntity.setName2("Name2");
        slaveEntity.setAge2("age2");
        slaveEntity.setDesc2("desc2");

        slaveEntityMap.put("1",slaveEntity);


        return slaveEntityMap;
    }


    @Override
    public void setMasterData(CopyOnWriteArrayList<Entity> masterData, Map<String,SlaveEntity> slaveData) {


        for (com.bierbobo.rainbow.framework.Entity entity : masterData) {

            SlaveEntity slaveEntity = slaveData.get(entity.getId());
            entity.setName2(slaveEntity.getName2());
            entity.setAge2(slaveEntity.getAge2());
            entity.setDesc2(slaveEntity.getDesc2());

        }

    }

}
