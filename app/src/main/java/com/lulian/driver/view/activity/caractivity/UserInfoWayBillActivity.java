package com.lulian.driver.view.activity.caractivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.hedgehog.ratingbar.RatingBar;
import com.lulian.driver.R;
import com.lulian.driver.base.BaseActivity;
import com.lulian.driver.entity.api.AddBlackListApi;
import com.lulian.driver.entity.api.AddDriverVehicle;
import com.lulian.driver.entity.api.RemoveDriverVehicle;
import com.lulian.driver.entity.server.resulte.Cargo;
import com.lulian.driver.entity.server.resulte.Driver;
import com.lulian.driver.entity.server.resulte.DriverVeDetail;
import com.lulian.driver.entity.server.resulte.DriverVeDetailCom;
import com.lulian.driver.entity.server.resulte.DriverVeDetailTag;
import com.lulian.driver.listener.ReportClickListener;
import com.lulian.driver.utils.L;
import com.lulian.driver.utils.ProjectUtil;
import com.mylhyl.circledialog.CircleDialog;
import com.mylhyl.circledialog.callback.ConfigButton;
import com.mylhyl.circledialog.callback.ConfigDialog;
import com.mylhyl.circledialog.callback.ConfigText;
import com.mylhyl.circledialog.params.ButtonParams;
import com.mylhyl.circledialog.params.DialogParams;
import com.mylhyl.circledialog.params.TextParams;
import com.rxretrofitlibrary.retrofit_rx.exception.ApiException;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by MARK on 2018/6/13.
 */

public class UserInfoWayBillActivity extends BaseActivity {
    String TAG = "UserInfoWayBillActivity";
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
    private String driverUserId = "";
    private String idType = "";
    private DriverVeDetail dvd;
    private int neType = 0;
    private DriverVeDetailCom cvdc;
    private List<DriverVeDetailTag> tagList;
    private GridLayoutManager layoutManager;
    private String userType;
    private Driver driver;
    private Cargo cargo;
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
        setContentView(R.layout.activity_baseuserintroduce);
        Intent intent = getIntent();
        userType = intent.getStringExtra("userType");
        ButterKnife.bind(this);
        if (userType.equals("huozu")) {
            cargo = (Cargo) intent.getSerializableExtra("info");
            L.e("test", "cargoid:" + cargo.getId());
            initcargoView();
            initcargoData();
        } else if (userType.equals("siji")) {
            driver = (Driver) intent.getSerializableExtra("info");
            L.e("test", "driverid:" + driver.getId());
            initDriverView();
            initDriverData();
        }

    }


    private void initDriverData() {
        driverDetailName.setText(driver.getName());
        ratingbar.setStar(driver.getStar());
        driverDetailVehicle.setText(driver.getVehicleType() + "/" + driver.getVehicleHeight() + "高/" + driver.getVehicleWeight() + "吨");

//                carlist_img_head
    }

    private void initcargoData() {
        driverDetailName.setText(cargo.getName());
        ratingbar.setStar(cargo.getStar());
    }

    @Override
    public void onSuccess(String data) {
        ProjectUtil.show(this, data);
        Log.i(TAG, data);
        if (neType == 0) {//详情信息
            dvd = JSONObject.parseObject(data, DriverVeDetail.class);
            cvdc = JSONObject.parseObject(dvd.getComments(), DriverVeDetailCom.class);
            tagList = JSONObject.parseArray(dvd.getTags(), DriverVeDetailTag.class);
//            showUserInfo();
        } else if (neType == 1) {//加入车队

        } else if (neType == 2) {//移出车队

        } else if (neType == 3) {//定位司机

        } else if (neType == 4) {//备注信息

        } else if (neType == 5) {//举报司机

        }
    }

    private void initDriverView() {
        textContent.setText("用户简介");

    }

    private void initcargoView() {
        textContent.setText("用户简介");
        titleTxtRightbottom.setVisibility(View.VISIBLE);
        titleTxtRightbottom.setText("加入收藏");

        userinfoLayoutCar.setVisibility(View.GONE);

        userinfoTxtCompany.setText("公司认证");
        if (idType.equals("1")) {

        } else if (idType.equals("2")) {
            titleTxtRightbottom.setVisibility(View.VISIBLE);
            titleTxtRightbottom.setText("加入车队");

        }
        layoutManager = new GridLayoutManager(this, 3);
    }


    @OnClick({R.id.img_return, R.id.title_txt_rightbottom,
           R.id.detail_txt_report})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.img_return:
                this.finish();
                break;

            case R.id.title_txt_rightbottom:
                neType = 1;
                toSelectDlog(2);
                break;

            case R.id.detail_txt_report:
                showReportDialog();
                break;
        }
    }

    @Override
    public void showProg() {
        super.showProg();
    }

    @Override
    public void dismissProg() {
        super.dismissProg();
    }


