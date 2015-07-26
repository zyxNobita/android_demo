package com.bai.demo.adapter;

import java.util.List;

import com.bai.demo.R;
import com.bai.demo.bean.ENTRY_LIST;
import com.bai.demo.entity.Constant;
import com.bai.demo.main.FrameDemoActivity;
import com.bai.demo.utils.MyDialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class Menu1Info2RightListViewAdapter extends BaseAdapter {
	private LayoutInflater layoutInflater;
	private List<ENTRY_LIST> data;
	
	public Menu1Info2RightListViewAdapter(Context context,List<ENTRY_LIST> data){
		layoutInflater=LayoutInflater.from(context);
		this.data=data;
	}
	
	private static  class ViewHolder{
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
		private TextView tv_text14;
		private TextView tv_text15;
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

	@SuppressLint("ParserError")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder=null;
		if(convertView==null){
			convertView=layoutInflater.inflate(R.layout.right_menu1info2_listview_right_layout_item, null);
//			final HorizontalScrollView menu1_scrollview = (HorizontalScrollView) convertView.findViewById(R.id.menu1_scrollView);
//			menu1_scrollview.setFocusable(true);
//			System.out.println("11111111111111111111111111111111");
//			menu1_scrollview.setOnTouchListener(new View.OnTouchListener() {
//				@Override
//				public boolean onTouch(View v, MotionEvent event) {
//					System.out.println("00000000000000000");
//					v.requestFocus();
//					v.requestFocusFromTouch();
//					return false;
//				}
//			});	
			holder=new ViewHolder();
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
			holder.tv_text14=(TextView) convertView.findViewById(R.id.textview14);
			holder.tv_text15=(TextView) convertView.findViewById(R.id.textview15);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		holder.tv_text1.setText(data.get(position).getCODE_TS());//CODE_TS;//商品编码
		holder.tv_text2.setText(data.get(position).getG_NAME());//G_NAME;//商品名称
		holder.tv_text3.setText(data.get(position).getG_MODEL());//G_MODEL;//规格型号
		holder.tv_text4.setText(data.get(position).getG_QTY()+"");//G_QTY;//申报数量
		holder.tv_text5.setText(data.get(position).getG_UNIT());//G_UNIT;//申报单位
		holder.tv_text6.setText(data.get(position).getQTY_1()+"");//
		holder.tv_text7.setText(data.get(position).getUNIT_1());//
		holder.tv_text8.setText(data.get(position).getQTY_2()+"");//
		holder.tv_text9.setText(data.get(position).getORIGIN_COUNTRY());//
		holder.tv_text10.setText(data.get(position).getDECL_PRICE()+"");
		holder.tv_text11.setText(data.get(position).getDECL_TOTAL()+"");
		holder.tv_text12.setText(data.get(position).getTRADE_TOTAL()+"");
		holder.tv_text13.setText(data.get(position).getTRADE_CURR()+"");
		holder.tv_text14.setText(data.get(position).getDUTY_MODE());
		holder.tv_text15.setText(data.get(position).getUSE_TO());	
		
		
		holder.tv_text1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				FrameDemoActivity.handler.sendEmptyMessage(Constant.RINGHT_GROUP2_MENU2);
				MyDialog.dismissDialog();
			}
		});
		
		holder.tv_text2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				FrameDemoActivity.handler.sendEmptyMessage(Constant.RINGHT_GROUP2_MENU3);
				MyDialog.dismissDialog();
			}
		});
		
		return convertView;
	}

}
