package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import com.mobileclient.domain.PlaceOrder;
import com.mobileclient.service.PlaceOrderService;
import com.mobileclient.domain.Place;
import com.mobileclient.service.PlaceService;
import com.mobileclient.domain.TimeSection;
import com.mobileclient.service.TimeSectionService;
import com.mobileclient.domain.UserInfo;
import com.mobileclient.service.UserInfoService;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Spinner;
import android.widget.Toast;

public class PlaceOrderEditActivity extends Activity {
	// 声明确定添加按钮
	private Button btnUpdate;
	// 声明预订idTextView
	private TextView TV_orderId;
	// 声明预订球场下拉框
	private Spinner spinner_placeObj;
	private ArrayAdapter<String> placeObj_adapter;
	private static  String[] placeObj_ShowText  = null;
	private List<Place> placeList = null;
	/*预订球场管理业务逻辑层*/
	private PlaceService placeService = new PlaceService();
	// 出版预订日期控件
	private DatePicker dp_orderDate;
	// 声明预订时段下拉框
	private Spinner spinner_timeSectionObj;
	private ArrayAdapter<String> timeSectionObj_adapter;
	private static  String[] timeSectionObj_ShowText  = null;
	private List<TimeSection> timeSectionList = null;
	/*预订时段管理业务逻辑层*/
	private TimeSectionService timeSectionService = new TimeSectionService();
	// 声明预订人下拉框
	private Spinner spinner_userObj;
	private ArrayAdapter<String> userObj_adapter;
	private static  String[] userObj_ShowText  = null;
	private List<UserInfo> userInfoList = null;
	/*预订人管理业务逻辑层*/
	private UserInfoService userInfoService = new UserInfoService();
	// 声明预订时间输入框
	private EditText ET_orderTime;
	// 声明审核状态输入框
	private EditText ET_shenHeState;
	// 声明审核时间输入框
	private EditText ET_shenHeTime;
	protected String carmera_path;
	/*要保存的场地预订信息*/
	PlaceOrder placeOrder = new PlaceOrder();
	/*场地预订管理业务逻辑层*/
	private PlaceOrderService placeOrderService = new PlaceOrderService();

