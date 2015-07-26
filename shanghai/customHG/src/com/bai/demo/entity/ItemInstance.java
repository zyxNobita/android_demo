package com.bai.demo.entity;

/**
 * 业务操作布局资源实体类<br>
 * 关联 nav_item.xml布局文件
 * @author bai
 *
 */
public class ItemInstance {

	private String infoName ;
	private int icon, to;

	public String getInfoName() {
		return infoName;
	}

	public void setInfoName(String infoName) {
		this.infoName = infoName;
	}

	public int getIcon() {
		return icon;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}

	public int getTo() {
		return to;
	}

	public void setTo(int to) {
		this.to = to;
	}

	public ItemInstance(String infoName, int icon) {
		super();
		this.infoName = infoName;
		this.icon = icon;
	}

	public ItemInstance(String infoName, int icon, int to) {
		super();
		this.infoName = infoName;
		this.icon = icon;
		this.to = to;
	}

	public ItemInstance() {
		
		super();
	}

}
