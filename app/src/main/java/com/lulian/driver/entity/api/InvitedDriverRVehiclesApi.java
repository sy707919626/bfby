package com.lulian.driver.entity.api;

import com.lulian.driver.HttpService;
import com.rxretrofitlibrary.retrofit_rx.Api.BaseApi;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.RequestBody;
import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by MARK on 2018/6/26.
 */

public class InvitedDriverRVehiclesApi extends BaseApi {

    private String IdUrl;
    private String IdUrl2;
    private String LlicUrl;
    private String PlateNo;
    private String VehicleType;
    private int VehicleLength;
    private String LicUrl;
    private String VPicUrl;
    private String InvitationID;
    private String CreateId;
    private String CreateUser;
    private String CreateTime;
    private String InviteCode;
    private String DriversPhone;
    private String DriversName;
    private String OrderId;
    private String InviteURL;
    private String FileIDList;

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

    public int getVehicleLength() {
        return VehicleLength;
    }

    public void setVehicleLength(int vehicleLength) {
        VehicleLength = vehicleLength;
    }

    public String getLicUrl() {
        return LicUrl;
    }

    public void setLicUrl(String licUrl) {
        LicUrl = licUrl;
    }

    public String getVPicUrl() {
        return VPicUrl;
    }

    public void setVPicUrl(String VPicUrl) {
        this.VPicUrl = VPicUrl;
    }

    public String getInvitationID() {
        return InvitationID;
    }

    public void setInvitationID(String invitationID) {
        InvitationID = invitationID;
    }

    public String getCreateId() {
        return CreateId;
    }

    public void setCreateId(String createId) {
        CreateId = createId;
    }

    public String getCreateUser() {
        return CreateUser;
    }

    public void setCreateUser(String createUser) {
        CreateUser = createUser;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public String getInviteCode() {
        return InviteCode;
    }

    public void setInviteCode(String inviteCode) {
        InviteCode = inviteCode;
    }

    public String getDriversPhone() {
        return DriversPhone;
    }

    public void setDriversPhone(String driversPhone) {
        DriversPhone = driversPhone;
    }

    public String getDriversName() {
        return DriversName;
    }

    public void setDriversName(String driversName) {
        DriversName = driversName;
    }

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }

    public String getInviteURL() {
        return InviteURL;
    }

    public void setInviteURL(String inviteURL) {
        InviteURL = inviteURL;
    }

    public String getFileIDList() {
        return FileIDList;
    }

    public void setFileIDList(String fileIDList) {
        FileIDList = fileIDList;
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        HttpService httpService = retrofit.create(HttpService.class);
        JSONObject root = new JSONObject();
        try {
            root.put("IdUrl", getUrl());
            root.put("IdUrl2", getIdUrl2());
            root.put("LlicUrl", getLlicUrl());
            root.put("PlateNo", getPlateNo());
            root.put("VehicleType", getVehicleType());
            root.put("VehicleLength",getVehicleLength());
            root.put("LicUrl", getLicUrl());
            root.put("VPicUrl",getVPicUrl());
            root.put("InvitationID", getInvitationID());
            root.put("CreateId", getCreateId());
            root.put("CreateUser", getCreateUser());
            root.put("CreateTime", getCreateTime());
            root.put("InviteCode", getInviteCode());
            root.put("DriversPhone", getDriversPhone());
            root.put("DriversName", getDriversName());
            root.put("OrderId", getOrderId());
            root.put("InviteURL", getInviteURL());
            root.put("FileIDList", getFileIDList());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("text/json; charset=utf-8"),
                root.toString());
        return httpService.SubmitCommentResutl(getHeader(), getUserHeader(), body);
    }

    @Override
    public String call(String httpResult) {
        return httpResult;
    }

    @Override
    protected boolean isNeedData() {
        return true;
    }

}
