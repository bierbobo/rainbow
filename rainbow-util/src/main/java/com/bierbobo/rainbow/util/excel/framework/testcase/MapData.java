package com.bierbobo.rainbow.util.excel.framework.testcase;

import com.bierbobo.rainbow.util.excel.framework.ExcelCellFormat;
import com.bierbobo.rainbow.util.excel.framework.ExcelData;
import com.bierbobo.rainbow.util.excel.framework.ExcelDataGenarate;
import org.apache.poi.ss.usermodel.Cell;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by lifubo on 2016/12/28.
 */
public class MapData extends ExcelDataGenarate<Object,HashMap> {

    private String excelType;//导出excel类型 03 07
    public String getExcelType() {
        return excelType;
    }

    public void setExcelType(String excelType) {
        this.excelType = excelType;
    }


    @Override
    public ExcelData<HashMap> generalExcelData(Object o) {

        ExcelData<HashMap> excelData=new ExcelData<HashMap>();
        List<ExcelCellFormat> colFormatList= new ArrayList<>();

        List<HashMap> list = new ArrayList<>();
        for (int j = 0; j < 10; j++) {

            ExcelCellFormat format=new ExcelCellFormat();
            HashMap hashMap=new HashMap();
            for (int i = 0; i < 10; i++) {
                hashMap.put(i,j+":"+i);
            }
            colFormatList.add(format);
            list.add(hashMap);
        }
        excelData.setDataList(list);
        excelData.setColFormatList(colFormatList);

        this.setExcelData(excelData);
        return excelData;
    }

    @Override
    public void setCellValue(int rowIndex, int columnIndex, Cell cell, ExcelCellFormat excelCellFormat, HashMap map) {

//        String propertyName = excelCellFormat.getPropertyName();
//        //根据bean和propertyName 获得列值
//        String cellValue=null;
//        cell.setCellType(excelCellFormat.getCellType());
//        cell.setCellStyle(excelCellFormat.getCellStyle());
        cell.setCellValue(map.get(columnIndex).toString());

    }

    @Override
    public String generalFilePath() {
        return "d:/123.xlsx";
    }
}
