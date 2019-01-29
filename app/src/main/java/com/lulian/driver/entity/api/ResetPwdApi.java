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

public class ResetPwdApi extends BaseApi {
    private String mobile;
    private String pwd;
    private int userType;
    private String newPwd;

    public String getNewPwd() {
        return newPwd;
    }

    public void setNewPwd(String newPwd) {
        this.newPwd = newPwd;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
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
        root.put("NewPassword",getNewPwd());
        RequestBody body=RequestBody.create(okhttp3.MediaType.parse("text/json; charset=utf-8"),
                root.toString());

        JSONObject user = new JSONObject();
        user.put("userid", getUserId());
        user.put("name", getUserName());
        user.put("roleid", getRoleId());
//        RequestBody userHead=RequestBody.create(okhttp3.MediaType.parse("text/json; charset=utf-8"),
//                user.toJSONString());
        return httpService.resetPwd(getHeader(),user.toString(),body);
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
