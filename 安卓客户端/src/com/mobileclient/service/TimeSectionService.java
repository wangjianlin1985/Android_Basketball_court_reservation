package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.TimeSection;
import com.mobileclient.util.HttpUtil;

/*时段信息管理业务逻辑层*/
public class TimeSectionService {
	/* 添加时段信息 */
	public String AddTimeSection(TimeSection timeSection) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("sectionId", timeSection.getSectionId() + "");
		params.put("sectionName", timeSection.getSectionName());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "TimeSectionServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询时段信息 */
	public List<TimeSection> QueryTimeSection(TimeSection queryConditionTimeSection) throws Exception {
		String urlString = HttpUtil.BASE_URL + "TimeSectionServlet?action=query";
		if(queryConditionTimeSection != null) {
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		TimeSectionListHandler timeSectionListHander = new TimeSectionListHandler();
		xr.setContentHandler(timeSectionListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<TimeSection> timeSectionList = timeSectionListHander.getTimeSectionList();
		return timeSectionList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<TimeSection> timeSectionList = new ArrayList<TimeSection>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				TimeSection timeSection = new TimeSection();
				timeSection.setSectionId(object.getInt("sectionId"));
				timeSection.setSectionName(object.getString("sectionName"));
				timeSectionList.add(timeSection);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return timeSectionList;
	}

	/* 更新时段信息 */
	public String UpdateTimeSection(TimeSection timeSection) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("sectionId", timeSection.getSectionId() + "");
		params.put("sectionName", timeSection.getSectionName());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "TimeSectionServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除时段信息 */
	public String DeleteTimeSection(int sectionId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("sectionId", sectionId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "TimeSectionServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "时段信息信息删除失败!";
		}
	}

	/* 根据记录id获取时段信息对象 */
	public TimeSection GetTimeSection(int sectionId)  {
		List<TimeSection> timeSectionList = new ArrayList<TimeSection>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("sectionId", sectionId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "TimeSectionServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				TimeSection timeSection = new TimeSection();
				timeSection.setSectionId(object.getInt("sectionId"));
				timeSection.setSectionName(object.getString("sectionName"));
				timeSectionList.add(timeSection);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = timeSectionList.size();
		if(size>0) return timeSectionList.get(0); 
		else return null; 
	}
}
