package com.bai.demo.entity;

import com.bai.demo.R;
import com.bai.demo.main.FrameDemoActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

public class MyHandler extends Handler {

	Context context;

	public MyHandler(Context activity) {
		super();
		this.context = activity;

	}

	public void handleMessage(Message msg) {
		// 调用父类处理
		super.handleMessage(msg);
		LoginActivity.progressDialog.dismiss();
		switch (msg.what) {
		case Constant.LOGIN_SUCCESS:
			context.startActivity(new Intent(context, FrameDemoActivity.class));
			((Activity) context).overridePendingTransition(R.anim.fade, R.anim.hold);
			((Activity) context).finish();
			break;
		case Constant.LOGIN_FAIL1:
			LoginActivity.progressDialog.dismiss();
			showToast(context, "工号未注册申请");
			break;
		case Constant.LOGIN_FAIL2:
			showToast(context, "工号或密码错误");
			break;
		case Constant.CONNECT_FAIL:
			//showToast(context, "网络连接错误！请检测您的网络接入方式。");
			LoginActivity.toolUtils.StartWifiMessage("链接服务器失败，或则网络连接错误，请检测服务器端，谢谢。");
			break;
		default:
			//showToast(context, "网络连接错误！请检测您的网络接入方式。");
			LoginActivity.toolUtils.promptMessage("网络连接错误！请检测您的网络接入方式。");
			break;
		}

	}

	private void showToast(Context context, String showString) {
		Toast.makeText(context, showString, Toast.LENGTH_SHORT).show();
	}

}
