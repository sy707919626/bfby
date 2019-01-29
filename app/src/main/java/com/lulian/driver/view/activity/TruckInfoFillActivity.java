package com.lulian.driver.view.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
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
import com.lulian.driver.entity.api.GetVehicleLengthListApi;
import com.lulian.driver.entity.api.GetVehicleTypeListApi;
import com.lulian.driver.entity.api.SaveDriverInfoApi;
import com.lulian.driver.entity.api.UploadFileApi;
import com.lulian.driver.entity.server.req.SaveDriverInfoBean;
import com.lulian.driver.entity.server.resulte.CarType;
import com.lulian.driver.entity.server.resulte.UploadFileResult;
import com.lulian.driver.utils.ActivityStorer;
import com.lulian.driver.utils.ProjectUtil;
import com.lulian.driver.utils.feature.InfoDisplayTool;
import com.lulian.driver.utils.feature.LoadFailTipHelper;
import com.lulian.driver.view.dialog.AllowCarTypeSelectDialog;
import com.lulian.driver.view.dialog.PhotoDisplayDialog;
import com.lulian.driver.view.dialog.TruckTypeLenSelectDialog;
import com.lulian.driver.view.wheel.ClearEditText;
import com.rxretrofitlibrary.retrofit_rx.exception.ApiException;
import com.rxretrofitlibrary.retrofit_rx.utils.GlobalValue;
import com.rxretrofitlibrary.retrofit_rx.utils.GsonUtil;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 注册,车辆信息填写界面
 * @author hxb
 */

