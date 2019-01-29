package com.lulian.driver.entity.server.resulte;

import java.io.Serializable;

/**
 * Created by MARK on 2018/7/13.
 */

public class Driver implements Serializable{
    private String id;
    private String Orderid;
    private String name;
    private String Phone;
    private int HaveCertification;
    private String RegState;
    private String PlateNo;
    private String VehicleHeight;
    private String VehicleWeight;
    private String VehicleType;
    private String Vehicle;
    private String HeaderUrl;
    private int Star;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderid() {
        return Orderid;
    }

    public void setOrderid(String orderid) {
        Orderid = orderid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public int getHaveCertification() {
        return HaveCertification;
    }

    public void setHaveCertification(int haveCertification) {
        HaveCertification = haveCertification;
    }

    public String getRegState() {
        return RegState;
    }

    public void setRegState(String regState) {
        RegState = regState;
    }

    public String getPlateNo() {
        return PlateNo;
    }

    public void setPlateNo(String plateNo) {
        PlateNo = plateNo;
    }

    public String getVehicleHeight() {
        return VehicleHeight;
    }

    public void setVehicleHeight(String vehicleHeight) {
        VehicleHeight = vehicleHeight;
    }

    public String getVehicleWeight() {
        return VehicleWeight;
    }

    public void setVehicleWeight(String vehicleWeight) {
        VehicleWeight = vehicleWeight;
    }

    public String getVehicleType() {
        return VehicleType;
    }

    public void setVehicleType(String vehicleType) {
        VehicleType = vehicleType;
    }

    public String getVehicle() {
        return Vehicle;
    }

    public void setVehicle(String vehicle) {
        Vehicle = vehicle;
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
