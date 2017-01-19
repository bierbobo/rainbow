package com.bierbobo.rainbow.excel;

import java.util.ArrayList;
import java.util.List;

/**


public beanClass WareExportVerticalServiceImpl {


    public CommonResult<Boolean> queryData(ExportData data,Task task) {


        QueryResult<WareData> result = new QueryResult<WareData>();//返回对象
        result.setExportType(ExportTypeEnum.getByType(data.getType()));
        result.setQueryFlag(para.getQueryFlag());

        循环设置：
        result.setData(pageWareDataList);  //每页保存的数据

        //排好序的配送中心或者机构的名字
        result.setDisList(this.distinctDistrbution(pageWareDataList));
        result.setCompanyList(this.distinctCompany(pageWareDataList));



        //准备导出数据
        ExportData para

        para.getType()   vo.getQueryFlag()
        para.getQueryCondition()  ----WareDataQueryVO vo
        // sku 和 dc的字段：   {名称：key; 名称：key }  解析成数组
        vo.getWareTitle(), vo.getDisTitle()


        List<ArrayList<String>> dataList = this.readyExprotData(para, result);



        List<String> selectlist;
        //按机构查询selectlist为companyList,否则为disList  名称列表，一种导出有用。
        if(vo != null && vo.getQueryFlag() != null && vo.getQueryFlag().intValue() == Constants.COMPANY_SELECTED){
            selectlist = queryResult.getCompanyList();
        }else{
            selectlist = queryResult.getDisList();
        }

        List<WareData> listData = (List<WareData>) queryResult.getData();
        data = export.process(vo.getWareTitle(), vo.getDisTitle(),selectlist, listData, vo.getQueryFlag());



    }


    private List<ArrayList<String>>    readyExprotData(ExportData para, QueryResult<WareData> queryResult) throws Exception {
                List<ArrayList<String>> data = null;

                List<String> wareTitle, List<String> disTitle,
                List<String> disList, //按机构查询selectlist为companyList,否则为disList
                List<WareData> wareList,  //每页保存的数据
                Integer queryFlag    0;	//按机构查询     1;	//按机构查询

                // 根据key从对象中取值，写入excel, 根据反射取值,组成list<String>


                // 采销大表横向、纵向导出
                POIAbstractExport export = new POIHorizontalExport();;

                if(vo != null){
                    data = export.process(vo.getWareTitle(), vo.getDisTitle(),selectlist, listData, vo.getQueryFlag());
                }

        return data;
    }

}
 */