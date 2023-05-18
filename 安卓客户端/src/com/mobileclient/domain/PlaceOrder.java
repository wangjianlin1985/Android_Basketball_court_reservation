package com.mobileclient.domain;

import java.io.Serializable;

public class PlaceOrder implements Serializable {
    /*预订id*/
    private int orderId;
    public int getOrderId() {
        return orderId;
    }
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    /*预订球场*/
    private int placeObj;
    public int getPlaceObj() {
        return placeObj;
    }
    public void setPlaceObj(int placeObj) {
        this.placeObj = placeObj;
    }

    /*预订日期*/
    private java.sql.Timestamp orderDate;
    public java.sql.Timestamp getOrderDate() {
        return orderDate;
    }
    public void setOrderDate(java.sql.Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    /*预订时段*/
    private int timeSectionObj;
    public int getTimeSectionObj() {
        return timeSectionObj;
    }
    public void setTimeSectionObj(int timeSectionObj) {
        this.timeSectionObj = timeSectionObj;
    }

    /*预订人*/
    private String userObj;
    public String getUserObj() {
        return userObj;
    }
    public void setUserObj(String userObj) {
        this.userObj = userObj;
    }

    /*预订时间*/
    private String orderTime;
    public String getOrderTime() {
        return orderTime;
    }
    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    /*审核状态*/
    private String shenHeState;
    public String getShenHeState() {
        return shenHeState;
    }
    public void setShenHeState(String shenHeState) {
        this.shenHeState = shenHeState;
    }

    /*审核时间*/
    private String shenHeTime;
    public String getShenHeTime() {
        return shenHeTime;
    }
    public void setShenHeTime(String shenHeTime) {
        this.shenHeTime = shenHeTime;
    }

}