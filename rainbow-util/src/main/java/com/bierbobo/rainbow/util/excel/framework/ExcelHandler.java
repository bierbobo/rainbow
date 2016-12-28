package com.bierbobo.rainbow.util.excel.framework;

import com.bierbobo.rainbow.domain.common.Page;
import com.bierbobo.rainbow.util.excel.Constants;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.*;


/**
 *
 * 读取仍然是“XSSFWorkbook”，写入则为“SXSSFWorkbook ”。
 *
 *
 *
 *
 * 1.读取excel生成的实体
 * 2.写入到excel中的实体
 * 3.读取模板后生成excel
 * 4.修改excel
 *
 */
public class ExcelHandler {

	public static final String OFFICE_EXCEL_2003_POSTFIX = "xls";
	public static final String OFFICE_EXCEL_2010_POSTFIX = "xlsx";
	public static final String NOT_EXCEL_FILE = " : Not the Excel file!";
	public static final String PROCESSING = "Processing...";

	private ExcelDataGenarate excelDataGenarate;

	public ExcelDataGenarate getExcelDataGenarate() {
		return excelDataGenarate;
	}
	public void setExcelDataGenarate(ExcelDataGenarate excelDataGenarate) {
		this.excelDataGenarate = excelDataGenarate;
	}


	/**
	 * 1. 读取到map中
	 * 2. 读取到bean中 ，bean没有属性的不映射
	 *
	 * @param clazz
	 * @param cell2BeanMap
	 * @param <T>
	 * @return
	 */
	public <T> List<T> readExcel(Class<T> clazz,Cell2BeanMap<T> cell2BeanMap){

		Workbook workbook =null;
		InputStream is = null;
		List<T> dataList=new ArrayList<>();

		try {
			is=new FileInputStream(cell2BeanMap.getFilePath());
			if(!is.markSupported()) {
				is = new PushbackInputStream(is, 8);
			}

			if(POIFSFileSystem.hasPOIFSHeader(is)) {
                System.out.println("2003及以下");
				workbook = new HSSFWorkbook(is);

            }else if(POIXMLDocument.hasOOXMLHeader(is)) {
				System.out.println("2007及以上");
				workbook = new XSSFWorkbook(is);
			}

			Sheet sheet = workbook.getSheetAt(0);

			// getLastRowNum  有数据的最后的一行，从0开始，包含最后一行，中间会有空行
			// getPhysicalNumberOfRows  有数据的行数
			int rowNum = sheet.getLastRowNum();
			for(int rowIndex=0;rowIndex<=rowNum;rowIndex++){

				Row row = sheet.getRow(rowIndex);
				if (row!=null) {
					//getLastCellNum() 	有数据的最后的一行，从0开始，包含最后一行，中间会有空行
					//getPhysicalNumberOfCells 有数据的行数
					int colNum = row.getLastCellNum();
					T bean = clazz.newInstance();
					for(int colIndex=0;colIndex<colNum;colIndex++){

						Cell cell = row.getCell(colIndex);
						if (cell!=null) {
							cell2BeanMap.setBean(rowIndex,colIndex, cell, bean);
						}
					}
					dataList.add(bean);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			if(workbook != null) {
				try {
					workbook.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(is != null){
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
		return dataList;
	}



	public   void generateExcel(){

		Workbook workbook =null;
		try {

			//根据查询条件判读是否需要进行 数据写入

			//生成excel内容
			if (this.excelDataGenarate.getExcelType().equalsIgnoreCase("2007")) {
				workbook=new SXSSFWorkbook();
			}else{
				workbook=new HSSFWorkbook();
			}

			Sheet sheet = workbook.createSheet();
			generateSheet(sheet);

			//输出到本地文件中
			this.writeFile(workbook);

			//上传到远程,生产下载路径给调用方

			//删除本地文件


		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			if(workbook != null) {

				try {
					if (workbook instanceof SXSSFWorkbook) {
						((SXSSFWorkbook)workbook).dispose();
					}else{
						workbook.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void generateSheet(Sheet sheet) throws IOException {

		int rowIndex=0;
		if (this.excelDataGenarate.isPageEnable()) {

            int totalCount =0;
            Page<Long> page = new Page<Long>();
            page.setPageSize(this.excelDataGenarate.getPageSize()).setTotalCount(this.excelDataGenarate.getTotalCount());
            for(int pageNo =1;pageNo<=page.getTotalPage();pageNo++){
                rowIndex = generateCell(sheet, rowIndex,pageNo);
            }

        }else {
            generateCell(sheet, rowIndex);

        }
	}


	/**
	 *生成excel信息
	 * @param sheet
	 * @param rowIndex
	 * @param <Result>
	 * @return
	 * @throws IOException
	 */
	private <Result> int generateCell(Sheet sheet, int rowIndex,int pageNo) throws IOException {

		// 根据查询条件 拼装出数据和显示列 QueryVO
		// 数据量需要多次生成

		ExcelData<Result> excelData=this.excelDataGenarate.getExcelData(pageNo);
		List<Result> dataList = excelData.getDataList();
		List<ExcelCellFormat> colList = excelData.getColFormatList();

		for(int dataIndex=0,rowLen=dataList.size();dataIndex<rowLen;dataIndex++ ){

			Result bean = dataList.get(dataIndex);
			//去数据的值， 标题列  列类型
			Row row = sheet.createRow(rowIndex);

			//1.对应的值，将值写入到excel中。
			//2.对应的key,根据key取值来放入到excel中
			for(int columnIndex = 0, columnLen=colList.size(); columnIndex < columnLen; columnIndex++) {//遍历一行
				ExcelCellFormat excelCellFormat = colList.get(columnIndex);
				Cell cell = row.createCell(columnIndex);
				this.excelDataGenarate.setCellValue(rowIndex,columnIndex, cell, excelCellFormat, bean);
			}

			//每当行数达到设置的值就刷新数据到硬盘,以清理内存
			if (sheet instanceof SXSSFSheet) {
				if(rowIndex% Constants.FLUSH_ROWS==0){
					((SXSSFSheet)sheet).flushRows();
				}

			}

			rowIndex++;
		}
		return rowIndex;
	}


	/**
	 *生成excel信息
	 * @param sheet
	 * @param rowIndex
	 * @param <Result>
	 * @return
	 * @throws IOException
	 */
	private <Result> int generateCell(Sheet sheet, int rowIndex) throws IOException {

		// 根据查询条件 拼装出数据和显示列 QueryVO
		// 数据量需要多次生成

		ExcelData<Result> excelData=this.excelDataGenarate.getExcelData();
		List<Result> dataList = excelData.getDataList();
		List<ExcelCellFormat> colList = excelData.getColFormatList();

//		colList=dataList.get(0);


		for(int dataIndex=0,rowLen=dataList.size();dataIndex<rowLen;dataIndex++ ){

			Result bean = dataList.get(dataIndex);
			//去数据的值， 标题列  列类型
			Row row = sheet.createRow(rowIndex);

			//1.对应的值，将值写入到excel中。
			//2.对应的key,根据key取值来放入到excel中
			for(int columnIndex = 0, columnLen=colList.size(); columnIndex < columnLen; columnIndex++) {//遍历一行
				ExcelCellFormat excelCellFormat = colList.get(columnIndex);
				Cell cell = row.createCell(columnIndex);
				this.excelDataGenarate.setCellValue(rowIndex,columnIndex, cell, excelCellFormat, bean);
			}

			//每当行数达到设置的值就刷新数据到硬盘,以清理内存
			if (sheet instanceof SXSSFSheet) {
				if(rowIndex% Constants.FLUSH_ROWS==0){
					((SXSSFSheet)sheet).flushRows();
				}

			}

			rowIndex++;
		}
		return rowIndex;
	}



	private void writeFile(Workbook workbook) throws IOException {

		FileOutputStream fileOutputStream = null;
		try {
			String path = this.excelDataGenarate.generalFilePath();
			fileOutputStream = new FileOutputStream(path);
			BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
			workbook.write(bufferedOutputStream);
			bufferedOutputStream.flush();

		} catch (IOException e) {
			e.printStackTrace();

		} finally {
			if(fileOutputStream != null){
				try {
					fileOutputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}


}
