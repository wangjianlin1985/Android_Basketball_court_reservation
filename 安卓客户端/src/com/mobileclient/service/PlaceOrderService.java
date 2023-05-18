package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.PlaceOrder;
import com.mobileclient.util.HttpUtil;

/*场地预订管理业务逻辑层*/
public class PlaceOrderService {
	/* 添加场地预订 */
	public String AddPlaceOrder(PlaceOrder placeOrder) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("orderId", placeOrder.getOrderId() + "");
		params.put("placeObj", placeOrder.getPlaceObj() + "");
		params.put("orderDate", placeOrder.getOrderDate().toString());
		params.put("timeSectionObj", placeOrder.getTimeSectionObj() + "");
		params.put("userObj", placeOrder.getUserObj());
		params.put("orderTime", placeOrder.getOrderTime());
		params.put("shenHeState", placeOrder.getShenHeState());
		params.put("shenHeTime", placeOrder.getShenHeTime());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "PlaceOrderServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询场地预订 */
	public List<PlaceOrder> QueryPlaceOrder(PlaceOrder queryConditionPlaceOrder) throws Exception {
		String urlString = HttpUtil.BASE_URL + "PlaceOrderServlet?action=query";
		if(queryConditionPlaceOrder != null) {
			urlString += "&placeObj=" + queryConditionPlaceOrder.getPlaceObj();
			if(queryConditionPlaceOrder.getOrderDate() != null) {
				urlString += "&orderDate=" + URLEncoder.encode(queryConditionPlaceOrder.getOrderDate().toString(), "UTF-8");
			}
			urlString += "&timeSectionObj=" + queryConditionPlaceOrder.getTimeSectionObj();
			urlString += "&userObj=" + URLEncoder.encode(queryConditionPlaceOrder.getUserObj(), "UTF-8") + "";
			urlString += "&orderTime=" + URLEncoder.encode(queryConditionPlaceOrder.getOrderTime(), "UTF-8") + "";
			urlString += "&shenHeState=" + URLEncoder.encode(queryConditionPlaceOrder.getShenHeState(), "UTF-8") + "";
			urlString += "&shenHeTime=" + URLEncoder.encode(queryConditionPlaceOrder.getShenHeTime(), "UTF-8") + "";
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		PlaceOrderListHandler placeOrderListHander = new PlaceOrderListHandler();
		xr.setContentHandler(placeOrderListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<PlaceOrder> placeOrderList = placeOrderListHander.getPlaceOrderList();
		return placeOrderList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<PlaceOrder> placeOrderList = new ArrayList<PlaceOrder>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				PlaceOrder placeOrder = new PlaceOrder();
				placeOrder.setOrderId(object.getInt("orderId"));
				placeOrder.setPlaceObj(object.getInt("placeObj"));
				placeOrder.setOrderDate(Timestamp.valueOf(object.getString("orderDate")));
				placeOrder.setTimeSectionObj(object.getInt("timeSectionObj"));
				placeOrder.setUserObj(object.getString("userObj"));
				placeOrder.setOrderTime(object.getString("orderTime"));
				placeOrder.setShenHeState(object.getString("shenHeState"));
				placeOrder.setShenHeTime(object.getString("shenHeTime"));
				placeOrderList.add(placeOrder);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return placeOrderList;
	}

	/* 更新场地预订 */
	public String UpdatePlaceOrder(PlaceOrder placeOrder) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("orderId", placeOrder.getOrderId() + "");
		params.put("placeObj", placeOrder.getPlaceObj() + "");
		params.put("orderDate", placeOrder.getOrderDate().toString());
		params.put("timeSectionObj", placeOrder.getTimeSectionObj() + "");
		params.put("userObj", placeOrder.getUserObj());
		params.put("orderTime", placeOrder.getOrderTime());
		params.put("shenHeState", placeOrder.getShenHeState());
		params.put("shenHeTime", placeOrder.getShenHeTime());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "PlaceOrderServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除场地预订 */
	public String DeletePlaceOrder(int orderId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("orderId", orderId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "PlaceOrderServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "场地预订信息删除失败!";
		}
	}

	/* 根据预订id获取场地预订对象 */
	public PlaceOrder GetPlaceOrder(int orderId)  {
		List<PlaceOrder> placeOrderList = new ArrayList<PlaceOrder>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("orderId", orderId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "PlaceOrderServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				PlaceOrder placeOrder = new PlaceOrder();
				placeOrder.setOrderId(object.getInt("orderId"));
				placeOrder.setPlaceObj(object.getInt("placeObj"));
				placeOrder.setOrderDate(Timestamp.valueOf(object.getString("orderDate")));
				placeOrder.setTimeSectionObj(object.getInt("timeSectionObj"));
				placeOrder.setUserObj(object.getString("userObj"));
				placeOrder.setOrderTime(object.getString("orderTime"));
				placeOrder.setShenHeState(object.getString("shenHeState"));
				placeOrder.setShenHeTime(object.getString("shenHeTime"));
				placeOrderList.add(placeOrder);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = placeOrderList.size();
		if(size>0) return placeOrderList.get(0); 
		else return null; 
	}
}
