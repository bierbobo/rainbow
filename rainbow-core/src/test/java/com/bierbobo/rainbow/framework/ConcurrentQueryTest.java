package com.bierbobo.rainbow.framework;

import com.bierbobo.rainbow.domain.common.CommonResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lifubo on 2016/11/3.
 */
public class ConcurrentQueryTest {

    public static void main(String[] args) {


        QueryMasterService masterService=new QueryMasterService();

        QuerySlave1Service querySlave1Service=new QuerySlave1Service();
        QuerySlave2Service querySlave2Service=new QuerySlave2Service();
//        QuerySlave3Service querySlave3Service=new QuerySlave3Service();

        List<ConcurrentQuerySlave> slaveList =new ArrayList<>();
        slaveList.add(querySlave1Service);
        slaveList.add(querySlave2Service);
//        slaveList.add(querySlave3Service);

        masterService.setSlaveList(slaveList);

        CommonResult<List<Entity>> commonResult = masterService.queryFullData(null);
        System.out.println(commonResult.getData());



    }

}
