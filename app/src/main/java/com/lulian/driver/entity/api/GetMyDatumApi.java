package com.lulian.driver.entity.api;

import com.lulian.driver.HttpService;
import com.rxretrofitlibrary.retrofit_rx.Api.BaseApi;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by Administrator on 2018/8/15.
 */

public class GetMyDatumApi extends BaseApi {
    @Override
    public Observable getObservable(Retrofit retrofit) {

        HttpService httpService = retrofit.create(HttpService.class);
        return httpService.getMyDatum(getHeader(),getUserHeader());
    }


    @Override
    protected boolean isNeedData() {
        return true;
    }

}
