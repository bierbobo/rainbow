package com.bierbobo.rainbow.framework;


import com.bierbobo.rainbow.domain.common.CommonResult;
import com.bierbobo.rainbow.domain.common.Page;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Created by lifubo on 2016/11/2.
 */
public abstract class ConcurrentQueryMaster<Param,ResultData> {


    Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final int corePoolSize = 10;
    private static final int maximumPoolSize = 100;
    private static final int keepAliveTime = 60;

    private static final ThreadPoolExecutor pool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime,
            TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(100), new ThreadPoolExecutor.CallerRunsPolicy());


    /**
     *
     *1.
     *
     * @param queryParams
     * @return
     */
    public CommonResult<List<ResultData>> queryFullData(Param queryParams) {

        //先查询出主要数据
        final CommonResult<List<ResultData>> dataPage = this.queryMasterData(queryParams);

        //根据spring配置得到所有子查询列表
        List<ConcurrentQuerySlave> slaveList = this.getSlaveList();

        List<ResultData> data = dataPage.getData();

        if (slaveList != null && dataPage!=null &&  CollectionUtils.isNotEmpty(data)) {

            CountDownLatch countDownLatch = new CountDownLatch(slaveList.size());
            for (ConcurrentQuerySlave slave : slaveList) {
                pool.execute(new Runnable() {

                    @Override
                    public void run() {
                        try {
                            Map<?,?> slaveData = slave.querySlave(dataPage);

                            System.out.println("slaveData::::"+slaveData);

                            CopyOnWriteArrayList<ResultData> copyMasterData = new CopyOnWriteArrayList<ResultData>(data);
                            slave.setMasterData(copyMasterData, slaveData);
                            System.out.println("复制后的copyMasterData::::"+copyMasterData);

                        } catch (Exception e) {
                            logger.error("Slave Query exception:", e);
                        }

                        countDownLatch.countDown();
                        System.out.println("当前未完成线程数{}" + countDownLatch.getCount());
                        logger.info("当前未完成线程数{}", countDownLatch.getCount());
                    }

                });
            }
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
                logger.error("多线程出错", e);
            }

        }
        return dataPage;

    }




    /**
     * 获取子查询类列表，声明一个成员变量，并在spring中配置
     * @return
     */
    protected abstract List<ConcurrentQuerySlave> getSlaveList();


    /**
     * 查询主要数据
     * @param queryParams
     * @return
     */
    protected abstract CommonResult<List<ResultData>> queryMasterData(Param queryParams);



}
