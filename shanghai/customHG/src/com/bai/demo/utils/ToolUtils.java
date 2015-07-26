package com.bai.demo.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.widget.Toast;

import com.bai.demo.R;
import com.bai.demo.entity.LoginActivity;
import com.bai.demo.main.FrameDemoActivity;

@SuppressLint("ParserError")
public class ToolUtils {

	private Context context;
	private ProgressDialog progressDialog;// 所有的进度条
	private ProgressDialog downloadProgressDialog;// 下载图片进度条

	public ToolUtils(Context context) {
		this.context = context;
	}

	/**
	 * 从SDCard读取字节流
	 * 
	 * @param filename
	 * @return
	 */
	public byte[] requestSDCardIamge(String filename) {
		byte[] res = null;
		if (!Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return null;
		}
		FileInputStream input = null;
		try {
			input = new FileInputStream(filename);
			byte[] buffer = new byte[1024 * 1024];
			int len = 0;
			while ((len = input.read(buffer)) != 0) {
				res = buffer;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return res;
	}

	/**
	 * 根据路径获得突破并压缩返回bitmap用于显示
	 * 
	 * @param imagesrc
	 * @return
	 */
	public static Bitmap getSmallBitmap(String filePath) {
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, 480, 800);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;

		return BitmapFactory.decodeFile(filePath, options);
	}

	/***
	 * 讲bitmap格式转换为 byte格式
	 * 
	 * @param bm
	 * @return
	 */
	public static byte[] bitmapTobyte(Bitmap bm) {
		byte[] buffer = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		if (bm != null) {
			bm.compress(Bitmap.CompressFormat.JPEG, 60, baos);
			buffer = baos.toByteArray();
		}

		return buffer;
	}

	// 确定不再需要该bitmap对象的时候可以将其回收掉
	public static void recycle(Bitmap bitmap) {
		if (bitmap != null && !bitmap.isRecycled()) {
			bitmap.recycle();
			System.gc();// 提醒系统及时回收
		}
	}

	/**
	 * 计算图片的缩放值
	 * 
	 * @param options
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */

	public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			// Calculate ratios of height and width to requested height and
			// width
			final int heightRatio = Math.round((float) height
					/ (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);

			// Choose the smallest ratio as inSampleSize value, this will
			// guarantee
			// a final image with both dimensions larger than or equal to the
			// requested height and width.
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}

		return inSampleSize;
	}

