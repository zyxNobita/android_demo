package com.bai.demo.frame;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;

public abstract class RightWindowBase extends FrameLayout {

	public TextView mContentTextView;
	
	private LayoutParams params = new LayoutParams(LayoutParams.FILL_PARENT,
			LayoutParams.FILL_PARENT);
		
	public RightWindowBase(Context context){
		super(context);
		setupViews();
	}
	
	public RightWindowBase(Context context, AttributeSet attrs) {
		super(context, attrs);
		setupViews();
	}
	
	private void setupViews(){
		mContentTextView = new TextView(getContext());
		mContentTextView.setLayoutParams(params);
	}
	
	//��Щ��Ϊ����չ��������
	public abstract void dosomething();
	//��Щ��2
	public abstract void dosomething2();

}

