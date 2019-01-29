package com.lulian.driver.view.activity.caractivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lulian.driver.R;
import com.lulian.driver.adapter.WalletDetailAdapter;
import com.lulian.driver.base.BaseActivity;
import com.lulian.driver.entity.api.GetWalletDetailApi;
import com.lulian.driver.entity.server.resulte.MyWalletDetail;
import com.rxretrofitlibrary.retrofit_rx.exception.ApiException;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.alibaba.fastjson.JSON.parseArray;

/**
 * Created by Administrator on 2018/8/27.
 */

public class MyWalletDetailActivity extends BaseActivity implements PullLoadMoreRecyclerView.PullLoadMoreListener  {

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
    @BindView(R.id.waybill_recyclerview_list)
    PullLoadMoreRecyclerView waybillRecyclerviewList;

    public int neType;
    private List<MyWalletDetail> MyWalletDetailList = new ArrayList<>();
    private WalletDetailAdapter walletDetailAdapter;
    /**
     * 每次加载更多请求成功后,将这个值+1,刷新成功,将这个值置为1
     */
    private int currentPage;

    private final int REQ_LIST_REFRESH=1; //通过下拉刷新触发请求列表
    private final int REQ_LIST_LOAD_MORE=2;  //通过加载更多触发请求列表
    /**
     * 用来记录是通过哪种操作方式触发的请求列表数据
     */
    private int currReqListAction= REQ_LIST_REFRESH;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waybilldetail_list);
        ButterKnife.bind(this);
        app.setiShow(true);
        initView();
        initData(1);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void initData( int page) {
        neType = 0;
        GetWalletDetailApi gfl = new GetWalletDetailApi();
        gfl.setHeader(app.getAuthorization());
        gfl.setUserHeader(app.getUserHeader());
        gfl.setUserId(app.getUserId());
        gfl.setPage(page);
        gfl.setRows(10);
        pClass.startHttpRequest(this, gfl);
    }

    private void performGetListSuccess(String data){
        List<MyWalletDetail> list = parseArray(data, MyWalletDetail.class);
        switch(currReqListAction){
            case REQ_LIST_REFRESH: //刷新
                MyWalletDetailList.clear();
                MyWalletDetailList.addAll(list);
                currentPage=1;

                /*
                 * 这个下拉刷新控件有坑,必须先调用setIsRefresh,再调用setRefreshing才能结束刷新,不然界面会一直保持刷新状态无法取消;
                 */
                waybillRecyclerviewList.setIsRefresh(false);
                waybillRecyclerviewList.setRefreshing(false);
                waybillRecyclerviewList.setHasMore(true);
                break;
            case REQ_LIST_LOAD_MORE: //加载
                MyWalletDetailList.addAll(list);
                if(list.size()>0){
                    currentPage++;
                }else{
                    waybillRecyclerviewList.setHasMore(false);
                }
                waybillRecyclerviewList.setPullLoadMoreCompleted();
                break;
        }

        if (walletDetailAdapter != null) {
            walletDetailAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onSuccess(String data) {
        if (neType == 0) {
            performGetListSuccess(data);
        }
    }

    private void initView() {
        textContent.setText("钱包明细");
        waybillRecyclerviewList.setLinearLayout();
        waybillRecyclerviewList.setItemAnimator(new DefaultItemAnimator());
        waybillRecyclerviewList.setOnPullLoadMoreListener(this);

        walletDetailAdapter = new WalletDetailAdapter(this, MyWalletDetailList, app.getUserId());
        waybillRecyclerviewList.setAdapter(walletDetailAdapter);

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
    public void onError(ApiException e) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onRefresh() {
        currReqListAction=REQ_LIST_REFRESH;
        initData(1);
    }

    @Override
    public void onLoadMore() {
        currReqListAction=REQ_LIST_LOAD_MORE;
        initData(currentPage+1);
    }


}
