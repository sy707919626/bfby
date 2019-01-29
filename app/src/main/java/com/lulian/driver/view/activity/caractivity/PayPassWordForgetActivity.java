package com.lulian.driver.view.activity.caractivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.lulian.driver.R;
import com.lulian.driver.base.BaseActivity;
import com.lulian.driver.entity.api.GetMyDatumApi;
import com.lulian.driver.entity.api.SmsApi;
import com.lulian.driver.entity.server.resulte.MyDatum;
import com.lulian.driver.utils.ProjectUtil;
import com.lulian.driver.view.wheel.ClearEditText;
import com.lulian.driver.view.wheel.MyCountDownTimer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/8/23.
 */

public class PayPassWordForgetActivity extends BaseActivity {
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
    @BindView(R.id.password_edit_idcard)
    ClearEditText passwordEditIdcard;
    @BindView(R.id.password_btn_next)
    Button passwordBtnNext;
    @BindView(R.id.idcard_yanzheng_layout)
    LinearLayout idcardYanzhengLayout;
    @BindView(R.id.forgetpwd_edit_phone)
    ClearEditText forgetpwdEditPhone;
    @BindView(R.id.forgetpwd_phone_code)
    ClearEditText forgetpwdPhoneCode;
    @BindView(R.id.forgetpwd_btn_phone_getcode)
    Button forgetpwdBtnPhoneGetcode;
    @BindView(R.id.forgetpwd_phone_btn_submit)
    Button forgetpwdPhoneBtnSubmit;
    @BindView(R.id.phone_yanzheng_layout)
    LinearLayout phoneYanzhengLayout;

    private int neType;
    private MyDatum myDatum;
    private String tempMobile;
    private boolean canClick;
    private String msg="";
    private String Code = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paypwdforget);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        textContent.setText("找回支付密码");
        idcardYanzhengLayout.setVisibility(View.VISIBLE);
        phoneYanzhengLayout.setVisibility(View.GONE);
    }

    private void initData() {
        //获取当前用户个人信息
        neType = 0;
        GetMyDatumApi getMy = new GetMyDatumApi();
        getMy.setHeader(app.getAuthorization());
        getMy.setUserHeader(app.getUserHeader());
        pClass.startHttpRequest(this, getMy);
    }

    @Override
    public void onSuccess(String data) {
        if (neType == 0) {
            myDatum = JSONObject.parseObject(data, MyDatum.class);

        } else if (neType == 1){
            JSONObject jsonObject=JSONObject.parseObject(data);
            String state=jsonObject.getString("state");
            msg = jsonObject.getString("msg");

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
    }

    @OnClick({R.id.img_return, R.id.password_btn_next, R.id.forgetpwd_btn_phone_getcode,
            R.id.forgetpwd_phone_btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_return:
                this.finish();
                break;
            case R.id.password_btn_next:  //验证

                if (passwordEditIdcard.getText().toString().trim().equals("")){
                    ProjectUtil.show(this, "请输入身份证号码");
                } else {

                    if (passwordEditIdcard.getText().toString().trim().equals(myDatum.getIdentityCode())) {
                        idcardYanzhengLayout.setVisibility(View.GONE);
                        phoneYanzhengLayout.setVisibility(View.VISIBLE);
                    }
                }

                break;
            case R.id.forgetpwd_btn_phone_getcode:  //验证码
                forgetpwdBtnPhoneGetcode=(Button)findViewById(R.id.forgetpwd_btn_getcode);
                final MyCountDownTimer myCountDownTimer = new MyCountDownTimer(60000,1000,forgetpwdBtnPhoneGetcode);

                tempMobile=forgetpwdEditPhone.getText().toString().trim();
                if(tempMobile.equals("")){
                    ProjectUtil.show(this,"请输入手机号码");
                }else if(ProjectUtil.isMobileNO(tempMobile)) {
                    neType = 1;
                    SmsApi smsApi = new SmsApi();
                    smsApi.setHeader(app.getAuthorization());
                    smsApi.setMobile(tempMobile);
                    smsApi.setVerifyType("3");
                    pClass.startHttpRequest(this, smsApi);
                    myCountDownTimer.start();
                }else{
                    ProjectUtil.show(this,"请输入正确的手机号码");
                }

                break;
            case R.id.forgetpwd_phone_btn_submit:  //确定
                if(!canClick){
                    ProjectUtil.show(this,msg);
                }else {
                    String cMobile = forgetpwdEditPhone.getText().toString().trim();
                    String cCode = forgetpwdBtnPhoneGetcode.getText().toString().trim();
                    if (cMobile.equals("")) {
                        ProjectUtil.show(this, "请输入手机号");
                    } else if (cCode.equals("")) {
                        ProjectUtil.show(this, "请输入验证码");
                    } else {
                        if (cCode.equals(Code)) {
                            startActivity(new Intent(this, UpadtePaypwdActivity.class));
                            finish();
                        }
                    }
                }
                break;

        }
    }
}
