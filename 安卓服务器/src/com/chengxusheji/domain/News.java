package com.chengxusheji.domain;

import java.sql.Timestamp;
public class News {
    /*����id*/
    private int newsId;
    public int getNewsId() {
        return newsId;
    }
    public void setNewsId(int newsId) {
        this.newsId = newsId;
    }

    /*���ŷ���*/
    private String newsType;
    public String getNewsType() {
        return newsType;
    }
    public void setNewsType(String newsType) {
        this.newsType = newsType;
    }

    /*���ű���*/
    private String title;
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    /*������ͼ*/
    private String newsPhoto;
    public String getNewsPhoto() {
        return newsPhoto;
    }
    public void setNewsPhoto(String newsPhoto) {
        this.newsPhoto = newsPhoto;
    }

    /*��������*/
    private String content;
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
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