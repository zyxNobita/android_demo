package cn.tydic.mobile.pdarequery.adapter;

import java.util.List;

import cn.tydic.mobile.pdarequery.R;
import cn.tydic.mobile.pdarequery.activity.BusinessAcceptanceActivity;
import cn.tydic.mobile.pdarequery.entity.Frm_Code;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

/***
 * 查验受理模块------车辆颜色适配器
 * @author zhangyx
 *
 */
public class MyCheckBoxAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private List<Frm_Code> gvLists;
	
	public MyCheckBoxAdapter(Context context,List<Frm_Code> gvLists){
		this.gvLists=gvLists;
		this.inflater=LayoutInflater.from(context);
	}
	public int getCount() {
		// TODO Auto-generated method stub
		return gvLists.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return gvLists.get(position);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@SuppressWarnings({ "unused", "unused", "unused" })
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder=null;
		if(convertView==null){
			convertView=inflater.inflate(R.layout.mycheck_gv_items, null);
			holder=new ViewHolder();
			holder.cb_checkBox=(CheckBox) convertView.findViewById(R.id.cb_checkBox);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		holder.cb_checkBox.setText(gvLists.get(position).getDmsm1());
		holder.cb_checkBox.setChecked(false);
		final int p=position;
		holder.cb_checkBox.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stubcsysMap
				((CheckBox) v).isChecked();
				if(((CheckBox) v).isChecked()){
					BusinessAcceptanceActivity.dataList2.get(p).setPrefix("1");
				}else{
					BusinessAcceptanceActivity.dataList2.get(p).setPrefix("0");
				}
			}
		});
		return convertView;
	}
	
	class ViewHolder{
		private CheckBox cb_checkBox;
	}
}
