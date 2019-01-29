package com.lulian.driver.entity.api;

import com.lulian.driver.HttpService;
import com.rxretrofitlibrary.retrofit_rx.Api.BaseApi;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by Administrator on 2018/8/15.
 */

public class GetMessageDetailApi extends BaseApi {
    private String messageid;

    public String getMessageid() {
        return messageid;
    }

    public void setMessageid(String messageid) {
        this.messageid = messageid;
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {

        HttpService httpService = retrofit.create(HttpService.class);
        return httpService.getMessageDetail(getHeader(),getUserHeader(), getMessageid());
    }


    @Override
    protected boolean isNeedData() {
        return true;
    }

}
