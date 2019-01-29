package com.lulian.driver.entity.api;

import com.lulian.driver.HttpService;
import com.lulian.driver.entity.server.MyTruckInfoBean;
import com.rxretrofitlibrary.retrofit_rx.Api.BaseApi;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * 保存我的车辆信息
 * @author hxb
 */
public class SaveDriverInfoStep2Api extends BaseApi {

    private MyTruckInfoBean truckInfoBean;

    public void setTruckInfoBean(MyTruckInfoBean truckInfoBean) {
        this.truckInfoBean = truckInfoBean;
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        HttpService httpService = retrofit.create(HttpService.class);
        return httpService.saveDriverInfoStep2(header,userHeader,truckInfoBean);
    }

    @Override
    protected boolean isNeedData() {
        return true;
    }
}
