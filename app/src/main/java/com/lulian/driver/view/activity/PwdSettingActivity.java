package com.lulian.driver.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lulian.driver.R;
import com.lulian.driver.base.BaseActivity;
import com.lulian.driver.entity.api.RegisterAccApi;
import com.lulian.driver.entity.server.resulte.UserInfo;
import com.lulian.driver.utils.ActivityStorer;
import com.lulian.driver.utils.ProjectUtil;
import com.lulian.driver.utils.SharedPreferencesUtils;
import com.lulian.driver.utils.feature.CheckUtil;
import com.lulian.driver.view.activity.caractivity.UsinghelpActivity;
import com.lulian.driver.view.wheel.ClearEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by MARK on 2018/6/8.
 * 设置密码界面
 */

public class PwdSettingActivity extends BaseActivity {

    @BindView(R.id.img_return)
    ImageView imgReturn;
    @BindView(R.id.text_content)
    TextView textContent;
    @BindView(R.id.activity_main)
    RelativeLayout activityMain;

    @BindView(R.id.tv_mobile)
    TextView tvMobile; //手机号

    @BindView(R.id.pwd_edit_name)
    ClearEditText pwdEditName; //输入密码
    @BindView(R.id.pwd_edit_name_agin)
    ClearEditText pwdEditNameAgin; //确认密码

    /**
     * 定义操作类型常量
     */
    public static final int ACT_TYPE_REGISTER=1; //注册
    public static final int ACT_TYPE_FORGET_PWD=2; //忘记密码

    /**
     * 用来标识到当前界面是做哪种操作类型
     */
    private int actType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pwdsetting);
        ButterKnife.bind(this);

        ActivityStorer.add(this);

        initData();
        initViews();

    }


    private void initData(){
        Bundle b = getIntent().getExtras();
        actType = b.getInt("actType",0);
    }

    private void initViews(){
        textContent.setText("密码设置");
        Intent bundle = getIntent();
        tvMobile.setText(bundle.getStringExtra("mobile"));
    }


    @Override
    public void onSuccess(String data) {
        if (neType == 0) {//注册成功

            ProjectUtil.show(this,"注册成功");

            UserInfo userInfo=new Gson().fromJson(data, UserInfo.class);
            app.setUserId(userInfo.getUserId());
            app.setUserName(userInfo.getName());
            app.setUserPhone(userInfo.getPhone());

            SharedPreferencesUtils.setParam(this, "name", userInfo.getName());
            String pwd = pwdEditName.getText().toString().trim();
            SharedPreferencesUtils.setParam(this, "pwd", pwd);

            //转到完善信息界面
            startActivity(new Intent(this, PersonalInfoFillActivity.class));

        }
    }


    @OnClick({R.id.img_return, R.id.pwd_btn_next, R.id.pwd_text_ser, R.id.pwd_text_help})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_return:
                this.finish();
                break;
            case R.id.pwd_btn_next://点击下一步
                if (checkInputIsCorrect()) {
                    if(actType==ACT_TYPE_REGISTER){

                        requestRegister();

                    }else if(actType==ACT_TYPE_FORGET_PWD){

                    }
                }

                break;
            case R.id.pwd_text_ser:
                ProjectUtil.checkCallPhone(this, "4008005525");
                break;
            case R.id.pwd_text_help:
                startActivity(new Intent(this, UsinghelpActivity.class));
                break;
        }
    }



    private boolean checkInputIsCorrect(){
        String pwd = pwdEditName.getText().toString().trim();
        String pwdAgain = pwdEditNameAgin.getText().toString().trim();

        String checkPwdMsg = CheckUtil.checkPwdIsCorrect(pwd);
        if(checkPwdMsg!=null){
            ProjectUtil.show(this,checkPwdMsg);
            return false;
        }


        if(!pwdAgain.equals(pwd)){
            ProjectUtil.show(this,"两次密码输入不一致");
            return false;
        }

        return true;

    }

    /**
     * 请求服务端,进行注册
     */
    private void requestRegister(){
        String mobile = tvMobile.getText().toString().trim();
        String pwd = pwdEditName.getText().toString().trim();

        neType=0;
        RegisterAccApi registerAccApi = new RegisterAccApi();
        registerAccApi.setHeader(app.getAuthorization());
        registerAccApi.setMobile(mobile);
        registerAccApi.setPwd(pwd);
        registerAccApi.setNewPassword("");
        pClass.startHttpRequest(this, registerAccApi);
    }


}
