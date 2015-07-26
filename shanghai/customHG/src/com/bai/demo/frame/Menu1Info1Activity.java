package com.bai.demo.frame;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.bai.demo.R;
import com.bai.demo.adapter.ImageAdapter;
import com.bai.demo.adapter.Menu1Info1BottomListAdapter;
import com.bai.demo.adapter.Menu1Info1TopListAdapter;
import com.bai.demo.bean.CHK_ALC;
import com.bai.demo.bean.EntryHead;
import com.bai.demo.bean.EntryList;
import com.bai.demo.bean.PhotoList;
import com.bai.demo.entity.Constant;
import com.bai.demo.main.FrameDemoActivity;
import com.bai.demo.main.PicZoomActivity;
import com.bai.demo.onlyTest.TestAddData;
import com.bai.demo.utils.DebugUtil;
import com.bai.demo.utils.FileUtil;
import com.bai.demo.zoomImage.ToImageZoomActivity;
import com.google.gson.reflect.TypeToken;

/**
 * @author zhangyx 待查验列表
 * 
 */

@SuppressLint({ "ParserError", "ParserError", "ParserError", "ParserError", "ParserError", "HandlerLeak" })
public class Menu1Info1Activity extends RightWindowBase {

	private Menu1Info1TopListAdapter menu1Info1ListAdapter;//待查验列表适配器
	private Menu1Info1BottomListAdapter menu1Info1BottomListAdapter;//暂存列表适配器
	private List<CHK_ALC> chkAlcList = new ArrayList<CHK_ALC>();//待查验数据
	public static Handler handler_RM1I1;
	private LinearLayout ll_rightMenu1Info_first, ll_rightMenu1Info_second, ll_titleTop, ll_titleBottom,ll_top,ll_bottom;// 待查页面列表第一个页面|批量处理页面|待查列表标题|暂存列表标题|待查验列表页面|暂存列表页面
	//批量处理页面
	private Gallery gl_RM1I1_numberList, gl_RM1I1_containerPic, gl_RM1I1_goodsPic, gl_RM1I1_containerInfoPic;//报关单号|集装箱箱体|货物堆放情况|集装箱标志
	private ImageButton IB_RM1I1_takeCameraOne, IB_RM1I1_takeCameraTwo, IB_RM1I1_takeCameraThree;//批量处理页面的拍照： 集装箱箱体|货物堆放情况|集装箱标志
	
	private List<Map<String,Object>> ls;//保存图片的路径集合
	private List<Map<String, String>> checkedEntrys;//批量上传选择的报关单号
	private ListView dcylbBody,lv_bottom ;
	public CheckBox cb_RM1I1_LVTop_checked;//待查验列表：全选
	private String saveEntryNum;//选择要暂存的报关单号
	private String entryNum;
	private ImageAdapter sa;
	private Button btn_RM1I1_refurbish;//刷新按钮
	
	public Menu1Info1Activity(Context context) {
		super(context);
		setupViews();
		menu1Info1ListAdapter.notifyDataSetChanged();
	}

	public Menu1Info1Activity(Context context, AttributeSet attrs) {
		super(context, attrs);
		setupViews();
	}

	private void setupViews() {
		//待查验列表|暂存列表 页面
		ll_rightMenu1Info_first = (LinearLayout) LinearLayout.inflate(getContext(),
				R.layout.right_menu1info1, null);
		btn_RM1I1_refurbish=(Button) ll_rightMenu1Info_first.findViewById(R.id.btn_RM1I1_refurbish);
		ll_top = (LinearLayout) ll_rightMenu1Info_first.findViewById(R.id.ll_rightMenu1Info1LVTop);
		ll_bottom = (LinearLayout) ll_rightMenu1Info_first.findViewById(R.id.ll_rightMenu1Info1LVBottom);
		cb_RM1I1_LVTop_checked = (CheckBox) ll_top.findViewById(R.id.cb_RM1I1_LVTop_checked);
		ll_titleTop = (LinearLayout) ll_rightMenu1Info_first.findViewById(R.id.ll_rightMenu1Info1ChangeOne);
		ll_titleBottom = (LinearLayout) ll_rightMenu1Info_first.findViewById(R.id.ll_rightMenu1Info1ChangeTwo);
		dcylbBody = (ListView) ll_rightMenu1Info_first.findViewById(R.id.dcylbBody);
		lv_bottom = (ListView) ll_rightMenu1Info_first.findViewById(R.id.lv_rightMenu1Info1_BottomlistView);
		Button btn_uploadAll = (Button) ll_rightMenu1Info_first.findViewById(R.id.btn_upload_all);

		//批量处理的页面
		ll_rightMenu1Info_second = (LinearLayout) LinearLayout.inflate(getContext(),
				R.layout.right_showcheckpictrue, null);
		initView();//批量处理页面的初始化View
		Button btn_RM1I1_saveEntryData = (Button) ll_rightMenu1Info_second.findViewById(R.id.btn_RM1I1_saveEntryData);//暂存
		Button btn_RM1I1_claneBack = (Button) ll_rightMenu1Info_second.findViewById(R.id.btn_RM1I1_claneBack);//取消
		Button btn_RMISecond_show_1 = (Button) ll_rightMenu1Info_second.findViewById(R.id.btn_RMISecond_show_1);
		Button btn_RMISecond_show_2 = (Button) ll_rightMenu1Info_second.findViewById(R.id.btn_RMISecond_show_2);
		Button btn_RMISecond_show_3 = (Button) ll_rightMenu1Info_second.findViewById(R.id.btn_RMISecond_show_3);
		btn_RMISecond_show_1.setOnClickListener(new Btn_RMISecond());
		btn_RMISecond_show_2.setOnClickListener(new Btn_RMISecond());
		btn_RMISecond_show_3.setOnClickListener(new Btn_RMISecond());

		loadData(btn_uploadAll,btn_RM1I1_saveEntryData,btn_RM1I1_claneBack);
		
		addView(ll_rightMenu1Info_first);
		addView(ll_rightMenu1Info_second);
		doExecute();

	}

