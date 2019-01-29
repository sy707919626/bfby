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
import com.lulian.driver.entity.api.ResetPwdApi;
import com.lulian.driver.utils.IDCard;
import com.lulian.driver.utils.ProjectUtil;
import com.lulian.driver.view.activity.DriverMainActivity;
import com.lulian.driver.view.wheel.ClearEditText;
import com.rxretrofitlibrary.retrofit_rx.exception.ApiException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by MARK on 2018/6/8.
 */

public class ChangePwdActivity extends BaseActivity {

    @BindView(R.id.img_return)
    ImageView imgReturn;
    @BindView(R.id.text_content)
    TextView textContent;
    @BindView(R.id.title_txt_rightbottom)
    TextView titleTxtRightbottom;
    @BindView(R.id.pwd_text_name)
    TextView pwdTextName;
    @BindView(R.id.pwd_edit_name)
    ClearEditText pwdEditName;
    @BindView(R.id.pwd_edit_name_agin)
    ClearEditText pwdEditNameAgin;
    @BindView(R.id.pwd_btn_next)
    Button pwdBtnNext;
    @BindView(R.id.pwd_text_ser)
    TextView pwdTextSer;
    @BindView(R.id.pwd_text_help)
    TextView pwdTextHelp;
    @BindView(R.id.pwdsetting_layout_callandhelp)
    RelativeLayout pwdsettingLayoutCallandhelp;
    @BindView(R.id.oldpwd_edit_name)
    ClearEditText oldpwdEditName;
    @BindView(R.id.oldpwd_layout)
    LinearLayout oldpwdLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pwd);
        Intent bundle = getIntent();
        ButterKnife.bind(this);
        pwdTextName.setText(bundle.getStringExtra("pwdTextName"));
        initView();
    }

    private void initView() {
        textContent.setText("设置新密码");
        pwdBtnNext.setText("完成");
        pwdsettingLayoutCallandhelp.setVisibility(View.GONE);
        oldpwdLayout.setVisibility(View.VISIBLE);
    }


    @Override
    public void onSuccess(String data) {
        JSONObject jsonObject = JSONObject.parseObject(data);
        String state = jsonObject.getString("state");
        String msg = jsonObject.getString("msg");
        if (jsonObject.containsKey("data")) {
            JSONObject dataJson = jsonObject.getJSONObject("data");
            app.setUserId(dataJson.getString("UserId"));
        }
        ProjectUtil.show(this, state + msg);
        if (state.equals("1")) {//修改成功
            startActivity(new Intent(this, DriverMainActivity.class));
            finish();
        } else {
            ProjectUtil.show(this, "修改失败!");
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


    @OnClick({R.id.img_return, R.id.pwd_btn_next, R.id.pwdsetting_layout_callandhelp})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_return:
                this.finish();
                break;
            case R.id.pwd_btn_next:
//                this.finish();
                String pwd = pwdEditName.getText().toString().trim();
                String pwdAgain = pwdEditNameAgin.getText().toString().trim();
                String oldpwd = oldpwdEditName.getText().toString().trim();
                if (oldpwd.equals("")) {
                    ProjectUtil.show(this, "原始密码不能为空!");
                } else if (pwd.equals("") || pwdAgain.equals("")) {
                    ProjectUtil.show(this, "密码不能为空!");
                } else if (pwd.length() < 8 || pwd.length() > 16) {
                    ProjectUtil.show(this, "请输入8到16位的密码!");
                } else if (IDCard.isNumeric(pwd) || IDCard.isLetter(pwd)) {
                    ProjectUtil.show(this, "密码不能为纯数字或纯字母!");
                } else if (pwd.equals(pwdAgain)) {
                    ResetPwdApi resetPwdApi = new ResetPwdApi();
                    resetPwdApi.setHeader(app.getAuthorization());
                    resetPwdApi.setMobile(pwdTextName.getText().toString().trim());
                    resetPwdApi.setPwd(oldpwd);
                    resetPwdApi.setNewPwd(pwd);
                    resetPwdApi.setUserType(2);
                    resetPwdApi.setUserId(app.getUserId());
                    resetPwdApi.setUserName("test");
                    resetPwdApi.setRoleId("1");
                    pClass.startHttpRequest(this, resetPwdApi);

                } else {
                    ProjectUtil.show(this, "密码输入不一致!");
                }
                break;
            case R.id.pwdsetting_layout_callandhelp:
                break;
        }
    }
}
