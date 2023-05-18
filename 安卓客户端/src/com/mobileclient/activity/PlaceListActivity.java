package com.mobileclient.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mobileclient.app.Declare;
import com.mobileclient.domain.Place;
import com.mobileclient.service.PlaceService;
import com.mobileclient.util.ActivityUtils;import com.mobileclient.util.PlaceSimpleAdapter;
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

public class PlaceListActivity extends Activity {
	PlaceSimpleAdapter adapter;
	ListView lv; 
	List<Map<String, Object>> list;
	int placeId;
	/* 篮球场地操作业务逻辑层对象 */
	PlaceService placeService = new PlaceService();
	/*保存查询参数条件的篮球场地对象*/
	private Place queryConditionPlace;

	private MyProgressDialog dialog; //进度条	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		setContentView(R.layout.place_list);
		dialog = MyProgressDialog.getInstance(this);
		Declare declare = (Declare) getApplicationContext();
		String username = declare.getUserName();
		//标题栏控件
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(PlaceListActivity.this, PlaceQueryActivity.class);
				startActivityForResult(intent,ActivityUtils.QUERY_CODE);//此处的requestCode应与下面结果处理函中调用的requestCode一致
			}
		});
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("篮球场地查询列表");
		ImageView add_btn = (ImageView) this.findViewById(R.id.add_btn);
		add_btn.setOnClickListener(new android.view.View.OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(PlaceListActivity.this, PlaceAddActivity.class);
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
        		queryConditionPlace = (Place)extras.getSerializable("queryConditionPlace");
        	setViews();
        }
        if(requestCode==ActivityUtils.EDIT_CODE && resultCode==RESULT_OK){
        	setViews();
        }
        if(requestCode == ActivityUtils.ADD_CODE && resultCode == RESULT_OK) {
        	queryConditionPlace = null;
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
						adapter = new PlaceSimpleAdapter(PlaceListActivity.this, list,
	        					R.layout.place_list_item,
	        					new String[] { "placeName","placePhoto","placePos","telephone","placePrice","addTime" },
	        					new int[] { R.id.tv_placeName,R.id.iv_placePhoto,R.id.tv_placePos,R.id.tv_telephone,R.id.tv_placePrice,R.id.tv_sellNum,},lv);
	        			lv.setAdapter(adapter);
					}
				});
			}
		}.start(); 

		// 添加长按点击
		lv.setOnCreateContextMenuListener(placeListItemListener);
		lv.setOnItemClickListener(new OnItemClickListener(){
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
            	int placeId = Integer.parseInt(list.get(arg2).get("placeId").toString());
            	Intent intent = new Intent();
            	intent.setClass(PlaceListActivity.this, PlaceDetailActivity.class);
            	Bundle bundle = new Bundle();
            	bundle.putInt("placeId", placeId);
            	intent.putExtras(bundle);
            	startActivity(intent);
            }
        });
	}
	private OnCreateContextMenuListener placeListItemListener = new OnCreateContextMenuListener() {
		public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
			Declare declare = (Declare) PlaceListActivity.this.getApplication();
			if(declare.getIdentify().equals("admin")) {
				menu.add(0, 0, 0, "编辑篮球场地信息"); 
				menu.add(0, 1, 0, "删除篮球场地信息");
			}
			
		}
	};

	// 长按菜单响应函数
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if (item.getItemId() == 0) {  //编辑篮球场地信息
			ContextMenuInfo info = item.getMenuInfo();
			AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) info;
			// 获取选中行位置
			int position = contextMenuInfo.position;
			// 获取场地id
			placeId = Integer.parseInt(list.get(position).get("placeId").toString());
			Intent intent = new Intent();
			intent.setClass(PlaceListActivity.this, PlaceEditActivity.class);
			Bundle bundle = new Bundle();
			bundle.putInt("placeId", placeId);
			intent.putExtras(bundle);
			startActivityForResult(intent,ActivityUtils.EDIT_CODE);
		} else if (item.getItemId() == 1) {// 删除篮球场地信息
			ContextMenuInfo info = item.getMenuInfo();
			AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) info;
			// 获取选中行位置
			int position = contextMenuInfo.position;
			// 获取场地id
			placeId = Integer.parseInt(list.get(position).get("placeId").toString());
			dialog();
		}
		return super.onContextItemSelected(item);
	}

	// 删除
	protected void dialog() {
		Builder builder = new Builder(PlaceListActivity.this);
		builder.setMessage("确认删除吗？");
		builder.setTitle("提示");
		builder.setPositiveButton("确认", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				String result = placeService.DeletePlace(placeId);
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
			/* 查询篮球场地信息 */
			List<Place> placeList = placeService.QueryPlace(queryConditionPlace);
			for (int i = 0; i < placeList.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("placeId",placeList.get(i).getPlaceId());
				map.put("placeName", placeList.get(i).getPlaceName());
				/*byte[] placePhoto_data = ImageService.getImage(HttpUtil.BASE_URL+ placeList.get(i).getPlacePhoto());// 获取图片数据
				BitmapFactory.Options placePhoto_opts = new BitmapFactory.Options();  
				placePhoto_opts.inJustDecodeBounds = true;  
				BitmapFactory.decodeByteArray(placePhoto_data, 0, placePhoto_data.length, placePhoto_opts); 
				placePhoto_opts.inSampleSize = photoListActivity.computeSampleSize(placePhoto_opts, -1, 100*100); 
				placePhoto_opts.inJustDecodeBounds = false; 
				try {
					Bitmap placePhoto = BitmapFactory.decodeByteArray(placePhoto_data, 0, placePhoto_data.length, placePhoto_opts);
					map.put("placePhoto", placePhoto);
				} catch (OutOfMemoryError err) { }*/
				map.put("placePhoto", HttpUtil.BASE_URL+ placeList.get(i).getPlacePhoto());
				map.put("placePos", placeList.get(i).getPlacePos());
				map.put("telephone", placeList.get(i).getTelephone());
				map.put("placePrice", placeList.get(i).getPlacePrice());
				map.put("topFlag",placeList.get(i).getTopFlag()==1?"是":"否");
				map.put("sellNum", placeList.get(i).getSellNum());
				map.put("addTime", placeList.get(i).getAddTime());
				list.add(map);
			}
		} catch (Exception e) { 
			Toast.makeText(getApplicationContext(), "", 1).show();
		}
		return list;
	}

}
