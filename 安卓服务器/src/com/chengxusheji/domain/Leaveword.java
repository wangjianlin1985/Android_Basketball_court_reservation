package com.chengxusheji.domain;

import java.sql.Timestamp;
public class Leaveword {
    /*����id*/
    private int leaveWordId;
    public int getLeaveWordId() {
        return leaveWordId;
    }
    public void setLeaveWordId(int leaveWordId) {
        this.leaveWordId = leaveWordId;
    }

    /*Լս����*/
    private String leaveTitle;
    public String getLeaveTitle() {
        return leaveTitle;
    }
    public void setLeaveTitle(String leaveTitle) {
        this.leaveTitle = leaveTitle;
    }

    /*Լս����*/
    private String leaveContent;
    public String getLeaveContent() {
        return leaveContent;
    }
    public void setLeaveContent(String leaveContent) {
        this.leaveContent = leaveContent;
    }

    /*Լս�绰*/
    private String telephone;
    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /*Լս��*/
    private UserInfo userObj;
    public UserInfo getUserObj() {
        return userObj;
    }
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }

    /*Լսʱ��*/
    private String leaveTime;
    public String getLeaveTime() {
        return leaveTime;
    }
    public void setLeaveTime(String leaveTime) {
        this.leaveTime = leaveTime;
    }

}