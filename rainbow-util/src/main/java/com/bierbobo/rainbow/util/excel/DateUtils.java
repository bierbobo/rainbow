package com.bierbobo.rainbow.util.excel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {


	
	public static String currentDate2Str(){
		
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyyMMdd");
		String dateStr = dateFormat.format(new Date());
		return dateStr;
		
	}
	
	public static Date getCurrentDate(){
		
		return new Date();
		
	}
	
	public static String currentDate2Str(String format){
		
		SimpleDateFormat dateFormat=new SimpleDateFormat(format);
		String dateStr = dateFormat.format(new Date());
		return dateStr;
		
	}
	
	public static String date2Str(Date date,String format){
		
		SimpleDateFormat dateFormat=new SimpleDateFormat(format);
		String dateStr = dateFormat.format(date);
		return dateStr;
		
	}

	public static Date str2Date(String str) {
		
		
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyyMMdd");
		
		Date date=null;
		try {
			date = dateFormat.parse(str);
		} catch (ParseException e) {
			
		}
		
		return date;		
	}
		
	public static Date str2Date(String str,String format) {
		
		
		SimpleDateFormat dateFormat=new SimpleDateFormat(format);
		
		Date date=null;
		try {
			date = dateFormat.parse(str);
		} catch (ParseException e) {
			
		}
		
		return date;		
	}
			
}
