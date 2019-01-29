package com.lulian.driver.entity.api;

import com.lulian.driver.HttpService;
import com.rxretrofitlibrary.retrofit_rx.Api.BaseApi;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by MARK on 2018/6/26.
 */

public class GetTFProblemListApi extends BaseApi {
    private String tfid;
    private String rtype;

    public String getTfid() {
        return tfid;
    }

    public void setTfid(String tfid) {
        this.tfid = tfid;
    }

    public String getRtype() {
        return rtype;
    }

    public void setRtype(String rtype) {
        this.rtype = rtype;
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        HttpService httpService = retrofit.create(HttpService.class);
        return httpService.getTFProblemList(getHeader(),getUserHeader(),getTfid(),getRtype());
    }


    @Override
    protected boolean isNeedData() {
        return true;
    }
}
