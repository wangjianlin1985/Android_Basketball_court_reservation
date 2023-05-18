package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import com.mobileclient.domain.Place;
import com.mobileclient.service.PlaceService;
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

public class PlaceEditActivity extends Activity {
	// 声明确定添加按钮
	private Button btnUpdate;
	// 声明场地idTextView
	private TextView TV_placeId;
	// 声明场地名称输入框
	private EditText ET_placeName;
	// 声明球场图片图片框控件
	private ImageView iv_placePhoto;
	private Button btn_placePhoto;
	protected int REQ_CODE_SELECT_IMAGE_placePhoto = 1;
	private int REQ_CODE_CAMERA_placePhoto = 2;
	// 声明球场地址输入框
	private EditText ET_placePos;
	// 声明联系电话输入框
	private EditText ET_telephone;
	// 声明球场价格输入框
	private EditText ET_placePrice;
	// 声明球场介绍输入框
	private EditText ET_placeDesc;
	// 声明营业时间输入框
	private EditText ET_onlineTime;
	// 声明是否置顶输入框
	private EditText ET_topFlag;
	// 声明发布时间输入框
	private EditText ET_addTime;
	protected String carmera_path;
	/*要保存的篮球场地信息*/
	Place place = new Place();
	/*篮球场地管理业务逻辑层*/
	private PlaceService placeService = new PlaceService();

