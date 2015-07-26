package com.bai.demo.frame;

import com.bai.demo.R;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class MenuVersionActivity extends RightWindowBase{

	public MenuVersionActivity(Context context){
		super(context);
		setupViews();
	}
	public MenuVersionActivity(Context context, AttributeSet attrs) {
		super(context, attrs);
		setupViews();
	}
	
	private void setupViews(){
		LinearLayout layout = (LinearLayout) LinearLayout.inflate(getContext(),
				R.layout.right_version, null);

		addView(layout);
	}

	@Override
	public void dosomething() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dosomething2() {
		// TODO Auto-generated method stub
		
	}

}
