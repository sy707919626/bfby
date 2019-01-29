package com.lulian.driver.entity.server.resulte;

/**
 * Created by Administrator on 2018/8/7.
 */

/*
* "Id": "49ad954c-df3e-4431-8c54-054c7b4cf3b6",
    "Name": "zzz",
    "HaveCertification": null,
    "RegState": 0,
    "HeaderUrl": null,
    "Star": 10
*/
public class DriverLead {
    private String Id;
    private String Name;
    private String HaveCertification;
    private String RegState;
    private String HeaderUrl;
    private int Star;

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
