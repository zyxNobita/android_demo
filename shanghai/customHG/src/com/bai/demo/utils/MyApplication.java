package com.bai.demo.utils;

import java.util.ArrayList;
import java.util.List;

import com.bai.demo.bean.HS_PHOTO_REL;
import com.bai.demo.bean.PHOTO_LIST;
import com.bai.demo.bean.UserInfo;

import android.app.Application;

/**
 * 全局变量
 * @author zhangyx
 *
 */
public class MyApplication extends Application{
	public UserInfo userInfo=new UserInfo();//用户登录的信息
	public String entryId;// 报关单号
	public List<String> CUSTOMS=new ArrayList<String>();// 查询关区
	public List<String> DEP_NAME=new ArrayList<String>();;// 查询科室
	public List<String> OP_NAME=new ArrayList<String>();;// 获取科室人员
	public String entryNum;// 待查验列表模块点击GalleryList获取报关单号
	public String entryNumber;// 查验图像上传模块获取EditText获取报关单号
	public String manifestNumber; // 上传审核信息模块的报关单号
	public String projectNum;// 表体项号
	public String picturePath;//本地上传 的path
	public ArrayList<String> pPathG1=new ArrayList<String>();
	public ArrayList<String> pPathG2=new ArrayList<String>();
	public ArrayList<String> pPathG3=new ArrayList<String>();
	//存储下载图片的相关信息  -------分类存储
	public List<PHOTO_LIST> photoList=new ArrayList<PHOTO_LIST>();//下载图片对象的集合------表头、表体
	
	//存储标准化商品信息下载图片的相关信息
	public List<HS_PHOTO_REL> hsPhotoList = new ArrayList<HS_PHOTO_REL>();//下载标准化商品信息图片对象的集合
	
	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public String getEntryId() {
		return entryId;
	}

	public void setEntryId(String entryId) {
		this.entryId = entryId;
	}

	public List<String> getCUSTOMS() {
		return CUSTOMS;
	}

	public void setCUSTOMS(List<String> cUSTOMS) {
		CUSTOMS = cUSTOMS;
	}

	public List<String> getDEP_NAME() {
		return DEP_NAME;
	}

	public void setDEP_NAME(List<String> dEP_NAME) {
		DEP_NAME = dEP_NAME;
	}

	public List<String> getOP_NAME() {
		return OP_NAME;
	}

	public void setOP_NAME(List<String> oP_NAME) {
		OP_NAME = oP_NAME;
	}

	public String getEntryNum() {
		return entryNum;
	}

	public void setEntryNum(String entryNum) {
		this.entryNum = entryNum;
	}

	public String getEntryNumber() {
		return entryNumber;
	}

	public void setEntryNumber(String entryNumber) {
		this.entryNumber = entryNumber;
	}

	public String getManifestNumber() {
		return manifestNumber;
	}

	public void setManifestNumber(String manifestNumber) {
		this.manifestNumber = manifestNumber;
	}

	public String getProjectNum() {
		return projectNum;
	}

	public void setProjectNum(String projectNum) {
		this.projectNum = projectNum;
	}

	public List<PHOTO_LIST> getPhotoList() {
		return photoList;
	}

	public void setPhotoList(List<PHOTO_LIST> photoList) {
		this.photoList = photoList;
	}
	
	public List<HS_PHOTO_REL> getHsPhotoList() {
		return hsPhotoList;
	}

	public void setHsPhotoList(List<HS_PHOTO_REL> hsPhotoList) {
		this.hsPhotoList = hsPhotoList;
	}

	public String getPicturePath() {
		return picturePath;
	}

	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
	}
	
}