package com.bai.demo.bean;

import java.io.Serializable;

/**
 * 查验知识库菜单-标准化商品信息
 * @author zhangyx
 *
 */
public class HS_RSK_REL implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String CODE_TS;//商品税号
    private String G_NAME;//商品品名
    private String RSK_POINT;//商品查验提示
    private String G_ID;//编号
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
	public String getRSK_POINT() {
		return RSK_POINT;
	}
	public void setRSK_POINT(String rSK_POINT) {
		RSK_POINT = rSK_POINT;
	}
	public String getG_ID() {
		return G_ID;
	}
	public void setG_ID(String g_ID) {
		G_ID = g_ID;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
