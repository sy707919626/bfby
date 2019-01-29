package com.lulian.driver.entity.api;

import com.lulian.driver.HttpService;
import com.rxretrofitlibrary.retrofit_rx.Api.BaseApi;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by MARK on 2018/6/26.
 */

public class GetReceiveTaskApi extends BaseApi {
    private String orderId;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        HttpService httpService = retrofit.create(HttpService.class);
        return httpService.getReceiveTask(getHeader(),getUserHeader(),getOrderId());
    }

    @Override
    public String call(String httpResult) {
        return super.call(httpResult);
    }


    @Override
    protected boolean isNeedData() {
        return true;
    }
}
