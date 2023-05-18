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

    private int sectionId;
    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }
    public int getSectionId() {
        return sectionId;
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
    @Resource TimeSectionDAO timeSectionDAO;

    /*待操作的TimeSection对象*/
    private TimeSection timeSection;
    public void setTimeSection(TimeSection timeSection) {
        this.timeSection = timeSection;
    }
    public TimeSection getTimeSection() {
        return this.timeSection;
    }

    /*跳转到添加TimeSection视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*添加TimeSection信息*/
    @SuppressWarnings("deprecation")
    public String AddTimeSection() {
        ActionContext ctx = ActionContext.getContext();
        try {
            timeSectionDAO.AddTimeSection(timeSection);
            ctx.put("message",  java.net.URLEncoder.encode("TimeSection添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("TimeSection添加失败!"));
            return "error";
        }
    }

    /*查询TimeSection信息*/
    public String QueryTimeSection() {
        if(currentPage == 0) currentPage = 1;
        List<TimeSection> timeSectionList = timeSectionDAO.QueryTimeSectionInfo(currentPage);
        /*计算总的页数和总的记录数*/
        timeSectionDAO.CalculateTotalPageAndRecordNumber();
        /*获取到总的页码数目*/
        totalPage = timeSectionDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = timeSectionDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("timeSectionList",  timeSectionList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QueryTimeSectionOutputToExcel() { 
        List<TimeSection> timeSectionList = timeSectionDAO.QueryTimeSectionInfo();
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "TimeSection信息记录"; 
        String[] headers = { "记录id","时段名称"};
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
		HttpServletResponse response = null;//创建一个HttpServletResponse对象 
		OutputStream out = null;//创建一个输出流对象 
		try { 
			response = ServletActionContext.getResponse();//初始化HttpServletResponse对象 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"TimeSection.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询TimeSection信息*/
    public String FrontQueryTimeSection() {
        if(currentPage == 0) currentPage = 1;
        List<TimeSection> timeSectionList = timeSectionDAO.QueryTimeSectionInfo(currentPage);
        /*计算总的页数和总的记录数*/
        timeSectionDAO.CalculateTotalPageAndRecordNumber();
        /*获取到总的页码数目*/
        totalPage = timeSectionDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = timeSectionDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("timeSectionList",  timeSectionList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "front_query_view";
    }

    /*查询要修改的TimeSection信息*/
    public String ModifyTimeSectionQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键sectionId获取TimeSection对象*/
        TimeSection timeSection = timeSectionDAO.GetTimeSectionBySectionId(sectionId);

        ctx.put("timeSection",  timeSection);
        return "modify_view";
    }

    /*查询要修改的TimeSection信息*/
    public String FrontShowTimeSectionQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键sectionId获取TimeSection对象*/
        TimeSection timeSection = timeSectionDAO.GetTimeSectionBySectionId(sectionId);

        ctx.put("timeSection",  timeSection);
        return "front_show_view";
    }

    /*更新修改TimeSection信息*/
    public String ModifyTimeSection() {
        ActionContext ctx = ActionContext.getContext();
        try {
            timeSectionDAO.UpdateTimeSection(timeSection);
            ctx.put("message",  java.net.URLEncoder.encode("TimeSection信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("TimeSection信息更新失败!"));
            return "error";
       }
   }

    /*删除TimeSection信息*/
    public String DeleteTimeSection() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            timeSectionDAO.DeleteTimeSection(sectionId);
            ctx.put("message",  java.net.URLEncoder.encode("TimeSection删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("TimeSection删除失败!"));
            return "error";
        }
    }

}
