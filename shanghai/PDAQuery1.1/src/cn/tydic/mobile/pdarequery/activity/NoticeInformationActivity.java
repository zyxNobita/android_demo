package cn.tydic.mobile.pdarequery.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.BitmapUtils;

import cn.tydic.mobile.pdarequery.MainActivity;
import cn.tydic.mobile.pdarequery.R;
import cn.tydic.mobile.pdarequery.entity.Image;
import cn.tydic.mobile.pdarequery.entity.NoticeInformation;
import cn.tydic.mobile.pdarequery.image.ToImageZoomActivity;
import cn.tydic.mobile.pdarequery.tools.Constant;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/***
 * 公告信息查询:整车公告信息---------PDA
 * @author zhangyx
 *
 */
public class NoticeInformationActivity extends Activity {
	
	private NoticeInformation notice;
	private ImageView iv_image1,iv_image2,iv_image3,iv_image4,iv_image5,iv_image6;// 加载图片1、图片2、图片3、图片4、图片5、图片6
	private List<Image> imageLists=new ArrayList<Image>();
	private BitmapUtils bitmapUtils;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.noticeinformation);
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		String p=getIntent().getStringExtra("position");
		if(p!=null && !p.equals("")){
			notice=QueryNoticeInformationActivity.noticeLists.get(Integer.valueOf(p));
		}
		iv_image1=(ImageView) findViewById(R.id.iv_image1);
		iv_image2=(ImageView) findViewById(R.id.iv_image2);
		iv_image3=(ImageView) findViewById(R.id.iv_image3);
		iv_image4=(ImageView) findViewById(R.id.iv_image4);
		iv_image5=(ImageView) findViewById(R.id.iv_image5);
		iv_image6=(ImageView) findViewById(R.id.iv_image6);
		findViewById(R.id.ll_noticeInfo_return).setOnClickListener(new MyOnClickListener());
		findViewById(R.id.ll_noticeInfo_close).setOnClickListener(new MyOnClickListener());
		findViewById(R.id.ll_noticeInfo_chassis).setOnClickListener(new MyOnClickListener());
		showCarInformation();
	
	}

	class MyOnClickListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			findViewById(R.id.ll_bottom1).setBackgroundResource(R.color.light_blue);
			findViewById(R.id.ll_bottom2).setBackgroundResource(R.color.light_blue);
			switch (v.getId()) {
			case R.id.ll_noticeInfo_return:
				finish();
				overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
				break;
			case R.id.ll_noticeInfo_close:
				findViewById(R.id.ll_bottom2).setBackgroundResource(R.color.dark_blue);
				findViewById(R.id.imgBtn_noticeInfo_close).setBackgroundResource(R.drawable.return_on);
				finish();
				overridePendingTransition(R.anim.push_right_in,
						R.anim.push_right_out);
				break;
				
			case R.id.ll_noticeInfo_chassis://整车底盘-----底盘Id
				findViewById(R.id.ll_bottom1).setBackgroundResource(R.color.dark_blue);
				findViewById(R.id.imgBtn_noticeInfo_chassis).setBackgroundResource(R.drawable.chassis_on);
				String id=notice.getDpid().replaceAll(",", "#").trim();
				System.out.println("底盘ID"+id);
				if(id!=null && !id.equals("") && !id.equals("- -")){
					Intent intent=new Intent(NoticeInformationActivity.this,QueryNoticeChassisActivity.class);
					intent.putExtra("dpid",id);
					startActivity(intent);
				}else{
					Toast.makeText(getApplicationContext(), "服务器无该条数据的底盘信息。", Toast.LENGTH_SHORT).show();
				}
				break;
			}
		}
	}
	
	/**
	 * 请求 公告信息查询:整车公告信息----获取ImagePath
	 * 
	 * @author zhangyx
	 * 
	 */
	class MyAsyncTaskREQNoticeImages extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			MainActivity.tool.startProgressDialog(getApplicationContext());
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String resultStr = "";
			// //接口1：----测试成功
			Constant.webService.setMETHOD_NAME(getApplicationContext()
					.getString(R.string.Connection_MethodName));
			Constant.webService.setWEBSERVICE(getApplicationContext()
					.getString(R.string.Connection_Webservice));
			if (Constant.webService.connect(new String[] { "jkid","userid","authid","QueryXmlDoc" },
					new Object[] { "V-ST-PHOTO","SHDIC","%C6%AC%8CD%FFz%3B%0A%F5%9F%DF%018K%89%85",params[0]})) {
				if (Constant.webService.getResult() != null) {
					resultStr = Constant.webService.getResult().toString();
				}
			}
			return resultStr;
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			System.out.println("返回结果："+result);
			MainActivity.tool.stopProgressDialog();
			if (result != null && !result.equals("")) {
				try {
					JSONObject obj = new JSONObject(result);
					String res = obj.getString("RESULT");
					String data = obj.getString("DATA");
					if (res.equals("TRUE")) {
						imageLists.clear();
						imageLists.addAll((List) MainActivity.gson.fromJson(
								data, new TypeToken<List<Image>>() {
								}.getType()));
						shouImages();
					} else if (res.equals("FALSE")) {
						Toast.makeText(getApplicationContext(), data,
								Toast.LENGTH_LONG).show();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				Toast.makeText(getApplicationContext(), "网络异常或者服务器异常。",
						Toast.LENGTH_LONG).show();
			}
		}
	};
	/**
	 * 显示图片，点击图片进行缩放
	 */
	private void shouImages() {
		// TODO Auto-generated method stub
		iv_image1.setBackgroundResource(R.drawable.no_pic);
		iv_image2.setBackgroundResource(R.drawable.no_pic);
		iv_image3.setBackgroundResource(R.drawable.no_pic);
		iv_image4.setBackgroundResource(R.drawable.no_pic);
		iv_image5.setBackgroundResource(R.drawable.no_pic);
		iv_image6.setBackgroundResource(R.drawable.no_pic);
		int imageSize=imageLists.size();
		if(imageSize>0){
			bitmapUtils=new BitmapUtils(getApplicationContext());
			switch (imageSize) {
			case 1:
				bitmapUtils.display(iv_image1, imageLists.get(0).getPath());
				System.out.println("image1路径：" + imageLists.get(0).getPath());
				iv_image1.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
//						Intent intentOne = new Intent(NoticeInformationActivity.this, ToImageZoomActivity.class);
//						intentOne.putExtra("imagePath", imageLists.get(0).getPath());
//						intentOne.putExtra("PATH_TYPE", Constant.LoadImage_Intenet);
//						startActivity(intentOne);
						toLocalImage(imageLists.get(0).getPath());
					}
				});
				
				break;
			case 2:
				bitmapUtils.display(iv_image1, imageLists.get(0).getPath());
				bitmapUtils.display(iv_image2, imageLists.get(1).getPath());
				System.out.println("image1路径：" + imageLists.get(0).getPath());
				System.out.println("image2路径：" + imageLists.get(1).getPath());
				iv_image1.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						toLocalImage(imageLists.get(0).getPath());
					}
				});
				iv_image2.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						toLocalImage(imageLists.get(1).getPath());
					}
				});
				break;
			case 3:
				bitmapUtils.display(iv_image1, imageLists.get(0).getPath());
				bitmapUtils.display(iv_image2, imageLists.get(1).getPath());
				bitmapUtils.display(iv_image3, imageLists.get(2).getPath());
				System.out.println("image1路径：" + imageLists.get(0).getPath());
				System.out.println("image2路径：" + imageLists.get(1).getPath());
				System.out.println("image3路径：" + imageLists.get(2).getPath());
				
				iv_image1.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						toLocalImage(imageLists.get(0).getPath());
					}
				});
				iv_image2.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						toLocalImage(imageLists.get(1).getPath());
					}
				});
				iv_image3.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						toLocalImage(imageLists.get(3).getPath());
					}
				});
				break;
			case 4:
				bitmapUtils.display(iv_image1, imageLists.get(0).getPath());
				bitmapUtils.display(iv_image2, imageLists.get(1).getPath());
				bitmapUtils.display(iv_image3, imageLists.get(2).getPath());
				bitmapUtils.display(iv_image4, imageLists.get(3).getPath());
				System.out.println("image1路径：" + imageLists.get(0).getPath());
				System.out.println("image2路径：" + imageLists.get(1).getPath());
				System.out.println("image3路径：" + imageLists.get(2).getPath());
				System.out.println("image4路径：" + imageLists.get(3).getPath());
				iv_image1.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						toLocalImage(imageLists.get(0).getPath());
					}
				});
				iv_image2.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						toLocalImage(imageLists.get(1).getPath());
					}
				});
				iv_image3.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						toLocalImage(imageLists.get(2).getPath());
					}
				});
				iv_image4.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						toLocalImage(imageLists.get(3).getPath());
					}
				});
				break;
			case 5:
				bitmapUtils.display(iv_image1, imageLists.get(0).getPath());
				bitmapUtils.display(iv_image2, imageLists.get(1).getPath());
				bitmapUtils.display(iv_image3, imageLists.get(2).getPath());
				bitmapUtils.display(iv_image4, imageLists.get(3).getPath());
				bitmapUtils.display(iv_image5, imageLists.get(4).getPath());
				
				System.out.println("image1路径：" + imageLists.get(0).getPath());
				System.out.println("image2路径：" + imageLists.get(1).getPath());
				System.out.println("image3路径：" + imageLists.get(2).getPath());
				System.out.println("image4路径：" + imageLists.get(3).getPath());
				System.out.println("image5路径：" + imageLists.get(4).getPath());
				
				iv_image1.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						toLocalImage(imageLists.get(0).getPath());
					}
				});
				iv_image2.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						toLocalImage(imageLists.get(1).getPath());
					}
				});
				iv_image3.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						toLocalImage(imageLists.get(2).getPath());
					}
				});
				iv_image4.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						toLocalImage(imageLists.get(3).getPath());
					}
				});
				iv_image5.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						toLocalImage(imageLists.get(4).getPath());
					}
				});
				break;
			case 6:
				bitmapUtils.display(iv_image1, imageLists.get(0).getPath());
				bitmapUtils.display(iv_image2, imageLists.get(1).getPath());
				bitmapUtils.display(iv_image3, imageLists.get(2).getPath());
				bitmapUtils.display(iv_image4, imageLists.get(3).getPath());
				bitmapUtils.display(iv_image5, imageLists.get(4).getPath());
				bitmapUtils.display(iv_image6, imageLists.get(5).getPath());

				System.out.println("image1路径：" + imageLists.get(0).getPath());
				System.out.println("image2路径：" + imageLists.get(1).getPath());
				System.out.println("image3路径：" + imageLists.get(2).getPath());
				System.out.println("image4路径：" + imageLists.get(3).getPath());
				System.out.println("image5路径：" + imageLists.get(4).getPath());
				System.out.println("image6路径：" + imageLists.get(5).getPath());
				
				iv_image1.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						toLocalImage(imageLists.get(0).getPath());
					}
				});
				iv_image2.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						toLocalImage(imageLists.get(1).getPath());
					}
				});
				iv_image3.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						toLocalImage(imageLists.get(2).getPath());
					}
				});
				iv_image4.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						toLocalImage(imageLists.get(3).getPath());
					}
				});
				iv_image5.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						toLocalImage(imageLists.get(4).getPath());
					}
				});
				iv_image6.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						toLocalImage(imageLists.get(5).getPath());
					}
				});
				break;
			}
		}
	}
	

	/****
	 * 本地图片放大
	 * @param localPath
	 */
	private void toLocalImage(String localPath){
		Intent intentOne = new Intent(NoticeInformationActivity.this,ToImageZoomActivity.class);
		intentOne.putExtra("imagePath", localPath);
		intentOne.putExtra("PATH_TYPE", Constant.LoadImage_Intenet);
		startActivity(intentOne);
	}
	
	/***
	 * 整车公告详细------（findView AddValue）
	 */
	private void showCarInformation() {
		if (notice!=null) {
			((TextView) findViewById(R.id.tv_text1)).setText(notice.getBh());
			((TextView) findViewById(R.id.tv_text2)).setText(notice.getHdzzl());
			((TextView) findViewById(R.id.tv_text3)).setText(notice.getClpp1());
			((TextView) findViewById(R.id.tv_text4)).setText(notice.getZqyzl());
			((TextView) findViewById(R.id.tv_text5)).setText(notice.getClpp2());
			((TextView) findViewById(R.id.tv_text6)).setText(notice.getHdzk());
			((TextView) findViewById(R.id.tv_text7)).setText(notice.getClxh());
			((TextView) findViewById(R.id.tv_text8)).setText(notice.getQpzk());
			((TextView) findViewById(R.id.tv_text9)).setText(notice.getFdjxh());
			((TextView) findViewById(R.id.tv_text10)).setText(notice.getHpzk());
			((TextView) findViewById(R.id.tv_text11)).setText(notice.getSbdhxl());
			((TextView) findViewById(R.id.tv_text12)).setText(notice.getDpid());
			((TextView) findViewById(R.id.tv_text13)).setText(notice.getCllx());
			((TextView) findViewById(R.id.tv_text14)).setText(notice.getHbdbqk());
			((TextView) findViewById(R.id.tv_text15)).setText(notice.getZzg());
			((TextView) findViewById(R.id.tv_text16)).setText(notice.getCslx());
			((TextView) findViewById(R.id.tv_text17)).setText(notice.getZxxs());
			((TextView) findViewById(R.id.tv_text18)).setText(notice.getBz());
			((TextView) findViewById(R.id.tv_text19)).setText(notice.getRlzl());
			((TextView) findViewById(R.id.tv_text20)).setText(notice.getZzcmc());
			((TextView) findViewById(R.id.tv_text21)).setText(notice.getPl());
			((TextView) findViewById(R.id.tv_text22)).setText(notice.getGgrq());
			((TextView) findViewById(R.id.tv_text23)).setText(notice.getGl());
			((TextView) findViewById(R.id.tv_text24)).setText(notice.getSfmj());
			((TextView) findViewById(R.id.tv_text25)).setText(notice.getCwkc());
			((TextView) findViewById(R.id.tv_text26)).setText(notice.getCxsxrq());
			((TextView) findViewById(R.id.tv_text27)).setText(notice.getCwkk());
			((TextView) findViewById(R.id.tv_text28)).setText(notice.getDpqyxh());
			((TextView) findViewById(R.id.tv_text29)).setText(notice.getCwkg());
			((TextView) findViewById(R.id.tv_text30)).setText(notice.getSfyxzc());
			((TextView) findViewById(R.id.tv_text31)).setText(notice.getHxnbcd());
			((TextView) findViewById(R.id.tv_text32)).setText(notice.getGgsxrq());
			((TextView) findViewById(R.id.tv_text33)).setText(notice.getHxnbkd());
			((TextView) findViewById(R.id.tv_text34)).setText(notice.getCxggrq());
			((TextView) findViewById(R.id.tv_text35)).setText(notice.getHxnbgd());
			((TextView) findViewById(R.id.tv_text36)).setText(notice.getTzscrq());
			((TextView) findViewById(R.id.tv_text37)).setText(notice.getGbthps());
			((TextView) findViewById(R.id.tv_text38)).setText(notice.getMjyxqz());
			((TextView) findViewById(R.id.tv_text39)).setText(notice.getZs());
			((TextView) findViewById(R.id.tv_text40)).setText(notice.getFgbsxh());
			((TextView) findViewById(R.id.tv_text41)).setText(notice.getZj());
			((TextView) findViewById(R.id.tv_text42)).setText(notice.getFgbssb());
			((TextView) findViewById(R.id.tv_text43)).setText(notice.getQlj());
			((TextView) findViewById(R.id.tv_text44)).setText(notice.getFgbsqy());
			((TextView) findViewById(R.id.tv_text45)).setText(notice.getHlj());
			((TextView) findViewById(R.id.tv_text46)).setText(notice.getFdjqy());
			((TextView) findViewById(R.id.tv_text47)).setText(notice.getLts());
			((TextView) findViewById(R.id.tv_text48)).setText(notice.getFdjsb());
			((TextView) findViewById(R.id.tv_text49)).setText(notice.getLtgg());
			((TextView) findViewById(R.id.tv_text50)).setText(notice.getQydm());
			((TextView) findViewById(R.id.tv_text51)).setText(notice.getZzl());
			((TextView) findViewById(R.id.tv_text52)).setText(notice.getGgbj());
			((TextView) findViewById(R.id.tv_text53)).setText(notice.getZbzl());
		//　　{‘bh:12313123123’ , ‘yhdh’:tydic’,’phone_id’:’00001’ }  ---请求图片
			if (Constant.user != null) {
				Map<String, String> conMap = new HashMap<String, String>();
				conMap.put("bh", notice.getBh());// 查询大类菜单
				conMap.put("yhdh", Constant.user.getUserName());
				conMap.put("phone_id", Constant.user.getPhoneId());
				System.out.println("MainActivity.gson.toJson(conMap)"+MainActivity.gson.toJson(conMap));
				new MyAsyncTaskREQNoticeImages().execute(MainActivity.gson
						.toJson(conMap));
			} else {
				Toast.makeText(getApplicationContext(), "你好，请先登陆。", Toast.LENGTH_SHORT).show();
			}
		}
	}

	/**
	 * 按下返回按钮退出
	 */
	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			if (event.getAction() == KeyEvent.ACTION_UP) {
				return false;
			}
			finish();
			overridePendingTransition(R.anim.push_right_in,
					R.anim.push_right_out);
			return true;
		}
		return super.dispatchKeyEvent(event);
	}
}
