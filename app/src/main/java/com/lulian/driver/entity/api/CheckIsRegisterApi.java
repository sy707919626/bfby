package com.lulian.driver.entity.api;

import com.lulian.driver.HttpService;
import com.rxretrofitlibrary.retrofit_rx.Api.BaseApi;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * 检测手机号是否已被注册
 * @author hxb
 */
public class CheckIsRegisterApi extends BaseApi {

    private String phone;

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        HttpService httpService = retrofit.create(HttpService.class);
        return httpService.checkIsRegister(header,userHeader,phone);
    }

    @Override
    protected boolean isNeedData() {
        return false;
    }
}
