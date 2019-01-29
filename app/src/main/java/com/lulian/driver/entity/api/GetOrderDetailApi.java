package com.lulian.driver.entity.api;

import com.lulian.driver.HttpService;
import com.rxretrofitlibrary.retrofit_rx.Api.BaseApi;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * 获取订单详情接口
 * @author hxb
 */
public class GetOrderDetailApi extends BaseApi {

    private String orderId;

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        HttpService httpService = retrofit.create(HttpService.class);
        return httpService.getOrderDetail(getHeader(),getUserHeader(),orderId);
    }

    @Override
    protected boolean isNeedData() {
        return true;
    }
}
