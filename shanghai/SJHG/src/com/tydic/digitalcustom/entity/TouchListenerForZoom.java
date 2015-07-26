/**
 * TouchListenerForZoom.java [v 1.0.0]
 * classes: com.tydic.digitalcustom.entity.TouchListenerForZoom
 * 兮兮 Create at 2013-7-11 上午9:25:33
 */
package com.tydic.digitalcustom.entity;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;

/**
 * com.tydic.digitalcustom.entity.TouchListenerForZoom 放大缩小触摸事件
 * 
 * @author 兮兮（bai） <br>
 *         creat at 2013-7-11 上午9:25:33
 * 
 */
@SuppressWarnings("unused")
public class TouchListenerForZoom implements android.view.View.OnTouchListener {

	private Context context;
	private Handler handler;
	private int mode;
	private final int DRAG = 0, ZOOM = 1, NONE = -1;
	private float oldDist, newDist;

	/**
	 * @param context
	 */
	public TouchListenerForZoom(Context context) {
		super();
		this.context = context;
	}

	/**
	 * @param context
	 */
	public TouchListenerForZoom(Context context, Handler handler) {
		super();
		this.context = context;
		this.handler = handler;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getActionMasked()) {
		case MotionEvent.ACTION_DOWN:// 单点触碰
			mode = DRAG;
			break;

		case MotionEvent.ACTION_POINTER_DOWN:// 多点触碰
			oldDist = MyGestureUtil.spacing(event);
			mode = ZOOM;
			break;
		case MotionEvent.ACTION_MOVE:// 移动
			if (mode == DRAG) {
				// Log.d("----", "触摸事件已经回传！");
				// 单点移动交由调用者使用
				return false;
			} else if (mode == ZOOM) {
				newDist = MyGestureUtil.spacing(event);
			}

			break;
		case MotionEvent.ACTION_UP:
			mode = NONE;
			break;

		case MotionEvent.ACTION_POINTER_UP:
			mode = NONE;
			// 判断触摸事件放大缩小事件
			if (MyGestureUtil.isZoomIn(oldDist, newDist)) {
				Message msg = new Message();
				msg.arg1 = Constant.ZOOM_IN;
				handler.sendMessage(msg);
				// Toast.makeText(context, "你想放大", Toast.LENGTH_SHORT).show();
			} else {
				Message msg = new Message();
				msg.arg1 = Constant.ZOOM_OUT;
				handler.sendMessage(msg);
				// Toast.makeText(context, "你想缩小", Toast.LENGTH_SHORT).show();
			}

			break;
		}
		return true;
	}
}
