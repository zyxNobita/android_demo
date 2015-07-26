package cn.tydic.mobile.pdarequery.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.reflect.TypeToken;

import cn.tydic.mobile.pdarequery.MainActivity;
import cn.tydic.mobile.pdarequery.R;
import cn.tydic.mobile.pdarequery.adapter.QueryChassisAdapter;
import cn.tydic.mobile.pdarequery.entity.ChassisInformation;
import cn.tydic.mobile.pdarequery.tools.Constant;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/***
 * 公告信息查询：底盘-----PDA
 * @author zhangyx
 *
 */
public class QueryNoticeChassisActivity extends Activity {
	private String id=null;//底盘ID
	private int page=0;
	private ListView lv_chassisData;
	public static List<ChassisInformation> chassisLists=new ArrayList<ChassisInformation>();
	private List<ChassisInformation> lists=new ArrayList<ChassisInformation>();
	private QueryChassisAdapter chassisAdapter;
	private String BJ=null;//标志是否最后一页
	public static Integer CHASSIS_INDEX=-1;
	private View footer;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.query_noticechassis);
		initView();
		doExecute();
	}
	
	private void doExecute() {
		// TODO Auto-generated method stub
		reqData(page+"");//第一次请求数据
		
		findViewById(R.id.ll_queryNoticeChassis_return).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
			}
		});
		
		findViewById(R.id.ll_queryNoticeChassis_exit).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				findViewById(R.id.ll_queryNoticeChassis_exit).setBackgroundResource(R.color.dark_blue);
				findViewById(R.id.imgBtn_queryNoticeChassis_close).setBackgroundResource(R.drawable.return_on);
				finish();
				overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
			}
		});
		
		footer.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(BJ!=null && !BJ.equals("")){
					if(BJ.equals("0")){
						page++;
						reqData(page+"");
					}else if(BJ.equals("0")){
						((TextView)footer.findViewById(R.id.tv_lvFooter)).setText("无更多数据加载");
					}
				}
			}
		});
		
		lv_chassisData.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(QueryNoticeChassisActivity.this,NoticeChassisInformationActivity.class);
//				intent.putExtra("CHASSIS_INDEX", arg2);
//				System.out.println("position----"+arg2);
				CHASSIS_INDEX=arg2;
				startActivity(intent);
			}
		});
	}

	private void initView() {
		// TODO Auto-generated method stub
		chassisLists.clear();
		id=getIntent().getStringExtra("dpid");
		System.out.println("底盘ID----"+id);
		lv_chassisData=(ListView) findViewById(R.id.lv_chassisData);
		chassisAdapter=new QueryChassisAdapter(getApplicationContext(), chassisLists);
		footer=LayoutInflater.from(getApplicationContext()).inflate(R.layout.listview_footer, null);
		((TextView)footer.findViewById(R.id.tv_lvFooter)).setText("加载更多数据");
		lv_chassisData.addFooterView(footer);
		lv_chassisData.setAdapter(chassisAdapter);
	}
	
	private void reqData(String pageIndex){
	//　　{‘bh’:’SC120213003277’, ‘yhdh’:tydic’,’phone_id’:’00001’,’page_index’:’0’ }
		if(id!=null && !id.equals("")){
			if (Constant.user != null) {
				Map<String, String> conMap = new HashMap<String, String>();
				conMap.put("dpid", id);// 
				conMap.put("page_index", pageIndex);// 
				conMap.put("yhdh", Constant.user.getUserName());
				conMap.put("phone_id", Constant.user.getPhoneId());
				System.out.println("MainActivity.gson.toJson(conMap)"+MainActivity.gson.toJson(conMap));
				new MyAsyncTaskREQNoticeImages().execute(MainActivity.gson
						.toJson(conMap));
			} else {
				Toast.makeText(getApplicationContext(), "你好，请先登陆。", Toast.LENGTH_SHORT).show();
			}
		}else{
			Toast.makeText(getApplicationContext(), "你好，此底盘ID无底盘信息数据。", Toast.LENGTH_SHORT).show();
		}
	}
	
	/**
	 * 请求 公告信息查询:底盘信息列表
	 * 
	 * @author zhangyx
	 * 
	 */
	class MyAsyncTaskREQNoticeImages extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			MainActivity.tool.startProgressDialog(getApplicationContext());
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String resultStr = "";
			// //接口1：----测试成功
			Constant.webService.setMETHOD_NAME(getApplicationContext()
					.getString(R.string.Connection_MethodName));
			Constant.webService.setWEBSERVICE(getApplicationContext()
					.getString(R.string.Connection_Webservice));
			if (Constant.webService.connect(new String[] { "jkid","userid","authid","QueryXmlDoc" },
					new Object[] { "V-CHASSIS","SHDIC","%96%60%D8%B7%22%A7%E7%16%E1%3B%C7%07%3D+K%17",params[0]})) {
				if (Constant.webService.getResult() != null) {
					resultStr = Constant.webService.getResult().toString();
				}
			}
			return resultStr;
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			System.out.println("返回结果："+result);
			MainActivity.tool.stopProgressDialog();
			if (result != null && !result.equals("")) {
				try {
					JSONObject obj = new JSONObject(result);
					String res = obj.getString("RESULT");
					String data = obj.getString("DATA");
					lists.clear();
					if (res.equals("TRUE")) {
						BJ=obj.getString("BJ");
						lists.addAll((List) MainActivity.gson.fromJson(
								data, new TypeToken<List<ChassisInformation>>() {
								}.getType()));
						showData();
					} else if (res.equals("FALSE")) {
						Toast.makeText(getApplicationContext(), data,
								Toast.LENGTH_LONG).show();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				Toast.makeText(getApplicationContext(), "网络异常或者服务器异常。",
						Toast.LENGTH_LONG).show();
			}
		}
	};
	/**
	 * 显示数据列表
	 */
	private void showData() {
		// TODO Auto-generated method stub
		if(lists.size()>0){
			for(ChassisInformation c:lists){
				chassisLists.add(c);
			}
		}
		chassisAdapter.notifyDataSetChanged();
		footer.setVisibility(View.VISIBLE);
		if(chassisLists.size()<13){
			((TextView)footer.findViewById(R.id.tv_lvFooter)).setText("无更多数据加载");
		}else{
			((TextView)footer.findViewById(R.id.tv_lvFooter)).setText("加载更多数据");
		}
	}
}
