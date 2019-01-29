package com.lulian.driver.entity.api;

import com.lulian.driver.HttpService;
import com.rxretrofitlibrary.retrofit_rx.Api.BaseApi;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.RequestBody;
import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by MARK on 2018/6/14.
 */

public class DriverBrokerInfoAccApi extends BaseApi {
    //用户ID
    private String userId;
    //邀请码
    private String inviteCode;
    //司机姓名
    private String name;
    //司机姓电话
    private String phone;
    // 身份证号
    private String idCard;
    //手持身份证照URL
    private String handUdentityURL;
    //身份证正面
    private String idUrl;
    //身份证背面
    private String idUrl2;
    //驾驶证正面
    private String llicUrl;
    //驾驶证背面
    private String llicUrl2;
    private String PlateNo;
    private String vehicleType;
    //车长（cm）
    private int vehicleLength;
    //车高（cm）
    private int vehicleHeight;
    //车宽（cm）
    private int vehicleWidth;
    //车载体积（立方米）
    private int vehicleVolume;
    //载重（t)
    private int vehicleWeight;
    //行驶证正面
    private String licUrl;
    //保险卡正面
    private String insuranceUrl;
    //车辆全身照
    private String vPicUrl;
    //车辆购置税
    private String taxUrl;
    //运营证
    private String bizLicUrl;

    @Override
    public String getUserId() {
        return userId;
    }

    @Override
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getHandUdentityURL() {
        return handUdentityURL;
    }

    public void setHandUdentityURL(String handUdentityURL) {
        this.handUdentityURL = handUdentityURL;
    }

    public String getIdUrl() {
        return idUrl;
    }

    public void setIdUrl(String idUrl) {
        this.idUrl = idUrl;
    }

    public String getIdUrl2() {
        return idUrl2;
    }

    public void setIdUrl2(String idUrl2) {
        this.idUrl2 = idUrl2;
    }

    public String getLlicUrl() {
        return llicUrl;
    }

    public void setLlicUrl(String llicUrl) {
        this.llicUrl = llicUrl;
    }

    public String getLlicUrl2() {
        return llicUrl2;
    }

    public void setLlicUrl2(String llicUrl2) {
        this.llicUrl2 = llicUrl2;
    }

    public String getPlateNo() {
        return PlateNo;
    }

    public void setPlateNo(String plateNo) {
        PlateNo = plateNo;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public int getVehicleLength() {
        return vehicleLength;
    }

    public void setVehicleLength(int vehicleLength) {
        this.vehicleLength = vehicleLength;
    }

    public int getVehicleHeight() {
        return vehicleHeight;
    }

    public void setVehicleHeight(int vehicleHeight) {
        this.vehicleHeight = vehicleHeight;
    }

    public int getVehicleWidth() {
        return vehicleWidth;
    }

    public void setVehicleWidth(int vehicleWidth) {
        this.vehicleWidth = vehicleWidth;
    }

    public int getVehicleVolume() {
        return vehicleVolume;
    }

    public void setVehicleVolume(int vehicleVolume) {
        this.vehicleVolume = vehicleVolume;
    }

    public int getVehicleWeight() {
        return vehicleWeight;
    }

    public void setVehicleWeight(int vehicleWeight) {
        this.vehicleWeight = vehicleWeight;
    }

    public String getLicUrl() {
        return licUrl;
    }

    public void setLicUrl(String licUrl) {
        this.licUrl = licUrl;
    }

    public String getInsuranceUrl() {
        return insuranceUrl;
    }

    public void setInsuranceUrl(String insuranceUrl) {
        this.insuranceUrl = insuranceUrl;
    }

    public String getvPicUrl() {
        return vPicUrl;
    }

    public void setvPicUrl(String vPicUrl) {
        this.vPicUrl = vPicUrl;
    }

    public String getTaxUrl() {
        return taxUrl;
    }

    public void setTaxUrl(String taxUrl) {
        this.taxUrl = taxUrl;
    }

    public String getBizLicUrl() {
        return bizLicUrl;
    }

    public void setBizLicUrl(String bizLicUrl) {
        this.bizLicUrl = bizLicUrl;
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        HttpService httpService = retrofit.create(HttpService.class);

        JSONObject root = new JSONObject();
        try {
            root.put("UserId",getUserId());
            root.put("InviteCode",getInviteCode());
            root.put("Name",getName());
            root.put("Phone",getPhone());
            root.put("IdCard",getIdCard());
            root.put("HandUdentityURL",getHandUdentityURL());
            root.put("IdUrl",getIdUrl());
            root.put("IdUrl2",getIdUrl2());
            root.put("LlicUrl",getLlicUrl());
            root.put("LlicUrl2",getLlicUrl2());
            root.put("PlateNo",getPlateNo());
            root.put("VehicleType",getVehicleType());
            root.put("VehicleLength",getVehicleLength());
            root.put("VehicleHeight",getVehicleHeight());
            root.put("VehicleWidth",getVehicleWidth());
            root.put("VehicleVolume",getVehicleVolume());
            root.put("VehicleWeight",getVehicleWeight());
            root.put("LicUrl",getLicUrl());
            root.put("InsuranceUrl",getInsuranceUrl());
            root.put("VPicUrl",getvPicUrl());
            root.put("TaxUrl",getTaxUrl());
            root.put("BizLicUrl",getBizLicUrl());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body=RequestBody.create(okhttp3.MediaType.parse("text/json; charset=utf-8"),
                root.toString());
        return httpService.saveInfo(getHeader(),body);
    }

    @Override
    public String call(String httpResult) {
        return httpResult;
    }

    @Override
    protected boolean isNeedData() {
        return false;
    }
}
