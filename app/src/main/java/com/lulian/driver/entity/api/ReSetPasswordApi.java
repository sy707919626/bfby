package com.lulian.driver.entity.api;

import com.lulian.driver.HttpService;
import com.rxretrofitlibrary.retrofit_rx.Api.BaseApi;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by Administrator on 2018/8/23.
 */

public class ReSetPasswordApi extends BaseApi {

    /**
     * AccountId : string
     * UserId : string
     * OldPassword : string
     * NewPassword : string
     */

    private String AccountId;
    private String UserId;
    private String OldPassword;
    private String NewPassword;



    public String getAccountId() {
        return AccountId;
    }

    public void setAccountId(String AccountId) {
        this.AccountId = AccountId;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String UserId) {
        this.UserId = UserId;
    }

    public String getOldPassword() {
        return OldPassword;
    }

    public void setOldPassword(String OldPassword) {
        this.OldPassword = OldPassword;
    }

    public String getNewPassword() {
        return NewPassword;
    }

    public void setNewPassword(String NewPassword) {
        this.NewPassword = NewPassword;
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        HttpService httpService = retrofit.create(HttpService.class);
        JSONObject root = new JSONObject();
        try {
            root.put("AccountId", getAccountId());
            root.put("UserId", getUserId());
            root.put("OldPassword", getOldPassword());
            root.put("NewPassword", getNewPassword());

        }catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body=RequestBody.create(MediaType.parse("text/json; charset=utf-8"),
                root.toString());

        return httpService.ReSetPassword(getHeader(),getUserHeader(),body);
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
