package com.bai.demo.main;

import com.bai.demo.R;
import com.bai.demo.utils.ImageZoomView;
import com.bai.demo.utils.SimpleZoomListener;
import com.bai.demo.utils.ZoomState;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ZoomControls;

/**
 * 图片缩放（类似地图缩放）
 * 获取 要缩放的 BitMap 或则  它的filePath
 * @author zhangyx
 *
 */
@SuppressLint({ "ParserError", "HandlerLeak" })
public class PicZoomActivity extends Activity {
	private String imagePath="";
	private ImageZoomView mZoomView;
	private ZoomState mZoomState;
	private Bitmap mBitmap;
	private SimpleZoomListener mZoomListener;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pic_zoom);
		imagePath = getIntent().getStringExtra("imagePath");
		mZoomView = (ImageZoomView) findViewById(R.id.zoomView);
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				mBitmap = BitmapFactory.decodeFile(imagePath);
//				mBitmap = BitmapFactory.decodeResource(PicZoomActivity.this.getResources(), R.drawable.show_demo1);
//				byte[] byt = getIntent().getByteArrayExtra("bitmap");
//				mBitmap = BitmapFactory.decodeByteArray(byt, 0, byt.length);
				handler.sendEmptyMessage(0);
			}
		});
		thread.start();

		ZoomControls zoomCtrl = (ZoomControls) findViewById(R.id.zoomCtrl);
		zoomCtrl.setOnZoomInClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				float z = mZoomState.getZoom() + 0.25f;
				mZoomState.setZoom(z);
				mZoomState.notifyObservers();
			}
		});
		zoomCtrl.setOnZoomOutClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				float z = mZoomState.getZoom() - 0.25f;
				mZoomState.setZoom(z);
				mZoomState.notifyObservers();
			}
		});
		
		Button btn_picZoomDialogCancel = (Button) findViewById(R.id.btn_picZoomDialogCancel);
		btn_picZoomDialogCancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mBitmap != null){
			mBitmap.recycle();
		}
	}

	private void resetZoomState() {
		mZoomState.setPanX(0.5f);
		mZoomState.setPanY(0.5f);
		mZoomState.setZoom(1f);
		mZoomState.notifyObservers();
	}
	
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			mZoomView.setImage(mBitmap);
			mZoomState = new ZoomState();
			mZoomView.setZoomState(mZoomState);
			mZoomListener = new SimpleZoomListener();
			mZoomListener.setZoomState(mZoomState);
			mZoomView.setOnTouchListener(mZoomListener);
			resetZoomState();
			if(mBitmap.isRecycled()){
				mBitmap.recycle();
				mBitmap = null;
			}
		}
	};
}
