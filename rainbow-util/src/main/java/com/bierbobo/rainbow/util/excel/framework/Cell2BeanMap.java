package com.bierbobo.rainbow.util.excel.framework;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;

import java.io.File;

/**
 * Created by lifubo on 2016/12/27.
 */
public  abstract class Cell2BeanMap<T>  {


    public abstract void setBean(int rowIndex,int colIndex, Cell cell, T t);
    public abstract String getFilePath();

    protected String generalFilePath1() {

        String path =null;

        //生成文件路径
        String fileName=null;
        String fileDir = null;
        File file = new File(fileDir+fileName);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        path=file.getPath();
        return path;
    }



    /**
     * 获取单元格数据内容
     * @param cell Excel单元格
     * @return String 单元格数据内容
     */
    protected Object getCellValue(Cell cell) {

        Object cellValue = "";
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                cellValue=String.valueOf(cell.getStringCellValue());
                break;
            case Cell.CELL_TYPE_NUMERIC:

                if(DateUtil.isCellDateFormatted(cell)){
                    cellValue= cell.getDateCellValue();
                }else{
                    cellValue=cell.getNumericCellValue();
                }
                break;

            case Cell.CELL_TYPE_BOOLEAN:
                cellValue=String.valueOf(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_BLANK:
                cellValue="";
                break;
            default:
                cellValue="";
                break;
        }
        return cellValue;

    }


}
