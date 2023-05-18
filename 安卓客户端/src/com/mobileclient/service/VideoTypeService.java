package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.VideoType;
import com.mobileclient.util.HttpUtil;

/*视频类型管理业务逻辑层*/
public class VideoTypeService {
	/* 添加视频类型 */
	public String AddVideoType(VideoType videoType) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("typeId", videoType.getTypeId() + "");
		params.put("typeName", videoType.getTypeName());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "VideoTypeServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询视频类型 */
	public List<VideoType> QueryVideoType(VideoType queryConditionVideoType) throws Exception {
		String urlString = HttpUtil.BASE_URL + "VideoTypeServlet?action=query";
		if(queryConditionVideoType != null) {
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		VideoTypeListHandler videoTypeListHander = new VideoTypeListHandler();
		xr.setContentHandler(videoTypeListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<VideoType> videoTypeList = videoTypeListHander.getVideoTypeList();
		return videoTypeList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<VideoType> videoTypeList = new ArrayList<VideoType>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				VideoType videoType = new VideoType();
				videoType.setTypeId(object.getInt("typeId"));
				videoType.setTypeName(object.getString("typeName"));
				videoTypeList.add(videoType);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return videoTypeList;
	}

	/* 更新视频类型 */
	public String UpdateVideoType(VideoType videoType) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("typeId", videoType.getTypeId() + "");
		params.put("typeName", videoType.getTypeName());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "VideoTypeServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除视频类型 */
	public String DeleteVideoType(int typeId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("typeId", typeId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "VideoTypeServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "视频类型信息删除失败!";
		}
	}

	/* 根据类别id获取视频类型对象 */
	public VideoType GetVideoType(int typeId)  {
		List<VideoType> videoTypeList = new ArrayList<VideoType>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("typeId", typeId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "VideoTypeServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				VideoType videoType = new VideoType();
				videoType.setTypeId(object.getInt("typeId"));
				videoType.setTypeName(object.getString("typeName"));
				videoTypeList.add(videoType);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = videoTypeList.size();
		if(size>0) return videoTypeList.get(0); 
		else return null; 
	}
}
