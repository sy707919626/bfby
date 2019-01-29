package com.lulian.driver.entity.api;

import com.lulian.driver.HttpService;
import com.rxretrofitlibrary.retrofit_rx.Api.BaseApi;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by MARK on 2018/6/14.
 */

public class DriverInfoPhoneApi extends BaseApi {
    //电话
    private String phoneNo;

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {

        HttpService httpService = retrofit.create(HttpService.class);

        return httpService.getDriverLeaderByPhone(getHeader(),getUserHeader(),getPhoneNo());
    }

    @Override
    protected boolean isNeedData() {
        return true;
    }
}
