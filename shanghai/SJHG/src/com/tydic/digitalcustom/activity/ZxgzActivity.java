package com.tydic.digitalcustom.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tydic.digitalcustom.R;
import com.tydic.digitalcustom.entity.Constant;
import com.tydic.digitalcustom.entity.IntervalColorAdapter;
import com.tydic.digitalcustom.entity.MyAdapterForRadioBtn;
import com.tydic.digitalcustom.entity.MyGestureUtil;
import com.tydic.digitalcustom.entity.MyJason;
import com.tydic.digitalcustom.entity.Webservice;
import com.tydic.digitalcustom.tools.Tools;
import com.tydic.digitalcustom.widget.MyDialog;
import com.tydic.digitalcustom.widget.PopupMenu;

/**
 * 
 * Description:专项工作业务模块 ZxgzActivity.java Create on 2013-7-8 上午10:27:58
 * 
 * @author wangwx
 * @version 1.0 Copyright (c) 2013 Company,Inc. All Rights Reserved.
 */
@SuppressLint({ "SetJavaScriptEnabled", "SetJavaScriptEnabled", "HandlerLeak" })
public class ZxgzActivity extends Activity {

	private ListView frameMenuList;
	private LinearLayout contentLayout;
	protected LayoutInflater inflater;
	private RelativeLayout index_layout;
	private LinearLayout menu_layout;
	private PopupMenu popupMenu;
	private int count, sheetStatue = Constant.ZOOM_OUT;
	private long firClick, secClick;
	private Handler menu3Handler, inHandler;// 菜单控制、内部功能控制
	private Button search;
	private WebView mWebView;
	private View contentView = null;
	private WebSettings webSettings = null;
	private Handler mHandler = new Handler();
	// private ProgressDialog progressDialog;
	private List<Map<String, Object>> listData = new ArrayList<Map<String, Object>>();
	private List<Map<String, Object>> statisData;
	private MyDialog selectDialog;
	private ImageButton zoomBtn;
	private int mode;
	private final int ZOOM = 1, NONE = -1;
	private float oldDist, newDist;

