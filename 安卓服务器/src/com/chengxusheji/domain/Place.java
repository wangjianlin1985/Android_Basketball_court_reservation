package com.chengxusheji.domain;

import java.sql.Timestamp;
public class Place {
    /*����id*/
    private int placeId;
    public int getPlaceId() {
        return placeId;
    }
    public void setPlaceId(int placeId) {
        this.placeId = placeId;
    }

    /*��������*/
    private String placeName;
    public String getPlaceName() {
        return placeName;
    }
    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    /*��ͼƬ*/
    private String placePhoto;
    public String getPlacePhoto() {
        return placePhoto;
    }
    public void setPlacePhoto(String placePhoto) {
        this.placePhoto = placePhoto;
    }

    /*�򳡵�ַ*/
    private String placePos;
    public String getPlacePos() {
        return placePos;
    }
    public void setPlacePos(String placePos) {
        this.placePos = placePos;
    }

    /*��ϵ�绰*/
    private String telephone;
    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /*�򳡼۸�*/
    private float placePrice;
    public float getPlacePrice() {
        return placePrice;
    }
    public void setPlacePrice(float placePrice) {
        this.placePrice = placePrice;
    }

    /*�򳡽���*/
    private String placeDesc;
    public String getPlaceDesc() {
        return placeDesc;
    }
    public void setPlaceDesc(String placeDesc) {
        this.placeDesc = placeDesc;
    }

    /*Ӫҵʱ��*/
    private String onlineTime;
    public String getOnlineTime() {
        return onlineTime;
    }
    public void setOnlineTime(String onlineTime) {
        this.onlineTime = onlineTime;
    }

    
    /*����*/
    private int sellNum; 
    public int getSellNum() {
		return sellNum;
	}
	public void setSellNum(int sellNum) {
		this.sellNum = sellNum;
	}

	/*�Ƿ��ö�*/
    private int topFlag;
    public int getTopFlag() {
        return topFlag;
    }
    public void setTopFlag(int topFlag) {
        this.topFlag = topFlag;
    }

    /*����ʱ��*/
    private String addTime;
    public String getAddTime() {
        return addTime;
    }
    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

}