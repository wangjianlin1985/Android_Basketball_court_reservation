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

	/*图片或文件字段videoPhoto参数接收*/
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
	/*图片或文件字段videoFile参数接收*/
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
    /*界面层需要查询的属性: 教学标题*/
    private String title;
    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return this.title;
    }

    /*界面层需要查询的属性: 教学类别*/
    private VideoType videoTypeObj;
    public void setVideoTypeObj(VideoType videoTypeObj) {
        this.videoTypeObj = videoTypeObj;
    }
    public VideoType getVideoTypeObj() {
        return this.videoTypeObj;
    }

    /*界面层需要查询的属性: 所打位置*/
    private String sportPos;
    public void setSportPos(String sportPos) {
        this.sportPos = sportPos;
    }
    public String getSportPos() {
        return this.sportPos;
    }

    /*界面层需要查询的属性: 发布时间*/
    private String publishTime;
    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }
    public String getPublishTime() {
        return this.publishTime;
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

    private int videoId;
    public void setVideoId(int videoId) {
        this.videoId = videoId;
    }
    public int getVideoId() {
        return videoId;
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
    @Resource VideoTypeDAO videoTypeDAO;
    @Resource VideoDAO videoDAO;

    /*待操作的Video对象*/
    private Video video;
    public void setVideo(Video video) {
        this.video = video;
    }
    public Video getVideo() {
        return this.video;
    }

    /*跳转到添加Video视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*查询所有的VideoType信息*/
        List<VideoType> videoTypeList = videoTypeDAO.QueryAllVideoTypeInfo();
        ctx.put("videoTypeList", videoTypeList);
        return "add_view";
    }

    /*添加Video信息*/
    @SuppressWarnings("deprecation")
    public String AddVideo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            VideoType videoTypeObj = videoTypeDAO.GetVideoTypeByTypeId(video.getVideoTypeObj().getTypeId());
            video.setVideoTypeObj(videoTypeObj);
            /*处理教学图片上传*/
            String videoPhotoPath = "upload/noimage.jpg"; 
       	 	if(videoPhotoFile != null)
       	 		videoPhotoPath = photoUpload(videoPhotoFile,videoPhotoFileContentType);
       	 	video.setVideoPhoto(videoPhotoPath);
            /*处理视频文件上传*/
            String videoFilePath = ""; 
       	 	if(videoFileFile != null)
       	 		videoFilePath = fileUpload(videoFileFile, videoFileFileFileName);
       	 	video.setVideoFile(videoFilePath);
            videoDAO.AddVideo(video);
            ctx.put("message",  java.net.URLEncoder.encode("Video添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Video添加失败!"));
            return "error";
        }
    }

    /*查询Video信息*/
    public String QueryVideo() {
        if(currentPage == 0) currentPage = 1;
        if(title == null) title = "";
        if(sportPos == null) sportPos = "";
        if(publishTime == null) publishTime = "";
        List<Video> videoList = videoDAO.QueryVideoInfo(title, videoTypeObj, sportPos, publishTime, currentPage);
        /*计算总的页数和总的记录数*/
        videoDAO.CalculateTotalPageAndRecordNumber(title, videoTypeObj, sportPos, publishTime);
        /*获取到总的页码数目*/
        totalPage = videoDAO.getTotalPage();
        /*当前查询条件下总记录数*/
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

    /*后台导出到excel*/
    public String QueryVideoOutputToExcel() { 
        if(title == null) title = "";
        if(sportPos == null) sportPos = "";
        if(publishTime == null) publishTime = "";
        List<Video> videoList = videoDAO.QueryVideoInfo(title,videoTypeObj,sportPos,publishTime);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Video信息记录"; 
        String[] headers = { "记录id","教学标题","教学类别","教学图片","所打位置","点击率","发布时间"};
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
		HttpServletResponse response = null;//创建一个HttpServletResponse对象 
		OutputStream out = null;//创建一个输出流对象 
		try { 
			response = ServletActionContext.getResponse();//初始化HttpServletResponse对象 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"Video.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询Video信息*/
    public String FrontQueryVideo() {
        if(currentPage == 0) currentPage = 1;
        if(title == null) title = "";
        if(sportPos == null) sportPos = "";
        if(publishTime == null) publishTime = "";
        List<Video> videoList = videoDAO.QueryVideoInfo(title, videoTypeObj, sportPos, publishTime, currentPage);
        /*计算总的页数和总的记录数*/
        videoDAO.CalculateTotalPageAndRecordNumber(title, videoTypeObj, sportPos, publishTime);
        /*获取到总的页码数目*/
        totalPage = videoDAO.getTotalPage();
        /*当前查询条件下总记录数*/
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

    /*查询要修改的Video信息*/
    public String ModifyVideoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键videoId获取Video对象*/
        Video video = videoDAO.GetVideoByVideoId(videoId);

        List<VideoType> videoTypeList = videoTypeDAO.QueryAllVideoTypeInfo();
        ctx.put("videoTypeList", videoTypeList);
        ctx.put("video",  video);
        return "modify_view";
    }

    /*查询要修改的Video信息*/
    public String FrontShowVideoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键videoId获取Video对象*/
        Video video = videoDAO.GetVideoByVideoId(videoId);

        List<VideoType> videoTypeList = videoTypeDAO.QueryAllVideoTypeInfo();
        ctx.put("videoTypeList", videoTypeList);
        ctx.put("video",  video);
        return "front_show_view";
    }

    /*更新修改Video信息*/
    public String ModifyVideo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            VideoType videoTypeObj = videoTypeDAO.GetVideoTypeByTypeId(video.getVideoTypeObj().getTypeId());
            video.setVideoTypeObj(videoTypeObj);
            /*处理教学图片上传*/
            if(videoPhotoFile != null) {
            	String videoPhotoPath = photoUpload(videoPhotoFile,videoPhotoFileContentType);
            	video.setVideoPhoto(videoPhotoPath);
            }
            String path = ServletActionContext.getServletContext().getRealPath("/upload"); 
            /*处理视频文件上传*/
            if(videoFileFile != null)
       	 		video.setVideoFile(fileUpload(videoFileFile, videoFileFileFileName));
            videoDAO.UpdateVideo(video);
            ctx.put("message",  java.net.URLEncoder.encode("Video信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Video信息更新失败!"));
            return "error";
       }
   }

    /*删除Video信息*/
    public String DeleteVideo() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            videoDAO.DeleteVideo(videoId);
            ctx.put("message",  java.net.URLEncoder.encode("Video删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Video删除失败!"));
            return "error";
        }
    }

}
