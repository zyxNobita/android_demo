package com.tydic.digitalcustom.entity;

import android.util.FloatMath;
import android.view.MotionEvent;

/**
 * 
 * @author xixi
 * 
 */
public class MyGestureUtil {

	/**
	 * 求多点触控间距离
	 * 
	 * @param event
	 * @return distance
	 */
	public static float spacing(MotionEvent event) {
		try {

			float x = event.getX(0) - event.getX(1);
			float y = event.getY(0) - event.getY(1);

			return FloatMath.sqrt(x * x + y * y);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 多点触控是否放大
	 * 
	 * @param event
	 * @return true 放大 <br>
	 *         false 缩小
	 */
	public static Boolean isZoomIn(float oldDist, float newDist) {
		if (newDist >= oldDist) {
			return true;
		} else {
			return false;
		}
	}

}
