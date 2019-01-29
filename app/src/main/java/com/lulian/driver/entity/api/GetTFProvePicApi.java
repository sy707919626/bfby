package com.lulian.driver.entity.api;

import com.lulian.driver.HttpService;
import com.rxretrofitlibrary.retrofit_rx.Api.BaseApi;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by MARK on 2018/6/26.
 */

public class GetTFProvePicApi extends BaseApi {
    private String tfid;
    private int statusvalue;

    public String getTfid() {
        return tfid;
    }

    public void setTfid(String tfid) {
        this.tfid = tfid;
    }

    public int getStatusvalue() {
        return statusvalue;
    }

    public void setStatusvalue(int statusvalue) {
        this.statusvalue = statusvalue;
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        HttpService httpService = retrofit.create(HttpService.class);
        return httpService.getTFProvePic(getHeader(),getUserHeader(),getTfid(),getStatusvalue());
    }


    @Override
    protected boolean isNeedData() {
        return true;
    }

}
