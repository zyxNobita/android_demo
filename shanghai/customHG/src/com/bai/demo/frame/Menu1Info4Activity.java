package com.bai.demo.frame;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.bai.demo.R;
import com.bai.demo.adapter.ImageAdapter;
import com.bai.demo.adapter.Menu1Info4LeftListViewAdapter;
import com.bai.demo.adapter.Menu1Info4RightListViewAdapter;
import com.bai.demo.bean.G_HEAD;
import com.bai.demo.bean.PHOTO_LIST;
import com.bai.demo.entity.Constant;
import com.bai.demo.main.FrameDemoActivity;
import com.bai.demo.main.PicZoomActivity;
import com.bai.demo.utils.FileUtil;
import com.bai.demo.utils.MyDialog;
import com.bai.demo.utils.MyListView;
import com.bai.demo.zoomImage.ToImageZoomActivity;
import com.google.gson.reflect.TypeToken;

/**
 * 上传信息审核
 * @author soft1_developer1
 */
@SuppressLint({ "HandlerLeak", "ParserError" })
public class Menu1Info4Activity extends RightWindowBase {

	private LinearLayout ll_rightMenu1Info4_first, ll_rightMenu1Info4_second, ll_rightMenu1Info4_third, ll_RM1I4_lv_main_left, ll_RM1I4_lv_main_right,
	ll_RM1I4_check_second_1,ll_RM1I4_check_second_2,ll_RM1I4_check_second_3,
	ll_RM1I4_checkAlarm_second_1,ll_RM1I4_checkAlarm_second_2,ll_RM1I4_checkAlarm_second_3,
	ll_RM1I4_checkViewDialog, ll_RM1I4_chooseCheckViewDialog;
	
	private MyListView mlv_RM1I4_leftLV, mlv_RM1I4_rightLV;
	private Menu1Info4LeftListViewAdapter leftListViewAdapter;
	private Menu1Info4RightListViewAdapter rightListViewAdapter;
	private int currentPage = 1;//当前的第一页
	private List<G_HEAD> gHeadLists=new ArrayList<G_HEAD>();//分页的数据
	private G_HEAD gCheckHead;// 审核获取的G_HEAD实体类
	private G_HEAD gCheckAlarmHead;// 审核报警获取的G_HEAD实体类

	private TextView tv_pageNext, tv_pageAgo;//分页按钮
	private EditText et_menu1Info4_uploadTimeStart, et_menu1Info4_uploadTimeStop, 
	et_RM1I4_orderNumber, et_RM1I4_uploadPeopleNumber,//查询条件
	et_RM1I4CheckViewDialog_editText, et_RM1I4ChooseCheckViewDialog_editText;//Dialog输入框
	
	private Spinner spn_RM1I4ChooseCheckViewDialog_spinner;
	
	private RelativeLayout layout_RM1I4_checkSecond_show_1, layout_RM1I4_checkSecond_show_2, layout_RM1I4_checkSecond_show_3,
	layout_RM1I4_checkAlarmSecond_show_1, layout_RM1I4_checkAlarmSecond_show_2, layout_RM1I4_checkAlarmSecond_show_3;
	
	private Button btn_rightMenu1Info4_llSecond_exitUpdate, btn_rightMenu1Info4_llSecond_waitHandle, btn_rightMenu1Info4_llSecond_back,//审核页面按钮
	btn_rightMenu1Info4_llThird_alarmEnd, btn_rightMenu1Info4_llThird_uploadAgain, btn_rightMenu1Info4_llThird_check, btn_rightMenu1Info4_llThird_back,//审核报警页面按钮
	btn_RM1I4_checkSecond_show_1, btn_RM1I4_checkSecond_show_2, btn_RM1I4_checkSecond_show_3,
	btn_RM1I4_checkAlarmSecond_show_1, btn_RM1I4_checkAlarmSecond_show_2, btn_RM1I4_checkAlarmSecond_show_3,btn_menu1Info4_submit;
	
	// 定义显示图片的Gallery控件
	private Gallery gl_RM1I4Check_containerPic, gl_RM1I4Check_goodsPic, gl_RM1I4Check_containerInfoPic,// 审核
	gl_RM1I4CheckAlarm_containerPic, gl_RM1I4CheckAlarm_goodsPic, gl_RM1I4CheckAlarm_containerInfoPic;// 审核报警
	
	private List<Map<String, Object>> ls;//返回图片的路径集合
	private ImageAdapter sa;//显示图片的Adapter	
	
	public static Handler handler_RM1I4;
	
	public String btnType = "";// 按钮标志（退回修改 | 重上传 = “1”，报警终止 | 转待处理 = “2”）

	public Menu1Info4Activity(Context context) {
		super(context);
		setupViews();
	}

	public Menu1Info4Activity(Context context, AttributeSet attrs) {
		super(context, attrs);
		setupViews();
	}

	private void setupViews() {
		
		initView();
		
		initPageView(ll_rightMenu1Info4_first);
		doChangePages();
		
		addView(ll_rightMenu1Info4_first);
		addView(ll_rightMenu1Info4_second);
		addView(ll_rightMenu1Info4_third);
		
		doExecute();
	}

