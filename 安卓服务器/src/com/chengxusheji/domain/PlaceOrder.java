package com.chengxusheji.domain;

import java.sql.Timestamp;
public class PlaceOrder {
    /*‘§∂©id*/
    private int orderId;
    public int getOrderId() {
        return orderId;
    }
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    /*‘§∂©«Ú≥°*/
    private Place placeObj;
    public Place getPlaceObj() {
        return placeObj;
    }
    public void setPlaceObj(Place placeObj) {
        this.placeObj = placeObj;
    }

    /*‘§∂©»’∆⁄*/
    private Timestamp orderDate;
    public Timestamp getOrderDate() {
        return orderDate;
    }
    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    /*‘§∂© ±∂Œ*/
    private TimeSection timeSectionObj;
    public TimeSection getTimeSectionObj() {
        return timeSectionObj;
    }
    public void setTimeSectionObj(TimeSection timeSectionObj) {
        this.timeSectionObj = timeSectionObj;
    }

    /*‘§∂©»À*/
    private UserInfo userObj;
    public UserInfo getUserObj() {
        return userObj;
    }
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }

    /*‘§∂© ±º‰*/
    private String orderTime;
    public String getOrderTime() {
        return orderTime;
    }
    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    /*…Û∫À◊¥Ã¨*/
    private String shenHeState;
    public String getShenHeState() {
        return shenHeState;
    }
    public void setShenHeState(String shenHeState) {
        this.shenHeState = shenHeState;
    }

    /*…Û∫À ±º‰*/
    private String shenHeTime;
    public String getShenHeTime() {
        return shenHeTime;
    }
    public void setShenHeTime(String shenHeTime) {
        this.shenHeTime = shenHeTime;
    }

}