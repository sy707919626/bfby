package com.lulian.driver.view.activity.caractivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lulian.driver.R;
import com.lulian.driver.adapter.FragAdapter;
import com.lulian.driver.base.BaseActivity;
import com.lulian.driver.listener.TransmitStrListener;
import com.lulian.driver.view.fragment.CargoCollectFragment;
import com.lulian.driver.view.fragment.DriverCollectFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.lulian.driver.R.id.actionmotor_img_left;
import static com.lulian.driver.R.id.actionmotor_txt_left;
import static com.lulian.driver.R.id.actionmotor_txt_right;

/**
 * Created by MARK on 2018/6/8.
 */

public class MyCollectActivity extends BaseActivity implements TransmitStrListener {


    @BindView(actionmotor_img_left)
    ImageView actionmotorImgLeft;
    @BindView(actionmotor_txt_left)
    TextView actionmotorTxtLeft;
    @BindView(actionmotor_txt_right)
    TextView actionmotorTxtRight;
    @BindView(R.id.actionmotor_layout_ba)
    LinearLayout actionmotorLayoutBa;
    @BindView(R.id.motorcade_viewpager_layout)
    ViewPager motorcadeViewpagerLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycollect);
        Intent intent = getIntent();
        ButterKnife.bind(this);
        initView();
        initData();
    }

    @Override
    public void onSuccess(String data) {

    }


    private void initView() {
        actionmotorImgLeft.setVisibility(View.GONE);
        actionmotorTxtLeft.setVisibility(View.GONE);
        actionmotorTxtRight.setVisibility(View.GONE);
    }

    private void initData() {

        ArrayList<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new CargoCollectFragment());
        fragmentList.add(new DriverCollectFragment());
        ArrayList<String> titles = new ArrayList<String>();
        titles.add("收藏的货主");
        titles.add("收藏车队长");
        FragAdapter fAdapter = new FragAdapter(getSupportFragmentManager(), fragmentList, titles);
        motorcadeViewpagerLayout.setAdapter(fAdapter);
//        motorcadeViewpagerLayout.setOnPageChangeListener(this);

    }


    @Override
    public void transmitSuccess(Object str) {

    }
}
