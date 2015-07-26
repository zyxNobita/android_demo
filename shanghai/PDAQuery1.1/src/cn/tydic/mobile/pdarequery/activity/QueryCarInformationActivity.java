package cn.tydic.mobile.pdarequery.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import cn.tydic.mobile.pdarequery.MainActivity;
import cn.tydic.mobile.pdarequery.R;
import cn.tydic.mobile.pdarequery.entity.CarInformation;
import cn.tydic.mobile.pdarequery.entity.CarNnumberType;
import cn.tydic.mobile.pdarequery.tools.Constant;
import cn.tydic.mobile.pdarequery.tools.InputLengthControler;

import com.google.gson.reflect.TypeToken;

/****
 * 车辆信息查询------PDA
 * 
 * @author zhangyx
 * 
 */
public class QueryCarInformationActivity extends Activity {

	private Spinner spn_number;
	private EditText et_number;
	private Integer ET_MAXLENGTH=6;//输入的号牌号码为6位
	private TextView tv_select, tv_carInfoResult;
	private List<CarNnumberType> carTypes = new ArrayList<CarNnumberType>();// 号牌种类----数据集合
	private List<String> carTypeList = new ArrayList<String>();// 号牌种类----显示集合
	private String carType = null;// 提交的数据-----号牌种类
	private ArrayAdapter<String> spnAdapter;
	private List<CarInformation> carLsits = new ArrayList<CarInformation>();// 车辆信息

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.query_carinformation);
		initView();
		doExecute();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	private void doExecute() {
		// TODO Auto-generated method stub
		spnAdapter = new ArrayAdapter<String>(getApplicationContext(),
				R.layout.query_car_spinner_item, carTypeList);
		spnAdapter.setDropDownViewResource(R.layout.query_car_spinner_item);
		//spn_number.setSelection(1, true);
		spn_number.setAdapter(spnAdapter);
		if (Constant.user != null) {
			String number = et_number.getText().toString();
			Map<String, String> conMap = new HashMap<String, String>();
			conMap.put("xtlb", "002");// 查询大类菜单
			conMap.put("dmlb", "87");
			conMap.put("yhdh", Constant.user.getUserName());
			conMap.put("phone_id", Constant.user.getPhoneId());
			// String json =
			// "{'yhdh':'tydic','phone_id':'1','dmlb':'87','xtlb':'002'}";
			new MyAsyncTaskREQCarType().execute(MainActivity.gson
					.toJson(conMap));
		} else {
			Toast.makeText(getApplicationContext(), "你好，请先登陆。", Toast.LENGTH_SHORT).show();
		}

		tv_select.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// "{'hpzl':'02','hphm':'D13959','yhdh':'tydic','phone_id':'1'}"
				String number = et_number.getText().toString();
				if (number == null || number.equals("")) {
					Toast.makeText(getApplicationContext(), "你好，请先输入号牌号码。", Toast.LENGTH_SHORT)
							.show();
				} else {
					if (Constant.user != null) {
						if (number.length() > 6) {
							Toast.makeText(QueryCarInformationActivity.this,
									"请输入6位以内的号牌号码", Toast.LENGTH_SHORT).show();
						} else {
							Map<String, String> conMap = new HashMap<String, String>();
							conMap.put(
									"hpzl",
									(String) spn_number
											.getSelectedItem()
											.toString()
											.trim()
											.subSequence(
													0,
													spn_number
															.getSelectedItem()
															.toString()
															.indexOf(":")));// 查询大类菜单
							conMap.put("hphm", number);
							conMap.put("yhdh", Constant.user.getUserName());
							conMap.put("phone_id", Constant.user.getPhoneId());
							MainActivity.gson.toJson(conMap);
							carTypes.clear();
							new MyAsyncTaskREQCarInformation()
									.execute(MainActivity.gson.toJson(conMap));
						}
					} else {
						Toast.makeText(getApplicationContext(), "你好，请先登陆。", Toast.LENGTH_SHORT)
								.show();
					}
				}
			}
		});
	}

	private void initView() {
		// TODO Auto-generated method stub
		spn_number = (Spinner) findViewById(R.id.spn_number);
		et_number = (EditText) findViewById(R.id.et_number);
		tv_select = (TextView) findViewById(R.id.tv_select);
		tv_carInfoResult = (TextView) findViewById(R.id.tv_carInfoResult);
		//限制号牌号码的长度
		InputLengthControler ilc=new InputLengthControler();
		ilc.config(et_number, ET_MAXLENGTH);
		
		findViewById(R.id.ll_queryCarInfo_return).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
			}
		});
		
		findViewById(R.id.ll_queryCarInfo_exit).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						findViewById(R.id.ll_queryCarInfo_exit)
								.setBackgroundResource(R.color.dark_blue);
						findViewById(R.id.imgBtn_queryCarInfo_close)
								.setBackgroundResource(R.drawable.return_on);
						Intent intent = getIntent();
						setResult(RESULT_OK, intent);
						finish();
						overridePendingTransition(R.anim.push_right_in,
								R.anim.push_right_out);
					}
				});
	}
	/**
	 * 请求 号牌种类
	 * 
	 * @author zhangyx
	 * 
	 */
	class MyAsyncTaskREQCarType extends AsyncTask<String, String, String> {

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
			// //接口1：----测试成功
			// iInterface
			Constant.webService.setMETHOD_NAME(getApplicationContext()
					.getString(R.string.Connection_MethodName));
			// zwapp/service/IServicePro
			Constant.webService.setWEBSERVICE(getApplicationContext()
					.getString(R.string.Connection_Webservice));
			if (Constant.webService.connect(new String[] { "jkid", "userid",
					"authid", "QueryXmlDoc" }, new Object[] { "V-CODE",
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
			MainActivity.tool.stopProgressDialog();
			if (result != null && !result.equals("")) {
				try {
					JSONObject obj = new JSONObject(result);
					String res = obj.getString("RESULT");
					String data = obj.getString("DATA");
					if (res.equals("TRUE")) {
						carTypes.clear();
						carTypes.addAll((List) MainActivity.gson.fromJson(data,
								new TypeToken<List<CarNnumberType>>() {
								}.getType()));
						addDataToSpinner();
					} else if (res.equals("FALSE")) {
						Toast.makeText(getApplicationContext(), data,
								Toast.LENGTH_LONG).show();
					}
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

	/**
	 * 添加请求数据
	 */
	private void addDataToSpinner() {
		if (carTypes.size() > 0) {
			carTypeList.clear();
			for (CarNnumberType car : carTypes) {
				System.out.println("号牌种类：" + car.getDmsm1());
				carTypeList.add(car.getDmsm1());
			}
			//设置默认选中
			if(carTypeList.size()>1){
				spn_number.setSelection(1, true);
			}
		}
		spnAdapter.notifyDataSetChanged();
	}

	/**
	 * 请求 车辆信息
	 * 
	 * @author zhangyx
	 * 
	 */
	class MyAsyncTaskREQCarInformation extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			MainActivity.tool.startProgressDialog(getApplicationContext());
			tv_carInfoResult.setVisibility(View.VISIBLE);
			tv_carInfoResult.setText("正在查询……");
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
					"authid", "QueryXmlDoc" }, new Object[] { "V-VEHCILE",
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
			System.out.println("接口返回结果：" + result);
			MainActivity.tool.stopProgressDialog();
			tv_carInfoResult.setText(getResources().getString(R.string.tv_carTitle));
			if (result != null && !result.equals("")) {
				try {
					JSONObject obj = new JSONObject(result);
					String res = obj.getString("RESULT");
					String data = obj.getString("DATA");
					carLsits.clear();
					if (res.equals("TRUE")) {
						carLsits.addAll((List) MainActivity.gson.fromJson(data,
								new TypeToken<List<CarInformation>>() {
								}.getType()));
					} else if (res.equals("FALSE")) {
						Toast.makeText(getApplicationContext(), data,
								Toast.LENGTH_LONG).show();
					}
					showCarInformation();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} else {
				Toast.makeText(getApplicationContext(), "网络异常或者服务器异常。",
						Toast.LENGTH_LONG).show();
			}
		}
	};

	/***
	 * 为车辆信息赋值------（findView AddValue）
	 */
	private void showCarInformation() {
		if (carLsits.size() > 0) {
			CarInformation car = carLsits.get(0);
			((TextView) findViewById(R.id.tv_text1)).setText(car.getHpzl());
			((TextView) findViewById(R.id.tv_text2)).setText(car.getSfzmmc());
			((TextView) findViewById(R.id.tv_text3)).setText(car.getHphm());
			((TextView) findViewById(R.id.tv_text4)).setText(car.getSyr());
			((TextView) findViewById(R.id.tv_text5)).setText(car.getClpp1());
			((TextView) findViewById(R.id.tv_text6)).setText(car.getCcdjrq());
			((TextView) findViewById(R.id.tv_text7)).setText(car.getClxh());
			((TextView) findViewById(R.id.tv_text8)).setText(car.getYxqz());
			((TextView) findViewById(R.id.tv_text9)).setText(car.getClpp2());
			((TextView) findViewById(R.id.tv_text10)).setText(car.getQzbfqz());
			((TextView) findViewById(R.id.tv_text11)).setText(car.getGcjk());
			((TextView) findViewById(R.id.tv_text12)).setText(car.getXzqh());
			((TextView) findViewById(R.id.tv_text13)).setText(car.getCsys());
			((TextView) findViewById(R.id.tv_text14)).setText(car.getZt());
			((TextView) findViewById(R.id.tv_text15)).setText(car.getSyxz());
			((TextView) findViewById(R.id.tv_text16)).setText(car.getDybj());
			((TextView) findViewById(R.id.tv_text17)).setText(car.getSfzmhm());
			((TextView) findViewById(R.id.tv_text18)).setText(car.getClyt());
			((TextView) findViewById(R.id.tv_text19)).setText(car.getZzg());
			((TextView) findViewById(R.id.tv_text20)).setText(car.getZs());
			((TextView) findViewById(R.id.tv_text21)).setText(car.getClsbdh());
			((TextView) findViewById(R.id.tv_text22)).setText(car.getZj());
			((TextView) findViewById(R.id.tv_text23)).setText(car.getFdjh());
			((TextView) findViewById(R.id.tv_text24)).setText(car.getQlj());
			((TextView) findViewById(R.id.tv_text25)).setText(car.getCllx());
			((TextView) findViewById(R.id.tv_text26)).setText(car.getHlj());
			((TextView) findViewById(R.id.tv_text27)).setText(car.getFdjxh());
			((TextView) findViewById(R.id.tv_text28)).setText(car.getLtgg());
			((TextView) findViewById(R.id.tv_text29)).setText(car.getRlzl());
			((TextView) findViewById(R.id.tv_text30)).setText(car.getLts());
			((TextView) findViewById(R.id.tv_text31)).setText(car.getPl());
			((TextView) findViewById(R.id.tv_text32)).setText(car.getZzl());
			((TextView) findViewById(R.id.tv_text33)).setText(car.getGl());
			((TextView) findViewById(R.id.tv_text34)).setText(car.getZbzl());
			((TextView) findViewById(R.id.tv_text35)).setText(car.getZxxs());
			((TextView) findViewById(R.id.tv_text36)).setText(car.getHdzzl());
			((TextView) findViewById(R.id.tv_text37)).setText(car.getCwkc());
			((TextView) findViewById(R.id.tv_text38)).setText(car.getHdzk());
			((TextView) findViewById(R.id.tv_text39)).setText(car.getCwkk());
			((TextView) findViewById(R.id.tv_text40)).setText(car.getZqyzl());
			((TextView) findViewById(R.id.tv_text41)).setText(car.getCwkg());
			((TextView) findViewById(R.id.tv_text42)).setText(car.getQpzk());
			((TextView) findViewById(R.id.tv_text43)).setText(car.getHxnbcd());
			((TextView) findViewById(R.id.tv_text44)).setText(car.getHpzk());
			((TextView) findViewById(R.id.tv_text45)).setText(car.getHxnbgd());
			((TextView) findViewById(R.id.tv_text46)).setText(car.getCcrq());
			((TextView) findViewById(R.id.tv_text47)).setText(car.getHxnbkd());
			((TextView) findViewById(R.id.tv_text48)).setText(car.getDphgzbh());
			((TextView) findViewById(R.id.tv_text49)).setText(car.getGbthps());
		}else{
			((TextView) findViewById(R.id.tv_text1)).setText("");
			((TextView) findViewById(R.id.tv_text2)).setText("");
			((TextView) findViewById(R.id.tv_text3)).setText("");
			((TextView) findViewById(R.id.tv_text4)).setText("");
			((TextView) findViewById(R.id.tv_text5)).setText("");
			((TextView) findViewById(R.id.tv_text6)).setText("");
			((TextView) findViewById(R.id.tv_text7)).setText("");
			((TextView) findViewById(R.id.tv_text8)).setText("");
			((TextView) findViewById(R.id.tv_text9)).setText("");
			((TextView) findViewById(R.id.tv_text10)).setText("");
			((TextView) findViewById(R.id.tv_text11)).setText("");
			((TextView) findViewById(R.id.tv_text12)).setText("");
			((TextView) findViewById(R.id.tv_text13)).setText("");
			((TextView) findViewById(R.id.tv_text14)).setText("");
			((TextView) findViewById(R.id.tv_text15)).setText("");
			((TextView) findViewById(R.id.tv_text16)).setText("");
			((TextView) findViewById(R.id.tv_text17)).setText("");
			((TextView) findViewById(R.id.tv_text18)).setText("");
			((TextView) findViewById(R.id.tv_text19)).setText("");
			((TextView) findViewById(R.id.tv_text20)).setText("");
			((TextView) findViewById(R.id.tv_text21)).setText("");
			((TextView) findViewById(R.id.tv_text22)).setText("");
			((TextView) findViewById(R.id.tv_text23)).setText("");
			((TextView) findViewById(R.id.tv_text24)).setText("");
			((TextView) findViewById(R.id.tv_text25)).setText("");
			((TextView) findViewById(R.id.tv_text26)).setText("");
			((TextView) findViewById(R.id.tv_text27)).setText("");
			((TextView) findViewById(R.id.tv_text28)).setText("");
			((TextView) findViewById(R.id.tv_text29)).setText("");
			((TextView) findViewById(R.id.tv_text30)).setText("");
			((TextView) findViewById(R.id.tv_text31)).setText("");
			((TextView) findViewById(R.id.tv_text32)).setText("");
			((TextView) findViewById(R.id.tv_text33)).setText("");
			((TextView) findViewById(R.id.tv_text34)).setText("");
			((TextView) findViewById(R.id.tv_text35)).setText("");
			((TextView) findViewById(R.id.tv_text36)).setText("");
			((TextView) findViewById(R.id.tv_text37)).setText("");
			((TextView) findViewById(R.id.tv_text38)).setText("");
			((TextView) findViewById(R.id.tv_text39)).setText("");
			((TextView) findViewById(R.id.tv_text40)).setText("");
			((TextView) findViewById(R.id.tv_text41)).setText("");
			((TextView) findViewById(R.id.tv_text42)).setText("");
			((TextView) findViewById(R.id.tv_text43)).setText("");
			((TextView) findViewById(R.id.tv_text44)).setText("");
			((TextView) findViewById(R.id.tv_text45)).setText("");
			((TextView) findViewById(R.id.tv_text46)).setText("");
			((TextView) findViewById(R.id.tv_text47)).setText("");
			((TextView) findViewById(R.id.tv_text48)).setText("");
			((TextView) findViewById(R.id.tv_text49)).setText("");
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
