package cn.tydic.mobile.pdarequery.entity;

import java.io.Serializable;

/***
 * 号牌种类----车辆信息查询
 * @author zhangyx
 *
 */
@SuppressWarnings("serial")
public class CarNnumberType implements Serializable{
	private String dmz;	//代码值
	private String dmsm1;	//代码说明1
	public String getDmz() {
		return dmz;
	}
	public void setDmz(String dmz) {
		this.dmz = dmz;
	}
	public String getDmsm1() {
		return dmsm1;
	}
	public void setDmsm1(String dmsm1) {
		this.dmsm1 = dmsm1;
	}
	
}
