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

    /*�������Ҫ��ѯ������: Լս����*/
    private String leaveTitle;
    public void setLeaveTitle(String leaveTitle) {
        this.leaveTitle = leaveTitle;
    }
    public String getLeaveTitle() {
        return this.leaveTitle;
    }

    /*�������Ҫ��ѯ������: Լս�绰*/
    private String telephone;
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    public String getTelephone() {
        return this.telephone;
    }

    /*�������Ҫ��ѯ������: Լս��*/
    private UserInfo userObj;
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }
    public UserInfo getUserObj() {
        return this.userObj;
    }

    /*�������Ҫ��ѯ������: Լսʱ��*/
    private String leaveTime;
    public void setLeaveTime(String leaveTime) {
        this.leaveTime = leaveTime;
    }
    public String getLeaveTime() {
        return this.leaveTime;
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

    private int leaveWordId;
    public void setLeaveWordId(int leaveWordId) {
        this.leaveWordId = leaveWordId;
    }
    public int getLeaveWordId() {
        return leaveWordId;
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
    @Resource UserInfoDAO userInfoDAO;
    @Resource LeavewordDAO leavewordDAO;

    /*��������Leaveword����*/
    private Leaveword leaveword;
    public void setLeaveword(Leaveword leaveword) {
        this.leaveword = leaveword;
    }
    public Leaveword getLeaveword() {
        return this.leaveword;
    }

    /*��ת�����Leaveword��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*��ѯ���е�UserInfo��Ϣ*/
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        return "add_view";
    }

    /*���Leaveword��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddLeaveword() {
        ActionContext ctx = ActionContext.getContext();
        try {
            UserInfo userObj = userInfoDAO.GetUserInfoByUser_name(leaveword.getUserObj().getUser_name());
            leaveword.setUserObj(userObj);
            leavewordDAO.AddLeaveword(leaveword);
            ctx.put("message",  java.net.URLEncoder.encode("Leaveword��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Leaveword���ʧ��!"));
            return "error";
        }
    }

    /*��ѯLeaveword��Ϣ*/
    public String QueryLeaveword() {
        if(currentPage == 0) currentPage = 1;
        if(leaveTitle == null) leaveTitle = "";
        if(telephone == null) telephone = "";
        if(leaveTime == null) leaveTime = "";
        List<Leaveword> leavewordList = leavewordDAO.QueryLeavewordInfo(leaveTitle, telephone, userObj, leaveTime, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        leavewordDAO.CalculateTotalPageAndRecordNumber(leaveTitle, telephone, userObj, leaveTime);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = leavewordDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*��̨������excel*/
    public String QueryLeavewordOutputToExcel() { 
        if(leaveTitle == null) leaveTitle = "";
        if(telephone == null) telephone = "";
        if(leaveTime == null) leaveTime = "";
        List<Leaveword> leavewordList = leavewordDAO.QueryLeavewordInfo(leaveTitle,telephone,userObj,leaveTime);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Leaveword��Ϣ��¼"; 
        String[] headers = { "����id","Լս����","Լս�绰","Լս��","Լսʱ��"};
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
		HttpServletResponse response = null;//����һ��HttpServletResponse���� 
		OutputStream out = null;//����һ����������� 
		try { 
			response = ServletActionContext.getResponse();//��ʼ��HttpServletResponse���� 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"Leaveword.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯLeaveword��Ϣ*/
    public String FrontQueryLeaveword() {
        if(currentPage == 0) currentPage = 1;
        if(leaveTitle == null) leaveTitle = "";
        if(telephone == null) telephone = "";
        if(leaveTime == null) leaveTime = "";
        List<Leaveword> leavewordList = leavewordDAO.QueryLeavewordInfo(leaveTitle, telephone, userObj, leaveTime, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        leavewordDAO.CalculateTotalPageAndRecordNumber(leaveTitle, telephone, userObj, leaveTime);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = leavewordDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*��ѯҪ�޸ĵ�Leaveword��Ϣ*/
    public String ModifyLeavewordQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������leaveWordId��ȡLeaveword����*/
        Leaveword leaveword = leavewordDAO.GetLeavewordByLeaveWordId(leaveWordId);

        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("leaveword",  leaveword);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�Leaveword��Ϣ*/
    public String FrontShowLeavewordQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������leaveWordId��ȡLeaveword����*/
        Leaveword leaveword = leavewordDAO.GetLeavewordByLeaveWordId(leaveWordId);

        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("leaveword",  leaveword);
        return "front_show_view";
    }

    /*�����޸�Leaveword��Ϣ*/
    public String ModifyLeaveword() {
        ActionContext ctx = ActionContext.getContext();
        try {
            UserInfo userObj = userInfoDAO.GetUserInfoByUser_name(leaveword.getUserObj().getUser_name());
            leaveword.setUserObj(userObj);
            leavewordDAO.UpdateLeaveword(leaveword);
            ctx.put("message",  java.net.URLEncoder.encode("Leaveword��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Leaveword��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��Leaveword��Ϣ*/
    public String DeleteLeaveword() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            leavewordDAO.DeleteLeaveword(leaveWordId);
            ctx.put("message",  java.net.URLEncoder.encode("Leavewordɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Leavewordɾ��ʧ��!"));
            return "error";
        }
    }

}
