package com.lulian.driver.view.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.lulian.driver.R;
import com.lulian.driver.base.BaseActivity;
import com.lulian.driver.base.BaseFragment;
import com.lulian.driver.listener.TransmitStrListener;
import com.lulian.driver.utils.CheckPermissionsUtil;
import com.lulian.driver.utils.ProjectUtil;
import com.lulian.driver.utils.feature.MesureStorer;
import com.lulian.driver.view.fragment.DriverLeaderFragment;
import com.lulian.driver.view.fragment.MeFragment;
import com.lulian.driver.view.fragment.OrderFragment;
import com.lulian.driver.view.fragment.WaybillFragment;
import com.rxretrofitlibrary.retrofit_rx.exception.ApiException;
import com.rxretrofitlibrary.retrofit_rx.utils.GlobalValue;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;


/**
 * Created by MARK on 2018/6/9.
 * 主界面
 */
public class DriverMainActivity extends BaseActivity implements TransmitStrListener {


    @BindView(R.id.carcaptain_layout_content)
    LinearLayout carcaptainLayoutContent;

    @BindView(R.id.bottomMenu)
    ViewGroup vgBottomMenu;

    private Fragment currentFragment = new Fragment();

    @SuppressLint("HandlerLeak")
    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            ProjectUtil.show(DriverMainActivity.this, (String) msg.obj);
        }
    };

    private FragmentManager manager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_main);
        ButterKnife.bind(this);
        app.setiShow(true);
        initView();
