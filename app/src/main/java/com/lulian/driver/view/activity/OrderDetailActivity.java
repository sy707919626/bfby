package com.lulian.driver.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hedgehog.ratingbar.RatingBar;
import com.lulian.driver.R;
import com.lulian.driver.base.BaseActivity;
import com.lulian.driver.entity.api.GetOrderDetailApi;
import com.lulian.driver.entity.api.OrderOfferApi;
import com.lulian.driver.entity.server.req.ReqOfferBean;
import com.lulian.driver.entity.server.resulte.OrderDetail;
import com.lulian.driver.utils.ProjectUtil;
import com.lulian.driver.utils.feature.InfoDisplayTool;
import com.lulian.driver.utils.feature.LoadFailTipHelper;
import com.lulian.driver.view.DictDataTool;
import com.lulian.driver.view.dialog.OfferFillDialog;
import com.rxretrofitlibrary.retrofit_rx.exception.ApiException;
import com.rxretrofitlibrary.retrofit_rx.utils.GsonUtil;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 订单详情界面
 * @author hxb
 */
public class OrderDetailActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.tv_captain_name)
    TextView tvCaptainName;//车队长名字
    @BindView(R.id.ratingbar_grade)
    RatingBar ratingBarGrade;//车队长等级
    @BindView(R.id.tv_captain_phone)
    TextView tvCaptainPhone;//车队长电话号码

    @BindView(R.id.tv_captain_line_start)
    TextView tvCaptainLineStart;//车队长负责路线起始地
    @BindView(R.id.tv_captain_line_end)
    TextView tvCaptainLineEnd;//车队长负责路线目的地

    @BindView(R.id.vg_my_offer_block)
    ViewGroup vgMyOfferBlock;//我的报价 板块父View
    @BindView(R.id.tv_transport_fee)
    TextView tvTransportFee;//运费
    @BindView(R.id.tv_service_fee)
    TextView tvServiceFee;//服务费
    @BindView(R.id.tv_total_fee)
    TextView tvTotalFee;//总费用

    @BindView(R.id.tv_order_status)
    TextView tvOrderStatus;//订单状态
    @BindView(R.id.tv_order_num)
    TextView tvOrderNum;//订单编号
    @BindView(R.id.tv_load_region)
    TextView tvLoadRegion;//装货地地区
    @BindView(R.id.tv_unload_region)
    TextView tvUnLoadRegion;//卸货地地区

    @BindView(R.id.tv_truck_type_len_weight_volume)
    TextView tvGoodsInfo;//货物信息(车型车长重量体积)
    @BindView(R.id.tv_goods_name)
    TextView tvGoodsName;//货物名称
    @BindView(R.id.tv_publish_time)
    TextView tvPublishTime;//发布时间

    @BindView(R.id.tv_other_require)
    TextView tvOtherRequire;//其他要求

    @BindView(R.id.tv_load_region_2)
    TextView tvLoadRegion2;//装货地地区2
    @BindView(R.id.tv_load_detail_address)
    TextView tvLoadDetailAddr;//装货详细地址

    @BindView(R.id.tv_unload_region_2)
    TextView tvUnLoadRegion2;//卸货地地区2
    @BindView(R.id.tv_unload_detail_address)
    TextView tvUnLoadDetailAddr;//卸货详细地址

    @BindView(R.id.et_remark)
    EditText etRemark;//备注填写框

    @BindView(R.id.btn_bottom_operation)
    Button btnBottomOperation;//底部操作按钮

    private String orderId;//订单id
    private OrderDetail orderDetail;//订单详情对象

    /*
     * 定义底部按钮操作类型常量
     */
    public final byte BOTTOM_BTN_OP_TYPE_BACK=1;//返回
    public final byte BOTTOM_BTN_OP_TYPE_OFFER=2;//报价

    private byte currentBottomOpType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(initLoadFailHelper());
        ButterKnife.bind(this);

        orderId=getIntent().getStringExtra("orderId");

        requestOrderDetail();
    }

    private OfferFillDialog offerFillDialog;//报价填写dialog

    private void initOfferFillDialog(int serviceFee){
        offerFillDialog = new OfferFillDialog(this,serviceFee);
        offerFillDialog.setCallback(new OfferFillDialog.Callback() {
            @Override
            public void onFillComplete(int transportFee, int serviceFee) {
                requestOffer(transportFee);
            }
        });
    }

    private LoadFailTipHelper loadFailTipHelper;

    private View initLoadFailHelper(){
        loadFailTipHelper = new LoadFailTipHelper(this,R.layout.activity_order_detail);
        loadFailTipHelper.setCallback(new LoadFailTipHelper.Callback() {
            @Override
            public void onReloadClicked() {
                requestOrderDetail();
            }
        });
        return loadFailTipHelper.getWrapperView();
    }


    @Override
    public void onSuccess(String data) {
        switch (neType) {
            case 1://获取订单详情成功
                performGetOrderDetailSuccess(data);
                break;
            case 2://报价成功
                ProjectUtil.show(this,"报价成功!");
                requestOrderDetail();
                break;
        }
    }

    private void performGetOrderDetailSuccess(String data){
        loadFailTipHelper.showContentView();

        orderDetail = GsonUtil.get().fromJson(data, OrderDetail.class);

        boolean isOfferAlready = isOfferAlready(orderDetail);

        //标题
        if(isOfferAlready){
            tvTitle.setText("订单详情");
        }else{
            tvTitle.setText("订单报价");
        }

        //车队长名字
        tvCaptainName.setText(InfoDisplayTool.convertNameToAppellation(orderDetail.getName(),"队长"));
        //车队长等级
        ratingBarGrade.setStar(orderDetail.getStar());
        //车长电话号码
        tvCaptainPhone.setText(orderDetail.getPhone());
        //车队长路线起始地
        tvCaptainLineStart.setText(orderDetail.getOnLoadArea());
        //车队长路线目的地
        tvCaptainLineEnd.setText(orderDetail.getUnLoadArea());

        if(isOfferAlready){
            vgMyOfferBlock.setVisibility(View.VISIBLE);
            //运费
            tvTransportFee.setText(new StringBuilder().append(orderDetail.getOffer()).append("元"));
            //服务费
            tvServiceFee.setText(new StringBuilder().append(orderDetail.getInfoFee()).append("元"));
            //总费用
            tvTotalFee.setText(new StringBuilder().append(orderDetail.getOffer()+orderDetail.getInfoFee()).append("元"));
        }else{
            vgMyOfferBlock.setVisibility(View.GONE);
            initOfferFillDialog(orderDetail.getInfoFee());
        }

        //订单状态
        Map<Integer, String> orderStatusMap = DictDataTool.getOrderStatusMap();
        String statusText = orderStatusMap.get(orderDetail.getStatus());
        tvOrderStatus.setText(statusText);

        //订单编号
        tvOrderNum.setText(orderDetail.getNumber());

        //订单装货地
        tvLoadRegion.setText(orderDetail.getOnLoadArea());
        //订单卸货地
        tvUnLoadRegion.setText(orderDetail.getUnLoadArea());

        //货物信息
        String assembleInfo = InfoDisplayTool.assemble_truckType_truckLength_weight_volume(orderDetail.getAutomobileTypName(),
                orderDetail.getAutomobileLength() + "",
                orderDetail.getWeight() + "",
                orderDetail.getVolume() + "");
        tvGoodsInfo.setText(assembleInfo);

        //货物名称
        tvGoodsName.setText(orderDetail.getGoodsName());

        //发布时间
        tvPublishTime.setText(orderDetail.getCreateTime());

        //装货详细地址
        tvLoadRegion2.setText(orderDetail.getOnLoadArea());
        tvLoadDetailAddr.setText(orderDetail.getOnLoadAdress());
        //卸货详细地址
        tvUnLoadRegion2.setText(orderDetail.getUnLoadArea());
        tvUnLoadDetailAddr.setText(orderDetail.getUnLoadAddress());

        //底部操作按钮
        if(isOfferAlready){
            btnBottomOperation.setText("返回");
            currentBottomOpType = BOTTOM_BTN_OP_TYPE_BACK;
        }else{
            btnBottomOperation.setText("我要报价");
            currentBottomOpType = BOTTOM_BTN_OP_TYPE_OFFER;
        }
    }


    /**
     * 判断当前用户是否已对此订单报过价
     * @param bean
     * @return
     */
    private boolean isOfferAlready(OrderDetail bean){
        int offer = bean.getOffer();
        return offer > 0;
    }


    @Override
    public void onError(ApiException e) {
        super.onError(e);
        switch (neType = 1) {
            case 1://获取订单详情失败
                loadFailTipHelper.showFailTipView();
                break;
        }
    }

    /**
     * 请求订单详情
     */
    private void requestOrderDetail(){
        neType=1;
        GetOrderDetailApi api = new GetOrderDetailApi();
        api.setHeader(app.getAuthorization());
        api.setUserHeader(app.getUserHeader());

        api.setOrderId(orderId);

        pClass.startHttpRequest(this,api);
    }


    /**
     * 请求进行订单报价
     * @param transportFee
     */
    private void requestOffer(int transportFee){
        neType = 2;
        OrderOfferApi api = new OrderOfferApi();
        api.setHeader(app.getAuthorization());
        api.setUserHeader(app.getUserHeader());

        ReqOfferBean bean = new ReqOfferBean();
        bean.setOrderId(orderId);
        bean.setPrice(transportFee);

        api.setBean(bean);

        pClass.startHttpRequest(this,api);
    }


    @OnClick({R.id.iv_back,
            R.id.btn_bottom_operation,
            R.id.vg_call_photo})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_bottom_operation://点击底部操作按钮
                performClickBottomBtn();
                break;
            case R.id.vg_call_photo://拨打电话
                ProjectUtil.checkCallPhone(this,orderDetail.getPhone());
                break;
        }
    }

    private void performClickBottomBtn(){
        switch (currentBottomOpType) {
            case BOTTOM_BTN_OP_TYPE_BACK:
                finish();
                break;
            case BOTTOM_BTN_OP_TYPE_OFFER:
                offerFillDialog.show();
                break;
        }
    }


}