	private int orderId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.placeorder_edit); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("编辑场地预订信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		TV_orderId = (TextView) findViewById(R.id.TV_orderId);
		spinner_placeObj = (Spinner) findViewById(R.id.Spinner_placeObj);
		// 获取所有的预订球场
		try {
			placeList = placeService.QueryPlace(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int placeCount = placeList.size();
		placeObj_ShowText = new String[placeCount];
		for(int i=0;i<placeCount;i++) { 
			placeObj_ShowText[i] = placeList.get(i).getPlaceName();
		}
		// 将可选内容与ArrayAdapter连接起来
		placeObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, placeObj_ShowText);
		// 设置图书类别下拉列表的风格
		placeObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_placeObj.setAdapter(placeObj_adapter);
		// 添加事件Spinner事件监听
		spinner_placeObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				placeOrder.setPlaceObj(placeList.get(arg2).getPlaceId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_placeObj.setVisibility(View.VISIBLE);
		dp_orderDate = (DatePicker)this.findViewById(R.id.dp_orderDate);
		spinner_timeSectionObj = (Spinner) findViewById(R.id.Spinner_timeSectionObj);
		// 获取所有的预订时段
		try {
			timeSectionList = timeSectionService.QueryTimeSection(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int timeSectionCount = timeSectionList.size();
		timeSectionObj_ShowText = new String[timeSectionCount];
		for(int i=0;i<timeSectionCount;i++) { 
			timeSectionObj_ShowText[i] = timeSectionList.get(i).getSectionName();
		}
		// 将可选内容与ArrayAdapter连接起来
		timeSectionObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, timeSectionObj_ShowText);
		// 设置图书类别下拉列表的风格
		timeSectionObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_timeSectionObj.setAdapter(timeSectionObj_adapter);
		// 添加事件Spinner事件监听
		spinner_timeSectionObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				placeOrder.setTimeSectionObj(timeSectionList.get(arg2).getSectionId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_timeSectionObj.setVisibility(View.VISIBLE);
		spinner_userObj = (Spinner) findViewById(R.id.Spinner_userObj);
		// 获取所有的预订人
		try {
			userInfoList = userInfoService.QueryUserInfo(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int userInfoCount = userInfoList.size();
		userObj_ShowText = new String[userInfoCount];
		for(int i=0;i<userInfoCount;i++) { 
			userObj_ShowText[i] = userInfoList.get(i).getName();
		}
		// 将可选内容与ArrayAdapter连接起来
		userObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, userObj_ShowText);
		// 设置图书类别下拉列表的风格
		userObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_userObj.setAdapter(userObj_adapter);
		// 添加事件Spinner事件监听
		spinner_userObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				placeOrder.setUserObj(userInfoList.get(arg2).getUser_name()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_userObj.setVisibility(View.VISIBLE);
		ET_orderTime = (EditText) findViewById(R.id.ET_orderTime);
		ET_shenHeState = (EditText) findViewById(R.id.ET_shenHeState);
		ET_shenHeTime = (EditText) findViewById(R.id.ET_shenHeTime);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		orderId = extras.getInt("orderId");
		/*单击修改场地预订按钮*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*获取出版日期*/
					Date orderDate = new Date(dp_orderDate.getYear()-1900,dp_orderDate.getMonth(),dp_orderDate.getDayOfMonth());
					placeOrder.setOrderDate(new Timestamp(orderDate.getTime()));
					/*验证获取预订时间*/ 
					if(ET_orderTime.getText().toString().equals("")) {
						Toast.makeText(PlaceOrderEditActivity.this, "预订时间输入不能为空!", Toast.LENGTH_LONG).show();
						ET_orderTime.setFocusable(true);
						ET_orderTime.requestFocus();
						return;	
					}
					placeOrder.setOrderTime(ET_orderTime.getText().toString());
					/*验证获取审核状态*/ 
					if(ET_shenHeState.getText().toString().equals("")) {
						Toast.makeText(PlaceOrderEditActivity.this, "审核状态输入不能为空!", Toast.LENGTH_LONG).show();
						ET_shenHeState.setFocusable(true);
						ET_shenHeState.requestFocus();
						return;	
					}
					placeOrder.setShenHeState(ET_shenHeState.getText().toString());
					/*验证获取审核时间*/ 
					if(ET_shenHeTime.getText().toString().equals("")) {
						Toast.makeText(PlaceOrderEditActivity.this, "审核时间输入不能为空!", Toast.LENGTH_LONG).show();
						ET_shenHeTime.setFocusable(true);
						ET_shenHeTime.requestFocus();
						return;	
					}
					placeOrder.setShenHeTime(ET_shenHeTime.getText().toString());
					/*调用业务逻辑层上传场地预订信息*/
					PlaceOrderEditActivity.this.setTitle("正在更新场地预订信息，稍等...");
					String result = placeOrderService.UpdatePlaceOrder(placeOrder);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					Intent intent = getIntent();
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
		});
		initViewData();
	}

	/* 初始化显示编辑界面的数据 */
	private void initViewData() {
	    placeOrder = placeOrderService.GetPlaceOrder(orderId);
		this.TV_orderId.setText(orderId+"");
		for (int i = 0; i < placeList.size(); i++) {
			if (placeOrder.getPlaceObj() == placeList.get(i).getPlaceId()) {
				this.spinner_placeObj.setSelection(i);
				break;
			}
		}
		Date orderDate = new Date(placeOrder.getOrderDate().getTime());
		this.dp_orderDate.init(orderDate.getYear() + 1900,orderDate.getMonth(), orderDate.getDate(), null);
		for (int i = 0; i < timeSectionList.size(); i++) {
			if (placeOrder.getTimeSectionObj() == timeSectionList.get(i).getSectionId()) {
				this.spinner_timeSectionObj.setSelection(i);
				break;
			}
		}
		for (int i = 0; i < userInfoList.size(); i++) {
			if (placeOrder.getUserObj().equals(userInfoList.get(i).getUser_name())) {
				this.spinner_userObj.setSelection(i);
				break;
			}
		}
		this.ET_orderTime.setText(placeOrder.getOrderTime());
		this.ET_shenHeState.setText(placeOrder.getShenHeState());
		this.ET_shenHeTime.setText(placeOrder.getShenHeTime());
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
