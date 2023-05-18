package com.chengxusheji.domain;

import java.sql.Timestamp;
public class VideoType {
    /*类别id*/
    private int typeId;
    public int getTypeId() {
        return typeId;
    }
    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    /*类别名称*/
    private String typeName;
    public String getTypeName() {
        return typeName;
    }
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

}