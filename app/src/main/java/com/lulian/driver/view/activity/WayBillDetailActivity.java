package com.lulian.driver.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hedgehog.ratingbar.RatingBar;
import com.lulian.driver.R;
import com.lulian.driver.base.BaseActivity;
import com.lulian.driver.entity.api.ChangeWayBillStatusApi;
import com.lulian.driver.entity.api.GetWayBillDetailApi;
import com.lulian.driver.entity.api.UploadFileApi;
import com.lulian.driver.entity.event.RefreshWayBillListEvent;
import com.lulian.driver.entity.server.ProofPhotoBean;
import com.lulian.driver.entity.server.resulte.UploadFileResult;
import com.lulian.driver.entity.server.resulte.WaybillDetail;
import com.lulian.driver.utils.ChoiceDialogTool;
import com.lulian.driver.utils.GlobalValue;
import com.lulian.driver.utils.ProjectUtil;
import com.lulian.driver.utils.feature.InfoDisplayTool;
import com.lulian.driver.utils.feature.LoadFailTipHelper;
import com.lulian.driver.utils.feature.WayBillBtnOp;
import com.lulian.driver.view.DictDataTool;
import com.lulian.driver.view.dialog.PhotoDisplayDialog;
import com.lulian.driver.view.dialog.ProofPhotoUploadDialog;
import com.rxretrofitlibrary.retrofit_rx.Api.BaseApi;
import com.rxretrofitlibrary.retrofit_rx.exception.ApiException;
import com.rxretrofitlibrary.retrofit_rx.utils.GsonUtil;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 运单详情界面
 * @author hxb
 */
