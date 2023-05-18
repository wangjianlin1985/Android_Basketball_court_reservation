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

	/*ͼƬ���ļ��ֶ�placePhoto��������*/
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
    /*�������Ҫ��ѯ������: ��������*/
    private String placeName;
    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }
    public String getPlaceName() {
        return this.placeName;
    }

    /*�������Ҫ��ѯ������: �򳡵�ַ*/
    private String placePos;
    public void setPlacePos(String placePos) {
        this.placePos = placePos;
    }
    public String getPlacePos() {
        return this.placePos;
    }

    /*�������Ҫ��ѯ������: ��ϵ�绰*/
    private String telephone;
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    public String getTelephone() {
        return this.telephone;
    }

    /*�������Ҫ��ѯ������: ����ʱ��*/
    private String addTime;
    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }
    public String getAddTime() {
        return this.addTime;
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

    private int placeId;
    public void setPlaceId(int placeId) {
        this.placeId = placeId;
    }
    public int getPlaceId() {
        return placeId;
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

    /*��������Place����*/
    private Place place;
    public void setPlace(Place place) {
        this.place = place;
    }
    public Place getPlace() {
        return this.place;
    }

    /*��ת�����Place��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*���Place��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddPlace() {
        ActionContext ctx = ActionContext.getContext();
        try {
            /*������ͼƬ�ϴ�*/
            String placePhotoPath = "upload/noimage.jpg"; 
       	 	if(placePhotoFile != null)
       	 		placePhotoPath = photoUpload(placePhotoFile,placePhotoFileContentType);
       	 	place.setPlacePhoto(placePhotoPath);
            placeDAO.AddPlace(place);
            ctx.put("message",  java.net.URLEncoder.encode("Place��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Place���ʧ��!"));
            return "error";
        }
    }

    /*��ѯPlace��Ϣ*/
    public String QueryPlace() {
        if(currentPage == 0) currentPage = 1;
        if(placeName == null) placeName = "";
        if(placePos == null) placePos = "";
        if(telephone == null) telephone = "";
        if(addTime == null) addTime = "";
        List<Place> placeList = placeDAO.QueryPlaceInfo(placeName, placePos, telephone, addTime, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        placeDAO.CalculateTotalPageAndRecordNumber(placeName, placePos, telephone, addTime);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = placeDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*��̨������excel*/
    public String QueryPlaceOutputToExcel() { 
        if(placeName == null) placeName = "";
        if(placePos == null) placePos = "";
        if(telephone == null) telephone = "";
        if(addTime == null) addTime = "";
        List<Place> placeList = placeDAO.QueryPlaceInfo(placeName,placePos,telephone,addTime);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Place��Ϣ��¼"; 
        String[] headers = { "����id","��������","��ͼƬ","�򳡵�ַ","��ϵ�绰","�򳡼۸�","Ӫҵʱ��","�Ƿ��ö�","����ʱ��"};
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
		HttpServletResponse response = null;//����һ��HttpServletResponse���� 
		OutputStream out = null;//����һ����������� 
		try { 
			response = ServletActionContext.getResponse();//��ʼ��HttpServletResponse���� 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"Place.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯPlace��Ϣ*/
    public String FrontQueryPlace() {
        if(currentPage == 0) currentPage = 1;
        if(placeName == null) placeName = "";
        if(placePos == null) placePos = "";
        if(telephone == null) telephone = "";
        if(addTime == null) addTime = "";
        List<Place> placeList = placeDAO.QueryPlaceInfo(placeName, placePos, telephone, addTime, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        placeDAO.CalculateTotalPageAndRecordNumber(placeName, placePos, telephone, addTime);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = placeDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*��ѯҪ�޸ĵ�Place��Ϣ*/
    public String ModifyPlaceQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������placeId��ȡPlace����*/
        Place place = placeDAO.GetPlaceByPlaceId(placeId);

        ctx.put("place",  place);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�Place��Ϣ*/
    public String FrontShowPlaceQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������placeId��ȡPlace����*/
        Place place = placeDAO.GetPlaceByPlaceId(placeId);

        ctx.put("place",  place);
        return "front_show_view";
    }

    /*�����޸�Place��Ϣ*/
    public String ModifyPlace() {
        ActionContext ctx = ActionContext.getContext();
        try {
            /*������ͼƬ�ϴ�*/
            if(placePhotoFile != null) {
            	String placePhotoPath = photoUpload(placePhotoFile,placePhotoFileContentType);
            	place.setPlacePhoto(placePhotoPath);
            }
            placeDAO.UpdatePlace(place);
            ctx.put("message",  java.net.URLEncoder.encode("Place��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Place��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��Place��Ϣ*/
    public String DeletePlace() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            placeDAO.DeletePlace(placeId);
            ctx.put("message",  java.net.URLEncoder.encode("Placeɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Placeɾ��ʧ��!"));
            return "error";
        }
    }

}
