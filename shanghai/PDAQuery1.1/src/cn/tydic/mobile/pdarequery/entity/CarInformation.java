package cn.tydic.mobile.pdarequery.entity;

import java.io.Serializable;

/***
 * 车辆信息查询：---车辆信息
 * @author zhangyx
 *
 */
@SuppressWarnings("serial")
public class CarInformation implements Serializable{
	  private String hpzl;		//号牌种类
	  private String hphm;		//号牌号码
	  private String clpp1;		//中文品牌
	  private String clxh;		//车辆型号
	  private String clpp2;		//英文品牌
	  private String gcjk;		//国产/进口
	  private String csys;		//车身颜色
	  private String syxz;		//所用性质
	  private String sfzmhm;	//身份证明号码
	  private String sfzmmc;	//身份证明名称
	  private String syr;		//所有人
	  private String ccdjrq;	//初次登记日期
	  private String yxqz;		//检验有效期至
	  private String qzbfqz;	//强制报废期至
	  private String xzqh;		//管理辖区
	  private String zt;		//机动车状态
	  private String dybj;		//是否抵押
	  private String clyt;		//车辆用途
	  private String zzg;		//制造国
	  private String clsbdh;	//车辆识别代号
	  private String fdjh;		//发动机号
	  private String cllx;		//车辆类型
	  private String fdjxh;		//发动机型号
	  private String rlzl;		//燃料种类
	  private String pl;		//排量
	  private String gl;		//功率
	  private String zxxs;		//转向形式
	  private String cwkc;		//车外廊长
	  private String cwkk;		//车外廊宽
	  private String cwkg;		//车外廓高
	  private String hxnbcd;	//货箱内部长度
	  private String hxnbkd;	//货箱内部宽度
	  private String hxnbgd;	//货箱内部高度
	  private String gbthps;	//钢板弹簧片数
	  private String zs;		//轴数
	  private String zj;		//轴距
	  private String qlj;		//前轮距
	  private String hlj;		//后轮距
	  private String ltgg;		//轮胎规格
	  private String lts;		//轮胎数
	  private String zzl;		//总质量
	  private String zbzl;		//整备质量
	  private String hdzzl;		//核定载质量
	  private String hdzk;		//核定载客
	  private String zqyzl;		//准牵引总质量
	  private String qpzk;		//驾驶室前排载客人数
	  private String hpzk;		//驾驶室后排载客人数
	  private String ccrq;		//出厂日期
	  private String dphgzbh;	//底盘合格证编号
	public String getHpzl() {
		return hpzl;
	}
	public void setHpzl(String hpzl) {
		this.hpzl = hpzl;
	}
	public String getHphm() {
		return hphm;
	}
	public void setHphm(String hphm) {
		this.hphm = hphm;
	}
	public String getClpp1() {
		return clpp1;
	}
	public void setClpp1(String clpp1) {
		this.clpp1 = clpp1;
	}
	public String getClxh() {
		return clxh;
	}
	public void setClxh(String clxh) {
		this.clxh = clxh;
	}
	public String getClpp2() {
		return clpp2;
	}
	public void setClpp2(String clpp2) {
		this.clpp2 = clpp2;
	}
	public String getGcjk() {
		return gcjk;
	}
	public void setGcjk(String gcjk) {
		this.gcjk = gcjk;
	}
	public String getCsys() {
		return csys;
	}
	public void setCsys(String csys) {
		this.csys = csys;
	}
	public String getSyxz() {
		return syxz;
	}
	public void setSyxz(String syxz) {
		this.syxz = syxz;
	}
	public String getSfzmhm() {
		return sfzmhm;
	}
	public void setSfzmhm(String sfzmhm) {
		this.sfzmhm = sfzmhm;
	}
	public String getSfzmmc() {
		return sfzmmc;
	}
	public void setSfzmmc(String sfzmmc) {
		this.sfzmmc = sfzmmc;
	}
	public String getSyr() {
		return syr;
	}
	public void setSyr(String syr) {
		this.syr = syr;
	}
	public String getCcdjrq() {
		return ccdjrq;
	}
	public void setCcdjrq(String ccdjrq) {
		this.ccdjrq = ccdjrq;
	}
	public String getYxqz() {
		return yxqz;
	}
	public void setYxqz(String yxqz) {
		this.yxqz = yxqz;
	}
	public String getQzbfqz() {
		return qzbfqz;
	}
	public void setQzbfqz(String qzbfqz) {
		this.qzbfqz = qzbfqz;
	}
	public String getXzqh() {
		return xzqh;
	}
	public void setXzqh(String xzqh) {
		this.xzqh = xzqh;
	}
	public String getZt() {
		return zt;
	}
	public void setZt(String zt) {
		this.zt = zt;
	}
	public String getDybj() {
		return dybj;
	}
	public void setDybj(String dybj) {
		this.dybj = dybj;
	}
	public String getClyt() {
		return clyt;
	}
	public void setClyt(String clyt) {
		this.clyt = clyt;
	}
	public String getZzg() {
		return zzg;
	}
	public void setZzg(String zzg) {
		this.zzg = zzg;
	}
	public String getClsbdh() {
		return clsbdh;
	}
	public void setClsbdh(String clsbdh) {
		this.clsbdh = clsbdh;
	}
	public String getFdjh() {
		return fdjh;
	}
	public void setFdjh(String fdjh) {
		this.fdjh = fdjh;
	}
	public String getCllx() {
		return cllx;
	}
	public void setCllx(String cllx) {
		this.cllx = cllx;
	}
	public String getFdjxh() {
		return fdjxh;
	}
	public void setFdjxh(String fdjxh) {
		this.fdjxh = fdjxh;
	}
	public String getRlzl() {
		return rlzl;
	}
	public void setRlzl(String rlzl) {
		this.rlzl = rlzl;
	}
	public String getPl() {
		return pl;
	}
	public void setPl(String pl) {
		this.pl = pl;
	}
	public String getGl() {
		return gl;
	}
	public void setGl(String gl) {
		this.gl = gl;
	}
	public String getZxxs() {
		return zxxs;
	}
	public void setZxxs(String zxxs) {
		this.zxxs = zxxs;
	}
	public String getCwkc() {
		return cwkc;
	}
	public void setCwkc(String cwkc) {
		this.cwkc = cwkc;
	}
	public String getCwkk() {
		return cwkk;
	}
	public void setCwkk(String cwkk) {
		this.cwkk = cwkk;
	}
	public String getCwkg() {
		return cwkg;
	}
	public void setCwkg(String cwkg) {
		this.cwkg = cwkg;
	}
	public String getHxnbcd() {
		return hxnbcd;
	}
	public void setHxnbcd(String hxnbcd) {
		this.hxnbcd = hxnbcd;
	}
	public String getHxnbkd() {
		return hxnbkd;
	}
	public void setHxnbkd(String hxnbkd) {
		this.hxnbkd = hxnbkd;
	}
	public String getHxnbgd() {
		return hxnbgd;
	}
	public void setHxnbgd(String hxnbgd) {
		this.hxnbgd = hxnbgd;
	}
	public String getGbthps() {
		return gbthps;
	}
	public void setGbthps(String gbthps) {
		this.gbthps = gbthps;
	}
	public String getZs() {
		return zs;
	}
	public void setZs(String zs) {
		this.zs = zs;
	}
	public String getZj() {
		return zj;
	}
	public void setZj(String zj) {
		this.zj = zj;
	}
	public String getQlj() {
		return qlj;
	}
	public void setQlj(String qlj) {
		this.qlj = qlj;
	}
	public String getHlj() {
		return hlj;
	}
	public void setHlj(String hlj) {
		this.hlj = hlj;
	}
	public String getLtgg() {
		return ltgg;
	}
	public void setLtgg(String ltgg) {
		this.ltgg = ltgg;
	}
	public String getLts() {
		return lts;
	}
	public void setLts(String lts) {
		this.lts = lts;
	}
	public String getZzl() {
		return zzl;
	}
	public void setZzl(String zzl) {
		this.zzl = zzl;
	}
	public String getZbzl() {
		return zbzl;
	}
	public void setZbzl(String zbzl) {
		this.zbzl = zbzl;
	}
	public String getHdzzl() {
		return hdzzl;
	}
	public void setHdzzl(String hdzzl) {
		this.hdzzl = hdzzl;
	}
	public String getHdzk() {
		return hdzk;
	}
	public void setHdzk(String hdzk) {
		this.hdzk = hdzk;
	}
	public String getZqyzl() {
		return zqyzl;
	}
	public void setZqyzl(String zqyzl) {
		this.zqyzl = zqyzl;
	}
	public String getQpzk() {
		return qpzk;
	}
	public void setQpzk(String qpzk) {
		this.qpzk = qpzk;
	}
	public String getHpzk() {
		return hpzk;
	}
	public void setHpzk(String hpzk) {
		this.hpzk = hpzk;
	}
	public String getCcrq() {
		return ccrq;
	}
	public void setCcrq(String ccrq) {
		this.ccrq = ccrq;
	}
	public String getDphgzbh() {
		return dphgzbh;
	}
	public void setDphgzbh(String dphgzbh) {
		this.dphgzbh = dphgzbh;
	}
}
