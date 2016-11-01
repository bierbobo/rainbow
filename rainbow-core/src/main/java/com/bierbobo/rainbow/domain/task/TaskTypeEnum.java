package com.bierbobo.rainbow.domain.task;


public enum TaskTypeEnum {
	
	BI_INPUT("BI_INPUT","BI采销数据导入BIIP任务"),
	STOCK_MAJOR("STOCK_MAJOR","重点品库存提醒任务"),
	STOCK_MINOR("STOCK_MINOR","非重点品库存提醒任务"),
	PO("PO","采购单任务"),
	WARE_EXP("WARE_EXP","页面查询导出任务"),
	STOCK_REMIND_IGNORE("STOCK_REMIND_IGNORE","商品不提醒数据导出任务"),
	STOCK_STA("STOCK_STA","库存统计"),
	REALTIME_INVENTORY("REALTIME_INVENTORY","实时库存更新任务"),
	ES_EXPORT("ES_EXPORT","备件库库存明细任务");
	
	private String type;//任务类型
	private String msg;//任务描述
	
	private TaskTypeEnum(String type,String msg){
		this.type = type;
		this.msg = msg;
	}
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}


	public static String getTaskTypeStr(){
		StringBuffer taskTypeStr = new StringBuffer("");
		for(TaskTypeEnum temp : values()){
			taskTypeStr.append(temp.getType()+",");
		}
		return taskTypeStr.toString().substring(0,taskTypeStr.toString().length()-1);
	}

}
