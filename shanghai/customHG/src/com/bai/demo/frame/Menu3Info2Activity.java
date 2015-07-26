package com.bai.demo.frame;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
import com.bai.demo.adapter.Menu3Info2LeftListViewAdapter;
import com.bai.demo.adapter.Menu3Info2RightListViewAdapter;
import com.bai.demo.bean.EXAM_MID_REL;
import com.bai.demo.bean.TipsPeople;
import com.bai.demo.main.FrameDemoActivity;
import com.bai.demo.utils.MyListView;
import com.bai.demo.utils.Webservice;
import com.google.gson.reflect.TypeToken;

/**
 * 科室查验单证列表
 * @author soft1_developer1
 *
 */
public class Menu3Info2Activity extends RightWindowBase{

	private Spinner sp_RM3I2_fullCert_spinnerOne, sp_RM3I2_fullCert_spinnerTwo, sp_RM3I2_fullCert_spinnerThree, 
	sp_RM3I2_day, sp_RM3I2_inOrOut, 
	sp_RM3I2_spinnerOne, sp_RM3I2_spinnerTwo, sp_RM3I2_spinnerThree,
	sp_RM3I2_spinnerFour, sp_RM3I2_spinnerFive, sp_RM3I2_spinnerSix;
	private EditText et_RM3I2_timeStart,et_RM3I2_timeStop;
	private Button btn_menu3Info2_submit, btn_rightMenu3Info2_llSecond_back,btn_RM3I2_fullCertRefurbish;
	private String[] secondGrade;
	private LinearLayout ll_RM3I2_lv_main_left, ll_RM3I2_lv_main_right, ll_rightMenu3Info2_first, ll_rightMenu3Info2_second;
	private View leftView, rightView;
	private MyListView mlv_RM3I2_leftLV, mlv_RM3I2_rightLV;
	private TextView txt_RM3I2_fullCertTicketText1, txt_RM3I2_fullCertTicketText2, txt_RM3I2_fullCertTicketText3, txt_RM3I2_fullCertTicketText4, txt_RM3I2_fullCertTicketText5;
	private List<TipsPeople> tipsPeopleList;
	private List<EXAM_MID_REL> examMidRelList=new ArrayList<EXAM_MID_REL>();
	private String strResult,strThree;//海关的部门
	public Menu3Info2Activity(Context context){
		super(context);
		setupViews();
	}
	public Menu3Info2Activity(Context context, AttributeSet attrs) {
		super(context, attrs);
		setupViews();
	}
	
	private void setupViews(){
		initView();
		addView(ll_rightMenu3Info2_first);
		addView(ll_rightMenu3Info2_second);
		doExecute();
	}

