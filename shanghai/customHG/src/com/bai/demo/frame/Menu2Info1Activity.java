package com.bai.demo.frame;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.bai.demo.R;
import com.bai.demo.adapter.Menu2Info1LeftListViewAdapter;
import com.bai.demo.adapter.Menu2Info1RightListViewAdapter;
import com.bai.demo.bean.TradeInfo;
import com.bai.demo.main.FrameDemoActivity;
import com.bai.demo.utils.MyListView;
import com.bai.demo.utils.Webservice;
import com.google.gson.reflect.TypeToken;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

/**
 * 企业信息查询
 */
public class Menu2Info1Activity extends RightWindowBase{

	private EditText et_menu2Info1_timeStart, et_menu2Info1_timeStop, et_menu2Info1_companyNumber, et_menu2Info1_companyName;
	private Button btn_RM2I1_search;//查询
	private Spinner spinner_RM2I1_inOrOut;//进出口
	private LinearLayout layout, ll_RM2I1_lv_main_left, ll_RM2I1_lv_main_right;
	//横向滑动的ListView
	private MyListView mlv_RM2I1_leftLV, mlv_RM2I1_rightLV;
	private View leftView, rightView;
	private List<TradeInfo> tradeInfoList = new ArrayList<TradeInfo>();
	//布控开始时间|布控结束时间|进出口|企业代码|企业名称
	private String timeStart, timeStop, inOrOut, companyNumber, companyType;
	
	public Menu2Info1Activity(Context context){
		super(context);
		setupViews();
	}
	
	public Menu2Info1Activity(Context context, AttributeSet attrs) {
		super(context, attrs);
		setupViews();
	}
	
	private void setupViews(){
		initView();
		addView(layout);
		doExecute();
	}

	private void initView(){
		layout = (LinearLayout) LinearLayout.inflate(getContext(),
				R.layout.right_menu2info1, null);
		
		ll_RM2I1_lv_main_left = (LinearLayout) layout
				.findViewById(R.id.ll_RM2I1_lv_main_left);
		ll_RM2I1_lv_main_right = (LinearLayout) layout
				.findViewById(R.id.ll_RM2I1_lv_main_right);
		leftView = LayoutInflater.from(getContext()).inflate(
				R.layout.right_menu2info1_listview_left_layout, null);
		rightView = LayoutInflater.from(getContext()).inflate(
				R.layout.right_menu2info1_listview_right_layout, null);
		mlv_RM2I1_leftLV = (MyListView) leftView.findViewById(R.id.lv_RM2I1_left_layout_listView1);
		mlv_RM2I1_rightLV = (MyListView) rightView.findViewById(R.id.lv_RM2I1_right_layout_listView1);
		showTradeInfoResult();//ListView的初始化
		addListView(leftView, rightView);
		
//		et_menu2Info1_time=(EditText) layout.findViewById(R.id.et_menu2Info1_time);
		et_menu2Info1_timeStart=(EditText) layout.findViewById(R.id.et_menu2Info1_timeStart);
		et_menu2Info1_timeStop=(EditText) layout.findViewById(R.id.et_menu2Info1_timeStop);
		spinner_RM2I1_inOrOut=(Spinner) layout.findViewById(R.id.spinner_RM2I1_inOrOut);
//		spinner_RM2I1_companyType=(Spinner) layout.findViewById(R.id.spinner_RM2I1_companyType);
		et_menu2Info1_companyNumber=(EditText) layout.findViewById(R.id.et_menu2Info1_companyNumber);
		et_menu2Info1_companyName=(EditText) layout.findViewById(R.id.et_menu2Info1_companyName);
		btn_RM2I1_search = (Button) layout.findViewById(R.id.btn_menu2Info1_submit);
		//showTradeInfoResult();//ListView的初始化
	}
	
