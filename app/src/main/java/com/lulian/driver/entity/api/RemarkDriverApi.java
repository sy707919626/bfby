package com.lulian.driver.entity.api;

import com.lulian.driver.HttpService;
import com.rxretrofitlibrary.retrofit_rx.Api.BaseApi;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by MARK on 2018/6/22.
 */

public class RemarkDriverApi extends BaseApi {
    private String driverUserId;
    private String remark;

    public String getDriverUserId() {
        return driverUserId;
    }

    public void setDriverUserId(String driverUserId) {
        this.driverUserId = driverUserId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        HttpService httpService = retrofit.create(HttpService.class);
        return httpService.remarkDriver(getHeader(),getUserHeader(),getDriverUserId(),getRemark());
    }

    @Override
    public String call(String httpResult) {
        return httpResult;
    }

    @Override
    protected boolean isNeedData() {
        return false;
    }

}
