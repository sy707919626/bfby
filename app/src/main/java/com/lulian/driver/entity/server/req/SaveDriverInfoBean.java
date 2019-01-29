package com.lulian.driver.entity.server.req;

import java.io.Serializable;

/**
 * 保存司机信息接口请求参数bean
 * @author hxb
 */
public class SaveDriverInfoBean implements Serializable {

    private String UserId;
    private String InviteCode;//邀请码
    private String Name;//姓名
    private String Phone;//手机号
    private String IdCard;//身份证号
    private String PlateNo;//车牌号
    private String HandUdentityURL;//手持身份证url
    private String IdUrl;//身份证正面url
    private String IdUrl2;//身份证反面url

    private String LlicUrl; //驾驶证正页
    private String LlicUrl2;//驾驶证副页

    private String VehicleType;//这里应该传车型id
    private String DrivingModel;//准驾车型
    private double VehicleLength;//车长
    private double VehicleVolume;//
    private double VehicleWeight;//车辆载重

    private String LicUrl;//行驶证正页
    private String InsuranceUrl;//保险卡正页
    private String VPicUrl;//车辆全身照
    private String TaxUrl;//车辆购置税发票
    private String BizLicUrl;//运营证


    public String getDrivingModel() {
        return DrivingModel;
    }

    public void setDrivingModel(String drivingModel) {
        DrivingModel = drivingModel;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public void setInviteCode(String inviteCode) {
        InviteCode = inviteCode;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public void setIdCard(String idCard) {
        IdCard = idCard;
    }

    public void setPlateNo(String plateNo) {
        PlateNo = plateNo;
    }

    public void setHandUdentityURL(String handUdentityURL) {
        HandUdentityURL = handUdentityURL;
    }

    public void setIdUrl(String idUrl) {
        IdUrl = idUrl;
    }

    public void setIdUrl2(String idUrl2) {
        IdUrl2 = idUrl2;
    }

    public void setLlicUrl(String llicUrl) {
        LlicUrl = llicUrl;
    }

    public void setLlicUrl2(String llicUrl2) {
        LlicUrl2 = llicUrl2;
    }

    public void setVehicleType(String vehicleType) {
        VehicleType = vehicleType;
    }

    public void setVehicleLength(double vehicleLength) {
        VehicleLength = vehicleLength;
    }

    public void setVehicleVolume(double vehicleVolume) {
        VehicleVolume = vehicleVolume;
    }

    public void setVehicleWeight(double vehicleWeight) {
        VehicleWeight = vehicleWeight;
    }

    public void setLicUrl(String licUrl) {
        LicUrl = licUrl;
    }

    public void setInsuranceUrl(String insuranceUrl) {
        InsuranceUrl = insuranceUrl;
    }

    public void setVPicUrl(String VPicUrl) {
        this.VPicUrl = VPicUrl;
    }

    public void setTaxUrl(String taxUrl) {
        TaxUrl = taxUrl;
    }

    public void setBizLicUrl(String bizLicUrl) {
        BizLicUrl = bizLicUrl;
    }

    public String getUserId() {
        return UserId;
    }

    public String getInviteCode() {
        return InviteCode;
    }

    public String getName() {
        return Name;
    }

    public String getPhone() {
        return Phone;
    }

    public String getIdCard() {
        return IdCard;
    }

    public String getPlateNo() {
        return PlateNo;
    }

    public String getHandUdentityURL() {
        return HandUdentityURL;
    }

    public String getIdUrl() {
        return IdUrl;
    }

    public String getIdUrl2() {
        return IdUrl2;
    }

    public String getLlicUrl() {
        return LlicUrl;
    }

    public String getLlicUrl2() {
        return LlicUrl2;
    }

    public String getVehicleType() {
        return VehicleType;
    }

    public double getVehicleLength() {
        return VehicleLength;
    }

    public double getVehicleVolume() {
        return VehicleVolume;
    }

    public double getVehicleWeight() {
        return VehicleWeight;
    }

    public String getLicUrl() {
        return LicUrl;
    }

    public String getInsuranceUrl() {
        return InsuranceUrl;
    }

    public String getVPicUrl() {
        return VPicUrl;
    }

    public String getTaxUrl() {
        return TaxUrl;
    }

    public String getBizLicUrl() {
        return BizLicUrl;
    }
}
