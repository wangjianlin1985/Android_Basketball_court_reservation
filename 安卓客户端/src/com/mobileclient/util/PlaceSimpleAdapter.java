package com.mobileclient.util;

import java.util.List;  
import java.util.Map;

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

public class PlaceSimpleAdapter extends SimpleAdapter { 
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

    public PlaceSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to,ListView listView) { 
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
	  if (convertView == null) convertView = mInflater.inflate(R.layout.place_list_item, null);
	  convertView.setTag("listViewTAG" + position);
	  holder = new ViewHolder(); 
	  /*绑定该view各个控件*/
	  holder.tv_placeName = (TextView)convertView.findViewById(R.id.tv_placeName);
	  holder.iv_placePhoto = (ImageView)convertView.findViewById(R.id.iv_placePhoto);
	  holder.tv_placePos = (TextView)convertView.findViewById(R.id.tv_placePos);
	  holder.tv_topFlag = (TextView)convertView.findViewById(R.id.tv_topFlag);
	  holder.tv_placePrice = (TextView)convertView.findViewById(R.id.tv_placePrice);
	  holder.tv_sellNum = (TextView)convertView.findViewById(R.id.tv_sellNum);
	  /*设置各个控件的展示内容*/
	  holder.tv_placeName.setText("场地名称：" + mData.get(position).get("placeName").toString());
	  holder.iv_placePhoto.setImageResource(R.drawable.default_photo);
	  ImageLoadListener placePhotoLoadListener = new ImageLoadListener(mListView,R.id.iv_placePhoto);
	  syncImageLoader.loadImage(position,(String)mData.get(position).get("placePhoto"),placePhotoLoadListener);  
	  holder.tv_placePos.setText("球场地址：" + mData.get(position).get("placePos").toString());
	  holder.tv_topFlag.setText("是否置顶：" + mData.get(position).get("topFlag").toString());
	  holder.tv_placePrice.setText("球场价格：" + mData.get(position).get("placePrice").toString());
	  holder.tv_sellNum.setText("人气销量：" + mData.get(position).get("sellNum").toString());
	  /*返回修改好的view*/
	  return convertView; 
    } 

    static class ViewHolder{ 
    	TextView tv_placeName;
    	ImageView iv_placePhoto;
    	TextView tv_placePos;
    	TextView tv_topFlag;
    	TextView tv_placePrice;
    	TextView tv_sellNum;
    }
} 
