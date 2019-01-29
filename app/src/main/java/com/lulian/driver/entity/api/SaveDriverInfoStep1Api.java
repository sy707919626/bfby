package com.lulian.driver.entity.api;

import com.lulian.driver.HttpService;
import com.lulian.driver.entity.server.MyPersonInfoBean;
import com.rxretrofitlibrary.retrofit_rx.Api.BaseApi;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * 保存我的个人信息
 * @author hxb
 */
public class SaveDriverInfoStep1Api extends BaseApi {

    private MyPersonInfoBean personInfo;

    public void setPersonInfo(MyPersonInfoBean personInfo) {
        this.personInfo = personInfo;
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        HttpService httpService = retrofit.create(HttpService.class);
        return httpService.saveDriverInfoStep1(header,userHeader,personInfo);
    }

    @Override
    protected boolean isNeedData() {
        return true;
    }
}
