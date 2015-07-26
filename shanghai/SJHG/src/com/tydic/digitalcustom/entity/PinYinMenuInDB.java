package com.tydic.digitalcustom.entity;

/**
 * PinYinMenuInDB.java [v 1.0.0]
 * classes: com.tydic.digitalcustom.entity.PinYinMenuInDB
 * 兮兮 Create at 2013-7-9 上午10:14:39
 */

import android.R.bool;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.tydic.digitalcustom.tools.DatabaseHelper;
import com.tydic.digitalcustom.tools.HanZi2PinYin;

/**
 * 
 * @author 兮兮（bai） <br>
 *         creat at 2013-7-9 上午10:14:39
 * 
 *         将拼音菜单保存如数据库中,便于菜单搜索 <br>
 *         如：通关无纸化 保存入数据库菜单为 通 tong 关 guan 无 wu 纸 zhi 化 hua
 */
public class PinYinMenuInDB {

	private String[] menus;
	private String[] menuIds;
	private DatabaseHelper dbHelper;
	private Context context;
	private SQLiteDatabase database;

	/**
	 * @param menu
	 *            中文菜单
	 */
	public PinYinMenuInDB(String menu, Context context) {
		super();
		String[] tempMenus = { "" };
		tempMenus[0] = menu;
		this.menus = tempMenus;
		this.context = context;
	}

	/**
	 * @param menuIds
	 *            菜单ID
	 * @param menus
	 *            中文菜单数组
	 */
	public PinYinMenuInDB(String menuIds[], String[] menus, Context context) {
		super();
		this.menuIds = menuIds;
		this.menus = menus;
		this.context = context;
	}

	/**
	 * @param menus
	 *            中文菜单数组
	 */
	public PinYinMenuInDB(String[] menus, Context context) {
		super();
		this.menus = menus;
		this.context = context;
	}

	public boolean saveInDb(String tab) {
		dbHelper = new DatabaseHelper(context, "MenuDB.db", 1);
		if (database == null) {
			database = dbHelper.getReadableDatabase();
		}
		// 将菜单插入数据库
		return insertData(database, tab, menus, menuIds, getMixMenus());

	}

	/**
	 * 
	 * @param db
	 * @param tag
	 *            所属tab大类
	 * @param mixMenus
	 *            混合菜单
	 */
	private Boolean insertData(SQLiteDatabase db, String tag, String[] menu,
			String[] menuIDS, String[] mixMenus) {
		int menusLength = mixMenus.length;

		Cursor cursor = db.rawQuery("select * from menu", null);
		if (cursor.getCount() != 0) {

			if (cursor.getCount() != menusLength) {
				Log.d("---------", "数据库菜单更新");
				for (int i = 0; i < menusLength; i++) {
					// 执行插入语句
					System.out.println("menus:" + menus);
					System.out.println("mixMenus:" + mixMenus);
					System.out.println("menuIDS:" + menuIDS);
					db.execSQL("insert into menu values(null,?,?,?,?)",
							new String[] { tag, menus[i], mixMenus[i],
									menuIDS[i] });
				}

			}
		} else {
			return false;
		}
		return true;
	}

	/**
	 * 获得混合菜单
	 * 
	 * @return
	 */
	private String[] getMixMenus() {
		int length = menus.length;
		String[] mixMenus = new String[length];
		for (int i = 0; i < length; i++) {
			mixMenus[i] = HanZi2PinYin.toMixPinYin(menus[i]);
		}
		return mixMenus;
	}

	public void close(SQLiteDatabase db) {
		if (db != null) {
			db.close();

		}
	}
}
