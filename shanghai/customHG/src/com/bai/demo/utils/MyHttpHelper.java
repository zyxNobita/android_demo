package com.bai.demo.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.params.ConnRouteParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.util.Log;

public class MyHttpHelper {
	//请求方式
	public static final int HTTP_POST = 0;
	public static final int HTTP_GET = 1;

	private static final int mTimeoutConnection = 30 * 1000;
	private static final int mTimeoutSocket = 40 * 1000;

	private static final String TAG = "MyHttpHelper";

	/**
	 * 
	 * @param serverUrl
	 *            服务器url
	 * @param remoteType
	 *            MyHttpHelper.HTTP_POST 或 MyHttpHelper.HTTP_GET
	 * @param hashMap
	 *            请求参数的hashmap
	 * @return 服务器返回的xml
	 */
	public static String doRequestForString(Context context, String serverUrl,
			int remoteType, HashMap<String, String> hashMap) {
		int timeoutConnection = mTimeoutConnection;
		int timeoutSocket = mTimeoutSocket;

		System.out.println("serverUrl==>" + serverUrl);
		return doRequestForString(context, serverUrl, remoteType, hashMap,
				timeoutConnection, timeoutSocket);
	}

	/**
	 * 
	 * @param serverUrl
	 *            服务器url
	 * @param remoteType
	 *            MyHttpHelper.HTTP_POST 或 MyHttpHelper.HTTP_GET
	 * @param hashMap
	 *            请求参数的hashmap
	 * @param timeoutConnection
	 *            建立连接超时时间
	 * @param timeoutSocket
	 *            等待数据超时时间
	 * @return 服务器返回的xml
	 */
	public static String doRequestForString(Context context, String serverUrl,
			int remoteType, HashMap<String, String> hashMap,
			int timeoutConnection, int timeoutSocket) {

		Log.d(TAG, serverUrl);
		if (hashMap != null && hashMap.size() > 0) {
			for (Entry<String, String> entry : hashMap.entrySet()) {
				Log.d(TAG, entry.getKey() + "=" + entry.getValue());
			}
		}

		String result = "";
		try {
			HttpEntity entity = doRequestForEntity(context, serverUrl,
					remoteType, hashMap, timeoutConnection, timeoutSocket);
			if (entity != null) {
				result = EntityUtils.toString(entity, "UTF-8");
				Log.d(TAG, result);
			} else {
				Log.d(TAG, "entity=null");
			}
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * 
	 * @param serverUrl
	 *            服务器url
	 * @param remoteType
	 *            MyHttpHelper.HTTP_POST 或 MyHttpHelper.HTTP_GET
	 * @param hashMap
	 *            请求参数的hashmap
	 * @return 服务器返回的HttpEntity
	 */
	public static HttpEntity doRequestForEntity(Context context,
			String serverUrl, int remoteType, HashMap<String, String> hashMap) {
		int timeoutConnection = mTimeoutConnection;
		int timeoutSocket = mTimeoutSocket;
		return doRequestForEntity(context, serverUrl, remoteType, hashMap,
				timeoutConnection, timeoutSocket);
	}

	/**
	 * 
	 * @param serverUrl
	 *            服务器url
	 * @param remoteType
	 *            MyHttpHelper.HTTP_POST 或 MyHttpHelper.HTTP_GET
	 * @param hashMap
	 *            请求参数的hashmap
	 * @param timeoutConnection
	 *            建立连接超时时间
	 * @param timeoutSocket
	 *            等待数据超时时间
	 * @return 服务器返回的HttpEntity
	 */
	public static HttpEntity doRequestForEntity(Context context,
			String serverUrl, int remoteType, HashMap<String, String> hashMap,
			int timeoutConnection, int timeoutSocket) {
		HttpEntity entity = null;
		try {
			HttpUriRequest request = null;
			if (remoteType == 0) {
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				for (Entry<String, String> entry : hashMap.entrySet()) {
					params.add(new BasicNameValuePair(entry.getKey(), entry
							.getValue()));
				}
				HttpPost post = new HttpPost(serverUrl);
				post.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
				request = post;
			} else if (remoteType == 1) {
				if (hashMap != null) {
					for (Entry<String, String> entry : hashMap.entrySet()) {
						if (!serverUrl.contains("?"))
							serverUrl += "?" + entry.getKey() + "="
									+ entry.getValue();
						else
							serverUrl += "&" + entry.getKey() + "="
									+ entry.getValue();
					}
				}
				request = new HttpGet(serverUrl);
			}

			HttpParams httpParameters = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParameters,
					timeoutConnection);
			HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
			DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);

			if (!ToolUtils.isHavingNetWork(context)) {
				String proxyHost = android.net.Proxy.getDefaultHost();//获得网络代理
				if (proxyHost != null) {
					HttpHost proxy = new HttpHost(
							android.net.Proxy.getDefaultHost(),
							android.net.Proxy.getDefaultPort());
					httpClient.getParams().setParameter(
							ConnRouteParams.DEFAULT_PROXY, proxy);
				}
			}

			HttpResponse httpResponse = httpClient.execute(request);

			int status = httpResponse.getStatusLine().getStatusCode();
			if (status != 401) {
				entity = httpResponse.getEntity();
			}
		} catch (Exception e) {
			if (e != null && e.getMessage() != null) {

				System.out.println("Exception==>" + e.getMessage());
			}
		}
		return entity;
	}

	
	//测试： test  
	
	
}
