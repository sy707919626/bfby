package com.lulian.driver.entity.api;

import com.lulian.driver.HttpService;
import com.rxretrofitlibrary.retrofit_rx.Api.BaseApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.RequestBody;
import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by Administrator on 2018/8/16.
 */

public class PostMydatumApi extends BaseApi {
    private String PhoneNo1;
    private String PhoneNo2;
    private String PhoneNo3;
    private String Name;
    private String InviteCode;
    private String IdentityCode;
    private String HandIdentityUrl;
    private String IdentityFrontUrl;
    private String IdentityBackUrl;
    private String DrivingLicenseFroneUrl;

    public String getHandIdentityUrl() {
        return HandIdentityUrl;
    }

    public void setHandIdentityUrl(String handIdentityUrl) {
        HandIdentityUrl = handIdentityUrl;
    }

    public String getIdentityFrontUrl() {
        return IdentityFrontUrl;
    }

    public void setIdentityFrontUrl(String identityFrontUrl) {
        IdentityFrontUrl = identityFrontUrl;
    }

    public String getIdentityBackUrl() {
        return IdentityBackUrl;
    }

    public void setIdentityBackUrl(String identityBackUrl) {
        IdentityBackUrl = identityBackUrl;
    }

    public String getDrivingLicenseFroneUrl() {
        return DrivingLicenseFroneUrl;
    }

    public void setDrivingLicenseFroneUrl(String drivingLicenseFroneUrl) {
        DrivingLicenseFroneUrl = drivingLicenseFroneUrl;
    }
    //    private List<FilesBean> Files;
//
//    public List<FilesBean> getFiles() {
//        return Files;
//    }
//
//    public void setFiles(List<FilesBean> files) {
//        Files = files;
//    }

    public String getPhoneNo1() {
        return PhoneNo1;
    }

    public void setPhoneNo1(String phoneNo1) {
        PhoneNo1 = phoneNo1;
    }

    public String getPhoneNo2() {
        return PhoneNo2;
    }

    public void setPhoneNo2(String phoneNo2) {
        PhoneNo2 = phoneNo2;
    }

    public String getPhoneNo3() {
        return PhoneNo3;
    }

    public void setPhoneNo3(String phoneNo3) {
        PhoneNo3 = phoneNo3;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getInviteCode() {
        return InviteCode;
    }

    public void setInviteCode(String inviteCode) {
        InviteCode = inviteCode;
    }

    public String getIdentityCode() {
        return IdentityCode;
    }

    public void setIdentityCode(String identityCode) {
        IdentityCode = identityCode;
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        HttpService httpService = retrofit.create(HttpService.class);
        JSONObject root = new JSONObject();
        try {
            root.put("PhoneNo1",getPhoneNo1());
            root.put("PhoneNo2",getPhoneNo2());
            root.put("PhoneNo3",getPhoneNo3());
            root.put("Name",getName());
            root.put("InviteCode",getInviteCode());
            root.put("IdentityCode",getIdentityCode());
            root.put("HandIdentityUrl",getHandIdentityUrl());
            root.put("IdentityFrontUrl",getIdentityFrontUrl());
            root.put("IdentityBackUrl",getIdentityBackUrl());
            root.put("DrivingLicenseFroneUrl",getDrivingLicenseFroneUrl());

            JSONArray jsonArray = new JSONArray();
//            for(FilesBean file:getFiles()){
                JSONObject jsArObj=new JSONObject();
                jsArObj.put("FieldFlag", "");
                jsArObj.put("Id","");
                jsArObj.put("FileName","");
                jsArObj.put("Url","");
                jsonArray.put(jsArObj);
//            }

            root.put("Files", jsonArray.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body=RequestBody.create(okhttp3.MediaType.parse("text/json; charset=utf-8"),
                root.toString());

        return httpService.postMydatum(getHeader(),getUserHeader(),body);
    }

    @Override
    public String call(String httpResult) {
        return super.call(httpResult);
    }

    @Override
    protected boolean isNeedData() {
        return false;
    }

}
