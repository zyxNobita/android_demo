package com.bai.demo.frame;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import android.widget.TextView;
import android.widget.Toast;

import com.bai.demo.R;
import com.bai.demo.adapter.Menu3Info1LeftListViewAdapter;
import com.bai.demo.adapter.Menu3Info1RightListViewAdapter;
import com.bai.demo.bean.ENTRY_HEAD;
import com.bai.demo.bean.EXAM_MID_REL;
import com.bai.demo.bean.TipsPeople;
import com.bai.demo.frame.Menu1Info1Activity.MyAsynTask;
import com.bai.demo.frame.Menu1Info2Activity.MyAsynTaskThere;
import com.bai.demo.frame.Menu1Info2Activity.MyAsynTaskTwo;
import com.bai.demo.main.FrameDemoActivity;
import com.bai.demo.utils.MyListView;
import com.bai.demo.utils.Webservice;
import com.google.gson.reflect.TypeToken;

/**
 * 个人查验单证列表
 * @author soft1_developer1
 *
 */
public class Menu3Info1Activity extends RightWindowBase{
	//近多少日|进出口
	private Spinner sp_RM3I1_day, sp_RM3I1_inOrOut, sp_RM3I1_stateOne, sp_RM3I1_stateTwo, sp_RM3I1_inAndOut;
	private EditText et_RM3I1_timeStart,et_RM3I1_timeStop;
	private Button btn_menu3Info1_submit,btn_RM3I1_refurbish, btn_rightMenu3Info1_llSecond_back;
	private LinearLayout ll_RM3I1_lv_main_left, ll_RM3I1_lv_main_right, ll_rightMenu3Info1_first, ll_rightMenu3Info1_second;
	private View leftView, rightView;
	private MyListView mlv_RM3I1_leftLV, mlv_RM3I1_rightLV;
	private List<Map<String, String>> listData = new ArrayList<Map<String, String>>();
	private TextView txt_RM3I1_selfTicketText1, txt_RM3I1_selfTicketText2, txt_RM3I1_selfTicketText3, txt_RM3I1_selfTicketText4, txt_RM3I1_selfTicketText5;
	private List<TipsPeople> tipsPeopleList;
	private List<EXAM_MID_REL> examMidRelList=new ArrayList<EXAM_MID_REL>();
	private String lobNumber="",spDayText="",spInOrOutText="";
	
	public Menu3Info1Activity(Context context){
		super(context);
		setupViews();
	}
	
	public Menu3Info1Activity(Context context, AttributeSet attrs) {
		super(context, attrs);
		setupViews();
	}
	
