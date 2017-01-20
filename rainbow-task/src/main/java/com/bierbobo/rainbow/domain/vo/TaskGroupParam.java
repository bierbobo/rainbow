package com.bierbobo.rainbow.domain.vo;

import java.io.Serializable;
import java.util.List;

public class TaskGroupParam implements Serializable {


	private static final long serialVersionUID = 8745225363482315394L;
	
	private String businessType;
	List<String> businessKeys;
	private List<Integer> sourceStateList;
	private Long effectiveUpdateTimeSpace;

	public TaskGroupParam() {
	}

	public TaskGroupParam(String businessType, List<String> businessKeys, List<Integer> sourceStateList, Long effectiveUpdateTimeSpace) {
		this.businessType = businessType;
		this.businessKeys = businessKeys;
		this.sourceStateList = sourceStateList;
		this.effectiveUpdateTimeSpace = effectiveUpdateTimeSpace;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public List<String> getBusinessKeys() {
		return businessKeys;
	}

	public void setBusinessKeys(List<String> businessKeys) {
		this.businessKeys = businessKeys;
	}

	public List<Integer> getSourceStateList() {
		return sourceStateList;
	}

	public void setSourceStateList(List<Integer> sourceStateList) {
		this.sourceStateList = sourceStateList;
	}

	public Long getEffectiveUpdateTimeSpace() {
		return effectiveUpdateTimeSpace;
	}

	public void setEffectiveUpdateTimeSpace(Long effectiveUpdateTimeSpace) {
		this.effectiveUpdateTimeSpace = effectiveUpdateTimeSpace;
	}
}
