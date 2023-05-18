package com.mobileclient.domain;

import java.io.Serializable;

public class Video implements Serializable {
    /*记录id*/
    private int videoId;
    public int getVideoId() {
        return videoId;
    }
    public void setVideoId(int videoId) {
        this.videoId = videoId;
    }

    /*教学标题*/
    private String title;
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    /*教学类别*/
    private int videoTypeObj;
    public int getVideoTypeObj() {
        return videoTypeObj;
    }
    public void setVideoTypeObj(int videoTypeObj) {
        this.videoTypeObj = videoTypeObj;
    }

    /*教学图片*/
    private String videoPhoto;
    public String getVideoPhoto() {
        return videoPhoto;
    }
    public void setVideoPhoto(String videoPhoto) {
        this.videoPhoto = videoPhoto;
    }

    /*教学内容*/
    private String content;
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    /*所打位置*/
    private String sportPos;
    public String getSportPos() {
        return sportPos;
    }
    public void setSportPos(String sportPos) {
        this.sportPos = sportPos;
    }

    /*视频文件*/
    private String videoFile;
    public String getVideoFile() {
        return videoFile;
    }
    public void setVideoFile(String videoFile) {
        this.videoFile = videoFile;
    }

    /*点击率*/
    private int hitNum;
    public int getHitNum() {
        return hitNum;
    }
    public void setHitNum(int hitNum) {
        this.hitNum = hitNum;
    }

    /*发布时间*/
    private String publishTime;
    public String getPublishTime() {
        return publishTime;
    }
    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

}