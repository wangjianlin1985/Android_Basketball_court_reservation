package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.Place;
import com.mobileclient.service.PlaceService;
import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import android.widget.Toast;
public class PlaceDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn,btnOrder;
	// 声明场地id控件
	private TextView TV_placeId;
	// 声明场地名称控件
	private TextView TV_placeName;
	// 声明球场图片图片框
	private ImageView iv_placePhoto;
	// 声明球场地址控件
	private TextView TV_placePos;
	// 声明联系电话控件
	private TextView TV_telephone;
	// 声明球场价格控件
	private TextView TV_placePrice;
	// 声明球场介绍控件
	private TextView TV_placeDesc;
	// 声明营业时间控件
	private TextView TV_onlineTime;
	// 声明是否置顶控件
	private TextView TV_topFlag;
	// 声明发布时间控件
	private TextView TV_addTime;
	/* 要保存的篮球场地信息 */
	Place place = new Place(); 
	/* 篮球场地管理业务逻辑层 */
	private PlaceService placeService = new PlaceService();
	private int placeId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.place_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看篮球场地详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		btnOrder = (Button) findViewById(R.id.btnOrder);
		TV_placeId = (TextView) findViewById(R.id.TV_placeId);
		TV_placeName = (TextView) findViewById(R.id.TV_placeName);
		iv_placePhoto = (ImageView) findViewById(R.id.iv_placePhoto); 
		TV_placePos = (TextView) findViewById(R.id.TV_placePos);
		TV_telephone = (TextView) findViewById(R.id.TV_telephone);
		TV_placePrice = (TextView) findViewById(R.id.TV_placePrice);
		TV_placeDesc = (TextView) findViewById(R.id.TV_placeDesc);
		TV_onlineTime = (TextView) findViewById(R.id.TV_onlineTime);
		TV_topFlag = (TextView) findViewById(R.id.TV_topFlag);
		TV_addTime = (TextView) findViewById(R.id.TV_addTime);
		Bundle extras = this.getIntent().getExtras();
		placeId = extras.getInt("placeId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				PlaceDetailActivity.this.finish();
			}
		});
		
		btnOrder.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(); 
				intent.putExtra("placeId", place.getPlaceId());
				intent.setClass(PlaceDetailActivity.this, PlaceOrderUserAddActivity.class);
				startActivity(intent);
				 
			}
		}); 
		
		
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    place = placeService.GetPlace(placeId); 
		this.TV_placeId.setText(place.getPlaceId() + "");
		this.TV_placeName.setText(place.getPlaceName());
		byte[] placePhoto_data = null;
		try {
			// 获取图片数据
			placePhoto_data = ImageService.getImage(HttpUtil.BASE_URL + place.getPlacePhoto());
			Bitmap placePhoto = BitmapFactory.decodeByteArray(placePhoto_data, 0,placePhoto_data.length);
			this.iv_placePhoto.setImageBitmap(placePhoto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.TV_placePos.setText(place.getPlacePos());
		this.TV_telephone.setText(place.getTelephone());
		this.TV_placePrice.setText(place.getPlacePrice() + "");
		this.TV_placeDesc.setText(place.getPlaceDesc());
		this.TV_onlineTime.setText(place.getOnlineTime());
		this.TV_topFlag.setText(place.getTopFlag() + "");
		this.TV_addTime.setText(place.getAddTime());
	} 
}
