package com.lulian.driver.view;

import com.rxretrofitlibrary.retrofit_rx.exception.ApiException;

/**
 *
 */

public interface VInterface {
    void onSuccess(String data);

    void onError(ApiException e);

    void connectNetwork();

    void showProg();

    void dismissProg();
}