	/**
	 * 0:视角按钮 1：搜索按钮 zoom 0显示为放大按钮，1显示为缩小
	 */
	protected int zoomSelect = 0;
	private int chooseBlock;// 选择的业务模块

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_frame);
		inflater = LayoutInflater.from(this);
		contentLayout = (LinearLayout) findViewById(R.id.menu_frame_content);

		frameMenuList = (ListView) findViewById(R.id.menu_frame_menulist);
		frameMenuList.setAdapter(new MyAdapterForRadioBtn(this, getResources()
				.getStringArray(R.array.zxgzMenu)));
		menu_layout = (LinearLayout) findViewById(R.id.layout_menu);
		menu3Handler = new Handler() {
			@Override
			public void dispatchMessage(Message msg) {
				switch (msg.what + Constant.TAB_YWL * 3) {
				// 通关无纸化
				case Constant.TAB_YWL * 3 + Constant.MENU_YWL + Constant.CHILD1:
					initTgwzh();
					break;
				// 查验报告
				case Constant.TAB_YWL * 3 + Constant.MENU_YWL * 2
						+ Constant.CHILD1:
					initCybg();
					break;

				default:
					Toast.makeText(ZxgzActivity.this, "页面未添加",
							Toast.LENGTH_SHORT).show();

					break;
				}
			}

			/**
			 * 
			 * Description:初始化通关无纸化 Date:2013-7-8
			 * 
			 * @author wangwx
			 * @return void
			 */
			private void initTgwzh() {
				contentLayout.removeAllViews();
				// 全关区日报表
				contentView = getLayoutInflater().inflate(
						R.layout.tgwzh_qgqrbb, null, false);
				initRightLayout();
				getTable(Constant.CUSTOM_TGWZH,
						new Object[] { null, null, null });
				initZoomBtn();
				chooseBlock = Constant.CUSTOM_TGWZH;
				// 查询按钮
				search = (Button) contentView.findViewById(R.id.searchbutton);
				search.setOnClickListener(searchClick);

				contentLayout.addView(contentView);
				MyDialog.removeMyDialogInstance(getApplicationContext(),
						R.style.dialog, chooseBlock);
				Tools.createWebView(contentView, R.id.webview,
						"initData('','','')",
						"file:///android_asset/zxgz_tgwzh_bar.html");
			}

			/**
			 * 
			 * Description:初始化查验报告 Date:2013-7-8
			 * 
			 * @author wangwx
			 * @return void
			 */
			private void initCybg() {
				contentLayout.removeAllViews();
				contentView = getLayoutInflater().inflate(
						R.layout.cytjrb_qgqbb, null, false);
				initRightLayout();
				getTable(Constant.CUSTOM_CYRB,
						new Object[] { null, null, null });
				initZoomBtn();
				chooseBlock = Constant.CUSTOM_CYRB;
				search = (Button) contentView.findViewById(R.id.searchbutton);
				search.setOnClickListener(searchClick);
				contentLayout.addView(contentView);
				MyDialog.removeMyDialogInstance(getApplicationContext(),
						R.style.dialog, chooseBlock);
				Tools.createWebView(contentView, R.id.webview,
						"initData('','','')",
						"file:///android_asset/zxgz_cytj_line.html");
			}

		};

		inHandler = new Handler() {
			public void handleMessage(Message msg) {

				switch (msg.arg1) {
				case Constant.WEBSERVICE_OVER:
					// progressDialog.dismiss();
					install_lst(msg.arg2);
					break;
				case Constant.CUSTOM_SEARCH:// 查询条件查询展示
					mWebView.loadUrl("javascript:initData(\""
							+ selectDialog.getStandardResult() + "\"," + "'"
							+ selectDialog.getStandardDataFrom() + "'" + ","
							+ "'" + selectDialog.getStandardDataTo() + "'"
							+ ")");
					if (selectDialog.getStandardResult() != null)
						Log.d("++++++发出请求的sql-",
								"javascript:initData(\""
										+ selectDialog.getStandardResult()
										+ "\"," + "'"
										+ selectDialog.getStandardDataFrom()
										+ "'" + "," + "'"
										+ selectDialog.getStandardDataTo()
										+ "'" + ")");
					// msg.arg2为选择的查询选项 通关无纸化或者查验日报
					invalidateTable(msg.arg2,
							new String[] { selectDialog.getStandardResult(),
									selectDialog.getStandardDataFrom(),
									selectDialog.getStandardDataTo() });
					// mWebView.loadUrl("javascript:initData('0','1','2')");
					selectDialog.dismiss();
					break;

				default:
					break;
				}
			}
		};
		frameMenuList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				switch (position) {
				case 0:
					popupMenu = new PopupMenu(ZxgzActivity.this, view,
							R.array.zxgz_tgwzh_child, menu3Handler, position);
					popupMenu.show(PopupMenu.RIGHT);
					break;
				case 1:
					popupMenu = new PopupMenu(ZxgzActivity.this, view,
							R.array.zxgz_cytjrb_child, menu3Handler, position);
					popupMenu.show(PopupMenu.RIGHT);
					break;
				default:
					contentLayout.removeAllViews();
					Toast.makeText(ZxgzActivity.this, "页面未建立！",
							Toast.LENGTH_SHORT).show();
					break;
				}

			}

		});

	}

	/**
	 * 初始化右侧表格、图表
	 */
	private void initRightLayout() {
		index_layout = (RelativeLayout) contentView
				.findViewById(R.id.tgwzh_table);
		index_layout.setOnTouchListener(new OnTouchListener() {

			// 双击放大列表
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (MotionEvent.ACTION_DOWN == event.getAction()) {
					count++;
					if (count == 1) {
						firClick = System.currentTimeMillis();

					} else if (count == 2) {
						secClick = System.currentTimeMillis();
						if (secClick - firClick < 1000) {
							Log.d("sheetStatue", String.valueOf(sheetStatue));
							// 双击事件
							if (sheetStatue == Constant.ZOOM_OUT) {
								zoomBtn.setVisibility(View.GONE);
								zoomSheet(Constant.ZOOM_IN);
							} else if (sheetStatue == Constant.ZOOM_IN) {
								zoomBtn.setVisibility(View.VISIBLE);
								zoomSheet(Constant.ZOOM_OUT);
							}
						}
						count = 0;
						firClick = 0;
						secClick = 0;

					}
				}
				return false;
			}
		});

		mWebView = (WebView) contentView.findViewById(R.id.webview);
		webSettings = mWebView.getSettings();
		webSettings.setSavePassword(false);
		webSettings.setSaveFormData(false);
		webSettings.setJavaScriptEnabled(true);
		webSettings.setSupportZoom(true);
		mWebView.setBackgroundColor(0);
		mWebView.setWebChromeClient(new MyWebChromeClient());
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.addJavascriptInterface(new DemoJavaScriptInterface(), "demo");

	}

	/**
	 * 通关无纸化、查验报告点击查询按钮出现查询条件
	 */
	OnClickListener searchClick = new OnClickListener() {
		@Override
		public void onClick(View v) {

			selectDialog = MyDialog.getMyDialogInstance(ZxgzActivity.this,
					R.style.dialog, chooseBlock);
			// 点击查询按钮执行业务逻辑
			selectDialog.setInHandler(inHandler);

			/*
			 * //传入参数 关区名，时间起始 getDataByNet(Constant.CUSTOM_TGWZH, new Object[]
			 * { selectDialog.getResult(), selectDialog.getStandardDataFrom(),
			 * selectDialog.getStandardDataTo() });
			 */
			selectDialog.setCanceledOnTouchOutside(true);// 设置点击Dialog外部任意区域关闭Dialog
			selectDialog.show();
		}
	};

	/**
	 * 根据查询条件更新列表数据展示
	 */
	private void invalidateTable(int choice, String arg[]) {
		Log.d("-----", "刷新列表！");
		getTable(choice, arg);
	}

	final class DemoJavaScriptInterface {
		DemoJavaScriptInterface() {
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
					Toast.makeText(ZxgzActivity.this, "点击了Webview",
							Toast.LENGTH_SHORT).show();
					mWebView.loadUrl("javascript:loadChart()");
				}
			});
		}
	}

	/**
	 * * Provides a hook for calling "alert" from javascript. Useful for *
	 * debugging your javascript.
	 */
	final class MyWebChromeClient extends WebChromeClient {
		@Override
		public boolean onJsAlert(WebView view, String url, String message,
				JsResult result) {
			result.confirm();
			return true;
		}
	}

	/**
	 * 
	 * Description:通关无纸化数据生成 Date:2013-7-8
	 * 
	 * @author wangwx
	 * @param choice
	 *            用户选取的业务模块
	 * @return void
	 */
	private void getTable(final int choice, final Object[] arg) {

		// progressDialog = ProgressDialog.show(ZxgzActivity.this, "正在查询",
		// "请稍后...", true, true);
		// progressDialog = new ProgressDialog(this);
		// progressDialog.show();
		// 获取动态数据
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {

				if (choice == Constant.CUSTOM_CYRB) {
					listData = getDataByNet(choice, arg);// wangwx
				} else if (choice == Constant.CUSTOM_TGWZH) {
					listData = getDataByNet(choice, arg);
				}
				Message msg = new Message();
				msg.arg1 = Constant.WEBSERVICE_OVER;
				msg.arg2 = choice;
				inHandler.sendMessage(msg);
			}
		});
		thread.start();
	}

	/**
	 * 
	 * Description:查验报告双层列表装载 Date:2013-7-8
	 * 
	 * @author wangwx
	 * @return void
	 */
	private void install_lst_cybg() {
		// 开始构建列表展示资源
		int left_to_itemId[] = null, right_to_itemId[] = null;
		String[] left_form_Item = null, right_form_Item = null;
		int left_resource = 0, right_resource = 0;

		left_to_itemId = new int[] { R.id.tgwzh_zb, R.id.tgwzh_lshg };
		right_to_itemId = new int[] { R.id.tgwzh_gqdm, R.id.tgwzh_jgs,
				R.id.tgwzh_jds, R.id.tgwzh_bkcys, R.id.tgwzh_bkcyl,
				R.id.tgwzh_bkchs_baohan, R.id.tgwzh_bkchl_baohan,
				R.id.tgwzh_bkchs_buhan, R.id.tgwzh_bkchl_buhan };

		left_form_Item = new String[] { "ZMC", "CUS_NAME" };
		right_form_Item = new String[] { "CUS_CODE", "END_MAYCHK_SUM",
				"REC_MAYCHK_SUM", "BKCYS", "BKCYL", "BKCHS", "BKCHL",
				"YSJSFGCHS", "YSJSFGCHL" };
		left_resource = R.layout.table_item_left;
		right_resource = R.layout.table_item_right;

		if (listData == null) {
			Toast.makeText(getApplicationContext(), "无列表数据展示！请检查网络设置！",
					Toast.LENGTH_SHORT).show();
			return;
		}
		// 左侧菜单统计
		statisData = new ArrayList<Map<String, Object>>();
		String tempCUS_NAME = "", tempZMC = "";
		for (int i = 0; i < listData.size(); i++) {
			statisData.add(listData.get(i));
			if (i == 0) {
				tempCUS_NAME = (String) listData.get(i).get("CUS_NAME");
				tempZMC = (String) listData.get(i).get("ZMC");
			} else {
				if (tempCUS_NAME.equals(listData.get(i).get("CUS_NAME"))) {
					Map<String, Object> map = statisData.get(i);
					map.put("CUS_NAME", "");
					statisData.set(i, map);
				} else {
					tempCUS_NAME = (String) listData.get(i).get("CUS_NAME");
				}
				if (tempZMC.equals(listData.get(i).get("ZMC"))) {

					Map<String, Object> map = statisData.get(i);
					map.put("ZMC", "");
					statisData.set(i, map);
				} else {
					tempZMC = (String) listData.get(i).get("ZMC");
				}
			}

			IntervalColorAdapter leftAdapter = new IntervalColorAdapter(
					getApplicationContext(), statisData, left_resource,
					left_form_Item, left_to_itemId);
			leftAdapter.setJudgeColumn("CUS_NAME");
			final ListView leftList = (ListView) contentView
					.findViewById(R.id.index_left_listView);
			leftList.setAdapter(leftAdapter);

			IntervalColorAdapter rightAdapter = new IntervalColorAdapter(
					getApplicationContext(), listData, right_resource,
					right_form_Item, right_to_itemId);
			rightAdapter.setJudgeColumn("CUS_NAME");
			final ListView rightList = (ListView) contentView
					.findViewById(R.id.table_list);//
			rightList.setAdapter(rightAdapter);

			leftList.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					rightList.onTouchEvent(event);
					return false;
				}
			});

			rightList.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					System.out.println("右边滑动");
					leftList.dispatchTouchEvent(event);

					switch (event.getActionMasked()) {

					case MotionEvent.ACTION_POINTER_DOWN:// 多点触碰

						oldDist = MyGestureUtil.spacing(event);
						mode = ZOOM;
						Log.d("----", "111111111111");
						break;
					case MotionEvent.ACTION_MOVE:// 移动

						if (mode == ZOOM) {
							Log.d("----", "多点移动捕获");
							newDist = MyGestureUtil.spacing(event);
						}
						break;
					case MotionEvent.ACTION_UP:
						mode = NONE;
						break;

					case MotionEvent.ACTION_POINTER_UP:
						Log.d("----", "多点大捕获");
						mode = NONE;
						// 判断触摸事件放大缩小事件
						if (MyGestureUtil.isZoomIn(oldDist, newDist)) {
							zoom(Constant.SHEET_IN);
							/*
							 * Toast.makeText(ZxgzActivity.this, "你想放大",
							 * Toast.LENGTH_SHORT).show();
							 */
						} else {
							zoom(Constant.SHEET_OUT);
							/*
							 * Toast.makeText(ZxgzActivity.this, "你想缩小",
							 * Toast.LENGTH_SHORT).show();
							 */
						}
						break;
					}

					return true;
				}
			});
		}
	}

	/**
	 * 
	 * Description:通关无纸化单列表装载 Date:2013-7-8
	 * 
	 * @author wangwx
	 * @return void
	 */
	private void install_lst_tgwzh() {
		if (listData == null) {
			Toast.makeText(getApplicationContext(), "无列表数据展示！请检查网络设置！",
					Toast.LENGTH_SHORT).show();
			return;
		}
		// 开始构建列表展示资源
		int to_itemId[] = { R.id.table_one_line_title1,
				R.id.table_one_line_title2, R.id.table_one_line_title3,
				R.id.table_one_line_title4, R.id.table_one_line_title5 };
		String[] form_Item = { "MASTER_CUSTOMS_CODE", "CUSTOMS_CODE",
				"ENTRY_COUNT_A", "ENTRY_COUNT_WZ", "WZ_RATIO" };
		int resource = R.layout.table_one_line_title;

		// 左侧菜单统计
		statisData = new ArrayList<Map<String, Object>>();
		String tempCUS_NAME = "";
		for (int i = 0; i < listData.size(); i++) {
			statisData.add(listData.get(i));
			if (i == 0) {
				tempCUS_NAME = (String) listData.get(i).get(
						"MASTER_CUSTOMS_CODE");
			} else {

				if (tempCUS_NAME.equals(listData.get(i).get(
						"MASTER_CUSTOMS_CODE"))) {
					Map<String, Object> map = statisData.get(i);
					map.put("MASTER_CUSTOMS_CODE", "");
					statisData.set(i, map);
				} else {
					tempCUS_NAME = (String) listData.get(i).get(
							"MASTER_CUSTOMS_CODE");
				}
			}
		}

		IntervalColorAdapter adapter = new IntervalColorAdapter(this,
				statisData, resource, form_Item, to_itemId);
		adapter.setJudgeColumn("MASTER_CUSTOMS_CODE");
		ListView listView = (ListView) contentView
				.findViewById(R.id.table_list);
		listView.setAdapter(adapter);
	}

	private void install_lst(int whichList) {
		TextView tableTitle = (TextView) contentView
				.findViewById(R.id.index_text);

		switch (whichList) {
		case Constant.CUSTOM_CYRB:
			// 设置表格标题
			tableTitle.setText(getResources().getString(
					R.string.table_name_cybg));
			install_lst_cybg();
			break;
		case Constant.CUSTOM_TGWZH:
			tableTitle.setText(getResources().getString(
					R.string.table_name_tgwzh));
			install_lst_tgwzh();

			break;

		default:
			break;
		}

	}

	/**
	 * 
	 * Description:根绝选择查询相应的webservice获取数据 Date:2013-7-8
	 * 
	 * @author wangwx
	 * @param choice
	 *            用户选择的模块
	 * @param arg
	 *            查询的参数
	 * @return
	 * @return List<Map<String,Object>> 返回的数据结果
	 */
	private List<Map<String, Object>> getDataByNet(int choice, Object[] arg) {
		Webservice webservice = Webservice.getInstance();
		if (choice == Constant.CUSTOM_CYRB) {
			return getCybgData(arg, webservice);
		} else if (choice == Constant.CUSTOM_TGWZH) {
			return getTgwzhData(arg, webservice);
		}
		return null;
	}

	/**
	 * 
	 * Description:获取通关无纸化的数据 Date:2013-7-8
	 * 
	 * @author wangwx
	 * @param arg
	 * @param webservice
	 * @return
	 * @return List<Map<String,Object>>
	 */
	private List<Map<String, Object>> getTgwzhData(Object[] arg,
			Webservice webservice) {
		webservice.setMETHOD_NAME("getWZHExprotAll");

		if (webservice.connect(new String[] { "MASTER_CUSTOMS_CODE",
				"startTime", "endTime" },
				new Object[] { arg[0], arg[1], arg[2] })) {
			if (webservice.getResult() != null) {
				MyJason myJason = new MyJason(webservice.getResult().toString());
				Log.d("--------------通关无纸化返回数据", webservice.getResult()
						.toString());
				// 关区中文名 关区代码 报关单总量 通关无纸化报关单总量 无纸率
				return myJason.jasonToList(new String[] {
						"MASTER_CUSTOMS_CODE", "CUSTOMS_CODE", "ENTRY_COUNT_A",
						"ENTRY_COUNT_WZ", "WZ_RATIO" });
			}
		}
		return null;
	}

	/**
	 * 
	 * Description:获取查验报告的数据 Date:2013-7-8
	 * 
	 * @author wangwx
	 * @param arg
	 * @param webservice
	 * @return void
	 */
	private List<Map<String, Object>> getCybgData(Object[] arg,
			Webservice webservice) {
		webservice.setMETHOD_NAME("getExamPort");
		if (webservice.connect(
				new String[] { "ieFlag", "startTime", "endTime" },
				new Object[] { arg[0], arg[1], arg[2] })) {

			if (webservice.getResult() != null) {
				MyJason myJason = new MyJason(webservice.getResult().toString());
				// 组别 隶属海关 关区代码 结关数 接单数 布控查验数 布控查验率
				return myJason.jasonToList(new String[] { "BKCYL", "BKCYS",
						"CUS_NAME", "ZMC", "CUS_CODE", "YSJSFGCHS",
						"REC_MAYCHK_SUM", "YSJSFGCHL", "BKCHS", "BKCHL",
						"END_MAYCHK_SUM", "ID" });

			}
		}
		return null;
	}

	/**
	 * 初始化放缩按钮
	 */
	private void initZoomBtn() {
		zoomBtn = (ImageButton) contentView.findViewById(R.id.zoom);
		zoomSelect = 0;
		zoomBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				zoom(Constant.CHART);
			}
		});
	}

	/**
	 * 放缩管理
	 * 
	 * @param choose
	 *            Constant.CHART,Constant.SHEET
	 */
	private void zoom(int choose) {

		switch (choose) {
		case Constant.CHART://图表  
			zoomChart();
			break;
		case Constant.SHEET_IN://列表放大 
			zoomSheet(Constant.ZOOM_IN);
			break;
		case Constant.SHEET_OUT://列表缩小
			zoomSheet(Constant.ZOOM_OUT);
			break;

		default:
			break;
		}
	}

	/**
	 * 列表放缩操作
	 */
	private void zoomSheet(int choose) {

		switch (choose) {
		case Constant.ZOOM_IN:// 放大
			System.out.println("contentView::" + contentView);
			sheetStatue = Constant.ZOOM_IN;
			mWebView.setVisibility(View.GONE);
			menu_layout.setVisibility(View.GONE);

			if (chooseBlock == Constant.CUSTOM_TGWZH) {
				System.out.println("通关无纸化");
				RelativeLayout tgwzh_table_layout = (RelativeLayout) contentView
						.findViewById(R.id.tgwzh_table);
				ListView table_list = (ListView) tgwzh_table_layout
						.findViewById(R.id.table_list);
				RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
						RelativeLayout.LayoutParams.FILL_PARENT,
						RelativeLayout.LayoutParams.FILL_PARENT);
				params.setMargins(10, 22, 10, 0);
				table_list.setLayoutParams(params);
			}
			break;
		case Constant.ZOOM_OUT:
			sheetStatue = Constant.ZOOM_OUT;
			mWebView.setVisibility(View.VISIBLE);
			menu_layout.setVisibility(View.VISIBLE);
			break;

		default:
			break;
		}
	}

	/**
	 * 
	 * Description:放大缩小图表装载 Date:2013-7-8
	 * 
	 * @author wangwx
	 * @return void
	 */
	private void zoomChart() {

		// 放大
		if (zoomSelect == 0) {
			mWebView.loadUrl("javascript:zoomIN()");
			Log.d("-----------", "javascript:zoomIN");
			mWebView.setLayoutParams(new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT, 800));
			index_layout.setVisibility(View.GONE);
			menu_layout.setVisibility(View.GONE);
			zoomBtn.setImageResource(R.drawable.icon_zoomout);
			zoomSelect = 1;

		} else if (zoomSelect == 1) {// 还原
			mWebView.loadUrl("javascript:zoomOUT()");
			mWebView.setLayoutParams(new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.FILL_PARENT, 220));
			index_layout.setVisibility(View.VISIBLE);
			menu_layout.setVisibility(View.VISIBLE);
			zoomBtn.setImageResource(R.drawable.icon_zoomin);
			zoomSelect = 0;
		}

	}
}
