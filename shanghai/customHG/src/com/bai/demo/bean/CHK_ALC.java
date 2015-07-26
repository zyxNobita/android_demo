package com.bai.demo.bean;

import java.io.Serializable;

/**
 * 派单表
 * @author zhangyx
 *
 */
public class CHK_ALC implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String ENTRY_ID;//报关单号
	private String ALC_DATE;//派单时间
	private String CHK_ER1;//查验人1
	private String CHK_ER2;//查验人2
	private String ALC_OP;
	private String MANUAL_FLAG;
	private String MANUAL_REASON;
	private String CHK_FINISHED;
	private String GROUND_FLAG;
	private String SPECIAL_FLAG;
	private String ON_WORK;
	private String ADD_MODE_FLAG;
	private String CHK_GROUP;
	private String OFFICE_NUM;
	private String CHK_CUSTOMS;
	public String getENTRY_ID() {
		return ENTRY_ID;
	}
	public void setENTRY_ID(String eNTRY_ID) {
		ENTRY_ID = eNTRY_ID;
	}

	public String getALC_DATE() {
		return ALC_DATE;
	}
	
	public void setALC_DATE(String aLC_DATE) {
		ALC_DATE = aLC_DATE;
	}

	public String getCHK_ER1() {
		return CHK_ER1;
	}
	public void setCHK_ER1(String cHK_ER1) {
		CHK_ER1 = cHK_ER1;
	}
	public String getCHK_ER2() {
		return CHK_ER2;
	}
	public void setCHK_ER2(String cHK_ER2) {
		CHK_ER2 = cHK_ER2;
	}
	public String getALC_OP() {
		return ALC_OP;
	}
	public void setALC_OP(String aLC_OP) {
		ALC_OP = aLC_OP;
	}
	public String getMANUAL_FLAG() {
		return MANUAL_FLAG;
	}
	public void setMANUAL_FLAG(String mANUAL_FLAG) {
		MANUAL_FLAG = mANUAL_FLAG;
	}
	public String getMANUAL_REASON() {
		return MANUAL_REASON;
	}
	public void setMANUAL_REASON(String mANUAL_REASON) {
		MANUAL_REASON = mANUAL_REASON;
	}
	public String getCHK_FINISHED() {
		return CHK_FINISHED;
	}
	public void setCHK_FINISHED(String cHK_FINISHED) {
		CHK_FINISHED = cHK_FINISHED;
	}
	public String getGROUND_FLAG() {
		return GROUND_FLAG;
	}
	public void setGROUND_FLAG(String gROUND_FLAG) {
		GROUND_FLAG = gROUND_FLAG;
	}
	public String getSPECIAL_FLAG() {
		return SPECIAL_FLAG;
	}
	public void setSPECIAL_FLAG(String sPECIAL_FLAG) {
		SPECIAL_FLAG = sPECIAL_FLAG;
	}
	public String getON_WORK() {
		return ON_WORK;
	}
	public void setON_WORK(String oN_WORK) {
		ON_WORK = oN_WORK;
	}
	public String getADD_MODE_FLAG() {
		return ADD_MODE_FLAG;
	}
	public void setADD_MODE_FLAG(String aDD_MODE_FLAG) {
		ADD_MODE_FLAG = aDD_MODE_FLAG;
	}
	public String getCHK_GROUP() {
		return CHK_GROUP;
	}
	public void setCHK_GROUP(String cHK_GROUP) {
		CHK_GROUP = cHK_GROUP;
	}
	public String getOFFICE_NUM() {
		return OFFICE_NUM;
	}
	public void setOFFICE_NUM(String oFFICE_NUM) {
		OFFICE_NUM = oFFICE_NUM;
	}
	public String getCHK_CUSTOMS() {
		return CHK_CUSTOMS;
	}
	public void setCHK_CUSTOMS(String cHK_CUSTOMS) {
		CHK_CUSTOMS = cHK_CUSTOMS;
	}

}
