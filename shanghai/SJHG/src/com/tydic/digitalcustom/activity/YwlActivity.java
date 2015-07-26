package com.tydic.digitalcustom.activity;

import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.tydic.digitalcustom.R;
import com.tydic.digitalcustom.entity.MyAdapterForRadioBtn;
import com.tydic.digitalcustom.entity.SearchUtil;
import com.tydic.digitalcustom.entity.WholeConstant;
import com.tydic.digitalcustom.entity.jg.Jgjzxbzzsl;
import com.tydic.digitalcustom.entity.jg.YwlNodeUtil;
import com.tydic.digitalcustom.widget.PopupMenu;
import com.tydic.digitalcustom.widget.RadioButton;

@SuppressLint({ "HandlerLeak" })
public class YwlActivity extends Activity {

	private ListView listView;// /显示菜单内容的区域
	private LinearLayout contentLayout;
	protected LayoutInflater inflater = null;
	private PopupMenu popupMenu;
	protected Handler twoLeavelMenuHandler;// 二级菜单
	public Handler threeLeavelMenuHandler;// 三级菜单
	// private JSONArray menuArray = null;//菜单数组
	String[] menu = null;
	String[] menuChild = null;
	private int twoPos = 0; // 记录点击的二级菜单的位置
	private int threePos = 0; // 记录点击的三级菜单的位置
	public static YwlActivity ywlactivity;
	public static SearchUtil SearchUtil;

	/**
	 * 获取当前对象的实例
	 * 
	 * @return
	 */
	public static Activity getYwlActivityInstance() {
		return ywlactivity;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_frame);

		ywlactivity = this;
		contentLayout = (LinearLayout) findViewById(R.id.menu_frame_content);
		listView = (ListView) findViewById(R.id.menu_frame_menulist);
		inflater = LayoutInflater.from(this);
		SearchUtil = new SearchUtil(YwlActivity.this, listView, "业务量");
		getTwoLeavelMenuHandler();
		getThreadLeavelMenuHandler();
		new Menu().start();
	}

	/**
	 * 处理二级菜单的Handler
	 */
	private void getTwoLeavelMenuHandler() {
		twoLeavelMenuHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				dispatchTwoLeavelHandler(msg);
			}
		};
	}

	/**
	 * 处理三级菜单的Handler
	 */
	private void getThreadLeavelMenuHandler() {
		threeLeavelMenuHandler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 0:
					if (!SearchUtil.init()) {
						;// 初始化搜索框
							// 处理数据
						listView.setAdapter(new MyAdapterForRadioBtn(
								getApplicationContext(), menu));
						listView.setOnItemClickListener(new OnItemClickListener() {
							@Override
							public void onItemClick(AdapterView<?> parent,
									View view, final int position, long id) {
								showThreeLeavelPopMenu(view, position);
							}
						});
					} else {
						Toast.makeText(getApplicationContext(), "服务端返回异常", 1)
								.show();
					}
					break;
				}
			};
		};
	}

	/**
	 * 
	 * Description:分发二级菜单目录 Date:2013-7-15
	 * 
	 * @author wangwx
	 * @param msg
	 * @return void
	 */
	private void dispatchTwoLeavelHandler(Message msg) {
		// String menuId = msg.getData().getString("menuId");
		threePos = msg.getData().getInt("position");

		JSONObject twoLeavelObj = null;
		JSONObject threeLeavelObj = null;
		JSONArray threeChildsList = null;
		String title = "";
		String guid = "";
		try {
			// twoLeavelObj = (JSONObject)menuArray.get(twoPos);
			twoLeavelObj = (JSONObject) WholeConstant.menuArray.get(twoPos);
			JSONArray childsList = (JSONArray) twoLeavelObj.get("childs");
			threeLeavelObj = (JSONObject) childsList.get(threePos);
			threeChildsList = (JSONArray) threeLeavelObj.get("childs");
			title = threeLeavelObj.getString("ZBMC");
			guid = threeLeavelObj.getString("GUID");
			System.out.println("threeChildsList::" + threeChildsList);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Jgjzxbzzsl jgjzxbzzsl = new Jgjzxbzzsl(this, contentLayout);
		jgjzxbzzsl.initJGJZXBZZSL(threeChildsList, title, guid);
	}

	/**
	 * 
	 * Description:分发三级菜单目录 Date:2013-7-15
	 * 
	 * @author wangwx
	 * @param parent
	 * @param position
	 * @return void
	 */
	private void showThreeLeavelPopMenu(View view, final int position) {
		twoPos = position;// /////二级菜单的位置
		contentLayout.removeAllViews();
		try {
			RadioButton rbtn = (RadioButton) view;
			// 通过比较菜单名称确认以前的位置
			for (int i = 0; i < menu.length; i++) {
				if (menu[i].equals(rbtn.getText())) {
					twoPos = i;
					break;
				}
			}
			Map<String, String[]> childMap = YwlNodeUtil.dealChilds(twoPos);
			if (childMap != null) {
				String childs[] = (String[]) childMap.get("zbmcChilds");
				String childIds[] = (String[]) childMap.get("guidChildIds");
				popupMenu = new PopupMenu(YwlActivity.this, view, childs,
						childIds, twoLeavelMenuHandler, twoPos);
			} else {
				Toast.makeText(this, "请检查网络设置", 1).show();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			Toast.makeText(this, "请检查网络设置", 1).show();
			/*
			 * ComponentName Component = getIntent().getComponent();
			 * Component.getClassName();
			 */
			// Tools.showError(getApplicationContext(),e1);
		}

		if (popupMenu != null) {
			popupMenu.show(PopupMenu.RIGHT);
			popupMenu = null;
		} else {
			Toast.makeText(YwlActivity.this, "页面未建立！", Toast.LENGTH_SHORT)
					.show();
		}
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		Log.d("---", "dispatchKeyEvent触发！");
		return super.dispatchKeyEvent(event);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Log.d("---", "onTouchEvent触发！");
		return false;
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	/**
	 * 
	 * Description: 获取菜单的线程 YwlActivity.java Create on 2013-7-15 下午1:21:26
	 * 
	 * @author wangwx 更新UI的东西请不要写在线程里面！
	 * @version 1.0 Copyright (c) 2013 Company,Inc. All Rights Reserved.
	 */
	class Menu extends Thread {
		@Override
		public void run() {
			menu = YwlNodeUtil.getZBMCMenu();
			threeLeavelMenuHandler.sendEmptyMessage(0);
		}
	}

}
