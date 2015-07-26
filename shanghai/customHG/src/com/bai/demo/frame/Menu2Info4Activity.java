package com.bai.demo.frame;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bai.demo.R;
import com.bai.demo.adapter.Menu2Info4LeftFirstListViewAdapter;
import com.bai.demo.adapter.Menu2Info4LeftForthListViewAdapter;
import com.bai.demo.adapter.Menu2Info4LeftSecondListViewAdapter;
import com.bai.demo.adapter.Menu2Info4LeftThirdListViewAdapter;
import com.bai.demo.adapter.Menu2Info4RightFirstListViewAdapter;
import com.bai.demo.adapter.Menu2Info4RightForthListViewAdapter;
import com.bai.demo.adapter.Menu2Info4RightSecondListViewAdapter;
import com.bai.demo.adapter.Menu2Info4RightThirdListViewAdapter;
import com.bai.demo.bean.COMPLEX;
import com.bai.demo.bean.G_LIST;
import com.bai.demo.bean.G_RSK_LIST;
import com.bai.demo.bean.HS_RSK_REL;
import com.bai.demo.entity.Constant;
import com.bai.demo.main.FrameDemoActivity;
import com.bai.demo.utils.MyListView;
import com.google.gson.reflect.TypeToken;

/**
 * 查询信息知识库
 * @author soft1_developer1
 *
 */
public class Menu2Info4Activity extends RightWindowBase{
	
	private EditText et_RM2I4_manifestNum, et_RM2I4_goodsName, et_RM2I4_goodsTaxNum;//报关单号|商品品名|商品税号
	private Spinner sp_RM2I4_score;//分数
	private ImageButton ib_RM2I4PageOne,ib_RM2I4PageTwo,ib_RM2I4PageThree,ib_RM2I4PageFour,ib_RM2I4PageFive;//报关单表体查看的图片
	private TextView tv_RM2I4PageOne,tv_RM2I4PageTwo,tv_RM2I4PageThree, tv_RM2I4PageFour;//分类标题头
	private LinearLayout layout, ll_RM2I4_lv_main_leftFirst, ll_RM2I4_lv_main_rightFirst,
	ll_RM2I4_lv_main_leftSecond, ll_RM2I4_lv_main_rightSecond,
	ll_RM2I4_lv_main_leftThird, ll_RM2I4_lv_main_rightThird,
	ll_RM2I4_lv_main_leftForth, ll_RM2I4_lv_main_rightForth;//控制ListView的容器
	private MyListView mlv_RM2I4_leftLVFirst, mlv_RM2I4_rightLVFirst,
	mlv_RM2I4_leftLVSecond, mlv_RM2I4_rightLVSecond,
	mlv_RM2I4_leftLVThird, mlv_RM2I4_rightLVThird,
	mlv_RM2I4_leftLVForth, mlv_RM2I4_rightLVForth;//自定义Listview 
	private Button btn_RM2I4_search, btn_RM2I4_refurbish;//查询|刷新
	private View leftViewFirst, rightViewFirst, leftViewSecond, rightViewSecond, leftViewThird, rightViewThird, leftViewForth, rightViewForth;
    private List<G_LIST> typicalGoodsInfoList = new ArrayList<G_LIST>();//典型查询商品信息
    private List<G_RSK_LIST> goodsRiskList = new ArrayList<G_RSK_LIST>();//查询风险要点
    private List<COMPLEX> goodsConditionList = new ArrayList<COMPLEX>();//监管条件
    private List<HS_RSK_REL> standardGoodsInfoList = new ArrayList<HS_RSK_REL>();//标准化商品信息
	//分页请求的参数
	private Integer page1=0,page2=0,page3=0,page4=0;//上一页、下一页
	private TextView tv_pageAgo,tv_pageNext;//分页按钮
	public Menu2Info4Activity(Context context){
		super(context);
		setupViews();
	}
	
	public Menu2Info4Activity(Context context, AttributeSet attrs) {
		super(context, attrs);
		setupViews();
	}
	
