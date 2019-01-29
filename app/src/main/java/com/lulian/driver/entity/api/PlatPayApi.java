package com.lulian.driver.entity.api;

import com.lulian.driver.HttpService;
import com.rxretrofitlibrary.retrofit_rx.Api.BaseApi;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.RequestBody;
import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by Administrator on 2018/8/23.
 */

public class PlatPayApi extends BaseApi {

    /**
     * AccountId : string
     * Password : string
     * Money : 0
     * ReceiverAccountId : string
     * Remark : string
     */

    private String AccountId;
    private String Password;
    private int Money;
    private String ReceiverAccountId;
    private String Remark;

    public String getAccountId() {
        return AccountId;
    }

    public void setAccountId(String AccountId) {
        this.AccountId = AccountId;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public int getMoney() {
        return Money;
    }

    public void setMoney(int Money) {
        this.Money = Money;
    }

    public String getReceiverAccountId() {
        return ReceiverAccountId;
    }

    public void setReceiverAccountId(String ReceiverAccountId) {
        this.ReceiverAccountId = ReceiverAccountId;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String Remark) {
        this.Remark = Remark;
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        HttpService httpService = retrofit.create(HttpService.class);
        JSONObject root = new JSONObject();
        try {
            root.put("AccountId", getAccountId());
            root.put("Password", getPassword());
            root.put("Money", getMoney());
            root.put("ReceiverAccountId", getReceiverAccountId());
            root.put("Remark", getRemark());

        }catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body=RequestBody.create(okhttp3.MediaType.parse("text/json; charset=utf-8"),
                root.toString());

        return httpService.PlatPay(getHeader(),getUserHeader(),body);
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
