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

public class Menu2Info4RightSecondListViewAdapter extends BaseAdapter {

	private LayoutInflater layoutInflater;
	private List<G_RSK_LIST> data;
	
	private static class ViewHolder {
		private TextView tv_text1;
		private TextView tv_text2;
	}
	
	public Menu2Info4RightSecondListViewAdapter(Context context,List<G_RSK_LIST> data) {
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
		ViewHolder holder = null;
		if(convertView == null){
			convertView = layoutInflater.inflate(R.layout.right_menu2info4_listview_second_right_layout_item, null);
			holder = new ViewHolder();
			holder.tv_text1 = (TextView) convertView.findViewById(R.id.textview1);
			holder.tv_text2 = (TextView) convertView.findViewById(R.id.textview2);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tv_text1.setText(data.get(position).getCODE_TS());
		holder.tv_text2.setText(data.get(position).getG_RSK_SUM());
		return convertView;
	}
	
}