	private void initView(){
		
		ll_rightMenu3Info2_first = (LinearLayout) LinearLayout.inflate(getContext(),
				R.layout.right_menu3info2_fullcert_info_hint, null);
		
		sp_RM3I2_fullCert_spinnerOne=(Spinner) ll_rightMenu3Info2_first.findViewById(R.id.sp_RM3I2_fullCert_info_hint_spinnerOne);
		sp_RM3I2_fullCert_spinnerTwo=(Spinner) ll_rightMenu3Info2_first.findViewById(R.id.sp_RM3I2_fullCert_info_hint_spinnerTwo);
		sp_RM3I2_fullCert_spinnerThree=(Spinner) ll_rightMenu3Info2_first.findViewById(R.id.sp_RM3I2_fullCert_info_hint_spinnerThree);
		
		sp_RM3I2_day=(Spinner) ll_rightMenu3Info2_first.findViewById(R.id.spinner_RM3I2_fullCert_info_hint_day);
		sp_RM3I2_inOrOut=(Spinner) ll_rightMenu3Info2_first.findViewById(R.id.spinner_RM3I2_fullCert_info_hint_inOrOut);
		btn_RM3I2_fullCertRefurbish=(Button) ll_rightMenu3Info2_first.findViewById(R.id.btn_RM3I2_fullCertRefurbish);
		txt_RM3I2_fullCertTicketText1 = (TextView) ll_rightMenu3Info2_first.findViewById(R.id.tv_fillCert_ticket_text1);
		txt_RM3I2_fullCertTicketText2 = (TextView) ll_rightMenu3Info2_first.findViewById(R.id.tv_fillCert_ticket_text2);
		txt_RM3I2_fullCertTicketText3 = (TextView) ll_rightMenu3Info2_first.findViewById(R.id.tv_fillCert_ticket_text3);
		txt_RM3I2_fullCertTicketText4 = (TextView) ll_rightMenu3Info2_first.findViewById(R.id.tv_fillCert_ticket_text4);
		txt_RM3I2_fullCertTicketText5 = (TextView) ll_rightMenu3Info2_first.findViewById(R.id.tv_fillCert_ticket_text5);
		
		ll_rightMenu3Info2_second = (LinearLayout) LinearLayout.inflate(getContext(),
				R.layout.right_menu3info2, null);
		
		ll_RM3I2_lv_main_left = (LinearLayout) ll_rightMenu3Info2_second
				.findViewById(R.id.ll_RM3I2_lv_main_left);
		ll_RM3I2_lv_main_right = (LinearLayout) ll_rightMenu3Info2_second
				.findViewById(R.id.ll_RM3I2_lv_main_right);
		
		leftView = LayoutInflater.from(getContext()).inflate(
				R.layout.right_menu3info2_listview_left_layout, null);
		rightView = LayoutInflater.from(getContext()).inflate(
				R.layout.right_menu3info2_listview_right_layout, null);


		mlv_RM3I2_leftLV = (MyListView) leftView
				.findViewById(R.id.lv_RM3I2_left_layout_listView1);
		mlv_RM3I2_rightLV = (MyListView) rightView
				.findViewById(R.id.lv_RM3I2_right_layout_listView1);

		addListView(leftView, rightView);
		
		et_RM3I2_timeStart=(EditText) ll_rightMenu3Info2_second.findViewById(R.id.et_RM3I2_timeStart);
		et_RM3I2_timeStop=(EditText) ll_rightMenu3Info2_second.findViewById(R.id.et_RM3I2_timeStop);
		sp_RM3I2_spinnerOne=(Spinner) ll_rightMenu3Info2_second.findViewById(R.id.sp_RM3I2_spinnerOne);
		sp_RM3I2_spinnerTwo=(Spinner) ll_rightMenu3Info2_second.findViewById(R.id.sp_RM3I2_spinnerTwo);
		sp_RM3I2_spinnerThree=(Spinner) ll_rightMenu3Info2_second.findViewById(R.id.sp_RM3I2_spinnerThree);
		sp_RM3I2_spinnerFour=(Spinner) ll_rightMenu3Info2_second.findViewById(R.id.sp_RM3I2_spinnerFour);
		sp_RM3I2_spinnerFive=(Spinner) ll_rightMenu3Info2_second.findViewById(R.id.sp_RM3I2_spinnerFive);
		sp_RM3I2_spinnerSix=(Spinner) ll_rightMenu3Info2_second.findViewById(R.id.sp_RM3I2_spinnerSix);
		btn_menu3Info2_submit=(Button) ll_rightMenu3Info2_second.findViewById(R.id.btn_menu3Info2_submit);
		btn_rightMenu3Info2_llSecond_back=(Button)ll_rightMenu3Info2_second.findViewById(R.id.btn_rightMenu3Info2_llSecond_back);
		
		showListViewData();//初始化Listview
}

