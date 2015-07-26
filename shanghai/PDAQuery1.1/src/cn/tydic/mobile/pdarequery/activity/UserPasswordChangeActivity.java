package cn.tydic.mobile.pdarequery.activity;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import cn.tydic.mobile.pdarequery.MainActivity;
import cn.tydic.mobile.pdarequery.R;
import cn.tydic.mobile.pdarequery.tools.Constant;
import cn.tydic.mobile.pdarequery.tools.InputLengthControler;

/**
 * 用户密码修改-----PDA
 * 
 */
public class UserPasswordChangeActivity extends Activity {

	private EditText etUserName, etOldPsw, etNewPsw, etSurePsw;// 输入用户名，原密码，新密码，确认密码
	private Button btnSureChange;// 确认修改按钮，提交用户修改后的密码
	private ImageView imgBtnExit;// 退出用户修改密码界面
	// private LinearLayout llTransparent;// 半透明布局，点击退出按钮后底部图片的加深
	private LinearLayout llBottom;// 半透明布局，点击退出按钮后底部图片的加深
	private Integer ET_MAXLENGTH=12;//密码  12
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.change_userpsw);
		initView();
		initEvent();
	}

	private void initView() {

		etUserName = (EditText) findViewById(R.id.et_changeUserPsw_userName);
		etUserName.setEnabled(false);
		etUserName.setText(Constant.user.getUserName());
		
		etOldPsw = (EditText) findViewById(R.id.et_changeUserPsw_oldPsw);
		etNewPsw = (EditText) findViewById(R.id.et_changeUserPsw_newPsw);
		etSurePsw = (EditText) findViewById(R.id.et_changeUserPsw_surePsw);
		btnSureChange = (Button) findViewById(R.id.btn_sureChange);
		imgBtnExit = (ImageView) findViewById(R.id.imgBtn_changeUserPsw_exit);
		// llTransparent = (LinearLayout)
		// findViewById(R.id.ll_changeUserPsw_transparent);
		llBottom = (LinearLayout) findViewById(R.id.ll_changeUserPsw_bottom);
		//限制号牌号码的长度
		InputLengthControler ilc1=new InputLengthControler();
		ilc1.config(etOldPsw, ET_MAXLENGTH);
		InputLengthControler ilc2=new InputLengthControler();
		ilc2.config(etNewPsw, ET_MAXLENGTH);
		InputLengthControler ilc3=new InputLengthControler();
		ilc3.config(etSurePsw, ET_MAXLENGTH);
	}

	private void initEvent() {

		findViewById(R.id.ll_changeUserPsw_return).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
			}
		});
		btnSureChange.setOnClickListener(new MyClickListener());
		llBottom.setOnClickListener(new MyClickListener());
	}

	class MyClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			int vId = v.getId();
			switch (vId) {
			case R.id.btn_sureChange:

				String userName = etUserName.getText().toString().trim();
				String oldPsw = etOldPsw.getText().toString().trim();
				String newPsw = etNewPsw.getText().toString().trim();
				String surePsw = etSurePsw.getText().toString().trim();

				if (userName.trim() == null || userName.trim().equals("")) {
					Toast.makeText(getApplicationContext(), "用户名不可为空",
							Toast.LENGTH_SHORT).show();
				} else if (oldPsw.trim() == null || oldPsw.trim().equals("")) {
					Toast.makeText(getApplicationContext(), "原密码不可为空",
							Toast.LENGTH_SHORT).show();
				}else if (newPsw.trim() == null || newPsw.trim().equals("")) {
					Toast.makeText(getApplicationContext(), "新密码不可为空",
							Toast.LENGTH_SHORT).show();
				} else if (!newPsw.equals(surePsw)) {
					Toast.makeText(getApplicationContext(), "两次密码输入不一致，请重新输入",
							Toast.LENGTH_SHORT).show();
				} else if(oldPsw.equals(newPsw)){
					Toast.makeText(getApplicationContext(), "新密码和原密码一样，请重新输入",
							Toast.LENGTH_SHORT).show();
				}else {
					if (oldPsw.length() > 12 || oldPsw.length() < 6
							|| newPsw.length() > 12 || newPsw.length() < 6
							|| surePsw.length() > 12 || surePsw.length() < 6) {
						Toast.makeText(UserPasswordChangeActivity.this,
								"密码需在6——12位之间", Toast.LENGTH_SHORT).show();
					} else {
						// {'yhdh':'tydic','ymm':'tydictest','xmm':'tydictes1t','phone_id':'1'}
						Map<String, String> conMap = new HashMap<String, String>();
						conMap.put("yhdh", userName);
						conMap.put("ymm", oldPsw);
						conMap.put("xmm", newPsw);
						conMap.put("phone_id", MainActivity.tool
								.getImieStatus(getApplicationContext()));
						System.out.println("getGson().toJson(conMap):"
								+ MainActivity.gson.toJson(conMap));
						new MyAsyncTaskREQUserPswChange()
								.execute(MainActivity.gson.toJson(conMap));
					}
				}

				break;

			case R.id.ll_changeUserPsw_bottom:
				// llTransparent.setVisibility(View.VISIBLE);
				llBottom.setBackgroundResource(R.color.dark_blue);
				imgBtnExit.setBackgroundResource(R.drawable.return_on);
				endActivity();
				break;

			default:
				break;
			}
		}

	}

	/**
	 * 请求 修改密码
	 * 
	 * @author ouyyt
	 * 
	 */
	class MyAsyncTaskREQUserPswChange extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String resultStr = "";
			// 接口测试
			Constant.webService.setMETHOD_NAME(getApplicationContext()
					.getString(R.string.Connection_MethodName));
			Constant.webService.setWEBSERVICE(getApplicationContext()
					.getString(R.string.Connection_Webservice));
			if (Constant.webService.connect(new String[] { "jkid", "userid",
					"authid", "QueryXmlDoc" }, new Object[] { "V-EDITPWD",
					"SHDIC", "%E1%BC%EA%8F%B0%8E%0D%26%05%8E%A2u%B3%0A%D1%E5",
					params[0] })) {
				if (Constant.webService.getResult() != null) {
					resultStr = Constant.webService.getResult().toString();
				}
			}
			return resultStr;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			System.out.println("接口返回结果：" + result);
			if (result != null && !result.equals("")) {
				try {
					JSONObject obj = new JSONObject(result);
					String res = obj.getString("RESULT");
					String data = obj.getString("DATA");
					if (res.equals("TRUE")) {
						Toast.makeText(getApplicationContext(), data,
								Toast.LENGTH_LONG).show();
						//调至登录页面
						MainActivity.handler.sendEmptyMessage(0);
						Constant.user=null;
						endActivity();
					} else if (res.equals("FALSE")) {
						etOldPsw.setText("");
						etNewPsw.setText("");
						etSurePsw.setText("");
						Toast.makeText(getApplicationContext(), data,
								Toast.LENGTH_LONG).show();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} else {
				Toast.makeText(getApplicationContext(), "网络异常或者服务器异常。",
						Toast.LENGTH_LONG).show();
			}
		}
	};

	/**
	 * 按下返回按钮退出
	 */
	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			if (event.getAction() == KeyEvent.ACTION_UP) {
				return false;
			}
			endActivity();
			return true;
		}
		return super.dispatchKeyEvent(event);
	}
	
	/**
	 * 关闭  该页面
	 */
	private void endActivity(){
		finish();
		overridePendingTransition(R.anim.push_right_in,
				R.anim.push_right_out);
	}
}
