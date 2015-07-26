package cn.tydic.mobile.pdarequery.adapter;

import cn.tydic.mobile.pdarequery.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 
 * @author jiangyue 首页GridView的适配器
 * 
 */
public class MainShowAdapter extends BaseAdapter {

	private String[] listName;
	private int[] listPic;
	private Context context;
	private LayoutInflater inflater;

	public MainShowAdapter(Context context) {
		super();
		this.listName=context.getResources().getStringArray(R.array.mainlistname);
		this.listPic = new int[] { R.drawable.icon1, R.drawable.icon2,
				R.drawable.icon3, R.drawable.icon4, R.drawable.icon5,
				R.drawable.icon6 };
		this.context = context;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listName.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return listName[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.main_item, null);
			viewHolder = new ViewHolder();
			viewHolder.iv_show = (ImageView) convertView
					.findViewById(R.id.iv_icon_show);
			viewHolder.tv_name = (TextView) convertView
					.findViewById(R.id.tv_name);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.iv_show.setImageResource(listPic[position]);
		viewHolder.tv_name.setText(listName[position]);
		return convertView;
	}

	class ViewHolder {
		ImageView iv_show;
		TextView tv_name;
	}
}
