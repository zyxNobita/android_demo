package com.bai.demo.bean;

import java.io.Serializable;
/**
 * 
 * @author zhangyx
 *
 */
public class EXAM_PER_MID_REL implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String OP_ID;//关员工号
    private String OP_NAME;//关员姓名
    private double EXAM_SUM;//查验批次
    private double EXAM_CATCH;//查获批次
    private double EXAM_CATCH_RATE;//查获率(百分比)
    private double SEND;//移送批数
    private double SEND_RATE;//移送率
    private double CATCH_CERT;//查获（涉证）数
    private double CATCH_TAX;//查获（涉税）数
    private double CATCH_DELETE;//查获（删改）数
    private double CATCH_OTHER;//查获（其它）数
    private double ABOUT_POWER;//涉侵权数
    private double ABOUT_ARMY;//涉军品数
    private double CATCH_CASE;//缉私立案数
	public String getOP_ID() {
		return OP_ID;
	}
	public void setOP_ID(String oP_ID) {
		OP_ID = oP_ID;
	}
	public String getOP_NAME() {
		return OP_NAME;
	}
	public void setOP_NAME(String oP_NAME) {
		OP_NAME = oP_NAME;
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
	public double getEXAM_CATCH_RATE() {
		return EXAM_CATCH_RATE;
	}
	public void setEXAM_CATCH_RATE(double eXAM_CATCH_RATE) {
		EXAM_CATCH_RATE = eXAM_CATCH_RATE;
	}
	public double getSEND() {
		return SEND;
	}
	public void setSEND(double sEND) {
		SEND = sEND;
	}
	public double getSEND_RATE() {
		return SEND_RATE;
	}
	public void setSEND_RATE(double sEND_RATE) {
		SEND_RATE = sEND_RATE;
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
	public double getCATCH_DELETE() {
		return CATCH_DELETE;
	}
	public void setCATCH_DELETE(double cATCH_DELETE) {
		CATCH_DELETE = cATCH_DELETE;
	}
	public double getCATCH_OTHER() {
		return CATCH_OTHER;
	}
	public void setCATCH_OTHER(double cATCH_OTHER) {
		CATCH_OTHER = cATCH_OTHER;
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
