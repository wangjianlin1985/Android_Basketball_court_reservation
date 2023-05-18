package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import com.mobileclient.domain.Video;
import com.mobileclient.service.VideoService;
import com.mobileclient.domain.VideoType;
import com.mobileclient.service.VideoTypeService;
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

public class VideoEditActivity extends Activity {
	// 声明确定添加按钮
	private Button btnUpdate;
	// 声明记录idTextView
	private TextView TV_videoId;
	// 声明教学标题输入框
	private EditText ET_title;
	// 声明教学类别下拉框
	private Spinner spinner_videoTypeObj;
	private ArrayAdapter<String> videoTypeObj_adapter;
	private static  String[] videoTypeObj_ShowText  = null;
	private List<VideoType> videoTypeList = null;
	/*教学类别管理业务逻辑层*/
	private VideoTypeService videoTypeService = new VideoTypeService();
	// 声明教学图片图片框控件
	private ImageView iv_videoPhoto;
	private Button btn_videoPhoto;
	protected int REQ_CODE_SELECT_IMAGE_videoPhoto = 1;
	private int REQ_CODE_CAMERA_videoPhoto = 2;
	// 声明教学内容输入框
	private EditText ET_content;
	// 声明所打位置输入框
	private EditText ET_sportPos;
	// 声明视频文件控件
	private TextView TV_videoFile;
	private Button btn_videoFile;
	private int REQ_CODE_SELECT_FILE_videoFile = 3;
	// 声明点击率输入框
	private EditText ET_hitNum;
	// 声明发布时间输入框
	private EditText ET_publishTime;
	protected String carmera_path;
	/*要保存的篮球教学信息*/
	Video video = new Video();
	/*篮球教学管理业务逻辑层*/
	private VideoService videoService = new VideoService();

