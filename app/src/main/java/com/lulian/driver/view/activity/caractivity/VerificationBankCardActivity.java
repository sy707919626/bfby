package com.lulian.driver.view.activity.caractivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lulian.driver.R;
import com.lulian.driver.base.BaseActivity;
import com.lulian.driver.utils.BankCard;
import com.lulian.driver.utils.ProjectUtil;
import com.lulian.driver.view.wheel.ClearEditText;
import com.lulian.driver.view.wheel.MyCountDownTimer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/8/24.
 */

public class VerificationBankCardActivity extends BaseActivity {


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
    @BindView(R.id.verification_bank_card)
    ClearEditText verificationBankCard;
    @BindView(R.id.verification_bank_cardnum)
    ClearEditText verificationBankCardnum;
    @BindView(R.id.verification_phone)
    ClearEditText verificationPhone;
    @BindView(R.id.verification_bank_code)
    ClearEditText verificationBankCode;
    @BindView(R.id.verification_bank_btn_code)
    Button verificationBankBtnCode;
    @BindView(R.id.bank_check_agree)
    CheckBox bankCheckAgree;
    @BindView(R.id.bank_txt_doc)
    TextView bankTxtDoc;
    @BindView(R.id.verification_btn_info)
    Button verificationBtnInfo;

    private String bankCard;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_bankcard);
        ButterKnife.bind(this);
        bankCard = getIntent().getStringExtra("bankCard");
        initView();

    }

    private void initData() {

    }

    private void initView() {
        textContent.setText("验证银行卡信息");
        verificationBankCardnum.setText(bankCard);
        verificationBankCard.setText(BankCard.getDetailNameOfBank(bankCard));
        verificationBankCardnum.setEnabled(false);
        verificationBankCard.setEnabled(false);
    }

    @OnClick({R.id.img_return, R.id.verification_btn_info,
                R.id.verification_bank_btn_code, R.id.bank_txt_doc})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_return:
                this.finish();
                break;

            case R.id.bank_txt_doc:
                startActivityForResult(new Intent(this, ProtocolActivity.class),1);
                break;

            case R.id.verification_bank_btn_code:
                neType = 0;
                final MyCountDownTimer myCountDownTimer = new MyCountDownTimer(60000,1000,verificationBankBtnCode);
                String tempMobile = verificationPhone.getText().toString().trim();

                if(tempMobile.equals("")){
                    ProjectUtil.show(this,"请输入手机号码");
                }else if(ProjectUtil.isMobileNO(tempMobile)) {
//                    SmsApi smsApi = new SmsApi();
//                    smsApi.setHeader(app.getAuthorization());
//                    smsApi.setMobile(tempMobile);
//                    smsApi.setVerifyType("1");
//                    pClass.startHttpRequest(this, smsApi);
                    myCountDownTimer.start();
                }else{
                    ProjectUtil.show(this,"请输入正确的手机号码");
                }

                break;

            case R.id.verification_btn_info:
                startActivity(new Intent(this, AddBankListActivity.class));
                finish();
                break;
        }
    }

    @Override
    public void onSuccess(String data) {
        super.onSuccess(data);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 2) {
            if (requestCode == 1) {
                boolean agree = data.getBooleanExtra("agree",false);
                bankCheckAgree.setChecked(agree);
            }
        }
    }
}
