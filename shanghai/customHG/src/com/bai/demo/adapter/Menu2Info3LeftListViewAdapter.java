package com.bai.demo.adapter;

import java.util.List;

import com.bai.demo.R;
import com.bai.demo.bean.ProductInfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class Menu2Info3LeftListViewAdapter extends BaseAdapter {
	
	private LayoutInflater layoutInflater;
//	private List<Map<String,String>> data;
	
	private List<ProductInfo> data;
	
//	public Menu2Info3LeftListViewAdapter(Context context,List<Map<String,String>> data) {
//		this.layoutInflater=LayoutInflater.from(context);
//		this.data=data;
//	}
	
	public Menu2Info3LeftListViewAdapter(Context context, List<ProductInfo> data) {
		this.layoutInflater=LayoutInflater.from(context);
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
		if(convertView==null){
			convertView=layoutInflater.inflate(R.layout.right_menu2info3_listview_left_layout_item, null);
		}
		TextView tv_text=(TextView) convertView.findViewById(R.id.textview1);
//		tv_text.setText(data.get(position).get("item1"));
		tv_text.setText(data.get(position).getCODE_TS());
		return convertView;
	}

}
