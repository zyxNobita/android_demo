package com.bai.demo.bean;

import java.io.Serializable;

/**
 * 
 * @author zhangyx
 *
 */
public class TradeInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String TRADE_CO; //企业代码
    private String TRADE_NAME;//企业名称
    private String I_E_FLAG;//
    private double EXAM_SUM;//查验批数
    private double EXAM_CATCH;//查获批数
    private double DEC_SUM;//申报批数
    private double EXAM_RATE;//查验率（百分比）
    private double EXAM_CATCH_RATE;//查获率（百分比）
    private double CATCH_CERT;//涉证数
    private double CATCH_TAX;//涉税数
    private double ABOUT_POWER;//涉侵权数
    private double ABOUT_ARMY;//涉军品数
    private double CATCH_CASE;//缉私立案数
	public String getTRADE_CO() {
		return TRADE_CO;
	}
	public void setTRADE_CO(String tRADE_CO) {
		TRADE_CO = tRADE_CO;
	}
	public String getTRADE_NAME() {
		return TRADE_NAME;
	}
	public void setTRADE_NAME(String tRADE_NAME) {
		TRADE_NAME = tRADE_NAME;
	}
	public String getI_E_FLAG() {
		return I_E_FLAG;
	}
	public void setI_E_FLAG(String i_E_FLAG) {
		I_E_FLAG = i_E_FLAG;
	}
	public double getEXAM_SUM() {
		return EXAM_SUM;
	}
	public void setEXAM_SUM(double eXAM_SUM) {
		EXAM_SUM = eXAM_SUM;
	}
	public double getEXAM_CATCH() {
		return EXAM_CATCH;
	}
	public void setEXAM_CATCH(double eXAM_CATCH) {
		EXAM_CATCH = eXAM_CATCH;
	}
	public double getDEC_SUM() {
		return DEC_SUM;
	}
	public void setDEC_SUM(double dEC_SUM) {
		DEC_SUM = dEC_SUM;
	}
	public double getEXAM_RATE() {
		return EXAM_RATE;
	}
	public void setEXAM_RATE(double eXAM_RATE) {
		EXAM_RATE = eXAM_RATE;
	}
	public double getEXAM_CATCH_RATE() {
		return EXAM_CATCH_RATE;
	}
	public void setEXAM_CATCH_RATE(double eXAM_CATCH_RATE) {
		EXAM_CATCH_RATE = eXAM_CATCH_RATE;
	}
	public double getCATCH_CERT() {
		return CATCH_CERT;
	}
	public void setCATCH_CERT(double cATCH_CERT) {
		CATCH_CERT = cATCH_CERT;
	}
	public double getCATCH_TAX() {
		return CATCH_TAX;
	}
	public void setCATCH_TAX(double cATCH_TAX) {
		CATCH_TAX = cATCH_TAX;
	}
	public double getABOUT_POWER() {
		return ABOUT_POWER;
	}
	public void setABOUT_POWER(double aBOUT_POWER) {
		ABOUT_POWER = aBOUT_POWER;
	}
	public double getABOUT_ARMY() {
		return ABOUT_ARMY;
	}
	public void setABOUT_ARMY(double aBOUT_ARMY) {
		ABOUT_ARMY = aBOUT_ARMY;
	}
	public double getCATCH_CASE() {
		return CATCH_CASE;
	}
	public void setCATCH_CASE(double cATCH_CASE) {
		CATCH_CASE = cATCH_CASE;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