public class TruckInfoFillActivity extends BaseActivity implements AdapterView.OnItemClickListener,
        TakePhoto.TakeResultListener, InvokeListener {
    @BindView(R.id.img_return)
    ImageView imgReturn;
    @BindView(R.id.text_content)
    TextView textContent;
    @BindView(R.id.title_txt_rightbottom)
    TextView titleTxtRightbottom;
    @BindView(R.id.title_txtrightbottomleft)
    TextView titleTxtrightbottomleft;


    @BindView(R.id.cet_plate_no)
    ClearEditText cetPlateNo; //车牌号码输入
    @BindView(R.id.tv_truck_type_len)
    TextView tvTruckTypeLen;//显示选择的车型车长
    @BindView(R.id.tv_lic_allow_car_type)
    TextView tvLicAllowCarType;//显示选择的驾驶证准驾车型

    @BindView(R.id.cb_agree)
    CheckBox cbAgree; //阅读并同意勾选框


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
    private LoadFailTipHelper loadTipHelper;


    //保存司机信息接口的请求参数对象
    private SaveDriverInfoBean saveDriverInfoBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getTakePhoto().onCreate(savedInstanceState);

        loadTipHelper = new LoadFailTipHelper(this, R.layout.activity_truck_info_fill);
        loadTipHelper.setCallback(loadFailTipCallback);
        setContentView(loadTipHelper.getWrapperView());

        ButterKnife.bind(this);

        ActivityStorer.add(this);

        initData();
        initView();

        initRequest();
    }

    LoadFailTipHelper.Callback loadFailTipCallback = new LoadFailTipHelper.Callback() {
        @Override
        public void onReloadClicked() {
            initRequest();
        }
    };


    private void initRequest(){
        List<CarType> list = app.getTrucktypeList();
        if(list==null){
            requestTruckTypeList();
        }else{
            loadTipHelper.showContentView();
        }
    }


    private void initData(){
        saveDriverInfoBean= (SaveDriverInfoBean) getIntent().getSerializableExtra("saveDriverInfoBean");
    }

    private void initView(){
        textContent.setText("车辆信息");
        initPhotoIvArray();
        initPhotoDialog();
    }


    /**
     * 请求车型列表数据
     */
    private void requestTruckTypeList(){
        neType = 3;
        GetVehicleTypeListApi api = new GetVehicleTypeListApi();
        api.setHeader(app.getAuthorization());
        api.setUserHeader(app.getUserHeader());
        pClass.startHttpRequest(this,api);
    }

    /**
     * 请求车长列表数据
     */
    private void requestTruckLenList(){
        neType = 4;
        GetVehicleLengthListApi api = new GetVehicleLengthListApi();
        api.setHeader(app.getAuthorization());
        api.setUserHeader(app.getUserHeader());
        pClass.startHttpRequest(this,api);
    }

    private void initPhotoIvArray() {
        ivArrayPhoto.put(R.id.vg_driving_lic_front_take_click_stub, (ImageView) findViewById(R.id.iv_driving_lic_front));
        ivArrayPhoto.put(R.id.vg_insure_card_front_take_click_stub, (ImageView) findViewById(R.id.iv_insure_card_front));
        ivArrayPhoto.put(R.id.vg_truck_body_take_click_stub, (ImageView) findViewById(R.id.iv_truck_body));
        ivArrayPhoto.put(R.id.vg_operation_lic_take_click_stub, (ImageView) findViewById(R.id.iv_operation_lic));
        ivArrayPhoto.put(R.id.vg_purchase_tax_take_click_stub, (ImageView) findViewById(R.id.iv_purchase_tax));
    }


    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        getTakePhoto().onSaveInstanceState(outState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getTakePhoto().onActivityResult(requestCode, resultCode, data);

    }


    @OnClick({R.id.img_return,
            R.id.btn_commit,
            R.id.vg_truck_type_len_click_stub,
            R.id.vg_lic_allow_car_type_click_stub})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_return:
                this.finish();
                break;

            case R.id.btn_commit://点击上传资料
                if(checkInputIsCorrect()){
//                    ProjectUtil.show(this,"校验通过");
                    requestSaveDriverInfo();
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


    private boolean checkInputIsCorrect(){
        String plateNo = cetPlateNo.getText().toString().trim();
        boolean plateNoComplete = !TextUtils.isEmpty(plateNo);

        if (!plateNoComplete ||
                selectedTruckType == null || selectedTruckLen == null || selectedLoadWeight == null ||
                selectedAllowCarType == null) {

            ProjectUtil.show(this,"请将必填项填写完整");
            return false;
        }

        if(!cbAgree.isChecked()){
            ProjectUtil.show(this,"请勾选阅读并同意");
            return false;
        }

        return true;
    }


    /**
     * 请求保存司机完善信息
     */
    private void requestSaveDriverInfo(){
        neType = 2;
        SaveDriverInfoApi api = new SaveDriverInfoApi();
        api.setHeader(app.getAuthorization());
        api.setUserHeader(app.getUserHeader());

        String plateNo = cetPlateNo.getText().toString().trim();

        saveDriverInfoBean.setUserId(app.getUserId());
        saveDriverInfoBean.setPlateNo(plateNo);
        saveDriverInfoBean.setVehicleType(selectedTruckType.getId());
        saveDriverInfoBean.setVehicleLength(Double.valueOf(selectedTruckLen));
        saveDriverInfoBean.setVehicleWeight(Double.valueOf(selectedLoadWeight));
        saveDriverInfoBean.setDrivingModel(selectedAllowCarType.getCode());


        saveDriverInfoBean.setLicUrl(uploadedPhotoUrl.get(R.id.vg_driving_lic_front_take_click_stub));
        saveDriverInfoBean.setInsuranceUrl(uploadedPhotoUrl.get(R.id.vg_insure_card_front_take_click_stub));
        saveDriverInfoBean.setVPicUrl(uploadedPhotoUrl.get(R.id.vg_truck_body_take_click_stub));
        saveDriverInfoBean.setBizLicUrl(uploadedPhotoUrl.get(R.id.vg_operation_lic_take_click_stub));
        saveDriverInfoBean.setTaxUrl(uploadedPhotoUrl.get(R.id.vg_purchase_tax_take_click_stub));

        api.setSaveDriverInfoBean(saveDriverInfoBean);

        pClass.startHttpRequest(this,api);
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
            ProjectUtil.showUploadFileDialog(TruckInfoFillActivity.this, TruckInfoFillActivity.this);
        }
    };


    @Override
    public void onSuccess(String data) {
        switch (neType){
            case 1: //上传图片成功
                UploadFileResult result= GsonUtil.get().fromJson(data, UploadFileResult.class);

                //将上传成功返回的图片url记录下来
                uploadedPhotoUrl.put(currentClickPhotoStubId,result.getUrl());

                //将上传成功的图片,显示到对应的iv上
                ImageView iv = ivArrayPhoto.get(currentClickPhotoStubId);
                iv.setVisibility(View.VISIBLE);
                Picasso.get().load("file://"+currentTookPhotoPath).into(iv);

                break;
            case 2://保存司机信息成功
                ProjectUtil.show(this,data);

                ActivityStorer.finishAll();
                startActivity(new Intent(this,LoginActivity.class));//跳转到登录界面
                break;
            case 3://请求车型列表成功
                List<CarType> truckTypeList= GsonUtil.get().fromJson(data,new TypeToken<ArrayList<CarType>>(){}.getType());
                app.setCartypeLsit(truckTypeList);
                requestTruckLenList();
                break;
            case 4://请求车长列表成功
                List<String> truckLenList=GsonUtil.get().fromJson(data,new TypeToken<ArrayList<String>>(){}.getType());
                app.setCarLenghtList(truckLenList);
                loadTipHelper.showContentView();
                break;

        }
    }


    @Override
    public void onError(ApiException e) {
        super.onError(e);
        switch (neType) {
            case 3:
            case 4://请求车型车长失败
                loadTipHelper.showFailTipView();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                initPhotoData(true);
                break;
            case 1:
                initPhotoData(false);
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


    @Override
    public void takeFail(TResult result, String msg) {

    }

    @Override
    public void takeCancel() {

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

    private void initPhotoData(boolean isTake) {
        ////获取TakePhoto实例
        takePhoto = getTakePhoto();
        //设置裁剪参数
        CropOptions cropOptions = new CropOptions.Builder().create();
        //设置压缩参数
        CompressConfig compressConfig = new CompressConfig.Builder().setMaxSize(50 * 1024).setMaxPixel(800).create();
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
