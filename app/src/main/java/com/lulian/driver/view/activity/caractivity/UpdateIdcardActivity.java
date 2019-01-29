package com.lulian.driver.view.activity.caractivity;

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
import com.lulian.driver.base.ACache;
import com.lulian.driver.base.BaseActivity;
import com.lulian.driver.base.CodeUtils;
import com.lulian.driver.entity.api.PostMydatumApi;
import com.lulian.driver.entity.server.resulte.MyDatum;
import com.lulian.driver.utils.IDCard;
import com.lulian.driver.utils.ProjectUtil;
import com.lulian.driver.view.wheel.ClearEditText;

import java.text.ParseException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/8/17.
 */

public class UpdateIdcardActivity extends BaseActivity {
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
    @BindView(R.id.reg_edit_idcard)
    ClearEditText regEditIdcard;
    @BindView(R.id.reg_edit_imagecode)
    ClearEditText regEditImagecode;
    @BindView(R.id.reg_btn_gettxcode)
    ImageView regBtnGettxcode;
    @BindView(R.id.reg_btn_next)
    Button regBtnNext;

    private CodeUtils codeUtils;
    private MyDatum myDatum;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updateidcard);
        ButterKnife.bind(this);
        codeUtils = CodeUtils.getInstance();
        initView();
    }

    private void initView() {
        textContent.setText("修改号码");
        regBtnGettxcode.setImageBitmap(codeUtils.createBitmap());

        regBtnGettxcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regBtnGettxcode.setImageBitmap(codeUtils.createBitmap());
            }
        });
    }


    @OnClick({R.id.img_return, R.id.reg_btn_next, R.id.reg_edit_idcard})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_return:
                this.finish();
                break;

            case R.id.reg_btn_next:
                String idCard = regEditIdcard.getText().toString().trim();
                String cCode =  regEditImagecode.getText().toString().trim();

                if (idCard.equals("")){
                    ProjectUtil.show(this, "请输入身份证号码");
                }else {
                    try {
                        ProjectUtil.show(this, IDCard.IDCardValidate(idCard));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                if (cCode.equals("")){
                    ProjectUtil.show(this, "请输入验证码");
                } else {
                    if (cCode.equals(codeUtils.getCode())) {//验证码正确
                        ACache aCache = ACache.get(this);
                        String data =  aCache.getAsString("data");
                        myDatum = JSONObject.parseObject(data, MyDatum.class);

                        PostMydatumApi postMy = new PostMydatumApi();
                        postMy.setHeader(app.getAuthorization());
                        postMy.setUserHeader(app.getUserHeader());

                        postMy.setName(myDatum.getName());
                        postMy.setIdentityCode(idCard);
                        postMy.setInviteCode(myDatum.getInviteCode());
                        postMy.setPhoneNo1(myDatum.getPhoneNo2());
                        postMy.setPhoneNo2(myDatum.getPhoneNo2());
                        postMy.setPhoneNo3(myDatum.getPhoneNo3());
                        postMy.setHandIdentityUrl(myDatum.getHandIdentityUrl());
                        postMy.setIdentityFrontUrl(myDatum.getIdentityFrontUrl());
                        postMy.setIdentityBackUrl(myDatum.getIdentityBackUrl());
                        postMy.setDrivingLicenseFroneUrl(myDatum.getHandIdentityUrl());
//                        postMy.setFiles(myDatum.getFiles());
                        pClass.startHttpRequest(this, postMy);

                    } else {
                        ProjectUtil.show(this, "验证码错误");
                        return;
                    }
                }
                break;

            case R.id.reg_text_click:

                break;
        }
    }

    @Override
    public void onSuccess(String data) {
        super.onSuccess(data);
    }
}
