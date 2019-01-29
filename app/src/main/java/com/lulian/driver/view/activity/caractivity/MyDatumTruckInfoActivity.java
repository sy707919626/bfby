package com.lulian.driver.view.activity.caractivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;
import com.lulian.driver.R;
import com.lulian.driver.base.BaseActivity;
import com.lulian.driver.entity.AllowCarType;
import com.lulian.driver.entity.api.GetMyInfoApi;
import com.lulian.driver.entity.api.SaveDriverInfoStep2Api;
import com.lulian.driver.entity.api.UploadFileApi;
import com.lulian.driver.entity.server.MyTruckInfoBean;
import com.lulian.driver.entity.server.resulte.CarType;
import com.lulian.driver.entity.server.resulte.MyInfoBean;
import com.lulian.driver.entity.server.resulte.SaveDriverInfoStepResult;
import com.lulian.driver.entity.server.resulte.UploadFileResult;
import com.lulian.driver.utils.ProjectUtil;
import com.lulian.driver.utils.feature.InfoDisplayTool;
import com.lulian.driver.utils.feature.LoadFailTipHelper;
import com.lulian.driver.view.DictDataTool;
import com.lulian.driver.view.dialog.AllowCarTypeSelectDialog;
import com.lulian.driver.view.dialog.PhotoDisplayDialog;
import com.lulian.driver.view.dialog.TruckTypeLenSelectDialog;
import com.lulian.driver.view.wheel.ClearEditText;
import com.rxretrofitlibrary.retrofit_rx.exception.ApiException;
import com.rxretrofitlibrary.retrofit_rx.utils.GlobalValue;
import com.rxretrofitlibrary.retrofit_rx.utils.GsonUtil;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的资料车辆信息界面
 * @author hxb
 */
