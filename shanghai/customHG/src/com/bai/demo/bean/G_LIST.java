package com.bai.demo.bean;

import java.io.Serializable;

/**
 * 查验知识库菜单-典型查询商品信息
 * @author zhangyx
 *
 */
public class G_LIST implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String ENTRY_ID;// 报关单号
	private String G_NAME;// 查获项商品名
	private String CODE_TS;// 查获项申报税号
	private String READ_NOTE;// 查获意见提示
	private String STAR_HTML;// 优秀程度
	private double G_NO;// 
	private String EXAM_PROC_CODE;// 
	private String EXAM_PROC_NAME;// H2000处理结果
	public String getENTRY_ID() {
		return ENTRY_ID;
	}
	public void setENTRY_ID(String eNTRY_ID) {
		ENTRY_ID = eNTRY_ID;
	}
	public String getG_NAME() {
		return G_NAME;
	}
	public void setG_NAME(String g_NAME) {
		G_NAME = g_NAME;
	}
	public String getCODE_TS() {
		return CODE_TS;
	}
	public void setCODE_TS(String cODE_TS) {
		CODE_TS = cODE_TS;
	}
	public String getREAD_NOTE() {
		return READ_NOTE;
	}
	public void setREAD_NOTE(String rEAD_NOTE) {
		READ_NOTE = rEAD_NOTE;
	}
	public String getSTAR_HTML() {
		return STAR_HTML;
	}
	public void setSTAR_HTML(String sTAR_HTML) {
		STAR_HTML = sTAR_HTML;
	}
	public double getG_NO() {
		return G_NO;
	}
	public void setG_NO(double g_NO) {
		G_NO = g_NO;
	}
	public String getEXAM_PROC_CODE() {
		return EXAM_PROC_CODE;
	}
	public void setEXAM_PROC_CODE(String eXAM_PROC_CODE) {
		EXAM_PROC_CODE = eXAM_PROC_CODE;
	}
	public String getEXAM_PROC_NAME() {
		return EXAM_PROC_NAME;
	}
	public void setEXAM_PROC_NAME(String eXAM_PROC_NAME) {
		EXAM_PROC_NAME = eXAM_PROC_NAME;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
