package com.lulian.driver.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lulian.driver.R;
import com.lulian.driver.adapter.CollectAdapter;
import com.lulian.driver.base.BaseFragment;
import com.lulian.driver.utils.L;
import com.rxretrofitlibrary.retrofit_rx.exception.ApiException;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by MARK on 2018/6/9.
 */

public class CargoCollectFragment extends BaseFragment implements PullLoadMoreRecyclerView.PullLoadMoreListener {
    String TAG = "CargoCollectFragment";
    Unbinder unbinder;
    @BindView(R.id.motor_recyle_list)
    PullLoadMoreRecyclerView motorRecyleList;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_collect, container, false);
        unbinder = ButterKnife.bind(this, view);
        getData();
        initView();
        return view;
    }

    @Override
    public void onSuccess(String data) {
        L.e("test", data.length() + " my");
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }

    private void getData() {

    }

    private void initView() {
        motorRecyleList.setLinearLayout();
        motorRecyleList.setItemAnimator(new DefaultItemAnimator());
        motorRecyleList.setOnPullLoadMoreListener(this);
        CollectAdapter collectAdapter = new CollectAdapter(0);
        motorRecyleList.setAdapter(collectAdapter);
    }

    @Override
    public void onError(ApiException e) {

    }

    @Override
    public void showProg() {

    }

    @Override
    public void dismissProg() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }

}
