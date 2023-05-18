package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.Place;
import com.mobileclient.util.HttpUtil;

/*篮球场地管理业务逻辑层*/
public class PlaceService {
	/* 添加篮球场地 */
	public String AddPlace(Place place) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("placeId", place.getPlaceId() + "");
		params.put("placeName", place.getPlaceName());
		params.put("placePhoto", place.getPlacePhoto());
		params.put("placePos", place.getPlacePos());
		params.put("telephone", place.getTelephone());
		params.put("placePrice", place.getPlacePrice() + "");
		params.put("placeDesc", place.getPlaceDesc());
		params.put("onlineTime", place.getOnlineTime());
		params.put("topFlag", place.getTopFlag() + "");
		params.put("addTime", place.getAddTime());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "PlaceServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询篮球场地 */
	public List<Place> QueryPlace(Place queryConditionPlace) throws Exception {
		String urlString = HttpUtil.BASE_URL + "PlaceServlet?action=query";
		if(queryConditionPlace != null) {
			urlString += "&placeName=" + URLEncoder.encode(queryConditionPlace.getPlaceName(), "UTF-8") + "";
			urlString += "&placePos=" + URLEncoder.encode(queryConditionPlace.getPlacePos(), "UTF-8") + "";
			urlString += "&telephone=" + URLEncoder.encode(queryConditionPlace.getTelephone(), "UTF-8") + "";
			urlString += "&addTime=" + URLEncoder.encode(queryConditionPlace.getAddTime(), "UTF-8") + "";
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		PlaceListHandler placeListHander = new PlaceListHandler();
		xr.setContentHandler(placeListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<Place> placeList = placeListHander.getPlaceList();
		return placeList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<Place> placeList = new ArrayList<Place>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Place place = new Place();
				place.setPlaceId(object.getInt("placeId"));
				place.setPlaceName(object.getString("placeName"));
				place.setPlacePhoto(object.getString("placePhoto"));
				place.setPlacePos(object.getString("placePos"));
				place.setTelephone(object.getString("telephone"));
				place.setPlacePrice((float) object.getDouble("placePrice"));
				place.setPlaceDesc(object.getString("placeDesc"));
				place.setOnlineTime(object.getString("onlineTime"));
				place.setSellNum(object.getInt("sellNum"));
				place.setTopFlag(object.getInt("topFlag"));
				place.setAddTime(object.getString("addTime"));
				placeList.add(place);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return placeList;
	}

	/* 更新篮球场地 */
	public String UpdatePlace(Place place) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("placeId", place.getPlaceId() + "");
		params.put("placeName", place.getPlaceName());
		params.put("placePhoto", place.getPlacePhoto());
		params.put("placePos", place.getPlacePos());
		params.put("telephone", place.getTelephone());
		params.put("placePrice", place.getPlacePrice() + "");
		params.put("placeDesc", place.getPlaceDesc());
		params.put("onlineTime", place.getOnlineTime());
		params.put("topFlag", place.getTopFlag() + "");
		params.put("addTime", place.getAddTime());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "PlaceServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除篮球场地 */
	public String DeletePlace(int placeId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("placeId", placeId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "PlaceServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "篮球场地信息删除失败!";
		}
	}

	/* 根据场地id获取篮球场地对象 */
	public Place GetPlace(int placeId)  {
		List<Place> placeList = new ArrayList<Place>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("placeId", placeId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "PlaceServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Place place = new Place();
				place.setPlaceId(object.getInt("placeId"));
				place.setPlaceName(object.getString("placeName"));
				place.setPlacePhoto(object.getString("placePhoto"));
				place.setPlacePos(object.getString("placePos"));
				place.setTelephone(object.getString("telephone"));
				place.setPlacePrice((float) object.getDouble("placePrice"));
				place.setPlaceDesc(object.getString("placeDesc"));
				place.setOnlineTime(object.getString("onlineTime"));
				place.setTopFlag(object.getInt("topFlag"));
				place.setAddTime(object.getString("addTime"));
				placeList.add(place);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = placeList.size();
		if(size>0) return placeList.get(0); 
		else return null; 
	}
}