	private void initView() {
		IB_RM1I1_takeCameraOne = (ImageButton) ll_rightMenu1Info_second.findViewById(R.id.IB_RM1I1_takeCameraOne);
		IB_RM1I1_takeCameraTwo = (ImageButton) ll_rightMenu1Info_second.findViewById(R.id.IB_RM1I1_takeCameraTwo);
		IB_RM1I1_takeCameraThree = (ImageButton) ll_rightMenu1Info_second.findViewById(R.id.IB_RM1I1_takeCameraThree);

		gl_RM1I1_numberList = (Gallery) ll_rightMenu1Info_second.findViewById(R.id.gl_RM1I1_numberList);
		gl_RM1I1_containerPic = (Gallery) ll_rightMenu1Info_second.findViewById(R.id.gl_RM1I1_containerPic);
		gl_RM1I1_goodsPic = (Gallery) ll_rightMenu1Info_second.findViewById(R.id.gl_RM1I1_goodsPic);
		gl_RM1I1_containerInfoPic = (Gallery) ll_rightMenu1Info_second.findViewById(R.id.gl_RM1I1_containerInfoPic);
		//添加事件
		addGalleryListener(gl_RM1I1_containerPic);
		addGalleryListener(gl_RM1I1_goodsPic);
		addGalleryListener(gl_RM1I1_containerInfoPic);
	}

	private Intent camera = null;

	private void doExecute() {
		IB_RM1I1_takeCameraOne.setOnClickListener(new BTNTakeCamera());
		IB_RM1I1_takeCameraTwo.setOnClickListener(new BTNTakeCamera());
		IB_RM1I1_takeCameraThree.setOnClickListener(new BTNTakeCamera());
		btn_RM1I1_refurbish.setOnClickListener(new BTNTakeCamera());
		camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		handler_RM1I1 = new Handler() {
			public void dispatchMessage(android.os.Message msg) {

				int resultId = msg.what;
				String picFilePathCamera;
				switch (resultId) {
				case Constant.REQCAMES_FIRST:
					//当照片完全存储后，由FrameDemoActivity发送一个handler通知，相对应的 照片展示类型  重新向SDCard加载   （再代码加载所以图片一次）
					//iv_RM1I1_topOne.setImageBitmap(FrameDemoActivity.saveBitmap);
					entryNum = FrameDemoActivity.myApp.getEntryNum();
					picFilePathCamera = Constant.SAVEPATH + entryNum
							+ Constant.SAVE_FORMHEADIMAGEUIR +entryNum+"_"+Constant.FORM_HEAD+"_"+Constant.CONTAINER_BODY
							+ "/";
					loadPicDataAgain(picFilePathCamera, gl_RM1I1_containerPic);
					break;
				case Constant.REQCAMES_SECOND:
					//iv_RM1I1_centerOne.setImageBitmap(FrameDemoActivity.saveBitmap);
					entryNum = FrameDemoActivity.myApp.getEntryNum();
					picFilePathCamera = Constant.SAVEPATH + entryNum
							+ Constant.SAVE_FORMHEADIMAGEUIR
							+ entryNum+"_"+Constant.FORM_HEAD+"_"+Constant.GOODSTACK_SITUATION + "/";
					loadPicDataAgain(picFilePathCamera, gl_RM1I1_goodsPic);
					break;
				case Constant.REQCAMES_THREE:
					///iv_RM1I1_bottomOne.setImageBitmap(FrameDemoActivity.saveBitmap);
					entryNum = FrameDemoActivity.myApp.getEntryNum();
					picFilePathCamera = Constant.SAVEPATH + entryNum
							+ Constant.SAVE_FORMHEADIMAGEUIR +entryNum+"_"+Constant.FORM_HEAD+"_"+ Constant.CONTAINER_MARK
							+ "/";
					loadPicDataAgain(picFilePathCamera, gl_RM1I1_containerInfoPic);
					break;
				case Constant.REQ_LOACHOSTINAGE_RM1_G1://1-1 G1
					System.out.println("本地上传选择图片---G1-->"+FrameDemoActivity.myApp.picturePath);
					break;
				case Constant.REQ_LOACHOSTINAGE_RM1_G2://1-1 G2
					System.out.println("本地上传选择图片---G2-->"+FrameDemoActivity.myApp.picturePath);
					break;
				case Constant.REQ_LOACHOSTINAGE_RM1_G3://1-1 G3
					System.out.println("本地上传选择图片---G3-->"+FrameDemoActivity.myApp.picturePath);
					break;
//				case Constant.CB_NOCHECKALL:
//					position=msg.arg1;
//					cb_RM1I1_LVTop_checked.setChecked(false);
//					noCheckAll(position);
//					break;
				}

			};
		};

	}

