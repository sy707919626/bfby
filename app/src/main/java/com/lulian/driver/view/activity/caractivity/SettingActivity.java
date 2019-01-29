package com.lulian.driver.view.activity.caractivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lulian.driver.R;
import com.lulian.driver.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by MARK on 2018/6/8.
 */

public class SettingActivity extends BaseActivity {


    @BindView(R.id.img_return)
    ImageView imgReturn;
    @BindView(R.id.text_content)
    TextView textContent;
    @BindView(R.id.me_layout_changepwd)
    RelativeLayout meLayoutChangepwd;
    @BindView(R.id.me_layout_clearrubbish)
    RelativeLayout meLayoutClearrubbish;
    @BindView(R.id.me_layout_checkupdate)
    RelativeLayout meLayoutCheckupdate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
    }

    @OnClick({R.id.img_return, R.id.me_layout_changepwd, R.id.me_layout_clearrubbish, R.id.me_layout_checkupdate})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_return:
                this.finish();
                break;
            case R.id.me_layout_changepwd:
                startActivity(new Intent(this,ChangePwdActivity.class));
                break;
            case R.id.me_layout_clearrubbish:
                break;
            case R.id.me_layout_checkupdate:
                break;
        }
    }
}
