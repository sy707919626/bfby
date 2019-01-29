package com.lulian.driver.view.activity.caractivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.lulian.driver.R;
import com.lulian.driver.base.BaseActivity;
import com.lulian.driver.entity.api.GetMessageDetailApi;
import com.lulian.driver.entity.api.GetOrderInfoApi;
import com.lulian.driver.entity.server.resulte.MessageDetailApi;
import com.lulian.driver.entity.server.resulte.OrderDetailNew;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.alibaba.fastjson.JSON.parseArray;

/**
 * Created by MARK on 2018/6/28.
 */

public class MessageDetailActivity extends BaseActivity {
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
    @BindView(R.id.activity_main)
    RelativeLayout activityMain;
    @BindView(R.id.message_statuType)
    TextView messageStatuType;
    @BindView(R.id.msg_datetime)
    TextView msgDatetime;
    @BindView(R.id.message_btn_show)
    Button messageBtnShow;
    @BindView(R.id.mgs_orderid_start)
    TextView mgsOrderidStart;
    @BindView(R.id.mgs_orderid_end)
    TextView mgsOrderidEnd;
    @BindView(R.id.mgs_car_type)
    TextView mgsCarType;
    @BindView(R.id.mgs_goods)
    TextView mgsGoods;
    @BindView(R.id.waybill_num)
    TextView waybillNum;
    @BindView(R.id.message_orderId)
    TextView messageOrderId;
    @BindView(R.id.msg_message_state)
    TextView msgMessageState;
    @BindView(R.id.message_layout_detail)
    LinearLayout messageLayoutDetail;
    @BindView(R.id.message_editremark)
    TextView messageEditremark;
    @BindView(R.id.orderId_mgs_layout)
    LinearLayout orderIdMgsLayout;
    @BindView(R.id.system_title)
    TextView systemTitle;
    @BindView(R.id.message_create_time)
    TextView messageCreateTime;
    @BindView(R.id.msg_system_state)
    TextView msgSystemState;
    @BindView(R.id.system_title_edit)
    TextView systemTitleEdit;
    @BindView(R.id.system_layout_detail)
    LinearLayout systemLayoutDetail;
    @BindView(R.id.system_mgs_layout)
    LinearLayout systemMgsLayout;

    private String messageid;
    private int messageType;
    private MessageDetailApi messageDetail;
    private OrderDetailNew orderDetailNew;
    private String orderId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messagedetail);
        ButterKnife.bind(this);
        messageid = getIntent().getStringExtra("messageid");
        messageType = getIntent().getIntExtra("MessageType", 0);
        initView();
        initData();
    }

    private void initData() {
        neType = 0;
        GetMessageDetailApi gms = new GetMessageDetailApi();
        gms.setHeader(app.getAuthorization());
        gms.setUserHeader(app.getUserHeader());
        gms.setMessageid(messageid);
        pClass.startHttpRequest(this, gms);
    }

    private void initView() {
        textContent.setText("消息");

        if (messageType == 1){
            systemMgsLayout.setVisibility(View.VISIBLE);
            orderIdMgsLayout.setVisibility(View.GONE);
        }

        if (messageType == 2){
            systemMgsLayout.setVisibility(View.GONE);
            orderIdMgsLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onSuccess(String data) {
        super.onSuccess(data);
        if (neType == 0){
            messageDetail =  JSON.parseObject(data, new TypeReference<MessageDetailApi>(){});
            orderId = messageDetail.getOrderId();
            //系统消息
            if (messageDetail.getMessageType() == 1){
                showData(messageDetail);
            } else {
                neType = 1;
                GetOrderInfoApi goia = new GetOrderInfoApi();
                goia.setHeader(app.getAuthorization());
                goia.setUserHeader(app.getUserHeader());
                goia.setOrderId(messageDetail.getOrderId());
                pClass.startHttpRequest(this, goia);
            }
        } else if (neType == 1){
            orderDetailNew = JSON.parseObject(data, new TypeReference<OrderDetailNew>() {});
            showOrderData(messageDetail, orderDetailNew);
        }
    }

    private void showOrderData(MessageDetailApi messageDetail, OrderDetailNew orderDetailNew) {
        messageStatuType.setText(messageDetail.getTitle());
        msgDatetime.setText(messageDetail.getCreateTime());
        messageEditremark.setText(messageDetail.getContent());
        messageOrderId.setText(messageDetail.getOrderId());

        if (messageDetail.getReaded() == 0){
            msgMessageState.setText("未读");
        }else {
            msgMessageState.setText("已读");
        }

        mgsOrderidStart.setText(orderDetailNew.getOnLoadArea());
        mgsOrderidEnd.setText(orderDetailNew.getUnLoadArea());
        mgsCarType.setText(orderDetailNew.getAutomobileTypName() +"/" + orderDetailNew.getAutomobileLength() + "米/"
            + orderDetailNew.getGoods() + "吨");
        mgsGoods.setText(orderDetailNew.getGoods());
    }

    private void showData(MessageDetailApi messageDetail){
        systemTitle.setText(messageDetail.getTitle());
        messageCreateTime.setText(messageDetail.getCreateTime());
        systemTitleEdit.setText(messageDetail.getContent());
        if (messageDetail.getReaded() == 0){
            msgSystemState.setText("未读");
        }else {
            msgSystemState.setText("已读");
        }
    }

    @OnClick({R.id.img_return, R.id.message_btn_show})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_return:
                this.finish();
                break;
            case R.id.message_btn_show:
                OrderDetailsActivity.start(this, orderId, 1);
                break;
        }
    }
}
