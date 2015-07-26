package com.bai.demo.entity;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.provider.Settings;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.view.WindowManager;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bai.demo.R;
import com.bai.demo.bean.ENTRY_LIST;
import com.bai.demo.bean.UserInfo;
import com.bai.demo.main.FrameDemoActivity;
import com.bai.demo.utils.MyApplication;
import com.bai.demo.utils.RequestPath;
import com.bai.demo.utils.ToolUtils;
import com.bai.demo.utils.Webservice;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

/**
 * 登陆页面
 * 
 */
@SuppressLint({ "ParserError", "ParserError" })
public class LoginActivity extends Activity implements Callback {
	private static final String My_Tag = "My_Tag";
	private boolean isExit = false;
	public static ToolUtils toolUtils;
	protected InputMethodManager inputManager;
	private MyHandler handler;
	private EditText et_usernameValue, tv_passwordValue;
	public static ProgressDialog progressDialog;
	private Gson gson;
	/** 获得版本更新 */

	private String updateUrl = "";// 此地址为服务器返回地址---下载apk 的地址
	private final static String tempfile = "storetmp.apk";// 存储的从服务器拿下来的文件的文件格式
	private final static String tempfilepath = "/data/data/cn.tydic.mobile.pdarequery/files/"
			+ tempfile;

