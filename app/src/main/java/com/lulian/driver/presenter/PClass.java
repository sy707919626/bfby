package com.lulian.driver.presenter;

import com.lulian.driver.model.MClass;
import com.lulian.driver.utils.ProjectUtil;
import com.lulian.driver.view.VInterface;
import com.rxretrofitlibrary.retrofit_rx.Api.BaseApi;
import com.rxretrofitlibrary.retrofit_rx.exception.ApiException;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

/**
 * Created by MARK on 2017/6/2.
 */

public class PClass implements PVInterface, PMInterface{
    VInterface vInterface;
    MClass mClass;

    public PClass(VInterface vInterface) {
        this.vInterface = vInterface;
        mClass = new MClass(this);
    }

    @Override
    public void startHttpRequest(RxAppCompatActivity appCompatActivity, BaseApi baseApi) {
        if (!ProjectUtil.isNetworkConnected(appCompatActivity)) {
            vInterface.connectNetwork();
        } else {
            if (baseApi.isShowProgress()) {
                vInterface.showProg();
            }
            mClass.startHttpRequest(appCompatActivity, baseApi);
        }
    }

    public void startHttpRequest(RxAppCompatActivity appCompatActivity, BaseApi baseApi, String url) {
        if (!ProjectUtil.isNetworkConnected(appCompatActivity)) {
            vInterface.connectNetwork();
        } else {
            if (baseApi.isShowProgress()) {
                vInterface.showProg();
            }
            mClass.startHttpRequest(appCompatActivity, baseApi, url);
        }
    }

    @Override
    public void onSuccess(String data) {
        vInterface.dismissProg();
        vInterface.onSuccess(data);
    }

    @Override
    public void onError(ApiException e) {
        vInterface.dismissProg();
        vInterface.onError(e);
    }
}
