package cn.tydic.mobile.pdarequery.tools;


import cn.tydic.mobile.pdarequery.PDAApliction;
import android.util.Log;

/**
 * 日志工具
 * 
 * @author : king
 * @since：Jdk1.6
 * @version : 1.0.0
 * @date : 2011-12-8
 * 
 */
public class LogUtil {

	private static final String TAG = "pdarequery_";

	/**
	 * Log.i打印日志信息
	 * 
	 * @param c
	 *            类文件
	 * @param msg
	 *            打印的日志信息
	 */
	public static void i(Object o, String msg) {

		if(msg != null && msg.contains("REQUEST JSONSting")){
			PDAApliction.getInstance().requestJsonStr = msg;
		}
		
		if(msg != null && msg.contains("RECEIVE JSONSting")){
			PDAApliction.getInstance().receiveJsonStr = msg;			
		}
		
		if(Constant.log_flag){
			String cName = getClassName(o);
			Log.i(TAG, cName + "====>>" + msg);
		}
	}

	/**
	 * Log.d打印日志信息
	 * 
	 * @param c
	 *            类文件
	 * @param msg
	 *            打印的日志信息
	 */
	public static void d(Object c, String msg) {
		if(Constant.log_flag){
			String cName = getClassName(c);
			Log.d(TAG, cName + "====>>" + msg);
		}
	}

	/**
	 * 获取文件类名（去除之前的包名）
	 * 
	 * @param c
	 * @return 类名
	 */
	public static String getClassName(Object o) {

		Class<? extends Object> c = o.getClass();
		String cName = c.getName();
		String[] tmp = cName.split("\\.");
		cName = tmp[tmp.length - 1];

		return cName;
	}
}
