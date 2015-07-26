package com.tydic.digitalcustom.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tydic.digitalcustom.R;

/***
 * listview 元素 包含单选框+箭头
 * 
 * @author zhangjia ,baiyin
 * @date 2013.5.31
 * 
 */
public class RadioButton extends LinearLayout {
	protected Context context;
	private ImageView imageView;
	private TextView textView;
	private LinearLayout layout;

	private int index = 0;
	private int id = 0;// 判断是否选中

	// private RadioButton tempRadioButton;// 模版用于保存上次点击的对象

	private int state[] = { R.drawable.radio_unchecked,
			R.drawable.radio_checked };
	private int bg[] = { R.drawable.bg_menu, R.drawable.bg_menu_selector };
	private int txtColor[] = { getResources().getColor(R.color.blue),
			Color.WHITE };

	/***
	 * 改变图片和背景
	 */
	public void ChangeImage() {

		index++;
		id = index % 2;// 获取图片id
		textView.setTextColor(txtColor[id]);
		imageView.setImageResource(state[id]);
		// layout.setBackgroundColor(Color.BLUE);
		layout.setBackgroundResource(bg[id]);
	}

	/***
	 * 设置文本
	 * 
	 * @param text
	 */
	public void setText(String text) {
		textView.setText(text);
	}

	public String getText() {
		return id == 0 ? "" : textView.getText().toString();

	}

	public RadioButton(Context context) {
		this(context, null);

	}

	public RadioButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		layout = (LinearLayout) LayoutInflater.from(context).inflate(
				R.layout.list_menu_item, this, true);
		layout.setBackgroundResource(bg[0]);
		imageView = (ImageView) findViewById(R.id.list_item_iv);
		textView = (TextView) findViewById(R.id.list_item_txt);
		// arrow = (ImageView) findViewById(R.id.list_item_arrow);

	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		
		return super.dispatchTouchEvent(ev);
	}
	
	
}
