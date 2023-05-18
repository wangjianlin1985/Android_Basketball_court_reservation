package com.mobileclient.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mobileclient.app.Declare;
import com.mobileclient.domain.Leaveword;
import com.mobileclient.service.LeavewordService;
import com.mobileclient.util.ActivityUtils;import com.mobileclient.util.LeavewordSimpleAdapter;
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

public class LeavewordListActivity extends Activity {
	LeavewordSimpleAdapter adapter;
	ListView lv; 
	List<Map<String, Object>> list;
	int leaveWordId;
	/* 约战留言操作业务逻辑层对象 */
	LeavewordService leavewordService = new LeavewordService();
	/*保存查询参数条件的约战留言对象*/
	private Leaveword queryConditionLeaveword;

	private MyProgressDialog dialog; //进度条	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		setContentView(R.layout.leaveword_list);
		dialog = MyProgressDialog.getInstance(this);
		final Declare declare = (Declare) getApplicationContext();
		String username = declare.getUserName();
		//标题栏控件
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(LeavewordListActivity.this, LeavewordQueryActivity.class);
				startActivityForResult(intent,ActivityUtils.QUERY_CODE);//此处的requestCode应与下面结果处理函中调用的requestCode一致
			}
		});
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("约战留言查询列表");
		ImageView add_btn = (ImageView) this.findViewById(R.id.add_btn);
		add_btn.setOnClickListener(new android.view.View.OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				if(declare.getIdentify().equals("admin")) 
					intent.setClass(LeavewordListActivity.this, LeavewordAddActivity.class);
				else
					intent.setClass(LeavewordListActivity.this, LeavewordUserAddActivity.class);
				startActivityForResult(intent,ActivityUtils.ADD_CODE);
			}
		});
		setViews();
	}

	//结果处理函数，当从secondActivity中返回时调用此函数
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==ActivityUtils.QUERY_CODE && resultCode==RESULT_OK){
        	Bundle extras = data.getExtras();
        	if(extras != null)
        		queryConditionLeaveword = (Leaveword)extras.getSerializable("queryConditionLeaveword");
        	setViews();
        }
        if(requestCode==ActivityUtils.EDIT_CODE && resultCode==RESULT_OK){
        	setViews();
        }
        if(requestCode == ActivityUtils.ADD_CODE && resultCode == RESULT_OK) {
        	queryConditionLeaveword = null;
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
						adapter = new LeavewordSimpleAdapter(LeavewordListActivity.this, list,
	        					R.layout.leaveword_list_item,
	        					new String[] { "leaveTitle","telephone","userObj","leaveTime" },
	        					new int[] { R.id.tv_leaveTitle,R.id.tv_telephone,R.id.tv_userObj,R.id.tv_leaveTime,},lv);
	        			lv.setAdapter(adapter);
					}
				});
			}
		}.start(); 

		// 添加长按点击
		lv.setOnCreateContextMenuListener(leavewordListItemListener);
		lv.setOnItemClickListener(new OnItemClickListener(){
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
            	int leaveWordId = Integer.parseInt(list.get(arg2).get("leaveWordId").toString());
            	Intent intent = new Intent();
            	intent.setClass(LeavewordListActivity.this, LeavewordDetailActivity.class);
            	Bundle bundle = new Bundle();
            	bundle.putInt("leaveWordId", leaveWordId);
            	intent.putExtras(bundle);
            	startActivity(intent);
            }
        });
	}
	private OnCreateContextMenuListener leavewordListItemListener = new OnCreateContextMenuListener() {
		public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
			
			AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) menuInfo;
			// 获取选中行位置
			int position = contextMenuInfo.position;
			// 获取约战人id
			String userObj = list.get(position).get("userObj").toString();
			
			Declare declare = (Declare) LeavewordListActivity.this.getApplication();
			if(declare.getIdentify().equals("admin")) {
				menu.add(0, 0, 0, "编辑约战留言信息"); 
				menu.add(0, 1, 0, "删除约战留言信息");
			} else {
				if(declare.getUserName().equals(userObj)) {
					menu.add(0, 0, 0, "删除约战留言信息");
				}
			}
			
			
			
			
		}
	};

	// 长按菜单响应函数
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		
		Declare declare = (Declare) LeavewordListActivity.this.getApplication();
		if(declare.getIdentify().equals("admin")) {
			if (item.getItemId() == 0) {  //编辑约战留言信息
				ContextMenuInfo info = item.getMenuInfo();
				AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) info;
				// 获取选中行位置
				int position = contextMenuInfo.position;
				// 获取留言id
				leaveWordId = Integer.parseInt(list.get(position).get("leaveWordId").toString());
				Intent intent = new Intent();
				intent.setClass(LeavewordListActivity.this, LeavewordEditActivity.class);
				Bundle bundle = new Bundle();
				bundle.putInt("leaveWordId", leaveWordId);
				intent.putExtras(bundle);
				startActivityForResult(intent,ActivityUtils.EDIT_CODE);
			} else if (item.getItemId() == 1) {// 删除约战留言信息
				ContextMenuInfo info = item.getMenuInfo();
				AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) info;
				// 获取选中行位置
				int position = contextMenuInfo.position;
				// 获取留言id
				leaveWordId = Integer.parseInt(list.get(position).get("leaveWordId").toString());
				dialog();
			}
		} else {
			ContextMenuInfo info = item.getMenuInfo();
			AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) info;
			// 获取选中行位置
			int position = contextMenuInfo.position;
			// 获取留言id
			leaveWordId = Integer.parseInt(list.get(position).get("leaveWordId").toString());
			dialog();
		}
		
		return super.onContextItemSelected(item);
	}

	// 删除
	protected void dialog() {
		Builder builder = new Builder(LeavewordListActivity.this);
		builder.setMessage("确认删除吗？");
		builder.setTitle("提示");
		builder.setPositiveButton("确认", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				String result = leavewordService.DeleteLeaveword(leaveWordId);
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
			/* 查询约战留言信息 */
			List<Leaveword> leavewordList = leavewordService.QueryLeaveword(queryConditionLeaveword);
			for (int i = 0; i < leavewordList.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("leaveWordId",leavewordList.get(i).getLeaveWordId());
				map.put("leaveTitle", leavewordList.get(i).getLeaveTitle());
				map.put("telephone", leavewordList.get(i).getTelephone());
				map.put("userObj", leavewordList.get(i).getUserObj());
				map.put("leaveTime", leavewordList.get(i).getLeaveTime());
				list.add(map);
			}
		} catch (Exception e) { 
			Toast.makeText(getApplicationContext(), "", 1).show();
		}
		return list;
	}

}
