package com.bai.demo.entity;

import com.bai.demo.utils.Webservice;

import android.view.Menu;

public class Constant {
	
	public static Webservice webservice;//所有请求页面的Webservice对象
	
	/**
	 * stream缓冲大小
	 */
	public static final int BUFFER_SIZE = 1024 * 16;

	/**
	 * 游戏下载缓冲大小
	 */
	public static final int DOWN_ARRAY_SIZE = 1024 * 32;
	
	public static final int GET_CODE = 0; // 相机扫描窗体间传值消息记号
	public static final int REQ_SCAN_FIRST=1;//第一次扫描请求
	public static final int REQ_SCAN_SECOND=2;//第二次扫描请求temporaryDate
	public static final int REQCAMES_FIRST=3;//集装箱体 拍照
	public static final int REQCAMES_SECOND=4;//货物堆放情况 拍照
	public static final int REQCAMES_THREE=5;//集装箱标志  拍照
	
	public static final int REQUPLOADCAMES_FIRST=134;//货物包装近照 拍照
	public static final int REQUPLOADCAMES_SECOND=135;//商品近照 拍照
	public static final int REQUPLOADCAMES_THIRD=136;//集装箱内景照  拍照
	public static final int REQUPLOADCAMES_FORTH=137;//商品规格型号照 拍照
	public static final int REQUPLOADCAMES_FIFTH=138;//其他特殊要求照 拍照
	public static final int REQUPLOADCAMES_SIXTH=139;//现场认定资料照  拍照
	public static final int REQUPLOADCAMES_SEVENTH=130;//视频 拍摄

	// 图片的类型
	public static final int IMAGETYPE_ONE=101;// 集装箱体照片
	public static final int IMAGETYPE_TWO=102;//
	public static final int IMAGETYPE_THREE=103;// 货物堆放情况照片
	public static final int IMAGETYPE_FOUR=401;// 集装箱封志照片
	public static final int REQ_LOACHOSTINAGE_RM1_G1=9*1000;//批量上传:本地上传的请求码--1-1--G1
	public static final int REQ_LOACHOSTINAGE_RM1_G2=9*1001;//批量上传:本地上传的请求码--1-1--G2
	public static final int REQ_LOACHOSTINAGE_RM1_G3=9*1002;//批量上传:本地上传的请求码--1-1--G3
	public static final int REQ_LOACHOSTINAGE_RM3_G1=8*1000;// 查验图像上传:表头本地上传的请求码--1-3--G1
	public static final int REQ_LOACHOSTINAGE_RM3_G2=8*1001;// 查验图像上传:表头本地上传的请求码--1-3--G2
	public static final int REQ_LOACHOSTINAGE_RM3_G3=8*1002;// 查验图像上传:表头本地上传的请求码--1-3--G3
	public static final int REQ_LOACHOSTINAGE_RM3_G4=8*1003;// 查验图像上传:表体本地上传的请求码--1-3--G4
	public static final int REQ_LOACHOSTINAGE_RM3_G5=8*1004;// 查验图像上传:表体本地上传的请求码--1-3--G5
	public static final int REQ_LOACHOSTINAGE_RM3_G6=8*1005;// 查验图像上传:表体本地上传的请求码--1-3--G6
	public static final int REQ_LOACHOSTINAGE_RM3_G7=8*1006;// 查验图像上传:表体本地上传的请求码--1-3--G7
	public static final int REQ_LOACHOSTINAGE_RM3_G8=8*1007;// 查验图像上传:表体本地上传的请求码--1-3--G8
	public static final int REQ_LOACHOSTINAGE_RM3_G9=8*1008;// 查验图像上传:表体本地上传的请求码--1-3--G9
	public final static String G_NO_HEAD="0";// 表头项号
	public final static String SAVEPATH="/sdcard/HG/";// 前部分的路径
	public final static String SAVE_FORMHEADIMAGEUIR="/0/";// 保存表头图片的路径
	public final static String SAVE_IMAGEUIR="/images/";// 保存图片的地址
	public final static String FORM_HEAD = "0";// 表头 照片
	public final static String CONTAINER_BODY="101";//集装箱箱体  照片
	public final static String GOODSTACK_SITUATION="103";//货物堆放情况  照片
	public final static String CONTAINER_MARK="401";// 集装箱标志    照片
	public final static String GOODS_PACKAGE="104";// 货物包装   照片
	public final static String GOODS="106";// 商品    照片
	public final static String CONTAINER_INNER="102";// 集装箱内景    照片
	public final static String GOODS_SPECIFICATION_MODEL="105";// 商品规格型号    照片
	public final static String ANOTHER_SPECIAL_REQUEST="107";// 其他特殊要求    照片
	public final static String SCENE_SRC="301";// 现场认定资料    照片
	public final static String VIDEO="201";// 视频
	public final static String SAVE_VIDEOUIR="/video/";//保存视频的地址
	
