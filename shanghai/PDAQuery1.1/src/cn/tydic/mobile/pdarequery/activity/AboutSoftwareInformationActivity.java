package cn.tydic.mobile.pdarequery.activity;

import cn.tydic.mobile.pdarequery.R;
import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/***
 * 关于软件信息------PDA
 * 
 */
public class AboutSoftwareInformationActivity extends Activity {
	private ImageView iv_back;
	private TextView tv_version;
	private String versionCode;
	private RelativeLayout rl_back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aboutsoft);

		// 获取软件版本号
		try {
			PackageInfo packageInfo = AboutSoftwareInformationActivity.this
					.getPackageManager()
					.getPackageInfo(
							AboutSoftwareInformationActivity.this
									.getApplicationInfo().packageName,
							0);
			versionCode = packageInfo.versionName;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tv_version = (TextView) findViewById(R.id.tv_version);
		tv_version.setText(versionCode);
		
		findViewById(R.id.ll_aboutsoft_return).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
			}
		});
		
		iv_back = (ImageView) findViewById(R.id.imgBtn_aboutsoft_close);
		rl_back = (RelativeLayout) findViewById(R.id.rl_aboutsoft);
		rl_back.setOnClickListener(new RelativeLayout.OnClickListener() {

			@Override
			public void onClick(View v) {
				rl_back.setBackgroundResource(R.color.dark_blue);
				iv_back.setBackgroundResource(R.drawable.return_on);
				finish();
				overridePendingTransition(R.anim.push_right_in,
						R.anim.push_right_out);
			}
		});
		iv_back.setOnClickListener(new ImageView.OnClickListener() {

			@Override
			public void onClick(View v) {
				rl_back.setBackgroundResource(R.color.dark_blue);
				iv_back.setBackgroundResource(R.drawable.return_on);
				finish();
				overridePendingTransition(R.anim.push_right_in,
						R.anim.push_right_out);
			}
		});
	}

	/**
	 * 按下返回按钮退出
	 */
	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			if (event.getAction() == KeyEvent.ACTION_UP) {
				return false;
			}
			finish();
			overridePendingTransition(R.anim.push_right_in,
					R.anim.push_right_out);
			return true;
		}
		return super.dispatchKeyEvent(event);
	}
}
