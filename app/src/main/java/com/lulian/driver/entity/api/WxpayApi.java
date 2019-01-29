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
 * Created by MARK on 2018/6/14.
 */

public class WxpayApi extends BaseApi {

    /**
     * AppId : wx41020b9adb44ad7d
     * PackageName :
     * UserId : 37c1ed16-61e1-46ba-bb3f-d8ae2b0e504f
     * AccountId :
     * AccountType : 0
     * FeeMoney : 1
     * Ip : string
     */
    private String AppId;
    private String PackageName;
    private String UserId;
    private String AccountId;
    private int AccountType;
    private int FeeMoney;
    private String Ip;

    public String getAppId() {
        return AppId;
    }

    public void setAppId(String AppId) {
        this.AppId = AppId;
    }

    public String getPackageName() {
        return PackageName;
    }

    public void setPackageName(String PackageName) {
        this.PackageName = PackageName;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String UserId) {
        this.UserId = UserId;
    }

    public String getAccountId() {
        return AccountId;
    }

    public void setAccountId(String AccountId) {
        this.AccountId = AccountId;
    }

    public int getAccountType() {
        return AccountType;
    }

    public void setAccountType(int AccountType) {
        this.AccountType = AccountType;
    }

    public int getFeeMoney() {
        return FeeMoney;
    }

    public void setFeeMoney(int FeeMoney) {
        this.FeeMoney = FeeMoney;
    }

    public String getIp() {
        return Ip;
    }

    public void setIp(String Ip) {
        this.Ip = Ip;
    }


    public Observable getObservable(Retrofit retrofit) {
        HttpService httpService = retrofit.create(HttpService.class);
        JSONObject root = new JSONObject();

        try {
            root.put("AppId",getAppId());
            root.put("PackageName",getPackageName());
            root.put("UserId",getUserId());
            root.put("AccountId",getAccountId());
            root.put("AccountType",getAccountType());
            root.put("FeeMoney",getFeeMoney());
            root.put("Ip",getIp());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body=RequestBody.create(MediaType.parse("text/json; charset=utf-8"),
                root.toString());

        return httpService.postPreMoney(getHeader(),getUserHeader(),body);
    }

    @Override
    protected boolean isNeedData() {
        return true;
    }

}
