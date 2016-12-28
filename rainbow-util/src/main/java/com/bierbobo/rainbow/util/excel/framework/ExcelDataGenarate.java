package com.bierbobo.rainbow.util.excel.framework;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.util.List;

/**
 * Created by lifubo on 2016/12/28.
 */
public abstract class ExcelDataGenarate<Result> {



    private ExcelData<Result> excelData;
    List<ExcelCellFormat> colFormatList;// 标题信息以及列的格式信息
    private boolean pageEnable;// 是否分页读取数据
    private Integer pageSize = -1; // 每页记录数
    private Integer totalCount = -1; // 总记录数



    public boolean isPageEnable() {
        return pageEnable;
    }
    public Integer getPageSize() {
        return pageSize;
    }
    public Integer getTotalCount() {
        return totalCount;
    }
    public List<ExcelCellFormat> getColFormatList() {
        return colFormatList;
    }

    public void setPageEnable(boolean pageEnable) {
        this.pageEnable = pageEnable;
    }
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }
    public void setColFormatList(List<ExcelCellFormat> colFormatList) {
        this.colFormatList = colFormatList;
    }

    public  <Param> Param getParam() {
        return null;
    }

    public ExcelData<Result> getExcelData() {
        return this.generalExcelData();
    }

    public   ExcelData<Result> getExcelData(int pageNo) {
        return this.generalExcelData(pageNo);
    }


    /**
     * 生成文件路径
     * @return
     */
    public abstract  String getFilePath() ;

    /**
     * 生成文件数据
     * @return
     */
    public abstract ExcelData<Result> generalExcelData();

    /**
     * 生成文件数据
     * @return
     */
    public  ExcelData<Result> generalExcelData(int pageNo){
        return null;
    }

    /**
     * 将数据填充到excel中
     * @param rowIndex
     * @param columnIndex
     * @param cell
     * @param excelCellFormat
     * @param bean
     */
    public abstract void setCellValue(int rowIndex,int columnIndex ,Cell cell,ExcelCellFormat excelCellFormat,Result bean );

/*

    protected   void generalExcelData1(Param param) {
*/
/*
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
		*//*


//        ExcelData excelData=new ExcelData();
//        return excelData;
    }


    protected   ExcelData<Result> generalExcelData1(Param param,int pageNo) {

        // 根据分页信息、查询条件  来客隆一个新的查询条件
       // QueryVO clonePara = ObjectCloneUtils.serializableClone(para);
*/
/*
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
		*//*


        ExcelData excelData=new ExcelData();
        return excelData;
    }
*/

    /**
     *
     * @Title: getCellStyle
     * @Description: TODO（设置表头样式）
     * @param wb
     * @return
     */
    public static CellStyle getCellStyle(Workbook wb) {
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 12);// 设置字体大小
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);// 加粗
//        style.setFillForegroundColor(Color.LIME.getBlue());// 设置背景色
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setAlignment(CellStyle.SOLID_FOREGROUND);// 让单元格居中
        // style.setWrapText(true);//设置自动换行
        style.setFont(font);
        return style;
    }


    protected void setCellValue1(int rowIndex,int columnIndex ,Cell cell,ExcelCellFormat excelCellFormat,Result bean ){

        String propertyName = excelCellFormat.getPropertyName();
        //根据bean和propertyName 获得列值
        String cellValue=null;

        cell.setCellType(excelCellFormat.getCellType());
        cell.setCellStyle(excelCellFormat.getCellStyle());
        cell.setCellValue(cellValue);


    }

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



}
