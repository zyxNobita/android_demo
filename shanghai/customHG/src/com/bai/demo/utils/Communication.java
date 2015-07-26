package com.bai.demo.utils;

import java.io.IOException;
import java.util.ArrayList;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

/************************************************************
 * 内容摘要 ：
 * 
 * 作者 ：adminstrator 创建时间 ：2013-3-11 下午03:51:43 当前版本号：v1.0 历史记录 : 日期 : 2013-3-11
 * 下午03:51:43 修改人：adminstrator 描述 :基本的通讯类
 ************************************************************/
public class Communication {

	private static Communication mCommunication;

	private Communication() {
	}

	public static Communication getInstance() {
		if (mCommunication == null) {
			mCommunication = new Communication();
		}

		return mCommunication;
	}

	private String strContent = null;

	/**
	 * 
	 * 函数名称 : connecting 功能描述 : 参数及返回值说明：
	 * 
	 * @param nameSpace
	 *            名称空间
	 * @param methodName
	 *            方法名
	 * @param parameters
	 *            参数集合
	 * @param WSDLurl
	 *            访问WSDL地址
	 * @return
	 * 
	 *         修改记录： 日期：2013-3-11 下午03:57:05 修改人：adminstrator 描述
	 *         ：与服务器端的webServices建立连接
	 * 
	 */
	public Object connecting(String nameSpace, String methodName,
			ArrayList<Object> parameters, String WSDLurl) {

		/**
		 * 1、实例化 SoapObject 对象 指定 WebService 的命名空间和调用的方法名。 SoapObject 类的第 1
		 * 个参数表示 WebService 的命名空间，可以从 WSDL 文档中找到 WebService 的命名空间。 第 2 个参数表示要调用的
		 * WebService 方法名
		 */
		SoapObject request = new SoapObject(nameSpace, methodName);
		/**
		 * 2、设置调用方法的参数值，这一步是可选的，如果方法没有参数，可以省略这一步。 要注意的是，addProperty 方法的第 1
		 * 个参数虽然表示调用方法的参数名， 此处的参数名的定义不是服务端的 WebService 类中的方法参数名，而是在 Jax-ws
		 * 搭建时自动生成的 WEB-INF/wsdl/*Service_schema*.xsd 文件中配置的方法名， <xs:element
		 * minOccurs="0" name="arg0" type="xs:string"/>，也就是函数名为 arg0
		 */
		if (parameters != null) {
			for (int i = 0; i < parameters.size(); i++) {
				request.addProperty("arg" + i, parameters.get(i).toString());
			}
		}

		/**
		 * 3、生成调用 WebService 方法的 SOAP 请求信息。该信息由 SoapSerializationEnvelope 对象描述
		 */
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		/**
		 * 创建 SoapSerializationEnvelope 对象时需要通过 SoapSerializationEnvelope
		 * 类的构造方法设置 SOAP 协 议的版本号。 该版本号需要根据服务端 WebService 的版本号设置。在创建
		 * SoapSerializationEnvelope 对象后， 不要忘了设置 SoapSerializationEnvelope 类的
		 * bodyOut属性，该属性的值就是在第 1 步创建的 SoapObject 对象
		 */
		// request.
		envelope.bodyOut = request;

		/**
		 * 4、创建 HttpTransportSE 对象。通过 HttpTransportSE 类的构造方法可以指定 WebService 的
		 * WSDL 文档的 URL
		 */
		HttpTransportSE ht = new HttpTransportSE(WSDLurl);
		try {
			/**
			 * 5、使用 call 方法调用 WebService 方法。 call 方法的第 1 个参数一般为 null，第 2 个参数就是在第
			 * 3 步创建的 SoapSerializationEnvelope 对象
			 */
			ht.call(null, envelope);
			/**
			 * 6、使用 getResponse 方法获得 WebService 方法的返回结果
			 */
			System.out.println("envelope.getResponse()="
					+ envelope.getResponse());

			if (envelope.getResponse() instanceof SoapPrimitive) {
				strContent = ((SoapPrimitive) envelope.getResponse())
						.toString();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}

		return strContent;
	}
}