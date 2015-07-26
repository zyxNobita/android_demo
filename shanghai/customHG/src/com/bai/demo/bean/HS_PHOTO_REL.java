package com.bai.demo.bean;

import java.io.Serializable;

/**
 * 查验知识库菜单-标准化商品信息-根据编号获取图片地址
 * @author zhangyx
 *
 */
public class HS_PHOTO_REL implements Serializable{
	
	private String G_ID;//编号
	private String G_PHOTO;//图片地址
    private String IS_DELETE;//是否删除
	public String getG_ID() {
		return G_ID;
	}
	public void setG_ID(String g_ID) {
		G_ID = g_ID;
	}
	public String getG_PHOTO() {
		return G_PHOTO;
	}
	public void setG_PHOTO(String g_PHOTO) {
		G_PHOTO = g_PHOTO;
	}
	public String getIS_DELETE() {
		return IS_DELETE;
	}
	public void setIS_DELETE(String iS_DELETE) {
		IS_DELETE = iS_DELETE;
	}

    
}