/*    private void showUserInfo() {
        driverDetailName.setText(dvd.getDriverName());
        driverDetailPlateno.setText(dvd.getPlateNo());
        driverDetailVehicle.setText(dvd.getVehicleId() + "/可载" + dvd.getVWeight() + "吨/车长" + dvd.getVLength() + "米");
        detailTxtFavorablerate.setText(cvdc.getScore() + "%");
        detailTxtEvaluationum.setText(cvdc.getCount() + "人评价");
        detailTxtGoodreputation.setText("好评: " + cvdc.getCountA());
        detailTxtCommonreputation.setText("一般: " + cvdc.getCountB());
        detailTxtBadreputation.setText("差评: " + cvdc.getCountC());
        detailTxtCumulative.setText(dvd.getOrderCount() + "单");
        detailTxtAndideal.setText(dvd.getDriverOrderCount() + "单");
        AppraiseAdapter adapter1 = new AppraiseAdapter(tagList, R.id.detail_list_goodreputaion, mHandler);
        detailListGoodreputaion.setLayoutManager(layoutManager);
        detailListGoodreputaion.setAdapter(adapter1);
//        AppraiseAdapter adapter2 = new AppraiseAdapter(tagList,2);
//        detailListBadreputaion.setLayoutManager(layoutManager);
        detailListBadreputaion.setAdapter(adapter1);

    }*/

    @Override
    public void onError(ApiException e) {
        super.onError(e);
    }

    public void toSelectDlog(final int id) {
        String msg = "";
        if (id == 2) {
            msg = "是否将该司机加入车队";
        } else if (id == 1) {
            msg = "是否将该司机移出车队";
        }
        new CircleDialog.Builder()
                .setCanceledOnTouchOutside(false)
                .setCancelable(false)
                .configDialog(new ConfigDialog() {
                    @Override
                    public void onConfig(DialogParams params) {
                        params.backgroundColor = Color.DKGRAY;
                        params.backgroundColorPress = Color.BLUE;
                    }
                })
                .setTitle("提示").setTitleColor(Color.parseColor("#ffffff"))

                .setText(msg).setTextColor(Color.parseColor("#ffffff"))
                .configText(new ConfigText() {
                    @Override
                    public void onConfig(TextParams params) {
//                                params.gravity = Gravity.LEFT | Gravity.TOP;
                        params.padding = new int[]{100, 0, 100, 50};
                    }
                })
                .setNegative("否", null)
                .setPositive("是", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (id == 2) {//加入车队
                            AddDriverVehicle adv = new AddDriverVehicle();
                            adv.setHeader(app.getAuthorization());
                            adv.setUserHeader(app.getUserHeader());
                            adv.setDriverUserId(driverUserId);
                            pClass.startHttpRequest(UserInfoWayBillActivity.this, adv);
                        } else if (id == 1) {//移出车队
                            RemoveDriverVehicle rdv = new RemoveDriverVehicle();
                            rdv.setHeader(app.getAuthorization());
                            rdv.setUserHeader(app.getUserHeader());
                            rdv.setDriverUserId(driverUserId);
                            pClass.startHttpRequest(UserInfoWayBillActivity.this, rdv);
                        }
                    }
                })
                .configPositive(new ConfigButton() {
                    @Override
                    public void onConfig(ButtonParams params) {
                        params.backgroundColorPress = Color.RED;
                    }
                })
                .show(getSupportFragmentManager());
    }

    public void showReportDialog() {
        final Dialog snapDialog = new Dialog(UserInfoWayBillActivity.this,
                R.style.AppTheme);
        snapDialog.setContentView(R.layout.reportpage);
        Window window = snapDialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        DisplayMetrics dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay()
                .getMetrics(dm);
        WindowManager.LayoutParams lp = snapDialog.getWindow().getAttributes();
        lp.width = (int) (dm.widthPixels);
        lp.height = (int) (dm.heightPixels - 250);
        snapDialog.getWindow().setAttributes(lp);
        snapDialog.show();
        ImageView report_img_delete = (ImageView) snapDialog.findViewById(R.id.report_img_delete);
        final EditText report_edit_msg = (EditText) snapDialog.findViewById(R.id.report_edit_msg);
        Button report_btn_takephoto = (Button) snapDialog.findViewById(R.id.report_btn_takephoto);
        Button report_btn_slbum = (Button) snapDialog.findViewById(R.id.report_btn_slbum);
        ImageView report_img_img1 = (ImageView) snapDialog.findViewById(R.id.report_img_img1);
        ImageView report_img_img2 = (ImageView) snapDialog.findViewById(R.id.report_img_img2);
        ImageView report_img_img3 = (ImageView) snapDialog.findViewById(R.id.report_img_img3);
        Button report_btn_submit = (Button) snapDialog.findViewById(R.id.report_btn_submit);
        ReportClickListener listener = new ReportClickListener(snapDialog);
        report_img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snapDialog.dismiss();
            }
        });
        report_btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rMsg = report_edit_msg.getText().toString().trim();
                if (!rMsg.equals("")) {
                    neType = 5;
                    AddBlackListApi abl = new AddBlackListApi();
                    abl.setHeader(app.getAuthorization());
                    abl.setUserHeader(app.getUserHeader());
                    abl.setDriverUserId(driverUserId);
                    abl.setReason(rMsg);
                    pClass.startHttpRequest(UserInfoWayBillActivity.this, abl);
                } else {
                    ProjectUtil.show(UserInfoWayBillActivity.this, "请填写举报内容!");
                }
            }
        });
    }

}
