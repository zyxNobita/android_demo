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

import com.bai.demo.R;
import com.bai.demo.adapter.Menu2Info3LeftListViewAdapter;
import com.bai.demo.adapter.Menu2Info3RightListViewAdapter;
import com.bai.demo.bean.ProductInfo;
import com.bai.demo.main.FrameDemoActivity;
import com.bai.demo.utils.MyListView;
import com.bai.demo.utils.Webservice;
import com.google.gson.reflect.TypeToken;

/**
 * 商品信息监控
 * @author soft1_developer1
 *
 */
public class Menu2Info3Activity extends RightWindowBase{

	private EditText et_RM2I3_time, et_RM2I3_goodsNum;//布控时间|商品编码
	private Spinner sp_RM2I3_intAndOut;//进出口
	private Button btn_RM2I3_search;//查询
	
	private LinearLayout layout, ll_RM2I3_lv_main_left, ll_RM2I3_lv_main_right;
	private MyListView mlv_RM2I3_leftLV, mlv_RM2I3_rightLV;
	private View leftView, rightView;
	
	private List<ProductInfo> productInfoList = new ArrayList<ProductInfo>();
	private String inAndOut, goodsNum, month;
	//分页数据
	
	public Menu2Info3Activity(Context context){
		super(context);
		setupViews();
	}
	
	public Menu2Info3Activity(Context context, AttributeSet attrs) {
		super(context, attrs);
		setupViews();
	}
	
	private void setupViews(){
		initView();
		//自定义ListView的布局
		leftView = LayoutInflater.from(getContext()).inflate(
				R.layout.right_menu2info3_listview_left_layout, null);
		rightView = LayoutInflater.from(getContext()).inflate(
				R.layout.right_menu2info3_listview_right_layout, null);
		mlv_RM2I3_leftLV = (MyListView) leftView
				.findViewById(R.id.lv_RM2I3_left_layout_listView1);
		mlv_RM2I3_rightLV = (MyListView) rightView
				.findViewById(R.id.lv_RM2I3_right_layout_listView1);
		showProductInfoResult();//设置MyListView的数据
		addListView(leftView, rightView);
		
		addView(layout);
		doExecute();
		//showProductInfoResult();//ListView的初始化
	}

	private void initView(){
		layout = (LinearLayout) LinearLayout.inflate(getContext(),
				R.layout.right_menu2info3, null);
		ll_RM2I3_lv_main_left = (LinearLayout) layout
				.findViewById(R.id.ll_RM2I3_lv_main_left);
		ll_RM2I3_lv_main_right = (LinearLayout) layout
				.findViewById(R.id.ll_RM2I3_lv_main_right);
		et_RM2I3_time=(EditText) layout.findViewById(R.id.et_RM2I3_time);
		sp_RM2I3_intAndOut=(Spinner) layout.findViewById(R.id.sp_RM2I3_inAndOut);
		et_RM2I3_goodsNum=(EditText) layout.findViewById(R.id.et_menu2Info3_number);
		btn_RM2I3_search=(Button) layout.findViewById(R.id.btn_menu2Info3_submit);
	}
	
