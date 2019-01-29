package com.lulian.driver.presenter;

import com.rxretrofitlibrary.retrofit_rx.exception.ApiException;

/**
 * Created by MARK on 2017/6/2.
 */

public interface PMInterface {
    void onSuccess(String data);

    void onError(ApiException e);
}
