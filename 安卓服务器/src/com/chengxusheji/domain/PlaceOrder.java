package com.chengxusheji.domain;

import java.sql.Timestamp;
public class PlaceOrder {
    /*Ԥ��id*/
    private int orderId;
    public int getOrderId() {
        return orderId;
    }
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    /*Ԥ����*/
    private Place placeObj;
    public Place getPlaceObj() {
        return placeObj;
    }
    public void setPlaceObj(Place placeObj) {
        this.placeObj = placeObj;
    }

    /*Ԥ������*/
    private Timestamp orderDate;
    public Timestamp getOrderDate() {
        return orderDate;
    }
    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    /*Ԥ��ʱ��*/
    private TimeSection timeSectionObj;
    public TimeSection getTimeSectionObj() {
        return timeSectionObj;
    }
    public void setTimeSectionObj(TimeSection timeSectionObj) {
        this.timeSectionObj = timeSectionObj;
    }

    /*Ԥ����*/
    private UserInfo userObj;
    public UserInfo getUserObj() {
        return userObj;
    }
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }

    /*Ԥ��ʱ��*/
    private String orderTime;
    public String getOrderTime() {
        return orderTime;
    }
    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    /*���״̬*/
    private String shenHeState;
    public String getShenHeState() {
        return shenHeState;
    }
    public void setShenHeState(String shenHeState) {
        this.shenHeState = shenHeState;
    }

    /*���ʱ��*/
    private String shenHeTime;
    public String getShenHeTime() {
        return shenHeTime;
    }
    public void setShenHeTime(String shenHeTime) {
        this.shenHeTime = shenHeTime;
    }

}