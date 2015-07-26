package cn.tydic.mobile.pdarequery.tools;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class MyGridView extends GridView {
	
//	private boolean haveScrollbar = true;

	public MyGridView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public MyGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	public MyGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}
	
	// 方法一
    /**
     * 设置是否有ScrollBar，当要在ScollView中显示时，应当设置为false。 默认为 true
     *
     * @param haveScrollbars
     */
//    public void setHaveScrollbar(boolean haveScrollbar) {
//        this.haveScrollbar = haveScrollbar;
//    }
//    
//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {  
//        if (haveScrollbar == false) {
//            int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
//            super.onMeasure(widthMeasureSpec, expandSpec);
//        } else {
//            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        }
//    }
	
	// 方法二
//	@Override
//	public boolean dispatchTouchEvent(MotionEvent ev) {
//
//		if (ev.getAction() == MotionEvent.ACTION_MOVE) {
//			return true; // 禁止GridView滑动
//		}
//		return super.dispatchTouchEvent(ev);
//
//	}
    
	// 该自定义控件只是重写了GridView的onMeasure方法，使其不会出现滚动条，ScrollView嵌套ListView也是同样的道理。 
    @Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}

}