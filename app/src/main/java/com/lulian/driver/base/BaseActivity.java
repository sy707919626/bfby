package com.lulian.driver.base;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.jaeger.library.StatusBarUtil;
import com.lulian.driver.AppData;
import com.lulian.driver.R;
import com.lulian.driver.presenter.PClass;
import com.lulian.driver.utils.GlobalValue;
import com.lulian.driver.utils.L;
import com.lulian.driver.utils.ProjectUtil;
import com.lulian.driver.view.VInterface;
import com.lulian.driver.view.wheel.EnsureDialog;
import com.rxretrofitlibrary.retrofit_rx.exception.ApiException;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

/**
 * Created by MARK on 2017/6/9.
 */

public class BaseActivity extends RxAppCompatActivity implements VInterface {
    //    加载框可自己定义
    public ProgressDialog pd;
    public AppData app;
    public PClass pClass;
    private EnsureDialog dialog;
    public int neType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        StatusBarUtil.setColor(this,getResources().getColor(R.color.bacolor),0);
        if (pd == null) {
            pd = new ProgressDialog(this);
            pd.setMessage("正在加载中....");
            pd.setCancelable(false);
        }
        app = (AppData) getApplication();
        pClass = new PClass(this);
    }

    //获取图片完整路径
    public String assembleImgUrl(String url){
        String path = GlobalValue.BASEURL + url.replaceFirst("/", "");
        L.e("assembleImgUrl",path);
        return path;
    }

    public void showProg() {
        if (pd != null && !pd.isShowing()) {
            pd.show();
        }
    }

    public void dismissProg() {
        if (pd != null && pd.isShowing()) {
            pd.dismiss();
        }
    }

    @Override
    public void onSuccess(String data){}

    @Override
    public void onError(ApiException e){
        ProjectUtil.show(this,e.getDisplayMessage());
    }

    public void connectNetwork() {
        String mess = "亲！您的网络出问题了，请检查设置！";

//        new NetConnectPopupWindow(this, "亲！您的网络出问题了，请检查设置！", okOnClick)
//                .showAtLocation(, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        if (dialog == null) {
            dialog = new EnsureDialog(this, mess,"网络设置", onNetClickListener);
        }
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
    }

    private View.OnClickListener onNetClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_ok:
                    Intent intent=null;
                    //判断手机系统的版本  即API大于10 就是3.0或以上版本
                    if(android.os.Build.VERSION.SDK_INT>10){
                        intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                    }else{
                        intent = new Intent();
                        ComponentName component = new ComponentName("com.android.settings","com.android.settings.WirelessSettings");
                        intent.setComponent(component);
                        intent.setAction("android.intent.action.VIEW");
                    }
                    startActivity(intent);
                    break;
                case R.id.btn_cancle:
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    break;
            }
        }
    };
}