//        initData();
//        initMapData();
        //进入界面,初始选择订单tab
        changeTab(R.id.vg_order_tab_click_stub);
    }

    private void initData() {
        JPushInterface.setAlias(this, app.getUserPhone(), new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {
//                ProjectUtil.show(DriverMainActivity.this, "极光推送设置别名回调结果:i=" + i + "       s=" + s);
            }
        });
        Set<String> sets = new HashSet<>();
        sets.add("sport");
        JPushInterface.setTags(this, sets, new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {
                if (i == 0) {
//                    ProjectUtil.show(DriverMainActivity.this, "极光推送设置TAG成功" + i);
                }
            }
        });
        JPushInterface.resumePush(app);
    }

    @Override
    protected void onResume() {
        super.onResume();
        CheckPermissionsUtil cpu = new CheckPermissionsUtil(this);
        cpu.resumeCheck();
    }

    private void initView() {
        manager = getSupportFragmentManager();

        initFragment();
        initTabViews();
        initTabImgRes();

        obtainBottomMenuHeight();
    }


    private void obtainBottomMenuHeight(){
        vgBottomMenu.post(new Runnable() {
            @Override
            public void run() {
                int height = vgBottomMenu.getHeight();
                MesureStorer.setMainBottomTabHeight(height);
            }
        });
    }


    //fragment
    private SparseArray<BaseFragment> fragments = new SparseArray<>();

    private void initFragment(){
        fragments.put(R.id.vg_order_tab_click_stub, new OrderFragment());
        fragments.put(R.id.vg_waybill_tab_click_stub, new WaybillFragment());
        fragments.put(R.id.vg_captain_tab_click_stub, new DriverLeaderFragment());
        fragments.put(R.id.vg_my_tab_click_stub, new MeFragment());
    }


    //底部tab中的iv
    private SparseArray<ImageView> tabIvArray = new SparseArray<>();
    //底部tab中的tv
    private SparseArray<TextView> tabTvArray = new SparseArray<>();

    //未选择时候的tab图标
    private SparseArray<Integer> normalTabImgResIds = new SparseArray<>();
    //选中时的tab图标
    private SparseArray<Integer> selectedTabImgResIds = new SparseArray<>();
    //未选择时tab的字体颜色
    private int normalTabTextColorResId=R.color.gray;
    //选择时tab的字体颜色
    private int selectedTabTextColorResId=R.color.bacolor;


    private void initTabViews(){
        tabIvArray.put(R.id.vg_order_tab_click_stub, (ImageView) findViewById(R.id.iv_order_tab));
        tabIvArray.put(R.id.vg_waybill_tab_click_stub, (ImageView) findViewById(R.id.iv_waybill_tab));
        tabIvArray.put(R.id.vg_captain_tab_click_stub, (ImageView) findViewById(R.id.iv_captain_tab));
        tabIvArray.put(R.id.vg_my_tab_click_stub, (ImageView) findViewById(R.id.iv_my_tab));

        tabTvArray.put(R.id.vg_order_tab_click_stub, (TextView) findViewById(R.id.tv_order_tab));
        tabTvArray.put(R.id.vg_waybill_tab_click_stub, (TextView) findViewById(R.id.tv_waybill_tab));
        tabTvArray.put(R.id.vg_captain_tab_click_stub, (TextView) findViewById(R.id.tv_captain_tab));
        tabTvArray.put(R.id.vg_my_tab_click_stub, (TextView) findViewById(R.id.tv_my_tab));

    }


    private void initTabImgRes(){
        normalTabImgResIds.put(R.id.vg_order_tab_click_stub,R.drawable.listnormal2x);
        normalTabImgResIds.put(R.id.vg_waybill_tab_click_stub,R.drawable.alaremnormal2x);
        normalTabImgResIds.put(R.id.vg_captain_tab_click_stub, R.drawable.medianormal2x);
        normalTabImgResIds.put(R.id.vg_my_tab_click_stub, R.drawable.nav_icon_chat_nor2x);

        selectedTabImgResIds.put(R.id.vg_order_tab_click_stub,R.drawable.listselected2x);
        selectedTabImgResIds.put(R.id.vg_waybill_tab_click_stub,R.drawable.alaremselected2x);
        selectedTabImgResIds.put(R.id.vg_captain_tab_click_stub, R.drawable.mediaselected2x);
        selectedTabImgResIds.put(R.id.vg_my_tab_click_stub, R.drawable.nav_icon_chat_pre2x);

    }


    @OnClick({R.id.vg_order_tab_click_stub,
            R.id.vg_waybill_tab_click_stub,
            R.id.vg_captain_tab_click_stub,
            R.id.vg_my_tab_click_stub})
    public void onBottomTabClicked(View v){
        //判断我的资料的是否已经完善,,如果没完善不能操作某些tab
        if(!app.isComplete()){
            if (v.getId() == R.id.vg_waybill_tab_click_stub || v.getId() == R.id.vg_captain_tab_click_stub) {
                //此处应该弹出提示框
                ProjectUtil.showGoToComplateDatumTip(this);
                return;
            }
        }

        changeTab(v.getId());
    }


    /**
     * 切换底部tab
     */
    private void changeTab(int stubId){
        resetAllTabToNormal();

        ImageView iv = tabIvArray.get(stubId);
        TextView tv = tabTvArray.get(stubId);

        //改变选择的tab的图标
        int selectedImgResId = selectedTabImgResIds.get(stubId);
        iv.setImageResource(selectedImgResId);
        //改变选择的tab的文字颜色
        tv.setTextColor(getResources().getColor(selectedTabTextColorResId));
        //显示对应的fragment
        showFragment(fragments.get(stubId));
    }


    /**
     * 将所有tab重置到未选择状态
     */
    private void resetAllTabToNormal(){
        for(int i=0;i<tabIvArray.size();i++){
            int stubId = tabIvArray.keyAt(i);
            ImageView iv = tabIvArray.valueAt(i);
            Integer normalImgResId = normalTabImgResIds.get(stubId);
            iv.setImageResource(normalImgResId);
        }

        for(int i=0;i<tabTvArray.size();i++){
            TextView tv = tabTvArray.valueAt(i);
            tv.setTextColor(getResources().getColor(normalTabTextColorResId));
        }

    }


    /**
     * 展示Fragment
     *
     */
    public void showFragment(Fragment fragment) {

        if (currentFragment != fragment) {
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.hide(currentFragment);
            currentFragment = fragment;
            if (!fragment.isAdded()) {
                transaction.add(R.id.carcaptain_layout_content, fragment).show(fragment).commit();
            } else {
                transaction.show(fragment).commit();
            }
        }
    }


    @Override
    public void showProg() {
        super.showProg();
    }

    @Override
    public void dismissProg() {
        super.dismissProg();
    }

    @Override
    public void onSuccess(String data) {
        super.onSuccess(data);
    }

    @Override
    public void onError(ApiException e) {
        super.onError(e);
    }

    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    /*-----------高德地图定位数据------------*/
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if (aMapLocation != null) {
                if (aMapLocation.getErrorCode() == 0) {
                    //可在其中解析amapLocation获取相应内容。
                    double latitude = aMapLocation.getLatitude();//获取纬度
                    double longitude = aMapLocation.getLongitude();//获取经度
                    String address = aMapLocation.getAddress();//地址
                    float speed = aMapLocation.getSpeed();//速度
//                    float bearing = aMapLocation.getBearing();//方向角

                    //获取定位时间
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = new Date(aMapLocation.getTime());
                    String time = df.format(date);
                    Message msg = new Message();
                    msg.what = 1;
                    msg.obj = "经度:"
                            + latitude + ",纬度:" + longitude + ",地址:" + address + ",时间:" + time;
                    mHandler.sendMessage(msg);
//                    L.e(TAG, "经度:" + latitude + ",纬度:" + longitude + ",地址:" + address + ",时间:" + time);

                    //定位回传


                } else {
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
//                    L.e("AmapError", "location Error, ErrCode:"
//                            + aMapLocation.getErrorCode() + ", errInfo:"
//                            + aMapLocation.getErrorInfo());
                }
            }
        }
    };

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;

    private void initMapData() {
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);

        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位模式为AMapLocationMode.Battery_Saving，低功耗模式。
//        mLocationOption.setLocationMode(AMapLocationMode.Battery_Saving);
        //设置定位模式为AMapLocationMode.Device_Sensors，仅设备模式。
//        mLocationOption.setLocationMode(AMapLocationMode.Device_Sensors);
        //设置定位间隔,单位毫秒,默认为2000ms，最低1000ms。
        mLocationOption.setInterval(GlobalValue.LOCATIONINTERVAL);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //单位是毫秒，默认30000毫秒，建议超时时间不要低于8000毫秒。
        mLocationOption.setHttpTimeOut(10000);
        //关闭缓存机制
        mLocationOption.setLocationCacheEnable(false);

        mLocationClient.setLocationOption(mLocationOption);
        mLocationClient.startLocation();

        //将引用存入application中
//        app.setmLocationClient(mLocationClient);
//        mLocationClient.stopLocation();//停止定位后，本地定位服务并不会被销毁
//        mLocationClient.onDestroy();//销毁定位客户端，同时销毁本地定位服务。
//        if (!TextUtils.isEmpty(app.getBindVehicleId())) {
//        app.startLocation();
//        }
    }


    @Override
    public void transmitSuccess(Object str) {

    }



}
