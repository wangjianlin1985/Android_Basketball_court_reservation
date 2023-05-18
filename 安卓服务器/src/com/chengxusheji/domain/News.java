package com.chengxusheji.domain;

import java.sql.Timestamp;
public class News {
    /*新闻id*/
    private int newsId;
    public int getNewsId() {
        return newsId;
    }
    public void setNewsId(int newsId) {
        this.newsId = newsId;
    }

    /*新闻分类*/
    private String newsType;
    public String getNewsType() {
        return newsType;
    }
    public void setNewsType(String newsType) {
        this.newsType = newsType;
    }

    /*新闻标题*/
    private String title;
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    /*新闻主图*/
    private String newsPhoto;
    public String getNewsPhoto() {
        return newsPhoto;
    }
    public void setNewsPhoto(String newsPhoto) {
        this.newsPhoto = newsPhoto;
    }

    /*新闻内容*/
    private String content;
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
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