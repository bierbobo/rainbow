package com.bierbobo.rainbow.excel;


import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


//构造格式行  num_format  bigdecimal_format ‘’
//构造标题行
//构造数据行


public abstract class POIAbstractExport {
	Logger logger = Logger.getLogger(this.getClass());
	
	private final static int MAX_ROW = 100;
	
	/**
	 * 生成数据的抽象方法，由子类实现
	 * @return
	 * @throws Exception 
	 */
	abstract List<ArrayList<String>> generateData(QueryResult queryResult) throws Exception;
	
	/**
	 * 主方法，对外的方法
	 * @param filePath
	 */
	public void process(QueryResult queryResult, String filePath) {
		try {
//			List<ArrayList<String>> data = this.generateData(wareTitle, disTitle, disList, wareList, queryFlag);
//			this.generateExcel(data, filePath);
//			this.generateCsv(data, filePath);
		} 
		catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
	}
	/**
	 * 主方法，对外的方法
	 */
	public List<ArrayList<String>> process(QueryResult queryResult) {
		List<ArrayList<String>> data = null;
		try {
//			data = this.generateData(wareTitle, disTitle, disList, wareList, queryFlag);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return data;
	}
	
	/**
	 * 处理配送中心维度的列，根据对象和字符串，反射获得结果
	 * 
	 * @param key
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	protected Object fetchFieldValue(String key, Object obj) {		
		try {
			//处理infoModel对象下的属性，客户端传过来的应该是这样："info.commonts"
			if(key.contains(".")) {
				String[] fieldArr = key.split("\\.");
				//找到infoModel的get方法，并获取值 - 即infoModel对象
				Method method = obj.getClass().getMethod(convertToGetMethod("infoModel"));
				Object infoModelObj = method.invoke(obj);
				
				//通过infoModel的属性获取对应的值		
				return fetchFieldValue(fieldArr[1], infoModelObj);
			}
			else {			

				Method method = obj.getClass().getMethod(convertToGetMethod(key));
				return method.invoke(obj);
			}
		} catch (Exception e) {
			logger.error("POIAbstractExport类 的fetchFieldValue方法反射获取值出现异常！", e);
			return "";
			
		}
	}

	/**
	 * 根据组织好的数据，生成excel文件
	 * 数据格式为：List<List<String>>，标题行放在第一的位置就行
	 * @param dataList
	 */
	protected void generateExcel(List<ArrayList<String>> dataList, String filePath) {
		SXSSFWorkbook workbook = null;
		FileOutputStream fileStream = null;
		try {
			workbook = new SXSSFWorkbook(MAX_ROW);
			SXSSFSheet sheet = (SXSSFSheet) workbook.createSheet("sheet1");
			
			List<String> colFormatInfo = dataList.get(0);//指定的是否需要设置单元格
			//遍历，每次遍历创建一行【从第二行开始是表头和数据,第一行为指定的是否需要设置单元格】
			for(Integer i = 1; i < dataList.size(); i++) {
				//创建POI的行对象SXSSFRow，将本行的数据存在临时变量tempList中
				SXSSFRow row = (SXSSFRow) sheet.createRow(i-1);
				List<String> tempList = dataList.get(i);
				
				//变成生成一行的每列
				for(Integer columnIndex = 0; columnIndex < tempList.size(); columnIndex++) {
					Cell cell = row.createCell(columnIndex);
					if(i>1){
						//设置单元格格式【数字型、是否保留小数】
						if(Constants.EXPORT_NUM_FORMAT_ENUM.equalsIgnoreCase(colFormatInfo.get(columnIndex))){
				            cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
				            String tempValue = tempList.get(columnIndex);
				            if(StringUtils.isBlank(tempValue)){
				            	tempValue = "0";
				            }
				            tempValue = tempValue.trim();
				            cell.setCellValue(Double.valueOf(tempValue));//数据加“0”是防止内容为空
						}else if(Constants.EXPORT_BIGDECIMAL_FORMAT_ENUM.equalsIgnoreCase(colFormatInfo.get(columnIndex))){
							cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
							String tempValue = tempList.get(columnIndex);
				            if(StringUtils.isBlank(tempValue)){
				            	tempValue = "0.0000";
				            }
				            tempValue = tempValue.trim();
							cell.setCellValue(Double.valueOf(tempValue));
						}else{
							cell.setCellValue(tempList.get(columnIndex));
						}
					}else{
						cell.setCellValue(tempList.get(columnIndex));//对于sheet标头，不进行数据类型转化
					}
				}
			}
			fileStream = new FileOutputStream(filePath);
			workbook.write(fileStream);

		}
		catch(Exception ex) {
			logger.error(ex.getMessage());
		}finally {
			if(workbook != null) {
				workbook.dispose();
			}
			if(fileStream != null){
		        try {
					fileStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	

	/**
	 * 根据属性名，获取相应的get方法的名字
	 * @param propertyName
	 * @return
	 */
	protected String convertToGetMethod(String propertyName) {
		return "get" + StringUtils.capitalize(propertyName);
	}
	
	protected boolean deleteRepeatCols(List<String> wareKeyList,List<String> wareValueList,List<String> targetCols){
		if(null!=targetCols&&targetCols.size()>0){
			for(String targetCol :targetCols){
				if(wareKeyList.contains(targetCol)){
					int index = wareKeyList.indexOf(targetCol);
					wareValueList.remove(index);
					wareKeyList.remove(index);
				}
			}
		}
		return true;
	}
	//指定需要把单元格设置成数字型的字段
	protected List<String> operatorColFormatInfo(List<String> colFormatInfo){
		List<String> resultList = new ArrayList<String>();
		if(null!=colFormatInfo&&colFormatInfo.size()>0){
			List<String> allNUMFormatCol = Arrays.asList(Constants.EXPORT_NUM_FORMAT_COL.split(","));
			List<String> bigdecimalNUMFormatCol = Arrays.asList(Constants.EXPORT_BIGDECIMAL_FORMAT_COL.split(","));
			for(String col : colFormatInfo){
				if(allNUMFormatCol.contains(col)){
					resultList.add(Constants.EXPORT_NUM_FORMAT_ENUM);
				}else if(bigdecimalNUMFormatCol.contains(col)){
					resultList.add(Constants.EXPORT_BIGDECIMAL_FORMAT_ENUM);
				}else{
					resultList.add("");
				}
			}
		}
		return resultList;
	}
}

