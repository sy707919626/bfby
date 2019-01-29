package com.lulian.driver.model;

import com.rxretrofitlibrary.retrofit_rx.Api.BaseApi;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

/**
 * Created by MARK on 2017/6/2.
 */

public interface MInterface {
    void startHttpRequest(RxAppCompatActivity appCompatActivity, BaseApi baseApi);
}
