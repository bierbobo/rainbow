package com.bierbobo.rainbow.domain.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Task implements Serializable{

    private String uuid;

    private String businessType;
    private String businessKey;

    private String name;

    private Integer state;

    private String serverIp;
    
    private String message;

	private Date createTime;

    private Date updateTime;
    
    private Long effectiveUpdateTimeSpace;

    //从源状态转变到新状态： 0-1 ，1-2 ，1-3,   30-1
    private List<Integer> sourceStateList;

//	private Integer canUpdateFlag;
    
	public Task(){
    	
    }
    public Task(String businessType, String businessKey, String name){
    	this.businessType = businessType;
    	this.businessKey = businessKey;
    	this.name = name;
    }
    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType == null ? null : businessType.trim();
    }

    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey == null ? null : businessKey.trim();
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid == null ? null : uuid.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp == null ? null : serverIp.trim();
    }
    public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    public Long getEffectiveUpdateTimeSpace() {
		return effectiveUpdateTimeSpace;
	}
	public void setEffectiveUpdateTimeSpace(Long effectiveUpdateTimeSpace) {
		this.effectiveUpdateTimeSpace = effectiveUpdateTimeSpace;
	}
    public List<Integer> getSourceStateList() {
		return sourceStateList;
	}
	public void setSourceStateList(List<Integer> sourceStateList) {
		this.sourceStateList = sourceStateList;
	}
//	public Integer getCanUpdateFlag() {
//		return canUpdateFlag;
//	}
//	public void setCanUpdateFlag(Integer canUpdateFlag) {
//		this.canUpdateFlag = canUpdateFlag;
//	}
//	@Override
//	public int hashCode(){
//		return (this.businessType+this.businessKey).hashCode();
////        int result = 17;
////        result = 37 * result + this.businessType.hashCode();
////        result = 37 * result + this.businessKey.hashCode();
////        return result;
//	}
//	@Override
//	public boolean equals(Object obj){
//		if(!(obj instanceof Task )){
//			return false;
//		}else{
//			Task temp = (Task)obj;
//			if(this == temp){
//				return true;
//			}else if(this.getBusinessType().equals(temp.getBusinessType())&&this.getBusinessKey().equals(temp.getBusinessKey())){
//				return true;
//			}else{
//				return false;
//			}
//			
//		}
//	}
}