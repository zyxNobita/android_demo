package com.bai.demo.onlyTest;

/***
 * 临时添加数据类:-----无服务器情况下
 * @author zhangyx
 *
 */
public class TestAddData {
	//待查验列表
	public static String str1="[{'ENTRY_ID':'328938922320','CHK_ER1':'java0'},{'ENTRY_ID':'328938922321','CHK_ER1':'java1'},{'ENTRY_ID':'328938922322','CHK_ER1':'java2'},{'ENTRY_ID':'328938922323','CHK_ER1':'java3'},{'ENTRY_ID':'328938922324','CHK_ER1':'java4'}]";
	
	//1-3 表头
	public static String str2="{'ENTRY_ID':'220120111010151633','TRADE_NAME':'上海威盛国际物流有限公司','TRAF_MODE':'水路','TRAF_NAME':'货船','BILL_NO':'32893892232','OWNER_NAME':'深圳市佳合顺进出口有限公司'}";
	
	//1-3表体
	public static String str3="[{'ENTRY_ID':'220120111010151633','G_NO':0.0,'CONTR_ITEM':0.0,'CODE_TS':'82159908310','G_NAME':'多功能开瓶器 BOTILE OPENER','G_MODEL':'开红酒瓶；开瓶器：钢铁，手柄：塑料；开瓶器；无 ','G_QTY':5000.0,'G_UNIT':'007','QTY_1':720.0,'UNIT_1':'035','QTY_2':0.0,'ORIGIN_COUNTRY':'318','DECL_PRICE':0.5,'DECL_TOTAL':3240.0,'TRADE_TOTAL':3240.0,'TRADE_CURR':'502','DUTY_MODE':'1','USE_TO':''},{'ENTRY_ID':'220120111010151633','G_NO':1.0,'CONTR_ITEM':0.0,'CODE_TS':'82159908311','G_NAME':'多功能开瓶器 BOTILE OPENER','G_MODEL':'开红酒瓶；开瓶器：钢铁，手柄：塑料；开瓶器；无 ','G_QTY':5000.0,'G_UNIT':'007','QTY_1':720.0,'UNIT_1':'035','QTY_2':0.0,'ORIGIN_COUNTRY':'318','DECL_PRICE':0.5,'DECL_TOTAL':3240.0,'TRADE_TOTAL':3240.0,'TRADE_CURR':'502','DUTY_MODE':'1','USE_TO':''},{'ENTRY_ID':'220120111010151633','G_NO':2.0,'CONTR_ITEM':0.0,'CODE_TS':'82159908312','G_NAME':'多功能开瓶器 BOTILE OPENER','G_MODEL':'开红酒瓶；开瓶器：钢铁，手柄：塑料；开瓶器；无 ','G_QTY':5000.0,'G_UNIT':'007','QTY_1':720.0,'UNIT_1':'035','QTY_2':0.0,'ORIGIN_COUNTRY':'318','DECL_PRICE':0.5,'DECL_TOTAL':3240.0,'TRADE_TOTAL':3240.0,'TRADE_CURR':'502','DUTY_MODE':'1','USE_TO':''},{'ENTRY_ID':'220120111010151633','G_NO':3.0,'CONTR_ITEM':0.0,'CODE_TS':'82159908313','G_NAME':'多功能开瓶器 BOTILE OPENER','G_MODEL':'开红酒瓶；开瓶器：钢铁，手柄：塑料；开瓶器；无 ','G_QTY':5000.0,'G_UNIT':'007','QTY_1':720.0,'UNIT_1':'035','QTY_2':0.0,'ORIGIN_COUNTRY':'318','DECL_PRICE':0.5,'DECL_TOTAL':3240.0,'TRADE_TOTAL':3240.0,'TRADE_CURR':'502','DUTY_MODE':'1','USE_TO':''},{'ENTRY_ID':'220120111010151633','G_NO':4.0,'CONTR_ITEM':0.0,'CODE_TS':'82159908314','G_NAME':'多功能开瓶器 BOTILE OPENER','G_MODEL':'开红酒瓶；开瓶器：钢铁，手柄：塑料；开瓶器；无 ','G_QTY':5000.0,'G_UNIT':'007','QTY_1':720.0,'UNIT_1':'035','QTY_2':0.0,'ORIGIN_COUNTRY':'318','DECL_PRICE':0.5,'DECL_TOTAL':3240.0,'TRADE_TOTAL':3240.0,'TRADE_CURR':'502','DUTY_MODE':'1','USE_TO':''}]";
	//1-4：审核信息列表数据
	public static String str4="[{'ENTRY_ID':'3749334233430','UPLOAD_ER':'224795','UPLOAD_TIME':'2013-01-01 12:23:00','SH_ER':'','SH_TIME':'2013-02-01 12:23:00','READ_ER':'','READ_TIME':'2013-02-11 12:23:00','OP_ID':'12','EXAM_ER':'','EXAM_PROC_CODE':'','STAR_NUM':3,'SH_ALARM':'','READ_ALARM':'0','FB_TIME':'2013-03-11 12:23:00','EXAM_PROC_IDEA':'3','EXAM_PROC_NAME':'','UPLOAD_ER_NAME':'adminUser','OP_ID_NAME':'','URL':'','HANDLE':'','CYCLYJ':'移动通过部门','RowNumber':'1'},{'ENTRY_ID':'3749334233431','UPLOAD_ER':'224795','UPLOAD_TIME':'2013-01-01 12:23:00','SH_ER':'','SH_TIME':'2013-02-01 12:23:00','READ_ER':'','READ_TIME':'2013-02-11 12:23:00','OP_ID':'12','EXAM_ER':'','EXAM_PROC_CODE':'','STAR_NUM':3,'SH_ALARM':'','READ_ALARM':'1','FB_TIME':'2013-03-11 12:23:00','EXAM_PROC_IDEA':'3','EXAM_PROC_NAME':'','UPLOAD_ER_NAME':'adminUser','OP_ID_NAME':'','URL':'','HANDLE':'','CYCLYJ':'移动通过部门','RowNumber':'1'},{'ENTRY_ID':'3749334233432','UPLOAD_ER':'224795','UPLOAD_TIME':'2013-01-01 12:23:00','SH_ER':'','SH_TIME':'2013-02-01 12:23:00','READ_ER':'','READ_TIME':'2013-02-11 12:23:00','OP_ID':'12','EXAM_ER':'','EXAM_PROC_CODE':'','STAR_NUM':3,'SH_ALARM':'','READ_ALARM':'2','FB_TIME':'2013-03-11 12:23:00','EXAM_PROC_IDEA':'3','EXAM_PROC_NAME':'','UPLOAD_ER_NAME':'adminUser','OP_ID_NAME':'','URL':'','HANDLE':'','CYCLYJ':'移动通过部门','RowNumber':'1'},{'ENTRY_ID':'3749334233433','UPLOAD_ER':'224795','UPLOAD_TIME':'2013-01-01 12:23:00','SH_ER':'','SH_TIME':'2013-02-01 12:23:00','READ_ER':'','READ_TIME':'2013-02-11 12:23:00','OP_ID':'12','EXAM_ER':'','EXAM_PROC_CODE':'','STAR_NUM':3,'SH_ALARM':'','READ_ALARM':'0','FB_TIME':'2013-03-11 12:23:00','EXAM_PROC_IDEA':'3','EXAM_PROC_NAME':'','UPLOAD_ER_NAME':'adminUser','OP_ID_NAME':'','URL':'','HANDLE':'','CYCLYJ':'移动通过部门','RowNumber':'1'},{'ENTRY_ID':'3749334233434','UPLOAD_ER':'224795','UPLOAD_TIME':'2013-01-01 12:23:00','SH_ER':'','SH_TIME':'2013-02-01 12:23:00','READ_ER':'','READ_TIME':'2013-02-11 12:23:00','OP_ID':'12','EXAM_ER':'','EXAM_PROC_CODE':'','STAR_NUM':3,'SH_ALARM':'','READ_ALARM':'1','FB_TIME':'2013-03-11 12:23:00','EXAM_PROC_IDEA':'3','EXAM_PROC_NAME':'','UPLOAD_ER_NAME':'adminUser','OP_ID_NAME':'','URL':'','HANDLE':'','CYCLYJ':'移动通过部门','RowNumber':'1'},{'ENTRY_ID':'3749334233435','UPLOAD_ER':'224795','UPLOAD_TIME':'2013-01-01 12:23:00','SH_ER':'','SH_TIME':'2013-02-01 12:23:00','READ_ER':'','READ_TIME':'2013-02-11 12:23:00','OP_ID':'12','EXAM_ER':'','EXAM_PROC_CODE':'','STAR_NUM':3,'SH_ALARM':'','READ_ALARM':'2','FB_TIME':'2013-03-11 12:23:00','EXAM_PROC_IDEA':'3','EXAM_PROC_NAME':'','UPLOAD_ER_NAME':'adminUser','OP_ID_NAME':'','URL':'','HANDLE':'','CYCLYJ':'移动通过部门','RowNumber':'1'},{'ENTRY_ID':'3749334233436','UPLOAD_ER':'224795','UPLOAD_TIME':'2013-01-01 12:23:00','SH_ER':'','SH_TIME':'2013-02-01 12:23:00','READ_ER':'','READ_TIME':'2013-02-11 12:23:00','OP_ID':'12','EXAM_ER':'','EXAM_PROC_CODE':'','STAR_NUM':3,'SH_ALARM':'','READ_ALARM':'0','FB_TIME':'2013-03-11 12:23:00','EXAM_PROC_IDEA':'3','EXAM_PROC_NAME':'','UPLOAD_ER_NAME':'adminUser','OP_ID_NAME':'','URL':'','HANDLE':'','CYCLYJ':'移动通过部门','RowNumber':'1'},{'ENTRY_ID':'3749334233437','UPLOAD_ER':'224795','UPLOAD_TIME':'2013-01-01 12:23:00','SH_ER':'','SH_TIME':'2013-02-01 12:23:00','READ_ER':'','READ_TIME':'2013-02-11 12:23:00','OP_ID':'12','EXAM_ER':'','EXAM_PROC_CODE':'','STAR_NUM':3,'SH_ALARM':'','READ_ALARM':'1','FB_TIME':'2013-03-11 12:23:00','EXAM_PROC_IDEA':'3','EXAM_PROC_NAME':'','UPLOAD_ER_NAME':'adminUser','OP_ID_NAME':'','URL':'','HANDLE':'','CYCLYJ':'移动通过部门','RowNumber':'1'}]";
}