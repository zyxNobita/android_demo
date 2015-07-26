package cn.tydic.mobile.pdarequery.entity;

import java.io.Serializable;

/***
 * 机动车底盘公告产品信息bean
 * @author zhangyx
 *
 */
@SuppressWarnings("serial")
public class ChassisInformation implements Serializable{

	private String bh;		//底盘公告编号
	private String dpid;	//底盘ID
	private String qyid;	//企业ID
	private String scdz;	//生产地址
	private String dpxh;	//底盘型号
	private String dplb;	//底盘类别
	private String cpmc;	//产品名称
	private String cpsb;	//产品商标
	private String c;		//外廓长
	private String k;		//外廓宽
	private String g;		//外廓高
	private String rlzl;	//燃料种类
	private String yjbz;	//依据标准
	private String zxxs;	//转向形式
	private String zs;		//轴数
	private String zj;		//轴距
	private String gbthps;	//钢板弹簧片数
	private String lts;		//轮胎数
	private String ltgg;	//轮胎规格
	private String qlj;		//前轮距
	private String hlj;		//后轮距
	private String zzl;		//总质量
	private String zbzl;	//整备质量
	private String zqyzl;	//准牵引总质量
	private String fdjxh;	//发动机型号
	private String pl;		//排量
	private String gl;		//功率
	private String sbdh;	//识别代号序列
	private String cslx;	//公告发布类型
	private String bz;		//备注
	private String zzcmc;	//车辆制造企业
	private String ggrq;	//公告发布日期
	private String cxsxrq;	//撤销生效日期
	private String qpzk;	//驾驶室前排人数
	private String hpzk;	//驾驶室后排人数
	private String ggsxrq;	//公告生效日期
	private String cxggrq;	//撤销公告发布日期
	private String tzscrq;	//停止生产日期
	private String fdjqy;	//发动机企业
	private String ywabs;	//是否带防抱死系统
	private String qydm;	//合格证编号前4位
	private String ggbj;	//公告标记
	public String getBh() {
		return bh;
	}
	public void setBh(String bh) {
		this.bh = bh;
	}
	public String getDpid() {
		return dpid;
	}
	public void setDpid(String dpid) {
		this.dpid = dpid;
	}
	public String getQyid() {
		return qyid;
	}
	public void setQyid(String qyid) {
		this.qyid = qyid;
	}
	public String getScdz() {
		return scdz;
	}
	public void setScdz(String scdz) {
		this.scdz = scdz;
	}
	public String getDpxh() {
		return dpxh;
	}
	public void setDpxh(String dpxh) {
		this.dpxh = dpxh;
	}
	public String getDplb() {
		return dplb;
	}
	public void setDplb(String dplb) {
		this.dplb = dplb;
	}
	public String getCpmc() {
		return cpmc;
	}
	public void setCpmc(String cpmc) {
		this.cpmc = cpmc;
	}
	public String getCpsb() {
		return cpsb;
	}
	public void setCpsb(String cpsb) {
		this.cpsb = cpsb;
	}
	public String getC() {
		return c;
	}
	public void setC(String c) {
		this.c = c;
	}
	public String getK() {
		return k;
	}
	public void setK(String k) {
		this.k = k;
	}
	public String getG() {
		return g;
	}
	public void setG(String g) {
		this.g = g;
	}
	public String getRlzl() {
		return rlzl;
	}
	public void setRlzl(String rlzl) {
		this.rlzl = rlzl;
	}
	public String getYjbz() {
		return yjbz;
	}
	public void setYjbz(String yjbz) {
		this.yjbz = yjbz;
	}
	public String getZxxs() {
		return zxxs;
	}
	public void setZxxs(String zxxs) {
		this.zxxs = zxxs;
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
	public String getGbthps() {
		return gbthps;
	}
	public void setGbthps(String gbthps) {
		this.gbthps = gbthps;
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
	public String getZqyzl() {
		return zqyzl;
	}
	public void setZqyzl(String zqyzl) {
		this.zqyzl = zqyzl;
	}
	public String getFdjxh() {
		return fdjxh;
	}
	public void setFdjxh(String fdjxh) {
		this.fdjxh = fdjxh;
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
	public String getSbdh() {
		return sbdh;
	}
	public void setSbdh(String sbdh) {
		this.sbdh = sbdh;
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
	public String getCxsxrq() {
		return cxsxrq;
	}
	public void setCxsxrq(String cxsxrq) {
		this.cxsxrq = cxsxrq;
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
	public String getFdjqy() {
		return fdjqy;
	}
	public void setFdjqy(String fdjqy) {
		this.fdjqy = fdjqy;
	}
	public String getYwabs() {
		return ywabs;
	}
	public void setYwabs(String ywabs) {
		this.ywabs = ywabs;
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
