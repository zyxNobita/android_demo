package com.bai.demo.adapter;

import java.util.List;

import com.bai.demo.R;
import com.bai.demo.bean.G_RSK_LIST;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class Menu2Info4LeftSecondListViewAdapter extends BaseAdapter {
	
	private LayoutInflater layoutInflater;
	private List<G_RSK_LIST> data;
	
	public Menu2Info4LeftSecondListViewAdapter(Context context,List<G_RSK_LIST> data) {
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
			convertView=layoutInflater.inflate(R.layout.right_menu2info4_listview_second_left_layout_item, null);
		}
		TextView tv_text=(TextView) convertView.findViewById(R.id.textview1);
		tv_text.setText(data.get(position).getG_NAME());
		return convertView;
	}

}
