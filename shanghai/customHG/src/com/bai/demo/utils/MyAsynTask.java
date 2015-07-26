package com.bai.demo.utils;

import java.util.Map;

import android.os.AsyncTask;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * 生成该类的对象，并调用execute方法之后 首先执行的是onProExecute方法 其次执行doInBackgroup方法
 * 
 */
public class MyAsynTask extends AsyncTask<Integer, Integer, Object> {

	public MyAsynTask(){
		
	}
	
	@Override
	protected Object doInBackground(Integer... params) {
		
		return null;
	}

	
	// 给List集合赋值的方法
	
	//适配器修改的方法

}
