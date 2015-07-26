package com.bai.demo.adapter;

import java.util.List;
import java.util.Map;

import com.bai.demo.R;
import com.bai.demo.bean.EXAM_PER_MID_REL;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class Menu3Info3RightListViewAdapter extends BaseAdapter {

	private LayoutInflater layoutInflater;
	private List<EXAM_PER_MID_REL> data;
	
	public Menu3Info3RightListViewAdapter(Context context,List<EXAM_PER_MID_REL> data){
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
		private TextView tv_text10;
		private TextView tv_text11;
		private TextView tv_text12;
		private TextView tv_text13;
	
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

	@SuppressWarnings("null")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHodler holder=null;
		if(convertView==null){
			convertView=layoutInflater.inflate(R.layout.right_menu3info3_listview_right_layout_item, null);
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
			holder.tv_text10=(TextView) convertView.findViewById(R.id.textview10);
			holder.tv_text11=(TextView) convertView.findViewById(R.id.textview11);
			holder.tv_text12=(TextView) convertView.findViewById(R.id.textview12);
			holder.tv_text13=(TextView) convertView.findViewById(R.id.textview13);
			convertView.setTag(holder);
		}else{
			holder=(ViewHodler) convertView.getTag();
		}
		holder.tv_text1.setText(data.get(position).getOP_NAME());
		holder.tv_text2.setText(data.get(position).getEXAM_SUM()+"");
		holder.tv_text3.setText(data.get(position).getEXAM_CATCH()+"");
		holder.tv_text4.setText(data.get(position).getEXAM_CATCH_RATE()+"");
		holder.tv_text5.setText(data.get(position).getSEND()+"");
		holder.tv_text6.setText(data.get(position).getSEND_RATE()+"");
		holder.tv_text7.setText(data.get(position).getCATCH_CERT()+"");
		holder.tv_text8.setText(data.get(position).getCATCH_TAX()+"");
		holder.tv_text9.setText(data.get(position).getCATCH_DELETE()+"");
		holder.tv_text10.setText(data.get(position).getCATCH_OTHER()+"");
		holder.tv_text11.setText(data.get(position).getABOUT_POWER()+"");
		holder.tv_text12.setText(data.get(position).getABOUT_ARMY()+"");
		holder.tv_text13.setText(data.get(position).getCATCH_CASE()+"");
		return convertView;
	}
	
}
