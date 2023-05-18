package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.Leaveword;
import com.mobileclient.service.LeavewordService;
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
public class LeavewordDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明留言id控件
	private TextView TV_leaveWordId;
	// 声明约战标题控件
	private TextView TV_leaveTitle;
	// 声明约战内容控件
	private TextView TV_leaveContent;
	// 声明约战电话控件
	private TextView TV_telephone;
	// 声明约战人控件
	private TextView TV_userObj;
	// 声明约战时间控件
	private TextView TV_leaveTime;
	/* 要保存的约战留言信息 */
	Leaveword leaveword = new Leaveword(); 
	/* 约战留言管理业务逻辑层 */
	private LeavewordService leavewordService = new LeavewordService();
	private UserInfoService userInfoService = new UserInfoService();
	private int leaveWordId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.leaveword_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看约战留言详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_leaveWordId = (TextView) findViewById(R.id.TV_leaveWordId);
		TV_leaveTitle = (TextView) findViewById(R.id.TV_leaveTitle);
		TV_leaveContent = (TextView) findViewById(R.id.TV_leaveContent);
		TV_telephone = (TextView) findViewById(R.id.TV_telephone);
		TV_userObj = (TextView) findViewById(R.id.TV_userObj);
		TV_leaveTime = (TextView) findViewById(R.id.TV_leaveTime);
		Bundle extras = this.getIntent().getExtras();
		leaveWordId = extras.getInt("leaveWordId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				LeavewordDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    leaveword = leavewordService.GetLeaveword(leaveWordId); 
		this.TV_leaveWordId.setText(leaveword.getLeaveWordId() + "");
		this.TV_leaveTitle.setText(leaveword.getLeaveTitle());
		this.TV_leaveContent.setText(leaveword.getLeaveContent());
		this.TV_telephone.setText(leaveword.getTelephone());
		UserInfo userObj = userInfoService.GetUserInfo(leaveword.getUserObj());
		this.TV_userObj.setText(userObj.getName());
		this.TV_leaveTime.setText(leaveword.getLeaveTime());
	} 
}
