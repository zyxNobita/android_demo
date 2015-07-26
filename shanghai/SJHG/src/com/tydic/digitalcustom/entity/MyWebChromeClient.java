package com.tydic.digitalcustom.entity;

import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

public final class MyWebChromeClient extends WebChromeClient {

	public boolean onJsAlert(WebView view, String url, String message,
			JsResult result) {
		result.confirm();
		return true;
	}
	
	public MyWebChromeClient(){
	
	}
}
