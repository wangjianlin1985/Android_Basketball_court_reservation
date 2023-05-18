package com.chengxusheji.domain;

import java.sql.Timestamp;
public class Place {
    /*场地id*/
    private int placeId;
    public int getPlaceId() {
        return placeId;
    }
    public void setPlaceId(int placeId) {
        this.placeId = placeId;
    }

    /*场地名称*/
    private String placeName;
    public String getPlaceName() {
        return placeName;
    }
    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    /*球场图片*/
    private String placePhoto;
    public String getPlacePhoto() {
        return placePhoto;
    }
    public void setPlacePhoto(String placePhoto) {
        this.placePhoto = placePhoto;
    }

    /*球场地址*/
    private String placePos;
    public String getPlacePos() {
        return placePos;
    }
    public void setPlacePos(String placePos) {
        this.placePos = placePos;
    }

    /*联系电话*/
    private String telephone;
    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /*球场价格*/
    private float placePrice;
    public float getPlacePrice() {
        return placePrice;
    }
    public void setPlacePrice(float placePrice) {
        this.placePrice = placePrice;
    }

    /*球场介绍*/
    private String placeDesc;
    public String getPlaceDesc() {
        return placeDesc;
    }
    public void setPlaceDesc(String placeDesc) {
        this.placeDesc = placeDesc;
    }

    /*营业时间*/
    private String onlineTime;
    public String getOnlineTime() {
        return onlineTime;
    }
    public void setOnlineTime(String onlineTime) {
        this.onlineTime = onlineTime;
    }

    
    /*销量*/
    private int sellNum; 
    public int getSellNum() {
		return sellNum;
	}
	public void setSellNum(int sellNum) {
		this.sellNum = sellNum;
	}

	/*是否置顶*/
    private int topFlag;
    public int getTopFlag() {
        return topFlag;
    }
    public void setTopFlag(int topFlag) {
        this.topFlag = topFlag;
    }

    /*发布时间*/
    private String addTime;
    public String getAddTime() {
        return addTime;
    }
    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

}