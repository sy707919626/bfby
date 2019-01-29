package com.lulian.driver.view.activity.caractivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.hedgehog.ratingbar.RatingBar;
import com.lulian.driver.R;
import com.lulian.driver.base.BaseActivity;
import com.lulian.driver.entity.api.GetOrderInfoApi;
import com.lulian.driver.entity.api.GetReceiveTaskApi;
import com.lulian.driver.entity.api.GetRefuseTaskApi;
import com.lulian.driver.entity.server.resulte.OrderDetailNew;
import com.lulian.driver.entity.server.resulte.OrderReturnHistory;
import com.lulian.driver.pay.WalletChargeActivity;
import com.lulian.driver.utils.ProjectUtil;
import com.rxretrofitlibrary.retrofit_rx.exception.ApiException;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.squareup.picasso.MemoryPolicy.NO_CACHE;
import static com.squareup.picasso.MemoryPolicy.NO_STORE;

/**
 * Created by MARK on 2018/6/8.
 */

public class OrderDetailsActivity extends BaseActivity {
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
    @BindView(R.id.orderdetail_btn_makeorder)
    Button orderdetailBtnMakeorder;
    @BindView(R.id.accept_orderdetail)
    Button acceptOrderdetail;
    @BindView(R.id.refuse_orderdetail)
    Button refuseOrderdetail;
    @BindView(R.id.orderdetail_task)
    LinearLayout orderdetailTask;
    @BindView(R.id.bootom_layout_make)
    LinearLayout bootomLayoutMake;
    @BindView(R.id.orderdetail_img_head)
    ImageView orderdetailImgHead;
    @BindView(R.id.order_txt_userlevel)
    ImageView orderTxtUserlevel;
    @BindView(R.id.order_layout_right)
    LinearLayout orderLayoutRight;
    @BindView(R.id.order_view_line)
    View orderViewLine;
    @BindView(R.id.orderdetail_btn_phone)
    ImageView orderdetailBtnPhone;
    @BindView(R.id.order_layout_left)
    LinearLayout orderLayoutLeft;
    @BindView(R.id.order_view_line2)
    View orderViewLine2;
    @BindView(R.id.ratingbar)
    RatingBar ratingbar;
    @BindView(R.id.orderdetail_txt_name)
    TextView orderdetailTxtName;
    @BindView(R.id.orderdetail_phoneNum)
    TextView orderdetailPhoneNum;
    @BindView(R.id.allorder_txt_fzstart)
    TextView allorderTxtFzstart;
    @BindView(R.id.allorder_txt_fzend)
    TextView allorderTxtFzend;
    @BindView(R.id.order_ll_user)
    RelativeLayout orderLlUser;
    @BindView(R.id.orderdetail_txt_czstate)
    TextView orderdetailTxtCzstate;
    @BindView(R.id.orderdetail_txt_ordernum)
    TextView orderdetailTxtOrdernum;
    @BindView(R.id.allorder_txt_start)
    TextView allorderTxtStart;
    @BindView(R.id.allorder_txt_end)
    TextView allorderTxtEnd;
    @BindView(R.id.allorder_txt_weight)
    TextView allorderTxtWeight;
    @BindView(R.id.allorder_txt_type)
    TextView allorderTxtType;
    @BindView(R.id.orderdetail_txt_time)
    TextView orderdetailTxtTime;
    @BindView(R.id.allorder_layout_centercontent)
    LinearLayout allorderLayoutCentercontent;
    @BindView(R.id.orderdetail_txt_remark)
    TextView orderdetailTxtRemark;
    @BindView(R.id.orderdetail_txt_startaread)
    TextView orderdetailTxtStartaread;
    @BindView(R.id.orderdetail_txt_startdetail)
    TextView orderdetailTxtStartdetail;
    @BindView(R.id.orderdetail_txt_endaread)
    TextView orderdetailTxtEndaread;
    @BindView(R.id.orderdetail_txt_endetail)
    TextView orderdetailTxtEndetail;
    @BindView(R.id.orderdetail_txt_starttime)
    TextView orderdetailTxtStarttime;
    @BindView(R.id.orderdetail_txt_endtime)
    TextView orderdetailTxtEndtime;
    @BindView(R.id.orderdetail_remark)
    TextView orderdetailRemark;
    private String orderId;
    private int type;
    private int neType = 0;
    OrderDetailNew orderDetailNew;
    private String TAG = "OrderDetailsActivity";
    private List<OrderReturnHistory> backList;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderdetail);

        orderId = getIntent().getStringExtra("orderId");
