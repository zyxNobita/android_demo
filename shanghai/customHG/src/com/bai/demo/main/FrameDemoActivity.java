package com.bai.demo.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.format.DateFormat;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.bai.demo.R;
import com.bai.demo.bean.EntryHead;
import com.bai.demo.bean.UserInfo;
import com.bai.demo.entity.Constant;
import com.bai.demo.entity.ItemInstance;
import com.bai.demo.frame.LeftNavBar;
import com.bai.demo.frame.Menu1Info1Activity;
import com.bai.demo.frame.Menu1Info2Activity;
import com.bai.demo.frame.Menu1Info3Activity;
import com.bai.demo.frame.Menu1Info4Activity;
import com.bai.demo.frame.Menu2Info1Activity;
import com.bai.demo.frame.Menu2Info3Activity;
import com.bai.demo.frame.Menu2Info4Activity;
import com.bai.demo.frame.Menu3Info1Activity;
import com.bai.demo.frame.Menu3Info2Activity;
import com.bai.demo.frame.Menu3Info3Activity;
import com.bai.demo.frame.Menu3Info4Activity;
import com.bai.demo.frame.RightTempActivity;
import com.bai.demo.frame.RightWindowBase;
import com.bai.demo.frame.RightWindowContainer;
import com.bai.demo.frame.RightWindowManager;
import com.bai.demo.utils.ImageUtil;
import com.bai.demo.utils.MDataBaseHelper;
import com.bai.demo.utils.MyApplication;
import com.bai.demo.utils.ToolUtils;
import com.bai.demo.utils.Webservice;
import com.google.gson.Gson;

