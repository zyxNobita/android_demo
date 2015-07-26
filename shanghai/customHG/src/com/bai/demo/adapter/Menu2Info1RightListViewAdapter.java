package com.bai.demo.adapter;

import java.util.List;

import com.bai.demo.R;
import com.bai.demo.bean.TradeInfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class Menu2Info1RightListViewAdapter extends BaseAdapter {

	private LayoutInflater layoutInflater;
//	private List<Map<String,String>> data;
	private List<TradeInfo> data;
	
//	public Menu2Info1RightListViewAdapter(Context context,List<Map<String,String>> data){
//		layoutInflater=LayoutInflater.from(context);
//		this.data=data;
//	}
	
	public Menu2Info1RightListViewAdapter(Context context,List<TradeInfo> data){
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
		if(convertView==null){
			convertView=layoutInflater.inflate(R.layout.right_menu2info1_listview_right_layout_item, null);
		}
		TextView tv_text1=(TextView) convertView.findViewById(R.id.textview1);
//		tv_text1.setText(data.get(position).get("item2"));
		tv_text1.setText(data.get(position).getTRADE_NAME());
		TextView tv_text2=(TextView) convertView.findViewById(R.id.textview2);
//		tv_text2.setText(data.get(position).get("item3"));
		tv_text2.setText(data.get(position).getDEC_SUM()+"");
		TextView tv_text3=(TextView) convertView.findViewById(R.id.textview3);
//		tv_text3.setText(data.get(position).get("item4"));
		tv_text3.setText(data.get(position).getEXAM_SUM()+"");
		TextView tv_text4=(TextView) convertView.findViewById(R.id.textview4);
//		tv_text4.setText(data.get(position).get("item5"));
		tv_text4.setText(data.get(position).getEXAM_RATE()+"");
		TextView tv_text5=(TextView) convertView.findViewById(R.id.textview5);
//		tv_text5.setText(data.get(position).get("item6"));
		tv_text5.setText(data.get(position).getEXAM_CATCH()+"");
		TextView tv_text6=(TextView) convertView.findViewById(R.id.textview6);
//		tv_text6.setText(data.get(position).get("item7"));
		tv_text6.setText(data.get(position).getEXAM_CATCH_RATE()+"");
		TextView tv_text7=(TextView) convertView.findViewById(R.id.textview7);
//		tv_text7.setText(data.get(position).get("item8"));
		tv_text7.setText(data.get(position).getCATCH_TAX()+"");
		TextView tv_text8=(TextView) convertView.findViewById(R.id.textview8);
		tv_text8.setText(data.get(position).getCATCH_CERT()+"");
		TextView tv_text9=(TextView) convertView.findViewById(R.id.textview9);
		tv_text9.setText(data.get(position).getABOUT_POWER()+"");
		TextView tv_text10=(TextView) convertView.findViewById(R.id.textview10);
		tv_text10.setText(data.get(position).getABOUT_ARMY()+"");
		TextView tv_text11=(TextView) convertView.findViewById(R.id.textview11);
		tv_text11.setText(data.get(position).getCATCH_CASE()+"");
		return convertView;
	}
	
}
