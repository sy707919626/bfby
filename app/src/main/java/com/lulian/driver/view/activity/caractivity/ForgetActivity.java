package com.lulian.driver.view.activity.caractivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.lulian.driver.R;
import com.lulian.driver.base.BaseActivity;
import com.lulian.driver.entity.api.CheckSmsApi;
import com.lulian.driver.entity.api.SmsApi;
import com.lulian.driver.utils.ProjectUtil;
import com.lulian.driver.view.wheel.ClearEditText;
import com.lulian.driver.view.wheel.MyCountDownTimer;
import com.rxretrofitlibrary.retrofit_rx.exception.ApiException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by MARK on 2018/6/8.
 */

public class ForgetActivity extends BaseActivity {
    private static final int SMS_GETCODE=0;
    private static final int SMS_CHECKCODE=1;

    @BindView(R.id.img_return)
    ImageView imgReturn;
    @BindView(R.id.text_content)
    TextView textContent;
    @BindView(R.id.forgetpwd_edit_name)
    ClearEditText forgetpwdEditName;
    @BindView(R.id.forgetpwd_edit_code)
    ClearEditText forgetpwdEditCode;
    @BindView(R.id.forgetpwd_btn_submit)
    Button forgetpwdBtnSubmit;
    private int neType;
    private String tempMobile;
    private Button forgetpwd_btn_getcode;
    private boolean canClick;
    private String msg="";
    private String Code = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpwd);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        textContent.setText("找回登录密码");
        forgetpwd_btn_getcode=(Button)findViewById(R.id.forgetpwd_btn_getcode);
        final MyCountDownTimer myCountDownTimer = new MyCountDownTimer(60000,1000,forgetpwd_btn_getcode);
        forgetpwd_btn_getcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                neType=SMS_GETCODE;
                tempMobile=forgetpwdEditName.getText().toString().trim();
                if(tempMobile.equals("")){
                    ProjectUtil.show(ForgetActivity.this,"请输入手机号码");
                }else if(ProjectUtil.isMobileNO(tempMobile)) {
                    SmsApi smsApi = new SmsApi();
                    smsApi.setHeader(app.getAuthorization());
                    smsApi.setMobile(tempMobile);
                    smsApi.setVerifyType("2");
                    pClass.startHttpRequest(ForgetActivity.this, smsApi);
                    myCountDownTimer.start();
                }else{
                    ProjectUtil.show(ForgetActivity.this,"请输入正确的手机号码");
                }
            }
        });
    }


    @Override
    public void onSuccess(String data) {
        JSONObject jsonObject=JSONObject.parseObject(data);
        String state=jsonObject.getString("state");
        msg=jsonObject.getString("msg");

        if(state.equals("0")){
            canClick=false;
            ProjectUtil.show(this,msg);
        }else if(state.equals("1")){
            canClick=true;
            JSONObject jo=jsonObject.getJSONObject("data");
            Code = jo.getString("Code");
            ProjectUtil.show(this,msg+"验证码为:"+ Code);
        }


    }

    @Override
    public void onError(ApiException e) {
        super.onError(e);
    }

    @Override
    public void dismissProg() {
        super.dismissProg();
    }

    @Override
    public void showProg() {
        super.showProg();
    }


    @OnClick({R.id.img_return, R.id.forgetpwd_btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_return:
                this.finish();
                break;
            case R.id.forgetpwd_btn_submit:
                if(!canClick){
                    ProjectUtil.show(this,msg);
                }else {
                    neType = SMS_CHECKCODE;
                    String cMobile = forgetpwdEditName.getText().toString().trim();
                    String cCode = forgetpwdEditCode.getText().toString().trim();
                    if (cMobile.equals("")) {
                        ProjectUtil.show(this, "请输入手机号");
                    } else if (cCode.equals("")) {
                        ProjectUtil.show(this, "请输入验证码");
                    } else {
                        if (cCode.equals(Code)) {
                            Intent intent = new Intent(this, ChangePwdActivity.class);
                            intent.putExtra("pwdTextName", tempMobile);
                            startActivity(intent);
                            finish();
                        }
                    }
                }
                break;
        }
    }
}