	private void doExecute(){
		et_RM2I3_time.setInputType(InputType.TYPE_NULL);
		et_RM2I3_time.setOnClickListener(new ETRequestDate());
		
		sp_RM2I3_intAndOut.setPrompt(getContext().getString(R.string.spinner_RM2I1_inOrOutTitle));
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.sp_InAndOutItemsText));
		arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);   
		sp_RM2I3_intAndOut.setAdapter(arrayAdapter);
		sp_RM2I3_intAndOut.setSelection(2, true);//设置默认选中
		sp_RM2I3_intAndOut.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
	           public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3){
	               arg0.setVisibility(View.VISIBLE);
	           }
	           public void onNothingSelected(AdapterView<?> arg0){
	           }

	       });
		
		btn_RM2I3_search.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				goodsNum = et_RM2I3_goodsNum.getText().toString().trim();
				inAndOut = sp_RM2I3_intAndOut.getSelectedItem().toString();
				month=et_RM2I3_time.getText().toString().trim();
				
				if(goodsNum != "" && !goodsNum.equals("")){
					new MyAsynTask().execute(goodsNum, inAndOut, month);
				} else {
					FrameDemoActivity.toolUtils.promptMessage("商品编码不能为空！！！");
				}
			}
			
		});
	}
	
	private class ETRequestDate implements OnClickListener{
		
		@Override
		public void onClick(View v) {
			Calendar c = Calendar.getInstance();
			DatePickerDialog datePickerDialog=new DatePickerDialog(getContext(), FOCUSABLES_TOUCH_MODE,new DatePickerDialog.OnDateSetListener() {
				
				@Override
				public void onDateSet(DatePicker view, int year, int monthOfYear,
						int dayOfMonth) {
					et_RM2I3_time.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
				}
			}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c
			.get(Calendar.DAY_OF_MONTH));
			datePickerDialog.setTitle("请选择日期");
			datePickerDialog.show();
		}
	};
	
	private void addListView(View leftView, View rightView) {
		mlv_RM2I3_leftLV.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				mlv_RM2I3_rightLV.onTouchEvent(event);
				return false;
			}
		});

		mlv_RM2I3_rightLV.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				mlv_RM2I3_leftLV.dispatchTouchEvent(event);
				return false;
			}
		});
		ll_RM2I3_lv_main_left.addView(leftView);
		ll_RM2I3_lv_main_right.addView(rightView);
	}
	
	@Override
	public void dosomething() {
		
	}

	@Override
	public void dosomething2() {
		
	}
	
	/**
	 * 辅助决策支持--商品信息监控
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
			// 获取商品信息
			String reqResult = "";
			FrameDemoActivity.webservice.setMETHOD_NAME("GetProductInfo");
			FrameDemoActivity.webservice.setSOAP_ACTION("http://tempuri.org/IGetExamDataService/GetProductInfo");
			if (FrameDemoActivity.webservice.connect(getContext(),new String[] { "code_ts", "i_e_flag", "rsk_month" },
					new Object[] { params[0], params[1], params[2] })) {
				reqResult = FrameDemoActivity.webservice.getResult().toString();
				System.out.println("商品信息列表：" + reqResult);
				//写服务器的Log日志
				if(reqResult!=null && !reqResult.equals("")){
					FrameDemoActivity.toolUtils.writeDataLog("查询商品信息成功。", "93", "");
				}
			}
			return reqResult;
		}
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			FrameDemoActivity.toolUtils.endProgressDialog();
			if (result.equals("")) {
				FrameDemoActivity.toolUtils.promptMessage("对不起，不存在该商品信息！！！");
			} else if (result != null && !result.equals("")) {
				System.out.println("商品信息列表：" + result);
				productInfoList.clear();
				productInfoList.addAll((List)FrameDemoActivity.gson.fromJson(result, new TypeToken<List<ProductInfo>>() {}.getType()));
				m2i3LeftAdapter.notifyDataSetChanged();
				m2i3RightAdapter.notifyDataSetChanged();
			}
		}
		
	};
	private Menu2Info3LeftListViewAdapter m2i3LeftAdapter;
	private Menu2Info3RightListViewAdapter m2i3RightAdapter;
	// 添加商品信息ListView列表数据
	private void showProductInfoResult(){
		m2i3LeftAdapter=new Menu2Info3LeftListViewAdapter(getContext(), productInfoList);
		mlv_RM2I3_leftLV.setAdapter(m2i3LeftAdapter);
		m2i3RightAdapter=new Menu2Info3RightListViewAdapter(getContext(), productInfoList);
		mlv_RM2I3_rightLV.setAdapter(m2i3RightAdapter);
	}

}
