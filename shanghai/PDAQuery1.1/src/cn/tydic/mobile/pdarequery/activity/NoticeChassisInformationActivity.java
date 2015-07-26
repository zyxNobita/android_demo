package cn.tydic.mobile.pdarequery.activity;

import cn.tydic.mobile.pdarequery.R;
import cn.tydic.mobile.pdarequery.entity.ChassisInformation;
import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

/***
 * 公告信息查询：底盘详细信息-----PDA
 * 
 * @author zhangyx
 * 
 */
public class NoticeChassisInformationActivity extends Activity {

	private ChassisInformation chassis;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chassisinformation);
		doExecute();
	}

	private void doExecute() {
		// TODO Auto-generated method stub
		// String position = getIntent().getStringExtra("CHASSIS_INDEX");
		int p = QueryNoticeChassisActivity.CHASSIS_INDEX;
		if (p >= 0) {
			chassis = QueryNoticeChassisActivity.chassisLists.get(p);
		}
		showData();
		
		findViewById(R.id.ll_chassisInfo_return).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
			}
		});
		
		findViewById(R.id.ll_chassisInfo_exit).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						findViewById(R.id.ll_chassisInfo_exit)
								.setBackgroundResource(R.color.dark_blue);
						findViewById(R.id.imgBtn_chassisInfo_close)
								.setBackgroundResource(R.drawable.return_on);
						finish();
						overridePendingTransition(R.anim.push_right_in,
								R.anim.push_right_out);
					}
				});
	}

	private void showData() {
		// TODO Auto-generated method stub
		if (chassis != null) {
			((TextView) findViewById(R.id.tv_text1)).setText(chassis.getBh());
			((TextView) findViewById(R.id.tv_text2)).setText(chassis.getZzl());
			((TextView) findViewById(R.id.tv_text3)).setText(chassis.getDpid());
			((TextView) findViewById(R.id.tv_text4)).setText(chassis.getZbzl());
			((TextView) findViewById(R.id.tv_text5)).setText(chassis.getQyid());
			((TextView) findViewById(R.id.tv_text6))
					.setText(chassis.getZqyzl());
			((TextView) findViewById(R.id.tv_text7)).setText(chassis.getScdz());
			((TextView) findViewById(R.id.tv_text8))
					.setText(chassis.getFdjxh());
			((TextView) findViewById(R.id.tv_text9)).setText(chassis.getDpxh());
			((TextView) findViewById(R.id.tv_text10)).setText(chassis.getPl());
			((TextView) findViewById(R.id.tv_text11))
					.setText(chassis.getDplb());
			((TextView) findViewById(R.id.tv_text12)).setText(chassis.getGl());

			((TextView) findViewById(R.id.tv_text13))
					.setText(chassis.getCpmc());
			((TextView) findViewById(R.id.tv_text14))
					.setText(chassis.getSbdh());
			((TextView) findViewById(R.id.tv_text15))
					.setText(chassis.getCpsb());
			((TextView) findViewById(R.id.tv_text16))
					.setText(chassis.getCslx());
			((TextView) findViewById(R.id.tv_text17)).setText(chassis.getC());
			((TextView) findViewById(R.id.tv_text18)).setText(chassis.getBz());
			((TextView) findViewById(R.id.tv_text19)).setText(chassis.getK());
			((TextView) findViewById(R.id.tv_text20)).setText(chassis
					.getZzcmc());
			((TextView) findViewById(R.id.tv_text21)).setText(chassis.getG());
			((TextView) findViewById(R.id.tv_text22))
					.setText(chassis.getGgrq());
			((TextView) findViewById(R.id.tv_text23))
					.setText(chassis.getRlzl());
			((TextView) findViewById(R.id.tv_text24)).setText(chassis
					.getCxsxrq());
			((TextView) findViewById(R.id.tv_text25))
					.setText(chassis.getYjbz());
			((TextView) findViewById(R.id.tv_text26))
					.setText(chassis.getQpzk());
			((TextView) findViewById(R.id.tv_text27))
					.setText(chassis.getZxxs());
			((TextView) findViewById(R.id.tv_text28))
					.setText(chassis.getHpzk());
			((TextView) findViewById(R.id.tv_text29)).setText(chassis.getZs());
			((TextView) findViewById(R.id.tv_text30)).setText(chassis
					.getGgsxrq());
			((TextView) findViewById(R.id.tv_text31)).setText(chassis.getZj());
			((TextView) findViewById(R.id.tv_text32)).setText(chassis
					.getCxggrq());
			((TextView) findViewById(R.id.tv_text33)).setText(chassis
					.getGbthps());
			((TextView) findViewById(R.id.tv_text34)).setText(chassis
					.getTzscrq());
			((TextView) findViewById(R.id.tv_text35)).setText(chassis.getLts());
			((TextView) findViewById(R.id.tv_text36)).setText(chassis
					.getFdjqy());
			((TextView) findViewById(R.id.tv_text37))
					.setText(chassis.getLtgg());
			((TextView) findViewById(R.id.tv_text38)).setText(chassis
					.getYwabs());
			((TextView) findViewById(R.id.tv_text39)).setText(chassis.getQlj());
			((TextView) findViewById(R.id.tv_text40))
					.setText(chassis.getQydm());
			((TextView) findViewById(R.id.tv_text41)).setText(chassis.getHlj());
			((TextView) findViewById(R.id.tv_text42))
					.setText(chassis.getGgbj());
		}
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
