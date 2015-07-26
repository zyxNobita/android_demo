/**
 * VPNOfCustom.java [v 1.0.0]
 * classes: com.tydic.digitalcustom.entity.VPNOfCustom
 * 兮兮 Create at 2013-7-18 下午3:28:55
 */
package com.tydic.digitalcustom.entity;

import java.net.InetAddress;
import java.net.UnknownHostException;

import android.app.Activity;
import android.util.Log;

import com.sangfor.vpn.IVpnDelegate;
import com.sangfor.vpn.SFException;
import com.sangfor.vpn.auth.SangforAuth;
import com.sangfor.vpn.common.VpnCommon;

/**
 * 建立海关VPN专线连接 com.tydic.digitalcustom.entity.VPNOfCustom
 * 
 * @author 兮兮（bai） <br>
 *         creat at 2013-7-18 下午3:28:55
 * 
 */
public class VPNOfCustom implements IVpnDelegate{

	private static VPNOfCustom vpn;
	private static Activity context;
	private final static String TAG = "vpn_of_custom";

	/*
	 * 单例
	 */
	public static VPNOfCustom getInstance( Activity context) {
		VPNOfCustom.context = context;
		if (vpn == null) {
			vpn = new VPNOfCustom();
		}
		return vpn;
	}

	/**
	 * 第一步
	 * 初始化vpn资源
	 */
	public boolean init() {
		try {
			SangforAuth.getInstance().init(context, this);
		} catch (SFException e) {
			e.printStackTrace();
			return false;
		}

		// 开始初始化VPN
		if (initSslVpn() == false) {
			Log.e(TAG, "init ssl vpn fail.");
			return false;
		} else {
			Log.d(TAG, "init ssl vpn succes.");

		}

		return true;
	}
	

	/**
	 * 第二步
	 * 开始认证，认证包括初始化vpn地址
	 * 涉及到网络连接会阻塞，请在线程中做此操作。
	 * 
	 * @return
	 */
	public boolean doVpnLogin() {
		SangforAuth sfAuth = SangforAuth.getInstance();

		// 设置用户名密码参数
		sfAuth.setLoginParam(IVpnDelegate.PASSWORD_AUTH_USERNAME, "xingyuntest");
		sfAuth.setLoginParam(IVpnDelegate.PASSWORD_AUTH_PASSWORD, "AAAaaa111");
		// 设置证书认证参数
		sfAuth.setLoginParam(IVpnDelegate.CERT_PASSWORD, "123456");
		sfAuth.setLoginParam(IVpnDelegate.CERT_P12_FILE_NAME,
				"/sdcard/csh/csh.p12");
		// 调用认证，认证类型为用户名密码，如果需要下一个认证未证书认证，则认证内部会直接获取之上设置的证书认证参数进行认证
		if (sfAuth.vpnLogin(IVpnDelegate.AUTH_TYPE_PASSWORD)) {
			Log.d(TAG, "do login vpn success.");
			return true;
		} else {
			Log.d(TAG, "do login vpn fail, error is " + sfAuth.vpnGeterr());
			return false;
		}
	}

	/**
	 * 初始化ssl vpn
	 * 
	 * @return 成功返回true，失败返回false
	 */
	private boolean initSslVpn() {
		SangforAuth sfAuth = SangforAuth.getInstance();

		// 添加加密文件路径规则，如果要开启文件加密则需要先填写需要加密文件规则，之后再初始化vpn，此处暂无用到文件加密
		// sfAuth.setCryptFileRule("/sdcard*");

		InetAddress iAddr = null;
		try {
//			 iAddr = InetAddress.getByName("200.200.75.161");
			// 数字沪关海关VPN地址
			iAddr = InetAddress.getByName("58.247.1.254");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		if (iAddr == null || iAddr.getHostAddress() == null) {
			Log.d(TAG, "vpn host error");
			return false;
		}
		long host = VpnCommon.ipToLong(iAddr.getHostAddress());
		int port = 443;

		if (sfAuth.vpnInit(host, port) == false) {
			Log.d(TAG, "vpn init fail, errno is " + sfAuth.vpnGeterr());
			return false;
		}

		return true;
	}

	
	/**
	 * 初始化vpn、认证或者其它情况均会通过该接口回调，
	 * 
	 * @param vpnResult
	 *            vpn结果，返回的值定义在IVpnDelegate中，例如一下几种
	 * @param authType
	 *            当前认证类型，如果是初始化vpn则是默认认证类型，如果需要下一个认证，则是下一个认证类型，如果认证成功则是无效认证类型，
	 *            其它为无效值
	 */
	@Override
	public void vpnCallback(int vpnResult, int authType) {
		SangforAuth sfAuth = SangforAuth.getInstance();

		switch (vpnResult) {
		case IVpnDelegate.RESULT_VPN_INIT_FAIL:
			/**
			 * 初始化vpn失败
			 */
			Log.i(TAG, "RESULT_VPN_INIT_FAIL, error is " + sfAuth.vpnGeterr());
			break;

		case IVpnDelegate.RESULT_VPN_INIT_SUCCESS:
			/**
			 * 初始化vpn成功，接下来就需要开始认证工作了
			 */
			Log.i(TAG, "RESULT_VPN_INIT_SUCCESS, current vpn status is "
					+ sfAuth.vpnQueryStatus());
			break;

		case IVpnDelegate.RESULT_VPN_AUTH_FAIL:
			/**
			 * 认证失败，有可能是传入参数有误，具体信息可通过sfAuth.vpnGeterr()获取
			 */
			Log.i(TAG, "RESULT_VPN_AUTH_FAIL, error is " + sfAuth.vpnGeterr());
			break;

		case IVpnDelegate.RESULT_VPN_AUTH_SUCCESS:
			/**
			 * 认证成功，认证成功有两种情况，一种是认证通过，可以使用sslvpn功能了，另一种是前一个认证（如：用户名密码认证）通过，
			 * 但需要继续认证（如：需要继续证书认证）
			 */
			if (authType == IVpnDelegate.AUTH_TYPE_NONE) {
				Log.i(TAG, "welcom to sangfor sslvpn!");
			} else {
				Log.i(TAG,
						"auth success, and need next auth, next auth type is "
								+ authType);
			}
			break;
		case IVpnDelegate.RESULT_VPN_AUTH_LOGOUT:
			/**
			 * 主动注销（自己主动调用logout接口）或者被动注销（通过控制台把用户踢掉）均会调用该接口
			 */
			Log.i(TAG, "RESULT_VPN_AUTH_LOGOUT");
			break;
		default:
			/**
			 * 其它情况，不会发生，如果到该分支说明代码逻辑有误
			 */
			Log.i(TAG, "default result, vpn result is " + vpnResult);
			break;
		}
	}

	@Override
	public void vpnRndCodeCallback(byte[] arg0) {
		
		
	}


}
