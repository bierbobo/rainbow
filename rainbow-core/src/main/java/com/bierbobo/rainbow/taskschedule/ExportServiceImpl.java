package com.bierbobo.rainbow.taskschedule;


import com.bierbobo.rainbow.domain.entity.Task;
import com.bierbobo.rainbow.domain.task.QueryTaskParam;
import com.bierbobo.rainbow.framework.BaseTaskSchedule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 因为要继承BaseTaskSchedule，不能继承其他接口
 * @author yangguangxing
 *
 */
@Service(value="exportService")
public class ExportServiceImpl extends BaseTaskSchedule {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public List<Task> excuteTask(List<Task> taskList) {
		return null;
	}


	public QueryTaskParam generateQueryTaskParam(){
		return null;
	}

	public List<Task> getTask(QueryTaskParam queryTaskParam ){
		return null;
	}


}
