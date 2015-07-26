package com.bai.demo.utils;

import java.util.ArrayList;
import java.util.Iterator;

import com.bai.demo.bean.EntryHead;
import com.bai.demo.bean.EntryList;
import com.bai.demo.bean.PhotoList;
import com.bai.demo.entity.Constant;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class MDataBaseHelper extends SQLiteOpenHelper {

	private ArrayList<EntryHead> listEntryId = null;
	private ArrayList<EntryList> listEntryList = null;
	private ArrayList<PhotoList> listPhotoList = null;

	public MDataBaseHelper(Context context) {
		super(context, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// 建立Entry_Head表
		String sql1 = "Create table " + Constant.Entry_Head + "("
				+ Constant.Entry_ID + " Varchar(22) UNIQUE ," + Constant.State
				+ " Integer," + Constant.Save_Time + " Varchar(20),"
				+ Constant.Upload_Time + " Varchar(20));";
		// 建立Entry_List表
		String sql2 = "Create table " + Constant.Entry_List + "("
				+ Constant.Entry_ID + " Varchar(22)," + Constant.G_No
				+ " Integer," + Constant.isYs
				+ " Integer DEFAULT 0," + Constant.Remark + " Varchar(200));";
		// 建立Photo_list表
		String sql3 = "Create table " + Constant.Photo_list + "("
				+ Constant.Entry_ID + " Varchar(22)," + Constant.G_No
				+ " Integer(2)," + Constant.Photo_list_Code + " Varchar(5),"
				+ Constant.Photo_list_ID + " varchar(100));";
		db.execSQL(sql1);
		db.execSQL(sql2);
		db.execSQL(sql3);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String sql1 = " DROP TABLE IF EXISTS " + Constant.Entry_Head;
		String sql2 = "DROP TABLE IF EXISTS" + Constant.Entry_List;
		String sql3 = "DROP TABLE IF EXISTS" + Constant.Photo_list;
		db.execSQL(sql1);
		db.execSQL(sql2);
		db.execSQL(sql3);
		onCreate(db);
	}

//	public Cursor select() {
//		SQLiteDatabase db = this.getReadableDatabase();
//		Cursor cursor = db.query(Constant.Entry_Head, null, null, null, null,
//				null, Constant.Entry_ID);
//		return cursor;
//	}

	/**
	 * @author jiangyue 向Entry_Head插入数据
	 * @param Entry_ID
	 *            : 报关单号
	 * @param State
	 *            :状态
	 * @param Save_Time
	 *            :暂存时间
	 * @param Upload_Time
	 *            :上传时间
	 * */
	public long insertToEntry_Head(String Entry_ID, int State,
			String Save_Time, String Upload_Time) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put(Constant.Entry_ID, Entry_ID);
		cv.put(Constant.State, State);
		cv.put(Constant.Save_Time, Save_Time);
		cv.put(Constant.Upload_Time, Upload_Time);
		long row = db.insert(Constant.Entry_Head, null, cv);
		return row;
	}

	/**
	 * @author jiangyue 向Entry_LIST插入数据
	 * @param Entry_ID
	 *     : 报关单号
	 * @param G_No
	 *            : 项号
	 * @param CODE_TS
	 *            : 商品编码-----无效
	 * @param G_NAME
	 *            : 品名---无效
	 * @param isYs
	 *            : 是否移送   0  1   2
	 * @param Remark
	 *            : 备注
	 * */
	public long insertToEntry_LIST(String Entry_ID, String G_No,int isYs, String Remark) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put(Constant.Entry_ID, Entry_ID);
		cv.put(Constant.G_No, G_No);
		cv.put(Constant.isYs, isYs);
		cv.put(Constant.Remark, Remark);
		long row = db.insert(Constant.Entry_List, null, cv);
		//测试
		//ArrayList<EntryList> lists=selectFromEntryList();
		return row;
	}

	/**
	 * @author jiangyue 向Photo_list插入数据
	 * @param Entry_ID
	 *            :报关单号
	 * @param G_No
	 *            :项号  0   
	 * @param Photo_list_Code
	 *            :图片类型
	 * @param Photo_list_ID------从SDCard的绝对Path
	 *            :图片名
	 * */
	public long insertToPhoto_list(String Entry_ID, String G_No,
			String Photo_list_Code, String Photo_list_ID) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put(Constant.Entry_ID, Entry_ID);
		cv.put(Constant.G_No, G_No);
		cv.put(Constant.Photo_list_Code, Photo_list_Code);
		cv.put(Constant.Photo_list_ID, Photo_list_ID);
		long row = db.insert(Constant.Photo_list, null, cv);
		return row;

	}

	/**
	 * 删除三张表中State = 2并且上传时间与现在时间差大于24小时的数据
	 * 
	 * @param Upload_Time
	 *            : 上传时间
	 * @author jiangyue 删除数据
	 * 
	 * julianday('now')  调用当前日期
	 * */
	public void delete(String Upload_Time) {

		SQLiteDatabase db = this.getWritableDatabase();
		String sql1 = "DELETE FROM Photo_list WHERE Entry_ID in (SELECT Entry_Id FROM Entry_Head WHERE State = 2 AND "
				+ "julianday('now')-julianday(" + Upload_Time + ")>1)";
		String sql2 = "DELETE FROM Entry_List WHERE Entry_ID in (SELECT Entry_Id FROM Entry_Head WHERE State = 2 AND "
				+ "julianday('now')-julianday(" + Upload_Time + ")>1)";
		String sql3 = "DELETE FROM Entry_Head WHERE  State = 2 AND "
				+ "julianday('now')-julianday('" + Upload_Time + "')>1";
		db.execSQL(sql1);
		db.execSQL(sql2);
		db.execSQL(sql3);
	}

	/**
	 * 删除所选的报关单数据，同时删除移动设备本地保存的该报关单的附件，即删除以报关单为名的一级文件夹
	 * 
	 * @author jiangyue 删除数据
	 * */
	public void deleteForStorage(String EntryID) {
		SQLiteDatabase db = this.getWritableDatabase();
		String sql1 = "DELETE FROM "+Constant.Entry_Head+" WHERE Entry_Id='" + EntryID + "'";
		String sql2 = "DELETE FROM "+Constant.Entry_List+" WHERE Entry_Id='" + EntryID + "'";
		String sql3 = "DELETE FROM "+Constant.Photo_list+" WHERE Entry_Id='" + EntryID + "'";
		db.execSQL(sql1);
		db.execSQL(sql2);
		db.execSQL(sql3);
	}

	/**
	 * 删除数据库的图片信息
	 * @param imagePath
	 * @return
	 */
	public boolean deletePhotoListByPath(String imagePath){
		boolean flag=false;
		try {
			SQLiteDatabase db = this.getWritableDatabase();
			String sql="SELECT * FROM "+Constant.Photo_list+" WHERE "+Constant.Photo_list_ID+"='"+imagePath+"'";
			Cursor c=db.rawQuery(sql, null);
			while(c.moveToNext()){
				sql="DELETE FROM "+Constant.Photo_list+" WHERE "+Constant.Photo_list_ID+"='"+imagePath+"'";
				//db.delete(Constant.Photo_list, new String[]{Constant.Photo_list_ID}, new String[]{imagePath});
				db.execSQL(sql);
			}
			flag=true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}
	
	
	/**
	 * 用于查询现有数据库中EntryHead表中的所有现存数据
	 * 
	 * @author jiangyue
	 * @return ArrayList<EntryHead> 返回值为列表形式的EntryHead的所有先存数据
	 * */
 	public ArrayList<EntryHead> selectFromEntryHead() {
		String sql = "select * from Entry_Head;";
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(sql, null);
		listEntryId = new ArrayList<EntryHead>();
		EntryHead entryHead = null;
		while(cursor.moveToNext()){
			entryHead = new EntryHead();;
			String EntryID = cursor.getString(cursor
					.getColumnIndex(Constant.Entry_ID));
			int State = cursor.getInt(cursor.getColumnIndex(Constant.State));
			String Save_Time = cursor.getString(cursor
					.getColumnIndex(Constant.Save_Time));
			String Upload_Time = cursor.getString(cursor
					.getColumnIndex(Constant.Upload_Time));
			entryHead.setEntry_ID(EntryID);
			entryHead.setState(State);
			entryHead.setSave_Time(Save_Time);
			entryHead.setUpload_Time(Upload_Time);
			listEntryId.add(entryHead);
		}
		cursor.close();
		return listEntryId;
	}

	/**
	 * 用于查询现有数据库中EntryList表中的所有现存数据
	 * 
	 * @author jiangyue
	 * @return ArrayList<EntryList> 返回值为列表形式的EntryList的所有先存数据
	 * */
	public ArrayList<EntryList> selectFromEntryList() {
		String sql = "select * from Entry_List;";
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(sql, null);
		listEntryList = new ArrayList<EntryList>();
		while(cursor.moveToNext()){
			EntryList entryList = new EntryList();
			String Entry_ID = cursor.getString(cursor
					.getColumnIndex(Constant.Entry_ID));
			int G_No = cursor.getInt(cursor.getColumnIndex(Constant.G_No));

			int isYs = cursor.getInt(cursor.getColumnIndex(Constant.isYs));
			String Remark = cursor.getString(cursor
					.getColumnIndex(Constant.Remark));
			entryList.setEntry_ID(Entry_ID);
			entryList.setG_No(G_No);
		
			entryList.setIsYs(isYs);
			entryList.setRemark(Remark);
			listEntryList.add(entryList);
		}
		cursor.close();
		return listEntryList;
	}

	/**
	 * 用于查询现有数据库中PhotoList表中的所有现存数据
	 * 
	 * @author jiangyue
	 * @return ArrayList<PhotoList> 返回值为列表形式的PhotoList的所有先存数据
	 * */
	public ArrayList<PhotoList> selectFromPhotoList() {
		String sql = "select * from "+Constant.Photo_list;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(sql, null);
		listPhotoList = new ArrayList<PhotoList>();
		while(cursor.moveToNext()){
			PhotoList photoList = new PhotoList();
			String Entry_ID = cursor.getString(cursor
					.getColumnIndex(Constant.Entry_ID));
			int G_No = cursor.getInt(cursor.getColumnIndex(Constant.G_No));
			String Photo_list_Code = cursor.getString(cursor
					.getColumnIndex(Constant.Photo_list_Code));
			String Photo_list_ID = cursor.getString(cursor
					.getColumnIndex(Constant.Photo_list_ID));
			photoList.setEntry_ID(Entry_ID);
			photoList.setG_No(G_No);
			photoList.setPhoto_list_Code(Photo_list_Code);
			photoList.setPhoto_list_ID(Photo_list_ID);
			listPhotoList.add(photoList);
		}
		cursor.close();
		return listPhotoList;

	}
	/***
	 * 根究报关单号查询 PhotoList
	 * @param entryId 报关单号
	 * @return
	 */
	public ArrayList<PhotoList> selectFromPhotoListByEntryId(String entryId) {
		String sql = "select * from "+Constant.Photo_list+" WHERE "+Constant.Entry_ID+"='"+entryId+"'";
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(sql, null);
		listPhotoList = new ArrayList<PhotoList>();
		while(cursor.moveToNext()){
			PhotoList photoList = new PhotoList();
			String Entry_ID = cursor.getString(cursor
					.getColumnIndex(Constant.Entry_ID));
			int G_No = cursor.getInt(cursor.getColumnIndex(Constant.G_No));
			String Photo_list_Code = cursor.getString(cursor
					.getColumnIndex(Constant.Photo_list_Code));
			String Photo_list_ID = cursor.getString(cursor
					.getColumnIndex(Constant.Photo_list_ID));
			photoList.setEntry_ID(Entry_ID);
			photoList.setG_No(G_No);
			photoList.setPhoto_list_Code(Photo_list_Code);
			photoList.setPhoto_list_ID(Photo_list_ID);
			listPhotoList.add(photoList);
			System.out.println("查询结果----->"+Entry_ID+"--"+G_No+"--"+Photo_list_Code+"--"+Photo_list_ID);
		}
		cursor.close();
		return listPhotoList;

	}

	/**
	 * 用于报关单表中获取暂存的单证列表
	 * 
	 * @author jiangyue
	 * @return ArrayList<EntryHead> 返回报关单表中获取暂存的单证列表
	 * */
	public ArrayList<EntryHead> selectEntryHeadForData() {
		ArrayList<EntryHead> listEntryIdStorage = new ArrayList<EntryHead>();
		String sql = "SELECT * FROM Entry_head WHERE State=1 ORDER BY Save_time Desc;";
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(sql, null);
		while(cursor.moveToNext()){
			EntryHead entryHead = new EntryHead();
			String EntryID = cursor.getString(cursor
					.getColumnIndex(Constant.Entry_ID));
			int State = cursor.getInt(cursor.getColumnIndex(Constant.State));
			String Save_Time = cursor.getString(cursor
					.getColumnIndex(Constant.Save_Time));
			String Upload_Time = cursor.getString(cursor
					.getColumnIndex(Constant.Upload_Time));
			entryHead.setEntry_ID(EntryID);
			entryHead.setState(State);
			entryHead.setSave_Time(Save_Time);
			entryHead.setUpload_Time(Upload_Time);
			listEntryId.add(entryHead);
		}
		cursor.close();
		return listEntryId;
	}
	
	
	/**
	 * 查询单个的 暂存的表头信息
	 * @param entry
	 * @return
	 */
	public EntryHead selectEntryHeadByEntry(String entry) {
		EntryHead entryHead = new EntryHead();
		String sql = "SELECT * FROM Entry_head WHERE State=1 AND Entry_ID='"+entry+"'";
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(sql, null);
		if(cursor.moveToNext()){
			String EntryID = cursor.getString(cursor
					.getColumnIndex(Constant.Entry_ID));
			int State = cursor.getInt(cursor.getColumnIndex(Constant.State));
			String Save_Time = cursor.getString(cursor
					.getColumnIndex(Constant.Save_Time));
			String Upload_Time = cursor.getString(cursor
					.getColumnIndex(Constant.Upload_Time));
			entryHead.setEntry_ID(EntryID);
			entryHead.setState(State);
			entryHead.setSave_Time(Save_Time);
			entryHead.setUpload_Time(Upload_Time);
		}
		cursor.close();
		return entryHead;
	}
	
	/**
	 * 查询单个的 暂存的表头信息
	 * @param entry
	 * @return
	 */
	public EntryList selectEntryListByEntry(String entry) {
		String sql = "select * from "+Constant.Entry_List+" WHERE "+Constant.Entry_ID+"='"+entry+"'";
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(sql, null);
		EntryList entryList = new EntryList();
		while(cursor.moveToNext()){
			String Entry_ID = cursor.getString(cursor
					.getColumnIndex(Constant.Entry_ID));
			int G_No = cursor.getInt(cursor.getColumnIndex(Constant.G_No));
			int isYs = cursor.getInt(cursor.getColumnIndex(Constant.isYs));
			String Remark = cursor.getString(cursor
					.getColumnIndex(Constant.Remark));
			entryList.setEntry_ID(Entry_ID);
			entryList.setG_No(G_No);
			entryList.setIsYs(isYs);
			entryList.setRemark(Remark);
			listEntryList.add(entryList);
		}
		cursor.close();
		return entryList;
	}
	/**
	 * 修改表头上传时间
	 * @param entryId
	 * @param saveTime
	 * @return
	 */
	public boolean updateEntry_HeadById(String entryId,String saveTime){
		boolean flag=false;
		SQLiteDatabase db = this.getWritableDatabase();
		try {
			String sql="UPDATE "+Constant.Entry_Head+" SET "+Constant.Save_Time+"='"+saveTime+"'"+" WHERE "+Constant.Entry_ID+"='"+entryId+"'";
			db.execSQL(sql);
			flag=true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return flag;
	}
	
	/**
	 * 修改表体上传的标志
	 * @param entryId
	 * @param gNo
	 * @param isYs
	 * @return
	 */
	public boolean updateEntry_ListById(String entryId,String gNo,int isYs){
		boolean flag=false;
		SQLiteDatabase db = this.getWritableDatabase();
		try {
			String sql="UPDATE "+Constant.Entry_List+" SET "+Constant.isYs+"="+isYs+" WHERE "+Constant.Entry_ID+"='"+entryId+"'"+" AND "+Constant.G_No+"='"+gNo+"'";
			db.execSQL(sql);
			flag=true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return flag;
	}
	

}
