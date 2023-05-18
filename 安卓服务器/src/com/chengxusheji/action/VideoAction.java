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
import com.chengxusheji.dao.VideoDAO;
import com.chengxusheji.domain.Video;
import com.chengxusheji.dao.VideoTypeDAO;
import com.chengxusheji.domain.VideoType;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class VideoAction extends BaseAction {

	/*ͼƬ���ļ��ֶ�videoPhoto��������*/
	private File videoPhotoFile;
	private String videoPhotoFileFileName;
	private String videoPhotoFileContentType;
	public File getVideoPhotoFile() {
		return videoPhotoFile;
	}
	public void setVideoPhotoFile(File videoPhotoFile) {
		this.videoPhotoFile = videoPhotoFile;
	}
	public String getVideoPhotoFileFileName() {
		return videoPhotoFileFileName;
	}
	public void setVideoPhotoFileFileName(String videoPhotoFileFileName) {
		this.videoPhotoFileFileName = videoPhotoFileFileName;
	}
	public String getVideoPhotoFileContentType() {
		return videoPhotoFileContentType;
	}
	public void setVideoPhotoFileContentType(String videoPhotoFileContentType) {
		this.videoPhotoFileContentType = videoPhotoFileContentType;
	}
	/*ͼƬ���ļ��ֶ�videoFile��������*/
	private File videoFileFile;
	private String videoFileFileFileName;
	private String videoFileFileContentType;
	public File getVideoFileFile() {
		return videoFileFile;
	}
	public void setVideoFileFile(File videoFileFile) {
		this.videoFileFile = videoFileFile;
	}
	public String getVideoFileFileFileName() {
		return videoFileFileFileName;
	}
	public void setVideoFileFileFileName(String videoFileFileFileName) {
		this.videoFileFileFileName = videoFileFileFileName;
	}
	public String getVideoFileFileContentType() {
		return videoFileFileContentType;
	}
	public void setVideoFileFileContentType(String videoFileFileContentType) {
		this.videoFileFileContentType = videoFileFileContentType;
	}
    /*�������Ҫ��ѯ������: ��ѧ����*/
    private String title;
    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return this.title;
    }

    /*�������Ҫ��ѯ������: ��ѧ���*/
    private VideoType videoTypeObj;
    public void setVideoTypeObj(VideoType videoTypeObj) {
        this.videoTypeObj = videoTypeObj;
    }
    public VideoType getVideoTypeObj() {
        return this.videoTypeObj;
    }

    /*�������Ҫ��ѯ������: ����λ��*/
    private String sportPos;
    public void setSportPos(String sportPos) {
        this.sportPos = sportPos;
    }
    public String getSportPos() {
        return this.sportPos;
    }

    /*�������Ҫ��ѯ������: ����ʱ��*/
    private String publishTime;
    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }
    public String getPublishTime() {
        return this.publishTime;
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

    private int videoId;
    public void setVideoId(int videoId) {
        this.videoId = videoId;
    }
    public int getVideoId() {
        return videoId;
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
    @Resource VideoTypeDAO videoTypeDAO;
    @Resource VideoDAO videoDAO;

    /*��������Video����*/
    private Video video;
    public void setVideo(Video video) {
        this.video = video;
    }
    public Video getVideo() {
        return this.video;
    }

    /*��ת�����Video��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*��ѯ���е�VideoType��Ϣ*/
        List<VideoType> videoTypeList = videoTypeDAO.QueryAllVideoTypeInfo();
        ctx.put("videoTypeList", videoTypeList);
        return "add_view";
    }

    /*���Video��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddVideo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            VideoType videoTypeObj = videoTypeDAO.GetVideoTypeByTypeId(video.getVideoTypeObj().getTypeId());
            video.setVideoTypeObj(videoTypeObj);
            /*�����ѧͼƬ�ϴ�*/
            String videoPhotoPath = "upload/noimage.jpg"; 
       	 	if(videoPhotoFile != null)
       	 		videoPhotoPath = photoUpload(videoPhotoFile,videoPhotoFileContentType);
       	 	video.setVideoPhoto(videoPhotoPath);
            /*������Ƶ�ļ��ϴ�*/
            String videoFilePath = ""; 
       	 	if(videoFileFile != null)
       	 		videoFilePath = fileUpload(videoFileFile, videoFileFileFileName);
       	 	video.setVideoFile(videoFilePath);
            videoDAO.AddVideo(video);
            ctx.put("message",  java.net.URLEncoder.encode("Video��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Video���ʧ��!"));
            return "error";
        }
    }

    /*��ѯVideo��Ϣ*/
    public String QueryVideo() {
        if(currentPage == 0) currentPage = 1;
        if(title == null) title = "";
        if(sportPos == null) sportPos = "";
        if(publishTime == null) publishTime = "";
        List<Video> videoList = videoDAO.QueryVideoInfo(title, videoTypeObj, sportPos, publishTime, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        videoDAO.CalculateTotalPageAndRecordNumber(title, videoTypeObj, sportPos, publishTime);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = videoDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = videoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("videoList",  videoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("title", title);
        ctx.put("videoTypeObj", videoTypeObj);
        List<VideoType> videoTypeList = videoTypeDAO.QueryAllVideoTypeInfo();
        ctx.put("videoTypeList", videoTypeList);
        ctx.put("sportPos", sportPos);
        ctx.put("publishTime", publishTime);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryVideoOutputToExcel() { 
        if(title == null) title = "";
        if(sportPos == null) sportPos = "";
        if(publishTime == null) publishTime = "";
        List<Video> videoList = videoDAO.QueryVideoInfo(title,videoTypeObj,sportPos,publishTime);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Video��Ϣ��¼"; 
        String[] headers = { "��¼id","��ѧ����","��ѧ���","��ѧͼƬ","����λ��","�����","����ʱ��"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<videoList.size();i++) {
        	Video video = videoList.get(i); 
        	dataset.add(new String[]{video.getVideoId() + "",video.getTitle(),video.getVideoTypeObj().getTypeName(),
video.getVideoPhoto(),video.getSportPos(),video.getHitNum() + "",video.getPublishTime()});
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
			response.setHeader("Content-disposition","attachment; filename="+"Video.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯVideo��Ϣ*/
    public String FrontQueryVideo() {
        if(currentPage == 0) currentPage = 1;
        if(title == null) title = "";
        if(sportPos == null) sportPos = "";
        if(publishTime == null) publishTime = "";
        List<Video> videoList = videoDAO.QueryVideoInfo(title, videoTypeObj, sportPos, publishTime, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        videoDAO.CalculateTotalPageAndRecordNumber(title, videoTypeObj, sportPos, publishTime);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = videoDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = videoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("videoList",  videoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("title", title);
        ctx.put("videoTypeObj", videoTypeObj);
        List<VideoType> videoTypeList = videoTypeDAO.QueryAllVideoTypeInfo();
        ctx.put("videoTypeList", videoTypeList);
        ctx.put("sportPos", sportPos);
        ctx.put("publishTime", publishTime);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�Video��Ϣ*/
    public String ModifyVideoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������videoId��ȡVideo����*/
        Video video = videoDAO.GetVideoByVideoId(videoId);

        List<VideoType> videoTypeList = videoTypeDAO.QueryAllVideoTypeInfo();
        ctx.put("videoTypeList", videoTypeList);
        ctx.put("video",  video);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�Video��Ϣ*/
    public String FrontShowVideoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������videoId��ȡVideo����*/
        Video video = videoDAO.GetVideoByVideoId(videoId);

        List<VideoType> videoTypeList = videoTypeDAO.QueryAllVideoTypeInfo();
        ctx.put("videoTypeList", videoTypeList);
        ctx.put("video",  video);
        return "front_show_view";
    }

    /*�����޸�Video��Ϣ*/
    public String ModifyVideo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            VideoType videoTypeObj = videoTypeDAO.GetVideoTypeByTypeId(video.getVideoTypeObj().getTypeId());
            video.setVideoTypeObj(videoTypeObj);
            /*�����ѧͼƬ�ϴ�*/
            if(videoPhotoFile != null) {
            	String videoPhotoPath = photoUpload(videoPhotoFile,videoPhotoFileContentType);
            	video.setVideoPhoto(videoPhotoPath);
            }
            String path = ServletActionContext.getServletContext().getRealPath("/upload"); 
            /*������Ƶ�ļ��ϴ�*/
            if(videoFileFile != null)
       	 		video.setVideoFile(fileUpload(videoFileFile, videoFileFileFileName));
            videoDAO.UpdateVideo(video);
            ctx.put("message",  java.net.URLEncoder.encode("Video��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Video��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��Video��Ϣ*/
    public String DeleteVideo() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            videoDAO.DeleteVideo(videoId);
            ctx.put("message",  java.net.URLEncoder.encode("Videoɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Videoɾ��ʧ��!"));
            return "error";
        }
    }

}
