package com.lulian.driver.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.lulian.driver.R;
import com.lulian.driver.adapter.WayBillListAdapter;
import com.lulian.driver.adapter.base.BaseRvAdapter;
import com.lulian.driver.base.BaseFragment;
import com.lulian.driver.entity.api.ChangeWayBillStatusApi;
import com.lulian.driver.entity.api.GetWayBillListApi;
import com.lulian.driver.entity.api.UploadFileApi;
import com.lulian.driver.entity.event.RefreshWayBillListEvent;
import com.lulian.driver.entity.server.ProofPhotoBean;
import com.lulian.driver.entity.server.req.ReqWayBillListBean;
import com.lulian.driver.entity.server.resulte.UploadFileResult;
import com.lulian.driver.entity.server.resulte.WayBillListItem;
import com.lulian.driver.utils.ChoiceDialogTool;
import com.lulian.driver.utils.ProjectUtil;
import com.lulian.driver.utils.RvConfigHelper;
import com.lulian.driver.utils.feature.FilterBarHelper;
import com.lulian.driver.utils.feature.WayBillBtnOp;
import com.lulian.driver.view.activity.WayBillDetailActivity;
import com.lulian.driver.view.dialog.ProofPhotoUploadDialog;
import com.rxretrofitlibrary.retrofit_rx.Api.BaseApi;
import com.rxretrofitlibrary.retrofit_rx.exception.ApiException;
import com.rxretrofitlibrary.retrofit_rx.utils.GsonUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 运单列表界面
 * @author hxb
 */

public class WaybillFragment extends BaseFragment {
    Unbinder unbinder;

