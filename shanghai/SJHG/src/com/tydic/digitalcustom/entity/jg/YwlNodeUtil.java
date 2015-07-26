package com.tydic.digitalcustom.entity.jg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import android.widget.Toast;

import com.tydic.digitalcustom.entity.MyJason;
import com.tydic.digitalcustom.entity.Webservice;
import com.tydic.digitalcustom.entity.WholeConstant;

public class YwlNodeUtil {
	/**
	 * 获取二级菜单的子菜单的ZBMC 列表 与 guid 列表
	 * 
	 * @param zbmcChilds
	 *            ZBMC 列表
	 * @param guidChildIds
	 *            guid 列表
	 * @param position
	 *            获取的节点位置
	 */
	public static Map<String,String[]> dealChilds(int position)  throws JSONException{
		Map<String,String[]> retMap = new HashMap<String,String[]>();
		try {
			JSONObject aaaMap = (JSONObject) WholeConstant.menuArray.get(position);
			JSONArray childsList = (JSONArray) aaaMap.get("childs");
			String zbmcChilds[] = new String[childsList.length()];//ZBMC 的列表
			String guidChildIds[]= new String[childsList.length()]; //GUID 的列表
			JSONObject achildMap;
			if(childsList!=null&&childsList.length()>0)
			for (int i = 0; i < childsList.length(); i++) {
				try {
					achildMap = (JSONObject) childsList.get(i);
					zbmcChilds[i] = achildMap.get("ZBMC").toString();
					guidChildIds[i] = achildMap.get("GUID").toString();
				} catch (JSONException e) {
					 e.printStackTrace();
					 System.err.println("二级菜单解析异常"+e.getMessage());
				}
			}
//			System.out.println("zbmcChilds::"+zbmcChilds);
			retMap.put("zbmcChilds",zbmcChilds);
			retMap.put("guidChildIds",guidChildIds);
		} catch (JSONException e1) {
			e1.printStackTrace();
//			Toast.makeText(this, "数据接收异常", 1).show()
			return null;
//			throw e1;
		}
		return retMap;
	}

	/**
	 * 获取所有的节点
	 * 
	 * @return
	 */
	public static List<Map<String, String>> getAllChilds() {
		List<Map<String, String>> retList = new ArrayList<Map<String, String>>();
		Map<String, String> aChildeNodeMap = null;
		for (int i = 0; i < WholeConstant.menuArray.length(); i++) {
			try {
				JSONObject oneObj = (JSONObject) WholeConstant.menuArray.get(i);
				JSONArray twochildObj = (JSONArray) oneObj.get("childs");
				if (twochildObj != null) {
					for (int t1 = 0; t1 < twochildObj.length(); t1++) {
						JSONObject twoObj = (JSONObject) twochildObj.get(t1);
						aChildeNodeMap = new HashMap<String, String>();
						aChildeNodeMap.put("FZBMC", twoObj.getString("FZBMC"));
						aChildeNodeMap.put("ZBMC", twoObj.getString("ZBMC"));
						aChildeNodeMap.put("GUID", twoObj.getString("GUID"));
						retList.add(aChildeNodeMap);

						JSONArray threeChildObj = (JSONArray) oneObj
								.get("childs");
						if (threeChildObj != null) {
							for (int t2 = 0; t2 < threeChildObj.length(); t2++) {
								JSONObject threeObj = (JSONObject) twochildObj
										.get(t2);
								aChildeNodeMap = new HashMap<String, String>();
								aChildeNodeMap.put("FZBMC",
										threeObj.getString("FZBMC"));
								aChildeNodeMap.put("ZBMC",
										threeObj.getString("ZBMC"));
								aChildeNodeMap.put("GUID",
										threeObj.getString("GUID"));
								retList.add(aChildeNodeMap);
							}
						}
					}
				}
				aChildeNodeMap = new HashMap<String, String>();
				aChildeNodeMap.put("ZBMC", oneObj.getString("ZBMC"));
				aChildeNodeMap.put("GUID", oneObj.getString("GUID"));
				aChildeNodeMap.put("FZBMC", oneObj.getString("FZBMC"));
				retList.add(aChildeNodeMap);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return retList;
	}

	public static String[] getZBMCMenu() {
		List<Map<String, Object>> menuList = new ArrayList<Map<String, Object>>();
		try {
			System.out.println("获取ZBMC菜单");
			Webservice webservice = Webservice.getInstance();
			webservice.setMETHOD_NAME("getMenuNodes");
			boolean resultFlag = webservice.connect(null, null);
			if (resultFlag) {
				if (webservice.getResult() != null) {
					MyJason myJason = new MyJason(webservice.getResult()
							.toString());
					try {
						WholeConstant.menuArray = new JSONArray(webservice
								.getResult().toString());
					} catch (JSONException e) {
						e.printStackTrace();
						System.out.println("获取菜单返回数据失败" + e.getMessage());
					}
					Log.d("--------------业务量获取菜单返回数据", webservice.getResult()
							.toString());
					// 关区中文名 关区代码 报关单总量 通关无纸化报关单总量 无纸率
					menuList = myJason.jasonToList(new String[] { "GUID",
							"ZBMC", "childs", "FZBMC" });
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		String menuIds[] = null;
		String menu[] = null;
		if (menuList != null && menuList.size() > 0) {
			menu = new String[menuList.size()];
			menuIds = new String[menuList.size()];
			Map<String, Object> aMap = null;
			for (int i = 0; i < menuList.size(); i++) {
				aMap = (HashMap<String, Object>) menuList.get(i);
				menu[i] = aMap.get("ZBMC").toString();
				menuIds[i] = aMap.get("GUID").toString();
			}
		} else {
			menu = new String[] { "暂无菜单" };
		}
		WholeConstant.ZBMCIDMenu = menuIds;
		WholeConstant.ZBMCMenu = menu;
		return menu;
	}

	public static List<Map<String, Object>> getChildNodes(String guid) {
		List<Map<String, Object>> menuList = new ArrayList<Map<String, Object>>();
		try {
			Webservice webservice = Webservice.getInstance();
			webservice.setMETHOD_NAME("getChildNodes");
			boolean resultFlag = webservice.connect(new String[] { "JGID" },
					new Object[] { guid });
			if (resultFlag) {
				if (webservice.getResult() != null) {
					MyJason myJason = new MyJason(webservice.getResult()
							.toString());
					Log.d("+++++获取菜单返回数据", webservice.getResult().toString());
					menuList = myJason.jasonToList(new String[] { "GUID",
							"ZBMC", "FZBMC" });
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return menuList;
	}
}
