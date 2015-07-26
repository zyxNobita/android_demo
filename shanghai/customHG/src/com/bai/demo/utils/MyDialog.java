package com.bai.demo.utils;

import com.bai.demo.R;
import com.bai.demo.main.FrameDemoActivity;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
/***
 * 自定义Dialog ---设置大小及位置的显示
 * @author zhangyx
 *
 */
public class MyDialog {

	private static Dialog dialog;

	public static void showDialog(Context context, View view, String titleStr) {
		dialog = new Dialog(context);
		dialog.setCanceledOnTouchOutside(true);
		dialog.setContentView(view);
		dialog.setTitle(titleStr);
		
		Window dialogWindow = dialog.getWindow();
		dialogWindow.setWindowAnimations(R.style.MYDIALOG_ANIMATION);
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		dialogWindow.setGravity(Gravity.LEFT | Gravity.TOP);

		Display d = new FrameDemoActivity().requestWindowManager(); // 获取屏幕宽、高用
		WindowManager.LayoutParams p = dialog.getWindow().getAttributes(); // 获取对话框当前的参数值
		p.height = (int) (d.getHeight() * 0.7); // 高度设置为屏幕的0.7
		p.width = (int) (d.getWidth() * 0.8); // 宽度设置为屏幕的0.8
		p.x=(int) (d.getWidth() * 0.15);
		p.y=(int) (d.getHeight() * 0.2);
		dialogWindow.setAttributes(p);
		dialog.onWindowAttributesChanged(lp);
		dialogWindow.setAttributes(lp);
		dialog.show();
	}
	
	public static void showCheckViewDialog(Context context, View view, String titleStr) {
		dialog = new Dialog(context);
		dialog.setCanceledOnTouchOutside(true);
		dialog.setContentView(view);
		dialog.setTitle(titleStr);
		
		Window dialogWindow = dialog.getWindow();
		dialogWindow.setWindowAnimations(R.style.MYDIALOG_ANIMATION);
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		dialogWindow.setGravity(Gravity.CENTER | Gravity.TOP);

		Display d = new FrameDemoActivity().requestWindowManager(); // 获取屏幕宽、高用
		WindowManager.LayoutParams p = dialog.getWindow().getAttributes(); // 获取对话框当前的参数值
		p.height = (int) (d.getHeight() * 0.3); // 高度设置为屏幕的0.3
		p.width = (int) (d.getWidth() * 0.5); // 宽度设置为屏幕的0.5
		p.x=(int) (d.getWidth() * 0.15);
		p.y=(int) (d.getHeight() * 0.2);
		dialogWindow.setAttributes(p);
		dialog.onWindowAttributesChanged(lp);
		dialogWindow.setAttributes(lp);
		dialog.show();
	}
	
	public static void showChooseCheckViewDialog(Context context, View view, String titleStr) {
		dialog = new Dialog(context);
		dialog.setCanceledOnTouchOutside(true);
		dialog.setContentView(view);
		dialog.setTitle(titleStr);
		
		Window dialogWindow = dialog.getWindow();
		dialogWindow.setWindowAnimations(R.style.MYDIALOG_ANIMATION);
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		dialogWindow.setGravity(Gravity.CENTER | Gravity.TOP);

		Display d = new FrameDemoActivity().requestWindowManager(); // 获取屏幕宽、高用
		WindowManager.LayoutParams p = dialog.getWindow().getAttributes(); // 获取对话框当前的参数值
		p.height = (int) (d.getHeight() * 0.38); // 高度设置为屏幕的0.38
		p.width = (int) (d.getWidth() * 0.5); // 宽度设置为屏幕的0.5
		p.x=(int) (d.getWidth() * 0.15);
		p.y=(int) (d.getHeight() * 0.2);
		dialogWindow.setAttributes(p);
		dialog.onWindowAttributesChanged(lp);
		dialogWindow.setAttributes(lp);
		dialog.show();
	}

	public static void dismissDialog() {
		if (dialog != null) {
			dialog.dismiss();
			dialog = null;
		}
	}
}
