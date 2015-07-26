package cn.tydic.mobile.pdarequery.tools;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ImageView;

@SuppressLint("DrawAllocation")
public class DrawImageView extends ImageView {

	public DrawImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	Paint paint = new Paint();
	{
		paint.setAntiAlias(true);
		paint.setColor(Color.RED);
		paint.setStyle(Style.STROKE);
		paint.setStrokeWidth(5.0f);// 设置线宽
		paint.setAlpha(100);
	};

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		// /--------------- 调试红色截取框的大小
		// canvas.drawRect(new Rect(120, 200, 620, 300), paint);//绘制矩形
		// 坐标 左 上 右 下
		canvas.drawRect(new Rect(60, 180, 680, 310), paint);// 绘制矩形
	}

}
