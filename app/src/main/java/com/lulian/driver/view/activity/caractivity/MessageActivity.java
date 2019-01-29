package com.lulian.driver.view.activity.caractivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lulian.driver.R;
import com.lulian.driver.adapter.MessageAdapter;
import com.lulian.driver.base.BaseActivity;
import com.lulian.driver.entity.api.GetMyMessageListApi;
import com.lulian.driver.entity.api.GetOrderInfoApi;
import com.lulian.driver.entity.server.resulte.MessageService;
import com.lulian.driver.entity.server.resulte.OrderDetailNew;

import com.lulian.driver.utils.L;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import static com.alibaba.fastjson.JSON.parseArray;

/**
 * Created by MARK on 2018/6/28.
 */

public class MessageActivity extends BaseActivity implements PullLoadMoreRecyclerView.PullLoadMoreListener {

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
    @BindView(R.id.message_list)
    PullLoadMoreRecyclerView messageList;

    private List<MessageService> mgsList;//消息
    private List<MessageService> mgsList1 = new ArrayList<>();
    private MessageAdapter messageAdapter;
    private int page = 1;
    public boolean isFresh=false;
    private int netType = 0;
    private boolean isLoadMode;
    OrderDetailNew orderDetailNew;

    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
           if (msg.what == 101) {
                if (messageList != null) {
//                    messageList.setIsRefresh(false);
//                    messageList.setRefreshing(false);
                    messageList.setPullLoadMoreCompleted();
                }
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        ButterKnife.bind(this);
        initView();
        initData(1);

    }

    private void initData( int page) {
        netType = 0;
        GetMyMessageListApi getMy = new GetMyMessageListApi();
        getMy.setHeader(app.getAuthorization());
        getMy.setUserHeader(app.getUserHeader());
        getMy.setPage(page);
        getMy.setRows(10);
        pClass.startHttpRequest(this, getMy);
    }

    @Override
    public void onSuccess(String data) {
        if (netType == 0) {
            mgsList = parseArray(data, MessageService.class);

            if (isFresh || page == 1) {
                if (mgsList1 != null) {
                    mgsList1.clear();
                    isFresh = false;
                }
            }

            if (mgsList.size() > 0) {
                for (MessageService my : mgsList) {
                    mgsList1.add(my);
                }
            }

            if (messageAdapter == null) {
                messageAdapter = new MessageAdapter(mgsList1);
                messageList.setAdapter(messageAdapter);

            } else {

                if (mgsList.size() > 0) {
                    mgsList1.addAll(mgsList);
                    messageAdapter.setData(mgsList1);
                    messageAdapter.notifyDataSetChanged();
                    isLoadMode = false;
                } else {
                    isLoadMode = true;
                }

                mHandler.sendEmptyMessage(101);
            }


        }
    }

    private void initView() {
        textContent.setText("消息");
        messageList.setLinearLayout();
        messageList.setItemAnimator(new DefaultItemAnimator());
        messageList.setOnPullLoadMoreListener(this);
    }

    @OnClick({R.id.img_return})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_return:
                this.finish();
                break;
        }
    }

    @Override
    public void onRefresh() {
        isFresh = true;
        page = 1;
        initData(page);
    }

    @Override
    public void onLoadMore() {
        if (!isLoadMode) {
            page = page + 1;
        }
        initData(page);
    }


}
