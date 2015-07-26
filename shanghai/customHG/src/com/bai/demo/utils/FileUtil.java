package com.bai.demo.utils;

import java.util.*;
import java.io.*;

import android.util.Log;

/**
 * 获取图片文件列表的集合
 * @author ouyyt
 *
 */
public class FileUtil {
	/**
	 * 用于遍历存储卡下HG目录下指定文件夹中的所有jpg或png或gif文件
	 * */
	public static List<Map<String,Object>> getFileList(String picFilePath){
		List<Map<String,Object>> ls = new ArrayList<Map<String,Object>>();
		File picfile = new File(picFilePath);
		File[] picfiles = picfile.listFiles();
		if(picfiles != null && picfiles.length > 0) {
			for (int i = 0; i < picfiles.length; i++) {
				if(picfiles[i].isDirectory()) {
					getFileList(picFilePath);
				} else {
					// 获取文件后缀
					String ext = picfiles[i].getAbsolutePath().substring(picfiles[i].getAbsolutePath().lastIndexOf(".",picfiles[i].getAbsolutePath().length()));
					Log.v("文件后缀::::",ext);
					
					// 判断文件的后缀是不是jpg或者png或者gif
					if(ext.equalsIgnoreCase(".jpg") || ext.equalsIgnoreCase(".png") || ext.equalsIgnoreCase(".gif")) {
						Map<String,Object> map = new HashMap<String, Object>();
						map.put("picFilePath", picfiles[i].getAbsolutePath());
						ls.add(map);
					}
				}
			}
		}
		System.out.println("ls--->"+ls.size());
		return ls;
	}
}
