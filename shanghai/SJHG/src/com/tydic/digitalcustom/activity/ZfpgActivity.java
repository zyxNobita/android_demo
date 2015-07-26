package com.tydic.digitalcustom.activity;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tydic.digitalcustom.R;
import com.tydic.digitalcustom.entity.Constant;
import com.tydic.digitalcustom.entity.MyAdapterForRadioBtn;
import com.tydic.digitalcustom.entity.PinYinMenuInDB;
import com.tydic.digitalcustom.entity.PinYinSearch;
import com.tydic.digitalcustom.tools.DatabaseHelper;
import com.tydic.digitalcustom.widget.MyTabHost;
import com.tydic.digitalcustom.widget.PopupMenu;

/**
 * 
 * Description:执法评估模块 ZfpgActivity.java Create on 2013-7-8 下午3:45:13
 * 
 * @author wangwx
 * @version 1.0 Copyright (c) 2013 Company,Inc. All Rights Reserved.
 */
@SuppressLint("HandlerLeak")
@SuppressWarnings("unused")
public class ZfpgActivity extends Activity {

	private ListView listView;
	private String[] menuList,searchResult;
	private String[] menuInPinyin = null;
	private LinearLayout contentLayout;
	private LayoutInflater inflater;
	private EditText editText;
	private PopupMenu popupMenu;
	private ImageView btnClearSearch;
	private TextView rstInfo;
	protected int pos = 0, zoomSelect = 0;// 0:视角按钮 1：搜索按钮 zoom 0显示为放大按钮，1显示为缩小
	protected static Handler menu2Handler;
	private DatabaseHelper dbHelper;
	private String tabName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_frame);

		menu2Handler = new Handler() {
			@Override
			public void dispatchMessage(Message msg) {
				switch (msg.what + Constant.TAB_YWL * 2) {
				case Constant.TAB_YWL * 2 + Constant.MENU_YWL + Constant.CHILD1:
					contentLayout.removeAllViews();
					final View tab2Menu1Child1View = inflater.inflate(
							R.layout.zfpg, null);
					contentLayout.addView(tab2Menu1Child1View);

					// 点击按钮弹出菜单
					Button pop = (Button) findViewById(R.id.sjbutton);
					pop.setOnClickListener(popClick);
					Button searchbutton = (Button) findViewById(R.id.searchbutton);
					// searchbutton.setOnClickListener(popClick);
					final ImageButton bigger = (ImageButton) findViewById(R.id.btn_bigger);
					zoomSelect = 0;
					bigger.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							RelativeLayout index_layout = (RelativeLayout) tab2Menu1Child1View
									.findViewById(R.id.left_bottom);
							LinearLayout menu_layout = (LinearLayout) findViewById(R.id.layout_menu);

							if (zoomSelect == 0) {
								index_layout.setVisibility(View.GONE);
								menu_layout.setVisibility(View.GONE);
								bigger.setImageResource(R.drawable.icon_zoomout);
								zoomSelect = 1;
							} else if (zoomSelect == 1) {
								index_layout.setVisibility(View.VISIBLE);
								menu_layout.setVisibility(View.VISIBLE);
								bigger.setImageResource(R.drawable.icon_zoomin);
								zoomSelect = 0;
							}
						}
					});
					break;

				default:
					Toast.makeText(ZfpgActivity.this, "页面未添加",
							Toast.LENGTH_SHORT).show();

					break;
				}
			}

		};

		inflater = LayoutInflater.from(this);
		contentLayout = (LinearLayout) findViewById(R.id.menu_frame_content);

		listView = (ListView) findViewById(R.id.menu_frame_menulist);
		//listView.setAdapter(new MyAdapterForRadioBtn(this, getResources().getStringArray(R.array.zfpgMenu)));
		String []menu = new String[1];
		menu[0] = "暂无菜单";
		final View notcreate = LinearLayout.inflate(getApplicationContext(), R.layout.notcreate, null);
		listView.setAdapter(new MyAdapterForRadioBtn(this, menu));
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				switch (position) {
				case 0:
					contentLayout.removeAllViews();//notcreate
					contentLayout.addView(notcreate, LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
					//popupMenu = new PopupMenu(ZfpgActivity.this, view,
					//		R.array.zfpg_sszg_child, menu2Handler, position);

					break;
				case 1:

					popupMenu = new PopupMenu(ZfpgActivity.this, view,
							R.array.zfpg_jgmy_child, menu2Handler, position);

					break;
				case 2:

					popupMenu = new PopupMenu(ZfpgActivity.this, view,
							R.array.zfpg_jgcy_child, menu2Handler, position);

					break;
				case 3:

					popupMenu = new PopupMenu(ZfpgActivity.this, view,
							R.array.zfpg_jggl_child, menu2Handler, position);

					break;
				case 4:

					popupMenu = new PopupMenu(ZfpgActivity.this, view,
							R.array.zfpg_tgsx_child, menu2Handler, position);

					break;

				default:
					/*contentLayout.removeAllViews();
					Toast.makeText(ZfpgActivity.this, "页面未建立！",
							Toast.LENGTH_SHORT).show();*/
					contentLayout.removeAllViews();//notcreate
					contentLayout.addView(notcreate, LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
					
					break;
				}
				if (popupMenu != null) {

					popupMenu.show(PopupMenu.RIGHT);
					popupMenu = null;
				}

			}

		});

		dbHelper = new DatabaseHelper(this, "MenuDB.db", 1);
		tabName = getResources().getString(R.string.zfpg);
		PinYinMenuInDB pinYinMenuInDB = new PinYinMenuInDB(getMenuInfo(), this);
		pinYinMenuInDB.saveInDb(tabName);

		editText = (EditText) findViewById(R.id.menu_frame_edittext);

		editText.addTextChangedListener(textWatcher);

		// 未找到查询结果展示textview
		rstInfo = (TextView) findViewById(R.id.menu_frame_textinfo);
		rstInfo.setText(getResources().getString(R.string.search_info));

		// 搜索栏删除键
		btnClearSearch = (ImageView) findViewById(R.id.menu_frame_btnclean);
		btnClearSearch.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				editText.setText("");
				btnClearSearch.setVisibility(View.GONE);
			}
		});
	}

	// 点击弹出左侧菜单的显示方式
	OnClickListener popClick = new OnClickListener() {

		@SuppressLint("HandlerLeak")
		@Override
		public void onClick(View v) {
			popupMenu = new PopupMenu(ZfpgActivity.this, v, R.array.taxView,
					menu2Handler, -1);

			popupMenu.show(PopupMenu.DOWN);
		}
	};

	/**
	 * 	文字改变 调用搜索方法
	 */
	TextWatcher textWatcher = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			if (menuList == null) {
				menuList = getMenuInfo();
			}
			if (s.length() != 0) {
				btnClearSearch.setVisibility(View.VISIBLE);
				if (true == getSearchResult(s.toString(),tabName)) {
					listView.setAdapter(new MyAdapterForRadioBtn(
							ZfpgActivity.this, searchResult));
					rstInfo.setVisibility(View.GONE);
				} else {
					listView.setVisibility(View.INVISIBLE);
					rstInfo.setVisibility(View.VISIBLE);
				}
			} else {
				listView.setVisibility(View.VISIBLE);
				btnClearSearch.setVisibility(View.GONE);
				rstInfo.setVisibility(View.GONE);
				listView.setAdapter(new MyAdapterForRadioBtn(ZfpgActivity.this,
						getMenuInfo()));
			}

		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}

		@Override
		public void afterTextChanged(Editable s) {
		}
	};

	/**
	 * 搜索关键字
	 * @param condition
	 * @param tab
	 * @return
	 */
	private Boolean getSearchResult(String condition, String tab) {
		if (condition == null || condition.equals(""))
			return false;

		String selection = "select * from menu where menu_tag like '%"
				+ PinYinSearch.getPYSearchRegExp(condition, "%")
				+ "%' and tab_tag = '" + tab + "'";

		Log.d("--------sql", selection);
		Cursor cur = dbHelper.getReadableDatabase().rawQuery(selection, null);

	
		Log.d("---", "一次次的读取数据库" + cur.getCount());
		if(cur.getCount() == 0){
			return false;
		}
		cur.moveToFirst();
		Log.d("----condition", condition);
		searchResult = new String[cur.getCount()];
		int i =0;
		while (cur.getCount() > cur.getPosition()) {
			String mix_menu = cur.getString(cur.getColumnIndex("menu_tag"));
			String menu = cur.getString(cur.getColumnIndex("menu"));
			searchResult[i] = menu;
			Log.i("menus>>>", "menu" + menu);
			Log.i("mixmenus>>>", "mixmenu" + mix_menu);
			i++;
			cur.moveToNext();
		}
		cur.close();
		dbHelper.close();
		return true;
	}

	/**
	 * 
	 * Description:是否包换汉字 Date:2013-7-8
	 * 
	 * @author wangwx
	 * @param str
	 *            传入的字符串
	 * @return
	 * @return boolean true:是 false:不是
	 */
	public boolean containCn(String str) {
		Pattern pattern = Pattern.compile("[\\u4e00-\\u9fa5]");
		return pattern.matcher(str).find();
	}

	/**
	 * 
	 * Description:获取菜单信息 Date:2013-7-8
	 * 
	 * @author wangwx
	 * @return
	 * @return String[]
	 */
	protected String[] getMenuInfo() {
		menuList = getResources().getStringArray(R.array.zfpgMenu);

		return menuList;
	}
}
