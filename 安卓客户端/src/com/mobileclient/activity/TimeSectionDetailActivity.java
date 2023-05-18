package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.TimeSection;
import com.mobileclient.service.TimeSectionService;
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
public class TimeSectionDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明记录id控件
	private TextView TV_sectionId;
	// 声明时段名称控件
	private TextView TV_sectionName;
	/* 要保存的时段信息信息 */
	TimeSection timeSection = new TimeSection(); 
	/* 时段信息管理业务逻辑层 */
	private TimeSectionService timeSectionService = new TimeSectionService();
	private int sectionId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.timesection_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看时段信息详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_sectionId = (TextView) findViewById(R.id.TV_sectionId);
		TV_sectionName = (TextView) findViewById(R.id.TV_sectionName);
		Bundle extras = this.getIntent().getExtras();
		sectionId = extras.getInt("sectionId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				TimeSectionDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    timeSection = timeSectionService.GetTimeSection(sectionId); 
		this.TV_sectionId.setText(timeSection.getSectionId() + "");
		this.TV_sectionName.setText(timeSection.getSectionName());
	} 
}
