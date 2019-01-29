package com.lulian.driver.presenter;

import com.rxretrofitlibrary.retrofit_rx.Api.BaseApi;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

/**
 * Created by MARK on 2017/6/2.
 */

public interface PVInterface {

    void startHttpRequest(RxAppCompatActivity appCompatActivity, BaseApi baseApi);
}
