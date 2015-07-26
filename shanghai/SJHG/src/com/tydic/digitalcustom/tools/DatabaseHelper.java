/**
 * DatabaseHelper.java [v 1.0.0]
 * classes: com.tydic.digitalcustom.tools.DatabaseHelper
 * 兮兮 Create at 2013-7-9 上午10:31:52
 */
package com.tydic.digitalcustom.tools;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * com.tydic.digitalcustom.tools.DatabaseHelper 对SQLite进行操作
 * 
 * @author 兮兮（bai） <br>
 *         creat at 2013-7-9 上午10:31:52
 * 
 */
public class DatabaseHelper extends SQLiteOpenHelper {

	final String CREATE_TABLE_SQL = "create table menu(_id integer primary key autoincrement ,tab_tag, menu,menu_tag,guid)";

	/**
	 * @param context
	 * @param name
	 * @param version
	 */
	public DatabaseHelper(Context context, String name, int version) {
		super(context, name, null, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// 第一个使用数据库时自动建表
		db.execSQL(CREATE_TABLE_SQL);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		System.out.println("--------onUpdate Called--------" + oldVersion
				+ "--->" + newVersion);
	}
	
	public void close(SQLiteDatabase db){
		if(db != null){
			db.close();
		}
	}
}
