package com.tydic.digitalcustom.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 共享文件的处理工具类
 * @author wangwx
 *
 */
public class SharePreferenceUtils {
	private Context context;

	public SharePreferenceUtils(Context context) {
		super();
		this.context = context;
	}
	/**
	 * 获取本地共享的列表
	 * @param name  共享文件的名称
	 * @return List  共享文件的列表（String:String）
	 */
	public List<Map<String, Object>> getSharePrefercncesList(String name) {
		SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
		Map<String, ?> allSp = sp.getAll();
		Iterator<String> it = allSp.keySet().iterator();
		List<Map<String, Object>> allSpList = new ArrayList<Map<String, Object>>();	
		Map<String,Object> aMap = null;
		while(it.hasNext()){
			String key = it.next();
			Object val = allSp.get(key);
			aMap = new HashMap<String, Object>();
			aMap.put(key, val);
			allSpList.add(aMap);
		};
		return allSpList;
	};
	
	/**
	 * 获取本地共享的列表
	 * @param name  共享文件的名称
	 * @return Map  共享文件的列表（String:Object）
	 */
	public Map<String, Object> getSharePrefercncesMap(String name) {
		SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
		Map<String, ?> allSp = sp.getAll();
		Iterator<String> it = allSp.keySet().iterator();
		Map<String, Object> allSpMap = new HashMap<String, Object>();	
		while(it.hasNext()){
			String key = it.next();
			allSpMap.put(key, allSp.get(key));
		};
		return allSpMap;
	};
}
