package com.bai.demo.bean;

import java.io.Serializable;

/**
 * 
 * @author zhangyx
 *
 */
public class EXAM_MID_REL implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String FORM_ID;//报关单号
    private String TRADE_NAME;//经营单位
    private String AGENT_NAME;//申报单位
    private String G_NAME;//第一品名
    private String EXAM_TIME;//查验时间
    private String EXAM_MODE;//查验方式
    private String EXAM_ER;//查验关员
    private String EXAM_PROC_IDEA;//查验处理意见
    private String EXAM_PROC_CODE;//查验处理结果
    private String I_E_FLAG;//进出口
	public String getFORM_ID() {
		return FORM_ID;
	}
	public void setFORM_ID(String fORM_ID) {
		FORM_ID = fORM_ID;
	}
	public String getTRADE_NAME() {
		return TRADE_NAME;
	}
	public void setTRADE_NAME(String tRADE_NAME) {
		TRADE_NAME = tRADE_NAME;
	}
	public String getAGENT_NAME() {
		return AGENT_NAME;
	}
	public void setAGENT_NAME(String aGENT_NAME) {
		AGENT_NAME = aGENT_NAME;
	}
	public String getG_NAME() {
		return G_NAME;
	}
	public void setG_NAME(String g_NAME) {
		G_NAME = g_NAME;
	}
	public String getEXAM_TIME() {
		return EXAM_TIME;
	}
	public void setEXAM_TIME(String eXAM_TIME) {
		EXAM_TIME = eXAM_TIME;
	}
	public String getEXAM_MODE() {
		return EXAM_MODE;
	}
	public void setEXAM_MODE(String eXAM_MODE) {
		EXAM_MODE = eXAM_MODE;
	}
	public String getEXAM_ER() {
		return EXAM_ER;
	}
	public void setEXAM_ER(String eXAM_ER) {
		EXAM_ER = eXAM_ER;
	}
	public String getEXAM_PROC_IDEA() {
		return EXAM_PROC_IDEA;
	}
	public void setEXAM_PROC_IDEA(String eXAM_PROC_IDEA) {
		EXAM_PROC_IDEA = eXAM_PROC_IDEA;
	}
	public String getEXAM_PROC_CODE() {
		return EXAM_PROC_CODE;
	}
	public void setEXAM_PROC_CODE(String eXAM_PROC_CODE) {
		EXAM_PROC_CODE = eXAM_PROC_CODE;
	}
	public String getI_E_FLAG() {
		return I_E_FLAG;
	}
	public void setI_E_FLAG(String i_E_FLAG) {
		I_E_FLAG = i_E_FLAG;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
 
}
