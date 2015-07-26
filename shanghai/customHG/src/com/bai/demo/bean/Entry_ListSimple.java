package com.bai.demo.bean;

import java.io.Serializable;

/**
 * CHK_ENTRY数据库-报关单表体
 * @author zhangyx
 *
 */
public class Entry_ListSimple implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String ENTRY_ID;// 报关单号
	private double G_NO;// 项号
	private double CONTR_ITEM;// 合同项号
	private String CODE_TS;// 商品编码
	private String G_NAME;// 商品名称
	private String G_MODEL;// 规格型号
	public String getENTRY_ID() {
		return ENTRY_ID;
	}
	public void setENTRY_ID(String eNTRY_ID) {
		ENTRY_ID = eNTRY_ID;
	}
	public double getG_NO() {
		return G_NO;
	}
	public void setG_NO(double g_NO) {
		G_NO = g_NO;
	}
	public double getCONTR_ITEM() {
		return CONTR_ITEM;
	}
	public void setCONTR_ITEM(double cONTR_ITEM) {
		CONTR_ITEM = cONTR_ITEM;
	}
	public String getCODE_TS() {
		return CODE_TS;
	}
	public void setCODE_TS(String cODE_TS) {
		CODE_TS = cODE_TS;
	}
	public String getG_NAME() {
		return G_NAME;
	}
	public void setG_NAME(String g_NAME) {
		G_NAME = g_NAME;
	}
	public String getG_MODEL() {
		return G_MODEL;
	}
	public void setG_MODEL(String g_MODEL) {
		G_MODEL = g_MODEL;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
