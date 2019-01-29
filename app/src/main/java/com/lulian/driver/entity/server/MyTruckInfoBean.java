package com.lulian.driver.entity.server;

import java.io.Serializable;

/**
 * 我的车辆信息对象
 * @author hxb
 */
public class MyTruckInfoBean implements Serializable {

    private String Id;
    private String PlateNo;
    private String VehicleType;
    private String VehicleTypeName;
    private double VehicleLength;
    private double VehicleVolume;
    private double VehicleWeight;
    private String DrivingModel;

    private String LicUrl;//行驶证正页
    private String InsuranceUrl;//保险卡正面
    private String VPicUrl;//车辆全身照
    private String BizLicUrl;//运行证
    private String TaxUrl;//购置税发票

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getVehicleTypeName() {
        return VehicleTypeName;
    }

    public void setVehicleTypeName(String vehicleTypeName) {
        VehicleTypeName = vehicleTypeName;
    }

    public String getPlateNo() {
        return PlateNo;
    }

    public void setPlateNo(String plateNo) {
        PlateNo = plateNo;
    }

    public String getVehicleType() {
        return VehicleType;
    }

    public void setVehicleType(String vehicleType) {
        VehicleType = vehicleType;
    }

    public double getVehicleLength() {
        return VehicleLength;
    }

    public void setVehicleLength(double vehicleLength) {
        VehicleLength = vehicleLength;
    }

    public double getVehicleVolume() {
        return VehicleVolume;
    }

    public void setVehicleVolume(double vehicleVolume) {
        VehicleVolume = vehicleVolume;
    }

    public double getVehicleWeight() {
        return VehicleWeight;
    }

    public void setVehicleWeight(double vehicleWeight) {
        VehicleWeight = vehicleWeight;
    }

    public String getDrivingModel() {
        return DrivingModel;
    }

    public void setDrivingModel(String drivingModel) {
        DrivingModel = drivingModel;
    }

    public String getLicUrl() {
        return LicUrl;
    }

    public void setLicUrl(String licUrl) {
        LicUrl = licUrl;
    }

    public String getInsuranceUrl() {
        return InsuranceUrl;
    }

    public void setInsuranceUrl(String insuranceUrl) {
        InsuranceUrl = insuranceUrl;
    }

    public String getVPicUrl() {
        return VPicUrl;
    }

    public void setVPicUrl(String VPicUrl) {
        this.VPicUrl = VPicUrl;
    }

    public String getBizLicUrl() {
        return BizLicUrl;
    }

    public void setBizLicUrl(String bizLicUrl) {
        BizLicUrl = bizLicUrl;
    }

    public String getTaxUrl() {
        return TaxUrl;
    }

    public void setTaxUrl(String taxUrl) {
        TaxUrl = taxUrl;
    }
}
