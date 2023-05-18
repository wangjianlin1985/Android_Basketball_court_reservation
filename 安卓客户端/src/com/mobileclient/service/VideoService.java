package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.Video;
import com.mobileclient.util.HttpUtil;

/*篮球教学管理业务逻辑层*/
public class VideoService {
	/* 添加篮球教学 */
	public String AddVideo(Video video) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("videoId", video.getVideoId() + "");
		params.put("title", video.getTitle());
		params.put("videoTypeObj", video.getVideoTypeObj() + "");
		params.put("videoPhoto", video.getVideoPhoto());
		params.put("content", video.getContent());
		params.put("sportPos", video.getSportPos());
		params.put("videoFile", video.getVideoFile());
		params.put("hitNum", video.getHitNum() + "");
		params.put("publishTime", video.getPublishTime());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "VideoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询篮球教学 */
	public List<Video> QueryVideo(Video queryConditionVideo) throws Exception {
		String urlString = HttpUtil.BASE_URL + "VideoServlet?action=query";
		if(queryConditionVideo != null) {
			urlString += "&title=" + URLEncoder.encode(queryConditionVideo.getTitle(), "UTF-8") + "";
			urlString += "&videoTypeObj=" + queryConditionVideo.getVideoTypeObj();
			urlString += "&sportPos=" + URLEncoder.encode(queryConditionVideo.getSportPos(), "UTF-8") + "";
			urlString += "&publishTime=" + URLEncoder.encode(queryConditionVideo.getPublishTime(), "UTF-8") + "";
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		VideoListHandler videoListHander = new VideoListHandler();
		xr.setContentHandler(videoListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<Video> videoList = videoListHander.getVideoList();
		return videoList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<Video> videoList = new ArrayList<Video>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Video video = new Video();
				video.setVideoId(object.getInt("videoId"));
				video.setTitle(object.getString("title"));
				video.setVideoTypeObj(object.getInt("videoTypeObj"));
				video.setVideoPhoto(object.getString("videoPhoto"));
				video.setContent(object.getString("content"));
				video.setSportPos(object.getString("sportPos"));
				video.setVideoFile(object.getString("videoFile"));
				video.setHitNum(object.getInt("hitNum"));
				video.setPublishTime(object.getString("publishTime"));
				videoList.add(video);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return videoList;
	}

	/* 更新篮球教学 */
	public String UpdateVideo(Video video) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("videoId", video.getVideoId() + "");
		params.put("title", video.getTitle());
		params.put("videoTypeObj", video.getVideoTypeObj() + "");
		params.put("videoPhoto", video.getVideoPhoto());
		params.put("content", video.getContent());
		params.put("sportPos", video.getSportPos());
		params.put("videoFile", video.getVideoFile());
		params.put("hitNum", video.getHitNum() + "");
		params.put("publishTime", video.getPublishTime());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "VideoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除篮球教学 */
	public String DeleteVideo(int videoId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("videoId", videoId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "VideoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "篮球教学信息删除失败!";
		}
	}

	/* 根据记录id获取篮球教学对象 */
	public Video GetVideo(int videoId)  {
		List<Video> videoList = new ArrayList<Video>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("videoId", videoId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "VideoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Video video = new Video();
				video.setVideoId(object.getInt("videoId"));
				video.setTitle(object.getString("title"));
				video.setVideoTypeObj(object.getInt("videoTypeObj"));
				video.setVideoPhoto(object.getString("videoPhoto"));
				video.setContent(object.getString("content"));
				video.setSportPos(object.getString("sportPos"));
				video.setVideoFile(object.getString("videoFile"));
				video.setHitNum(object.getInt("hitNum"));
				video.setPublishTime(object.getString("publishTime"));
				videoList.add(video);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = videoList.size();
		if(size>0) return videoList.get(0); 
		else return null; 
	}
}
