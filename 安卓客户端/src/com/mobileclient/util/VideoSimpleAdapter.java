package com.mobileclient.util;

import java.util.List;  
import java.util.Map;

import com.mobileclient.service.VideoTypeService;
import com.mobileclient.activity.R;
import com.mobileclient.imgCache.ImageLoadListener;
import com.mobileclient.imgCache.ListViewOnScrollListener;
import com.mobileclient.imgCache.SyncImageLoader;
import android.content.Context;
import android.view.LayoutInflater; 
import android.view.View;
import android.view.ViewGroup;  
import android.widget.ImageView; 
import android.widget.ListView;
import android.widget.SimpleAdapter; 
import android.widget.TextView; 

public class VideoSimpleAdapter extends SimpleAdapter { 
	/*需要绑定的控件资源id*/
    private int[] mTo;
    /*map集合关键字数组*/
    private String[] mFrom;
/*需要绑定的数据*/
    private List<? extends Map<String, ?>> mData; 

    private LayoutInflater mInflater;
    Context context = null;

    private ListView mListView;
    //图片异步缓存加载类,带内存缓存和文件缓存
    private SyncImageLoader syncImageLoader;

    public VideoSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to,ListView listView) { 
        super(context, data, resource, from, to); 
        mTo = to; 
        mFrom = from; 
        mData = data;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context= context;
        mListView = listView; 
        syncImageLoader = SyncImageLoader.getInstance();
        ListViewOnScrollListener onScrollListener = new ListViewOnScrollListener(syncImageLoader,listView,getCount());
        mListView.setOnScrollListener(onScrollListener);
    } 

  public View getView(int position, View convertView, ViewGroup parent) { 
	  ViewHolder holder = null;
	  ///*第一次装载这个view时=null,就新建一个调用inflate渲染一个view*/
	  if (convertView == null) convertView = mInflater.inflate(R.layout.video_list_item, null);
	  convertView.setTag("listViewTAG" + position);
	  holder = new ViewHolder(); 
	  /*绑定该view各个控件*/
	  holder.tv_title = (TextView)convertView.findViewById(R.id.tv_title);
	  holder.tv_videoTypeObj = (TextView)convertView.findViewById(R.id.tv_videoTypeObj);
	  holder.iv_videoPhoto = (ImageView)convertView.findViewById(R.id.iv_videoPhoto);
	  holder.tv_sportPos = (TextView)convertView.findViewById(R.id.tv_sportPos);
	  holder.tv_hitNum = (TextView)convertView.findViewById(R.id.tv_hitNum);
	  holder.tv_publishTime = (TextView)convertView.findViewById(R.id.tv_publishTime);
	  /*设置各个控件的展示内容*/
	  holder.tv_title.setText("教学标题：" + mData.get(position).get("title").toString());
	  holder.tv_videoTypeObj.setText("教学类别：" + (new VideoTypeService()).GetVideoType(Integer.parseInt(mData.get(position).get("videoTypeObj").toString())).getTypeName());
	  holder.iv_videoPhoto.setImageResource(R.drawable.default_photo);
	  ImageLoadListener videoPhotoLoadListener = new ImageLoadListener(mListView,R.id.iv_videoPhoto);
	  syncImageLoader.loadImage(position,(String)mData.get(position).get("videoPhoto"),videoPhotoLoadListener);  
	  holder.tv_sportPos.setText("所打位置：" + mData.get(position).get("sportPos").toString());
	  holder.tv_hitNum.setText("点击率：" + mData.get(position).get("hitNum").toString());
	  holder.tv_publishTime.setText("发布时间：" + mData.get(position).get("publishTime").toString());
	  /*返回修改好的view*/
	  return convertView; 
    } 

    static class ViewHolder{ 
    	TextView tv_title;
    	TextView tv_videoTypeObj;
    	ImageView iv_videoPhoto;
    	TextView tv_sportPos;
    	TextView tv_hitNum;
    	TextView tv_publishTime;
    }
} 
