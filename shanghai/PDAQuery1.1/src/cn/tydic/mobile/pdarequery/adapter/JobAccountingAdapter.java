package cn.tydic.mobile.pdarequery.adapter;

import java.util.List;

import cn.tydic.mobile.pdarequery.R;
import cn.tydic.mobile.pdarequery.entity.JobAccountingInfo;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class JobAccountingAdapter extends BaseAdapter {
	
	private Context context;
//	private List<Map<String, String>> jobAccountLists;
	private List<JobAccountingInfo> jobAccountLists;
	private LayoutInflater inflater;
	
	private class ViewHolder {
		private TextView tvSerialNum;
		private TextView tvBusiness;
		private TextView tvNum;
		private LinearLayout ll;
	}

//	public JobAccountingAdapter(Context context, List<Map<String, String>> jobAccountLists) {
//		super();
//		this.context = context;
//		this.jobAccountLists = jobAccountLists;
//		this.inflater = LayoutInflater.from(context);
//	}
	
	public JobAccountingAdapter(Context context, List<JobAccountingInfo> jobAccountLists) {
		super();
		this.context = context;
		this.jobAccountLists = jobAccountLists;
		this.inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return jobAccountLists.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return jobAccountLists.get(position);
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
			convertView = inflater.inflate(R.layout.jobaccount_lv_item, null);
			holder = new ViewHolder();
			holder.tvSerialNum = (TextView) convertView.findViewById(R.id.tv_jobAccount_serialNumber);
			holder.tvBusiness = (TextView) convertView.findViewById(R.id.tv_jobAccount_business);
			holder.tvNum = (TextView) convertView.findViewById(R.id.tv_jobAccount_number);
			holder.ll = (LinearLayout) convertView.findViewById(R.id.ll_jobAccount_item);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		if((position%2) == 0){
			holder.ll.setBackgroundColor(0x00000000);
		}else{
			holder.ll.setBackgroundColor(0x10000000);
		}
		
//		holder.tvSerialNum.setText(jobAccountLists.get(position).get("item1"));
//		holder.tvBusiness.setText(jobAccountLists.get(position).get("item2"));
//		holder.tvNum.setText(jobAccountLists.get(position).get("item3"));
		
		holder.tvSerialNum.setText((position + 1) + "");
		holder.tvBusiness.setText(jobAccountLists.get(position).getYwlx());
		holder.tvNum.setText(jobAccountLists.get(position).getYwl());
		return convertView;
	}

}
