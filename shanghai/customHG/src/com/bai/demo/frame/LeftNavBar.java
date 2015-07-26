package com.bai.demo.frame;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.bai.demo.R;

public class LeftNavBar extends FrameLayout {

	public LeftNavBar(Context context){
		super(context);
		setupViews();
	}
	
	public LeftNavBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		setupViews();
	}
	
	private void setupViews(){
		final LayoutInflater mLayoutInflater = LayoutInflater.from(getContext());
		View v = mLayoutInflater.inflate(R.layout.nav_bar, null);
		addView(v);
	}

}
