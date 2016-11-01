package com.bierbobo.rainbow.util;


import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Date;


/**
 * Created by lifubo on 2016/10/17.
 */
public class ExcelUtil {

    public static final String OFFICE_EXCEL_2003_POSTFIX = "xls";
    public static final String OFFICE_EXCEL_2010_POSTFIX = "xlsx";
    public static final String EMPTY = "";
    public static final String POINT = ".";
    public static final String NOT_EXCEL_FILE = " : Not the Excel file!";
    public static final String PROCESSING = "Processing...";


    public static void main(String[] args) {

        try {
            getExcelAsFile("d:/excel/a.xlsx");
//            CreateExcel();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static String getPostfix(String path) {
        if (path == null || EMPTY.equals(path.trim())) {
            return EMPTY;
        }
        if (path.contains(POINT)) {
            return path.substring(path.lastIndexOf(POINT) + 1, path.length());
        }
        return EMPTY;
    }

    /**
     * 得到Excel，并解析内容
     * @param file
     */
    public static void getExcelAsFile(String file) throws Exception{

        //1.得到Excel常用对象
//        POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(file));

        //2.得到Excel工作簿对象

//        Workbook wb = new SXSSFWorkbook(new XSSFWorkbook(new FileInputStream(file)));
//        Workbook wb = new XSSFWorkbook(new BufferedInputStream(new FileInputStream(file)));
        Date date2=new Date();
//        Workbook wb = new XSSFWorkbook(new BufferedInputStream(new FileInputStream(file)));
        Workbook wb = new SXSSFWorkbook(new XSSFWorkbook(new BufferedInputStream(new FileInputStream(file)))) ;

        Date date3=new Date();
        System.out.println((date3.getTime()-date2.getTime())/1000);


//        Workbook  wb = WorkbookFactory.create(fs);
        //3.得到Excel工作表对象
        Sheet sheet = wb.getSheetAt(0);
        //总行数
        int rowNum = sheet.getLastRowNum();


        for(int i=1;i<=rowNum;i++){
            XSSFRow row = (XSSFRow) sheet.getRow(i);

            int colNum = row.getPhysicalNumberOfCells();
//            row.getLastCellNum() ???
            for(int j=0;j<colNum;j++){

                Cell cell = row.getCell(j);

                /**
                 * 为了处理：Excel异常Cannot get a text value from a numeric cell
                 * 将所有列中的内容都设置成String类型格式
                 */
                if(cell!=null){
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                }

                //获得每一列中的值
                System.out.print(cell.getStringCellValue()+"\t\t\t");

                cell.setCellValue(i+":"+j);
            }


            System.out.println();
        }

        FileOutputStream out = new FileOutputStream(file);
        wb.write(out);
        out.close();



    }


    /**
     * 创建Excel，并写入内容
     */
    public static void CreateExcel(){

        //1.创建Excel工作薄对象
        HSSFWorkbook wb = new HSSFWorkbook();
        //2.创建Excel工作表对象
        HSSFSheet sheet = wb.createSheet("new Sheet");
        //3.创建Excel工作表的行
        HSSFRow row = sheet.createRow(6);





        //4.创建单元格样式
        CellStyle cellStyle =wb.createCellStyle();
        // 设置这些样式
//        cellStyle.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
//        cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);

        cellStyle.setFillForegroundColor(IndexedColors.AQUA.getIndex());
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);  // 创建一个居中格式



        //5.创建Excel工作表指定行的单元格
        row.createCell(0).setCellStyle(cellStyle);
        //6.设置Excel工作表的值
        row.createCell(0).setCellValue("sdfsdf中文测试_Chinese Words Test");

        row.createCell(1).setCellStyle(cellStyle);
        row.createCell(1).setCellValue("bbbb");


        //设置sheet名称和单元格内容
        wb.setSheetName(0,"第一张工作表");
        //设置单元格内容   cell.setCellValue("单元格内容");

        // 最后一步，将文件存到指定位置
        try
        {
            FileOutputStream fout = new FileOutputStream("d:/students.xls");
            wb.write(fout);
            fout.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 获取单元格数据内容为字符串类型的数据
     * @param cell Excel单元格
     * @return String 单元格数据内容
     */
    private static String getCellValue(Cell cell) {

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