	private int videoId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.video_edit); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("编辑篮球教学信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		TV_videoId = (TextView) findViewById(R.id.TV_videoId);
		ET_title = (EditText) findViewById(R.id.ET_title);
		spinner_videoTypeObj = (Spinner) findViewById(R.id.Spinner_videoTypeObj);
		// 获取所有的教学类别
		try {
			videoTypeList = videoTypeService.QueryVideoType(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int videoTypeCount = videoTypeList.size();
		videoTypeObj_ShowText = new String[videoTypeCount];
		for(int i=0;i<videoTypeCount;i++) { 
			videoTypeObj_ShowText[i] = videoTypeList.get(i).getTypeName();
		}
		// 将可选内容与ArrayAdapter连接起来
		videoTypeObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, videoTypeObj_ShowText);
		// 设置图书类别下拉列表的风格
		videoTypeObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_videoTypeObj.setAdapter(videoTypeObj_adapter);
		// 添加事件Spinner事件监听
		spinner_videoTypeObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				video.setVideoTypeObj(videoTypeList.get(arg2).getTypeId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_videoTypeObj.setVisibility(View.VISIBLE);
		iv_videoPhoto = (ImageView) findViewById(R.id.iv_videoPhoto);
		/*单击图片显示控件时进行图片的选择*/
		iv_videoPhoto.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(VideoEditActivity.this,photoListActivity.class);
				startActivityForResult(intent,REQ_CODE_SELECT_IMAGE_videoPhoto);
			}
		});
		btn_videoPhoto = (Button) findViewById(R.id.btn_videoPhoto);
		btn_videoPhoto.setOnClickListener(new OnClickListener() { 
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); 
				carmera_path = HttpUtil.FILE_PATH + "/carmera_videoPhoto.bmp";
				File out = new File(carmera_path); 
				intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(out)); 
				startActivityForResult(intent, REQ_CODE_CAMERA_videoPhoto);  
			}
		});
		ET_content = (EditText) findViewById(R.id.ET_content);
		ET_sportPos = (EditText) findViewById(R.id.ET_sportPos);
		TV_videoFile = (TextView) findViewById(R.id.TV_videoFile);
		btn_videoFile = (Button) findViewById(R.id.btn_videoFile);
		btn_videoFile.setOnClickListener(new OnClickListener() { 
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(VideoEditActivity.this,fileListActivity.class);
				startActivityForResult(intent,REQ_CODE_SELECT_FILE_videoFile);
			}
		});
		ET_hitNum = (EditText) findViewById(R.id.ET_hitNum);
		ET_publishTime = (EditText) findViewById(R.id.ET_publishTime);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		videoId = extras.getInt("videoId");
		/*单击修改篮球教学按钮*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取教学标题*/ 
					if(ET_title.getText().toString().equals("")) {
						Toast.makeText(VideoEditActivity.this, "教学标题输入不能为空!", Toast.LENGTH_LONG).show();
						ET_title.setFocusable(true);
						ET_title.requestFocus();
						return;	
					}
					video.setTitle(ET_title.getText().toString());
					if (!video.getVideoPhoto().startsWith("upload/")) {
						//如果图片地址不为空，说明用户选择了图片，这时需要连接服务器上传图片
						VideoEditActivity.this.setTitle("正在上传图片，稍等...");
						String videoPhoto = HttpUtil.uploadFile(video.getVideoPhoto());
						VideoEditActivity.this.setTitle("图片上传完毕！");
						video.setVideoPhoto(videoPhoto);
					} 
					/*验证获取教学内容*/ 
					if(ET_content.getText().toString().equals("")) {
						Toast.makeText(VideoEditActivity.this, "教学内容输入不能为空!", Toast.LENGTH_LONG).show();
						ET_content.setFocusable(true);
						ET_content.requestFocus();
						return;	
					}
					video.setContent(ET_content.getText().toString());
					/*验证获取所打位置*/ 
					if(ET_sportPos.getText().toString().equals("")) {
						Toast.makeText(VideoEditActivity.this, "所打位置输入不能为空!", Toast.LENGTH_LONG).show();
						ET_sportPos.setFocusable(true);
						ET_sportPos.requestFocus();
						return;	
					}
					video.setSportPos(ET_sportPos.getText().toString());
					/*验证获取点击率*/ 
					if(ET_hitNum.getText().toString().equals("")) {
						Toast.makeText(VideoEditActivity.this, "点击率输入不能为空!", Toast.LENGTH_LONG).show();
						ET_hitNum.setFocusable(true);
						ET_hitNum.requestFocus();
						return;	
					}
					video.setHitNum(Integer.parseInt(ET_hitNum.getText().toString()));
					/*验证获取发布时间*/ 
					if(ET_publishTime.getText().toString().equals("")) {
						Toast.makeText(VideoEditActivity.this, "发布时间输入不能为空!", Toast.LENGTH_LONG).show();
						ET_publishTime.setFocusable(true);
						ET_publishTime.requestFocus();
						return;	
					}
					video.setPublishTime(ET_publishTime.getText().toString());
					/*调用业务逻辑层上传篮球教学信息*/
					VideoEditActivity.this.setTitle("正在更新篮球教学信息，稍等...");
					String result = videoService.UpdateVideo(video);
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
	    video = videoService.GetVideo(videoId);
		this.TV_videoId.setText(videoId+"");
		this.ET_title.setText(video.getTitle());
		for (int i = 0; i < videoTypeList.size(); i++) {
			if (video.getVideoTypeObj() == videoTypeList.get(i).getTypeId()) {
				this.spinner_videoTypeObj.setSelection(i);
				break;
			}
		}
		byte[] videoPhoto_data = null;
		try {
			// 获取图片数据
			videoPhoto_data = ImageService.getImage(HttpUtil.BASE_URL + video.getVideoPhoto());
			Bitmap videoPhoto = BitmapFactory.decodeByteArray(videoPhoto_data, 0, videoPhoto_data.length);
			this.iv_videoPhoto.setImageBitmap(videoPhoto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.ET_content.setText(video.getContent());
		this.ET_sportPos.setText(video.getSportPos());
		this.TV_videoFile.setText(video.getVideoFile());
		this.ET_hitNum.setText(video.getHitNum() + "");
		this.ET_publishTime.setText(video.getPublishTime());
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQ_CODE_CAMERA_videoPhoto  && resultCode == Activity.RESULT_OK) {
			carmera_path = HttpUtil.FILE_PATH + "/carmera_videoPhoto.bmp"; 
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(carmera_path, opts); 
			opts.inSampleSize = photoListActivity.computeSampleSize(opts, -1, 300*300);
			opts.inJustDecodeBounds = false;
			try {
				Bitmap booImageBm = BitmapFactory.decodeFile(carmera_path, opts);
				String jpgFileName = "carmera_videoPhoto.jpg";
				String jpgFilePath =  HttpUtil.FILE_PATH + "/" + jpgFileName;
				try {
					FileOutputStream jpgOutputStream = new FileOutputStream(jpgFilePath);
					booImageBm.compress(Bitmap.CompressFormat.JPEG, 30, jpgOutputStream);// 把数据写入文件 
					File bmpFile = new File(carmera_path);
					bmpFile.delete();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} 
				this.iv_videoPhoto.setImageBitmap(booImageBm);
				this.iv_videoPhoto.setScaleType(ScaleType.FIT_CENTER);
				this.video.setVideoPhoto(jpgFileName);
			} catch (OutOfMemoryError err) {  }
		}

		if(requestCode == REQ_CODE_SELECT_IMAGE_videoPhoto && resultCode == Activity.RESULT_OK) {
			Bundle bundle = data.getExtras();
			String filename =  bundle.getString("fileName");
			String filepath = HttpUtil.FILE_PATH + "/" + filename;
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true; 
			BitmapFactory.decodeFile(filepath, opts); 
			opts.inSampleSize = photoListActivity.computeSampleSize(opts, -1, 128*128);
			opts.inJustDecodeBounds = false; 
			try { 
				Bitmap bm = BitmapFactory.decodeFile(filepath, opts);
				this.iv_videoPhoto.setImageBitmap(bm); 
				this.iv_videoPhoto.setScaleType(ScaleType.FIT_CENTER); 
			} catch (OutOfMemoryError err) {  } 
			video.setVideoPhoto(filename); 
		}
		if(requestCode == REQ_CODE_SELECT_FILE_videoFile && resultCode == Activity.RESULT_OK) {
			Bundle bundle = data.getExtras();
			String filename =  bundle.getString("fileName");
			String filepath = HttpUtil.FILE_PATH + "/upload/" + filename;
			this.TV_videoFile.setText(filepath);
			String videoFile = HttpUtil.uploadFile(filename);
			video.setVideoFile(videoFile);
		}
	}
}
