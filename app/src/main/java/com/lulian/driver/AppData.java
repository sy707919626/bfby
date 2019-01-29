package com.lulian.driver;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDexApplication;

import com.lulian.driver.entity.RegionBean;
import com.lulian.driver.entity.server.resulte.CarType;
import com.lulian.driver.view.DictDataTool;
import com.rxretrofitlibrary.retrofit_rx.RxRetrofitApp;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshInitializer;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by MARK on 2018/6/8.
 */

public class AppData extends MultiDexApplication {
    public static Context app;
    private boolean iShow=false;
    private String userPhone;
    private String userId;
    private String userName;
    private String userType;
    private int userStatus;
    /*
     * 用来标识用户信息是否完善 0:必填信息已完善
     * 如果不为0,则无法在app中作业务
     */
    private String IsComplete;
    private String token;
    private String authorization;
    private int messageNum;
    private List<CarType> cartypeLsit;
    private List<String> carLenghtList;

    private List<RegionBean> regionData;

    public List<RegionBean> getRegionData() {
        return regionData;
    }

    public void setRegionData(List<RegionBean> regionData) {
        this.regionData = regionData;
    }

    public int getMessageNum() {
        return messageNum;
    }

    public void setMessageNum(int messageNum) {
        this.messageNum = messageNum;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        app=getApplicationContext();
        RxRetrofitApp.init(this);
        JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);     		// 初始化 JPush

    }


    static {
        initSmartRefreshLayout();
    }

    /**
     * 全局配置下拉刷新上拉加载控件
     */
    private static void initSmartRefreshLayout(){
        SmartRefreshLayout.setDefaultRefreshInitializer(new DefaultRefreshInitializer() {
            @Override
            public void initialize(@NonNull Context context, @NonNull RefreshLayout refreshLayout) {
                refreshLayout.setEnableLoadMoreWhenContentNotFull(false);//是否在列表不满一页时候开启上拉加载功能
                refreshLayout.setDisableContentWhenRefresh(true);//是否在刷新的时候禁止列表的操作
                refreshLayout.setDisableContentWhenLoading(true);//是否在加载的时候禁止列表的操作
            }
        });

        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @NonNull
            @Override
            public RefreshHeader createRefreshHeader(@NonNull Context context, @NonNull RefreshLayout layout) {
                MaterialHeader refreshHeader = new MaterialHeader(context);
                refreshHeader.setColorSchemeResources(R.color.bacolor);
                return refreshHeader;
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @NonNull
            @Override
            public RefreshFooter createRefreshFooter(@NonNull Context context, @NonNull RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }

    public String getUserHeader(){
        JSONObject user = new JSONObject();
        try {
            user.put("userid", getUserId());
            user.put("name", getUserName());
            user.put("usertype", getUserType());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user.toString();
    }


    public void setIsComplete(String isComplete) {
        IsComplete = isComplete;
    }

    public boolean isComplete() {
        if(IsComplete!=null){
            return IsComplete.equals("0");
        }
        return false;
    }

    public int getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(int userStatus) {
        this.userStatus = userStatus;
    }

    public List<String> getCarLenghtList() {
        return carLenghtList;
    }

    public void setCarLenghtList(List<String> carLenghtList) {
        this.carLenghtList = carLenghtList;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    //车型
    public List<CarType> getTrucktypeList() {
        //特殊处理:把"待定"从车型列表中删除掉
        CarType shouldRemove=null;
        if(cartypeLsit!=null){
            for (CarType ct : cartypeLsit) {
                if(ct.getText().equals(DictDataTool.TRUCK_TYPE_TEXT_UNCONFIRM)){
                    shouldRemove = ct;
                }
            }
            if(shouldRemove!=null){
                cartypeLsit.remove(shouldRemove);
            }
        }
        return cartypeLsit;
    }

    public void setCartypeLsit(List<CarType> cartypeLsit) {
        this.cartypeLsit = cartypeLsit;
    }

    public boolean iShow() {
        return iShow;
    }

    public void setiShow(boolean iShow) {
        this.iShow = iShow;
    }

}
