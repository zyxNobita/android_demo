package com.bai.demo.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.bai.demo.R;
import com.bai.demo.bean.CHK_ALC;
import com.bai.demo.entity.Constant;
import com.bai.demo.frame.Menu1Info1Activity;
import com.bai.demo.main.FrameDemoActivity;

import android.content.Context;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
/***
 * 
 * @author Administrator
 *
 */
public class Menu1Info1TopListAdapter extends BaseAdapter {
	private LayoutInflater layoutInflater;
	private List<CHK_ALC> list_data;// 数据集合
	public List<Integer> checkPosition;
	private Context context;
	public HashMap<Integer, Boolean> state = new HashMap<Integer, Boolean>();// 记录CheckBox的状态

	private static class ViewHolder {
		private CheckBox cb_checkBox;
		private TextView tv_orderNumber;
		private TextView tv_customsDeclarationNumber;
		private Button btn_RM1I1_Top_uploadImgs;
		private Button btn_RM1I1_Top_seeCorrelationMsg;
	};

	public Menu1Info1TopListAdapter(List<CHK_ALC> list_data, Context context) {
		this.context = context;
		this.layoutInflater = LayoutInflater.from(context);
		this.list_data = list_data;
		this.checkPosition = new ArrayList<Integer>();
	}

	@Override
	public int getCount() {
		return list_data.size();
	}

	@Override
	public Object getItem(int position) {
		return list_data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public List<CHK_ALC> getMlist() {
		return list_data;
	}

	public void setMlist(List<CHK_ALC> myRecycleBoxList) {
		this.list_data = myRecycleBoxList;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		// 初始化数据
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = this.layoutInflater.inflate(
					R.layout.right_menu1info1_listview_top_items, null);
			holder = new ViewHolder();
			holder.cb_checkBox = (CheckBox) convertView.findViewById(R.id.fxk);
			holder.tv_orderNumber = (TextView) convertView.findViewById(R.id.xh);
			holder.tv_customsDeclarationNumber = (TextView) convertView
					.findViewById(R.id.bgdh);
			holder.btn_RM1I1_Top_seeCorrelationMsg = (Button) convertView
					.findViewById(R.id.btn_RM1I1_Top_seeCorrelationMsg);
			holder.btn_RM1I1_Top_uploadImgs = (Button) convertView
					.findViewById(R.id.btn_RM1I1_Top_uploadImgs);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		final int p=position;
		holder.cb_checkBox.setTag(new Integer(position));
		if (checkPosition != null) {
			holder.cb_checkBox.setChecked((checkPosition.contains(new Integer(
					position)) ? true : false));
		} else {
			holder.cb_checkBox.setChecked(false);
		}
		final CheckBox finalHolder = holder.cb_checkBox;
		holder.cb_checkBox
				.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						if (isChecked) {
							if (!checkPosition.contains(finalHolder.getTag())) {
								checkPosition.add((Integer) finalHolder.getTag());
							}
							state.put((Integer) finalHolder.getTag(), isChecked);
						} else {
							//去掉全选
							//Menu1Info1Activity.cb_RM1I1_LVTop_checked.setChecked(false);
//							Message msg=new Message();
//							msg.arg1=p;//点击的position
//							msg.what=Constant.CB_NOCHECKALL;
//							Menu1Info1Activity.handler_RM1I1.sendMessage(msg);
			
							if (checkPosition.contains(finalHolder.getTag())) {
								checkPosition.remove(finalHolder.getTag());
							}
							state.remove(finalHolder.getTag());
						}
					}
				});
		
		holder.cb_checkBox.setChecked(state.get(position) == null ? false : true);
		
//		holder.cb_checkBox.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				((CheckBox)v).setChecked(false);
//				if (checkPosition.contains(finalHolder.getTag())) {
//					checkPosition.remove(finalHolder.getTag());
//				}
//				state.remove(finalHolder.getTag());
//				
//				Message msg=new Message();
//				msg.arg1=p;//点击的position
//				msg.what=Constant.CB_NOCHECKALL;
//				Menu1Info1Activity.handler_RM1I1.sendMessage(msg);
//				
//			}
//		});
		
		holder.tv_orderNumber.setText(Integer.valueOf(position+1)+"");
		holder.tv_customsDeclarationNumber.setText(list_data.get(position)
				.getENTRY_ID());

		holder.btn_RM1I1_Top_uploadImgs
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						//传报关单号到查验图像上传菜单
						Message msg=new Message();
						msg.what=Constant.RINGHT_GROUP1_MENU3;//判断跳转菜单的位置
						msg.getData().putString("entryId", list_data.get(p).getENTRY_ID()+"");//报关单号
						FrameDemoActivity.handler.sendMessage(msg);
					}
				});
		
		holder.btn_RM1I1_Top_seeCorrelationMsg
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						//传报关单号查验单证查询
						Message msg=new Message();
						msg.what=Constant.RINGHT_GROUP1_MENU2;//判断跳转菜单的位置
						msg.getData().putString("entryNum", list_data.get(p).getENTRY_ID()+"");//报关单号
						FrameDemoActivity.handler.sendMessage(msg);
					}
				});

		return convertView;
	}

}
