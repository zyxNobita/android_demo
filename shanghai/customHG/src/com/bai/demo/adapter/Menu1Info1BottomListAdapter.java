package com.bai.demo.adapter;

import java.util.ArrayList;

import com.bai.demo.R;
import com.bai.demo.bean.EntryHead;
import com.bai.demo.entity.Constant;
import com.bai.demo.main.FrameDemoActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class Menu1Info1BottomListAdapter extends BaseAdapter{

	private LayoutInflater layoutInflater;
	private ArrayList<EntryHead> list_data;
	private Context context;
	public Menu1Info1BottomListAdapter(ArrayList<EntryHead> list_data,Context context){
		this.context=context;
		this.layoutInflater=LayoutInflater.from(context);
		this.list_data=list_data;
	}
	private static class ViewHolder{
		private static TextView tv_orderNumber,customsDeclarationNumber;
		private static Button btn_RM1I1_bottom_uploadImgs,btn_RM1I1_bottom_seeCorrelationMsg,btn_RM1I1_bottom_Delete;
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

	@SuppressWarnings("static-access")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final int  rowId=position;
		ViewHolder holder=null;
		if(convertView==null){
			convertView=this.layoutInflater.inflate(R.layout.right_menu1info1_bottom_item, null);
			holder=new ViewHolder();
			holder.tv_orderNumber=(TextView) convertView.findViewById(R.id.xh);
			holder.customsDeclarationNumber=(TextView) convertView.findViewById(R.id.bgdh);
			holder.btn_RM1I1_bottom_seeCorrelationMsg=(Button) convertView.findViewById(R.id.btn_RM1I1_bottom_seeCorrelationMsg);
			holder.btn_RM1I1_bottom_uploadImgs=(Button) convertView.findViewById(R.id.btn_RM1I1_bottom_uploadImgs);
			holder.btn_RM1I1_bottom_Delete=(Button) convertView.findViewById(R.id.btn_RM1I1_bottom_Delete);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		holder.tv_orderNumber.setText((position+1)+"");
		
		String aa=list_data.get(position).getEntry_ID();
		
		holder.customsDeclarationNumber.setText(list_data.get(position).getEntry_ID());
		
		holder.btn_RM1I1_bottom_uploadImgs.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Message msg=new Message();
				msg.what=Constant.RINGHT_GROUP1_MENU3;//判断跳转菜单的位置
				msg.getData().putString("entryId", list_data.get(rowId).getEntry_ID());//报关单号
				FrameDemoActivity.handler.sendMessage(msg);
			}
		});
		holder.btn_RM1I1_bottom_seeCorrelationMsg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Message msg=new Message();
				msg.what=Constant.RINGHT_GROUP1_MENU2;//判断跳转菜单的位置
				msg.getData().putString("entryNum", list_data.get(rowId).getEntry_ID());//报关单号
				FrameDemoActivity.handler.sendMessage(msg);
			}
		});
		holder.btn_RM1I1_bottom_Delete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//删除 传来的数据，并把SQLite数据删除，提示Dialog形势
				
				AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
				//alertDialog.setTitle(context.getString(R.string.titleMessage));
				alertDialog.setTitle("删除报关单");
				alertDialog.setIcon(R.drawable.ic_launcher);
				alertDialog.setMessage("你确定要删除报关单吗？");
				alertDialog.setPositiveButton(context.getString(R.string.makeSure),
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								FrameDemoActivity.db.deleteForStorage(list_data.get(rowId).getEntry_ID());
								list_data.remove(list_data.get(rowId));
								Menu1Info1BottomListAdapter.this.notifyDataSetChanged();
							}

						});
				
				alertDialog.setNegativeButton(context.getString(R.string.cancel),
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								
							}
						});
				alertDialog.create().show();
			}
		});
		
		return convertView;
	}
}