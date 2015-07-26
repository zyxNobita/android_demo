package com.bai.demo.bean;

import java.io.Serializable;

/**
 * 对应报关单的集装箱随附单据信息
 * @author zhangyx
 *
 */
public class ENTRY_CERTIFICATE implements Serializable{
	
     /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String ENTRY_ID;// 报关单号
     private String DOCU_CODE;// 
     private String CERT_CODE;// 随附单据编号
	public String getENTRY_ID() {
		return ENTRY_ID;
	}
	public void setENTRY_ID(String eNTRY_ID) {
		ENTRY_ID = eNTRY_ID;
	}
	public String getDOCU_CODE() {
		return DOCU_CODE;
	}
	public void setDOCU_CODE(String dOCU_CODE) {
		DOCU_CODE = dOCU_CODE;
	}
	public String getCERT_CODE() {
		return CERT_CODE;
	}
	public void setCERT_CODE(String cERT_CODE) {
		CERT_CODE = cERT_CODE;
	}
 
}
