package com.bai.demo.adapter;

import java.util.List;
import java.util.Map;

import com.bai.demo.R;
import com.bai.demo.bean.EXAM_DEP_MID_NEW_REL;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class Menu3Info4RightFirstListViewAdapter extends BaseAdapter {

	private LayoutInflater layoutInflater;
	private List<EXAM_DEP_MID_NEW_REL> data;
	
	public Menu3Info4RightFirstListViewAdapter(Context context,List<EXAM_DEP_MID_NEW_REL> data){
		layoutInflater=LayoutInflater.from(context);
		this.data=data;
	}
	
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
	
	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHodler holder;
		if(convertView==null){
			convertView=layoutInflater.inflate(R.layout.right_menu3info4_listview_right_layout_item, null);
			holder=new ViewHodler();
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
		}else{
			holder=(ViewHodler) convertView.getTag();
		}
	
		holder.tv_text1.setText(data.get(position).getSUM()+"");
		holder.tv_text2.setText(data.get(position).getCONTAINER()+"");
		holder.tv_text3.setText(data.get(position).getCONTAINER_CATCH()+"");
		holder.tv_text4.setText(data.get(position).getSAND()+"");
		holder.tv_text5.setText(data.get(position).getCATCH()+"");
		holder.tv_text6.setText(data.get(position).getCATCH_RATE()+"");
		holder.tv_text7.setText(data.get(position).getDAYMAX()+"");
		holder.tv_text8.setText(data.get(position).getSEND()+"");
		holder.tv_text9.setText(data.get(position).getSEND_RATE()+"");
		return convertView;
	}
	
}
