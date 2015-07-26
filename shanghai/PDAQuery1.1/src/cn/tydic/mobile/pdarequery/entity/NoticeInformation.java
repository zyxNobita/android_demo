package cn.tydic.mobile.pdarequery.entity;

import java.io.Serializable;

/***
 * 机动车整车公告产品信息
 * @author zhangyx
 *
 */
@SuppressWarnings("serial")
public class NoticeInformation implements Serializable{

	  private String bh;  		//整车公告编号
	  private String clpp1;		//车辆品牌(中文)
	  private String clpp2;		//车辆品牌(英文)
	  private String clxh;		//车辆型号
	  private String fdjxh;		//发动机型号
	  private String sbdhxl;	//识别代号序列
	  private String cllx;		//车辆类型
	  private String zzg;		//制造国
	  private String zxxs;		//转向形式
	  private String rlzl;		//燃料种类
	  private String pl;		//排量
	  private String gl;		//功率
	  private String cwkc;		//车外廓长
	  private String cwkk;		//车外廓宽
	  private String cwkg;		//车外廓高
	  private String hxnbcd;	//货箱内部长度
	  private String hxnbkd;	//货箱内部宽度
	  private String hxnbgd;	//货箱内部高度
	  private String gbthps;	//钢板弹簧片数
	  private String zs;		//轴数
	  private String zj;		//轴距
	  private String qlj;		//前轮距
	  private String hlj;		//后轮距
	  private String lts;		//轮胎数
	  private String ltgg;		//轮胎规格
	  private String zzl;		//总数量
	  private String zbzl;		//整备质量
	  private String hdzzl;		//额定载质量
	  private String zqyzl;		//准牵引总质量
	  private String hdzk;		//额定载客
	  private String qpzk;		//驾驶室前排人数
	  private String hpzk;		//驾驶室后排人数
	  private String dpid;		//底盘ID
	  private String hbdbqk;	//环保达标情况
	  private String cslx;		//公告发布类型
	  private String bz;		//备注
	  private String zzcmc;		//车辆制造企业
	  private String ggrq;		//公告发布日期
	  private String sfmj;		//免检标记
	  private String cxsxrq;	//撤销生效日期
	  private String dpqyxh;	//底盘企业及型号
	  private String sfyxzc;	//是否允许注册
	  private String ggsxrq;	//公告生效日期
	  private String cxggrq;	//撤销公告发布日期
	  private String tzscrq;	//停止生产日期
	  private String mjyxqz;	//免检有效期止
	  private String fgbsxh;	//反光标识型号
	  private String fgbssb;	//反光标识商标
	  private String fgbsqy;	//反光标识企业
	  private String fdjqy;		//发动机企业
	  private String fdjsb;		//发动机商标
	  private String qydm;		//合格证编号前4位
	  private String ggbj;		//公告标记
	public String getBh() {
		return bh;
	}
	public void setBh(String bh) {
		this.bh = bh;
	}
	public String getClpp1() {
		return clpp1;
	}
	public void setClpp1(String clpp1) {
		this.clpp1 = clpp1;
	}
	public String getClpp2() {
		return clpp2;
	}
	public void setClpp2(String clpp2) {
		this.clpp2 = clpp2;
	}
	public String getClxh() {
		return clxh;
	}
	public void setClxh(String clxh) {
		this.clxh = clxh;
	}
	public String getFdjxh() {
		return fdjxh;
	}
	public void setFdjxh(String fdjxh) {
		this.fdjxh = fdjxh;
	}
	public String getSbdhxl() {
		return sbdhxl;
	}
	public void setSbdhxl(String sbdhxl) {
		this.sbdhxl = sbdhxl;
	}
	public String getCllx() {
		return cllx;
	}
	public void setCllx(String cllx) {
		this.cllx = cllx;
	}
	public String getZzg() {
		return zzg;
	}
	public void setZzg(String zzg) {
		this.zzg = zzg;
	}
	public String getZxxs() {
		return zxxs;
	}
	public void setZxxs(String zxxs) {
		this.zxxs = zxxs;
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
	public String getLts() {
		return lts;
	}
	public void setLts(String lts) {
		this.lts = lts;
	}
	public String getLtgg() {
		return ltgg;
	}
	public void setLtgg(String ltgg) {
		this.ltgg = ltgg;
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
	public String getZqyzl() {
		return zqyzl;
	}
	public void setZqyzl(String zqyzl) {
		this.zqyzl = zqyzl;
	}
	public String getHdzk() {
		return hdzk;
	}
	public void setHdzk(String hdzk) {
		this.hdzk = hdzk;
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
	public String getDpid() {
		return dpid;
	}
	public void setDpid(String dpid) {
		this.dpid = dpid;
	}
	public String getHbdbqk() {
		return hbdbqk;
	}
	public void setHbdbqk(String hbdbqk) {
		this.hbdbqk = hbdbqk;
	}
	public String getCslx() {
		return cslx;
	}
	public void setCslx(String cslx) {
		this.cslx = cslx;
	}
	public String getBz() {
		return bz;
	}
	public void setBz(String bz) {
		this.bz = bz;
	}
	public String getZzcmc() {
		return zzcmc;
	}
	public void setZzcmc(String zzcmc) {
		this.zzcmc = zzcmc;
	}
	public String getGgrq() {
		return ggrq;
	}
	public void setGgrq(String ggrq) {
		this.ggrq = ggrq;
	}
	public String getSfmj() {
		return sfmj;
	}
	public void setSfmj(String sfmj) {
		this.sfmj = sfmj;
	}
	public String getCxsxrq() {
		return cxsxrq;
	}
	public void setCxsxrq(String cxsxrq) {
		this.cxsxrq = cxsxrq;
	}
	public String getDpqyxh() {
		return dpqyxh;
	}
	public void setDpqyxh(String dpqyxh) {
		this.dpqyxh = dpqyxh;
	}
	public String getSfyxzc() {
		return sfyxzc;
	}
	public void setSfyxzc(String sfyxzc) {
		this.sfyxzc = sfyxzc;
	}
	public String getGgsxrq() {
		return ggsxrq;
	}
	public void setGgsxrq(String ggsxrq) {
		this.ggsxrq = ggsxrq;
	}
	public String getCxggrq() {
		return cxggrq;
	}
	public void setCxggrq(String cxggrq) {
		this.cxggrq = cxggrq;
	}
	public String getTzscrq() {
		return tzscrq;
	}
	public void setTzscrq(String tzscrq) {
		this.tzscrq = tzscrq;
	}
	public String getMjyxqz() {
		return mjyxqz;
	}
	public void setMjyxqz(String mjyxqz) {
		this.mjyxqz = mjyxqz;
	}
	public String getFgbsxh() {
		return fgbsxh;
	}
	public void setFgbsxh(String fgbsxh) {
		this.fgbsxh = fgbsxh;
	}
	public String getFgbssb() {
		return fgbssb;
	}
	public void setFgbssb(String fgbssb) {
		this.fgbssb = fgbssb;
	}
	public String getFgbsqy() {
		return fgbsqy;
	}
	public void setFgbsqy(String fgbsqy) {
		this.fgbsqy = fgbsqy;
	}
	public String getFdjqy() {
		return fdjqy;
	}
	public void setFdjqy(String fdjqy) {
		this.fdjqy = fdjqy;
	}
	public String getFdjsb() {
		return fdjsb;
	}
	public void setFdjsb(String fdjsb) {
		this.fdjsb = fdjsb;
	}
	public String getQydm() {
		return qydm;
	}
	public void setQydm(String qydm) {
		this.qydm = qydm;
	}
	public String getGgbj() {
		return ggbj;
	}
	public void setGgbj(String ggbj) {
		this.ggbj = ggbj;
	}
}
