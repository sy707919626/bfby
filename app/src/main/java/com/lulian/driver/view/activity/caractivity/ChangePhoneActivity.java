package com.lulian.driver.view.activity.caractivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lulian.driver.R;
import com.lulian.driver.base.BaseActivity;
import com.lulian.driver.view.wheel.ClearEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by MARK on 2018/6/8.
 */

public class ChangePhoneActivity extends BaseActivity {


    @BindView(R.id.img_return)
    ImageView imgReturn;
    @BindView(R.id.text_content)
    TextView textContent;
    @BindView(R.id.reg_edit_name)
    ClearEditText regEditName;
    @BindView(R.id.reg_edit_code)
    ClearEditText regEditCode;
    @BindView(R.id.reg_btn_getcode)
    Button regBtnGetcode;
    @BindView(R.id.reg_ckbox_procotol)
    CheckBox regCkboxProcotol;
    @BindView(R.id.register_ll_check)
    LinearLayout registerLlCheck;
    @BindView(R.id.reg_btn_submit)
    Button regBtnSubmit;
    @BindView(R.id.register_ll_help)
    RelativeLayout registerLlHelp;
    @BindView(R.id.register_ll_msg)
    LinearLayout registerLlMsg;
    @BindView(R.id.register_txt_clickhere)
    TextView registerTxtClickhere;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        textContent.setText("修改号码");
        registerLlCheck.setVisibility(View.GONE);
        registerLlHelp.setVisibility(View.GONE);
        registerLlMsg.setVisibility(View.VISIBLE);
    }


    @OnClick({R.id.img_return, R.id.register_txt_clickhere})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_return:
                this.finish();
                break;
            case R.id.register_txt_clickhere:
                startActivity(new Intent(this,ChangePhoneForIdCardActivity.class));

                break;
        }
    }
}
