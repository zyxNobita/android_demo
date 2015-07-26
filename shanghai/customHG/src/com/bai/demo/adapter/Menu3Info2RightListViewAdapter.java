package com.bai.demo.adapter;

import java.util.List;
import java.util.Map;

import com.bai.demo.R;
import com.bai.demo.bean.EXAM_MID_REL;
import com.bai.demo.utils.MyDialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Menu3Info2RightListViewAdapter extends BaseAdapter {

	private LinearLayout lv_RM3I2_dialogView;
	private LayoutInflater layoutInflater;
	private List<EXAM_MID_REL> data;
	
	private Button btn_dialogClose;
	private Context context;
	
	private static class ViewHodler{
		private TextView tv_text1;
		private TextView tv_text2;
		private TextView tv_text3;
		private TextView tv_text4;
		private TextView tv_text5;
		private TextView tv_text6;
		private TextView tv_text7;
		private TextView tv_text8;
		private TextView tv_text9;
	}
	
	public Menu3Info2RightListViewAdapter(Context context,List<EXAM_MID_REL> data){
		this.context = context;
		layoutInflater = LayoutInflater.from(context);
		this.data = data;
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
		if(convertView==null){
			convertView = layoutInflater.inflate(R.layout.right_menu3info2_listview_right_layout_item, null);
			holder = new ViewHodler();
			holder.tv_text1=(TextView) convertView.findViewById(R.id.textview1);
			holder.tv_text2=(TextView) convertView.findViewById(R.id.textview2);
			holder.tv_text3=(TextView) convertView.findViewById(R.id.textview3);
			holder.tv_text4=(TextView) convertView.findViewById(R.id.textview4);
			holder.tv_text5=(TextView) convertView.findViewById(R.id.textview5);
			holder.tv_text6=(TextView) convertView.findViewById(R.id.textview6);
			holder.tv_text7=(TextView) convertView.findViewById(R.id.textview7);
			holder.tv_text8=(TextView) convertView.findViewById(R.id.textview8);
			holder.tv_text9=(TextView) convertView.findViewById(R.id.textview9);
			convertView.setTag(holder);
		}else {
			holder=(ViewHodler) convertView.getTag();
		}
		holder.tv_text1.setText(data.get(position).getTRADE_NAME());
		holder.tv_text2.setText(data.get(position).getAGENT_NAME());
		holder.tv_text3.setText(data.get(position).getG_NAME());
		holder.tv_text4.setText(data.get(position).getEXAM_TIME());
		holder.tv_text5.setText(data.get(position).getEXAM_MODE());
		holder.tv_text6.setText(data.get(position).getEXAM_ER());
		holder.tv_text7.setText(data.get(position).getEXAM_PROC_IDEA());
		holder.tv_text8.setText(data.get(position).getEXAM_PROC_CODE());
		holder.tv_text9.setText(data.get(position).getI_E_FLAG());

		return convertView;
	}
	
}
