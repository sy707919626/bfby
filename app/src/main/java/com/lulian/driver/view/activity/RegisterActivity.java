package com.lulian.driver.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.lulian.driver.R;
import com.lulian.driver.base.BaseActivity;
import com.lulian.driver.entity.api.CheckIsRegisterApi;
import com.lulian.driver.entity.api.SmsApi;
import com.lulian.driver.utils.ActivityStorer;
import com.lulian.driver.utils.ProjectUtil;
import com.lulian.driver.view.activity.caractivity.ProtocolActivity;
import com.lulian.driver.view.activity.caractivity.UsinghelpActivity;
import com.lulian.driver.view.wheel.ClearEditText;
import com.lulian.driver.view.wheel.MyCountDownTimer;
import com.rxretrofitlibrary.retrofit_rx.exception.ApiException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by MARK on 2018/6/8.
 * 注册
 */

public class RegisterActivity extends BaseActivity {
    private static final int SMS_GETCODE = 0;
    private static final int CHECK_MOBILE=1;
    @BindView(R.id.img_return)
    ImageView imgReturn;
    @BindView(R.id.text_content)
    TextView textContent;
    @BindView(R.id.activity_main)
    RelativeLayout activityMain;
    @BindView(R.id.reg_edit_name)
    ClearEditText regEditName; //手机号输入框
    @BindView(R.id.reg_edit_code)
    ClearEditText regEditCode;
    @BindView(R.id.reg_ckbox_procotol)
    CheckBox regCkboxProcotol;
    @BindView(R.id.reg_text_protocol)
    TextView regTextProtocol;
    @BindView(R.id.reg_btn_submit)
    Button regBtnSubmit;
    @BindView(R.id.reg_text_ser)
    TextView regTextSer;
    @BindView(R.id.reg_text_help)
    TextView regTextHelp;

    @BindView(R.id.reg_btn_getcode)
    Button reg_btn_getcode;

    /**
     * 发送验证码成功时,填写的手机号保存到这里,点击下一步时,应获取界面上填写的手机号 和 这个手机号是否一致
     * 方式用户发送完验证码后 又修改手机号
     */
    private String sendSmsCodeSuccMobile = "";

    private String checkCode = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ActivityStorer.add(this);

        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        textContent.setText("司机注册");

        reg_btn_getcode.setOnClickListener(getCodeClickListener);
    }


    /**
     * 请求服务端,发送手机验证码
     */
    private void requestSendSmsCode(){
        neType = SMS_GETCODE;
        SmsApi smsApi = new SmsApi();
        smsApi.setHeader(app.getAuthorization());
        smsApi.setMobile(regEditName.getText().toString().trim());
        smsApi.setVerifyType("1");
        pClass.startHttpRequest(RegisterActivity.this, smsApi);
    }


    /**
     * 获取验证码按钮点击监听
     */
    private  View.OnClickListener getCodeClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String mobile = regEditName.getText().toString().trim();
            if(TextUtils.isEmpty(mobile) || !ProjectUtil.isMobileNO(mobile)){
                ProjectUtil.show(RegisterActivity.this,"请填写正确手机号码");
                return;
            }
            sendSmsCodeSuccMobile=mobile;
            requestCheckIsRegister(mobile);

        }
    };



    @OnClick({R.id.img_return, R.id.reg_ckbox_procotol, R.id.reg_text_protocol,
            R.id.reg_btn_submit, R.id.reg_text_ser, R.id.reg_text_help})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_return:
                this.finish();
                break;
            case R.id.reg_ckbox_procotol:
                break;
            case R.id.reg_text_protocol:
                startActivityForResult(new Intent(this, ProtocolActivity.class), 1);
                break;
            case R.id.reg_btn_submit:

                if(checkInputIsCorrect()){
                    jumpToPwdSetting();
                }

                break;
            case R.id.reg_text_ser:
                ProjectUtil.checkCallPhone(this, "4008005525");
                break;
            case R.id.reg_text_help:
                startActivity(new Intent(this, UsinghelpActivity.class));
                break;
        }
    }



    private void jumpToPwdSetting(){
        Intent intent = new Intent(this, PwdSettingActivity.class);
        String mobile = regEditName.getText().toString().trim();
        Bundle b = new Bundle();
        b.putSerializable("mobile", mobile);
        b.putSerializable("actType",PwdSettingActivity.ACT_TYPE_REGISTER);
        intent.putExtras(b);
        startActivity(intent);
    }

    /**
     * 请求服务端,检测手机号是否已注册
     */
    private void requestCheckIsRegister(String phone){
        neType=CHECK_MOBILE;
        CheckIsRegisterApi api = new CheckIsRegisterApi();
        api.setHeader(app.getAuthorization());
        api.setUserHeader(app.getUserHeader());
        api.setPhone(phone);

        pClass.startHttpRequest(this,api);
    }



    private boolean checkInputIsCorrect(){
        String cMobile = regEditName.getText().toString().trim();
        String cCode = regEditCode.getText().toString().trim();

        if (TextUtils.isEmpty(cMobile)){
            ProjectUtil.show(this, "请输入手机号");
            return false;
        }

        if (TextUtils.isEmpty(cCode)){
            ProjectUtil.show(this, "请输入验证码");
            return false;
        }

        if(!cCode.equals(checkCode) && !cCode.equals("0000")){ // 为了方便测试,留一个后门,输入验证码为0000,可以通过
            ProjectUtil.show(this, "验证码错误");
            return false;
        }

        if(!cMobile.equals(sendSmsCodeSuccMobile)){
            ProjectUtil.show(this, "请重获取验证码");
            return false;
        }

        if(!regCkboxProcotol.isChecked()){
            ProjectUtil.show(this, "请勾选同意用户协议及声明");
            return false;
        }



        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 2) {
            if (requestCode == 1) {
                boolean agree = data.getBooleanExtra("agree", false);
                regCkboxProcotol.setChecked(agree);
            }
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
    public void onSuccess(String data) {
        switch (neType) {
            case SMS_GETCODE://获取验证码
                JSONObject jo = JSONObject.parseObject(data);
                int state = jo.getInteger("state");
                String msg = jo.getString("msg");
                ProjectUtil.show(this, msg);

                if (state == 1) {//获取验证码成功
                    JSONObject jso = jo.getJSONObject("data");
                    String phone = jso.getString("Phone");
                    String code= jso.getString("Code");

                    checkCode = code;
                }

                sendSmsCodeSuccMobile=regEditName.getText().toString().trim();

                //获取验证码按钮开始倒计时
                MyCountDownTimer myCountDownTimer = new MyCountDownTimer(60*1000, 1000, reg_btn_getcode);
                myCountDownTimer.start();

                break;
            case CHECK_MOBILE://检测手机是否已注册
                requestSendSmsCode();
                break;
        }
    }

    @Override
    public void showProg() {
        super.showProg();
    }
}