	private void initView(){
		
		//PAGESIZE = Integer.valueOf(Constant.PAGESIZE);
//		mInflater = LayoutInflater.from(getContext());
		ll_rightMenu1Info4_first = (LinearLayout) LinearLayout.inflate(getContext(),
				R.layout.right_menu1info4, null);
		
		btn_menu1Info4_submit=(Button) ll_rightMenu1Info4_first.findViewById(R.id.btn_menu1Info4_submit);
		ll_RM1I4_lv_main_left = (LinearLayout) ll_rightMenu1Info4_first
				.findViewById(R.id.ll_RM1I4_lv_main_left);
		ll_RM1I4_lv_main_right = (LinearLayout) ll_rightMenu1Info4_first
				.findViewById(R.id.ll_RM1I4_lv_main_right);
		View leftView = LayoutInflater.from(getContext()).inflate(
				R.layout.right_menu1info4_listview_left_layout, null);
		View rightView = LayoutInflater.from(getContext()).inflate(
				R.layout.right_menu1info4_listview_right_layout, null);
		mlv_RM1I4_rightLV = (MyListView) rightView
				.findViewById(R.id.lv_RM1I4_right_layout_listView1);
		mlv_RM1I4_leftLV = (MyListView) leftView
				.findViewById(R.id.lv_RM1I4_left_layout_listView1);
		//初始化数据
		leftListViewAdapter=new Menu1Info4LeftListViewAdapter(getContext(), gHeadLists);
		rightListViewAdapter=new Menu1Info4RightListViewAdapter(getContext(), gHeadLists);
		mlv_RM1I4_rightLV.setAdapter(rightListViewAdapter);
		mlv_RM1I4_leftLV.setAdapter(leftListViewAdapter);
		addListView(leftView, rightView);
		
		et_RM1I4_orderNumber=(EditText) ll_rightMenu1Info4_first.findViewById(R.id.et_RM1I4_orderNumber);
		et_RM1I4_uploadPeopleNumber=(EditText) ll_rightMenu1Info4_first.findViewById(R.id.et_RM1I4_uploadPeopleNumber);
		// 设置分页
		
		
		
		ll_rightMenu1Info4_second = (LinearLayout) LinearLayout.inflate(getContext(),
				R.layout.right_menu1info4_list_check_info, null);
		layout_RM1I4_checkSecond_show_1=(RelativeLayout) ll_rightMenu1Info4_second.findViewById(R.id.layout_checkSecond_show_1);
		layout_RM1I4_checkSecond_show_2=(RelativeLayout) ll_rightMenu1Info4_second.findViewById(R.id.layout_checkSecond_show_2);
		layout_RM1I4_checkSecond_show_3=(RelativeLayout) ll_rightMenu1Info4_second.findViewById(R.id.layout_checkSecond_show_3);
		ll_RM1I4_check_second_1=(LinearLayout) ll_rightMenu1Info4_second.findViewById(R.id.ll_check_second_1);
		ll_RM1I4_check_second_2=(LinearLayout) ll_rightMenu1Info4_second.findViewById(R.id.ll_check_second_2);
		ll_RM1I4_check_second_3=(LinearLayout) ll_rightMenu1Info4_second.findViewById(R.id.ll_check_second_3);
		btn_RM1I4_checkSecond_show_1=(Button) ll_rightMenu1Info4_second.findViewById(R.id.btn_checkSecond_show_1);
		btn_RM1I4_checkSecond_show_2=(Button) ll_rightMenu1Info4_second.findViewById(R.id.btn_checkSecond_show_2);
		btn_RM1I4_checkSecond_show_3=(Button) ll_rightMenu1Info4_second.findViewById(R.id.btn_checkSecond_show_3);
		btn_rightMenu1Info4_llSecond_exitUpdate = (Button) ll_rightMenu1Info4_second.findViewById(R.id.btn_rightMenu1Info4_llSecond_exitUpdate);
		btn_rightMenu1Info4_llSecond_waitHandle = (Button) ll_rightMenu1Info4_second.findViewById(R.id.btn_rightMenu1Info4_llSecond_waitHandle);
		btn_rightMenu1Info4_llSecond_back = (Button) ll_rightMenu1Info4_second.findViewById(R.id.btn_rightMenu1Info4_llSecond_back);
		gl_RM1I4Check_containerPic = (Gallery) ll_rightMenu1Info4_second.findViewById(R.id.gl_RM1I4Check_containerPic);
		gl_RM1I4Check_goodsPic = (Gallery) ll_rightMenu1Info4_second.findViewById(R.id.gl_RM1I4Check_goodsPic);
        gl_RM1I4Check_containerInfoPic = (Gallery) ll_rightMenu1Info4_second.findViewById(R.id.gl_RM1I4Check_containerInfoPic);
		
		ll_rightMenu1Info4_third = (LinearLayout) LinearLayout.inflate(getContext(),
				R.layout.right_menu1info4_list_checkalarm_info, null);
		layout_RM1I4_checkAlarmSecond_show_1=(RelativeLayout) ll_rightMenu1Info4_third.findViewById(R.id.layout_checkAlarmSecond_show_1);
		layout_RM1I4_checkAlarmSecond_show_2=(RelativeLayout) ll_rightMenu1Info4_third.findViewById(R.id.layout_checkAlarmSecond_show_2);
		layout_RM1I4_checkAlarmSecond_show_3=(RelativeLayout) ll_rightMenu1Info4_third.findViewById(R.id.layout_checkAlarmSecond_show_3);
		ll_RM1I4_checkAlarm_second_1=(LinearLayout) ll_rightMenu1Info4_third.findViewById(R.id.ll_checkAlarm_second_1);
		ll_RM1I4_checkAlarm_second_2=(LinearLayout) ll_rightMenu1Info4_third.findViewById(R.id.ll_checkAlarm_second_2);
		ll_RM1I4_checkAlarm_second_3=(LinearLayout) ll_rightMenu1Info4_third.findViewById(R.id.ll_checkAlarm_second_3);
		btn_RM1I4_checkAlarmSecond_show_1=(Button) ll_rightMenu1Info4_third.findViewById(R.id.btn_checkAlarmSecond_show_1);
		btn_RM1I4_checkAlarmSecond_show_2=(Button) ll_rightMenu1Info4_third.findViewById(R.id.btn_checkAlarmSecond_show_2);
		btn_RM1I4_checkAlarmSecond_show_3=(Button) ll_rightMenu1Info4_third.findViewById(R.id.btn_checkAlarmSecond_show_3);
		btn_rightMenu1Info4_llThird_alarmEnd = (Button) ll_rightMenu1Info4_third.findViewById(R.id.btn_rightMenu1Info4_llThird_alarmEnd);
		btn_rightMenu1Info4_llThird_uploadAgain = (Button) ll_rightMenu1Info4_third.findViewById(R.id.btn_rightMenu1Info4_llThird_uploadAgain);
		btn_rightMenu1Info4_llThird_check = (Button) ll_rightMenu1Info4_third.findViewById(R.id.btn_rightMenu1Info4_llThird_check);
		btn_rightMenu1Info4_llThird_back = (Button) ll_rightMenu1Info4_third.findViewById(R.id.btn_rightMenu1Info4_llThird_back);
		gl_RM1I4CheckAlarm_containerPic = (Gallery) ll_rightMenu1Info4_third.findViewById(R.id.gl_RM1I4CheckAlarm_containerPic);
        gl_RM1I4CheckAlarm_goodsPic = (Gallery) ll_rightMenu1Info4_third.findViewById(R.id.gl_RM1I4CheckAlarm_goodsPic);
        gl_RM1I4CheckAlarm_containerInfoPic = (Gallery) ll_rightMenu1Info4_third.findViewById(R.id.gl_RM1I4CheckAlarm_containerInfoPic);
	}
	
