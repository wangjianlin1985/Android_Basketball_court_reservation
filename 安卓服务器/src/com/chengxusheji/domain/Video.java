package com.chengxusheji.domain;

import java.sql.Timestamp;
public class Video {
    /*��¼id*/
    private int videoId;
    public int getVideoId() {
        return videoId;
    }
    public void setVideoId(int videoId) {
        this.videoId = videoId;
    }

    /*��ѧ����*/
    private String title;
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    /*��ѧ���*/
    private VideoType videoTypeObj;
    public VideoType getVideoTypeObj() {
        return videoTypeObj;
    }
    public void setVideoTypeObj(VideoType videoTypeObj) {
        this.videoTypeObj = videoTypeObj;
    }

    /*��ѧͼƬ*/
    private String videoPhoto;
    public String getVideoPhoto() {
        return videoPhoto;
    }
    public void setVideoPhoto(String videoPhoto) {
        this.videoPhoto = videoPhoto;
    }

    /*��ѧ����*/
    private String content;
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    /*����λ��*/
    private String sportPos;
    public String getSportPos() {
        return sportPos;
    }
    public void setSportPos(String sportPos) {
        this.sportPos = sportPos;
    }

    /*��Ƶ�ļ�*/
    private String videoFile;
    public String getVideoFile() {
        return videoFile;
    }
    public void setVideoFile(String videoFile) {
        this.videoFile = videoFile;
    }

    /*�����*/
    private int hitNum;
    public int getHitNum() {
        return hitNum;
    }
    public void setHitNum(int hitNum) {
        this.hitNum = hitNum;
    }

    /*����ʱ��*/
    private String publishTime;
    public String getPublishTime() {
        return publishTime;
    }
    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

}