public class WayBillDetailActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.tv_shipper_name)
    TextView tvShipperName;//发货人姓名
    @BindView(R.id.tv_shipper_type)
    TextView tvShipperType;//发货人用户类型(经纪人/货主)
    @BindView(R.id.ratingbar_shipper_grade)
    RatingBar ratingBarShipperGrade;//发货人星级
    @BindView(R.id.tv_shipper_phone)
    TextView tvShipperPhone;//发货人手机号

    @BindView(R.id.tv_captain_name)
    TextView tvCaptainName;//车队长姓名
    @BindView(R.id.ratingbar_captain_grade)
    RatingBar ratingBarCaptainGrade;//发货人星级
    @BindView(R.id.tv_captain_phone)
    TextView tvCaptainPhone;//发货人手机号
    @BindView(R.id.tv_captain_line_start)
    TextView tvCaptainLineStart;//车队长负责路线起始地
    @BindView(R.id.tv_captain_line_end)
    TextView tvCaptainLineEnd;//车队长负责路线目的地

    @BindView(R.id.tv_transport_fee)
    TextView tvTransportFee;//运费
    @BindView(R.id.tv_total_fee)
    TextView tvTotalFee;//总费用

    @BindView(R.id.tv_way_bill_status)
    TextView tvWayBillStatus;//运单状态
    @BindView(R.id.tv_way_bill_num)
    TextView tvWayBillNum;//运单编号

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

    @BindView(R.id.tv_load_time)
    TextView tvLoadTime;//装货时间
    @BindView(R.id.tv_arrive_time)
    TextView tvArriveTime;//达到时间
    @BindView(R.id.tv_arrive_load_point_time)
    TextView tvArriveLoadPointTime;//达到装货地时间
    @BindView(R.id.tv_info_fee)
    TextView tvInfoFee;//信息费

    @BindView(R.id.vg_confirm_start_off_proof_block)
    ViewGroup vgStartOffProofBlock;//确认发车证明照片板块
    @BindView(R.id.vg_confirm_goods_arrive_proof_block)
    ViewGroup vgGoodsArriveProofBlock;//确认到货证明照片板块

    @BindViews({R.id.iv_confirm_start_off_proof_1,R.id.iv_confirm_start_off_proof_2})
    ImageView[] ivStartOffProofArray;//确认发车证明照片iv
    @BindViews({R.id.iv_confirm_goods_arrive_proof_1,R.id.iv_confirm_goods_arrive_proof_2})
    ImageView[] ivGoodsArriveProofArray;//确认到货证明照片iv

    @BindView(R.id.et_remark)
    EditText etRemark;//备注填写框

    @BindView(R.id.ll_bottom_btn_container)
    LinearLayout llBottomBtnContainer;//底部操作按钮容器

    private String wayBillId;//跳转到此界面时,传过来的id,这个id用来请求运单详情
    private Map<Integer, String> statusMap;//运单状态map
    private WaybillDetail detail;//订单详情数据对象


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(initLoadFailHelper());
        ButterKnife.bind(this);

        initData();
        initView();

        requestWayBillDetail();

    }

    private void initData(){
        wayBillId = getIntent().getStringExtra("wayBillId");
        statusMap = DictDataTool.getWayBillStatusMap();
    }

    private void initView(){
        tvTitle.setText("运单详情");
        initPhotoDisplayDialog();
    }


    private PhotoDisplayDialog photoDisplayDialog;//显示完成照片的dialog

    private void initPhotoDisplayDialog() {
        photoDisplayDialog = new PhotoDisplayDialog(this);
        photoDisplayDialog.setShootAgainBtnVisible(false);
    }

    private LoadFailTipHelper loadFailTipHelper;

    private View initLoadFailHelper(){
        loadFailTipHelper = new LoadFailTipHelper(this, R.layout.activity_way_bill_detail);
        loadFailTipHelper.setCallback(new LoadFailTipHelper.Callback() {
            @Override
            public void onReloadClicked() {
                requestWayBillDetail();
            }
        });
        return loadFailTipHelper.getWrapperView();
    }


    @Override
    public void onSuccess(String data) {
        switch (neType) {
            case 1:
                performGetWayBillDetailSuccess(data);
                break;
            case 2://到达装货地
            case 3://确认发车
            case 4://到达目的地
            case 5://确认到货
                ProjectUtil.show(this,data);
                requestWayBillDetail();
                break;
            case 6://上传图片成功
                UploadFileResult result= GsonUtil.get().fromJson(data, UploadFileResult.class);
                currentOperatePhotoDialog.oneItemPhotoUploadSuccess(result.getUrl());
                break;
        }
    }


    private void performGetWayBillDetailSuccess(String data){
        loadFailTipHelper.showContentView();
        detail = GsonUtil.get().fromJson(data, WaybillDetail.class);
        WaybillDetail.Plain detailPlain = detail.getFormDetail();

        /*
         * 将数据显示到界面
         */
        //发货人信息
        tvShipperName.setText(detailPlain.getUserName());
        tvShipperType.setText(detailPlain.getUsertype());
        ratingBarShipperGrade.setStar(detailPlain.getUserStar());
        tvShipperPhone.setText(detailPlain.getUserPhone());

        //车队长信息
        tvCaptainName.setText(InfoDisplayTool.convertNameToAppellation(detailPlain.getLeadersName(),"队长"));
        tvCaptainPhone.setText(detailPlain.getLeadersPhone());
        tvCaptainLineStart.setText(detailPlain.getOnLoadArea());
        tvCaptainLineEnd.setText(detailPlain.getUnLoadArea());

        //报价信息
        tvTransportFee.setText(new StringBuilder().append(detailPlain.getPrice()).append("元"));
        tvTotalFee.setText(new StringBuilder().append(detailPlain.getPrice()).append("元"));

        //货源信息
        tvWayBillStatus.setText(statusMap.get(detailPlain.getStatus()));
        tvWayBillNum.setText(detailPlain.getNumber());
        tvLoadRegion.setText(detailPlain.getOnLoadArea());
        tvUnLoadRegion.setText(detailPlain.getUnLoadArea());

        String assembleInfo = InfoDisplayTool.assemble_truckType_truckLength_weight_volume(detailPlain.getAutomobileTypName(),
                detailPlain.getAutomobileLength() + "",
                detailPlain.getWeight(),
                detailPlain.getVolume());
        tvGoodsInfo.setText(assembleInfo);
        tvGoodsName.setText(detailPlain.getGoodsName());
        tvPublishTime.setText(detailPlain.getCreateTime());
        tvLoadRegion2.setText(detailPlain.getOnLoadArea());
        tvLoadDetailAddr.setText(detailPlain.getOnLoadAdress());
        tvUnLoadRegion2.setText(detailPlain.getUnLoadArea());
        tvUnLoadDetailAddr.setText(detailPlain.getUnLoadAddress());

        placeBottomBtn(detailPlain.getStatus());
        displayProofPhoto(detail.getFormDetailImg());
    }


    /**
     * 显示证明照片(确认发证证明,确认到货证明)
     */
    private void displayProofPhoto(List<ProofPhotoBean> photoList){
        vgStartOffProofBlock.setVisibility(View.GONE);
        vgGoodsArriveProofBlock.setVisibility(View.GONE);

        if(photoList==null || photoList.size()<=0){
            return;
        }

        List<ProofPhotoBean> startOffPhotoList = new ArrayList<>();
        List<ProofPhotoBean> goodsArrivePhotoList = new ArrayList<>();

        for (ProofPhotoBean bean : photoList) {
            int type = bean.getF_Type();
            switch (type) {
                case ProofPhotoUploadDialog.PHOTO_CATEGORY_CONFIRM_START_OFF:
                    startOffPhotoList.add(bean);
                    break;
                case ProofPhotoUploadDialog.PHOTO_CATEGORY_CONFIRM_GOODS_ARRIVE:
                    goodsArrivePhotoList.add(bean);
                    break;
            }
        }


        Comparator<ProofPhotoBean> comparator = new Comparator<ProofPhotoBean>() {
            @Override
            public int compare(ProofPhotoBean o1, ProofPhotoBean o2) {
                int o1Loc = o1.getF_Location();
                int o2Loc = o2.getF_Location();
                return o1Loc-o2Loc;
            }
        };


        if (startOffPhotoList.size() > 0) {
            vgStartOffProofBlock.setVisibility(View.VISIBLE);
            Collections.sort(startOffPhotoList,comparator);
            for (int i=0;i<startOffPhotoList.size();i++) {
                String url = startOffPhotoList.get(i).getF_Url();
                ImageView iv = ivStartOffProofArray[i];
                iv.setTag(url);
                Picasso.get().load(GlobalValue.BASEURL + url).into(iv);
            }
        }


        if(goodsArrivePhotoList.size()>0){
            vgGoodsArriveProofBlock.setVisibility(View.VISIBLE);
            Collections.sort(goodsArrivePhotoList,comparator);
            for (int i=0;i<startOffPhotoList.size();i++) {
                String url = goodsArrivePhotoList.get(i).getF_Url();
                ImageView iv = ivGoodsArriveProofArray[i];
                iv.setTag(url);
                Picasso.get().load(GlobalValue.BASEURL + url).into(iv);
            }
        }

    }


    /**
     * 证明照片iv点击监听
     */
    @OnClick({R.id.iv_confirm_start_off_proof_1, R.id.iv_confirm_start_off_proof_2,
            R.id.iv_confirm_goods_arrive_proof_1, R.id.iv_confirm_goods_arrive_proof_2})
    public void onProofPhotoIvClicked(View v){
        String url= (String) v.getTag();
        photoDisplayDialog.setImgUrl(GlobalValue.BASEURL + url);
        photoDisplayDialog.show();
    }


    /**
     * 根据运单的状态等,动态添加底部操作按钮
     */
    private void placeBottomBtn(int status) {

        switch (status) {
            case 1: //待取货状态
                placeBottomBtnByOps(WayBillBtnOp.CONFIRM_ARRIVE_LOAD_POINT,
                        WayBillBtnOp.LOAD_POINT_NAVIGATION,
                        WayBillBtnOp.EXCEPTION_RECORD,
                        WayBillBtnOp.DROP_ORDER);
                break;
            case 2://已装车状态
                placeBottomBtnByOps(WayBillBtnOp.CONFIRM_START_OFF,
                        WayBillBtnOp.EXCEPTION_RECORD,
                        WayBillBtnOp.DROP_ORDER);
                break;
            case 3://运送中状态
                placeBottomBtnByOps(WayBillBtnOp.CONFIRM_ARRIVE_DEST,
                        WayBillBtnOp.DEST_POINT_NAVIGATION,
                        WayBillBtnOp.EXCEPTION_RECORD);
                break;
            case 4://已到达状态
                placeBottomBtnByOps(WayBillBtnOp.CONFIRM_GOODS_ARRIVE,
                        WayBillBtnOp.EXCEPTION_RECORD);
                break;
            case 12://已卸货状态
                placeBottomBtnByOps(WayBillBtnOp.REMIND_PAY);
                break;
            case 5://已完成状态
                placeBottomBtnByOps(WayBillBtnOp.APPRAISE);
                break;

        }

    }


    /**
     * 底部操作按钮点击监听
     */
    private View.OnClickListener bottomBtnClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            WayBillBtnOp btnOp = (WayBillBtnOp) v.getTag();
            switch (btnOp) {
                case CONFIRM_ARRIVE_LOAD_POINT://达到装货地
                    neType = 2;
                    showConfirmTipDialog("是否已到达装货地?",buildConfirmArriveLoadPlace(),btnOp);
                    break;
                case CONFIRM_START_OFF://确认发车
                    neType = 3;
                    showConfirmTipDialog("是否确认发车?",buildConfirmStartOff(),btnOp);
                    break;
                case CONFIRM_ARRIVE_DEST://到达目的地
                    neType = 4;
                    showConfirmTipDialog("是否已到达目的地?",buildConfirmArriveDest(),btnOp);
                    break;
                case CONFIRM_GOODS_ARRIVE://确认到货
                    neType = 5;
                    showConfirmTipDialog("是否确认到货?",buildConfirmGoodsArrive(),btnOp);
                    break;
                case EXCEPTION_RECORD://异常记录
                    break;
                case LOAD_POINT_NAVIGATION://装货地导航
                    break;
                case DEST_POINT_NAVIGATION://目的地导航
                    break;
                case REMIND_PAY://提醒支付
                    break;
                case DROP_ORDER://退单
                    break;
                case APPRAISE://评价
                    break;


            }
        }
    };

    private LayoutInflater lif;

    /**
     * 根据传入的操作按钮类型枚举,添加对应的按钮到界面中
     * @param btnOps
     */
    private void placeBottomBtnByOps(WayBillBtnOp ... btnOps) {
        if(lif==null){
            lif = LayoutInflater.from(this);
        }

        llBottomBtnContainer.removeAllViews();

        for(int i=0;i<btnOps.length;i++){
            View pairBtnGroup = lif.inflate(R.layout.layout_btn_pair, null);
            WayBillBtnOp btnOp = btnOps[i];
            if (btnOps.length % 2 != 0) {//需要放置的操作按钮为为 奇数个
                if(i==0){
                    Button btn1 = pairBtnGroup.findViewById(R.id.btn_1);
                    Button btn2 = pairBtnGroup.findViewById(R.id.btn_2);
                    placeOpToBtnAndSetClickListener(btn1,btnOp);
                    btn2.setVisibility(View.GONE);
                    llBottomBtnContainer.addView(pairBtnGroup);
                }else{
                    if(llBottomBtnContainer.getChildCount()==1){
                        placeOneBtnOpToPairGroup(pairBtnGroup, btnOp);
                        llBottomBtnContainer.addView(pairBtnGroup);
                    }else{
                        View lastBtnPairGroup = llBottomBtnContainer.getChildAt(llBottomBtnContainer.getChildCount() - 1);
                        View returnPairGroup = placeOneBtnOpToPairGroup(lastBtnPairGroup, btnOp);
                        if(returnPairGroup!=null){
                            llBottomBtnContainer.addView(returnPairGroup);
                        }
                    }

                }


            }else{//需要放置的操作按钮为 偶数个
                if(llBottomBtnContainer.getChildCount()==0){
                    placeOneBtnOpToPairGroup(pairBtnGroup, btnOp);
                    llBottomBtnContainer.addView(pairBtnGroup);
                }else{
                    View lastBtnPairGroup = llBottomBtnContainer.getChildAt(llBottomBtnContainer.getChildCount() - 1);
                    View returnPairGroup = placeOneBtnOpToPairGroup(lastBtnPairGroup, btnOp);
                    if(returnPairGroup!=null){
                        llBottomBtnContainer.addView(returnPairGroup);
                    }
                }
            }
        }

    }

    /**
     *
     */
    private View placeOneBtnOpToPairGroup(View pairBtnGroup,WayBillBtnOp op){
        if(lif==null){
            lif = LayoutInflater.from(this);
        }
        Button btn1 = pairBtnGroup.findViewById(R.id.btn_1);
        Button btn2 = pairBtnGroup.findViewById(R.id.btn_2);
        Object btn1Tag = btn1.getTag();
        Object btn2Tag = btn2.getTag();

        if(btn1Tag!=null && btn2Tag!=null){//如果传进来的pairBtnGroup中的按钮已经设置满了,就应该新建一个
            View newBtnPairGroup=lif.inflate(R.layout.layout_btn_pair, null);
            Button newBtn1 = newBtnPairGroup.findViewById(R.id.btn_1);
            placeOpToBtnAndSetClickListener(newBtn1,op);
            return newBtnPairGroup;
        }else{
            if(btn1Tag==null){
                placeOpToBtnAndSetClickListener(btn1, op);
            }else {
                placeOpToBtnAndSetClickListener(btn2, op);
            }
            return null;
        }

    }

    /**
     * 给一个按钮设置一个操作选项类型,并且设置点击监听
     */
    private void placeOpToBtnAndSetClickListener(Button btn,WayBillBtnOp op){
        btn.setTag(op);
        btn.setText(op.getText());
        btn.setOnClickListener(bottomBtnClickListener);
    }



    @Override
    public void onError(ApiException e) {
        super.onError(e);
        switch (neType) {
            case 1:
                loadFailTipHelper.showFailTipView();
                break;
        }
    }

    @OnClick({R.id.iv_back})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }



    /**
     * 请求运单详情
     */
    private void requestWayBillDetail(){
        neType = 1;

        GetWayBillDetailApi api = new GetWayBillDetailApi();
        api.setHeader(app.getAuthorization());
        api.setUserHeader(app.getUserHeader());

        api.setId(wayBillId);

        pClass.startHttpRequest(this,api);
    }

    /**
     * 创建 确认达到装货地 的接口请求对象
     */
    private ChangeWayBillStatusApi buildConfirmArriveLoadPlace(){
        ChangeWayBillStatusApi api = buildChangeWayBillStatusApi();
        api.setChangeToStatus(2);
        return api;
    }


    /**
     * 创建 确认发车 的接口请求对象
     */
    private ChangeWayBillStatusApi buildConfirmStartOff(){
        ChangeWayBillStatusApi api = buildChangeWayBillStatusApi();
        api.setChangeToStatus(3);
        return api;
    }


    /**
     * 创建 确认到达目的地 的接口请求对象
     */
    private ChangeWayBillStatusApi buildConfirmArriveDest(){
        ChangeWayBillStatusApi api = buildChangeWayBillStatusApi();
        api.setChangeToStatus(4);
        return api;
    }

    /**
     * 创建 确认到达目的地 的接口请求对象
     */
    private ChangeWayBillStatusApi buildConfirmGoodsArrive(){
        ChangeWayBillStatusApi api = buildChangeWayBillStatusApi();
        api.setChangeToStatus(12);
        return api;
    }


    private ChangeWayBillStatusApi buildChangeWayBillStatusApi(){
        ChangeWayBillStatusApi api = new ChangeWayBillStatusApi();
        api.setHeader(app.getAuthorization());
        api.setUserHeader(app.getUserHeader());
        api.setId(detail.getFormDetail().getId());
        return api;
    }

    /**
     * 显示确认提示对话框
     * @param tipText 要显示的提示文字
     * @param shouldReqApi 点击"是"按钮后,要请求的接口
     */
    private void showConfirmTipDialog(String tipText, final BaseApi shouldReqApi,final WayBillBtnOp op){
        ChoiceDialogTool.showDialog(this, tipText,
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
                            pClass.startHttpRequest(WayBillDetailActivity.this,shouldReqApi);
                        }
                    }
                });
    }



    private ProofPhotoUploadDialog currentOperatePhotoDialog;//当前正在操作的上传照片的dialog

    private ProofPhotoUploadDialog confirmStartOffPhotoDialog;//确认发车上传照片dialog

    private void showUploadConfirmStartOffPhotoDialog(final BaseApi shouldReqApi){
        if(confirmStartOffPhotoDialog==null){
            confirmStartOffPhotoDialog = new ProofPhotoUploadDialog(this,this);
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
                pClass.startHttpRequest(WayBillDetailActivity.this,api);
            }
        });

        currentOperatePhotoDialog = confirmStartOffPhotoDialog;
        confirmStartOffPhotoDialog.show();
    }


    private ProofPhotoUploadDialog confirmGoodsArrivePhotoDialog;//确认到货上传照片dialog

    private void showUploadGoodsArrivePhotoDialog(final BaseApi shouldReqApi){
        if(confirmGoodsArrivePhotoDialog==null){
            confirmGoodsArrivePhotoDialog = new ProofPhotoUploadDialog(this,this);
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
                pClass.startHttpRequest(WayBillDetailActivity.this,api);
            }
        });

        currentOperatePhotoDialog = confirmGoodsArrivePhotoDialog;
        confirmGoodsArrivePhotoDialog.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(currentOperatePhotoDialog!=null && currentOperatePhotoDialog.isShowing()){
            currentOperatePhotoDialog.onActivityResult(requestCode,resultCode,data);
        }
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
        pClass.startHttpRequest(this, uploadImgApi);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().post(new RefreshWayBillListEvent());
    }
}