	private void doExecute() {
	
		btn_RM1I4_checkSecond_show_1.setOnClickListener(new MyOnClickListener());
		btn_RM1I4_checkSecond_show_2.setOnClickListener(new MyOnClickListener());
		btn_RM1I4_checkSecond_show_3.setOnClickListener(new MyOnClickListener());
		btn_rightMenu1Info4_llSecond_exitUpdate.setOnClickListener(new MyOnClickListener());
		btn_rightMenu1Info4_llSecond_waitHandle.setOnClickListener(new MyOnClickListener());
		btn_rightMenu1Info4_llSecond_back.setOnClickListener(new MyOnClickListener());
		
		btn_RM1I4_checkAlarmSecond_show_1.setOnClickListener(new MyOnClickListener());
		btn_RM1I4_checkAlarmSecond_show_2.setOnClickListener(new MyOnClickListener());
		btn_RM1I4_checkAlarmSecond_show_3.setOnClickListener(new MyOnClickListener());
		btn_rightMenu1Info4_llThird_alarmEnd.setOnClickListener(new MyOnClickListener());
		btn_rightMenu1Info4_llThird_uploadAgain.setOnClickListener(new MyOnClickListener());
		btn_rightMenu1Info4_llThird_check.setOnClickListener(new MyOnClickListener());
		btn_rightMenu1Info4_llThird_back.setOnClickListener(new MyOnClickListener());

		galleryClick(gl_RM1I4Check_containerPic);
		galleryClick(gl_RM1I4Check_goodsPic);
		galleryClick(gl_RM1I4Check_containerInfoPic);
		galleryClick(gl_RM1I4CheckAlarm_containerPic);
		galleryClick(gl_RM1I4CheckAlarm_goodsPic);
		galleryClick(gl_RM1I4CheckAlarm_containerInfoPic);
		
		handler_RM1I4 = new Handler() {
			public void dispatchMessage(android.os.Message msg) {
				int result = msg.what;
				switch (result) {
				
				case Constant.RINGHT_GROUP1_MENU4_CHECK://审核
					ll_rightMenu1Info4_first.setVisibility(View.GONE);
					ll_rightMenu1Info4_second.setVisibility(View.VISIBLE);
					ll_rightMenu1Info4_third.setVisibility(View.GONE);
					if(rightListViewAdapter.getgHead()!=null && !rightListViewAdapter.getgHead().equals("")){
//						new MyAsynTaskCheckGetBGDHeadInfo().execute(rightListViewAdapter.getgHead().getENTRY_ID());
						FrameDemoActivity.myApp.setManifestNumber("");
						FrameDemoActivity.myApp.setManifestNumber(rightListViewAdapter.getgHead().getENTRY_ID());
						String manifestNumber = FrameDemoActivity.myApp.getManifestNumber();
						reqCheckGetBGDHeadInfo(manifestNumber);
					}
					break;
					
				case Constant.RINGHT_GROUP1_MENU4_CHECKALARM://报警处理
					ll_rightMenu1Info4_first.setVisibility(View.GONE);
					ll_rightMenu1Info4_second.setVisibility(View.GONE);
					ll_rightMenu1Info4_third.setVisibility(View.VISIBLE);
					if(rightListViewAdapter.getgHead()!=null && !rightListViewAdapter.getgHead().equals("")){
//						new MyAsynTaskCheckAlarmGetBGDHeadInfo().execute(rightListViewAdapter.getgHead().getENTRY_ID());
						FrameDemoActivity.myApp.setManifestNumber("");
						FrameDemoActivity.myApp.setManifestNumber(rightListViewAdapter.getgHead().getENTRY_ID());
						String manifestNumber = FrameDemoActivity.myApp.getManifestNumber();
						reqCheckAlarmGetBGDHeadInfo(manifestNumber);
					}
					break;
					
				case Constant.NOTITY_DATACHANGE://ListView的数据刷新
					leftListViewAdapter.notifyDataSetChanged();
					rightListViewAdapter.notifyDataSetChanged();
					break;
				
				case Constant.CHECK_SUCCESS:
					FrameDemoActivity.toolUtils.endProgressDialog();
					showCheckGHeadResult();
					break;
					
				case Constant.CHECK_FAIL:
					FrameDemoActivity.toolUtils.promptMessage("对不起，不存在此单证审核信息！！！");
					FrameDemoActivity.toolUtils.endProgressDialog();
					break;
					
				case Constant.CHECKALARM_SUCCESS:
					FrameDemoActivity.toolUtils.endProgressDialog();
					showCheckAlarmGHeadResult();
					break;
					
				case Constant.CHECKALARM_FAIL:
					FrameDemoActivity.toolUtils.promptMessage("对不起，不存在此单证审核报警信息！！！");
					FrameDemoActivity.toolUtils.endProgressDialog();
					break;
					
				case Constant.WEBSERVICE_SUCCESS:
					FrameDemoActivity.toolUtils.endProgressDialog();
					break;
					
				case Constant.WEBSERVICE_FAIL:
					FrameDemoActivity.toolUtils.endProgressDialog();
					break;
				
				}
		
			};
		};
		
		btn_menu1Info4_submit.setOnClickListener(new DoPageClickListener());
		showTradeInfoResult();//初始化ListView
		reqOPNAME();
	}
	
