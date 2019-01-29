package com.lulian.driver.entity.api;


import com.lulian.driver.HttpService;
import com.lulian.driver.entity.server.req.SaveDriverInfoBean;
import com.rxretrofitlibrary.retrofit_rx.Api.BaseApi;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * 保存司机信息
 * @author hxb
 */
public class SaveDriverInfoApi extends BaseApi {

    private SaveDriverInfoBean saveDriverInfoBean;

    public void setSaveDriverInfoBean(SaveDriverInfoBean saveDriverInfoBean) {
        this.saveDriverInfoBean = saveDriverInfoBean;
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        HttpService httpService = retrofit.create(HttpService.class);
        return httpService.saveDriverInfo(header,userHeader,saveDriverInfoBean);
    }

    @Override
    protected boolean isNeedData() {
        return false;
    }
}
