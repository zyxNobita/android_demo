package com.bai.demo.adapter;

import java.util.List;
import java.util.Map;

import com.bai.demo.R;
import com.bai.demo.bean.ENTRY_LIST;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class Menu1Info3LeftListViewAdapter extends BaseAdapter{
	private LayoutInflater layoutInflater;
	private List<ENTRY_LIST> data;
	public Menu1Info3LeftListViewAdapter(Context context,List<ENTRY_LIST> data) {
		this.layoutInflater=LayoutInflater.from(context);
		this.data=data;
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
		if(convertView==null){
			convertView=layoutInflater.inflate(R.layout.right_menu1info3_listview_left_layout_item, null);
		}
		TextView tv_text=(TextView) convertView.findViewById(R.id.textview1);
		tv_text.setText(data.get(position).getCODE_TS());
		return convertView;
	}


}
