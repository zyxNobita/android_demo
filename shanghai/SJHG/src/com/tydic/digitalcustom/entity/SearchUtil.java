package com.tydic.digitalcustom.entity;

import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.database.Cursor;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.tydic.digitalcustom.R;
import com.tydic.digitalcustom.tools.DatabaseHelper;

/**
 * 
 * Description:搜索工具栏模块
 * 
 * @author wangwx
 * @version 1.0 Copyright (c) 2013 Company,Inc. All Rights Reserved.
 */
@SuppressLint("HandlerLeak")
public class SearchUtil {

	private ListView listView;// 保存菜单的对象
	private String[] searchResult;// 搜索结果
	private EditText editText;// 搜索框内容
	private ImageView btnClearSearch;// 删除按钮
	private TextView rstInfo;// 查询结果
	private DatabaseHelper dbHelper;
	private String tabName;// 保存到本地数据库后的tag的名称
	private Activity curActivity;// 当前activity的名称

	/**
	 * 
	 * @param curActivity
	 *            activity 的对象
	 * @param listView
	 *            保存菜单的对象
	 * @param tabName
	 *            保存到本地数据库后的tag的名称
	 */
	public SearchUtil(Activity curActivity, ListView listView, String tabName) {
		this.curActivity = curActivity;
		this.listView = listView;
		this.tabName = tabName;
	}

	public boolean init() {
		dbHelper = new DatabaseHelper(curActivity, "MenuDB.db", 1);
		PinYinMenuInDB pinYinMenuInDB = new PinYinMenuInDB(
				WholeConstant.ZBMCIDMenu, WholeConstant.ZBMCMenu, curActivity);

		editText = (EditText) curActivity
				.findViewById(R.id.menu_frame_edittext);
		editText.addTextChangedListener(textWatcher);

		// 未找到查询结果展示textview
		rstInfo = (TextView) curActivity.findViewById(R.id.menu_frame_textinfo);
		rstInfo.setText(curActivity.getResources().getString(
				R.string.search_info));

		// 搜索栏删除键
		initClearSearch();
		return pinYinMenuInDB.saveInDb(tabName);
	}

	private void initClearSearch() {
		btnClearSearch = (ImageView) curActivity
				.findViewById(R.id.menu_frame_btnclean);
		btnClearSearch.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				editText.setText("");
				btnClearSearch.setVisibility(View.GONE);
			}
		});
	}

	/**
	 * 文字改变 调用搜索方法
	 */
	TextWatcher textWatcher = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {

			if (s.length() != 0) {
				btnClearSearch.setVisibility(View.VISIBLE);
				if (true == getSearchResult(s.toString(), tabName)) {
					listView.setAdapter(new MyAdapterForRadioBtn(curActivity,
							searchResult));
					rstInfo.setVisibility(View.GONE);
				} else {
					listView.setVisibility(View.INVISIBLE);
					rstInfo.setVisibility(View.VISIBLE);
				}
			} else {
				listView.setVisibility(View.VISIBLE);
				btnClearSearch.setVisibility(View.GONE);
				rstInfo.setVisibility(View.GONE);
				listView.setAdapter(new MyAdapterForRadioBtn(curActivity,
						WholeConstant.ZBMCMenu));
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
	 * 
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
		if (cur.getCount() == 0) {
			return false;
		}
		cur.moveToFirst();
		Log.d("----condition", condition);
		searchResult = new String[cur.getCount()];
		int i = 0;
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
}
