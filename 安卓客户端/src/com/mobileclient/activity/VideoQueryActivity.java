package com.mobileclient.activity;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.mobileclient.domain.Video;
import com.mobileclient.domain.VideoType;
import com.mobileclient.service.VideoTypeService;

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
public class VideoQueryActivity extends Activity {
	// 声明查询按钮
	private Button btnQuery;
	// 声明教学标题输入框
	private EditText ET_title;
	// 声明教学类别下拉框
	private Spinner spinner_videoTypeObj;
	private ArrayAdapter<String> videoTypeObj_adapter;
	private static  String[] videoTypeObj_ShowText  = null;
	private List<VideoType> videoTypeList = null; 
	/*视频类型管理业务逻辑层*/
	private VideoTypeService videoTypeService = new VideoTypeService();
	// 声明所打位置输入框
	private EditText ET_sportPos;
	// 声明发布时间输入框
	private EditText ET_publishTime;
	/*查询过滤条件保存到这个对象中*/
	private Video queryConditionVideo = new Video();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.video_query);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("设置篮球教学查询条件");
		ImageView back_btn = (ImageView) this.findViewById(R.id.back_btn);
		back_btn.setOnClickListener(new android.view.View.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		btnQuery = (Button) findViewById(R.id.btnQuery);
		ET_title = (EditText) findViewById(R.id.ET_title);
		spinner_videoTypeObj = (Spinner) findViewById(R.id.Spinner_videoTypeObj);
		// 获取所有的视频类型
		try {
			videoTypeList = videoTypeService.QueryVideoType(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int videoTypeCount = videoTypeList.size();
		videoTypeObj_ShowText = new String[videoTypeCount+1];
		videoTypeObj_ShowText[0] = "不限制";
		for(int i=1;i<=videoTypeCount;i++) { 
			videoTypeObj_ShowText[i] = videoTypeList.get(i-1).getTypeName();
		} 
		// 将可选内容与ArrayAdapter连接起来
		videoTypeObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, videoTypeObj_ShowText);
		// 设置教学类别下拉列表的风格
		videoTypeObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_videoTypeObj.setAdapter(videoTypeObj_adapter);
		// 添加事件Spinner事件监听
		spinner_videoTypeObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionVideo.setVideoTypeObj(videoTypeList.get(arg2-1).getTypeId()); 
				else
					queryConditionVideo.setVideoTypeObj(0);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_videoTypeObj.setVisibility(View.VISIBLE);
		ET_sportPos = (EditText) findViewById(R.id.ET_sportPos);
		ET_publishTime = (EditText) findViewById(R.id.ET_publishTime);
		/*单击查询按钮*/
		btnQuery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*获取查询参数*/
					queryConditionVideo.setTitle(ET_title.getText().toString());
					queryConditionVideo.setSportPos(ET_sportPos.getText().toString());
					queryConditionVideo.setPublishTime(ET_publishTime.getText().toString());
					Intent intent = getIntent();
					//这里使用bundle绷带来传输数据
					Bundle bundle =new Bundle();
					//传输的内容仍然是键值对的形式
					bundle.putSerializable("queryConditionVideo", queryConditionVideo);
					intent.putExtras(bundle);
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
			});
	}
}
