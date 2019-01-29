package com.lulian.driver.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.google.gson.reflect.TypeToken;
import com.lulian.driver.R;
import com.lulian.driver.adapter.HallOrderListAdapter;
import com.lulian.driver.adapter.base.BaseRvAdapter;
import com.lulian.driver.base.BaseFragment;
import com.lulian.driver.entity.RegionBean;
import com.lulian.driver.entity.SimpleBean;
import com.lulian.driver.entity.api.GetHallOrderListApi;
import com.lulian.driver.entity.server.req.ReqHallOrderListBean;
import com.lulian.driver.entity.server.resulte.CarType;
import com.lulian.driver.entity.server.resulte.OrderListItem;
import com.lulian.driver.utils.RvConfigHelper;
import com.lulian.driver.utils.feature.FilterBarHelper;
import com.lulian.driver.utils.feature.MesureStorer;
import com.lulian.driver.view.activity.OrderDetailActivity;
import com.lulian.driver.view.wheel.BaseFilterOpSelectPopup;
import com.lulian.driver.view.wheel.EndRegionSelectPopup;
import com.lulian.driver.view.wheel.OrderFilterSelectPopup;
import com.lulian.driver.view.wheel.StartRegionSelectPopup;
import com.rxretrofitlibrary.retrofit_rx.exception.ApiException;
import com.rxretrofitlibrary.retrofit_rx.utils.GsonUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 配货大厅(全部订单)
 * @author hxb
 */

public class AllOrderFragment extends BaseFragment {
    @BindView(R.id.srl)
    SmartRefreshLayout srl;
    @BindView(R.id.rv)
    RecyclerView rv;

    @BindView(R.id.vg_filter_bar)
    ViewGroup vgFilterBar;


    private HallOrderListAdapter adapter;//列表数据适配器

    /**
     * 筛选选择框
     */
    private SparseArray<BaseFilterOpSelectPopup> popupArray = new SparseArray<>();

    private Unbinder unbinder;


    private RegionBean selectedStartRegion;//当前已选择要进行筛选的起始地
    private List<RegionBean> selectedEndRegion;//当前已选择要进行筛选的的目的地
    private List<CarType> selectedTruckType;//当前已选择要进行筛选的的车型
    private List<String> selectedTruckLen;//当前已选择要进行筛选的的车长
    private List<SimpleBean> selectedLoadTimeScope;//当前已选择要进行筛选的装货时间范围


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_orderall, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        initView();