	private void setupViews(){
		
		layout = (LinearLayout) LinearLayout.inflate(getContext(),
				R.layout.right_menu2info4, null);
		
		initView();
		
		LinearLayout ll_RM2I4_one=(LinearLayout) layout.findViewById(R.id.ll_rightMenu2Info4ChangeOne);
		LinearLayout ll_RM2I4_two=(LinearLayout) layout.findViewById(R.id.ll_rightMenu2Info4ChangeTwo);
		LinearLayout ll_RM2I4_three=(LinearLayout) layout.findViewById(R.id.ll_rightMenu2Info4ChangeThree);
		LinearLayout ll_RM2I4_four=(LinearLayout) layout.findViewById(R.id.ll_rightMenu2Info4ChangeFour);
		
		initPageView(ll_RM2I4_one,ll_RM2I4_two,ll_RM2I4_three,ll_RM2I4_four);
		doRM2I4LoadData(ll_RM2I4_one,ll_RM2I4_two,ll_RM2I4_three,ll_RM2I4_four);
		
		addView(layout);
		doExecute();
		
	}
	//初始化控件
	private void initPageView(LinearLayout ll_RM2I4_one,LinearLayout ll_RM2I4_two,LinearLayout ll_RM2I4_three,LinearLayout ll_RM2I4_four){
		
		ib_RM2I4PageOne=(ImageButton) ll_RM2I4_one.findViewById(R.id.ib_pageTab1);
		tv_RM2I4PageOne=(TextView)ll_RM2I4_one.findViewById(R.id.tv_RM2I4Tab1);
		ib_RM2I4PageTwo=(ImageButton) ll_RM2I4_two.findViewById(R.id.ib_pageTab2);
		tv_RM2I4PageTwo=(TextView)ll_RM2I4_two.findViewById(R.id.tv_RM2I4Tab2);
		ib_RM2I4PageThree=(ImageButton) ll_RM2I4_three.findViewById(R.id.ib_pageTab3);
		tv_RM2I4PageThree=(TextView)ll_RM2I4_three.findViewById(R.id.tv_RM2I4Tab3);
		ib_RM2I4PageFour=(ImageButton) ll_RM2I4_four.findViewById(R.id.ib_pageTab4);
		ib_RM2I4PageFive=(ImageButton)ll_RM2I4_four.findViewById(R.id.ib_pageTab5);
		tv_RM2I4PageFour=(TextView)ll_RM2I4_four.findViewById(R.id.tv_RM2I4Tab4);
		
	}
	
