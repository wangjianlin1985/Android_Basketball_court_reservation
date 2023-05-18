package com.mobileclient.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mobileclient.app.Declare;
import com.mobileclient.domain.Video;
import com.mobileclient.service.VideoService;
import com.mobileclient.util.ActivityUtils;import com.mobileclient.util.VideoSimpleAdapter;
import com.mobileclient.util.HttpUtil;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.Handler;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnCreateContextMenuListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class VideoListActivity extends Activity {
	VideoSimpleAdapter adapter;
	ListView lv; 
	List<Map<String, Object>> list;
	int videoId;
	/* 篮球教学操作业务逻辑层对象 */
	VideoService videoService = new VideoService();
	/*保存查询参数条件的篮球教学对象*/
	private Video queryConditionVideo;

	private MyProgressDialog dialog; //进度条	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		setContentView(R.layout.video_list);
		dialog = MyProgressDialog.getInstance(this);
		Declare declare = (Declare) getApplicationContext();
		String username = declare.getUserName();
		//标题栏控件
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(VideoListActivity.this, VideoQueryActivity.class);
				startActivityForResult(intent,ActivityUtils.QUERY_CODE);//此处的requestCode应与下面结果处理函中调用的requestCode一致
			}
		});
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("篮球教学查询列表");
		ImageView add_btn = (ImageView) this.findViewById(R.id.add_btn);
		add_btn.setOnClickListener(new android.view.View.OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(VideoListActivity.this, VideoAddActivity.class);
				startActivityForResult(intent,ActivityUtils.ADD_CODE);
			}
		});
		if(declare.getIdentify().equals("user")) add_btn.setVisibility(View.GONE);
		setViews();
	}

	//结果处理函数，当从secondActivity中返回时调用此函数
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==ActivityUtils.QUERY_CODE && resultCode==RESULT_OK){
        	Bundle extras = data.getExtras();
        	if(extras != null)
        		queryConditionVideo = (Video)extras.getSerializable("queryConditionVideo");
        	setViews();
        }
        if(requestCode==ActivityUtils.EDIT_CODE && resultCode==RESULT_OK){
        	setViews();
        }
        if(requestCode == ActivityUtils.ADD_CODE && resultCode == RESULT_OK) {
        	queryConditionVideo = null;
        	setViews();
        }
    }

	private void setViews() {
		lv = (ListView) findViewById(R.id.h_list_view);
		dialog.show();
		final Handler handler = new Handler();
		new Thread(){
			@Override
			public void run() {
				//在子线程中进行下载数据操作
				list = getDatas();
				//发送消失到handler，通知主线程下载完成
				handler.post(new Runnable() {
					@Override
					public void run() {
						dialog.cancel();
						adapter = new VideoSimpleAdapter(VideoListActivity.this, list,
	        					R.layout.video_list_item,
	        					new String[] { "title","videoTypeObj","videoPhoto","sportPos","hitNum","publishTime" },
	        					new int[] { R.id.tv_title,R.id.tv_videoTypeObj,R.id.iv_videoPhoto,R.id.tv_sportPos,R.id.tv_hitNum,R.id.tv_publishTime,},lv);
	        			lv.setAdapter(adapter);
					}
				});
			}
		}.start(); 

		// 添加长按点击
		lv.setOnCreateContextMenuListener(videoListItemListener);
		lv.setOnItemClickListener(new OnItemClickListener(){
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
            	int videoId = Integer.parseInt(list.get(arg2).get("videoId").toString());
            	Intent intent = new Intent();
            	intent.setClass(VideoListActivity.this, VideoDetailActivity.class);
            	Bundle bundle = new Bundle();
            	bundle.putInt("videoId", videoId);
            	intent.putExtras(bundle);
            	startActivity(intent);
            }
        });
	}
	private OnCreateContextMenuListener videoListItemListener = new OnCreateContextMenuListener() {
		public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
			Declare declare = (Declare) getApplicationContext();
			if(declare.getIdentify().equals("admin")) {
				menu.add(0, 0, 0, "编辑篮球教学信息"); 
				menu.add(0, 1, 0, "删除篮球教学信息");
			}
			
		}
	};

	// 长按菜单响应函数
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if (item.getItemId() == 0) {  //编辑篮球教学信息
			ContextMenuInfo info = item.getMenuInfo();
			AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) info;
			// 获取选中行位置
			int position = contextMenuInfo.position;
			// 获取记录id
			videoId = Integer.parseInt(list.get(position).get("videoId").toString());
			Intent intent = new Intent();
			intent.setClass(VideoListActivity.this, VideoEditActivity.class);
			Bundle bundle = new Bundle();
			bundle.putInt("videoId", videoId);
			intent.putExtras(bundle);
			startActivityForResult(intent,ActivityUtils.EDIT_CODE);
		} else if (item.getItemId() == 1) {// 删除篮球教学信息
			ContextMenuInfo info = item.getMenuInfo();
			AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) info;
			// 获取选中行位置
			int position = contextMenuInfo.position;
			// 获取记录id
			videoId = Integer.parseInt(list.get(position).get("videoId").toString());
			dialog();
		}
		return super.onContextItemSelected(item);
	}

	// 删除
	protected void dialog() {
		Builder builder = new Builder(VideoListActivity.this);
		builder.setMessage("确认删除吗？");
		builder.setTitle("提示");
		builder.setPositiveButton("确认", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				String result = videoService.DeleteVideo(videoId);
				Toast.makeText(getApplicationContext(), result, 1).show();
				setViews();
				dialog.dismiss();
			}
		});
		builder.setNegativeButton("取消", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}

	private List<Map<String, Object>> getDatas() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			/* 查询篮球教学信息 */
			List<Video> videoList = videoService.QueryVideo(queryConditionVideo);
			for (int i = 0; i < videoList.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("videoId",videoList.get(i).getVideoId());
				map.put("title", videoList.get(i).getTitle());
				map.put("videoTypeObj", videoList.get(i).getVideoTypeObj());
				/*byte[] videoPhoto_data = ImageService.getImage(HttpUtil.BASE_URL+ videoList.get(i).getVideoPhoto());// 获取图片数据
				BitmapFactory.Options videoPhoto_opts = new BitmapFactory.Options();  
				videoPhoto_opts.inJustDecodeBounds = true;  
				BitmapFactory.decodeByteArray(videoPhoto_data, 0, videoPhoto_data.length, videoPhoto_opts); 
				videoPhoto_opts.inSampleSize = photoListActivity.computeSampleSize(videoPhoto_opts, -1, 100*100); 
				videoPhoto_opts.inJustDecodeBounds = false; 
				try {
					Bitmap videoPhoto = BitmapFactory.decodeByteArray(videoPhoto_data, 0, videoPhoto_data.length, videoPhoto_opts);
					map.put("videoPhoto", videoPhoto);
				} catch (OutOfMemoryError err) { }*/
				map.put("videoPhoto", HttpUtil.BASE_URL+ videoList.get(i).getVideoPhoto());
				map.put("sportPos", videoList.get(i).getSportPos());
				map.put("hitNum", videoList.get(i).getHitNum());
				map.put("publishTime", videoList.get(i).getPublishTime());
				list.add(map);
			}
		} catch (Exception e) { 
			Toast.makeText(getApplicationContext(), "", 1).show();
		}
		return list;
	}

}
