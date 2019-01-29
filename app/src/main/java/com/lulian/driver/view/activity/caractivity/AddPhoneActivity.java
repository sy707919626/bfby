package com.lulian.driver.view.activity.caractivity;

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
import com.lulian.driver.base.ACache;
import com.lulian.driver.base.BaseActivity;
import com.lulian.driver.entity.api.PostMydatumApi;
import com.lulian.driver.entity.api.SmsApi;
import com.lulian.driver.entity.server.resulte.MyDatum;
import com.lulian.driver.utils.ProjectUtil;
import com.lulian.driver.view.wheel.ClearEditText;
import com.lulian.driver.view.wheel.MyCountDownTimer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/8/16.
 */

public class AddPhoneActivity extends BaseActivity {
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
    @BindView(R.id.reg_edit_name)
    ClearEditText regEditName;
    @BindView(R.id.reg_edit_code)
    ClearEditText regEditCode;
    @BindView(R.id.reg_btn_getcode)
    Button regBtnGetcode;
    @BindView(R.id.reg_btn_submit)
    Button regBtnSubmit;

    private int neType;
    String tempMobile="";
    String Code = "";

    private MyDatum myDatum;
    private int netype = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addphone);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        textContent.setText("添加新联系方式");
        final MyCountDownTimer myCountDownTimer = new MyCountDownTimer(60000,1000,regBtnGetcode);
        regBtnGetcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tempMobile = regEditName.getText().toString().trim();
                if(tempMobile.equals("")){
                    ProjectUtil.show(AddPhoneActivity.this,"请输入手机号码");
                }else if(ProjectUtil.isMobileNO(tempMobile)) {
                    netype = 1;
                    SmsApi smsApi = new SmsApi();
                    smsApi.setHeader(app.getAuthorization());
                    smsApi.setMobile(tempMobile);
                    smsApi.setVerifyType("4"); //1.用户注册 2.重置密码 3.重置支付密码 4.更新联系方式
                    pClass.startHttpRequest(AddPhoneActivity.this, smsApi);
                    myCountDownTimer.start();
                }else{
                    ProjectUtil.show(AddPhoneActivity.this,"请输入正确的手机号码");
                }
            }
        });

    }

    @Override
    public void onSuccess(String data) {
        if (netype == 1) {
            JSONObject jsonObject = JSONObject.parseObject(data);
            String state = jsonObject.getString("state");
            String msg = jsonObject.getString("msg");

            jsonObject = jsonObject.getJSONObject("data");
            if (state.equals("0")) {
                ProjectUtil.show(this, msg);

            } else if (state.equals("1")) {
                Code = jsonObject.getString("Code");
                Log.e("Code  : ", Code);
            }
        } else if (netype == 2){
            finish();
        }
    }

    @OnClick({R.id.img_return, R.id.reg_btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
                case R.id.img_return:
                    this.finish();
                    break;

            case R.id.reg_btn_submit:
                String cMobile=regEditName.getText().toString().trim();
                String cCode=regEditCode.getText().toString().trim();
//                if(cMobile.equals("")){
//                    ProjectUtil.show(this,"请输入手机号");
//                } else {
//                    if(cCode.equals(Code)){//验证码正确
//                       PersonalDataActivity.start(this, cMobile, 1);
//                    }
//                }
                ACache aCache = ACache.get(this);
                String data =  aCache.getAsString("data");
                myDatum = JSONObject.parseObject(data, MyDatum.class);

                netype = 2;
                PostMydatumApi postMy = new PostMydatumApi();
                postMy.setHeader(app.getAuthorization());
                postMy.setUserHeader(app.getUserHeader());

                postMy.setName(myDatum.getName());
                postMy.setIdentityCode(myDatum.getIdentityCode());
                postMy.setInviteCode(myDatum.getInviteCode());

                if (myDatum.getPhoneNo1().equals("")){
                    postMy.setPhoneNo1(cMobile);
                    postMy.setPhoneNo2("");
                    postMy.setPhoneNo3("");
                } else if (myDatum.getPhoneNo2().equals("")){
                    postMy.setPhoneNo1(myDatum.getPhoneNo1());
                    postMy.setPhoneNo2(cMobile);
                    postMy.setPhoneNo3("");
                } else if (myDatum.getPhoneNo3().equals("")){
                    postMy.setPhoneNo1(myDatum.getPhoneNo1());
                    postMy.setPhoneNo2(myDatum.getPhoneNo2());
                    postMy.setPhoneNo3(cMobile);
                }
                postMy.setHandIdentityUrl(myDatum.getHandIdentityUrl());
                postMy.setIdentityFrontUrl(myDatum.getIdentityFrontUrl());
                postMy.setIdentityBackUrl(myDatum.getIdentityBackUrl());
                postMy.setDrivingLicenseFroneUrl(myDatum.getHandIdentityUrl());

//                postMy.setFiles(myDatum.getFiles());
                pClass.startHttpRequest(this, postMy);

                finish();
                break;

        }
    }

}
