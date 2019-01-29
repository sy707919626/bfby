package com.lulian.driver.entity.api;

import com.lulian.driver.HttpService;
import com.rxretrofitlibrary.retrofit_rx.Api.BaseApi;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * 获取运单详情接口
 * @author hxb
 */
public class GetWayBillDetailApi extends BaseApi {

    private String id;

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        HttpService httpService = retrofit.create(HttpService.class);
        return httpService.getWayBillDetail(getHeader(),getUserHeader(),id);
    }

    @Override
    protected boolean isNeedData() {
        return true;
    }
}