	public final static String DISPLAY_SAVAPATH="/sdcard/CheckPicRecord/";// 存储显示图片的前部分路径
	
	public final static String CONCRETE_DATATIME="yyyy-MM-dd_HH:mm:ss";//具体yyyyMMdd_hhmmss时间
	public final static String CONCRETE_DATA="yyyy-MM-dd";
	public final static String VAGUE_DATATIME="yyyyMMdd";//具体yyyyMMdd时间
	//暂存报关单在数据库的状态
	public final static int NO_UPLOADED=1;//暂存状态的报关单
	public final static int UPLOADED=2;//以上传完的报关单
	
	public final static String PAGESIZE="5";//分页的条数
	public final static int LOGIN_SUCCESS = 0x00;//登录成功
	public final static int LOGIN_FAIL1 = 1;//登录失败1
	public final static int LOGIN_FAIL2 = 2;//登录失败2
	public final static int FLIP_DISTANCE = 50;// 定义手势动作两点之间的最小距离
	//查验图像上传
	public final static int RM1I3_TAKEPHONE_FIEST=131;//查验图像上传的   集装箱箱体拍照 请求RequestCode
	public final static int RM1I3_TAKEPHONE_TWO=132;//查验图像上传的   货物堆放情况拍照 请求RequestCode
	public final static int RM1I3_TAKEPHONE_THREE=133;//查验图像上传的   集装箱标志拍照 请求RequestCode
	//点去按钮  通过 Handler 去改变 UI 页面，需求到达的菜单View
	public final static int RINGHT_GROUP1_MENU1=11;//第一个模块的第一个菜单
	public final static int RINGHT_GROUP1_MENU2=12;
	public final static int RINGHT_GROUP1_MENU3=13;
	public final static int RINGHT_GROUP1_MENU4=14;
	public final static int RINGHT_GROUP1_MENU4_CHECK=15;
	public final static int RINGHT_GROUP1_MENU4_CHECKALARM=16;
	public final static int RINGHT_GROUP2_MENU1=21;//第二个模块的第一个菜单
	public final static int RINGHT_GROUP2_MENU2=22;
	public final static int RINGHT_GROUP2_MENU3=23;
	public final static int RINGHT_GROUP3_MENU1=31;//第三个模块的第一个菜单
	public final static int RINGHT_GROUP3_MENU2=32;
	public final static int RINGHT_GROUP3_MENU3=33;
	public final static int RINGHT_GROUP3_MENU4=34;
	public final static String LOGIN_RESULTFAIL="9";//返回结果的9为失败
	
	// 数据库（SQLite）的字段数据
	public static final String DATABASE_NAME = "sec_db";
	public static final  int DATABASE_VERSION = 1;
	/*
	 * 报关单表所有字段
	 * */
	public static final String Entry_Head = "Entry_Head";
	public static final String Entry_ID = "Entry_ID";
	public static final String State = "State";
	public static final String Save_Time = "Save_Time";
	public static final String Upload_Time = "Upload_Time";
	
	/*
	 * 报关单表体表所有字段
	 * */
	public static final String Entry_List = "Entry_List";
	public static final String G_No = "G_No";
	public static final String CODE_TS = "CODE_TS";
	public static final String G_NAME = "G_NAME";
	public static final String isYs = "isYs";
	public static final String Remark ="Remark";
	
	/*
	 * 附件表所有字段
	 * */
	public static final String Photo_list = "Photo_list";
	public static final String Photo_list_Code ="Photo_list_Code";
	public static final String Photo_list_ID ="Photo_list_ID";
	public static final int CB_NOCHECKALL=111;//去掉全选
	
	
	public static final int NOTITY_DATACHANGE=0000;//在Handler通知适配器数据改变
	
