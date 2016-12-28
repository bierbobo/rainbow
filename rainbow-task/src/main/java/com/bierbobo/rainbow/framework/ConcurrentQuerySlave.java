package com.bierbobo.rainbow.framework;

import com.bierbobo.rainbow.domain.common.CommonResult;
import com.bierbobo.rainbow.domain.common.Page;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by lifubo on 2016/11/2.
 */
public abstract class ConcurrentQuerySlave<ResultData,Id,ResultSlaveData> {



    /**
     *
     *  根据传入的基础数据，查询子数据
     *
     *
     * 1.根据 基础数据构建查询条件
     * 2.根据查询条件 进行 分页查询
     * 3.根据查询结果  构建  map<主数据的唯一key值,子数据>  （map结构为了可以快速找到主数据对应的子数据）
     *
     * @param data 基础数据集合
     * @return 子数据Map
     */
    public abstract Map<Id,ResultSlaveData> querySlave(CommonResult<List<ResultData>> data);


    /**
     * 把子数据组装到主数据集合中
     *
     * 1.遍历基础数据，将子数据填充到基础数据中
     *
     * @param masterData
     * @param slaveData
     */
    public  abstract void setMasterData(CopyOnWriteArrayList<ResultData> masterData, Map<Id,ResultSlaveData> slaveData);


}
