package com.lulian.driver.pay;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lulian.driver.R;
import com.lulian.driver.base.BaseActivity;
import com.lulian.driver.entity.api.WxpayApi;
import com.lulian.driver.pay.wxpay.Constants;
import com.lulian.driver.utils.GlobalValue;
import com.lulian.driver.utils.ProjectUtil;
import com.lulian.driver.wxapi.MoneyTextWatcher;
import com.lulian.driver.wxapi.WxPayManager;
import com.rxretrofitlibrary.retrofit_rx.Api.BaseApi;
import com.rxretrofitlibrary.retrofit_rx.exception.ApiException;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Retrofit;
import rx.Observable;

/**
 * 钱包充值界面
 * @author hxb
 */
public class WalletChargeActivity extends BaseActivity {

    @BindView(R.id.text_content)
    TextView tvTitle;

    @BindView(R.id.et_amount)
    EditText etAmount; //金额输入框

    @BindViews({R.id.rb_wx_pay,R.id.rb_ali_pay})
    RadioButton[] payWayRadios;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_charge);
        ButterKnife.bind(this);
        initViews();
    }


    private void initViews(){
        tvTitle.setText("钱包充值");
        etAmount.addTextChangedListener(new MoneyTextWatcher(etAmount).setDigits(1));

        for(RadioButton rb: payWayRadios){
            rb.setOnClickListener(radioClickListener);
        }
    }


    @OnClick({R.id.img_return,R.id.btn_next_step})
    public void onViewClicked(View v){
        switch (v.getId()){
            case R.id.img_return:
                finish();
                break;
            case R.id.btn_next_step://点击下一步
                if(checkInputIsCorrect()){
                    performClickBtnNextStep();
                }

                break;
        }
    }



    private void performClickBtnNextStep(){
        switch(currSelectedRadioId){
            case R.id.rb_wx_pay: //微信支付
                requestWxPrePay();
                break;
            case R.id.rb_ali_pay://支付宝

                break;

        }
    }


    /**
     * 请求服务端微信预支付
     */
    private void requestWxPrePay(){

        neType=0;
        WxpayApi api = new WxpayApi();
        api.setHeader(app.getAuthorization());
        api.setUserHeader(app.getUserHeader());
        api.setUserId(app.getUserId());
        api.setAppId(Constants.APP_ID);
        String amount = etAmount.getText().toString().trim();
        api.setFeeMoney(Integer.valueOf(amount));

        pClass.startHttpRequest(this, api, "http://121.199.48.242:12356/");
    }

    //记录当前选择的支付方式RadioButton的id
    private int currSelectedRadioId=-1;

    /**
     * 支付方式RadioButton点击监听
     */
    private View.OnClickListener radioClickListener=new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            currSelectedRadioId=v.getId();
            for(RadioButton rb: payWayRadios){
                if(currSelectedRadioId!=rb.getId()){
                    rb.setChecked(false);
                }
            }
        }
    };



    @Override
    public void onSuccess(String data) {
        switch (neType){
            case 0: //微信预支付接口调用成功
                WxPrePayInfo wxPrePayInfo = new Gson().fromJson(data, WxPrePayInfo.class);
                WxPayManager wxPayMgr = new WxPayManager(this);
                wxPayMgr.reqPay(wxPrePayInfo);

                GlobalValue.BASEURL = "http://192.168.1.242:12356/";
                finish();
                break;
            case 1:

                break;
        }
    }




    @Override
    public void onError(ApiException e) {
        ProjectUtil.show(this,e.getDisplayMessage());
    }


    private void performAliPay(){

    }


    /**
     * 校验输入的信息是否正确
     * @return
     */
    private boolean checkInputIsCorrect(){
        String amount = etAmount.getText().toString().trim();
        if(!amount.isEmpty()){
            double dAmount = Double.valueOf(amount);
            if(dAmount<=0){
                ProjectUtil.show(this,"输入正确的金额");
                return false;
            }
        }else{
            ProjectUtil.show(this,"输入正确的金额");
            return false;
        }

        if(currSelectedRadioId==-1){
            ProjectUtil.show(this,"请选择支付方式");
            return false;
        }

        return true;

    }


}
