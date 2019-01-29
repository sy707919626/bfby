package com.lulian.driver.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.lulian.driver.R;
import com.lulian.driver.utils.ProjectUtil;
import com.lulian.driver.utils.ScreenUtil;
import com.lulian.driver.view.wheel.ClearEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 报价填写dialog
 * @author hxb
 */
public class OfferFillDialog extends Dialog {

    @BindView(R.id.cet_transport_fee)
    ClearEditText cetTransportFee;//运费填写框
    @BindView(R.id.cet_service_fee)
    ClearEditText cetServiceFee;//服务费填写框
    @BindView(R.id.tv_total_fee)
    TextView tvTotalFee;//总费用

    private Callback callback;

    public interface Callback{
        void onFillComplete(int transportFee,int serviceFee);
    }

    public OfferFillDialog(@NonNull Context context,int serviceFee) {
        super(context);
        this.serviceFee = serviceFee;
        init();
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    private void init(){
        setContentView(R.layout.dialog_offer_fill);
        ButterKnife.bind(this);

        //设置宽高,位置
        int screenWidth = ScreenUtil.getScreenWidth(getContext());
        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width=screenWidth;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        window.setGravity(Gravity.BOTTOM);

        initView();
    }


    private void initView(){
        cetTransportFee.addTextChangedListener(feeInputListener);
        cetServiceFee.addTextChangedListener(feeInputListener);
        cetServiceFee.setText(serviceFee+"");
    }


    /**
     * 费用输入框监听
     */
    private TextWatcher feeInputListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            refreshTotalFee();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


    /**
     * 刷新显示总费用
     */
    private void refreshTotalFee(){
        String transportFeeStr = cetTransportFee.getText().toString().trim();
        String serviceFeeStr = cetServiceFee.getText().toString().trim();

        int totalFee=0;

        if(!TextUtils.isEmpty(transportFeeStr)){
            totalFee += Integer.parseInt(transportFeeStr);
        }

        if(!TextUtils.isEmpty(serviceFeeStr)){
            totalFee += Integer.parseInt(serviceFeeStr);
        }

        tvTotalFee.setText(new StringBuilder().append(totalFee).append("元"));

    }

    @OnClick({R.id.iv_close,R.id.btn_commit})
    public void onViewClicked(View v){
        switch (v.getId()) {
            case R.id.iv_close://点击关闭按钮
                dismiss();
                break;
            case R.id.btn_commit://点击提交按钮
                if(checkInputIsCorrect()){
                    if(callback!=null){
                        callback.onFillComplete(transportFee,serviceFee);
                        dismiss();
                    }
                }
                break;
        }
    }

    private int transportFee;
    private int serviceFee;

    private boolean checkInputIsCorrect(){
        String transportFeeStr = cetTransportFee.getText().toString().trim();

        if(!TextUtils.isEmpty(transportFeeStr)){
            transportFee=Integer.parseInt(transportFeeStr);
            if(transportFee==0){
                ProjectUtil.show(getContext(),"请正确填写运费");
                return false;
            }
        }

        return true;
    }

}