    @BindView(R.id.text_content)
    TextView tvTitle;
    @BindView(R.id.srl)
    SmartRefreshLayout srl;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.vg_filter_bar)
    ViewGroup vgFilterBar;

    private WayBillListAdapter adapter;//运单列表数据适配器

    private int currentPage;//当前所处的页码
    private boolean currentActIsRefresh;//用来标识当前是否是刷新操作触发的请求列表(从筛选按钮触发也属于刷新操作)
    private List<Integer> currentFilterStatus =new ArrayList<>();//当前要进行筛选的运单状态


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_waybill, container, false);
        unbinder = ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);

        initView();
        requestGetWayBillList(true);
        return view;
    }


    @Override
    public void onStart() {
        super.onStart();

    }

    private void initView(){
        tvTitle.setText("我的运单");

        srl.setOnRefreshLoadMoreListener(refreshLoadMoreListener);
        RvConfigHelper.configToLLMgrVertical(mActivity,rv);
        adapter = new WayBillListAdapter(mActivity);
        adapter.setChildClickListener(listItemChildClickListener);
        rv.setAdapter(adapter);

        initFilterBarHelper();
    }


    /**
     * 控制筛选栏的帮助工具
     */
    private FilterBarHelper filterBarHelper;

    private void initFilterBarHelper(){
        filterBarHelper = new FilterBarHelper(mActivity, vgFilterBar);
        filterBarHelper.setShouldControlItemVgId(R.id.vg_click_stub_1, R.id.vg_click_stub_2, R.id.vg_click_stub_3, R.id.vg_click_stub_4);
        filterBarHelper.selectItem(R.id.vg_click_stub_1);
    }


    @OnClick({R.id.vg_click_stub_1,
            R.id.vg_click_stub_2,
            R.id.vg_click_stub_3,
            R.id.vg_click_stub_4})
    public void onFilterBarClicked(View v){
        filterBarHelper.selectItem(v.getId());
        currentFilterStatus.clear();
        switch (v.getId()) {
            case R.id.vg_click_stub_1://点击筛选全部

                break;
            case R.id.vg_click_stub_2://点击筛选进行中
                currentFilterStatus.add(1);
                currentFilterStatus.add(2);
                currentFilterStatus.add(3);
                currentFilterStatus.add(4);
                currentFilterStatus.add(12);
                break;
            case R.id.vg_click_stub_3://点击筛选已完成
                currentFilterStatus.add(5);
                break;
            case R.id.vg_click_stub_4://点击筛选其他
                break;
        }

        requestGetWayBillList(true);
    }

    /**
     * 列表条目点击监听
     */
    private BaseRvAdapter.OnChildClickListener listItemChildClickListener = new BaseRvAdapter.OnChildClickListener() {
        @Override
        public void onClick(BaseRvAdapter adapter, View view, int position) {
            WayBillListItem item = (WayBillListItem) adapter.getItem(position);
            switch (view.getId()) {
                case R.id.vg_item://点击条目
                    jumpToWayBillDetail(item);
                    break;
                case R.id.vg_operation://操作按钮
                    WayBillBtnOp op = (WayBillBtnOp) view.getTag();
                    performClickOpBtn(op,item);
                    break;

            }
        }
    };


    private void performClickOpBtn(WayBillBtnOp btnOp,WayBillListItem item){
        if(btnOp==null){
            return;
        }
        switch (btnOp) {
            case CONFIRM_ARRIVE_LOAD_POINT://达到装货地
                neType = 2;
                showConfirmTipDialog("是否已到达装货地?",buildConfirmArriveLoadPlace(item),btnOp);
                break;
            case CONFIRM_START_OFF://确认发车
                neType = 3;
                showConfirmTipDialog("是否确认发车?",buildConfirmStartOff(item),btnOp);
                break;
            case CONFIRM_ARRIVE_DEST://到达目的地
                neType = 4;
                showConfirmTipDialog("是否已到达目的地?",buildConfirmArriveDest(item),btnOp);
                break;
            case CONFIRM_GOODS_ARRIVE://确认到货
                neType = 5;
                showConfirmTipDialog("是否确认到货?",buildConfirmGoodsArrive(item),btnOp);
                break;
            case REMIND_PAY://提醒支付
                showConfirmTipDialog("是否确认提醒支付",null,btnOp);
                break;
            case APPRAISE://评价
                break;
        }
    }

    /**
     * 显示确认提示对话框
     * @param tipText 要显示的提示文字
     * @param shouldReqApi 点击"是"按钮后,要请求的接口
     */
    private void showConfirmTipDialog(String tipText, final BaseApi shouldReqApi,final WayBillBtnOp op){
        ChoiceDialogTool.showDialog(mActivity, tipText,
                new ChoiceDialogTool.Callback() {
                    @Override
                    public void onLeftBtnClick() {
                    }

                    @Override
                    public void onRightBtnClick() {
                        //确认发车和确认到货需要传拍照
                        if(op==WayBillBtnOp.CONFIRM_START_OFF){
                            showUploadConfirmStartOffPhotoDialog(shouldReqApi);
                            return;
                        }
                        if(op==WayBillBtnOp.CONFIRM_GOODS_ARRIVE){
                            showUploadGoodsArrivePhotoDialog(shouldReqApi);
                            return;
                        }

                        if(shouldReqApi!=null){
                            pClass.startHttpRequest(mActivity,shouldReqApi);
                        }
                    }
                });
    }


    private ProofPhotoUploadDialog currentOperatePhotoDialog;//当前正在操作的上传照片的dialog

    private ProofPhotoUploadDialog confirmStartOffPhotoDialog;//确认发车上传照片dialog

    private void showUploadConfirmStartOffPhotoDialog(final BaseApi shouldReqApi){
        if(confirmStartOffPhotoDialog==null){
            confirmStartOffPhotoDialog = new ProofPhotoUploadDialog(mActivity,this);
            confirmStartOffPhotoDialog.setPhotoCategory(ProofPhotoUploadDialog.PHOTO_CATEGORY_CONFIRM_START_OFF);

            String[] photoDescription = new String[]{"车尾45度角照片", "送货单和车牌照组合照片"};
            int [] samplePhotoImgResIds=new int[]{R.drawable.home1,R.drawable.home};
            confirmStartOffPhotoDialog.setPhotoDescriptions(photoDescription);
            confirmStartOffPhotoDialog.setSamplePhotoImgResIds(samplePhotoImgResIds);
        }

        confirmStartOffPhotoDialog.setCallback(new ProofPhotoUploadDialog.Callback() {
            @Override
            public void onNotifyUploadFile(File photoFile) {
                requestUploadFile(photoFile);
            }

            @Override
            public void onCommit(List<ProofPhotoBean> list) {
                //调用确认发车接口
                ChangeWayBillStatusApi api= (ChangeWayBillStatusApi) shouldReqApi;
                api.setProofPhotoList(list);
                neType = 3;
                pClass.startHttpRequest(mActivity,api);
            }
        });

        currentOperatePhotoDialog = confirmStartOffPhotoDialog;
        confirmStartOffPhotoDialog.show();
    }


    private ProofPhotoUploadDialog confirmGoodsArrivePhotoDialog;//确认到货上传照片dialog

    private void showUploadGoodsArrivePhotoDialog(final BaseApi shouldReqApi){
        if(confirmGoodsArrivePhotoDialog==null){
            confirmGoodsArrivePhotoDialog = new ProofPhotoUploadDialog(mActivity,this);
            confirmGoodsArrivePhotoDialog.setPhotoCategory(ProofPhotoUploadDialog.PHOTO_CATEGORY_CONFIRM_GOODS_ARRIVE);

            String[] photoDescription = new String[]{"车头45度角照片", "车尾45度角照片"};
            int [] samplePhotoImgResIds=new int[]{R.drawable.home1,R.drawable.home};
            confirmGoodsArrivePhotoDialog.setPhotoDescriptions(photoDescription);
            confirmGoodsArrivePhotoDialog.setSamplePhotoImgResIds(samplePhotoImgResIds);
        }

        confirmGoodsArrivePhotoDialog.setCallback(new ProofPhotoUploadDialog.Callback() {
            @Override
            public void onNotifyUploadFile(File photoFile) {
                requestUploadFile(photoFile);
            }

            @Override
            public void onCommit(List<ProofPhotoBean> list) {
                //调用确认到货接口
                ChangeWayBillStatusApi api= (ChangeWayBillStatusApi) shouldReqApi;
                api.setProofPhotoList(list);
                neType=5;
                pClass.startHttpRequest(mActivity,api);
            }
        });

        currentOperatePhotoDialog = confirmGoodsArrivePhotoDialog;
        confirmGoodsArrivePhotoDialog.show();
    }

    /**
     * 创建 确认达到装货地 的接口请求对象
     */
    private ChangeWayBillStatusApi buildConfirmArriveLoadPlace(WayBillListItem item){
        ChangeWayBillStatusApi api = buildChangeWayBillStatusApi(item);
        api.setChangeToStatus(2);
        return api;
    }


    /**
     * 创建 确认发车 的接口请求对象
     */
    private ChangeWayBillStatusApi buildConfirmStartOff(WayBillListItem item){
        ChangeWayBillStatusApi api = buildChangeWayBillStatusApi(item);
        api.setChangeToStatus(3);
        return api;
    }


    /**
     * 创建 确认到达目的地 的接口请求对象
     */
    private ChangeWayBillStatusApi buildConfirmArriveDest(WayBillListItem item){
        ChangeWayBillStatusApi api = buildChangeWayBillStatusApi(item);
        api.setChangeToStatus(4);
        return api;
    }

    /**
     * 创建 确认到货的接口请求对象
     */
    private ChangeWayBillStatusApi buildConfirmGoodsArrive(WayBillListItem item){
        ChangeWayBillStatusApi api = buildChangeWayBillStatusApi(item);
        api.setChangeToStatus(12);
        return api;
    }


    private ChangeWayBillStatusApi buildChangeWayBillStatusApi(WayBillListItem item){
        ChangeWayBillStatusApi api = new ChangeWayBillStatusApi();
        api.setHeader(app.getAuthorization());
        api.setUserHeader(app.getUserHeader());
        api.setId(item.getId());
        return api;
    }


    /**
     * 跳转到运单详情界面
     * @param item
     */
    private void jumpToWayBillDetail(WayBillListItem item){
        Intent it = new Intent(mActivity, WayBillDetailActivity.class);
        it.putExtra("wayBillId", item.getId());
        startActivity(it);
    }


    /**
     * 下拉刷新,上拉加载监听
     */
    private OnRefreshLoadMoreListener refreshLoadMoreListener = new OnRefreshLoadMoreListener() {
        @Override
        public void onRefresh(@NonNull RefreshLayout refreshLayout) {
            requestGetWayBillList(true);
            srl.finishRefresh();
        }

        @Override
        public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
            requestGetWayBillList(false);
        }
    };

    @Override
    public void onSuccess(String data) {
        switch (neType) {
            case 1://运单列表
                performGetWayBillListSuccess(data);
                break;
            case 2://到达装货地
            case 3://确认发车
            case 4://到达目的地
            case 5://确认到货
                ProjectUtil.show(mActivity,data);
                requestGetWayBillList(true);
                break;
            case 6://上传图片成功
                UploadFileResult result= GsonUtil.get().fromJson(data, UploadFileResult.class);
                currentOperatePhotoDialog.oneItemPhotoUploadSuccess(result.getUrl());
                break;
        }
    }


    private void performGetWayBillListSuccess(String data){
        List<WayBillListItem> list=GsonUtil.get().fromJson(data, new TypeToken<List<WayBillListItem>>(){}.getType());
        if(currentActIsRefresh){//刷新

            currentPage=1;
            srl.finishRefresh();
            srl.setNoMoreData(false);
            adapter.setData(list);

        }else{//加载更多
            if(list.size()>0){
                srl.finishLoadMore();
                currentPage++;
                adapter.addItemAll(list);
            }else{
                srl.finishLoadMoreWithNoMoreData();
            }
        }
    }


    @Override
    public void onError(ApiException e) {
        super.onError(e);
        switch (neType) {
            case 1:
                srl.finishRefresh(false);
                srl.finishLoadMore(false);
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({})
    public void onViewClicked(View view) {
        switch (view.getId()) {

        }
    }


    /**
     * 请求运单列表
     */
    private void requestGetWayBillList(boolean isRefresh){
        neType = 1;
        GetWayBillListApi api = new GetWayBillListApi();
        api.setHeader(app.getAuthorization());
        api.setUserHeader(app.getUserHeader());

        currentActIsRefresh = isRefresh;

        ReqWayBillListBean bean = new ReqWayBillListBean();
        if(currentActIsRefresh){
            bean.setPageIndex(1);
        }else{
            bean.setPageIndex(currentPage+1);
        }
        bean.setStatus(currentFilterStatus);

        api.setBean(bean);

        pClass.startHttpRequest(mActivity,api);
    }


    /**
     * 请求上传文件
     */
    private void requestUploadFile(File photoFile) {
        neType = 6;
        UploadFileApi uploadImgApi = new UploadFileApi();
        uploadImgApi.setHeader(app.getAuthorization());
        uploadImgApi.setUserHeader(app.getUserHeader());
        uploadImgApi.setImg(photoFile);
        pClass.startHttpRequest(mActivity, uploadImgApi);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(currentOperatePhotoDialog!=null && currentOperatePhotoDialog.isShowing()){
            currentOperatePhotoDialog.onActivityResult(requestCode,resultCode,data);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * 接收刷新列表事件的回调
     * @param e
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNotifyRefresh(RefreshWayBillListEvent e){
        requestGetWayBillList(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