	private int placeId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.place_edit); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("编辑篮球场地信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		TV_placeId = (TextView) findViewById(R.id.TV_placeId);
		ET_placeName = (EditText) findViewById(R.id.ET_placeName);
		iv_placePhoto = (ImageView) findViewById(R.id.iv_placePhoto);
		/*单击图片显示控件时进行图片的选择*/
		iv_placePhoto.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(PlaceEditActivity.this,photoListActivity.class);
				startActivityForResult(intent,REQ_CODE_SELECT_IMAGE_placePhoto);
			}
		});
		btn_placePhoto = (Button) findViewById(R.id.btn_placePhoto);
		btn_placePhoto.setOnClickListener(new OnClickListener() { 
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); 
				carmera_path = HttpUtil.FILE_PATH + "/carmera_placePhoto.bmp";
				File out = new File(carmera_path); 
				intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(out)); 
				startActivityForResult(intent, REQ_CODE_CAMERA_placePhoto);  
			}
		});
		ET_placePos = (EditText) findViewById(R.id.ET_placePos);
		ET_telephone = (EditText) findViewById(R.id.ET_telephone);
		ET_placePrice = (EditText) findViewById(R.id.ET_placePrice);
		ET_placeDesc = (EditText) findViewById(R.id.ET_placeDesc);
		ET_onlineTime = (EditText) findViewById(R.id.ET_onlineTime);
		ET_topFlag = (EditText) findViewById(R.id.ET_topFlag);
		ET_addTime = (EditText) findViewById(R.id.ET_addTime);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		placeId = extras.getInt("placeId");
		/*单击修改篮球场地按钮*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取场地名称*/ 
					if(ET_placeName.getText().toString().equals("")) {
						Toast.makeText(PlaceEditActivity.this, "场地名称输入不能为空!", Toast.LENGTH_LONG).show();
						ET_placeName.setFocusable(true);
						ET_placeName.requestFocus();
						return;	
					}
					place.setPlaceName(ET_placeName.getText().toString());
					if (!place.getPlacePhoto().startsWith("upload/")) {
						//如果图片地址不为空，说明用户选择了图片，这时需要连接服务器上传图片
						PlaceEditActivity.this.setTitle("正在上传图片，稍等...");
						String placePhoto = HttpUtil.uploadFile(place.getPlacePhoto());
						PlaceEditActivity.this.setTitle("图片上传完毕！");
						place.setPlacePhoto(placePhoto);
					} 
					/*验证获取球场地址*/ 
					if(ET_placePos.getText().toString().equals("")) {
						Toast.makeText(PlaceEditActivity.this, "球场地址输入不能为空!", Toast.LENGTH_LONG).show();
						ET_placePos.setFocusable(true);
						ET_placePos.requestFocus();
						return;	
					}
					place.setPlacePos(ET_placePos.getText().toString());
					/*验证获取联系电话*/ 
					if(ET_telephone.getText().toString().equals("")) {
						Toast.makeText(PlaceEditActivity.this, "联系电话输入不能为空!", Toast.LENGTH_LONG).show();
						ET_telephone.setFocusable(true);
						ET_telephone.requestFocus();
						return;	
					}
					place.setTelephone(ET_telephone.getText().toString());
					/*验证获取球场价格*/ 
					if(ET_placePrice.getText().toString().equals("")) {
						Toast.makeText(PlaceEditActivity.this, "球场价格输入不能为空!", Toast.LENGTH_LONG).show();
						ET_placePrice.setFocusable(true);
						ET_placePrice.requestFocus();
						return;	
					}
					place.setPlacePrice(Float.parseFloat(ET_placePrice.getText().toString()));
					/*验证获取球场介绍*/ 
					if(ET_placeDesc.getText().toString().equals("")) {
						Toast.makeText(PlaceEditActivity.this, "球场介绍输入不能为空!", Toast.LENGTH_LONG).show();
						ET_placeDesc.setFocusable(true);
						ET_placeDesc.requestFocus();
						return;	
					}
					place.setPlaceDesc(ET_placeDesc.getText().toString());
					/*验证获取营业时间*/ 
					if(ET_onlineTime.getText().toString().equals("")) {
						Toast.makeText(PlaceEditActivity.this, "营业时间输入不能为空!", Toast.LENGTH_LONG).show();
						ET_onlineTime.setFocusable(true);
						ET_onlineTime.requestFocus();
						return;	
					}
					place.setOnlineTime(ET_onlineTime.getText().toString());
					/*验证获取是否置顶*/ 
					if(ET_topFlag.getText().toString().equals("")) {
						Toast.makeText(PlaceEditActivity.this, "是否置顶输入不能为空!", Toast.LENGTH_LONG).show();
						ET_topFlag.setFocusable(true);
						ET_topFlag.requestFocus();
						return;	
					}
					place.setTopFlag(Integer.parseInt(ET_topFlag.getText().toString()));
					/*验证获取发布时间*/ 
					if(ET_addTime.getText().toString().equals("")) {
						Toast.makeText(PlaceEditActivity.this, "发布时间输入不能为空!", Toast.LENGTH_LONG).show();
						ET_addTime.setFocusable(true);
						ET_addTime.requestFocus();
						return;	
					}
					place.setAddTime(ET_addTime.getText().toString());
					/*调用业务逻辑层上传篮球场地信息*/
					PlaceEditActivity.this.setTitle("正在更新篮球场地信息，稍等...");
					String result = placeService.UpdatePlace(place);
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
	    place = placeService.GetPlace(placeId);
		this.TV_placeId.setText(placeId+"");
		this.ET_placeName.setText(place.getPlaceName());
		byte[] placePhoto_data = null;
		try {
			// 获取图片数据
			placePhoto_data = ImageService.getImage(HttpUtil.BASE_URL + place.getPlacePhoto());
			Bitmap placePhoto = BitmapFactory.decodeByteArray(placePhoto_data, 0, placePhoto_data.length);
			this.iv_placePhoto.setImageBitmap(placePhoto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.ET_placePos.setText(place.getPlacePos());
		this.ET_telephone.setText(place.getTelephone());
		this.ET_placePrice.setText(place.getPlacePrice() + "");
		this.ET_placeDesc.setText(place.getPlaceDesc());
		this.ET_onlineTime.setText(place.getOnlineTime());
		this.ET_topFlag.setText(place.getTopFlag() + "");
		this.ET_addTime.setText(place.getAddTime());
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQ_CODE_CAMERA_placePhoto  && resultCode == Activity.RESULT_OK) {
			carmera_path = HttpUtil.FILE_PATH + "/carmera_placePhoto.bmp"; 
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(carmera_path, opts); 
			opts.inSampleSize = photoListActivity.computeSampleSize(opts, -1, 300*300);
			opts.inJustDecodeBounds = false;
			try {
				Bitmap booImageBm = BitmapFactory.decodeFile(carmera_path, opts);
				String jpgFileName = "carmera_placePhoto.jpg";
				String jpgFilePath =  HttpUtil.FILE_PATH + "/" + jpgFileName;
				try {
					FileOutputStream jpgOutputStream = new FileOutputStream(jpgFilePath);
					booImageBm.compress(Bitmap.CompressFormat.JPEG, 30, jpgOutputStream);// 把数据写入文件 
					File bmpFile = new File(carmera_path);
					bmpFile.delete();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} 
				this.iv_placePhoto.setImageBitmap(booImageBm);
				this.iv_placePhoto.setScaleType(ScaleType.FIT_CENTER);
				this.place.setPlacePhoto(jpgFileName);
			} catch (OutOfMemoryError err) {  }
		}

		if(requestCode == REQ_CODE_SELECT_IMAGE_placePhoto && resultCode == Activity.RESULT_OK) {
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
				this.iv_placePhoto.setImageBitmap(bm); 
				this.iv_placePhoto.setScaleType(ScaleType.FIT_CENTER); 
			} catch (OutOfMemoryError err) {  } 
			place.setPlacePhoto(filename); 
		}
	}
}
