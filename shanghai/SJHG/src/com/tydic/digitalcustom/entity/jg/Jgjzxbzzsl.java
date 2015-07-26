package com.tydic.digitalcustom.entity.jg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.tydic.digitalcustom.R;
import com.tydic.digitalcustom.activity.WdscActivity;
import com.tydic.digitalcustom.entity.Constant;
import com.tydic.digitalcustom.entity.MyGestureUtil;
import com.tydic.digitalcustom.entity.MyJason;
import com.tydic.digitalcustom.entity.SharePreferenceUtils;
import com.tydic.digitalcustom.entity.Webservice;
import com.tydic.digitalcustom.tools.Tools;
import com.tydic.digitalcustom.widget.MyTask;

/**
 * 
 * Description:业务量-监管菜单-进出口货物重量 Jgjzxbzzsl.java Create on 2013-7-15 下午12:16:18
 * 
 * @author wangwx
 * @version 1.0 Copyright (c) 2013 Company,Inc. All Rights Reserved.
 */
@SuppressLint("HandlerLeak")
@SuppressWarnings({ "unchecked","unused" })
public class Jgjzxbzzsl {
	private List<Map<String, Object>> monthData = new ArrayList<Map<String, Object>>();
	private LinearLayout contentLayout = null;
	private int btnPostion = 2;// 默认总量按钮
	private ListView tablemonthList;
	private ListView tablemonthYearList;
	private LayoutInflater inflater = null;
	private View menuView;
	private Float posX;
	private Handler yearHandler;
	private Handler twoLeavemenuHandler;
	private Handler monthHandler;
	private Context context;
	private View right_bottom = null;
	private Activity curActivity;//当前活动的active
	private SimpleAdapter monthListAdapter = null;
	private SimpleAdapter monthYearListAdapter = null;
	private WebView mWebView;
	private String guid = "";
	private float oldDist;
	private int ZOOM = 0;// 0:正常 1:放大
	private int count;
	private int sheetStatue = Constant.ZOOM_OUT;
	private long firClick,secClick;//窗体点击时间
	final int rela2 = Animation.RELATIVE_TO_PARENT;
	LinearLayout chartArear =null;
	final ScaleAnimation rightIn = new ScaleAnimation(0, 1, 0, 1, rela2, 0.0f,
			rela2, 1.0f);
	final ScaleAnimation t_right_out = new ScaleAnimation(1, 0, 1, 0, rela2,
			1.0f, rela2, 0f);
	final ScaleAnimation t_left_out = new ScaleAnimation(1, 0, 1, 0, rela2,
			1.0f, rela2, 0.0f);
	final ScaleAnimation t_right_in = new ScaleAnimation(0, 1, 0, 1, rela2,
			1.0f, rela2, 0.0f);
	private boolean isFavorite  = false;//是否已经收藏
	private boolean isFromFavorite  = false;

	public void setFromFavorite(boolean isFromFavorite) {
		this.isFromFavorite = isFromFavorite;
	}

