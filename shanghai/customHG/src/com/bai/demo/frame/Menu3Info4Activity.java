package com.bai.demo.frame;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
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
import android.widget.Toast;

import com.bai.demo.R;
import com.bai.demo.adapter.Menu3Info3LeftListViewAdapter;
import com.bai.demo.adapter.Menu3Info3RightListViewAdapter;
import com.bai.demo.adapter.Menu3Info4LeftListViewAdapter;
import com.bai.demo.adapter.Menu3Info4RightFirstListViewAdapter;
import com.bai.demo.bean.EXAM_DEP_MID_NEW_REL;
import com.bai.demo.bean.EXAM_PER_MID_REL;
import com.bai.demo.frame.Menu3Info3Activity.MyAsynTask;
import com.bai.demo.main.FrameDemoActivity;
import com.bai.demo.utils.MyListView;
import com.bai.demo.utils.Webservice;
import com.google.gson.reflect.TypeToken;
/**
 * 查验部门工作绩效
 * @author soft1_developer1
 *
 */
public class Menu3Info4Activity extends RightWindowBase{
	
	private Spinner sp_RM3I4_spinnerOne,sp_RM3I4_spinnerTwo,sp_RM3I4_spinnerThree,
					sp_RM3I4_spinnerFour,sp_RM3I4_spinnerFive;
	private EditText et_tv_RM3I4_time;
	private String[] checkType={"人工查验","机验查验","合计"};
	private LinearLayout layout, ll_RM3I4_one, ll_RM3I4_two, ll_RM3I4_three,ll_RM3I4_lv_main_leftFirst, ll_RM3I4_lv_main_rightFirst,
	ll_RM3I4_lv_main_leftSecond, ll_RM3I4_lv_main_rightSecond,
	ll_RM3I4_lv_main_leftThird, ll_RM3I4_lv_main_rightThird;
	private MyListView mlv_RM3I4_leftLVFirst, mlv_RM3I4_rightLVFirst,
	mlv_RM3I4_leftLVSecond, mlv_RM3I4_rightLVSecond,
	mlv_RM3I4_leftLVThird, mlv_RM3I4_rightLVThird;
	@SuppressLint("ParserError")
	private View leftViewFirst, rightViewFirst, leftViewSecond, rightViewSecond, leftViewThird, rightViewThird;
	private List<EXAM_DEP_MID_NEW_REL> examDepMidNewRelList=new ArrayList<EXAM_DEP_MID_NEW_REL>();
	private Button btn_search;
	public Menu3Info4Activity(Context context){
		super(context);
		setupViews();
	}
	public Menu3Info4Activity(Context context, AttributeSet attrs) {
		super(context, attrs);
		setupViews();
	}
	
	private void setupViews(){
		layout = (LinearLayout) LinearLayout.inflate(getContext(),
				R.layout.right_menu3info4, null);
		
		initView();

		ll_RM3I4_one=(LinearLayout) layout.findViewById(R.id.ll_RM3I4_lv_one);
		ll_RM3I4_two=(LinearLayout) layout.findViewById(R.id.ll_RM3I4_lv_two);
		ll_RM3I4_three=(LinearLayout) layout.findViewById(R.id.ll_RM3I4_lv_three);

		doRM3I4LoadData(ll_RM3I4_one,ll_RM3I4_two,ll_RM3I4_three);
		
		addView(layout);
		doExecute();
	}

