package com.lulian.driver.entity.api;

import com.lulian.driver.HttpService;
import com.rxretrofitlibrary.retrofit_rx.Api.BaseApi;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by MARK on 2018/7/27.
 */

public class GetListByUserIdApi extends BaseApi{
    private String userid;


    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        HttpService httpService = retrofit.create(HttpService.class);
        return httpService.GetListByUserId(getHeader(),getUserHeader(),getUserid());
    }

    @Override
    protected boolean isNeedData() {
        return true;
    }

}
