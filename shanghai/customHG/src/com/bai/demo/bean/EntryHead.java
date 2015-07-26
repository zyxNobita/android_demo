package com.bai.demo.bean;

import java.io.Serializable;

/**
 * 本地数据库-报关单表
 * @author zhangyx
 *
 */
public class EntryHead implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String Entry_ID;//报关单号
	private int State;//状态
	private String Save_Time;//保存时间
	private String Upload_Time;//上传时间
	public String getEntry_ID() {
		return Entry_ID;
	}
	public void setEntry_ID(String entry_ID) {
		Entry_ID = entry_ID;
	}
	public int getState() {
		return State;
	}
	public void setState(int state) {
		State = state;
	}
	public String getSave_Time() {
		return Save_Time;
	}
	public void setSave_Time(String save_Time) {
		Save_Time = save_Time;
	}
	public String getUpload_Time() {
		return Upload_Time;
	}
	public void setUpload_Time(String upload_Time) {
		Upload_Time = upload_Time;
	}
	@Override
	public String toString() {
		return "EntryID [Entry_ID=" + Entry_ID + ", State=" + State
				+ ", Save_Time=" + Save_Time + ", Upload_Time=" + Upload_Time
				+ "]";
	}
	
	
}
