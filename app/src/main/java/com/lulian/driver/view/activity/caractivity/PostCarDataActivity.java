package com.lulian.driver.view.activity.caractivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
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
import com.lulian.driver.entity.api.UploadImgApi;
import com.lulian.driver.entity.server.resulte.ExceptionFile;
import com.lulian.driver.utils.ProjectUtil;
import com.lulian.driver.view.wheel.ClearEditText;
import com.rxretrofitlibrary.retrofit_rx.exception.ApiException;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.squareup.picasso.MemoryPolicy.NO_CACHE;
import static com.squareup.picasso.MemoryPolicy.NO_STORE;

/**
 * Created by MARK on 2018/6/8.
 */

public class PostCarDataActivity extends BaseActivity implements AdapterView.OnItemClickListener,
        TakePhoto.TakeResultListener, InvokeListener {


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
    @BindView(R.id.car_edit_plateNo)
    ClearEditText carEditPlateNo;
    @BindView(R.id.carmsg_txt_title_licUrl)
    TextView carmsgTxtTitleLicUrl;
    @BindView(R.id.carmsg_img_front_licUrl)
    ImageView carmsgImgFrontLicUrl;
    @BindView(R.id.carmsg_layout_reverse_licUrl)
    TextView carmsgLayoutReverseLicUrl;
    @BindView(R.id.carmsg_layout_licUrl)
    LinearLayout carmsgLayoutLicUrl;
    @BindView(R.id.carmsg_txt_title_insurance)
    TextView carmsgTxtTitleInsurance;
    @BindView(R.id.carmsg_img_front_insurance)
    ImageView carmsgImgFrontInsurance;
    @BindView(R.id.carmsg_layout_reverse_InsuranceUrl)
    TextView carmsgLayoutReverseInsuranceUrl;
    @BindView(R.id.carmsg_layout_insuranceUrl)
    LinearLayout carmsgLayoutInsuranceUrl;
    @BindView(R.id.carmsg_txt_title_VPicUrl)
    TextView carmsgTxtTitleVPicUrl;
    @BindView(R.id.carmsg_img_front_VPicUrl)
    ImageView carmsgImgFrontVPicUrl;
    @BindView(R.id.carmsg_layout_reverse_VPicUrl)
    TextView carmsgLayoutReverseVPicUrl;
    @BindView(R.id.carmsg_layout_VPicUrl)
    LinearLayout carmsgLayoutVPicUrl;
    @BindView(R.id.carmsg_txt_title_BizLicUrl)
    TextView carmsgTxtTitleBizLicUrl;
    @BindView(R.id.carmsg_img_front_BizLicUrl)
    ImageView carmsgImgFrontBizLicUrl;
    @BindView(R.id.carmsg_layout_reverse_BizLicUrl)
    TextView carmsgLayoutReverseBizLicUrl;
    @BindView(R.id.carmsg_layout_BizLicUrl)
    LinearLayout carmsgLayoutBizLicUrl;
    @BindView(R.id.carmsg_txt_title_TaxUrl)
    TextView carmsgTxtTitleTaxUrl;
    @BindView(R.id.carmsg_img_front_TaxUrl)
    ImageView carmsgImgFrontTaxUrl;
    @BindView(R.id.carmsg_layout_reverse_TaxUrl)
    TextView carmsgLayoutReverseTaxUrl;
    @BindView(R.id.carmsg_layout_TaxUrl)
    LinearLayout carmsgLayoutTaxUrl;
    CheckBox carCheckAgree;
    @BindView(R.id.car_txt_doc)
    TextView carTxtDoc;
    Button per2BtnNext;

    private TakePhoto takePhoto;
    private String iconPath;
    private int upLoadType;//0,行驶证正页;1:保险卡正页;2:车辆全身照;3:运营证;4:购置税证

    private ExceptionFile excFile1;
    private ExceptionFile excFile2;
    private ExceptionFile excFile3;
    private ExceptionFile excFile4;
    private ExceptionFile excFile5;
    ArrayList<ExceptionFile> exFileList;


    private String licUrl = "";
    private String insuranceUrl = "";
    private String vPicUrl = "";
    private String taxUrl = "";
    private String bizLicUrl = "";

    private String mCarVehicleLength = ""; //车长
    private String mCarVehicleType = "";  //车型
    private int netType;//0:上传图片;1:下一步

    private String imgType;
    InvokeParam invokeParam;
    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String realPath = "file://" + iconPath;
            if (upLoadType == 0) {
                Picasso.get().load(realPath).memoryPolicy(NO_CACHE, NO_STORE).into(carmsgImgFrontLicUrl);
            } else if (upLoadType == 1) {
                Picasso.get().load(realPath).memoryPolicy(NO_CACHE, NO_STORE).into(carmsgImgFrontInsurance);
            } else if (upLoadType == 2) {
                Picasso.get().load(realPath).memoryPolicy(NO_CACHE, NO_STORE).into(carmsgImgFrontVPicUrl);
            } else if (upLoadType == 3) {
                Picasso.get().load(realPath).memoryPolicy(NO_CACHE, NO_STORE).into(carmsgImgFrontBizLicUrl);
            } else if (upLoadType == 4) {
                Picasso.get().load(realPath).memoryPolicy(NO_CACHE, NO_STORE).into(carmsgImgFrontTaxUrl);
            }
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getTakePhoto().onCreate(savedInstanceState);
        setContentView(R.layout.activity_truck_info_fill);
        ButterKnife.bind(this);
        initView();
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

        if (requestCode == 1 && resultCode == 4) {
            mCarVehicleType = data.getStringExtra("cartype");
            mCarVehicleLength = data.getStringExtra("carlength");
        }

        if (resultCode == 2) {
            if (requestCode == 1) {
                boolean agree = data.getBooleanExtra("agree",false);
                carCheckAgree.setChecked(agree);
            }
        }
    }

    private void initView() {
        textContent.setText("车辆信息");
        per2BtnNext.setText("提交审核");
        carmsgTxtTitleLicUrl.setText("上传清晰的行驶证正页");
        carmsgLayoutReverseLicUrl.setText("上传行驶证");
        carmsgTxtTitleInsurance.setText("上传保险卡正页");
        carmsgLayoutReverseInsuranceUrl.setText("上传保险卡");
        carmsgTxtTitleVPicUrl.setText("上传车辆全身照");
        carmsgLayoutReverseVPicUrl.setText("上传车辆全身照");
        carmsgTxtTitleBizLicUrl.setText("上传运营");
        carmsgLayoutReverseBizLicUrl.setText("上传运营证");
        carmsgTxtTitleTaxUrl.setText("上传车辆购置税");
        carmsgLayoutReverseTaxUrl.setText("上传车辆购置税");

    }

    @OnClick({R.id.img_return, R.id.carmsg_layout_licUrl,
            R.id.carmsg_layout_insuranceUrl,
            R.id.carmsg_layout_VPicUrl,R.id.car_txt_doc,
            R.id.carmsg_layout_BizLicUrl, R.id.carmsg_layout_TaxUrl,
            })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_return:
                this.finish();
                break;
            case R.id.car_txt_doc:
                startActivityForResult(new Intent(this, ProtocolActivity.class),1);
                break;
            case R.id.carmsg_layout_licUrl:
                imgType = "6";
                upLoadType = 0;
                netType = 0;
                ProjectUtil.showUploadFileDialog(this, this);
                break;
            case R.id.carmsg_layout_insuranceUrl:
                imgType = "7";
                upLoadType = 1;
                netType = 0;
                ProjectUtil.showUploadFileDialog(this, this);
                break;
            case R.id.carmsg_layout_VPicUrl:
                imgType = "8";
                upLoadType = 2;
                netType = 0;
                ProjectUtil.showUploadFileDialog(this, this);
                break;
            case R.id.carmsg_layout_BizLicUrl:
                imgType = "9";
                upLoadType = 3;
                netType = 0;
                ProjectUtil.showUploadFileDialog(this, this);
                break;
            case R.id.carmsg_layout_TaxUrl:
                imgType = "10";
                upLoadType = 4;
                netType = 0;
                ProjectUtil.showUploadFileDialog(this, this);
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

    @Override
    public void onSuccess(String data) {
        JSONObject jsonObject = JSONObject.parseObject(data);
        String state = jsonObject.getString("state");
        if (netType == 0) {
            if (state.equals("1")) {
                ProjectUtil.show(this, jsonObject.getString("msg"));
                JSONObject object = jsonObject.getJSONObject("data");
                String pId = object.getString("Id");
                String pUrl = object.getString("Url");
                String FileName = object.getString("FileName");
                switch (upLoadType) {
                    case 0:
                        licUrl = pUrl;
                        excFile1 = new ExceptionFile();
                        excFile1.setFileName(FileName);
                        excFile1.setId(pId);
                        excFile1.setUrl(pUrl);
                        exFileList.add(excFile1);

                        mHandler.sendEmptyMessage(1);
                        break;
                    case 1:
                        insuranceUrl = pUrl;

                        excFile2 = new ExceptionFile();
                        excFile2.setFileName(FileName);
                        excFile2.setId(pId);
                        excFile2.setUrl(pUrl);
                        exFileList.add(excFile2);
                        mHandler.sendEmptyMessage(1);
                        break;
                    case 2:
                        vPicUrl = pUrl;

                        excFile3 = new ExceptionFile();
                        excFile3.setFileName(FileName);
                        excFile3.setId(pId);
                        excFile3.setUrl(pUrl);
                        exFileList.add(excFile3);

                        mHandler.sendEmptyMessage(1);
                        break;
                    case 3:
                        taxUrl = pUrl;

                        excFile4 = new ExceptionFile();
                        excFile4.setFileName(FileName);
                        excFile4.setId(pId);
                        excFile4.setUrl(pUrl);
                        exFileList.add(excFile4);

                        mHandler.sendEmptyMessage(1);
                        break;
                    case 4:
                        bizLicUrl = pUrl;

                        excFile5 = new ExceptionFile();
                        excFile5.setFileName(FileName);
                        excFile5.setId(pId);
                        excFile5.setUrl(pUrl);
                        exFileList.add(excFile5);

                        mHandler.sendEmptyMessage(1);
                        break;
                }
            }
        } else if (netType == 1) {

            if (state.equals("1")) {
            }
        }
    }


    @Override
    public void onError(ApiException e) {
        super.onError(e);
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

    @Override
    public void takeSuccess(TResult result) {
        iconPath = result.getImage().getOriginalPath();
        File imgFile = new File(iconPath);
        UploadImgApi uploadImgApi = new UploadImgApi();
        uploadImgApi.setHeader(app.getAuthorization());
        uploadImgApi.setUserHeader(app.getUserHeader());
        uploadImgApi.setImg(imgFile);
        uploadImgApi.setFileType(imgType);
        pClass.startHttpRequest(PostCarDataActivity.this, uploadImgApi);
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

    private void initPhotoData(boolean istake) {
        ////获取TakePhoto实例
        takePhoto = getTakePhoto();
        //设置裁剪参数
        CropOptions cropOptions = new CropOptions.Builder().setAspectX(1).setAspectY(1).setWithOwnCrop(false).create();
        //设置压缩参数
        CompressConfig compressConfig = new CompressConfig.Builder().setMaxSize(50 * 1024).setMaxPixel(800).create();
        takePhoto.onEnableCompress(compressConfig, true);  //设置为需要压缩
        if (istake) {
            takePhoto.onPickFromCaptureWithCrop(getImageCropUri(), cropOptions);
        } else {
            takePhoto.onPickFromGalleryWithCrop(getImageCropUri(), cropOptions);
        }
    }

    //获得照片的输出保存Uri
    private Uri getImageCropUri() {
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "/temp/" + System.currentTimeMillis() + ".jpg");
//        ProjectUtil.show(this,file.getPath());
        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
        return Uri.fromFile(file);
    }

    /**
     * 获取TakePhoto实例
     *
     * @return
     */
    public TakePhoto getTakePhoto() {
        if (takePhoto == null) {
            takePhoto = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this, this));
        }
        return takePhoto;
    }

    private void requestWritePermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
    }
}
