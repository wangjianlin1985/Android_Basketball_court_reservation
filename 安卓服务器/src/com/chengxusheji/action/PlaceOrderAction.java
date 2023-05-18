package com.chengxusheji.action;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import com.opensymphony.xwork2.ActionContext;
import com.chengxusheji.dao.PlaceOrderDAO;
import com.chengxusheji.domain.PlaceOrder;
import com.chengxusheji.dao.PlaceDAO;
import com.chengxusheji.domain.Place;
import com.chengxusheji.dao.TimeSectionDAO;
import com.chengxusheji.domain.TimeSection;
import com.chengxusheji.dao.UserInfoDAO;
import com.chengxusheji.domain.UserInfo;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class PlaceOrderAction extends BaseAction {

    /*�������Ҫ��ѯ������: Ԥ����*/
    private Place placeObj;
    public void setPlaceObj(Place placeObj) {
        this.placeObj = placeObj;
    }
    public Place getPlaceObj() {
        return this.placeObj;
    }

    /*�������Ҫ��ѯ������: Ԥ������*/
    private String orderDate;
    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }
    public String getOrderDate() {
        return this.orderDate;
    }

    /*�������Ҫ��ѯ������: Ԥ��ʱ��*/
    private TimeSection timeSectionObj;
    public void setTimeSectionObj(TimeSection timeSectionObj) {
        this.timeSectionObj = timeSectionObj;
    }
    public TimeSection getTimeSectionObj() {
        return this.timeSectionObj;
    }

    /*�������Ҫ��ѯ������: Ԥ����*/
    private UserInfo userObj;
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }
    public UserInfo getUserObj() {
        return this.userObj;
    }

    /*�������Ҫ��ѯ������: Ԥ��ʱ��*/
    private String orderTime;
    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }
    public String getOrderTime() {
        return this.orderTime;
    }

    /*�������Ҫ��ѯ������: ���״̬*/
    private String shenHeState;
    public void setShenHeState(String shenHeState) {
        this.shenHeState = shenHeState;
    }
    public String getShenHeState() {
        return this.shenHeState;
    }

    /*�������Ҫ��ѯ������: ���ʱ��*/
    private String shenHeTime;
    public void setShenHeTime(String shenHeTime) {
        this.shenHeTime = shenHeTime;
    }
    public String getShenHeTime() {
        return this.shenHeTime;
    }

    /*��ǰ�ڼ�ҳ*/
    private int currentPage;
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getCurrentPage() {
        return currentPage;
    }

    /*һ������ҳ*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    private int orderId;
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
    public int getOrderId() {
        return orderId;
    }

    /*��ǰ��ѯ���ܼ�¼��Ŀ*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*ҵ������*/
    @Resource PlaceDAO placeDAO;
    @Resource TimeSectionDAO timeSectionDAO;
    @Resource UserInfoDAO userInfoDAO;
    @Resource PlaceOrderDAO placeOrderDAO;

    /*��������PlaceOrder����*/
    private PlaceOrder placeOrder;
    public void setPlaceOrder(PlaceOrder placeOrder) {
        this.placeOrder = placeOrder;
    }
    public PlaceOrder getPlaceOrder() {
        return this.placeOrder;
    }

    /*��ת�����PlaceOrder��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*��ѯ���е�Place��Ϣ*/
        List<Place> placeList = placeDAO.QueryAllPlaceInfo();
        ctx.put("placeList", placeList);
        /*��ѯ���е�TimeSection��Ϣ*/
        List<TimeSection> timeSectionList = timeSectionDAO.QueryAllTimeSectionInfo();
        ctx.put("timeSectionList", timeSectionList);
        /*��ѯ���е�UserInfo��Ϣ*/
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        return "add_view";
    }

    /*���PlaceOrder��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddPlaceOrder() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Place placeObj = placeDAO.GetPlaceByPlaceId(placeOrder.getPlaceObj().getPlaceId());
            placeOrder.setPlaceObj(placeObj);
            TimeSection timeSectionObj = timeSectionDAO.GetTimeSectionBySectionId(placeOrder.getTimeSectionObj().getSectionId());
            placeOrder.setTimeSectionObj(timeSectionObj);
            UserInfo userObj = userInfoDAO.GetUserInfoByUser_name(placeOrder.getUserObj().getUser_name());
            placeOrder.setUserObj(userObj);
            placeOrderDAO.AddPlaceOrder(placeOrder);
            ctx.put("message",  java.net.URLEncoder.encode("PlaceOrder��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("PlaceOrder���ʧ��!"));
            return "error";
        }
    }

    /*��ѯPlaceOrder��Ϣ*/
    public String QueryPlaceOrder() {
        if(currentPage == 0) currentPage = 1;
        if(orderDate == null) orderDate = "";
        if(orderTime == null) orderTime = "";
        if(shenHeState == null) shenHeState = "";
        if(shenHeTime == null) shenHeTime = "";
        List<PlaceOrder> placeOrderList = placeOrderDAO.QueryPlaceOrderInfo(placeObj, orderDate, timeSectionObj, userObj, orderTime, shenHeState, shenHeTime, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        placeOrderDAO.CalculateTotalPageAndRecordNumber(placeObj, orderDate, timeSectionObj, userObj, orderTime, shenHeState, shenHeTime);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = placeOrderDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = placeOrderDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("placeOrderList",  placeOrderList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("placeObj", placeObj);
        List<Place> placeList = placeDAO.QueryAllPlaceInfo();
        ctx.put("placeList", placeList);
        ctx.put("orderDate", orderDate);
        ctx.put("timeSectionObj", timeSectionObj);
        List<TimeSection> timeSectionList = timeSectionDAO.QueryAllTimeSectionInfo();
        ctx.put("timeSectionList", timeSectionList);
        ctx.put("userObj", userObj);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("orderTime", orderTime);
        ctx.put("shenHeState", shenHeState);
        ctx.put("shenHeTime", shenHeTime);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryPlaceOrderOutputToExcel() { 
        if(orderDate == null) orderDate = "";
        if(orderTime == null) orderTime = "";
        if(shenHeState == null) shenHeState = "";
        if(shenHeTime == null) shenHeTime = "";
        List<PlaceOrder> placeOrderList = placeOrderDAO.QueryPlaceOrderInfo(placeObj,orderDate,timeSectionObj,userObj,orderTime,shenHeState,shenHeTime);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "PlaceOrder��Ϣ��¼"; 
        String[] headers = { "Ԥ��id","Ԥ����","Ԥ������","Ԥ��ʱ��","Ԥ����","Ԥ��ʱ��","���״̬","���ʱ��"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<placeOrderList.size();i++) {
        	PlaceOrder placeOrder = placeOrderList.get(i); 
        	dataset.add(new String[]{placeOrder.getOrderId() + "",placeOrder.getPlaceObj().getPlaceName(),
new SimpleDateFormat("yyyy-MM-dd").format(placeOrder.getOrderDate()),placeOrder.getTimeSectionObj().getSectionName(),
placeOrder.getUserObj().getName(),
placeOrder.getOrderTime(),placeOrder.getShenHeState(),placeOrder.getShenHeTime()});
        }
        /*
        OutputStream out = null;
		try {
			out = new FileOutputStream("C://output.xls");
			ex.exportExcel(title,headers, dataset, out);
		    out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		HttpServletResponse response = null;//����һ��HttpServletResponse���� 
		OutputStream out = null;//����һ����������� 
		try { 
			response = ServletActionContext.getResponse();//��ʼ��HttpServletResponse���� 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"PlaceOrder.xls");//filename�����ص�xls���������������Ӣ�� 
			response.setContentType("application/msexcel;charset=UTF-8");//�������� 
			response.setHeader("Pragma","No-cache");//����ͷ 
			response.setHeader("Cache-Control","no-cache");//����ͷ 
			response.setDateHeader("Expires", 0);//��������ͷ  
			String rootPath = ServletActionContext.getServletContext().getRealPath("/");
			ex.exportExcel(rootPath,title,headers, dataset, out);
			out.flush();
		} catch (IOException e) { 
			e.printStackTrace(); 
		}finally{
			try{
				if(out!=null){ 
					out.close(); 
				}
			}catch(IOException e){ 
				e.printStackTrace(); 
			} 
		}
		return null;
    }
    /*ǰ̨��ѯPlaceOrder��Ϣ*/
    public String FrontQueryPlaceOrder() {
        if(currentPage == 0) currentPage = 1;
        if(orderDate == null) orderDate = "";
        if(orderTime == null) orderTime = "";
        if(shenHeState == null) shenHeState = "";
        if(shenHeTime == null) shenHeTime = "";
        List<PlaceOrder> placeOrderList = placeOrderDAO.QueryPlaceOrderInfo(placeObj, orderDate, timeSectionObj, userObj, orderTime, shenHeState, shenHeTime, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        placeOrderDAO.CalculateTotalPageAndRecordNumber(placeObj, orderDate, timeSectionObj, userObj, orderTime, shenHeState, shenHeTime);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = placeOrderDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = placeOrderDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("placeOrderList",  placeOrderList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("placeObj", placeObj);
        List<Place> placeList = placeDAO.QueryAllPlaceInfo();
        ctx.put("placeList", placeList);
        ctx.put("orderDate", orderDate);
        ctx.put("timeSectionObj", timeSectionObj);
        List<TimeSection> timeSectionList = timeSectionDAO.QueryAllTimeSectionInfo();
        ctx.put("timeSectionList", timeSectionList);
        ctx.put("userObj", userObj);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("orderTime", orderTime);
        ctx.put("shenHeState", shenHeState);
        ctx.put("shenHeTime", shenHeTime);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�PlaceOrder��Ϣ*/
    public String ModifyPlaceOrderQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������orderId��ȡPlaceOrder����*/
        PlaceOrder placeOrder = placeOrderDAO.GetPlaceOrderByOrderId(orderId);

        List<Place> placeList = placeDAO.QueryAllPlaceInfo();
        ctx.put("placeList", placeList);
        List<TimeSection> timeSectionList = timeSectionDAO.QueryAllTimeSectionInfo();
        ctx.put("timeSectionList", timeSectionList);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("placeOrder",  placeOrder);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�PlaceOrder��Ϣ*/
    public String FrontShowPlaceOrderQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������orderId��ȡPlaceOrder����*/
        PlaceOrder placeOrder = placeOrderDAO.GetPlaceOrderByOrderId(orderId);

        List<Place> placeList = placeDAO.QueryAllPlaceInfo();
        ctx.put("placeList", placeList);
        List<TimeSection> timeSectionList = timeSectionDAO.QueryAllTimeSectionInfo();
        ctx.put("timeSectionList", timeSectionList);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("placeOrder",  placeOrder);
        return "front_show_view";
    }

    /*�����޸�PlaceOrder��Ϣ*/
    public String ModifyPlaceOrder() {
        ActionContext ctx = ActionContext.getContext();
        try {
        	
        	PlaceOrder db_order = placeOrderDAO.GetPlaceOrderByOrderId(placeOrder.getOrderId());
        	String oldState = db_order.getShenHeState(); 
        	
            Place placeObj = placeDAO.GetPlaceByPlaceId(placeOrder.getPlaceObj().getPlaceId());
            placeOrder.setPlaceObj(placeObj);
            TimeSection timeSectionObj = timeSectionDAO.GetTimeSectionBySectionId(placeOrder.getTimeSectionObj().getSectionId());
            placeOrder.setTimeSectionObj(timeSectionObj);
            UserInfo userObj = userInfoDAO.GetUserInfoByUser_name(placeOrder.getUserObj().getUser_name());
            placeOrder.setUserObj(userObj);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            placeOrder.setShenHeTime(sdf.format(new java.util.Date()));
            placeOrderDAO.UpdatePlaceOrder(placeOrder);
            
            if(oldState.equals("�����") && placeOrder.getShenHeState().equals("���ͨ��")) {
            	//���³�������
            	placeObj.setSellNum(placeObj.getSellNum() + 1);
            	placeDAO.UpdatePlace(placeObj);
            }
            
            ctx.put("message",  java.net.URLEncoder.encode("PlaceOrder��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("PlaceOrder��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��PlaceOrder��Ϣ*/
    public String DeletePlaceOrder() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            placeOrderDAO.DeletePlaceOrder(orderId);
            ctx.put("message",  java.net.URLEncoder.encode("PlaceOrderɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("PlaceOrderɾ��ʧ��!"));
            return "error";
        }
    }

}
