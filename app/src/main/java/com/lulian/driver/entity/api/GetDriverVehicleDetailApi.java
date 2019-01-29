package com.lulian.driver.entity.api;

import com.lulian.driver.HttpService;
import com.rxretrofitlibrary.retrofit_rx.Api.BaseApi;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by MARK on 2018/6/21.
 */

public class GetDriverVehicleDetailApi extends BaseApi {
    private String driverUserId;
    private String type;

    public String getDriverUserId() {
        return driverUserId;
    }

    public void setDriverUserId(String driverUserId) {
        this.driverUserId = driverUserId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {

        HttpService httpService = retrofit.create(HttpService.class);

        return httpService.getDriverVehicleDetail(getHeader(),getUserHeader(),getDriverUserId(),getType());
    }

    @Override
    protected boolean isNeedData() {
        return true;
    }
}
