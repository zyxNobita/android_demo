package com.bai.demo.frame;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bai.demo.R;
import com.bai.demo.adapter.Menu1Info2LeftListViewAdapter;
import com.bai.demo.adapter.Menu1Info2RightListViewAdapter;
import com.bai.demo.bean.ENTRY_CERTIFICATE;
import com.bai.demo.bean.ENTRY_CONTAINER;
import com.bai.demo.bean.ENTRY_HEAD;
import com.bai.demo.bean.ENTRY_LIST;
import com.bai.demo.entity.Constant;
import com.bai.demo.main.BarCodeScannerActivity;
import com.bai.demo.main.FrameDemoActivity;
import com.bai.demo.utils.MyDialog;
import com.bai.demo.utils.MyListView;
import com.google.gson.reflect.TypeToken;

/**
 * 查验图像留存
 * 
 * @author Administrator
 * 
 */
@SuppressLint({ "HandlerLeak", "ParserError" })
public class Menu1Info2Activity extends RightWindowBase {
    //主要布局|商品项左布局|商品项右布局|商品项布局|Table列表数据布局|单个报关单证信息
	private LinearLayout layout, ll_RM1I2_lv_main_left, ll_RM1I2_lv_main_right,
			lv_RM1I2_dialogView, ll_RM1I2_tableHead, View_takeTableBodyPhotos;
	private MyListView mlv_RM1I2_leftLV, mlv_RM1I2_rightLV;//商品项数据的ListView
	private List<ENTRY_LIST> entrysList=new ArrayList<ENTRY_LIST>();
	//扫描按钮|查询|商品项|商品项的取消|上传
	private Button btn_RM1I2_scan, btn_RM1I2_makeSure, btn_RM1I2_detail,
			btn_RM1I2_dialogCancel, btn_RM1I2_upload;
	//商品项Dialog的左右布局
	private View leftView, rightView;
	private LayoutInflater lInflater;
	//经营单位|收货单位|申报单位
	private TextView tv_RM1I2_TBtext8, tv_RM1I2_TBtext12, tv_RM1I2_TBtext15;
	private ENTRY_HEAD entryHead;//表头信息
	private ENTRY_CERTIFICATE entryCertificate;//附单据信息
	private ENTRY_CONTAINER entryContainer;//集装箱信息
	public static Handler handler_RM1I2;
	public static EditText et_RM1I2_barCode;//条码扫描
	
	public Menu1Info2Activity(Context context) {
		super(context);
		setupViews();
	}

	public Menu1Info2Activity(Context context, AttributeSet attrs) {
		super(context, attrs);
		setupViews();
	}

	private void setupViews() {
		initView();
		addView(layout);
		doExecute();
	}

	private void initView() {
		lInflater = LayoutInflater.from(getContext());
		layout = (LinearLayout) LinearLayout.inflate(getContext(),
				R.layout.right_menu1info2, null);

		btn_RM1I2_scan = (Button) layout.findViewById(R.id.btn_RM1I2_scan);
		btn_RM1I2_makeSure = (Button) layout
				.findViewById(R.id.btn_RM1I2_makeSure);
		btn_RM1I2_detail = (Button) layout.findViewById(R.id.btn_RM1I2_detail);
		btn_RM1I2_upload = (Button) layout.findViewById(R.id.btn_RM1I2_upload);
		et_RM1I2_barCode = (EditText) layout
				.findViewById(R.id.et_RM1I2_barCode);

		ll_RM1I2_tableHead = (LinearLayout) layout
				.findViewById(R.id.ll_RM1I2_tableHead);
		tv_RM1I2_TBtext8 = (TextView) layout.findViewById(R.id.tv_RM1I2_text8);
		tv_RM1I2_TBtext12 = (TextView) layout
				.findViewById(R.id.tv_RM1I2_text12);
		tv_RM1I2_TBtext15 = (TextView) layout
				.findViewById(R.id.tv_RM1I2_text15);

		View_takeTableBodyPhotos = (LinearLayout) layout
				.findViewById(R.id.View_takeTableBodyPhotos);
		View_takeTableBodyPhotos.setVisibility(View.GONE);

	}

