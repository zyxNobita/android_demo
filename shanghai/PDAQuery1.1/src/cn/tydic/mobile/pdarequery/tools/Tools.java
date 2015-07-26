package cn.tydic.mobile.pdarequery.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

import cn.tydic.mobile.pdarequery.R;

import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.telephony.TelephonyManager;

public class Tools {

	private static Tools instance;
	private Context context;// 程序的上下文

	public Tools(Context context){
		this.context=context;
	}
//	public static synchronized Tools getInstance(Context context) {
//		if (instance == null) {
//			instance = new Tools(context);
//		}
//		return instance;
//	}

	/**
	 * 获取手机你唯一识别码 不会随手机刷机而改变
	 */
	public String getImieStatus(Context context) {
		TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		String IMEI = tm.getDeviceId();
		LogUtil.i(this, "DEVICE_ID =>>" + IMEI);
		return IMEI;
	}

	private ProgressDialog progressDialog;// 进度条

	/**
	 * 
	 */
	public void startProgressDialog(Context context) {
		if (progressDialog == null) {
			progressDialog = new ProgressDialog(context);
			progressDialog.setTitle("温馨提示：");
			progressDialog.setMessage("正在请求服务器的数据...");
		}
	}

	public void stopProgressDialog() {
		if (progressDialog != null) {
			progressDialog.dismiss();
			progressDialog.cancel();
			progressDialog = null;
		}
	}

	/**
	 * 保存用户名称
	 * 
	 * @param userName
	 */
	public void saveUserName(Context context, String userName) {
		SharedPreferences sp = context
				.getSharedPreferences("userInfo", Integer.valueOf(context
						.getString(R.string.MODE_WORLD_PRIVATE)));
		Editor et = sp.edit();
		et.putString("userName", userName);
		et.commit();
	}
	
	/**
	 * 保存服务器地址
	 * @param context
	 * @param serverAddress
	 */
	public void saveServerAddress(Context context, String serverAddress) {
		SharedPreferences sp = context
				.getSharedPreferences("userInfo", Integer.valueOf(context
						.getString(R.string.MODE_WORLD_PRIVATE)));
		Editor et = sp.edit();
		et.putString("serverAddress", serverAddress);
		et.commit();
	}


	
	/**
	 * 记录是否已经勾选
	 * 
	 * @param userName
	 */
	public void saveCkeckUserName(Context context, String check) {
		SharedPreferences sp = context
				.getSharedPreferences("userInfo", Integer.valueOf(context
						.getString(R.string.MODE_WORLD_PRIVATE)));
		Editor et = sp.edit();
		et.putString("isCheck", check);
		et.commit();
	}

	/***
	 * 读取用户名称
	 * 
	 * @return
	 */
	public String readUserName(Context context) {
		String userName = null;
		SharedPreferences sp = context
				.getSharedPreferences("userInfo", Integer.valueOf(context
						.getString(R.string.MODE_WORLD_PRIVATE)));
		userName = sp.getString("userName", null);
		return userName;
	}
	
	/***
	 * 读取用户名称
	 * 
	 * @return
	 */
	public String readIsCheck(Context context) {
		String isCheck = null;
		SharedPreferences sp = context
				.getSharedPreferences("userInfo", Integer.valueOf(context
						.getString(R.string.MODE_WORLD_PRIVATE)));
		isCheck = sp.getString("isCheck", null);
		return isCheck;
	}
	
	/**
	 * 读取服务器地址
	 * @param context
	 * @return
	 */
	public String readServerAddress(Context context) {
		String serverAddress = null;
		SharedPreferences sp = context
				.getSharedPreferences("userInfo", Integer.valueOf(context
						.getString(R.string.MODE_WORLD_PRIVATE)));
		serverAddress = sp.getString("serverAddress", null);
		return serverAddress;
	}

	/**
	 * 
	 * 根据传入的参数格式，获取当前系统时间
	 * 
	 * @param format
	 *            格式字符串，如"yyyy-MM-dd HH:mm:ss"
	 * @return String 字符串格式的时间
	 */
	public String requestData() {
		Calendar c=Calendar.getInstance();
		String y=c.get(Calendar.YEAR)+"年";
		String m=(c.get(Calendar.MONTH)+1)+"月";
		String d=c.get(Calendar.DAY_OF_MONTH)+"日";
		return y+m+d;
	}

	/**
	 * 
	 * 根据传入的参数格式，获取当前系统时间
	 * 
	 * @param format
	 *            格式字符串，如"yyyy-MM-dd HH:mm:ss"
	 * @return String 字符串格式的时间
	 */
	public String requestJobData() {
		Calendar c=Calendar.getInstance();
		String y=c.get(Calendar.YEAR)+"-";
		String m=(c.get(Calendar.MONTH)+1)+"-";
		String d=c.get(Calendar.DAY_OF_MONTH)+"";
		return y+m+d;
	}
	
