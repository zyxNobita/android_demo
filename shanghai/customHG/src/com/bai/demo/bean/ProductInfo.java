package com.bai.demo.bean;

import java.io.Serializable;

/**
 * 
 * @author zhangyx
 *
 */
public class ProductInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String CODE_TS;//商品编码
    private String DEC_SUM;//申报批数
    private String EXAM_SUM;//查验批数
    private double EXAM_RATE;//查验率(百分比)
    private double EXAM_CATCH;//查获批数
    private double EXAM_CATCH_RATE;//查获率(百分比)
    private double RSK_MONTH;//月份
	public String getCODE_TS() {
		return CODE_TS;
	}
	public void setCODE_TS(String cODE_TS) {
		CODE_TS = cODE_TS;
	}
	public String getDEC_SUM() {
		return DEC_SUM;
	}
	public void setDEC_SUM(String dEC_SUM) {
		DEC_SUM = dEC_SUM;
	}
	public String getEXAM_SUM() {
		return EXAM_SUM;
	}
	public void setEXAM_SUM(String eXAM_SUM) {
		EXAM_SUM = eXAM_SUM;
	}
	public double getEXAM_RATE() {
		return EXAM_RATE;
	}
	public void setEXAM_RATE(double eXAM_RATE) {
		EXAM_RATE = eXAM_RATE;
	}
	public double getEXAM_CATCH() {
		return EXAM_CATCH;
	}
	public void setEXAM_CATCH(double eXAM_CATCH) {
		EXAM_CATCH = eXAM_CATCH;
	}
	public double getEXAM_CATCH_RATE() {
		return EXAM_CATCH_RATE;
	}
	public void setEXAM_CATCH_RATE(double eXAM_CATCH_RATE) {
		EXAM_CATCH_RATE = eXAM_CATCH_RATE;
	}
	public double getRSK_MONTH() {
		return RSK_MONTH;
	}
	public void setRSK_MONTH(double rSK_MONTH) {
		RSK_MONTH = rSK_MONTH;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

    
}
