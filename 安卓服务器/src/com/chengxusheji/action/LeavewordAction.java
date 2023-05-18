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
import com.chengxusheji.dao.LeavewordDAO;
import com.chengxusheji.domain.Leaveword;
import com.chengxusheji.dao.UserInfoDAO;
import com.chengxusheji.domain.UserInfo;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class LeavewordAction extends BaseAction {

    /*界面层需要查询的属性: 约战标题*/
    private String leaveTitle;
    public void setLeaveTitle(String leaveTitle) {
        this.leaveTitle = leaveTitle;
    }
    public String getLeaveTitle() {
        return this.leaveTitle;
    }

    /*界面层需要查询的属性: 约战电话*/
    private String telephone;
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    public String getTelephone() {
        return this.telephone;
    }

    /*界面层需要查询的属性: 约战人*/
    private UserInfo userObj;
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }
    public UserInfo getUserObj() {
        return this.userObj;
    }

    /*界面层需要查询的属性: 约战时间*/
    private String leaveTime;
    public void setLeaveTime(String leaveTime) {
        this.leaveTime = leaveTime;
    }
    public String getLeaveTime() {
        return this.leaveTime;
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

    private int leaveWordId;
    public void setLeaveWordId(int leaveWordId) {
        this.leaveWordId = leaveWordId;
    }
    public int getLeaveWordId() {
        return leaveWordId;
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
    @Resource UserInfoDAO userInfoDAO;
    @Resource LeavewordDAO leavewordDAO;

    /*待操作的Leaveword对象*/
    private Leaveword leaveword;
    public void setLeaveword(Leaveword leaveword) {
        this.leaveword = leaveword;
    }
    public Leaveword getLeaveword() {
        return this.leaveword;
    }

    /*跳转到添加Leaveword视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*查询所有的UserInfo信息*/
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        return "add_view";
    }

    /*添加Leaveword信息*/
    @SuppressWarnings("deprecation")
    public String AddLeaveword() {
        ActionContext ctx = ActionContext.getContext();
        try {
            UserInfo userObj = userInfoDAO.GetUserInfoByUser_name(leaveword.getUserObj().getUser_name());
            leaveword.setUserObj(userObj);
            leavewordDAO.AddLeaveword(leaveword);
            ctx.put("message",  java.net.URLEncoder.encode("Leaveword添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Leaveword添加失败!"));
            return "error";
        }
    }

    /*查询Leaveword信息*/
    public String QueryLeaveword() {
        if(currentPage == 0) currentPage = 1;
        if(leaveTitle == null) leaveTitle = "";
        if(telephone == null) telephone = "";
        if(leaveTime == null) leaveTime = "";
        List<Leaveword> leavewordList = leavewordDAO.QueryLeavewordInfo(leaveTitle, telephone, userObj, leaveTime, currentPage);
        /*计算总的页数和总的记录数*/
        leavewordDAO.CalculateTotalPageAndRecordNumber(leaveTitle, telephone, userObj, leaveTime);
        /*获取到总的页码数目*/
        totalPage = leavewordDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = leavewordDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("leavewordList",  leavewordList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("leaveTitle", leaveTitle);
        ctx.put("telephone", telephone);
        ctx.put("userObj", userObj);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("leaveTime", leaveTime);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QueryLeavewordOutputToExcel() { 
        if(leaveTitle == null) leaveTitle = "";
        if(telephone == null) telephone = "";
        if(leaveTime == null) leaveTime = "";
        List<Leaveword> leavewordList = leavewordDAO.QueryLeavewordInfo(leaveTitle,telephone,userObj,leaveTime);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Leaveword信息记录"; 
        String[] headers = { "留言id","约战标题","约战电话","约战人","约战时间"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<leavewordList.size();i++) {
        	Leaveword leaveword = leavewordList.get(i); 
        	dataset.add(new String[]{leaveword.getLeaveWordId() + "",leaveword.getLeaveTitle(),leaveword.getTelephone(),leaveword.getUserObj().getName(),
leaveword.getLeaveTime()});
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
			response.setHeader("Content-disposition","attachment; filename="+"Leaveword.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询Leaveword信息*/
    public String FrontQueryLeaveword() {
        if(currentPage == 0) currentPage = 1;
        if(leaveTitle == null) leaveTitle = "";
        if(telephone == null) telephone = "";
        if(leaveTime == null) leaveTime = "";
        List<Leaveword> leavewordList = leavewordDAO.QueryLeavewordInfo(leaveTitle, telephone, userObj, leaveTime, currentPage);
        /*计算总的页数和总的记录数*/
        leavewordDAO.CalculateTotalPageAndRecordNumber(leaveTitle, telephone, userObj, leaveTime);
        /*获取到总的页码数目*/
        totalPage = leavewordDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = leavewordDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("leavewordList",  leavewordList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("leaveTitle", leaveTitle);
        ctx.put("telephone", telephone);
        ctx.put("userObj", userObj);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("leaveTime", leaveTime);
        return "front_query_view";
    }

    /*查询要修改的Leaveword信息*/
    public String ModifyLeavewordQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键leaveWordId获取Leaveword对象*/
        Leaveword leaveword = leavewordDAO.GetLeavewordByLeaveWordId(leaveWordId);

        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("leaveword",  leaveword);
        return "modify_view";
    }

    /*查询要修改的Leaveword信息*/
    public String FrontShowLeavewordQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键leaveWordId获取Leaveword对象*/
        Leaveword leaveword = leavewordDAO.GetLeavewordByLeaveWordId(leaveWordId);

        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("leaveword",  leaveword);
        return "front_show_view";
    }

    /*更新修改Leaveword信息*/
    public String ModifyLeaveword() {
        ActionContext ctx = ActionContext.getContext();
        try {
            UserInfo userObj = userInfoDAO.GetUserInfoByUser_name(leaveword.getUserObj().getUser_name());
            leaveword.setUserObj(userObj);
            leavewordDAO.UpdateLeaveword(leaveword);
            ctx.put("message",  java.net.URLEncoder.encode("Leaveword信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Leaveword信息更新失败!"));
            return "error";
       }
   }

    /*删除Leaveword信息*/
    public String DeleteLeaveword() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            leavewordDAO.DeleteLeaveword(leaveWordId);
            ctx.put("message",  java.net.URLEncoder.encode("Leaveword删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Leaveword删除失败!"));
            return "error";
        }
    }

}
