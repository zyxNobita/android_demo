package com.tydic.digitalcustom.tools;

import com.tydic.digitalcustom.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Toast;

	public class IntentDialog {

		/**
		 * @author king
		 * @category退出对话框
		 */
		public static void showExitDialog(final Activity ctx) {

			new AlertDialog.Builder(ctx)
					.setIcon(R.drawable.ic_launcher)
					.setTitle("提示")
					.setMessage("是否退出当前程序")
					.setPositiveButton("是", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							// 发送广播
							System.exit(0);//退出整个程序
						}
					})
					.setNegativeButton("否", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					}).show();
		}

		/**
		 * 显示错误输入提示
		 */
		public static void showAlert(Context context2, String string) {
			Toast.makeText(context2, string, 1).show();
		}

		public static void LoginAlert(final Context context) {
			new AlertDialog.Builder(context).setIcon(R.drawable.ic_launcher)
					.setTitle("提示ʾ").setMessage("您还没有登录，是否登录")
					.setPositiveButton("是", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							Intent intent = new Intent();
//							intent.setClass(context, LoginActivity.class);
							context.startActivity(intent);
						}
					})
					.setNegativeButton("否", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					}).show();
		}



		/**
		 * 获取一个“请稍后”对话框
		 * 
		 * @param context
		 * @return
		 */
		public static ProgressDialog getProgressDialog(Context context) {
			ProgressDialog progress = new ProgressDialog(context);
			progress.setCancelable(false);
			progress.setMessage("请稍后...");
			return progress;
		}

		/**
		 * 弹出一个“请稍后”对话框
		 * 
		 * @param context
		 * @return
		 */
		public static ProgressDialog showProgressDialog(Context context) {
			ProgressDialog dialog = getProgressDialog(context);
			dialog.show();
			return dialog;
		}
	}

