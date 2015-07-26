package com.bai.demo.frame;

import java.util.HashMap;
import java.util.Iterator;

import android.view.View;

public class RightWindowManager {

	/**
	 * 暂时性右侧菜单
	 */
	public static final int TEMP_WINDOW = 0;

	/**
	 * Iphoto 业务1
	 */
	public static final int MENU3 = 1;
	
	public static final int MENU3INFO2 = 2;
	
	public static final int MENU1INFO1 = 11;
	public static final int MENU1INFO2 = 12;
	public static final int txdzgl = 13;
	public static final int scxxsh = 14;
	
	
	public static final int MENU2INFO1 = 21;
	public static final int MENU2INFO2 = 22;
	public static final int MENU2INFO3 = 23;
	public static final int MENU2INFO4 = 24;
	public static final int MENU2INFO5 = 25;
	
	public static final int MENU4INFO1 = 41;
	public static final int MENU4INFO2 = 42;
	public static final int MENU3INFO3 = 43;
	public static final int MENU3INFO4 = 44;
	
	public static final int MENUVERSION = 3;

	/**
	 * 好友关系链面板的KEY
	 */
	public static final int FRIEND_LIST_WINDOW = 2;

	/**
	 * 应用中心面板的KEY
	 */
	public static final int APP_CENTER_WINDOW = 3;

	private HashMap<Integer, RightWindowBase> mHashMap;

	private RightWindowContainer mContainer;

	public RightWindowManager() {
		mHashMap = new HashMap<Integer, RightWindowBase>();
	}

	public void setmContainer(RightWindowContainer container) {
		this.mContainer = container;
	}

	public void showRightWindow(int num, RightWindowBase mRightWindowBase) {
		if (!mHashMap.containsKey(num)) {
			mHashMap.put(num, mRightWindowBase);
			if (!(mRightWindowBase instanceof RightTempActivity)) {
				mContainer.addView(mRightWindowBase);
			}
		}

		for (Iterator<Integer> iter = mHashMap.keySet().iterator(); iter.hasNext();) {
			Object key = iter.next();
			RightWindowBase qzb = mHashMap.get(key);
			qzb.setVisibility(View.INVISIBLE);
		}

		mRightWindowBase.setVisibility(View.VISIBLE);
	}

}
