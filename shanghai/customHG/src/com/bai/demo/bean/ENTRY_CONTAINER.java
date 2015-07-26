package com.bai.demo.bean;

import java.io.Serializable;

/**
 * 对应报关单的集装箱信息
 * @author zhangyx
 *
 */
public class ENTRY_CONTAINER implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String ENTRY_ID;// 报关单号
    private String CONTAINER_ID;// 集装箱号
    private String CONTAINER_WT;// 
	public String getENTRY_ID() {
		return ENTRY_ID;
	}
	public void setENTRY_ID(String eNTRY_ID) {
		ENTRY_ID = eNTRY_ID;
	}
	public String getCONTAINER_ID() {
		return CONTAINER_ID;
	}
	public void setCONTAINER_ID(String cONTAINER_ID) {
		CONTAINER_ID = cONTAINER_ID;
	}
	public String getCONTAINER_WT() {
		return CONTAINER_WT;
	}
	public void setCONTAINER_WT(String cONTAINER_WT) {
		CONTAINER_WT = cONTAINER_WT;
	}
    
    
}
