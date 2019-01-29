package com.lulian.driver.view.activity.caractivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
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
import com.lulian.driver.entity.api.DriverInfoPhoneApi;
import com.lulian.driver.entity.api.addMyDreaverLeader;
import com.lulian.driver.entity.server.resulte.DriverInfoPhone;
import com.lulian.driver.utils.ProjectUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.alibaba.fastjson.JSON.parseArray;
import static com.squareup.picasso.MemoryPolicy.NO_CACHE;
import static com.squareup.picasso.MemoryPolicy.NO_STORE;

/**
 * Created by MARK on 2018/6/8.
 */

public class SearchActivity extends BaseActivity {
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
    @BindView(R.id.imgsearch)
    ImageView imgsearch;
    @BindView(R.id.phone_editText_search)
    EditText phoneEditTextSearch;
    @BindView(R.id.text_search)
    EditText textSearch;
    @BindView(R.id.search_tishi_textview)
    TextView searchTishiTextview;
    @BindView(R.id.carlist_img_head)
    ImageView carlistImgHead;
    @BindView(R.id.item_driver_name)
    TextView itemDriverName;
    @BindView(R.id.ratingbar)
    RatingBar ratingbar;
    @BindView(R.id.waybillDetail_txt_start)
    TextView waybillDetailTxtStart;
    @BindView(R.id.waybillDetail_txt_end)
    TextView waybillDetailTxtEnd;
    @BindView(R.id.item_driver_phone)
    TextView itemDriverPhone;
    @BindView(R.id.allandmy_layout_centercontent)
    LinearLayout allandmyLayoutCentercontent;
    @BindView(R.id.addiver_btn_jrsc)
    Button addiverBtnJrsc;
    @BindView(R.id.add_my_favorite)
    LinearLayout addMyFavorite;
    @BindView(R.id.search_btn_text)
    Button searchBtnText;

    private String phoneNum = "";
    private String temPhone;
    private boolean isRegister;//是否注册

    private int neType;
    private List<DriverInfoPhone> driverInfoList;

    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                showHideView(isRegister);
            }
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchdriver);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        textContent.setText("搜索");
    }

    @OnClick({R.id.img_return, R.id.imgsearch, R.id.search_btn_text})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_return:
                this.finish();
                break;
            case R.id.imgsearch:
                String searchPhone = phoneEditTextSearch.getText().toString().trim();
                temPhone = searchPhone.replaceAll(" ", "");
                if (searchPhone.equals("") || !ProjectUtil.isMobileNO(temPhone)) {
                    ProjectUtil.show(this, "请填写正确的手机号码!");
                } else {
                    neType = 1;
                    DriverInfoPhoneApi qdva = new DriverInfoPhoneApi();
                    qdva.setHeader(app.getAuthorization());
                    qdva.setUserHeader(app.getUserHeader());
                    qdva.setPhoneNo(temPhone);
                    pClass.startHttpRequest(this, qdva);
                }
                break;

            case R.id.search_btn_text:

                break;
        }
    }

    @Override
    public void onSuccess(String data) {
        super.onSuccess(data);
        if (neType == 1){
            if (driverInfoList != null) {
                driverInfoList.clear();
            }

            driverInfoList = parseArray(data, DriverInfoPhone.class);
            if (driverInfoList.size() == 0) {
                isRegister = false;
            } else {
                isRegister = true;
            }
            mHandler.sendEmptyMessage(0);

        } else if (neType == 2){ //收藏搜索出的车队长
            JSONObject jsonObject = JSONObject.parseObject(data);
            String state = jsonObject.getString("state");
            if (state.equals("1")){
                finish();
            }
        }
    }

    private void showHideView(boolean isRegister) {
        //邀请车队长注册
        if (!isRegister) {
            searchBtnText.setVisibility(View.VISIBLE);
            addMyFavorite.setVisibility(View.GONE);
            searchTishiTextview.setText("对方还未注册，你可以邀请他注册，对方注册后，直接加入你的收藏管理。");
        } else {
            //已注册车队长
            searchBtnText.setVisibility(View.GONE);
            addMyFavorite.setVisibility(View.VISIBLE);
            searchTishiTextview.setText("注：该车队长已注册");
            setValue();

        }
    }

    private void setValue() {
        final DriverInfoPhone motorCade = driverInfoList.get(0);
        Picasso.get().load(motorCade.getHeaderUrl()).memoryPolicy(NO_CACHE, NO_STORE).into(carlistImgHead);

        itemDriverName.setText(motorCade.getName());
        itemDriverPhone.setText(motorCade.getPhone());

        ratingbar.setStar(motorCade.getStar());
        waybillDetailTxtStart.setText(motorCade.getStartAreaId());
        waybillDetailTxtEnd.setText(motorCade.getEndAreaId());

        addiverBtnJrsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                neType = 2;
                addMyDreaverLeader addMyDreaverLeader = new addMyDreaverLeader();
                addMyDreaverLeader.setHeader(app.getAuthorization());
                addMyDreaverLeader.setUserHeader(app.getUserHeader());
                addMyDreaverLeader.setUserId(motorCade.getId());

                pClass.startHttpRequest(SearchActivity.this, addMyDreaverLeader);
            }
        });

    }

}