	public static final int RECORDER_VIDEO=11000;//视频刻录的 requestCoder
	//图片的上传下载
	//图片的缩放
	public static String LoadImage_Local="LOCAL";//加载本地的图片
	public static String LoadImage_Intenet="INTENET";//加载网络图片
	
	
	//一下常量不确定是否需要，但是上面的确定需要
	public static final int LOGINVIEW = 0;// 修改EditText的内容
	public static final int CHANGEVIEW = 1; // 切换view
	public static final int WEBSERVICE_OVER = 0;// 调用网络服务结束
	public static final int SQLITE_OVER = 0;// 调用数据库查询结束
	public static final int DATE_FROM = 0;// 设置起始时间dialog编号
	public static final int DATE_TO = 1;// 设置结束时间dialog编号
	public static final int NOTIFICATION_SEARCHAPPROVE = 0x0001;// 准予进港信息推送编号
	public static final int ACTIVITY_ASSISTANCE = 0x2000;// 辅助业务主界面
	public static final int ACTIVITY_LAWS = 0x2001;// 窗体切换法律法规界面
	public static final int ACTIVITY_GOODS = 0x2002;// 窗体切换商品归类界面
	public static final int ACTIVITY_TEL = 0x2003;// 窗体切换通讯录界面
	public static final int ACTIVITY_SETTING = 0x4000;// 系统设置主界面
	public static final int ACTIVITY_ABOUT = 0x4001;// 窗体切换关于界面
	public static final int ACTIVITY_VERSION = 0x4002;// 窗体切换版本界面
	public final static int CONTEXT_ITEM0 = Menu.FIRST;// context菜单选项
	public final static int CONTEXT_ITEM1 = Menu.FIRST + 1;
	public final static int CONTEXT_ITEM2 = Menu.FIRST + 2;
	
	/*
	 * 1未给此手机注册申请<br>
	 * 2密码不正确 <br>
	 * 3您的申请未提交<br>
	 * 4申请正在审核 请耐心等候 <br>
	 * 5审核未通过，请重新提交申请 <br>
	 * */
	public final static int CONNECT_FAIL = 0x10;//连接失败
	public final static int NO_USEFUL_VERSION = 0x03;//没有可用版本
	public final static int USEFUL_VERSION = 0x04;//有可用版本
	public final static int DOWNLOAD_FILE_LENGTH = 0x05;//文件大小
	public final static int DOWNLOAD_FILE_NOW = 0x06;//当前文件下载大小
	public final static int DOWNLOAD_FILE_OVER = 0x07;//文件下载完成
	public final static int DOWNLOAD_FILE_ERROR = 0x08;//文件下载中途出错
	public final static int SEARCH_1 = 1001;//文件下载中途出错
	public final static int SEARCH_2 = 1002;//文件下载中途出错
	public final static int SEARCH_3 = 1003;//文件下载中途出错
	public final static int SEARCH_4 = 1004;//文件下载中途出错
	public static final int CHANGE_EDIT2 = 1;
	public static final String IP_ADDRESS = "10.16.95.90";
	public static final int MAINVIEW = 2;
	public static final int KFVIEW = 3;
	public static final int RESOURCEDETAIL = 4;
	public static final int ALLRESOURCE = 5;
	public static final int CHANGE_EDIT6 = 6;
	public static final int MODIFYINFO = 7;
	public static final int USERINFO = 8;
	public static final int REGISTER = 9;
	public static final int CHANGE_EDIT7 = 10;
	public static final int MEETINTVIEW = 11;
	public static final int ADDLIST = 14;
	public static final int ORDER = 12;
	public static final int ORDERDETAIL = 13;
	public static final int DELETE = 16;

	public static final int DATE_INPUT_DIALOG = 0;
	public static final int DATE_INPUT_DIALOG_1 = 2;
	public static final int EXIT_DIALOG = 4;
	public static final int UP = 1;
	public static final int DOWN = -1;
	public static final int GOTOLOGIN = 15; // 进入登陆界面

	public static final int MAINLISTVIEW01 = 1;
	public static final int RESOURCEDIVIDEDBYGROUPLISTVIEW01 = 2;
	public static final int RESOURCEDIVIDEDBYGROUP1LISTVIEW01 = 3;
	public static final int ALLRESOURCELISTVIEW01 = 4;
	public static final int ORDERLISTVIEW01 = 5;

	public static final int HELP_GROUP = 20;
	public static final int GY_GROUP = 32;

	public static final int MENU_HELP = 21;
	public static final int MENU_GY = 33;
	
	public static final int CHECK_FAIL = 1;
	public static final int CHECK_SUCCESS = 2;
	public static final int CHECKALARM_FAIL = 3;
	public static final int CHECKALARM_SUCCESS = 4;
	public static final int WEBSERVICE_SUCCESS = 5;
	public static final int WEBSERVICE_FAIL = 6;
}
