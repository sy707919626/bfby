package com.lulian.driver.entity.api;

import com.lulian.driver.HttpService;
import com.rxretrofitlibrary.retrofit_rx.Api.BaseApi;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * 收藏车队长接口
 */
public class AddDriverLeaderApi extends BaseApi {

    private String driverLeaderId;

    public void setDriverLeaderId(String driverLeaderId) {
        this.driverLeaderId = driverLeaderId;
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        HttpService httpService = retrofit.create(HttpService.class);
        return httpService.addDriverLeader(header,userHeader,driverLeaderId);
    }

    @Override
    protected boolean isNeedData() {
        return false;
    }
}
