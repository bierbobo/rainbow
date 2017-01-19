package com.bierbobo.rainbow.util;

import com.alibaba.fastjson.JSON;

public class JSONUtil {
	
	private static final int LENG = 1000;
	
	public static String cutParams(Object ob){
		try
		{
			String str = JSON.toJSONString(ob);
			if(str!=null&&str.length()>LENG){
				return str.substring(0, LENG);
			}
			return str;
		}
		catch(Exception ex) {
			return "";
		}
	}
	//调用方决定截取的字符串长度
	public static String cutParams(Object ob,int paramLength){
		try{
			String str = JSON.toJSONString(ob);
			if(str!=null&&str.length()>paramLength){
				return str.substring(0, paramLength);
			}
			return str;
		}catch(Exception ex) {
			return "";
		}
	}

}
