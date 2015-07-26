package com.bai.demo.bean;

import java.io.Serializable;

/**
 * 本地数据库-附件表
 * @author zhangyx
 *
 */
public class PhotoList implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String Entry_ID;// 报关单号
	private int G_No;// 项号
	private String Photo_list_Code;// 图片类型
	private String Photo_list_ID;// 图片名
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
	public String getPhoto_list_Code() {
		return Photo_list_Code;
	}
	public void setPhoto_list_Code(String photo_list_Code) {
		Photo_list_Code = photo_list_Code;
	}
	public String getPhoto_list_ID() {
		return Photo_list_ID;
	}
	public void setPhoto_list_ID(String photo_list_ID) {
		Photo_list_ID = photo_list_ID;
	}
	@Override
	public String toString() {
		return "PhotoList [Entry_ID=" + Entry_ID + ", G_No=" + G_No
				+ ", Photo_list_Code=" + Photo_list_Code + ", Photo_list_ID="
				+ Photo_list_ID + "]";
	}
	
	
}
