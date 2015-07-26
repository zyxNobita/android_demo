package com.tydic.digitalcustom.widget;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.tydic.digitalcustom.R;
import com.tydic.digitalcustom.entity.Constant;
import com.tydic.digitalcustom.entity.MyJason;
import com.tydic.digitalcustom.entity.MyUtil;
import com.tydic.digitalcustom.entity.Webservice;

/**
 * 
 * Description:自定义查询条件对话框 MyDialog.java Create on 2013-7-8 下午4:28:12
 * 
 * @author wangwx
 * @version 1.0 Copyright (c) 2013 Company,Inc. All Rights Reserved.
 */
@SuppressLint("HandlerLeak")
@SuppressWarnings("unused")
public class MyDialog extends AlertDialog {
	
	private static MyDialog myDialog;
	private int year=0;
	private int	month=0;
    private int	day=0;
	private Button btn;
	private int[] dateFromTmp = { 0, 0, 0 }, dateToTmp = { 0, 0, 0 };
	private int dialogChoose;
	private static int styleChoose;//模块栏目 （通关无纸化：查验日报） 
	private boolean[] flags = new boolean[14];
	private String standardDataFrom, standardDataTo;//起始时间，截止时间
	private String items[];
	private String result;
	private String standardResult;//选中的文本结果(海关or进出口)
	private Handler inHandler;
	private int single_choice = 0;// 单选框选项(海关默认第一项，进出口默认全部：第三项)
	private Handler queryHandler;
	private String []queryitem;
	
//	private  SimpleAdapter checkBoxListAdapter =null;
	

	public void setInHandler(Handler inHandler) {
		this.inHandler = inHandler;
	}

	private Context context;
	private TextView dateFrom, dateTo, customsSelector;

	public MyDialog(Context context) {
		super(context);
		this.context = context;
	}

	@SuppressWarnings("static-access")
	public MyDialog(Context context, int theme, int choose) {

		super(context, theme);
		this.context = context;
		this.styleChoose = choose;
		if(styleChoose == Constant.CUSTOM_CYRB){
			single_choice = 2;			
		}
	}

	public static MyDialog getMyDialogInstance(Context context, int theme, int choose){
		if(styleChoose!=choose){
			myDialog = new MyDialog(context,  theme,  choose);
		}else{
			if(myDialog==null){
				myDialog = new MyDialog(context,  theme,  choose);
			}
		}
		return myDialog;
	}
	/**
	 *移除相应Contexg的对象 
	 * Description:
	 * Date:2013-8-4
	 * @author wangwx
	 * @param context
	 * @param theme
	 * @param choose 
	 * @return void
	 */
	public static void removeMyDialogInstance(Context context, int theme, int choose){
		//MyDialog t_myDialog = getMyDialogInstance(context,theme,choose);
		myDialog = null;
	}
	protected Dialog onCreateTimeDialog() {
		if (dialogChoose == Constant.DATE_FROM) {
			return new DatePickerDialog(context, mDateSetListener, year, month,
					day);
		} else if (dialogChoose == Constant.DATE_TO) {
				return new DatePickerDialog(context, mDateSetListener, year, month,day);
		} else {
			return new Dialog(context);
		}

	}

	protected Dialog onCreateInfoDialog() {

		if (styleChoose == Constant.CUSTOM_CYRB) {
			return getCyrb();
		} else if (styleChoose == Constant.CUSTOM_TGWZH) {
			return getTgwzh();
		}
		return null;
	}

