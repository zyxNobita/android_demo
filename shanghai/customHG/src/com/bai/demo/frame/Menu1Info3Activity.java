package com.bai.demo.frame;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bai.demo.R;
import com.bai.demo.adapter.ImageAdapter;
import com.bai.demo.adapter.Menu1Info3LeftListViewAdapter;
import com.bai.demo.adapter.Menu1Info3RightListViewAdapter;
import com.bai.demo.bean.ENTRY_HEAD;
import com.bai.demo.bean.ENTRY_LIST;
import com.bai.demo.bean.EntryList;
import com.bai.demo.bean.G_HEAD;
import com.bai.demo.bean.G_WORKFLOW;
import com.bai.demo.bean.PHOTO_LIST;
import com.bai.demo.bean.PhotoList;
import com.bai.demo.entity.Constant;
import com.bai.demo.main.BarCodeScannerActivity;
import com.bai.demo.main.FrameDemoActivity;
import com.bai.demo.main.PicZoomActivity;
import com.bai.demo.onlyTest.TestAddData;
import com.bai.demo.utils.Base64;
import com.bai.demo.utils.FileUtil;
import com.bai.demo.utils.Image;
import com.bai.demo.utils.MyDialog;
import com.bai.demo.utils.MyListView;
import com.bai.demo.zoomImage.ToImageZoomActivity;
import com.google.gson.reflect.TypeToken;
/**
 * 图像单证关联(查验图像上传)：
 * 
 * @author zhangyx
 */

@SuppressLint({ "HandlerLeak", "ParserError", "ParserError", "ParserError" })
public class Menu1Info3Activity extends RightWindowBase {
    			//主布局|表体ListView1|表体ListView2
	private LinearLayout layout, ll_RM1I3_lv_main_left, ll_RM1I3_lv_main_right,
			//|表体Dialog列表布局|表头布局|
			ll_RM1I3_listViewDialog, ll_RM1I3_tableHead,
			//表体上传页面布局|表头集装箱箱体图片布局|表头货物堆放情况图片布局
			View_takeTableBodyPhotos, ll_RM1I3_second_1, ll_RM1I3_second_2,
			//表头集装箱标志图片布局|
			ll_RM1I3_second_3,ll_first, ll_second, ll_third,
			ll_forth, ll_fifth, ll_sixth, ll_seventh;
	private RelativeLayout layout_RM1I3Second_show_1,
			layout_RM1I3Second_show_2, layout_RM1I3Second_show_3,
			layout_uploadPicSecond_show_1, layout_uploadPicSecond_show_2,
			layout_uploadPicSecond_show_3, layout_uploadPicSecond_show_4,
			layout_uploadPicSecond_show_5, layout_uploadPicSecond_show_6,
			layout_uploadVideoSecond_show_7;
	//表体的
	private MyListView mlv_RM1I3_leftLV, mlv_RM1I3_rightLV;
	private Menu1Info3LeftListViewAdapter M1I3LeftAdapter;
	private Menu1Info3RightListViewAdapter M1I3RightAdapter;
	private List<ENTRY_LIST> entrysList = new ArrayList<ENTRY_LIST>();
	
	public static EditText et_RM1I3_barCode;
	private Button btn_RM1I3_scan, btn_RM1I3_makeSure, btn_RM1I3_detail,
			btn_RM1I3Second_show_1, btn_RM1I3Second_show_2,
			btn_RM1I3Second_show_3, btn_RM1I3_uploadPicSecond_show_1,
			btn_RM1I3_uploadPicSecond_show_2, btn_RM1I3_uploadPicSecond_show_3,
			btn_RM1I3_uploadPicSecond_show_4, btn_RM1I3_uploadPicSecond_show_5,
			btn_RM1I3_uploadPicSecond_show_6,
			btn_RM1I3_uploadVideoSecond_show_7,
			btn_RM1I3P_back,//
			btn_RM1I3P_save,// 表体的上传按钮
			btn_RM1I3_HsaveImage, btn_RM1I3_Hupload, btn_RM1I3_HCupload,
			btn_RM1I3_Hback;// 表头的上传按钮

	private LayoutInflater lInflater;

	private ImageButton IB_RM1I3_takeCameraOne, IB_RM1I3_takeCameraTwo,
			IB_RM1I3_takeCameraThree, IB_RM1I3_uploadPic_takeCameraOne,
			IB_RM1I3_uploadPic_takeCameraTwo,
			IB_RM1I3_uploadPic_takeCameraThree,
			IB_RM1I3_uploadPic_takeCameraFour,
			IB_RM1I3_uploadPic_takeCameraFive,
			IB_RM1I3_uploadPic_takeCameraSix, IB_uploadVideo_takeVideo;

	private Gallery gl_RM1I3_containerPic, gl_RM1I3_goodsPic,
			gl_RM1I3_containerInfoPic, gl_RM1I3_goodsPackagePhoto,
			gl_RM1I3_goodsPhoto, gl_RM1I3_containerInnerPhoto,
			gl_RM1I3_goodsSpecModelPhoto, gl_RM1I3_anotherSpecialRequestPhoto,
			gl_RM1I3_sceneSrcPhoto;

	private List<Map<String, Object>> ls;//返回图片的路径集合
	private ImageAdapter sa;//显示图片的Adapter
	private String entryNumber;//报关单号
	private String projectNum;//项号--表体
	public static Handler handler_RM1I3;
	private Intent camera = null;
	private ENTRY_HEAD entryHead;// 表头信息
	private ENTRY_LIST entryList;// 表体信息
	private G_HEAD gHead;
	private G_WORKFLOW gWorkFlow;
	private String doType="";//判断操作的是  表头|表体     HEAD|BODY
	private EditText et_uploadPic_remark;//表体备注
	
	public Menu1Info3Activity(Context context) {
		super(context);
		setupViews();
	}

	public Menu1Info3Activity(Context context, AttributeSet attrs) {
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
				R.layout.right_menu1info3, null);

		et_RM1I3_barCode = (EditText) layout.findViewById(R.id.et_RM1I3_barCode);
		btn_RM1I3_scan = (Button) layout.findViewById(R.id.btn_RM1I3_scan);//扫描
		btn_RM1I3_makeSure = (Button) layout.findViewById(R.id.btn_RM1I3_makeSure);//查询
		btn_RM1I3_detail = (Button) layout.findViewById(R.id.btn_RM1I3_detail);//商品项
		ll_RM1I3_tableHead = (LinearLayout) layout.findViewById(R.id.ll_RM1I3_tableHead);
		initTableHeadBTN();// 上传表头页面的控件初始化

		// 上传 表体页面的控件初始化
		View_takeTableBodyPhotos = (LinearLayout) layout.findViewById(R.id.View_takeTableBodyPhotos);
		View_takeTableBodyPhotos.setVisibility(View.GONE);
		et_uploadPic_remark=(EditText) layout.findViewById(R.id.et_uploadPic_remark);
		btn_RM1I3_uploadPicSecond_show_1 = (Button) layout.findViewById(R.id.btn_uploadPicSecond_show_1);
		btn_RM1I3_uploadPicSecond_show_2 = (Button) layout.findViewById(R.id.btn_uploadPicSecond_show_2);
		btn_RM1I3_uploadPicSecond_show_3 = (Button) layout.findViewById(R.id.btn_uploadPicSecond_show_3);
		btn_RM1I3_uploadPicSecond_show_4 = (Button) layout.findViewById(R.id.btn_uploadPicSecond_show_4);
		btn_RM1I3_uploadPicSecond_show_5 = (Button) layout.findViewById(R.id.btn_uploadPicSecond_show_5);
		btn_RM1I3_uploadPicSecond_show_6 = (Button) layout.findViewById(R.id.btn_uploadPicSecond_show_6);
		btn_RM1I3_uploadVideoSecond_show_7 = (Button) layout.findViewById(R.id.btn_uploadVideoSecond_show_7);
		btn_RM1I3P_save = (Button) layout.findViewById(R.id.btn_RM1I3P_save);
		btn_RM1I3P_back = (Button) layout.findViewById(R.id.btn_RM1I3P_back);

		IB_RM1I3_uploadPic_takeCameraOne = (ImageButton) layout.findViewById(R.id.IB_uploadPic_takeCameraOne);
		IB_RM1I3_uploadPic_takeCameraTwo = (ImageButton) layout.findViewById(R.id.IB_uploadPic_takeCameraTwo);
		IB_RM1I3_uploadPic_takeCameraThree = (ImageButton) layout.findViewById(R.id.IB_uploadPic_takeCameraThree);
		IB_RM1I3_uploadPic_takeCameraFour = (ImageButton) layout.findViewById(R.id.IB_uploadPic_takeCameraFour);
		IB_RM1I3_uploadPic_takeCameraFive = (ImageButton) layout.findViewById(R.id.IB_uploadPic_takeCameraFive);
		IB_RM1I3_uploadPic_takeCameraSix = (ImageButton) layout.findViewById(R.id.IB_uploadPic_takeCameraSix);
		IB_uploadVideo_takeVideo = (ImageButton) layout.findViewById(R.id.IB_uploadVideo_takeVideo);