	/****
	 * 比较Data1和Data2的前后
	 * @param DATE1
	 * @param DATE2
	 * @return
	 */
	public boolean requestJobDataBJ(String s1, String s2){
		boolean flag=false;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date dt1 = sdf.parse(s1);
			Date dt2 = sdf.parse(s2);
			System.out.println("比较：data1---->" + dt1.getTime() + "===data2--->"
					+ dt2.getTime());
			if (dt1.getTime() > dt2.getTime()) {
				System.out.println("dt1 在dt2后面");
				flag=false;
			} else if (dt1.getTime() <= dt2.getTime()) {
				System.out.println("dt1在dt2前面");
				flag=true;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return flag;
	}
	/**
	 * MD5单向加密
	 * 
	 * @param str
	 * @return String 加密后的字符串
	 */
	public static String MD5(String str) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		char[] charArray = str.toCharArray();
		byte[] byteArray = new byte[charArray.length];
		for (int i = 0; i < charArray.length; i++) {
			byteArray[i] = (byte) charArray[i];
		}
		byte[] md5Bytes = md5.digest(byteArray);
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16) {
				hexValue.append("0");
			}
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();
	}
	/***
	 * 根据ImagePath获得Bitmap
	 * @param fileName
	 * @return
	 */
	public Bitmap getLocalBitMap(String fileName) {
		File picture = new File(fileName);
		try {
			//获取SDCard上的图片，转换为BitMap
			FileInputStream is = new FileInputStream(picture);
			return BitmapFactory.decodeStream(is);
			//Bitmap  bm=BitmapFactory.decodeStream(is);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// 读取网络图片
		public Bitmap getBitMapFromUrl(String url) {
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
	/**
	 * 写入SDCard里面
	 * @param savePath
	 * @param fileName
	 * @param saveBitmap
	 * @return
	 */
	public String writeToSDcard(String savePath,String fileName,Bitmap saveBitmap){
		FileOutputStream fout = null;
		File file = new File(savePath);
		if(!file.exists()){
			file.mkdirs();
		}
		String filename = file.getPath()+file.separator + fileName;
		try {
			fout = new FileOutputStream(filename);
			saveBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fout);
//			context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse(Environment.getExternalStorageDirectory()+File.separator +"DCIM"+File.separator+filename)));
			return filename;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				fout.flush();
				fout.close();
//				if(saveBitmap.isRecycled() == false){
//					saveBitmap.recycle();
//					saveBitmap = null;
//				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return filename;
	}
	
	/***
	 * 获取图片的名称
	 * @return
	 */
	  public String getPhotoFileName() {  
		    Date date = new Date(System.currentTimeMillis());  
		    SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyyMMdd_HHmmss");  
		    return dateFormat.format(date) + ".jpg";  
		}
	  
	  /**
		 * 递归删除SDCard的文件夹
		 * @param file
		 */
		public void RecursionDeleteFile(File file){
				if(file.isFile()){
					file.delete();
					return;
				}
				if(file.isDirectory()){
					File[] childFile=file.listFiles();
					if(childFile==null || childFile.length==0){
						file.delete();
						return;
					}
					for(File f: childFile){
						RecursionDeleteFile(f);
					}
					file.delete();
			}
		}
		
		/**
		 * 硬件缓存机制
		 */
		
		/***
		 * 判断手机是否挂载SDCard
		 * 
		 * @return true | false 挂载|不挂载 （内置的SDCard也算算SDCard的挂载）
		 */
		public boolean isSDCardExist() {
			boolean flag = false;
			if (Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED)) {
				flag = true;
			} else {
				flag = false;
			}
			System.out.println("Path---->"
					+ Environment.getExternalStorageDirectory().getAbsolutePath());
			return flag;
		}

		/***
		 * 获取当前应用的包名
		 * 
		 * @return
		 */
		public String getCurRunningActivityPackageName() {
			ActivityManager mgr = (ActivityManager) this.context
					.getSystemService(Context.ACTIVITY_SERVICE);
			RunningTaskInfo info = mgr.getRunningTasks(1).get(0);
			// return info.topActivity.getClassName();
			return info.topActivity.getPackageName();
		}

		/***
		 * 获取当前项目工程的名称------{用途：作为硬件缓存机制的路径}
		 * 
		 * @return
		 */
		public String getApplicationName() {
			return "PDA_IMAGE";
//			PackageManager packageManager = null;
//			ApplicationInfo applicationInfo = null;
//			try {
//				packageManager = this.context.getPackageManager();
//				applicationInfo = packageManager.getApplicationInfo(
//						this.context.getPackageName(), 0);
//			} catch (PackageManager.NameNotFoundException e) {
//				applicationInfo = null;
//			}
//			String applicationName = (String) packageManager
//					.getApplicationLabel(applicationInfo);
//			return applicationName;
		}
		
		/***
		 * 验证是否为数字、字母
		 * @param noStr
		 * @return
		 */
//		public boolean isWMatches(String noStr){
//			boolean flag=false;
//			Pattern pattern = Pattern.compile("^[A-Za-z0-9]*$");  
//			flag= pattern.matcher(noStr).matches();  
//			return flag;
//		}
		
		
		/***
		 * 验证是否为数字、字母
		 * @param noStr
		 * @return
		 */
		public boolean isWMatches2(String noStr){
			boolean flag=false;
			Pattern pattern = Pattern.compile("^[0-9]*$");  
			flag= pattern.matcher(noStr).matches();  
			return flag;
		}
}
