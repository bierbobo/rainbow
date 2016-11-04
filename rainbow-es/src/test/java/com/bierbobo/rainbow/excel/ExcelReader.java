package com.bierbobo.rainbow.excel;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Created by lifubo on 2016/10/9.
 */
public class ExcelReader {


    private POIFSFileSystem fs;
    private HSSFWorkbook wb;
    private HSSFSheet sheet;
    private HSSFRow row;

    /**
     * 读取Excel表格表头的内容
     * @param
     * @return String 表头内容的数组
     */
    public String[] readExcelTitle(InputStream is) {
        try {
            fs = new POIFSFileSystem(is);
            wb = new HSSFWorkbook(fs);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sheet = wb.getSheetAt(0);
        row = sheet.getRow(0);
        // 标题总列数
        int colNum = row.getPhysicalNumberOfCells();
        System.out.println("colNum:" + colNum);
        String[] title = new String[colNum];
        for (int i = 0; i < colNum; i++) {
            //title[i] = getStringCellValue(row.getCell((short) i));
            title[i] = getCellFormatValue(row.getCell((short) i));
        }
        return title;
    }

    /**
     * 根据HSSFCell类型设置数据
     * @param cell
     * @return
     */
    private String getCellFormatValue(HSSFCell cell) {
        String cellvalue = "";
        if (cell != null) {
            // 判断当前Cell的Type
            switch (cell.getCellType()) {
                // 如果当前Cell的Type为NUMERIC
                case HSSFCell.CELL_TYPE_NUMERIC:
                case HSSFCell.CELL_TYPE_FORMULA: {
                    // 判断当前的cell是否为Date
                    if (HSSFDateUtil.isCellDateFormatted(cell)) {
                        // 如果是Date类型则，转化为Data格式

                        //方法1：这样子的data格式是带时分秒的：2011-10-12 0:00:00
                        //cellvalue = cell.getDateCellValue().toLocaleString();

                        //方法2：这样子的data格式是不带带时分秒的：2011-10-12
                        Date date = cell.getDateCellValue();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        cellvalue = sdf.format(date);

                    }
                    // 如果是纯数字
                    else {
                        // 取得当前Cell的数值
                        cellvalue = String.valueOf(cell.getNumericCellValue());
                    }
                    break;
                }
                // 如果当前Cell的Type为STRIN
                case HSSFCell.CELL_TYPE_STRING:
                    // 取得当前的Cell字符串
                    cellvalue = cell.getRichStringCellValue().getString();
                    break;
                // 默认的Cell值
                default:
                    cellvalue = " ";
            }
        } else {
            cellvalue = "";
        }
        return cellvalue;

    }

    /**
     * 读取Excel数据内容
     * @param
     * @return Map 包含单元格数据内容的Map对象
     */
    public Map<Integer, String> readExcelContent(InputStream is) {
        Map<Integer, String> content = new HashMap<Integer, String>();
        String str = "";
        try {
            fs = new POIFSFileSystem(is);
            wb = new HSSFWorkbook(fs);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sheet = wb.getSheetAt(0);
        // 得到总行数
        int rowNum = sheet.getLastRowNum();
        row = sheet.getRow(0);
        int colNum = row.getPhysicalNumberOfCells();
        // 正文内容应该从第二行开始,第一行为表头的标题
        for (int i = 1; i <= rowNum; i++) {
            row = sheet.getRow(i);
            int j = 0;
            while (j < colNum) {
                // 每个单元格的数据内容用"-"分割开，以后需要时用String类的replace()方法还原数据
                // 也可以将每个单元格的数据设置到一个javabean的属性中，此时需要新建一个javabean
                // str += getStringCellValue(row.getCell((short) j)).trim() +
                // "-";
                str += getCellFormatValue(row.getCell((short) j)).trim() + "    ";
                j++;
            }
            content.put(i, str);
            str = "";
        }
        return content;
    }

    /**
     * 获取单元格数据内容为字符串类型的数据
     *
     * @param cell Excel单元格
     * @return String 单元格数据内容
     */
    private String getStringCellValue(HSSFCell cell) {
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
        if (strCell.equals("") || strCell == null) {
            return "";
        }
        if (cell == null) {
            return "";
        }
        return strCell;
    }


    public static void main(String[] args) {
/*

        try {
            // 对读取Excel表格标题测试
            InputStream is = new FileInputStream("d:\\test2.xls");
            ExcelReader excelReader = new ExcelReader();
            String[] title = excelReader.readExcelTitle(is);
            System.out.println("获得Excel表格的标题:");
            for (String s : title) {
                System.out.print(s + " ");
            }

            // 对读取Excel表格内容测试
            InputStream is2 = new FileInputStream("d:\\test2.xls");
            Map<Integer, String> map = excelReader.readExcelContent(is2);
            System.out.println("获得Excel表格的内容:");
            for (int i = 1; i <= map.size(); i++) {
                System.out.println(map.get(i));
            }

        } catch (FileNotFoundException e) {
            System.out.println("未找到指定路径的文件!");
            e.printStackTrace();
        }
*/

//        StringBuilder sb = new StringBuilder();
//        Formatter formatter = new Formatter(sb);
//        formatter.format("%1s %1s %3$2s %1$2s", "a", "b", "c", "d");
//        System.out.println(sb);

//        try {
//            String dataPath="d:/excel1/c.xlsx";
//
//            InputStream is = new FileInputStream(dataPath);
//            Workbook originWb= new XSSFWorkbook(is);
//             Sheet originSheet =originWb.getSheetAt(0);
//            Row  originRow = originSheet.getRow(0);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        ExcelReader excelReader = new ExcelReader();
        excelReader.compareExcel1("d:/excel1/c.xlsx", "d:/excel1/d.xlsx");




    }
    /**
     * 读取Excel表格表头的内容
     * @param
     * @return String 表头内容的数组
     */
    public void compareExcel1(String originFile,String destFile) {
        try {

//            String[] s=new String[]{
//
//                    "售价","前台售价","市场价","采购价","现货库存","现货库存成本","订单预定","已锁定","不可售","采购未到货","可用库存","可订购数量","内配入","内配在途","内配出","可售天数"
////                   ,"毛利","毛利率",
////                     "异常消息提醒",
////                    "7天三级销量Band","退供应商在途","月至
// 今累计仓报价成本","月至今累计退货仓报价成本",
////                    "上柜时间","全国仓报价","仓报价","供应商名称","保质期"
//            };

            String[] s=new String[]{

                    "售价","前台售价","市场价","采购价","现货库存","现货库存成本","订单预定","已锁定","不可售","采购未到货","可用库存","可订购数量","内配入","内配在途","内配出","可售天数",
                    "毛利","毛利率", "异常消息提醒",
                    "全国仓报价","仓报价"
//                    "月至今累计仓报价成本","月至今累计退货仓报价成本","供应商名称","月至今日均库存金额","财务存货周转"
//
//                    "7天三级销量Band","退供应商在途","月至今累计仓报价成本","月至今累计退货仓报价成本",
//                    "上柜时间","仓报价","供应商名称","保质期"
            };
            List<String> list = Arrays.asList(s);


            Formatter formatter=new Formatter("d:/excel1/out.txt");

            InputStream originIs=new FileInputStream(originFile);
            Workbook originWb =new XSSFWorkbook(originIs);
            Sheet originSheet =originWb.getSheetAt(0);
            Row  originRow = originSheet.getRow(0);

            System.out.println("==============");

            int originRowNum = originSheet.getLastRowNum();
            int originColNum = originRow.getPhysicalNumberOfCells();

            CellStyle style = originWb.createCellStyle();
            style.setFillForegroundColor(IndexedColors.AQUA.getIndex());
            style.setFillPattern(CellStyle.SOLID_FOREGROUND);


            InputStream destIs=new FileInputStream(destFile);
            Workbook destWb = new XSSFWorkbook(destIs);
            Sheet destSheet = destWb.getSheetAt(0);
            Row destRow = destSheet.getRow(0);
            int destRowNum = originSheet.getLastRowNum();
            int destColNum = originRow.getPhysicalNumberOfCells();

            System.out.println("==============");

            Map<Integer,String> titleMap=new HashMap<>();
            Row title = originSheet.getRow(0);
            int j1 = 0;
            while (j1 < originColNum) {
                Cell  originCell = originRow.getCell((short) j1);
                String originCellValue = getCellValue(originCell);
                titleMap.put(j1,originCellValue);
                j1++;
            }

            for (int i = 0; i <= originRowNum; i++) {

                originRow = originSheet.getRow(i);
                destRow = destSheet.getRow(i);

                int j = 0;
                while (j < originColNum) {

                    Cell originCell = originRow.getCell((short) j);
                    Cell destCell = destRow.getCell((short) j);
                    if(originCell==null || destCell==null){
                        System.out.println("===");
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
    private String getCellValue(Cell cell) {
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
//        if (strCell.equals("") || strCell == null) {
//            return "";
//        }
//        if (cell == null) {
//            return "";
//        }
        return strCell;
    }

    /**
     * 读取Excel表格表头的内容
     * @param
     * @return String 表头内容的数组
     */
    public void compareExcel(String originFile,String destFile) {
        try {

//            String[] s=new String[]{
//
//                    "售价","前台售价","市场价","采购价","现货库存","现货库存成本","订单预定","已锁定","不可售","采购未到货","可用库存","可订购数量","内配入","内配在途","内配出","可售天数"
////                   ,"毛利","毛利率",
////                     "异常消息提醒",
////                    "7天三级销量Band","退供应商在途","月至
// 今累计仓报价成本","月至今累计退货仓报价成本",
////                    "上柜时间","全国仓报价","仓报价","供应商名称","保质期"
//            };

            String[] s=new String[]{

                    "售价","前台售价","市场价","采购价","现货库存","现货库存成本","订单预定","已锁定","不可售","采购未到货","可用库存","可订购数量","内配入","内配在途","内配出","可售天数",
                    "毛利","毛利率", "异常消息提醒",
                    "全国仓报价","仓报价"
//                    "月至今累计仓报价成本","月至今累计退货仓报价成本","供应商名称","月至今日均库存金额","财务存货周转"
//
//                    "7天三级销量Band","退供应商在途","月至今累计仓报价成本","月至今累计退货仓报价成本",
//                    "上柜时间","仓报价","供应商名称","保质期"
            };
            List<String> list = Arrays.asList(s);


            Formatter formatter=new Formatter("d:/excel1/out.txt");

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
                        System.out.println("===");
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
//                            System.out.printf("第%s行,第%s列, %s  |%s|  --- |%s| \n", i, j,titleMap.get(j), originCellValue, destCellValue);
                            formatter.format("第%s行,第%s列, %s  |%s|  --- |%s| \n", i+1, j+1,titleMap.get(j), originCellValue, destCellValue);
                        }
                    }
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
//        if (strCell.equals("") || strCell == null) {
//            return "";
//        }
//        if (cell == null) {
//            return "";
//        }
        return strCell;
    }


}
