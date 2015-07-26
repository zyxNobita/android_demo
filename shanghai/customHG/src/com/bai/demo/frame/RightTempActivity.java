package com.bai.demo.frame;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.bai.demo.R;

public class RightTempActivity extends RightWindowBase {

	public RightTempActivity(Context context) {
		super(context);
		setupViews();
	}

	public RightTempActivity(Context context, AttributeSet attrs) {
		super(context, attrs);
		setupViews();
	}

	private void setupViews() {
		LinearLayout layout = (LinearLayout) LinearLayout.inflate(getContext(),
				R.layout.right_temp, null);

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
