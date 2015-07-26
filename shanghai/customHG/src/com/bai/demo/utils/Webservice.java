package com.bai.demo.utils;

import java.io.IOException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import com.bai.demo.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.util.Log;

/***
 * 功能描述：连接服务器WebService的Soap协议
 * @author zhangyx
 *
 */
public class Webservice {
	
	/**
	 * 
	http://192.168.0.231:8080/GetExamDataService.svc?wsdl
	
	192.168.0.201:802
 	PadUserLogin----登陆方法
	账号test test 
	 * */

	/**
	 * 重点：服务器IP地址、路径、命名空间和方法
	 */
	private String NAMESPACE = "http://tempuri.org/";// 命名空间//http://tempuri.org/
	private String SERVER_ADD = "";// 服务器地址
	private String WEBSERVICE = "/GetExamDataService.svc?WSDL";// webService目录
	private String METHOD_NAME = "PadUserLogin";// 要调用的webService方法
	private String SOAP_ACTION = NAMESPACE + "IGetExamDataService/"+METHOD_NAME;

	private SoapPrimitive result;// 服务器返回结果
	private Context context;
	

	/**
	 * 无参数构造方法 默认调用本机 目录/mobile/Service1.asmx?WSDL 下的HelloWorld方法
	 */
	public Webservice() {
		super();
	}
	
	public Webservice(Context context) {
		super();
		this.context=context;
		//对服务器：命名空间、服务器地址（IP）、webService目录  （项目统一不变的赋值）
		this.SERVER_ADD=context.getString(R.string.REQ_IP);
	}

	/**
	 * 
	 * @param nAMESPACE
	 *            webservice命名空间 默认为 http://tempuri.org/
	 * @param sERVER_ADD
	 *            webservice IP地址 默认为 http://10.0.2.2
	 * @param wEBSERVICE
	 *            webservice 目录地址 默认为 /mobile/Service1.asmx?WSDL
	 * @param mETHOD_NAME
	 *            webservice 方法名 默认为 HelloWorld
	 */
	public Webservice(String nAMESPACE, String sERVER_ADD, String wEBSERVICE,
			String mETHOD_NAME) {
		super();
		NAMESPACE = nAMESPACE;
		SERVER_ADD = sERVER_ADD;
		WEBSERVICE = wEBSERVICE;
		METHOD_NAME = mETHOD_NAME;
	}

	/**
	 * 
	 * @return 连接成功返回true，否则为false
	 */
	public boolean connect() {
		return connect(null,null, null);
	}

	/**
	 * @param paramName
	 *            调用webservice传入的参数名称
	 * @param paramValue
	 *            调用webservice传入的参数值
	 * 
	 * @return 连接成功返回true，否则为false
	 */
	public boolean connect(Context context,String[] paramName, Object[] paramValue) {
		//测试 网络链接的问题
		if(!isHavingNetWork(context)){
			return false;
		}
		// step1 指定WebService的命名空间和调用的方法名
		SoapObject soapObject = new SoapObject(NAMESPACE, METHOD_NAME);
		// step2 设置调用方法的参数值,这里的参数名称最好和和WebService一致
		if (paramName != null) {
			for (int i = 0; i < paramName.length; i++) {
				soapObject.addProperty(paramName[i], paramValue[i]);
			}
		}
		// step3 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
		// 创建Envelope对象用于封装整个回传的SOAP协议数据（Envelope是跟对象，解释为信封）
		// SoapEnvelope.VER11 使用SOAP1.1协议（前提是WebService提供商提供了这个版本的服务）
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		// 设置是否调用的是asp.net,dotNet下的WebService
		envelope.dotNet = true;
		// 必须，等价于envelope.bodyOut = soapObject;
		// 设置读取返回
		envelope.bodyOut = soapObject;
		// envelope.setOutputSoapObject(soapObject);

		// step4 创建HttpTransportSE对象
		// 创建WEBService远程访问对象(超时时间:5秒)
		// 网络连接通讯中，用户不能取消连接，只能通过设置超时时间来实现。
		HttpTransportSE ht = new HttpTransportSE(SERVER_ADD+ WEBSERVICE, 8000);
		ht.debug = true;
		try {
			// step5 调用WebService
			ht.call(SOAP_ACTION, envelope);
			// step6 使用getResponse方法获得WebService方法的返回结果
			if (envelope.getResponse() != null) {
				// 获取服务器返回的信息
				result = (SoapPrimitive) envelope.getResponse();
				return true;
			}
		} catch (IOException e) {
			Log.w("WebXMLService:disconnect@IOException",
					"WebXML Service dicconnected！");
			//e.printStackTrace();
			return false;
		} catch (XmlPullParserException e) {
			Log.w("WebXMLService:disconnect@XmlPullParserException",
					"WebXML Service soap fault!");
			//e.printStackTrace();
			return false;
		}
		return true;
	}

	public SoapPrimitive getResult() {
		return result;
	}
	
	

	/**
	 * 测试 网络
	 * @param context
	 * @return
	 */
	public static boolean isHavingNetWork(Context context){
		boolean flag=false;
		ConnectivityManager cm=(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni=cm.getActiveNetworkInfo();
		if(ni!=null){
			flag=true;
		}
		return flag;
	}
	
	public String getNAMESPACE() {
		return NAMESPACE;
	}

	public void setNAMESPACE(String nAMESPACE) {
		NAMESPACE = nAMESPACE;
	}

	public String getSERVER_ADD() {
		return SERVER_ADD;
	}

	public void setSERVER_ADD(String sERVER_ADD) {
		SERVER_ADD = sERVER_ADD;
	}

	public String getWEBSERVICE() {
		return WEBSERVICE;
	}

	public void setWEBSERVICE(String wEBSERVICE) {
		WEBSERVICE = wEBSERVICE;
	}

	public String getMETHOD_NAME() {
		return METHOD_NAME;
	}

	public void setMETHOD_NAME(String mETHOD_NAME) {
		METHOD_NAME = mETHOD_NAME;
	}

	public String getSOAP_ACTION() {
		return SOAP_ACTION;
	}

	public void setSOAP_ACTION(String sOAP_ACTION) {
		SOAP_ACTION = sOAP_ACTION;
	}
}
