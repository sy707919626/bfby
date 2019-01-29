package com.lulian.driver.entity.api;

import com.lulian.driver.HttpService;
import com.rxretrofitlibrary.retrofit_rx.Api.BaseApi;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by MARK on 2018/6/14.
 */

public class CheckSmsApi extends BaseApi {
    private String mobile;
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        HttpService httpService = retrofit.create(HttpService.class);

        return httpService.checkMsmCode(getHeader(), getMobile(), getCode());
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
