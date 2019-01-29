package com.lulian.driver.entity.api;

import com.lulian.driver.HttpService;
import com.rxretrofitlibrary.retrofit_rx.Api.BaseApi;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by Administrator on 2018/8/18.
 */

public class ModeifyWorkStatus extends BaseApi {
    private String driverid;

    public String getDriverid() {
        return driverid;
    }

    public void setDriverid(String driverid) {
        this.driverid = driverid;
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        HttpService httpService = retrofit.create(HttpService.class);
        return httpService.ModeifyWorkStatus(getHeader(),getUserHeader(),getDriverid());
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
