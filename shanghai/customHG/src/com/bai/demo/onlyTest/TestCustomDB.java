package com.bai.demo.onlyTest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import com.bai.demo.bean.PhotoList;
import com.bai.demo.utils.MDataBaseHelper;
import com.google.gson.Gson;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;

/**
 * 测试数据库：
 * @author zhangyx
 *
 */
public class TestCustomDB extends Activity{

	private MDataBaseHelper dbHelp;//数据库帮助类
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		dbHelp=new MDataBaseHelper(this);
		ArrayList<PhotoList> list=dbHelp.selectFromPhotoList();
		Gson gson=new Gson();
		String str=gson.toJson(list);
		w(str);
		System.out.println(str);
	}
	
	private void w(String str){
		String strPath=Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+"testWeb.txt";
		byte[] byteStr=str.getBytes();
		FileOutputStream out=null;
		try {
			out=new FileOutputStream(new File(strPath));
			out.write(byteStr);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(out!=null){
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}