@SuppressLint("ParserError")
public class FrameDemoActivity extends Activity implements OnClickListener,
		OnGestureListener {
	private final static String MY_LOG = "mylog";
	private RightTempActivity rightTempWindow;
	// 菜单页面
	private Menu1Info1Activity menu1Info1Activity;
	private Menu1Info2Activity menu1Info2Activity;
	private Menu1Info3Activity cygz_txdzglactivity;
	private Menu1Info4Activity cygz_scxxshactivity;
	private Menu2Info1Activity menu2Info1Activity;
	private Menu2Info3Activity menu2Info3Activity;
	private Menu2Info4Activity menu2Info4Activity;
	private Menu3Info1Activity menu3Info1Activity;
	private Menu3Info2Activity menu3Info2Activity;
	private Menu3Info3Activity menu3Info3Activity;
	private Menu3Info4Activity menu3Info4Activity;
	
	private RightWindowContainer mRightWindowContainer;
	private RightWindowManager mRightWindowManager;
	private View getView;
	// 菜单的布局
	private LinearLayout itemLayout;
	private TextView itemText;
	private ImageView itemIcon;
	public static LeftNavBar navbar;
	private ItemInstance itemInstance;

	private View lastView;// 为setbackgroud方法保存上次点击的layout
	private GestureDetector detector;
	private Button btn_sendToast, btn_logout;
	public static UserInfo userInfo;// 用户对象
	public static Handler handler;//
	public static String barCode;//扫描的二维码号码
	public static Bitmap saveBitmap;
	public static WindowManager m;
	public static WindowManager.LayoutParams p;

	private String saveIamgePath;
	private String entryNum;// RM1I1报关单号
	private String entryNumber;// RM1I3报关单号
	private String projectNum;// 表体项号
	public static ToolUtils toolUtils;// 全局的工具类
	public static MyApplication myApp;// 全局变量对象
	public static Gson gson = new Gson();// 全局的Gson对象
	public static MDataBaseHelper db;// 数据库对象 （移动的）
	public static Webservice webservice;//调用网络WebService
	public static ImageUtil imageUtil;//对图片处理的类
	//private final static int CWJ_HEAD_SIZE=6*1024*1024;//定义最小的RAM
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//设置查询的最小RAM
		setContentView(R.layout.main);
		setupViews();
		initView();
		doExecute();

	}

	private void initView() {
		toolUtils = new ToolUtils(this);
		m = this.getWindowManager();
		db = new MDataBaseHelper(this);
		btn_logout = (Button) this.findViewById(R.id.btn_logout);
		myApp = (MyApplication) getApplicationContext();
		userInfo = myApp.getUserInfo();
		webservice=new Webservice(this);
		System.out.println("登录后的用户结果：" + userInfo.getUserName() + "--"
				+ userInfo.getSectionName() + userInfo.getLobNumber()
				+ userInfo.getCustomsName());
		
		showUserInfo();//登录用户信息显示
		checkIsUploadEntrys();// 删除已经上传的数据
		// 初始化 海关部门级别数据
		//new MyAsynTaskCUSTOMS().execute();
		reqCUSTOMS();
		//new MyAsynTaskDEP().execute();
	}

	/***
	 * 用户的信息显示
	 */
	private void showUserInfo() {
		if (userInfo != null && !userInfo.equals("")) {
			((TextView) findViewById(R.id.tv_LobNumber)).setText(userInfo
					.getLobNumber());
			((TextView) findViewById(R.id.tv_UserName)).setText(userInfo
					.getUserName());
			((TextView) findViewById(R.id.tv_CustomsName)).setText(userInfo
					.getCustomsName());
			((TextView) findViewById(R.id.tv_SectionName)).setText(userInfo
					.getSectionName());
		}

	}

	private void doExecute() {
		// 创建手势检测器
		// detector = new GestureDetector(this);
		initCheckFirstMenu();
		// 提示消息的popuwindow
		btn_logout.setOnClickListener(new BTNClickListener());
		handler = new Handler() {
			public void dispatchMessage(android.os.Message msg) {
				int result = msg.what;
				switch (result) {
				case Constant.RINGHT_GROUP1_MENU2://跳转到  1-2 查验单证查询
					onClick(findItem(new int[] { R.id.navbar, R.id.nav_menu1,
							R.id.menu1_info2 }));
					String entryNum = msg.getData().getString("entryNum");
					System.out.println(entryNum + "！！！！！！！！！！");
					if (entryNum != null && !entryNum.equals("")) {
						menu1Info2Activity.et_RM1I2_barCode.setText(entryNum);
					}
					break;

				case Constant.RINGHT_GROUP1_MENU3://跳转到  1-3 查验图像上传
					onClick(findItem(new int[] { R.id.navbar, R.id.nav_menu1,
							R.id.menu1_info3 }));
					String entryId = msg.getData().getString("entryId");
					System.out.println(entryId + "---------");
					if (entryId != null && !entryId.equals("")) {
						cygz_txdzglactivity.et_RM1I3_barCode.setText(entryId);
					}
					break;

				case Constant.RINGHT_GROUP2_MENU1://跳转到  2-1 企业信息查询
					onClick(findItem(new int[] { R.id.navbar, R.id.nav_menu2,
							R.id.menu2_info1 }));
					break;

				case Constant.RINGHT_GROUP2_MENU2://跳转到  2-1 商品信息监控
					onClick(findItem(new int[] { R.id.navbar, R.id.nav_menu2,
							R.id.menu2_info3 }));
					break;

				case Constant.RINGHT_GROUP2_MENU3://跳转到  2-3   查验知识库
					onClick(findItem(new int[] { R.id.navbar, R.id.nav_menu2,
							R.id.menu2_info4 }));
					break;
				}
			};
		};
	}

	private class BTNClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			int vId = v.getId();
			switch (vId) {
			case R.id.btn_logout://注销
				toolUtils.exitUser(FrameDemoActivity.this,
						FrameDemoActivity.this.getString(R.string.sureLogout));
				break;
			}

		}
	}

	public Display requestWindowManager() {
		Display d = m.getDefaultDisplay();
		return d;

	}

	/***
	 * 初始化View的布局
	 */
	private void setupViews() {
		detector = new GestureDetector(this);
		mRightWindowManager = new RightWindowManager();

		navbar = (LeftNavBar) findViewById(R.id.navbar);

		/* 查验工作区 业务1 */
		itemInstance = new ItemInstance(
				getString(R.string.frameDemo_menu_waitCheck), R.drawable.menu_1);

		itemLayout = (LinearLayout) findItem(new int[] { R.id.navbar,
				R.id.nav_menu1, R.id.menu1_info1 });
		itemLayout.setOnClickListener(this);

		setbackgroud(itemLayout);// 设置起始选择菜单

		itemText = (TextView) findItem(new int[] { R.id.navbar, R.id.nav_menu1,
				R.id.menu1_info1, R.id.nav_item_title });
		itemText.setText(itemInstance.getInfoName());
		itemText.setTextColor(Color.WHITE);
		itemIcon = (ImageView) findItem(new int[] { R.id.navbar,
				R.id.nav_menu1, R.id.menu1_info1, R.id.nav_item_icon });
		itemIcon.setImageResource(itemInstance.getIcon());

		/* 查验工作区 业务2 */
		itemInstance = new ItemInstance(
				getString(R.string.frameDemo_menu_stayImgs),
				R.drawable.icon_camera);

		itemLayout = (LinearLayout) findItem(new int[] { R.id.navbar,
				R.id.nav_menu1, R.id.menu1_info2 });
		itemLayout.setOnClickListener(this);

		itemText = (TextView) findItem(new int[] { R.id.navbar, R.id.nav_menu1,

		R.id.menu1_info2, R.id.nav_item_title });
		itemText.setText(itemInstance.getInfoName());
		itemIcon = (ImageView) findItem(new int[] { R.id.navbar,
				R.id.nav_menu1, R.id.menu1_info2, R.id.nav_item_icon });

		itemIcon.setImageResource(itemInstance.getIcon());

		/* 查验工作区 业务3 */
		itemInstance = new ItemInstance(
				getString(R.string.frameDemo_menu_imgsRelated),
				R.drawable.icon_photo);

		itemLayout = (LinearLayout) findItem(new int[] { R.id.navbar,
				R.id.nav_menu1, R.id.menu1_info3 });
		itemLayout.setOnClickListener(this);

		itemText = (TextView) findItem(new int[] { R.id.navbar, R.id.nav_menu1,
				R.id.menu1_info3, R.id.nav_item_title });
		itemText.setText(itemInstance.getInfoName());
		itemIcon = (ImageView) findItem(new int[] { R.id.navbar,
				R.id.nav_menu1, R.id.menu1_info3, R.id.nav_item_icon });
		itemIcon.setImageResource(itemInstance.getIcon());

		/* 查验工作区 业务4 */
		itemInstance = new ItemInstance(
				getString(R.string.frameDemo_menu_uploadAuditing),
				R.drawable.icon_reflect);

		itemLayout = (LinearLayout) findItem(new int[] { R.id.navbar,
				R.id.nav_menu1, R.id.menu1_info4 });
		itemLayout.setOnClickListener(this);

		itemText = (TextView) findItem(new int[] { R.id.navbar, R.id.nav_menu1,
				R.id.menu1_info4, R.id.nav_item_title });
		itemText.setText(itemInstance.getInfoName());
		itemIcon = (ImageView) findItem(new int[] { R.id.navbar,
				R.id.nav_menu1, R.id.menu1_info4, R.id.nav_item_icon });
		itemIcon.setImageResource(itemInstance.getIcon());

		/* 查验辅助决策支持 业务1 */
		itemInstance = new ItemInstance(
				getString(R.string.frameDemo_menu_checkBusinessMsg),
				R.drawable.icon_tag);
		itemLayout = (LinearLayout) findItem(new int[] { R.id.navbar,
				R.id.nav_menu2, R.id.menu2_info1 });
		itemLayout.setOnClickListener(this);

		itemText = (TextView) findItem(new int[] { R.id.navbar, R.id.nav_menu2,
				R.id.menu2_info1, R.id.nav_item_title });
		itemText.setText(itemInstance.getInfoName());
		itemIcon = (ImageView) findItem(new int[] { R.id.navbar,
				R.id.nav_menu2, R.id.menu2_info1, R.id.nav_item_icon });
		itemIcon.setImageResource(itemInstance.getIcon());

		/* 查验辅助决策支持 业务2 */
		itemInstance = new ItemInstance(
				getString(R.string.frameDemo_menu_controlGoodsMsg),
				R.drawable.icon_goods);

		itemLayout = (LinearLayout) findItem(new int[] { R.id.navbar,
				R.id.nav_menu2, R.id.menu2_info3 });
		itemLayout.setOnClickListener(this);

		itemText = (TextView) findItem(new int[] { R.id.navbar, R.id.nav_menu2,
				R.id.menu2_info3, R.id.nav_item_title });
		itemText.setText(itemInstance.getInfoName());
		itemIcon = (ImageView) findItem(new int[] { R.id.navbar,
				R.id.nav_menu2, R.id.menu2_info3, R.id.nav_item_icon });
		itemIcon.setImageResource(itemInstance.getIcon());

		/* 查验辅助决策支持 业务4 */
		itemInstance = new ItemInstance(
				getString(R.string.frameDemo_menu_allCheckLibrary),
				R.drawable.icon_dialog);

		itemLayout = (LinearLayout) findItem(new int[] { R.id.navbar,
				R.id.nav_menu2, R.id.menu2_info4 });
		itemLayout.setOnClickListener(this);

		itemText = (TextView) findItem(new int[] { R.id.navbar, R.id.nav_menu2,
				R.id.menu2_info4, R.id.nav_item_title });
		itemText.setText(itemInstance.getInfoName());
		itemIcon = (ImageView) findItem(new int[] { R.id.navbar,
				R.id.nav_menu2, R.id.menu2_info4, R.id.nav_item_icon });
		itemIcon.setImageResource(itemInstance.getIcon());

		/* 查验辅助决策支持 业务5 */
		// itemInstance = new
		// ItemInstance(getString(R.string.frameDemo_menu_checkStockMsg),
		// R.drawable.icon_box);
		//
		// itemLayout = (LinearLayout) findItem(new int[] { R.id.navbar,
		// R.id.nav_menu2, R.id.menu2_info2 });
		// itemLayout.setOnClickListener(this);
		//
		// itemText = (TextView) findItem(new int[] { R.id.navbar,
		// R.id.nav_menu2,
		// R.id.menu2_info2, R.id.nav_item_title });
		// itemText.setText(itemInstance.getInfoName());
		// itemIcon = (ImageView) findItem(new int[] { R.id.navbar,
		// R.id.nav_menu2, R.id.menu2_info2, R.id.nav_item_icon });
		// itemIcon.setImageResource(itemInstance.getIcon());

		/* 工作量统计 业务菜单项1 */

		itemInstance = new ItemInstance(
				getString(R.string.frameDemo_menu_owerChecks),
				R.drawable.icon_chart);

		itemLayout = (LinearLayout) findItem(new int[] { R.id.navbar,
				R.id.nav_menu3, R.id.menu3_info1 });
		itemLayout.setOnClickListener(this);

		itemText = (TextView) findItem(new int[] { R.id.navbar, R.id.nav_menu3,
				R.id.menu3_info1, R.id.nav_item_title });
		itemText.setText(itemInstance.getInfoName());
		itemIcon = (ImageView) findItem(new int[] { R.id.navbar,
				R.id.nav_menu3, R.id.menu3_info1, R.id.nav_item_icon });
		itemIcon.setImageResource(itemInstance.getIcon());

		/* 工作量统计 业务菜单项2 */
		itemInstance = new ItemInstance(
				getString(R.string.frameDemo_menu_department),
				R.drawable.icon_piechart);

		itemLayout = (LinearLayout) findItem(new int[] { R.id.navbar,
				R.id.nav_menu3, R.id.menu3_info2 });
		itemLayout.setOnClickListener(this);

		itemText = (TextView) findItem(new int[] { R.id.navbar, R.id.nav_menu3,
				R.id.menu3_info2, R.id.nav_item_title });
		itemText.setText(itemInstance.getInfoName());
		itemIcon = (ImageView) findItem(new int[] { R.id.navbar,
				R.id.nav_menu3, R.id.menu3_info2, R.id.nav_item_icon });
		itemIcon.setImageResource(itemInstance.getIcon());

		/* 工作量统计 业务菜单项3 */
		itemInstance = new ItemInstance(
				getString(R.string.frameDemo_menu_checkOfficialPerformance),
				R.drawable.main_menu_temp11);

		itemLayout = (LinearLayout) findItem(new int[] { R.id.navbar,
				R.id.nav_menu3, R.id.menu3_info3 });
		itemLayout.setOnClickListener(this);

		itemText = (TextView) findItem(new int[] { R.id.navbar, R.id.nav_menu3,
				R.id.menu3_info3, R.id.nav_item_title });
		itemText.setText(itemInstance.getInfoName());
		itemIcon = (ImageView) findItem(new int[] { R.id.navbar,
				R.id.nav_menu3, R.id.menu3_info3, R.id.nav_item_icon });
		itemIcon.setImageResource(itemInstance.getIcon());

		/* 工作量统计 业务菜单项4 */
		itemInstance = new ItemInstance(
				getString(R.string.frameDemo_menu_checkDepartmentPerformance),
				R.drawable.main_menu_temp12);

		itemLayout = (LinearLayout) findItem(new int[] { R.id.navbar,
				R.id.nav_menu3, R.id.menu3_info4 });
		itemLayout.setOnClickListener(this);

		itemText = (TextView) findItem(new int[] { R.id.navbar, R.id.nav_menu3,
				R.id.menu3_info4, R.id.nav_item_title });
		itemText.setText(itemInstance.getInfoName());
		itemIcon = (ImageView) findItem(new int[] { R.id.navbar,
				R.id.nav_menu3, R.id.menu3_info4, R.id.nav_item_icon });
		itemIcon.setImageResource(itemInstance.getIcon());

		rightTempWindow = (RightTempActivity) findViewById(R.id.rw_temp);

		mRightWindowContainer = (RightWindowContainer) findViewById(R.id.container);
		mRightWindowManager.setmContainer(mRightWindowContainer);

		// findMenuViewItem
		// menu1_info3=this.findViewById(R.id.menu1_info3);
	}

	/**
	 * 通过布局id，在多布局嵌套，找到最底层的item组件
	 * 
	 * @param Ids
	 */
	private View findItem(int[] Ids) {
		if (Ids.length > 0) {

			for (int i = 0; i < Ids.length; i++) {
				if (i == 0) {
					getView = findViewById(Ids[0]);
				} else {
					getView = getView.findViewById(Ids[i]);
				}
			}
		}
		return getView;

	}

	private void showRightWindow(int num, RightWindowBase mRightWindowBase) {
		mRightWindowManager.showRightWindow(num, mRightWindowBase);
	}

	/**
	 * layout 点击后更换背景颜色
	 * 
	 * @param v
	 */
	private void setbackgroud(View v) {

		// 上次点击的view
		if (lastView == null) {
			lastView = v;
		}
		// 获得字体，设置上次的字体还原为黑色
		TextView tv_title_old = (TextView) lastView
				.findViewById(R.id.nav_item_title);
		TextView tv_title = (TextView) v.findViewById(R.id.nav_item_title);
		ImageView iv_menuChange = (ImageView) v
				.findViewById(R.id.nav_item_icon);
		ImageView lastImageView = (ImageView) lastView
				.findViewById(R.id.nav_item_icon);

		tv_title_old.setTextColor(Color.BLACK);

		LayoutParams layout = new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT);

		changeMenuBackground(v, iv_menuChange, lastImageView);
		// 还原上次
		lastView.setBackgroundResource(R.drawable.main_menu_tempbg);
		// 设置animation
		final TranslateAnimation animationF = new TranslateAnimation(0, 20, 0,
				0);
		animationF.setDuration(200);
		animationF.setRepeatCount(1);
		animationF.setRepeatMode(Animation.REVERSE);
		tv_title.setAnimation(animationF);

		// final ScaleAnimation scaleAnimation=new ScaleAnimation(0, 1.5f, 0,
		// 1.5f, 1, 1);
		// scaleAnimation.setDuration(2000);
		iv_menuChange.setAnimation(animationF);
		final AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
		alphaAnimation.setDuration(500);
		iv_menuChange.setAnimation(alphaAnimation);
		// 设置当前
		v.setBackgroundResource(R.drawable.right_background);
		layout.rightMargin = 0;
		v.setLayoutParams(layout);
		lastView = v;
	}

	/**
	 * 默认选择菜单
	 */
	private void initCheckFirstMenu() {
		if (menu1Info1Activity == null) {
			menu1Info1Activity = new Menu1Info1Activity(FrameDemoActivity.this);
		}
		showRightWindow(RightWindowManager.MENU1INFO1, menu1Info1Activity);
	}

	// 设置 权限的-----后续权限的设置是否可以点击
	public void onClick(View v) {
		changeBg(v);
		int id = v.getId();
		switch (id) {
		//查验工作区
		case R.id.menu1_info1://待查验列表
			initCheckFirstMenu();
			break;
		case R.id.menu1_info2://查验单证查询
			if (menu1Info2Activity == null) {
				menu1Info2Activity = new Menu1Info2Activity(
						FrameDemoActivity.this);
			}
			showRightWindow(RightWindowManager.MENU1INFO2, menu1Info2Activity);
			break;

		case R.id.menu1_info3://查验图像上传
			if (cygz_txdzglactivity == null) {
				cygz_txdzglactivity = new Menu1Info3Activity(
						FrameDemoActivity.this);

			}
			showRightWindow(RightWindowManager.txdzgl, cygz_txdzglactivity);
			break;

		case R.id.menu1_info4://上传信息审核
			if (cygz_scxxshactivity == null) {
				cygz_scxxshactivity = new Menu1Info4Activity(
						FrameDemoActivity.this);
			}
			showRightWindow(RightWindowManager.scxxsh, cygz_scxxshactivity);
			break;
		// --------辅助决策支持
		case R.id.menu2_info1://企业信息查询
			if (menu2Info1Activity == null) {
				menu2Info1Activity = new Menu2Info1Activity(
						FrameDemoActivity.this);
			}
			showRightWindow(RightWindowManager.MENU2INFO1, menu2Info1Activity);
			break;
		case R.id.menu2_info3://商品信息监控
			if (menu2Info3Activity == null) {
				menu2Info3Activity = new Menu2Info3Activity(
						FrameDemoActivity.this);
			}
			showRightWindow(RightWindowManager.MENU2INFO3, menu2Info3Activity);
			break;
		case R.id.menu2_info4://查验知识库
			if (menu2Info4Activity == null) {
				menu2Info4Activity = new Menu2Info4Activity(
						FrameDemoActivity.this);
			}
			showRightWindow(RightWindowManager.MENU2INFO4, menu2Info4Activity);
			break;

		// ----工作量统计
		case R.id.menu3_info1://个人查验单证
			if (menu3Info1Activity == null) {
				menu3Info1Activity = new Menu3Info1Activity(
						FrameDemoActivity.this);
			}
			showRightWindow(RightWindowManager.MENU3, menu3Info1Activity);
			break;

		case R.id.menu3_info2://科室查验单证
			if (menu3Info2Activity == null) {
				menu3Info2Activity = new Menu3Info2Activity(
						FrameDemoActivity.this);
			}
			showRightWindow(RightWindowManager.MENU3INFO2, menu3Info2Activity);
			break;

		case R.id.menu3_info3://查验关员绩效
			if (menu3Info3Activity == null) {
				menu3Info3Activity = new Menu3Info3Activity(
						FrameDemoActivity.this);
			}
			showRightWindow(RightWindowManager.MENU3INFO3, menu3Info3Activity);
			break;
		case R.id.menu3_info4://查验部门绩效
			if (menu3Info4Activity == null) {
				menu3Info4Activity = new Menu3Info4Activity(
						FrameDemoActivity.this);
			}
			showRightWindow(RightWindowManager.MENU3INFO4, menu3Info4Activity);
			break;
		default:
			if (rightTempWindow == null) {
				rightTempWindow = new RightTempActivity(FrameDemoActivity.this);
			}
			showRightWindow(RightWindowManager.TEMP_WINDOW, rightTempWindow);
			break;
		}
		;
	}

	/**
	 * 改变菜单View的背景
	 * @param v
	 */
	private void changeBg(View v) {
		ImageView iv_menuChange = (ImageView) v
				.findViewById(R.id.nav_item_icon);
		TextView tv_title = (TextView) v.findViewById(R.id.nav_item_title);
		setbackgroud(v);// 设置layout点击后的背景
		tv_title.setTextColor(Color.WHITE);
	}

	private void changeMenuBackground(View v, ImageView currentView,
			ImageView lastImageView) {
		int vId = v.getId();
		switch (vId) {
		case R.id.menu1_info1:
			currentView.setImageResource(R.drawable.menu_1);
			currentView.setTag("1");
			break;
		case R.id.menu1_info2:
			currentView.setImageResource(R.drawable.menu_2);
			currentView.setTag("2");
			break;
		case R.id.menu1_info3:
			currentView.setImageResource(R.drawable.menu_3);
			currentView.setTag("3");
			break;

		case R.id.menu1_info4:
			currentView.setImageResource(R.drawable.menu_4);
			currentView.setTag("4");
			break;
		// --------以上是第一个菜单的

		case R.id.menu2_info1:
			currentView.setImageResource(R.drawable.menu_5);
			currentView.setTag("5");
			break;

		case R.id.menu2_info3:
			currentView.setImageResource(R.drawable.menu_7);
			currentView.setTag("7");
			break;

		case R.id.menu2_info4:
			currentView.setImageResource(R.drawable.menu_8);
			currentView.setTag("8");
			break;

		// ----以上是第二个菜单的
		case R.id.menu3_info1:
			currentView.setImageResource(R.drawable.menu_9);
			currentView.setTag("9");
			break;

		case R.id.menu3_info2:
			currentView.setImageResource(R.drawable.menu_10);
			currentView.setTag("10");
			break;

		case R.id.menu3_info3:
			currentView.setImageResource(R.drawable.menu_11);
			currentView.setTag("11");
			break;

		case R.id.menu3_info4:
			currentView.setImageResource(R.drawable.menu_12);
			currentView.setTag("12");
			break;

		}
		chengeImageBack(lastImageView);
	}

	private void chengeImageBack(ImageView lastImageView) {
		String iv_tag = lastImageView.getTag().toString();
		if (iv_tag.equals("1")) {
			lastImageView.setImageResource(R.drawable.icon_list);
		} else if (iv_tag.equals("2")) {
			lastImageView.setImageResource(R.drawable.icon_camera);
		} else if (iv_tag.equals("3")) {
			lastImageView.setImageResource(R.drawable.icon_photo);
		} else if (iv_tag.equals("4")) {
			lastImageView.setImageResource(R.drawable.icon_reflect);
		} else if (iv_tag.equals("5")) {
			lastImageView.setImageResource(R.drawable.icon_tag);
		} else if (iv_tag.equals("6")) {
			lastImageView.setImageResource(R.drawable.icon_box);
		} else if (iv_tag.equals("7")) {
			lastImageView.setImageResource(R.drawable.icon_goods);
		} else if (iv_tag.equals("8")) {
			lastImageView.setImageResource(R.drawable.icon_dialog);
		} else if (iv_tag.equals("9")) {
			lastImageView.setImageResource(R.drawable.icon_chart);
		} else if (iv_tag.equals("10")) {
			lastImageView.setImageResource(R.drawable.icon_piechart);
		} else if (iv_tag.equals("11")) {
			lastImageView.setImageResource(R.drawable.main_menu_temp11);
		} else if (iv_tag.equals("12")) {
			lastImageView.setImageResource(R.drawable.main_menu_temp12);
		}

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// 将该Activity上的触碰事件交给GestureDetector处理
		return detector.onTouchEvent(event);
	}

	public boolean onDown(MotionEvent e) {
		return false;
	}

	public void onShowPress(MotionEvent e) {

	}

	public boolean onSingleTapUp(MotionEvent e) {
		return false;
	}

	public boolean onFling(MotionEvent event1, MotionEvent event2,
			float velocityX, float velocityY) {
		if (event1 == null || event2 == null) {
			return true;
		}
		/*
		 * 如果第一个触点事件的X座标大于第二个触点事件的X座标超过FLIP_DISTANCE 也就是手势从右向左滑。
		 */
		if (event1.getX() - event2.getX() > Constant.FLIP_DISTANCE) {
			if (navbar.getVisibility() != View.GONE)
				navbar.setVisibility(View.GONE);
			return true;
		}
		/*
		 * 如果第二个触点事件的X座标大于第一个触点事件的X座标超过FLIP_DISTANCE 也就是手势从左向右滑。
		 */
		else if (event2.getX() - event1.getX() > Constant.FLIP_DISTANCE) {
			if (navbar.getVisibility() != View.VISIBLE)
				navbar.setVisibility(View.VISIBLE);
			return true;
		}
		return false;
	}

	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		return false;
	}

	public void onLongPress(MotionEvent e) {

	}
	/***
	 * 所有页面的ActivityResult返回数据处理
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == Activity.RESULT_OK && null != data) {
			String imageName;
			String concreteDataTime;
			entryNum = myApp.getEntryNum();
			entryNumber = myApp.getEntryNumber();
			projectNum = myApp.getProjectNum();
			concreteDataTime = toolUtils
					.requestData(Constant.CONCRETE_DATATIME);
			if (requestCode == Constant.REQCAMES_FIRST) {//1-1 表头
				imageName = entryNum + "_" + Constant.FORM_HEAD + "_"
						+ Constant.CONTAINER_BODY + "_" + concreteDataTime
						+ ".jpg";
				saveImage(requestCode, imageName, data,
						Constant.REQCAMES_FIRST, Constant.CONTAINER_BODY);
			} else if (requestCode == Constant.REQCAMES_SECOND) {//1-2 表头
				imageName = entryNum + "_" + Constant.FORM_HEAD + "_"
						+ Constant.GOODSTACK_SITUATION + "_" + concreteDataTime
						+ ".jpg";
				saveImage(requestCode, imageName, data,
						Constant.REQCAMES_SECOND, Constant.GOODSTACK_SITUATION);
			} else if (requestCode == Constant.REQCAMES_THREE) {//1-3 表头
				imageName = entryNum + "_" + Constant.FORM_HEAD + "_"
						+ Constant.CONTAINER_MARK + "_" + concreteDataTime
						+ ".jpg";
				saveImage(requestCode, imageName, data,
						Constant.REQCAMES_THREE, Constant.CONTAINER_MARK);
			} else if (requestCode == Constant.RM1I3_TAKEPHONE_FIEST) {//3-1 表头
				imageName = entryNumber + "_" + Constant.FORM_HEAD + "_"
						+ Constant.CONTAINER_BODY + "_" + concreteDataTime
						+ ".jpg";
				saveImage(requestCode, imageName, data,
						Constant.RM1I3_TAKEPHONE_FIEST, Constant.CONTAINER_BODY);
			} else if (requestCode == Constant.RM1I3_TAKEPHONE_TWO) {//3-2 表头
				imageName = entryNumber + "_" + Constant.FORM_HEAD + "_"
						+ Constant.GOODSTACK_SITUATION + "_" + concreteDataTime
						+ ".jpg";
				saveImage(requestCode, imageName, data,
						Constant.RM1I3_TAKEPHONE_TWO, Constant.GOODSTACK_SITUATION);
			} else if (requestCode == Constant.RM1I3_TAKEPHONE_THREE) {//3-3表头
				imageName = entryNumber + "_" + Constant.FORM_HEAD + "_"
						+ Constant.CONTAINER_MARK + "_" + concreteDataTime
						+ ".jpg";
				saveImage(requestCode, imageName, data,
						Constant.RM1I3_TAKEPHONE_THREE, Constant.CONTAINER_MARK);
			} else if (requestCode == Constant.REQUPLOADCAMES_FIRST) {//3-1表体
				imageName = entryNumber + "_" + projectNum + "_"
						+ Constant.GOODS_PACKAGE + "_" + concreteDataTime
						+ ".jpg";
				saveImage(requestCode, imageName, data,
						Constant.REQUPLOADCAMES_FIRST, Constant.GOODS_PACKAGE);
			} else if (requestCode == Constant.REQUPLOADCAMES_SECOND) {//3-2表体
				imageName = entryNumber + "_" + projectNum + "_"
						+ Constant.GOODS + "_" + concreteDataTime
						+ ".jpg";
				saveImage(requestCode, imageName, data,
						Constant.REQUPLOADCAMES_SECOND, Constant.GOODS);
			} else if (requestCode == Constant.REQUPLOADCAMES_THIRD) {//3-3表体
				imageName = entryNumber + "_" + projectNum + "_"
						+ Constant.CONTAINER_INNER + "_" + concreteDataTime
						+ ".jpg";
				saveImage(requestCode, imageName, data,
						Constant.REQUPLOADCAMES_THIRD, Constant.CONTAINER_INNER);
			} else if (requestCode == Constant.REQUPLOADCAMES_FORTH) {//3-4表体
				imageName = entryNumber + "_" + projectNum + "_"
						+ Constant.GOODS_SPECIFICATION_MODEL + "_"
						+ concreteDataTime + ".jpg";
				saveImage(requestCode, imageName, data,
						Constant.REQUPLOADCAMES_FORTH, Constant.GOODS_SPECIFICATION_MODEL);
			} else if (requestCode == Constant.REQUPLOADCAMES_FIFTH) {//3-5表体
				imageName = entryNumber + "_" + projectNum + "_"
						+ Constant.ANOTHER_SPECIAL_REQUEST + "_"
						+ concreteDataTime + ".jpg";
				saveImage(requestCode, imageName, data,
						Constant.REQUPLOADCAMES_FIFTH, Constant.ANOTHER_SPECIAL_REQUEST);
			} else if (requestCode == Constant.REQUPLOADCAMES_SIXTH) {//3-6表体
				imageName = entryNumber + "_" + projectNum + "_"
						+ Constant.SCENE_SRC + "_"
						+ concreteDataTime + ".jpg";
				saveImage(requestCode, imageName, data,
						Constant.REQUPLOADCAMES_SIXTH, Constant.SCENE_SRC);
			}
		
			//本地上传---保存到数据库|并且复制到文集夹-|并且显示
			myApp.picturePath="";
			if(requestCode==Constant.REQ_LOACHOSTINAGE_RM1_G1){//1-1 G1 
				myApp.picturePath=FrameDemoActivity.toolUtils.reqLoachostIamgePath(data);
				
				//并且复制到文集夹
				saveIamgePath = Constant.SAVEPATH + entryNum
						+ Constant.SAVE_FORMHEADIMAGEUIR +entryNum+"_"+Constant.FORM_HEAD+"_"+Constant.CONTAINER_BODY
						+ "/";
				if(!myApp.picturePath.equals(saveIamgePath)){
					imageName = entryNum + "_" + Constant.FORM_HEAD + "_"
							+ Constant.CONTAINER_BODY + "_" + concreteDataTime
							+ ".jpg";
					toolUtils.loachostImageSave(myApp.picturePath, saveIamgePath, imageName);
					//数据库
					toolUtils.saveEntryToSQLite(entryNum, Constant.G_NO_HEAD, Constant.CONTAINER_BODY,
							saveIamgePath+imageName);
				}
				//并且显示
				Menu1Info1Activity.handler_RM1I1.sendEmptyMessage(Constant.REQCAMES_FIRST);
			}else if(requestCode==Constant.REQ_LOACHOSTINAGE_RM1_G2){//1-1 G2 
				
				myApp.picturePath=FrameDemoActivity.toolUtils.reqLoachostIamgePath(data);
				//并且复制到文集夹
				saveIamgePath = Constant.SAVEPATH + entryNum
						+ Constant.SAVE_FORMHEADIMAGEUIR
						+ entryNum+"_"+Constant.FORM_HEAD+"_"+Constant.GOODSTACK_SITUATION + "/";
				if(!myApp.picturePath.equals(saveIamgePath)){
					imageName = entryNum + "_" + Constant.FORM_HEAD + "_"
							+ Constant.GOODSTACK_SITUATION + "_" + concreteDataTime
							+ ".jpg";
					toolUtils.loachostImageSave(myApp.picturePath, saveIamgePath, imageName);
					//数据库的展示
					toolUtils.saveEntryToSQLite(entryNum, Constant.G_NO_HEAD, Constant.GOODSTACK_SITUATION,
							saveIamgePath+imageName);
				}
				Menu1Info1Activity.handler_RM1I1.sendEmptyMessage(Constant.REQCAMES_SECOND);
			}else if(requestCode==Constant.REQ_LOACHOSTINAGE_RM1_G3){//1-1 G3 
				myApp.picturePath=FrameDemoActivity.toolUtils.reqLoachostIamgePath(data);
				//并且复制到文集夹
				saveIamgePath = Constant.SAVEPATH + entryNum
						+ Constant.SAVE_FORMHEADIMAGEUIR +entryNum+"_"+Constant.FORM_HEAD+"_"+ Constant.CONTAINER_MARK
						+ "/";
				if(!myApp.picturePath.equals(saveIamgePath)){//当本地上传的图片路径与要存的路径不相同时，操作下面的
					imageName = entryNum + "_" + Constant.FORM_HEAD + "_"
							+ Constant.CONTAINER_MARK + "_" + concreteDataTime
							+ ".jpg";
					toolUtils.loachostImageSave(myApp.picturePath, saveIamgePath, imageName);
					//数据库
					toolUtils.saveEntryToSQLite(entryNum, Constant.G_NO_HEAD, Constant.CONTAINER_MARK,
							saveIamgePath+imageName);
				}
				//展示
				Menu1Info1Activity.handler_RM1I1.sendEmptyMessage(Constant.REQCAMES_THREE);
			}else if(requestCode==Constant.REQ_LOACHOSTINAGE_RM3_G1){//1-3 表头 G1 
				myApp.picturePath=FrameDemoActivity.toolUtils.reqLoachostIamgePath(data);
				//并且复制到文集夹
				saveIamgePath = Constant.SAVEPATH + entryNumber
						+ Constant.SAVE_FORMHEADIMAGEUIR +entryNumber+"_"+Constant.FORM_HEAD+"_"+ Constant.CONTAINER_BODY
						+ "/";
				if(!myApp.picturePath.equals(saveIamgePath)){//当本地上传的图片路径与要存的路径不相同时，操作下面的
					imageName = entryNumber + "_" + Constant.FORM_HEAD + "_"
							+ Constant.CONTAINER_BODY + "_" + concreteDataTime
							+ ".jpg";
					toolUtils.loachostImageSave(myApp.picturePath, saveIamgePath, imageName);
					//数据库
					toolUtils.saveEntryToSQLite(entryNumber, Constant.G_NO_HEAD, Constant.CONTAINER_BODY,
							saveIamgePath+imageName);
				}
				//展示
				Menu1Info3Activity.handler_RM1I3.sendEmptyMessage(Constant.RM1I3_TAKEPHONE_FIEST);
			}else if(requestCode==Constant.REQ_LOACHOSTINAGE_RM3_G2){//1-3 表头 G2 
				myApp.picturePath=FrameDemoActivity.toolUtils.reqLoachostIamgePath(data);
				//并且复制到文集夹
				saveIamgePath = Constant.SAVEPATH + entryNumber
						+ Constant.SAVE_FORMHEADIMAGEUIR +entryNumber+"_"+Constant.FORM_HEAD+"_"+ Constant.GOODSTACK_SITUATION
						+ "/";
				if(!myApp.picturePath.equals(saveIamgePath)){//当本地上传的图片路径与要存的路径不相同时，操作下面的
					imageName = entryNumber + "_" + Constant.FORM_HEAD + "_"
							+ Constant.GOODSTACK_SITUATION + "_" + concreteDataTime
							+ ".jpg";
					toolUtils.loachostImageSave(myApp.picturePath, saveIamgePath, imageName);
					//数据库
					toolUtils.saveEntryToSQLite(entryNumber, Constant.G_NO_HEAD, Constant.GOODSTACK_SITUATION,
							saveIamgePath+imageName);
				}
				//展示
				Menu1Info3Activity.handler_RM1I3.sendEmptyMessage(Constant.RM1I3_TAKEPHONE_TWO);
			}else if(requestCode==Constant.REQ_LOACHOSTINAGE_RM3_G3){//1-3 表头 G3 
				myApp.picturePath=FrameDemoActivity.toolUtils.reqLoachostIamgePath(data);
				//并且复制到文集夹
				saveIamgePath = Constant.SAVEPATH + entryNumber
						+ Constant.SAVE_FORMHEADIMAGEUIR +entryNumber+"_"+Constant.FORM_HEAD+"_"+ Constant.CONTAINER_MARK
						+ "/";
				if(!myApp.picturePath.equals(saveIamgePath)){//当本地上传的图片路径与要存的路径不相同时，操作下面的
					imageName = entryNumber + "_" + Constant.FORM_HEAD + "_"
							+ Constant.CONTAINER_MARK + "_" + concreteDataTime
							+ ".jpg";
					toolUtils.loachostImageSave(myApp.picturePath, saveIamgePath, imageName);
					//数据库
					toolUtils.saveEntryToSQLite(entryNumber, Constant.G_NO_HEAD, Constant.CONTAINER_MARK,
							saveIamgePath+imageName);
				}
				//展示
				Menu1Info3Activity.handler_RM1I3.sendEmptyMessage(Constant.RM1I3_TAKEPHONE_THREE);
			}else if(requestCode==Constant.REQ_LOACHOSTINAGE_RM3_G4){//1-3 表体 G4 
				myApp.picturePath=FrameDemoActivity.toolUtils.reqLoachostIamgePath(data);
				//并且复制到文集夹
				saveIamgePath = Constant.SAVEPATH + entryNumber
						+ "/" +projectNum + "/" +entryNumber+"_" + projectNum + "_" + Constant.GOODS_PACKAGE
						+ "/";
				if(!myApp.picturePath.equals(saveIamgePath)){//当本地上传的图片路径与要存的路径不相同时，操作下面的
					imageName = entryNumber + "_" + projectNum + "_"
							+ Constant.GOODS_PACKAGE + "_" + concreteDataTime
							+ ".jpg";
					toolUtils.loachostImageSave(myApp.picturePath, saveIamgePath, imageName);
					//数据库
					toolUtils.saveEntryToSQLite(entryNumber, projectNum, Constant.GOODS_PACKAGE,
							saveIamgePath+imageName);
				}
				//展示
				Menu1Info3Activity.handler_RM1I3.sendEmptyMessage(Constant.REQUPLOADCAMES_FIRST);
			}else if(requestCode==Constant.REQ_LOACHOSTINAGE_RM3_G5){//1-3 表体 G5 
				myApp.picturePath=FrameDemoActivity.toolUtils.reqLoachostIamgePath(data);
				//并且复制到文集夹
				saveIamgePath = Constant.SAVEPATH + entryNumber
						+ "/" +projectNum + "/" +entryNumber+"_" + projectNum + "_" + Constant.GOODS
						+ "/";
				if(!myApp.picturePath.equals(saveIamgePath)){//当本地上传的图片路径与要存的路径不相同时，操作下面的
					imageName = entryNumber + "_" + projectNum + "_"
							+ Constant.GOODS + "_" + concreteDataTime
							+ ".jpg";
					toolUtils.loachostImageSave(myApp.picturePath, saveIamgePath, imageName);
					//数据库
					toolUtils.saveEntryToSQLite(entryNumber, projectNum, Constant.GOODS,
							saveIamgePath+imageName);
				}
				//展示
				Menu1Info3Activity.handler_RM1I3.sendEmptyMessage(Constant.REQUPLOADCAMES_SECOND);
			}else if(requestCode==Constant.REQ_LOACHOSTINAGE_RM3_G6){//1-3 表体 G6 
				myApp.picturePath=FrameDemoActivity.toolUtils.reqLoachostIamgePath(data);
				//并且复制到文集夹
				saveIamgePath = Constant.SAVEPATH + entryNumber
						+ "/" +projectNum + "/" +entryNumber+"_" + projectNum + "_" + Constant.CONTAINER_INNER
						+ "/";
				if(!myApp.picturePath.equals(saveIamgePath)){//当本地上传的图片路径与要存的路径不相同时，操作下面的
					imageName = entryNumber + "_" + projectNum + "_"
							+ Constant.CONTAINER_INNER + "_" + concreteDataTime
							+ ".jpg";
					toolUtils.loachostImageSave(myApp.picturePath, saveIamgePath, imageName);
					//数据库
					toolUtils.saveEntryToSQLite(entryNumber, projectNum, Constant.CONTAINER_INNER,
							saveIamgePath+imageName);
				}
				//展示
				Menu1Info3Activity.handler_RM1I3.sendEmptyMessage(Constant.REQUPLOADCAMES_THIRD);
			}else if(requestCode==Constant.REQ_LOACHOSTINAGE_RM3_G7){//1-3 表体 G7 
				myApp.picturePath=FrameDemoActivity.toolUtils.reqLoachostIamgePath(data);
				//并且复制到文集夹
				saveIamgePath = Constant.SAVEPATH + entryNumber
						+ "/" +projectNum + "/" +entryNumber+"_" + projectNum + "_" + Constant.GOODS_SPECIFICATION_MODEL
						+ "/";
				if(!myApp.picturePath.equals(saveIamgePath)){//当本地上传的图片路径与要存的路径不相同时，操作下面的
					imageName = entryNumber + "_" + projectNum + "_"
							+ Constant.GOODS_SPECIFICATION_MODEL + "_" + concreteDataTime
							+ ".jpg";
					toolUtils.loachostImageSave(myApp.picturePath, saveIamgePath, imageName);
					//数据库
					toolUtils.saveEntryToSQLite(entryNumber, projectNum, Constant.GOODS_SPECIFICATION_MODEL,
							saveIamgePath+imageName);
				}
				//展示
				Menu1Info3Activity.handler_RM1I3.sendEmptyMessage(Constant.REQUPLOADCAMES_FORTH);
			}else if(requestCode==Constant.REQ_LOACHOSTINAGE_RM3_G8){//1-3 表体 G8 
				myApp.picturePath=FrameDemoActivity.toolUtils.reqLoachostIamgePath(data);
				//并且复制到文集夹
				saveIamgePath = Constant.SAVEPATH + entryNumber
						+ "/" +projectNum + "/" +entryNumber+"_" + projectNum + "_" + Constant.ANOTHER_SPECIAL_REQUEST
						+ "/";
				if(!myApp.picturePath.equals(saveIamgePath)){//当本地上传的图片路径与要存的路径不相同时，操作下面的
					imageName = entryNumber + "_" + projectNum + "_"
							+ Constant.ANOTHER_SPECIAL_REQUEST + "_" + concreteDataTime
							+ ".jpg";
					toolUtils.loachostImageSave(myApp.picturePath, saveIamgePath, imageName);
					//数据库
					toolUtils.saveEntryToSQLite(entryNumber, projectNum, Constant.ANOTHER_SPECIAL_REQUEST,
							saveIamgePath+imageName);
				}
				//展示
				Menu1Info3Activity.handler_RM1I3.sendEmptyMessage(Constant.REQUPLOADCAMES_FIFTH);
			}else if(requestCode==Constant.REQ_LOACHOSTINAGE_RM3_G9){//1-3 表体 G9 
				myApp.picturePath=FrameDemoActivity.toolUtils.reqLoachostIamgePath(data);
				//并且复制到文集夹
				saveIamgePath = Constant.SAVEPATH + entryNumber
						+ "/" +projectNum + "/" +entryNumber+"_" + projectNum + "_" + Constant.SCENE_SRC
						+ "/";
				if(!myApp.picturePath.equals(saveIamgePath)){//当本地上传的图片路径与要存的路径不相同时，操作下面的
					imageName = entryNumber + "_" + projectNum + "_"
							+ Constant.SCENE_SRC + "_" + concreteDataTime
							+ ".jpg";
					toolUtils.loachostImageSave(myApp.picturePath, saveIamgePath, imageName);
					//数据库
					toolUtils.saveEntryToSQLite(entryNumber, projectNum, Constant.SCENE_SRC,
							saveIamgePath+imageName);
				}
				//展示
				Menu1Info3Activity.handler_RM1I3.sendEmptyMessage(Constant.REQUPLOADCAMES_SIXTH);
			}
			// 条形码操作
			if (requestCode == Constant.REQ_SCAN_FIRST) {
				barCode = data.getStringExtra("num");
				if (barCode != null && !barCode.equals("")) {
					Menu1Info2Activity.handler_RM1I2
							.sendEmptyMessage(Constant.REQ_SCAN_FIRST);
				}
			} else if (requestCode == Constant.REQ_SCAN_SECOND) {
				barCode = data.getStringExtra("num");
				if (barCode != null && !barCode.equals("")) {
					System.out.println("requestCode....." + requestCode);
					Menu1Info3Activity.handler_RM1I3
							.sendEmptyMessage(Constant.REQ_SCAN_SECOND);
				}
			}

			// 视频的刻录：
			if (requestCode == Constant.RECORDER_VIDEO) {
				if (Environment.getExternalStorageState().equals(
						Environment.MEDIA_MOUNTED)) {
					try {
						AssetFileDescriptor videoAsset = getContentResolver()
								.openAssetFileDescriptor(data.getData(), "r");
						FileInputStream fis = videoAsset.createInputStream();
						// /sdcard/HG/
//						saveIamgePath = Environment.getExternalStorageDirectory()+Constant.SAVEPATH + entryNumber + "/"
//								+ projectNum + "/" + Constant.VIDEO;
//						File tmFile = new File(saveIamgePath);// 创建目录下的文件
//						
						File tmFile=new File(Environment.getExternalStorageDirectory()+"/HG/videoRecorder");//创建目录下的文件
						if (!tmFile.exists()) {
							tmFile.mkdir();
						}
						 
						String str = tmFile.getPath() + "/" + entryNumber + "_"
								+ projectNum + "_" + Constant.VIDEO + "_"
								+ concreteDataTime + ".3gp";
						;
						FileOutputStream fos = new FileOutputStream(str);
						byte[] buf = new byte[1024];
						int len;
						while ((len = fis.read(buf)) > 0) {
							fos.write(buf, 0, len);
						}
						fis.close();
						fos.close();
					} catch (FileNotFoundException e) {
						e.printStackTrace();// libcore.io.ErrnoException:open
											// failed:EnoEnt
					} catch (IOException e) {
						e.printStackTrace();
					}
					Menu1Info3Activity.handler_RM1I3
							.sendEmptyMessage(Constant.RECORDER_VIDEO);
				} else {
					Toast.makeText(getApplicationContext(),
							"移动设备的SDCard不存在。。。。", 500).show();
				}
			}

		}

	}

	// 图片拍照后的处理
	private void saveImage(int requestCode, String imageName, Intent data,
			int version, String imageType) {
		String vagueDataTime;
		String sdState = Environment.getExternalStorageState();
		// 拍照记录
		if (!sdState.equals(Environment.MEDIA_MOUNTED)) {
			return;
		}
		new DateFormat();
		vagueDataTime = toolUtils.requestData(Constant.VAGUE_DATATIME);
		// 未加 报关单号 /HG/userNumber/taday/manifestNumber/ image
		if (requestCode == Constant.REQCAMES_FIRST) {//1-1 表头
			saveIamgePath = Constant.SAVEPATH + entryNum
					+ Constant.SAVE_FORMHEADIMAGEUIR +entryNum+"_"+Constant.FORM_HEAD+"_"+Constant.CONTAINER_BODY
					+ "/";
			toolUtils.saveEntryToSQLite(entryNum, Constant.G_NO_HEAD, imageType,
					saveIamgePath+imageName);
		} else if (requestCode == Constant.REQCAMES_SECOND) {//1-2 表头
			saveIamgePath = Constant.SAVEPATH + entryNum
					+ Constant.SAVE_FORMHEADIMAGEUIR
					+ entryNum+"_"+Constant.FORM_HEAD+"_"+Constant.GOODSTACK_SITUATION + "/";
			toolUtils.saveEntryToSQLite(entryNum, Constant.G_NO_HEAD, imageType,
					saveIamgePath+imageName);
		} else if (requestCode == Constant.REQCAMES_THREE) {//1-3表头
			saveIamgePath = Constant.SAVEPATH + entryNum
					+ Constant.SAVE_FORMHEADIMAGEUIR +entryNum+"_"+Constant.FORM_HEAD+"_"+ Constant.CONTAINER_MARK
					+ "/";
			toolUtils.saveEntryToSQLite(entryNum, Constant.G_NO_HEAD, imageType,
					saveIamgePath+imageName);
		} else if (requestCode == Constant.RM1I3_TAKEPHONE_FIEST) {//3-1 表头
			saveIamgePath = Constant.SAVEPATH + entryNumber
					+ Constant.SAVE_FORMHEADIMAGEUIR
					+entryNumber+"_"+Constant.FORM_HEAD+"_"+ Constant.CONTAINER_BODY + "/";
			toolUtils.saveEntryToSQLite(entryNumber, Constant.G_NO_HEAD, imageType,
					saveIamgePath+imageName);
		} else if (requestCode == Constant.RM1I3_TAKEPHONE_TWO) {//3-2 表头
			saveIamgePath = Constant.SAVEPATH + entryNumber
					+ Constant.SAVE_FORMHEADIMAGEUIR
					+entryNumber+"_"+Constant.FORM_HEAD+"_"+Constant.GOODSTACK_SITUATION + "/";
			toolUtils.saveEntryToSQLite(entryNumber, Constant.G_NO_HEAD, imageType,
					saveIamgePath+imageName);
		} else if (requestCode == Constant.RM1I3_TAKEPHONE_THREE) {//3-3 表头
			saveIamgePath = Constant.SAVEPATH + entryNumber
					+ Constant.SAVE_FORMHEADIMAGEUIR
					+entryNumber+"_"+Constant.FORM_HEAD+"_"+Constant.CONTAINER_MARK + "/";
			toolUtils.saveEntryToSQLite(entryNumber, Constant.G_NO_HEAD, imageType,
					saveIamgePath+imageName);
		} else if (requestCode == Constant.REQUPLOADCAMES_FIRST) {//3-1 表体
			saveIamgePath = Constant.SAVEPATH + entryNumber + "/" + projectNum
					+ "/" +entryNumber+"_"+projectNum+"_"+Constant.GOODS_PACKAGE + "/";
			toolUtils.saveEntryToSQLite(entryNumber, projectNum, imageType,
					saveIamgePath+imageName);
		} else if (requestCode == Constant.REQUPLOADCAMES_SECOND) {//3-2 表体
			saveIamgePath = Constant.SAVEPATH + entryNumber + "/" + projectNum
					+ "/" +entryNumber+"_"+projectNum+"_"+ Constant.GOODS + "/";
			toolUtils.saveEntryToSQLite(entryNumber, projectNum, imageType,
					saveIamgePath+imageName);
		} else if (requestCode == Constant.REQUPLOADCAMES_THIRD) {//3-3 表体
			saveIamgePath = Constant.SAVEPATH + entryNumber + "/" + projectNum
					+ "/" +entryNumber+"_"+projectNum+"_"+ Constant.CONTAINER_INNER + "/";
			toolUtils.saveEntryToSQLite(entryNumber, projectNum, imageType,
					saveIamgePath+imageName);
		} else if (requestCode == Constant.REQUPLOADCAMES_FORTH) {//3-4 表体
			saveIamgePath = Constant.SAVEPATH + entryNumber + "/" + projectNum
					+ "/" + entryNumber+"_"+projectNum+"_"+Constant.GOODS_SPECIFICATION_MODEL + "/";
			toolUtils.saveEntryToSQLite(entryNumber, projectNum, imageType,
					saveIamgePath+imageName);
		} else if (requestCode == Constant.REQUPLOADCAMES_FIFTH) {//3-5 表体
			saveIamgePath = Constant.SAVEPATH + entryNumber + "/" + projectNum
					+ "/" +entryNumber+"_"+projectNum+"_"+ Constant.ANOTHER_SPECIAL_REQUEST + "/";
			toolUtils.saveEntryToSQLite(entryNumber, projectNum, imageType,
					saveIamgePath+imageName);
		} else if (requestCode == Constant.REQUPLOADCAMES_SIXTH) {//3-6 表体
			saveIamgePath = Constant.SAVEPATH + entryNumber + "/" + projectNum
					+ "/" + entryNumber+"_"+projectNum+"_"+Constant.SCENE_SRC + "/";
			toolUtils.saveEntryToSQLite(entryNumber, projectNum, imageType,
					saveIamgePath+imageName);
		}

		Bundle bundle = data.getExtras();
		// 获取相机返回的数据，并转换为图片格式
		saveBitmap = (Bitmap) bundle.get("data");
		boolean resultBool = toolUtils.writeToSDcard(saveIamgePath, imageName,
				saveBitmap);
		if (resultBool) {

			// 显示图片
			// Message msg=new Message();
			// msg.getData().putCharSequence("filename", filename);
			// Menu1Info1Activity.handler_RM1I1.sendMessage(msg);
			// 添加到SQLite数据库，然后通知页面改变
			if (requestCode == Constant.REQCAMES_FIRST
					|| requestCode == Constant.REQCAMES_SECOND
					|| requestCode == Constant.REQCAMES_THREE) {
				Menu1Info1Activity.handler_RM1I1.sendEmptyMessage(version);
			} else if (requestCode == Constant.RM1I3_TAKEPHONE_FIEST
					|| requestCode == Constant.RM1I3_TAKEPHONE_TWO
					|| requestCode == Constant.RM1I3_TAKEPHONE_THREE
					|| requestCode == Constant.REQUPLOADCAMES_FIRST
					|| requestCode == Constant.REQUPLOADCAMES_SECOND
					|| requestCode == Constant.REQUPLOADCAMES_THIRD
					|| requestCode == Constant.REQUPLOADCAMES_FORTH
					|| requestCode == Constant.REQUPLOADCAMES_FIFTH
					|| requestCode == Constant.REQUPLOADCAMES_SIXTH) {
				Menu1Info3Activity.handler_RM1I3.sendEmptyMessage(version);
			}
		} else {
			Toast.makeText(FrameDemoActivity.this, "保存图片失败", Toast.LENGTH_SHORT)
					.show();
		}
	}

//	// 保存数据到本地数据库
//	@SuppressLint({ "ParserError" })
//	private void saveEntryToSQLite(String Entry_ID, int G_No,
//			String Photo_list_Code, String Photo_list_ID) {
//		// 插入所有表体信息：
//		// INSERT INTO Entry_List(Entry_ID,G_No) values('报关单号',项号)
//		// INSERT INTO Photo_list(Entry_ID,G_No,Photo_list_Code,Photo_list_ID)
//		// db.insertToEntry_LIST(Entry_ID, G_No, CODE_TS, G_NAME, isYs, Remark)
//		// db.insertToEntry_Head(Entry_ID, State, Save_Time, Upload_Time)
//		long row = db.insertToPhoto_list(Entry_ID, G_No, Photo_list_Code,
//				Photo_list_ID);
//
//		if (G_No != 0) {
//			// 表体
//			// db.insertToEntry_LIST(Entry_ID, G_No, CODE_TS, G_NAME, isYs,
//			// Remark)
//		}
//		System.out.println("saveEntryToSQLite----" + row);
//	}

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

	// 查询出 已经上传了的数据（在本地数据库）
	private void checkIsUploadEntrys() {
		ArrayList<EntryHead> heads = db.selectFromEntryHead();
		if (heads != null) {
			for (EntryHead h : heads) {
				if (h.getUpload_Time() != null) {
					db.delete(h.getUpload_Time());
					// 删除 文件
					deleteEntryFiles(h.getEntry_ID());
				}
			}
		}
	}

	// 删除报关单文件夹
	private void deleteEntryFiles(String entryId) {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			String path = Constant.SAVEPATH + entryId;
			File file = new File(path);
			if (file.exists()) {
				toolUtils.RecursionDeleteFile(file);
			}
		}
	}

	/***
	 * 初始化 工作量统计的数据
	 */
	@SuppressWarnings("static-access")
	private void reqCUSTOMS(){
		Thread thread=new Thread(new Runnable() {
			
			@Override
			public void run() {
				//  查询关区
				String reqResult = "";
				webservice.setMETHOD_NAME("GetAllCustoms");
				webservice
						.setSOAP_ACTION("http://tempuri.org/IGetExamDataService/GetAllCustoms");
				if (webservice.connect(getApplicationContext(), null, null)) {
					reqResult = webservice.getResult().toString();
					System.out.println("工作量统计-  查询关区：" + reqResult);
				}
				if(reqResult!=null && !reqResult.equals("")){
					try {
						JSONArray arr = new JSONArray(reqResult);
						FrameDemoActivity.myApp.CUSTOMS.clear();
						if (arr != null && arr.length() > 0) {
							for (int i = 0; i < arr.length(); i++) {
								JSONObject obj = arr.getJSONObject(i);
								FrameDemoActivity.myApp.CUSTOMS.add(obj.getString("CUSTOMS_NAME"));
								System.out.println("obj.getString(CUSTOMS_NAME)"+obj.getString("CUSTOMS_NAME"));
							}
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
				
				webservice.setMETHOD_NAME("GetDepsName");
				webservice
						.setSOAP_ACTION("http://tempuri.org/IGetExamDataService/GetDepsName");
				if (webservice.connect(getApplicationContext(), null, null)) {
					reqResult="";
					reqResult = webservice.getResult().toString();
					System.out.println("工作量统计-  查询科室：" + reqResult);
				}
				if(reqResult!=null && !reqResult.equals("")){
					try {
						JSONArray arr = new JSONArray(reqResult);
						FrameDemoActivity.myApp.DEP_NAME.clear();
						if (arr != null && arr.length() > 0) {
							for (int i = 0; i < arr.length(); i++) {
								JSONObject obj = arr.getJSONObject(i);
								FrameDemoActivity.myApp.DEP_NAME.add(obj.getString("DEP_NAME"));
							}
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}

				}
				
			}
		});
		thread.setDaemon(true);//守护线程
		thread.start();
		//thread.dumpStack();//
	}

}