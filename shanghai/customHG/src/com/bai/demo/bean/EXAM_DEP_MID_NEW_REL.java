package com.bai.demo.bean;

import java.io.Serializable;

/**
 * 
 * @author zhangyx
 *
 */
public class EXAM_DEP_MID_NEW_REL implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private String depCode;//
    private String DepName;//单位名称
    private double SUM;//查验批次
    private double CONTAINER;//查验自然箱量
    private double CONTAINER_CATCH;//查获自然箱量
    private double SAND;//查验散货数
    private double CATCH;//查获批次
    private double CATCH_RATE;//查验查获率(百分比)
    private double DAYMAX;//日最高查验批次
    private double SEND;//移送批数
    private double SEND_RATE;//移送率
	public String getDepCode() {
		return depCode;
	}
	public void setDepCode(String depCode) {
		this.depCode = depCode;
	}
	public String getDepName() {
		return DepName;
	}
	public void setDepName(String depName) {
		DepName = depName;
	}
	public double getSUM() {
		return SUM;
	}
	public void setSUM(double sUM) {
		SUM = sUM;
	}
	public double getCONTAINER() {
		return CONTAINER;
	}
	public void setCONTAINER(double cONTAINER) {
		CONTAINER = cONTAINER;
	}
	public double getCONTAINER_CATCH() {
		return CONTAINER_CATCH;
	}
	public void setCONTAINER_CATCH(double cONTAINER_CATCH) {
		CONTAINER_CATCH = cONTAINER_CATCH;
	}
	public double getSAND() {
		return SAND;
	}
	public void setSAND(double sAND) {
		SAND = sAND;
	}
	public double getCATCH() {
		return CATCH;
	}
	public void setCATCH(double cATCH) {
		CATCH = cATCH;
	}
	public double getCATCH_RATE() {
		return CATCH_RATE;
	}
	public void setCATCH_RATE(double cATCH_RATE) {
		CATCH_RATE = cATCH_RATE;
	}
	public double getDAYMAX() {
		return DAYMAX;
	}
	public void setDAYMAX(double dAYMAX) {
		DAYMAX = dAYMAX;
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
