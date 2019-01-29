package com.lulian.driver.view.activity.caractivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.lulian.driver.R;
import com.lulian.driver.base.BaseActivity;
import com.lulian.driver.entity.api.SmsApi;
import com.lulian.driver.utils.ProjectUtil;
import com.lulian.driver.view.wheel.ClearEditText;
import com.lulian.driver.view.wheel.MyCountDownTimer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/8/16.
 */

public class UpdatePhoneActivity extends BaseActivity {

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
    @BindView(R.id.title_bar_tishi)
    LinearLayout titleBarTishi;
    @BindView(R.id.reg_edit_phone)
    ClearEditText regEditPhone;
    @BindView(R.id.reg_edit_code)
    ClearEditText regEditCode;
    @BindView(R.id.reg_btn_getcode)
    Button regBtnGetcode;
    @BindView(R.id.reg_btn_next)
    Button regBtnNext;
    @BindView(R.id.reg_text_click)
    TextView regTextClick;
    @BindView(R.id.update_phone_check)
    LinearLayout updatePhoneCheck;
    String oldPhone;
    String Code = "";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatephone);
        ButterKnife.bind(this);

        oldPhone = getIntent().getStringExtra("oldPhone");
        initView();
    }

    private void initView() {
        textContent.setText("修改号码");
        regEditPhone.setText(oldPhone);
        regEditPhone.setEnabled(false);

        final MyCountDownTimer myCountDownTimer = new MyCountDownTimer(60000,1000,regBtnGetcode);
        regBtnGetcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ProjectUtil.isMobileNO(oldPhone)) {
                    SmsApi smsApi = new SmsApi();
                    smsApi.setHeader(app.getAuthorization());
                    smsApi.setMobile(oldPhone);
                    smsApi.setVerifyType("4"); //1.用户注册 2.重置密码 3.重置支付密码 4.更新联系方式
                    pClass.startHttpRequest(UpdatePhoneActivity.this, smsApi);
                    myCountDownTimer.start();
                }
            }
        });
    }

    @Override
    public void onSuccess(String data) {
        JSONObject jsonObject = JSONObject.parseObject(data);
        String state = jsonObject.getString("state");
        String msg = jsonObject.getString("msg");

        jsonObject = jsonObject.getJSONObject("data");
        if(state.equals("0")){
            ProjectUtil.show(this, msg);

        }else if(state.equals("1")){
            Code = jsonObject.getString("Code");
            Log.e("Code  : " , Code);
        }
    }

    @OnClick({R.id.img_return, R.id.reg_btn_next, R.id.reg_text_click})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_return:
                this.finish();
                break;

            case R.id.reg_btn_next:
                String cCode= regEditCode.getText().toString().trim();

//                if (cCode.equals("")){
//                    ProjectUtil.show(this, "请输入验证码");
//                    return;
//                } else {
//
//                    if (cCode.equals(Code)) {//验证码正确
//                        Intent intent = new Intent(this, UpdatePhone2Activity.class);
//                        startActivity(intent);
//                    } else {
//                        ProjectUtil.show(this, "验证码错误");
//                        return;
//                    }
//                }

                Intent intent = new Intent(this, UpdatePhone2Activity.class);
                startActivity(intent);
                finish();
                break;

            case R.id.reg_text_click:

                break;
        }
    }
}
