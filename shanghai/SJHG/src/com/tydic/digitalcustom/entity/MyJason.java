package com.tydic.digitalcustom.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * 
 * Description:json转化工具
 * MyJason.java Create on 2013-7-8 下午4:26:14 
 * @author wangwx
 * @version 1.0
 * Copyright (c) 2013 Company,Inc. All Rights Reserved.
 */
public class MyJason {

	private String resource;

	public MyJason(String resource) {
		this.resource = resource;

	}

	/**
	 * jason格式解析，传入参数为map的key
	 * @param mapKey
	 * @return
	 */
	public List<Map<String, Object>> jasonToList(String[] mapKey) {
		Map<String, Object> map;
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		try {
			JSONArray dataArray = new JSONArray(resource);
			if(dataArray!=null&&dataArray.length()>0){
				for (int i = 0; i < dataArray.length(); i++) {
					JSONObject jsonObject = dataArray.getJSONObject(i);
					map = new HashMap<String, Object>();
					for (int j = 0; j < mapKey.length; j++) {
						map.put(mapKey[j], jsonObject.get(mapKey[j]));
					}
					list.add(map);
				}
			}else{
				System.out.println("jasonToList::无数据");
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return list;

	}

}
