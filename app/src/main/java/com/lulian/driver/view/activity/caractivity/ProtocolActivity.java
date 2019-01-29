package com.lulian.driver.view.activity.caractivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lulian.driver.R;
import com.lulian.driver.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by MARK on 2018/6/8.
 */

public class ProtocolActivity extends BaseActivity {
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protocol);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        textContent.setText("风险阅读");
        protocolBtnAgree.setVisibility(View.VISIBLE);
    }


    @OnClick({R.id.img_return, R.id.title_txt_rightbottom,
            R.id.protocol_btn_agree})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_return:
                this.finish();
                break;
            case R.id.title_txt_rightbottom:
                break;
            case R.id.protocol_btn_agree:
                Intent intent = new Intent();
                intent.putExtra("agree", true);
                setResult(2, intent);
                finish();
                break;
        }
    }

}
