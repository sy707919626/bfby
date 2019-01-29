package com.lulian.driver.view.activity.caractivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.alibaba.fastjson.JSONObject;
import com.lulian.driver.R;
import com.lulian.driver.adapter.OrderDetailAdapter;
import com.lulian.driver.base.BaseActivity;
import com.lulian.driver.entity.api.GetOrderReturnHistory;
import com.lulian.driver.entity.api.ReturnOrderApi;
import com.lulian.driver.entity.server.resulte.OrderReturnHistory;
import com.lulian.driver.utils.ProjectUtil;
import com.lulian.driver.view.wheel.ClearEditText;
import com.rxretrofitlibrary.retrofit_rx.exception.ApiException;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.alibaba.fastjson.JSON.parseArray;


/**
 * Created by MARK on 2018/6/8.
 */

public class ChargebackDialogActivity extends BaseActivity {

    @BindView(R.id.charge_img_delete)
    ImageView chargeImgDelete;
    @BindView(R.id.charge_edit_rests)
    ClearEditText chargeEditRests;
    @BindView(R.id.charge_btn_submit)
    Button chargeBtnSubmit;
    @BindView(R.id.charge_list)
    ListView chargeList;

    private String orderId;
    private int neType = 0;
    private List<OrderReturnHistory> backList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_chargeback);
        Intent intent = getIntent();
        orderId = intent.getStringExtra("orderid");
        ButterKnife.bind(this);
        getData();
        initView();
    }

    private void getData() {
        neType = 0;
        GetOrderReturnHistory gro  = new GetOrderReturnHistory();
        gro.setOrderId(orderId);
        gro.setHeader(app.getAuthorization());
        gro.setUserHeader(app.getUserHeader());
        pClass.startHttpRequest(this, gro);
    }

    private void initView() {
    }

    @Override
    public void onSuccess(String data) {
        if (neType == 0) {//其他人退单记录
            backList = parseArray(data, OrderReturnHistory.class);
            chargeList.setAdapter(new OrderDetailAdapter(this, backList));
            ProjectUtil.setListViewHeightBasedOnChildren(chargeList);
        } else if (neType == 1) {
            JSONObject jsonObject = JSONObject.parseObject(data);
            int state = jsonObject.getIntValue("state");
            String msg = jsonObject.getString("msg");
            ProjectUtil.show(this, "退单:" + msg);
            this.finish();
        }
    }

    @Override
    public void onError(ApiException e) {
        super.onError(e);
    }

    @OnClick({R.id.charge_img_delete, R.id.charge_btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.charge_img_delete:
                this.finish();
                break;
            case R.id.charge_btn_submit:
                String rests = chargeEditRests.getText().toString().trim();//其他原因

                neType = 1;
                ReturnOrderApi roa = new ReturnOrderApi();
                roa.setOrderId(orderId);
                roa.setHeader(app.getAuthorization());
                roa.setUserHeader(app.getUserHeader());
                roa.setConsignorPrice(0);
                roa.setMarketPrice(0);
                roa.setRemark(rests);
                pClass.startHttpRequest(this, roa);

                break;
        }
    }
}
