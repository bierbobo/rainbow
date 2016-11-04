package com.bierbobo.rainbow.excel;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;


import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;


/**
 * Created by lifubo on 2016/10/9.
 */
public class ExcelCompare {


    public static void main(String[] args) {

        ExcelCompare excelReader = new ExcelCompare();
        excelReader.compareExcel("d:/excel/b.xls", "d:/excel/b1.xls");

//        System.out.println(Integer.MAX_VALUE);
    }

    /**
     * 读取Excel表格表头的内容
     * @param
     * @return String 表头内容的数组
     */
    public void compareExcel(String originFile,String destFile) {
        try {

            String[] s=new String[]{
                    "售价","前台售价","市场价","采购价","现货库存","现货库存成本","订单预定","已锁定","不可售","采购未到货","可用库存","可订购数量","内配入","内配在途","内配出","可售天数",
//                    "7天一级销量Band","7天三级销量Band",
                    "毛利","毛利率", "异常消息提醒",
                    "全国仓报价","仓报价"
            };

            List<String> list = Arrays.asList(s);


            Formatter formatter=new Formatter("d:/excel/out.txt");

            InputStream originIs=new FileInputStream(originFile);
            POIFSFileSystem originFs = new POIFSFileSystem(originIs);

            HSSFWorkbook originWb = new HSSFWorkbook(originFs);
            HSSFSheet originSheet = originWb.getSheetAt(0);
            HSSFRow originRow = originSheet.getRow(0);

               int originRowNum = originSheet.getLastRowNum();
            int originColNum = originRow.getPhysicalNumberOfCells();

            CellStyle style = originWb.createCellStyle();
            style.setFillForegroundColor(IndexedColors.AQUA.getIndex());
            style.setFillPattern(CellStyle.SOLID_FOREGROUND);


            InputStream destIs=new FileInputStream(destFile);
            POIFSFileSystem destFs = new POIFSFileSystem(destIs);
            HSSFWorkbook destWb = new HSSFWorkbook(destFs);
            HSSFSheet destSheet = destWb.getSheetAt(0);
            HSSFRow destRow = destSheet.getRow(0);
            int destRowNum = originSheet.getLastRowNum();
            int destColNum = originRow.getPhysicalNumberOfCells();

            Map<Integer,String> titleMap=new HashMap<>();
            HSSFRow title = originSheet.getRow(0);
            int j1 = 0;
            while (j1 < originColNum) {
                HSSFCell originCell = originRow.getCell((short) j1);
                String originCellValue = getCellValue(originCell);
                titleMap.put(j1,originCellValue);
                j1++;
            }

            for (int i = 0; i <= originRowNum; i++) {

                originRow = originSheet.getRow(i);
                destRow = destSheet.getRow(i);

                int j = 0;
                while (j < originColNum) {

                    HSSFCell originCell = originRow.getCell((short) j);
                    HSSFCell destCell = destRow.getCell((short) j);
                    if(originCell==null || destCell==null){
                        if(originCell!=null && destCell==null){
                            formatter.format("第%s行,第%s列,数据不一致,源数据不为空,目标数据为空\n", i, j);
                        }else if (originCell ==null && destCell!=null ){
                            formatter.format("第%s行,第%s列,数据不一致,源数据为空,目标数据不为空\n", i, j);
                        }
                        j++;
                        continue;
                    }


                    String originCellValue = getCellValue(originCell);
                    String destCellValue = getCellValue(destCell);

//                    String originCellValue = originCell.getStringCellValue();
//                    String destCellValue = destCell.getStringCellValue();
                    boolean contain=false;
                    for (String s1 : list) {
                        if(titleMap.get(j).contains(s1)){

                            contain=true;
                            break;
                        }
                    }

                    if(!contain){
                        if(!originCellValue.equals(destCellValue)){
                            System.out.printf("第%s行,第%s列, %s  |%s|  --- |%s| \n", i, j,titleMap.get(j), originCellValue, destCellValue);
                            formatter.format("第%s行,第%s列, %s  |%s|  --- |%s| \n", i+1, j+1,titleMap.get(j), originCellValue, destCellValue);

                        }
                    }
                    formatter.flush();
                    j++;
                }

            }

            formatter.close();
            originIs.close();
            destIs.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 获取单元格数据内容为字符串类型的数据
     *
     * @param cell Excel单元格
     * @return String 单元格数据内容
     */
    private String getCellValue(HSSFCell cell) {
        String strCell = "";
        switch (cell.getCellType()) {
            case HSSFCell.CELL_TYPE_STRING:
                strCell = cell.getStringCellValue();
                break;
            case HSSFCell.CELL_TYPE_NUMERIC:
                strCell = String.valueOf(cell.getNumericCellValue());
                break;
            case HSSFCell.CELL_TYPE_BOOLEAN:
                strCell = String.valueOf(cell.getBooleanCellValue());
                break;
            case HSSFCell.CELL_TYPE_BLANK:
                strCell = "";
                break;
            default:
                strCell = "";
                break;
        }

        return strCell;
    }


}
