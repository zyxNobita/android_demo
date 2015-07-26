package com.tydic.digitalcustom.widget;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.tydic.digitalcustom.R;
import com.tydic.digitalcustom.entity.MenuSelectManager;
import com.tydic.digitalcustom.entity.MyAdapterForTextview;

/**
 * 利用PopupWin 制作的弹出式菜单 默认包含一个listview（纯文本）顶端可自行设置是否显示查询按钮 <br>
 * <h2>使用方法：</h2>先实例化，再调用show() 弹出菜单点击事件会用handler发出msg<br>
 * 为了判断选择触发事件，需要set 一下回传选择callBackSelect。<br>
 * <h2>例如：</h2>你在listview中第三个item点击触发了popupWin，弹出的子菜单又选择了第四个item
 * ，那么本函数最后传回的msg包含两个arg，第一个为设置的回传选项3（如果未set则为-1），第二个为4<br>
 * 同时方法可以实现listview菜单元素的自适应，宽度为最长长度+5dp（在配置文件中设置popup_menu.xml）
 * 
 * @author baiyin
 * 
 */
public class PopupMenu {

	private Context context;
	private PopupWindow popupWindow,popupImage;
	private View parent, popupWindow_view;
	private String max;
	private int listRes, imageResource;
	private String listViewStr[];//菜单列表
	private String listViewIdStr[];//菜单ID列表
	private ListView listView;
	private Handler rstHandler;
	private int callBackSelect = -1;// 回调选择项
	private int posX, posY;
	public static final int RIGHT = 0x00;
	public static final int DOWN = 0x01;

	/**
	 * 
	 * @param context
	 *            不解释
	 * @param parent
	 *            the parent view which the popupMenu rely
	 * @param listValueRes
	 *            the resource which list will show，like “r.array.lsitRes”
	 */
	public PopupMenu(Context context, View parent, int listValueRes,
			Handler resultHandle, int position) {
		this.context = context;
		this.parent = parent;
		this.listRes = listValueRes;
		this.rstHandler = resultHandle;
		if (position != -1) {
			setCallBackSelect(position);
		}
		getPopupWindow();

	}
	
	/**
	 * 
	 * @param context
	 *            不解释
	 * @param parent
	 *            the parent view which the popupMenu rely
	 * @param menuValues
	 *            菜单名称列表
	 */
	public PopupMenu(Context context, View parent, String [] menuValues,
			Handler resultHandle, int position) {
		this.context = context;
		this.parent = parent;
		this.listViewStr =menuValues;
		this.rstHandler = resultHandle;
		if (position != -1) {
			setCallBackSelect(position);
		}
		getPopupWindow();

	}

	
	/**
	 * 
	 * @param context
	 *            不解释
	 * @param parent
	 *            the parent view which the popupMenu rely
	 * @param menuValues
	 *            菜单名称列表
	 * @param menuIdValues
	 *            菜单ID列表(隐藏)
	 */
	public PopupMenu(Context context, View parent, String [] menuValues,String [] menuIdValues,
			Handler resultHandle, int position) {
		this.context = context;
		this.parent = parent;
		this.listViewStr =menuValues;
		this.listViewIdStr = menuIdValues;
		this.rstHandler = resultHandle;
		if (position != -1) {
			setCallBackSelect(position);
		}
		getPopupWindow();

	}
	/**
	 * 
	 * @param context
	 * @param imageResource
	 */
	public PopupMenu(Context context, int imageResource) {
		this.context = context;
		this.imageResource = imageResource;
		
	}

	public int getCallBackSelect() {
		return callBackSelect;
	}

	public void setCallBackSelect(int callBackSelect) {
		this.callBackSelect = callBackSelect;
	}

	public void setSearchBtnVisible() {
		ImageButton btn = (ImageButton) popupWindow_view
				.findViewById(R.id.popup_btn_search);
		btn.setVisibility(View.VISIBLE);

	}

