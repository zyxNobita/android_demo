package com.bai.demo.adapter;

import java.util.List;

import com.bai.demo.R;
import com.bai.demo.bean.G_HEAD;
import com.bai.demo.entity.Constant;
import com.bai.demo.frame.Menu1Info4Activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class Menu1Info4RightListViewAdapter extends BaseAdapter {
	private LayoutInflater layoutInflater;
	private List<G_HEAD> data;
	private G_HEAD gHead;
	public Menu1Info4RightListViewAdapter(Context context,List<G_HEAD> data){
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

	public G_HEAD getgHead() {
		return gHead;
	}

	public void setgHead(G_HEAD gHead) {
		this.gHead = gHead;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if(convertView==null){
			convertView=layoutInflater.inflate(R.layout.right_menu1info4_listview_right_layout_item, null);
			viewHolder=new ViewHolder();
			viewHolder.tv_one=(TextView) convertView.findViewById(R.id.textview1);
			viewHolder.tv_two=(TextView) convertView.findViewById(R.id.textview2);
			viewHolder.tv_four=(TextView) convertView.findViewById(R.id.textview4);
			viewHolder.tv_five=(TextView) convertView.findViewById(R.id.textview5);
		    viewHolder.tv_six=(TextView) convertView.findViewById(R.id.textview6);
		    convertView.setTag(viewHolder);
		}else{
			viewHolder=(ViewHolder) convertView.getTag();
		}
		viewHolder.tv_one.setText(data.get(position).getUPLOAD_ER());
		viewHolder.tv_two.setText(data.get(position).getUPLOAD_TIME());
		viewHolder.tv_four.setText(data.get(position).getOP_ID());
		viewHolder.tv_five.setText(data.get(position).getEXAM_PROC_CODE());
		if(data.get(position).getREAD_ALARM().equals("1")){
			viewHolder.tv_six.setText("报警处理");
		}else{
			viewHolder.tv_six.setText("审核");
		}
		final int p=position;
		if(viewHolder.tv_six.getText().equals("审核")){
			viewHolder.tv_six.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					gHead=data.get(p);
					Menu1Info4Activity.handler_RM1I4.sendEmptyMessage(Constant.RINGHT_GROUP1_MENU4_CHECK);
				}
			});
		}else{
			viewHolder.tv_six.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					gHead=data.get(p);
					Menu1Info4Activity.handler_RM1I4.sendEmptyMessage(Constant.RINGHT_GROUP1_MENU4_CHECKALARM);
				}
			});
		}
		
		return convertView;
	}
	
	static class ViewHolder{
		TextView tv_one;
		TextView tv_two;
		TextView tv_four;
		TextView tv_five;
		TextView tv_six;
	}

}
