package com.lulian.driver.entity.api;

import com.lulian.driver.HttpService;
import com.rxretrofitlibrary.retrofit_rx.Api.BaseApi;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by Administrator on 2018/8/17.
 */

public class GetVehiclesInfoApi extends BaseApi {
    private String plateno;
    private String driverid;

    public String getPlateno() {
        return plateno;
    }

    public void setPlateno(String plateno) {
        this.plateno = plateno;
    }

    public String getDriverid() {
        return driverid;
    }

    public void setDriverid(String driverid) {
        this.driverid = driverid;
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {

        HttpService httpService = retrofit.create(HttpService.class);
        return httpService.getVehiclesInfo(getHeader(),getUserHeader(), getDriverid(), getPlateno());
    }


    @Override
    protected boolean isNeedData() {
        return true;
    }
}
