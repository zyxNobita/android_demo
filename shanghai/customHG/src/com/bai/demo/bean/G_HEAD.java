package com.bai.demo.bean;

import java.io.Serializable;
import java.util.Date;
/**
 * EXAM_ANALYSE_DB_T数据库-单证表头
 * @author zhangyx
 *
 */
@SuppressWarnings("serial")
public class G_HEAD implements Serializable{
	
	private String ENTRY_ID;// 报关单号
	private String UPLOAD_ER;// 上传人 
	private String UPLOAD_TIME;// 上传时间
	private String SH_ER;// 上传人
	private String SH_TIME;// 上传时间
	private String READ_ER;// 
    private String READ_TIME;// 
    private String OP_ID;// 状态（工作流类型）
    private String EXAM_ER;// 查验关员
    private String EXAM_PROC_CODE;// 查验处理结果
    private Integer STAR_NUM;// 优秀程度
    private String SH_ALARM;// 报警标志
    private String READ_ALARM;// 阅处报警处理标志
    private String FB_TIME;// 
    private String EXAM_PROC_IDEA;// 查验处理意见
    private String EXAM_PROC_NAME;
    private String UPLOAD_ER_NAME;
    private String OP_ID_NAME;
    private String URL;
    private String HANDLE;
    private String CYCLYJ;
    private String RowNumber;
    private String AlarmReason;//报警理由
    private String AuditReason;//发还重申理由
	public String getENTRY_ID() {
		return ENTRY_ID;
	}
	public void setENTRY_ID(String eNTRY_ID) {
		ENTRY_ID = eNTRY_ID;
	}
	public String getUPLOAD_ER() {
		return UPLOAD_ER;
	}
	public void setUPLOAD_ER(String uPLOAD_ER) {
		UPLOAD_ER = uPLOAD_ER;
	}

	public String getSH_ER() {
		return SH_ER;
	}
	public void setSH_ER(String sH_ER) {
		SH_ER = sH_ER;
	}
	
	public String getREAD_ER() {
		return READ_ER;
	}
	public void setREAD_ER(String rEAD_ER) {
		READ_ER = rEAD_ER;
	}
	
	public String getOP_ID() {
		return OP_ID;
	}
	public void setOP_ID(String oP_ID) {
		OP_ID = oP_ID;
	}
	public String getEXAM_ER() {
		return EXAM_ER;
	}
	public void setEXAM_ER(String eXAM_ER) {
		EXAM_ER = eXAM_ER;
	}
	public String getEXAM_PROC_CODE() {
		return EXAM_PROC_CODE;
	}
	public void setEXAM_PROC_CODE(String eXAM_PROC_CODE) {
		EXAM_PROC_CODE = eXAM_PROC_CODE;
	}
	public Integer getSTAR_NUM() {
		return STAR_NUM;
	}
	public void setSTAR_NUM(Integer sTAR_NUM) {
		STAR_NUM = sTAR_NUM;
	}
	public String getSH_ALARM() {
		return SH_ALARM;
	}
	public void setSH_ALARM(String sH_ALARM) {
		SH_ALARM = sH_ALARM;
	}
	public String getREAD_ALARM() {
		return READ_ALARM;
	}
	public void setREAD_ALARM(String rEAD_ALARM) {
		READ_ALARM = rEAD_ALARM;
	}
	
	public String getUPLOAD_TIME() {
		return UPLOAD_TIME;
	}
	public void setUPLOAD_TIME(String uPLOAD_TIME) {
		UPLOAD_TIME = uPLOAD_TIME;
	}
	public String getSH_TIME() {
		return SH_TIME;
	}
	public void setSH_TIME(String sH_TIME) {
		SH_TIME = sH_TIME;
	}
	public String getREAD_TIME() {
		return READ_TIME;
	}
	public void setREAD_TIME(String rEAD_TIME) {
		READ_TIME = rEAD_TIME;
	}
	public String getFB_TIME() {
		return FB_TIME;
	}
	public void setFB_TIME(String fB_TIME) {
		FB_TIME = fB_TIME;
	}
	public String getEXAM_PROC_IDEA() {
		return EXAM_PROC_IDEA;
	}
	public void setEXAM_PROC_IDEA(String eXAM_PROC_IDEA) {
		EXAM_PROC_IDEA = eXAM_PROC_IDEA;
	}
	public String getEXAM_PROC_NAME() {
		return EXAM_PROC_NAME;
	}
	public void setEXAM_PROC_NAME(String eXAM_PROC_NAME) {
		EXAM_PROC_NAME = eXAM_PROC_NAME;
	}
	public String getUPLOAD_ER_NAME() {
		return UPLOAD_ER_NAME;
	}
	public void setUPLOAD_ER_NAME(String uPLOAD_ER_NAME) {
		UPLOAD_ER_NAME = uPLOAD_ER_NAME;
	}
	public String getOP_ID_NAME() {
		return OP_ID_NAME;
	}
	public void setOP_ID_NAME(String oP_ID_NAME) {
		OP_ID_NAME = oP_ID_NAME;
	}
	public String getURL() {
		return URL;
	}
	public void setURL(String uRL) {
		URL = uRL;
	}
	public String getHANDLE() {
		return HANDLE;
	}
	public void setHANDLE(String hANDLE) {
		HANDLE = hANDLE;
	}
	public String getCYCLYJ() {
		return CYCLYJ;
	}
	public void setCYCLYJ(String cYCLYJ) {
		CYCLYJ = cYCLYJ;
	}
	public String getRowNumber() {
		return RowNumber;
	}
	public void setRowNumber(String rowNumber) {
		RowNumber = rowNumber;
	}
	public String getAlarmReason() {
		return AlarmReason;
	}
	public void setAlarmReason(String alarmReason) {
		AlarmReason = alarmReason;
	}
	public String getAuditReason() {
		return AuditReason;
	}
	public void setAuditReason(String auditReason) {
		AuditReason = auditReason;
	}
    
}