	boolean isAlive = true;
	private ProgressDialog progressDialog1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		initView();
		checkUpdate();//版本更新操作
		//登录
		Button loginBtn = (Button) findViewById(R.id.btn_loginBtn);
		loginBtn.setOnClickListener(new LoginBtnClick());
		//取消
		Button quitBtn = (Button) findViewById(R.id.btn_quitBtn);
		quitBtn.setOnClickListener(new QuitBtnClick());

	}

	private void initView() {
		handler = new MyHandler(LoginActivity.this);
		toolUtils = new ToolUtils(LoginActivity.this);
		Constant.webservice=new Webservice(getApplicationContext());
		inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		et_usernameValue = (EditText) findViewById(R.id.et_usernameValue);
		tv_passwordValue = (EditText) findViewById(R.id.tv_passwordValue);
		gson=new Gson();
	}

	/**
	 * 设置退出监听
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			// 确认对话框
			final AlertDialog isExit = new AlertDialog.Builder(this).create();
			// 对话框标题
			isExit.setTitle(this.getString(R.string.titleMessage));
			isExit.setIcon(R.drawable.ic_launcher);
			// 对话框消息
			isExit.setMessage(this.getString(R.string.sureExite));
			// 实例化对话框上的按钮点击事件监听
			DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					switch (which) {
					case AlertDialog.BUTTON1:// "确认"按钮退出程序
						// 切记 释放一些系统的资源操作
						finish();
						
						break;
					case AlertDialog.BUTTON2:// "取消"第二个按钮取消对话框
						isExit.cancel();
						break;
					default:
						break;
					}
				}
			};
			// 注册监听
			isExit.setButton(this.getString(R.string.makeSure), listener);
			isExit.setButton2(this.getString(R.string.cancel), listener);
			// 显示对话框
			isExit.show();
			return false;
		}
		return false;
	}

	/***
	 * 登录 的验证
	 */
	private void check() {
		String userName=et_usernameValue.getText().toString().trim();
		String pwd=tv_passwordValue.getText().toString().trim();
		if (userName==null || userName.equals("")) {
			toolUtils.promptMessage("工号不得为空");
		} else if (pwd==null || pwd.equals("")) {
			toolUtils.promptMessage("密码不得为空");
		} else {
			loginNetCheck(userName,pwd);
			
		}
	}

	private boolean loginNetCheck(final String userName,final String pwd) {
		inputManager.hideSoftInputFromWindow(et_usernameValue.getWindowToken(),
				0);
		inputManager.hideSoftInputFromWindow(tv_passwordValue.getWindowToken(),
				0);

		progressDialog = ProgressDialog.show(LoginActivity.this, "正在登录",
				"请稍后...");
		
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				Message msg = new Message();
				Constant.webservice.setMETHOD_NAME("PadUserLogin");
				Constant.webservice.setSOAP_ACTION("http://tempuri.org/IGetExamDataService/PadUserLogin");
				if (Constant.webservice.connect(getApplicationContext(),new String[] { "UserId",
						"Pwd" }, new Object[] {
					userName, pwd })) {
					String rst = Constant.webservice.getResult().toString();
					if (rst != null && !rst.equals("") && !rst.equals("9")) {
						
						Constant.webservice.setMETHOD_NAME("GetCurrentUserInfo");
						Constant.webservice.setSOAP_ACTION(getString(R.string.REQ_Login_Action));
						if(Constant.webservice.connect(getApplicationContext(),new String[] {"strUserJobNumber"
						}, new Object[] {et_usernameValue.getText().toString()})){
							String userStr= Constant.webservice.getResult().toString();
							if(userStr!=null && !userStr.equals("")){
								System.out.println("获取用户详细信息"+userStr);
								MyApplication myApp=(MyApplication) getApplicationContext();
								myApp.userInfo=gson.fromJson(userStr, UserInfo.class);
								msg.what = Constant.LOGIN_SUCCESS;
								//添加Log日志---登录成功的日志
								toolUtils.writeDataLog("登录系统成功。", "81", myApp.userInfo.getLobNumber());
							}
						}
					}else{
						msg.what=Constant.LOGIN_FAIL2;
					}
				} else{
					//开启网络的
					///手动设置网络
					msg.what=Constant.CONNECT_FAIL;
					startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
				}
				handler.sendMessage(msg);
			}

		});
		thread.start();
		return false;
	}

	class LoginBtnClick implements OnClickListener {
		@Override
		public void onClick(View v) {
			// 无需要验证 可登陆
			testLogin();
			// 链接后台服务器的
			//check();
			
		}
	}

	class QuitBtnClick implements OnClickListener {
		@Override
		public void onClick(View v) {
			toolUtils.exiteAppMessage(LoginActivity.this
					.getString(R.string.sureExite));
		}
	}

	@Override
	public boolean handleMessage(Message msg) {
		return false;
	}
	/**
	 * 无需后台验证登陆
	 */
	private void testLogin() {
		Intent intent = new Intent(getApplicationContext(),
				FrameDemoActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.fade, R.anim.hold);
		LoginActivity.this.finish();
	}

	//版本更新
    private void checkUpdate(){
    	
    }
    
    /***
	 * 
	 * 根据当前版本的版本号与从服务端获取的版本号进行比较，如果服务器的版本号大于现在项目的版本号则强制进行升级
	 * 
	 */
	class GetUpdateTask extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... params) {
			String resultStr = "";
			Constant.webservice.setMETHOD_NAME(getApplicationContext()
					.getString(R.string.Connection_MethodName));
			Constant.webservice.setWEBSERVICE(getApplicationContext()
					.getString(R.string.Connection_Webservice));

			if (Constant.webservice.connect(getApplicationContext(), null, null)) {
				if (Constant.webservice.getResult() != null) {
					resultStr = Constant.webservice.getResult().toString();
					System.out.println(resultStr + "更新");
				} else {
					return null;
				}
			}
			return resultStr;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if (result != null) {
				// String versionName = result.getString("version");
				//返回数据处理
				try {
					JSONObject obj = new JSONObject(result);
					String res = obj.getString("RESULT");
					String data = obj.getString("DATA");
					String versionName = null;
					if (res.equals("TRUE")) {
						JSONArray RES = new JSONArray(data);
						for (int i = 0; i < RES.length(); i++) {
							JSONObject updateInfo = (JSONObject) RES.get(i);
							versionName = updateInfo.getString("dmz");// 版本号
							updateUrl = updateInfo.getString("dmsm1");// 地址
						}

						System.out.println(versionName + "更新版本号");
					} else {
						Toast.makeText(LoginActivity.this, "手机唯一识别码不存在！", 1)
								.show();
					}
					PackageInfo packageInfo = LoginActivity.this
							.getPackageManager()
							.getPackageInfo(
									LoginActivity.this.getApplicationInfo().packageName,
									0);
					String oldVersionName = packageInfo.versionName;
					int versionCode = Integer.parseInt(versionName.replaceAll(
							"[.]", ""));
					int oldVersionCode = Integer.parseInt(oldVersionName
							.replaceAll("[.]", ""));
					if (versionCode > oldVersionCode) { // 新版本比老版本大,需要升级
						AlertDialog dialog = new AlertDialog.Builder(
								LoginActivity.this)
								.setTitle("系统检测到有版本更新,点击确定开始升级")
								.setPositiveButton("确定",
										new DialogInterface.OnClickListener() {
											@Override
											public void onClick(
													DialogInterface dialog,
													int which) {
												new DownloadUpdate()
														.execute(updateUrl);
											}
										}).create();
						dialog.setCancelable(false);//有新版本的时候，强制下载新版本：false（强制）|true（不强制）
						dialog.show();
					} else { // 不需要升级
						Toast.makeText(LoginActivity.this, "当前版本是最新版本不需要升级",
								Toast.LENGTH_LONG).show();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				Toast.makeText(LoginActivity.this, "未检测到新版本", Toast.LENGTH_LONG)
						.show();
			}
		}
	}
	
	/**
	 * 
	 * 下载升级文件的异步任务
	 * @param updateUrl
	 *            需要下载apk的地址
	 */
	class DownloadUpdate extends AsyncTask<String, Integer, Boolean> {

		@Override
		protected Boolean doInBackground(String... params) {
			String url = params[0];
			if (!URLUtil.isNetworkUrl(url)) {

			} else {
				/* 取得URL */
				URL myURL;
				try {
					myURL = new URL(url);
					/* 创建连接 */
					HttpURLConnection conn;
					InputStream is;
					BufferedInputStream bis = null;
					FileOutputStream fos;
					BufferedOutputStream bos = null;
					conn = (HttpURLConnection) myURL.openConnection();
					conn.setConnectTimeout(1000 * 10);
					conn.setReadTimeout(1000 * 10);
					conn.connect();
					int size = conn.getContentLength();
					publishProgress(1, size);
					/* InputStream 下载文件 */
					is = conn.getInputStream();
					bis = new BufferedInputStream(is, Constant.BUFFER_SIZE);

					if (is == null) {
						return false;
					}

					fos = LoginActivity.this.openFileOutput(tempfile,
							Context.MODE_WORLD_READABLE);
					bos = new BufferedOutputStream(fos, Constant.BUFFER_SIZE);
					byte buf[] = new byte[Constant.DOWN_ARRAY_SIZE];
					do {
						int numread = bis.read(buf);
						if (numread <= 0) {
							break;
						}
						bos.write(buf, 0, numread);
						publishProgress(2, numread);
					} while (isAlive);
					bos.flush();
					try {
						if (is != null)
							is.close();
						if (fos != null)
							fos.close();
						if (bos != null)
							bos.close();
						if (bis != null)
							bis.close();
						if (conn != null)
							conn.disconnect();
					} catch (Exception ex) {
						Log.e("getDataSource", "error: " + ex.getMessage(), ex);
					}
				} catch (MalformedURLException e) {
					e.printStackTrace();
					return false;
				} catch (IOException e) {
					e.printStackTrace();
					return false;
				}
			}
			return true;
		}

		@Override
		protected void onCancelled() {
			super.onCancelled();
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			if (!isAlive) {
				return;
			}
			if (!LoginActivity.this.isFinishing()) {
				progressDialog.dismiss();
				if (result) {
					new AlertDialog.Builder(LoginActivity.this)
							.setTitle("下载完成")
							.setMessage("下载完成,请点击安装!")
							.setPositiveButton("安装",
									new DialogInterface.OnClickListener() {
										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											dialog.dismiss();
											try {
												Field field = dialog
														.getClass()
														.getSuperclass()
														.getDeclaredField(
																"mShowing");
												field.setAccessible(true);
												// 将mShowing变量设为false，表示对话框已关闭
												field.set(dialog, false);
											} catch (Exception e) {
											}
											Intent intent = new Intent(
													Intent.ACTION_VIEW);
											intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
											intent.setDataAndType(
													Uri.parse("file://"
															+ tempfilepath),
													"application/vnd.android.package-archive");
											startActivity(intent);
										}
									}).setCancelable(false).show();//程序强制安装：false（强制）|true（不强制）

				} else {
					new AlertDialog.Builder(LoginActivity.this)
							.setTitle("下载出错")
							.setMessage("下载更新出错,请检查网络并重新更新")
							.setPositiveButton("确定",
									new DialogInterface.OnClickListener() {
										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
										}
									}).show();
				}
			}
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);
			if (values[0] == 1) {
				progressDialog.setMax(values[1]);
			} else if (values[0] == 2) {
				progressDialog.setProgress(progressDialog.getProgress()
						+ values[1]);
			}
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog.show();
		}
	}
	
}