	private void addListView(View leftView, View rightView) {

		mlv_RM1I4_leftLV.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				mlv_RM1I4_rightLV.onTouchEvent(event);
				return false;
			}
		});

		mlv_RM1I4_rightLV.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				mlv_RM1I4_leftLV.dispatchTouchEvent(event);
				return false;
			}
		});
		
		ll_RM1I4_lv_main_left.addView(leftView);
		ll_RM1I4_lv_main_right.addView(rightView);
	}
	//分页监听
	private void doChangePages() {
		tv_pageNext.setOnClickListener(new DoPageClickListener());
		tv_pageAgo.setOnClickListener(new DoPageClickListener());
	}

	/**
	 * 分页按钮监听事件
	 * @author zhangyx
	 *
	 */
	private class DoPageClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			int v_id = v.getId();
			switch (v_id) {
			case R.id.btn_menu1Info4_submit://查询
				//测试数据
				requestDataInit("1");
				break;
			case R.id.tv_pageNext:
				if(currentPage<=0){
					currentPage=0;
				}
				currentPage++;
				requestDataInit(currentPage+"");
				break;
			case R.id.tv_pageAgo:
				if(currentPage<=0){
					currentPage=0;
				}else{
					currentPage--;
				}
				requestDataInit(currentPage+"");
				break;
			}
		}
	};

	private void initPageView(LinearLayout layout) {
		et_menu1Info4_uploadTimeStart = (EditText) layout
				.findViewById(R.id.et_menu1Info4_uploadTimeStart);
		et_menu1Info4_uploadTimeStop = (EditText) layout
				.findViewById(R.id.et_menu1Info4_uploadTimeStop);
		et_menu1Info4_uploadTimeStart.setInputType(InputType.TYPE_NULL);
		et_menu1Info4_uploadTimeStop.setInputType(InputType.TYPE_NULL);
		et_menu1Info4_uploadTimeStart.setOnClickListener(new ETRequestDate());
		et_menu1Info4_uploadTimeStop.setOnClickListener(new ETRequestDate());
		tv_pageNext = (TextView) layout.findViewById(R.id.tv_pageNext);
		tv_pageAgo = (TextView) layout.findViewById(R.id.tv_pageAgo);
	}

	private String dateResult = "";
	//日期获取
	private class ETRequestDate implements OnClickListener {

		@Override
		public void onClick(View v) {

			final int vId = v.getId();
			Calendar c = Calendar.getInstance();
			DatePickerDialog datePickerDialog = new DatePickerDialog(
					getContext(), FOCUSABLES_TOUCH_MODE,
					new DatePickerDialog.OnDateSetListener() {

						@Override
						public void onDateSet(DatePicker view, int year,
								int monthOfYear, int dayOfMonth) {
							dateResult = year + "-" + (monthOfYear + 1) + "-"
									+ dayOfMonth;
							if (vId == R.id.et_menu1Info4_uploadTimeStart) {
								et_menu1Info4_uploadTimeStart
										.setText(dateResult);
							} else if (vId == R.id.et_menu1Info4_uploadTimeStop) {
								et_menu1Info4_uploadTimeStop
										.setText(dateResult);
							}
						}
					}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c
							.get(Calendar.DAY_OF_MONTH));
			datePickerDialog.setTitle("请选择日期");
			datePickerDialog.show();
		}
	};
	
	private class MyOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			int btnId = v.getId();
			switch (btnId) {
			
			case R.id.btn_checkSecond_show_1:
				ll_RM1I4_check_second_1.setVisibility(View.VISIBLE);
				ll_RM1I4_check_second_2.setVisibility(View.GONE);
				ll_RM1I4_check_second_3.setVisibility(View.GONE);
				layout_RM1I4_checkSecond_show_1.setBackgroundResource(R.drawable.show_btn_bg_down_pic);
				layout_RM1I4_checkSecond_show_2.setBackgroundResource(R.drawable.show_btn_bg_up_pic);
				layout_RM1I4_checkSecond_show_3.setBackgroundResource(R.drawable.show_btn_bg_up_pic);
				break;
				
			case R.id.btn_checkSecond_show_2:
				ll_RM1I4_check_second_1.setVisibility(View.GONE);
				ll_RM1I4_check_second_2.setVisibility(View.VISIBLE);
				ll_RM1I4_check_second_3.setVisibility(View.GONE);
				layout_RM1I4_checkSecond_show_1.setBackgroundResource(R.drawable.show_btn_bg_up_pic);
				layout_RM1I4_checkSecond_show_2.setBackgroundResource(R.drawable.show_btn_bg_down_pic);
				layout_RM1I4_checkSecond_show_3.setBackgroundResource(R.drawable.show_btn_bg_up_pic);
				break;
				
			case R.id.btn_checkSecond_show_3:
				ll_RM1I4_check_second_1.setVisibility(View.GONE);
				ll_RM1I4_check_second_2.setVisibility(View.GONE);
				ll_RM1I4_check_second_3.setVisibility(View.VISIBLE);
				layout_RM1I4_checkSecond_show_1.setBackgroundResource(R.drawable.show_btn_bg_up_pic);
				layout_RM1I4_checkSecond_show_2.setBackgroundResource(R.drawable.show_btn_bg_up_pic);
				layout_RM1I4_checkSecond_show_3.setBackgroundResource(R.drawable.show_btn_bg_down_pic);
				break;
				
			case R.id.btn_checkAlarmSecond_show_1:
				ll_RM1I4_checkAlarm_second_1.setVisibility(View.VISIBLE);
				ll_RM1I4_checkAlarm_second_2.setVisibility(View.GONE);
				ll_RM1I4_checkAlarm_second_3.setVisibility(View.GONE);
				layout_RM1I4_checkAlarmSecond_show_1.setBackgroundResource(R.drawable.show_btn_bg_down_pic);
				layout_RM1I4_checkAlarmSecond_show_2.setBackgroundResource(R.drawable.show_btn_bg_up_pic);
				layout_RM1I4_checkAlarmSecond_show_3.setBackgroundResource(R.drawable.show_btn_bg_up_pic);
				break;
				
			case R.id.btn_checkAlarmSecond_show_2:
				ll_RM1I4_checkAlarm_second_1.setVisibility(View.GONE);
				ll_RM1I4_checkAlarm_second_2.setVisibility(View.VISIBLE);
				ll_RM1I4_checkAlarm_second_3.setVisibility(View.GONE);
				layout_RM1I4_checkAlarmSecond_show_1.setBackgroundResource(R.drawable.show_btn_bg_up_pic);
				layout_RM1I4_checkAlarmSecond_show_2.setBackgroundResource(R.drawable.show_btn_bg_down_pic);
				layout_RM1I4_checkAlarmSecond_show_3.setBackgroundResource(R.drawable.show_btn_bg_up_pic);
				break;
				
			case R.id.btn_checkAlarmSecond_show_3:
				ll_RM1I4_checkAlarm_second_1.setVisibility(View.GONE);
				ll_RM1I4_checkAlarm_second_2.setVisibility(View.GONE);
				ll_RM1I4_checkAlarm_second_3.setVisibility(View.VISIBLE);
				layout_RM1I4_checkAlarmSecond_show_1.setBackgroundResource(R.drawable.show_btn_bg_up_pic);
				layout_RM1I4_checkAlarmSecond_show_2.setBackgroundResource(R.drawable.show_btn_bg_up_pic);
				layout_RM1I4_checkAlarmSecond_show_3.setBackgroundResource(R.drawable.show_btn_bg_down_pic);
				break;
				
			case R.id.btn_rightMenu1Info4_llSecond_exitUpdate:
				btnType = "1";
				String manifestNumberOne = ((TextView)ll_rightMenu1Info4_second.findViewById(R.id.tv_check_text1)).getText().toString();
				FrameDemoActivity.myApp.setManifestNumber("");
				FrameDemoActivity.myApp.setManifestNumber(manifestNumberOne);
				initChooseCheckViewDialog();
				break;
				
			case R.id.btn_rightMenu1Info4_llSecond_waitHandle:
				btnType = "1";
				String manifestNumberTwo = ((TextView)ll_rightMenu1Info4_second.findViewById(R.id.tv_check_text1)).getText().toString();
				FrameDemoActivity.myApp.setManifestNumber("");
				FrameDemoActivity.myApp.setManifestNumber(manifestNumberTwo);
				initCheckViewDialog();
				break;
			
			case R.id.btn_rightMenu1Info4_llSecond_back:
				ll_rightMenu1Info4_first.setVisibility(View.VISIBLE);
				ll_rightMenu1Info4_second.setVisibility(View.GONE);
				ll_rightMenu1Info4_third.setVisibility(View.GONE);
				break;
				
			case R.id.btn_rightMenu1Info4_llThird_alarmEnd:
				btnType = "2";
				String manifestNumberThree = ((TextView)ll_rightMenu1Info4_third.findViewById(R.id.tv_checkAlarm_text1)).getText().toString();
				FrameDemoActivity.myApp.setManifestNumber("");
				FrameDemoActivity.myApp.setManifestNumber(manifestNumberThree);
				initCheckViewDialog();
				break;
				
			case R.id.btn_rightMenu1Info4_llThird_uploadAgain:
				btnType = "2";
				String manifestNumberFour = ((TextView)ll_rightMenu1Info4_third.findViewById(R.id.tv_checkAlarm_text1)).getText().toString();
				FrameDemoActivity.myApp.setManifestNumber("");
				FrameDemoActivity.myApp.setManifestNumber(manifestNumberFour);
				initChooseCheckViewDialog();
				break;
				
			case R.id.btn_rightMenu1Info4_llThird_check:
				ll_rightMenu1Info4_first.setVisibility(View.GONE);
				ll_rightMenu1Info4_second.setVisibility(View.VISIBLE);
				ll_rightMenu1Info4_third.setVisibility(View.GONE);
				String manifestNumberFive = ((TextView)ll_rightMenu1Info4_third.findViewById(R.id.tv_checkAlarm_text1)).getText().toString();
				FrameDemoActivity.myApp.setManifestNumber("");
				FrameDemoActivity.myApp.setManifestNumber(manifestNumberFive);
				new MyAsynTaskUpdateAuditAlarmFlag().execute(FrameDemoActivity.myApp.getManifestNumber(), "0");
				reqCheckGetBGDHeadInfo(FrameDemoActivity.myApp.getManifestNumber());
				break;
				
			case R.id.btn_rightMenu1Info4_llThird_back:
				ll_rightMenu1Info4_first.setVisibility(View.VISIBLE);
				ll_rightMenu1Info4_second.setVisibility(View.GONE);
				ll_rightMenu1Info4_third.setVisibility(View.GONE);
				break;
				
			}
		}
	};
	
	// 弹出选择上传修改人、填写审核意见Dialog
	private void initChooseCheckViewDialog() {
		ll_RM1I4_chooseCheckViewDialog = (LinearLayout) LinearLayout.inflate(getContext(),
				R.layout.right_menu1info4_choose_checkview_dialog, null);
		MyDialog.showChooseCheckViewDialog(getContext(), ll_RM1I4_chooseCheckViewDialog,
				getContext().getString(R.string.chooseCheckViewDialog_title));
		spn_RM1I4ChooseCheckViewDialog_spinner = (Spinner) ll_RM1I4_chooseCheckViewDialog.
				findViewById(R.id.spn_RM1I4ChooseCheckViewDialog_spinner);
		et_RM1I4ChooseCheckViewDialog_editText = (EditText) ll_RM1I4_chooseCheckViewDialog.
				findViewById(R.id.et_RM1I4ChooseCheckViewDialog_editText);
		
		spn_RM1I4ChooseCheckViewDialog_spinner.setPrompt(getContext().getString(R.string.sp_RM1I4_spinnerTitle));
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, FrameDemoActivity.myApp.OP_NAME);
		arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);   
		spn_RM1I4ChooseCheckViewDialog_spinner.setAdapter(arrayAdapter);
		spn_RM1I4ChooseCheckViewDialog_spinner.setSelection(0, true);
		spn_RM1I4ChooseCheckViewDialog_spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){

	           public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
	        	   parent.setVisibility(View.VISIBLE);
	           }

	           public void onNothingSelected(AdapterView<?> arg0){
	           }

	       });
		
		Button btn_RM1I4ChooseCheckViewDialog_sure = (Button) ll_RM1I4_chooseCheckViewDialog
				.findViewById(R.id.btn_RM1I4ChooseCheckViewDialog_sure);
		Button btn_RM1I4ChooseCheckViewDialog_close = (Button) ll_RM1I4_chooseCheckViewDialog
				.findViewById(R.id.btn_RM1I4ChooseCheckViewDialog_close);
		
		btn_RM1I4ChooseCheckViewDialog_sure.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(btnType != null && btnType.equals("1")){
					reqUpdateAudit("21", spn_RM1I4ChooseCheckViewDialog_spinner.getSelectedItem().toString(), 
							FrameDemoActivity.toolUtils.requestData(Constant.CONCRETE_DATA), FrameDemoActivity.myApp.getManifestNumber(), 
							et_RM1I4ChooseCheckViewDialog_editText.getText().toString());
					
				} else if(btnType != null && btnType.equals("2")) {
					reqUpdateAuditAlarm(FrameDemoActivity.myApp.getManifestNumber(), "22", "4", FrameDemoActivity.toolUtils.requestData(Constant.CONCRETE_DATA), 
							spn_RM1I4ChooseCheckViewDialog_spinner.getSelectedItem().toString(), 
							et_RM1I4ChooseCheckViewDialog_editText.getText().toString());
				}
				MyDialog.dismissDialog();
			}
					
		});
		
		btn_RM1I4ChooseCheckViewDialog_close.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				MyDialog.dismissDialog();
			}
					
		});

	}
	
	// 弹出填写审核意见Dialog
	private void initCheckViewDialog() {
		ll_RM1I4_checkViewDialog = (LinearLayout) LinearLayout.inflate(getContext(),
				R.layout.right_menu1info4_checkview_dialog, null);
		MyDialog.showCheckViewDialog(getContext(), ll_RM1I4_checkViewDialog,
				getContext().getString(R.string.checkViewDialog_title));
		
		et_RM1I4CheckViewDialog_editText = (EditText) ll_RM1I4_checkViewDialog.
				findViewById(R.id.et_RM1I4CheckViewDialog_editText);
		
		Button btn_RM1I4CheckViewDialog_sure = (Button) ll_RM1I4_checkViewDialog
				.findViewById(R.id.btn_RM1I4CheckViewDialog_sure);
		Button btn_RM1I4CheckViewDialog_close = (Button) ll_RM1I4_checkViewDialog
				.findViewById(R.id.btn_RM1I4CheckViewDialog_close);
		
		btn_RM1I4CheckViewDialog_sure.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(btnType != null && btnType.equals("1")){
					reqUpdateAudit("20", FrameDemoActivity.myApp.userInfo.getLobNumber(), 
							FrameDemoActivity.toolUtils.requestData(Constant.CONCRETE_DATA), FrameDemoActivity.myApp.getManifestNumber(), 
							et_RM1I4CheckViewDialog_editText.getText().toString());
					
				} else if(btnType != null && btnType.equals("2")) {
					reqUpdateAuditAlarm(FrameDemoActivity.myApp.getManifestNumber(), "22", "3", FrameDemoActivity.toolUtils.requestData(Constant.CONCRETE_DATA), 
							FrameDemoActivity.myApp.userInfo.getLobNumber(),
							et_RM1I4CheckViewDialog_editText.getText().toString());
				}
				MyDialog.dismissDialog();
			}
					
		});
		
		btn_RM1I4CheckViewDialog_close.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				MyDialog.dismissDialog();
			}
					
		});

	}

	@Override
	public void dosomething() {

	}

	@Override
	public void dosomething2() {

	}
	
	/**
	 * 准备请求分页的数据
	 */
	private void requestDataInit(String currentPage){
		String startTime=et_menu1Info4_uploadTimeStart.getText().toString();
		String stopTime=et_menu1Info4_uploadTimeStop.getText().toString();
		//测试数据  以后需删除
		String sectionCode=FrameDemoActivity.myApp.userInfo.getSectionCode();//科室号
		if(sectionCode==null || sectionCode.equals("")){
			sectionCode="2200131";
		}
		startTime = "2011-09-01";
		stopTime = "2013-09-01";
		if(startTime!=null && !startTime.equals("") || stopTime!=null && !stopTime.equals("")){
			new MyAsynTask().execute(et_RM1I4_orderNumber.getText().toString(),startTime,stopTime,et_RM1I4_uploadPeopleNumber.getText().toString(),sectionCode,currentPage,Constant.PAGESIZE);
		}else{
			FrameDemoActivity.toolUtils.promptMessage("查询时间不能为空");
		}
	}
	
	/**
	 * 请求审核列表数据:查询待审核的单证
	 * @author zhangyx
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
			// 查询待审核的单证
			String reqResult = "";
		
			FrameDemoActivity.webservice.setMETHOD_NAME("GetAuditInfo");
			FrameDemoActivity.webservice.setSOAP_ACTION("http://tempuri.org/IGetExamDataService/GetAuditInfo");
			if (FrameDemoActivity.webservice.connect(getContext(),new String[] { "entryId", "beginTime", "endTime", "uploader", "sectionCode" ,"pageIndex","pageSize"},
					new Object[] { params[0], params[1], params[2], params[3], params[4],params[5],params[6] })) {
				reqResult = FrameDemoActivity.webservice.getResult().toString();
				System.out.println("查询待审核的单证：" + reqResult);
				//添加Log日志
				if(reqResult!=null && !reqResult.equals("")){
					FrameDemoActivity.toolUtils.writeDataLog("待审核列表查询成功。", "91", "");
				}
			}
			//添加临时数据-----本地数据测试
//			if(reqResult.equals("") || reqResult.equals("[]")){
//				reqResult=TestAddData.str4;
//			}
			return reqResult;
		}
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			FrameDemoActivity.toolUtils.endProgressDialog();
			if (result.equals("") || result.equals("[]")) {
				currentPage--;
				FrameDemoActivity.toolUtils.promptMessage("对不起，不存在审核单证信息！！！");
			} else if (result != null && !result.equals("") && !result.equals("[]")) {
				gHeadLists.clear();
				gHeadLists.addAll((List)FrameDemoActivity.gson.fromJson(result, new TypeToken<List<G_HEAD>>() {}.getType()));
				handler_RM1I4.sendEmptyMessage(Constant.NOTITY_DATACHANGE);
			}
		}
		 
	};
	
	//数据得赋值
	private void showTradeInfoResult(){
		leftListViewAdapter=new Menu1Info4LeftListViewAdapter(getContext(), gHeadLists);
		rightListViewAdapter=new Menu1Info4RightListViewAdapter(getContext(), gHeadLists);
		mlv_RM1I4_rightLV.setAdapter(rightListViewAdapter);
		mlv_RM1I4_leftLV.setAdapter(leftListViewAdapter);
	}
	
	// 下载图片的相关信息
	class MyAsynTaskImageDownload extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			FrameDemoActivity.toolUtils.startProgressDialog();
		}
		
		@Override
		protected String doInBackground(String... params) {
			// 图片的下载 Task
			String reqResult = "";
			FrameDemoActivity.webservice.setMETHOD_NAME("GetPhotoInfo");
			FrameDemoActivity.webservice
					.setSOAP_ACTION("http://tempuri.org/IGetExamDataService/GetPhotoInfo");
			if (FrameDemoActivity.webservice.connect(getContext(),new String[] {"entry_id","g_no" },
					new Object[] { params[0], params[1] })) {
				reqResult = FrameDemoActivity.webservice.getResult().toString();
				System.out.println("图片下载：" + reqResult);
			}
			return reqResult;
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if (result.equals("")) {
				FrameDemoActivity.toolUtils.promptMessage("报关单没有上传过图片。");
			} else if (result != null && !result.equals("")) {
				FrameDemoActivity.myApp.photoList.clear();
				FrameDemoActivity.myApp.photoList.addAll(((List) FrameDemoActivity.gson.fromJson(
						result, new TypeToken<List<PHOTO_LIST>>() {
						}.getType())));
				new MyAsynTaskDownloadToSDCard().execute();
			}
			FrameDemoActivity.toolUtils.endProgressDialog();
		}

	};

	//下载图片到SDCard
	class MyAsynTaskDownloadToSDCard extends AsyncTask<String, String, Map<String,String>> {
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			FrameDemoActivity.toolUtils.startDownloadProgressDialog();
		}

		@SuppressLint("ParserError")
		@SuppressWarnings("static-access")
		@Override
		protected Map<String,String> doInBackground(String... params) {
			// 图片的下载 Task
			Map<String,String> resMap=new HashMap<String,String>();
			if(FrameDemoActivity.myApp.photoList!=null){
				int pListSize=FrameDemoActivity.myApp.photoList.size();
				for(int i=0;i<pListSize;i++){
					PHOTO_LIST p=FrameDemoActivity.myApp.photoList.get(i);
					String photoPath=p.getPHOTO_ID();
					if(photoPath!=null && !photoPath.equals("")){
						String reqPath=getContext().getString(R.string.DOWNLOADIMAGE_P)+p.getPHOTO_ID();
						String imageName= p.getENTRY_ID() + "_" + p.getG_NO() + "_"
								+ p.getPHOTO_CODE() + "_"+ i;
						String savePath=Constant.DISPLAY_SAVAPATH+ p.getENTRY_ID() + "/" + p.getG_NO()
								+ "/" + p.getPHOTO_CODE() + "/";
						Bitmap bitmap=FrameDemoActivity.imageUtil.downloadBitmapByCwj(reqPath);//下载图片
						byte[] buffer=FrameDemoActivity.imageUtil.bitmapTobyte(bitmap);
						//写入SDCard---图片写入
						FrameDemoActivity.imageUtil.downloadImage(buffer,imageName, savePath);//写入SDCard
						//下载了图片成功之后
						resMap.put("res", "True");//返回的结果
						resMap.put("reqResult"+i, savePath);//返回保存图片的路径----有多类图片
						FrameDemoActivity.imageUtil.recycle(bitmap);//释放资源
					}else{
						//没有要下载的图片
						resMap.put("res", "");//返回的结果
						resMap.put("reqResult", "对不起，报关单没有上传过图片。");
					}
				}
			}
			return resMap;
		}

		@Override
		protected void onPostExecute(Map<String,String> result) {
			super.onPostExecute(result);
			//通知页面显示下载得图片
			if(result.get("res").equals("")){//无下载图片
				System.out.println(result.get("reqResult")+"");
			}else{
				//扫面SDCard加载对应图片显示
				reqSDCardDBImages(result);
			}
			FrameDemoActivity.toolUtils.endDownloadProgressDialog();
			
		}

	};
	
	//根据图片的路径扫描图片显示
	private void reqSDCardDBImages(Map<String,String> mapPaths){
		int size=FrameDemoActivity.myApp.photoList.size();
		for(int i=0;i<size;i++){
			String savePath=mapPaths.get("reqResult"+i);//SDCard地址
			String[] strArr=savePath.split("/");
			String photoCode=strArr[strArr.length-1];//图片类型
			//根据不同的类型 ---加载不同路径的图片
			if(photoCode.equals(Constant.CONTAINER_BODY)){//集装箱箱体  照片
				loadPicDataAgain(savePath, gl_RM1I4Check_containerPic);
				loadPicDataAgain(savePath, gl_RM1I4CheckAlarm_containerPic);
			}else if(photoCode.equals(Constant.GOODSTACK_SITUATION)){//货物堆放情况  照片
				loadPicDataAgain(savePath, gl_RM1I4Check_goodsPic);
				loadPicDataAgain(savePath, gl_RM1I4CheckAlarm_goodsPic);
			}else if(photoCode.equals(Constant.CONTAINER_MARK)){// 集装箱标志    照片
				loadPicDataAgain(savePath, gl_RM1I4Check_containerInfoPic);
				loadPicDataAgain(savePath, gl_RM1I4CheckAlarm_containerInfoPic);
			}
		}
	}
	
	// 加载对应 文件夹下面的所有图片
	private void loadPicDataAgain(String picFileFolderPath, Gallery gallery) {
		ls = FileUtil.getFileList(picFileFolderPath);
		sa = new ImageAdapter(getContext(), ls);
		gallery.setAdapter(sa);
	}
	
	private void galleryClick(Gallery gallery){
		gallery.setOnItemClickListener(new MyGalleryOnItemClickListener());
	}
	
	/**
	 * 图片的缩放
	 * 
	 * @author ouyyt
	 * 
	 */
	private class MyGalleryOnItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			String picFilePath = (String) ls.get(position).get("picFilePath");
