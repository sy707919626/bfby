package com.lulian.driver.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.lulian.driver.R;
import com.lulian.driver.adapter.FragAdapter;
import com.lulian.driver.base.BaseFragment;
import com.lulian.driver.view.activity.caractivity.AddDriverActivity;
import com.lulian.driver.view.activity.caractivity.SearchActivity;
import com.rxretrofitlibrary.retrofit_rx.exception.ApiException;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.lulian.driver.R.id.action_rb_allorder;


/**
 * Created by MARK on 2018/6/9.
 */

public class DriverLeaderFragment extends BaseFragment implements RadioGroup.OnCheckedChangeListener, ViewPager.OnPageChangeListener {

    Unbinder unbinder;
    @BindView(R.id.actionmotor_txt_left)
    TextView actionmotorTxtLeft;
    @BindView(R.id.actionmotor_img_left)
    LinearLayout actionmotorImgLeft;
    @BindView(R.id.action_rb_portoinorder)
    RadioButton actionRbPortoinorder;
    @BindView(R.id.action_rb_allorder)
    RadioButton actionRbAllorder;
    @BindView(R.id.action_rg_top)
    RadioGroup actionRgTop;
    @BindView(R.id.actionmotor_txt_right)
    TextView actionmotorTxtRight;
    @BindView(R.id.actionmotor_layout_ba)
    LinearLayout actionmotorLayoutBa;
    @BindView(R.id.motorcade_viewpager_layout)
    ViewPager motorcadeViewpagerLayout;


    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_driver_leader, container, false);
        unbinder = ButterKnife.bind(this, view);
        initData();
        initView();
        return view;
    }


    private void initData() {
        ArrayList<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new DriverLeaderMyFragment());
        fragmentList.add(new DriverLeaderAllFragment());
        ArrayList<String> titles = new ArrayList<>();
        FragAdapter fAdapter = new FragAdapter(getChildFragmentManager(), fragmentList,titles);
        motorcadeViewpagerLayout.setAdapter(fAdapter);
        motorcadeViewpagerLayout.setOnPageChangeListener(this);
    }

    private void initView() {
        actionRgTop.setOnCheckedChangeListener(this);
        actionRgTop.check(R.id.action_rb_portoinorder);
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

    @OnClick({R.id.actionmotor_img_left, R.id.actionmotor_layout_ba})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.actionmotor_img_left:
                startActivity(new Intent(mActivity, SearchActivity.class));
                break;
            case R.id.actionmotor_layout_ba:
                startActivity(new Intent(mActivity, AddDriverActivity.class));
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
                actionRgTop.check(R.id.action_rb_portoinorder);
                break;
            case 1:
                actionRgTop.check(R.id.action_rb_allorder);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.action_rb_portoinorder:
                motorcadeViewpagerLayout.setCurrentItem(0);
                break;
            case action_rb_allorder:
                motorcadeViewpagerLayout.setCurrentItem(1);
                break;
        }
    }
}
