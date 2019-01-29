package com.lulian.driver.view.activity.caractivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lulian.driver.R;
import com.lulian.driver.base.BaseActivity;
import com.lulian.driver.utils.BankCard;
import com.lulian.driver.utils.ProjectUtil;
import com.lulian.driver.view.wheel.ClearEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/8/23.
 */

public class AddBankActivity extends BaseActivity {

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
    @BindView(R.id.add_bank_chikaren)
    ClearEditText addBankChikaren;
    @BindView(R.id.add_bank_kahao)
    ClearEditText addBankKahao;
    @BindView(R.id.add_banks_next)
    Button addBanksNext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initData() {

    }

    private void initView() {
        textContent.setText("添加银行卡");
    }

    @OnClick({R.id.img_return, R.id.add_banks_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_return:
                this.finish();
                break;

            case R.id.add_banks_next:
                String bankcard = addBankKahao.getText().toString().trim();
                String bankNames = addBankChikaren.getText().toString().trim();

                if (bankNames.equals("")) {
                    ProjectUtil.show(this, "持卡人不能为空");
                } else if (bankcard.equals("")) {
                    ProjectUtil.show(this, "银行卡号不能为空");
                } else if (!BankCard.checkBankCard(bankcard)) {
                    ProjectUtil.show(this, "银行卡号不正确，请检查后重新输入");
                } else {
                    Intent intent = new Intent(this, VerificationBankCardActivity.class);
                    intent.putExtra("bankCard", bankcard);
                    startActivity(intent);
                    finish();
                }

                break;
        }
    }
}
