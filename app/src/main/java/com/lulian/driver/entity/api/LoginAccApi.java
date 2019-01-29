package com.lulian.driver.entity.api;

import com.alibaba.fastjson.JSONObject;
import com.lulian.driver.HttpService;
import com.rxretrofitlibrary.retrofit_rx.Api.BaseApi;

import okhttp3.RequestBody;
import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by MARK on 2018/6/14.
 */

public class LoginAccApi extends BaseApi {
    private String mobile;
    private String pwd;
    private String userType;


    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        HttpService httpService = retrofit.create(HttpService.class);

        JSONObject root = new JSONObject();
        root.put("Phone", getMobile());
        root.put("Password", getPwd());
        root.put("UserType", getUserType());

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("text/json; charset=utf-8"),
                root.toString());
        return httpService.login(getHeader(),body);
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
