package com.bai.demo.adapter;

import java.util.List;

import com.bai.demo.R;
import com.bai.demo.bean.COMPLEX;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class Menu2Info4RightThirdListViewAdapter extends BaseAdapter {

	private LayoutInflater layoutInflater;
	private List<COMPLEX> data;
	
	private static class ViewHolder {
		private TextView tv_text1;
		private TextView tv_text2;
		private TextView tv_text3;
		private TextView tv_text4;
		private TextView tv_text5;
		private TextView tv_text6;
		private TextView tv_text7;
		private TextView tv_text8;
		private TextView tv_text9;
		private TextView tv_text10;
		private TextView tv_text11;
	}
	
	public Menu2Info4RightThirdListViewAdapter(Context context,List<COMPLEX> data){
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
			convertView = layoutInflater.inflate(R.layout.right_menu2info4_listview_third_right_layout_item, null);
			holder = new ViewHolder();
			holder.tv_text1 = (TextView) convertView.findViewById(R.id.textview1);
			holder.tv_text2 = (TextView) convertView.findViewById(R.id.textview2);
			holder.tv_text3 = (TextView) convertView.findViewById(R.id.textview3);
			holder.tv_text4 = (TextView) convertView.findViewById(R.id.textview4);
			holder.tv_text5 = (TextView) convertView.findViewById(R.id.textview5);
			holder.tv_text6 = (TextView) convertView.findViewById(R.id.textview6);
			holder.tv_text7 = (TextView) convertView.findViewById(R.id.textview7);
			holder.tv_text8 = (TextView) convertView.findViewById(R.id.textview8);
			holder.tv_text9 = (TextView) convertView.findViewById(R.id.textview9);
			holder.tv_text10 = (TextView) convertView.findViewById(R.id.textview10);
			holder.tv_text11 = (TextView) convertView.findViewById(R.id.textview11);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tv_text1.setText(data.get(position).getG_NAME());
		holder.tv_text2.setText(data.get(position).getBEGIN_DATE());
		holder.tv_text3.setText(data.get(position).getLSJM_FLAG());
		holder.tv_text4.setText(data.get(position).getEND_DATE());
		holder.tv_text5.setText(data.get(position).getLOW_RATE()+"");
		holder.tv_text6.setText(data.get(position).getHIGH_RATE()+"");
		holder.tv_text7.setText(data.get(position).getOUT_RATE()+"");
		holder.tv_text8.setText(data.get(position).getTAX_RATE()+"");
		holder.tv_text9.setText(data.get(position).getUNIT_1());
		holder.tv_text10.setText(data.get(position).getCONTROL_MARK());
		holder.tv_text11.setText(data.get(position).getNOTE_S());
		return convertView;
	}
	
}