	/**
	 * 得到查验日报进出口单选框
	 * 
	 * @return dialog
	 */
	private Dialog getCyrb() {
		items = context.getResources().getStringArray(R.array.customs_inorout);
		return new AlertDialog.Builder(context)
				.setTitle(R.string.inorout_set)
				// 弹出单选框
				.setSingleChoiceItems(R.array.inorout_lst, single_choice,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								single_choice = which;
							}

						})

				.setPositiveButton(R.string.ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								customsSelector.setText(items[single_choice]);
								standardResult = items[single_choice];

							}
						})
				.setNegativeButton(R.string.cancel,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {

							}
						}).create();
	}

	/**
	 * 得到通关无纸化关区多选框
	 * 
	 * @return dialog
	 */
	private Dialog getTgwzh() {
		/*MyTask myTask = new MyTask(checkBoxListAdapter);
		myTask.execute(1000);*/
		
		items = queryitem;
//		flags = new boolean[queryitem.length]; 000
		View view = LinearLayout.inflate(context, R.layout.checkbox, null); 
		CheckBox selAll = (CheckBox) view.findViewById(R.id.selAll);
		final Builder builder = new AlertDialog.Builder(context)		         
		.setCustomTitle(view)
		.setMultiChoiceItems(queryitem, flags,
				new DialogInterface.OnMultiChoiceClickListener() {

					public void onClick(DialogInterface dialog,
							int which, boolean isChecked) {
						flags[which] = isChecked;
					}
				})
		.setPositiveButton(R.string.ok,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,
							int whichButton) {
						result = null;
						for (int i = 0; i < flags.length; i++) {

							if (flags[i]) {
								if (result == null) {
									result = items[i];
								} else {
									result = result + "," + items[i];
								}
							}
						}

						customsSelector.setText(result);
						standardResult = result;

					}
				})
		.setNegativeButton(R.string.cancel,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,
							int whichButton) {

					}
				});
		final AlertDialog adRef =  builder.create();
	
		System.out.println("flags::"+flags.length);
		System.out.println("queryitem::"+queryitem.length);
		int checkNum = 0;
		for(int i=0;i<flags.length;i++){
			if(flags[i]){
				checkNum++;
			}
		}
		if(flags.length!=0&&queryitem.length!=0&&checkNum==queryitem.length){
			selAll.setChecked(true);
		}else{
			selAll.setChecked(false);
		}
/*		selAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				
				for(int i=0;i<flags.length;i++){
					flags[i] = isChecked;
				}
				//builder.notifyAll();
				builder.setMultiChoiceItems(queryitem, flags,
						new DialogInterface.OnMultiChoiceClickListener() {
					public void onClick(DialogInterface dialog,
							int which, boolean isChecked) {
						flags[which] = isChecked;
					}
				}).create();
			}
		});*/
		
		selAll.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				
                ListView lv = adRef.getListView();  
                for (int i = 0; i < lv.getCount(); i++) {  
                    lv.setItemChecked(i, isChecked);  
                }  

                for(int i=0;i<flags.length;i++){
					flags[i] = isChecked;
				}
                
                // setAccessible()方法的解释：  
                // 它提供了将反射的对象标记为在使用时  
                // 取消默认 Java 语言访问控制检查的能力  
		        // 值为true则指示反射的对象在使用时应该取消 Java 语言访问检查。  
		        // 按着反射代码的功能来进行实现  
		        // 值为false则指示反射的对象应该实施Java语言访问检查  
		        //  field.set(adRef, false)的功能是将field字段在adRef对象上  
		        // 设置最新的值，这个值为false  
/*                try {  
                    Field field = adRef.getClass().getSuperclass().getDeclaredField("mShowing");  
                    field.setAccessible(true);  
                    // 将mShowing变量设为false，表示对话框已关闭  
                    field.set(adRef, false);  
                } catch (Exception e) {  

                }  */
			}
		});
		
		return adRef;
	}

	
