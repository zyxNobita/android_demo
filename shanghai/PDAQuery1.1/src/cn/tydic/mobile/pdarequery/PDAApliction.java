package cn.tydic.mobile.pdarequery;

/**
 * 本程序中完整的单例
 * 
 * @author jiangyue
 *
 */
public class PDAApliction {

	public String requestJsonStr;
	public String receiveJsonStr;
	
	private static PDAApliction instance;

	// 单例模式中获取唯一的ApplicationUtil实例
	public static PDAApliction getInstance() {
		if (null == instance) {
			instance = new PDAApliction();
		}
		return instance;

	}
}
