package com.lulian.driver.entity.api;

import com.lulian.driver.HttpService;
import com.lulian.driver.entity.server.req.ReqHallOrderListBean;
import com.rxretrofitlibrary.retrofit_rx.Api.BaseApi;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * 获取配货大厅订单列表
 * @author hxb
 */
public class GetHallOrderListApi extends BaseApi {

    private ReqHallOrderListBean bean;

    public void setBean(ReqHallOrderListBean bean) {
        this.bean = bean;
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        HttpService httpService = retrofit.create(HttpService.class);
        return httpService.getOrderListHall(header,userHeader,bean);
    }

    @Override
    protected boolean isNeedData() {
        return true;
    }
}
