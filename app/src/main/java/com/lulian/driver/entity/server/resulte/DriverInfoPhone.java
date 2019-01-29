package com.lulian.driver.entity.server.resulte;

import java.io.Serializable;

/**
 * Created by MARK on 2018/6/21.
 */

public class DriverInfoPhone implements Serializable{
   /* "Id": "a2d5755c-ac7e-42f6-8d52-97cd70f2cd89",
      "Name": "zxz",
      "Star": 10,
      "StartAreaId": "山东济南市历下区",
      "EndAreaId": "江苏南京市玄武区",
      "RegState": 0,
      "Phone": "15580815744",
      "HeaderUrl": null,
      "CompanyReg": null*/

    private String Id;
    private String Name;
    private int Star;
    private String StartAreaId;
    private String EndAreaId;
    private int RegState;
    private String Phone;
    private String HeaderUrl;
    private String CompanyReg;

    public String getCompanyReg() {
        return CompanyReg;
    }

    public void setCompanyReg(String companyReg) {
        CompanyReg = companyReg;
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

    public int getStar() {
        return Star;
    }

    public void setStar(int star) {
        Star = star;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getStartAreaId() {
        return StartAreaId;
    }

    public void setStartAreaId(String startAreaId) {
        StartAreaId = startAreaId;
    }

    public String getEndAreaId() {
        return EndAreaId;
    }

    public void setEndAreaId(String endAreaId) {
        EndAreaId = endAreaId;
    }

    public int getRegState() {
        return RegState;
    }

    public void setRegState(int regState) {
        RegState = regState;
    }

    public String getHeaderUrl() {
        return HeaderUrl;
    }

    public void setHeaderUrl(String headerUrl) {
        HeaderUrl = headerUrl;
    }


}
