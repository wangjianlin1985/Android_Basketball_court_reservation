package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import com.mobileclient.domain.Leaveword;
import com.mobileclient.service.LeavewordService;
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

public class LeavewordEditActivity extends Activity {
	// 声明确定添加按钮
	private Button btnUpdate;
	// 声明留言idTextView
	private TextView TV_leaveWordId;
	// 声明约战标题输入框
	private EditText ET_leaveTitle;
	// 声明约战内容输入框
	private EditText ET_leaveContent;
	// 声明约战电话输入框
	private EditText ET_telephone;
	// 声明约战人下拉框
	private Spinner spinner_userObj;
	private ArrayAdapter<String> userObj_adapter;
	private static  String[] userObj_ShowText  = null;
	private List<UserInfo> userInfoList = null;
	/*约战人管理业务逻辑层*/
	private UserInfoService userInfoService = new UserInfoService();
	// 声明约战时间输入框
	private EditText ET_leaveTime;
	protected String carmera_path;
	/*要保存的约战留言信息*/
	Leaveword leaveword = new Leaveword();
	/*约战留言管理业务逻辑层*/
	private LeavewordService leavewordService = new LeavewordService();

	private int leaveWordId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.leaveword_edit); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("编辑约战留言信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		TV_leaveWordId = (TextView) findViewById(R.id.TV_leaveWordId);
		ET_leaveTitle = (EditText) findViewById(R.id.ET_leaveTitle);
		ET_leaveContent = (EditText) findViewById(R.id.ET_leaveContent);
		ET_telephone = (EditText) findViewById(R.id.ET_telephone);
		spinner_userObj = (Spinner) findViewById(R.id.Spinner_userObj);
		// 获取所有的约战人
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
				leaveword.setUserObj(userInfoList.get(arg2).getUser_name()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_userObj.setVisibility(View.VISIBLE);
		ET_leaveTime = (EditText) findViewById(R.id.ET_leaveTime);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		leaveWordId = extras.getInt("leaveWordId");
		/*单击修改约战留言按钮*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取约战标题*/ 
					if(ET_leaveTitle.getText().toString().equals("")) {
						Toast.makeText(LeavewordEditActivity.this, "约战标题输入不能为空!", Toast.LENGTH_LONG).show();
						ET_leaveTitle.setFocusable(true);
						ET_leaveTitle.requestFocus();
						return;	
					}
					leaveword.setLeaveTitle(ET_leaveTitle.getText().toString());
					/*验证获取约战内容*/ 
					if(ET_leaveContent.getText().toString().equals("")) {
						Toast.makeText(LeavewordEditActivity.this, "约战内容输入不能为空!", Toast.LENGTH_LONG).show();
						ET_leaveContent.setFocusable(true);
						ET_leaveContent.requestFocus();
						return;	
					}
					leaveword.setLeaveContent(ET_leaveContent.getText().toString());
					/*验证获取约战电话*/ 
					if(ET_telephone.getText().toString().equals("")) {
						Toast.makeText(LeavewordEditActivity.this, "约战电话输入不能为空!", Toast.LENGTH_LONG).show();
						ET_telephone.setFocusable(true);
						ET_telephone.requestFocus();
						return;	
					}
					leaveword.setTelephone(ET_telephone.getText().toString());
					/*验证获取约战时间*/ 
					if(ET_leaveTime.getText().toString().equals("")) {
						Toast.makeText(LeavewordEditActivity.this, "约战时间输入不能为空!", Toast.LENGTH_LONG).show();
						ET_leaveTime.setFocusable(true);
						ET_leaveTime.requestFocus();
						return;	
					}
					leaveword.setLeaveTime(ET_leaveTime.getText().toString());
					/*调用业务逻辑层上传约战留言信息*/
					LeavewordEditActivity.this.setTitle("正在更新约战留言信息，稍等...");
					String result = leavewordService.UpdateLeaveword(leaveword);
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
	    leaveword = leavewordService.GetLeaveword(leaveWordId);
		this.TV_leaveWordId.setText(leaveWordId+"");
		this.ET_leaveTitle.setText(leaveword.getLeaveTitle());
		this.ET_leaveContent.setText(leaveword.getLeaveContent());
		this.ET_telephone.setText(leaveword.getTelephone());
		for (int i = 0; i < userInfoList.size(); i++) {
			if (leaveword.getUserObj().equals(userInfoList.get(i).getUser_name())) {
				this.spinner_userObj.setSelection(i);
				break;
			}
		}
		this.ET_leaveTime.setText(leaveword.getLeaveTime());
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
