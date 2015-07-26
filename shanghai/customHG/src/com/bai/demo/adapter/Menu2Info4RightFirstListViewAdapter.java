package com.bai.demo.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.bai.demo.R;
import com.bai.demo.bean.G_LIST;
import com.bai.demo.bean.PHOTO_LIST;
import com.bai.demo.entity.Constant;
import com.bai.demo.main.FrameDemoActivity;
import com.bai.demo.main.PicZoomActivity;
import com.bai.demo.utils.FileUtil;
import com.bai.demo.utils.MyDialog;
import com.bai.demo.zoomImage.ToImageZoomActivity;
import com.google.gson.reflect.TypeToken;

public class Menu2Info4RightFirstListViewAdapter extends BaseAdapter {

	private LinearLayout lv_RM2I4_dialogView;
	private LayoutInflater layoutInflater;
	private List<G_LIST> data;
	
	private Button btn_dialogClose;
	private Context context;
	
	private G_LIST goodsList;
	
	// 定义显示图片的Gallery控件
	private Gallery gl_uploadPicInfo_goodsPackagePhoto, gl_uploadPicInfo_goodsPhoto, gl_uploadPicInfo_containerInnerPhoto,
	gl_uploadPicInfo_goodsSpecModelPhoto, gl_uploadPicInfo_anotherSpecialRequestPhoto, gl_uploadPicInfo_sceneSrcPhoto;
	
	private List<Map<String, Object>> ls;//返回图片的路径集合
	private ImageAdapter sa;//显示图片的Adapter
	
	private static class ViewHodler{
		private TextView tv_text1;
		private TextView tv_text2;
		private TextView tv_text3;
		private TextView tv_text4;
		private TextView tv_text5;
		private TextView tv_text6;
	}
	
