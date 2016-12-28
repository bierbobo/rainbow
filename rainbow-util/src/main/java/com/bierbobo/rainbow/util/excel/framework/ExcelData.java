package com.bierbobo.rainbow.util.excel.framework;



import java.util.List;

/**
 * Created by lifubo on 2016/12/27.
 */
public class ExcelData<T> {



    //标题列 -- 简单值
    private List<String> colTitleList;

    List<ExcelCellFormat> colFormatList;

    //1. T 可以为list  其中数据直接写入到excel
    //2. T 可以为map  其中数据根据key写入到excel
    private List<T> dataList;




    public List<String> getColTitleList() {
        return colTitleList;
    }

    public void setColTitleList(List<String> colTitleList) {
        this.colTitleList = colTitleList;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }

    public List<ExcelCellFormat> getColFormatList() {
        return colFormatList;
    }

    public void setColFormatList(List<ExcelCellFormat> colFormatList) {
        this.colFormatList = colFormatList;
    }
}
