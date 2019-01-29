package com.lulian.driver.entity.api;

import com.lulian.driver.HttpService;
import com.rxretrofitlibrary.retrofit_rx.Api.BaseApi;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by MARK on 2018/6/21.
 */

public class AddDriverVehicle extends BaseApi {
    private String driverUserId;
    private String DriverVehicleId;

    public String getDriverUserId() {
        return driverUserId;
    }

    public void setDriverUserId(String driverUserId) {
        this.driverUserId = driverUserId;
    }

    public String getDriverVehicleId() {
        return DriverVehicleId;
    }

    public void setDriverVehicleId(String driverVehicleId) {
        DriverVehicleId = driverVehicleId;
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        HttpService httpService = retrofit.create(HttpService.class);
        return httpService.addDriverVehicle(getHeader(),getUserHeader(),getDriverUserId(),getDriverVehicleId());
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
