package com.lulian.driver.view.activity.caractivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.lulian.driver.R;
import com.lulian.driver.base.BaseActivity;
import com.lulian.driver.base.MD5Util;
import com.lulian.driver.entity.api.GetListByUserIdApi;
import com.lulian.driver.entity.server.resulte.CapitalAcount;
import com.lulian.driver.entity.server.resulte.CapitalAcountInfo;
import com.lulian.driver.utils.L;
import com.lulian.driver.view.wheel.PayPsdInputView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by MARK on 2018/7/7.
 */

public class ChangePaypwdActivity extends BaseActivity {
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
    @BindView(R.id.pay_password)
    PayPsdInputView payPassword;
    @BindView(R.id.password_remove)
    RelativeLayout passwordRemove;
    @BindView(R.id.password_btn_next)
    Button passwordBtnNext;

    private CapitalAcountInfo caInfo;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepaypwd);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initData() {
        neType = 0;
        GetListByUserIdApi glbui = new GetListByUserIdApi();
        glbui.setHeader(app.getAuthorization());
        glbui.setUserHeader(app.getUserHeader());
        glbui.setUserId(app.getUserId());
        pClass.startHttpRequest(this, glbui);

    }

    @Override
    public void onSuccess(String data) {
        if (neType == 0) {
            //获取当前用户的账户信息
            List<CapitalAcount> capitalAcount = JSONObject.parseArray(data, CapitalAcount.class);

            L.e("test1", "capitalAcount.size()=" + capitalAcount.size());
            caInfo = JSONObject.parseObject(capitalAcount.get(0).getMyInfo_AccountEntity(),
                    CapitalAcountInfo.class);

            String payPassWord = MD5Util.encrypt(payPassword.getText().toString().trim());

            if (!payPassword.getText().toString().trim().equals("")) {
                if (payPassWord.equals(caInfo.getPassword())) {
                    //跳转到银行卡界面
                }
            }
        }
    }

    private void initView() {
        textContent.setText("身份验证");
    }

    @OnClick({R.id.img_return, R.id.password_btn_next, R.id.password_remove})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_return:
                this.finish();
                break;
            case R.id.password_remove:  //忘记密码
                Intent intent = new Intent(this, PayPassWordForgetActivity.class);
                startActivity(intent);
                finish();
                break;

        }
    }

}
