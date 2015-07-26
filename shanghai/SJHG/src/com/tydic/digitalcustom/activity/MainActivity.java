package com.tydic.digitalcustom.activity;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;


import com.tydic.digitalcustom.R;
import com.tydic.digitalcustom.widget.MyTabHost;

/**
 * 主界面----主布局显示
 * @author zhangyx
 *
 */
public class MainActivity extends ActivityGroup {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_main);
		prepare();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	protected void prepare() {

		MyTabHost tabHost = (MyTabHost) findViewById(R.id.tabhost);
		if (tabHost == null) {
			Log.d("----------", "tabhost is null");
		}
		tabHost.setup(getLocalActivityManager());

		tabHost.addTab(tabHost//业务量  模块
				.newTabSpec("ywl")
				.setIndicator(getResources().getString(R.string.ywl),
						R.drawable.tab_ywl)
				.setContent(new Intent(this, YwlActivity.class)));
		tabHost.addTab(tabHost// 专项工作
				.newTabSpec("zxgz")
				.setIndicator(getResources().getString(R.string.zxgz),
						R.drawable.tab_zxgz)
				.setContent(new Intent(this, ZxgzActivity.class)));
		// tabHost.addTab(tabHost  //执法评估
		// .newTabSpec("zfpg")
		// .setIndicator(getResources().getString(R.string.zfpg),
		// R.drawable.tab_zfpg)
		// .setContent(new Intent(this, ZfpgActivity.class)));
		tabHost.addTab(tabHost // 我得收藏
				.newTabSpec("wdsc")
				.setIndicator(getResources().getString(R.string.wdsc),
						R.drawable.tab_wdsc)
				.setContent(new Intent(this, WdscActivity.class)));
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		/*
		 * if (TabAnim.enterAnim != 0 || TabAnim.exitAnim != 0) {
		 * overridePendingTransition(R.anim.enterAnim, R.anim.exitAnim);
		 * TabAnim.clear(); }
		 */
		overridePendingTransition(R.anim.fade, R.anim.hold);
		super.onPause();
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {

		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			if (event.getAction() == KeyEvent.ACTION_UP) {
				com.tydic.digitalcustom.tools.IntentDialog.showExitDialog(MainActivity.this);
			}
			return true;
		}
		return super.dispatchKeyEvent(event);
	}
}