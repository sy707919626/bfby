package com.lulian.driver.entity.server.resulte;

import java.io.Serializable;

/**
 * Created by MARK on 2018/6/21.
 */

public class DriverLeaderInfo implements Serializable {
   /* "Id": "001",
      "Name": "张三",
      "Star": 0,
      "StartAreaId": null,
      "EndAreaId": null,
      "RegState": 0,
      "Phone": "",
      "HeaderUrl": "string",
      "OrderCount": 0,
      "Favorite": 1*/

    private String Id;
    private String Name;
    private int Star;
    private String Phone;
    private String StartAreaId;
    private String EndAreaId;
    private String StartAreaName;
    private String EndAreaName;
    private int RegState;
    private String HeaderUrl;
    private int OrderCount;
    private int Favorite;

    public String getStartAreaName() {
        return StartAreaName;
    }

    public void setStartAreaName(String startAreaName) {
        StartAreaName = startAreaName;
    }

    public String getEndAreaName() {
        return EndAreaName;
    }

    public void setEndAreaName(String endAreaName) {
        EndAreaName = endAreaName;
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

    public int getOrderCount() {
        return OrderCount;
    }

    public void setOrderCount(int orderCount) {
        OrderCount = orderCount;
    }

    public int getFavorite() {
        return Favorite;
    }

    public void setFavorite(int favorite) {
        Favorite = favorite;
    }
}