	public Menu2Info4RightFirstListViewAdapter(Context context,List<G_LIST> data){
		this.context = context;
		layoutInflater=LayoutInflater.from(context);
		this.data=data;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHodler holder = null;
		if(convertView == null){
			convertView = layoutInflater.inflate(R.layout.right_menu2info4_listview_first_right_layout_item, null);
			holder = new ViewHodler();
			holder.tv_text1 = (TextView) convertView.findViewById(R.id.textview1);
			holder.tv_text2 = (TextView) convertView.findViewById(R.id.textview2);
			holder.tv_text3 = (TextView) convertView.findViewById(R.id.textview3);
			holder.tv_text4 = (TextView) convertView.findViewById(R.id.textview4);
			holder.tv_text5 = (TextView) convertView.findViewById(R.id.textview5);
			holder.tv_text6 = (TextView) convertView.findViewById(R.id.textview6);
			convertView.setTag(holder);
		}else {
			holder = (ViewHodler) convertView.getTag();
		}
		
		holder.tv_text1.setText(data.get(position).getG_NAME());
		holder.tv_text2.setText(data.get(position).getCODE_TS());
		holder.tv_text3.setText(data.get(position).getEXAM_PROC_NAME());
		holder.tv_text4.setText(data.get(position).getREAD_NOTE());
		holder.tv_text5.setText(data.get(position).getSTAR_HTML());

		final int p=position;
		//设置点击事件
		holder.tv_text6.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				lv_RM2I4_dialogView = (LinearLayout) layoutInflater.inflate(R.layout.right_list_check_pic_interaction_info, null);

				// 获取Gallery控件
				gl_uploadPicInfo_goodsPackagePhoto = (Gallery) lv_RM2I4_dialogView.findViewById(R.id.gl_uploadPicInfo_goodsPackagePhoto);
				gl_uploadPicInfo_goodsPhoto = (Gallery) lv_RM2I4_dialogView.findViewById(R.id.gl_uploadPicInfo_goodsPhoto);
				gl_uploadPicInfo_containerInnerPhoto = (Gallery) lv_RM2I4_dialogView.findViewById(R.id.gl_uploadPicInfo_containerInnerPhoto);
				gl_uploadPicInfo_goodsSpecModelPhoto = (Gallery) lv_RM2I4_dialogView.findViewById(R.id.gl_uploadPicInfo_goodsSpecModelPhoto);
				gl_uploadPicInfo_anotherSpecialRequestPhoto = (Gallery) lv_RM2I4_dialogView.findViewById(R.id.gl_uploadPicInfo_anotherSpecialRequestPhoto);
				gl_uploadPicInfo_sceneSrcPhoto = (Gallery) lv_RM2I4_dialogView.findViewById(R.id.gl_uploadPicInfo_sceneSrcPhoto);
				btn_dialogClose = (Button) lv_RM2I4_dialogView.findViewById(R.id.btn_dialogClose);
				
				// 添加Gallery控件事件
				galleryClick(gl_uploadPicInfo_goodsPackagePhoto);
				galleryClick(gl_uploadPicInfo_goodsPhoto);
				galleryClick(gl_uploadPicInfo_containerInnerPhoto);
				galleryClick(gl_uploadPicInfo_goodsSpecModelPhoto);
				galleryClick(gl_uploadPicInfo_anotherSpecialRequestPhoto);
				galleryClick(gl_uploadPicInfo_sceneSrcPhoto);
				
				goodsList = data.get(p);
				initViewData(lv_RM2I4_dialogView);//给lv_RM2I4_dialogView的控件赋值
				MyDialog.showDialog(context, lv_RM2I4_dialogView, context.getString(R.string.upload_info));
				
				btn_dialogClose.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						MyDialog.dismissDialog();
					}
				});
			}
		});
		return convertView;
	}
	
	//给lv_RM2I4_dialogView的控件赋值
	private void initViewData(View lv_RM2I4_dialogView){
		if(goodsList!=null && !goodsList.equals("")){
			((TextView) lv_RM2I4_dialogView.findViewById(R.id.tv_uploadPicInfo_text1)).setText(goodsList.getENTRY_ID());
			// 测试数据
			String entryId="6666666666";
			String gNo="0";
			new MyAsynTaskImageDownload().execute(entryId, gNo);
		}
	}
	
	// 下载相关图片
	class MyAsynTaskImageDownload extends AsyncTask<String, String, String> {
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			FrameDemoActivity.toolUtils.startProgressDialog();
		}

		@Override
		protected String doInBackground(String... params) {
			// 图片的下载
			String reqResult = "";
			FrameDemoActivity.webservice.setMETHOD_NAME("GetPhotoInfo");
			FrameDemoActivity.webservice
					.setSOAP_ACTION("http://tempuri.org/IGetExamDataService/GetPhotoInfo");
			if (FrameDemoActivity.webservice.connect(context,new String[] {"entry_id","g_no" },
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
				FrameDemoActivity.toolUtils.endProgressDialog();
			} else if (result != null && !result.equals("")) {
				FrameDemoActivity.myApp.photoList.clear();
				FrameDemoActivity.myApp.photoList.addAll(((List) FrameDemoActivity.gson.fromJson(
						result, new TypeToken<List<PHOTO_LIST>>() {
						}.getType())));
				new MyAsynTaskDownloadToSdcard().execute();
			}
		}

	};
	
	//下载图片到SDCard  
	class MyAsynTaskDownloadToSdcard extends AsyncTask<String, String, Map<String,String>> {
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			FrameDemoActivity.toolUtils.startDownloadProgressDialog();
		}

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
						String reqPath=context.getString(R.string.DOWNLOADIMAGE_P)+p.getPHOTO_ID();
						String imageName= p.getENTRY_ID() + "_" + p.getG_NO() + "_"
								+ p.getPHOTO_CODE() + "_"+ i;
						String savePath=Constant.DISPLAY_SAVAPATH + p.getENTRY_ID() + "/" + p.getG_NO()
								+ "/" + p.getPHOTO_CODE() + "/";
						Bitmap bitmap=FrameDemoActivity.imageUtil.downloadBitmapByCwj(reqPath);//下载图片
						byte[] buffer=FrameDemoActivity.imageUtil.bitmapTobyte(bitmap);
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
			FrameDemoActivity.toolUtils.endProgressDialog();
			//通知页面显示下载得图片
			if(result.get("res").equals("")){//无下载图片
				System.out.println(result.get("reqResult"));
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
//			String[] strArr=savePath.split("/");
//			String photoCode=strArr[strArr.length-1];//图片类型
			//根据不同的类型 ---加载不同路径的图片
//			if(photoCode.equals(Constant.GOODS_PACKAGE)){// 货物包装   照片--表体1
				loadPicDataAgain(savePath, gl_uploadPicInfo_goodsPackagePhoto);
//			}else if(photoCode.equals(Constant.GOODS)){//商品近照----表体2
				loadPicDataAgain(savePath, gl_uploadPicInfo_goodsPhoto);
//			}else if(photoCode.equals(Constant.CONTAINER_INNER)){// 集装箱内景    照片-----表体3
				loadPicDataAgain(savePath, gl_uploadPicInfo_containerInnerPhoto);
//			}else if(photoCode.equals(Constant.GOODS_SPECIFICATION_MODEL)){// 商品规格型号    照片--表体4
				loadPicDataAgain(savePath, gl_uploadPicInfo_goodsSpecModelPhoto);
//			}else if(photoCode.equals(Constant.ANOTHER_SPECIAL_REQUEST)){//其他特殊要求照-----表体5
				loadPicDataAgain(savePath, gl_uploadPicInfo_anotherSpecialRequestPhoto);
//			}else if(photoCode.equals(Constant.SCENE_SRC)){//现场认定资料照-----表体6
				loadPicDataAgain(savePath, gl_uploadPicInfo_sceneSrcPhoto);
//			}
		}
	}
	
	// 加载对应 文件夹下面的所有图片
	private void loadPicDataAgain(String picFileFolderPath, Gallery gallery) {
		ls = FileUtil.getFileList(picFileFolderPath);
		sa = new ImageAdapter(context, ls);
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
			Intent intentOne = new Intent(context,ToImageZoomActivity.class);
			intentOne.putExtra("imagePath", picFilePath);
			intentOne.putExtra("PATH_TYPE", Constant.LoadImage_Intenet);
			context.startActivity(intentOne);
//			String picFilePath = (String) ls.get(position).get("picFilePath");
//			Intent intent = new Intent(context, PicZoomActivity.class);
//			intent.putExtra("imagePath", picFilePath);
//			context.startActivity(intent);
//			System.out.println("你点击了第" + position + "张的Gallery图片" + "\n" + "图片路径为：" + picFilePath);
		}
	}
}
