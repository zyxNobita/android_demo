package cn.tydic.mobile.pdarequery.activity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.reflect.TypeToken;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;
import cn.tydic.Zxing.camera.CaptureActivity;
import cn.tydic.mobile.pdarequery.MainActivity;
import cn.tydic.mobile.pdarequery.R;
import cn.tydic.mobile.pdarequery.adapter.BusinessAcceptanceGvAdapter;
import cn.tydic.mobile.pdarequery.adapter.MyCheckBoxAdapter;
import cn.tydic.mobile.pdarequery.entity.Frm_Code;
import cn.tydic.mobile.pdarequery.image.ToImageZoomActivity;
import cn.tydic.mobile.pdarequery.tools.Base64;
import cn.tydic.mobile.pdarequery.tools.Constant;
import cn.tydic.mobile.pdarequery.tools.InputLengthControler;
import cn.tydic.mobile.pdarequery.tools.RectPhoto;

/***
 * 查验业务受理-------PDA
 * 
 */
public class BusinessAcceptanceActivity extends Activity {

	private EditText etScanner,et_scannerByHandInput, etCarColor;// 扫描 | 手动输入排队号|选择业务类型，选择车辆颜色
	private Integer ET_MAXLENGTH=4;//手动输入的排队号4位
	private GridView baGv;
	private CheckBox cbOne, cbTwo,cb_scanner,cb_scannerByHandInput;// 人工判断项目 | 排队号得区分
	private ImageButton imgBtnQueryCar, imgBtnQueryNotice, imgBtnSubmit,
			imgBtnReplace;// 查车辆，查公告，提交，重置
	private ImageView imgBtnExit;// 退出查验业务受理界面
	private LinearLayout llQueryCar, llQueryNotice, llSubmit, llReplace,
			llBottom, ll_sCarType;
	private BusinessAcceptanceGvAdapter baGvAdapter;
	// 以下字段数据对Spinner的操作
	private Spinner sp_carUseWay, sp_carType, sp_businessType, sp_carSignKind,
			sp_comments;// 车辆用途|车辆类型|业务类型|号牌种类|备注
	private ArrayAdapter<String> sp_carUseWayAdapter, sp_carTypeAdapter,
			sp_businessTypeAdapter, sp_carSignKindAdapter, sp_commentsAdapter;
	private List<Frm_Code> dataList1 = new ArrayList<Frm_Code>();// 车辆用途代码值
	private List<String> dataLists1 = new ArrayList<String>();
	private List<Frm_Code> dataList3 = new ArrayList<Frm_Code>();// 号牌种类代码值
	private List<String> dataLists3 = new ArrayList<String>();
	private List<Frm_Code> dataList4 = new ArrayList<Frm_Code>();// 备注
	private List<String> dataLists4 = new ArrayList<String>();
	private CheckBox cb_special, cb_public;// 校车类型：专用|非专用
	private String queueNum;
	// 根据条件查询饿Spinner控件显示
	private List<Frm_Code> carTypes = new ArrayList<Frm_Code>();// 车辆类型
	private List<String> carType = new ArrayList<String>();
	private List<Frm_Code> bzTypes = new ArrayList<Frm_Code>();// 业务类型
	private List<String> bzType = new ArrayList<String>();
	private List<Frm_Code> xmLists = new ArrayList<Frm_Code>();// 查验项目
	// 车辆颜色的处理
	public static List<Frm_Code> dataList2 = new ArrayList<Frm_Code>();// 车身颜色代码值
	//public static Map<String,String> csysMap=new HashMap<String,String>();
	private String colorRes = "";// 选择颜色的结果
	private PopupWindow popup;
	private View popupView;
	private GridView gv_content;
	private MyCheckBoxAdapter adapter;
	// 拍摄照片的处理
	private ImageView img_carCodeName, img_car, img_another;// 车连识别代号照片|整车照片|其他照片
	private Handler handler;
	private String imgPath1, imgPath2, imgPath3;// 车辆识别代号照片 | 整车照片 | 其他照片
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.businessacceptance);
		initView();
		initEvent();
	}

	private void initView() {
		etScanner = (EditText) findViewById(R.id.et_businessAcceptance_scanner);
		et_scannerByHandInput=(EditText) findViewById(R.id.et_scannerByHandInput);
		etCarColor = (EditText) findViewById(R.id.et_businessAcceptance_carColor);
		//限制号牌号码的长度
		InputLengthControler ilc=new InputLengthControler();
		ilc.config(et_scannerByHandInput, ET_MAXLENGTH);
		
		baGv = (GridView) findViewById(R.id.gv_businessAcceptance);
		baGvAdapter = new BusinessAcceptanceGvAdapter(
				BusinessAcceptanceActivity.this, xmLists);
		baGv.setAdapter(baGvAdapter);

		cbOne = (CheckBox) findViewById(R.id.cb_businessAcceptance_one);
		cbTwo = (CheckBox) findViewById(R.id.cb_businessAcceptance_two);

		cb_scanner=(CheckBox) findViewById(R.id.cb_scanner);
		cb_scannerByHandInput=(CheckBox) findViewById(R.id.cb_scannerByHandInput);
		
		imgBtnQueryCar = (ImageButton) findViewById(R.id.imgBtn_businessAcceptance_queryCarInfo);
		imgBtnQueryNotice = (ImageButton) findViewById(R.id.imgBtn_businessAcceptance_queryNoticeInfo);
		imgBtnSubmit = (ImageButton) findViewById(R.id.imgBtn_businessAcceptance_submit);
		imgBtnReplace = (ImageButton) findViewById(R.id.imgBtn_businessAcceptance_replace);

		llQueryCar = (LinearLayout) findViewById(R.id.ll_businessAcceptance_queryCarInfo);
		llQueryNotice = (LinearLayout) findViewById(R.id.ll_businessAcceptance_queryNoticeInfo);
		llSubmit = (LinearLayout) findViewById(R.id.ll_businessAcceptance_submit);
		llReplace = (LinearLayout) findViewById(R.id.ll_businessAcceptance_replace);
		llBottom = (LinearLayout) findViewById(R.id.ll_businessAcceptance_bottom);
		imgBtnExit = (ImageView) findViewById(R.id.imgBtn_businessAcceptance_exit);

		ll_sCarType = (LinearLayout) findViewById(R.id.ll_sCarType);
		// 对初始化下拉数据的处理
		initSpinnerData();
		// 校车类型
		cb_special = (CheckBox) findViewById(R.id.cb_special);
		cb_public = (CheckBox) findViewById(R.id.cb_public);
	}

	/**
	 * 对初始化下拉数据的处理
	 * sp_carUseWay,sp_carType,sp_businessType,sp_carSignKind;//车辆用途|
	 * 车辆类型|业务类型|号牌种类
	 */
	private void initSpinnerData() {
		sp_carUseWay = (Spinner) findViewById(R.id.sp_carUseWay);
		sp_carType = (Spinner) findViewById(R.id.sp_carType);
		sp_businessType = (Spinner) findViewById(R.id.sp_businessType);
		sp_carSignKind = (Spinner) findViewById(R.id.sp_carSignKind);
		sp_comments = (Spinner) findViewById(R.id.sp_comments);
		// 车辆用途
		sp_carUseWayAdapter = new ArrayAdapter<String>(getApplicationContext(),
				R.layout.query_car_spinner_item, dataLists1);
		sp_carUseWayAdapter
				.setDropDownViewResource(R.layout.query_car_spinner_item);
		sp_carUseWay.setAdapter(sp_carUseWayAdapter);
		// 号牌种类
		sp_carSignKindAdapter = new ArrayAdapter<String>(
				getApplicationContext(), R.layout.query_car_spinner_item,
				dataLists3);
		sp_carSignKindAdapter
				.setDropDownViewResource(R.layout.query_car_spinner_item);
		sp_carSignKind.setAdapter(sp_carSignKindAdapter);
		// 备注
		sp_commentsAdapter = new ArrayAdapter<String>(getApplicationContext(),
				R.layout.query_car_spinner_item, dataLists4);
		sp_commentsAdapter
				.setDropDownViewResource(R.layout.query_car_spinner_item);
		sp_comments.setAdapter(sp_commentsAdapter);
		// 车辆类型
		sp_carTypeAdapter = new ArrayAdapter<String>(getApplicationContext(),
				R.layout.query_car_spinner_item, carType);
		sp_carTypeAdapter
				.setDropDownViewResource(R.layout.query_car_spinner_item);
		sp_carType.setAdapter(sp_carTypeAdapter);
		// 业务类型
		sp_businessTypeAdapter = new ArrayAdapter<String>(
				getApplicationContext(), R.layout.query_car_spinner_item,
				bzType);
		sp_carTypeAdapter
				.setDropDownViewResource(R.layout.query_car_spinner_item);
		sp_businessType.setAdapter(sp_businessTypeAdapter);

		// 初始化服务器的数据
		if (Constant.user != null) {
			Map<String, String> conMap = new HashMap<String, String>();
			conMap.put("yhdh", Constant.user.getUserName());
			conMap.put("phone_id", Constant.user.getPhoneId());
			new MyAsyncTaskREQInitSpinnerData().execute(MainActivity.gson
					.toJson(conMap));
		} else {
			Toast.makeText(getApplicationContext(), "您好，请先登陆。",
					Toast.LENGTH_SHORT).show();
		}
		// 获取业务类型 的数据 
		sp_carUseWay.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				System.out.println("车辆用途----选择了..." + arg2 + "提交的值--"
						+ dataList1.get(arg2).getDmz());
				//清理数据
				bzTypes.clear();
				bzType.clear();
				sp_businessTypeAdapter.notifyDataSetChanged();
				// //{'yhdh':'tydic','phone_id':'1','clyt':'A'}---车辆类型
				if (Constant.user != null) {
					// 对校车的处理
					if (!dataList1.get(arg2).getDmz().equals("")) {
						if (dataList1.get(arg2).getDmz().equals("B")) {
							ll_sCarType.setVisibility(View.VISIBLE);
						} else {
							ll_sCarType.setVisibility(View.GONE);
						}
					}
					// 获取业务类型数据|车辆类型数据
					Map<String, String> conMap = new HashMap<String, String>();
					conMap.put("yhdh", Constant.user.getUserName());
					conMap.put("phone_id", Constant.user.getPhoneId());
					conMap.put("clyt", dataList1.get(arg2).getDmz());
					// 业务类型的接口调用
					new MyAsyncTaskREQbzType().execute(MainActivity.gson
							.toJson(conMap));
				} else {
					Toast.makeText(getApplicationContext(), "您好，请先登陆。",
							Toast.LENGTH_SHORT).show();
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
		// 获取 车辆类型 的数据  
		sp_businessType.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
//						System.out.println("车辆用途----选择了..." + arg2 + "提交的值--"
//								+ dataList1.get(arg2).getDmz());
						//数据清理
						carType.clear();
						sp_carTypeAdapter.notifyDataSetChanged();
						// //{'yhdh':'tydic','phone_id':'1','clyt':'A'}---车辆类型
						if (Constant.user != null) {
							// 对校车的处理
							String car=sp_carUseWay.getSelectedItem().toString();
							car=car.substring(0, car.indexOf(":"));
							if (!car.equals("")) {
								if (car.equals("B")) {
									//----
									if(cb_special.isChecked()){
										cb_public.setChecked(false);
									}
									if(cb_public.isChecked()){
										cb_special.setChecked(false);
									}
									ll_sCarType.setVisibility(View.VISIBLE);
								} else {
									ll_sCarType.setVisibility(View.GONE);
								}
							}
							if(bzType.size()>0){
								String bz=sp_businessType.getSelectedItem().toString();
								bz=bz.substring(0, bz.indexOf(":"));
								// 获取业务类型数据|车辆类型数据
								Map<String, String> conMap = new HashMap<String, String>();
								conMap.put("yhdh", Constant.user.getUserName());
								conMap.put("phone_id", Constant.user.getPhoneId());
								conMap.put("clyt", car);
								conMap.put("ywlx", bz);
								if(bz!=null && !bz.equals("")){
									// 车辆类型的接口调用
									System.out.println("车辆类型的接口调用请求条件---"+MainActivity.gson
											.toJson(conMap));
									new MyAsyncTaskREQCarType().execute(MainActivity.gson
											.toJson(conMap));
								}
							}
						} else {
							Toast.makeText(getApplicationContext(), "您好，请先登陆。",
									Toast.LENGTH_SHORT).show();
						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub

					}
				});
	
	}

	private void initEvent() {
		etScanner.setInputType(InputType.TYPE_NULL);
		etScanner.setOnClickListener(new MyClickListener());

		initCarColor();// 车辆颜色的操作

		doTakePicture();// 拍摄照片功能的处理

		findViewById(R.id.ll_businessAcceptance_return).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						finish();
						overridePendingTransition(R.anim.push_right_in,
								R.anim.push_right_out);
					}
				});

		imgBtnQueryCar.setOnClickListener(new MyClickListener());
		imgBtnQueryNotice.setOnClickListener(new MyClickListener());
		imgBtnSubmit.setOnClickListener(new MyClickListener());
		imgBtnReplace.setOnClickListener(new MyClickListener());
		llBottom.setOnClickListener(new MyClickListener());
		// 点击获取查验项目
		findViewById(R.id.btn_query).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// System.out.println("车辆用途----选择了..."+arg2+"提交的值--"+dataList1.get(arg2).getDmz());
				//清理数据
				xmLists.clear();
				baGvAdapter.notifyDataSetChanged();
				if (bzType.size() > 0 && carType.size() > 0) {
					String s1 = sp_carUseWay.getSelectedItem().toString()
							.trim();
					s1 = s1.subSequence(0, s1.indexOf(":")).toString();
					String s2 = sp_businessType.getSelectedItem().toString()
							.trim();
					s2 = s2.subSequence(0, s2.indexOf(":")).toString();
					String s3 = sp_carType.getSelectedItem().toString().trim();
					s3 = s3.subSequence(0, s3.indexOf(":")).toString();
					System.out.println("点击获取查验项目----" + s1 + "----" + s2
							+ "----" + s3);
					// MyAsyncTaskREQ_XM
					// {'yhdh':'tydic','phone_id':'1','clyt':'B','ywlx':'BB','cllx':'B17'}
					if (s1 != null && !s1.equals("") && s2 != null
							&& !s2.equals("") && s3 != null && !s3.equals("")) {
						Map<String, String> conMap = new HashMap<String, String>();
						conMap.put("yhdh", Constant.user.getUserName());
						conMap.put("phone_id", Constant.user.getPhoneId());
						conMap.put("clyt", s1);
						conMap.put("ywlx", s2);
						conMap.put("cllx", s3);
						// 点击查验项目
						new MyAsyncTaskREQ_XM().execute(MainActivity.gson
								.toJson(conMap));
					}
				} else {
					Toast.makeText(getApplicationContext(),
							"您好，查询的条件不充分，无法查询。", Toast.LENGTH_LONG).show();
				}
			}
		});

		// Handler事件处理更新UI界面
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				switch (msg.what) {
				case 0:// 菜单按钮的点击效果
					changeClick();
					break;
				}
			}
		};
		//设置排队号得判断依据
		cb_scanner.setChecked(true);//默认扫描
		cb_scannerByHandInput.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(cb_scannerByHandInput.isChecked()){
					etScanner.setText("");//设置扫描显示的清空数据
					etScanner.setVisibility(View.INVISIBLE);
					et_scannerByHandInput.setVisibility(View.VISIBLE);
					cb_scanner.setChecked(false);
				}else{
					etScanner.setVisibility(View.VISIBLE);
					et_scannerByHandInput.setVisibility(View.INVISIBLE);
					cb_scanner.setChecked(true);
				}
			}
		});
		
		cb_scanner.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(cb_scanner.isChecked()){
					etScanner.setVisibility(View.VISIBLE);
					et_scannerByHandInput.setText("");
					et_scannerByHandInput.setVisibility(View.INVISIBLE);
					cb_scannerByHandInput.setChecked(false);
				}else{
					et_scannerByHandInput.setVisibility(View.VISIBLE);
					etScanner.setVisibility(View.INVISIBLE);
					cb_scannerByHandInput.setChecked(true);
				}
			}
		});
	
		//校车类型：
		cb_special = (CheckBox) findViewById(R.id.cb_special);
		
		cb_special.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO Auto-generated method stub
				if(cb_special.isChecked()){
					cb_public.setChecked(false);
				}else{
					cb_public.setChecked(true);
				}
			}
		});
		cb_public.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO Auto-generated method stub
				if(cb_public.isChecked()){
					cb_special.setChecked(false);
				}else{
					cb_special.setChecked(true);
				}
			}
		});

	}

	/***
	 * 拍摄照片功能的处理
	 */
	private void doTakePicture() {
		// TODO Auto-generated method stub
		img_carCodeName = (ImageView) findViewById(R.id.img_carCodeName);
		img_car = (ImageView) findViewById(R.id.img_car);
		img_another = (ImageView) findViewById(R.id.img_another);

		findViewById(R.id.btn_carCodeNamePic).setOnClickListener(
				new MyTakePictureListener());// 车辆识别代号照片
		findViewById(R.id.btn_carPic).setOnClickListener(
				new MyTakePictureListener());// 整车照片
		findViewById(R.id.btn_anotherPic).setOnClickListener(
				new MyTakePictureListener());// 其他照片
		
		img_carCodeName.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(imgPath1!=null && !imgPath1.equals("")){
					toLocalImage(imgPath1);
				}else{
					Toast.makeText(getApplicationContext(), "您还没有拍摄照片，请先拍摄照片，谢谢。", Toast.LENGTH_LONG).show();
				}
			}
		});
		img_car.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(imgPath2!=null && !imgPath2.equals("")){
					toLocalImage(imgPath2);
				}else{
					Toast.makeText(getApplicationContext(), "您还没有拍摄照片，请先拍摄照片，谢谢。", Toast.LENGTH_LONG).show();
				}
			}
		});
		
		img_another.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(imgPath3!=null && !imgPath3.equals("")){
					toLocalImage(imgPath3);
				}else{
					Toast.makeText(getApplicationContext(), "您还没有拍摄照片，请先拍摄照片，谢谢。", Toast.LENGTH_LONG).show();
				}
			}
		});

	}
	/****
	 * 本地图片放大
	 * @param localPath
	 */
	private void toLocalImage(String localPath){
		//Intent intentOne = new Intent(BusinessAcceptanceActivity.this, LocalPicZoomActivity.class);
		Intent intentOne = new Intent(BusinessAcceptanceActivity.this,ToImageZoomActivity.class);
		intentOne.putExtra("imagePath", localPath);
		intentOne.putExtra("PATH_TYPE", Constant.LoadImage_Local);
		startActivity(intentOne);
	}
	
	/****
	 * 拍摄照片功能的事件监听类
	 * 
	 * @author zhangyx
	 * 
	 */
	class MyTakePictureListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.btn_carCodeNamePic:// 车辆识别代号照片
				//getPicFromCapture();
				startActivityForResult(new Intent(getApplicationContext(),RectPhoto.class), Constant.CAMERA_WITH_DATA);
				break;
			case R.id.btn_carPic:// 整车照片
				startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), Constant.RC_CAR_PIC);
				//imgPath2=getPhoto(Constant.RC_CAR_PIC);
				break;
			case R.id.btn_anotherPic:// 其他照片
				startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), Constant.RC_OTHER_PIC);
				//imgPath3=getPhoto(Constant.RC_OTHER_PIC);
				break;
			}
		}
	};
	
	/**
	 * 车辆颜色的操作
	 */
	private void initCarColor() {
		// TODO Auto-generated method stub
		etCarColor.setInputType(InputType.TYPE_NULL);
		popupView = LayoutInflater.from(getApplicationContext()).inflate(
				R.layout.myckeck_popu, null);
		// 创建一个PopupWindow对象
		popup = new PopupWindow(popupView, 300, 450);

		gv_content = (GridView) popupView.findViewById(R.id.gv_content);
		adapter = new MyCheckBoxAdapter(getApplicationContext(), dataList2);
		gv_content.setAdapter(adapter);

		etCarColor.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 下拉方式显示
				if(dataList2.size()>0){
					popup.showAsDropDown(v);
					for(Frm_Code f:dataList2){
						f.setPrefix("0");
					}
					// 将PopupWindow显示在指定位置
					popup.showAtLocation(etCarColor, Gravity.CENTER, 20, 20);
					adapter.notifyDataSetChanged();
					etCarColor.setText("");
					colorRes="";
				}
			}
		});

		// 获取popup窗口中的关闭按钮
		popupView.findViewById(R.id.close).setOnClickListener(
				new OnClickListener() {

					public void onClick(View v) {
						// TODO Auto-generated method stub
						if(dataList2.size()>0){
							for(Frm_Code f:dataList2){
								if(f.getPrefix().equals("1")){
									colorRes+=f.getDmsm1()+",";
								}
							}
						}
						if (!colorRes.equals("")) {
							// colorRes.replaceAll(colorRes.charAt(colorRes.lastIndexOf("、")),
							// "");
							System.out.println("选择的值是----"
									+ colorRes.subSequence(0,
											colorRes.length() - 1));
							etCarColor.setText(colorRes.subSequence(0,
									colorRes.length() - 1));
						}
						popup.dismiss();
					}
				});
	}

	class MyClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			int vId = v.getId();
			changeClick();
			switch (vId) {
			case R.id.et_businessAcceptance_scanner:// 扫描
				startActivityForResult(
						new Intent(BusinessAcceptanceActivity.this,
								CaptureActivity.class), Constant.RC_SCANNER);
				break;
			case R.id.imgBtn_businessAcceptance_queryCarInfo:// 查车辆
				llQueryCar.setBackgroundResource(R.color.dark_blue);
				imgBtnQueryCar.setBackgroundResource(R.drawable.querycar_on);
				startActivity(new Intent(BusinessAcceptanceActivity.this,
						QueryCarInformationActivity.class));
				// finish();
				overridePendingTransition(R.anim.push_right_in,
						R.anim.push_right_out);
				break;

			case R.id.imgBtn_businessAcceptance_queryNoticeInfo:// 查公告
				llQueryNotice.setBackgroundResource(R.color.dark_blue);
				imgBtnQueryNotice
						.setBackgroundResource(R.drawable.querynotice_on);
				startActivity(new Intent(BusinessAcceptanceActivity.this,
						QueryNoticeInformationActivity.class));
				// finish();
				overridePendingTransition(R.anim.push_right_in,
						R.anim.push_right_out);
				break;

			case R.id.imgBtn_businessAcceptance_submit:// 提交
				llSubmit.setBackgroundResource(R.color.dark_blue);
				imgBtnSubmit.setBackgroundResource(R.drawable.submit_on);

				doSubimtData();// 提交业务受理数据

				break;

			case R.id.imgBtn_businessAcceptance_replace:// 重置
				llReplace.setBackgroundResource(R.color.dark_blue);
				imgBtnReplace.setBackgroundResource(R.drawable.replace_on);
				resetData();// 重置UI
				break;
			case R.id.ll_businessAcceptance_bottom:// 返回
				llBottom.setBackgroundResource(R.color.dark_blue);
				imgBtnExit.setBackgroundResource(R.drawable.return_on);
				finish();
				overridePendingTransition(R.anim.push_right_in,
						R.anim.push_right_out);
				break;
			default:
				break;
			}
		}
	}

	/***
	 * 重置页面UI
	 */
	private void resetData() {
		// TODO Auto-generated method stub
		etScanner.setText("");
		et_scannerByHandInput.setText("");
		etCarColor.setText("");
		cbOne.setChecked(false);
		cbTwo.setChecked(false);
		imgPath1=null;
		imgPath2=null;
		imgPath3=null;
		img_carCodeName.setImageBitmap(null);
		img_car.setImageBitmap(null);
		img_another.setImageBitmap(null);
		//查验项目
		xmLists.clear();
		baGvAdapter.notifyDataSetChanged();
		// 对初始化下拉数据的处理
		initSpinnerData();
		initCarColor();// 车辆颜色的操作

		doTakePicture();// 拍摄照片功能的处理
		//校车： cb_special, cb_public;/
		cb_special.setChecked(false);
		cb_public.setChecked(false);
		handler.sendEmptyMessage(0);
	}

	/**
	 * 菜单按钮的点击效果
	 */
	private void changeClick() {
		llQueryCar.setBackgroundResource(R.color.light_blue);
		llQueryNotice.setBackgroundResource(R.color.light_blue);
		llSubmit.setBackgroundResource(R.color.light_blue);
		llReplace.setBackgroundResource(R.color.light_blue);
	}

	/***
	 * 整理提交数据
	 */
	private void doSubimtData() {
		// TODO Auto-generated method stub
		// {'yhdh':'tydic','phone_id':'1','pdlsh':'123456','hpzl':'01','hphm':'A0001','clsbdh':'ADAFADF',
		// 'cllx':'K11','ywlx':'A','bz':'测试','csys':'A','fdjxh':'123',
		// 'xclb':'1','clyt':'B','hgzm':'1','jbq':'1'}
		// 拼接上传的JSON格式----业务受理
		
		//必须下拉数据不能为空
		//  车辆用途   | 车辆类型   |  业务类型    | 号牌种类   |   备注  
		
		if(dataLists1.size()>0){
			if(carType.size()>0){
				if(bzType.size()>0){
					if(dataLists3.size()>0){
						if(dataLists4.size()>0){
							if (Constant.user != null) {
								Map<String, String> conMap = new HashMap<String, String>();
								conMap.put("yhdh", Constant.user.getUserName());
								conMap.put("phone_id", Constant.user.getPhoneId());
								//判断排队号
								String scanner=etScanner.getText().toString().trim();
								String handScanner=et_scannerByHandInput.getText().toString().trim();//手动输入
								if ((scanner != null && !scanner.equals("")) || (handScanner != null && !handScanner.equals(""))) {
									if(cb_scanner.isChecked()){//扫描
										conMap.put("pdlsh", scanner);
										conMap.put("BJ", "1");
										initSubmitData(conMap);//请求数据的提交
									}else if(cb_scannerByHandInput.isChecked()){//手输入
										if(MainActivity.tool.isWMatches2(handScanner)){
											if(handScanner.length()!=4){
												Toast.makeText(getApplicationContext(), "手动输入的排队号不合格，必须是4位。", Toast.LENGTH_LONG).show();
											}else{
												conMap.put("pdlsh", handScanner);
												conMap.put("BJ", "2");
												initSubmitData(conMap);//请求数据的提交
											}
										}else{
											Toast.makeText(getApplicationContext(), "排队号的取值为数字、字母[0-9]。",
													Toast.LENGTH_LONG).show();
										}
									}
								} else {
									Toast.makeText(getApplicationContext(), "排队号不能为空，请手动输入 或者 扫描。",
											Toast.LENGTH_LONG).show();
								}
								handler.sendEmptyMessage(0);
							}
						}else{
							Toast.makeText(getApplicationContext(), "备注不能为空，请先获取备注数据。", Toast.LENGTH_LONG).show();
						}
					}else{
						Toast.makeText(getApplicationContext(), "号牌种类不能为空，请先获取号牌种类数据。", Toast.LENGTH_LONG).show();
					}
				}else{
					Toast.makeText(getApplicationContext(), "业务类型不能为空，请先获取业务类型数据。", Toast.LENGTH_LONG).show();
				}
			}else{
				Toast.makeText(getApplicationContext(), "车辆类型不能为空，请先获取车辆类型数据。", Toast.LENGTH_LONG).show();
			}
		}else{
			Toast.makeText(getApplicationContext(), "车辆用途不能为空，请先获取车辆用途数据。", Toast.LENGTH_LONG).show();
		}
	}

	/***
	 * 提交数据到后台服务器
	 * @param conMap
	 */
	private void initSubmitData(Map<String, String> conMap){
		//添加其他的数据
		String hpzl = sp_carSignKind.getSelectedItem().toString()
				.trim();
		conMap.put("hpzl", hpzl.subSequence(0, hpzl.indexOf(":"))
				.toString());
		String cllx = sp_carType.getSelectedItem().toString().trim();
		conMap.put("cllx", cllx.subSequence(0, cllx.indexOf(":"))
				.toString());
		String ywlx = sp_businessType.getSelectedItem().toString()
				.trim();
		conMap.put("ywlx", ywlx.subSequence(0, ywlx.indexOf(":"))
				.toString());
		//备注
		String bz = sp_comments.getSelectedItem().toString().trim();
		conMap.put("bz", bz);
		//车身颜色
		String csys = "";
		if (colorRes != null && !colorRes.equals("")) {
			String[] str = colorRes.split(",");
			for (int i = 0; i < str.length; i++) {
				csys += str[i].subSequence(0, str[i].indexOf(":"))
						.toString() + "#";
			}
			csys = csys.subSequence(0, csys.length() - 1).toString();
		}
		conMap.put("csys", csys);
		System.out.println("车身颜色----"+colorRes+"---->"+csys);
		//校车类型
		if(ll_sCarType.getVisibility()==View.VISIBLE){
			if (cb_public.isChecked()) {// 非专用
				conMap.put("xclb", "2");
			} else if (cb_special.isChecked()) {// 专用
				conMap.put("xclb", "1");
			}
		}else {
			conMap.put("xclb", "");
		}
		String clyt = sp_carUseWay.getSelectedItem().toString().trim();
		conMap.put("clyt", clyt.subSequence(0, clyt.indexOf(":"))
				.toString());

		if (cbOne.isChecked()) {// 安全技术减压合格征免
			conMap.put("hgzm", "1");
		} else {
			conMap.put("hgzm", "0");
		}
		if (cbTwo.isChecked()) {// 标志灯具，报警器
			conMap.put("jbq", "1");
		} else {
			conMap.put("jbq", "0");
		}
		
		if (imgPath2 != null && !imgPath2.equals("")) {
			//conMap.put("path2",getEncodeBytes(getSmallBitmap(imgPath2)));
			conMap.put("path2",getEncodeBytes(MainActivity.tool.getLocalBitMap(imgPath2)));
		}
		
		if (imgPath3 != null && !imgPath3.equals("")) {
			//conMap.put("path3",getEncodeBytes(getSmallBitmap(imgPath3)));
			conMap.put("path3",getEncodeBytes(MainActivity.tool.getLocalBitMap(imgPath3)));
		}
		// 添加图片的字节码 path1
		if (imgPath1 != null && !imgPath1.equals("")) {
			//conMap.put("path1",getEncodeBytes(getSmallBitmap(imgPath1)));
			conMap.put("path1",getEncodeBytes(MainActivity.tool.getLocalBitMap(imgPath1)));
			//上传数据----到服务器
			System.out.println("上传的JSON格式----"+ MainActivity.gson.toJson(conMap));
			//上传的参数写入SDCard的文件中
			w(MainActivity.gson.toJson(conMap));
			new MyAsysTaskReqSubmitAll().execute(MainActivity.gson
					.toJson(conMap));
		}else{
			Toast.makeText(getApplicationContext(), "车辆识别代号照片是必传参数，请拍照。",
					Toast.LENGTH_LONG).show();
		}
	}
	
	/***
	 * 构造字节码--
	 * 
	 * @param bm
	 * @return
	 */
	private String getEncodeBytes(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.JPEG, 60, baos);
		byte[] buffer = baos.toByteArray();
		return Base64.encodeBytes(buffer);
	}

	/***
	 * 测试参数写入SDCard
	 * 
	 * @param str
	 */
	private void w(String str) {
		String strPath = Environment.getExternalStorageDirectory()
				.getAbsolutePath() + "/" + "testWeb.txt";
		byte[] byteStr = str.getBytes();
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(new File(strPath));
			out.write(byteStr);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
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

	/**
	 * 请求 Spinner初始化数据
	 * 
	 * @author zhangyx
	 * 
	 */
	class MyAsyncTaskREQInitSpinnerData extends
			AsyncTask<String, String, String> {

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
			Constant.webService.setMETHOD_NAME(getApplicationContext()
					.getString(R.string.Connection_MethodName));
			Constant.webService.setWEBSERVICE(getApplicationContext()
					.getString(R.string.Connection_Webservice));
			if (Constant.webService.connect(new String[] { "jkid", "userid",
					"authid", "QueryXmlDoc" }, new Object[] { "V-CODE_JH",
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
			System.out.println("返回结果---》" + result);
			sp_carUseWay.setClickable(true);//车辆用途
			sp_comments.setClickable(true);//备注
			sp_carSignKind.setClickable(true);//号牌种类
			if (result != null && !result.equals("")) {
				try {
					JSONObject obj = new JSONObject(result);
					String res = obj.getString("RESULT");
					String data1 = obj.getString("DATA1");
					if (res.equals("TRUE")) {
						dataList1.clear();
						dataList2.clear();
						dataList3.clear();
						dataList4.clear();
						dataList1.addAll((List) MainActivity.gson.fromJson(
								data1, new TypeToken<List<Frm_Code>>() {
								}.getType()));
						dataList2.addAll((List) MainActivity.gson.fromJson(
								obj.getString("DATA2"),
								new TypeToken<List<Frm_Code>>() {
								}.getType()));
						dataList3.addAll((List) MainActivity.gson.fromJson(
								obj.getString("DATA3"),
								new TypeToken<List<Frm_Code>>() {
								}.getType()));
						dataList4.addAll((List) MainActivity.gson.fromJson(
								obj.getString("DATA4"),
								new TypeToken<List<Frm_Code>>() {
								}.getType()));
						addDataToSpinner();
					} else if (res.equals("FALSE")) {
						Toast.makeText(getApplicationContext(), data1,
								Toast.LENGTH_SHORT).show();
						sp_carUseWay.setClickable(false);//车辆用途
						sp_comments.setClickable(false);//备注
						sp_carSignKind.setClickable(false);//号牌种类
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				sp_carUseWay.setClickable(false);//车辆用途
				sp_comments.setClickable(false);//备注
				sp_carSignKind.setClickable(false);//号牌种类
				Toast.makeText(getApplicationContext(), "网络异常或者服务器异常，获取 车辆用途、号牌种类、备注  数据失败。",
						Toast.LENGTH_SHORT).show();
			}
		}

	};

	/**
	 * 添加请求数据
	 */
	private void addDataToSpinner() {
		if (dataList1.size() > 0) {
			dataLists1.clear();
			for (Frm_Code f : dataList1) {
				dataLists1.add(f.getDmsm1());
			}
			sp_carUseWayAdapter.notifyDataSetChanged();
		}
		if (dataList3.size() > 0) {
			dataLists3.clear();
			for (Frm_Code f : dataList3) {
				dataLists3.add(f.getDmsm1());
			}
			sp_carSignKindAdapter.notifyDataSetChanged();
		}
		if (dataList4.size() > 0) {
			dataLists4.clear();
			for (Frm_Code f : dataList4) {
				dataLists4.add(f.getDmsm1());
			}
			sp_commentsAdapter.notifyDataSetChanged();
		}
	}

	/**
	 * 请求 Spinner车辆类型接口(车辆类型下拉框)
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
			Constant.webService.setMETHOD_NAME(getApplicationContext()
					.getString(R.string.Connection_MethodName));
			Constant.webService.setWEBSERVICE(getApplicationContext()
					.getString(R.string.Connection_Webservice));
			// {'yhdh':'tydic','phone_id':'1','clyt':'A'}
			if (Constant.webService.connect(new String[] { "jkid", "userid",
					"authid", "QueryXmlDoc" }, new Object[] { "V-CLLX",
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
			System.out.println("返回结果---》" + result);
			sp_carType.setClickable(true);//车辆类型
			if (result != null && !result.equals("")) {
				try {
					JSONObject obj = new JSONObject(result);
					String res = obj.getString("RESULT");
					String data1 = obj.getString("DATA");
					carTypes.clear();
					if (res.equals("TRUE")) {
						carTypes.addAll((List) MainActivity.gson.fromJson(
								data1, new TypeToken<List<Frm_Code>>() {
								}.getType()));
					} else if (res.equals("FALSE")) {
						sp_carType.setClickable(false);//车辆类型
						Toast.makeText(getApplicationContext(), data1,
								Toast.LENGTH_SHORT).show();
					}
					showCarTypes();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				sp_carType.setClickable(false);//车辆类型
				Toast.makeText(getApplicationContext(), "网络异常或者服务器异常,获取  车辆类型  数据失败。",
						Toast.LENGTH_SHORT).show();
			}
		}

	};

	/**
	 * 读取车辆类型接口(车辆类型下拉框)
	 */
	private void showCarTypes() {
		carType.clear();
		if (carTypes.size() > 0) {
			for (Frm_Code f : carTypes) {
				carType.add(f.getDmsm1());
			}
		}
		sp_carTypeAdapter.notifyDataSetChanged();
	}

	/**
	 * 请求 Spinner业务类型接口数据
	 * 
	 * @author zhangyx
	 * 
	 */
	class MyAsyncTaskREQbzType extends AsyncTask<String, String, String> {

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
			Constant.webService.setMETHOD_NAME(getApplicationContext()
					.getString(R.string.Connection_MethodName));
			Constant.webService.setWEBSERVICE(getApplicationContext()
					.getString(R.string.Connection_Webservice));
			// {'yhdh':'tydic','phone_id':'1','clyt':'B'}
			if (Constant.webService.connect(new String[] { "jkid", "userid",
					"authid", "QueryXmlDoc" }, new Object[] { "V-YWLX",
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
			System.out.println("返回结果---》" + result);
			sp_businessType.setClickable(true);//业务类型
			if (result != null && !result.equals("")) {
				try {
					JSONObject obj = new JSONObject(result);
					String res = obj.getString("RESULT");
					String data1 = obj.getString("DATA");
					bzTypes.clear();
					if (res.equals("TRUE")) {
						bzTypes.addAll((List) MainActivity.gson.fromJson(data1,
								new TypeToken<List<Frm_Code>>() {
								}.getType()));
					} else if (res.equals("FALSE")) {
						sp_businessType.setClickable(false);//业务类型
						Toast.makeText(getApplicationContext(), data1,
								Toast.LENGTH_SHORT).show();
					}
					showbzTypes();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				sp_businessType.setClickable(false);//业务类型
				Toast.makeText(getApplicationContext(), "网络异常或者服务器异常，获取  业务类型  数据失败。",
						Toast.LENGTH_SHORT).show();
			}
		}

	};

	/**
	 * 读取业务类型接口
	 */
	private void showbzTypes() {
		bzType.clear();
		if (bzTypes.size() > 0) {
			for (Frm_Code f : bzTypes) {
				bzType.add(f.getDmsm1());
			}
		}
		sp_businessTypeAdapter.notifyDataSetChanged();
	}

	/**
	 * 点击获取查验项目
	 * 
	 * @author zhangyx
	 * 
	 */
	class MyAsyncTaskREQ_XM extends AsyncTask<String, String, String> {

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
			Constant.webService.setMETHOD_NAME(getApplicationContext()
					.getString(R.string.Connection_MethodName));
			Constant.webService.setWEBSERVICE(getApplicationContext()
					.getString(R.string.Connection_Webservice));
			// {'yhdh':'tydic','phone_id':'1','clyt':'B'}
			if (Constant.webService.connect(new String[] { "jkid", "userid",
					"authid", "QueryXmlDoc" }, new Object[] { "V-CYXM",
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
			System.out.println("返回结果---》" + result);
			if (result != null && !result.equals("")) {
				try {
					JSONObject obj = new JSONObject(result);
					String res = obj.getString("RESULT");
					String data1 = obj.getString("DATA");
					xmLists.clear();
					if (res.equals("TRUE")) {
						xmLists.addAll((List) MainActivity.gson.fromJson(data1,
								new TypeToken<List<Frm_Code>>() {
								}.getType()));
					} else if (res.equals("FALSE")) {
						Toast.makeText(getApplicationContext(), data1,
								Toast.LENGTH_SHORT).show();
					}
					baGvAdapter.notifyDataSetChanged();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				Toast.makeText(getApplicationContext(), "网络异常或者服务器异常。",
						Toast.LENGTH_SHORT).show();
			}
		}

	};

	/**
	 * PDA查验业务数据保存接口----全部一起提交
	 * 
	 * @author zhangyx
	 * 
	 */
	class MyAsysTaskReqSubmitAll extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			MainActivity.tool.startProgressDialog(getApplicationContext());
		}

		@SuppressLint("ParserError")
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String resultStr = "";
			// 接口1：----测试成功 PDA查验业务数据保存接口
			// {'yhdh':'tydic','phone_id':'1','pdlsh':'123456','hpzl':'01','hphm':'A0001','clsbdh':'ADAFADF','cllx':'K11','ywlx':'A','bz':'测试','csys':'A','fdjxh':'123','xclb':'1','clyt':'B','hgzm':'1','jbq':'1'}
			Constant.webService.setMETHOD_NAME("iInterface");
			Constant.webService.setWEBSERVICE("zwapp/service/IServicePro");
			if (Constant.webService.connect(new String[] { "jkid", "userid",
					"authid", "QueryXmlDoc" }, new Object[] { "V-CYSL",
					"SHDIC", "%E3%FAU%E0%CA*kh%05%8E%A2u%B3%0A%D1%E5",
					params[0] })) {
				if (Constant.webService.getResult() != null) {
					resultStr = Constant.webService.getResult().toString();
				}
			}
			return resultStr;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			System.out.println("PDA查验业务数据保存接口--返回结果：===" + result);
			if (result != null && !result.equals("")) {
				try {
					JSONObject obj = new JSONObject(result);
					String type = obj.getString("RESULT");
					String data = obj.getString("DATA");
					if (type.equals("TRUE")) {
						Toast.makeText(getApplicationContext(), "" + data,
								Toast.LENGTH_SHORT).show();
						resetData();//刷新数据
					} else if (type.equals("FALSE")) {
						Toast.makeText(getApplicationContext(), "" + data,
								Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				Toast.makeText(getApplicationContext(), "服务器异常或者网络异常。",
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	/***
	 * 跳转辅助Activity页面的返回结果
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// 扫描条码的返回处理
		if (requestCode == Constant.RC_SCANNER && data != null) {
			queueNum = data.getStringExtra("code");
			etScanner.setText(queueNum);
		}
		
		if (resultCode == Activity.RESULT_OK) {
			
			//获取项目所有图片的本地存储路径
			if(MainActivity.tool.isSDCardExist()){//判断SDCard是否存在
				if(Constant.localImagePath==null | Constant.localImagePath.equals("")){
					Constant.localImagePath=Environment.getExternalStorageDirectory()+"/"+MainActivity.tool.getApplicationName();
				}
			}
			System.out.println("返回参数-----" + requestCode + "==" + resultCode);
			
			// 车辆识别代号照片
			if (requestCode ==  Constant.CAMERA_WITH_DATA && data != null) {//剪切成功返回数据
				
				String path=data.getStringExtra("picture");
				System.out.println("剪切图片的保存路径是----->====="+path);
				Bitmap bitmap=BitmapFactory.decodeFile(data.getStringExtra("picture"));
	        	img_carCodeName.setImageBitmap(bitmap);
	        	//保存图片到SDCard
				if(bitmap!=null){
					imgPath1=data.getStringExtra("picture");
				}
			}
//			// 车辆照片
			if (requestCode == Constant.RC_CAR_PIC) {//
				///img_car.setImageBitmap(BitmapFactory.decodeFile(imgPath2));
				Bitmap bm = (Bitmap) data.getExtras().get("data");
				img_car.setImageBitmap(bm);
				//保存图片到SDCard
				if(bm!=null){
					imgPath2=MainActivity.tool.writeToSDcard(Constant.localImagePath,MainActivity.tool.getPhotoFileName(), bm);
				}
			}
			// 其他照片
			if (requestCode == Constant.RC_OTHER_PIC) {//
				//img_another.setImageBitmap(BitmapFactory.decodeFile(imgPath3));
				Bitmap bm = (Bitmap) data.getExtras().get("data");
				img_another.setImageBitmap(bm);
				//保存图片到SDCard
				if(bm!=null){
					imgPath3=MainActivity.tool.writeToSDcard(Constant.localImagePath,MainActivity.tool.getPhotoFileName(), bm);
				}
			}
		}
	}

	public static Bitmap getSmallBitmap(String filePath) {
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, 800, 1200);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;

		return BitmapFactory.decodeFile(filePath, options);
	}

	/**
	 * 计算图片的缩放值
	 * 
	 * @param options
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			// Calculate ratios of height and width to requested height and
			// width
			final int heightRatio = Math.round((float) height
					/ (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);

			// Choose the smallest ratio as inSampleSize value, this will
			// guarantee
			// a final image with both dimensions larger than or equal to the
			// requested height and width.
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}

		return inSampleSize;
	}
}