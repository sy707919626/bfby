package com.lulian.driver.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.lulian.driver.R;
import com.lulian.driver.base.BaseActivity;
import com.lulian.driver.entity.api.TokenApi;
import com.lulian.driver.utils.ProjectUtil;
import com.rxretrofitlibrary.retrofit_rx.exception.ApiException;

/**
 * Created by MARK on 2018/6/29.
 */

public class WelcomeActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_wec);

        delayRequestToken();
    }


    @Override
    public void onSuccess(String data) {
        switch (neType) {//获取token 成功
            case 0:
                //保存token数据
                app.setToken(data);
                app.setAuthorization("Bearer " + data);

                jumpToLogin();
                break;
        }

    }


    @Override
    public void onError(ApiException e) {
        switch (neType) {
            case 0://获取token失败
                ProjectUtil.show(this,"请求token失败!");
                delayRequestToken();
                break;
        }
    }

    private void jumpToLogin() {
        startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
        WelcomeActivity.this.finish();
    }


    private void requestToken() {
        neType = 0;
        TokenApi tokenApi = new TokenApi();
        pClass.startHttpRequest(this, tokenApi);
    }


    /**
     * 延迟三秒调用请求token接口
     */
    private void delayRequestToken() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                requestToken();
            }
        }, 2000);
    }

}
