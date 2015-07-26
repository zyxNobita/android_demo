package com.bai.demo.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector.OnGestureListener;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ListView;

public class MyListView extends ListView{

	private GestureDetector mGesture;
	
	public MyListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		mGesture=new GestureDetector(context, onGestureListener);
	}

	
	protected OnGestureListener onGestureListener=new GestureDetector.SimpleOnGestureListener() {
		
		
		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
				float distanceY) {
			// TODO Auto-generated method stub
			return true;
		}
		
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			return true;
		}
		
		@Override
		public boolean onDown(MotionEvent e) {
			return true;
		}
	};
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		super.dispatchTouchEvent(ev);
		return mGesture.onTouchEvent(ev);
//		return super.dispatchTouchEvent(ev);
	}
	
}
