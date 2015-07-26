package com.bai.demo.utils;

public class RequestPath {
	
	//"http://tempuri.org/";// 命名空间
	public static final String REQ_NAMESPACE="http://tempuri.org/";
	//SERVER_ADD = "http://192.168.0.47";// 服务器地址
	public static final String REQ_SERVERADD="http://192.168.0.47";
	//WEBSERVICE = "/ExamServices/GetExamDataService.svc?WSDL";// webService目录
	public static final String REQ_WEBSERVICE="/ExamServices/GetExamDataService.svc?WSDL";
	//统一的Action：命名空间+接口名称+方法名称
	public static final String REQ_ACTION="http://tempuri.org/IGetExamDataService/";
	
	//测试 数据 工号  
	public static final String TEST_JOBNUMBER="220018";
	
	//登陆	
	public static final String REQ_LOGINURL_METHOD="PadUserLogin";
	public static final String REQ_LOGIN_ACTION="http://tempuri.org/IGetExamDataService/PadUserLogin";
	//获取用户详细信息
	public static final String REQ_GETUSERINFO_METHOD="GetCurrentUserInfo";
	//获得用户角色
	public static final String REQ_GETROLES_METHOD="GetUserRolesCVSString";
	//获得用户权限
	public static final String REQ_GETPERMISSION_METHOD="GetPermissionsCVSString";
	
	
	
}
