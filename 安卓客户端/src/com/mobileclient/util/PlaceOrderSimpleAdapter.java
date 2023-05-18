package com.mobileclient.util;

import java.util.List;  
import java.util.Map;

import com.mobileclient.service.PlaceService;
import com.mobileclient.service.TimeSectionService;
import com.mobileclient.service.UserInfoService;
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

public class PlaceOrderSimpleAdapter extends SimpleAdapter { 
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

    public PlaceOrderSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to,ListView listView) { 
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
	  if (convertView == null) convertView = mInflater.inflate(R.layout.placeorder_list_item, null);
	  convertView.setTag("listViewTAG" + position);
	  holder = new ViewHolder(); 
	  /*绑定该view各个控件*/
	  holder.tv_placeObj = (TextView)convertView.findViewById(R.id.tv_placeObj);
	  holder.tv_orderDate = (TextView)convertView.findViewById(R.id.tv_orderDate);
	  holder.tv_timeSectionObj = (TextView)convertView.findViewById(R.id.tv_timeSectionObj);
	  holder.tv_userObj = (TextView)convertView.findViewById(R.id.tv_userObj);
	  holder.tv_orderTime = (TextView)convertView.findViewById(R.id.tv_orderTime);
	  holder.tv_shenHeState = (TextView)convertView.findViewById(R.id.tv_shenHeState);
	  holder.tv_shenHeTime = (TextView)convertView.findViewById(R.id.tv_shenHeTime);
	  /*设置各个控件的展示内容*/
	  holder.tv_placeObj.setText("预订球场：" + (new PlaceService()).GetPlace(Integer.parseInt(mData.get(position).get("placeObj").toString())).getPlaceName());
	  try {holder.tv_orderDate.setText("预订日期：" + mData.get(position).get("orderDate").toString().substring(0, 10));} catch(Exception ex){}
	  holder.tv_timeSectionObj.setText("预订时段：" + (new TimeSectionService()).GetTimeSection(Integer.parseInt(mData.get(position).get("timeSectionObj").toString())).getSectionName());
	  holder.tv_userObj.setText("预订人：" + (new UserInfoService()).GetUserInfo(mData.get(position).get("userObj").toString()).getName());
	  holder.tv_orderTime.setText("预订时间：" + mData.get(position).get("orderTime").toString());
	  holder.tv_shenHeState.setText("审核状态：" + mData.get(position).get("shenHeState").toString());
	  holder.tv_shenHeTime.setText("审核时间：" + mData.get(position).get("shenHeTime").toString());
	  /*返回修改好的view*/
	  return convertView; 
    } 

    static class ViewHolder{ 
    	TextView tv_placeObj;
    	TextView tv_orderDate;
    	TextView tv_timeSectionObj;
    	TextView tv_userObj;
    	TextView tv_orderTime;
    	TextView tv_shenHeState;
    	TextView tv_shenHeTime;
    }
} 
