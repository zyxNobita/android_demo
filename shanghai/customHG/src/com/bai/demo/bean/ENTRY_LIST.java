package com.bai.demo.bean;

import java.io.Serializable;

/**
 * 商品信息
 * @author zhangyx
 *
 */
public class ENTRY_LIST implements Serializable{

	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String ENTRY_ID;//报关单号
	 private double G_NO;//项号
	 private double CONTR_ITEM;//合同项号
	 private String CODE_TS;//商品编码
	 private String G_NAME;//商品名称
	 private String G_MODEL;//规格型号
     private double G_QTY;//申报数量
     private String G_UNIT;//申报单位
     private double QTY_1;//法定数量
     private String UNIT_1;//法定计量单位
     private double QTY_2;//法定第二数量
     private String UNIT_2;//法定第二计量单位
     private String ORIGIN_COUNTRY;//原产国
     private double DECL_PRICE;//单价
     private double DECL_TOTAL;//总价
     private double TRADE_TOTAL;//成交总价
     private String TRADE_CURR;//币值
     private String DUTY_MODE;//征免
     private String USE_TO;//用途
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
	public double getG_QTY() {
		return G_QTY;
	}
	public void setG_QTY(double g_QTY) {
		G_QTY = g_QTY;
	}
	public String getG_UNIT() {
		return G_UNIT;
	}
	public void setG_UNIT(String g_UNIT) {
		G_UNIT = g_UNIT;
	}
	public double getQTY_1() {
		return QTY_1;
	}
	public void setQTY_1(double qTY_1) {
		QTY_1 = qTY_1;
	}
	public String getUNIT_1() {
		return UNIT_1;
	}
	public void setUNIT_1(String uNIT_1) {
		UNIT_1 = uNIT_1;
	}
	public double getQTY_2() {
		return QTY_2;
	}
	public void setQTY_2(double qTY_2) {
		QTY_2 = qTY_2;
	}
	public String getUNIT_2() {
		return UNIT_2;
	}
	public void setUNIT_2(String uNIT_2) {
		UNIT_2 = uNIT_2;
	}
	public String getORIGIN_COUNTRY() {
		return ORIGIN_COUNTRY;
	}
	public void setORIGIN_COUNTRY(String oRIGIN_COUNTRY) {
		ORIGIN_COUNTRY = oRIGIN_COUNTRY;
	}
	public double getDECL_PRICE() {
		return DECL_PRICE;
	}
	public void setDECL_PRICE(double dECL_PRICE) {
		DECL_PRICE = dECL_PRICE;
	}
	public double getDECL_TOTAL() {
		return DECL_TOTAL;
	}
	public void setDECL_TOTAL(double dECL_TOTAL) {
		DECL_TOTAL = dECL_TOTAL;
	}
	public double getTRADE_TOTAL() {
		return TRADE_TOTAL;
	}
	public void setTRADE_TOTAL(double tRADE_TOTAL) {
		TRADE_TOTAL = tRADE_TOTAL;
	}
	public String getTRADE_CURR() {
		return TRADE_CURR;
	}
	public void setTRADE_CURR(String tRADE_CURR) {
		TRADE_CURR = tRADE_CURR;
	}
	public String getDUTY_MODE() {
		return DUTY_MODE;
	}
	public void setDUTY_MODE(String dUTY_MODE) {
		DUTY_MODE = dUTY_MODE;
	}
	public String getUSE_TO() {
		return USE_TO;
	}
	public void setUSE_TO(String uSE_TO) {
		USE_TO = uSE_TO;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
 
}