	private void initView(){
		//参数初始化
		et_RM2I4_manifestNum = (EditText) layout.findViewById(R.id.et_menu2Info4_number);
		et_RM2I4_goodsName = (EditText) layout.findViewById(R.id.et_menu2Info4_goodsName);
		et_RM2I4_goodsTaxNum = (EditText) layout.findViewById(R.id.et_menu2Info4_goodsDutyNumber);
		sp_RM2I4_score=(Spinner) layout.findViewById(R.id.sp_RM2I4_score);
		btn_RM2I4_search=(Button) layout.findViewById(R.id.btn_menu2Info4_submit);
			
		tv_pageAgo=(TextView) layout.findViewById(R.id.tv_pageAgo);
		tv_pageNext=(TextView) layout.findViewById(R.id.tv_pageNext);
		
		ll_RM2I4_lv_main_leftFirst = (LinearLayout) layout.findViewById(R.id.ll_RM2I4_lv_main_leftFirst);
		ll_RM2I4_lv_main_rightFirst = (LinearLayout) layout.findViewById(R.id.ll_RM2I4_lv_main_rightFirst);
		leftViewFirst = LayoutInflater.from(getContext()).inflate(
				R.layout.right_menu2info4_listview_first_left_layout, null);
		rightViewFirst = LayoutInflater.from(getContext()).inflate(
				R.layout.right_menu2info4_listview_first_right_layout, null);
		mlv_RM2I4_leftLVFirst = (MyListView) leftViewFirst
				.findViewById(R.id.lv_RM2I4_left_layout_listViewFirst);
		mlv_RM2I4_rightLVFirst = (MyListView) rightViewFirst
				.findViewById(R.id.lv_RM2I4_right_layout_listViewFirst);
		
		ll_RM2I4_lv_main_leftSecond = (LinearLayout) layout.findViewById(R.id.ll_RM2I4_lv_main_leftSecond);
		ll_RM2I4_lv_main_rightSecond = (LinearLayout) layout.findViewById(R.id.ll_RM2I4_lv_main_rightSecond);
		leftViewSecond = LayoutInflater.from(getContext()).inflate(
				R.layout.right_menu2info4_listview_second_left_layout, null);
		rightViewSecond = LayoutInflater.from(getContext()).inflate(
				R.layout.right_menu2info4_listview_second_right_layout, null);		
		mlv_RM2I4_leftLVSecond = (MyListView) leftViewSecond
				.findViewById(R.id.lv_RM2I4_left_layout_listViewSecond);
		mlv_RM2I4_rightLVSecond = (MyListView) rightViewSecond
				.findViewById(R.id.lv_RM2I4_right_layout_listViewSecond);
		
		ll_RM2I4_lv_main_leftThird = (LinearLayout) layout.findViewById(R.id.ll_RM2I4_lv_main_leftThird);
		ll_RM2I4_lv_main_rightThird = (LinearLayout) layout.findViewById(R.id.ll_RM2I4_lv_main_rightThird);
		leftViewThird = LayoutInflater.from(getContext()).inflate(
				R.layout.right_menu2info4_listview_third_left_layout, null);
		rightViewThird = LayoutInflater.from(getContext()).inflate(
				R.layout.right_menu2info4_listview_third_right_layout, null);
		mlv_RM2I4_leftLVThird = (MyListView) leftViewThird
				.findViewById(R.id.lv_RM2I4_left_layout_listViewThird);
		mlv_RM2I4_rightLVThird = (MyListView) rightViewThird
				.findViewById(R.id.lv_RM2I4_right_layout_listViewThird);
		
		ll_RM2I4_lv_main_leftForth = (LinearLayout) layout.findViewById(R.id.ll_RM2I4_lv_main_leftForth);
		ll_RM2I4_lv_main_rightForth = (LinearLayout) layout.findViewById(R.id.ll_RM2I4_lv_main_rightForth);
		leftViewForth = LayoutInflater.from(getContext()).inflate(
				R.layout.right_menu2info4_listview_forth_left_layout, null);
		rightViewForth = LayoutInflater.from(getContext()).inflate(
				R.layout.right_menu2info4_listview_forth_right_layout, null);
		mlv_RM2I4_leftLVForth = (MyListView) leftViewForth
				.findViewById(R.id.lv_RM2I4_left_layout_listViewForth);
		mlv_RM2I4_rightLVForth = (MyListView) rightViewForth
				.findViewById(R.id.lv_RM2I4_right_layout_listViewForth);
		
		btn_RM2I4_refurbish=(Button) layout.findViewById(R.id.btn_RM2I4_refurbish);
	
		ll_RM2I4_one=(LinearLayout) layout.findViewById(R.id.ll_RM2I4_lv_one);
		ll_RM2I4_two=(LinearLayout) layout.findViewById(R.id.ll_RM2I4_lv_two);
		ll_RM2I4_three=(LinearLayout) layout.findViewById(R.id.ll_RM2I4_lv_three);
		ll_RM2I4_four=(LinearLayout) layout.findViewById(R.id.ll_RM2I4_lv_four);
		
	}
	
