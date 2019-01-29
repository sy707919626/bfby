package com.lulian.driver.entity.server.resulte;

import java.io.Serializable;

/**
 * Created by MARK on 2018/7/9.
 */

public class OrderDetailNew implements Serializable {

    /*
    "id": "e97cc203-ed5e-4a9a-b150-397ef65b8d83",
    "Number": "D201808011014000004",
    "Phone": null,
    "Status": 5,
    "OnLoadArea": "上海 上海市 黄浦区",
    "UnLoadArea": "安徽 合肥市 瑶海区",
    "AutomobileTypName": null,
    "AutomobileLength": 10,
    "DriverUserId": "71CA273B-638B-4783-846D-CE65D3AD38D3",
    "goods": "香蕉&#x20;&#x20;",
    "Name": "zzz",
    "HeaderUrl": null,
    "MindOnloadTime": "2018-08-01 02:13:00",
    "MinUnLoadTime": "2018-08-01 02:13:00",
    "otherminaMin": "备注",
    "SendType": null,
    "AcceptState": 0,
    "Remark": null
    * */
    private String Id;
    private String Number;
    private String Phone;
    private int Status;
    private String OnLoadArea;
    private String UnLoadArea;
    private String AutomobileTypName;
    private int AutomobileLength;
    private String DriverUserId;
    private String goods;
    private String Name;
    private String HeaderUrl;
    private String MindOnloadTime;
    private String MinUnLoadTime;
    private String otherminaMin;
    private String SendType;
    private String AcceptState;
    private String Remark;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public String getOnLoadArea() {
        return OnLoadArea;
    }

    public void setOnLoadArea(String onLoadArea) {
        OnLoadArea = onLoadArea;
    }

    public String getUnLoadArea() {
        return UnLoadArea;
    }

    public void setUnLoadArea(String unLoadArea) {
        UnLoadArea = unLoadArea;
    }

    public String getAutomobileTypName() {
        return AutomobileTypName;
    }

    public void setAutomobileTypName(String automobileTypName) {
        AutomobileTypName = automobileTypName;
    }

    public int getAutomobileLength() {
        return AutomobileLength;
    }

    public void setAutomobileLength(int automobileLength) {
        AutomobileLength = automobileLength;
    }

    public String getDriverUserId() {
        return DriverUserId;
    }

    public void setDriverUserId(String driverUserId) {
        DriverUserId = driverUserId;
    }

    public String getGoods() {
        return goods;
    }

    public void setGoods(String goods) {
        this.goods = goods;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getHeaderUrl() {
        return HeaderUrl;
    }

    public void setHeaderUrl(String headerUrl) {
        HeaderUrl = headerUrl;
    }

    public String getMindOnloadTime() {
        return MindOnloadTime;
    }

    public void setMindOnloadTime(String mindOnloadTime) {
        MindOnloadTime = mindOnloadTime;
    }

    public String getMinUnLoadTime() {
        return MinUnLoadTime;
    }

    public void setMinUnLoadTime(String minUnLoadTime) {
        MinUnLoadTime = minUnLoadTime;
    }

    public String getOtherminaMin() {
        return otherminaMin;
    }

    public void setOtherminaMin(String otherminaMin) {
        this.otherminaMin = otherminaMin;
    }

    public String getSendType() {
        return SendType;
    }

    public void setSendType(String sendType) {
        SendType = sendType;
    }

    public String getAcceptState() {
        return AcceptState;
    }

    public void setAcceptState(String acceptState) {
        AcceptState = acceptState;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }
}
