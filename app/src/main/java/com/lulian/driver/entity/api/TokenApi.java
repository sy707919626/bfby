package com.lulian.driver.entity.api;

import com.lulian.driver.HttpService;
import com.rxretrofitlibrary.retrofit_rx.Api.BaseApi;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by MARK on 2018/6/14.
 */

public class TokenApi extends BaseApi {
    private String userid = "AF5D1BD8-FCA3-4218-8DF2-95E9380BE150";
    private String username = "openzen";
    private String password = "IL561008";

    public TokenApi() {
        setShowProgress(false);
        setCache(false);
        setMothed("token");
        setCookieNoNetWorkTime(24*60*60*3);//3天
        setCookieNetWorkTime(24*60*60*3);//3天
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        HttpService httpService = retrofit.create(HttpService.class);
        Map<String, String> options = new HashMap<>();
        options.put("userid", userid);
        options.put("username", username);
        options.put("password", password);

        return httpService.getToken(options);
    }


    @Override
    protected boolean isNeedData() {
        return true;
    }

}