	private LinearLayout leftTop,rightTop,rightBottom;
	private RelativeLayout leftBottom  = null;
	private OnClickListener addFavorite = null;//添加收藏事件
	private OnClickListener removeFavorite = null;//移除收藏事件 
	public Jgjzxbzzsl(Activity curActivity, LinearLayout contentLayout) {
		this.curActivity = curActivity;
		this.context = curActivity.getApplicationContext();
		this.contentLayout = contentLayout;
	}

	
	public void initJGJZXBZZSL(JSONArray threeChildsList,String CurrTitle,String guid) {
		this.guid=guid;
		monthListAdapter = new SimpleAdapter(
				context, monthData, R.layout.menu_item_table,
				new String[] {  "DY", "DYTB","YDDY","YDDYTB" }, new int[] { R.id.table_count, R.id.table_tongbi,R.id.table_yddy,R.id.table_yddytb});
		
		monthYearListAdapter = new SimpleAdapter(
				context, monthData, R.layout.menu_item_year_table,
				new String[] { "ZB_MONTH" }, new int[] {R.id.table_year});
		inflater = LayoutInflater.from(context);
		menuView = inflater.inflate(R.layout.ywl, null,false);
		
		monthListAdapter.notifyDataSetChanged();
		/*MyTask myTask = new MyTask(monthListAdapter);
		myTask.execute(1000);*/
		
		monthYearListAdapter.notifyDataSetChanged();
		
		/*MyTask myYearTask = new MyTask(monthYearListAdapter);
		myYearTask.execute(1000);*/
		
		leftTop = (LinearLayout) menuView.findViewById(R.id.left_top);
		chartArear = (LinearLayout)leftTop.findViewById(R.id.chartArear);
		mWebView = (WebView) leftTop.findViewById(R.id.chartArearWeb);
		leftBottom = (RelativeLayout) menuView
				.findViewById(R.id.left_bottom);
		rightTop = (LinearLayout) menuView
				.findViewById(R.id.right_top);
		rightBottom = (LinearLayout) menuView
				.findViewById(R.id.right_bottom);

		
		right_bottom = menuView.findViewById(R.id.right_bottom);
		right_bottom.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				
				if (MotionEvent.ACTION_DOWN == event.getAction()) {
					count++;
					if (count == 1) {
						firClick = System.currentTimeMillis();

					} else if (count == 2) {
						secClick = System.currentTimeMillis();
						if (secClick - firClick < 1000) {
							Log.d("sheetStatue", String.valueOf(sheetStatue));
							// 双击事件
							if (sheetStatue == Constant.ZOOM_OUT) {
								//放大
								//rightTop.startAnimation(t_right_out);
								//rightBottom.startAnimation(t_right_out);
								LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,LinearLayout.LayoutParams.FILL_PARENT);
								rightTop.setLayoutParams(params);
								rightBottom.setLayoutParams(params);
								rightTop.startAnimation(rightIn);
								rightBottom.startAnimation(rightIn);

								rightTop.setVisibility(View.VISIBLE);
								rightBottom.setVisibility(View.VISIBLE);
								leftTop.setVisibility(View.GONE);
								leftBottom.setVisibility(View.GONE);
								
								/*rightTop.startAnimation(t_right_in);
								rightBottom.startAnimation(t_right_in);
								leftTop.setVisibility(View.GONE);
								leftBottom.setVisibility(View.GONE);
								rightTop.setVisibility(View.VISIBLE);
								rightBottom.setVisibility(View.VISIBLE);
								LinearLayout.LayoutParams params =  new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,270);
								rightBottom.setLayoutParams(params);
								*/
								sheetStatue = Constant.ZOOM_IN;
								
							} else if (sheetStatue == Constant.ZOOM_IN) {								
								//缩小
								
								rightTop.startAnimation(t_right_in);
								rightBottom.startAnimation(t_right_in);

								leftBottom.startAnimation(rightIn);
								leftTop.startAnimation(rightIn);

								rightTop.setVisibility(View.VISIBLE);
								rightBottom.setVisibility(View.VISIBLE);
								leftTop.setVisibility(View.VISIBLE);
								leftBottom.setVisibility(View.VISIBLE);
								
								LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(220,LinearLayout.LayoutParams.FILL_PARENT);
								rightTop.setLayoutParams(params);
								rightBottom.setLayoutParams(params);
								
								LinearLayout.LayoutParams mWebViewParm = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,LinearLayout.LayoutParams.FILL_PARENT);
								mWebView.setLayoutParams(mWebViewParm);
								
								mWebView.loadUrl("javascript:zoomOUT()");
								/*rightTop.startAnimation(t_right_out);
								rightBottom.startAnimation(t_right_out);

								leftBottom.startAnimation(rightIn);
								leftTop.startAnimation(rightIn);
								
								rightTop.setVisibility(View.GONE);
								rightBottom.setVisibility(View.GONE);*/
								
								sheetStatue = Constant.ZOOM_OUT;
							}
						}
						count = 0;
						firClick = 0;
						secClick = 0;
					}
				}
				
				rightIn.setDuration(1000);
				t_right_out.setDuration(1000);
				t_left_out.setDuration(1000);
				t_right_in.setDuration(1000);
				return false;
			}
		});
		tablemonthYearList= (ListView) right_bottom.findViewById(R.id.table_month_year_list);
		tablemonthList = (ListView) right_bottom.findViewById(R.id.table_month_data_list);
		tablemonthList.setAdapter(monthListAdapter);
		tablemonthYearList.setAdapter(monthYearListAdapter);
		System.out.println("tbaleMonthList"+tablemonthList);
		tablemonthList.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				System.out.println("触发事件-----------");
				tablemonthYearList.dispatchTouchEvent(event);

				switch (event.getActionMasked()) {

				case MotionEvent.ACTION_POINTER_DOWN:// 多点触碰
					
					oldDist = MyGestureUtil.spacing(event);
					System.out.println("右边滑动");
					break;
				case MotionEvent.ACTION_MOVE:// 移动
					break;
				case MotionEvent.ACTION_UP:
					break;
				}

				return true;
			}
		});
		tablemonthYearList.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				tablemonthList.onTouchEvent(event);
				return false;
			}
		});
		
		
		
		
		if (contentLayout.getChildCount() == 0) {
			btnPostion = 2;
			// 缩放列表
		/*	tablemonthList = (ListView) menuView.findViewById(R.id.table_month_data_list);
			Log.d("----++++", "rightBottomList初始化成功！");
			TouchListenerForZoom listenerForZoom = new TouchListenerForZoom(
					context);
			tablemonthList.setOnTouchListener(listenerForZoom);*/

			addSlideEvent(menuView);
			contentLayout.addView(menuView);
			jkAndCk(menuView,threeChildsList,CurrTitle);
			getYearTable(menuView);
			
			try{
				if(threeChildsList!=null&&threeChildsList.length()>0){
					Tools.createWebView(menuView, R.id.chartArearWeb, twoLeavemenuHandler,
							"initData('','','','"+guid+"')",
							"file:///android_asset/ywl_jg_jckzl_all.html");
				}else{
					LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,350);
					chartArear.setLayoutParams(params);	
					Tools.createWebView(menuView, R.id.chartArearWeb, twoLeavemenuHandler,
							"initData('','','','"+guid+"')",
							"file:///android_asset/ywl_jg_jckzl_all_small.html");
				}
				
			}catch(Exception e){
				System.out.println("创建失败");
			}
		}
		
	}

	/**
	 * 添加滑动事件
	 * 
	 * @param v
	 *            添加事件的view
	 */
	public void addSlideEvent(View v) {

		rightIn.setDuration(1000);
		t_right_out.setDuration(1000);
		t_left_out.setDuration(1000);
		t_right_in.setDuration(1000);
		
		mWebView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					// System.out.println("开始");
					posX = event.getX();
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					// System.out.println("结束");
					posX = event.getX() - posX;
					if (posX < 0 && ZOOM == 1) {
						System.out.println("向左滑动");
						ZOOM = 0;
						rightTop.startAnimation(t_right_in);
						rightBottom.startAnimation(t_right_in);

						leftTop.startAnimation(rightIn);
						leftBottom.startAnimation(rightIn);

						rightTop.setVisibility(View.VISIBLE);
						rightBottom.setVisibility(View.VISIBLE);
						
						LinearLayout.LayoutParams mWebViewParm = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,LinearLayout.LayoutParams.FILL_PARENT);
						mWebView.setLayoutParams(mWebViewParm);
						mWebView.loadUrl("javascript:zoomOUT()");
					} else if (posX > 0 && ZOOM == 0) {
						System.out.println("向右滑动");
						ZOOM = 1;
						rightTop.startAnimation(t_right_out);
						rightBottom.startAnimation(t_right_out);

						leftBottom.startAnimation(rightIn);
						leftTop.startAnimation(rightIn);

						rightTop.setVisibility(View.GONE);
						rightBottom.setVisibility(View.GONE);

						LinearLayout.LayoutParams mWebViewParm = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,LinearLayout.LayoutParams.FILL_PARENT);
						mWebView.setLayoutParams(mWebViewParm);
						mWebView.loadUrl("javascript:zoomIN()");

					}
				}
				return true;
			}
		});
	}

	/**
	 * 进口出口总量按钮的切换
	 * 
	 * @param menuView
	 */
	private void jkAndCk(View menuView, JSONArray threeChildsList,
			 final String CurrTitle) {

//		chartArear = (LinearLayout) menuView.findViewById(R.id.chartArear);

		final TextView title = (TextView) menuView.findViewById(R.id.titleStr);
		final ImageView favImg = (ImageView) menuView.findViewById(R.id.favImg);
		final Button allcellName = (Button) menuView.findViewById(R.id.zlbutton);
		final Button cellName1 = (Button) menuView.findViewById(R.id.ckbutton);
		final Button cellName2 = (Button) menuView.findViewById(R.id.jkbutton);
		SharePreferenceUtils spu = new SharePreferenceUtils(context);
		Map<String, Object> favoritesMap =  spu.getSharePrefercncesMap("favorite");
		
		if(favoritesMap.containsKey(CurrTitle)){
			isFavorite = true;
			favImg.setBackgroundResource(R.drawable.fav);
		}else{
			isFavorite = false;
			favImg.setBackgroundResource(R.drawable.nofav);
		}
		
		/**
		 * 添加收藏事件
		 */
		addFavorite = new OnClickListener() {
			@Override
			public void onClick(View v) {
				    SharedPreferences sp = context.getSharedPreferences("favorite",Context.MODE_PRIVATE );
				    Editor editor = sp.edit();
				    editor.putString(CurrTitle,guid );
				    editor.commit();	
				    Toast.makeText(context, "收藏成功", Toast.LENGTH_SHORT).show();
				    favImg.setBackgroundResource(R.drawable.fav);
				    favImg.setOnClickListener(removeFavorite);
					isFavorite = true;
					
					WdscActivity WdscContext = null;
					WdscContext = WdscActivity.getWdscActivityInstance();
					if(WdscContext!=null){
						System.out.println("WdscContext::"+WdscContext);
						WdscContext.menuPublic();
					}
			}
		};
		/**
		 * 移除收藏事件
		 */
		removeFavorite = new OnClickListener() {
			@Override
			public void onClick(View v) {
				    SharedPreferences sp = context.getSharedPreferences("favorite",Context.MODE_PRIVATE );
				    Editor editor = sp.edit();
				    editor.remove(CurrTitle);
				    editor.commit();	
				    Toast.makeText(context, "移除成功", Toast.LENGTH_SHORT).show();
					WdscActivity WdscContext = WdscActivity.getWdscActivityInstance();
					if(WdscContext!=null){
						System.out.println("+++++++++++"+WdscContext.getApplicationInfo());
						WdscContext.menuPublic();
					}
					if(isFromFavorite==false){
						favImg.setOnClickListener(addFavorite);
						//favImg.setText("收藏");
						favImg.setBackgroundResource(R.drawable.nofav);
						isFavorite = false;
					}else{
						//curActivity.finish();
						//final View nocontent = LinearLayout.inflate(curActivity, R.layout.nocontent, null);
						contentLayout.removeAllViews();
						//contentLayout.addView(nocontent, LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
					}
					 
			}
		};
		if(!isFavorite){
			favImg.setOnClickListener(addFavorite);
		}else{
			favImg.setOnClickListener(removeFavorite);
		}
		
		System.out.println("threeChildsList:" + threeChildsList);
		if (threeChildsList != null && threeChildsList.length() > 0) {
			System.out.println("进来:" + CurrTitle);
			try {
				title.setText(CurrTitle);
				for (int i = 0; i < threeChildsList.length(); i++) {
					if (i == 0) {
						cellName1.setText(((JSONObject) threeChildsList.get(i))
								.getString("ZBMC"));
					} else if (i == 1) {
						cellName2.setText(((JSONObject) threeChildsList.get(i))
								.getString("ZBMC"));
					}
				}

			} catch (JSONException e) {
				e.printStackTrace();
			}
			setCellListener(chartArear, cellName1, cellName2, allcellName,
					threeChildsList);
		} else {
			title.setText(CurrTitle);
			allcellName.setVisibility(View.GONE);
			cellName1.setVisibility(View.GONE);
			cellName2.setVisibility(View.GONE);
		}

	}

	private void setCellListener(final LinearLayout layout,
			final Button cellName1, final Button cellName2,
			final Button allcellName, final JSONArray threeChildsList) {
		cellName1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					guid = ((JSONObject) threeChildsList.get(0))
							.getString("GUID");
					getYearTable(menuView);
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				if (btnPostion != 0) {
					cellName1.setBackgroundResource(R.drawable.ck_selector);
					cellName2.setBackgroundResource(R.drawable.jk);
					allcellName.setBackgroundResource(R.drawable.all);
					// layout.setBackgroundDrawable(temp1);
					btnPostion = 0;
					try {
						System.out.println("cell1111111111");
						Tools.createWebView(menuView, R.id.chartArearWeb,
								twoLeavemenuHandler, "initData('','','','"
										+ guid + "')",
								"file:///android_asset/ywl_jg_jckzl_jk_line.html");
					} catch (Exception e) {
						System.out.println("创建失败");
					}
				}
			}
		});
		cellName2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					guid = ((JSONObject) threeChildsList.get(1))
							.getString("GUID");
					getYearTable(menuView);
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				if (btnPostion != 1) {
					cellName1.setBackgroundResource(R.drawable.ck);
					cellName2.setBackgroundResource(R.drawable.jk_selector);
					allcellName.setBackgroundResource(R.drawable.all);
					// layout.setBackgroundDrawable(temp2);
					btnPostion = 1;

					try {
						System.out.println("cell222222222");
						Tools.createWebView(menuView, R.id.chartArearWeb,
								twoLeavemenuHandler, "initData('','','','"
										+ guid + "')",
								"file:///android_asset/ywl_jg_jckzl_ck_bar.html");
					} catch (Exception e) {
						System.out.println("创建失败");
					}

				}
			}
		});
		allcellName.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					guid = ((JSONObject) threeChildsList.get(2))
							.getString("GUID");
					getYearTable(menuView);
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				if (btnPostion != 2) {
					cellName1.setBackgroundResource(R.drawable.ck);
					cellName2.setBackgroundResource(R.drawable.jk);
					allcellName.setBackgroundResource(R.drawable.all_selector);
					// layout.setBackgroundDrawable(temp2);
					btnPostion = 2;
					try {
						System.out
								.println("allcellNameallcellNameallcellNameallcellName");
						Tools.createWebView(menuView, R.id.chartArearWeb,
								twoLeavemenuHandler, "initData('','','','"
										+ guid + "')",
								"file:///android_asset/ywl_jg_jckzl_all.html");
					} catch (Exception e) {
						System.out.println("创建失败");
					}
				}
			}
		});
	}

	/**
	 * 获取年份统计
	 * 
	 * @param menuView
	 */
	private void getYearTable(final View menuView) {
		View right_top = menuView.findViewById(R.id.right_top);
		final ListView table_year_list = (ListView) right_top
				.findViewById(R.id.table_year_list);
		final List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();

		yearHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {

				if (msg.what == 0) {
					System.out.println("msg.what" + msg.what);
					SimpleAdapter tabAdapter = new SimpleAdapter(context, data,
							R.layout.menu_item_table_year, new String[] {
									"YEAR", "COUNT" }, new int[] {
									R.id.table_year, R.id.table_count });
					table_year_list.setAdapter(tabAdapter);
					table_year_list
							.setOnItemClickListener(new OnItemClickListener() {
								@Override
								public void onItemClick(
										AdapterView<?> adapterview, View view,
										int position, long arg3) {
									TextView yearV = (TextView) view.findViewById(R.id.table_year);
									String inYear = (String) yearV.getText();
									dealMonth(menuView, inYear);
								}
							});

					if (data != null && data.size() > 0) {
						String inYear = data.get(0).get("YEAR").toString();
						dealMonth(menuView, inYear);
					}
				}
			}

		};
		yearThread(data);
	}

	/**
	 * 
	 * Description:处理月份数据 Date:2013-7-12
	 * 
	 * @author wangwx
	 * @param menuView
	 * @param inYear
	 * @return void
	 */
	private void dealMonth(final View menuView, String inYear) {
		monthHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 1) {
					System.out.println("处理月份数据");
					monthListAdapter.notifyDataSetChanged();
					monthYearListAdapter.notifyDataSetChanged();
				}
			}
		};
		monthThread(inYear, monthData);
	}

	private void yearThread(final List<Map<String, Object>> data) {
		new Thread() {
			public void run() {
				List<Map<String, Object>> retList = null;
				Webservice webservice = Webservice.getInstance();
				webservice.setMETHOD_NAME("getYwlCountData");
				if (webservice.connect(new String[] { "ZBGUID" },
						new Object[] { guid })) {
					if (webservice.getResult() != null) {
						MyJason myJason = new MyJason(webservice.getResult()
								.toString());
						Log.d("----------数据", webservice.getResult().toString());
						retList = myJason.jasonToList(new String[] { "YEAR",
								"COUNT" });
					}
				}
				System.out.println("retList-----" + retList);
				if (retList != null && retList.size() > 0)
					for (Object obj : retList) {
						data.add((HashMap<String, Object>) obj);
					}
				yearHandler.sendEmptyMessage(0);
			};
		}.start();
	}

	private void monthThread(final String year,
			final List<Map<String, Object>> data) {
		new Thread() {
			public void run() {
				List<Map<String, Object>> retList = null;
				Webservice webservice = Webservice.getInstance();
				webservice.setMETHOD_NAME("getYwl");
				if (webservice.connect(new String[] { "ZBGUID", "ZBMONTH" },
						new Object[] { guid, year })) {
					if (webservice.getResult() != null) {
						MyJason myJason = new MyJason(webservice.getResult()
								.toString());
						Log.d("--数据", webservice.getResult().toString());
						retList = myJason.jasonToList(new String[] {
								"ZB_MONTH", "DY", "DYTB", "YDDY", "YDDYTB" });
					}
				}
				System.out.println("---------月份retList-----" + retList);
				data.clear();
				if (retList != null && retList.size() > 0)
					for (Object obj : retList) {
						data.add((HashMap<String, Object>) obj);
					}
				monthHandler.sendEmptyMessage(1);
			};
		}.start();
	}

}
