package com.lulian.driver.view.activity.caractivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.hedgehog.ratingbar.RatingBar;
import com.lulian.driver.R;
import com.lulian.driver.adapter.AppraiseAdapter;
import com.lulian.driver.base.BaseActivity;
import com.lulian.driver.entity.api.GetDriverVehicleDetailApi;
import com.lulian.driver.entity.server.resulte.DriverVeDetail;
import com.lulian.driver.entity.server.resulte.DriverVeDetailCom;
import com.lulian.driver.entity.server.resulte.DriverVeDetailTag;
import com.lulian.driver.utils.L;
import com.rxretrofitlibrary.retrofit_rx.exception.ApiException;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.squareup.picasso.MemoryPolicy.NO_CACHE;
import static com.squareup.picasso.MemoryPolicy.NO_STORE;

/**
 * Created by MARK on 2018/6/13.
 */

public class UserIntroduceActivity extends BaseActivity{
    String TAG = "UserIntroduceActivity";
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
    @BindView(R.id.carlist_img_head)
    ImageView carlistImgHead;
    @BindView(R.id.userinfo_txt_vip)
    TextView userinfoTxtVip;
    @BindView(R.id.driver_detail_name)
    TextView driverDetailName;
    @BindView(R.id.ratingbar)
    RatingBar ratingbar;
    @BindView(R.id.orderdetail_phoneNum)
    TextView orderdetailPhoneNum;
    @BindView(R.id.allorder_txt_fzstart)
    TextView allorderTxtFzstart;
    @BindView(R.id.allorder_txt_fzend)
    TextView allorderTxtFzend;
    @BindView(R.id.userinfo_layout_fzlx)
    LinearLayout userinfoLayoutFzlx;
    @BindView(R.id.userinfo_txt_company)
    TextView userinfoTxtCompany;
    @BindView(R.id.driver_detail_vehicle)
    TextView driverDetailVehicle;
    @BindView(R.id.userinfo_layout_car)
    LinearLayout userinfoLayoutCar;
    @BindView(R.id.allandmy_layout_centercontent)
    LinearLayout allandmyLayoutCentercontent;
    @BindView(R.id.detail_txt_cumulative)
    TextView detailTxtCumulative;
    @BindView(R.id.detail_txt_favorablerate)
    TextView detailTxtFavorablerate;
    @BindView(R.id.detail_txt_evaluationum)
    TextView detailTxtEvaluationum;
    @BindView(R.id.detail_txt_goodreputation)
    TextView detailTxtGoodreputation;
    @BindView(R.id.detail_txt_commonreputation)
    TextView detailTxtCommonreputation;
    @BindView(R.id.detail_txt_badreputation)
    TextView detailTxtBadreputation;
    @BindView(R.id.detail_list_goodreputaion)
    RecyclerView detailListGoodreputaion;
    @BindView(R.id.detail_text_showgood)
    TextView detailTextShowgood;
    @BindView(R.id.detail_list_badreputaion)
    RecyclerView detailListBadreputaion;
    @BindView(R.id.detail_text_showbad)
    TextView detailTextShowbad;


    private Intent intent;
    private String cargoUserId = "";
    private DriverVeDetail dvd;
    private int neType = 0;
    private DriverVeDetailCom cvdc;
    private List<DriverVeDetailTag> tagList;

    private GridLayoutManager layoutManager;
    private GridLayoutManager layoutManager1;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            if (msg.what == 1) {
                detailTextShowgood.setVisibility(View.VISIBLE);
            } else if (msg.what == 2) {
                detailTextShowbad.setVisibility(View.VISIBLE);
            }
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent = getIntent();
        cargoUserId = intent.getStringExtra("cargoUserId");
        setContentView(R.layout.activity_baseuserintroduce);
        ButterKnife.bind(this);
        app.setiShow(true);
        initData();
        initView();
    }

    private void initData() {
        neType = 0;
        GetDriverVehicleDetailApi gvd = new GetDriverVehicleDetailApi();
        gvd.setHeader(app.getAuthorization());
        gvd.setDriverUserId(cargoUserId);
        gvd.setUserHeader(app.getUserHeader());
        gvd.setType("2");
        pClass.startHttpRequest(this, gvd);
    }

    @Override
    public void onSuccess(String data) {
        if (neType == 0) {//详情信息
            dvd = JSONObject.parseObject(data, DriverVeDetail.class);
            cvdc = JSONObject.parseObject(dvd.getComments(), DriverVeDetailCom.class);
            tagList = JSONObject.parseArray(dvd.getTags(), DriverVeDetailTag.class);

            L.e("test1", "tagList:" + tagList.size());
            showUserInfo();
        }
    }

    private void initView() {
        textContent.setText("用户简介");
        layoutManager = new GridLayoutManager(this, 4);
        layoutManager1 = new GridLayoutManager(this, 4);
    }

    @Override
    public void showProg() {
        super.showProg();
    }

    @Override
    public void dismissProg() {
        super.dismissProg();
    }


    private void showUserInfo() {
        driverDetailName.setText(dvd.getName());
        driverDetailVehicle.setText(dvd.getPlateNo() + "/" + dvd.getVehicleLength() + "米/"
                + dvd.getVehicleWeight() + "吨/" + dvd.getVehicleTypeName() +"/" + dvd.getVehicleAge() + "年车龄");
        detailTxtFavorablerate.setText(cvdc.getScore() + "%");
        detailTxtEvaluationum.setText(cvdc.getCount() + "人评价");
        detailTxtGoodreputation.setText("好评: " + cvdc.getCountA());
        detailTxtCommonreputation.setText("一般: " + cvdc.getCountB());
        detailTxtBadreputation.setText("差评: " + cvdc.getCountC());
        detailTxtCumulative.setText(dvd.getOrderCount() + "单");

        orderdetailPhoneNum.setText(dvd.getPhone());
        ratingbar.setStar(dvd.getStar());
        AppraiseAdapter adapter1 = new AppraiseAdapter(tagList, R.id.detail_list_goodreputaion, mHandler);
        detailListGoodreputaion.setLayoutManager(layoutManager);
        detailListGoodreputaion.setAdapter(adapter1);

        AppraiseAdapter adapter2 = new AppraiseAdapter(tagList, R.id.detail_list_badreputaion, mHandler);
        detailListBadreputaion.setLayoutManager(layoutManager1);
        detailListBadreputaion.setAdapter(adapter2);

        Picasso.get().load(assembleImgUrl(dvd.getHeaderUrl())).memoryPolicy(NO_CACHE, NO_STORE).into(carlistImgHead);
    }

    @Override
    public void onError(ApiException e) {
        super.onError(e);
    }

    @OnClick({R.id.img_return})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_return:
                this.finish();
                break;
        }
    }
}
