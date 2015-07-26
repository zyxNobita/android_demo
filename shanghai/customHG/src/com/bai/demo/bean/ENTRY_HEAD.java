package com.bai.demo.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * CHK_ENTRY、RiskH2000数据库-报关单表头
 * @author zhangyx
 *
 */
public class ENTRY_HEAD implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String ENTRY_ID;// 报关单号
	private String PRE_ENTRY_ID;// 预录入编号
	private String I_E_PORT;// 进出口岸编码
	private String I_E_PORT_NAME;// 进出口岸名称
	private String MANUAL_NO;// 备案号
	private String I_E_DATE;// 进（出）口日期
	private String D_DATE;// 申报日期
    private String TRADE_CO;// 经营单位编号
    private String TRADE_NAME;// 经营单位名称
    private String TRAF_MODE;// 运输方式
    private String TRAF_SPEC;// 
    private String TRAF_NAME;// 运输工具名称
    private String VOYAGE_NO;// 运输工具航次（班）号
    private String BILL_NO;// 提运单号
    private String OWNER_CODE;// 收货单位编号
    private String OWNER_NAME;// 收货单位名称
    private String TRADE_MODE;// 贸易方式
    private String ABBR_TRADE;//
    private String CUT_MODE;// 征免性质
    private String ABBR_CUT;//
    private String LICENSE_NO;// 许可证号
    private String TRADE_COUNTRY;// 起始地
    private String COUN_C_NAME; // 抵达地
    private String DESTINATION_PORT;// 装卸港
    private String PORT_C_NAME;// 装卸港
    private String DISTRICT_CODE;// 货主地区编号
    private String DISTRICT_NAME;// 货主地区名称
    private String APPR_NO;// 批文证号
    private String TRANS_MODE;// 成交方式
    private String TRANS_SPEC;// 
    private String FEE_MARK;// 运费
    private String FEE_CURR;//
    private String FEE_CURR_NAME;//
    private String FEE_RATE;//
    private String INSUR_MARK;// 保费
    private String INSUR_CURR;//
    private String INSUR_CURR_NAME;//
    private String INSUR_RATE;// 杂费
    private String OTHER_MARK;//
    private String OTHER_CURR;//
    private String OTHER_CURR_NAME;//
    private double OTHER_RATE;//
    private String CONTR_NO;// 合同协议号
    private double PACK_NO;// 件数
    private String WRAP_TYPE;// 包装类型
    private String WRAP_NAME;// 包装名称
    private double GROSS_WT;// 毛重
    private double NET_WT;// 净重
    private String ENTRY_CONTAINER;// 集装箱号
    private String ENTRY_CERTIFICATE;// 随附单据
    private String BONDED_NO;// 监管仓库号
    private String RELATIVE_ID;// 关联报关单号
    private String RELATIVE_MANUAL_NO;// 关联备案号
    private String ENTRY_TYPE;// 报关单类型
    private String NOTE_S;// 备注
    private String AGENT_CODE;// 申报单位编码
    private String AGENT_NAME;// 申报单位名称
	public String getENTRY_ID() {
		return ENTRY_ID;
	}
	public void setENTRY_ID(String eNTRY_ID) {
		ENTRY_ID = eNTRY_ID;
	}
	public String getPRE_ENTRY_ID() {
		return PRE_ENTRY_ID;
	}
	public void setPRE_ENTRY_ID(String pRE_ENTRY_ID) {
		PRE_ENTRY_ID = pRE_ENTRY_ID;
	}
	public String getI_E_PORT() {
		return I_E_PORT;
	}
	public void setI_E_PORT(String i_E_PORT) {
		I_E_PORT = i_E_PORT;
	}
	public String getI_E_PORT_NAME() {
		return I_E_PORT_NAME;
	}
	public void setI_E_PORT_NAME(String i_E_PORT_NAME) {
		I_E_PORT_NAME = i_E_PORT_NAME;
	}
	public String getMANUAL_NO() {
		return MANUAL_NO;
	}
	public void setMANUAL_NO(String mANUAL_NO) {
		MANUAL_NO = mANUAL_NO;
	}
	
	public String getI_E_DATE() {
		return I_E_DATE;
	}
	public void setI_E_DATE(String i_E_DATE) {
		I_E_DATE = i_E_DATE;
	}
	public String getD_DATE() {
		return D_DATE;
	}
	public void setD_DATE(String d_DATE) {
		D_DATE = d_DATE;
	}
	public String getTRADE_CO() {
		return TRADE_CO;
	}
	public void setTRADE_CO(String tRADE_CO) {
		TRADE_CO = tRADE_CO;
	}
	public String getTRADE_NAME() {
		return TRADE_NAME;
	}
	public void setTRADE_NAME(String tRADE_NAME) {
		TRADE_NAME = tRADE_NAME;
	}
	public String getTRAF_MODE() {
		return TRAF_MODE;
	}
	public void setTRAF_MODE(String tRAF_MODE) {
		TRAF_MODE = tRAF_MODE;
	}
	public String getTRAF_SPEC() {
		return TRAF_SPEC;
	}
	public void setTRAF_SPEC(String tRAF_SPEC) {
		TRAF_SPEC = tRAF_SPEC;
	}
	public String getTRAF_NAME() {
		return TRAF_NAME;
	}
	public void setTRAF_NAME(String tRAF_NAME) {
		TRAF_NAME = tRAF_NAME;
	}
	public String getVOYAGE_NO() {
		return VOYAGE_NO;
	}
	public void setVOYAGE_NO(String vOYAGE_NO) {
		VOYAGE_NO = vOYAGE_NO;
	}
	public String getBILL_NO() {
		return BILL_NO;
	}
	public void setBILL_NO(String bILL_NO) {
		BILL_NO = bILL_NO;
	}
	public String getOWNER_CODE() {
		return OWNER_CODE;
	}
	public void setOWNER_CODE(String oWNER_CODE) {
		OWNER_CODE = oWNER_CODE;
	}
	public String getOWNER_NAME() {
		return OWNER_NAME;
	}
	public void setOWNER_NAME(String oWNER_NAME) {
		OWNER_NAME = oWNER_NAME;
	}
	public String getTRADE_MODE() {
		return TRADE_MODE;
	}
	public void setTRADE_MODE(String tRADE_MODE) {
		TRADE_MODE = tRADE_MODE;
	}
	public String getABBR_TRADE() {
		return ABBR_TRADE;
	}
	public void setABBR_TRADE(String aBBR_TRADE) {
		ABBR_TRADE = aBBR_TRADE;
	}
	public String getCUT_MODE() {
		return CUT_MODE;
	}
	public void setCUT_MODE(String cUT_MODE) {
		CUT_MODE = cUT_MODE;
	}
	public String getABBR_CUT() {
		return ABBR_CUT;
	}
	public void setABBR_CUT(String aBBR_CUT) {
		ABBR_CUT = aBBR_CUT;
	}
	public String getLICENSE_NO() {
		return LICENSE_NO;
	}
	public void setLICENSE_NO(String lICENSE_NO) {
		LICENSE_NO = lICENSE_NO;
	}
	public String getTRADE_COUNTRY() {
		return TRADE_COUNTRY;
	}
	public void setTRADE_COUNTRY(String tRADE_COUNTRY) {
		TRADE_COUNTRY = tRADE_COUNTRY;
	}
	public String getCOUN_C_NAME() {
		return COUN_C_NAME;
	}
	public void setCOUN_C_NAME(String cOUN_C_NAME) {
		COUN_C_NAME = cOUN_C_NAME;
	}
	public String getDESTINATION_PORT() {
		return DESTINATION_PORT;
	}
	public void setDESTINATION_PORT(String dESTINATION_PORT) {
		DESTINATION_PORT = dESTINATION_PORT;
	}
	public String getPORT_C_NAME() {
		return PORT_C_NAME;
	}
	public void setPORT_C_NAME(String pORT_C_NAME) {
		PORT_C_NAME = pORT_C_NAME;
	}
	public String getDISTRICT_CODE() {
		return DISTRICT_CODE;
	}
	public void setDISTRICT_CODE(String dISTRICT_CODE) {
		DISTRICT_CODE = dISTRICT_CODE;
	}
	public String getDISTRICT_NAME() {
		return DISTRICT_NAME;
	}
	public void setDISTRICT_NAME(String dISTRICT_NAME) {
		DISTRICT_NAME = dISTRICT_NAME;
	}
	public String getAPPR_NO() {
		return APPR_NO;
	}
	public void setAPPR_NO(String aPPR_NO) {
		APPR_NO = aPPR_NO;
	}
	public String getTRANS_MODE() {
		return TRANS_MODE;
	}
	public void setTRANS_MODE(String tRANS_MODE) {
		TRANS_MODE = tRANS_MODE;
	}
	public String getTRANS_SPEC() {
		return TRANS_SPEC;
	}
	public void setTRANS_SPEC(String tRANS_SPEC) {
		TRANS_SPEC = tRANS_SPEC;
	}
	public String getFEE_MARK() {
		return FEE_MARK;
	}
	public void setFEE_MARK(String fEE_MARK) {
		FEE_MARK = fEE_MARK;
	}
	public String getFEE_CURR() {
		return FEE_CURR;
	}
	public void setFEE_CURR(String fEE_CURR) {
		FEE_CURR = fEE_CURR;
	}
	public String getFEE_CURR_NAME() {
		return FEE_CURR_NAME;
	}
	public void setFEE_CURR_NAME(String fEE_CURR_NAME) {
		FEE_CURR_NAME = fEE_CURR_NAME;
	}
	public String getFEE_RATE() {
		return FEE_RATE;
	}
	public void setFEE_RATE(String fEE_RATE) {
		FEE_RATE = fEE_RATE;
	}
	public String getINSUR_MARK() {
		return INSUR_MARK;
	}
	public void setINSUR_MARK(String iNSUR_MARK) {
		INSUR_MARK = iNSUR_MARK;
	}
	public String getINSUR_CURR() {
		return INSUR_CURR;
	}
	public void setINSUR_CURR(String iNSUR_CURR) {
		INSUR_CURR = iNSUR_CURR;
	}
	public String getINSUR_CURR_NAME() {
		return INSUR_CURR_NAME;
	}
	public void setINSUR_CURR_NAME(String iNSUR_CURR_NAME) {
		INSUR_CURR_NAME = iNSUR_CURR_NAME;
	}
	public String getINSUR_RATE() {
		return INSUR_RATE;
	}
	public void setINSUR_RATE(String iNSUR_RATE) {
		INSUR_RATE = iNSUR_RATE;
	}
	public String getOTHER_MARK() {
		return OTHER_MARK;
	}
	public void setOTHER_MARK(String oTHER_MARK) {
		OTHER_MARK = oTHER_MARK;
	}
	public String getOTHER_CURR() {
		return OTHER_CURR;
	}
	public void setOTHER_CURR(String oTHER_CURR) {
		OTHER_CURR = oTHER_CURR;
	}
	public String getOTHER_CURR_NAME() {
		return OTHER_CURR_NAME;
	}
	public void setOTHER_CURR_NAME(String oTHER_CURR_NAME) {
		OTHER_CURR_NAME = oTHER_CURR_NAME;
	}
	public double getOTHER_RATE() {
		return OTHER_RATE;
	}
	public void setOTHER_RATE(double oTHER_RATE) {
		OTHER_RATE = oTHER_RATE;
	}
	public String getCONTR_NO() {
		return CONTR_NO;
	}
	public void setCONTR_NO(String cONTR_NO) {
		CONTR_NO = cONTR_NO;
	}
	public double getPACK_NO() {
		return PACK_NO;
	}
	public void setPACK_NO(double pACK_NO) {
		PACK_NO = pACK_NO;
	}
	public String getWRAP_TYPE() {
		return WRAP_TYPE;
	}
	public void setWRAP_TYPE(String wRAP_TYPE) {
		WRAP_TYPE = wRAP_TYPE;
	}
	public String getWRAP_NAME() {
		return WRAP_NAME;
	}
	public void setWRAP_NAME(String wRAP_NAME) {
		WRAP_NAME = wRAP_NAME;
	}
	public double getGROSS_WT() {
		return GROSS_WT;
	}
	public void setGROSS_WT(double gROSS_WT) {
		GROSS_WT = gROSS_WT;
	}
	public double getNET_WT() {
		return NET_WT;
	}
	public void setNET_WT(double nET_WT) {
		NET_WT = nET_WT;
	}
	public String getENTRY_CONTAINER() {
		return ENTRY_CONTAINER;
	}
	public void setENTRY_CONTAINER(String eNTRY_CONTAINER) {
		ENTRY_CONTAINER = eNTRY_CONTAINER;
	}
	public String getENTRY_CERTIFICATE() {
		return ENTRY_CERTIFICATE;
	}
	public void setENTRY_CERTIFICATE(String eNTRY_CERTIFICATE) {
		ENTRY_CERTIFICATE = eNTRY_CERTIFICATE;
	}
	public String getBONDED_NO() {
		return BONDED_NO;
	}
	public void setBONDED_NO(String bONDED_NO) {
		BONDED_NO = bONDED_NO;
	}
	public String getRELATIVE_ID() {
		return RELATIVE_ID;
	}
	public void setRELATIVE_ID(String rELATIVE_ID) {
		RELATIVE_ID = rELATIVE_ID;
	}
	public String getRELATIVE_MANUAL_NO() {
		return RELATIVE_MANUAL_NO;
	}
	public void setRELATIVE_MANUAL_NO(String rELATIVE_MANUAL_NO) {
		RELATIVE_MANUAL_NO = rELATIVE_MANUAL_NO;
	}
	public String getENTRY_TYPE() {
		return ENTRY_TYPE;
	}
	public void setENTRY_TYPE(String eNTRY_TYPE) {
		ENTRY_TYPE = eNTRY_TYPE;
	}
	public String getNOTE_S() {
		return NOTE_S;
	}
	public void setNOTE_S(String nOTE_S) {
		NOTE_S = nOTE_S;
	}
	public String getAGENT_CODE() {
		return AGENT_CODE;
	}
	public void setAGENT_CODE(String aGENT_CODE) {
		AGENT_CODE = aGENT_CODE;
	}
	public String getAGENT_NAME() {
		return AGENT_NAME;
	}
	public void setAGENT_NAME(String aGENT_NAME) {
		AGENT_NAME = aGENT_NAME;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
