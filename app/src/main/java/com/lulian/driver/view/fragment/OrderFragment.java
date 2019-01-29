package com.lulian.driver.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.lulian.driver.R;
import com.lulian.driver.adapter.FragAdapter;
import com.lulian.driver.base.BaseFragment;
import com.lulian.driver.view.activity.caractivity.ServiceActivity;
import com.rxretrofitlibrary.retrofit_rx.exception.ApiException;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import q.rorbin.badgeview.QBadgeView;


/**
 * Created by MARK on 2018/6/9.
 * 订单
 */

public class OrderFragment extends BaseFragment implements RadioGroup.OnCheckedChangeListener, ViewPager.OnPageChangeListener {

    @BindView(R.id.action_img_left)
    ImageView actionImgLeft;
    @BindView(R.id.action_txt_left)
    TextView actionTxtLeft;
    @BindView(R.id.action_img_right)
    ImageView actionImgRight;
    @BindView(R.id.action_txt_right)
    TextView actionTxtRight;
    @BindView(R.id.action_layout_ba)
    LinearLayout actionLayoutBa;
    @BindView(R.id.order_layout_show)
    LinearLayout orderLayoutShow;
    @BindView(R.id.order_viewpage_show)
    ViewPager orderViewpageShow;
    @BindView(R.id.order_layout_service)
    LinearLayout orderLayoutService;

    private View view;
    public static  RadioGroup action_rg_top;

    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_order, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        initData();
        return view;
    }
    private void initData() {
        ArrayList<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new AllOrderFragment());
        fragmentList.add(new KnownOrderFragment());
        ArrayList<String> titles = new ArrayList<>();
        FragAdapter fAdapter = new FragAdapter(getChildFragmentManager(), fragmentList, titles);
        orderViewpageShow.setAdapter(fAdapter);
        orderViewpageShow.setOnPageChangeListener(this);

    }

    private void initView() {

        action_rg_top = view.findViewById(R.id.action_rg_top);
        action_rg_top.setOnCheckedChangeListener(this);

        QBadgeView qbadge = new QBadgeView(getActivity());
        qbadge.bindTarget(actionLayoutBa).setBadgeGravity(Gravity.END | Gravity.TOP).setBadgeNumber(app.getMessageNum());

        action_rg_top.check(R.id.action_rb_allorder);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onSuccess(String data) {

    }

    @Override
    public void onError(ApiException e) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.action_rb_allorder:
                orderViewpageShow.setCurrentItem(0);
                break;
            case R.id.action_rb_portoinorder:
            orderViewpageShow.setCurrentItem(1);
            break;
        }
    }

    @OnClick({R.id.action_img_left, R.id.action_img_right,
            R.id.action_layout_ba,R.id.order_layout_service})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.action_layout_ba:

                break;
            case R.id.order_layout_service:
                mActivity.startActivity(new Intent(mActivity,ServiceActivity.class));
                break;

        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                action_rg_top.check(R.id.action_rb_allorder);
                break;
            case 1:
                action_rg_top.check(R.id.action_rb_portoinorder);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
