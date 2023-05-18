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
import com.chengxusheji.dao.PlaceDAO;
import com.chengxusheji.domain.Place;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class PlaceAction extends BaseAction {

	/*图片或文件字段placePhoto参数接收*/
	private File placePhotoFile;
	private String placePhotoFileFileName;
	private String placePhotoFileContentType;
	public File getPlacePhotoFile() {
		return placePhotoFile;
	}
	public void setPlacePhotoFile(File placePhotoFile) {
		this.placePhotoFile = placePhotoFile;
	}
	public String getPlacePhotoFileFileName() {
		return placePhotoFileFileName;
	}
	public void setPlacePhotoFileFileName(String placePhotoFileFileName) {
		this.placePhotoFileFileName = placePhotoFileFileName;
	}
	public String getPlacePhotoFileContentType() {
		return placePhotoFileContentType;
	}
	public void setPlacePhotoFileContentType(String placePhotoFileContentType) {
		this.placePhotoFileContentType = placePhotoFileContentType;
	}
    /*界面层需要查询的属性: 场地名称*/
    private String placeName;
    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }
    public String getPlaceName() {
        return this.placeName;
    }

    /*界面层需要查询的属性: 球场地址*/
    private String placePos;
    public void setPlacePos(String placePos) {
        this.placePos = placePos;
    }
    public String getPlacePos() {
        return this.placePos;
    }

    /*界面层需要查询的属性: 联系电话*/
    private String telephone;
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    public String getTelephone() {
        return this.telephone;
    }

    /*界面层需要查询的属性: 发布时间*/
    private String addTime;
    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }
    public String getAddTime() {
        return this.addTime;
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

    private int placeId;
    public void setPlaceId(int placeId) {
        this.placeId = placeId;
    }
    public int getPlaceId() {
        return placeId;
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

    /*待操作的Place对象*/
    private Place place;
    public void setPlace(Place place) {
        this.place = place;
    }
    public Place getPlace() {
        return this.place;
    }

    /*跳转到添加Place视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*添加Place信息*/
    @SuppressWarnings("deprecation")
    public String AddPlace() {
        ActionContext ctx = ActionContext.getContext();
        try {
            /*处理球场图片上传*/
            String placePhotoPath = "upload/noimage.jpg"; 
       	 	if(placePhotoFile != null)
       	 		placePhotoPath = photoUpload(placePhotoFile,placePhotoFileContentType);
       	 	place.setPlacePhoto(placePhotoPath);
            placeDAO.AddPlace(place);
            ctx.put("message",  java.net.URLEncoder.encode("Place添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Place添加失败!"));
            return "error";
        }
    }

    /*查询Place信息*/
    public String QueryPlace() {
        if(currentPage == 0) currentPage = 1;
        if(placeName == null) placeName = "";
        if(placePos == null) placePos = "";
        if(telephone == null) telephone = "";
        if(addTime == null) addTime = "";
        List<Place> placeList = placeDAO.QueryPlaceInfo(placeName, placePos, telephone, addTime, currentPage);
        /*计算总的页数和总的记录数*/
        placeDAO.CalculateTotalPageAndRecordNumber(placeName, placePos, telephone, addTime);
        /*获取到总的页码数目*/
        totalPage = placeDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = placeDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("placeList",  placeList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("placeName", placeName);
        ctx.put("placePos", placePos);
        ctx.put("telephone", telephone);
        ctx.put("addTime", addTime);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QueryPlaceOutputToExcel() { 
        if(placeName == null) placeName = "";
        if(placePos == null) placePos = "";
        if(telephone == null) telephone = "";
        if(addTime == null) addTime = "";
        List<Place> placeList = placeDAO.QueryPlaceInfo(placeName,placePos,telephone,addTime);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Place信息记录"; 
        String[] headers = { "场地id","场地名称","球场图片","球场地址","联系电话","球场价格","营业时间","是否置顶","发布时间"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<placeList.size();i++) {
        	Place place = placeList.get(i); 
        	dataset.add(new String[]{place.getPlaceId() + "",place.getPlaceName(),place.getPlacePhoto(),place.getPlacePos(),place.getTelephone(),place.getPlacePrice() + "",place.getOnlineTime(),place.getTopFlag() + "",place.getAddTime()});
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
			response.setHeader("Content-disposition","attachment; filename="+"Place.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询Place信息*/
    public String FrontQueryPlace() {
        if(currentPage == 0) currentPage = 1;
        if(placeName == null) placeName = "";
        if(placePos == null) placePos = "";
        if(telephone == null) telephone = "";
        if(addTime == null) addTime = "";
        List<Place> placeList = placeDAO.QueryPlaceInfo(placeName, placePos, telephone, addTime, currentPage);
        /*计算总的页数和总的记录数*/
        placeDAO.CalculateTotalPageAndRecordNumber(placeName, placePos, telephone, addTime);
        /*获取到总的页码数目*/
        totalPage = placeDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = placeDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("placeList",  placeList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("placeName", placeName);
        ctx.put("placePos", placePos);
        ctx.put("telephone", telephone);
        ctx.put("addTime", addTime);
        return "front_query_view";
    }

    /*查询要修改的Place信息*/
    public String ModifyPlaceQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键placeId获取Place对象*/
        Place place = placeDAO.GetPlaceByPlaceId(placeId);

        ctx.put("place",  place);
        return "modify_view";
    }

    /*查询要修改的Place信息*/
    public String FrontShowPlaceQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键placeId获取Place对象*/
        Place place = placeDAO.GetPlaceByPlaceId(placeId);

        ctx.put("place",  place);
        return "front_show_view";
    }

    /*更新修改Place信息*/
    public String ModifyPlace() {
        ActionContext ctx = ActionContext.getContext();
        try {
            /*处理球场图片上传*/
            if(placePhotoFile != null) {
            	String placePhotoPath = photoUpload(placePhotoFile,placePhotoFileContentType);
            	place.setPlacePhoto(placePhotoPath);
            }
            placeDAO.UpdatePlace(place);
            ctx.put("message",  java.net.URLEncoder.encode("Place信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Place信息更新失败!"));
            return "error";
       }
   }

    /*删除Place信息*/
    public String DeletePlace() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            placeDAO.DeletePlace(placeId);
            ctx.put("message",  java.net.URLEncoder.encode("Place删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Place删除失败!"));
            return "error";
        }
    }

}
