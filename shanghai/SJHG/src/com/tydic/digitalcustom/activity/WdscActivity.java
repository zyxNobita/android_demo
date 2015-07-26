package com.tydic.digitalcustom.activity;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.tydic.digitalcustom.R;
import com.tydic.digitalcustom.entity.MyAdapterForRadioBtn;
import com.tydic.digitalcustom.entity.SharePreferenceUtils;
import com.tydic.digitalcustom.entity.jg.Jgjzxbzzsl;
import com.tydic.digitalcustom.entity.jg.YwlNodeUtil;
import com.tydic.digitalcustom.widget.RadioButton;

/**
 * 
 * Description:我的收藏模块
 * WdscActivity.java Create on 2013-7-8 下午4:14:03 
 * @author wangwx
 * @version 1.0
 * Copyright (c) 2013 Company,Inc. All Rights Reserved.
 */
@SuppressLint("HandlerLeak")
public class WdscActivity extends Activity {

	private ListView listView;///显示菜单内容的区域
	private LinearLayout contentLayout;
	protected LayoutInflater inflater;
	private String[] menu ;
	private Iterator<String> it ;
	public  Handler inHandler;
	private Map<String, Object> favoriteMap ;//收藏夹列表

	public static WdscActivity wdscactivity;//当前对象实例
	private List<Map<String, Object>>  childsList = null;
	private String  btnText=  "";
	private String  guid = "";
	private Jgjzxbzzsl jgjzxbzzsl;
	
	/**
	 * 获取对象的实例
	 * @return
	 */
	public static WdscActivity getWdscActivityInstance(){	
		return wdscactivity;
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_frame);
		wdscactivity = this;
		inflater = LayoutInflater.from(this);
		contentLayout = (LinearLayout) findViewById(R.id.menu_frame_content);
		listView = (ListView) findViewById(R.id.menu_frame_menulist);
		jgjzxbzzsl = new Jgjzxbzzsl(WdscActivity.this, contentLayout);
		jgjzxbzzsl.setFromFavorite(true);
		menuPublic();
		initInHandler();
		//listView.setAdapter(new MyAdapterForRadioBtn(this, new String[] {"暂无收藏！"}));
		//final View notcreate = LinearLayout.inflate(getApplicationContext(), R.layout.notcreate, null);
		listView.setOnItemClickListener(listViewOnItemClickListener);
	}
	
	OnItemClickListener listViewOnItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view,
				int position, long id) {
			switch (position) {
			default:
				contentLayout.removeAllViews();//notcreate
				RadioButton  viewBut =  (RadioButton)view;
				btnText = viewBut.getText();
				guid = favoriteMap.get(btnText).toString();
				new ChildMenu().start();
				break;
			}
		}
	};
	private void initInHandler() {
		inHandler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				switch(msg.what){
					case 0:
						listView.setAdapter(new MyAdapterForRadioBtn(getApplicationContext(),menu));
					 break;
					case 1:
						System.out.println("获取：childsList"+childsList);
						JSONArray childsArray = new JSONArray();
						for(int i=0;i<childsList.size();i++){
							JSONObject ajsonObj = new JSONObject((HashMap<String, Object>)childsList.get(i));
							childsArray.put(ajsonObj);
						}
						jgjzxbzzsl.initJGJZXBZZSL(childsArray,btnText,favoriteMap.get(btnText).toString());
						break;
				}
			}
		};
	}

	class ChildMenu extends Thread{
		@Override
		public void run() {
			childsList = YwlNodeUtil.getChildNodes(guid);
			inHandler.sendEmptyMessage(1);
		}
	}
	
	public void menuPublic() {
		Menu menu = new Menu(); 
		menu.start();
	}
	
	class Menu extends Thread{
		@Override
		public void run() {			
			getFavoriteMenu(); 
			inHandler.sendEmptyMessage(0);
		}
	}
	
	/**
	 * 获取收藏列表
	 */
	private void getFavoriteMenu() {
		favoriteMap =  new  SharePreferenceUtils(getApplicationContext()).getSharePrefercncesMap("favorite");
		it = favoriteMap.keySet().iterator();
		int t = 0;
		menu= new String[favoriteMap.size()]; 
		while(it.hasNext()){
			menu[t] = it.next().toString();
			t++;
		}
	}
}