		gl_RM1I3_goodsPackagePhoto = (Gallery) layout.findViewById(R.id.gl_RM1I3_goodsPackagePhoto);
		gl_RM1I3_goodsPhoto = (Gallery) layout.findViewById(R.id.gl_RM1I3_goodsPhoto);
		gl_RM1I3_containerInnerPhoto = (Gallery) layout.findViewById(R.id.gl_RM1I3_containerInnerPhoto);
		gl_RM1I3_goodsSpecModelPhoto = (Gallery) layout.findViewById(R.id.gl_RM1I3_goodsSpecModelPhoto);
		gl_RM1I3_anotherSpecialRequestPhoto = (Gallery) layout.findViewById(R.id.gl_RM1I3_anotherSpecialRequestPhoto);
		gl_RM1I3_sceneSrcPhoto = (Gallery) layout.findViewById(R.id.gl_RM1I3_sceneSrcPhoto);
		
		//添加事件
		galleryClick(gl_RM1I3_containerPic);
		galleryClick(gl_RM1I3_goodsPic);
		galleryClick(gl_RM1I3_containerInfoPic);
		galleryClick(gl_RM1I3_goodsPackagePhoto);
		galleryClick(gl_RM1I3_goodsPhoto);
		galleryClick(gl_RM1I3_containerInnerPhoto);
		galleryClick(gl_RM1I3_goodsSpecModelPhoto);
		galleryClick(gl_RM1I3_anotherSpecialRequestPhoto);
		galleryClick(gl_RM1I3_sceneSrcPhoto);
	}

	// 表头上传页面的 控件初始化
	private void initTableHeadBTN() {
		btn_RM1I3Second_show_1 = (Button) layout.findViewById(R.id.btn_RM1I3Second_show_1);
		btn_RM1I3Second_show_2 = (Button) layout.findViewById(R.id.btn_RM1I3Second_show_2);
		btn_RM1I3Second_show_3 = (Button) layout.findViewById(R.id.btn_RM1I3Second_show_3);

		IB_RM1I3_takeCameraOne = (ImageButton) layout.findViewById(R.id.IB_RM1I3_takeCameraOne);
		IB_RM1I3_takeCameraTwo = (ImageButton) layout.findViewById(R.id.IB_RM1I3_takeCameraTwo);
		IB_RM1I3_takeCameraThree = (ImageButton) layout.findViewById(R.id.IB_RM1I3_takeCameraThree);

		gl_RM1I3_containerPic = (Gallery) layout.findViewById(R.id.gl_RM1I3_containerPic);
		gl_RM1I3_goodsPic = (Gallery) layout.findViewById(R.id.gl_RM1I3_goodsPic);
		gl_RM1I3_containerInfoPic = (Gallery) layout.findViewById(R.id.gl_RM1I3_containerInfoPic);

		ll_RM1I3_second_1 = (LinearLayout) layout.findViewById(R.id.ll_RM1I3_second_1);
		ll_RM1I3_second_2 = (LinearLayout) layout.findViewById(R.id.ll_RM1I3_second_2);
		ll_RM1I3_second_3 = (LinearLayout) layout.findViewById(R.id.ll_RM1I3_second_3);

		layout_RM1I3Second_show_1 = (RelativeLayout) layout.findViewById(R.id.layout_RM1I3Second_show_1);
		layout_RM1I3Second_show_2 = (RelativeLayout) layout.findViewById(R.id.layout_RM1I3Second_show_2);
		layout_RM1I3Second_show_3 = (RelativeLayout) layout.findViewById(R.id.layout_RM1I3Second_show_3);

		btn_RM1I3_HsaveImage = (Button) ll_RM1I3_tableHead.findViewById(R.id.btn_RM1I3_HsaveImage);
		btn_RM1I3_Hupload = (Button) ll_RM1I3_tableHead.findViewById(R.id.btn_RM1I3_Hupload);
		btn_RM1I3_HCupload = (Button) ll_RM1I3_tableHead.findViewById(R.id.btn_RM1I3_HCupload);
		btn_RM1I3_Hback = (Button) ll_RM1I3_tableHead.findViewById(R.id.btn_RM1I3_Hback);
	}

	// 表体的Dialog展示
	private void initDialog() {
		ll_RM1I3_listViewDialog = (LinearLayout) lInflater.inflate(
				R.layout.right_menu1info3_listview_dialog, null);
		ll_RM1I3_lv_main_left = (LinearLayout) ll_RM1I3_listViewDialog
				.findViewById(R.id.ll_RM1I3_lv_main_left);
		ll_RM1I3_lv_main_right = (LinearLayout) ll_RM1I3_listViewDialog
				.findViewById(R.id.ll_RM1I3_lv_main_right);
		View leftView = LayoutInflater.from(getContext()).inflate(
				R.layout.right_menu1info3_listview_left_layout, null);
		View rightView = LayoutInflater.from(getContext()).inflate(
				R.layout.right_menu1info3_listview_right_layout, null);

		mlv_RM1I3_rightLV = (MyListView) rightView.findViewById(R.id.lv_RM1I3_right_layout_listView1);
		mlv_RM1I3_leftLV = (MyListView) leftView.findViewById(R.id.lv_RM1I3_left_layout_listView1);
		M1I3LeftAdapter = new Menu1Info3LeftListViewAdapter(getContext(),
				entrysList);
		M1I3RightAdapter = new Menu1Info3RightListViewAdapter(getContext(),
				entrysList);
		mlv_RM1I3_rightLV.setAdapter(M1I3RightAdapter);
		mlv_RM1I3_leftLV.setAdapter(M1I3LeftAdapter);

		addListView(leftView, rightView);
	}

	private void doExecute() {
		btn_RM1I3_scan.setOnClickListener(new BTNDetailClickListener());
		btn_RM1I3_makeSure.setOnClickListener(new BTNDetailClickListener());
		btn_RM1I3_detail.setOnClickListener(new BTNDetailClickListener());
		//表头页面：  集装箱箱体  | 货物堆放情况 |集装箱标志
		btn_RM1I3Second_show_1.setOnClickListener(new BTNDetailClickListener());
		btn_RM1I3Second_show_2.setOnClickListener(new BTNDetailClickListener());
		btn_RM1I3Second_show_3.setOnClickListener(new BTNDetailClickListener());
		IB_RM1I3_takeCameraOne.setOnClickListener(new BTNDetailClickListener());
		IB_RM1I3_takeCameraTwo.setOnClickListener(new BTNDetailClickListener());
		IB_RM1I3_takeCameraThree.setOnClickListener(new BTNDetailClickListener());
		//商品项：货物包装近照  | 商品近照   |  集装箱内景照   | 商品规格型号照   |  其他特殊 要求照   |现场认定资料照|视频
		btn_RM1I3_uploadPicSecond_show_1.setOnClickListener(new Btn_uploadSecond());
		btn_RM1I3_uploadPicSecond_show_2.setOnClickListener(new Btn_uploadSecond());
		btn_RM1I3_uploadPicSecond_show_3.setOnClickListener(new Btn_uploadSecond());
		btn_RM1I3_uploadPicSecond_show_4.setOnClickListener(new Btn_uploadSecond());
		btn_RM1I3_uploadPicSecond_show_5.setOnClickListener(new Btn_uploadSecond());
		btn_RM1I3_uploadPicSecond_show_6.setOnClickListener(new Btn_uploadSecond());
		btn_RM1I3_uploadVideoSecond_show_7.setOnClickListener(new Btn_uploadSecond());

		IB_RM1I3_uploadPic_takeCameraOne.setOnClickListener(new BTNTakeCamera());
		IB_RM1I3_uploadPic_takeCameraTwo.setOnClickListener(new BTNTakeCamera());
		IB_RM1I3_uploadPic_takeCameraThree.setOnClickListener(new BTNTakeCamera());
		IB_RM1I3_uploadPic_takeCameraFour.setOnClickListener(new BTNTakeCamera());
		IB_RM1I3_uploadPic_takeCameraFive.setOnClickListener(new BTNTakeCamera());
		IB_RM1I3_uploadPic_takeCameraSix.setOnClickListener(new BTNTakeCamera());
		IB_uploadVideo_takeVideo.setOnClickListener(new BTNTakeCamera());

		btn_RM1I3P_save.setOnClickListener(new BTNTakeCamera());//上传表体的图片
		camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		handler_RM1I3 = new Handler() {
			@SuppressLint("ParserError")
			public void dispatchMessage(android.os.Message msg) {
				int result = msg.what;
				String picFilePathCamera;// 报关单图片存储路径
				switch (result) {
				case Constant.REQ_SCAN_SECOND:// 扫描按钮
					String barCodeScan = FrameDemoActivity.barCode;
					if (barCodeScan != null && !barCodeScan.equals("")) {
						et_RM1I3_barCode.setText(barCodeScan);
					}
					break;

				case Constant.RM1I3_TAKEPHONE_FIEST:// 表头 1
					//当照片完全存储后，由FrameDemoActivity发送一个Handler通知，相对应的 照片展示类型  重新向SDCard加载   （再代码加载所以图片一次）
					entryNumber = FrameDemoActivity.myApp.getEntryNumber();
					//SDCard目录-报关单号-项号-报关单号_项号_图片类型----SavePath
					picFilePathCamera = Constant.SAVEPATH + entryNumber
							+ Constant.SAVE_FORMHEADIMAGEUIR
							+ entryNumber+"_"+Constant.FORM_HEAD+"_"+Constant.CONTAINER_BODY + "/";
					System.out.println("表头1picFilePathCamera返回的路径---"+picFilePathCamera);
					loadPicDataAgain(picFilePathCamera, gl_RM1I3_containerPic);
					break;

				case Constant.RM1I3_TAKEPHONE_TWO:// 表头 2
					entryNumber = FrameDemoActivity.myApp.getEntryNumber();
					picFilePathCamera = Constant.SAVEPATH + entryNumber
							+ Constant.SAVE_FORMHEADIMAGEUIR
							+ entryNumber+"_"+Constant.FORM_HEAD+"_"+Constant.GOODSTACK_SITUATION + "/";
					loadPicDataAgain(picFilePathCamera, gl_RM1I3_goodsPic);
					break;

				case Constant.RM1I3_TAKEPHONE_THREE:// 表头 3
					entryNumber = FrameDemoActivity.myApp.getEntryNumber();
					picFilePathCamera = Constant.SAVEPATH + entryNumber
							+ Constant.SAVE_FORMHEADIMAGEUIR
							+ entryNumber+"_"+Constant.FORM_HEAD+"_"+Constant.CONTAINER_MARK + "/";
					System.out.println("表头3picFilePathCamera返回的路径---"+picFilePathCamera);
					loadPicDataAgain(picFilePathCamera, gl_RM1I3_containerInfoPic);
					break;

				case Constant.REQUPLOADCAMES_FIRST:// 表体 1
					entryNumber = FrameDemoActivity.myApp.getEntryNumber();
					projectNum = FrameDemoActivity.myApp.getProjectNum();
					picFilePathCamera = Constant.SAVEPATH + entryNumber + "/"
							+ projectNum + "/" +entryNumber+"_" + projectNum + "_" + Constant.GOODS_PACKAGE + "/";
					loadPicDataAgain(picFilePathCamera, gl_RM1I3_goodsPackagePhoto);
					break;

				case Constant.REQUPLOADCAMES_SECOND:// 表体 2
					entryNumber = FrameDemoActivity.myApp.getEntryNumber();
					projectNum = FrameDemoActivity.myApp.getProjectNum();
					picFilePathCamera = Constant.SAVEPATH + entryNumber + "/"
							+ projectNum + "/" +entryNumber+"_" + projectNum + "_" + Constant.GOODS + "/";
					loadPicDataAgain(picFilePathCamera, gl_RM1I3_goodsPhoto);
					break;

				case Constant.REQUPLOADCAMES_THIRD:// 表体 3
					entryNumber = FrameDemoActivity.myApp.getEntryNumber();
					projectNum = FrameDemoActivity.myApp.getProjectNum();
					picFilePathCamera = Constant.SAVEPATH + entryNumber + "/"
							+ projectNum + "/" +entryNumber+"_" + projectNum + "_" + Constant.CONTAINER_INNER + "/";
					loadPicDataAgain(picFilePathCamera, gl_RM1I3_containerInnerPhoto);
					break;

				case Constant.REQUPLOADCAMES_FORTH:// 表体 4
					entryNumber = FrameDemoActivity.myApp.getEntryNumber();
					projectNum = FrameDemoActivity.myApp.getProjectNum();
					picFilePathCamera = Constant.SAVEPATH + entryNumber + "/"
							+ projectNum + "/" +entryNumber+"_" + projectNum + "_" + Constant.GOODS_SPECIFICATION_MODEL + "/";
					loadPicDataAgain(picFilePathCamera, gl_RM1I3_goodsSpecModelPhoto);
					break;

				case Constant.REQUPLOADCAMES_FIFTH:// 表体 5
					entryNumber = FrameDemoActivity.myApp.getEntryNumber();
					projectNum = FrameDemoActivity.myApp.getProjectNum();
					picFilePathCamera = Constant.SAVEPATH + entryNumber + "/"
							+ projectNum + "/" +entryNumber+"_" + projectNum + "_" + Constant.ANOTHER_SPECIAL_REQUEST + "/";
					loadPicDataAgain(picFilePathCamera, gl_RM1I3_anotherSpecialRequestPhoto);
					break;

				case Constant.REQUPLOADCAMES_SIXTH:// 表体 6
					entryNumber = FrameDemoActivity.myApp.getEntryNumber();
					projectNum = FrameDemoActivity.myApp.getProjectNum();
					picFilePathCamera = Constant.SAVEPATH + entryNumber + "/"
							+ projectNum + "/" +entryNumber+"_" + projectNum + "_" + Constant.SCENE_SRC + "/";
					loadPicDataAgain(picFilePathCamera, gl_RM1I3_sceneSrcPhoto);
					break;
				case Constant.RINGHT_GROUP1_MENU3:// 从表体的列表----> 上传表体页面
					int p = msg.arg1;
					entryList = entrysList.get(p);
					FrameDemoActivity.myApp.setProjectNum("");
					FrameDemoActivity.myApp.setProjectNum(entryList
							.getCODE_TS());
					System.out.println("点击的上传表体是" + p + "====="
							+ entryList.getENTRY_ID() + "--"
							+ entryList.getCODE_TS() + "=="
							+ entryList.getG_NAME());
					uploadEntryList();
					ll_RM1I3_tableHead.setVisibility(View.GONE);
					View_takeTableBodyPhotos.setVisibility(View.VISIBLE);
					break;
				case Constant.RECORDER_VIDEO:
					Toast.makeText(getContext(), "视频刻录成功，由于视频文件太大，需在PC机上传，谢谢。", 500).show();
					break;
				}

			};
		};

		// 上传图片的按钮事件添加监听
		addListenerToBTN();
	}

	// 上传图片的按钮事件添加监听
	private void addListenerToBTN() {
		btn_RM1I3_HsaveImage.setOnClickListener(new BTN_UploadImageListener());
		btn_RM1I3_Hupload.setOnClickListener(new BTN_UploadImageListener());
		btn_RM1I3_HCupload.setOnClickListener(new BTN_UploadImageListener());
		btn_RM1I3_Hback.setOnClickListener(new BTN_UploadImageListener());
		btn_RM1I3P_save.setOnClickListener(new BTN_UploadImageListener());
		btn_RM1I3P_back.setOnClickListener(new BTN_UploadImageListener());
	}

	// 上传图片的按钮事件添加监听------设置监听事件
	@SuppressLint("ParserError")
	private class BTN_UploadImageListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			int btnId = v.getId();
			String GHEntryId=entryHead.getENTRY_ID();
			String saveTime=FrameDemoActivity.toolUtils.requestData(Constant.VAGUE_DATATIME);
			switch (btnId) {
			case R.id.btn_RM1I3_HsaveImage:// 表头---保存图片
				//测试数据
				GHEntryId="R10220010000011";
				if(GHEntryId!=null && !GHEntryId.equals("")){
					ArrayList<PhotoList> photols=FrameDemoActivity.db.selectFromPhotoListByEntryId(GHEntryId);
					boolean bool=FrameDemoActivity.db.updateEntry_HeadById(GHEntryId, saveTime);
					String iploadJson=uploadChangeToJSON(photols);//转换成JSON格式的参数上传
					if(bool){//确定上传
						if(iploadJson!=null && !iploadJson.equals("")){
							System.out.println("上传的JSON"+iploadJson);
							new MyTaskUpload().execute(iploadJson,"暂存表头成功。",GHEntryId);
						}
					}
				}
				break;
			case R.id.btn_RM1I3_Hupload:// 表头---确认上传
				System.out.println("//表头---确认上传");
				//上传表头图片
				if(GHEntryId!=null && !GHEntryId.equals("")){
					ArrayList<PhotoList> photols=FrameDemoActivity.db.selectFromPhotoListByEntryId(GHEntryId);
					boolean bool=FrameDemoActivity.db.updateEntry_HeadById(GHEntryId, saveTime);
					String iploadJson=uploadChangeToJSON(photols);//转换成JSON格式的参数上传
					if(bool){//确定上传
						if(iploadJson!=null && !iploadJson.equals("")){
							System.out.println("上传的JSON"+iploadJson);
							new MyTaskUpload().execute(iploadJson,"确认上传成功。",GHEntryId);
						}
					}
				}
				//// 判断是否 上传过--表头上传图片-
				G_HeadUploadImage();
				break;
			case R.id.btn_RM1I3_HCupload:// 表头---取消上传
				System.out.println("//表头---取消上传");
				new MyCancelUploadTask().execute(GHEntryId);//取消上传
				break;
			case R.id.btn_RM1I3_Hback:// 表头---返回
				System.out.println("//表头---返回");
				break;
			case R.id.btn_RM1I3P_back:// 表体---返回
				ll_RM1I3_tableHead.setVisibility(View.VISIBLE);
				View_takeTableBodyPhotos.setVisibility(View.GONE);
				break;
			case R.id.btn_RM1I3P_save:// 表体---保存图片
				// 暂存表体信息----暂存成功
				String remark=et_uploadPic_remark.getText().toString();
				if(remark!=null && !remark.equals("")){
					if(entryList.getENTRY_ID()!=null && !entryList.getENTRY_ID().equals("")){
						ArrayList<PhotoList> photols=FrameDemoActivity.db.selectFromPhotoListByEntryId(GHEntryId);
						String iploadJson=uploadChangeToJSON(photols);//转换成JSON格式的参数上传
						if(isEntryListUpload(photols)){//判断必上传的图片是否上传    1|0
							if(FrameDemoActivity.db.updateEntry_ListById(entryList.getENTRY_ID(), entryList.getG_NO()+"", 1)){
								new MyTaskUploadEntryList1().execute(iploadJson,entryList.getENTRY_ID(),entryList.getG_NO()+"","1",""+remark);
							}
						}else{
							if(FrameDemoActivity.db.updateEntry_ListById(entryList.getENTRY_ID(), entryList.getG_NO()+"", 0)){
								new MyTaskUploadEntryList2().execute(entryList.getENTRY_ID(),entryList.getG_NO()+"","1",""+remark);
							}
						}
						//成功返回
						ll_RM1I3_tableHead.setVisibility(View.VISIBLE);
						View_takeTableBodyPhotos.setVisibility(View.GONE);
					}
				}else{
					FrameDemoActivity.toolUtils.promptMessage("商品的评论备注不能为空，请填写，谢谢。");
				}
				System.out.println("//表体---保存图片");
//				FrameDemoActivity.handler
//						.sendEmptyMessage(Constant.RINGHT_GROUP1_MENU1);
				break;
			}
		}

	}
	//上传页面的JSON封装转换
	@SuppressWarnings("static-access")
	private String uploadChangeToJSON(ArrayList<PhotoList> photols){
		
		List<Image> imageList=new ArrayList<Image>();
		Image image;
		for(PhotoList p:photols){
			image = new Image();
			image.setENTRY_ID(p.getEntry_ID());
			image.setPHOTO_CODE(p.getPhoto_list_Code());
			image.setG_NO(p.getG_No());
		
			System.out.println("-图片的路径-"+p.getPhoto_list_ID());
			
			Bitmap bitmap=FrameDemoActivity.toolUtils.getSmallBitmap(p.getPhoto_list_ID());
			FrameDemoActivity.toolUtils.compressImage(bitmap);
			byte[] buffer=FrameDemoActivity.toolUtils.bitmapTobyte(bitmap);
			image.setBase64String(Base64.encodeBytes(buffer));
			imageList.add(image);
			FrameDemoActivity.toolUtils.recycle(bitmap);
		}
		String json=FrameDemoActivity.gson.toJson(imageList);
		return json;
	}
	//上传图片的Task----表头
	class MyTaskUpload extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			FrameDemoActivity.toolUtils.startProgressDialog();
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String reqResult = "";
			FrameDemoActivity.webservice.setMETHOD_NAME("UploadPhotoInfo");
			FrameDemoActivity.webservice
					.setSOAP_ACTION("http://tempuri.org/IGetExamDataService/UploadPhotoInfo");
			if (FrameDemoActivity.webservice.connect(getContext(),new String[] { "strJSON" },
					new Object[] { params[0] })) {
				reqResult = FrameDemoActivity.webservice.getResult().toString();
				System.out.println("图像上传返回结果：" + reqResult);
				if(reqResult.equals("true")){
					FrameDemoActivity.toolUtils.writeDataLog(params[1], "86", params[2]);
				}
			}
			return reqResult;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result != null) {
				System.out.println("返回结果：" + result);
				Toast.makeText(getContext(), "返回结果：" + result, 500).show();
			}
			FrameDemoActivity.toolUtils.endProgressDialog();
		}

	}
	
	////判断必上传的图片是否上传
	private boolean isEntryListUpload(ArrayList<PhotoList> photols){
		boolean flag=false;
		for(PhotoList p:photols){
			String photoCode=p.getPhoto_list_Code();
			if(photoCode.equals(Constant.GOODS_PACKAGE) || photoCode.equals(Constant.GOODS)){
				flag=true;//1
			}
		}
		return flag;
	}
	
	//上传图片的Task----表体1
	class MyTaskUploadEntryList1 extends AsyncTask<String, String, String> {
		
			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				FrameDemoActivity.toolUtils.startProgressDialog();
			}

			@Override
			protected String doInBackground(String... params) {
				// TODO Auto-generated method stub
				String reqResult = "";
				FrameDemoActivity.webservice.setMETHOD_NAME("UploadPhotoInfo");
				FrameDemoActivity.webservice
						.setSOAP_ACTION("http://tempuri.org/IGetExamDataService/UploadPhotoInfo");
				if (FrameDemoActivity.webservice.connect(getContext(),new String[] { "strJSON" },
						new Object[] { params[0] })) {
					reqResult = FrameDemoActivity.webservice.getResult().toString();
					System.out.println("图像上传返回结果：" + reqResult);
					if(reqResult.equals("true")){
						FrameDemoActivity.webservice.setMETHOD_NAME("UpdateGListInfo");
						FrameDemoActivity.webservice
								.setSOAP_ACTION("http://tempuri.org/IGetExamDataService/UpdateGListInfo");
						if (FrameDemoActivity.webservice.connect(getContext(),new String[] { "entry_id","g_no","g_ys","remark" },
								new Object[] { params[1],params[2],params[3],params[4] })) {
							//成功的添加日志
							FrameDemoActivity.toolUtils.writeDataLog("暂存表体成功。", "86", params[1]);
						}
					}
				}
				return reqResult;
			}

			@Override
			protected void onPostExecute(String result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				FrameDemoActivity.toolUtils.endProgressDialog();
				System.out.println("返回结果：" + result);
				if (result != null) {
					Toast.makeText(getContext(), "返回结果：" + result, 500).show();
				}

			}

		}
	//上传图片的Task----表体2
	class MyTaskUploadEntryList2 extends AsyncTask<String, String, String> {
			
				@Override
				protected void onPreExecute() {
					// TODO Auto-generated method stub
					super.onPreExecute();
					FrameDemoActivity.toolUtils.startProgressDialog();
				}

				@SuppressLint("ParserError")
				@Override
				protected String doInBackground(String... params) {
					// TODO Auto-generated method stub
					String reqResult = "";
					FrameDemoActivity.webservice.setMETHOD_NAME("DeleteServerPhotoGno");
					FrameDemoActivity.webservice
							.setSOAP_ACTION("http://tempuri.org/IGetExamDataService/DeleteServerPhotoGno");
					if (FrameDemoActivity.webservice.connect(getContext(),new String[] { "entry_id","g_no" },
							new Object[] { params[0],params[1] })) {
							FrameDemoActivity.webservice.setMETHOD_NAME("UpdateGListInfo");
							FrameDemoActivity.webservice
									.setSOAP_ACTION("http://tempuri.org/IGetExamDataService/UpdateGListInfo");
							if (FrameDemoActivity.webservice.connect(getContext(),new String[] { "entry_id","g_no","g_ys","remark" },
									new Object[] { params[0],params[1],params[2],params[3] })) {
								//成功的添加日志
								//FrameDemoActivity.toolUtils.writeDataLog("暂存表体成功。", "86", "");
							}
					}
					return reqResult;
				}

				@Override
				protected void onPostExecute(String result) {
					// TODO Auto-generated method stub
					super.onPostExecute(result);
					FrameDemoActivity.toolUtils.endProgressDialog();
					System.out.println("返回结果：" + result);
					if (result != null) {
						Toast.makeText(getContext(), "返回结果：" + result, 500).show();
					}

				}

			}
	//取消上传的接口调用
 	class MyCancelUploadTask extends AsyncTask<String, String, String>{
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			FrameDemoActivity.toolUtils.startProgressDialog();
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String reqResult = "";
			FrameDemoActivity.webservice.setMETHOD_NAME("CancelUpload");
			FrameDemoActivity.webservice
					.setSOAP_ACTION("http://tempuri.org/IGetExamDataService/CancelUpload");
			if (FrameDemoActivity.webservice.connect(getContext(),new String[] { "entry_id" },
					new Object[] { params[0] })) {
				FrameDemoActivity.toolUtils.writeDataLog("取消上传成功。", "86", params[0]);
			}
			return reqResult;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			FrameDemoActivity.toolUtils.endProgressDialog();
		}
		
	}
	// 上传表头页面的监听事件
	@SuppressLint({ "ParserError", "ParserError" })
	private class BTNDetailClickListener implements OnClickListener {

		String photoFilePath;

		@Override
		public void onClick(View v) {
			int btnId = v.getId();
			et_RM1I3_barCode.setText("010120111011982012");// 测试数据
			entryNumber = et_RM1I3_barCode.getText().toString();
			FrameDemoActivity.myApp.setEntryNumber(entryNumber);
			switch (btnId) {
			case R.id.btn_RM1I3_detail:// 商品项---表体数据
				// setContentView可以设置为一个View也可以简单地指定资源ID
				if (entryNumber != null && !entryNumber.equals("")) {
					entryNumber = "220120111010151633";// 测试数据
					new MyAsynTaskEntrys().execute(entryNumber);
					initDialog();
					MyDialog.showDialog(getContext(), ll_RM1I3_listViewDialog,
							getContext().getString(R.string.dialog_title));
					Button btn_RM1I3_dialogCancel = (Button) ll_RM1I3_listViewDialog
							.findViewById(R.id.btn_RM1I3_dialogCancel);
					// 表体Dialog的显示
					btn_RM1I3_dialogCancel
							.setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View v) {
									MyDialog.dismissDialog();
								}
							});
				}
				break;

			case R.id.btn_RM1I3_scan:// 扫描
				Intent mIntent = new Intent(getContext(),BarCodeScannerActivity.class);
				((Activity) getContext()).startActivityForResult(mIntent,Constant.REQ_SCAN_SECOND);
				break;

			case R.id.btn_RM1I3Second_show_1:  // 左边 按钮的事件
				searchLayoutShow();
				ll_RM1I3_second_1.setVisibility(View.VISIBLE);
				layout_RM1I3Second_show_1.setBackgroundResource(R.drawable.show_btn_bg_down);
				break;

			case R.id.btn_RM1I3Second_show_2:// 左边 按钮的事件
				searchLayoutShow();
				ll_RM1I3_second_2.setVisibility(View.VISIBLE);
				layout_RM1I3Second_show_2.setBackgroundResource(R.drawable.show_btn_bg_down);
				break;

			case R.id.btn_RM1I3Second_show_3:// 左边 按钮的事件
				searchLayoutShow();
				ll_RM1I3_second_3.setVisibility(View.VISIBLE);
				layout_RM1I3Second_show_3.setBackgroundResource(R.drawable.show_btn_bg_down);
				break;

			case R.id.IB_RM1I3_takeCameraOne://3-1 表头   ：右边拍照按钮的事件
				entryNumber = FrameDemoActivity.myApp.getEntryNumber();
				photoFilePath = Constant.SAVEPATH + entryNumber
						+ Constant.SAVE_FORMHEADIMAGEUIR
						+ Constant.CONTAINER_BODY + "/";
				loadPicData(photoFilePath, camera, R.id.IB_RM1I3_takeCameraOne,
						gl_RM1I3_containerPic);
				break;

			case R.id.IB_RM1I3_takeCameraTwo://3-2 表头     ：右边拍照按钮的事件
				entryNumber = FrameDemoActivity.myApp.getEntryNumber();
				photoFilePath = Constant.SAVEPATH + entryNumber
						+ Constant.SAVE_FORMHEADIMAGEUIR
						+ Constant.GOODSTACK_SITUATION + "/";
				loadPicData(photoFilePath, camera, R.id.IB_RM1I3_takeCameraTwo,
						gl_RM1I3_goodsPic);
				break;

			case R.id.IB_RM1I3_takeCameraThree://3-3 表头  ：右边拍照按钮的事件
				entryNumber = FrameDemoActivity.myApp.getEntryNumber();
				photoFilePath = Constant.SAVEPATH + entryNumber
						+ Constant.SAVE_FORMHEADIMAGEUIR
						+ Constant.CONTAINER_MARK + "/";
				loadPicData(photoFilePath, camera, R.id.IB_RM1I3_takeCameraThree,
						gl_RM1I3_containerInfoPic);
				break;

			case R.id.btn_RM1I3_makeSure://查询  按钮的事件
				if (entryNumber != null && !entryNumber.equals("")) {
					new MyAsynTask().execute(entryNumber);// 获取表头信息
					
					// 表头、表体切换
					ll_RM1I3_tableHead.setVisibility(View.VISIBLE);
					View_takeTableBodyPhotos.setVisibility(View.GONE);
				} else {
					FrameDemoActivity.toolUtils.promptMessage("报关单号不能为空。");
				}
				break;
			}

		}
	};

	// 上传表体页面拍照 的监听事件-----商品项---右边拍照按钮的事件
	private class BTNTakeCamera implements OnClickListener {

		String photoFilePath;

		@Override
		public void onClick(View v) {
			int vId = v.getId();
			if (camera != null) {
				switch (vId) {
				case R.id.IB_uploadPic_takeCameraOne:
					entryNumber = FrameDemoActivity.myApp.getEntryNumber();
					projectNum = FrameDemoActivity.myApp.getProjectNum();
					photoFilePath = Constant.SAVEPATH + entryNumber + "/"
							+ projectNum + "/" + Constant.GOODS_PACKAGE + "/";
					loadPicData(photoFilePath, camera,
							R.id.IB_uploadPic_takeCameraOne,
							gl_RM1I3_goodsPackagePhoto);
					break;

				case R.id.IB_uploadPic_takeCameraTwo:
					entryNumber = FrameDemoActivity.myApp.getEntryNumber();
					projectNum = FrameDemoActivity.myApp.getProjectNum();
					photoFilePath = Constant.SAVEPATH + entryNumber + "/"
							+ projectNum + "/" + Constant.GOODS + "/";
					loadPicData(photoFilePath, camera,
							R.id.IB_uploadPic_takeCameraTwo, gl_RM1I3_goodsPhoto);
					break;

				case R.id.IB_uploadPic_takeCameraThree:
					entryNumber = FrameDemoActivity.myApp.getEntryNumber();
					projectNum = FrameDemoActivity.myApp.getProjectNum();
					photoFilePath = Constant.SAVEPATH + entryNumber + "/"
							+ projectNum + "/" + Constant.CONTAINER_INNER + "/";
					loadPicData(photoFilePath, camera,
							R.id.IB_uploadPic_takeCameraThree,
							gl_RM1I3_containerInnerPhoto);
					break;

				case R.id.IB_uploadPic_takeCameraFour:
					entryNumber = FrameDemoActivity.myApp.getEntryNumber();
					projectNum = FrameDemoActivity.myApp.getProjectNum();
					photoFilePath = Constant.SAVEPATH + entryNumber + "/"
							+ projectNum + "/"
							+ Constant.GOODS_SPECIFICATION_MODEL + "/";
					loadPicData(photoFilePath, camera,
							R.id.IB_uploadPic_takeCameraFour,
							gl_RM1I3_goodsSpecModelPhoto);
					break;

				case R.id.IB_uploadPic_takeCameraFive:
					entryNumber = FrameDemoActivity.myApp.getEntryNumber();
					projectNum = FrameDemoActivity.myApp.getProjectNum();
					photoFilePath = Constant.SAVEPATH + entryNumber + "/"
							+ projectNum + "/"
							+ Constant.ANOTHER_SPECIAL_REQUEST + "/";
					loadPicData(photoFilePath, camera,
							R.id.IB_uploadPic_takeCameraFive,
							gl_RM1I3_anotherSpecialRequestPhoto);
					break;

				case R.id.IB_uploadPic_takeCameraSix:
					entryNumber = FrameDemoActivity.myApp.getEntryNumber();
					projectNum = FrameDemoActivity.myApp.getProjectNum();
					photoFilePath = Constant.SAVEPATH + entryNumber + "/"
							+ projectNum + "/" + Constant.SCENE_SRC + "/";
					loadPicData(photoFilePath, camera,
							R.id.IB_uploadPic_takeCameraSix,
							gl_RM1I3_sceneSrcPhoto);
					break;

				case R.id.IB_uploadVideo_takeVideo:// 刻录视频
					toRecorderVideo();
					break;

				}
			}
		}
	}

	//----以下  Btn_uploadSecond类、dialogLayoutShow()是表体上传页面的图片展示UI效果作用
	// 上传表体页面 照片分类的改变----左边按钮的控制事件
	private class Btn_uploadSecond implements OnClickListener {
		@Override
		public void onClick(View v) {
			int vId = v.getId();
			LinearLayout ll_view = (LinearLayout) v.getParent().getParent().getParent().getParent();
			ll_first = (LinearLayout) ll_view.findViewById(R.id.ll_uploadPic_second_1);
			ll_second = (LinearLayout) ll_view.findViewById(R.id.ll_uploadPic_second_2);
			ll_third = (LinearLayout) ll_view.findViewById(R.id.ll_uploadPic_second_3);
			ll_forth = (LinearLayout) ll_view.findViewById(R.id.ll_uploadPic_second_4);
			ll_fifth = (LinearLayout) ll_view.findViewById(R.id.ll_uploadPic_second_5);
			ll_sixth = (LinearLayout) ll_view.findViewById(R.id.ll_uploadPic_second_6);
			ll_seventh = (LinearLayout) ll_view.findViewById(R.id.ll_uploadVideo_second_7);
			layout_uploadPicSecond_show_1 = (RelativeLayout) ll_view.findViewById(R.id.layout_uploadPicSecond_show_1);
			layout_uploadPicSecond_show_2 = (RelativeLayout) ll_view.findViewById(R.id.layout_uploadPicSecond_show_2);
			layout_uploadPicSecond_show_3 = (RelativeLayout) ll_view.findViewById(R.id.layout_uploadPicSecond_show_3);
			layout_uploadPicSecond_show_4 = (RelativeLayout) ll_view.findViewById(R.id.layout_uploadPicSecond_show_4);
			layout_uploadPicSecond_show_5 = (RelativeLayout) ll_view.findViewById(R.id.layout_uploadPicSecond_show_5);
			layout_uploadPicSecond_show_6 = (RelativeLayout) ll_view.findViewById(R.id.layout_uploadPicSecond_show_6);
			layout_uploadVideoSecond_show_7 = (RelativeLayout) ll_view.findViewById(R.id.layout_uploadVideoSecond_show_7);
			switch (vId) {
			case R.id.btn_uploadPicSecond_show_1:
				dialogLayoutShow();
				ll_first.setVisibility(View.VISIBLE);
				layout_uploadPicSecond_show_1.setBackgroundResource(R.drawable.show_btn_bg_down_edit);
				break;

			case R.id.btn_uploadPicSecond_show_2:
				dialogLayoutShow();
				ll_second.setVisibility(View.VISIBLE);
				layout_uploadPicSecond_show_2.setBackgroundResource(R.drawable.show_btn_bg_down_edit);
				break;

			case R.id.btn_uploadPicSecond_show_3:
				dialogLayoutShow();
				ll_third.setVisibility(View.VISIBLE);
				layout_uploadPicSecond_show_3.setBackgroundResource(R.drawable.show_btn_bg_down_edit);
				break;

			case R.id.btn_uploadPicSecond_show_4:
				dialogLayoutShow();
				ll_forth.setVisibility(View.VISIBLE);
				layout_uploadPicSecond_show_4.setBackgroundResource(R.drawable.show_btn_bg_down_edit);
				break;

			case R.id.btn_uploadPicSecond_show_5:
				dialogLayoutShow();
				ll_fifth.setVisibility(View.VISIBLE);
				layout_uploadPicSecond_show_5.setBackgroundResource(R.drawable.show_btn_bg_down_edit);
				break;

			case R.id.btn_uploadPicSecond_show_6:
				dialogLayoutShow();
				ll_sixth.setVisibility(View.VISIBLE);
				layout_uploadPicSecond_show_6.setBackgroundResource(R.drawable.show_btn_bg_down_edit);
				break;

			case R.id.btn_uploadVideoSecond_show_7:
				dialogLayoutShow();
				ll_seventh.setVisibility(View.VISIBLE);
				layout_uploadVideoSecond_show_7.setBackgroundResource(R.drawable.show_btn_bg_down_edit);
				break;
			}
		}
	};

	// 表头图片类型初始化
	private void searchLayoutShow() {
		ll_RM1I3_second_1.setVisibility(View.GONE);
		ll_RM1I3_second_2.setVisibility(View.GONE);
		ll_RM1I3_second_3.setVisibility(View.GONE);
		layout_RM1I3Second_show_1.setBackgroundResource(R.drawable.show_btn_bg_up);
		layout_RM1I3Second_show_2.setBackgroundResource(R.drawable.show_btn_bg_up);
		layout_RM1I3Second_show_3.setBackgroundResource(R.drawable.show_btn_bg_up);
	}

	// 表体上传页面图片类型初始化
	private void dialogLayoutShow() {
		ll_first.setVisibility(View.GONE);
		ll_second.setVisibility(View.GONE);
		ll_third.setVisibility(View.GONE);
		ll_forth.setVisibility(View.GONE);
		ll_fifth.setVisibility(View.GONE);
		ll_sixth.setVisibility(View.GONE);
		ll_seventh.setVisibility(View.GONE);
		layout_uploadPicSecond_show_1
				.setBackgroundResource(R.drawable.show_btn_bg_up_edit);
		layout_uploadPicSecond_show_2
				.setBackgroundResource(R.drawable.show_btn_bg_up_edit);
		layout_uploadPicSecond_show_3
				.setBackgroundResource(R.drawable.show_btn_bg_up_edit);
		layout_uploadPicSecond_show_4
				.setBackgroundResource(R.drawable.show_btn_bg_up_edit);
		layout_uploadPicSecond_show_5
				.setBackgroundResource(R.drawable.show_btn_bg_up_edit);
		layout_uploadPicSecond_show_6
				.setBackgroundResource(R.drawable.show_btn_bg_up_edit);
		layout_uploadVideoSecond_show_7
				.setBackgroundResource(R.drawable.show_btn_bg_up_edit);
	}

	// 加载对应 文件夹下面的所有图片
	private void loadPicDataAgain(String picFileFolderPath, Gallery gallery) {
		ls = FileUtil.getFileList(picFileFolderPath);
		sa = new ImageAdapter(getContext(), ls);
		gallery.setAdapter(sa);
	}

	// 照片的拍照 或者 本地加载
	private void loadPicData(final String picFilePath, final Intent intent,
			final int btnReqCoder, final Gallery gallery) {
		// 确认对话框
		final AlertDialog isExit = new AlertDialog.Builder(getContext())
				.create();
		// 对话框标题
		isExit.setTitle(getContext().getString(R.string.titleChoose));
		isExit.setIcon(R.drawable.ic_launcher);
		// 实例化对话框上的按钮点击事件监听
		DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case AlertDialog.BUTTON1:// "立即拍照"按钮调用系统照相机拍照并将照片存储到指定的文件夹下
					// 表头 Constant.RM1I3_TAKEPHONE_FIEST | Constant.RM1I3_TAKEPHONE_TWO | Constant.RM1I3_TAKEPHONE_THREE
					/**
					 *  表体
					 *  Constant.REQUPLOADCAMES_FIRST | Constant.REQUPLOADCAMES_SECOND | Constant.REQUPLOADCAMES_THIRD
					 *  Constant.REQUPLOADCAMES_FORTH | Constant.REQUPLOADCAMES_FIFTH | Constant.REQUPLOADCAMES_SIXTH
					 */
					if(btnReqCoder==R.id.IB_RM1I3_takeCameraOne){
						((Activity) getContext()).startActivityForResult(intent, Constant.RM1I3_TAKEPHONE_FIEST);
					}else if(btnReqCoder==R.id.IB_RM1I3_takeCameraTwo){
						((Activity) getContext()).startActivityForResult(intent, Constant.RM1I3_TAKEPHONE_TWO);
					}else if(btnReqCoder==R.id.IB_RM1I3_takeCameraThree){
						((Activity) getContext()).startActivityForResult(intent, Constant.RM1I3_TAKEPHONE_THREE);
					}else if(btnReqCoder==R.id.IB_uploadPic_takeCameraOne){
						((Activity) getContext()).startActivityForResult(intent, Constant.REQUPLOADCAMES_FIRST);
					}else if(btnReqCoder==R.id.IB_uploadPic_takeCameraTwo){
						((Activity) getContext()).startActivityForResult(intent, Constant.REQUPLOADCAMES_SECOND);
					}else if(btnReqCoder==R.id.IB_uploadPic_takeCameraThree){
						((Activity) getContext()).startActivityForResult(intent, Constant.REQUPLOADCAMES_THIRD);
					}else if(btnReqCoder==R.id.IB_uploadPic_takeCameraFour){
						((Activity) getContext()).startActivityForResult(intent, Constant.REQUPLOADCAMES_FORTH);
					}else if(btnReqCoder==R.id.IB_uploadPic_takeCameraFive){
						((Activity) getContext()).startActivityForResult(intent, Constant.REQUPLOADCAMES_FIFTH);
					}else if(btnReqCoder==R.id.IB_uploadPic_takeCameraSix){
						((Activity) getContext()).startActivityForResult(intent, Constant.REQUPLOADCAMES_SIXTH);
					}
					break;
				case AlertDialog.BUTTON2:// "本地上传"第二个按钮获取本地指定目录存储图片
					// 表头 REQ_LOACHOSTINAGE_RM3_G1 | REQ_LOACHOSTINAGE_RM3_G2 | REQ_LOACHOSTINAGE_RM3_G3
					/**
					 * 表体
					 * REQ_LOACHOSTINAGE_RM3_G4 | REQ_LOACHOSTINAGE_RM3_G5 | REQ_LOACHOSTINAGE_RM3_G6
					 * REQ_LOACHOSTINAGE_RM3_G7 | REQ_LOACHOSTINAGE_RM3_G8 | REQ_LOACHOSTINAGE_RM3_G9
					 */
					if(btnReqCoder==R.id.IB_RM1I3_takeCameraOne){
						FrameDemoActivity.toolUtils.requestLoachostImage(Constant.REQ_LOACHOSTINAGE_RM3_G1);
					}else if(btnReqCoder==R.id.IB_RM1I3_takeCameraTwo){
						FrameDemoActivity.toolUtils.requestLoachostImage(Constant.REQ_LOACHOSTINAGE_RM3_G2);
					}else if(btnReqCoder==R.id.IB_RM1I3_takeCameraThree){
						FrameDemoActivity.toolUtils.requestLoachostImage(Constant.REQ_LOACHOSTINAGE_RM3_G3);
					}else if(btnReqCoder==R.id.IB_uploadPic_takeCameraOne){
						FrameDemoActivity.toolUtils.requestLoachostImage(Constant.REQ_LOACHOSTINAGE_RM3_G4);
					}else if(btnReqCoder==R.id.IB_uploadPic_takeCameraTwo){
						FrameDemoActivity.toolUtils.requestLoachostImage(Constant.REQ_LOACHOSTINAGE_RM3_G5);
					}else if(btnReqCoder==R.id.IB_uploadPic_takeCameraThree){
						FrameDemoActivity.toolUtils.requestLoachostImage(Constant.REQ_LOACHOSTINAGE_RM3_G6);
					}else if(btnReqCoder==R.id.IB_uploadPic_takeCameraFour){
						FrameDemoActivity.toolUtils.requestLoachostImage(Constant.REQ_LOACHOSTINAGE_RM3_G7);
					}else if(btnReqCoder==R.id.IB_uploadPic_takeCameraFive){
						FrameDemoActivity.toolUtils.requestLoachostImage(Constant.REQ_LOACHOSTINAGE_RM3_G8);
					}else if(btnReqCoder==R.id.IB_uploadPic_takeCameraSix){
						FrameDemoActivity.toolUtils.requestLoachostImage(Constant.REQ_LOACHOSTINAGE_RM3_G9);
					}
					break;
				default:
					break;
				}
			}
		};
		// 注册监听
		isExit.setButton(getContext().getString(R.string.immediately_camera), listener);
		isExit.setButton2(getContext().getString(R.string.local_upload), listener);
		// 显示对话框
		isExit.show();
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
			Intent intentOne = new Intent(getContext(),ToImageZoomActivity.class);
			intentOne.putExtra("imagePath", picFilePath);
			intentOne.putExtra("PATH_TYPE", Constant.LoadImage_Intenet);//修改图片的查看方式：网络|本地
			getContext().startActivity(intentOne);
			System.out.println("你点击了第" + position + "张的Gallery图片" + "\n" + "图片路径为：" + picFilePath);
		}
	}

	/**
	 * 图片的删除
	 * 
	 * @author ouyyt
	 * 
	 */
	private class MyGalleryOnItemLongClickListener implements
			OnItemLongClickListener {

		@Override
		public boolean onItemLongClick(AdapterView<?> parent, View view,
				int position, long id) {
			// TODO Auto-generated method stub
			final int p = position;
			// 确认对话框
			final AlertDialog isExit = new AlertDialog.Builder(getContext())
					.create();
			// 对话框标题
			isExit.setTitle(getContext().getString(R.string.titleMessage));
			isExit.setIcon(R.drawable.ic_launcher);
			// 对话框消息
			isExit.setMessage(getContext().getString(R.string.sureDelete));
			// 实例化对话框上的按钮点击事件监听
			DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					switch (which) {
					case AlertDialog.BUTTON1:// "确认"按钮删除图片
						String picFilePath = (String) ls.get(p).get("picFilePath");
						// 删除数据库的记录
						if(FrameDemoActivity.db.deletePhotoListByPath(picFilePath)){
						    // 删除文件中的图片
							File picfile = new File(picFilePath);
							if(picfile.exists()) {
								picfile.delete();
								ls.remove(p);
							}
						}
						sa.setLs(ls);
						sa.notifyDataSetChanged();
						System.out.println("你删除了第" + p + "张的Gallery图片" + "\n" + "图片路径为：" + picFilePath);
						break;
					case AlertDialog.BUTTON2:// "取消"第二个按钮取消对话框
						isExit.cancel();
						break;
					default:
						break;
					}
				}
			};
			// 注册监听
			isExit.setButton(getContext().getString(R.string.makeSure),
					listener);
			isExit.setButton2(getContext().getString(R.string.cancel), listener);
			// 显示对话框
			isExit.show();
			return true;
		}
	}

	//图片的  缩放---删除  事件
	private void galleryClick(Gallery gallery){
		gallery.setOnItemClickListener(new MyGalleryOnItemClickListener());
		gallery.setOnItemLongClickListener(new MyGalleryOnItemLongClickListener());
	}
	
	/**
	 * 表体Dialog列表数据
	 * @param leftView
	 * @param rightView
	 */
 	private void addListView(View leftView, View rightView) {
		mlv_RM1I3_leftLV.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				mlv_RM1I3_rightLV.onTouchEvent(event);
				return false;
			}
		});

		mlv_RM1I3_rightLV.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {

				mlv_RM1I3_leftLV.dispatchTouchEvent(event);
				return false;
			}
		});
		ll_RM1I3_lv_main_left.addView(leftView);
		ll_RM1I3_lv_main_right.addView(rightView);
	}

	@Override
	public void dosomething() {

	}

	@Override
	public void dosomething2() {

	}

	/**
	 * 获取报关单表头信息
	 */
	class MyAsynTask extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			FrameDemoActivity.toolUtils.startProgressDialog();
		}

		@SuppressWarnings("static-access")
		@Override
		protected String doInBackground(String... params) {

			// 获得报关单表头--获取报关单信息(简单，不去查询RISKH2000库)
			String reqResult = "";
			FrameDemoActivity.webservice.setMETHOD_NAME("GetBGDBTSimInfo");
			FrameDemoActivity.webservice
					.setSOAP_ACTION("http://tempuri.org/IGetExamDataService/GetBGDBTSimInfo");
			if (FrameDemoActivity.webservice.connect(getContext(), new String[] { "entry_id" },
					new Object[] { params[0] })) {
				reqResult = FrameDemoActivity.webservice.getResult().toString();
				//写服务器的Log日志
				if(reqResult!=null && !reqResult.equals("")){
					FrameDemoActivity.toolUtils.writeDataLog("获取报关单成功。", "86", params[0]);
				}
			}
			
			//ENTRY_HEAD表头 测试 本地数据
			if(reqResult == null || reqResult.equals("")){
				TestAddData test = new TestAddData();
				reqResult = test.str2;
			}
			
			return reqResult;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if (result.equals("")) {
				FrameDemoActivity.toolUtils.promptMessage("报关单不存在。");
			} else if (result != null && !result.equals("")) {
				entryHead = new ENTRY_HEAD();
				// 获得表头信息
				entryHead = FrameDemoActivity.gson.fromJson(result,
						ENTRY_HEAD.class);
				showEntryHeadResult();
				// 测试数据
				entryNumber = "220120111010151633";
				String lonNum = FrameDemoActivity.userInfo.getLobNumber();// 工号
				new MyAsynTaskG_HEAD().execute(entryNumber, lonNum);// 判断是否上传的操作
			}
			FrameDemoActivity.toolUtils.endProgressDialog();
		}

	};

	/**
	 * 获取G_HEAD报关单信息(若不存在，则插入G_HEAD) 报关单号、工号
	 */
	class MyAsynTaskG_HEAD extends AsyncTask<String, String, String> {
	
		@Override
		protected String doInBackground(String... params) {

			// 获取G_HEAD报关单信息(若不存在，则插入G_HEAD)
			String reqResult = "";
			FrameDemoActivity.webservice.setMETHOD_NAME("GetGHeadInfo");
			FrameDemoActivity.webservice
					.setSOAP_ACTION("http://tempuri.org/IGetExamDataService/GetGHeadInfo");
			if (FrameDemoActivity.webservice.connect(getContext(), new String[] { "entry_id",
					"uploader" }, new Object[] { params[0], params[1] })) {
				reqResult = FrameDemoActivity.webservice.getResult().toString();
			}
			return reqResult;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if (result.equals("")) {
				FrameDemoActivity.toolUtils.promptMessage("报关单不存在。");
				FrameDemoActivity.toolUtils.endProgressDialog();
			} else if (result != null && !result.equals("")) {
				gHead = new G_HEAD();
				// 获得表头信息
				gHead = FrameDemoActivity.gson.fromJson(result, G_HEAD.class);
				// 报关单是否 的状态进行判断 （上传|重上传|退回上传）
				isG_HeadUpload();
			}
		}

	};

	// 判断是否 上传过------表头-----图片的显示、控件的控制
	private void isG_HeadUpload() {
		if (gHead != null && !gHead.equals("")) {
			System.out.println(gHead.getENTRY_ID() + "---" + gHead.getOP_ID());
			String opID = gHead.getOP_ID();// 状态（工作流类型）
			//opID = "22";// 测试数据
			// gHead.setSH_ALARM("4");//测试数据
			
			String entryId="";//报关单号
			String gNo="";//项号
			if(doType.equals("HEAD")){//表头
				entryId=entryList.getENTRY_ID();
				gNo="0";
			}else if(doType.equals("BODY")){//表体
				entryId=entryHead.getENTRY_ID();
				gNo=entryList.getG_NO()+"";
			}
			if (opID == null || opID.equals("") || opID.equals("00")) { // 未上传操作
				// 未上传操作
			} else if (opID.equals("22") && gHead.getSH_ALARM().equals("4")) { // 重上传
				// 先下载，以前的图片 展示，
				//测试参数
				entryId="6666666666";
				gNo="0";
				new MyAsynTaskImageDownload().execute(entryId,gNo);
			} else if (opID.equals("21")) {// 退回操作；则可以上传
				// 先下载，以前的图片 展示，
				//测试参数
				entryId="6666666666";
				gNo="0";
				new MyAsynTaskImageDownload().execute(entryId,gNo);
			} else {
				// 只浏览图片R.drawable.show_btn_bg_up_pic.9.png
				onlyLookImageChangeUI();//表头的处理
			}
		}
	}

	// 判断是否 上传过--表头上传图片---------上传的时候用
	private void G_HeadUploadImage() {
		if (gHead != null && !gHead.equals("")) {
			System.out.println(gHead.getENTRY_ID() + "---" + gHead.getOP_ID());
			String opID = gHead.getOP_ID();// 状态（工作流类型）
//			opID = "22";// 测试数据
//			gHead.setSH_ALARM("4");// 测试数据
			if (opID == null || opID.equals("") || opID.equals("00")) { // 未上传操作
				// 未上传操作
				new MyMakeSureUploadTask().execute(gHead.getENTRY_ID(),FrameDemoActivity.myApp.userInfo.getLobNumber(),"10");
			} else if ((opID.equals("22") && gHead.getSH_ALARM().equals("4")) || opID.equals("21")) { // 重上传、退回操作；则可以上传
				new MyMakeSureUploadTask().execute(gHead.getENTRY_ID(),FrameDemoActivity.myApp.userInfo.getLobNumber(),"11");
			} 
		}
	}
	/**
	 * 确定上传的操作
	 * @author zhangyx
	 *
	 */
	class MyMakeSureUploadTask extends AsyncTask<String, String, String>{

		@Override
		protected String doInBackground(String... params) {
			FrameDemoActivity.webservice.setMETHOD_NAME("UpdateUpload");
			FrameDemoActivity.webservice
					.setSOAP_ACTION("http://tempuri.org/IGetExamDataService/UpdateUpload");
			if (FrameDemoActivity.webservice.connect(getContext(), new String[] { "entry_id","upload_er","op_id" },
					new Object[] { params[0],params[1],params[2] })) {
			}
			return null;
		}
		
	};
	
	/**
	 * 获取WorkFlow信息
	 */
	class MyAsynTaskG_WorkFlow extends AsyncTask<String, String, String> {
		
		@Override
		protected String doInBackground(String... params) {

			// 获取WorkFlow信息
			String reqResult = "";
			FrameDemoActivity.webservice.setMETHOD_NAME("GetWorkFlowInfo");
			FrameDemoActivity.webservice
					.setSOAP_ACTION("http://tempuri.org/IGetExamDataService/GetWorkFlowInfo");
			if (FrameDemoActivity.webservice.connect(getContext(), new String[] { "op_id",
					"entry_id" }, new Object[] { params[0], params[1] })) {
				reqResult = FrameDemoActivity.webservice.getResult().toString();
			}
			return reqResult;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			
			if (result.equals("")) {
				FrameDemoActivity.toolUtils.promptMessage("报关单不存在。");
				FrameDemoActivity.toolUtils.endProgressDialog();
			} else if (result != null && !result.equals("")) {
				try {
					JSONArray jsonArray = new JSONArray(result);
					for (int i = 0; i < jsonArray.length(); i++) {
						gWorkFlow = new G_WORKFLOW();
						JSONObject obj = jsonArray.getJSONObject(i);
						gWorkFlow.setENTRY_ID(obj.getString("ENTRY_ID"));
						gWorkFlow.setOP_ER(obj.getString("OP_ER"));
						gWorkFlow.setOP_ID(obj.getString("OP_ID"));
						gWorkFlow.setOP_NOTE(obj.getString("OP_NOTE"));
						gWorkFlow.setOP_TIME(obj.getString("OP_TIME"));
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}

	};

	/***
	 * 表体上传--获得报关单表体
	 * 
	 * @author zhangyx
	 * 
	 */
	class MyAsynTaskEntrys extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			FrameDemoActivity.toolUtils.startProgressDialog();
		}

		@SuppressWarnings("static-access")
		@Override
		protected String doInBackground(String... params) {
			// 表体上传--获得报关单表体
			String reqResult = "";
			FrameDemoActivity.webservice.setMETHOD_NAME("GetGListInfo");
			FrameDemoActivity.webservice
					.setSOAP_ACTION("http://tempuri.org/IGetExamDataService/GetGListInfo");
			if (FrameDemoActivity.webservice.connect(getContext(), new String[] { "entry_id" },
					new Object[] { params[0] })) {
				reqResult = FrameDemoActivity.webservice.getResult().toString();
				System.out.println("表体上传--获得报关单表体 ：" + reqResult);
			}
			
			//ENTRY_LIST表体 测试 本地数据
			if(reqResult == null || reqResult.equals("")){
				TestAddData test = new TestAddData();
				reqResult=test.str3;
			}
			return reqResult;
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if (result.equals("")) {
				FrameDemoActivity.toolUtils.promptMessage("报关单不存在。");
			} else if (result != null && !result.equals("")) {
				entrysList.clear();
				entrysList.addAll(((List) FrameDemoActivity.gson.fromJson(
						result, new TypeToken<List<ENTRY_LIST>>() {
						}.getType())));
				showEntryBodyResult();
			}
			FrameDemoActivity.toolUtils.endProgressDialog();
		}

	};

	/**
	 * 表体的集合显示
	 */
	private void showEntryBodyResult() {
		M1I3LeftAdapter.notifyDataSetChanged();
		M1I3RightAdapter.notifyDataSetChanged();
	}

	/**
	 * 上传表头的赋值等操作
	 */
	private void showEntryHeadResult() {
		if (entryHead != null && !entryHead.equals("")) {
			((TextView) layout.findViewById(R.id.tv_RM1I3_text1))
					.setText(entryHead.getENTRY_ID());
			((TextView) layout.findViewById(R.id.tv_RM1I3_text6))
					.setText(entryHead.getTRADE_NAME());
			((TextView) layout.findViewById(R.id.tv_RM1I3_text7))
					.setText(entryHead.getTRAF_MODE());
			((TextView) layout.findViewById(R.id.tv_RM1I3_text8))
					.setText(entryHead.getTRAF_NAME());
			((TextView) layout.findViewById(R.id.tv_RM1I3_text9))
					.setText(entryHead.getBILL_NO());
			((TextView) layout.findViewById(R.id.tv_RM1I3_text10))
					.setText(entryHead.getOWNER_NAME());
		}
	}

	/**
	 * 上传表体的赋值等操作
	 */
	private void uploadEntryList() {
		if (entryList != null && !entryList.equals("")) {
			((TextView) layout.findViewById(R.id.tv_uploadPic_text1))
					.setText(entryList.getENTRY_ID());
			((TextView) layout.findViewById(R.id.tv_uploadPic_text2))
					.setText(entryList.getG_NO() + "");
			((TextView) layout.findViewById(R.id.tv_uploadPic_text3))
					.setText(entryList.getCODE_TS());
			((TextView) layout.findViewById(R.id.tv_uploadPic_text4))
					.setText(entryList.getG_NAME());
		}
	}

	// 下载图片的相关信息
	class MyAsynTaskImageDownload extends AsyncTask<String, String, String> {
		
		@Override
		protected void onPreExecute() {
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
						String savePath=Constant.SAVEPATH + p.getENTRY_ID() + "/" + p.getG_NO()
								+ "/" + p.getPHOTO_CODE() + "/";
						Bitmap bitmap=FrameDemoActivity.imageUtil.downloadBitmapByCwj(reqPath);//下载图片
						byte[] buffer=FrameDemoActivity.imageUtil.bitmapTobyte(bitmap);
						//写入SDCard---图片写入
						FrameDemoActivity.imageUtil.downloadImage(buffer,imageName, savePath);//写入SDCard
						//移动数据库操作
						EntryList entryList=FrameDemoActivity.db.selectEntryListByEntry(p.getENTRY_ID());
						if(entryList==null || entryList.equals("")){
							//保存到移动数据库
							FrameDemoActivity.db.insertToEntry_LIST(p.getENTRY_ID(), p.getG_NO(), 0, "");
						}
						FrameDemoActivity.db.insertToPhoto_list(p.getENTRY_ID(), p.getG_NO(), p.getPHOTO_CODE(), savePath+imageName);
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
				Toast.makeText(getContext(), result.get("reqResult"), Toast.LENGTH_SHORT).show();
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
			if(photoCode.equals(Constant.CONTAINER_BODY)){//集装箱箱体  照片--表头
				loadPicDataAgain(savePath, gl_RM1I3_containerPic);
			}else if(photoCode.equals(Constant.GOODSTACK_SITUATION)){//货物堆放情况  照片--表头
				loadPicDataAgain(savePath, gl_RM1I3_goodsPic);
			}else if(photoCode.equals(Constant.CONTAINER_MARK)){// 集装箱标志    照片--表头
				loadPicDataAgain(savePath, gl_RM1I3_containerInfoPic);
			}else if(photoCode.equals(Constant.GOODS_PACKAGE)){// 货物包装   照片--表体1
				loadPicDataAgain(savePath, gl_RM1I3_goodsPackagePhoto);
			}else if(photoCode.equals(Constant.GOODS)){//商品近照----表体2
				loadPicDataAgain(savePath, gl_RM1I3_goodsPhoto);
			}else if(photoCode.equals(Constant.CONTAINER_INNER)){// 集装箱内景    照片-----表体3
				loadPicDataAgain(savePath, gl_RM1I3_containerInnerPhoto);
			}else if(photoCode.equals(Constant.GOODS_SPECIFICATION_MODEL)){// 商品规格型号    照片--表体4
				loadPicDataAgain(savePath, gl_RM1I3_goodsSpecModelPhoto);
			}else if(photoCode.equals(Constant.ANOTHER_SPECIAL_REQUEST)){//其他特殊要求照-----表体5
				loadPicDataAgain(savePath, gl_RM1I3_anotherSpecialRequestPhoto);
			}else if(photoCode.equals(Constant.SCENE_SRC)){//现场认定资料照-----表体6
				loadPicDataAgain(savePath, gl_RM1I3_sceneSrcPhoto);
			}
		}
	}
	
	//下载图片只展示----表头
	private void onlyLookImageChangeUI(){
		layout_RM1I3Second_show_1
		.setBackgroundResource(R.drawable.show_btn_bg_up_pic);
		layout_RM1I3Second_show_2
		.setBackgroundResource(R.drawable.show_btn_bg_up_pic);
		layout_RM1I3Second_show_3
		.setBackgroundResource(R.drawable.show_btn_bg_up_pic);
		IB_RM1I3_takeCameraOne.setVisibility(View.GONE);
		IB_RM1I3_takeCameraTwo.setVisibility(View.GONE);
		IB_RM1I3_takeCameraThree.setVisibility(View.GONE);
	}
	
	// 视频刻录-----上传表体页面
 	private void toRecorderVideo() {

		AlertDialog.Builder alert = new Builder(getContext());
		alert.setTitle("温馨提示");
		alert.setIcon(R.drawable.ic_launcher);
		alert.setMessage("视频文件太长，可能无法在移动端上传，建议PC机上传，确定继续刻录？");
		alert.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
				intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0.5);
				intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 7000);
				((Activity) getContext()).startActivityForResult(intent,
						Constant.RECORDER_VIDEO);
			}
		});
		alert.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
			
			}
		});
		alert.create();
		alert.show();
		return;

	}

}