	private void addListView(View leftView, View rightView) {

		mlv_RM1I2_leftLV.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				mlv_RM1I2_rightLV.onTouchEvent(event);
				return false;
			}
		});

		mlv_RM1I2_rightLV.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				mlv_RM1I2_leftLV.dispatchTouchEvent(event);
				return false;
			}
		});
		ll_RM1I2_lv_main_left.addView(leftView);
		ll_RM1I2_lv_main_right.addView(rightView);
	}

	private void initDialog() {
		lv_RM1I2_dialogView = (LinearLayout) lInflater.inflate(
				R.layout.right_menu1info2_listview, null);
		ll_RM1I2_lv_main_left = (LinearLayout) lv_RM1I2_dialogView
				.findViewById(R.id.ll_RM1I2_lv_main_left);
		ll_RM1I2_lv_main_right = (LinearLayout) lv_RM1I2_dialogView
				.findViewById(R.id.ll_RM1I2_lv_main_right);
		leftView = lInflater.inflate(
				R.layout.right_menu1info2_listview_left_layout, null);
		rightView = lInflater.inflate(
				R.layout.right_menu1info2_listview_right_layout, null);

		mlv_RM1I2_rightLV = (MyListView) rightView
				.findViewById(R.id.lv_RM1I2_right_layout_listView1);
		mlv_RM1I2_leftLV = (MyListView) leftView
				.findViewById(R.id.lv_RM1I2_left_layout_listView1);
		mlv_RM1I2_rightLV.setAdapter(new Menu1Info2RightListViewAdapter(
				getContext(), entrysList));
		mlv_RM1I2_leftLV.setAdapter(new Menu1Info2LeftListViewAdapter(
				getContext(), entrysList));
		showEntryBodyResult();
		addListView(leftView, rightView);
	}

	private void doExecute() {
		btn_RM1I2_scan.setOnClickListener(new BTNDetailClickListener());
		btn_RM1I2_makeSure.setOnClickListener(new BTNDetailClickListener());
		btn_RM1I2_detail.setOnClickListener(new BTNDetailClickListener());
		btn_RM1I2_upload.setOnClickListener(new BTNDetailClickListener());
		tv_RM1I2_TBtext8.setOnClickListener(new BTNDetailClickListener());
		tv_RM1I2_TBtext12.setOnClickListener(new BTNDetailClickListener());
		tv_RM1I2_TBtext15.setOnClickListener(new BTNDetailClickListener());

		handler_RM1I2 = new Handler() {
			public void dispatchMessage(android.os.Message msg) {
				int result = msg.what;
				switch (result) {
				case Constant.REQ_SCAN_FIRST://扫描条码
					String barCodeScan = FrameDemoActivity.barCode;
					if (barCodeScan != null && !barCodeScan.equals("")) {
						et_RM1I2_barCode.setText(barCodeScan);
					}
					break;
				case Constant.RINGHT_GROUP1_MENU2://Table列表|单个报关单信息的切换
					ll_RM1I2_tableHead.setVisibility(View.GONE);
					View_takeTableBodyPhotos.setVisibility(View.VISIBLE);
					break;	
				}

			};
		};
	}

	private class BTNDetailClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			int btnId = v.getId();
			et_RM1I2_barCode.setText("010120111011982012");//测试数据
			String barCodeNum = et_RM1I2_barCode.getText().toString().trim();
			
			switch (btnId) {
			case R.id.btn_RM1I2_scan://扫描报关单号
				Intent mIntent = new Intent(getContext(),
						BarCodeScannerActivity.class);
				((Activity) getContext()).startActivityForResult(mIntent,
						Constant.REQ_SCAN_FIRST);
				break;

			case R.id.btn_RM1I2_makeSure://查询
				// 查询 单个商品项
				if (barCodeNum != null && !barCodeNum.equals("")) {
					new MyAsynTask().execute(barCodeNum);
					ll_RM1I2_tableHead.setVisibility(View.VISIBLE);
					View_takeTableBodyPhotos.setVisibility(View.GONE);
				}else{
					Toast.makeText(getContext(), "报关单号不能为空", 500).show();
				}
				break;

			case R.id.btn_RM1I2_detail://商品项
				if (barCodeNum != null && !barCodeNum.equals("")) {
					new MyAsynTaskEntrys().execute(barCodeNum);
						initDialog();
						btn_RM1I2_dialogCancel = (Button) lv_RM1I2_dialogView
								.findViewById(R.id.btn_RM1I2_dialogCancel);
						MyDialog.showDialog(getContext(), lv_RM1I2_dialogView,
								getContext().getString(R.string.dialog_title));
						btn_RM1I2_dialogCancel
								.setOnClickListener(new OnClickListener() {
									public void onClick(View v) {
										MyDialog.dismissDialog();
									}
								});
				
				}else{
					FrameDemoActivity.toolUtils.promptMessage("报关单号不能为空。");
				}
				break;

			case R.id.btn_RM1I2_upload://上传
				if (barCodeNum != null && !barCodeNum.equals("")) {
					Message msg=new Message();
					msg.what=Constant.RINGHT_GROUP1_MENU3;//判断跳转菜单的位置
					msg.getData().putString("entryId", barCodeNum);//报关单号
					FrameDemoActivity.handler.sendMessage(msg);
				}
				break;

			case R.id.tv_RM1I2_text8://跳转--->企业信息查询
				FrameDemoActivity.handler
						.sendEmptyMessage(Constant.RINGHT_GROUP2_MENU1);
				break;

			case R.id.tv_RM1I2_text12://跳转--->企业信息查询
				FrameDemoActivity.handler
						.sendEmptyMessage(Constant.RINGHT_GROUP2_MENU1);
				break;

			case R.id.tv_RM1I2_text15://跳转--->企业信息查询
				FrameDemoActivity.handler
						.sendEmptyMessage(Constant.RINGHT_GROUP2_MENU1);
				break;

			}

		}
	};

	@Override
	public void dosomething() {

	}

	@Override
	public void dosomething2() {

	}
	//表头信息的获取
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
			FrameDemoActivity.webservice.setMETHOD_NAME("CheckExistBGD");
			FrameDemoActivity.webservice
					.setSOAP_ACTION("http://tempuri.org/IGetExamDataService/CheckExistBGD");
			if (FrameDemoActivity.webservice.connect(getContext(),new String[] { "entry_id" },
					new Object[] { params[0] })) {
				String strResult = FrameDemoActivity.webservice.getResult().toString();
				System.out.println("待查验列表信息：" + strResult);
				if (strResult != null && !strResult.equals("")
						&& strResult.equals("True")) {
					FrameDemoActivity.webservice.setMETHOD_NAME("GetBGDBTInfo");
					FrameDemoActivity.webservice
							.setSOAP_ACTION("http://tempuri.org/IGetExamDataService/GetBGDBTInfo");
					if (FrameDemoActivity.webservice.connect(getContext(),new String[] { "entry_id" },
							new Object[] { params[0] })) {
						reqResult = FrameDemoActivity.webservice.getResult().toString();
						//写服务器的Log日志
						if(reqResult != null && !reqResult.equals("")){
							FrameDemoActivity.toolUtils.writeDataLog("查验单证查询成功。", "90", params[0]);
						}
					}
				}
			}
			return reqResult;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if (result.equals("")) {
				FrameDemoActivity.toolUtils.endProgressDialog();
				FrameDemoActivity.toolUtils.promptMessage("对不起，不存在此报关单。");
			} else if (result != null && !result.equals("")) {
				entryHead = new ENTRY_HEAD();
				// 获得表头信息
				entryHead = FrameDemoActivity.gson.fromJson(result,
						ENTRY_HEAD.class);
				new MyAsynTaskTwo().execute(entryHead.getENTRY_ID());
				new MyAsynTaskThere().execute(entryHead.getENTRY_ID());
				showEntryHeadResult();
			}
		}

	};
	//表头信息的获取---1
	class MyAsynTaskTwo extends AsyncTask<String, String, String> {
	
		@Override
		protected String doInBackground(String... params) {

			// 获取派单
			String reqResult = "";
			FrameDemoActivity.webservice.setMETHOD_NAME("GetJZXInfo");
			FrameDemoActivity.webservice
					.setSOAP_ACTION("http://tempuri.org/IGetExamDataService/GetJZXInfo");
			if (FrameDemoActivity.webservice.connect(getContext(),new String[] { "entry_id" },
					new Object[] { params[0] })) {
					reqResult = FrameDemoActivity.webservice.getResult().toString();
					System.out.println("获得报关单表头--查询该报关单的集装箱信息"+reqResult);
			}
			return reqResult;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			FrameDemoActivity.toolUtils.endProgressDialog();
			if (result.equals("")) {
				FrameDemoActivity.toolUtils.promptMessage("报关单不存在。");
			} else if (result != null && !result.equals("")) {
				entryContainer = new ENTRY_CONTAINER();
				// 获得表头信息
				entryContainer = FrameDemoActivity.gson.fromJson(result,
						ENTRY_CONTAINER.class);
				showEntryHeadResult();
			}
		}

	};
	//表头信息的获取---2
	class MyAsynTaskThere extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... params) {

			// 获取派单
			String reqResult = "";
			FrameDemoActivity.webservice.setMETHOD_NAME("GetSFDJInfo");
			FrameDemoActivity.webservice
					.setSOAP_ACTION("http://tempuri.org/IGetExamDataService/GetSFDJInfo");
			if (FrameDemoActivity.webservice.connect(getContext(),new String[] { "entry_id" },
					new Object[] { params[0] })) {
				reqResult = FrameDemoActivity.webservice.getResult().toString();
				System.out.println("获得报关单表头--查询该报关单的随附单据信息：" + reqResult);
			}
			return reqResult;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			FrameDemoActivity.toolUtils.endProgressDialog();
			if (result.equals("")) {
				FrameDemoActivity.toolUtils.promptMessage("报关单不存在。");
			} else if (result != null && !result.equals("")) {
				entryCertificate = new ENTRY_CERTIFICATE();
				// 获得表头信息
				entryCertificate = FrameDemoActivity.gson.fromJson(result,
						ENTRY_CERTIFICATE.class);
				showEntryHeadResult();
			}
		}

	};
	
	/**
	 * 加载表体数据
	 * @author zhangyx
	 *
	 */
	class MyAsynTaskEntrys extends AsyncTask<String, String, String> {
	
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			FrameDemoActivity.toolUtils.startProgressDialog();
		}

		@Override
		protected String doInBackground(String... params) {
			// 获取派单
			String reqResult = "";
			FrameDemoActivity.webservice.setMETHOD_NAME("GetBGDBT2Info");
			FrameDemoActivity.webservice
					.setSOAP_ACTION("http://tempuri.org/IGetExamDataService/GetBGDBT2Info");
			if (FrameDemoActivity.webservice.connect(getContext(),new String[] { "entry_id" },
					new Object[] { params[0] })) {
				reqResult = FrameDemoActivity.webservice.getResult().toString();
				System.out.println("待查验列表信息：" + reqResult);
				//写服务器的Log日志
				if(reqResult != null && !reqResult.equals("")){
					FrameDemoActivity.toolUtils.writeDataLog("查验单证查询成功。", "90", "");
				}
			}
			return reqResult;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			FrameDemoActivity.toolUtils.endProgressDialog();
			if (result.equals("")) {
				FrameDemoActivity.toolUtils.promptMessage("报关单不存在。");
			} else if (result != null && !result.equals("")) {
				entrysList=FrameDemoActivity.gson.fromJson(result, new TypeToken<List<ENTRY_LIST>>() {}.getType());
				//entrysList.addAll((List)FrameDemoActivity.gson.fromJson(result, new TypeToken<List<ENTRY_LIST>>() {}.getType()));
				showEntryBodyResult();
			}
		}

	};

	/***
	 * 商品项数据
	 */
	private void showEntryBodyResult(){
		mlv_RM1I2_rightLV.setAdapter(new Menu1Info2RightListViewAdapter(
				getContext(), entrysList));
		mlv_RM1I2_leftLV.setAdapter(new Menu1Info2LeftListViewAdapter(
				getContext(), entrysList));
	}
	
	/***
	 * Table列表数据的赋值
	 */
	@SuppressLint("ParserError")
	private void showEntryHeadResult() {
		if (entryHead != null && !entryHead.equals("")) {
			((TextView) layout.findViewById(R.id.tv_RM1I2_text1))
					.setText(entryHead.getENTRY_ID());// ENTRY_ID;// 报关单号
			((TextView) layout.findViewById(R.id.tv_RM1I2_text2))
					.setText(entryHead.getENTRY_ID());// ENTRY_ID;// 报关单号
			((TextView) layout.findViewById(R.id.tv_RM1I2_text3))
					.setText(entryHead.getPRE_ENTRY_ID());// PRE_ENTRY_ID;//
															// 预录入编号
			((TextView) layout.findViewById(R.id.tv_RM1I2_text4))
					.setText(entryHead.getI_E_PORT() + "|"
							+ entryHead.getI_E_PORT_NAME());// I_E_PORT_NAME;//
															// 进出口岸名称
															// I_E_PORT;//
															// 进出口岸编码
			((TextView) layout.findViewById(R.id.tv_RM1I2_text5))
					.setText(entryHead.getMANUAL_NO());// MANUAL_NO;// 备案号
			((TextView) layout.findViewById(R.id.tv_RM1I2_text6))
					.setText(entryHead.getI_E_DATE()+"");// I_E_DATE;// 进（出）口日期
			((TextView) layout.findViewById(R.id.tv_RM1I2_text7))
					.setText(entryHead.getD_DATE()+"");// D_DATE;// 申报日期
			((TextView) layout.findViewById(R.id.tv_RM1I2_text9))
					.setText(entryHead.getTRAF_MODE());// TRAF_MODE;// 运输方式
			((TextView) layout.findViewById(R.id.tv_RM1I2_text10))
					.setText(entryHead.getTRAF_NAME());// TRAF_NAME;// 运输工具名称
			((TextView) layout.findViewById(R.id.tv_RM1I2_text11))
					.setText(entryHead.getBILL_NO());// BILL_NO;// 提运单号
			((TextView) layout.findViewById(R.id.tv_RM1I2_text13))
					.setText(entryHead.getTRADE_MODE());// TRADE_MODE;// 贸易方式
			((TextView) layout.findViewById(R.id.tv_RM1I2_text14))
					.setText(entryHead.getCUT_MODE());
			((TextView) layout.findViewById(R.id.tv_RM1I2_text16))
					.setText(entryHead.getLICENSE_NO());
			((TextView) layout.findViewById(R.id.tv_RM1I2_text17))
					.setText(entryHead.getTRADE_COUNTRY() + "-"
							+ entryHead.getCOUN_C_NAME());
			((TextView) layout.findViewById(R.id.tv_RM1I2_text18))
					.setText(entryHead.getPORT_C_NAME());
			((TextView) layout.findViewById(R.id.tv_RM1I2_text19))
					.setText(entryHead.getDISTRICT_NAME());
			((TextView) layout.findViewById(R.id.tv_RM1I2_text20))
					.setText(entryHead.getAPPR_NO());
			((TextView) layout.findViewById(R.id.tv_RM1I2_text21))
					.setText(entryHead.getTRANS_MODE());
			((TextView) layout.findViewById(R.id.tv_RM1I2_text22))
					.setText(entryHead.getFEE_MARK());//
			((TextView) layout.findViewById(R.id.tv_RM1I2_text23))
					.setText(entryHead.getINSUR_MARK());
			((TextView) layout.findViewById(R.id.tv_RM1I2_text24))
					.setText(entryHead.getINSUR_RATE());//
			((TextView) layout.findViewById(R.id.tv_RM1I2_text25))
					.setText(entryHead.getCONTR_NO());
			((TextView) layout.findViewById(R.id.tv_RM1I2_text26))
					.setText(entryHead.getPACK_NO() + "");//
			((TextView) layout.findViewById(R.id.tv_RM1I2_text27))
					.setText(entryHead.getWRAP_TYPE());// WRAP_TYPE;// 包装类型
			((TextView) layout.findViewById(R.id.tv_RM1I2_text28))
					.setText(entryHead.getGROSS_WT() + "");// GROSS_WT;// 毛重
			((TextView) layout.findViewById(R.id.tv_RM1I2_text29))
					.setText(entryHead.getNET_WT() + "");// NET_WT; 净重
			
			((TextView) layout.findViewById(R.id.tv_RM1I2_text32))
					.setText(entryHead.getBONDED_NO());
			((TextView) layout.findViewById(R.id.tv_RM1I2_text33))
					.setText(entryHead.getRELATIVE_ID());
			((TextView) layout.findViewById(R.id.tv_RM1I2_text34))
					.setText(entryHead.getRELATIVE_MANUAL_NO());
			((TextView) layout.findViewById(R.id.tv_RM1I2_text35))
					.setText(entryHead.getENTRY_TYPE());
			((TextView) layout.findViewById(R.id.tv_RM1I2_text36))
					.setText(entryHead.getNOTE_S());
			// 点击的 tv_RM1I2_text8 12 15
			// tv_RM1I2_TBtext12, tv_RM1I2_TBtext17, tv_RM1I2_TBtext19;
			tv_RM1I2_TBtext8.setText(entryHead.getTRADE_NAME());
			tv_RM1I2_TBtext12.setText(entryHead.getOWNER_NAME());
			tv_RM1I2_TBtext15.setText(entryHead.getAGENT_NAME());
		}
		//随附单据信息
		if(entryCertificate!=null && !entryCertificate.equals("")){
			((TextView) layout.findViewById(R.id.tv_RM1I2_text31))
					.setText(entryCertificate.getDOCU_CODE());
		}
		if(entryContainer!=null && !entryContainer.equals("")){
			((TextView) layout.findViewById(R.id.tv_RM1I2_text30))
			.setText(entryContainer.getCONTAINER_ID());// 集装箱号
		}
	}
}
