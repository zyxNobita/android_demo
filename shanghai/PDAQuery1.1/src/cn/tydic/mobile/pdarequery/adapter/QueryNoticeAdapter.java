package cn.tydic.mobile.pdarequery.adapter;

import java.util.List;

import cn.tydic.mobile.pdarequery.R;
import cn.tydic.mobile.pdarequery.entity.NoticeInformation;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class QueryNoticeAdapter extends BaseAdapter{

	private List<NoticeInformation> noticeLists;
	private LayoutInflater inflater;
	public QueryNoticeAdapter(Context context,List<NoticeInformation> noticeLists){
		this.inflater=LayoutInflater.from(context);
		this.noticeLists=noticeLists;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return noticeLists.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return noticeLists.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder=null;
		if(convertView==null){
			convertView=inflater.inflate(R.layout.query_notice_lv_item, null);
			holder=new ViewHolder();
			holder.tv_notic1=(TextView) convertView.findViewById(R.id.tv_notice1);
			holder.tv_notic2=(TextView) convertView.findViewById(R.id.tv_notice2);
			holder.tv_notic3=(TextView) convertView.findViewById(R.id.tv_notice3);
			holder.tv_notic4=(TextView) convertView.findViewById(R.id.tv_notice4);
			holder.tv_notic5=(TextView) convertView.findViewById(R.id.tv_notice5);
			holder.tv_notic6=(TextView) convertView.findViewById(R.id.tv_notice6);
			holder.tv_notic7=(TextView) convertView.findViewById(R.id.tv_notice7);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		holder.tv_notic1.setText(noticeLists.get(position).getClpp1());
		holder.tv_notic2.setText(noticeLists.get(position).getClxh());
		holder.tv_notic3.setText(noticeLists.get(position).getFdjxh());
		holder.tv_notic4.setText(noticeLists.get(position).getSbdhxl());
		holder.tv_notic5.setText(noticeLists.get(position).getCllx());
		holder.tv_notic6.setText(noticeLists.get(position).getGgrq());
		holder.tv_notic7.setText(noticeLists.get(position).getSfyxzc());
		return convertView;
	}
	
	class ViewHolder{
		private TextView tv_notic1,tv_notic2,tv_notic3,tv_notic4,tv_notic5,tv_notic6,tv_notic7;
	}
}
