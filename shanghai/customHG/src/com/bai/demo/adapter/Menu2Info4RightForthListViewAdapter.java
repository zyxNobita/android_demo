package com.bai.demo.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bai.demo.R;
import com.bai.demo.bean.HS_PHOTO_REL;
import com.bai.demo.bean.HS_RSK_REL;
import com.bai.demo.entity.Constant;
import com.bai.demo.main.FrameDemoActivity;
import com.bai.demo.main.PicZoomActivity;
import com.bai.demo.utils.FileUtil;
import com.bai.demo.utils.MyDialog;
import com.bai.demo.zoomImage.ToImageZoomActivity;
import com.google.gson.reflect.TypeToken;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class Menu2Info4RightForthListViewAdapter extends BaseAdapter {
	
	private LinearLayout lv_RM2I4_dialogView;
	private LayoutInflater layoutInflater;
	private List<HS_RSK_REL> data;
	
	private Button btn_dialogClose;
	private Context context;
	
	private HS_RSK_REL goodsRskRel;
	
	// 定义显示图片的Gallery控件
	private Gallery gl_checkRecord_goodsRelationPic;
	
	private List<Map<String, Object>> ls;//返回图片的路径集合
	private ImageAdapter sa;//显示图片的Adapter	
	
	private static class ViewHodler {
		private TextView tv_text1;
		private TextView tv_text2;
		private TextView tv_text3;
	}
	
	public Menu2Info4RightForthListViewAdapter(Context context,List<HS_RSK_REL> data) {
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
			convertView = layoutInflater.inflate(R.layout.right_menu2info4_listview_forth_right_layout_item, null);
			holder = new ViewHodler();
			holder.tv_text1 = (TextView) convertView.findViewById(R.id.textview1);
			holder.tv_text2 = (TextView) convertView.findViewById(R.id.textview2);
			holder.tv_text3 = (TextView) convertView.findViewById(R.id.textview3);
			convertView.setTag(holder);
		}else {
			holder = (ViewHodler) convertView.getTag();
		}
		holder.tv_text1.setText(data.get(position).getG_NAME());
		holder.tv_text2.setText(data.get(position).getRSK_POINT());
		
		final int p=position;
		//设置点击事件
		holder.tv_text3.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				//获得点击事件需要的数据,对应的View控件里面赋值
				lv_RM2I4_dialogView = (LinearLayout) layoutInflater.inflate(R.layout.right_list_check, null);
				btn_dialogClose = (Button) lv_RM2I4_dialogView.findViewById(R.id.btn_dialogClose);

				// 获取Gallery控件
				gl_checkRecord_goodsRelationPic = (Gallery) lv_RM2I4_dialogView.findViewById(R.id.gl_checkRecord_goodsRelationPic);
				
				// 添加Gallery控件事件
				galleryClick(gl_checkRecord_goodsRelationPic);
				
				goodsRskRel = data.get(p);
				initViewData(lv_RM2I4_dialogView);//给lv_RM2I4_dialogView的控件赋值
				MyDialog.showDialog(context, lv_RM2I4_dialogView, context.getString(R.string.goods_info));
				
				btn_dialogClose.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						MyDialog.dismissDialog();
					}
				});
			}});
		return convertView;
	}
	
	//给lv_RM2I4_dialogView的控件赋值
	private void initViewData(View lv_RM2I4_dialogView){
		if(goodsRskRel!=null && !goodsRskRel.equals("")){
			((TextView) lv_RM2I4_dialogView.findViewById(R.id.tv_checkRecord_text1)).setText(goodsRskRel.getG_NAME());
			((TextView) lv_RM2I4_dialogView.findViewById(R.id.tv_checkRecord_text2)).setText(goodsRskRel.getCODE_TS());
			((TextView) lv_RM2I4_dialogView.findViewById(R.id.tv_checkRecord_text5)).setText(goodsRskRel.getRSK_POINT());
			// 测试数据
			String goodsId = goodsRskRel.getG_ID();
			new MyAsynTaskImageDownload().execute(goodsId);
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
			FrameDemoActivity.webservice.setMETHOD_NAME("GetHsRskPhotos");
			FrameDemoActivity.webservice
					.setSOAP_ACTION("http://tempuri.org/IGetExamDataService/GetHsRskPhotos");
			if (FrameDemoActivity.webservice.connect(context,new String[] { "g_id" },
					new Object[] { params[0] })) {
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
				FrameDemoActivity.toolUtils.promptMessage("没有商品相关图片。");
				FrameDemoActivity.toolUtils.endProgressDialog();
			} else if (result != null && !result.equals("")) {
				FrameDemoActivity.myApp.hsPhotoList.clear();
				FrameDemoActivity.myApp.hsPhotoList.addAll(((List) FrameDemoActivity.gson.fromJson(
						result, new TypeToken<List<HS_PHOTO_REL>>() {
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
			if(FrameDemoActivity.myApp.hsPhotoList!=null){
				int pListSize=FrameDemoActivity.myApp.hsPhotoList.size();
				for(int i=0;i<pListSize;i++){
					HS_PHOTO_REL p=FrameDemoActivity.myApp.hsPhotoList.get(i);
					String photoPath=p.getG_PHOTO();
					if(photoPath!=null && !photoPath.equals("")){
						String reqPath=context.getString(R.string.DISPLAYDOWNLOADIMAGE_P)+p.getG_PHOTO();
						String imageName= goodsRskRel.getCODE_TS() + "_" + i;
						String savePath=Constant.DISPLAY_SAVAPATH + goodsRskRel.getCODE_TS() + "/";
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
						resMap.put("reqResult", "对不起，没有商品相关图片。");
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
		int size=FrameDemoActivity.myApp.hsPhotoList.size();
		for(int i=0;i<size;i++){
			String savePath=mapPaths.get("reqResult"+i);//SDCard地址
			loadPicDataAgain(savePath, gl_checkRecord_goodsRelationPic);
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
//			String picFilePath = (String) ls.get(position).get("picFilePath");
//			Intent intent = new Intent(context, PicZoomActivity.class);
//			intent.putExtra("imagePath", picFilePath);
//			context.startActivity(intent);
			String picFilePath = (String) ls.get(position).get("picFilePath");
			Intent intentOne = new Intent(context,ToImageZoomActivity.class);
			intentOne.putExtra("imagePath", picFilePath);
			intentOne.putExtra("PATH_TYPE", Constant.LoadImage_Intenet);
			context.startActivity(intentOne);
			System.out.println("你点击了第" + position + "张的Gallery图片" + "\n" + "图片路径为：" + picFilePath);
		}
	}
}