	private void setupViews(){
		initView();
		addView(ll_rightMenu3Info1_first);
		addView(ll_rightMenu3Info1_second);
		doExecute();
	}
	private void initView(){
		ll_rightMenu3Info1_first = (LinearLayout) LinearLayout.inflate(getContext(),
				R.layout.right_menu3info1_self_info_hint, null);
		
		sp_RM3I1_day=(Spinner) ll_rightMenu3Info1_first.findViewById(R.id.spinner_RM3I1_self_info_hint_day);
		sp_RM3I1_inOrOut=(Spinner) ll_rightMenu3Info1_first.findViewById(R.id.spinner_RM3I1_self_info_hint_inOrOut);
		btn_RM3I1_refurbish=(Button) ll_rightMenu3Info1_first.findViewById(R.id.btn_RM3I1_refurbish);
		txt_RM3I1_selfTicketText1 = (TextView) ll_rightMenu3Info1_first.findViewById(R.id.tv_self_ticket_text1);
		txt_RM3I1_selfTicketText2 = (TextView) ll_rightMenu3Info1_first.findViewById(R.id.tv_self_ticket_text2);
		txt_RM3I1_selfTicketText3 = (TextView) ll_rightMenu3Info1_first.findViewById(R.id.tv_self_ticket_text3);
		txt_RM3I1_selfTicketText4 = (TextView) ll_rightMenu3Info1_first.findViewById(R.id.tv_self_ticket_text4);
		txt_RM3I1_selfTicketText5 = (TextView) ll_rightMenu3Info1_first.findViewById(R.id.tv_self_ticket_text5);
		
		ll_rightMenu3Info1_second = (LinearLayout) LinearLayout.inflate(getContext(),
				R.layout.right_menu3info1, null);
		
		ll_RM3I1_lv_main_left = (LinearLayout) ll_rightMenu3Info1_second
				.findViewById(R.id.ll_RM3I1_lv_main_left);
		ll_RM3I1_lv_main_right = (LinearLayout) ll_rightMenu3Info1_second
				.findViewById(R.id.ll_RM3I1_lv_main_right);
		
		leftView = LayoutInflater.from(getContext()).inflate(
				R.layout.right_menu3info1_listview_left_layout, null);
		rightView = LayoutInflater.from(getContext()).inflate(
				R.layout.right_menu3info1_listview_right_layout, null);

		mlv_RM3I1_leftLV = (MyListView) leftView
				.findViewById(R.id.lv_RM3I1_left_layout_listView1);
		mlv_RM3I1_rightLV = (MyListView) rightView
				.findViewById(R.id.lv_RM3I1_right_layout_listView1);
		
		
		
		addListView(leftView, rightView);
		
		sp_RM3I1_stateOne=(Spinner) ll_rightMenu3Info1_second.findViewById(R.id.sp_RM3I1_stateOne);
		sp_RM3I1_stateTwo=(Spinner) ll_rightMenu3Info1_second.findViewById(R.id.sp_RM3I1_stateTwo);
		sp_RM3I1_inAndOut=(Spinner) ll_rightMenu3Info1_second.findViewById(R.id.sp_RM3I1_inAndOut);
		et_RM3I1_timeStart=(EditText) ll_rightMenu3Info1_second.findViewById(R.id.et_RM3I1_timeStart);
		et_RM3I1_timeStop=(EditText) ll_rightMenu3Info1_second.findViewById(R.id.et_RM3I1_timeStop);
		btn_menu3Info1_submit=(Button) ll_rightMenu3Info1_second.findViewById(R.id.btn_menu3Info1_submit);
		btn_rightMenu3Info1_llSecond_back=(Button)ll_rightMenu3Info1_second.findViewById(R.id.btn_rightMenu3Info1_llSecond_back);
	
		showListViewData();//ListView的初始化
	}
	
