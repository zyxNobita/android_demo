package cn.tydic.mobile.pdarequery.nettools;

import java.io.IOException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import cn.tydic.mobile.pdarequery.tools.Constant;

import android.util.Log;

/**
 * 
 * Description:调用接口服务 Webservice.java Create on 2013-7-8 下午2:18:08
 * 
 * 
 * @version 1.0 Copyright (c) 2013 Company,Inc. All Rights Reserved.
 */
public class Webservice {
	//  http://192.168.0.43:8080/zwapp/service/IServicePro?wsdl
	public String NAMESPACE = Constant.SERVER_ADD
			+ "/zwapp/service/IServicePro";// 命名空间
	//   http://192.168.0.43:8080
	public String SERVER_ADD = Constant.SERVER_ADD + "/";// 服务器地址
	public String WEBSERVICE = "zwapp/service/IServicePro";// webService目录

	public String METHOD_NAME = "iInterface";// 要调用的webService方法
	public String SOAP_ACTION = NAMESPACE + METHOD_NAME;

	public SoapPrimitive result;// 服务器返回结果
	private static Webservice webservice;

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

	/**
	 * 无参数构造方法 默认调用本机 目录/mobile/Service1.asmx?WSDL 下的HelloWorld方法
	 */
	public Webservice() {
		super();
	}

	// public static Webservice getInstance() {
	// if (webservice != null) {
	// return webservice;
	// }
	// webservice = new Webservice();
	// return webservice;
	// }

	/**
	 * @author bai
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
		return connect(null, null);
	}

	/**
	 * @param paramName
	 *            调用webservice传入的参数名称
	 * @param paramValue
	 *            调用webservice传入的参数值
	 * 
	 * @return 连接成功返回true，否则为false
	 */
	public boolean connect(String[] paramName, Object[] paramValue) {
		// step1 指定WebService的命名空间和调用的方法名
		SoapObject soapObject = new SoapObject(NAMESPACE, METHOD_NAME);
		// step2 设置调用方法的参数值,这里的参数名称最好和和WebService一致
		if (paramName != null) {
			for (int i = 0; i < paramName.length; i++) {
				soapObject.addProperty(paramName[i], paramValue[i]);
			}
		}
		// step3 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
		// 创建Envelope对象用于封装整个回传的SOPA协议数据（Envelope是跟对象，解释为信封）
		// SoapEnvelope.VER11 使用SOAP1.1协议（前提是WebService提供商提供了这个版本的服务）
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		// 设置是否调用的是asp.net,dotNet下的WebService
		envelope.dotNet = true;
		// 必须，等价于envelope.bodyOut = soapObject;
		// 设置读取返回
		envelope.bodyOut = soapObject;
		envelope.encodingStyle = "UTF-8";
		// envelope.setOutputSoapObject(soapObject);

		// step4 创建HttpTransportSE对象
		// 创建WEBService远程访问对象(超时时间:5秒)
		// 网络连接通讯中，用户不能取消连接，只能通过设置超时时间来实现。
		HttpTransportSE ht = new HttpTransportSE(SERVER_ADD + WEBSERVICE, 8000);
		System.out.println("服务器地址:" + SERVER_ADD);
		ht.debug = true;
		Log.d("----", "服务初始化成功，准备连接！");
		try {
			// step5 调用WebService
			ht.call(SOAP_ACTION, envelope);
			Log.d("----", "服务请求成功！");
			// step6 使用getResponse方法获得WebService方法的返回结果
			if (envelope.getResponse() != null) {
				// 获取服务器返回的信息
				result = (SoapPrimitive) envelope.getResponse();
				return true;
			}
		} catch (IOException e) {
			Log.w("WebXMLService:disconnect@IOException",
					"WebXML Service dicconnected！");
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			Log.w("WebXMLService:disconnect@XmlPullParserException",
					"WebXML Service soap fault!");
			e.printStackTrace();
		}
		return false;
	}

	public SoapPrimitive getResult() {
		return result;
	}

}