	//封装选中的报关单号
	private List<Map<String, String>> requestGalleryData() {
		List<Map<String, String>> galleryData = new ArrayList<Map<String, String>>();
		Map<String, String> map = null;
		HashMap<Integer, Boolean> state = menu1Info1ListAdapter.state;
		for (int i = 0; i < menu1Info1ListAdapter.getCount(); i++) {
			map = new HashMap<String, String>();
			if (state.get(i) != null) {
				String tv_manifestNumber = chkAlcList.get(i).getENTRY_ID().toString();
				System.out.println(tv_manifestNumber);
				map.put("tv_number", tv_manifestNumber);
				galleryData.add(map);
			}
		}
		return galleryData;
	}

	private class BTNTakeCamera implements OnClickListener {
		
		String photoFilePath;
		
		@Override
		public void onClick(View v) {
			int vId = v.getId();
			if (camera != null) {
				switch (vId) {
				case R.id.IB_RM1I1_takeCameraOne://G1---集装箱箱体 拍照
					entryNum = FrameDemoActivity.myApp.getEntryNum();
					photoFilePath = Constant.SAVEPATH + entryNum + Constant.SAVE_FORMHEADIMAGEUIR + Constant.CONTAINER_BODY+"/";
					loadPicData(photoFilePath, camera, R.id.IB_RM1I1_takeCameraOne, gl_RM1I1_containerPic);
					break;
				case R.id.IB_RM1I1_takeCameraTwo://G2------货物堆放情况 拍照
					entryNum = FrameDemoActivity.myApp.getEntryNum();
					photoFilePath = Constant.SAVEPATH+entryNum+Constant.SAVE_FORMHEADIMAGEUIR+Constant.GOODSTACK_SITUATION+"/";
					loadPicData(photoFilePath, camera, R.id.IB_RM1I1_takeCameraTwo, gl_RM1I1_goodsPic);
					break;
				case R.id.IB_RM1I1_takeCameraThree:// ---集装箱标志 拍照
					entryNum = FrameDemoActivity.myApp.getEntryNum();
					photoFilePath = Constant.SAVEPATH+entryNum+Constant.SAVE_FORMHEADIMAGEUIR+Constant.CONTAINER_MARK+"/";
					loadPicData(photoFilePath, camera, R.id.IB_RM1I1_takeCameraThree, gl_RM1I1_containerInfoPic);
					break;
				case R.id.btn_RM1I1_refurbish://刷新---待查验列表数据
					if (ll_bottom.getVisibility() == View.VISIBLE) {
						requestData();//本地数据库----暂存列表
					}else if(ll_top.getVisibility()==View.VISIBLE){
						String lobNumber=FrameDemoActivity.myApp.userInfo.getLobNumber();
						lobNumber="220018";//测试数据---工号
						if(lobNumber!=null && !lobNumber.equals(""))
							new MyAsynTask().execute(lobNumber);///加载待查验列表
					}
					break;
				}
			}
		}
	}
	
	private class Btn_RMISecond implements OnClickListener {
		
