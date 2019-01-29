package com.lulian.driver.pay;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lulian.driver.R;
import com.lulian.driver.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 *
 * 钱包充值 结果显示界面
 * @author hxb
 */
public class WalletChargeResultActivity extends BaseActivity {

    @BindView(R.id.text_content)
    TextView tvTitle;

    @BindView(R.id.iv_result_tip)
    ImageView ivTip;

    @BindView(R.id.tv_result_tip)
    TextView tvTip;

    @BindView(R.id.tv_remaining_sum)
    TextView tvRemainingSumTip;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_charge_result);
        ButterKnife.bind(this);
        initViews();
    }


    private void initViews(){
        findViewById(R.id.img_return).setVisibility(View.GONE);

        Intent it=getIntent();
        boolean isSuccess = it.getBooleanExtra("isSuccess", false);//是否充值成功
        String remainingSum = it.getStringExtra("RemainingSum");//余额

        if(isSuccess){
            tvTitle.setText("充值成功");
            ivTip.setImageResource(R.drawable.big_success);
            tvTip.setText("充值成功");
        }else{
            tvTitle.setText("充值失败");
            ivTip.setImageResource(R.drawable.big_fail);
            tvTip.setText("充值失败");
        }

        String remainingSumTip = "您当前的账户余额:" + remainingSum + "元";
        tvRemainingSumTip.setText(remainingSumTip);

    }


    @OnClick({R.id.btn_confirm})
    public void onViewClicked(View v){
        finish();
    }



}
