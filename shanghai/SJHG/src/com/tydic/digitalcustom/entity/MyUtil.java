package com.tydic.digitalcustom.entity;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;


/**
 * 
 * Description:工具类
 * MyUtil.java Create on 2013-7-8 下午4:26:45 
 * @author wangwx
 * @version 1.0
 * Copyright (c) 2013 Company,Inc. All Rights Reserved.
 */
public class MyUtil {

	/**
	 * 
	 * 根据传入的参数格式，获取当前系统时间
	 * 
	 * @param format
	 *            格式字符串，如"yyyy-MM-dd HH:mm:ss"
	 * @return String 字符串格式的时间
	 */
	public static String getCurrentDateFormat(String format) {
		String current = null;
		java.util.Date d = new java.util.Date();
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		current = sdf.format(d);
		return current;
	}

	/**
	 * 判断list指定数值是否已经存在
	 * 
	 * @param targetList
	 * @param key
	 * @param value
	 * @return true 存在
	 */
	public static boolean isExistInList(List<Map<String, String>> targetList,
			String key, String value) {

		for (int i = 0; i < targetList.size(); i++) {
			if (value.equals(targetList.get(i).get(key))) {
				return true;
			}
		}

		return false;
	}

	
	
	public static void main(String[] args) {
		System.out.println(getCurrentDateFormat("yyyy-MM-dd"));
	}
	
}