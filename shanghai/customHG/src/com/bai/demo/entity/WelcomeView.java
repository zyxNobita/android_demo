package com.bai.demo.entity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Message;
import android.util.AttributeSet;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.bai.demo.R;

/**
 * 程序开启动画
 * 
 * @author bai
 * 
 */
public class WelcomeView extends SurfaceView implements SurfaceHolder.Callback // 实现生命周期回调接口
{

	WelcomeActivity activity;// activity的引用
	Paint paint; // 画笔
	int currentAlpha = 0; // 当前的不透明值
	int screenWidth = 320; // 屏幕宽度
	int screenHeight = 480; // 屏幕高度
	int sleepSpan = 2000; // 动画的时延ms
	int picWidth, picHeight;
	Bitmap[] logos = new Bitmap[1];// logo图片数组
	Bitmap currentLogo; // 当前logo图片引用
	int currentX; // 图片位置
	int currentY;

	/**
	 * 系统推荐构建方法 @author bai
	 * 
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public WelcomeView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public WelcomeView(WelcomeActivity activity) {
		super(activity);
		this.activity = activity;
		this.getHolder().addCallback(this); // 设置生命周期回调接口的实现者
		paint = new Paint(); // 创建画笔
		paint.setAntiAlias(true); // 打开抗锯齿
		// 加载图片
		logos[0] = BitmapFactory.decodeResource(activity.getResources(),
				R.drawable.welcome);
		picWidth = logos[0].getWidth();
		picHeight = logos[0].getHeight();
		// 得到屏幕大小
		Display dis = activity.getWindowManager().getDefaultDisplay();
		screenWidth = dis.getWidth();
		screenHeight = dis.getHeight();
	}

	public void onDraw(Canvas canvas) {

		// 绘制黑填充矩形清背景
		paint.setColor(Color.BLACK);// 设置画笔颜色
		// 进行平面贴图
		if (currentLogo == null)
			return;
		canvas.drawBitmap(currentLogo, currentX, currentY, paint);
	}

	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
	}

	public void surfaceCreated(SurfaceHolder holder) // 创建时被调用
	{
		new Thread() {
			public void run() {
				for (Bitmap bm : logos) {
					currentLogo = bm;// 当前图片的引用
					currentX = screenWidth / 2 - bm.getWidth() / 2;// 图片位置
					currentY = screenHeight / 2 - bm.getHeight() / 2;

					SurfaceHolder myholder = WelcomeView.this.getHolder();// 获取回调接口
					Canvas canvas = myholder.lockCanvas();// 获取画布
					try {
						synchronized (myholder)// 同步
						{
							onDraw(canvas);// 进行绘制绘制
						}
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						if (canvas != null)// 如果当前画布不为空
						{
							myholder.unlockCanvasAndPost(canvas);// 解锁画布
						}
					}
				}
				try {
					sleep(sleepSpan);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Message msg = new Message();
				msg.what = Constant.GOTOLOGIN;
				WelcomeActivity.handler.sendMessage(msg);
			}
		}.start();
	}

	public void surfaceDestroyed(SurfaceHolder arg0) {// 销毁时被调用
	}
}