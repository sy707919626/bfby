package com.lulian.driver.entity.api;

import com.lulian.driver.HttpService;
import com.rxretrofitlibrary.retrofit_rx.Api.BaseApi;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by MARK on 2018/6/27.
 */

public class GetNewMessageListApi extends BaseApi {
    @Override
    public Observable getObservable(Retrofit retrofit) {
        HttpService httpService = retrofit.create(HttpService.class);
        return httpService.getNewMessageList(getHeader(), getUserHeader());
    }


    @Override
    protected boolean isNeedData() {
        return true;
    }
}
