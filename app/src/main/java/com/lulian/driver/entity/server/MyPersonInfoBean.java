package com.lulian.driver.entity.server;

import java.io.Serializable;

/**
 * 我的个人信息对象
 * @author hxb
 */
public class MyPersonInfoBean implements Serializable {

    private String InviteCode;
    private String Name;
    private String Phone;
    private String IdCard;

    private String HandUdentityURL;
    private String IdUrl;
    private String IdUrl2;
    private String LlicUrl;
    private String LlicUrl2;


    public String getInviteCode() {
        return InviteCode;
    }

    public void setInviteCode(String inviteCode) {
        InviteCode = inviteCode;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getIdCard() {
        return IdCard;
    }

    public void setIdCard(String idCard) {
        IdCard = idCard;
    }

    public String getHandUdentityURL() {
        return HandUdentityURL;
    }

    public void setHandUdentityURL(String handUdentityURL) {
        HandUdentityURL = handUdentityURL;
    }

    public String getIdUrl() {
        return IdUrl;
    }

    public void setIdUrl(String idUrl) {
        IdUrl = idUrl;
    }

    public String getIdUrl2() {
        return IdUrl2;
    }

    public void setIdUrl2(String idUrl2) {
        IdUrl2 = idUrl2;
    }

    public String getLlicUrl() {
        return LlicUrl;
    }

    public void setLlicUrl(String llicUrl) {
        LlicUrl = llicUrl;
    }

    public String getLlicUrl2() {
        return LlicUrl2;
    }

    public void setLlicUrl2(String llicUrl2) {
        LlicUrl2 = llicUrl2;
    }
}
