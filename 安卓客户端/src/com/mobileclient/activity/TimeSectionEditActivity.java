package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import com.mobileclient.domain.TimeSection;
import com.mobileclient.service.TimeSectionService;
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

public class TimeSectionEditActivity extends Activity {
	// 声明确定添加按钮
	private Button btnUpdate;
	// 声明记录idTextView
	private TextView TV_sectionId;
	// 声明时段名称输入框
	private EditText ET_sectionName;
	protected String carmera_path;
	/*要保存的时段信息信息*/
	TimeSection timeSection = new TimeSection();
	/*时段信息管理业务逻辑层*/
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
		setContentView(R.layout.timesection_edit); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("编辑时段信息信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		TV_sectionId = (TextView) findViewById(R.id.TV_sectionId);
		ET_sectionName = (EditText) findViewById(R.id.ET_sectionName);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		sectionId = extras.getInt("sectionId");
		/*单击修改时段信息按钮*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取时段名称*/ 
					if(ET_sectionName.getText().toString().equals("")) {
						Toast.makeText(TimeSectionEditActivity.this, "时段名称输入不能为空!", Toast.LENGTH_LONG).show();
						ET_sectionName.setFocusable(true);
						ET_sectionName.requestFocus();
						return;	
					}
					timeSection.setSectionName(ET_sectionName.getText().toString());
					/*调用业务逻辑层上传时段信息信息*/
					TimeSectionEditActivity.this.setTitle("正在更新时段信息信息，稍等...");
					String result = timeSectionService.UpdateTimeSection(timeSection);
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
	    timeSection = timeSectionService.GetTimeSection(sectionId);
		this.TV_sectionId.setText(sectionId+"");
		this.ET_sectionName.setText(timeSection.getSectionName());
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
