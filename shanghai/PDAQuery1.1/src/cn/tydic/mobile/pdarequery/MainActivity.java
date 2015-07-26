package cn.tydic.mobile.pdarequery;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.URLUtil;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.tydic.mobile.pdarequery.activity.AboutSoftwareInformationActivity;
import cn.tydic.mobile.pdarequery.activity.BusinessAcceptanceActivity;
import cn.tydic.mobile.pdarequery.activity.JobAccountingActivity;
import cn.tydic.mobile.pdarequery.activity.QueryCarInformationActivity;
import cn.tydic.mobile.pdarequery.activity.QueryNoticeInformationActivity;
import cn.tydic.mobile.pdarequery.activity.UserPasswordChangeActivity;
import cn.tydic.mobile.pdarequery.adapter.MainShowAdapter;
import cn.tydic.mobile.pdarequery.adapter.ServerAdapter;
import cn.tydic.mobile.pdarequery.entity.UserInfo;
import cn.tydic.mobile.pdarequery.nettools.Webservice;
import cn.tydic.mobile.pdarequery.tools.Constant;
import cn.tydic.mobile.pdarequery.tools.InputLengthControler;
import cn.tydic.mobile.pdarequery.tools.IntentDialog;
import cn.tydic.mobile.pdarequery.tools.Tools;

/**
 * 程序主界面
 * 
 * @author jiangyue 主要功能： 1.显示主页布局界面； 2.判断是否已经注册，没有注册跳转至注册界面，如果已经注册跳转登陆界面；
 *         3.判断这个版本是否需要升级（强制升级）。
 */
@SuppressLint({ "WorldReadableFiles", "HandlerLeak" })
public class MainActivity extends Activity {

	private GridView mainShow_GV;// 主界面所使用的GridView
	private MainShowAdapter adapter;// GridiView的适配器
	private ServerAdapter serverAdapter;
	private LinearLayout ll_transparent, iv_user, popup_loginCancel,
			popup_regCancel, ll_server_cancel;// 半透明布局，
												// 登陆图标，PopupWindow登陆取消按钮，PopupWindow注册取消按钮
	private Button popup_loginReg;
	private RelativeLayout rl;
	private PopupWindow popup_login, popup_reg, popup_server;
	private View popupView_login, popupView_reg, popupView_server;
	private EditText et_login_user;
	private TextView tv_custom, tv_ipAddress;
	private CheckBox cb_login_user;
	private ListView lv_server;
	private ArrayList<String> serverList;
	// private ImageView iv_server_cancel;
	public static Tools tool;
	public static Gson gson;
	public static Handler handler;
	private Integer ET_MAXLENGTH=12;
	
	/** 获得版本更新 */

	private String updateUrl = "";// 此地址为服务器返回地址
	private final static String tempfile = "storetmp.apk";// 存储的从服务器拿下来的文件的文件格式
	private final static String tempfilepath = "/data/data/cn.tydic.mobile.pdarequery/files/"
			+ tempfile;

	boolean isAlive = true;
	private ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		tool = new Tools(getApplicationContext());
		gson = new Gson();
		
