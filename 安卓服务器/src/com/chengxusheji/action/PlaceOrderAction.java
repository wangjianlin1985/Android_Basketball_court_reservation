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

    /*界面层需要查询的属性: 预订球场*/
    private Place placeObj;
    public void setPlaceObj(Place placeObj) {
        this.placeObj = placeObj;
    }
    public Place getPlaceObj() {
        return this.placeObj;
    }

    /*界面层需要查询的属性: 预订日期*/
    private String orderDate;
    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }
    public String getOrderDate() {
        return this.orderDate;
    }

    /*界面层需要查询的属性: 预订时段*/
    private TimeSection timeSectionObj;
    public void setTimeSectionObj(TimeSection timeSectionObj) {
        this.timeSectionObj = timeSectionObj;
    }
    public TimeSection getTimeSectionObj() {
        return this.timeSectionObj;
    }

    /*界面层需要查询的属性: 预订人*/
    private UserInfo userObj;
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }
    public UserInfo getUserObj() {
        return this.userObj;
    }

    /*界面层需要查询的属性: 预订时间*/
    private String orderTime;
    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }
    public String getOrderTime() {
        return this.orderTime;
    }

    /*界面层需要查询的属性: 审核状态*/
    private String shenHeState;
    public void setShenHeState(String shenHeState) {
        this.shenHeState = shenHeState;
    }
    public String getShenHeState() {
        return this.shenHeState;
    }

    /*界面层需要查询的属性: 审核时间*/
    private String shenHeTime;
    public void setShenHeTime(String shenHeTime) {
        this.shenHeTime = shenHeTime;
    }
    public String getShenHeTime() {
        return this.shenHeTime;
    }

    /*当前第几页*/
    private int currentPage;
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getCurrentPage() {
        return currentPage;
    }

    /*一共多少页*/
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

    /*当前查询的总记录数目*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*业务层对象*/
    @Resource PlaceDAO placeDAO;
    @Resource TimeSectionDAO timeSectionDAO;
    @Resource UserInfoDAO userInfoDAO;
    @Resource PlaceOrderDAO placeOrderDAO;

    /*待操作的PlaceOrder对象*/
    private PlaceOrder placeOrder;
    public void setPlaceOrder(PlaceOrder placeOrder) {
        this.placeOrder = placeOrder;
    }
    public PlaceOrder getPlaceOrder() {
        return this.placeOrder;
    }

    /*跳转到添加PlaceOrder视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*查询所有的Place信息*/
        List<Place> placeList = placeDAO.QueryAllPlaceInfo();
        ctx.put("placeList", placeList);
        /*查询所有的TimeSection信息*/
        List<TimeSection> timeSectionList = timeSectionDAO.QueryAllTimeSectionInfo();
        ctx.put("timeSectionList", timeSectionList);
        /*查询所有的UserInfo信息*/
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        return "add_view";
    }

    /*添加PlaceOrder信息*/
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
            ctx.put("message",  java.net.URLEncoder.encode("PlaceOrder添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("PlaceOrder添加失败!"));
            return "error";
        }
    }

    /*查询PlaceOrder信息*/
    public String QueryPlaceOrder() {
        if(currentPage == 0) currentPage = 1;
        if(orderDate == null) orderDate = "";
        if(orderTime == null) orderTime = "";
        if(shenHeState == null) shenHeState = "";
        if(shenHeTime == null) shenHeTime = "";
        List<PlaceOrder> placeOrderList = placeOrderDAO.QueryPlaceOrderInfo(placeObj, orderDate, timeSectionObj, userObj, orderTime, shenHeState, shenHeTime, currentPage);
        /*计算总的页数和总的记录数*/
        placeOrderDAO.CalculateTotalPageAndRecordNumber(placeObj, orderDate, timeSectionObj, userObj, orderTime, shenHeState, shenHeTime);
        /*获取到总的页码数目*/
        totalPage = placeOrderDAO.getTotalPage();
        /*当前查询条件下总记录数*/
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

    /*后台导出到excel*/
    public String QueryPlaceOrderOutputToExcel() { 
        if(orderDate == null) orderDate = "";
        if(orderTime == null) orderTime = "";
        if(shenHeState == null) shenHeState = "";
        if(shenHeTime == null) shenHeTime = "";
        List<PlaceOrder> placeOrderList = placeOrderDAO.QueryPlaceOrderInfo(placeObj,orderDate,timeSectionObj,userObj,orderTime,shenHeState,shenHeTime);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "PlaceOrder信息记录"; 
        String[] headers = { "预订id","预订球场","预订日期","预订时段","预订人","预订时间","审核状态","审核时间"};
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
		HttpServletResponse response = null;//创建一个HttpServletResponse对象 
		OutputStream out = null;//创建一个输出流对象 
		try { 
			response = ServletActionContext.getResponse();//初始化HttpServletResponse对象 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"PlaceOrder.xls");//filename是下载的xls的名，建议最好用英文 
			response.setContentType("application/msexcel;charset=UTF-8");//设置类型 
			response.setHeader("Pragma","No-cache");//设置头 
			response.setHeader("Cache-Control","no-cache");//设置头 
			response.setDateHeader("Expires", 0);//设置日期头  
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
    /*前台查询PlaceOrder信息*/
    public String FrontQueryPlaceOrder() {
        if(currentPage == 0) currentPage = 1;
        if(orderDate == null) orderDate = "";
        if(orderTime == null) orderTime = "";
        if(shenHeState == null) shenHeState = "";
        if(shenHeTime == null) shenHeTime = "";
        List<PlaceOrder> placeOrderList = placeOrderDAO.QueryPlaceOrderInfo(placeObj, orderDate, timeSectionObj, userObj, orderTime, shenHeState, shenHeTime, currentPage);
        /*计算总的页数和总的记录数*/
        placeOrderDAO.CalculateTotalPageAndRecordNumber(placeObj, orderDate, timeSectionObj, userObj, orderTime, shenHeState, shenHeTime);
        /*获取到总的页码数目*/
        totalPage = placeOrderDAO.getTotalPage();
        /*当前查询条件下总记录数*/
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

    /*查询要修改的PlaceOrder信息*/
    public String ModifyPlaceOrderQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键orderId获取PlaceOrder对象*/
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

    /*查询要修改的PlaceOrder信息*/
    public String FrontShowPlaceOrderQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键orderId获取PlaceOrder对象*/
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

    /*更新修改PlaceOrder信息*/
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
            
            if(oldState.equals("待审核") && placeOrder.getShenHeState().equals("审核通过")) {
            	//更新场地销量
            	placeObj.setSellNum(placeObj.getSellNum() + 1);
            	placeDAO.UpdatePlace(placeObj);
            }
            
            ctx.put("message",  java.net.URLEncoder.encode("PlaceOrder信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("PlaceOrder信息更新失败!"));
            return "error";
       }
   }

    /*删除PlaceOrder信息*/
    public String DeletePlaceOrder() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            placeOrderDAO.DeletePlaceOrder(orderId);
            ctx.put("message",  java.net.URLEncoder.encode("PlaceOrder删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("PlaceOrder删除失败!"));
            return "error";
        }
    }

}
