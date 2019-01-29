package com.lulian.driver.entity.api;

import com.lulian.driver.HttpService;
import com.rxretrofitlibrary.retrofit_rx.Api.BaseApi;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by MARK on 2018/6/26.
 */

public class GetAreaListApi extends BaseApi {
    private String level;
    private String pId;

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        HttpService httpService = retrofit.create(HttpService.class);
        return httpService.getAreaList(getHeader(),getUserHeader(),getLevel(),getpId());
    }

    @Override
    protected boolean isNeedData() {
        return true;
    }
}