	private void doExecute(){
		et_menu2Info1_timeStart.setOnClickListener(new ETRequestDate());
		et_menu2Info1_timeStart.setInputType(InputType.TYPE_NULL);
		et_menu2Info1_timeStop.setOnClickListener(new ETRequestDate());
		et_menu2Info1_timeStop.setInputType(InputType.TYPE_NULL);
		
		spinner_RM2I1_inOrOut.setPrompt(getContext().getString(R.string.spinner_RM2I1_inOrOutTitle));
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.sp_InAndOutItemsText));
		arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);   
		spinner_RM2I1_inOrOut.setAdapter(arrayAdapter);
		spinner_RM2I1_inOrOut.setSelection(2, true);
		spinner_RM2I1_inOrOut.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
	           public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3){
	               arg0.setVisibility(View.VISIBLE);
	           }
	           public void onNothingSelected(AdapterView<?> arg0){
	           }
	       });

		btn_RM2I1_search.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 测试数据-----上传条件判断
				et_menu2Info1_timeStart.setText("2011-01-28");
				et_menu2Info1_timeStop.setText("2011-04-30");
				et_menu2Info1_companyNumber.setText("2200027");
				timeStart = et_menu2Info1_timeStart.getText().toString();
				timeStop = et_menu2Info1_timeStop.getText().toString();
				inOrOut = spinner_RM2I1_inOrOut.getSelectedItem().toString();
				companyNumber = et_menu2Info1_companyNumber.getText().toString();
				companyType="1";
				
				if(timeStart != "" && !timeStart.equals("") && timeStop != "" && !timeStop.equals("")){
					new MyAsynTask().execute(timeStart, timeStop, inOrOut, companyNumber, companyType);
				} else {
					FrameDemoActivity.toolUtils.promptMessage("布控时间不能为空！！！");
				}
				
			}
		});
	} 
	
	private class ETRequestDate implements OnClickListener{
		@Override
		public void onClick(View v) {
			
			final int vId=v.getId();
			Calendar c = Calendar.getInstance();
			DatePickerDialog datePickerDialog=new DatePickerDialog(getContext(), FOCUSABLES_TOUCH_MODE,new DatePickerDialog.OnDateSetListener() {
				
				@Override
				public void onDateSet(DatePicker view, int year, int monthOfYear,
						int dayOfMonth) {
					if(vId==R.id.et_menu2Info1_timeStart){
						et_menu2Info1_timeStart.setText(year+"年"+(monthOfYear+1)+"月"+dayOfMonth+"日");
					}else if(vId==R.id.et_menu2Info1_timeStop){
						et_menu2Info1_timeStop.setText(year+"年"+(monthOfYear+1)+"月"+dayOfMonth+"日");
					}
				}
			}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c
			.get(Calendar.DAY_OF_MONTH));
			datePickerDialog.setTitle("请选择日期");
			datePickerDialog.show();
		}
	};
	
	private void addListView(View leftView, View rightView) {
		mlv_RM2I1_leftLV.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				mlv_RM2I1_rightLV.onTouchEvent(event);
				return false;
			}
		});

		mlv_RM2I1_rightLV.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {

				mlv_RM2I1_leftLV.dispatchTouchEvent(event);
				return false;
			}
		});

		ll_RM2I1_lv_main_left.addView(leftView);
		ll_RM2I1_lv_main_right.addView(rightView);
	}
	
	@Override
	public void dosomething() {
		
	}

	@Override
	public void dosomething2() {
		
	}
	
	/**
	 * 辅助决策支持--企业信息查询
	 * @author ouyyt
	 *
	 */
	class MyAsynTask extends AsyncTask<String, String, String> {
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			FrameDemoActivity.toolUtils.startProgressDialog();
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			// 获取企业信息
			String reqResult = "";
			FrameDemoActivity.webservice.setMETHOD_NAME("GetTradeInfo");
			FrameDemoActivity.webservice.setSOAP_ACTION("http://tempuri.org/IGetExamDataService/GetTradeInfo");
			if (FrameDemoActivity.webservice.connect(getContext(),new String[] { "begin_time", "end_time", "code", "ieflag", "type" },
					new Object[] { params[0], params[1], params[2], params[3], params[4] })) {
				reqResult = FrameDemoActivity.webservice.getResult().toString();
				System.out.println("企业信息列表：" + reqResult);
				//写服务器的Log日志
				if(reqResult!=null && !reqResult.equals("")){
					FrameDemoActivity.toolUtils.writeDataLog("查询企业信息成功。", "92", "");
				}
			}
			return reqResult;
		}
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			FrameDemoActivity.toolUtils.endProgressDialog();
			tradeInfoList.clear();
			if (result.equals("")) {
				FrameDemoActivity.toolUtils.promptMessage("对不起，不存在该企业信息！！！");
			} else if (result != null && !result.equals("")) {
				System.out.println("企业信息列表:" + result);
				tradeInfoList.addAll((List)FrameDemoActivity.gson.fromJson(result, new TypeToken<List<TradeInfo>>() {}.getType()));
			}
			m2i1LeftAdapter.notifyDataSetChanged();
			m2i1RightAdapter.notifyDataSetChanged();
		}
		
	};
	
	private Menu2Info1LeftListViewAdapter m2i1LeftAdapter;
	private Menu2Info1RightListViewAdapter m2i1RightAdapter;
	// 添加企业信息ListView列表
	private void showTradeInfoResult(){
		m2i1LeftAdapter=new Menu2Info1LeftListViewAdapter(getContext(), tradeInfoList);
		mlv_RM2I1_leftLV.setAdapter(m2i1LeftAdapter);
		m2i1RightAdapter=new Menu2Info1RightListViewAdapter(getContext(), tradeInfoList);
		mlv_RM2I1_rightLV.setAdapter(m2i1RightAdapter);
	}

}