		@Override
		public void onClick(View v) {
			int vId = v.getId();
			LinearLayout ll_view = (LinearLayout) v.getParent().getParent().getParent();
			LinearLayout ll_first = (LinearLayout) ll_view.findViewById(R.id.ll_RM1I1_second_1);
			LinearLayout ll_second = (LinearLayout) ll_view.findViewById(R.id.ll_RM1I1_second_2);
			LinearLayout ll_three = (LinearLayout) ll_view.findViewById(R.id.ll_RM1I1_second_3);
			RelativeLayout layout_RMISecond_show_1 = (RelativeLayout) ll_view.findViewById(R.id.layout_RMISecond_show_1);
			RelativeLayout layout_RMISecond_show_2 = (RelativeLayout) ll_view.findViewById(R.id.layout_RMISecond_show_2);
			RelativeLayout layout_RMISecond_show_3 = (RelativeLayout) ll_view.findViewById(R.id.layout_RMISecond_show_3);
			//统一处理
			ll_first.setVisibility(View.GONE);
			ll_second.setVisibility(View.GONE);
			ll_three.setVisibility(View.GONE);
			layout_RMISecond_show_1.setBackgroundResource(R.drawable.show_btn_bg_up);
			layout_RMISecond_show_2.setBackgroundResource(R.drawable.show_btn_bg_up);
			layout_RMISecond_show_3.setBackgroundResource(R.drawable.show_btn_bg_up);
			switch (vId) {
			case R.id.btn_RMISecond_show_1:
				ll_first.setVisibility(View.VISIBLE);
				layout_RMISecond_show_1.setBackgroundResource(R.drawable.show_btn_bg_down);
				break;
			case R.id.btn_RMISecond_show_2:
				ll_second.setVisibility(View.VISIBLE);
				layout_RMISecond_show_2.setBackgroundResource(R.drawable.show_btn_bg_down);
				break;
			case R.id.btn_RMISecond_show_3:
				ll_three.setVisibility(View.VISIBLE);
				layout_RMISecond_show_3.setBackgroundResource(R.drawable.show_btn_bg_down);
				break;
			}
		}
	};

	private void loadPicDataAgain(String picFileFolderPath, Gallery gallery) {
		ls = FileUtil.getFileList(picFileFolderPath);
        sa = new ImageAdapter(getContext(), ls);
        gallery.setAdapter(sa);
	}
	
