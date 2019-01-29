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

public class ChangePhoneForIdCardActivity extends BaseActivity {


    @BindView(R.id.img_return)
    ImageView imgReturn;
    @BindView(R.id.text_content)
    TextView textContent;
    @BindView(R.id.idcarecp_btn)
    Button idcarecpBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idcardchangephone);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        textContent.setText("修改号码");
    }

    @OnClick({R.id.img_return, R.id.idcarecp_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_return:
                this.finish();
                break;
            case R.id.idcarecp_btn:
                startActivity(new Intent(this,ChangePhoneActivity.class));
                break;
        }
    }
}
