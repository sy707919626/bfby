package com.lulian.driver.entity.api;

import com.lulian.driver.HttpService;
import com.lulian.driver.entity.server.req.ReqWayBillListBean;
import com.rxretrofitlibrary.retrofit_rx.Api.BaseApi;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * 获取运单列表
 * @author hxb
 */
public class GetWayBillListApi extends BaseApi {

    private ReqWayBillListBean bean;

    public void setBean(ReqWayBillListBean bean) {
        this.bean = bean;
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        HttpService httpService = retrofit.create(HttpService.class);
        return httpService.getWayBillList(header,userHeader,bean);
    }

    @Override
    protected boolean isNeedData() {
        return true;
    }
}