/*	class  MyCheckBox implements CompoundButton.OnCheckedChangeListener{
		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			buttonView.setChecked(isChecked);
		}
	}*/
	private String[] getCustoms_gq() {
		Webservice webservice = Webservice.getInstance();
		webservice.setMETHOD_NAME("getCustoms_jg");
		boolean resultFlag = webservice.connect(new String[] { },new Object[] {  });
		String menuJson = "";
		List<Map<String,Object>> menuList = new ArrayList<Map<String,Object>>();
		if (resultFlag) {
			if (webservice.getResult() != null) {
				MyJason myJason = new MyJason(webservice.getResult().toString());
				menuJson = webservice.getResult().toString();
				Log.d("-------------getCustoms_jg", webservice.getResult()
						.toString());
				// 关区中文名 关区代码 报关单总量 通关无纸化报关单总量 无纸率
				menuList = myJason.jasonToList(new String[] { "MASTER_CUSTOMS_NAME"});
			}
		}

		String menu[] = null;
		if (menuList != null && menuList.size() > 0) {
			menu = new String[menuList.size()];
			Map<String, Object> aMap = null;
			for (int i = 0; i < menuList.size(); i++) {
				aMap = (HashMap<String, Object>) menuList.get(i);
				menu[i] = aMap.get("MASTER_CUSTOMS_NAME").toString();
			}
		} else {
			menu = new String[]{};
		}
		System.out.println("查询条件："+menu);
		return menu;
	}
	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

		@Override
		public void onDateSet(DatePicker view, int mYear, int monthOfYear,
				int dayOfMonth) {

			year = mYear;
			month = monthOfYear + 1;
			day = dayOfMonth;

			if (dialogChoose == Constant.DATE_FROM) {
				if (standardDataTo != null
						&& !standardDataTo.equals("")
						&& (dateToTmp[0] < year
								|| (dateToTmp[0] == year && dateToTmp[1] < month) || (dateToTmp[0] == year
								&& dateToTmp[1] == month && dateToTmp[2] <= day))) {
					Toast.makeText(context, "起始日期必须小于于结束日期 ",
							Toast.LENGTH_SHORT).show();
				} else {
					dateFromTmp[0] = year;
					dateFromTmp[1] = month;
					dateFromTmp[2] = day;
					//单日前加0 2013-05-02
					if (month < 10 && day < 10) {
						standardDataFrom = year + "-0" + month + "-0" + day;
					} else if (month < 10) {
						standardDataFrom = year + "-0" + month + "-" + day;
					} else if (day < 10) {
						standardDataFrom = year + "-" + month + "-0" + day;
					} else {
						standardDataFrom = year + "-" + month + "-" + day;
					}
					dateFrom.setText(standardDataFrom);
					
				}
			} else if (dialogChoose == Constant.DATE_TO) {
				// 终止日期必须大于起始日期
				if (dateFromTmp[0] > year
						|| (dateFromTmp[0] == year && dateFromTmp[1] > month)
						|| (dateFromTmp[0] == year && dateFromTmp[1] == month && dateFromTmp[2] >= day)) {
					Toast.makeText(context, "结束日期必须大于起始日期 ", Toast.LENGTH_SHORT)
							.show();
				} else {
					dateToTmp[0] = year;
					dateToTmp[1] = month;
					dateToTmp[2] = day;
					//单日前加0 2013-05-02
					if (month < 10 && day < 10) {
						standardDataTo = year + "-0" + month + "-0" + day;
					} else if (month < 10) {
						standardDataTo = year + "-0" + month + "-" + day;
					} else if (day < 10) {
						standardDataTo = year + "-" + month + "-0" + day;
					} else {
						standardDataTo = year + "-" + month + "-" + day;
					}
					dateTo.setText(standardDataTo);
					
				}
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.api_mydialog);
		init();// 初始化组件资源
	}

	private void init() {
		// 时间起止框
		dateFrom = (TextView) findViewById(R.id.date_from_txt_selector);
		dateTo = (TextView) findViewById(R.id.date_to_txt_selector);
		// 关区选择框
		customsSelector = (TextView) findViewById(R.id.guanqu_txt_selector);
		// 关区标题
		TextView customsTitle = (TextView) findViewById(R.id.guanqu_txt);
		if (styleChoose == Constant.CUSTOM_TGWZH) {
			customsTitle.setText(context.getString(R.string.gq));
		} else if (styleChoose == Constant.CUSTOM_CYRB) {
			customsTitle.setText(context.getString(R.string.in_or_out));
		}

		Calendar today = Calendar.getInstance();
		year = today.get(Calendar.YEAR);
		month = today.get(Calendar.MONTH);
		day = today.get(Calendar.DAY_OF_MONTH);

		// 关区 or 进出口初始化
		initGuanqu();

		initDateFrom();

		initDateTo();

		initSearch();
	}

	/**
	 * 
	 * Description:初始化查询按钮 Date:2013-7-8
	 * 
	 * @author wangwx
	 * @return void
	 */
	private void initSearch() {
		btn = (Button) findViewById(R.id.button);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Message msg = new Message();
				msg.arg1 = Constant.CUSTOM_SEARCH;
				msg.arg2 = styleChoose;
				inHandler.sendMessage(msg);
			}
		});
	}

	/**
	 * 
	 * Description:初始化时间止布局 Date:2013-7-8
	 * 
	 * @author wangwx
	 * @return void
	 */
	private void initDateTo() {
		LinearLayout layoutTo = (LinearLayout) findViewById(R.id.date_to_layout);
//		if(styleChoose == Constant.CUSTOM_CYRB){
		TextView date_to_txt_selector = (TextView)layoutTo.findViewById(R.id.date_to_txt_selector);
		String curDate = MyUtil.getCurrentDateFormat("yyyy-MM-dd");
		date_to_txt_selector.setText(curDate);			
//		}
		layoutTo.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dialogChoose = Constant.DATE_TO;
				Dialog dialog = onCreateTimeDialog();
				dialog.show();
			}
		});
		
	}

	/**
	 * 
	 * Description:初始化时间起布局 Date:2013-7-8
	 * 
	 * @author wangwx
	 * @return void
	 */
	private void initDateFrom() {
		LinearLayout layoutFrom = (LinearLayout) findViewById(R.id.date_from_layout);
		TextView date_from_txt_selector = (TextView)layoutFrom.findViewById(R.id.date_from_txt_selector);
		if(styleChoose == Constant.CUSTOM_CYRB){
			//统计日报--开始时间不可变，为每年的1月1日
			date_from_txt_selector.setText(MyUtil.getCurrentDateFormat("yyyy-01-01"));
		}else{
			date_from_txt_selector.setText(MyUtil.getCurrentDateFormat("yyyy-MM-01"));
			layoutFrom.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					dialogChoose = Constant.DATE_FROM;
					Dialog dialog = onCreateTimeDialog();
					dialog.show();
				}
			});
		}
		
	}

	/**
	 * 
	 * Description:初始化关区布局 Date:2013-7-8
	 * 
	 * @author wangwx
	 * @return void
	 */
	private void initGuanqu() {
		LinearLayout layoutCustom = (LinearLayout) findViewById(R.id.guanqu_layout);
		items = context.getResources().getStringArray(R.array.customs_inorout);
		if(styleChoose == Constant.CUSTOM_CYRB){
			//统计日报--开始时间不可变，为每年的1月1日
			TextView guanqu_txt_selector = (TextView)layoutCustom.findViewById(R.id.guanqu_txt_selector);
			guanqu_txt_selector.setText(items[single_choice]);
		}
		
		layoutCustom.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				
				if(styleChoose == Constant.CUSTOM_TGWZH){
					if(queryitem==null||queryitem.length==0){
						CustomsGqMenu menu = new CustomsGqMenu();
						menu.start();
						
						queryHandler = new Handler(){
							public void dispatchMessage(Message msg) {
								switch (msg.what) {
								case Constant.WEBSERVICE_OVER:
									Dialog dialog = onCreateInfoDialog();
									dialog.show();
									break;

								default:
									break;
								}
							};
						};
					}else{
						Dialog dialog = onCreateInfoDialog();
						dialog.show();
					}
					
					
				}else{
					//查验日报
					Dialog dialog = onCreateInfoDialog();
					dialog.show();
				}
			}
		});
	
		
	}

	/**
	 * 获取关区列表
	 * @author wangwx
	 *
	 */
	class CustomsGqMenu extends Thread {
		@Override
		public void run() {
			queryitem = getCustoms_gq();
			flags = new boolean[queryitem.length];
			queryHandler.sendEmptyMessage(Constant.WEBSERVICE_OVER);
		}
	}
	
	public String getStandardDataFrom() {
		return dateFrom.getText().toString();
	}

	public String getStandardDataTo() {
		return dateTo.getText().toString();
	}

	public String getStandardResult() {
		return standardResult;
	}

}
