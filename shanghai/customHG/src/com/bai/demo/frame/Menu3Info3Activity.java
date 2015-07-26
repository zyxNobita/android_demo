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
import com.bai.demo.bean.EXAM_PER_MID_REL;
import com.bai.demo.bean.TipsPeople;
import com.bai.demo.main.FrameDemoActivity;
import com.bai.demo.utils.MyListView;
import com.bai.demo.utils.Webservice;
import com.google.gson.reflect.TypeToken;

/**
 * 查验官员工作绩效
 * @author soft1_developer1
 *
 */
@SuppressLint("ParserError")
public class Menu3Info3Activity extends RightWindowBase{

	private Spinner sp_RM3I3_spinnerOne,sp_RM3I3_spinnerTwo,
					sp_RM3I3_spinnerThree,sp_RM3I3_spinnerFour;
	private EditText et_RM3I3_time;
	private String[] arr={"进口","出口","全部"};
	private LinearLayout layout, ll_RM3I3_lv_main_left, ll_RM3I3_lv_main_right;
	private MyListView mlv_RM3I3_leftLV, mlv_RM3I3_rightLV;
	private List<EXAM_PER_MID_REL> examPerMidRelList=new ArrayList<EXAM_PER_MID_REL>();
	private Button btn_RM3I3_submit;
	public Menu3Info3Activity(Context context){
		super(context);
		setupViews();
	}
	public Menu3Info3Activity(Context context, AttributeSet attrs) {
		super(context, attrs);
		setupViews();
	}
	
	private void setupViews(){
		initView();
		View leftView = LayoutInflater.from(getContext()).inflate(
				R.layout.right_menu3info3_listview_left_layout, null);
		View rightView = LayoutInflater.from(getContext()).inflate(
				R.layout.right_menu3info3_listview_right_layout, null);

		

		mlv_RM3I3_rightLV = (MyListView) rightView
				.findViewById(R.id.lv_RM3I3_right_layout_listView1);
		mlv_RM3I3_leftLV = (MyListView) leftView
				.findViewById(R.id.lv_RM3I3_left_layout_listView1);
		
		addListView(leftView, rightView);
		
		addView(layout);
		doExecute();
		showListViewResult();
	}

	private void initView(){
		layout = (LinearLayout) LinearLayout.inflate(getContext(),
				R.layout.right_menu3info3, null);
		
		ll_RM3I3_lv_main_left = (LinearLayout) layout
				.findViewById(R.id.ll_RM3I3_lv_main_left);
		ll_RM3I3_lv_main_right = (LinearLayout) layout
				.findViewById(R.id.ll_RM3I3_lv_main_right);
		
		et_RM3I3_time=(EditText) layout.findViewById(R.id.et_RM3I3_time);
		sp_RM3I3_spinnerOne=(Spinner) layout.findViewById(R.id.sp_RM3I3_spinnerOne);
		sp_RM3I3_spinnerTwo=(Spinner) layout.findViewById(R.id.sp_RM3I3_spinnerTwo);
		sp_RM3I3_spinnerThree=(Spinner) layout.findViewById(R.id.sp_RM3I3_spinnerThree);
		sp_RM3I3_spinnerFour=(Spinner) layout.findViewById(R.id.sp_RM3I3_spinnerFour);
		btn_RM3I3_submit=(Button) layout.findViewById(R.id.btn_menu3Info3_submit);
	
		
	}

