package com.bierbobo.rainbow.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class FileUtils {
	
	static Logger logger = Logger.getLogger(FileUtils.class);

	
	public static String getFolderPath(String filePath,boolean keepLastSeparator){
		filePath = filePath.trim();
		if(StringUtils.isEmpty(filePath)) {
			throw new RuntimeException("不能获取到文件在本地的路径");
		}
		try {
			File path = new File(filePath);
			if(!path.exists()){
				logger.info("PATH(" + filePath + ")不存在，准备新建。");
				boolean flag = path.mkdirs();
				if(!flag){
					logger.error("商品不提醒 不能获取到文件在本地的路径");
					throw new RuntimeException("商品不提醒 不能获取到文件在的路径");
				}
			}
			filePath = path.getAbsolutePath();
			boolean hasSeparator = filePath.endsWith(File.separator);
			if(keepLastSeparator&&(!hasSeparator)){
					filePath +=File.separator;
			}else if((!keepLastSeparator)&&hasSeparator){
				filePath = filePath.substring(0,filePath.length()-1);
			}
			return filePath;
		} catch (Exception e) {
			logger.error("商品不提醒 不能获取到文件在本地的路径"+e);
			throw new RuntimeException("商品不提醒 不能获取到文件在的路径",e);
		}
	}
	
	/**
     * 删除文件
     * @param filePath
     * @return
     */
    public static boolean deleteLocalFile(String filePath) {
    	try {
        	File file = new File(filePath);
        	if(file.exists()) {
        		boolean flag = file.delete();
        		if(!flag){
        			logger.error("删除文件错误，文件路径：" + filePath);
        			return false;
				}
        	}
		} catch (Exception e) {
			logger.error("删除文件错误，文件路径：" + filePath + "，错误信息：" + e.getMessage());
			return false;
		}
    	return true;
    }
}
