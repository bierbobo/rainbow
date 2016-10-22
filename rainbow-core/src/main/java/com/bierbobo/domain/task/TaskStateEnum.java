package com.bierbobo.domain.task;

public enum TaskStateEnum {
	
	NEW_TASK(0,"任务待执行"),
	PROCESSING_TASK(1,"任务执行中"),
	COMPLETE_TASK(2,"任务执行成功"),
	FAIL_TASK(3,"任务执行失败");
	
	private Integer state;
	
	private String msg;
	
	private TaskStateEnum(Integer state,String msg){
		this.state = state;
		this.msg = msg;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
    public static TaskStateEnum getByState(String state) {
        for (TaskStateEnum ens : values()) {
            if (ens.getState().toString().equals(state)) {
                return ens;
            }
        }
        return null;
    }
    public static String getMsgByState(Integer state){
    	String msg = "";
    	for (TaskStateEnum ens : values()) {
            if (ens.getState().equals(state)) {
            	msg =  ens.getMsg();
            }
        }
    	return msg;
    }
}
