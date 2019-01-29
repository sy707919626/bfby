package com.lulian.driver.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.reflect.TypeToken;
import com.lulian.driver.R;
import com.lulian.driver.adapter.DriverLeaderAdapter;
import com.lulian.driver.adapter.base.BaseRvAdapter;
import com.lulian.driver.base.BaseFragment;
import com.lulian.driver.entity.api.AddDriverLeaderApi;
import com.lulian.driver.entity.api.DriverLeaderListApi;
import com.lulian.driver.entity.event.RefreshMyDriverLeader;
import com.lulian.driver.entity.server.resulte.DriverLeaderInfo;
import com.lulian.driver.utils.ChoiceDialogTool;
import com.lulian.driver.utils.ProjectUtil;
import com.lulian.driver.utils.RvConfigHelper;
import com.rxretrofitlibrary.retrofit_rx.exception.ApiException;
import com.rxretrofitlibrary.retrofit_rx.utils.GsonUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by MARK on 2018/6/9.
 * 全部车队长列表
 */

public class DriverLeaderAllFragment extends BaseFragment{
    Unbinder unbinder;

    @BindView(R.id.srl)
    SmartRefreshLayout srl;
    @BindView(R.id.rv)
    RecyclerView rv;

    private DriverLeaderAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_driver_leader, container, false);
        unbinder = ButterKnife.bind(this, view);

        initView();

        requestDriverLeaderList(true);

        return view;
    }

    private void initView(){
        RvConfigHelper.configToLLMgrVertical(getActivity(),rv);
        adapter = new DriverLeaderAdapter(getActivity());
        adapter.setChildClickListener(listItemChildClickListener);
        rv.setAdapter(adapter);

        srl.setOnRefreshLoadMoreListener(refreshLoadMoreListener);
    }


    /**
     * 下拉刷新,上拉加载监听
     */
    private OnRefreshLoadMoreListener refreshLoadMoreListener = new OnRefreshLoadMoreListener() {
        @Override
        public void onRefresh(@NonNull RefreshLayout refreshLayout) {
            requestDriverLeaderList(true);
        }

        @Override
        public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
            requestDriverLeaderList(false);
        }
    };

    private int currentClickedPosition;//记录当前进行操作的条目的位置

    /**
     * 列表条目中的view的点击监听
     */
    private BaseRvAdapter.OnChildClickListener listItemChildClickListener = new BaseRvAdapter.OnChildClickListener() {
        @Override
        public void onClick(BaseRvAdapter adapter, View view, int position) {
            DriverLeaderInfo item = (DriverLeaderInfo) adapter.getItem(position);
            currentClickedPosition = position;
            switch (view.getId()) {
                case R.id.vg_contact_click_stub://点击 联系
                    ProjectUtil.checkCallPhone(getActivity(),item.getPhone());
                    break;
                case R.id.vg_favorite_click_stub://点击收藏
                    performClickAddFavorite(item);
                    break;
            }
        }
    };


    private void performClickAddFavorite(final DriverLeaderInfo bean){
        if(bean.getFavorite()==1){//这个属性为1,表示这是已经收藏过的
            return;
        }

        ChoiceDialogTool.showDialog(mActivity, "确定要收藏吗", "取消", "确定",
                new ChoiceDialogTool.Callback() {
                    @Override
                    public void onLeftBtnClick() {
                    }

                    @Override
                    public void onRightBtnClick() {
                        requestAddDriverLeader(bean.getId());
                    }
                });
    }

    private int currentPage;//当前所处的页码
    private boolean currentActIsRefresh;//用来标识当前是否是刷新操作触发的请求列表(从筛选按钮触发也属于刷新操作)

    /**
     *  请求,获取车队长列表
     * @param isRefresh
     */
    private void requestDriverLeaderList(boolean isRefresh) {
        neType = 1;
        currentActIsRefresh = isRefresh;

        DriverLeaderListApi api = new DriverLeaderListApi();
        api.setHeader(app.getAuthorization());
        api.setUserHeader(app.getUserHeader());

        //页码
        if(currentActIsRefresh){
            api.setPage(1);
        }else{
            api.setPage(currentPage+1);
        }

        pClass.startHttpRequest(mActivity, api);
    }

    /**
     * 请求,将车队长加入我的收藏
     */
    private void requestAddDriverLeader(String driverLeaderId){
        neType = 2;
        AddDriverLeaderApi api = new AddDriverLeaderApi();
        api.setHeader(app.getAuthorization());
        api.setUserHeader(app.getUserHeader());

        api.setDriverLeaderId(driverLeaderId);

        pClass.startHttpRequest(mActivity,api);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void onSuccess(String data) {
        switch (neType) {
            case 1://请求车队长列表成功
                performGetDriverLeaderListSuccess(data);
                break;
            case 2://加入收藏成功
                //将请求收藏成功的条目的数据更改,并刷新
                DriverLeaderInfo item = adapter.getItem(currentClickedPosition);
                item.setFavorite(1);
                adapter.notifyItemChanged(currentClickedPosition);
                //通知刷新 "我的"车队长列表
                EventBus.getDefault().post(new RefreshMyDriverLeader());
                ProjectUtil.show(mActivity,"加入收藏成功");
                break;
        }
    }


    @Override
    public void onError(ApiException e) {
        switch (neType = 1) {
            case 1://请求车队长列表失败
                srl.finishRefresh(false);
                srl.finishLoadMore(false);
                break;
        }
    }

    private void performGetDriverLeaderListSuccess(String data){
        List<DriverLeaderInfo> list=GsonUtil.get().fromJson(data,new TypeToken<List<DriverLeaderInfo>>(){}.getType());
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
    }

}