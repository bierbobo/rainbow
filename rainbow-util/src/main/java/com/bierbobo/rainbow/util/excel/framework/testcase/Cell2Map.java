package com.bierbobo.rainbow.util.excel.framework.testcase;

import com.bierbobo.rainbow.util.excel.framework.Cell2BeanMap;
import org.apache.poi.ss.usermodel.Cell;

import java.util.HashMap;

/**
 * Created by lifubo on 2016/12/27.
 */
public class Cell2Map  extends Cell2BeanMap<HashMap> {


    private String filePath;

    @Override
    public void setBean(int rowIndex,int colIndex,Cell cell , HashMap map){

        Object cellValue = this.getCellValue(cell);
        map.put(rowIndex+":"+colIndex,cellValue.toString());

    }

    @Override
    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

 /*
    @Override
    public <DataBean> List<DataBean> readExcel(Class<DataBean> clazz) {
        return super.readExcel(clazz);
    }

    public <DataBean> void setBean(int colIndex,Cell cell , DataBean t) {
        // 根据列号， 单元格值， 填充到t对象中
        Object cellValue = getCellValue(cell);
    }

*/


}
