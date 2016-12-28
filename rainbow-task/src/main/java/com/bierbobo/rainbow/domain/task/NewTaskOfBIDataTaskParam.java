package com.bierbobo.rainbow.domain.task;

import java.io.Serializable;

public class NewTaskOfBIDataTaskParam implements Serializable {
	
	
			/**
	 * @chenzhifei 
	 */
	private static final long serialVersionUID = -2104026256008005350L;
	private String businessType;
	private String businessKeyPre;
	public String getBusinessType() {
		return businessType;
	}
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	public String getBusinessKeyPre() {
		return businessKeyPre;
	}
	public void setBusinessKeyPre(String businessKeyPre) {
		this.businessKeyPre = businessKeyPre;
	}

}
