package cn.tydic.mobile.pdarequery.tools;

import cn.tydic.mobile.pdarequery.entity.UserInfo;
import cn.tydic.mobile.pdarequery.nettools.Webservice;

/**
 * 本类中存放的均是全局静态变量，不要随意更改
 * 
 * @author king
 * 
 */
public class Constant {
	
	

	/**
	 * 日志打印开关 true 测试 false 发布
	 */
	static boolean log_flag = true;
	/**
	 * stream缓冲大小
	 */
	public static final int BUFFER_SIZE = 1024 * 16;

	/**
	 * 游戏下载缓冲大小
	 */
	public static final int DOWN_ARRAY_SIZE = 1024 * 32;

	public static Webservice webService;// 存储所有WebService的实体对象，

	public static String SERVER_ADD = "http://60.0.6.40:9080";//车管所的服务器IP
	//public static String SERVER_ADD = "http://192.168.0.43:8080";//公式服务器的IP

	public static UserInfo user;// 全局的用户
	
	//业务受理模块
	public static Integer RC_SCANNER=10;//请求扫描的条码
	//拍摄照片的请求requestCode  btn_carCodeNamePic btn_anotherPic
	public static Integer RC_CARCODENAME_PIC=11;//车辆识别代号照片
	public static Integer RC_CAR_PIC=12;///整车照片
	public static Integer RC_OTHER_PIC=13;//其他照片
	public static Integer RC_CUTPICTURE=15;	//剪切图片
	public static Integer CAMERA_WITH_DATA = 111;   //拍照
	public static Integer PHOTO_PICKED_WITH_DATA = 112;  //gallery
	public static String localImagePath="";//所有图片的文件夹
	public static Integer CUT_PICTURE = 115;   //剪切图片返回的结果
	
	//图片的缩放
	public static String LoadImage_Local="LOCAL";//加载本地的图片
	public static String LoadImage_Intenet="INTENET";//加载网络图片
	
	
}
