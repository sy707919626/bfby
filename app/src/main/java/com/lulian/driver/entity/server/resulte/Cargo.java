package com.lulian.driver.entity.server.resulte;

import java.io.Serializable;

/**
 * Created by MARK on 2018/7/13.
 */

public class Cargo implements Serializable{
    private String Id;
    private String Name;
    private String Phone;
    private String HaveCertification;
    private String RegState;
    private String HeaderUrl;
    private int Star;

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getHaveCertification() {
        return HaveCertification;
    }

    public void setHaveCertification(String haveCertification) {
        HaveCertification = haveCertification;
    }

    public String getRegState() {
        return RegState;
    }

    public void setRegState(String regState) {
        RegState = regState;
    }

    public String getHeaderUrl() {
        return HeaderUrl;
    }

    public void setHeaderUrl(String headerUrl) {
        HeaderUrl = headerUrl;
    }

    public int getStar() {
        return Star;
    }

    public void setStar(int star) {
        Star = star;
    }
}
