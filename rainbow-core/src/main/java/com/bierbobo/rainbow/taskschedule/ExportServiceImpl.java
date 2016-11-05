package com.bierbobo.rainbow.taskschedule;


import com.bierbobo.rainbow.domain.entity.Task;
import com.bierbobo.rainbow.framework.BaseTaskSchedule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 因为要继承BaseTaskSchedule，不能继承其他接口
 * @author yangguangxing
 *
 */
@Service(value="exportService")
public class ExportServiceImpl extends BaseTaskSchedule {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());


	/*@Resource
	private ExportAbstractService concreteService;*/
	
	/**
	 * 继承于BaseTaskSchedule，处理业务逻辑 - 根据task生成导出的excel文件
	 */

	public List<Task> excuteTask(List<Task> taskList) {

		return taskList;
	}
	

	/**
	 * 添加ExportData到数据库
	 */

	public <T> void registerExtraInfo(List<T> dataList) {
//		//判断参数类型是否正确
//		if(dataList == null || (dataList instanceof List) == false) {
//			return;
//		}
//		List<ExportData> list = (List<ExportData>)dataList;
//		
//		if(list.size() == 0) {
//			return;
//		}
//		
//		for(ExportData data : list) {
//			exportDao.insert(data);
//		}
	}

	
	
}
