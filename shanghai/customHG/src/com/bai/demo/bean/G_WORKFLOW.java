package com.bai.demo.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * EXAM_ANALYSE_DB_T数据库-工作流表
 * @author zhangyx
 *
 */
public class G_WORKFLOW implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String ENTRY_ID;// 报关单号
	private String OP_ER;// 操作人工号
	private String OP_ID;// 工作流类型
	private String OP_TIME;// 操作时间
	private String OP_NOTE;//
	public String getENTRY_ID() {
		return ENTRY_ID;
	}
	public void setENTRY_ID(String eNTRY_ID) {
		ENTRY_ID = eNTRY_ID;
	}
	public String getOP_ER() {
		return OP_ER;
	}
	public void setOP_ER(String oP_ER) {
		OP_ER = oP_ER;
	}
	public String getOP_ID() {
		return OP_ID;
	}
	public void setOP_ID(String oP_ID) {
		OP_ID = oP_ID;
	}
	
	public String getOP_TIME() {
		return OP_TIME;
	}
	public void setOP_TIME(String oP_TIME) {
		OP_TIME = oP_TIME;
	}
	
	
	public String getOP_NOTE() {
		return OP_NOTE;
	}
	public void setOP_NOTE(String oP_NOTE) {
		OP_NOTE = oP_NOTE;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
