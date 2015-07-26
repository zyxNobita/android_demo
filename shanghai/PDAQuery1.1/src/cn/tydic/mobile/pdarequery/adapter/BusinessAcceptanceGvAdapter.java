package cn.tydic.mobile.pdarequery.adapter;

import java.util.List;

import cn.tydic.mobile.pdarequery.R;
import cn.tydic.mobile.pdarequery.entity.Frm_Code;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class BusinessAcceptanceGvAdapter extends BaseAdapter {
	
	private Context context;
	//private String[] gvContent;
	private List<Frm_Code> xmLists;
	private LayoutInflater inflater;
	
	private class ViewHolder {
		TextView tv_checkPro;
	}
	
	public BusinessAcceptanceGvAdapter(Context context,List<Frm_Code> xmLists) {
		super();
		this.context = context;
		this.xmLists=xmLists;
		//this.gvContent = context.getResources().getStringArray(R.array.baGvContent);
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return xmLists.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return xmLists.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.businessacceptance_gv_item, null);
			holder = new ViewHolder();
			holder.tv_checkPro = (TextView) convertView.findViewById(R.id.tv_checkPro);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tv_checkPro.setText(xmLists.get(position).getDmsm1());
		return convertView;
	}

}