	/**
	 * @param orientation
	 *            RIGHT,DOWN or others
	 */
	public void show(int orientation) {
		switch (orientation) {
		case RIGHT:
			listView.setBackgroundResource(R.drawable.bg_popup);
			showRight();
			break;
		case DOWN:
			listView.setBackgroundResource(R.drawable.bg_popup_down);
			showDown(0, 0);
			break;

		default:
			showDown(0, 0);
			break;
		}

	}

	/***
	 *    获取PopupWindow实例  
	 */
	private void getPopupWindow() {

		if (null != popupWindow) {
			popupWindow.dismiss();
			return;
		} else {
			initPopuptWindow();
		}
	}

	/**
	 *    创建PopupWindow  
	 */
	protected void initPopuptWindow() {

		LayoutInflater mLayoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		// 获取自定义布局文件pop.xml的视图
		popupWindow_view = mLayoutInflater.inflate(R.layout.popup_menu, null,
				false);
		popupWindow_view.invalidate();

		listView = (ListView) popupWindow_view.findViewById(R.id.popup_list);
		final MyAdapterForTextview textAdapter = new MyAdapterForTextview(context, getData(),listViewIdStr);

		listView.setAdapter(textAdapter);

		listView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				popupWindow.dismiss();
				// 构建选项菜单编号				
				/*rstHandler.sendEmptyMessage(MenuSelectManager.getMenu(
						callBackSelect, textAdapter.getPositon()));*/
				Message message =  new Message();
				if(listViewIdStr!=null&&listViewIdStr.length>0){
					Bundle data = new Bundle();
					data.putString("menuId",listViewIdStr[textAdapter.getPositon()]);				
					data.putInt("position",textAdapter.getPositon());
					message.setData(data);
					rstHandler.sendMessage(message);
				}else{
					//回传menu字符串
					rstHandler.sendEmptyMessage(MenuSelectManager.getMenu(callBackSelect, textAdapter.getPositon()));
				}
			
//				Log.d("-------",
//						"传回的菜单值为："
//								+ String.valueOf(MenuSelectManager.getMenu(
//										callBackSelect,
//										textAdapter.getPositon())));
				return true;
			}
		});

		TextView textView = (TextView) popupWindow_view
				.findViewById(R.id.popup_adapt);
		//撑大popupwindow
		textView.setText(max + max);

		popupWindow = new PopupWindow(popupWindow_view,
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);
		popupWindow.setFocusable(false);

		popupWindow.setOutsideTouchable(true);
		// 点击其他地方消失
		popupWindow_view.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {

				if (popupWindow != null && popupWindow.isShowing()) {
					popupWindow.dismiss();
					popupWindow = null;

				}
				return false;
			}
		});

	}

	private void showRight() {
		popupWindow.setAnimationStyle(R.style.PopupAnimationRight);
		posX = parent.getWidth();
		posY = parent.getHeight();
		popupWindow.showAsDropDown(parent, posX, -posY);

	}

	private void showDown(int x, int y) {
		popupWindow.setAnimationStyle(R.style.PopupAnimationDown);
		popupWindow.showAsDropDown(parent, x, y);

	}

	/**
	 * 从资源文件中获取字符数组
	 * 
	 * @return list
	 */
	private String[] getData() {

		String[] menuData = new String[1];

		System.out.println("listRes()"+listRes);
		System.out.println("listViewStr()"+listViewStr);
		if (listRes != 0) {
			menuData = context.getResources().getStringArray(listRes);
		} else if(listViewStr!=null&&listViewStr.length>0){
			menuData = listViewStr;
		}else{
			menuData[0] = "Please add something into the menuLst！";
			return menuData;
		}

		for (int i = 0; i < menuData.length; i++) {

			// 找到菜单项中最长的一项
			if (max == null) {
				max = menuData[i];
			} else {
				if (max.length() < menuData[i].length()) {
					max = menuData[i];
				}
			}

		}

		return menuData;
	}

}
