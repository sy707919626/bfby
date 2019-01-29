package com.lulian.driver.entity.api;

import com.lulian.driver.HttpService;
import com.rxretrofitlibrary.retrofit_rx.Api.BaseApi;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by MARK on 2018/6/22.
 */

public class RefreshGpsApi extends BaseApi {
    private String driverUserId;
    private String gps;

    public String getDriverUserId() {
        return driverUserId;
    }

    public void setDriverUserId(String driverUserId) {
        this.driverUserId = driverUserId;
    }

    public String getGps() {
        return gps;
    }

    public void setGps(String gps) {
        this.gps = gps;
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        HttpService httpService = retrofit.create(HttpService.class);
        return httpService.refreshDriverGPS(getHeader(),getUserHeader(),getDriverUserId(),getGps());
    }

    @Override
    protected boolean isNeedData() {
        return false;
    }

}
