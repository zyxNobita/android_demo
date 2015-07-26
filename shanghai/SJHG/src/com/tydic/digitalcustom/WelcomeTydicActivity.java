package com.tydic.digitalcustom;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.tydic.digitalcustom.activity.MainActivity;
import com.tydic.digitalcustom.entity.Constant;
import com.tydic.digitalcustom.entity.VPNOfCustom;
import com.tydic.digitalcustom.entity.Webservice;
import com.tydic.digitalcustom.tools.Tools;

/**
 * 
 * Description:设置进入应用的动画 WelcomeTydicActivity.java Create on 2013-7-8 上午10:41:20
 * 
 * @author wangwx
 * @version 1.0 Copyright (c) 2013 Company,Inc. All Rights Reserved.
 */
public class WelcomeTydicActivity extends Activity {

	String msgStr;
	LinearLayout linearLayout;
	private Message msg = new Message();
	private Handler handler;
	private VPNOfCustom vpn;
	private ProgressDialog progressdialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);
		// 利用线程 保持开机动画
		sleep(1);
		vpn = VPNOfCustom.getInstance(this);
		handler = new Handler() {

			@Override
			public void dispatchMessage(Message msg) {
				super.dispatchMessage(msg);
				switch (msg.what) {
				case Constant.LOGINVIEW:
					// 选择接入方式
					// chooseNet().show();
					Webservice webservice = Webservice.getInstance();
					webservice.setSERVER_ADD(Constant.IP_81);
					// progressdialog = ProgressDialog.show(
					// WelcomeTydicActivity.this, "网络接入", "初始化vpn");
					handler.sendEmptyMessage(Constant.VPN);
					break;
				case Constant.CONNECT_FAIL:
					// 无网络
					getInfoDialog().show();
					break;

				// VPN接入
				case Constant.VPN:
					try {
						vpnInit();
					} catch (Exception e) {
						Toast.makeText(WelcomeTydicActivity.this,
								"vpn初始化导致未知错误", Toast.LENGTH_SHORT).show();
						e.printStackTrace();
					}
					break;

				// pc服务接入
				case Constant.PC:
					startActivity(new Intent(WelcomeTydicActivity.this,
							MainActivity.class));
					WelcomeTydicActivity.this.finish();
					break;

				case Constant.WEBSERVICE_OVER:
					// progressdialog.dismiss();
					startActivity(new Intent(WelcomeTydicActivity.this,
							MainActivity.class));
					WelcomeTydicActivity.this.finish();
					// Toast.makeText(WelcomeTydicActivity.this, "vpn通道建立成功!",
					// Toast.LENGTH_SHORT).show();
					break;

				case Constant.VPN_INIT_FAIL:
					// progressdialog.dismiss();
					// Toast.makeText(WelcomeTydicActivity.this, "vpn初始化建立失败!",
					// Toast.LENGTH_SHORT).show();
					Toast.makeText(WelcomeTydicActivity.this, "网络连接异常!",
							Toast.LENGTH_SHORT).show();
					break;

				case Constant.VPN_CONNECT_FAIL:
					// progressdialog.dismiss();
					// Toast.makeText(WelcomeTydicActivity.this, "vpn连接失败!",
					// Toast.LENGTH_SHORT).show();
					Toast.makeText(WelcomeTydicActivity.this, "请尝试重新连接!",
							Toast.LENGTH_SHORT).show();
					break;
				default:
					break;
				}
			}
		};

		// mainSleep();

	}

	@Override
	protected void onRestart() {
		super.onRestart();
		try {
			vpnInit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

//	@Override
//	protected void onResume() {
//		super.onResume();
//		try {
//			vpnInit();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

	private void sleep(int i) {

		Thread thread = new Thread() {
			public void run() {
				try {
					sleep(1000);
					handler.sendEmptyMessage(Constant.LOGINVIEW);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			};

		};
		thread.start();
	}

	/**
	 * 网络选择对话框
	 */
	protected AlertDialog chooseNet() {
		final Webservice webservice = Webservice.getInstance();
		return new AlertDialog.Builder(WelcomeTydicActivity.this)
				.setTitle("接入方式选择")
				.setMessage("1、vpn为正式访问81库\n2、本地服务为访问baipc数据")
				.setPositiveButton("vpn",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								webservice.setSERVER_ADD(Constant.IP_81);
								progressdialog = ProgressDialog.show(
										WelcomeTydicActivity.this, "网络接入",
										"初始化vpn");
								handler.sendEmptyMessage(Constant.VPN);
							}
						})
				.setNegativeButton("本地服务",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								webservice.setSERVER_ADD(Constant.IP_BAIPC);
								handler.sendEmptyMessage(Constant.PC);
							}
						}).create();

	}

	/**
	 * 获得提示对话框
	 */
	protected AlertDialog getInfoDialog() {
		return new AlertDialog.Builder(WelcomeTydicActivity.this)
				.setTitle("温馨提示").setMessage("网络连接错误，请设置网络连接")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						WelcomeTydicActivity.this
								.startActivity(new Intent(
										android.provider.Settings.ACTION_WIRELESS_SETTINGS));
						WelcomeTydicActivity.this.finish();
					}
				}).create();

	}

	/**
	 * vpn连接
	 */
	private void vpnInit() throws Exception {
		if (!vpn.init()) {
			handler.sendEmptyMessage(Constant.VPN_INIT_FAIL);
		}
		Thread thread = new Thread() {
			@Override
			public void run() {

				if (Tools.isNetworkAvailable(getApplicationContext())) {
					// 有网络

					if (vpn.doVpnLogin()) {
						handler.sendEmptyMessage(Constant.WEBSERVICE_OVER);
					} else {
						handler.sendEmptyMessage(Constant.VPN_CONNECT_FAIL);
					}

				} else {
					handler.sendEmptyMessage(Constant.CONNECT_FAIL);
				}

			}
		};
		thread.start();
	}

	@Override
	protected void onPause() {
		overridePendingTransition(R.anim.fade, R.anim.hold);
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// progressdialog.dismiss();
	}

}
