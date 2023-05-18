package com.mobileclient.domain;

import java.io.Serializable;

public class TimeSection implements Serializable {
    /*记录id*/
    private int sectionId;
    public int getSectionId() {
        return sectionId;
    }
    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }

    /*时段名称*/
    private String sectionName;
    public String getSectionName() {
        return sectionName;
    }
    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

}