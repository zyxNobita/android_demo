package cn.tydic.mobile.pdarequery.activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import cn.tydic.mobile.pdarequery.MainActivity;
import cn.tydic.mobile.pdarequery.R;
import cn.tydic.mobile.pdarequery.adapter.JobAccountingAdapter;
import cn.tydic.mobile.pdarequery.date.AlternativeDateSlider;
import cn.tydic.mobile.pdarequery.date.DateSlider;
import cn.tydic.mobile.pdarequery.entity.JobAccountingInfo;
import cn.tydic.mobile.pdarequery.tools.Constant;

import com.google.gson.reflect.TypeToken;

/***
 * 工作台账统计----PDA
 * 
 */
@SuppressLint("HandlerLeak")
public class JobAccountingActivity extends Activity {

	private EditText etBeginTime, etEndTime;// 选择开始时间，选择结束时间
	private TextView tvCount, tvBeginTime, tvEndTime;// 统计按钮，获取开始时间，获取结束时间
	private ListView lvJobAccount;// ListView显示数据
	private ImageView imgBtnExit;// 退出工作台账统计界面
	private LinearLayout llContent;
	private LinearLayout llBottom;
	private String beginTimeRes, endTimeRes;
	private JobAccountingAdapter jaAdapter;
	private List<JobAccountingInfo> jobAccountLists = new ArrayList<JobAccountingInfo>();
	//修改日期控件
	private static final int STARTDATE_DIALOG_ID = 2;
	private static final int ENDDATE_DIALOG_ID = 3;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.jobaccount);
		initView();
		initEvent();
	}

	private void initView() {

		etBeginTime = (EditText) findViewById(R.id.et_jobAccount_beginTime);
		etEndTime = (EditText) findViewById(R.id.et_jobAccount_endTime);
		tvCount = (TextView) findViewById(R.id.tv_jobAccount_count);
		tvBeginTime = (TextView) findViewById(R.id.tv_beginTime);
		tvEndTime = (TextView) findViewById(R.id.tv_endTime);
		lvJobAccount = (ListView) findViewById(R.id.lv_jobAccount);
		imgBtnExit = (ImageView) findViewById(R.id.imgBtn_jobAccount_exit);
		llContent = (LinearLayout) findViewById(R.id.ll_jobAccount_content);
		llBottom = (LinearLayout) findViewById(R.id.ll_jobAccount_bottom);
		
		//给开始、结束时间赋初始值
		etBeginTime.setText(MainActivity.tool.requestJobData());
		etEndTime.setText(MainActivity.tool.requestJobData());
	}

	private void initEvent() {
		
		
		
		etBeginTime.setInputType(InputType.TYPE_NULL);
		etEndTime.setInputType(InputType.TYPE_NULL);
		tvCount.setOnClickListener(new MyClickListener());

		
		etBeginTime.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showDialog(STARTDATE_DIALOG_ID);
			}
		});
		
		etEndTime.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showDialog(ENDDATE_DIALOG_ID);
			}
		});
		
		jaAdapter = new JobAccountingAdapter(JobAccountingActivity.this,
				jobAccountLists);
		lvJobAccount.setAdapter(jaAdapter);
		
		findViewById(R.id.ll_jobAccount_return).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
			}
		});

		llBottom.setOnClickListener(new MyClickListener());
	}

	class MyClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			int vId = v.getId();
			switch (vId) {
			case R.id.tv_jobAccount_count:
				if (Constant.user != null) {
					if (etBeginTime != null
							&& !etBeginTime.getText().toString().equals("")
							&& etEndTime != null
							&& !etEndTime.getText().toString().equals("")) {
						//比较开始时间与结束时间
						String beginTime = etBeginTime.getText().toString();
						String endTime = etEndTime.getText().toString();
						System.out.println("etBeginTime"+beginTime+"===="+"etEndTime"+endTime);
						
						if (MainActivity.tool.requestJobDataBJ(beginTime, endTime)) {
							System.out.println("时间比较：开始-->"+beginTimeRes+"结束---->"+endTimeRes);
							tvBeginTime.setText(beginTime);
							tvEndTime.setText(endTime);
							// {'kssj':'2014-01-01','jssj:'2014-01-02','yhdh':'tydic','phone_id':'00001'}
							// {'kssj':'2014-01-01','jssj:'2014-01-16','yhdh':'test2','phone_id':'860275020786106'}
							Map<String, String> conMap = new HashMap<String, String>();
							conMap.put("kssj", beginTime);
							conMap.put("jssj", endTime);
							conMap.put("yhdh", Constant.user.getUserName());
							conMap.put("phone_id", MainActivity.tool
									.getImieStatus(getApplicationContext()));
							System.out.println("getGson().toJson(conMap):"
									+ MainActivity.gson.toJson(conMap));
							new MyAsyncTaskREQJobAccount()
									.execute(MainActivity.gson.toJson(conMap));
							// new MyAsyncTaskREQJobAccount().execute();
						} else {
							jobAccountLists.clear();
							jaAdapter.notifyDataSetChanged();
							llContent.setVisibility(View.GONE);
							Toast.makeText(getApplicationContext(),
									"结束时间不能小于开始时间", Toast.LENGTH_SHORT).show();
						}

					} else {
						Toast.makeText(getApplicationContext(), "请选择日期",
								Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(getApplicationContext(), "请先登录",
							Toast.LENGTH_SHORT).show();
				}

				break;

			case R.id.ll_jobAccount_bottom:
				llBottom.setBackgroundResource(R.color.dark_blue);
				imgBtnExit.setBackgroundResource(R.drawable.return_on);
				finish();
				overridePendingTransition(R.anim.push_right_in,
						R.anim.push_right_out);
				break;

			default:
				break;
			}
		}

	}
	
	/**
	 * 开始日期控件的事件
	 */
	private DateSlider.OnDateSetListener mStartDateSetListener = new DateSlider.OnDateSetListener() {
		public void onDateSet(DateSlider view, Calendar selectedDate) {
			// update the dateText view with the corresponding date
			etBeginTime.setText(String.format("%tY-%tB-%te", selectedDate, selectedDate, selectedDate).replace("月", ""));
		}
	};
	
	/**
	 * 结束日期控件的事件
	 */
	private DateSlider.OnDateSetListener mEndDateSetListener = new DateSlider.OnDateSetListener() {
		public void onDateSet(DateSlider view, Calendar selectedDate) {
			// update the dateText view with the corresponding date
			etEndTime.setText(String.format("%tY-%tB-%te", selectedDate, selectedDate, selectedDate).replace("月", ""));
		}
	};
	
    @Override
    protected Dialog onCreateDialog(int id) {
    	// this method is called after invoking 'showDialog' for the first time
    	// here we initiate the corresponding DateSlideSelector and return the dialog to its caller

    	// get todays date and the time
        final Calendar c = Calendar.getInstance();
        switch (id) {
        case STARTDATE_DIALOG_ID:
            return new AlternativeDateSlider(JobAccountingActivity.this, mStartDateSetListener, c);
        case ENDDATE_DIALOG_ID:
            return new AlternativeDateSlider(JobAccountingActivity.this, mEndDateSetListener, c);
        }
        return null;
    }
	
	/**
	 * 请求 工作台账统计信息
	 * 
	 * @author ouyyt
	 * 
	 */
	class MyAsyncTaskREQJobAccount extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			MainActivity.tool.startProgressDialog(getApplicationContext());
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
					"authid", "QueryXmlDoc" }, new Object[] { "V-YWTJ",
					"SHDIC", "h%E8%85%03%931%95%5B%2B%B6%F6%1EfI%05%DE",
					params[0] })) {
				if (Constant.webService.getResult() != null) {
					resultStr = Constant.webService.getResult().toString();
				}
			}
			return resultStr;
		}

		@SuppressWarnings({ "rawtypes", "unchecked" })
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			System.out.println("接口返回结果：" + result);
			MainActivity.tool.stopProgressDialog();
			if (result != null && !result.equals("")) {
				try {
					JSONObject obj = new JSONObject(result);
					String res = obj.getString("RESULT");
					String data = obj.getString("DATA");
					if (res.equals("TRUE")) {
						llContent.setVisibility(View.VISIBLE);
						jobAccountLists.clear();
						jobAccountLists
								.addAll((List) MainActivity.gson
										.fromJson(
												data,
												new TypeToken<List<JobAccountingInfo>>() {
												}.getType()));
						jaAdapter.notifyDataSetChanged();
						Toast.makeText(getApplicationContext(), "统计数据成功",
								Toast.LENGTH_LONG).show();
					} else if (res.equals("FALSE")) {
						llContent.setVisibility(View.GONE);
						Toast.makeText(getApplicationContext(), data,
								Toast.LENGTH_LONG).show();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} else {
				llContent.setVisibility(View.GONE);
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
			finish();
			overridePendingTransition(R.anim.push_right_in,
					R.anim.push_right_out);
			return true;
		}
		return super.dispatchKeyEvent(event);
	}
}