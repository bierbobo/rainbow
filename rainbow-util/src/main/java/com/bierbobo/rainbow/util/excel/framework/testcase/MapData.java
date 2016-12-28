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
public class MapData extends ExcelDataGenarate<HashMap> {

    private String filePath;
    private QueryVo param;

    @Override
    public String getFilePath() {
        return filePath;
    }
    @Override
    public   QueryVo getParam() {
        return param;
    }


    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    public void setParam(QueryVo param) {
        this.param = param;
    }



    @Override
    public ExcelData<HashMap> generalExcelData() {

//        QueryVo queryVo = this.getParam();

        ExcelData<HashMap> excelData=new ExcelData<HashMap>();
        List<ExcelCellFormat> colFormatList= new ArrayList<>();

        List<HashMap> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {

            ExcelCellFormat format=new ExcelCellFormat();
            HashMap hashMap=new HashMap();
            for (int j = 0; j < 10; j++) {
                hashMap.put(j,i+":"+j);
            }
            colFormatList.add(format);
            list.add(hashMap);
        }

        excelData.setDataList(list);
        excelData.setColFormatList(colFormatList);

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


}