	private void initView(){
		btn_search=(Button) layout.findViewById(R.id.btn_menu3Info4_submit);
		et_tv_RM3I4_time=(EditText) layout.findViewById(R.id.et_tv_RM3I4_time);
		sp_RM3I4_spinnerOne=(Spinner) layout.findViewById(R.id.sp_RM3I4_spinnerOne);
		sp_RM3I4_spinnerTwo=(Spinner) layout.findViewById(R.id.sp_RM3I4_spinnerTwo);
		sp_RM3I4_spinnerThree=(Spinner) layout.findViewById(R.id.sp_RM3I4_spinnerThree);
		sp_RM3I4_spinnerFour=(Spinner) layout.findViewById(R.id.sp_RM3I4_spinnerFour);
		sp_RM3I4_spinnerFive=(Spinner) layout.findViewById(R.id.sp_RM3I4_spinnerFive);
	
		ll_RM3I4_lv_main_leftFirst = (LinearLayout) layout
				.findViewById(R.id.ll_RM3I4_lv_main_leftFirst);
		ll_RM3I4_lv_main_rightFirst = (LinearLayout) layout
				.findViewById(R.id.ll_RM3I4_lv_main_rightFirst);
		leftViewFirst = LayoutInflater.from(getContext()).inflate(
				R.layout.right_menu3info4_listview_first_left_layout, null);
		rightViewFirst = LayoutInflater.from(getContext()).inflate(
				R.layout.right_menu3info4_listview_first_right_layout, null);
		mlv_RM3I4_leftLVFirst = (MyListView) leftViewFirst
				.findViewById(R.id.lv_RM3I4_left_layout_listViewFirst);
		mlv_RM3I4_rightLVFirst = (MyListView) rightViewFirst
				.findViewById(R.id.lv_RM3I4_right_layout_listViewFirst);
		
		ll_RM3I4_lv_main_leftSecond = (LinearLayout) layout
		.findViewById(R.id.ll_RM3I4_lv_main_leftSecond);
		ll_RM3I4_lv_main_rightSecond = (LinearLayout) layout
		.findViewById(R.id.ll_RM3I4_lv_main_rightSecond);
		leftViewSecond = LayoutInflater.from(getContext()).inflate(
				R.layout.right_menu3info4_listview_second_left_layout, null);
		rightViewSecond = LayoutInflater.from(getContext()).inflate(
				R.layout.right_menu3info4_listview_second_right_layout, null);		
		mlv_RM3I4_leftLVSecond = (MyListView) leftViewSecond
				.findViewById(R.id.lv_RM3I4_left_layout_listViewSecond);
		mlv_RM3I4_rightLVSecond = (MyListView) rightViewSecond
				.findViewById(R.id.lv_RM3I4_right_layout_listViewSecond);
		
		ll_RM3I4_lv_main_leftThird = (LinearLayout) layout
		.findViewById(R.id.ll_RM3I4_lv_main_leftThird);
		ll_RM3I4_lv_main_rightThird = (LinearLayout) layout
		.findViewById(R.id.ll_RM3I4_lv_main_rightThird);
		leftViewThird = LayoutInflater.from(getContext()).inflate(
				R.layout.right_menu3info4_listview_third_left_layout, null);
		rightViewThird = LayoutInflater.from(getContext()).inflate(
				R.layout.right_menu3info4_listview_third_right_layout, null);
		mlv_RM3I4_leftLVThird = (MyListView) leftViewThird
				.findViewById(R.id.lv_RM3I4_left_layout_listViewThird);
		mlv_RM3I4_rightLVThird = (MyListView) rightViewThird
				.findViewById(R.id.lv_RM3I4_right_layout_listViewThird);
	
		showListViewResult();//初始化Listview
	}
	