	private void doExecute(){
		et_RM3I3_time.setInputType(InputType.TYPE_NULL);
		et_RM3I3_time.setOnClickListener(new ETRequestDate());
		
		sp_RM3I3_spinnerFour.setPrompt(getContext().getString(R.string.spinner_RM2I1_inOrOutTitle));
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.sp_InAndOutItemsText));
		arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);   
		sp_RM3I3_spinnerFour.setAdapter(arrayAdapter);
		sp_RM3I3_spinnerFour.setSelection(2, true);
		sp_RM3I3_spinnerFour.setOnItemSelectedListener(new MySpinnerItemSelectedListener());
		
		sp_RM3I3_spinnerOne.setPrompt(getContext().getString(R.string.sp_RM3I2_spinnerOneTitle));
		ArrayAdapter<String> firstGradeAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.sp_customFirstGradeItemsText));
		firstGradeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); 
		sp_RM3I3_spinnerOne.setAdapter(firstGradeAdapter);
		sp_RM3I3_spinnerOne.setSelection(0, true);
		sp_RM3I3_spinnerOne.setOnItemSelectedListener(new MySpinnerItemSelectedListener());
	
		sp_RM3I3_spinnerTwo.setPrompt(getContext().getString(R.string.sp_RM3I2_spinnerTwoTitle));
		secondGradeAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, FrameDemoActivity.myApp.CUSTOMS);
		secondGradeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); 
		sp_RM3I3_spinnerTwo.setAdapter(secondGradeAdapter);
		sp_RM3I3_spinnerTwo.setSelection(3, true);
		sp_RM3I3_spinnerTwo.setOnItemSelectedListener(new MySpinnerItemSelectedListener());
		
		sp_RM3I3_spinnerThree.setPrompt(getContext().getString(R.string.sp_RM3I2_spinnerThreeTitle));
		threeGradeAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, FrameDemoActivity.myApp.DEP_NAME);
		threeGradeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); 
		sp_RM3I3_spinnerThree.setAdapter(threeGradeAdapter);
		sp_RM3I3_spinnerThree.setSelection(0, true);
		sp_RM3I3_spinnerThree.setOnItemSelectedListener(new MySpinnerItemSelectedListener());
	
		btn_RM3I3_submit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				requestData();
			}
		});
	}
	
	private ArrayAdapter<String> threeGradeAdapter,secondGradeAdapter;
	
	private class MySpinnerItemSelectedListener implements Spinner.OnItemSelectedListener{

        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3){
        	arg0.setVisibility(View.VISIBLE);
        }
        public void onNothingSelected(AdapterView<?> arg0){
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
					if(monthOfYear<=9){
						dateResult="0"+(monthOfYear+1)+"";
					}else{
						dateResult=(monthOfYear+1)+"";
					}
					if(vId==R.id.et_RM3I3_time){
						et_RM3I3_time.setText(dateResult);
					}
				}
			}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c
			.get(Calendar.DAY_OF_MONTH));
			datePickerDialog.setTitle("请选择日期");
			datePickerDialog.show();
		}
	};
	
	private void addListView(View leftView, View rightView) {
		mlv_RM3I3_leftLV.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				mlv_RM3I3_rightLV.onTouchEvent(event);
				return false;
			}
		});

		mlv_RM3I3_rightLV.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {

				mlv_RM3I3_leftLV.dispatchTouchEvent(event);
				return false;
			}
		});

		ll_RM3I3_lv_main_left.addView(leftView);
		ll_RM3I3_lv_main_right.addView(rightView);
	}
	
	@Override
	public void dosomething() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dosomething2() {
		// TODO Auto-generated method stub
		
	}
	
	private void requestData(){
		if(FrameDemoActivity.myApp.CUSTOMS.size()<=0){
			Toast.makeText(getContext(), "海关部门的查询关区不能为空,请选择。", Toast.LENGTH_LONG).show();
		}else if(FrameDemoActivity.myApp.DEP_NAME.size()<=0){
			Toast.makeText(getContext(), "海关部门的查询科室不能为空,请选择。", Toast.LENGTH_LONG).show();
		}else{
			String time=et_RM3I3_time.getText().toString();
			String str1=sp_RM3I3_spinnerOne.getSelectedItem().toString();
			String str2=sp_RM3I3_spinnerTwo.getSelectedItem().toString();
			String str3=sp_RM3I3_spinnerThree.getSelectedItem().toString();
			String str4=sp_RM3I3_spinnerFour.getSelectedItem().toString();
			
			//测试数据 ("上海海关.浦东海关", ".关税业务处.展览品监管科", "", "全部", "201109");
			String str=str1+"."+str2;
			str3=".关税业务处.展览品监管科";
			String temp="";
			time="201109";
			System.out.println("条件："+str+str3+temp+str4+time);
			if(time!=null && !time.equals("")){
				new MyAsynTask().execute(str,str3,temp,str4,time);
			}else {
				FrameDemoActivity.toolUtils.promptMessage(" 查询日期不能为空，谢谢。");
			}
		}
	}
	//工作量统计--查验关员绩效
	class MyAsynTask extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			FrameDemoActivity.toolUtils.startProgressDialog();
		}

		@Override
		protected String doInBackground(String... params) {

			// 工作量统计-  查验关员绩效
			String reqResult = "";
			FrameDemoActivity.webservice.setMETHOD_NAME("GetPerWorkForPageList");
			FrameDemoActivity.webservice
					.setSOAP_ACTION("http://tempuri.org/IGetExamDataService/GetPerWorkForPageList");
			if (FrameDemoActivity.webservice.connect(getContext(),new String[] { "customName","depName","operationUser","i_e_flag","time" },
					new Object[] { params[0],params[1],params[2],params[3],params[4] })) {
				reqResult = FrameDemoActivity.webservice.getResult().toString();
				System.out.println("工作量统计-  查验关员绩效：" + reqResult);
				//添加Log日志
				if(reqResult!=null && !reqResult.equals("")){
					FrameDemoActivity.toolUtils.writeDataLog("查询查验关员绩效成功。", "96", "");
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
				System.out.println("查询本人处理单证情况"+result);
				examPerMidRelList.clear();
				examPerMidRelList.addAll((List)FrameDemoActivity.gson.fromJson(result,
						new TypeToken<List<EXAM_PER_MID_REL>>() {}.getType()));
				m3i3LeftAdapter.notifyDataSetChanged();
				m3i3RightAdapter.notifyDataSetChanged();
			}
		}

	};
	private Menu3Info3RightListViewAdapter m3i3LeftAdapter;
	private Menu3Info3LeftListViewAdapter m3i3RightAdapter;
	//显示加载的数据结果
	private void showListViewResult(){
		m3i3LeftAdapter=new Menu3Info3RightListViewAdapter(getContext(), examPerMidRelList);
		mlv_RM3I3_rightLV.setAdapter(m3i3LeftAdapter);
		m3i3RightAdapter=new Menu3Info3LeftListViewAdapter(getContext(), examPerMidRelList);
		mlv_RM3I3_leftLV.setAdapter(m3i3RightAdapter);
	}
}
