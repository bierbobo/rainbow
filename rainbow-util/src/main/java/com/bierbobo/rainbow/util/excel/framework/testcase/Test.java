package com.bierbobo.rainbow.util.excel.framework.testcase;

import com.bierbobo.rainbow.util.excel.framework.ExcelHandler;

import java.util.HashMap;
import java.util.List;

/**
 * Created by lifubo on 2016/12/28.
 */
public class Test {

    public static void main(String[] args) {

        ExcelHandler excelHandler=new ExcelHandler();


        MapData mapData=new MapData();
        mapData.setFilePath("d:/3.xlsx");
        excelHandler.generateExcel(mapData);


        Cell2Map cell2Map = new Cell2Map();
        cell2Map.setFilePath("d:/3.xlsx");
        List<HashMap> hashMaps = excelHandler.readExcel(HashMap.class,cell2Map );

        System.out.println(hashMaps);
    }
}
