package com.tydic.digitalcustom.entity;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tydic.digitalcustom.R;

public class MyAdapterForTextview extends BaseAdapter {

	protected Context context;
	protected LayoutInflater inflater;
	private TextView textView;
	private TextView idTextView;
	private String[] menu;
	private String[] menuId;
	private int positon;

	/**
	 * 
	 * @param context 上写文
  	 * @param menu    菜单列表
	 */
	public MyAdapterForTextview(Context context, String[] menu) {
		this.context = context;
		this.menu = menu;
		inflater = LayoutInflater.from(context);
	}
	
	/**
	 * 
	 * @param context 上写文
  	 * @param menu    菜单列表
	 */
	public MyAdapterForTextview(Context context, String[] menu,String[] menuId) {
		this.context = context;
		this.menu = menu;
		this.menuId = menuId;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return menu.length;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.list_popup_item, null);
		}
		textView = (TextView) convertView.findViewById(R.id.list_item_popup);
		textView.setText(menu[position]);
		
		if(menuId!=null){
			idTextView = (TextView) convertView.findViewById(R.id.list_item_popup_id);
			idTextView.setText(menuId[position]);
		}

		textView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					textView = (TextView) v.findViewById(R.id.list_item_popup);
					textView.setBackgroundColor(Color.WHITE);
					textView.setTextColor(context.getResources().getColor(
							R.color.blue));
				}

				if (event.getAction() == MotionEvent.ACTION_UP) {
					Log.d("----", "up");
				}
				setPositon(position);
				return false;
			}
		});

		return convertView;
	}

	public int getPositon() {
		return positon;
	}

	public void setPositon(int positon) {
		this.positon = positon;
	}

}