	private void doExecute(){
		
		txt_RM3I1_selfTicketText1.setOnClickListener(new MyOnClickListener());
		txt_RM3I1_selfTicketText2.setOnClickListener(new MyOnClickListener());
		txt_RM3I1_selfTicketText3.setOnClickListener(new MyOnClickListener());
		txt_RM3I1_selfTicketText4.setOnClickListener(new MyOnClickListener());
		txt_RM3I1_selfTicketText5.setOnClickListener(new MyOnClickListener());
		
		spinnerCheck();//为选下拉选择赋值
		
		btn_menu3Info1_submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//请求服务器加载数据
				reqData();
			}
		});
		
		btn_rightMenu3Info1_llSecond_back.setOnClickListener(new MyOnClickListener());
	
		lobNumber=FrameDemoActivity.myApp.userInfo.getLobNumber();
		btn_RM3I1_refurbish.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				reqSpinnerValue();//刷新加载数据
			}
		});
		reqSpinnerValue();//init数据
	}
	//为选下拉选择赋值
	private void spinnerCheck(){
		sp_RM3I1_day.setPrompt(getContext().getString(R.string.sp_dayTitle));
		ArrayAdapter<String> dayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.sp_dayItemsText));
		dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp_RM3I1_day.setAdapter(dayAdapter);
		sp_RM3I1_day.setSelection(1, true);
		sp_RM3I1_day.setOnItemSelectedListener(new MySpinnerSelectedListener());
		
		sp_RM3I1_inOrOut.setPrompt(getContext().getString(R.string.spinner_RM2I1_inOrOutTitle));
		ArrayAdapter<String> ieAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.sp_InAndOutItemsText));
		ieAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);   
		sp_RM3I1_inOrOut.setAdapter(ieAdapter);
		sp_RM3I1_inOrOut.setSelection(2, true);
		sp_RM3I1_inOrOut.setOnItemSelectedListener(new MySpinnerSelectedListener());
		
		et_RM3I1_timeStart.setInputType(InputType.TYPE_NULL);
		et_RM3I1_timeStop.setInputType(InputType.TYPE_NULL);
		et_RM3I1_timeStart.setOnClickListener(new ETRequestDate());
		et_RM3I1_timeStop.setOnClickListener(new ETRequestDate());
		
		sp_RM3I1_stateOne.setPrompt(getContext().getString(R.string.sp_RM3I1_stateTitle));
		ArrayAdapter<String> stateOneAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.sp_stateArrOneItemsText));
		stateOneAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp_RM3I1_stateOne.setAdapter(stateOneAdapter);
		sp_RM3I1_stateOne.setSelection(2, true);
		sp_RM3I1_stateOne.setOnItemSelectedListener(new MySpinnerSelectedListener());
		
		sp_RM3I1_stateTwo.setPrompt(getContext().getString(R.string.sp_RM3I1_stateTitle));
		ArrayAdapter<String> stateTwoAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.sp_stateArrTwoItemsText));
		stateTwoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp_RM3I1_stateTwo.setAdapter(stateTwoAdapter);
		sp_RM3I1_stateTwo.setSelection(1, true);
		sp_RM3I1_stateTwo.setOnItemSelectedListener(new MySpinnerSelectedListener());
		
		sp_RM3I1_inAndOut.setPrompt(getContext().getString(R.string.spinner_RM2I1_inOrOutTitle));
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.sp_InAndOutItemsText));
		arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);   
		sp_RM3I1_inAndOut.setAdapter(arrayAdapter);
		sp_RM3I1_inAndOut.setSelection(0, true);
		sp_RM3I1_inAndOut.setOnItemSelectedListener(new MySpinnerSelectedListener());
	}
	
	private class MySpinnerSelectedListener implements Spinner.OnItemSelectedListener{

        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3){
            arg0.setVisibility(View.VISIBLE);
            if(arg1.getId()==R.id.spinner_RM3I1_self_info_hint_inOrOut || arg1.getId()==R.id.spinner_RM3I1_self_info_hint_day){
            	 reqSpinnerValue();
            }
        }

        public void onNothingSelected(AdapterView<?> arg0){
        }

    };
	private class MyOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			int btnId = v.getId();
			switch (btnId) {
			case R.id.tv_self_ticket_text1:
				txt_RM3I1_selfTicketText1.setTextColor(getResources().getColor(R.color.red));
				ll_rightMenu3Info1_first.setVisibility(View.GONE);
				ll_rightMenu3Info1_second.setVisibility(View.VISIBLE);
				sp_RM3I1_stateOne.setSelection(0, true);
				sp_RM3I1_stateTwo.setVisibility(View.GONE);
				break;
				
			case R.id.tv_self_ticket_text2:
				txt_RM3I1_selfTicketText2.setTextColor(getResources().getColor(R.color.red));
				ll_rightMenu3Info1_first.setVisibility(View.GONE);
				ll_rightMenu3Info1_second.setVisibility(View.VISIBLE);
				sp_RM3I1_stateOne.setSelection(1, true);
				sp_RM3I1_stateTwo.setVisibility(View.GONE);
				break;
				
			case R.id.tv_self_ticket_text3:
				txt_RM3I1_selfTicketText3.setTextColor(getResources().getColor(R.color.red));
				ll_rightMenu3Info1_first.setVisibility(View.GONE);
				ll_rightMenu3Info1_second.setVisibility(View.VISIBLE);
				sp_RM3I1_stateOne.setSelection(2, true);
				sp_RM3I1_stateTwo.setVisibility(View.VISIBLE);
				sp_RM3I1_stateTwo.setSelection(2, true);
				break;
				
			case R.id.tv_self_ticket_text4:
				txt_RM3I1_selfTicketText4.setTextColor(getResources().getColor(R.color.red));
				ll_rightMenu3Info1_first.setVisibility(View.GONE);
				ll_rightMenu3Info1_second.setVisibility(View.VISIBLE);
				sp_RM3I1_stateOne.setSelection(2, true);
				sp_RM3I1_stateTwo.setVisibility(View.VISIBLE);
				sp_RM3I1_stateTwo.setSelection(1, true);
				break;
				
			case R.id.tv_self_ticket_text5:
				txt_RM3I1_selfTicketText5.setTextColor(getResources().getColor(R.color.red));
				ll_rightMenu3Info1_first.setVisibility(View.GONE);
				ll_rightMenu3Info1_second.setVisibility(View.VISIBLE);
				sp_RM3I1_stateOne.setSelection(2, true);
				sp_RM3I1_stateTwo.setVisibility(View.VISIBLE);
				sp_RM3I1_stateTwo.setSelection(0, true);
				break;
				
			case R.id.btn_rightMenu3Info1_llSecond_back:
				ll_rightMenu3Info1_first.setVisibility(View.VISIBLE);
				ll_rightMenu3Info1_second.setVisibility(View.GONE);
				break;
			}
		}
	};
	
	private String dateResult="";
	
	private class ETRequestDate implements OnClickListener{
		
		@Override
		public void onClick(View v) {
			
			final int vId=v.getId();
			Calendar c = Calendar.getInstance();
			DatePickerDialog datePickerDialog=new DatePickerDialog(getContext(), FOCUSABLES_TOUCH_MODE,new DatePickerDialog.OnDateSetListener() {
				
				@Override
				public void onDateSet(DatePicker view, int year, int monthOfYear,
						int dayOfMonth) {
					dateResult=year+"-"+(monthOfYear+1)+"-"+dayOfMonth;
					if(vId==R.id.et_RM3I1_timeStart){
						et_RM3I1_timeStart.setText(dateResult);
					}else if(vId==R.id.et_RM3I1_timeStop){
						et_RM3I1_timeStop.setText(dateResult);
					}
				}
			}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c
			.get(Calendar.DAY_OF_MONTH));
			datePickerDialog.setTitle("请选择日期");
			datePickerDialog.show();
		}
	};
	
	private void addListView(View leftView, View rightView) {
		mlv_RM3I1_leftLV.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				mlv_RM3I1_rightLV.onTouchEvent(event);
				return false;
			}
		});

		mlv_RM3I1_rightLV.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {

				mlv_RM3I1_leftLV.dispatchTouchEvent(event);
				return false;
			}
		});

		ll_RM3I1_lv_main_left.addView(leftView);
		ll_RM3I1_lv_main_right.addView(rightView);
	}
	
	
	@Override
	public void dosomething() {
		
	}

	@Override
	public void dosomething2() {
		
	}

	/**
	 * 工作量统计--个人查验单证-查询本人处理单证情况
	 * @author zhangyx
	 *
	 */
	class MyAsynTask extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			FrameDemoActivity.toolUtils.startProgressDialog();
		}

		@Override
		protected String doInBackground(String... params) {

			// 获取派单
			String reqResult = "";
			FrameDemoActivity.webservice.setMETHOD_NAME("GetRowCountByExpression");
			FrameDemoActivity.webservice
					.setSOAP_ACTION("http://tempuri.org/IGetExamDataService/GetRowCountByExpression");
			if (FrameDemoActivity.webservice.connect(getContext(),new String[] { "strUserJobNumber","dayNum","i_e_flag" },
					new Object[] { params[0],params[1],params[2] })) {
				reqResult = FrameDemoActivity.webservice.getResult().toString();
				System.out.println("查询本人处理单证情况：" + reqResult);
			}
			return reqResult;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			FrameDemoActivity.toolUtils.endProgressDialog();
			if (result.equals("")) {
				FrameDemoActivity.toolUtils.promptMessage("对不起，不存在此报关单。");
			} else if (result != null && !result.equals("")) {
				System.out.println("查询本人处理单证情况"+result);
				tipsPeopleList = new ArrayList<TipsPeople>();
				tipsPeopleList = FrameDemoActivity.gson.fromJson(result,
						new TypeToken<List<TipsPeople>>() {}.getType());
				showTipsPeopleInfo();
			}
		}

	};
	
	private void reqSpinnerValue(){
		spDayText=sp_RM3I1_day.getSelectedItem().toString();
		spInOrOutText=sp_RM3I1_inOrOut.getSelectedItem().toString();
		System.out.println("条件："+spDayText+"-"+spInOrOutText);
		//测试数据
		lobNumber="223468";
//		spDayText="2";
//		spInOrOutText="全部";
		if(lobNumber!=null && !lobNumber.equals(""))
		new MyAsynTask().execute(lobNumber,spDayText,spInOrOutText);
	}
	
	private void showTipsPeopleInfo(){
		if(tipsPeopleList!=null && !tipsPeopleList.equals("")){
			Integer NotInput=0;
			Integer HaveInput=0;
			Integer WaitAll=0;
			Integer HaveHandle=0;
			Integer NoHandle=0;
			for(int i=0;i<tipsPeopleList.size();i++){
				NotInput+=tipsPeopleList.get(i).getNotInput();
				HaveInput+=tipsPeopleList.get(i).getHaveInput();
				WaitAll+=tipsPeopleList.get(i).getWaitAll();
				HaveHandle+=tipsPeopleList.get(i).getHaveHandle();
				NoHandle+=tipsPeopleList.get(i).getNoHandle();
			}
			txt_RM3I1_selfTicketText1.setText(NotInput+"票");
			txt_RM3I1_selfTicketText2.setText(HaveInput+"票");
			txt_RM3I1_selfTicketText3.setText(WaitAll+"票");
			txt_RM3I1_selfTicketText4.setText(HaveHandle+"票");
			txt_RM3I1_selfTicketText5.setText(NoHandle+"票");
		}
	}
	
	/**
	 * 工作量统计--个人查验单证2-分类列表
	 * @author zhangyx
	 *
	 */
	class MyAsynTaskTwo extends AsyncTask<String, String, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			FrameDemoActivity.toolUtils.startProgressDialog();
		}

		@Override
		protected String doInBackground(String... params) {
			String reqResult = "";
			FrameDemoActivity.webservice.setMETHOD_NAME("GetListForPageList");
			FrameDemoActivity.webservice
					.setSOAP_ACTION("http://tempuri.org/IGetExamDataService/GetListForPageList");
			if (FrameDemoActivity.webservice.connect(getContext(),new String[] { "strUserJobNumber","strcbType1","strcbType2","i_e_flag","beginTime","endTime" },
					new Object[] { params[0],params[1],params[2],params[3],params[4],params[5] })) {
				reqResult = FrameDemoActivity.webservice.getResult().toString();
				System.out.println("个人查验单证2-分类列表：" + reqResult);
				//添加Log日志
				if(reqResult!=null && !reqResult.equals("")){
					FrameDemoActivity.toolUtils.writeDataLog("查询个人查验单证成功。", "94", "");
				}
			}
			return reqResult;
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			FrameDemoActivity.toolUtils.endProgressDialog();
			if (result.equals("")) {
				FrameDemoActivity.toolUtils.promptMessage("对不起，不存在此报关单。");
			} else if (result != null && !result.equals("")) {
				System.out.println("个人查验单证2-分类列表"+result);
				examMidRelList.clear();
				examMidRelList.addAll((List) FrameDemoActivity.gson.fromJson(result,
						new TypeToken<List<EXAM_MID_REL>>() {}.getType()));
				m3i1LeftAdapter.notifyDataSetChanged();
				m3i1RightAdapter.notifyDataSetChanged();
			}
		}

	};
	//请求后的个人查验单证2-分类列表数据
	private void reqData(){
		String spStataOne=sp_RM3I1_stateOne.getSelectedItem().toString();
		String spStataTwo=sp_RM3I1_stateTwo.getSelectedItem().toString();
		String spInAndOut=sp_RM3I1_inAndOut.getSelectedItem().toString();
		String timeStart=et_RM3I1_timeStart.getText().toString();
		String timeStop=et_RM3I1_timeStop.getText().toString();
		//测试数据
		timeStart="2011-09-01";
		timeStop="2011-09-30 23:59:59";
		lobNumber="223263";
		System.out.println("分类列表数据条件："+spStataOne+"---"+spStataTwo+"---"+spInAndOut);
		//查询后台数据
		if(timeStart!=null && !timeStart.equals("") && timeStop!=null && !timeStop.equals("")){
			new MyAsynTaskTwo().execute(lobNumber,spStataOne,spStataTwo,spInAndOut,timeStart,timeStop);
		}
	}
	private Menu3Info1LeftListViewAdapter m3i1LeftAdapter;
	private Menu3Info1RightListViewAdapter m3i1RightAdapter;
	//添加ListView中的数据
	private void showListViewData(){
		m3i1LeftAdapter=new Menu3Info1LeftListViewAdapter(getContext(), examMidRelList);
		mlv_RM3I1_leftLV.setAdapter(m3i1LeftAdapter);
		m3i1RightAdapter=new Menu3Info1RightListViewAdapter(getContext(), examMidRelList);
		mlv_RM3I1_rightLV.setAdapter(m3i1RightAdapter);
	}
}