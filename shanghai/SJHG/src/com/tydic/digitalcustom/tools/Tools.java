package com.tydic.digitalcustom.tools;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.tydic.digitalcustom.R;
import com.tydic.digitalcustom.entity.MyWebChromeClient;
@SuppressLint({"SetJavaScriptEnabled","ShowToast", "Registered"})
public class Tools extends Activity{

	 static WebView mWebView = null;
	/**
	 * 产生body
	 * @param context
	 * @param namesArry
	 * @param onclicklistener
	 * @return
	 * @throws Exception
	 */
	public static 	void getMenuItem(Context context,LinearLayout itemLayout ,int idCount,OnClickListener onclicklistener) throws Exception{
//		LinearLayout itemLayout = null;
		
		try{
			if(idCount>0){
//				itemLayout = new LinearLayout(context);
				//LayoutParams layoutparamsparams = new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT);
				LayoutParams textparamsparams = new LayoutParams(0,LayoutParams.WRAP_CONTENT);
				textparamsparams.weight=1;
//				itemLayout.setLayoutParams(layoutparamsparams);
//				itemLayout.setOrientation(LinearLayout.HORIZONTAL);
				for (int i = 0; i < idCount; i++) {  
					TextView  tvArray = new TextView(context);
		             tvArray.setId(i);
		             tvArray.setWidth(40);
		             tvArray.setHeight(50);
		             tvArray.setOnClickListener(onclicklistener);
		             //tvArray.setGravity(Gravity.CENTER);
		             tvArray.setTextColor(context.getResources().getColor(R.color.fontColor));
		             itemLayout.addView(tvArray ); 
		            }  
			}
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
//		return myLinearLayout;
	}
	
	/**
	 * 产生title
	 * @param context
	 * @param menuView
	 * @param namesArry
	 * @param onclicklistener
	 * @throws Exception
	 */
	public static 	void  getMenuTitle(Context context,View menuView,String namesArry[],OnClickListener onclicklistener) throws Exception{
		TableRow tablerow = (TableRow) menuView.findViewById(R.id.rowTitle);
		int aWith = tablerow.getWidth()/namesArry.length;
		tablerow.removeAllViews();
		try{
			if(namesArry!=null&&namesArry.length>0){
				for (int i = 0; i < namesArry.length; i++) {  
					 TextView tvArray = new TextView(context);
//		             tvArray.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT,1.0f));//第三个参数就是weight
					 
		             tvArray.setId(i);
		             tvArray.setText(namesArry[i]);
		             tvArray.setGravity(Gravity.CENTER);
		             tvArray.setWidth(aWith);
		             tvArray.setHeight(30);
//		             tvArray.setTextColor(Color.RED);
//		             tvArray.setBackgroundColor(Color.BLACK);
		            // tvArray[i].setTextColor(context.getResources().getColor(R.color.fontColor));
		             tablerow.addView(tvArray); 
		            }  
			}
			System.out.println("总数量："+tablerow.getChildCount());
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * 
	 * @param webView  webView
	 * @param guid     菜单的GUDI
	 * @param mHandler    
	 * @param callBackJS  初始化的js方法
	 * @param url         页面URL
	 */
	public static void createWebView(View webView, int guid,final Handler mHandler,final String callBackJS,String url) {
	    mWebView = (WebView) webView.findViewById(R.id.chartArearWeb);
	    WebSettings webSettings = mWebView.getSettings();
		webSettings.setSavePassword(false);
		webSettings.setSaveFormData(false);
		webSettings.setJavaScriptEnabled(true);
		webSettings.setSupportZoom(true);
		mWebView.setBackgroundColor(0);
		MyWebChromeClient MyWebChromeClient = new MyWebChromeClient();
		mWebView.setWebChromeClient(MyWebChromeClient);
		//mWebView.addJavascriptInterface(new DemoJavaScriptInterface(mHandler, mWebView, callBackJS), "demo");
		mWebView.addJavascriptInterface(new Object() {        
			          public void clickOnAndroid() {  
			        	  Handler aHandler = new Handler();
			        	  aHandler.post(new Runnable() {        
			                    public void run() {      
			                    	System.out.println("被调用"+callBackJS);
			                    	try{
			                    		mWebView.loadUrl("javascript:"+callBackJS);
			                    	}catch(Exception e){
			                    		System.out.println(e.getMessage());
			                    	}
			                    	        
			                    }        
			                });        
			           }        
			        }, "demo");
		mWebView.loadUrl(url);
		System.out.println("url"+url);
	}
	
	
	/**
	 * 
	 * @param webView  webView
	 * @param guid     菜单的GUDI
	 * @param mHandler    
	 * @param callBackJS  初始化的js方法
	 * @param url         页面URL
	 */
	public static void createWebView(View webViewContainer,final int vebviewID ,final String callBackJS,String url) {
	    mWebView = (WebView) webViewContainer.findViewById(vebviewID);
	    WebSettings webSettings = mWebView.getSettings();
		webSettings.setSavePassword(false);
		webSettings.setSaveFormData(false);
		webSettings.setJavaScriptEnabled(true);
		webSettings.setSupportZoom(true);
		mWebView.setBackgroundColor(0);
		MyWebChromeClient MyWebChromeClient = new MyWebChromeClient();
		mWebView.setWebChromeClient(MyWebChromeClient);
//		mWebView.addJavascriptInterface(new DemoJavaScriptInterface(mHandler, mWebView, callBackJS), "demo");
		mWebView.addJavascriptInterface(new Object() {        
			          public void clickOnAndroid() {  
			        	  Handler aHandler = new Handler();
			        	  aHandler.post(new Runnable() {        
			                    public void run() {      
			                    	System.out.println("被调用"+callBackJS);
			                    	try{
			                    		mWebView.loadUrl("javascript:"+callBackJS);
			                    	}catch(Exception e){
			                    		System.out.println(e.getMessage());
			                    	}
			                    	        
			                    }        
			                });        
			           }        
			        }, "demo");
		mWebView.loadUrl(url);
		System.out.println("url"+url);
	}
	
	
	//判断网络连接方法
    public static boolean isNetworkAvailable(Context ctx) {   
        try {   
            ConnectivityManager cm = (ConnectivityManager) ctx   
                    .getSystemService(Context.CONNECTIVITY_SERVICE);   
            NetworkInfo info = cm.getActiveNetworkInfo();   
            return (info != null && info.isConnected());   
        } catch (Exception e) {   
            e.printStackTrace();   
            return false;   
        }   
    }   
    
	public static void showError(Context context,Exception e){
		context.getClass().getName();		
		Toast.makeText(context, "系统异常，请联系管理员,错误："+e.getMessage(), Toast.LENGTH_LONG);
		
	}
	
}
