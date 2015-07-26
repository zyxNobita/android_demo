package cn.tydic.mobile.pdarequery.nettools;
import cn.tydic.mobile.pdarequery.tools.LogUtil;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

/**
 * 网络开启判断
 * @author King
 *
 */
public class NetworkManageUtil {
	private static final String TAG = "NetworkManageUtil";

	/**
	 * 判断wifi网络是否连接
	 * @param inContext
	 * @return
	 */
	public static boolean isWiFiActive(Context inContext) {
		WifiManager mWifiManager = (WifiManager) inContext
				.getSystemService(Context.WIFI_SERVICE);
		WifiInfo wifiInfo = mWifiManager.getConnectionInfo();
		int ipAddress = wifiInfo == null ? 0 : wifiInfo.getIpAddress();
		return mWifiManager.isWifiEnabled() && ipAddress != 0;
	}

	/**
	 * 判断3G网络是否连接
	 * @param context
	 * @return
	 */
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);			
		if (connectivity == null) {
			LogUtil.i(NetworkManageUtil.class, "**** newwork is off");
			return false;
		} else {					
			NetworkInfo info = connectivity.getActiveNetworkInfo();								
			if (info == null) {
				LogUtil.i(NetworkManageUtil.class, "**** newwork is off");
				return false;
			} else {
				NetworkInfo mobNetInfo = connectivity.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
				if (mobNetInfo != null) {
					return info.isAvailable() && mobNetInfo.isAvailable();								
				}				
			}
		}
		LogUtil.i(NetworkManageUtil.class, "**** newwork is off");
		return false;
	}
	
	
}