	private void doExecute(){
		et_tv_RM3I4_time.setInputType(InputType.TYPE_NULL);
		et_tv_RM3I4_time.setOnClickListener(new ETRequestDate());
		
		sp_RM3I4_spinnerOne.setPrompt(getContext().getString(R.string.sp_RM3I2_spinnerOneTitle));
		ArrayAdapter<String> firstGradeAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, getContext().getResources().getStringArray(R.array.sp_customFirstGradeItemsText));
		firstGradeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); 
		sp_RM3I4_spinnerOne.setAdapter(firstGradeAdapter);
		sp_RM3I4_spinnerOne.setSelection(0, true);
		sp_RM3I4_spinnerOne.setOnItemSelectedListener(new MySpinnerItemSelectedListener());
		
		sp_RM3I4_spinnerTwo.setPrompt(getContext().getString(R.string.sp_RM3I2_spinnerTwoTitle));
		secondGradeAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, FrameDemoActivity.myApp.CUSTOMS);
		secondGradeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); 
		sp_RM3I4_spinnerTwo.setAdapter(secondGradeAdapter);
		sp_RM3I4_spinnerTwo.setSelection(3, true);
		sp_RM3I4_spinnerTwo.setOnItemSelectedListener(new MySpinnerItemSelectedListener());
		
		sp_RM3I4_spinnerThree.setPrompt(getContext().getString(R.string.sp_RM3I2_spinnerThreeTitle));
		threeGradeAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item,  FrameDemoActivity.myApp.DEP_NAME);
		threeGradeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); 
		sp_RM3I4_spinnerThree.setAdapter(threeGradeAdapter);
		sp_RM3I4_spinnerThree.setSelection(0, true);
		sp_RM3I4_spinnerThree.setOnItemSelectedListener(new MySpinnerItemSelectedListener());
	
		sp_RM3I4_spinnerFour.setPrompt(getContext().getString(R.string.spinner_RM2I1_inOrOutTitle));
		checkTypeAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, checkType);
		checkTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);   
		sp_RM3I4_spinnerFour.setAdapter(checkTypeAdapter);
		sp_RM3I4_spinnerFour.setSelection(0, true);
		sp_RM3I4_spinnerFour.setOnItemSelectedListener(new MySpinnerItemFourSelectedListener());

		sp_RM3I4_spinnerFive.setPrompt(getContext().getString(R.string.spinner_RM2I1_inOrOutTitle));
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.sp_InAndOutItemsText));
		arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);   
		sp_RM3I4_spinnerFive.setAdapter(arrayAdapter);
		sp_RM3I4_spinnerFive.setSelection(2, true);
		sp_RM3I4_spinnerFive.setOnItemSelectedListener(new MySpinnerItemSelectedListener());
		
		///requestData()
		btn_search.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				requestData();
			}
		});
	}
	
	private ArrayAdapter<String> threeGradeAdapter, secondGradeAdapter, checkTypeAdapter;
	
	private class MySpinnerItemSelectedListener implements Spinner.OnItemSelectedListener{

        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3){
        	arg0.setVisibility(View.VISIBLE);
        }
        public void onNothingSelected(AdapterView<?> arg0){
        }
    };
    
    private class MySpinnerItemFourSelectedListener implements Spinner.OnItemSelectedListener{

		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			// TODO Auto-generated method stub
			parent.setVisibility(View.VISIBLE);
			changeListView();
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub
			
		}

    };

    private void changeListView(){
    	if(sp_RM3I4_spinnerFour.getSelectedItem()!=null && !sp_RM3I4_spinnerFour.getSelectedItem().equals("")){
    		ll_RM3I4_one.setVisibility(View.GONE);
			ll_RM3I4_two.setVisibility(View.GONE);
			ll_RM3I4_three.setVisibility(View.GONE);
    		if(sp_RM3I4_spinnerFour.getSelectedItem().equals("人工查验")){
    			ll_RM3I4_one.setVisibility(View.VISIBLE);
			}
			if(sp_RM3I4_spinnerFour.getSelectedItem().equals("机验查验")){
				ll_RM3I4_two.setVisibility(View.VISIBLE);
			}
			if(sp_RM3I4_spinnerFour.getSelectedItem().equals("合计")){
				ll_RM3I4_three.setVisibility(View.VISIBLE);
			}
			
			requestData();//加载后台数据
    	}
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
					if(vId==R.id.et_tv_RM3I4_time){
						et_tv_RM3I4_time.setText((monthOfYear+1)+"");
					}
				}
			}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c
			.get(Calendar.DAY_OF_MONTH));
			datePickerDialog.setTitle("请选择日期");
			datePickerDialog.show();
		}
	};
	
	private void doRM3I4LoadData(LinearLayout ll_RM3I4_one,LinearLayout ll_RM3I4_two,LinearLayout ll_RM3I4_three){
		addFirstListView(leftViewFirst, rightViewFirst);
		addSecondListView(leftViewSecond, rightViewSecond);
		addThirdListView(leftViewThird, rightViewThird);
		sp_RM3I4_spinnerFour.setOnItemSelectedListener(new MySpinnerItemFourSelectedListener());
	}	
	
	private void addFirstListView(View leftView, View rightView) {
		mlv_RM3I4_leftLVFirst.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				mlv_RM3I4_rightLVFirst.onTouchEvent(event);
				return false;
			}
		});

		mlv_RM3I4_rightLVFirst.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {

				mlv_RM3I4_leftLVFirst.dispatchTouchEvent(event);
				return false;
			}
		});

		ll_RM3I4_lv_main_leftFirst.addView(leftView);
		ll_RM3I4_lv_main_rightFirst.addView(rightView);
	}
	
	private void addSecondListView(View leftView, View rightView) {
		mlv_RM3I4_leftLVSecond.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				mlv_RM3I4_rightLVSecond.onTouchEvent(event);
				return false;
			}
		});

		mlv_RM3I4_rightLVSecond.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {

				mlv_RM3I4_leftLVSecond.dispatchTouchEvent(event);
				return false;
			}
		});

		ll_RM3I4_lv_main_leftSecond.addView(leftView);
		ll_RM3I4_lv_main_rightSecond.addView(rightView);
	}
	
	private void addThirdListView(View leftView, View rightView) {
		mlv_RM3I4_leftLVThird.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				mlv_RM3I4_rightLVThird.onTouchEvent(event);
				return false;
			}
		});

		mlv_RM3I4_rightLVThird.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {

				mlv_RM3I4_leftLVThird.dispatchTouchEvent(event);
				return false;
			}
		});

		ll_RM3I4_lv_main_leftThird.addView(leftView);
		ll_RM3I4_lv_main_rightThird.addView(rightView);
	}
	
	@Override
	public void dosomething() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dosomething2() {
		// TODO Auto-generated method stub
		
	}
	//-查验科室绩效-----先判断Null
	private void requestData(){
		if(FrameDemoActivity.myApp.CUSTOMS.size()<=0){
			Toast.makeText(getContext(), "海关部门的查询关区不能为空,请选择。", Toast.LENGTH_LONG).show();
		}else if(FrameDemoActivity.myApp.DEP_NAME.size()<=0){
			Toast.makeText(getContext(), "海关部门的查询科室不能为空,请选择。", Toast.LENGTH_LONG).show();
		}else{
			String time=et_tv_RM3I4_time.getText().toString();
			String str1=sp_RM3I4_spinnerOne.getSelectedItem().toString();
			String str2=sp_RM3I4_spinnerTwo.getSelectedItem().toString();
			String str3=sp_RM3I4_spinnerThree.getSelectedItem().toString();
			String str4=sp_RM3I4_spinnerFour.getSelectedItem().toString();
			String str5=sp_RM3I4_spinnerFive.getSelectedItem().toString();
			//测试数据"上海海关.浦东海关", "全部", "全部", "机检查验", "201109")
			String str=str1+"."+str2;
			str3="全部";
			str5="全部";
			str4="机检查验";
			time="201109";
			System.out.println("条件："+str+str3+str4+time);
			if(time!=null && !time.equals("")){
				new MyAsynTask().execute(str,str3,str5,str4,time);
			}else {
				FrameDemoActivity.toolUtils.promptMessage(" 查询日期不能为空，谢谢。");
			}
		}
	}
	//工作量统计---查验科室绩效
	class MyAsynTask extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			FrameDemoActivity.toolUtils.startProgressDialog();
		}

		@Override
		protected String doInBackground(String... params) {

			// 工作量统计-  -查验科室绩效
			String reqResult = "";
			FrameDemoActivity.webservice.setMETHOD_NAME("GetDeptWorkForPageList");
			FrameDemoActivity.webservice
					.setSOAP_ACTION("http://tempuri.org/IGetExamDataService/GetDeptWorkForPageList");
			if (FrameDemoActivity.webservice.connect(getContext(),new String[] { "customName","depName","i_e_flag","type","time" },
					new Object[] { params[0],params[1],params[2],params[3],params[4] })) {
				reqResult = FrameDemoActivity.webservice.getResult().toString();
				System.out.println("-查验科室绩效：" + reqResult);
				//添加Log日志
				if(reqResult!=null && !reqResult.equals("")){
					FrameDemoActivity.toolUtils.writeDataLog("查询查验部门绩效成功。", "97", "");
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
				System.out.println("-查验科室绩效:"+result);
				examDepMidNewRelList.clear();
				examDepMidNewRelList.addAll((List)FrameDemoActivity.gson.fromJson(result,
						new TypeToken<List<EXAM_DEP_MID_NEW_REL>>() {}.getType()));
				m3i4LeftListAdapter.notifyDataSetChanged();
				m3i4RightListAdapter.notifyDataSetChanged();
			}
		}

	};
	
	private Menu3Info4LeftListViewAdapter m3i4LeftListAdapter;
	private Menu3Info4RightFirstListViewAdapter m3i4RightListAdapter;
	//显示加载的数据结果
	private void showListViewResult(){
		m3i4LeftListAdapter=new Menu3Info4LeftListViewAdapter(getContext(), examDepMidNewRelList);
		m3i4RightListAdapter=new Menu3Info4RightFirstListViewAdapter(getContext(), examDepMidNewRelList);
		mlv_RM3I4_leftLVFirst.setAdapter(m3i4LeftListAdapter);
		mlv_RM3I4_rightLVFirst.setAdapter(m3i4RightListAdapter);
		mlv_RM3I4_leftLVSecond.setAdapter(m3i4LeftListAdapter);
		mlv_RM3I4_rightLVSecond.setAdapter(m3i4RightListAdapter);
		mlv_RM3I4_leftLVThird.setAdapter(m3i4LeftListAdapter);
		mlv_RM3I4_rightLVThird.setAdapter(m3i4RightListAdapter);
	}

}
