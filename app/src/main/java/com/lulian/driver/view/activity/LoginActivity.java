package com.lulian.driver.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.reflect.TypeToken;
import com.lulian.driver.R;
import com.lulian.driver.base.BaseActivity;
import com.lulian.driver.entity.api.GetVehicleLengthListApi;
import com.lulian.driver.entity.api.GetVehicleTypeListApi;
import com.lulian.driver.entity.api.LoginAccApi;
import com.lulian.driver.entity.server.resulte.CarType;
import com.lulian.driver.utils.ProjectUtil;
import com.lulian.driver.utils.SharedPreferencesUtils;
import com.lulian.driver.view.activity.caractivity.ForgetActivity;
import com.lulian.driver.view.activity.caractivity.UsinghelpActivity;
import com.lulian.driver.view.wheel.ClearEditText;
import com.rxretrofitlibrary.retrofit_rx.utils.GsonUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by MARK on 2018/6/8.
 * 登录界面
 */

public class LoginActivity extends BaseActivity {
    @BindView(R.id.edit_name)
    ClearEditText editName;
    @BindView(R.id.edit_pwd)
    ClearEditText editPwd;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.text_forgetpwd)
    TextView textForgetpwd;
    @BindView(R.id.text_register)
    TextView textRegister;
    @BindView(R.id.text_version)
    TextView textVersion;
    @BindView(R.id.login_txt_relation)
    TextView loginTxtRelation;
    @BindView(R.id.login_txt_usinghelp)
    TextView loginTxtUsinghelp;
    @BindView(R.id.ll_login)
    LinearLayout llLogin;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        putVersionName();
    }

    @Override
    protected void onStart() {
        super.onStart();
        putPhotoAndPwdToEt();
    }


    /**
     * 显示版本号
     */
    private void putVersionName(){
        String versionName = ProjectUtil.getVersionName(this);
        textVersion.setText("版本 "+versionName);
    }

    /**
     * 将储存的用户名密码显示到输入框
     */
    private void putPhotoAndPwdToEt(){
        String name = (String) SharedPreferencesUtils.getParam(this, "name", "test");
        String pwd = (String) SharedPreferencesUtils.getParam(this, "pwd", "test");
        if (name.equals("test") && pwd.equals("test")) {
        } else if (name.equals("") || pwd.equals("")) {
        } else {
            editName.setText(name);
            editPwd.setText(pwd);
        }
    }


    @OnClick({R.id.btn_submit, R.id.text_register,
            R.id.text_version, R.id.text_forgetpwd,
            R.id.login_txt_relation, R.id.login_txt_usinghelp})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_submit:

                String phoneNum = editName.getText().toString().trim();
                String pwd = editPwd.getText().toString().trim();

                SharedPreferencesUtils.setParam(this, "name", phoneNum);
                SharedPreferencesUtils.setParam(this, "pwd", pwd);

                if (phoneNum.equals("") || pwd.equals("")) {
                    ProjectUtil.show(this, "请输入用户名或密码!");
                } else {

                    requestLogin();
                }
                break;
            case R.id.text_register:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.text_version:
                break;
            case R.id.text_forgetpwd:
                startActivity(new Intent(this, ForgetActivity.class));
                break;
            case R.id.login_txt_relation:
                ProjectUtil.checkCallPhone(this, "4008005525");
                break;
            case R.id.login_txt_usinghelp:
                startActivity(new Intent(this, UsinghelpActivity.class));
                break;
        }
    }


    private void requestLogin(){
        String phoneNum = editName.getText().toString().trim();
        String pwd = editPwd.getText().toString().trim();

        neType = 1;
        LoginAccApi loginAccApi = new LoginAccApi();
        loginAccApi.setHeader(app.getAuthorization());
        loginAccApi.setMobile(phoneNum);
        loginAccApi.setPwd(pwd);
        loginAccApi.setUserType("2");
        pClass.startHttpRequest(this, loginAccApi);
    }


    @Override
    public void onSuccess(String data) {

        if (neType == 1) {
            JSONObject jsonObject = JSONObject.parseObject(data);
            String state = jsonObject.getString("state");
            String msg = jsonObject.getString("msg");

            if (state.equals("1")) {//登录成功
                if (jsonObject.containsKey("data")) {
                    JSONObject dataJson = jsonObject.getJSONObject("data");
                    app.setUserId(dataJson.getString("UserId"));
                    app.setUserName(dataJson.getString("Phone"));
                    app.setUserType(dataJson.getString("UserType") + "");
                    app.setUserPhone(dataJson.getString("Phone"));
                    app.setIsComplete(dataJson.getString("IsComplete"));

                    //登录成功后,还需要请求车长列表和车型列表数据
                    requestTruckTypeList();

                }

            }else{

                ProjectUtil.show(this,msg);

            }
        }else if (neType==2){

            List<CarType> truckTypeList= GsonUtil.get().fromJson(data,new TypeToken<ArrayList<CarType>>(){}.getType());
            app.setCartypeLsit(truckTypeList);

            requestTruckLenList();

        }else if(neType==3){

            List<String> truckLenList=GsonUtil.get().fromJson(data,new TypeToken<ArrayList<String>>(){}.getType());
            app.setCarLenghtList(truckLenList);

            //跳转到主界面
            startActivity(new Intent(this, DriverMainActivity.class));
            finish();
//            ProjectUtil.show(this,"login success");
        }
    }


    /**
     * 请求车型列表数据
     */
    private void requestTruckTypeList(){
        neType = 2;
        GetVehicleTypeListApi api = new GetVehicleTypeListApi();
        api.setHeader(app.getAuthorization());
        api.setUserHeader(app.getUserHeader());
        pClass.startHttpRequest(this,api);
    }

    /**
     * 请求车长列表数据
     */
    private void requestTruckLenList(){
        neType = 3;
        GetVehicleLengthListApi api = new GetVehicleLengthListApi();
        api.setHeader(app.getAuthorization());
        api.setUserHeader(app.getUserHeader());
        pClass.startHttpRequest(this,api);
    }


}
