package com.bai.demo.bean;

import java.io.Serializable;

/**
 * 查验知识库菜单-监管条件
 * @author zhangyx
 *
 */
public class COMPLEX implements Serializable{

	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String CODE_TS; //商品税号
	private String G_NAME;//商品品名
    private String BEGIN_DATE;//生效日期
    private String LSJM_FLAG;//临时减免标志
    private String END_DATE;//截止日期
    private double LOW_RATE;//进口关税率/低
    private double HIGH_RATE;//进口关税率/普
    private double OUT_RATE;//出口从价关税率
    private double TAX_RATE;//增值税率
    private String UNIT_1;//第一计量单位
    private String CONTROL_MARK;//监管要求
    private String NOTE_S;//备注
    private String CONTROL_MARK_TEXT;//
    
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

	public String getBEGIN_DATE() {
		return BEGIN_DATE;
	}

	public void setBEGIN_DATE(String bEGIN_DATE) {
		BEGIN_DATE = bEGIN_DATE;
	}

	public String getLSJM_FLAG() {
		return LSJM_FLAG;
	}

	public void setLSJM_FLAG(String lSJM_FLAG) {
		LSJM_FLAG = lSJM_FLAG;
	}

	public String getEND_DATE() {
		return END_DATE;
	}

	public void setEND_DATE(String eND_DATE) {
		END_DATE = eND_DATE;
	}

	public double getLOW_RATE() {
		return LOW_RATE;
	}

	public void setLOW_RATE(double lOW_RATE) {
		LOW_RATE = lOW_RATE;
	}

	public double getHIGH_RATE() {
		return HIGH_RATE;
	}

	public void setHIGH_RATE(double hIGH_RATE) {
		HIGH_RATE = hIGH_RATE;
	}

	public double getOUT_RATE() {
		return OUT_RATE;
	}

	public void setOUT_RATE(double oUT_RATE) {
		OUT_RATE = oUT_RATE;
	}

	public double getTAX_RATE() {
		return TAX_RATE;
	}

	public void setTAX_RATE(double tAX_RATE) {
		TAX_RATE = tAX_RATE;
	}

	public String getUNIT_1() {
		return UNIT_1;
	}

	public void setUNIT_1(String uNIT_1) {
		UNIT_1 = uNIT_1;
	}

	public String getCONTROL_MARK() {
		return CONTROL_MARK;
	}

	public void setCONTROL_MARK(String cONTROL_MARK) {
		CONTROL_MARK = cONTROL_MARK;
	}

	public String getNOTE_S() {
		return NOTE_S;
	}

	public void setNOTE_S(String nOTE_S) {
		NOTE_S = nOTE_S;
	}

	public String getCONTROL_MARK_TEXT() {
		return CONTROL_MARK_TEXT;
	}

	public void setCONTROL_MARK_TEXT(String cONTROL_MARK_TEXT) {
		CONTROL_MARK_TEXT = cONTROL_MARK_TEXT;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
