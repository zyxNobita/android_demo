package cn.tydic.mobile.pdarequery.entity;

import java.io.Serializable;

/**
 * 工作台账统计实体类
 * @author ouyyt
 *
 */
@SuppressWarnings("serial")
public class JobAccountingInfo implements Serializable {
	
	private String prefix;// 号牌种类
	private String ywl;// 业务量
	private String ywlx;// 业务类型
	private String ywmc;// 业务名称
	  
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public String getYwl() {
		return ywl;
	}
	public void setYwl(String ywl) {
		this.ywl = ywl;
	}
	public String getYwlx() {
		return ywlx;
	}
	public void setYwlx(String ywlx) {
		this.ywlx = ywlx;
	}
	public String getYwmc() {
		return ywmc;
	}
	public void setYwmc(String ywmc) {
		this.ywmc = ywmc;
	}
	  
}