//			Intent intent = new Intent(getContext(), PicZoomActivity.class);
//			intent.putExtra("imagePath", picFilePath);
//			getContext().startActivity(intent);
			
			Intent intentOne = new Intent(getContext(),ToImageZoomActivity.class);
			intentOne.putExtra("imagePath", picFilePath);
			intentOne.putExtra("PATH_TYPE", Constant.LoadImage_Intenet);
			getContext().startActivity(intentOne);
			System.out.println("你点击了第" + position + "张的Gallery图片" + "\n" + "图片路径为：" + picFilePath);
		}
	}
	
	/**
	 * 请求获得本科室人员：选择上传修改人
	 * @author ouyyt
	 *
	 */
	private void reqOPNAME(){
		Thread thread=new Thread(new Runnable() {
			
			@Override
			public void run() {
				String reqResult = "";
				FrameDemoActivity.webservice.setMETHOD_NAME("GetOpIdListBySectionCode");
				FrameDemoActivity.webservice
						.setSOAP_ACTION("http://tempuri.org/IGetExamDataService/GetOpIdListBySectionCode");
				//测试数据  以后需删除
				String sectionCode = FrameDemoActivity.myApp.userInfo.getSectionCode();//科室号
				if(sectionCode == null || sectionCode.equals("")){
					sectionCode = "2233006";
				}
				if(FrameDemoActivity.webservice.connect(getContext(), new String[] {"SECTION_CODE" },
						new Object[] { sectionCode })) {
					reqResult = FrameDemoActivity.webservice.getResult().toString();
					System.out.println("上传信息审核 - 选择上传修改人：" + reqResult);
				}
				if(reqResult != null && !reqResult.equals("")){
					try {
						JSONArray arr = new JSONArray(reqResult);
						FrameDemoActivity.myApp.OP_NAME.clear();
						if (arr != null && arr.length() > 0) {
							for (int i = 0; i < arr.length(); i++) {
								JSONObject obj = arr.getJSONObject(i);
								FrameDemoActivity.myApp.OP_NAME.add(obj.getString("OP_NAME"));
								System.out.println("obj.getString(OP_NAME)"+obj.getString("OP_NAME"));
							}
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		});
		thread.setDaemon(true);//守护线程
		thread.start();
	}
	
	/**
	 * 更新审核单证
	 * @author ouyyt
	 *
	 */
	private void reqUpdateAudit(final String opId, final String sh_Op_Er, final String sh_Op_Time, final String entryId, final String opNote) {
		FrameDemoActivity.toolUtils.startUpdateProgressDialog();
		Thread thread = new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Message msg = new Message();
				FrameDemoActivity.webservice.setMETHOD_NAME("UpdateAudit");
				FrameDemoActivity.webservice.setSOAP_ACTION("http://tempuri.org/IGetExamDataService/UpdateAudit");
				Boolean bool = FrameDemoActivity.webservice.connect(getContext(),new String[] { "op_id", "sh_er", "sh_time", "entry_id"},
						new Object[] { opId, sh_Op_Er, sh_Op_Time, entryId });
				if (bool) {
					System.out.println("退回修改链接结果："+bool);
					System.out.println("转待处理链接结果："+bool);
					// 插入工作流程信息
					reqInsertWorkFlowInfo(entryId, opId, sh_Op_Time, sh_Op_Er, opNote);
					// 添加Log日志
					FrameDemoActivity.toolUtils.writeDataLog("审核—退回修改/转待处理成功。", "91", entryId);
					// 链接成功
					msg.what = Constant.WEBSERVICE_SUCCESS;
				} else {
					// 链接失败
					msg.what = Constant.WEBSERVICE_FAIL;
				}
				handler_RM1I4.sendMessage(msg);
		}});
		thread.setDaemon(true);
		thread.start();
	}
	
	/**
	 * 更新审核报警单证
	 * @author ouyyt
	 *
	 */
	private void reqUpdateAuditAlarm(final String entryId, final String opId, final String shAlarm, final String opTime, final String opEr, final String opNote) {
		FrameDemoActivity.toolUtils.startUpdateProgressDialog();
		
		Thread thread = new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Message msg = new Message();
				FrameDemoActivity.webservice.setMETHOD_NAME("UpdateAuditAlarm");
				FrameDemoActivity.webservice.setSOAP_ACTION("http://tempuri.org/IGetExamDataService/UpdateAuditAlarm");
				Boolean bool = FrameDemoActivity.webservice.connect(getContext(),new String[] { "entry_id", "op_id", "sh_alarm" },
						new Object[] { entryId, opId, shAlarm });
				if (bool) {
					System.out.println("报警终止链接结果："+bool);
					System.out.println("重上传链接结果："+bool);
					// 插入工作流程信息
					reqInsertWorkFlowInfo(entryId, opId, opTime, opEr, opNote);
					// 添加Log日志
					FrameDemoActivity.toolUtils.writeDataLog("审核报警—报警终止/重上传成功。", "91", entryId);
					// 链接成功
					msg.what = Constant.WEBSERVICE_SUCCESS;
				} else {
					// 链接失败
					msg.what = Constant.WEBSERVICE_FAIL;
					
				}
				handler_RM1I4.sendMessage(msg);
		}});
		thread.setDaemon(true);
		thread.start();
	}
	
	/**
	 * 更新审核标志
	 * 
	 * @author ouyyt
	 * 
	 */
	class MyAsynTaskUpdateAuditAlarmFlag extends AsyncTask<String, String, String> {
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			FrameDemoActivity.toolUtils.startUpdateProgressDialog();
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			// 更新单证
			FrameDemoActivity.webservice.setMETHOD_NAME("UpdateAuditAlarmFlag");
			FrameDemoActivity.webservice.setSOAP_ACTION("http://tempuri.org/IGetExamDataService/UpdateAuditAlarmFlag");
			if (FrameDemoActivity.webservice.connect(getContext(),new String[] { "entry_id", "sh_alarm" },
					new Object[] { params[0], params[1] })) {
				System.out.println("更新审核标志");
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			FrameDemoActivity.toolUtils.endProgressDialog();
		}
		 
	};
	
	/**
	 * 向服务器插入工作流程信息
	 * @param entry_id  报关单号
	 * @param op_id  操作ID
	 * @param op_time  审核时间
	 * @param op_er  审核人
	 * @param op_note 退回修改--退回人|审核意见、转待处理--审核意见、报警终止--单证终止理由、重上传--重上传人|重上传.理由
	 * 
	 * @author ouyyt
	 */
	private void reqInsertWorkFlowInfo(final String entryId, final String opId, final String opTime,
			final String opEr, final String opNote) {
		
		Thread t = new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				FrameDemoActivity.webservice.setMETHOD_NAME("InsertWorkFlowInfo");
				FrameDemoActivity.webservice.setSOAP_ACTION("http://tempuri.org/IGetExamDataService/InsertWorkFlowInfo");
				if (FrameDemoActivity.webservice.connect(getContext(),new String[] { "entry_id", "op_id", "op_time", "op_er", "op_note" },
						new Object[] { entryId, opId, opTime, opEr, opNote })) {
					System.out.println("插入工作流程信息成功");
				}
		}});
		t.setDaemon(true);
		t.start();
	}
	
	/**
	 * 审核和审核报警得页面调用--获取G_HEAD报关单信息
	 * @param manifestNumber
	 */
	private void reqCheckGetBGDHeadInfo(final String manifestNumber) {
		FrameDemoActivity.toolUtils.startProgressDialog();
		Thread thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Message msg = new Message();
				String reqResult = "";
				FrameDemoActivity.webservice.setMETHOD_NAME("GetBGDHeadInfo");
				FrameDemoActivity.webservice.setSOAP_ACTION("http://tempuri.org/IGetExamDataService/GetBGDHeadInfo");
				if (FrameDemoActivity.webservice.connect(getContext(),new String[] { "entry_id" },
						new Object[] { manifestNumber })) {
					reqResult = FrameDemoActivity.webservice.getResult().toString();
					System.out.println("获得报关单上传审核信息："+reqResult);
				}
				if (reqResult.equals("")) {
					msg.what = Constant.CHECK_FAIL;
				} else if (reqResult != null && !reqResult.equals("")) {
					gCheckHead = new G_HEAD();
					// 获得表头信息
					gCheckHead = FrameDemoActivity.gson.fromJson(reqResult, G_HEAD.class);
					msg.what = Constant.CHECK_SUCCESS;
					// 测试数据
					String entryId="6666666666";
					String gNo="0";
					new MyAsynTaskImageDownload().execute(entryId, gNo);
				}
				handler_RM1I4.sendMessage(msg);
			}
		});
		thread.setDaemon(true);
		thread.start();
	}
	
	private void reqCheckAlarmGetBGDHeadInfo(final String manifestNumber) {
		FrameDemoActivity.toolUtils.startProgressDialog();
		Thread thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Message msg = new Message();
				String reqResult = "";
				FrameDemoActivity.webservice.setMETHOD_NAME("GetBGDHeadInfo");
				FrameDemoActivity.webservice.setSOAP_ACTION("http://tempuri.org/IGetExamDataService/GetBGDHeadInfo");
				if (FrameDemoActivity.webservice.connect(getContext(),new String[] { "entry_id" },
						new Object[] { manifestNumber })) {
					reqResult = FrameDemoActivity.webservice.getResult().toString();
					System.out.println("获得报关单上传审核报警信息："+reqResult);
				}
				if (reqResult.equals("")) {
					msg.what = Constant.CHECKALARM_FAIL;
				} else if (reqResult != null && !reqResult.equals("")) {
					gCheckAlarmHead = new G_HEAD();
					// 获得表头信息
					gCheckAlarmHead = FrameDemoActivity.gson.fromJson(reqResult, G_HEAD.class);
					msg.what = Constant.CHECKALARM_SUCCESS;
					// 测试数据
					String entryId="6666666666";
					String gNo="0";
					new MyAsynTaskImageDownload().execute(entryId, gNo);
					
				}
				handler_RM1I4.sendMessage(msg);
			}
		});
		thread.setDaemon(true);
		thread.start();
	}
	
	// 审核页面控件赋值
	private void showCheckGHeadResult() {
		if(gCheckHead != null && !gCheckHead.equals("")) {
			// 当操作ID为52时显示发还重审理由
			if(gCheckHead.getOP_ID().equals("52")){
				((LinearLayout)ll_rightMenu1Info4_second.findViewById(R.id.lr_check_checkAgainReason)).setVisibility(VISIBLE);
				((TextView) ll_rightMenu1Info4_second.findViewById(R.id.tv_check_text1)).setText(gCheckHead.getENTRY_ID());
				((TextView) ll_rightMenu1Info4_second.findViewById(R.id.tv_check_text2)).setText(gCheckHead.getEXAM_PROC_CODE());
				((TextView) ll_rightMenu1Info4_second.findViewById(R.id.tv_check_text3)).setText(gCheckHead.getSTAR_NUM()+"");
				((TextView) ll_rightMenu1Info4_second.findViewById(R.id.tv_check_text4)).setText(gCheckHead.getAuditReason());
				((TextView) ll_rightMenu1Info4_second.findViewById(R.id.tv_check_text5)).setText(gCheckHead.getEXAM_PROC_IDEA());
			} else {
				((LinearLayout)ll_rightMenu1Info4_second.findViewById(R.id.lr_check_checkAgainReason)).setVisibility(GONE);
				((TextView) ll_rightMenu1Info4_second.findViewById(R.id.tv_check_text1)).setText(gCheckHead.getENTRY_ID());
				((TextView) ll_rightMenu1Info4_second.findViewById(R.id.tv_check_text2)).setText(gCheckHead.getEXAM_PROC_CODE());
				((TextView) ll_rightMenu1Info4_second.findViewById(R.id.tv_check_text3)).setText(gCheckHead.getSTAR_NUM()+"");
				((TextView) ll_rightMenu1Info4_second.findViewById(R.id.tv_check_text5)).setText(gCheckHead.getEXAM_PROC_IDEA());
			}
		}
	}
	
	// 审核报警页面控件赋值
	private void showCheckAlarmGHeadResult() {
		if(gCheckAlarmHead != null && !gCheckAlarmHead.equals("")) {
			// 当审核报警标志为1时显示报警理由
			if(gCheckAlarmHead.getSH_ALARM().equals("1")){
				((LinearLayout)ll_rightMenu1Info4_third.findViewById(R.id.lr_checkAlarm_alarmReason)).setVisibility(VISIBLE);
				((TextView) ll_rightMenu1Info4_third.findViewById(R.id.tv_checkAlarm_text1)).setText(gCheckAlarmHead.getENTRY_ID());
				((TextView) ll_rightMenu1Info4_third.findViewById(R.id.tv_checkAlarm_text2)).setText(gCheckAlarmHead.getEXAM_PROC_CODE());
				((TextView) ll_rightMenu1Info4_third.findViewById(R.id.tv_checkAlarm_text3)).setText(gCheckAlarmHead.getSH_ALARM());
				((TextView) ll_rightMenu1Info4_third.findViewById(R.id.tv_checkAlarm_text5)).setText(gCheckAlarmHead.getAlarmReason());
			} else {
				((LinearLayout)ll_rightMenu1Info4_third.findViewById(R.id.lr_checkAlarm_alarmReason)).setVisibility(GONE);
				((TextView) ll_rightMenu1Info4_third.findViewById(R.id.tv_checkAlarm_text1)).setText(gCheckAlarmHead.getENTRY_ID());
				((TextView) ll_rightMenu1Info4_third.findViewById(R.id.tv_checkAlarm_text2)).setText(gCheckAlarmHead.getEXAM_PROC_CODE());
				((TextView) ll_rightMenu1Info4_third.findViewById(R.id.tv_checkAlarm_text3)).setText(gCheckAlarmHead.getSH_ALARM());
			}
		}
	}
	
}