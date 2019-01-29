package com.lulian.driver.view.activity.caractivity;

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
 * Created by MARK on 2018/6/8.
 */

public class UsinghelpActivity extends BaseActivity {


    @BindView(R.id.img_return)
    ImageView imgReturn;
    @BindView(R.id.text_content)
    TextView textContent;
    @BindView(R.id.title_txt_rightbottom)
    TextView titleTxtRightbottom;
    @BindView(R.id.title_txtrightbottomleft)
    TextView titleTxtrightbottomleft;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usinghelp);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        textContent.setText("使用帮助");
    }


    @OnClick({R.id.img_return, R.id.title_txt_rightbottom})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_return:
                this.finish();
                break;
            case R.id.title_txt_rightbottom:
                break;
        }
    }
}
