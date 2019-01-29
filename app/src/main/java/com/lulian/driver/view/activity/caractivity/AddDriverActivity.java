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
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.hedgehog.ratingbar.RatingBar;
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
import com.lulian.driver.base.CheckPermissionsActivity;
import com.lulian.driver.entity.api.AddInviteDriverLeaderApi;
import com.lulian.driver.entity.api.DriverInfoPhoneApi;
import com.lulian.driver.entity.api.UploadImgApi;
import com.lulian.driver.entity.api.addMyDreaverLeader;
import com.lulian.driver.entity.server.resulte.DriverInfoPhone;
import com.lulian.driver.entity.server.resulte.ExceptionFile;
import com.lulian.driver.utils.ProjectUtil;
import com.lulian.driver.view.wheel.ClearEditText;
import com.rxretrofitlibrary.retrofit_rx.exception.ApiException;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.alibaba.fastjson.JSON.parseArray;
import static com.squareup.picasso.MemoryPolicy.NO_CACHE;
import static com.squareup.picasso.MemoryPolicy.NO_STORE;

/**
 * Created by MARK on 2018/6/21.
 */

public class AddDriverActivity extends CheckPermissionsActivity implements AdapterView.OnItemClickListener,
        CheckPermissionsActivity.ResultPhone, TakePhoto.TakeResultListener, InvokeListener{

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
    @BindView(R.id.addriver_ll_phone)
    LinearLayout addriverLlPhone;
    @BindView(R.id.addriver_edit_searchphone)
    ClearEditText addriverEditSearchphone;
    @BindView(R.id.addriver_btn_search)
    Button addriverBtnSearch;
    @BindView(R.id.addriver_edit_name)
    ClearEditText addriverEditName;
    @BindView(R.id.addriver_edit_phone)
    ClearEditText addriverEditPhone;
    @BindView(R.id.paper_txt_title1)
    TextView paperTxtTitle1;
    @BindView(R.id.paper_txt_title2)
    TextView paperTxtTitle2;
    @BindView(R.id.paper_layout_uploadidcardfront)
    LinearLayout paperLayoutUploadidcardfront;
    @BindView(R.id.paper_img_img1)
    ImageView paperImgImg1;
    @BindView(R.id.paper_txt_titlefront)
    TextView paperTxtTitlefront;
    @BindView(R.id.paper_img_front)
    ImageView paperImgFront;
    @BindView(R.id.paper_img_reverse)
    ImageView paperImgReverse;
    @BindView(R.id.paper_layout_front_txt)
    TextView paperLayoutFrontTxt;
    @BindView(R.id.paper_layout_front)
    LinearLayout paperLayoutFront;
    @BindView(R.id.paper_layout_reverse_txt)
    TextView paperLayoutReverseTxt;
    @BindView(R.id.paper_layout_reverse)
    LinearLayout paperLayoutReverse;
    @BindView(R.id.paper_layout_idcard)
    LinearLayout paperLayoutIdcard;
    @BindView(R.id.addiver_btn_fsyq)
    Button addiverBtnFsyq;
    @BindView(R.id.addiver_layout_hide)
    ScrollView addiverLayoutHide;
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
    private int neType;
    private List<DriverInfoPhone> motoList;

    private boolean isRegister;//是否注册
    private String phoneNum = "";
    private String temPhone;

    private TakePhoto takePhoto;
    InvokeParam invokeParam;
    private String iconPath;
    private int upLoadType;//0,手持身份证照;1:身份证正面照;2:身份证反面照;
    private String headerUrl = "";
    private String idUrl = "";
    private String idUrl2 = "";

    private ExceptionFile excFile1;
    private ExceptionFile excFile2;
    private ExceptionFile excFile3;
    ArrayList<ExceptionFile> exFileList;

    private String imgType;

    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                showHideView(isRegister);
            } else if (msg.what == 1) {
                addriverEditSearchphone.setText(phoneNum);
            } else if (msg.what == 2) {
                String realPath = "file://" + iconPath;
                if (upLoadType == 0) {
                    Picasso.get().load(realPath).memoryPolicy(NO_CACHE, NO_STORE).into(paperImgImg1);
                } else if (upLoadType == 1) {
                    Picasso.get().load(realPath).memoryPolicy(NO_CACHE, NO_STORE).into(paperImgFront);
                } else if (upLoadType == 2) {
                    Picasso.get().load(realPath).memoryPolicy(NO_CACHE, NO_STORE).into(paperImgReverse);
                }

            }
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getTakePhoto().onCreate(savedInstanceState);
        setContentView(R.layout.activity_addriver);
        ButterKnife.bind(this);
        initView();
    }


    @Override
    public void onSuccess(String data) {
        if (neType == 1) { //注册邀请
            JSONObject jsonObject = JSONObject.parseObject(data);
            String state = jsonObject.getString("state");

            if (state.equals("1")){
                ProjectUtil.show(this, "邀请注册成功");
                finish();
            }

        } else if (neType == 2) {

            if (motoList!= null) {
                motoList.clear();
            }
            motoList = parseArray(data, DriverInfoPhone.class);

            if (motoList.size() == 0) {
                isRegister = false;
            } else {
                isRegister = true;
            }

            mHandler.sendEmptyMessage(0);

        } else if (neType == 3) {//收藏选中的车队长
            JSONObject jsonObject = JSONObject.parseObject(data);
            String state = jsonObject.getString("state");

            if (state.equals("1")){
                finish();
            }

        } else if (neType == 4) {
            takephoto = 0;
            JSONObject jsonObject = JSONObject.parseObject(data);
            String state = jsonObject.getString("state");

            if (state.equals("1")) {
                ProjectUtil.show(this, jsonObject.getString("msg"));
                JSONObject object = jsonObject.getJSONObject("data");
                String pId = object.getString("Id");
                String pUrl = object.getString("Url");
                String FileName = object.getString("FileName");
                switch (upLoadType) {
                    case 0:
                        headerUrl = pUrl;

                        excFile1 = new ExceptionFile();
                        excFile1.setFileName(FileName);
                        excFile1.setId(pId);
                        excFile1.setUrl(pUrl);
                        exFileList.add(excFile1);

                        mHandler.sendEmptyMessage(2);
                        break;
                    case 1:
                        idUrl = pUrl;

                        excFile2 = new ExceptionFile();
                        excFile2.setFileName(FileName);
                        excFile2.setId(pId);
                        excFile2.setUrl(pUrl);
                        exFileList.add(excFile2);
                        mHandler.sendEmptyMessage(2);
                        break;
                    case 2:
                        idUrl2 = pUrl;

                        excFile3 = new ExceptionFile();
                        excFile3.setFileName(FileName);
                        excFile3.setId(pId);
                        excFile3.setUrl(pUrl);
                        exFileList.add(excFile3);
                        mHandler.sendEmptyMessage(2);
                        break;
                }
            }
        }
    }

    private void initView() {
        textContent.setText("添加车队长");
        this.setResultPhoneListener(this);
        exFileList = new ArrayList<>();
    }

    private void showHideView(boolean isRegister) {
        //邀请车队长注册
        if (!isRegister) {
            addiverLayoutHide.setVisibility(View.VISIBLE);
            addMyFavorite.setVisibility(View.GONE);
            addriverEditPhone.setText(temPhone);
        } else {
            //已注册车队长
            addiverLayoutHide.setVisibility(View.GONE);
            addMyFavorite.setVisibility(View.VISIBLE);
            setValue();

        }
    }

    private void setValue() {
        final DriverInfoPhone motorCade = motoList.get(0);
        Picasso.get().load(motorCade.getHeaderUrl()).memoryPolicy(NO_CACHE, NO_STORE).into(carlistImgHead);

        itemDriverName.setText(motorCade.getName());
        itemDriverPhone.setText(motorCade.getPhone());

        ratingbar.setStar(motorCade.getStar());
        waybillDetailTxtStart.setText(motorCade.getStartAreaId());
        waybillDetailTxtEnd.setText(motorCade.getEndAreaId());

        addiverBtnJrsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                neType = 3;
                addMyDreaverLeader addMyDreaverLeader = new addMyDreaverLeader();
                addMyDreaverLeader.setHeader(app.getAuthorization());
                addMyDreaverLeader.setUserHeader(app.getUserHeader());
                addMyDreaverLeader.setUserId(motorCade.getId());

                pClass.startHttpRequest(AddDriverActivity.this, addMyDreaverLeader);
            }
        });

    }

    @Override
    public void dismissProg() {
        super.dismissProg();
    }

    @Override
    public void showProg() {
        super.showProg();
    }

    @Override
    public void onError(ApiException e) {
        super.onError(e);
    }

    @OnClick({R.id.img_return, R.id.addriver_btn_search, R.id.addiver_btn_fsyq,
            R.id.addriver_ll_phone, R.id.addiver_btn_jrsc,
            R.id.paper_layout_uploadidcardfront,
            R.id.paper_layout_front, R.id.paper_layout_reverse})

    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_return:
                this.finish();
                break;
            case R.id.addriver_btn_search://搜索
                String searchPhone = addriverEditSearchphone.getText().toString().trim();
                temPhone = searchPhone.replaceAll(" ", "");
                if (searchPhone.equals("") || !ProjectUtil.isMobileNO(temPhone)) {
                    ProjectUtil.show(this, "请填写正确的手机号码!");
                } else {
                    neType = 2;
                    DriverInfoPhoneApi qdva = new DriverInfoPhoneApi();
                    qdva.setHeader(app.getAuthorization());
                    qdva.setUserHeader(app.getUserHeader());
                    qdva.setPhoneNo(temPhone);
                    pClass.startHttpRequest(this, qdva);
                }
                break;

            case R.id.addiver_btn_fsyq:
                neType = 1;
                String name = addriverEditName.getText().toString().trim();
                String phone = addriverEditPhone.getText().toString().trim();
                if (name.equals("")) {
                    ProjectUtil.show(this, "请填写姓名!");
                } else if (phone.equals("") || !ProjectUtil.isMobileNO(phone)) {
                    ProjectUtil.show(this, "请填写正确的手机号码!");
                } else if (headerUrl.equals("")) {
                    ProjectUtil.show(this, "请上传手持身份证照!");
                } else if (idUrl.equals("")) {
                    ProjectUtil.show(this, "请上传身份证正面照!");
                } else if (idUrl2.equals("")) {
                    ProjectUtil.show(this, "请上传身份证反面照!");
                } else {

                    AddInviteDriverLeaderApi addIn = new AddInviteDriverLeaderApi();
                    addIn.setHeader(app.getAuthorization());
                    addIn.setUserHeader(app.getUserHeader());
                    addIn.setFileList(exFileList);
                    addIn.setPhoneNo(phone);
                    addIn.setName(name);
                    addIn.setIdentityCode("1");
                    addIn.setInviteCode("1");
                    pClass.startHttpRequest(this, addIn);
                }
                break;
            case R.id.addriver_ll_phone:
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_PICK);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
                startActivityForResult(intent, 1);
                break;

            case R.id.paper_layout_uploadidcardfront://手持身份证照
                takephoto = 1;
                upLoadType = 0;
                imgType = "1";
                ProjectUtil.showUploadFileDialog(this, this);
                break;
            case R.id.paper_layout_front://身份证正面
                takephoto = 1;
                upLoadType = 1;
                imgType = "2";
                ProjectUtil.showUploadFileDialog(this, this);
                break;
            case R.id.paper_layout_reverse://身份证反面
                takephoto = 1;
                upLoadType = 2;
                imgType = "3";
                ProjectUtil.showUploadFileDialog(this, this);
                break;
        }
    }


    @Override
    public void success(String phoneNum) {
        this.phoneNum = phoneNum;
        mHandler.sendEmptyMessage(1);
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
        neType = 4;
        iconPath = result.getImage().getOriginalPath();
//        ProjectUtil.show(MotorcadeStep2Activity.this, iconPath);
//        mHandler.sendEmptyMessage(1);
        File imgFile = new File(iconPath);
        UploadImgApi uploadImgApi = new UploadImgApi();
        uploadImgApi.setHeader(app.getAuthorization());
        uploadImgApi.setUserHeader(app.getUserHeader());
        uploadImgApi.setImg(imgFile);
        uploadImgApi.setFileType(imgType);
        pClass.startHttpRequest(AddDriverActivity.this, uploadImgApi);
    }

    @Override
    public void takeFail(TResult result, String msg) {

    }

    @Override
    public void takeCancel() {

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