        return rootView;
    }


    @Override
    public void onStart() {
        super.onStart();
        requestGetHallOrderList(true);
    }

    private void initView(){
        obtainPopAnchorHeight();
        initFilterBarHelper();

        RvConfigHelper.configToLLMgrVertical(getActivity(),rv);
        adapter = new HallOrderListAdapter(getActivity());
        adapter.setChildClickListener(listItemChildClickListener);
        rv.setAdapter(adapter);

        srl.setOnRefreshLoadMoreListener(refreshLoadMoreListener);
    }

    /**
     * 列表条目中的view的点击监听
     */
    private BaseRvAdapter.OnChildClickListener listItemChildClickListener = new BaseRvAdapter.OnChildClickListener() {
        @Override
        public void onClick(BaseRvAdapter adapter, View view, int position) {
            OrderListItem item = (OrderListItem) adapter.getItem(position);
            switch (view.getId()) {
                case R.id.btn_contact://点击联系货主
                    jumpToOrderDetail(item);
                    break;
            }
        }
    };


    /**
     * 跳转到订单详情界面
     */
    private void jumpToOrderDetail(OrderListItem item){
        Intent it = new Intent(mActivity, OrderDetailActivity.class);
        it.putExtra("orderId", item.getId());
        startActivity(it);
    }

    /**
     * 下拉刷新,上拉加载监听
     */
    private OnRefreshLoadMoreListener refreshLoadMoreListener = new OnRefreshLoadMoreListener() {
        @Override
        public void onRefresh(@NonNull RefreshLayout refreshLayout) {
            requestGetHallOrderList(true);
        }

        @Override
        public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
            requestGetHallOrderList(false);
        }
    };

    @Override
    public void onSuccess(String data) {
        switch (neType) {
            case 1://获取订单列表成功
                List<OrderListItem> list=GsonUtil.get().fromJson(data, new TypeToken<List<OrderListItem>>(){}.getType());
                if(currentActIsRefresh){//刷新

                    currentPage=1;
                    srl.finishRefresh();
                    srl.setNoMoreData(false);
                    adapter.setData(list);

                }else{//加载更多
                    if(list.size()>0){
                        srl.finishLoadMore();
                        currentPage++;
                        adapter.addItemAll(list);
                    }else{
                        srl.finishLoadMoreWithNoMoreData();
                    }
                }

                break;
        }
    }

    @Override
    public void onError(ApiException e) {
        super.onError(e);
        switch (neType) {
            case 1:
                srl.finishRefresh(false);
                srl.finishLoadMore(false);
                break;
        }
    }

    private int currentPage;//当前所处的页码
    private boolean currentActIsRefresh;//用来标识当前是否是刷新操作触发的请求列表(从筛选按钮触发也属于刷新操作)

    /**
     * 请求获取配货大厅订单列表数据
     * @param isRefresh true:刷新,false:加载更多
     */
    private void requestGetHallOrderList(boolean isRefresh){
        neType=1;
        GetHallOrderListApi api = new GetHallOrderListApi();

        api.setHeader(app.getAuthorization());
        api.setUserHeader(app.getUserHeader());

        currentActIsRefresh = isRefresh;
        ReqHallOrderListBean bean = buildReqListBeanByCurrentCondition();
        api.setBean(bean);

        pClass.startHttpRequest(mActivity,api);
    }


    /**
     * 根据当前选择的筛选提条件创一个列表请求的参数对象
     */
    private ReqHallOrderListBean buildReqListBeanByCurrentCondition(){
        ReqHallOrderListBean bean = new ReqHallOrderListBean();

        //设置次属性是用来标识是请求未报价订单
        bean.setIsQuotation(false);

        //页码
        if(currentActIsRefresh){
            bean.setPageIndex(1);
        }else{
            bean.setPageIndex(currentPage+1);
        }

        //起始地
        if(selectedStartRegion!=null){
            bean.setStarTtion(selectedStartRegion.getId());
        }

        //目的地
        if(selectedEndRegion!=null){
            List<String> endRegionIdList = new ArrayList<>();
            for(RegionBean endRb:selectedEndRegion){
                String id = endRb.getId();
                if(!TextUtils.isEmpty(id)){
                    endRegionIdList.add(endRb.getId());
                }
            }
            bean.setEndTion(endRegionIdList);
        }

        //车型
        if(selectedTruckType!=null){
            List<String> truckIdList = new ArrayList<>();
            for(CarType ct:selectedTruckType){
                truckIdList.add(ct.getId());
            }
            bean.setAutomobileTypName(truckIdList);
        }

        //车长
        bean.setAutomobileLength(selectedTruckLen);

        //装货时间范围
        if(selectedLoadTimeScope!=null){
            List<String> timeScopeCodeList = new ArrayList<>();
            for(SimpleBean sb:selectedLoadTimeScope){
                timeScopeCodeList.add(sb.getCode());
            }
            bean.setLoadingTime(timeScopeCodeList);
        }

        return bean;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    private int slrHeight;

    private void obtainPopAnchorHeight(){
        srl.post(new Runnable() {
            @Override
            public void run() {
                slrHeight = srl.getHeight();
                initStartRegionPopup();
                initEndRegionPopup();
                initOrderFilterPopup();
            }
        });
    }


    /**
     * 控制筛选栏的帮助工具
     */
    private FilterBarHelper filterBarHelper;

    private void initFilterBarHelper(){
        filterBarHelper = new FilterBarHelper(mActivity, vgFilterBar);
        filterBarHelper.setShouldControlItemVgId(R.id.vg_click_stub_1,R.id.vg_click_stub_2,R.id.vg_click_stub_3);
    }


    /**
     * 筛选栏按钮点击监听
     */
    @OnClick({R.id.vg_click_stub_1,R.id.vg_click_stub_2,R.id.vg_click_stub_3})
    public void onFilterBarClicked(View v){
        int clickedId = v.getId();
        showPopup(clickedId);
//        popupArray.get(clickedId).dismiss();
        filterBarHelper.selectItem(clickedId);


    }


    private void showPopup(int vgId){
        BaseFilterOpSelectPopup popup = popupArray.get(vgId);
        if(popup!=null){
            popup.showAsDropDown(vgFilterBar);
        }
    }

    /**
     * 最近一次关闭的popup对应的stubId
     */
    private int recentlyDismissPopupStubId=-1;

    /**
     * 初始化起始地选择的popup
     */
    private void initStartRegionPopup(){
        StartRegionSelectPopup popup = new StartRegionSelectPopup(app,mActivity,getPopShouldShowHeight());
        //起始地选择监听
        popup.setCallback(new StartRegionSelectPopup.Callback() {
            @Override
            public void onRegionSelected(RegionBean region) {
                selectedStartRegion = region;

                filterBarHelper.setItemTvText(R.id.vg_click_stub_1,region.getName());

                requestGetHallOrderList(true);
            }
        });
        //popup关闭监听
        popup.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                recentlyDismissPopupStubId = R.id.vg_click_stub_1;
                filterBarHelper.resetAllItemToNormal();
            }
        });
        popupArray.put(R.id.vg_click_stub_1,popup);
    }


    /**
     * 初始化目的地选择popup
     */
    private void initEndRegionPopup(){
        EndRegionSelectPopup popup = new EndRegionSelectPopup(app,mActivity,getPopShouldShowHeight());
        //目的地选择监听
        popup.setCallback(new EndRegionSelectPopup.Callback() {
            @Override
            public void onRegionSelected(List<RegionBean> regionList) {
                selectedEndRegion = regionList;

                String assembledText = assembleMultiRegionText(regionList);
                filterBarHelper.setItemTvText(R.id.vg_click_stub_2,assembledText);

                requestGetHallOrderList(true);
            }
        });
        //popup关闭监听
        popup.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                recentlyDismissPopupStubId = R.id.vg_click_stub_2;
                filterBarHelper.resetAllItemToNormal();
            }
        });
        popupArray.put(R.id.vg_click_stub_2,popup);
    }


    /**
     * 初始化订单筛选popup
     */
    private void initOrderFilterPopup(){
        OrderFilterSelectPopup popup = new OrderFilterSelectPopup(mActivity, getPopShouldShowHeight(),
                app.getTrucktypeList(),app.getCarLenghtList());

        popup.setCallback(new OrderFilterSelectPopup.Callback() {
            @Override
            public void onSelectConfirm(List<CarType> truckType, List<String> truckLen,List<SimpleBean> timeScope) {
                selectedTruckType=truckType;
                selectedTruckLen = truckLen;
                selectedLoadTimeScope = timeScope;

                requestGetHallOrderList(true);
            }
        });

        //popup关闭监听
        popup.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                recentlyDismissPopupStubId = R.id.vg_click_stub_3;
                filterBarHelper.resetAllItemToNormal();
            }
        });

        popupArray.put(R.id.vg_click_stub_3,popup);
    }


    /**
     * 获取popup应该显示的高度
     * @return
     */
    private int getPopShouldShowHeight(){
        return slrHeight + MesureStorer.getMainBottomTabHeight();
    }


    /**
     * 拼接地区列表名称
     * @param regionList
     * @return
     */
    private String assembleMultiRegionText(List<RegionBean> regionList){
        StringBuilder sb = new StringBuilder();

        for(RegionBean bean:regionList){
            sb.append(bean.getName()).append(",");
        }

        return sb.substring(0, sb.length()-1);
    }

}
