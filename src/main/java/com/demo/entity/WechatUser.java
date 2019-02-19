package com.demo.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.GeoPointField;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

@Document(indexName = "nearby_search",type = "wechat_user")
public class WechatUser {

    //@GeoPointField
    private GeoPoint location;

    private String sex;

    @Id
    private String wxId;

    private String nickName;
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getWxId() {
        return wxId;
    }

    public void setWxId(String wxId) {
        this.wxId = wxId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public GeoPoint getLocation() {
        return location;
    }

    public void setLocation(GeoPoint location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "WechatUser{" +
                "location=" + location +
                ", sex='" + sex + '\'' +
                ", wxId='" + wxId + '\'' +
                ", nickName='" + nickName + '\'' +
                '}';
    }
}
