package com.bierbobo.rainbow.domain.task;

import java.io.Serializable;

/**
 * 重置task任务状态参数类
 * @author chenzhifei
 *
 */

public class RepeatTaskStateParam implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4203312841971405377L;
	private String businessType;//任务类型
	private Integer originalState;//初始任务状态
	private Long effectiveUpdateTimeSpace;//任务在初始状态有效的时间
	private Integer targetState;//目标任务状态
	
	public RepeatTaskStateParam(){
		
	}
	public RepeatTaskStateParam(String businessType,Integer originalState,Long effectiveUpdateTimeSpace,Integer targetState){
		this.businessType = businessType;
		this.originalState = originalState;
		this.effectiveUpdateTimeSpace = effectiveUpdateTimeSpace;
		this.targetState = targetState;
	}
	
	public String getBusinessType() {
		return businessType;
	}
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	public Integer getOriginalState() {
		return originalState;
	}
	public void setOriginalState(Integer originalState) {
		this.originalState = originalState;
	}
	public Long getEffectiveUpdateTimeSpace() {
		return effectiveUpdateTimeSpace;
	}
	public void setEffectiveUpdateTimeSpace(Long effectiveUpdateTimeSpace) {
		this.effectiveUpdateTimeSpace = effectiveUpdateTimeSpace;
	}
	public Integer getTargetState() {
		return targetState;
	}
	public void setTargetState(Integer targetState) {
		this.targetState = targetState;
	}
	

}
