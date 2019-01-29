package com.lulian.driver.view.activity.caractivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lulian.driver.R;
import com.lulian.driver.base.BaseActivity;
import com.lulian.driver.entity.server.resulte.DriverVeDetail;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by MARK on 2018/6/8.
 * 我的资料界面
 */

public class MyDatumActivity extends BaseActivity {

    @BindView(R.id.img_return)
    ImageView imgReturn;
    @BindView(R.id.text_content)
    TextView textContent;
    @BindView(R.id.title_txt_rightbottom)
    TextView titleTxtRightbottom;
    @BindView(R.id.title_txtrightbottomleft)
    TextView titleTxtrightbottomleft;
    @BindView(R.id.protocol_btn_agree)
    Button protocolBtnAgree;
    @BindView(R.id.activity_main)
    RelativeLayout activityMain;
    @BindView(R.id.member_layout_userinfo)
    RelativeLayout memberLayoutUserinfo;
    @BindView(R.id.member_layout_carinfo)
    RelativeLayout memberLayoutCarinfo;

    private String userId;
    private DriverVeDetail dvd;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");

        initView();
    }


    private void initView() {
        textContent.setText("我的资料");
    }

    @OnClick({R.id.img_return, R.id.member_layout_userinfo, R.id.member_layout_carinfo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_return:
                this.finish();
                break;
            case R.id.member_layout_userinfo://点击个人信息
                startActivity(new Intent(this,MyDatumPersonalInfoActivity.class));
                break;
            case R.id.member_layout_carinfo://点击车辆信息
                startActivity(new Intent(this,MyDatumTruckInfoActivity.class));
                break;
        }
    }

}
