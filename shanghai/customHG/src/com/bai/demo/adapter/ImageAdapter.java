package com.bai.demo.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

/**
 * 图片本地加载适配器
 * @author ouyyt
 *
 */
public class ImageAdapter extends BaseAdapter {

	private Context context;
	private List<Map<String, Object>> ls;
	
	public void setLs(List<Map<String,Object>> ls) {
		this.ls = ls;
	}

	public ImageAdapter(Context context, List<Map<String,Object>> ls) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.ls = ls;
		if(this.ls == null && this.ls.isEmpty()){
			this.ls = new ArrayList<Map<String,Object>>();
		}
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return ls.size();
	}
	
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return ls.get(position);
	}
	
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ImageView iv = new ImageView(context);
		// 获取图片路径
		String picFilePath = (String) ls.get(position).get("picFilePath");
		System.out.println("filePath--->"+picFilePath);
		
		try {
			// 读取文件流对象
//			FileInputStream fin=new FileInputStream(new File(picFilePath));
			// 通过字节流获取图片
//			Bitmap bm=BitmapFactory.decodeStream(fin);
			Bitmap bm = BitmapFactory.decodeFile(picFilePath);
			// 生成略缩图信息
			bm = ThumbnailUtils.extractThumbnail(bm, 250, 250);
			// 设置图片到ImageView上
			iv.setImageBitmap(bm);
			// 设置边界对齐
			iv.setAdjustViewBounds(true);
			// 设置Gallery的布局参数(千万不能错)
			iv.setLayoutParams(new Gallery.LayoutParams (300, 300));				
//			fin.close();
		}  catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return iv;
	}
}