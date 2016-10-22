package com.bierbobo.domain.task;

import java.io.Serializable;
import java.util.List;

public class TaskGroupTypeParam implements Serializable {

	
			/**
	 * @chenzhifei 
	 */
	private static final long serialVersionUID = 8745225363482315394L;
	
	private String businessType;
	List<String> businessKeys;
	List<String> names;
	private List<Integer> sourceStateList;
	private Integer targerState;
	private String serverIp;
	private Long effectiveUpdateTimeSpace;
	
	public TaskGroupTypeParam() {
		// TODO Auto-generated constructor stub
	}
	public TaskGroupTypeParam(String businessType, List<String> businessKeys, List<String> names, List<Integer> sourceStateList, Integer targerState, String serverIp, Long effectiveUpdateTimeSpace){
		this.businessType = businessType;
		this.businessKeys = businessKeys;
		this.names = names;
		this.sourceStateList = sourceStateList;
		this.targerState = targerState;
		this.serverIp = serverIp;
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
	public List<String> getNames() {
		return names;
	}
	public void setNames(List<String> names) {
		this.names = names;
	}
	public List<Integer> getSourceStateList() {
		return sourceStateList;
	}
	public void setSourceStateList(List<Integer> sourceStateList) {
		this.sourceStateList = sourceStateList;
	}
	public Integer getTargerState() {
		return targerState;
	}
	public void setTargerState(Integer targerState) {
		this.targerState = targerState;
	}
	public String getServerIp() {
		return serverIp;
	}
	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}
	public Long getEffectiveUpdateTimeSpace() {
		return effectiveUpdateTimeSpace;
	}
	public void setEffectiveUpdateTimeSpace(Long effectiveUpdateTimeSpace) {
		this.effectiveUpdateTimeSpace = effectiveUpdateTimeSpace;
	}
	

}