//        orderId = "c93dafbc-f167-42e5-a2fb-05dc31133a81";
        type = getIntent().getIntExtra("type", 0);

        ButterKnife.bind(this);
        initView();
        initData();
    }

    public static void start(Context context, String orderIds, int Type) {
        Intent intent = new Intent(context, OrderDetailsActivity.class);
        intent.putExtra("orderId", orderIds);
        intent.putExtra("type", Type);
        context.startActivity(intent);
    }

    @SuppressLint("ResourceAsColor")
    private void initView() {
        textContent.setText("订单详情");
        titleTxtRightbottom.setVisibility(View.VISIBLE);
        titleTxtrightbottomleft.setVisibility(View.VISIBLE);
        titleTxtRightbottom.setText("分享");
        titleTxtrightbottomleft.setText("投诉");

        if (type == 2) {
            //任务订单
            orderdetailTask.setVisibility(View.VISIBLE);
            orderdetailBtnMakeorder.setVisibility(View.GONE);

        } else {
            //全部订单
            orderdetailTask.setVisibility(View.GONE);
            orderdetailBtnMakeorder.setVisibility(View.VISIBLE);
        }
    }

    private void initData() {
        neType = 0;
        GetOrderInfoApi goia = new GetOrderInfoApi();
        goia.setHeader(app.getAuthorization());
        goia.setUserHeader(app.getUserHeader());
        goia.setOrderId(orderId);
        pClass.startHttpRequest(this, goia);
    }

    @Override
    public void onSuccess(String data) {
        if (neType == 0) {
            orderDetailNew = JSON.parseObject(data, new TypeReference<OrderDetailNew>() {
            });
            showData();

        } else if (neType == 1) {//支付订金

        } else if (neType == 2) {
            ProjectUtil.show(this, "你已拒绝该订单指派车辆任务");
            this.finish();

        } else if (neType == 3) {
            ProjectUtil.show(this, "你已接受此任务，请支付定金，并及时前往装货地");
            orderdetailBtnMakeorder.setVisibility(View.VISIBLE);
            orderdetailTask.setVisibility(View.GONE);
    }
    }

    @Override
    public void onError(ApiException e) {
        super.onError(e);
    }

    @Override
    public void dismissProg() {
        super.dismissProg();
    }

    @Override
    public void showProg() {
        super.showProg();
    }

    @OnClick({R.id.img_return, R.id.title_txt_rightbottom,
            R.id.title_txtrightbottomleft, R.id.order_ll_user,
            R.id.orderdetail_btn_makeorder, R.id.orderdetail_btn_phone,
            R.id.refuse_orderdetail, R.id.accept_orderdetail})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_return:
                this.finish();
                break;
            case R.id.title_txt_rightbottom: //分享
                break;
            case R.id.title_txtrightbottomleft: //投诉
                break;
            case R.id.orderdetail_btn_makeorder: //支付订金
                neType = 1;
                Intent intent = new Intent(this, WalletChargeActivity.class);
                intent.putExtra("orderId", orderId);
                startActivity(intent);

                break;

            case R.id.orderdetail_btn_phone:
                ProjectUtil.checkCallPhone(this, orderdetailPhoneNum.getText().toString().trim());
                break;

            case R.id.refuse_orderdetail: //拒绝任务
                neType = 2;
                GetRefuseTaskApi getRefuseTaskApi = new GetRefuseTaskApi();
                getRefuseTaskApi.setHeader(app.getAuthorization());
                getRefuseTaskApi.setUserHeader(app.getUserHeader());
                getRefuseTaskApi.setOrderId(orderId);
                pClass.startHttpRequest(this, getRefuseTaskApi);

                break;

            case R.id.accept_orderdetail: //接受任务
                neType = 3;
                GetReceiveTaskApi getReceiveTaskApi = new GetReceiveTaskApi();
                getReceiveTaskApi.setHeader(app.getAuthorization());
                getReceiveTaskApi.setUserHeader(app.getUserHeader());
                getReceiveTaskApi.setOrderId(orderId);
                pClass.startHttpRequest(this, getReceiveTaskApi);

                break;
        }
    }

    public void showData() {
        String number = null;
        String goods = null;

        orderdetailTxtName.setText(orderDetailNew.getName());
        String startArea = orderDetailNew.getOnLoadArea();
        allorderTxtStart.setText(startArea);
        allorderTxtEnd.setText(orderDetailNew.getUnLoadArea());

        allorderTxtWeight.setText(orderDetailNew.getAutomobileTypName() + "/" + orderDetailNew.getAutomobileLength() + "/" +
                number + "吨");

        allorderTxtType.setText(orderDetailNew.getGoods()); //货物
        orderdetailTxtTime.setText(orderDetailNew.getMindOnloadTime()); //发布时间
        orderdetailTxtOrdernum.setText(orderDetailNew.getNumber()); //订单号
        orderdetailTxtStartaread.setText(startArea);   //装货地址
//        ProjectUtil.show(this,""+orderdetailTxtStartaread.toString());
        orderdetailTxtStartdetail.setText(""); //装货详细地址
        orderdetailTxtEndaread.setText(orderDetailNew.getUnLoadArea());   //卸货地址
        orderdetailTxtEndetail.setText("");  //卸货详细地址
        orderdetailTxtStarttime.setText(orderDetailNew.getMindOnloadTime()); //装货时间
        orderdetailTxtEndtime.setText(orderDetailNew.getMinUnLoadTime()); //卸货时间
        orderdetailTxtRemark.setText(orderDetailNew.getOtherminaMin()); //其他要求
        orderdetailPhoneNum.setText(orderDetailNew.getPhone());
        orderdetailRemark.setText(orderDetailNew.getRemark());

        Picasso.get()
                .load(assembleImgUrl(orderDetailNew.getHeaderUrl())).memoryPolicy(NO_CACHE, NO_STORE).into(orderdetailImgHead);
        //states
        int status = orderDetailNew.getStatus();
        if (status == 5) {
            orderdetailTxtCzstate.setText("待派车");
        } else {
            orderdetailTxtCzstate.setText("不明确");
        }
    }
}
