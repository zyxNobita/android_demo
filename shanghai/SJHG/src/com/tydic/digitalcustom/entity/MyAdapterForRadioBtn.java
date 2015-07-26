package com.tydic.digitalcustom.entity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.tydic.digitalcustom.widget.RadioButton;

public class MyAdapterForRadioBtn extends BaseAdapter {

	private Context context;
	protected LayoutInflater inflater;
	private RadioButton temp;
	private String menu[];

	public MyAdapterForRadioBtn(Context context, String[] menu) {
		super();
		this.context = context;
		this.menu = menu;
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
	public View getView( int position, View convertView, ViewGroup parent) {
		final RadioButton radioButton;

		// 重用了listview个convertView.所以会出现这种bug，解决方法也很简单，只需要我们把上面代码更换为
		radioButton = new RadioButton(context);

		radioButton.setText(menu[position]);
		
		radioButton.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// 模版不为空，则change.
				if (temp != null) {
					temp.ChangeImage();
				}
				temp = radioButton;
				radioButton.ChangeImage();
				return false;
			}
		});
		return radioButton;
	}

}
