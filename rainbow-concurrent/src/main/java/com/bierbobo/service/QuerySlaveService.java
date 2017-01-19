package com.bierbobo.service;

import com.bierbobo.concurrent.ConcurrentQuerySlave;
import com.bierbobo.domain.entity.Entity;
import com.bierbobo.domain.entity.SlaveEntity;
import com.bierbobo.rainbow.domain.common.CommonResult;
import com.bierbobo.rainbow.domain.common.Page;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Created by lifubo on 2016/11/2.
 */
public class QuerySlaveService extends ConcurrentQuerySlave<Entity,String,SlaveEntity> {

    /**
     * 接口调用的时候用的线程池
     */
    private static final int corePoolSize = 10;
    private static final int maximumPoolSize = 100;
    private static final int keepAliveTime = 60;

    private static final ThreadPoolExecutor pool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime,
            TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(10000), new ThreadPoolExecutor.CallerRunsPolicy());


    @Override
    public Map<String, SlaveEntity> querySlave(CommonResult<List<Entity>> data) {


        serialQuerySlave(data);
        ConcurrentQuerySlave(data);

        return null;

    }


    /**
     * 分页串行查询
     *
     * @param data
     * @return
     */
    private Map<String, SlaveEntity> serialQuerySlave(CommonResult<List<Entity>> data) {

        List<Entity> infoList=data.getData();
        List<SlaveEntity> slaveList=new ArrayList<SlaveEntity>();

        Page<Long> page = new Page<Long>();
        page.setPageSize(10).setTotalCount(infoList.size());
        for(int pageNo =1;pageNo<=page.getTotalPage();pageNo++){

            page.setPageNo(pageNo);

            List<Entity> queryList=new ArrayList<Entity>();
            for(int infoIndex=page.getStartRow() - 1;infoIndex<page.getEndRow();infoIndex++){
                queryList.add(infoList.get(infoIndex));
            }

            List<SlaveEntity> querySlaveList=new ArrayList<SlaveEntity>();
            //querySlaveList=service.querySlaveList(queryList);
            slaveList.addAll(querySlaveList);

        }
        return this.buildResultMap(slaveList);
    }


    /**
     *
     * 分页并行查询
     *
     * @param data
     * @return
     */
    private Map<String, SlaveEntity> ConcurrentQuerySlave(CommonResult<List<Entity>> data) {

        int sleepMillSec = 4;

        List<Entity> infoList=data.getData();
        List<SlaveEntity> slaveList=new ArrayList<SlaveEntity>();

        Page<Long> page = new Page<Long>();
        page.setPageSize(10).setTotalCount(infoList.size());

        CountDownLatch countDownLatch = new CountDownLatch( page.getTotalPage() );

        for(int pageNo =1;pageNo<=page.getTotalPage();pageNo++){

            page.setPageNo(pageNo);

            List<Entity> queryList=new ArrayList<Entity>();
            for(int infoIndex=page.getStartRow() - 1;infoIndex<page.getEndRow();infoIndex++){
                queryList.add(infoList.get(infoIndex));
            }

            List<SlaveEntity> querySlaveList=new ArrayList<SlaveEntity>();

            pool.execute(new Thread() {
                public void run() {
                    //querySlaveList=service.querySlaveList(queryList);
                    slaveList.addAll(querySlaveList);
                    countDownLatch.countDown();
                }
            });

            try {
                //sleep 4毫秒，减少接口的压力
                Thread.sleep(sleepMillSec);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return this.buildResultMap(slaveList);
    }


    private Map<String, SlaveEntity> buildResultMap(List<SlaveEntity> slaveList) {

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
