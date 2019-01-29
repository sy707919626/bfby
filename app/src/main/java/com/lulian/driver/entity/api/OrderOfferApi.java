package com.lulian.driver.entity.api;

import com.lulian.driver.HttpService;
import com.lulian.driver.entity.server.req.ReqOfferBean;
import com.rxretrofitlibrary.retrofit_rx.Api.BaseApi;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * 订单报价接口
 * @author hxb
 */
public class OrderOfferApi extends BaseApi {

    private ReqOfferBean bean;

    public void setBean(ReqOfferBean bean) {
        this.bean = bean;
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        HttpService httpService = retrofit.create(HttpService.class);
        return httpService.orderOffer(getHeader(),getUserHeader(),bean);
    }

    @Override
    protected boolean isNeedData() {
        return false;
    }
}
