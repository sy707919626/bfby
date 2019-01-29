package com.lulian.driver.entity.api;

import com.lulian.driver.HttpService;
import com.rxretrofitlibrary.retrofit_rx.Api.BaseApi;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by MARK on 2018/6/22.
 */

public class AddBlackListApi extends BaseApi {
    private String driverUserId;
    private String reason;

    public String getDriverUserId() {
        return driverUserId;
    }

    public void setDriverUserId(String driverUserId) {
        this.driverUserId = driverUserId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        HttpService httpService = retrofit.create(HttpService.class);
        return httpService.addBlacklist(getHeader(),getUserHeader(),getDriverUserId(),getReason());
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
