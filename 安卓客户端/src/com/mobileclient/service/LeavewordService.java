package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.Leaveword;
import com.mobileclient.util.HttpUtil;

/*约战留言管理业务逻辑层*/
public class LeavewordService {
	/* 添加约战留言 */
	public String AddLeaveword(Leaveword leaveword) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("leaveWordId", leaveword.getLeaveWordId() + "");
		params.put("leaveTitle", leaveword.getLeaveTitle());
		params.put("leaveContent", leaveword.getLeaveContent());
		params.put("telephone", leaveword.getTelephone());
		params.put("userObj", leaveword.getUserObj());
		params.put("leaveTime", leaveword.getLeaveTime());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "LeavewordServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询约战留言 */
	public List<Leaveword> QueryLeaveword(Leaveword queryConditionLeaveword) throws Exception {
		String urlString = HttpUtil.BASE_URL + "LeavewordServlet?action=query";
		if(queryConditionLeaveword != null) {
			urlString += "&leaveTitle=" + URLEncoder.encode(queryConditionLeaveword.getLeaveTitle(), "UTF-8") + "";
			urlString += "&telephone=" + URLEncoder.encode(queryConditionLeaveword.getTelephone(), "UTF-8") + "";
			urlString += "&userObj=" + URLEncoder.encode(queryConditionLeaveword.getUserObj(), "UTF-8") + "";
			urlString += "&leaveTime=" + URLEncoder.encode(queryConditionLeaveword.getLeaveTime(), "UTF-8") + "";
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		LeavewordListHandler leavewordListHander = new LeavewordListHandler();
		xr.setContentHandler(leavewordListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<Leaveword> leavewordList = leavewordListHander.getLeavewordList();
		return leavewordList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<Leaveword> leavewordList = new ArrayList<Leaveword>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Leaveword leaveword = new Leaveword();
				leaveword.setLeaveWordId(object.getInt("leaveWordId"));
				leaveword.setLeaveTitle(object.getString("leaveTitle"));
				leaveword.setLeaveContent(object.getString("leaveContent"));
				leaveword.setTelephone(object.getString("telephone"));
				leaveword.setUserObj(object.getString("userObj"));
				leaveword.setLeaveTime(object.getString("leaveTime"));
				leavewordList.add(leaveword);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return leavewordList;
	}

	/* 更新约战留言 */
	public String UpdateLeaveword(Leaveword leaveword) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("leaveWordId", leaveword.getLeaveWordId() + "");
		params.put("leaveTitle", leaveword.getLeaveTitle());
		params.put("leaveContent", leaveword.getLeaveContent());
		params.put("telephone", leaveword.getTelephone());
		params.put("userObj", leaveword.getUserObj());
		params.put("leaveTime", leaveword.getLeaveTime());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "LeavewordServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除约战留言 */
	public String DeleteLeaveword(int leaveWordId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("leaveWordId", leaveWordId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "LeavewordServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "约战留言信息删除失败!";
		}
	}

	/* 根据留言id获取约战留言对象 */
	public Leaveword GetLeaveword(int leaveWordId)  {
		List<Leaveword> leavewordList = new ArrayList<Leaveword>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("leaveWordId", leaveWordId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "LeavewordServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Leaveword leaveword = new Leaveword();
				leaveword.setLeaveWordId(object.getInt("leaveWordId"));
				leaveword.setLeaveTitle(object.getString("leaveTitle"));
				leaveword.setLeaveContent(object.getString("leaveContent"));
				leaveword.setTelephone(object.getString("telephone"));
				leaveword.setUserObj(object.getString("userObj"));
				leaveword.setLeaveTime(object.getString("leaveTime"));
				leavewordList.add(leaveword);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = leavewordList.size();
		if(size>0) return leavewordList.get(0); 
		else return null; 
	}
}
