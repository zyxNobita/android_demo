package com.tydic.digitalcustom.entity;


/**
 * 
 * 用来管理多级菜单的选择
 * 
 * @author baiyin
 * 
 */
public class MenuSelectManager {

	/**
	 * 
	 * @param menu
	 *            二级菜单
	 * @param child
	 *            三级菜单
	 * @return
	 */
	public static final int getMenu(int menu, int child) {

		return (menu+1) * 100 + child+1;
	}
	
	/**
	 * 菜单中文名转拼音
	 */
	public static final String[] getMenuInPinyin(String[] menu){
	
		return menu;
		
		
	}
}
