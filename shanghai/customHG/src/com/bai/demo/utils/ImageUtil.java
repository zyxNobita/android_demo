package com.bai.demo.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.graphics.Shader.TileMode;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.http.AndroidHttpClient;
import android.text.TextPaint;
import android.util.Log;

/**
 * 关于图片的处理
 * 
 * @author zhangyx
 * 
 */
public class ImageUtil {

	/**
	 * 以最省内存的方式读取本地资源的图片
	 * 
	 * @param context
	 * @param resId
	 * @return
	 */
	public static Bitmap readBitMap(Context context, int resId) {
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Bitmap.Config.RGB_565;
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		// 获取资源图片
		InputStream is = context.getResources().openRawResource(resId);
		return BitmapFactory.decodeStream(is, null, opt);
	}

	/***
	 * Http类访问远程服务器,获取图片
	 * 
	 * @param url
	 * @return
	 */
	public static Bitmap downloadBitmapByCwj(String url) {
		final AndroidHttpClient client = AndroidHttpClient
				.newInstance("Android123");
		final HttpGet getRequest = new HttpGet(url);

		try {
			HttpResponse response = client.execute(getRequest);
			final int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				Log.e("cwjDebug", "Error " + statusCode
						+ " while retrieving bitmap from " + url);
				return null;
			}

			final HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream inputStream = null;
				try {
					inputStream = entity.getContent();
					final Bitmap bitmap = BitmapFactory
							.decodeStream(inputStream);
					return bitmap;
				} finally {
					if (inputStream != null) {
						inputStream.close();
					}
					entity.consumeContent();
				}
			}
		} catch (Exception e) {
			getRequest.abort();
			Log.e("android123Debug", "Error while retrieving bitmap from "
					+ url);
		} finally {
			if (client != null) {
				client.close();
			}
		}
		return null;
	}
	/***
	 * 讲bitmap格式转换为 byte格式
	 * @param bm
	 * @return
	 */
	public static byte[] bitmapTobyte(Bitmap bm){
		byte[] buffer = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		if(bm!=null){
			bm.compress(Bitmap.CompressFormat.JPEG, 60, baos);
			buffer = baos.toByteArray();
		}
		
		return buffer;
	}
	
	/***
	 * 从服务器下载图片到 SDCard 暂存
	 */
	public static void downloadImage(byte[] b,String imageName,String savePath){
		File file = new File(savePath);
		file.mkdirs();
		File img_path = new File(file, imageName+ ".jpg");
		FileOutputStream fos=null;
		try {
			fos = new FileOutputStream(img_path);
			
			fos.write(b,0,b.length);
			fos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(fos!=null){
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}

	/**
	 * Matrix:矩阵类拥有3 x3的坐标变换矩阵。没有一个构造函数矩阵，所以它必须显式初始化使用reset() -如何构建一个矩阵，
	 * 或一组..（）函数（例如settranslate，setrotate，等。）。
	 * Canvas:帆布类拥有“画”的电话。画一些东西，你需要4个基本组成部分：一个位图将像素，帆布举办绘制调用（写入位图），
	 * 一个图元（如矩形，路径，文本，位图），和一个油漆（描述颜色和款式供图）
	 * */

	// 放大缩小图片
	public static Bitmap zoomBitmap(Bitmap bitmap, int w, int h) {
		// 获得原始图片的大小
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		// 得到Matrix工具类
		Matrix matrix = new Matrix();
		float scaleWidht = ((float) w / width);
		float scaleHeight = ((float) h / height);
		matrix.postScale(scaleWidht, scaleHeight);// 设置比率大小
		Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, width, height,
				matrix, true);
		return newbmp;
	}

	// 将Drawable转化为Bitmap
	public static Bitmap drawableToBitmap(Drawable drawable) {
		// 获得原始大小
		int width = drawable.getIntrinsicWidth();
		int height = drawable.getIntrinsicHeight();
		// 图片的大小 、图片类型参数
		Bitmap bitmap = Bitmap.createBitmap(width, height, drawable
				.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
				: Bitmap.Config.RGB_565);
		// 绘画渲染
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, width, height);
		drawable.draw(canvas);
		return bitmap;

	}

	// 获得圆角图片的方法
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {

		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);

		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;// 背景颜色
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);

		return output;
	}

	// 获得带倒影的图片方法
	public static Bitmap createReflectionImageWithOrigin(Bitmap bitmap) {
		final int reflectionGap = 4;
		// 获得图片的长宽
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		// 获得矩阵
		Matrix matrix = new Matrix();
		matrix.preScale(1, -1);

		Bitmap reflectionImage = Bitmap.createBitmap(bitmap, 0, height / 2,
				width, height / 2, matrix, false);
		// 返回加工图片的
		Bitmap bitmapWithReflection = Bitmap.createBitmap(width,
				(height + height / 2), Config.ARGB_8888);

		Canvas canvas = new Canvas(bitmapWithReflection);
		canvas.drawBitmap(bitmap, 0, 0, null);
		Paint deafalutPaint = new Paint();
		canvas.drawRect(0, height, width, height + reflectionGap, deafalutPaint);

		canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);

		Paint paint = new Paint();
		LinearGradient shader = new LinearGradient(0, bitmap.getHeight(), 0,
				bitmapWithReflection.getHeight() + reflectionGap, 0x70ffffff,
				0x00ffffff, TileMode.CLAMP);
		paint.setShader(shader);
		// Set the Transfer mode to be porter duff and destination in
		paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
		// Draw a rectangle using the paint with our linear gradient
		canvas.drawRect(0, height, width, bitmapWithReflection.getHeight()
				+ reflectionGap, paint);

		return bitmapWithReflection;
	}

	// 确定不再需要该bitmap对象的时候可以将其回收掉
	public static void recycle(Bitmap bitmap) {
		if (bitmap != null && !bitmap.isRecycled()) {
			bitmap.recycle();
			System.gc();// 提醒系统及时回收
		}
	}

	// 放在assets中的图片(只读)
	public static Bitmap readBitmap(Context context, String picname) {
		InputStream is;
		Bitmap bitmap = null;
		try {
			is = context.getResources().getAssets().open(picname);// "icon.png"
			bitmap = BitmapFactory.decodeStream(is);

		} catch (IOException e) {

			e.printStackTrace();
		}
		return null;
	}

	// 读取sd卡中的图片
	public static Bitmap readBitmap(String picpath) {
		Bitmap bitmap = BitmapFactory.decodeFile(picpath); // "/sdcard/icon.png"
		return bitmap;
	}

	// 读取网络图片
	public static Drawable getDrawableFromUrl(String url) {
		URLConnection urlConnection;
		Drawable drawable = null;
		try {
			urlConnection = new URL(url).openConnection();
			drawable = Drawable.createFromStream(
					urlConnection.getInputStream(), "image");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return drawable;
	}

	// 读取网络图片
	public static Bitmap getBitMapFromUrl(String url) {
		URLConnection urlConnection;
		Bitmap bitmap = null;
		try {
			urlConnection = new URL(url).openConnection();
			bitmap = BitmapFactory.decodeStream(urlConnection.getInputStream());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bitmap;
	}

	public static Bitmap watermarkBitmap(Bitmap src, Bitmap watermark,
			String title) {
		if (src == null) {
			return null;
		}
		int w = src.getWidth();
		int h = src.getHeight();
		/*
		 * w=195; h=292;
		 */
		// 需要处理图片太大造成的内存超过的问题,这里我的图片很小所以不写相应代码了
		Bitmap newb = Bitmap.createBitmap(w, h, Config.ARGB_8888);// 创建一个新的和SRC长度宽度一样的位图
		Canvas cv = new Canvas(newb);
		cv.drawBitmap(src, 0, 0, null);// 在 0，0坐标开始画入src
		Paint paint = new Paint();
		// System.out.println("-------"+watermark);
		// 加入图片
		if (watermark != null) {
			int ww = watermark.getWidth();
			int wh = watermark.getHeight();
			paint.setAlpha(190);
			watermark = watermarkBitmapPic(watermark, title);
			// cv.drawBitmap(watermark, w - ww + 5, h - wh + 5, paint);//
			// 在src的右下角画入水印
			cv.drawBitmap(watermark, w - ww, h - wh, paint);// 在src的右下角画入水印
		}
		// 加入文字
		if (title != null) {
			String familyName = "宋体";
			Typeface font = Typeface.create(familyName, Typeface.BOLD);
			TextPaint textPaint = new TextPaint();
			textPaint.setColor(Color.RED);
			textPaint.setTypeface(font);
			textPaint.setTextSize(22);
			// 这里是自动换行的
			/*
			 * StaticLayout layout = new
			 * StaticLayout(title,textPaint,w,Alignment
			 * .ALIGN_NORMAL,1.0F,0.0F,true); layout.draw(cv);
			 */
			// 文字就加左上角算了
			paint.setColor(Color.RED);
			// cv.drawText(title,0,0,paint);
		}
		cv.save(Canvas.ALL_SAVE_FLAG);// 保存
		cv.restore();// 存储
		return newb;
	}

	public static Bitmap watermarkBitmapPic(Bitmap watermark, String title) {
		int ww = 0;
		int wh = 0;
		ww = watermark.getWidth();
		wh = watermark.getHeight();
		Bitmap newb = Bitmap.createBitmap(ww, wh, Config.ARGB_8888);// 创建一个新的和SRC长度宽度一样的位图
		// 需要处理图片太大造成的内存超过的问题,这里我的图片很小所以不写相应代码了
		Canvas cv = new Canvas(newb);
		// cv.drawColor(Color.BLUE);
		Paint paint = new Paint();
		// System.out.println("-------"+watermark);

		// 加入图片
		if (watermark != null) {
			ww = watermark.getWidth();
			wh = watermark.getHeight();
			paint.setAlpha(255);
			cv.drawBitmap(watermark, 0, 0, paint);// 在src的右下角画入水印
		}
		// 加入文字
		if (title != null) {
			String familyName = "宋体";
			Typeface font = Typeface.create(familyName, Typeface.BOLD);
			TextPaint textPaint = new TextPaint();
			textPaint.setColor(Color.RED);
			textPaint.setTypeface(font);
			textPaint.setTextSize(18);
			// 这里是自动换行的
			/*
			 * StaticLayout layout = new
			 * StaticLayout(title,textPaint,w,Alignment
			 * .ALIGN_NORMAL,1.0F,0.0F,true); layout.draw(cv);
			 */
			// 文字就加左上角算了
			paint.setColor(Color.RED);
			// cv.drawText(title,ww/2-4,wh/2-4,paint);
			cv.drawText(title, 12, 17, paint);
			// cv.drawText(title,5,5,paint);
		}
		cv.save(Canvas.ALL_SAVE_FLAG);// 保存
		cv.restore();// 存储
		return newb;
	}
}