	/**
	 * Android UI 图片处理：质量压缩方法
	 * 
	 * @param image
	 * @return
	 */
	public Bitmap compressImage(Bitmap image) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
																// 0---100
		int options = 100;
		while (baos.toByteArray().length / 1024 > 100) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
			baos.reset();// 重置baos即清空baos
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
			options -= 10;// 每次都减少10
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
		return bitmap;
	}

	// 读取sd卡中的图片
	public static Bitmap readBitmap(String picpath) {
		Bitmap bitmap = BitmapFactory.decodeFile(picpath); // "/sdcard/icon.png"
		return bitmap;
	}

	/**
	 * 写入SDCard里面
	 * 
	 * @param savePath
	 * @param fileName
	 * @param saveBitmap
	 * @return
	 */
	public boolean writeToSDcard(String savePath, String fileName,
			Bitmap saveBitmap) {
		FileOutputStream fout = null;
		File file = new File(savePath);
		if (!file.exists()) {
			file.mkdirs();
		}
		String filename = file.getPath() + file.separator + fileName;
		try {
			fout = new FileOutputStream(filename);
			saveBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fout);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				fout.flush();
				fout.close();
				if (saveBitmap.isRecycled() == false) {
					saveBitmap.recycle();
					saveBitmap = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	/**
	 * 测试 网络
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isHavingNetWork(Context context) {
		boolean flag = false;
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		if (ni != null) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 链接网络失败 处理
	 */
	public void connectionFail() {
		if (isHavingNetWork(context)) {
			// msg.what = Constant.CONNECT_FAIL;//服务器链接失败
			promptMessage("服务器链接失败");
		} else {
			// 开启网络的
			// /手动设置网络
			context.startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
		}
	}

	/**
	 * 
	 * 根据传入的参数格式，获取当前系统时间
	 * 
	 * @param format
	 *            格式字符串，如"yyyy-MM-dd HH:mm:ss"
	 * @return String 字符串格式的时间
	 */
	public String requestData(String format) {
		// String current = null;
		// java.util.Date d = new java.util.Date();
		// SimpleDateFormat sdf = new SimpleDateFormat(format);
		// current = sdf.format(d);
		Calendar c = Calendar.getInstance();
		String y = c.get(Calendar.YEAR) + "年";
		String m = (c.get(Calendar.MONTH) + 1) + "月";
		String d = c.get(Calendar.DAY_OF_MONTH) + "日";
		return y + m + d;
	}

	/**
	 * 
	 * @param str
	 *            提示语
	 */
	public void promptMessage(String str) {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
		alertDialog.setTitle(context.getString(R.string.titleMessage));
		alertDialog.setIcon(R.drawable.ic_launcher);
		alertDialog.setMessage(str);
		alertDialog.setPositiveButton(context.getString(R.string.makeSure),
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub

					}

				});

		alertDialog.setNegativeButton(context.getString(R.string.cancel),
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {

					}
				});
		alertDialog.create().show();
	}

	/**
	 * 开启网络
	 * 
	 * @param str
	 */
	public void StartWifiMessage(String str) {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
		alertDialog.setTitle(context.getString(R.string.titleMessage));
		alertDialog.setIcon(R.drawable.ic_launcher);
		alertDialog.setMessage(str);
		alertDialog.setPositiveButton(context.getString(R.string.makeSure),
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {

						context.startActivity(new Intent(
								Settings.ACTION_WIRELESS_SETTINGS));
					}

				});

		alertDialog.setNegativeButton(context.getString(R.string.cancel),
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {

					}
				});
		alertDialog.create().show();
	}

	/**
	 * 退出App系统
	 * 
	 * @param str
	 */
	public void exiteAppMessage(String str) {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
		alertDialog.setTitle(context.getString(R.string.titleMessage));
		alertDialog.setIcon(R.drawable.ic_launcher);
		alertDialog.setMessage(str);
		alertDialog.setPositiveButton(context.getString(R.string.makeSure),
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						// ActivityManager manager = (ActivityManager)
						// getSystemService(Context.ACTIVITY_SERVICE);
						// System.out.println(getPackageName()
						// + "------------");
						// manager.restartPackage(getPackageName());
						android.os.Process.killProcess(android.os.Process
								.myPid());
					}

				});

		alertDialog.setNegativeButton(context.getString(R.string.cancel),
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						ActivityManager manager = (ActivityManager) context
								.getSystemService(Context.ACTIVITY_SERVICE);
						manager.restartPackage(context.getPackageName());
					}
				});
		alertDialog.create().show();
	}

	/**
	 * 用户退出
	 * 
	 * @param str
	 *            提示语
	 */
	public boolean exitUser(final Context c, String str) {
		boolean flag = false;

		AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
		alertDialog.setTitle(context.getString(R.string.titleMessage));
		alertDialog.setIcon(android.R.drawable.stat_sys_warning);
		alertDialog.setMessage(str);
		alertDialog.setPositiveButton(context.getString(R.string.makeSure),
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {

						c.startActivity(new Intent(c, LoginActivity.class));
						((Activity) c).finish();
						Thread thread = new Thread(new Runnable() {
							@Override
							public void run() {
								// TODO Auto-generated method stub
								// 注销时的Log日志
								writeDataLog("注销系统成功。", "82",
										FrameDemoActivity.myApp.userInfo
												.getLobNumber());
							}
						});
						thread.setDaemon(true);
						thread.start();
					}

				});

		alertDialog.setNegativeButton(context.getString(R.string.cancel),
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {

					}
				});
		alertDialog.create().show();
		return flag;
	}

	/**
	 * 目标：屏蔽 所有的Exception
	 * 
	 * @param context
	 * @param e
	 */
	public static void showError(Context context, Exception e) {
		context.getClass().getName();
		Toast.makeText(context, "系统异常，请联系管理员,错误：" + e.getMessage(),
				Toast.LENGTH_LONG).show();
	}

	/**
	 * 递归删除SDCard的文件夹
	 * 
	 * @param file
	 */
	public void RecursionDeleteFile(File file) {
		if (file.isFile()) {
			file.delete();
			return;
		}
		if (file.isDirectory()) {
			File[] childFile = file.listFiles();
			if (childFile == null || childFile.length == 0) {
				file.delete();
				return;
			}
			for (File f : childFile) {
				RecursionDeleteFile(f);
			}
			file.delete();
		}
	}

	/***
	 * 从服务器下载图片到 SDCard 暂存
	 */
	public void downloadImage(byte[] b, String imageName, String savePath) {
		File file = new File(savePath);
		file.mkdirs();
		File img_path = new File(file, imageName + ".png");
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(img_path);

			fos.write(b, 0, b.length);
			fos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	/***
	 * 所有进度条的开启
	 */
	public void startProgressDialog() {
		if (progressDialog == null) {
			progressDialog = ProgressDialog.show(this.context, "温馨提示：",
					"正在请求后台数据，请耐心等待....");
		}
	}

	public void startUpdateProgressDialog() {
		if (progressDialog == null) {
			progressDialog = ProgressDialog.show(this.context, "温馨提示：",
					"正在更新，请耐心等待....");
		}
	}

	public void startInsertProgressDialog() {
		if (progressDialog == null) {
			progressDialog = ProgressDialog.show(this.context, "温馨提示：",
					"正在插入，请耐心等待....");
		}
	}

	public void startDownloadProgressDialog() {
		if (downloadProgressDialog == null) {
			downloadProgressDialog = ProgressDialog.show(this.context, "温馨提示：",
					"正在加载图片，请耐心等待....");
		}
	}

	/***
	 * 所有进度条的关闭
	 */
	public void endProgressDialog() {
		if (progressDialog != null) {
			progressDialog.dismiss();
			progressDialog.cancel();
			progressDialog = null;
		}
	}

	public void endDownloadProgressDialog() {
		if (downloadProgressDialog != null) {
			downloadProgressDialog.dismiss();
			downloadProgressDialog.cancel();
			downloadProgressDialog = null;
		}
	}

	// 请求相册
	public void requestLoachostImage(Integer requestCoder) {
		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		((Activity) context).startActivityForResult(intent, requestCoder);
	}

	/**
	 * 
	 * @param data
	 * @return 返回本地图片的Path
	 */
	public String reqLoachostIamgePath(Intent data) {
		String picturePath;
		Uri selectedImage = data.getData();
		String[] filePathColumn = { MediaStore.Images.Media.DATA };

		Cursor cursor = context.getContentResolver().query(selectedImage,
				filePathColumn, null, null, null);
		cursor.moveToFirst();

		int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
		picturePath = cursor.getString(columnIndex);
		cursor.close();

		return picturePath;
	}

	// 本地上传时图片的保存
	public void loachostImageSave(String inPath, String savePath,
			String imageName) {

		FileInputStream fis = null;
		FileOutputStream fos = null;
		try {
			fis = new FileInputStream(new File(inPath));
			File file = new File(savePath);
			if (!file.exists()) {
				file.mkdirs();
			}
			String save = file.getAbsolutePath() + file.separator + imageName;
			fos = new FileOutputStream(save);
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = fis.read(buffer)) != -1) {
				fos.write(buffer, 0, len);
			}
			fos.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 保存数据到本地数据库
	 * 
	 * @param Entry_ID
	 * @param G_No
	 * @param Photo_list_Code
	 * @param Photo_list_ID
	 */
	public void saveEntryToSQLite(String Entry_ID, String G_No,
			String Photo_list_Code, String Photo_list_ID) {
		// 插入所有表体信息：
		// INSERT INTO Entry_List(Entry_ID,G_No) values('报关单号',项号)
		// INSERT INTO Photo_list(Entry_ID,G_No,Photo_list_Code,Photo_list_ID)
		// db.insertToEntry_LIST(Entry_ID, G_No, CODE_TS, G_NAME, isYs, Remark)
		// db.insertToEntry_Head(Entry_ID, State, Save_Time, Upload_Time)
		long row = FrameDemoActivity.db.insertToPhoto_list(Entry_ID, G_No,
				Photo_list_Code, Photo_list_ID);

		if (!G_No.equals("0")) {
			// 表体
			// db.insertToEntry_LIST(Entry_ID, G_No, CODE_TS, G_NAME, isYs,
			// Remark)
		}
		System.out.println("saveEntryToSQLite----" + row);
	}

	/**
	 * 向服务器书写日志
	 * 
	 * @param userId
	 *            关员工号
	 * @param operation
	 *            操作的功能
	 * @param menuId
	 *            操作菜单
	 * @param modelId
	 *            操作对象ID
	 */
	public void writeDataLog(String operation, String menuId, String modelId) {
		String userId = FrameDemoActivity.myApp.userInfo.getLobNumber();
		if (userId != null && !userId.equals("")) {
			FrameDemoActivity.webservice.setMETHOD_NAME("InsertLog");
			FrameDemoActivity.webservice
					.setSOAP_ACTION("http://tempuri.org/IGetExamDataService/InsertLog");
			if (FrameDemoActivity.webservice
					.connect(this.context, new String[] { "userId",
							"operation", "menuId", "modelId" }, new Object[] {
							userId, operation, menuId, modelId })) {
			}
		}
	}

	/***
	 * 根据ImagePath获得Bitmap
	 * 
	 * @param fileName
	 * @return
	 */
	public Bitmap getLocalBitMap(String fileName) {
		File picture = new File(fileName);
		try {
			// 获取SDCard上的图片，转换为BitMap
			FileInputStream is = new FileInputStream(picture);
			return BitmapFactory.decodeStream(is);
			// Bitmap bm=BitmapFactory.decodeStream(is);
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
}
