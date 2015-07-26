package com.bai.demo.bean;

import java.io.Serializable;

/**
 * 本地数据库-报关单表表体
 * @author zhangyx
 *
 */
public class EntryList implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String Entry_ID;// 报关单号
	private int G_No;// 项号
	private String CODE_TS;// 商品编码
	private String G_NAME;// 品名
	private int isYs;// 是否移送
	private String Remark;// 备注
	public String getEntry_ID() {
		return Entry_ID;
	}
	public void setEntry_ID(String entry_ID) {
		Entry_ID = entry_ID;
	}
	public int getG_No() {
		return G_No;
	}
	public void setG_No(int g_No) {
		G_No = g_No;
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
	public int getIsYs() {
		return isYs;
	}
	public void setIsYs(int isYs) {
		this.isYs = isYs;
	}
	public String getRemark() {
		return Remark;
	}
	public void setRemark(String remark) {
		Remark = remark;
	}
	@Override
	public String toString() {
		return "EntryList [Entry_ID=" + Entry_ID + ", G_No=" + G_No
				+ ", CODE_TS=" + CODE_TS + ", G_NAME=" + G_NAME + ", isYs="
				+ isYs + ", Remark=" + Remark + "]";
	}
	
	
	
}
