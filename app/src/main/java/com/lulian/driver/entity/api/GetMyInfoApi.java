package com.lulian.driver.entity.api;

import com.lulian.driver.HttpService;
import com.rxretrofitlibrary.retrofit_rx.Api.BaseApi;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * 获取我的个人信息和车辆信息
 */
public class GetMyInfoApi extends BaseApi {

    @Override
    public Observable getObservable(Retrofit retrofit) {
        HttpService httpService = retrofit.create(HttpService.class);
        return httpService.getMyInfo(header,userHeader);
    }

    @Override
    protected boolean isNeedData() {
        return true;
    }
}
