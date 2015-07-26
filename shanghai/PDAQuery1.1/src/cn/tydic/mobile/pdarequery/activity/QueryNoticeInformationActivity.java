package cn.tydic.mobile.pdarequery.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.reflect.TypeToken;

import cn.tydic.mobile.pdarequery.MainActivity;
import cn.tydic.mobile.pdarequery.R;
import cn.tydic.mobile.pdarequery.adapter.QueryNoticeAdapter;
import cn.tydic.mobile.pdarequery.entity.NoticeInformation;
import cn.tydic.mobile.pdarequery.tools.Constant;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/***
 * 公告信息查询---------PDA
 * 
 * @author zhangyx
 * 
 */
public class QueryNoticeInformationActivity extends Activity {

	public static List<NoticeInformation> noticeLists = new ArrayList<NoticeInformation>();// 总公告信息集合
	private List<NoticeInformation> noticeList = new ArrayList<NoticeInformation>();// 每次请求的公告信息集合
	private QueryNoticeAdapter noticeAdapter;
	private ListView lv_noticeData;
	private EditText et_number;
	private TextView tv_select, tv_noticeInfoResult;
	private int pageIndex = 0;// 页数
	private boolean isClear = false;// /是否清空数据
	private String BJ = null;// 标志是否最后一页
	private View footer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.query_noticeinformation);
		initView();
		onExecute();
	}

	@SuppressLint("ParserError")
	private void onExecute() {
		// TODO Auto-generated method stub
		// MyAsyncTaskREQNotice
		tv_select.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				((TextView) footer.findViewById(R.id.tv_lvFooter))
						.setText("加载更多数据");
				pageIndex = 0;
				reqData(pageIndex + "");
				isClear = true;
				if (isClear) {
					noticeLists.clear();
				}
			}
		});

		lv_noticeData.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(QueryNoticeInformationActivity.this,
						NoticeInformationActivity.class);
				intent.putExtra("position", arg2 + "");
				startActivity(intent);
			}
		});
	}

	private void reqData(String pageIndex) {
		// "{'clxh':'EQ5050XXY4','yhdh':'tydic','phone_id':'1','page_index':'0'}"
		String number = et_number.getText().toString().trim();
		System.out.println("number----" + number);
		if (number == null || number.equals("")) {
			Toast.makeText(getApplicationContext(), "你好，请输入车辆型号。", Toast.LENGTH_SHORT).show();
		} else {
			if(number.length()>40){
				Toast.makeText(getApplicationContext(), "车辆型号长度的范围[1-40]个数字、字母。", Toast.LENGTH_SHORT).show();
			}else{
				if (Constant.user != null) {
					if (number.length() > 14) {
						Toast.makeText(QueryNoticeInformationActivity.this,
								"请输入准确的车辆型号", Toast.LENGTH_SHORT).show();
					} else {
						Map<String, String> conMap = new HashMap<String, String>();
						conMap.put("clxh", number);//
						conMap.put("page_index", pageIndex + "");
						conMap.put("yhdh", Constant.user.getUserName());
						conMap.put("phone_id", Constant.user.getPhoneId());
						MainActivity.gson.toJson(conMap);
						new MyAsyncTaskREQNotice().execute(MainActivity.gson
								.toJson(conMap));
					}
				} else {
					Toast.makeText(getApplicationContext(), "你好，请先登陆。", Toast.LENGTH_SHORT).show();
				}
			}
		}
	}

	private void initView() {
		// TODO Auto-generated method stub
		noticeLists.clear();
		et_number = (EditText) findViewById(R.id.et_number);
		tv_select = (TextView) findViewById(R.id.tv_select);
		tv_noticeInfoResult = (TextView) findViewById(R.id.tv_noticeInfoResult);
		lv_noticeData = (ListView) findViewById(R.id.lv_noticeData);
		noticeAdapter = new QueryNoticeAdapter(getApplicationContext(),
				noticeLists);
		footer = LayoutInflater.from(getApplicationContext()).inflate(
				R.layout.listview_footer, null);
		lv_noticeData.addFooterView(footer);
		lv_noticeData.setAdapter(noticeAdapter);
		
		findViewById(R.id.ll_queryNoticeInfo_return).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
			}
		});
		
		findViewById(R.id.ll_queryNoticeInfo_exit).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						findViewById(R.id.ll_queryNoticeInfo_exit)
								.setBackgroundResource(R.color.dark_blue);
						findViewById(R.id.imgBtn_queryNoticeInfo_close)
								.setBackgroundResource(R.drawable.return_on);
						finish();
						overridePendingTransition(R.anim.push_right_in,
								R.anim.push_right_out);
					}
				});

		footer.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (BJ != null && !BJ.equals("")) {
					if (BJ.equals("0")) {
						pageIndex++;
						reqData(pageIndex + "");
					} else if (BJ.equals("1")) {
						((TextView) footer.findViewById(R.id.tv_lvFooter))
								.setText("无更多数据加载");
					}
				}
			}
		});
	}

	/**
	 * 请求 公告信息查询
	 * 
	 * @author zhangyx
	 * 
	 */
	class MyAsyncTaskREQNotice extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			MainActivity.tool.startProgressDialog(getApplicationContext());
			tv_noticeInfoResult.setVisibility(View.VISIBLE);
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String resultStr = "";
			// //接口1：----测试成功
			Constant.webService.setMETHOD_NAME(getApplicationContext()
					.getString(R.string.Connection_MethodName));
			Constant.webService.setWEBSERVICE(getApplicationContext()
					.getString(R.string.Connection_Webservice));
			if (Constant.webService.connect(new String[] { "jkid", "userid",
					"authid", "QueryXmlDoc" }, new Object[] { "V-PARA",
					"SHDIC", "%E1%BC%EA%8F%B0%8E%0D%26%05%8E%A2u%B3%0A%D1%E5",
					params[0] })) {
				if (Constant.webService.getResult() != null) {
					resultStr = Constant.webService.getResult().toString();
				}
			}
			return resultStr;
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			System.out.println("返回结果：" + result);
			MainActivity.tool.stopProgressDialog();
			tv_noticeInfoResult.setVisibility(View.GONE);
			if (result != null && !result.equals("")) {
				try {
					JSONObject obj = new JSONObject(result);
					String res = obj.getString("RESULT");
					String data = obj.getString("DATA");
					noticeList.clear();
					if (res.equals("TRUE")) {
						BJ = obj.getString("BJ");
						noticeList.addAll((List) MainActivity.gson.fromJson(
								data, new TypeToken<List<NoticeInformation>>() {
								}.getType()));
					} else if (res.equals("FALSE")) {
						Toast.makeText(getApplicationContext(), data,
								Toast.LENGTH_LONG).show();
					}
					reflushData();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				Toast.makeText(getApplicationContext(), "网络异常或者服务器异常。",
						Toast.LENGTH_LONG).show();
			}
		}

	};

	/***
	 * 刷新数据
	 */
	private void reflushData() {
		// TODO Auto-generated method stub

		if (noticeList.size() > 0) {
			for (NoticeInformation n : noticeList) {
				if (isClear) {
					noticeLists.clear();
					isClear = false;
				}
				noticeLists.add(n);
			}
		}
		if (footer.getVisibility() == View.GONE)
			footer.setVisibility(View.VISIBLE);
		noticeAdapter.notifyDataSetChanged();
		if (noticeLists.size() == 0) {
			footer.setVisibility(View.GONE);
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
