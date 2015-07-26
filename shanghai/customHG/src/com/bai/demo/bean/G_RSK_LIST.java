package com.bai.demo.bean;

import java.io.Serializable;

/**
 * 查验知识库菜单-查询风险要点
 * @author zhangyx
 *
 */
public class G_RSK_LIST implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String G_NAME;// 商品品名
	private String CODE_TS;// 商品税号
	private String G_RSK_SUM;// 查验风险要点
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
	public String getG_RSK_SUM() {
		return G_RSK_SUM;
	}
	public void setG_RSK_SUM(String g_RSK_SUM) {
		G_RSK_SUM = g_RSK_SUM;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