	private void loadPicData(final String picFilePath, final Intent intent, final int btnReqCoder, final Gallery gallery) {
		if(FrameDemoActivity.myApp.getEntryNum() == null || FrameDemoActivity.myApp.getEntryNum().equals("")){
			FrameDemoActivity.toolUtils.promptMessage("请选择要拍照的报关单。");
		}else {
			// 确认对话框
			final AlertDialog isExit = new AlertDialog.Builder(getContext()).create();
			// 对话框标题
			isExit.setTitle(getContext().getString(R.string.titleChoose));
			isExit.setIcon(R.drawable.ic_launcher);
			// 实例化对话框上的按钮点击事件监听
			DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					switch (which) {
					case AlertDialog.BUTTON1:// "立即拍照"按钮调用系统照相机拍照并将照片存储到指定的文件夹下
						// Constant.REQCAMES_FIRST | Constant.REQCAMES_SECOND | Constant.REQCAMES_THREE
						if(btnReqCoder==R.id.IB_RM1I1_takeCameraOne){
							((Activity) getContext()).startActivityForResult(intent, Constant.REQCAMES_FIRST);
						}else if(btnReqCoder==R.id.IB_RM1I1_takeCameraTwo){
							((Activity) getContext()).startActivityForResult(intent, Constant.REQCAMES_SECOND);
						}else if(btnReqCoder==R.id.IB_RM1I1_takeCameraThree){
							((Activity) getContext()).startActivityForResult(intent, Constant.REQCAMES_THREE);
						}
						break;
					case AlertDialog.BUTTON2:// "本地上传"第二个按钮获取本地指定目录存储图片
						//REQ_LOACHOSTINAGE_RM1_G1 | REQ_LOACHOSTINAGE_RM1_G2 | REQ_LOACHOSTINAGE_RM1_G3
						if(btnReqCoder==R.id.IB_RM1I1_takeCameraOne){
							FrameDemoActivity.toolUtils.requestLoachostImage(Constant.REQ_LOACHOSTINAGE_RM1_G1);
						}else if(btnReqCoder==R.id.IB_RM1I1_takeCameraTwo){
							FrameDemoActivity.toolUtils.requestLoachostImage(Constant.REQ_LOACHOSTINAGE_RM1_G2);
						}else if(btnReqCoder==R.id.IB_RM1I1_takeCameraThree){
							FrameDemoActivity.toolUtils.requestLoachostImage(Constant.REQ_LOACHOSTINAGE_RM1_G3);
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
	}

	private void loadData(Button btn_uploadAll, Button btn_rightMenu1Info_llSecond_back,
			Button btn_rightMenu1Info_llSecond_clane) {
		menu1Info1ListAdapter = new Menu1Info1TopListAdapter(chkAlcList,
				this.getContext());
		dcylbBody.setAdapter(menu1Info1ListAdapter);
		changeTitlePage();//待查验列表|暂存列表的切换
		btn_uploadAll.setOnClickListener(new MyOnClickListener());
		btn_rightMenu1Info_llSecond_back.setOnClickListener(new MyOnClickListener());
		btn_rightMenu1Info_llSecond_clane.setOnClickListener(new MyOnClickListener());
		//全选
		cb_RM1I1_LVTop_checked.setTag("1");
		cb_RM1I1_LVTop_checked.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					for (int i = 0; i < menu1Info1ListAdapter.getCount(); i++) {
						View view = menu1Info1ListAdapter.getView(i, null, null);
						CheckBox cb_checkBox = (CheckBox) view.findViewById(R.id.fxk);
						cb_checkBox.setChecked(true);
					}
				} else {
					if(cb_RM1I1_LVTop_checked.getTag().toString().equals("1")){
						
					}
					for (int i = 0; i < menu1Info1ListAdapter.getCount(); i++) {
						View view = menu1Info1ListAdapter.getView(i, null, null);
						CheckBox cb_checkBox = (CheckBox) view.findViewById(R.id.fxk);
						cb_checkBox.setChecked(false);
					}
				}
				menu1Info1ListAdapter.notifyDataSetChanged();
			}
		});
	}

	//待查验列 | 暂存列表   ----切换
	private void changeTitlePage() {
		final ImageButton iv_leftTwo = (ImageButton) ll_titleTop.findViewById(R.id.ib_pageLeftTwo);
		final TextView tv_reqDataTwo = (TextView) ll_titleTop.findViewById(R.id.tv_RM1I1ChangeOne);
		final ImageButton iv_leftThree = (ImageButton) ll_titleBottom.findViewById(R.id.ib_pageLeftThree);
		final TextView tv_reqDataThree = (TextView) ll_titleBottom.findViewById(R.id.tv_RM1I1ChangeTwo);
		final ImageButton iv_leftFour = (ImageButton) ll_titleBottom.findViewById(R.id.ib_pageLeftFour);

		//待查验列表
		ll_titleTop.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				String lobNumber=FrameDemoActivity.myApp.userInfo.getLobNumber();
				lobNumber="220018";//测试数据
				if(lobNumber!=null && !lobNumber.equals(""))
					new MyAsynTask().execute(lobNumber);
				if (ll_bottom.getVisibility() == View.VISIBLE) {
					ll_bottom.setVisibility(View.GONE);
					ll_top.setVisibility(View.VISIBLE);
				}
				iv_leftTwo.setTag("1");
				if (iv_leftTwo.getTag().toString().equals("1")) {
					iv_leftTwo.setBackgroundResource(R.drawable.left2);
					iv_leftTwo.setTag("2");
				} else if (iv_leftTwo.getTag().toString().equals("2")) {
					iv_leftTwo.setBackgroundResource(R.drawable.left1);
					iv_leftTwo.setTag("1");
				}
				tv_reqDataTwo.setBackgroundResource(R.drawable.leftcontent2);
				iv_leftThree.setBackgroundResource(R.drawable.left3);
				tv_reqDataThree.setBackgroundResource(R.drawable.leftcontent_1);
				iv_leftFour.setBackgroundResource(R.drawable.right1);
			}
		});
		//暂存列表
		ll_titleBottom.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				requestData() ;
				if (ll_top.getVisibility() == View.VISIBLE) {
					ll_top.setVisibility(View.GONE);
					ll_bottom.setVisibility(View.VISIBLE);
				}
				iv_leftTwo.setTag("2");
				if (iv_leftTwo.getTag().toString().equals("1")) {
					iv_leftTwo.setBackgroundResource(R.drawable.left2);
					iv_leftTwo.setTag("2");
				} else if (iv_leftTwo.getTag().toString().equals("2")) {
					iv_leftTwo.setBackgroundResource(R.drawable.left1);
					iv_leftTwo.setTag("1");
				}
				tv_reqDataTwo.setBackgroundResource(R.drawable.leftcontent_1);
				iv_leftThree.setBackgroundResource(R.drawable.left);
				tv_reqDataThree.setBackgroundResource(R.drawable.leftcontent2);
				iv_leftFour.setBackgroundResource(R.drawable.right2);
			}
		});
	}
	
	/**
	 * 图片的缩放
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
			intentOne.putExtra("PATH_TYPE", Constant.LoadImage_Local);
			getContext().startActivity(intentOne);
		}
	}
	
	/**
	 * 图片的删除
	 * @author ouyyt
	 *
	 */
	private class MyGalleryOnItemLongClickListener implements OnItemLongClickListener {

		@Override
		public boolean onItemLongClick(AdapterView<?> parent, View view,
				int position, long id) {
			// TODO Auto-generated method stub
			final int p = position;
			// 确认对话框
			final AlertDialog isExit = new AlertDialog.Builder(getContext()).create();
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
						System.out.println("确认按钮删除图片---picFilePath"+picFilePath);
						//删除数据库的记录
						if(FrameDemoActivity.db.deletePhotoListByPath(picFilePath)){
						//删除文件中的图片
							File picfile = new File(picFilePath);
							if(picfile.exists()) {
								picfile.delete();
								ls.remove(p);
							}
						}
						sa.setLs(ls);
						sa.notifyDataSetChanged();
						Toast.makeText(getContext(), "你删除了第"+p+"张的Gallery图片"+"\n"+"图片路径为："+picFilePath, Toast.LENGTH_SHORT).show();
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
			isExit.setButton(getContext().getString(R.string.makeSure), listener);
			isExit.setButton2(getContext().getString(R.string.cancel), listener);
			// 显示对话框
			isExit.show();
			return true;
		}
	}
	
	private class MyOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			int clickViewId = v.getId();
			
			switch (clickViewId) {
			case R.id.btn_upload_all://批量处理
				checkedEntrys = requestGalleryData();
				if(checkedEntrys.size() > 0){
					ll_rightMenu1Info_first.setVisibility(View.GONE);
					ll_rightMenu1Info_second.setVisibility(View.VISIBLE);
					SimpleAdapter sAdapter = new SimpleAdapter(getContext(), checkedEntrys, R.layout.gallery_items, new String[] { "tv_number" }, new int[] { R.id.tv_number });
					gl_RM1I1_numberList.setAdapter(sAdapter);
					gl_RM1I1_numberList.setOnItemClickListener(new OnItemClickListener() {
						@Override
						public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
							saveEntryNum = ((TextView) view.findViewById(R.id.tv_number)).getText().toString();
							FrameDemoActivity.myApp.setEntryNum("");
							FrameDemoActivity.myApp.setEntryNum(saveEntryNum);
							DebugUtil.debug("你点击选择的报关单号是:" + saveEntryNum);
							System.out.println( "你点击选择的报关单号是:" + saveEntryNum);
							//加载相对应的报关单表头图片
						}
					});
				}else{
					FrameDemoActivity.toolUtils.promptMessage("请选择要暂存的报关单。");
				}
				break;
				
			case R.id.btn_RM1I1_saveEntryData://暂存到本地数据库----选择了的报关单全部暂存、并且图片相互复制{图片复制到文件夹|图片信息添加到数据库}
				//暂存
				String strNum=FrameDemoActivity.myApp.entryNum;
				System.out.println(strNum+"点击了的报关单....");
				if(toSaveDataBase(strNum)){
					Toast.makeText(getContext(), "暂存成功！！", Toast.LENGTH_SHORT).show();
					ll_rightMenu1Info_first.setVisibility(View.VISIBLE);
					ll_rightMenu1Info_second.setVisibility(View.GONE);
				}else{
					ll_rightMenu1Info_first.setVisibility(View.GONE);
					ll_rightMenu1Info_second.setVisibility(View.VISIBLE);
				}
				break;
				
			case R.id.btn_RM1I1_claneBack://取消
				//全部设置为不选中
				for (int i = 0; i < menu1Info1ListAdapter.getCount(); i++) {
					View view = menu1Info1ListAdapter.getView(i, null, null);
					CheckBox cb_checkBox = (CheckBox) view.findViewById(R.id.fxk);
					cb_checkBox.setChecked(false);
				}
				menu1Info1ListAdapter.notifyDataSetChanged();
				
				ll_rightMenu1Info_first.setVisibility(View.VISIBLE);
				ll_rightMenu1Info_second.setVisibility(View.GONE);
				break;
			}

		}
	};
	
	//请求本地移动的数据库
	private void requestData() {
		new MySavaAsynTask().execute();
	}
	
	//暂存数据
	private boolean toSaveDataBase(String savedEntryId){//选择了的报关单全部暂存、并且图片相互复制{图片复制到文件夹|图片信息添加到数据库}
		boolean flag=false;
		System.out.println("选择暂存的报关单"+saveEntryNum);
		ArrayList<PhotoList> photoLists=FrameDemoActivity.db.selectFromPhotoListByEntryId(savedEntryId);//保存了的
		if(saveEntryNum!=null && !saveEntryNum.equals("")){
			//进行暂存的操作  //选择了的报关单全部暂存、并且图片相互复制{图片复制到文件夹|图片信息添加到数据库}
			String saveTime=FrameDemoActivity.toolUtils.requestData(Constant.CONCRETE_DATATIME)+"";
			System.out.println("被选择的报关单集合---->"+checkedEntrys);
			//进行暂存的操作  //选择了的报关单全部暂存、并且图片相互复制{图片复制到文件夹|图片信息添加到数据库}
			int length=checkedEntrys.size();
			long row=0;
			for(int i=0;i<length;i++){
				//报关单全部暂存
				String entryIdSave=checkedEntrys.get(i).get("tv_number").toString();//选择暂存的报关单号
				EntryHead entryHead=FrameDemoActivity.db.selectEntryHeadByEntry(entryIdSave);
				EntryList entryList=FrameDemoActivity.db.selectEntryListByEntry(entryIdSave);
				String entryId=entryHead.getEntry_ID();
				if(entryId==null || entryId.equals("")){
					//表头的暂存---全部
					long row1 = FrameDemoActivity.db.insertToEntry_Head(entryIdSave, Constant.NO_UPLOADED, saveTime,null);//表头Entry_Head表
					//表体的暂存
					if(row1>0)
						flag=true;
				}else if(entryList==null || entryList.equals("")){
					row=FrameDemoActivity.db.insertToEntry_LIST(entryIdSave, "0",0, "0");
					if(row>0){
						flag=true;
					}else{
						flag=false;
					}
				}else{
					//FrameDemoActivity.toolUtils.promptMessage("此报关单已被暂存过，请重新选择 。");
					Toast.makeText(getContext(), "此报关单已被暂存过，请重新选择 。", Toast.LENGTH_SHORT).show();
				}
				//图片的相互复制{图片复制到文件夹|图片信息添加到数据库}
				
				System.out.println("PhotoList的结果 ----"+photoLists.size());
				for(PhotoList p:photoLists){
					if(!entryIdSave.equals(p.getEntry_ID())){
						saveImageAndSQLiteMsg(p,entryIdSave);					
					}
				}
				flag=true;
			}
			
		}else {
			FrameDemoActivity.toolUtils.promptMessage("请选择要暂存的报关单。");
		}
		return flag;
	}

	//图片的相互复制{图片复制到文件夹|图片信息添加到数据库}
	private void saveImageAndSQLiteMsg(PhotoList p,String entryId){
		String imgType=p.getPhoto_list_Code();//图片的类型
		String imgPath=p.getPhoto_list_ID();//图片的路径
		String concreteDataTime=FrameDemoActivity.toolUtils.requestData(Constant.CONCRETE_DATATIME);//时间
		String saveIamgePath;
		String imageName;
		if(imgType!=null && !imgType.equals("")){
			if(imgType.equals(Constant.CONTAINER_BODY)){//G1
				//并且复制到文集夹
				saveIamgePath = Constant.SAVEPATH + entryId
						+ Constant.SAVE_FORMHEADIMAGEUIR +entryId+"_"+Constant.FORM_HEAD+"_"+Constant.CONTAINER_BODY
						+ "/";
				imageName = entryId + "_" + Constant.FORM_HEAD + "_"
							+ Constant.CONTAINER_BODY + "_" + concreteDataTime+ ".jpg";
				FrameDemoActivity.toolUtils.loachostImageSave(imgPath, saveIamgePath, imageName);
				//数据库
				FrameDemoActivity.toolUtils.saveEntryToSQLite(entryId, Constant.G_NO_HEAD, Constant.CONTAINER_BODY,
							saveIamgePath+imageName);
			}else if(imgType.equals(Constant.GOODSTACK_SITUATION)){//G2
				//并且复制到文集夹
				saveIamgePath = Constant.SAVEPATH + entryId+ Constant.SAVE_FORMHEADIMAGEUIR
						+ entryId+"_"+Constant.FORM_HEAD+"_"+Constant.GOODSTACK_SITUATION + "/";
				imageName = entryId + "_" + Constant.FORM_HEAD + "_"
							+ Constant.GOODSTACK_SITUATION + "_" + concreteDataTime+ ".jpg";
				FrameDemoActivity.toolUtils.loachostImageSave(imgPath, saveIamgePath, imageName);
				//数据库的展示
				FrameDemoActivity.toolUtils.saveEntryToSQLite(entryId, Constant.G_NO_HEAD, Constant.GOODSTACK_SITUATION,
							saveIamgePath+imageName);
				
			}else if(imgType.equals(Constant.CONTAINER_MARK)){//G3
				//并且复制到文集夹
				saveIamgePath = Constant.SAVEPATH + entryId
						+ Constant.SAVE_FORMHEADIMAGEUIR +entryId+"_"+Constant.FORM_HEAD+"_"+ Constant.CONTAINER_MARK
						+ "/";
				//当本地上传的图片路径与要存的路径不相同时，操作下面的
				imageName = entryId + "_" + Constant.FORM_HEAD + "_"
							+ Constant.CONTAINER_MARK + "_" + concreteDataTime+ ".jpg";
				FrameDemoActivity.toolUtils.loachostImageSave(imgPath, saveIamgePath, imageName);
				//数据库
				FrameDemoActivity.toolUtils.saveEntryToSQLite(entryNum, Constant.G_NO_HEAD, Constant.CONTAINER_MARK,
							saveIamgePath+imageName);
			
			}
		}
	}
	
	@SuppressLint({ "ParserError", "ParserError" })
	@Override
	public void dosomething() {

	}

	@Override
	public void dosomething2() {

	}
	//添加Gallery的监听事件
	private void addGalleryListener(Gallery gallery){
		gallery.setOnItemClickListener(new MyGalleryOnItemClickListener());//缩放
		gallery.setOnItemLongClickListener(new MyGalleryOnItemLongClickListener());
	}
	
	//请求待查验报关单信息
	class MyAsynTask extends AsyncTask<String, String, List<CHK_ALC>> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			FrameDemoActivity.toolUtils.startProgressDialog();
		}

		@SuppressWarnings("static-access")
		@Override
		protected List<CHK_ALC> doInBackground(String... params) {
			// 获取派单
			String strResult="";
			FrameDemoActivity.webservice.setMETHOD_NAME("GetPDList");
			FrameDemoActivity.webservice.setSOAP_ACTION("http://tempuri.org/IGetExamDataService/GetPDList");
			if (FrameDemoActivity.webservice.connect(getContext(),new String[] { "strUserJobNumber" },
					new Object[] { params[0] })) {
				strResult = FrameDemoActivity.webservice.getResult().toString();
				System.out.println("待查验列表信息：" + strResult);
				
			}
			//测试 本地数据
			if(strResult==null || strResult.equals("")){
				TestAddData test=new TestAddData();
				strResult=test.str1;
			}
			//记录日志
			if (strResult != null && !strResult.equals("")) {
				FrameDemoActivity.toolUtils.writeDataLog("获取派单成功。", "89", "");
				return FrameDemoActivity.gson.fromJson(strResult, new TypeToken<List<CHK_ALC>>() {}.getType());
			}
			return null;
		}

		@Override
		protected void onPostExecute(List<CHK_ALC> result) {
			super.onPostExecute(result);
			FrameDemoActivity.toolUtils.endProgressDialog();
			chkAlcList.clear();
			if(result!=null){
				chkAlcList.addAll(result);
				menu1Info1ListAdapter.notifyDataSetChanged();
			}else{
				Toast.makeText(getContext(), "对不起，没有待查验的报关单信息。", Toast.LENGTH_SHORT).show();
			}
		}
	};
	//暂存列表
	class MySavaAsynTask extends AsyncTask<String, String, ArrayList<EntryHead>> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			FrameDemoActivity.toolUtils.startProgressDialog();
		}
		@Override
		protected ArrayList<EntryHead> doInBackground(String... params) {
			// 获取派单selectFromEntryHead
			//return FrameDemoActivity.db.selectEntryHeadForData();
			return FrameDemoActivity.db.selectFromEntryHead();
		}

		@Override
		protected void onPostExecute(ArrayList<EntryHead> result) {
			super.onPostExecute(result);
			FrameDemoActivity.toolUtils.endProgressDialog();
			if(result!=null && !result.equals("")){
				menu1Info1BottomListAdapter = new Menu1Info1BottomListAdapter(
						result, getContext());
				System.out.println("暂存列表===="+result);
				DebugUtil.debug("获取暂存列表数据===="+result);
				lv_bottom.setAdapter(menu1Info1BottomListAdapter);
			}else{
				FrameDemoActivity.toolUtils.promptMessage("本地无暂存报关单数据。");
			}
			
		}
	};
}