	private void doExecute(){
		
		//表格文本统计的点击事件
		txt_RM3I2_fullCertTicketText1.setOnClickListener(new MyOnClickListener());
		txt_RM3I2_fullCertTicketText2.setOnClickListener(new MyOnClickListener());
		txt_RM3I2_fullCertTicketText3.setOnClickListener(new MyOnClickListener());
		txt_RM3I2_fullCertTicketText4.setOnClickListener(new MyOnClickListener());
		txt_RM3I2_fullCertTicketText5.setOnClickListener(new MyOnClickListener());
		firstPage();//页面的表格统计展示
		
		secondPage();//ListView显示的查询条件
		
	
		//加载后台数据
		btn_menu3Info2_submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				reqData();
			}
		});
		
		btn_rightMenu3Info2_llSecond_back.setOnClickListener(new MyOnClickListener());
		btn_RM3I2_fullCertRefurbish.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				reqSpinnerValue();
			}
		});
		
		//加载后台数据-----初始化页面加载数据
		reqSpinnerValue();
	}
	
	//页面的表格统计
	private void firstPage(){
		sp_RM3I2_fullCert_spinnerOne.setPrompt(getContext().getString(R.string.sp_RM3I2_spinnerOneTitle));
		String[] str=getResources().getStringArray(R.array.sp_customFirstGradeItemsText);
		System.out.println("一级区域："+str[0]);
		ArrayAdapter<String> firstAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, str);
		firstAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp_RM3I2_fullCert_spinnerOne.setAdapter(firstAdapter);
		sp_RM3I2_fullCert_spinnerOne.setSelection(0, true);
		sp_RM3I2_fullCert_spinnerOne.setOnItemSelectedListener(new MySpinnerItemSelectedListener());
		
		sp_RM3I2_fullCert_spinnerTwo.setPrompt(getContext().getString(R.string.sp_RM3I2_spinnerTwoTitle));
		ArrayAdapter<String> secondAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, FrameDemoActivity.myApp.CUSTOMS);
		secondAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp_RM3I2_fullCert_spinnerTwo.setAdapter(secondAdapter);
		sp_RM3I2_fullCert_spinnerTwo.setSelection(0, true);
		sp_RM3I2_fullCert_spinnerTwo.setOnItemSelectedListener(new MySpinnerItemSelectedListener());
		
		sp_RM3I2_fullCert_spinnerThree.setPrompt(getContext().getString(R.string.sp_RM3I2_spinnerThreeTitle));
		ArrayAdapter<String> thirdAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, FrameDemoActivity.myApp.DEP_NAME);
		thirdAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp_RM3I2_fullCert_spinnerThree.setAdapter(thirdAdapter);
		sp_RM3I2_fullCert_spinnerThree.setSelection(0, true);
		sp_RM3I2_fullCert_spinnerThree.setOnItemSelectedListener(new MySpinnerItemSelectedListener());
		
		
		sp_RM3I2_day.setPrompt(getContext().getString(R.string.sp_dayTitle));
		ArrayAdapter<String> dayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.sp_dayItemsText));
		dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp_RM3I2_day.setAdapter(dayAdapter);
		sp_RM3I2_day.setSelection(0, true);
		sp_RM3I2_day.setOnItemSelectedListener(new MySpinnerItemSelectedListener());
		
		sp_RM3I2_inOrOut.setPrompt(getContext().getString(R.string.spinner_RM2I1_inOrOutTitle));
		ArrayAdapter<String> ieAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.sp_InAndOutItemsText));
		ieAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);   
		sp_RM3I2_inOrOut.setAdapter(ieAdapter);
		sp_RM3I2_inOrOut.setSelection(2, true);
		sp_RM3I2_inOrOut.setOnItemSelectedListener(new MySpinnerItemSelectedListener());
	}
	
	//页面ListView显示的查询条件
	private void secondPage(){
		et_RM3I2_timeStart.setInputType(InputType.TYPE_NULL);
		et_RM3I2_timeStop.setInputType(InputType.TYPE_NULL);
		et_RM3I2_timeStart.setOnClickListener(new ETRequestDate());
		et_RM3I2_timeStop.setOnClickListener(new ETRequestDate());
		
		sp_RM3I2_spinnerOne.setPrompt(getContext().getString(R.string.sp_RM3I2_spinnerOneTitle));
		ArrayAdapter<String> firstGradeAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.sp_customFirstGradeItemsText));
		firstGradeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); 
		sp_RM3I2_spinnerOne.setAdapter(firstGradeAdapter);
		sp_RM3I2_spinnerOne.setSelection(0, true);
		sp_RM3I2_spinnerOne.setOnItemSelectedListener(new MySpinnerItemSelectedListener());
		
		
		sp_RM3I2_spinnerTwo.setPrompt(getContext().getString(R.string.sp_RM3I2_spinnerTwoTitle));
		secondGradeAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.sp_customSecondGradeItemsText));
		secondGradeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); 
		sp_RM3I2_spinnerTwo.setAdapter(secondGradeAdapter);
		sp_RM3I2_spinnerTwo.setSelection(0, true);
		sp_RM3I2_spinnerTwo.setOnItemSelectedListener(new MySpinnerItemSelectedListener());
		
		sp_RM3I2_spinnerThree.setPrompt(getContext().getString(R.string.sp_RM3I2_spinnerThreeTitle));
		threeGradeAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.sp_customThirdGradeItemsText));
		threeGradeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); 
		sp_RM3I2_spinnerThree.setAdapter(threeGradeAdapter);
		sp_RM3I2_spinnerThree.setSelection(0, true);
		sp_RM3I2_spinnerThree.setOnItemSelectedListener(new MySpinnerItemSelectedListener());
		
		sp_RM3I2_spinnerFour.setPrompt(getContext().getString(R.string.sp_RM3I1_stateTitle));
		ArrayAdapter<String> stateOneAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.sp_stateArrOneItemsText));
		stateOneAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);   
		sp_RM3I2_spinnerFour.setAdapter(stateOneAdapter);
		sp_RM3I2_spinnerFour.setOnItemSelectedListener(new MySpinnerItemSelectedListener());
		
		sp_RM3I2_spinnerFive.setPrompt(getContext().getString(R.string.sp_RM3I1_stateTitle));
		ArrayAdapter<String> stateTwoAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.sp_stateArrTwoItemsText));
		stateTwoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);   
		sp_RM3I2_spinnerFive.setAdapter(stateTwoAdapter);
		sp_RM3I2_spinnerFive.setOnItemSelectedListener(new MySpinnerItemSelectedListener());
		
		sp_RM3I2_spinnerSix.setPrompt(getContext().getString(R.string.spinner_RM2I1_inOrOutTitle));
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.sp_InAndOutItemsText));
		arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);   
		sp_RM3I2_spinnerSix.setAdapter(arrayAdapter);
		sp_RM3I2_spinnerSix.setSelection(2, true);
		sp_RM3I2_spinnerSix.setOnItemSelectedListener(new MySpinnerItemSelectedListener());
	}
	
	private class MyOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			int btnId = v.getId();
			switch (btnId) {
			case R.id.tv_fillCert_ticket_text1:
				txt_RM3I2_fullCertTicketText1.setTextColor(getResources().getColor(R.color.red));
				ll_rightMenu3Info2_first.setVisibility(View.GONE);
				ll_rightMenu3Info2_second.setVisibility(View.VISIBLE);
				sp_RM3I2_spinnerFour.setSelection(0, true);
				sp_RM3I2_spinnerFive.setVisibility(View.GONE);
				break;
				
			case R.id.tv_fillCert_ticket_text2:
				txt_RM3I2_fullCertTicketText2.setTextColor(getResources().getColor(R.color.red));
				ll_rightMenu3Info2_first.setVisibility(View.GONE);
				ll_rightMenu3Info2_second.setVisibility(View.VISIBLE);
				sp_RM3I2_spinnerFour.setSelection(1, true);
				sp_RM3I2_spinnerFive.setVisibility(View.GONE);
				break;
				
			case R.id.tv_fillCert_ticket_text3:
				txt_RM3I2_fullCertTicketText3.setTextColor(getResources().getColor(R.color.red));
				ll_rightMenu3Info2_first.setVisibility(View.GONE);
				ll_rightMenu3Info2_second.setVisibility(View.VISIBLE);
				sp_RM3I2_spinnerFour.setSelection(2, true);
				sp_RM3I2_spinnerFive.setVisibility(View.VISIBLE);
				sp_RM3I2_spinnerFive.setSelection(2, true);
				break;
				
			case R.id.tv_fillCert_ticket_text4:
				txt_RM3I2_fullCertTicketText4.setTextColor(getResources().getColor(R.color.red));
				ll_rightMenu3Info2_first.setVisibility(View.GONE);
				ll_rightMenu3Info2_second.setVisibility(View.VISIBLE);
				sp_RM3I2_spinnerFour.setSelection(2, true);
				sp_RM3I2_spinnerFive.setVisibility(View.VISIBLE);
				sp_RM3I2_spinnerFive.setSelection(1, true);
				break;
				
			case R.id.tv_fillCert_ticket_text5:
				txt_RM3I2_fullCertTicketText5.setTextColor(getResources().getColor(R.color.red));
				ll_rightMenu3Info2_first.setVisibility(View.GONE);
				ll_rightMenu3Info2_second.setVisibility(View.VISIBLE);
				sp_RM3I2_spinnerFour.setSelection(2, true);
				sp_RM3I2_spinnerFive.setVisibility(View.VISIBLE);
				sp_RM3I2_spinnerFive.setSelection(0, true);
				break;
				
			case R.id.btn_rightMenu3Info2_llSecond_back:
				ll_rightMenu3Info2_first.setVisibility(View.VISIBLE);
				ll_rightMenu3Info2_second.setVisibility(View.GONE);
				break;
			}
		}
	};
	
	private ArrayAdapter<String> threeGradeAdapter,secondGradeAdapter;
	private class MySpinnerItemSelectedListener implements Spinner.OnItemSelectedListener{

        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3){
        	arg0.setVisibility(View.VISIBLE);
        }
        public void onNothingSelected(AdapterView<?> arg0){
        }
    };

	private String dateResult="";
	//获取时间控件
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
					if(vId==R.id.et_RM3I2_timeStart){
						et_RM3I2_timeStart.setText(dateResult);
					}else if(vId==R.id.et_RM3I2_timeStop){
						et_RM3I2_timeStop.setText(dateResult);
					}
				}
			}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c
			.get(Calendar.DAY_OF_MONTH));
			datePickerDialog.setTitle("请选择日期");
			datePickerDialog.show();
		}
	};
	
	private void addListView(View leftView, View rightView) {
		mlv_RM3I2_leftLV.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				mlv_RM3I2_rightLV.onTouchEvent(event);
				return false;
			}
		});

		mlv_RM3I2_rightLV.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {

				mlv_RM3I2_leftLV.dispatchTouchEvent(event);
				return false;
			}
		});

		ll_RM3I2_lv_main_left.addView(leftView);
		ll_RM3I2_lv_main_right.addView(rightView);
	}
	
	@Override
	public void dosomething() {
		
	}

	@Override
	public void dosomething2() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 工作量统计---科室查验单证-查询科室处理单证情况
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
			FrameDemoActivity.webservice.setMETHOD_NAME("GetDeptRowCountByExpression");
			FrameDemoActivity.webservice
					.setSOAP_ACTION("http://tempuri.org/IGetExamDataService/GetDeptRowCountByExpression");
			if (FrameDemoActivity.webservice.connect(getContext(),new String[] { "customName","depName","dayNum","i_e_flag" },
					new Object[] { params[0],params[1],params[2],params[3] })) {
				reqResult = FrameDemoActivity.webservice.getResult().toString();
				System.out.println("科室查验单证-查询科室处理单证情况：" + reqResult);
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
	
	//初始化数据的加载，统计
	private void reqSpinnerValue(){
		if(FrameDemoActivity.myApp.CUSTOMS.size()<=0){
			Toast.makeText(getContext(), "海关部门的查询关区不能为空,请选择。", Toast.LENGTH_LONG).show();
		}else if(FrameDemoActivity.myApp.DEP_NAME.size()<=0){
			Toast.makeText(getContext(), "海关部门的查询科室不能为空,请选择。", Toast.LENGTH_LONG).show();
		}else{
			///先判断
			String strOne=sp_RM3I2_fullCert_spinnerOne.getSelectedItem().toString();
			String strTwo=sp_RM3I2_fullCert_spinnerTwo.getSelectedItem().toString();
			strThree=sp_RM3I2_fullCert_spinnerThree.getSelectedItem().toString();
			String strDay=sp_RM3I2_day.getSelectedItem().toString();
			String inOrOut=sp_RM3I2_inOrOut.getSelectedItem().toString();
			
			//测试数据
			strDay="3";
			strResult=strOne+"."+strTwo;
			System.out.println("条件："+strResult+"-"+strThree+"-"+strDay+"-"+inOrOut);
			
			new MyAsynTask().execute(strResult,strThree,strDay,inOrOut);
		}
	}
	//显示统计的结果
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
			txt_RM3I2_fullCertTicketText1.setText(NotInput+"票");
			txt_RM3I2_fullCertTicketText2.setText(HaveInput+"票");
			txt_RM3I2_fullCertTicketText3.setText(WaitAll+"票");
			txt_RM3I2_fullCertTicketText4.setText(HaveHandle+"票");
			txt_RM3I2_fullCertTicketText5.setText(NoHandle+"票");
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
			FrameDemoActivity.webservice.setMETHOD_NAME("GetDeptListForPageList");
			FrameDemoActivity.webservice
					.setSOAP_ACTION("http://tempuri.org/IGetExamDataService/GetDeptListForPageList");
			if (FrameDemoActivity.webservice.connect(getContext(),new String[] { "customName","depName","strcbType1","strcbType2","i_e_flag","beginTime","endTime" },
					new Object[] { params[0],params[1],params[2],params[3],params[4],params[5],params[6] })) {
				reqResult = FrameDemoActivity.webservice.getResult().toString();
				System.out.println("个人查验单证2-分类列表：" + reqResult);
				//添加Log日志
				if(reqResult!=null && !reqResult.equals("")){
					FrameDemoActivity.toolUtils.writeDataLog("查询科室查验单证成功。", "95", "");
				}
			}
			return reqResult;
		}

		@SuppressWarnings({ "rawtypes", "unchecked" })
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			FrameDemoActivity.toolUtils.endProgressDialog();
			if (result.equals("")) {
				FrameDemoActivity.toolUtils.promptMessage("对不起，不存在此报关单。");
			} else if (result != null && !result.equals("")) {
				System.out.println("个人查验单证2-分类列表"+result);
				examMidRelList.clear();
				examMidRelList.addAll((List)FrameDemoActivity.gson.fromJson(result,
						new TypeToken<List<EXAM_MID_REL>>() {}.getType()));
				m3i2LeftAdapter.notifyDataSetChanged();
				m3i2RightAdapter.notifyDataSetChanged();
			}
		}

	};
	//科室查验单证2-分类列表
	private void reqData(){
		String timeStart=et_RM3I2_timeStart.getText().toString();
		String timeStop=et_RM3I2_timeStop.getText().toString();
		String str1=sp_RM3I2_spinnerOne.getSelectedItem().toString();
		String str2=sp_RM3I2_spinnerTwo.getSelectedItem().toString();
		String str3=sp_RM3I2_spinnerThree.getSelectedItem().toString();
		String str4=sp_RM3I2_spinnerFour.getSelectedItem().toString();
		String str5=sp_RM3I2_spinnerFive.getSelectedItem().toString();
		String str6=sp_RM3I2_spinnerSix.getSelectedItem().toString();
		//测试数据：
		String result=str1+"."+str2;
		str3="办公室";
		str4="已移交单证";
		str5="全部移交单证";
		timeStart="2011-07-01 00:00:00";
		timeStop="2013-09-27 23:59:59";
		//str6="全部";
		if(timeStart!=null && !timeStart.equals("") && timeStop!=null && !timeStop.equals("")){
			//"上海海关.浦江海关","办公室","已移交单证","全部移交单证","全部","2011-07-01 00:00:00","2013-09-27 23:59:59"
			new MyAsynTaskTwo().execute(result,str3,str4,str5,str6,timeStart,timeStop);
		}
	}
	
	private Menu3Info2LeftListViewAdapter m3i2LeftAdapter;
	private Menu3Info2RightListViewAdapter m3i2RightAdapter;
	//添加ListView中的数据
	private void showListViewData(){
		m3i2LeftAdapter=new Menu3Info2LeftListViewAdapter(getContext(), examMidRelList);
		mlv_RM3I2_leftLV.setAdapter(m3i2LeftAdapter);
		m3i2RightAdapter=new Menu3Info2RightListViewAdapter(getContext(), examMidRelList);
		mlv_RM3I2_rightLV.setAdapter(m3i2RightAdapter);
	}
	
}
