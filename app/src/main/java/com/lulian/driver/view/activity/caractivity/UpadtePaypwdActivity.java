package com.lulian.driver.view.activity.caractivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lulian.driver.R;
import com.lulian.driver.base.BaseActivity;
import com.lulian.driver.base.MD5Util;
import com.lulian.driver.entity.api.GetListByUserIdApi;
import com.lulian.driver.entity.api.ReSetPasswordApi;
import com.lulian.driver.entity.server.resulte.CapitalAcount;
import com.lulian.driver.entity.server.resulte.CapitalAcountInfo;
import com.lulian.driver.utils.L;
import com.lulian.driver.utils.ProjectUtil;
import com.lulian.driver.view.wheel.PayPsdInputView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/8/23.
 */

public class UpadtePaypwdActivity extends BaseActivity {
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
    @BindView(R.id.update_password1)
    PayPsdInputView updatePassword1;
    @BindView(R.id.pay_password2)
    PayPsdInputView payPassword2;
    @BindView(R.id.updatepwd_btn_submit)
    Button updatepwdBtnSubmit;

    private CapitalAcountInfo caInfo;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatepaypwd);
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
        } else if (neType == 1){

            JSONObject jsonObject = JSON.parseObject(data);
            String state = jsonObject.getString("state");
            if (state.equals("1")){
                ProjectUtil.show(this, "修改支付密码成功");
                startActivity(new Intent(this, AddBankListActivity.class));

                finish();
            }
        }
    }

    private void initView() {
        textContent.setText("重置支付密码");
    }

    @OnClick({R.id.img_return, R.id.updatepwd_btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_return:
                this.finish();
                break;

            case R.id.updatepwd_btn_submit:
                String password1 = updatePassword1.getText().toString().trim();
                String password2 = payPassword2.getText().toString().trim();

                if (password1.equals("")){
                    ProjectUtil.show(this, "新密码不能为空");
                } else if (password2.equals("")){
                    ProjectUtil.show(this, "再次确认密码不能为空");
                } else if (password1.equals(password2)){
                    ProjectUtil.show(this, "两次密码不一致，请确认后再次提交");
                } else{
                    neType = 1;
                    ReSetPasswordApi reset = new ReSetPasswordApi();
                    reset.setHeader(app.getAuthorization());
                    reset.setUserHeader(app.getUserHeader());
                    reset.setAccountId(caInfo.getId());
                    reset.setOldPassword(caInfo.getPassword());
                    reset.setNewPassword(MD5Util.encrypt(password1));
                    reset.setUserId(app.getUserId());
                    pClass.startHttpRequest(this, reset);
                }
                break;
        }
    }
}
