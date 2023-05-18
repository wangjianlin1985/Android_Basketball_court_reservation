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
import com.chengxusheji.dao.TimeSectionDAO;
import com.chengxusheji.domain.TimeSection;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class TimeSectionAction extends BaseAction {

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

    private int sectionId;
    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }
    public int getSectionId() {
        return sectionId;
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
    @Resource TimeSectionDAO timeSectionDAO;

    /*��������TimeSection����*/
    private TimeSection timeSection;
    public void setTimeSection(TimeSection timeSection) {
        this.timeSection = timeSection;
    }
    public TimeSection getTimeSection() {
        return this.timeSection;
    }

    /*��ת�����TimeSection��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*���TimeSection��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddTimeSection() {
        ActionContext ctx = ActionContext.getContext();
        try {
            timeSectionDAO.AddTimeSection(timeSection);
            ctx.put("message",  java.net.URLEncoder.encode("TimeSection��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("TimeSection���ʧ��!"));
            return "error";
        }
    }

    /*��ѯTimeSection��Ϣ*/
    public String QueryTimeSection() {
        if(currentPage == 0) currentPage = 1;
        List<TimeSection> timeSectionList = timeSectionDAO.QueryTimeSectionInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        timeSectionDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = timeSectionDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = timeSectionDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("timeSectionList",  timeSectionList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryTimeSectionOutputToExcel() { 
        List<TimeSection> timeSectionList = timeSectionDAO.QueryTimeSectionInfo();
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "TimeSection��Ϣ��¼"; 
        String[] headers = { "��¼id","ʱ������"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<timeSectionList.size();i++) {
        	TimeSection timeSection = timeSectionList.get(i); 
        	dataset.add(new String[]{timeSection.getSectionId() + "",timeSection.getSectionName()});
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
			response.setHeader("Content-disposition","attachment; filename="+"TimeSection.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯTimeSection��Ϣ*/
    public String FrontQueryTimeSection() {
        if(currentPage == 0) currentPage = 1;
        List<TimeSection> timeSectionList = timeSectionDAO.QueryTimeSectionInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        timeSectionDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = timeSectionDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = timeSectionDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("timeSectionList",  timeSectionList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�TimeSection��Ϣ*/
    public String ModifyTimeSectionQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������sectionId��ȡTimeSection����*/
        TimeSection timeSection = timeSectionDAO.GetTimeSectionBySectionId(sectionId);

        ctx.put("timeSection",  timeSection);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�TimeSection��Ϣ*/
    public String FrontShowTimeSectionQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������sectionId��ȡTimeSection����*/
        TimeSection timeSection = timeSectionDAO.GetTimeSectionBySectionId(sectionId);

        ctx.put("timeSection",  timeSection);
        return "front_show_view";
    }

    /*�����޸�TimeSection��Ϣ*/
    public String ModifyTimeSection() {
        ActionContext ctx = ActionContext.getContext();
        try {
            timeSectionDAO.UpdateTimeSection(timeSection);
            ctx.put("message",  java.net.URLEncoder.encode("TimeSection��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("TimeSection��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��TimeSection��Ϣ*/
    public String DeleteTimeSection() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            timeSectionDAO.DeleteTimeSection(sectionId);
            ctx.put("message",  java.net.URLEncoder.encode("TimeSectionɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("TimeSectionɾ��ʧ��!"));
            return "error";
        }
    }

}
