package com.tydic.digitalcustom.entity;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;

import com.tydic.digitalcustom.R;

/**
 * 
 * Description:隔行变色的SimpleAdapter IntervalColorAdapter.java Create on 2013-8-5
 * 下午2:29:39
 * 
 * @author wangwx
 * @version 1.0 Copyright (c) 2013 Company,Inc. All Rights Reserved.
 */
@SuppressWarnings("unused")
public class IntervalColorAdapter extends SimpleAdapter {

	private int[] colors = { R.color.chartbg, R.color.white };
	private String judgeColumn = "CUS_NAME";// 根据某列进行判断是否变色
	private String judgeValue = "";//当前
	private String lastJudgeValue = "";//上一个
	private int    curColor = 0;
	private int    pos = 0;
	
	private Map<String,Integer> colorMap = new LinkedHashMap<String,Integer>();
	private List<? extends Map<String, ?>> dataList;

	public IntervalColorAdapter(Context context,
			List<? extends Map<String, ?>> data, int resource, String[] from,
			int[] to) {
		super(context, data, resource, from, to);
		this.dataList = data;
	}

	public String getJudgeColumn() {
		return judgeColumn;
	}

	public void setJudgeColumn(String judgeColumn) {
		this.judgeColumn = judgeColumn;
	}

	public int[] getColors() {
		return colors;
	}

	public void setColors(int[] colors) {
		this.colors = colors;
	}

	
	public Map<String, Integer> getColorMap() {
		return colorMap;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		 View   curView = null;
		
		curView = super.getView(position, convertView, parent);
        
		Map<String, ?> aMap = dataList.get(position);
		judgeValue = (String)aMap.get(judgeColumn);
		if(colorMap.get(String.valueOf(position))==null){
			if(position==0){
				pos=0;
			}else{
				if (!"".equals(judgeValue)){
					pos+=1;
				}
			}
			curColor = colors[pos%2];
			lastJudgeValue = judgeValue;
			colorMap.put(String.valueOf(position),curColor);
//			System.out.println("position:"+position+"--"+colorMap);
			curView.setBackgroundResource(curColor);
		}else{
			curView.setBackgroundResource(colorMap.get(String.valueOf(position)));
		}
		return curView;
	}

	
}
