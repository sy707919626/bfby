package com.lulian.driver.view.activity.caractivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lulian.driver.R;
import com.lulian.driver.base.BaseActivity;
import com.lulian.driver.pay.WalletChargeActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by MARK on 2018/6/8.
 */

public class MyWalletActivity extends BaseActivity {
    @BindView(R.id.img_return)
    ImageView imgReturn;
    @BindView(R.id.mywallet_textView)
    TextView mywalletTextView;
    @BindView(R.id.mywallet_txt_right)
    TextView mywalletTxtRight;
    @BindView(R.id.mywallet_layout_right)
    LinearLayout mywalletLayoutRight;
    @BindView(R.id.my_balance)
    TextView myBalance;
    @BindView(R.id.wallet_ll_paypwd)
    RelativeLayout walletLlPaypwd;
    @BindView(R.id.my_bank_card)
    RelativeLayout myBankCard;
    @BindView(R.id.my_sfk)
    RelativeLayout mySfk;
    @BindView(R.id.my_recharge)
    RelativeLayout myRecharge;
    @BindView(R.id.my_tixian)
    RelativeLayout myTixian;
    @BindView(R.id.purse_detail)
    RelativeLayout purseDetail;

    private int money;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mywallet);
        ButterKnife.bind(this);
        money = getIntent().getIntExtra("money", 0);
        initView();
    }

    private void initView() {
        mywalletTextView.setText("我的钱包");
        myBalance.setText(String.valueOf(money));
    }


    @OnClick({R.id.img_return, R.id.wallet_ll_paypwd,R.id.my_bank_card,
            R.id.my_sfk,R.id.my_recharge, R.id.my_tixian, R.id.purse_detail})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_return:
                this.finish();
                break;
            case R.id.wallet_ll_paypwd:  //修改支付密码
                startActivity(new Intent(this, ChangePaypwdActivity.class));
                break;

            case R.id.my_bank_card:  //银行卡
                startActivity(new Intent(this, AddBankListActivity.class));
                break;

            case R.id.my_sfk: //收付款
                break;

            case R.id.my_recharge: //充值
                Intent intent = new Intent(this, WalletChargeActivity.class);
                startActivity(intent);
                break;

            case R.id.my_tixian: //提现
                break;

            case R.id.purse_detail: //钱包明细
                startActivity(new Intent(this, MyWalletDetailActivity.class));
                break;
        }
    }
}
