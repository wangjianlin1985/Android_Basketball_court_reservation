package com.chengxusheji.domain;

import java.sql.Timestamp;
public class TimeSection {
    /*��¼id*/
    private int sectionId;
    public int getSectionId() {
        return sectionId;
    }
    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }

    /*ʱ������*/
    private String sectionName;
    public String getSectionName() {
        return sectionName;
    }
    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

}