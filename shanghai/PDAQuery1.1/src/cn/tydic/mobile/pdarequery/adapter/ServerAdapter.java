package cn.tydic.mobile.pdarequery.adapter;

import java.util.ArrayList;

import cn.tydic.mobile.pdarequery.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ServerAdapter extends BaseAdapter {

	/**
	 * 首页服务器地址适配器 服务器的地址固定写死在serverlist中
	 */
	private ArrayList<String> serverlist;
	private Context context;
	private LayoutInflater inflater;

	public ServerAdapter(ArrayList<String> serverlist, Context context) {
		super();
		this.serverlist = serverlist;
		this.context = context;
		this.inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return serverlist.size();
	}

	@Override
	public Object getItem(int position) {
		return serverlist.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.addressitem, null);
			holder = new ViewHolder();
			holder.tv_item = (TextView) convertView
					.findViewById(R.id.tv_server_item);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.tv_item.setText(serverlist.get(position));
		return convertView;
	}

	public class ViewHolder {
		TextView tv_item;
	}

}