public class MyDatumTruckInfoActivity extends BaseActivity implements AdapterView.OnItemClickListener,
        TakePhoto.TakeResultListener, InvokeListener {

    @BindView(R.id.text_content)
    TextView textContent;
    @BindView(R.id.title_txt_rightbottom)
    TextView titleTxtRightBottom;

    @BindView(R.id.cet_plate_no)
    ClearEditText cetPlateNo; //车牌号码输入
    @BindView(R.id.tv_truck_type_len)
    TextView tvTruckTypeLen;//显示选择的车型车长
    @BindView(R.id.tv_lic_allow_car_type)
    TextView tvLicAllowCarType;//显示选择的驾驶证准驾车型

    /**
     * 照片上传成功后,服务端返回的图片url暂存在这里
     * key:拍照框可点击View的id,value:对应拍照框图片上传成功后图片的url;
     */
    private SparseArray<String> uploadedPhotoUrl = new SparseArray<>();


    /**
     * 展示照片的iv
     * key:拍照框可点击View的id, value: 拍照框可点击View对应的显示图片的ImageView
     */
    private SparseArray<ImageView> ivArrayPhoto = new SparseArray<>();

    /**
     * 记录当前点击的照片框(可点击View的id)
     */
    private int currentClickPhotoStubId;


    private TakePhoto takePhoto;
    InvokeParam invokeParam;
    private LoadFailTipHelper loadFailTipHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initLoadFailTipHelper();
        setContentView(loadFailTipHelper.getWrapperView());

        ButterKnife.bind(this);

        initView();

        requestGetMyInfo();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getTakePhoto().onSaveInstanceState(outState);
    }

    private void initLoadFailTipHelper(){
        loadFailTipHelper = new LoadFailTipHelper(this, R.layout.activity_my_datum_truck_info);
        loadFailTipHelper.setCallback(new LoadFailTipHelper.Callback() {
            @Override
            public void onReloadClicked() {
                requestGetMyInfo();
            }
        });
    }


    private void initView(){
        textContent.setText("车辆信息");
        titleTxtRightBottom.setVisibility(View.VISIBLE);
        titleTxtRightBottom.setText("重新绑定");

        initPhotoIvArray();
        initPhotoDialog();
    }


    private void initPhotoIvArray() {
        ivArrayPhoto.put(R.id.vg_driving_lic_front_take_click_stub, (ImageView) findViewById(R.id.iv_driving_lic_front));
        ivArrayPhoto.put(R.id.vg_insure_card_front_take_click_stub, (ImageView) findViewById(R.id.iv_insure_card_front));
        ivArrayPhoto.put(R.id.vg_truck_body_take_click_stub, (ImageView) findViewById(R.id.iv_truck_body));
        ivArrayPhoto.put(R.id.vg_operation_lic_take_click_stub, (ImageView) findViewById(R.id.iv_operation_lic));
        ivArrayPhoto.put(R.id.vg_purchase_tax_take_click_stub, (ImageView) findViewById(R.id.iv_purchase_tax));
    }

    private PhotoDisplayDialog photoDialog;//显示完成照片的dialog

    private void initPhotoDialog() {
        photoDialog = new PhotoDisplayDialog(this);
        photoDialog.setCallback(photoDialogCallback);
    }

    /**
     * 图片展示dialog的回调
     */
    private PhotoDisplayDialog.Callback photoDialogCallback = new PhotoDisplayDialog.Callback() {
        @Override
        public void onShootAgainClicked() {//点击了重新选择/拍摄 按钮
            ProjectUtil.showUploadFileDialog(MyDatumTruckInfoActivity.this, MyDatumTruckInfoActivity.this);
        }
    };


    @OnClick({R.id.img_return,
            R.id.title_txt_rightbottom,
            R.id.vg_truck_type_len_click_stub,
            R.id.vg_lic_allow_car_type_click_stub})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_return:
                this.finish();
                break;

            case R.id.title_txt_rightbottom://点击重新绑定
                if(checkInputIsCorrect()){
//                    ProjectUtil.show(this,"校验通过");
                    requestSaveMyTruckInfo();
                }

                break;
            case R.id.vg_truck_type_len_click_stub://点击选择车型车长

                showTruckTypeLenSelectDialog();

                break;
            case R.id.vg_lic_allow_car_type_click_stub://点击选择驾驶证准驾车型

                showAllowCarTypeSelectDialog();

                break;

        }
    }



    private TruckTypeLenSelectDialog truckTypeLenDialog;//车长车型选择对话框

    /*
     * 记录已选择的车长车型载重
     */
    private CarType selectedTruckType;
    private String selectedTruckLen;
    private String selectedLoadWeight;

    /**
     * 弹出车长车型选择对话框
     */
    private void showTruckTypeLenSelectDialog(){
        if(truckTypeLenDialog ==null){
            truckTypeLenDialog = new TruckTypeLenSelectDialog(this, app);
            truckTypeLenDialog.setCallback(new TruckTypeLenSelectDialog.Callback() {
                @Override
                public void onSelectConfirm(CarType truckType, String truckLen, String loadWeight) {
                    //确认选择
                    selectedTruckType = truckType;
                    selectedTruckLen= truckLen;
                    selectedLoadWeight = loadWeight;
                    //将信息显示到界面
                    String assembledInfo = InfoDisplayTool.assemble_truckType_truckLength_weight(selectedTruckType.getText(), selectedTruckLen, selectedLoadWeight);
                    tvTruckTypeLen.setText(assembledInfo);
                }
            });
        }

        truckTypeLenDialog.show();
    }


    private AllowCarTypeSelectDialog allowCarTypeSelectDialog;//准驾车型选择对话框

    private AllowCarType selectedAllowCarType;//已选择的准驾车型

    private void showAllowCarTypeSelectDialog(){
        if(allowCarTypeSelectDialog == null){
            allowCarTypeSelectDialog = new AllowCarTypeSelectDialog(this);
            allowCarTypeSelectDialog.setCallback(new AllowCarTypeSelectDialog.Callback() {
                @Override
                public void onAllowCarTypeSelected(AllowCarType allowCarType) {
                    selectedAllowCarType = allowCarType;
                    //将信息显示到界面
                    tvLicAllowCarType.setText(selectedAllowCarType.getCode()+" "+selectedAllowCarType.getText());
                }
            });
        }

        allowCarTypeSelectDialog.show();
    }


    private boolean checkInputIsCorrect(){
        String plateNo = cetPlateNo.getText().toString().trim();
        boolean plateNoComplete = !TextUtils.isEmpty(plateNo);

        if (!plateNoComplete ||
                selectedTruckType == null || selectedTruckLen == null || selectedLoadWeight == null ||
                selectedAllowCarType == null) {

            ProjectUtil.show(this,"请将必填项填写完整");
            return false;
        }

        return true;
    }

    /**
     * 照片框框点击监听
     */
    @OnClick({R.id.vg_driving_lic_front_take_click_stub,
            R.id.vg_insure_card_front_take_click_stub,
            R.id.vg_truck_body_take_click_stub,
            R.id.vg_operation_lic_take_click_stub,
            R.id.vg_purchase_tax_take_click_stub})
    public void onPhotoStubClicked(View v) {
        currentClickPhotoStubId = v.getId();

        String url = uploadedPhotoUrl.get(currentClickPhotoStubId);
        if (TextUtils.isEmpty(url)) {
            ProjectUtil.showUploadFileDialog(this, this);
        } else {
            photoDialog.setImgUrl(GlobalValue.BASEURL + url).show();
        }
    }


    /**
     * 将url所表示的图片显示到iv上
     */
    private void displayPhotoByUri(ImageView iv, String uri){
        if(!TextUtils.isEmpty(uri)){
            iv.setVisibility(View.VISIBLE);
            Picasso.get().load(uri).into(iv);
        }
    }

    @Override
    public void onSuccess(String data) {
        switch (neType) {
            case 1: //上传图片成功
                UploadFileResult result= GsonUtil.get().fromJson(data, UploadFileResult.class);

                //将上传成功返回的图片url记录下来
                uploadedPhotoUrl.put(currentClickPhotoStubId,result.getUrl());

                //将上传成功的图片,显示到对应的iv上
                ImageView iv = ivArrayPhoto.get(currentClickPhotoStubId);
                displayPhotoByUri(iv,"file://"+currentTookPhotoPath);

                break;
            case 2://获取我的资料数据成功
                performGetMyInfoSuccess(data);
                break;
            case 3://保存我的车辆信息成功
                SaveDriverInfoStepResult result2 = GsonUtil.get().fromJson(data, SaveDriverInfoStepResult.class);
                //将完成状态字段保存到AppData中
                app.setIsComplete(result2.getIsComplete());
                ProjectUtil.show(this,"保存成功");
                finish();
                break;
        }

    }

    @Override
    public void onError(ApiException e) {
        super.onError(e);
        switch (neType) {
            case 2://获取我的资料数据失败
                loadFailTipHelper.showFailTipView();
                break;
        }
    }


    private String truckBeanId;//请求接口保存车辆信息的时候要把这个id带过去

    private void performGetMyInfoSuccess(String data){
        loadFailTipHelper.showContentView();

        /*
         * 显示接口请求到的车辆信息数据
         */
        MyInfoBean myInfo=GsonUtil.get().fromJson(data, MyInfoBean.class);
        List<MyTruckInfoBean> truckList = myInfo.getVehicles();
        if(truckList.size()<=0){
            return;
        }
        MyTruckInfoBean truckInfo = truckList.get(0);

        truckBeanId = truckInfo.getId();

        String plateNo = truckInfo.getPlateNo();//车牌
        cetPlateNo.setText(plateNo);

        selectedTruckType=new CarType();//车型
        selectedTruckType.setId(truckInfo.getVehicleType());
        selectedTruckType.setText(truckInfo.getVehicleTypeName());

        selectedTruckLen=String.valueOf(truckInfo.getVehicleLength());//车长

        selectedLoadWeight = String.valueOf(truckInfo.getVehicleWeight());//载重

        String assembledInfo = InfoDisplayTool.assemble_truckType_truckLength_weight(selectedTruckType.getText(),
                selectedTruckLen, selectedLoadWeight);
        tvTruckTypeLen.setText(assembledInfo);

        //准驾车型
        Map<String, AllowCarType> allowCarTypeMap = DictDataTool.getLicAllowCarTypeMap();
        selectedAllowCarType = allowCarTypeMap.get(truckInfo.getDrivingModel());
        if(selectedAllowCarType!=null){
            tvLicAllowCarType.setText(selectedAllowCarType.getCode()+" "+selectedAllowCarType.getText());
        }

        //照片url
        uploadedPhotoUrl.put(R.id.vg_driving_lic_front_take_click_stub,truckInfo.getLicUrl());
        uploadedPhotoUrl.put(R.id.vg_insure_card_front_take_click_stub,truckInfo.getInsuranceUrl());
        uploadedPhotoUrl.put(R.id.vg_truck_body_take_click_stub,truckInfo.getVPicUrl());
        uploadedPhotoUrl.put(R.id.vg_operation_lic_take_click_stub,truckInfo.getBizLicUrl());
        uploadedPhotoUrl.put(R.id.vg_purchase_tax_take_click_stub,truckInfo.getTaxUrl());

        for(int i=0;i<uploadedPhotoUrl.size();i++){
            int stubId = uploadedPhotoUrl.keyAt(i);
            String uri = uploadedPhotoUrl.get(stubId);
            ImageView iv = ivArrayPhoto.get(stubId);
            displayPhotoByUri(iv, uri != null ? GlobalValue.BASEURL + uri : null);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                goToTakePhoto(true);
                break;
            case 1:
                goToTakePhoto(false);
                break;
        }
    }

    private String currentTookPhotoPath;

    @Override
    public void takeSuccess(TResult result) {
        currentTookPhotoPath = result.getImage().getOriginalPath();
        requestUploadFile();
    }


    /**
     * 请求上传文件
     */
    private void requestUploadFile() {
        neType = 1;
        File imgFile = new File(currentTookPhotoPath);
        UploadFileApi uploadImgApi = new UploadFileApi();
        uploadImgApi.setHeader(app.getAuthorization());
        uploadImgApi.setUserHeader(app.getUserHeader());
        uploadImgApi.setImg(imgFile);
        pClass.startHttpRequest(this, uploadImgApi);
    }


    /**
     * 请求 获取我的信息
     */
    private void requestGetMyInfo(){
        neType = 2;
        GetMyInfoApi api = new GetMyInfoApi();
        api.setHeader(app.getAuthorization());
        api.setUserHeader(app.getUserHeader());

        pClass.startHttpRequest(this, api);
    }


    /**
     * 请求 保存我的车辆信息
     */
    private void requestSaveMyTruckInfo(){
        neType=3;
        SaveDriverInfoStep2Api api = new SaveDriverInfoStep2Api();
        api.setHeader(app.getAuthorization());
        api.setUserHeader(app.getUserHeader());

        MyTruckInfoBean bean = new MyTruckInfoBean();

        bean.setId(truckBeanId);

        String plateNo = cetPlateNo.getText().toString().trim();
        bean.setPlateNo(plateNo);
        bean.setVehicleType(selectedTruckType.getId());
        bean.setVehicleLength(Double.valueOf(selectedTruckLen));
        bean.setVehicleWeight(Double.valueOf(selectedLoadWeight));
        bean.setDrivingModel(selectedAllowCarType.getCode());

        bean.setLicUrl(uploadedPhotoUrl.get(R.id.vg_driving_lic_front_take_click_stub));
        bean.setInsuranceUrl(uploadedPhotoUrl.get(R.id.vg_insure_card_front_take_click_stub));
        bean.setVPicUrl(uploadedPhotoUrl.get(R.id.vg_truck_body_take_click_stub));
        bean.setBizLicUrl(uploadedPhotoUrl.get(R.id.vg_operation_lic_take_click_stub));
        bean.setTaxUrl(uploadedPhotoUrl.get(R.id.vg_purchase_tax_take_click_stub));

        api.setTruckInfoBean(bean);

        pClass.startHttpRequest(this, api);
    }

    @Override
    public void takeFail(TResult result, String msg) {

    }

    @Override
    public void takeCancel() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type = PermissionManager.checkPermission(TContextWrap.of(this), invokeParam.getMethod());
        if (PermissionManager.TPermissionType.WAIT.equals(type)) {
            this.invokeParam = invokeParam;
        }
        return type;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //以下代码为处理Android6.0、7.0动态权限所需
        PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handlePermissionsResult(this, type, invokeParam, this);
    }

    private void goToTakePhoto(boolean isTake) {
        ////获取TakePhoto实例
        takePhoto = getTakePhoto();
        //设置裁剪参数
        CropOptions cropOptions = new CropOptions.Builder().create();
        //设置压缩参数
        CompressConfig compressConfig = new CompressConfig.Builder().setMaxPixel(400).create();
        takePhoto.onEnableCompress(compressConfig, true);  //设置为需要压缩
        if (isTake) {
            takePhoto.onPickFromCaptureWithCrop(getImageCropUri(), cropOptions);
        } else {
            takePhoto.onPickFromGalleryWithCrop(getImageCropUri(), cropOptions);
        }
    }

    //获得照片的输出保存Uri
    private Uri getImageCropUri() {
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "/temp/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
        return Uri.fromFile(file);
    }

    /**
     * 获取TakePhoto实例
     */
    public TakePhoto getTakePhoto() {
        if (takePhoto == null) {
            takePhoto = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this, this));
        }
        return takePhoto;
    }
}