		initViewData();
		initView();
		check4Update();
		initEvent();

	}

	public void check4Update() {
		Map<String, String> conMap = new HashMap<String, String>();
		String phone_id = tool.getImieStatus(MainActivity.this);
		conMap.put("phone_id", phone_id);// 查询大类菜单
		conMap.put("xtlb", "008");
		conMap.put("dmlb", "006");
		progressDialog = new ProgressDialog(MainActivity.this);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		progressDialog.setCancelable(false);
		new GetUpdateTask().execute(gson.toJson(conMap));
	}

	/**
	 * 初始化界面
	 */
	public void initView() {
		//获取项目所有图片的本地存储路径
		if(tool.isSDCardExist()){//判断SDCard是否存在
			if(Constant.localImagePath==null | Constant.localImagePath.equals("")){
				Constant.localImagePath=Environment.getExternalStorageDirectory()+"/"+MainActivity.tool.getApplicationName();
			}
		}
		
		iv_user = (LinearLayout) findViewById(R.id.iv_user);

		mainShow_GV = (GridView) findViewById(R.id.main_GV);
		adapter = new MainShowAdapter(this);
		mainShow_GV.setAdapter(adapter);

		// 用户标题
		tv_custom = (TextView) findViewById(R.id.tv_custom);
		((TextView) findViewById(R.id.tv_time)).setText(tool.requestData());
		ll_transparent = (LinearLayout) findViewById(R.id.ll_transparent);
		rl = (RelativeLayout) findViewById(R.id.rl_mainTop);

		// 创建一个登陆PopupWindow对象
		int screenWidth = getWindowManager().getDefaultDisplay().getWidth();
		int screenHeight = getWindowManager().getDefaultDisplay().getHeight();

		// 服务器地址
		tv_ipAddress = (TextView) findViewById(R.id.tv_ipAddress);
		// tv_ipAddress.setText("当前服务器地址:" + Constant.SERVER_ADD + "（请勿随意更改）");
		String serverAddress = tool.readServerAddress(getApplicationContext());
		if (serverAddress != null && !serverAddress.equals("")) {
			Constant.SERVER_ADD = null;
			Constant.SERVER_ADD = serverAddress;
			Constant.webService = new Webservice();
			System.out.println("当前的服务器地址为：" + Constant.SERVER_ADD);
			tv_ipAddress.setText("当前服务器地址:" + serverAddress + "（请勿随意更改）");
		} else {
			tv_ipAddress.setText("当前服务器地址:" + Constant.SERVER_ADD + "（请勿随意更改）");
			Constant.webService = new Webservice();
			System.out.println("默认的服务器地址为：" + Constant.SERVER_ADD);
		}

		// 创建服务器地址选择的PopupWindow
		popupView_server = LayoutInflater.from(MainActivity.this).inflate(
				R.layout.serveraddress, null);
		lv_server = (ListView) popupView_server.findViewById(R.id.serverlist);
		ll_server_cancel = (LinearLayout) popupView_server
				.findViewById(R.id.ll_server_cancel);
		popup_server = new PopupWindow(popupView_server, screenWidth - 150,
				screenHeight - 945);

		// 装载R.layout.Popup对应的登陆布局
		popupView_login = LayoutInflater.from(MainActivity.this).inflate(
				R.layout.loginpopup, null);
		et_login_user = (EditText) popupView_login
				.findViewById(R.id.et_login_user);
		cb_login_user = (CheckBox) popupView_login
				.findViewById(R.id.cb_login_user);

		System.out.println("屏幕宽度:" + screenWidth + ",屏幕高度:" + screenHeight);
		popup_login = new PopupWindow(popupView_login, screenWidth - 150,
				screenHeight - 450);
		popup_loginReg = (Button) popupView_login
				.findViewById(R.id.btn_login_reg);
		popup_loginCancel = (LinearLayout) popupView_login
				.findViewById(R.id.ll_loginCancel);

		// 装载R.layout.Popup对应的注册布局
		popupView_reg = LayoutInflater.from(MainActivity.this).inflate(
				R.layout.regpopup, null);

		// 创建一个注册PopupWindow对象
		popup_reg = new PopupWindow(popupView_reg, screenWidth - 150,
				screenHeight - 450);
		popup_regCancel = (LinearLayout) popupView_reg
				.findViewById(R.id.ll_regCancel);
	}

	/**
	 * 初始化界面监听
	 */
	public void initEvent() {
		// 服务器地址ListView
		lv_server.setOnItemClickListener(new ListView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				tv_ipAddress.setText("当前服务器地址：" + serverList.get(position)
						+ "（请勿随意更改）");

				Constant.SERVER_ADD = null;
				Constant.webService = null;
				Constant.SERVER_ADD = serverList.get(position);
				String serverAddress = serverList.get(position);
				tool.saveServerAddress(getApplicationContext(), serverAddress);
				Constant.webService = new Webservice();
				System.out.println(Constant.webService.SERVER_ADD + "地址");
				popup_server.dismiss();
			}
		});

		// 更改服务器地址
		tv_ipAddress.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// Toast.makeText(MainActivity.this, "选择", 1).show();
				serverAdapter = new ServerAdapter(serverList, MainActivity.this);
				lv_server.setAdapter(serverAdapter);
				initServerPopup();
			}
		});

		ll_server_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				popup_server.dismiss();
			}
		});

		iv_user.setOnClickListener(new ImageView.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// Toast.makeText(MainActivity.this, "登陆", Toast.LENGTH_SHORT)
				// .show();
				initLoginPoppup();
				ll_transparent.setVisibility(View.VISIBLE);
				// 登陆功能
				doLogin();
			}
		});

		mainShow_GV.setOnItemClickListener(new GridView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				switch (position) {
				case 0:// 查验业务受理
					if (Constant.user == null) {
						Toast.makeText(MainActivity.this, "请先登陆，谢谢！",
								Toast.LENGTH_SHORT).show();
						initLoginPoppup();
						ll_transparent.setVisibility(View.VISIBLE);
						// 登陆功能
						doLogin();
					} else {
						 startActivity(new Intent(MainActivity.this,
						 BusinessAcceptanceActivity.class));
						 overridePendingTransition(R.anim.push_left_in,
						 R.anim.push_left_out);
					}
					break;
				case 1:// 车辆信息查询
					if (Constant.user == null) {
						Toast.makeText(MainActivity.this, "请先登陆，谢谢！",
								Toast.LENGTH_SHORT).show();
						initLoginPoppup();
						ll_transparent.setVisibility(View.VISIBLE);
						// 登陆功能
						doLogin();
					} else {
						startActivity(new Intent(MainActivity.this,
								QueryCarInformationActivity.class));
						overridePendingTransition(R.anim.push_left_in,
								R.anim.push_left_out);
					}
					break;
				case 2:// 公告信息查询
					if (Constant.user == null) {
						Toast.makeText(MainActivity.this, "请先登陆，谢谢！",
								Toast.LENGTH_SHORT).show();
						initLoginPoppup();
						ll_transparent.setVisibility(View.VISIBLE);
						// 登陆功能
						doLogin();
					} else {
						startActivity(new Intent(MainActivity.this,
								QueryNoticeInformationActivity.class));
						overridePendingTransition(R.anim.push_left_in,
								R.anim.push_left_out);
					}
					break;
				case 3:// 工作台账统计
					if (Constant.user == null) {
						Toast.makeText(MainActivity.this, "请先登陆，谢谢！",
								Toast.LENGTH_SHORT).show();
						initLoginPoppup();
						ll_transparent.setVisibility(View.VISIBLE);
						// 登陆功能
						doLogin();
					} else {
						startActivity(new Intent(MainActivity.this,
								JobAccountingActivity.class));
						overridePendingTransition(R.anim.push_left_in,
								R.anim.push_left_out);
					}
					break;
				case 4:// 用户密码修改
					if (Constant.user == null) {
						Toast.makeText(MainActivity.this, "请先登陆，谢谢！",
								Toast.LENGTH_SHORT).show();
						initLoginPoppup();
						ll_transparent.setVisibility(View.VISIBLE);
						// 登陆功能
						doLogin();
					} else {
						startActivity(new Intent(MainActivity.this,
								UserPasswordChangeActivity.class));
						overridePendingTransition(R.anim.push_left_in,
								R.anim.push_left_out);
					}
					break;
				case 5:// 关于软件信息
					startActivity(new Intent(MainActivity.this,
							AboutSoftwareInformationActivity.class));
					overridePendingTransition(R.anim.push_left_in,
							R.anim.push_left_out);
					break;
				}
			}
		});

		popup_loginReg.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				popup_login.dismiss();
				initRegPoppup();
				ll_transparent.setVisibility(View.VISIBLE);
				// 注册 用户
				registerUser();
			}
		});

		// 获取PopupWindow中的登陆取消按钮
		popup_loginCancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				popup_login.dismiss();
				ll_transparent.setVisibility(View.GONE);
				if (cb_login_user.isChecked()) {
					tool.saveCkeckUserName(getApplicationContext(), "1");
				} else {
					tool.saveCkeckUserName(getApplicationContext(), "0");
				}
			}
		});

		// 获取PopupWindow中的注册取消按钮
		popup_regCancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				popup_reg.dismiss();
				ll_transparent.setVisibility(View.GONE);
			}
		});
		// 所有Handler的处理
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				switch (msg.what) {
				case 0:// 修改密码成功的时候
					initLoginPoppup();
					ll_transparent.setVisibility(View.VISIBLE);
					// 登陆功能
					doLogin();
					break;
				case 1:// 注册成功后的新用户调至登陆页面
					cb_login_user.setChecked(true);
					initLoginPoppup();
					ll_transparent.setVisibility(View.VISIBLE);
					// 登陆功能
					doLogin();
					break;
				case 2:// 登陆失败的新用户，调至注册页面
					if (registerName != null) {
						((EditText) popupView_reg
								.findViewById(R.id.et_reg_user))
								.setText(registerName);
					}
					popup_login.dismiss();
					initRegPoppup();
					ll_transparent.setVisibility(View.VISIBLE);
					// 注册 用户
					registerUser();
					break;
				}
			}
		};
	}

	private String registerName = null;

	/***
	 * 注册新用户
	 */
	private void registerUser() {
		((Button) popupView_login.findViewById(R.id.btn_login)).setText("注册");
		((Button) popupView_login.findViewById(R.id.btn_login))
				.setBackgroundResource(R.drawable.btn_bg2);
		//清除EditText里面的文本
		((EditText) popupView_reg.findViewById(R.id.et_reg_user)).setText("");
		((EditText) popupView_reg.findViewById(R.id.et_reg_pass)).setText("");
		((EditText) popupView_reg.findViewById(R.id.et_reg_surePass)).setText("");
		//
		//限制号牌号码的长度
		InputLengthControler ilc=new InputLengthControler();
		ilc.config(((EditText) popupView_reg.findViewById(R.id.et_reg_user)), ET_MAXLENGTH);
		ilc.config(((EditText) popupView_reg.findViewById(R.id.et_reg_pass)), ET_MAXLENGTH);
		ilc.config(((EditText) popupView_reg.findViewById(R.id.et_reg_surePass)), ET_MAXLENGTH);
		popupView_reg.findViewById(R.id.btn_reg).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						String userName = ((EditText) popupView_reg
								.findViewById(R.id.et_reg_user)).getText()
								.toString().trim();
						String pwd = ((EditText) popupView_reg
								.findViewById(R.id.et_reg_pass)).getText()
								.toString().trim();
						String rePwd = ((EditText) popupView_reg
								.findViewById(R.id.et_reg_surePass)).getText()
								.toString().trim();
						if (userName == null || userName.equals("")) {
							showToast("用户名不能为空。");
						}else if(pwd == null && pwd.equals("")){
							showToast("注册密码不能为空。");
						}else if(rePwd == null && rePwd.equals("")){
							showToast("注册确认密码不能为空。");
						}else if (pwd != null && !pwd.equals("")
								&& rePwd != null && !rePwd.equals("")
								&& rePwd.equals(pwd)) {
							// 　{'yhdh':'tydic','mm':'tydictest','phone_id':'1','phone_type':'123'}
							if (pwd.length() > 12 || pwd.length() < 6) {
								Toast.makeText(MainActivity.this,
										"密码需在6——12位之间", Toast.LENGTH_SHORT)
										.show();
							} else {
								//用户名称的验证
								if(tool.isWMatches2(userName)){
									if(userName.length()==6){
										Map<String, String> conMap = new HashMap<String, String>();
										conMap.put("yhdh", userName);// 查询大类菜单
										conMap.put("mm", pwd);
										conMap.put("phone_id", tool
												.getImieStatus(getApplicationContext()));
										conMap.put("phone_type", "");
										System.out.println("注册参数："
												+ MainActivity.gson.toJson(conMap)
												+ tool.getImieStatus(getApplicationContext()));
										new MyAsyncTaskRE().execute(MainActivity.gson
												.toJson(conMap));

									}else{
										Toast.makeText(getApplicationContext(), "用户名的长度必须是6位。", Toast.LENGTH_LONG).show();
									}
								}else{
									Toast.makeText(getApplicationContext(), "用户名只能是数字(0-9)、字母(a-zA-Z)。", Toast.LENGTH_LONG).show();
								}
							}
						} else {
							Toast.makeText(getApplicationContext(),
									"2次输入的密码不一致，请重新输入。", Toast.LENGTH_SHORT)
									.show();
						}
					}
				});
	}

	/***
	 * 登陆方法
	 */
	@SuppressLint("ParserError")
	private void doLogin() {
		Constant.user = null;
		tv_custom.setText("");
		String isCheck = tool.readIsCheck(getApplicationContext());
		if (isCheck != null && !isCheck.equals("")) {
			if (isCheck.equals("1")) {
				cb_login_user.setChecked(true);
				System.out.println("--记住---");
			} else {
				cb_login_user.setChecked(false);
			}
		}
		if (cb_login_user.isChecked()) {
			String str = tool.readUserName(getApplicationContext());
			System.out.println("--记住---"+str);
			if (str != null && !str.equals("")) {
				et_login_user.setText(str);
			}
		} else {
			et_login_user.setText("");
		}
		((EditText) popupView_login.findViewById(R.id.et_login_pass))
				.setText("");
		((Button) popupView_login.findViewById(R.id.btn_login)).setText("登录");
		((Button) popupView_login.findViewById(R.id.btn_login))
				.setBackgroundResource(R.drawable.btn_bg2);
//		
//		InputLengthControler ilc=new InputLengthControler();
//		ilc.config(((EditText) popupView_login.findViewById(R.id.et_login_pass)), ET_MAXLENGTH);
		popupView_login.findViewById(R.id.btn_login).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if (Constant.user == null) {
							String userName = et_login_user.getText()
									.toString().trim();
							String pwd = ((EditText) popupView_login
									.findViewById(R.id.et_login_pass))
									.getText().toString().trim();
							if (userName == null || userName.equals("")) {
								showToast("用户名不能为空。");
							} else if (pwd == null || pwd.equals("")) {
								showToast("密码不能为空。");
							} else {
								if(userName.length()!=6){
									showToast("用户名是6位的警号。");
								}else if (pwd.length() > 12 || pwd.length() < 6) {
									showToast("密码需在6——12位之间");
								} else {
									//用户名称的验证
									if(tool.isWMatches2(userName)){
										if(userName.length()==6){
											Map<String, String> conMap = new HashMap<String, String>();
											conMap.put("yhdh", userName);// 查询大类菜单
											conMap.put("mm", pwd);
											conMap.put(
													"phone_id",
													tool.getImieStatus(getApplicationContext()));
											saveUserName(userName);//保存用户名
											new MyAsyncTaskREQLogin().execute(gson
													.toJson(conMap));
										}else{
											Toast.makeText(getApplicationContext(), "用户名的长度必须是6位。", Toast.LENGTH_LONG).show();
										}
										//用户名长度不验证的
//										Map<String, String> conMap = new HashMap<String, String>();
//										conMap.put("yhdh", userName);// 查询大类菜单
//										conMap.put("mm", pwd);
//										conMap.put(
//												"phone_id",
//												tool.getImieStatus(getApplicationContext()));
//										new MyAsyncTaskREQLogin().execute(gson
//												.toJson(conMap));
									}else{
										Toast.makeText(getApplicationContext(), "用户名只能是数字(0-9)。", Toast.LENGTH_LONG).show();
									}
								}
							}
						} else {
							Toast.makeText(getApplicationContext(),
									" 您好，" + Constant.user.getXm() + "已经登陆了。",
									Toast.LENGTH_SHORT).show();
						}
					}
				});
	}

	/**
	 * 注册 数据
	 * 
	 * @author zhangyx
	 * 
	 */
	class MyAsyncTaskRE extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			MainActivity.tool.startProgressDialog(getApplicationContext());
			((Button) popupView_login.findViewById(R.id.btn_login))
					.setText("注册中...");
			((Button) popupView_login.findViewById(R.id.btn_login))
					.setBackgroundResource(R.drawable.btn_bg2);
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String resultStr = "";
			// //接口1：----测试成功
			Constant.webService.setMETHOD_NAME(getApplicationContext()
					.getString(R.string.Connection_MethodName));
			Constant.webService.setWEBSERVICE(getApplicationContext()
					.getString(R.string.Connection_Webservice));
			// PDA用户注册申请接口
			if (Constant.webService.connect(new String[] { "jkid", "userid",
					"authid", "QueryXmlDoc" }, new Object[] { "V-REGISTER",
					"SHDIC", "%E1%BC%EA%8F%B0%8E%0D%26%05%8E%A2u%B3%0A%D1%E5",
					params[0] })) {
				if (Constant.webService.getResult() != null) {
					resultStr = Constant.webService.getResult().toString();
				}
			}
			return resultStr;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result != null && !result.equals("")) {
				try {
					JSONObject obj = new JSONObject(result);
					String res = obj.getString("RESULT");
					String data = obj.getString("DATA");
					if (res.equals("TRUE")) {
						tool.saveUserName(getApplicationContext(),
								((EditText) popupView_reg
										.findViewById(R.id.et_reg_user))
										.getText().toString());
						handler.sendEmptyMessage(1);
						// 清空数据
						((EditText) popupView_reg
								.findViewById(R.id.et_reg_user)).setText("");
						((EditText) popupView_reg
								.findViewById(R.id.et_reg_pass)).setText("");
						((EditText) popupView_reg
								.findViewById(R.id.et_reg_surePass))
								.setText("");
						Toast.makeText(getApplicationContext(), data,
								Toast.LENGTH_SHORT).show();
						popup_reg.dismiss();
						ll_transparent.setVisibility(View.GONE);
					} else if (res.equals("FALSE")) {
						Toast.makeText(getApplicationContext(), data,
								Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				Toast.makeText(getApplicationContext(), "网络异常或者服务器异常。",
						Toast.LENGTH_SHORT).show();
			}
			((Button) popupView_login.findViewById(R.id.btn_login))
					.setText("注册");
			((Button) popupView_login.findViewById(R.id.btn_login))
					.setBackgroundResource(R.drawable.btn_bg2);
		}

	};

	/**
	 * 登陆用户
	 * 
	 * @author zhangyx
	 * 
	 */
	class MyAsyncTaskREQLogin extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			MainActivity.tool.startProgressDialog(getApplicationContext());
			// 修改登陆按钮
			// popupView_login
			((Button) popupView_login.findViewById(R.id.btn_login))
					.setText("登陆中...");
			((Button) popupView_login.findViewById(R.id.btn_login))
					.setBackgroundResource(R.drawable.btn_bg3);
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String resultStr = "";
			// //接口1：----测试成功
			Constant.webService.setMETHOD_NAME(getApplicationContext()
					.getString(R.string.Connection_MethodName));
			Constant.webService.setWEBSERVICE(getApplicationContext()
					.getString(R.string.Connection_Webservice));
			// PDA用户登陆接口
			if (Constant.webService.connect(new String[] { "jkid", "userid",
					"authid", "QueryXmlDoc" }, new Object[] { "V-LOGIN",
					"SHDIC", "%E1%BC%EA%8F%B0%8E%0D%26%05%8E%A2u%B3%0A%D1%E5",
					params[0] })) {
				if (Constant.webService.getResult() != null) {
					resultStr = Constant.webService.getResult().toString();
					System.out.println(resultStr + "返回结果");
				}
			}
			return resultStr;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result != null && !result.equals("")) {
				try {
					JSONObject obj = new JSONObject(result);
					String res = obj.getString("RESULT");
					String data = obj.getString("DATA");
					if (res.equals("TRUE")) {
						String data1 = obj.getString("DATA1");
						JSONObject jsonObj = new JSONObject(data1);
						Constant.user = new UserInfo();
						// !","DATA1":"{'userName':'测试','pwd':'111111','phoneId':'869274010285865','xm':'测试'
						Constant.user
								.setUserName(jsonObj.getString("userName"));
						Constant.user.setPwd(jsonObj.getString("pwd"));
						Constant.user.setPhoneId(jsonObj.getString("phoneId"));
						Constant.user.setXm(jsonObj.getString("xm"));
						Toast.makeText(getApplicationContext(), data,
								Toast.LENGTH_SHORT).show();
						doLoginSuccese();
						et_login_user.setText("");
						((EditText) popupView_login
								.findViewById(R.id.et_login_pass)).setText("");
					} else if (res.equals("FALSE")) {
						String bj = obj.getString("BJ");
						if (bj.equals("1")) {
							et_login_user.setText("");
						} else if (bj.equals("2")) {// /该用户未注册，调至注册页面
							registerName = et_login_user.getText().toString();
							handler.sendEmptyMessage(2);
						}
						((EditText) popupView_login
								.findViewById(R.id.et_login_pass)).setText("");
						showToast(data);
						// Toast.makeText(getApplicationContext(), data,
						// Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				((Button) popupView_login.findViewById(R.id.btn_login))
						.setText("登陆");
				((Button) popupView_login.findViewById(R.id.btn_login))
						.setBackgroundResource(R.drawable.btn_bg2);
			} else {
				Toast.makeText(getApplicationContext(), "网络异常或者服务器异常。",
						Toast.LENGTH_SHORT).show();
			}
			((Button) popupView_login.findViewById(R.id.btn_login))
					.setText("登陆");
			((Button) popupView_login.findViewById(R.id.btn_login))
					.setBackgroundResource(R.drawable.btn_bg2);
		}

	};

	/***
	 * 登陆成功---保存用户名称、用户对象
	 */
	private void doLoginSuccese() {
		System.out.println("登陆成功信息：" + Constant.user.getPhoneId() + "---"
				+ Constant.user.getUserName() + "---" + Constant.user.getPwd());
		if (Constant.user != null) {
			tv_custom.setText(Constant.user.getXm() + "--"
					+ Constant.user.getUserName());
		}
		// 保存 用户名称
		saveUserName(Constant.user.getUserName());
		popup_login.dismiss();
		ll_transparent.setVisibility(View.GONE);
		// 清除本地缓存
		tool.RecursionDeleteFile(new File(Constant.localImagePath));
	}
	
	private void saveUserName(String userName){
		// 保存 用户名称
		if (cb_login_user.isChecked()) {
			tool.saveUserName(getApplicationContext(),userName);
			tool.saveCkeckUserName(getApplicationContext(), "1");
		} else {
			tool.saveCkeckUserName(getApplicationContext(), "0");
		}
	}

	/**
	 * 初始化登陆PopupWindw
	 */
	private void initLoginPoppup() {

		// popup.setBackgroundDrawable(new BitmapDrawable());//
		// 这句话很重要，划定popupwindow外部区域
		popup_login.setFocusable(true);
		popup_login.setOutsideTouchable(false);// 设置外部不能能点击
		popup_login.showAtLocation(rl, Gravity.TOP, 0, 120);
		popup_login.update();

	}

	/**
	 * 初始化注册PopupWindw
	 */
	private void initRegPoppup() {

		popup_reg.setFocusable(true);
		popup_reg.setOutsideTouchable(false);// 设置外部不能能点击
		popup_reg.showAtLocation(rl, Gravity.TOP, 0, 120);
		popup_reg.update();

	}

	/**
	 * 初始化服务器地址PopupWindow
	 */
	private void initServerPopup() {
		popup_server.setFocusable(true);
		popup_server.setOutsideTouchable(false);// 设置外部不能能点击
		popup_server.showAtLocation(rl, Gravity.CENTER, 0, 0);
		popup_server.update();
	}

	/**
	 * 初始化界面的数据
	 */
	public void initViewData() {
		serverList = new ArrayList<String>();
		serverList.add("http://60.0.6.40:9080");
		serverList.add("http://60.0.6.220:8080");
		serverList.add("http://192.168.0.43:8080");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/***
	 * 
	 * @author jiangyue 根据当前版本的版本号与从服务端获取的版本号进行比较，如果服务器的版本号大于现在项目的版本号则强制进行升级
	 * 
	 */
	class GetUpdateTask extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... params) {
			String resultStr = "";
			Constant.webService.setMETHOD_NAME(getApplicationContext()
					.getString(R.string.Connection_MethodName));
			Constant.webService.setWEBSERVICE(getApplicationContext()
					.getString(R.string.Connection_Webservice));

			if (Constant.webService.connect(new String[] { "jkid", "userid",
					"authid", "QueryXmlDoc" }, new Object[] { "V-BBJC",
					"SHDIC", "%E1%BC%EA%8F%B0%8E%0D%26%05%8E%A2u%B3%0A%D1%E5",
					params[0] })) {
				if (Constant.webService.getResult() != null) {
					resultStr = Constant.webService.getResult().toString();
					System.out.println(resultStr + "更新");
				} else {
					return null;
				}
			}

			// String url = null;
			// if (Constant.webService.getResult() != null) {
			// url = Constant.webService.getResult().toString();
			// } else {
			// return null;
			// }
			// try {
			// JSONObject obj = new JSONObject(url);
			// return obj;
			// } catch (Exception e) {
			// e.printStackTrace();
			// }
			return resultStr;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if (result != null) {
				// String versionName = result.getString("version");
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
						Toast.makeText(MainActivity.this, "手机唯一识别码不存在！", 1)
								.show();
					}
					PackageInfo packageInfo = MainActivity.this
							.getPackageManager()
							.getPackageInfo(
									MainActivity.this.getApplicationInfo().packageName,
									0);
					String oldVersionName = packageInfo.versionName;
					int versionCode = Integer.parseInt(versionName.replaceAll(
							"[.]", ""));
					int oldVersionCode = Integer.parseInt(oldVersionName
							.replaceAll("[.]", ""));
					if (versionCode > oldVersionCode) { // 新版本比老版本大,需要升级
						AlertDialog dialog = new AlertDialog.Builder(
								MainActivity.this)
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
						Toast.makeText(MainActivity.this, "当前版本是最新版本不需要升级",
								Toast.LENGTH_LONG).show();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				Toast.makeText(MainActivity.this, "未检测到新版本", Toast.LENGTH_LONG)
						.show();
			}
		}
	}

	/**
	 * 
	 * @author jiangyue 下载升级文件的异步任务
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

					fos = MainActivity.this.openFileOutput(tempfile,
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
			if (!MainActivity.this.isFinishing()) {
				progressDialog.dismiss();
				if (result) {
					new AlertDialog.Builder(MainActivity.this)
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
											// Intent upIntent = new Intent();
											// ComponentName componentName = new
											// ComponentName(
											// "cn.tydic.mobile.pdarequery",
											// "MainActivity.class");
											// upIntent.setComponent(componentName);
											// upIntent.setAction("android.intent.action.MAIN");
											// upIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
											// startActivity(upIntent);
										}
									}).setCancelable(false).show();//程序强制安装：false（强制）|true（不强制）

				} else {
					new AlertDialog.Builder(MainActivity.this)
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

	public void showToast(String notice) {
		Toast toast = Toast.makeText(MainActivity.this, notice,
				Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 140);
		toast.show();
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {

		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			if (event.getAction() == KeyEvent.ACTION_UP) {
				IntentDialog.showExitDialog(MainActivity.this);
			}
			return true;
		}
		return super.dispatchKeyEvent(event);
	}

}