	private void doExecute(){
		//分数
		sp_RM2I4_score.setPrompt(getContext().getString(R.string.sp_RM2I4_score));
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.sp_scoreItemsText));
		arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);   
		sp_RM2I4_score.setAdapter(arrayAdapter);
		sp_RM2I4_score.setSelection(0, true);
		sp_RM2I4_score.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){

	           public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3){
	               arg0.setVisibility(View.VISIBLE);
	           }

	           public void onNothingSelected(AdapterView<?> arg0){
	           }

	       });
		//查询
		btn_RM2I4_search.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				reqAllDataByWhere("all","1");
			}});
		
		btn_RM2I4_refurbish.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(getContext(), "刷新数据中。。。。。。", Toast.LENGTH_SHORT).show();
				Animation rotateAnimation=AnimationUtils.loadAnimation(getContext(), R.anim.rotate);
				btn_RM2I4_refurbish.setAnimation(rotateAnimation);
			}
		});
	
		tv_pageNext.setOnClickListener(new PageOnClickListener());
		tv_pageAgo.setOnClickListener(new PageOnClickListener());
	}
	//请求后台数据
	private void reqAllDataByWhere(String reqType,String page){
		String score = sp_RM2I4_score.getSelectedItem().toString();//分数
		String compareString = getResources().getString(R.string.compareString);
		//参数
		String entryId=et_RM2I4_manifestNum.getText().toString();//报关单号
		String gName=et_RM2I4_goodsName.getText().toString();//商品名称
		String gTaxNum=et_RM2I4_goodsTaxNum.getText().toString();//商品税号
		if(score.compareTo(compareString) >= 0){
			if(reqType!=null && !reqType.equals("")){
				if(reqType.equals("1")){//分页的操作
					new MyAsynTaskOne().execute(page, Constant.PAGESIZE+"", entryId,gName,gTaxNum,score);//典型查获商品信息
				}else if(reqType.equals("2")){
					new MyAsynTaskTwo().execute(page, Constant.PAGESIZE+"", gName,gTaxNum);//查验风险要点
				}else if(reqType.equals("3")){
					new MyAsynTaskThree().execute(page, Constant.PAGESIZE+"", gName,gTaxNum);//
				}else if(reqType.equals("4")){
					new MyAsynTaskFour().execute(page, Constant.PAGESIZE+"", gName,gTaxNum);
				}else{//不属于分页
					new MyAsynTaskOne().execute("1", Constant.PAGESIZE+"", entryId,gName,gTaxNum,score);//典型查获商品信息
					new MyAsynTaskTwo().execute("1", Constant.PAGESIZE+"", gName,gTaxNum);//查验风险要点
					new MyAsynTaskThree().execute("1", Constant.PAGESIZE+"", gName,gTaxNum);//
					new MyAsynTaskFour().execute("1", Constant.PAGESIZE+"", gName,gTaxNum);
				}
			}
		} else {
			FrameDemoActivity.toolUtils.promptMessage("请选择分数大于3的数才可查询！！！");
		}
	}
	//分页按钮
	private class PageOnClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			int viewId=v.getId();
			switch (viewId) {
				case R.id.tv_pageAgo:
					//reqAllDataByWhere(String reqType,String page);
					if(ll_RM2I4_one.getVisibility()==View.VISIBLE){//第一个
						if(page1==0){
							page1=0;
						}else{
							page1--;
						}
						reqAllDataByWhere("1",page1+"");
					}else if(ll_RM2I4_two.getVisibility()==View.VISIBLE){//第二个
						if(page2==0){
							page2=0;
						}else{
							page2--;
						}
						reqAllDataByWhere("2",page2+"");
					}else if(ll_RM2I4_three.getVisibility()==View.VISIBLE){//第二个
						if(page3==0){
							page3=0;
						}else{
							page3--;
						}
						reqAllDataByWhere("3",page3+"");
					}else if(ll_RM2I4_four.getVisibility()==View.VISIBLE){//第二个
						if(page4==0){
							page4=0;
						}else{
							page4--;
						}
						reqAllDataByWhere("4",page4+"");
					}
					break;
				case R.id.tv_pageNext:
					if(ll_RM2I4_one.getVisibility()==View.VISIBLE){//第一个
						if(page1>=0){
							page1++;
						}
						reqAllDataByWhere("1",page1+"");
					}else if(ll_RM2I4_two.getVisibility()==View.VISIBLE){//第二个
						if(page2>=0){
							page2++;
						}
						reqAllDataByWhere("2",page2+"");
					}else if(ll_RM2I4_three.getVisibility()==View.VISIBLE){//第二个
						if(page3>=0){
							page3++;
						}
						reqAllDataByWhere("3",page3+"");
					}else if(ll_RM2I4_four.getVisibility()==View.VISIBLE){//第二个
						if(page4>=0){
							page4++;
						}
						reqAllDataByWhere("4",page4+"");
					}
					break;
			}
		}
		
	}
	
	private LinearLayout ll_RM2I4_one,ll_RM2I4_two,ll_RM2I4_three,ll_RM2I4_four;
	//ListView的容器 切换
	private class BtnRM2I4_OnClick implements OnClickListener{
		
		@Override
		public void onClick(View v) {
			int btn_RM2I4_Id=v.getId();		
			switch (btn_RM2I4_Id) {
				case R.id.ll_rightMenu2Info4ChangeOne:
					ll_RM2I4_one.setVisibility(View.VISIBLE);
					ll_RM2I4_two.setVisibility(View.GONE);
					ll_RM2I4_three.setVisibility(View.GONE);
					ll_RM2I4_four.setVisibility(View.GONE);
					ib_RM2I4PageOne.setBackgroundResource(R.drawable.left2);
					tv_RM2I4PageOne.setBackgroundResource(R.drawable.leftcontent2);
					ib_RM2I4PageTwo.setBackgroundResource(R.drawable.left3);
					tv_RM2I4PageTwo.setBackgroundResource(R.drawable.leftcontent_1);
					ib_RM2I4PageThree.setBackgroundResource(R.drawable.left3);
					tv_RM2I4PageThree.setBackgroundResource(R.drawable.leftcontent_1);
					ib_RM2I4PageFour.setBackgroundResource(R.drawable.left3);
					ib_RM2I4PageFive.setBackgroundResource(R.drawable.right1);
					tv_RM2I4PageFour.setBackgroundResource(R.drawable.leftcontent_1);
					
					break;
				case R.id.ll_rightMenu2Info4ChangeTwo:
					ll_RM2I4_one.setVisibility(View.GONE);
					ll_RM2I4_two.setVisibility(View.VISIBLE);
					ll_RM2I4_three.setVisibility(View.GONE);
					ll_RM2I4_four.setVisibility(View.GONE);
					ib_RM2I4PageOne.setBackgroundResource(R.drawable.left1);
					tv_RM2I4PageOne.setBackgroundResource(R.drawable.leftcontent_1);
					ib_RM2I4PageTwo.setBackgroundResource(R.drawable.left);
					tv_RM2I4PageTwo.setBackgroundResource(R.drawable.leftcontent2);
					ib_RM2I4PageThree.setBackgroundResource(R.drawable.left3);
					tv_RM2I4PageThree.setBackgroundResource(R.drawable.leftcontent_1);
					ib_RM2I4PageFour.setBackgroundResource(R.drawable.left3);
					ib_RM2I4PageFive.setBackgroundResource(R.drawable.right1);
					tv_RM2I4PageFour.setBackgroundResource(R.drawable.leftcontent_1);
					
					break;
				case R.id.ll_rightMenu2Info4ChangeThree:
					ll_RM2I4_one.setVisibility(View.GONE);
					ll_RM2I4_two.setVisibility(View.GONE);
					ll_RM2I4_three.setVisibility(View.VISIBLE);
					ll_RM2I4_four.setVisibility(View.GONE);
					ib_RM2I4PageOne.setBackgroundResource(R.drawable.left1);
					tv_RM2I4PageOne.setBackgroundResource(R.drawable.leftcontent_1);
					ib_RM2I4PageTwo.setBackgroundResource(R.drawable.left3);
					tv_RM2I4PageTwo.setBackgroundResource(R.drawable.leftcontent_1);
					ib_RM2I4PageThree.setBackgroundResource(R.drawable.left);
					tv_RM2I4PageThree.setBackgroundResource(R.drawable.leftcontent2);
					ib_RM2I4PageFour.setBackgroundResource(R.drawable.left3);
					ib_RM2I4PageFive.setBackgroundResource(R.drawable.right1);
					tv_RM2I4PageFour.setBackgroundResource(R.drawable.leftcontent_1);
					
					break;
				case R.id.ll_rightMenu2Info4ChangeFour:
					ll_RM2I4_one.setVisibility(View.GONE);
					ll_RM2I4_two.setVisibility(View.GONE);
					ll_RM2I4_three.setVisibility(View.GONE);
					ll_RM2I4_four.setVisibility(View.VISIBLE);
					ib_RM2I4PageOne.setBackgroundResource(R.drawable.left1);
					tv_RM2I4PageOne.setBackgroundResource(R.drawable.leftcontent_1);
					ib_RM2I4PageTwo.setBackgroundResource(R.drawable.left3);
					tv_RM2I4PageTwo.setBackgroundResource(R.drawable.leftcontent_1);
					ib_RM2I4PageThree.setBackgroundResource(R.drawable.left3);
					tv_RM2I4PageThree.setBackgroundResource(R.drawable.leftcontent_1);
					ib_RM2I4PageFour.setBackgroundResource(R.drawable.left);
					ib_RM2I4PageFive.setBackgroundResource(R.drawable.right2);
					tv_RM2I4PageFour.setBackgroundResource(R.drawable.leftcontent2);
					
					break;

			}
		}
	};
	//ListView 的初始化
	private void doRM2I4LoadData(LinearLayout ll_RM2I4_one,LinearLayout ll_RM2I4_two,LinearLayout ll_RM2I4_three,LinearLayout ll_RM2I4_four){
		initListViewToAdapter();//初始化所有的ListView---Adapter
		addFirstListView(leftViewFirst, rightViewFirst);
		addSecondListView(leftViewSecond, rightViewSecond);
		addThirdListView(leftViewThird, rightViewThird);
		addForthListView(leftViewForth, rightViewForth);
		
	    ll_RM2I4_one.setOnClickListener(new BtnRM2I4_OnClick());
		ll_RM2I4_two.setOnClickListener(new BtnRM2I4_OnClick());
		ll_RM2I4_three.setOnClickListener(new BtnRM2I4_OnClick());
		ll_RM2I4_four.setOnClickListener(new BtnRM2I4_OnClick());
	}
	
	private void addFirstListView(View leftView, View rightView) {
		mlv_RM2I4_leftLVFirst.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				mlv_RM2I4_rightLVFirst.onTouchEvent(event);
				return false;
			}
		});

		mlv_RM2I4_rightLVFirst.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {

				mlv_RM2I4_leftLVFirst.dispatchTouchEvent(event);
				return false;
			}
		});

		ll_RM2I4_lv_main_leftFirst.addView(leftView);
		ll_RM2I4_lv_main_rightFirst.addView(rightView);
	}
	
	private void addSecondListView(View leftView, View rightView) {
		mlv_RM2I4_leftLVSecond.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				mlv_RM2I4_rightLVSecond.onTouchEvent(event);
				return false;
			}
		});

		mlv_RM2I4_rightLVSecond.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {

				mlv_RM2I4_leftLVSecond.dispatchTouchEvent(event);
				return false;
			}
		});

		ll_RM2I4_lv_main_leftSecond.addView(leftView);
		ll_RM2I4_lv_main_rightSecond.addView(rightView);
	}
	
	private void addThirdListView(View leftView, View rightView) {
		mlv_RM2I4_leftLVThird.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				mlv_RM2I4_rightLVThird.onTouchEvent(event);
				return false;
			}
		});

		mlv_RM2I4_rightLVThird.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {

				mlv_RM2I4_leftLVThird.dispatchTouchEvent(event);
				return false;
			}
		});

		ll_RM2I4_lv_main_leftThird.addView(leftView);
		ll_RM2I4_lv_main_rightThird.addView(rightView);
	}
	
	private void addForthListView(View leftView, View rightView) {
		mlv_RM2I4_leftLVForth.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				mlv_RM2I4_rightLVForth.onTouchEvent(event);
				return false;
			}
		});

		mlv_RM2I4_rightLVForth.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {

				mlv_RM2I4_leftLVForth.dispatchTouchEvent(event);
				return false;
			}
		});

		ll_RM2I4_lv_main_leftForth.addView(leftView);
		ll_RM2I4_lv_main_rightForth.addView(rightView);
	}
	
	@Override
	public void dosomething() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dosomething2() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 辅助决策支持--查验知识库-典型查获商品信息
	 * @author ouyyt
	 *
	 */
	class MyAsynTaskOne extends AsyncTask<String, String, String> {
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			FrameDemoActivity.toolUtils.endProgressDialog();
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			// 获取典型查询商品信息
			String reqResult = "";
			FrameDemoActivity.webservice.setMETHOD_NAME("GetGList");
			FrameDemoActivity.webservice.setSOAP_ACTION("http://tempuri.org/IGetExamDataService/GetGList");
			if (FrameDemoActivity.webservice.connect(getContext(),new String[] { "pageIndex", "pageSize", "entryId","gName","codeTs","starNum"},
					new Object[] { params[0], params[1], params[2],params[3],params[4],params[5] })) {
				reqResult = FrameDemoActivity.webservice.getResult().toString();
				System.out.println("典型查询商品信息列表：" + reqResult);
				//添加Log日志
				if(reqResult!=null && !reqResult.equals("")){
					FrameDemoActivity.toolUtils.writeDataLog("查询典型商品信息成功。", "85", "");
				}
			}
			return reqResult;
		}
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			FrameDemoActivity.toolUtils.endProgressDialog();
			if (result.equals("")) {
				page1--;
				FrameDemoActivity.toolUtils.promptMessage("对不起，不存在该商品信息！！！");
			} else if (result != null && !result.equals("")) {
				System.out.println("典型查询商品信息列表：" + result);
				typicalGoodsInfoList.clear();
				typicalGoodsInfoList.addAll((List)FrameDemoActivity.gson.fromJson(result, new TypeToken<List<G_LIST>>() {}.getType()));
				m2i4LeftFAdapter.notifyDataSetChanged();
				m2i4RightFAdapter.notifyDataSetChanged();
			}
		}
		
	};
	
	/**
	 * 辅助决策支持--查验知识库-查验风险要点
	 * @author ouyyt
	 *
	 */
    class MyAsynTaskTwo extends AsyncTask<String, String, String> {
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			FrameDemoActivity.toolUtils.startProgressDialog();
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			// 获取商品风险要点信息
			String reqResult = "";
			FrameDemoActivity.webservice.setMETHOD_NAME("GetGRskList");
			FrameDemoActivity.webservice.setSOAP_ACTION("http://tempuri.org/IGetExamDataService/GetGRskList");
			if (FrameDemoActivity.webservice.connect(getContext(),new String[] { "pageIndex", "pageSize", "gName","codeTs"},
					new Object[] { params[0], params[1], params[2],params[3] })) {
				reqResult = FrameDemoActivity.webservice.getResult().toString();
				//添加Log日志
				if(reqResult!=null && !reqResult.equals("")){
					FrameDemoActivity.toolUtils.writeDataLog("查询查验风险要点成功。", "85", "");
				}
				System.out.println("查询风险要点列表：" + reqResult);
			}
			return reqResult;
		}
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result.equals("")) {
				page2--;
				FrameDemoActivity.toolUtils.promptMessage("对不起，不存在该商品的风险要点信息！！！");
			} else if (result != null && !result.equals("")) {
				System.out.println("查询风险要点列表：" + result);
				goodsRiskList.clear();
				goodsRiskList.addAll((List)FrameDemoActivity.gson.fromJson(result, new TypeToken<List<G_RSK_LIST>>() {}.getType()));
				//goodsRiskList=FrameDemoActivity.gson.fromJson(result, new TypeToken<List<G_RSK_LIST>>() {}.getType());
				m2i4LeftSAdapter.notifyDataSetChanged();
				m2i4RightSAdapter.notifyDataSetChanged();
			}
			FrameDemoActivity.toolUtils.endProgressDialog();
		}
		
	};
	
	/**
	 * 辅助决策支持--查验知识库-监管条件
	 * @author ouyyt
	 *
	 */
    class MyAsynTaskThree extends AsyncTask<String, String, String> {
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			FrameDemoActivity.toolUtils.startProgressDialog();
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			// 获取商品监管条件信息
			String reqResult = "";
			FrameDemoActivity.webservice.setMETHOD_NAME("GetAutoQuery");
			FrameDemoActivity.webservice.setSOAP_ACTION("http://tempuri.org/IGetExamDataService/GetAutoQuery");
			if (FrameDemoActivity.webservice.connect(getContext(),new String[] { "pageIndex", "pageSize", "gName","codeTs"},
					new Object[] { params[0], params[1], params[2],params[3] })) {
				reqResult = FrameDemoActivity.webservice.getResult().toString();
				System.out.println("监管条件列表：" + reqResult);
				//添加Log日志
				if(reqResult!=null && !reqResult.equals("")){
					FrameDemoActivity.toolUtils.writeDataLog("查询监管条件成功。", "85", "");
				}
			}
			return reqResult;
		}
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result.equals("")) {
				page3--;
				FrameDemoActivity.toolUtils.promptMessage("对不起，不存在该商品的监管条件信息！！！");
			} else if (result != null && !result.equals("")) {
				System.out.println("监管条件列表：" + result);
				goodsConditionList.clear();
				goodsConditionList.addAll((List)FrameDemoActivity.gson.fromJson(result, new TypeToken<List<COMPLEX>>() {}.getType()));
				m2i4LeftTAdapter.notifyDataSetChanged();
				m2i4RightAdapter.notifyDataSetChanged();
			}
			FrameDemoActivity.toolUtils.endProgressDialog();
		}
		
	};
	
	/**
	 * 辅助决策支持--查验知识库-标准化商品信息
	 * @author ouyyt
	 *
	 */
    class MyAsynTaskFour extends AsyncTask<String, String, String> {
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			FrameDemoActivity.toolUtils.startProgressDialog();
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String reqResult = "";
			FrameDemoActivity.webservice.setMETHOD_NAME("GetHsRsk");
			FrameDemoActivity.webservice.setSOAP_ACTION("http://tempuri.org/IGetExamDataService/GetHsRsk");
			if (FrameDemoActivity.webservice.connect(getContext(),new String[] { "pageIndex", "pageSize", "gName","codeTs" },
					new Object[] { params[0], params[1], params[2],params[3] })) {
				reqResult = FrameDemoActivity.webservice.getResult().toString();
				System.out.println("标准化商品信息列表：" + reqResult);
				//添加Log日志
				if(reqResult!=null && !reqResult.equals("")){
					FrameDemoActivity.toolUtils.writeDataLog("查询标准化商品信息成功。", "85", "");
				}
			}
			return reqResult;
		}
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result.equals("")) {
				page4--;
				FrameDemoActivity.toolUtils.promptMessage("对不起，不存在该商品的标准化信息！！！");
			} else if (result != null && !result.equals("")) {
				System.out.println("标准化商品信息列表：" + result);
				standardGoodsInfoList.clear();
				standardGoodsInfoList.addAll((List)FrameDemoActivity.gson.fromJson(result, new TypeToken<List<HS_RSK_REL>>() {}.getType()));
				m2i4LeftForAdapter.notifyDataSetChanged();
				m2i4RightForAdapter.notifyDataSetChanged();
			}
			FrameDemoActivity.toolUtils.endProgressDialog();
		}
		
	};
	
	//所有ListView的适配器的数据初始化
	private void initListViewToAdapter(){
		// 添加典型查获商品信息ListView列表数据
		m2i4LeftFAdapter=new Menu2Info4LeftFirstListViewAdapter(getContext(), typicalGoodsInfoList);
		mlv_RM2I4_leftLVFirst.setAdapter(m2i4LeftFAdapter);
		m2i4RightFAdapter=new Menu2Info4RightFirstListViewAdapter(getContext(), typicalGoodsInfoList);
		mlv_RM2I4_rightLVFirst.setAdapter(m2i4RightFAdapter);
		// 添加商品查验风险要点ListView列表数据
		m2i4LeftSAdapter=new Menu2Info4LeftSecondListViewAdapter(getContext(), goodsRiskList);
		mlv_RM2I4_leftLVSecond.setAdapter(m2i4LeftSAdapter);
		m2i4RightSAdapter=new Menu2Info4RightSecondListViewAdapter(getContext(), goodsRiskList);
		mlv_RM2I4_rightLVSecond.setAdapter(m2i4RightSAdapter);
		// 添加商品监管条件ListView列表数据
		m2i4LeftTAdapter=new Menu2Info4LeftThirdListViewAdapter(getContext(), goodsConditionList);
		mlv_RM2I4_leftLVThird.setAdapter(m2i4LeftTAdapter);
		m2i4RightAdapter=new Menu2Info4RightThirdListViewAdapter(getContext(), goodsConditionList);
		mlv_RM2I4_rightLVThird.setAdapter(m2i4RightAdapter);
		// 添加标准化商品信息ListView列表数据
		m2i4LeftForAdapter=new Menu2Info4LeftForthListViewAdapter(getContext(), standardGoodsInfoList);
		mlv_RM2I4_leftLVForth.setAdapter(m2i4LeftForAdapter);
		m2i4RightForAdapter=new Menu2Info4RightForthListViewAdapter(getContext(), standardGoodsInfoList);
		mlv_RM2I4_rightLVForth.setAdapter(m2i4RightForAdapter);
	}
	private Menu2Info4LeftFirstListViewAdapter m2i4LeftFAdapter;
	private Menu2Info4RightFirstListViewAdapter m2i4RightFAdapter;
	private Menu2Info4LeftSecondListViewAdapter m2i4LeftSAdapter;
	private Menu2Info4RightSecondListViewAdapter m2i4RightSAdapter;
	private Menu2Info4LeftThirdListViewAdapter m2i4LeftTAdapter;
	private Menu2Info4RightThirdListViewAdapter m2i4RightAdapter;
	private Menu2Info4LeftForthListViewAdapter m2i4LeftForAdapter;
	private Menu2Info4RightForthListViewAdapter m2i4RightForAdapter;
}
