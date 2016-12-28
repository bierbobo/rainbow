package com.bierbobo.rainbow.domain.task;

public class QueryTaskParam {

	private Integer taskNum;
	private String businessType;

	public QueryTaskParam() {

	}

	public QueryTaskParam(String businessType) {
		this.businessType = businessType;
	}

	public QueryTaskParam(String businessType, Integer taskNum) {
		this.taskNum = taskNum;
		this.businessType = businessType;
	}

	public Integer getTaskNum() {
		return taskNum;
	}

	public void setTaskNum(Integer taskNum) {
		this.taskNum = taskNum;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

}
