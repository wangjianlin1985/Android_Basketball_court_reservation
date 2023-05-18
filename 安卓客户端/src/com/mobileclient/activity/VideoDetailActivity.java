package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.Video;
import com.mobileclient.service.VideoService;
import com.mobileclient.domain.VideoType;
import com.mobileclient.service.VideoTypeService;
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
public class VideoDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明记录id控件
	private TextView TV_videoId;
	// 声明教学标题控件
	private TextView TV_title;
	// 声明教学类别控件
	private TextView TV_videoTypeObj;
	// 声明教学图片图片框
	private ImageView iv_videoPhoto;
	// 声明教学内容控件
	private TextView TV_content;
	// 声明所打位置控件
	private TextView TV_sportPos;
	// 声明视频文件控件
	private TextView TV_videoFile;
	private Button btnDownVideoFile;
	// 声明点击率控件
	private TextView TV_hitNum;
	// 声明发布时间控件
	private TextView TV_publishTime;
	/* 要保存的篮球教学信息 */
	Video video = new Video(); 
	/* 篮球教学管理业务逻辑层 */
	private VideoService videoService = new VideoService();
	private VideoTypeService videoTypeService = new VideoTypeService();
	private int videoId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.video_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看篮球教学详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_videoId = (TextView) findViewById(R.id.TV_videoId);
		TV_title = (TextView) findViewById(R.id.TV_title);
		TV_videoTypeObj = (TextView) findViewById(R.id.TV_videoTypeObj);
		iv_videoPhoto = (ImageView) findViewById(R.id.iv_videoPhoto); 
		TV_content = (TextView) findViewById(R.id.TV_content);
		TV_sportPos = (TextView) findViewById(R.id.TV_sportPos);
		TV_videoFile = (TextView) findViewById(R.id.TV_videoFile);
		TV_hitNum = (TextView) findViewById(R.id.TV_hitNum);
		TV_publishTime = (TextView) findViewById(R.id.TV_publishTime);
		Bundle extras = this.getIntent().getExtras();
		videoId = extras.getInt("videoId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				VideoDetailActivity.this.finish();
			}
		}); 
		btnDownVideoFile = (Button)findViewById(R.id.btnDownVideoFile);
		btnDownVideoFile.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				VideoDetailActivity.this.setTitle("正在开始下载视频文件....");
				HttpUtil.downloadFile(video.getVideoFile()); 
				Toast.makeText(getApplicationContext(), "下载成功，你也可以在mobileclient/upload目录查看！", 1).show();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    video = videoService.GetVideo(videoId); 
		this.TV_videoId.setText(video.getVideoId() + "");
		this.TV_title.setText(video.getTitle());
		VideoType videoTypeObj = videoTypeService.GetVideoType(video.getVideoTypeObj());
		this.TV_videoTypeObj.setText(videoTypeObj.getTypeName());
		byte[] videoPhoto_data = null;
		try {
			// 获取图片数据
			videoPhoto_data = ImageService.getImage(HttpUtil.BASE_URL + video.getVideoPhoto());
			Bitmap videoPhoto = BitmapFactory.decodeByteArray(videoPhoto_data, 0,videoPhoto_data.length);
			this.iv_videoPhoto.setImageBitmap(videoPhoto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.TV_content.setText(video.getContent());
		this.TV_sportPos.setText(video.getSportPos());
		this.TV_videoFile.setText(video.getVideoFile());
		if(video.getVideoFile().equals("")) {
			// 获取视频文件数据
			this.btnDownVideoFile.setVisibility(View.GONE);
		}
		this.TV_hitNum.setText(video.getHitNum() + "");
		this.TV_publishTime.setText(video.getPublishTime());
	} 
}
