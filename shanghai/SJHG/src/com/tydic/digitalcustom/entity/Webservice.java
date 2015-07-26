package com.tydic.digitalcustom.entity;

import java.io.IOException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;

/**
 * 
 * Description:���ýӿڷ��� Webservice.java Create on 2013-7-8 ����2:18:08
 * 
 * @author wangwx
 * @version 1.0 Copyright (c) 2013 Company,Inc. All Rights Reserved.
 */

public class Webservice {

	public String NAMESPACE = "http://localhost:8080/Server/Service/";// �����ռ�
	//public String SERVER_ADD = "http://10.62.1.81:8090/";// ��������ַ
	public String SERVER_ADD = "http://192.168.0.103:8080/";// ��������ַ
	// baipc ip http://192.168.173.1:8080/
	// 81 ip http://10.62.1.81:8090/
	public String WEBSERVICE = "Server/services/Service";// webServiceĿ¼
	public String METHOD_NAME = "getExamPort";// Ҫ���õ�webService����
	public String SOAP_ACTION = NAMESPACE + METHOD_NAME;

	public SoapPrimitive result;// ���������ؽ��
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
	 * �޲������췽�� Ĭ�ϵ��ñ��� Ŀ¼/mobile/Service1.asmx?WSDL �µ�HelloWorld����
	 */
	private Webservice() {
		super();
	}

	public static Webservice getInstance() {
		if (webservice != null) {
			return webservice;
		}
		webservice = new Webservice();
		return webservice;
	}

	/**
	 * @author bai
	 * @param nAMESPACE
	 *            webservice�����ռ� Ĭ��Ϊ http://tempuri.org/
	 * @param sERVER_ADD
	 *            webservice IP��ַ Ĭ��Ϊ http://10.0.2.2
	 * @param wEBSERVICE
	 *            webservice Ŀ¼��ַ Ĭ��Ϊ /mobile/Service1.asmx?WSDL
	 * @param mETHOD_NAME
	 *            webservice ������ Ĭ��Ϊ HelloWorld
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
	 * @return ���ӳɹ�����true������Ϊfalse
	 */
	public boolean connect() {
		return connect(null, null);
	}

	/**
	 * @param paramName
	 *            ����webservice����Ĳ�������
	 * @param paramValue
	 *            ����webservice����Ĳ���ֵ
	 * 
	 * @return ���ӳɹ�����true������Ϊfalse
	 */
	public boolean connect(String[] paramName, Object[] paramValue) {
		// step1 ָ��WebService�������ռ�͵��õķ�����
		SoapObject soapObject = new SoapObject(NAMESPACE, METHOD_NAME);
		// step2 ���õ��÷����Ĳ���ֵ,����Ĳ���������úͺ�WebServiceһ��
		if (paramName != null) {
			for (int i = 0; i < paramName.length; i++) {
				soapObject.addProperty(paramName[i], paramValue[i]);
			}
		}
		// step3 ���ɵ���WebService������SOAP������Ϣ,��ָ��SOAP�İ汾
		// ����Envelope�������ڷ�װ�����ش���SOPAЭ�����ݣ�Envelope�Ǹ����󣬽���Ϊ�ŷ⣩
		// SoapEnvelope.VER11 ʹ��SOAP1.1Э�飨ǰ����WebService�ṩ���ṩ������汾�ķ���
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		// �����Ƿ���õ���asp.net,dotNet�µ�WebService
		envelope.dotNet = true;
		// ���룬�ȼ���envelope.bodyOut = soapObject;
		// ���ö�ȡ����
		envelope.bodyOut = soapObject;
		envelope.encodingStyle = "UTF-8";
		// envelope.setOutputSoapObject(soapObject);

		// step4 ����HttpTransportSE����
		// ����WEBServiceԶ�̷��ʶ���(��ʱʱ��:5��)
		// ��������ͨѶ�У��û�����ȡ�����ӣ�ֻ��ͨ�����ó�ʱʱ����ʵ�֡�
		HttpTransportSE ht = new HttpTransportSE(SERVER_ADD + WEBSERVICE, 8000);
		ht.debug = true;
		Log.d("----", "�����ʼ���ɹ���׼�����ӣ�");
		try {
			// step5 ����WebService
			ht.call(SOAP_ACTION, envelope);
			Log.d("----", "��������ɹ���");
			// step6 ʹ��getResponse�������WebService�����ķ��ؽ��
			if (envelope.getResponse() != null) {
				// ��ȡ���������ص���Ϣ
				result = (SoapPrimitive) envelope.getResponse();
				return true;
			}
		} catch (IOException e) {
			Log.w("WebXMLService:disconnect@IOException",
					"WebXML Service dicconnected��");
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
