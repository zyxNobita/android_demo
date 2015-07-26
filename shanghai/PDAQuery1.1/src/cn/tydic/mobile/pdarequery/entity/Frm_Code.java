package cn.tydic.mobile.pdarequery.entity;

import java.io.Serializable;

/***
 *  查验业务受理----Spinner条件类{车辆用途、车辆颜色、号牌种类}
 * @author zhangyx
 *
 */
@SuppressWarnings("serial")
public class Frm_Code implements Serializable{

	private String dmsm1;//显示的值
	private String dmz;//提交的值
	private String prefix;//
	public String getDmsm1() {
		return dmsm1;
	}
	public void setDmsm1(String dmsm1) {
		this.dmsm1 = dmsm1;
	}
	public String getDmz() {
		return dmz;
	}
	public void setDmz(String dmz) {
		this.dmz = dmz;
	}
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
}
