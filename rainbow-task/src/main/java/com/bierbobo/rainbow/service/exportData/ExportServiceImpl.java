package com.bierbobo.rainbow.service.exportData;


import com.bierbobo.rainbow.domain.common.CommonResult;
import com.bierbobo.rainbow.domain.entity.ExportData;
import com.bierbobo.rainbow.domain.entity.Task;
import com.bierbobo.rainbow.domain.task.TaskStateEnum;
import com.bierbobo.rainbow.taskschedule.BaseTaskSchedule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 因为要继承BaseTaskSchedule，不能继承其他接口
 * @author yangguangxing
 *
 */
@Service(value="exportService")
public class ExportServiceImpl extends BaseTaskSchedule {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());



//	@Override
	public List<Task> excuteTask(List<Task> taskList) {

		for(Task task : taskList) {

			ExportData exportData =null;

			//根据ExportData的类型，得到相应的处理类【大表横向、纵向导出；断货原因；统计导出等等】
			ExportAbstractService<?> concreteService = getConcreteService(exportData.getType());

			//调用process方法，所有导出相关的逻辑都封装在process里面【数据查询、生成excel文件、excel文件上传JSS等】
			CommonResult<Boolean> result = concreteService.process(task, exportData);

			//根据处理结果，设置task的state，之后要返回，用于更新task的状态。
			if(!result.isSuccess()) {
				task.setState(TaskStateEnum.FAIL_TASK.getState());
			}else{
				task.setState(TaskStateEnum.COMPLETE_TASK.getState());
			}
			task.setMessage(result.getMessage());//保存任务描述的信息
		}

		return taskList;
	}

	/**
	 * 根据task的类型，返回相应的处理类
	 * @param type
	 * @return
	 */
	private ExportAbstractService<?> getConcreteService(String type) {
		//根据相应的类型，返回对应的对象
		ExportAbstractService<?> service = null;

		return service;
	}
}
