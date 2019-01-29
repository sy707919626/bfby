package com.lulian.driver.base;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.lulian.driver.AppData;
import com.lulian.driver.R;
import com.lulian.driver.listener.TransmitStrListener;
import com.lulian.driver.presenter.PClass;
import com.lulian.driver.utils.GlobalValue;
import com.lulian.driver.utils.L;
import com.lulian.driver.utils.ProjectUtil;
import com.lulian.driver.view.VInterface;
import com.lulian.driver.view.wheel.EnsureDialog;
import com.rxretrofitlibrary.retrofit_rx.exception.ApiException;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.trello.rxlifecycle.components.support.RxFragment;

import static com.rxretrofitlibrary.retrofit_rx.RxRetrofitApp.getApplication;

/**
 * Created by MARK on 2017/6/8.
 */

public abstract class BaseFragment extends RxFragment implements VInterface {
    //    加载框可自己定义
    protected ProgressDialog pd;
    protected AppData app;
    protected PClass pClass;
    private EnsureDialog dialog;
    public  int neType;
    public boolean isFresh=false;
    public boolean isClick=false;

    protected RxAppCompatActivity mActivity;
    protected TransmitStrListener transmitStrListener;

    public BaseFragment() {
        // Required empty public constructor
    }

    //获取图片完整路径
    public String assembleImgUrl(String url){
        String path = GlobalValue.BASEURL + url.replaceFirst("/", "");
        L.e("assembleImgUrl",path);
        return path;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (pd == null) {
            pd = new ProgressDialog(getContext());
            pd.setMessage("加载中....");
            pd.setCancelable(false);
        }
        app = (AppData) getApplication();
        pClass = new PClass(this);
        mActivity = (RxAppCompatActivity) getActivity();

    }

    @Override
    public void onAttach(Context context) {
        transmitStrListener=(TransmitStrListener)context;
        super.onAttach(context);
    }


    //    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        TextView textView = new TextView(getActivity());
//        textView.setText(initView());
//        return textView;
//    }

/*    public String initView() {
        return "fragment";
    }*/

//    public void getFragment(int checkedId,int layout){
//        mActivity.getSupportFragmentManager().beginTransaction().replace(layout
//                , FragmentFactory.getFragment(checkedId)).commit();
//    }


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
    public abstract void onSuccess(String data);

    @Override
    public void onError(ApiException e){
        ProjectUtil.show(getActivity(),e.getDisplayMessage());
        e.printStackTrace();
    }


    public void connectNetwork() {
        String mess = "亲！您的网络出问题了，请检查设置！";
//        new NetConnectPopupWindow(this, "亲！您的网络出问题了，请检查设置！", okOnClick)
//                .showAtLocation(, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        if (dialog == null) {
            dialog = new EnsureDialog(getActivity(),mess,"网络设置", onNetClickListener);
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
