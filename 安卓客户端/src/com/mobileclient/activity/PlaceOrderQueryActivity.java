package com.mobileclient.activity;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.mobileclient.domain.PlaceOrder;
import com.mobileclient.domain.Place;
import com.mobileclient.service.PlaceService;
import com.mobileclient.domain.TimeSection;
import com.mobileclient.service.TimeSectionService;
import com.mobileclient.domain.UserInfo;
import com.mobileclient.service.UserInfoService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import android.widget.ImageView;
import android.widget.TextView;
public class PlaceOrderQueryActivity extends Activity {
	// 声明查询按钮
	private Button btnQuery;
	// 声明预订球场下拉框
	private Spinner spinner_placeObj;
	private ArrayAdapter<String> placeObj_adapter;
	private static  String[] placeObj_ShowText  = null;
	private List<Place> placeList = null; 
	/*篮球场地管理业务逻辑层*/
	private PlaceService placeService = new PlaceService();
	// 预订日期控件
	private DatePicker dp_orderDate;
	private CheckBox cb_orderDate;
	// 声明预订时段下拉框
	private Spinner spinner_timeSectionObj;
	private ArrayAdapter<String> timeSectionObj_adapter;
	private static  String[] timeSectionObj_ShowText  = null;
	private List<TimeSection> timeSectionList = null; 
	/*时段信息管理业务逻辑层*/
	private TimeSectionService timeSectionService = new TimeSectionService();
	// 声明预订人下拉框
	private Spinner spinner_userObj;
	private ArrayAdapter<String> userObj_adapter;
	private static  String[] userObj_ShowText  = null;
	private List<UserInfo> userInfoList = null; 
	/*用户管理业务逻辑层*/
	private UserInfoService userInfoService = new UserInfoService();
	// 声明预订时间输入框
	private EditText ET_orderTime;
	// 声明审核状态输入框
	private EditText ET_shenHeState;
	// 声明审核时间输入框
	private EditText ET_shenHeTime;
	/*查询过滤条件保存到这个对象中*/
	private PlaceOrder queryConditionPlaceOrder = new PlaceOrder();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.placeorder_query);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("设置场地预订查询条件");
		ImageView back_btn = (ImageView) this.findViewById(R.id.back_btn);
		back_btn.setOnClickListener(new android.view.View.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		btnQuery = (Button) findViewById(R.id.btnQuery);
		spinner_placeObj = (Spinner) findViewById(R.id.Spinner_placeObj);
		// 获取所有的篮球场地
		try {
			placeList = placeService.QueryPlace(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int placeCount = placeList.size();
		placeObj_ShowText = new String[placeCount+1];
		placeObj_ShowText[0] = "不限制";
		for(int i=1;i<=placeCount;i++) { 
			placeObj_ShowText[i] = placeList.get(i-1).getPlaceName();
		} 
		// 将可选内容与ArrayAdapter连接起来
		placeObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, placeObj_ShowText);
		// 设置预订球场下拉列表的风格
		placeObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_placeObj.setAdapter(placeObj_adapter);
		// 添加事件Spinner事件监听
		spinner_placeObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionPlaceOrder.setPlaceObj(placeList.get(arg2-1).getPlaceId()); 
				else
					queryConditionPlaceOrder.setPlaceObj(0);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_placeObj.setVisibility(View.VISIBLE);
		dp_orderDate = (DatePicker) findViewById(R.id.dp_orderDate);
		cb_orderDate = (CheckBox) findViewById(R.id.cb_orderDate);
		spinner_timeSectionObj = (Spinner) findViewById(R.id.Spinner_timeSectionObj);
		// 获取所有的时段信息
		try {
			timeSectionList = timeSectionService.QueryTimeSection(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int timeSectionCount = timeSectionList.size();
		timeSectionObj_ShowText = new String[timeSectionCount+1];
		timeSectionObj_ShowText[0] = "不限制";
		for(int i=1;i<=timeSectionCount;i++) { 
			timeSectionObj_ShowText[i] = timeSectionList.get(i-1).getSectionName();
		} 
		// 将可选内容与ArrayAdapter连接起来
		timeSectionObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, timeSectionObj_ShowText);
		// 设置预订时段下拉列表的风格
		timeSectionObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_timeSectionObj.setAdapter(timeSectionObj_adapter);
		// 添加事件Spinner事件监听
		spinner_timeSectionObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionPlaceOrder.setTimeSectionObj(timeSectionList.get(arg2-1).getSectionId()); 
				else
					queryConditionPlaceOrder.setTimeSectionObj(0);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_timeSectionObj.setVisibility(View.VISIBLE);
		spinner_userObj = (Spinner) findViewById(R.id.Spinner_userObj);
		// 获取所有的用户
		try {
			userInfoList = userInfoService.QueryUserInfo(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int userInfoCount = userInfoList.size();
		userObj_ShowText = new String[userInfoCount+1];
		userObj_ShowText[0] = "不限制";
		for(int i=1;i<=userInfoCount;i++) { 
			userObj_ShowText[i] = userInfoList.get(i-1).getName();
		} 
		// 将可选内容与ArrayAdapter连接起来
		userObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, userObj_ShowText);
		// 设置预订人下拉列表的风格
		userObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_userObj.setAdapter(userObj_adapter);
		// 添加事件Spinner事件监听
		spinner_userObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionPlaceOrder.setUserObj(userInfoList.get(arg2-1).getUser_name()); 
				else
					queryConditionPlaceOrder.setUserObj("");
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_userObj.setVisibility(View.VISIBLE);
		ET_orderTime = (EditText) findViewById(R.id.ET_orderTime);
		ET_shenHeState = (EditText) findViewById(R.id.ET_shenHeState);
		ET_shenHeTime = (EditText) findViewById(R.id.ET_shenHeTime);
		/*单击查询按钮*/
		btnQuery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*获取查询参数*/
					if(cb_orderDate.isChecked()) {
						/*获取预订日期*/
						Date orderDate = new Date(dp_orderDate.getYear()-1900,dp_orderDate.getMonth(),dp_orderDate.getDayOfMonth());
						queryConditionPlaceOrder.setOrderDate(new Timestamp(orderDate.getTime()));
					} else {
						queryConditionPlaceOrder.setOrderDate(null);
					} 
					queryConditionPlaceOrder.setOrderTime(ET_orderTime.getText().toString());
					queryConditionPlaceOrder.setShenHeState(ET_shenHeState.getText().toString());
					queryConditionPlaceOrder.setShenHeTime(ET_shenHeTime.getText().toString());
					Intent intent = getIntent();
					//这里使用bundle绷带来传输数据
					Bundle bundle =new Bundle();
					//传输的内容仍然是键值对的形式
					bundle.putSerializable("queryConditionPlaceOrder", queryConditionPlaceOrder);
					intent.putExtras(bundle);
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
			});
	}
}
