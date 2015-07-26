package com.tydic.digitalcustom.entity;

import android.os.Handler;
import android.webkit.WebView;

public class DemoJavaScriptInterface {

	private Handler mHandler;
	private WebView mWebView;
	private String  initJS ;

	public DemoJavaScriptInterface(Handler mHandler, WebView mWebView,String callBackJS) {
		this.mHandler = mHandler;
		this.mWebView = mWebView;
		this.initJS = callBackJS;
	}

	/**
	 * * This is not called on the UI thread. Post a runnable to invoke *
	 * loadUrl on the UI thread.
	 */
	public void clickOnAndroid() {
		mHandler.post(new Runnable() {
			public void run() {
				// String data = "25,4,0,6,8,5,3";
				// String values =
				// "Monday,Tuesday,Wednesday,Thursday,Friday,Saturday,Sunday";
				// String jsonText = "{\"count\":\"25\"}";
				// Toast.makeText(ZxgzActivity.this,
				// "点击了Webview",Toast.LENGTH_SHORT).show();
				System.out.println("调用方法："+"javascript:"+initJS);
				mWebView.loadUrl("javascript:"+initJS);
			}
		});
	}

}
