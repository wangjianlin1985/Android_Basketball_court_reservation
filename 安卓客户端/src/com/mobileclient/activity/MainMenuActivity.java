package com.mobileclient.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class MainMenuActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("手机客户端-主界面");
        setContentView(R.layout.main_menu);
        GridView gridview = (GridView) findViewById(R.id.gridview);
        
        AnimationSet set = new AnimationSet(false);
        Animation animation = new AlphaAnimation(0,1);
        animation.setDuration(500);
        set.addAnimation(animation);
        
        animation = new TranslateAnimation(1, 13, 10, 50);
        animation.setDuration(300);
        set.addAnimation(animation);
        
        animation = new RotateAnimation(30,10);
        animation.setDuration(300);
        set.addAnimation(animation);
        
        animation = new ScaleAnimation(5,0,2,0);
        animation.setDuration(300);
        set.addAnimation(animation);
        
        LayoutAnimationController controller = new LayoutAnimationController(set, 1);
        
        gridview.setLayoutAnimation(controller);
        
        gridview.setAdapter(new ImageAdapter(this));
    }
    
    // 继承BaseAdapter
    public class ImageAdapter extends BaseAdapter {
    	
    	LayoutInflater inflater;
    	
    	// 上下文
        private Context mContext;
        
        // 图片资源数组
        private Integer[] mThumbIds = {
                R.drawable.operateicon,R.drawable.operateicon,R.drawable.operateicon,R.drawable.operateicon,R.drawable.operateicon,R.drawable.operateicon,R.drawable.operateicon,R.drawable.operateicon
        };
        private String[] menuString = {"用户管理","篮球教学管理","视频类型管理","篮球场地管理","场地预订管理","时段信息管理","篮球新闻管理","约战留言管理"};

        // 构造方法
        public ImageAdapter(Context c) {
            mContext = c;
            inflater = LayoutInflater.from(mContext);
        }
        // 组件个数
        public int getCount() {
            return mThumbIds.length;
        }
        // 当前组件
        public Object getItem(int position) {
            return null;
        }
        // 当前组件id
        public long getItemId(int position) {
            return 0;
        }
        // 获得当前视图
        public View getView(int position, View convertView, ViewGroup parent) { 
        	View view = inflater.inflate(R.layout.gv_item, null);
			TextView tv = (TextView) view.findViewById(R.id.gv_item_appname);
			ImageView iv = (ImageView) view.findViewById(R.id.gv_item_icon);  
			tv.setText(menuString[position]); 
			iv.setImageResource(mThumbIds[position]); 
			  switch (position) {
				case 0:
					// 用户管理监听器
					view.setOnClickListener(userInfoLinstener);
					break;
				case 1:
					// 篮球教学管理监听器
					view.setOnClickListener(videoLinstener);
					break;
				case 2:
					// 视频类型管理监听器
					view.setOnClickListener(videoTypeLinstener);
					break;
				case 3:
					// 篮球场地管理监听器
					view.setOnClickListener(placeLinstener);
					break;
				case 4:
					// 场地预订管理监听器
					view.setOnClickListener(placeOrderLinstener);
					break;
				case 5:
					// 时段信息管理监听器
					view.setOnClickListener(timeSectionLinstener);
					break;
				case 6:
					// 篮球新闻管理监听器
					view.setOnClickListener(newsLinstener);
					break;
				case 7:
					// 约战留言管理监听器
					view.setOnClickListener(leavewordLinstener);
					break;

			 
				default:
					break;
				} 
			return view; 
        }
       
    }
    
    OnClickListener userInfoLinstener = new OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent();
			// 启动用户管理Activity
			intent.setClass(MainMenuActivity.this, UserInfoListActivity.class);
			startActivity(intent);
		}
	};
    OnClickListener videoLinstener = new OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent();
			// 启动篮球教学管理Activity
			intent.setClass(MainMenuActivity.this, VideoListActivity.class);
			startActivity(intent);
		}
	};
    OnClickListener videoTypeLinstener = new OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent();
			// 启动视频类型管理Activity
			intent.setClass(MainMenuActivity.this, VideoTypeListActivity.class);
			startActivity(intent);
		}
	};
    OnClickListener placeLinstener = new OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent();
			// 启动篮球场地管理Activity
			intent.setClass(MainMenuActivity.this, PlaceListActivity.class);
			startActivity(intent);
		}
	};
    OnClickListener placeOrderLinstener = new OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent();
			// 启动场地预订管理Activity
			intent.setClass(MainMenuActivity.this, PlaceOrderListActivity.class);
			startActivity(intent);
		}
	};
    OnClickListener timeSectionLinstener = new OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent();
			// 启动时段信息管理Activity
			intent.setClass(MainMenuActivity.this, TimeSectionListActivity.class);
			startActivity(intent);
		}
	};
    OnClickListener newsLinstener = new OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent();
			// 启动篮球新闻管理Activity
			intent.setClass(MainMenuActivity.this, NewsListActivity.class);
			startActivity(intent);
		}
	};
    OnClickListener leavewordLinstener = new OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent();
			// 启动约战留言管理Activity
			intent.setClass(MainMenuActivity.this, LeavewordListActivity.class);
			startActivity(intent);
		}
	};


	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		menu.add(0, 1, 1, "重新登入");  
		menu.add(0, 2, 2, "退出"); 
		return super.onCreateOptionsMenu(menu); 
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if (item.getItemId() == 1) {//重新登入 
			Intent intent = new Intent();
			intent.setClass(MainMenuActivity.this,
					LoginActivity.class);
			startActivity(intent);
		} else if (item.getItemId() == 2) {//退出
			System.exit(0);  
		} 
		return true; 
	}
}
