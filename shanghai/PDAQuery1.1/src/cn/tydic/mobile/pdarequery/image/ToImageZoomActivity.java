package cn.tydic.mobile.pdarequery.image;

import cn.tydic.mobile.pdarequery.MainActivity;
import cn.tydic.mobile.pdarequery.R;
import cn.tydic.mobile.pdarequery.tools.Constant;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

/****
 * 图片的缩放功能
 * @author Administrator
 *
 */
public class ToImageZoomActivity extends Activity{
	private GestureImageView giv_zoomImage;
	private String imagePath=null,pathType=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.toimagezoom);
		imagePath=getIntent().getStringExtra("imagePath");
		pathType=getIntent().getStringExtra("PATH_TYPE");
		giv_zoomImage=(GestureImageView) findViewById(R.id.giv_zoomImage);
		
		//加载图片---
		doLoadImage();
		
		findViewById(R.id.ll_picZoomDialog_bottom).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ToImageZoomActivity.this.finish();
			}
		});
	}
	
	private void doLoadImage() {
		// TODO Auto-generated method stub
		if(pathType!=null && !pathType.equals("") && imagePath!=null && !imagePath.equals("")){
			if(pathType.equals(Constant.LoadImage_Local)){//加载本地图片
				giv_zoomImage.setImageBitmap(MainActivity.tool.getLocalBitMap(imagePath));
			}else if(pathType.equals(Constant.LoadImage_Intenet)){//加载网络图片
				new LoadIntenetImage().execute(imagePath);
			}
		}
	}
	
	class LoadIntenetImage extends AsyncTask<String, Integer, Bitmap>{

		@Override
		protected Bitmap doInBackground(String... params) {
			// TODO Auto-generated method stub
			return MainActivity.tool.getBitMapFromUrl(params[0]);
		}
		
		@Override
		protected void onPostExecute(Bitmap result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			giv_zoomImage.setImageBitmap(result);
		}
	}
	
	
	
}
