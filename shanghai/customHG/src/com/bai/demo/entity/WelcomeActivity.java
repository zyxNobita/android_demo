package com.bai.demo.entity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.LinearLayout;

import com.bai.demo.R;
import com.bai.demo.main.FrameDemoActivity;

/**
 * 过度页面：检查版本 、更新版本
 * @author zhangyx
 *
 */
public class WelcomeActivity extends Activity {

	public static Handler handler;
	WelcomeView wv;
	String msgStr;
	LinearLayout linearLayout;
	Message msg = new Message();
	int flag = 0;// 为0展示tydic界面，为1直接登入登陆界面

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);
		linearLayout = (LinearLayout) findViewById(R.id.welcome);

		// 强制为横屏
		 this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		// 强制为竖屏
//		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		// gotoWelcomeView();
		mainSleep();
		// 创建消息处理器对象
		handler = new Handler() {

			public void handleMessage(Message msg) {
				// 调用父类处理
				super.handleMessage(msg);
				// 获取消息中的数据
				Bundle b;
				b = msg.getData();
				// 获取内容字符串
				msgStr = b.getString("msg");
				// 根据消息what编号的不同，执行不同的业务逻辑
				switch (msg.what) {
				case Constant.GOTOLOGIN:
					//startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
					goToMainView();
					break;
				case  Constant.CHANGEVIEW:
					//直接免登陆
					goToLoginView();

				default:
					break;
				}
			}
		};
	}

	private void mainSleep() {
		Thread thread = new Thread() {
			@Override
			public void run() {
				try {
					sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (flag == 0) {
					msg.what = Constant.CHANGEVIEW;
				}else {
					msg.what = Constant.GOTOLOGIN;
				}

				WelcomeActivity.handler.sendMessage(msg);
			}
		};
		thread.start();
	}

	protected void goToMainView() {
		Intent intent = new Intent(this, FrameDemoActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.fade, R.anim.hold);
		this.finish();
	}

	private  void  goToLoginView() {
		Intent intent = new Intent(this,LoginActivity.class);
		startActivity(intent);
		this.finish();
	}


	/**
	 * 开启动画
	 */
	protected void gotoWelcomeView() {
		if (wv == null) {
			wv = new WelcomeView(this);
		}
		setContentView(wv);
	}

}
