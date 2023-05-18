package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.PlaceOrder;
import com.mobileclient.service.PlaceOrderService;
import com.mobileclient.domain.Place;
import com.mobileclient.service.PlaceService;
import com.mobileclient.domain.TimeSection;
import com.mobileclient.service.TimeSectionService;
import com.mobileclient.domain.UserInfo;
import com.mobileclient.service.UserInfoService;
import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import android.app.Activity;
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
public class PlaceOrderDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明预订id控件
	private TextView TV_orderId;
	// 声明预订球场控件
	private TextView TV_placeObj;
	// 声明预订日期控件
	private TextView TV_orderDate;
	// 声明预订时段控件
	private TextView TV_timeSectionObj;
	// 声明预订人控件
	private TextView TV_userObj;
	// 声明预订时间控件
	private TextView TV_orderTime;
	// 声明审核状态控件
	private TextView TV_shenHeState;
	// 声明审核时间控件
	private TextView TV_shenHeTime;
	/* 要保存的场地预订信息 */
	PlaceOrder placeOrder = new PlaceOrder(); 
	/* 场地预订管理业务逻辑层 */
	private PlaceOrderService placeOrderService = new PlaceOrderService();
	private PlaceService placeService = new PlaceService();
	private TimeSectionService timeSectionService = new TimeSectionService();
	private UserInfoService userInfoService = new UserInfoService();
	private int orderId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.placeorder_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看场地预订详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_orderId = (TextView) findViewById(R.id.TV_orderId);
		TV_placeObj = (TextView) findViewById(R.id.TV_placeObj);
		TV_orderDate = (TextView) findViewById(R.id.TV_orderDate);
		TV_timeSectionObj = (TextView) findViewById(R.id.TV_timeSectionObj);
		TV_userObj = (TextView) findViewById(R.id.TV_userObj);
		TV_orderTime = (TextView) findViewById(R.id.TV_orderTime);
		TV_shenHeState = (TextView) findViewById(R.id.TV_shenHeState);
		TV_shenHeTime = (TextView) findViewById(R.id.TV_shenHeTime);
		Bundle extras = this.getIntent().getExtras();
		orderId = extras.getInt("orderId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				PlaceOrderDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    placeOrder = placeOrderService.GetPlaceOrder(orderId); 
		this.TV_orderId.setText(placeOrder.getOrderId() + "");
		Place placeObj = placeService.GetPlace(placeOrder.getPlaceObj());
		this.TV_placeObj.setText(placeObj.getPlaceName());
		Date orderDate = new Date(placeOrder.getOrderDate().getTime());
		String orderDateStr = (orderDate.getYear() + 1900) + "-" + (orderDate.getMonth()+1) + "-" + orderDate.getDate();
		this.TV_orderDate.setText(orderDateStr);
		TimeSection timeSectionObj = timeSectionService.GetTimeSection(placeOrder.getTimeSectionObj());
		this.TV_timeSectionObj.setText(timeSectionObj.getSectionName());
		UserInfo userObj = userInfoService.GetUserInfo(placeOrder.getUserObj());
		this.TV_userObj.setText(userObj.getName());
		this.TV_orderTime.setText(placeOrder.getOrderTime());
		this.TV_shenHeState.setText(placeOrder.getShenHeState());
		this.TV_shenHeTime.setText(placeOrder.getShenHeTime());
	} 
}
