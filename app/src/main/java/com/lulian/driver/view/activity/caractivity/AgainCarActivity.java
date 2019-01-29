package com.lulian.driver.view.activity.caractivity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.AppCompatSpinner;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
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
import com.lulian.driver.adapter.CarTypeAdapter;
import com.lulian.driver.base.CheckPermissionsActivity;
import com.lulian.driver.entity.api.AddBlackListApi;
import com.lulian.driver.entity.api.GetDriverInfoApi;
import com.lulian.driver.entity.api.InvitedDriverRVehiclesApi;
import com.lulian.driver.entity.api.DriverLeaderListApi;
import com.lulian.driver.entity.api.UploadFileApi;
import com.lulian.driver.entity.api.UploadImgApi;
import com.lulian.driver.entity.server.resulte.CarType;
import com.lulian.driver.entity.server.resulte.Driver;
import com.lulian.driver.entity.server.resulte.DriverLeaderInfo;
import com.lulian.driver.listener.ReportClickListener;
import com.lulian.driver.utils.ProjectUtil;
import com.lulian.driver.view.wheel.ClearEditText;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.alibaba.fastjson.JSON.parseArray;
import static com.squareup.picasso.MemoryPolicy.NO_CACHE;
import static com.squareup.picasso.MemoryPolicy.NO_STORE;

/**
 * Created by MARK on 2018/6/28.
 */

public class AgainCarActivity extends CheckPermissionsActivity implements AdapterView.OnItemClickListener,
        CheckPermissionsActivity.ResultPhone, TakePhoto.TakeResultListener, InvokeListener {

    @BindView(R.id.img_return)
    ImageView imgReturn;
    @BindView(R.id.text_content)
    TextView textContent;
    @BindView(R.id.title_txt_rightbottom)
    TextView titleTxtRightbottom;
    @BindView(R.id.title_txtrightbottomleft)
    TextView titleTxtrightbottomleft;
    @BindView(R.id.activity_main)
    RelativeLayout activityMain;
    @BindView(R.id.carlist_img_head)
    ImageView carlistImgHead;
    @BindView(R.id.driver_detail_name)
    TextView driverDetailName;
    @BindView(R.id.ratingbar)
    RatingBar ratingbar;
    @BindView(R.id.driver_detail_vehicle)
    TextView driverDetailVehicle;
    @BindView(R.id.detail_txt_report)
    TextView detailTxtReport;
    @BindView(R.id.knownorder_layout_call)
    LinearLayout knownorderLayoutCall;
    @BindView(R.id.allandmy_layout_centercontent)
    LinearLayout allandmyLayoutCentercontent;
    @BindView(R.id.addriver_ll_phone)
    LinearLayout addriverLlPhone;
    @BindView(R.id.addriver_edit_searchphone)
    ClearEditText addriverEditSearchphone;
    @BindView(R.id.addriver_btn_search)
    Button addriverBtnSearch;
    @BindView(R.id.againcar_edit_name)
    ClearEditText againcarEditName;
    @BindView(R.id.againcar_spinner_style)
    AppCompatSpinner againcarSpinnerStyle;
    @BindView(R.id.againcar_layout_show)
    LinearLayout againcarLayoutShow;
    @BindView(R.id.driver_detail_canorder)
    TextView driverDetailCanorder;
    @BindView(R.id.driver_img_zidcard)
    ImageView driverImgZidcard;
    @BindView(R.id.driver_detail_zidcard)
    Button driverDetailZidcard;
    @BindView(R.id.driver_detail_fidcard)
    Button driverDetailFidcard;
    @BindView(R.id.driver_detail_zjiashiz)
    Button driverDetailZjiashiz;
    @BindView(R.id.driver_detail_fjiashiz)
    Button driverDetailFjiashiz;
    @BindView(R.id.driver_detail_qzhao)
    Button driverDetailQzhao;
    @BindView(R.id.protocol_btn_agree)
    Button protocolBtnAgree;
    @BindView(R.id.againcar_txt_jubao)
    LinearLayout againcarTxtJubao;
    @BindView(R.id.driver_detail_plateno)
    TextView driverDetailPlateno;
    @BindView(R.id.againcar_edit_card)
    ClearEditText againcarEditCard;
    @BindView(R.id.driver_img_fidcard)
    ImageView driverImgFidcard;
    @BindView(R.id.driver_img_jiashizzhao)
    ImageView driverImgJiashizzhao;
    @BindView(R.id.driver_img_jshifanz)
    ImageView driverImgJshifanz;
    @BindView(R.id.driver_img_qzhao)
    ImageView driverImgQzhao;
    @BindView(R.id.againcar_btn_paiche)
    Button againcarBtnPaiche;
    @BindView(R.id.carlistimghead1)
    ImageView carlistimghead1;
    @BindView(R.id.driverdetailname1)
    TextView driverdetailname1;
    @BindView(R.id.ratingbar1)
    RatingBar ratingbar1;
    @BindView(R.id.driverdetailvehicle1)
    TextView driverdetailvehicle1;
    @BindView(R.id.driverdetailplateno1)
    TextView driverdetailplateno1;
    @BindView(R.id.detailtxtreport1)
    TextView detailtxtreport1;
    @BindView(R.id.againcartxtjubao1)
    LinearLayout againcartxtjubao1;
    @BindView(R.id.driverdetailcanorder1)
    TextView driverdetailcanorder1;
    @BindView(R.id.knownorderlayoutcall1)
    LinearLayout knownorderlayoutcall1;
    @BindView(R.id.allandmylayoutcentercontent1)
    LinearLayout allandmylayoutcentercontent1;
    private String orderId;
    private Driver driver;
    private String phoneNum = "";
    private String temPhone = "";
    private boolean isRegister;
    private List<DriverLeaderInfo> motoList;
    private TakePhoto takePhoto;
    private String iconPath;
    private String imgType;
    private InvokeParam invokeParam;
    private List<CarType> cartypeLsit;
    private String vModel;
    private Dialog snapDialog;
    private ImageView report_img_img1, report_img_img2, report_img_img3;
    public boolean first;
    public boolean second;
    public boolean third;
    private String imgUrl1;
    private String imgUrl2;
    private String imgUrl3;
    private boolean firstImgUploadSuccess = false;
    private boolean secondImgUploadSuccess = false;
    private boolean thirdImgUploadSuccess = false;
    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                setDriverInfo();
            } else if (msg.what == 1) {
                addriverEditSearchphone.setText(phoneNum);
            } else if (msg.what == 2) {
                showHideView(isRegister);
            } else if (msg.what == 3) {//加载上传的图片
                String realPath = "file://" + iconPath;
                switch (imgType) {
                    case "17"://车队长代传司机身份证正面
                        Picasso.get().load(realPath).memoryPolicy(NO_CACHE, NO_STORE).into(driverImgZidcard);
                        break;
                    case "18"://代传身份证背面照（国徽面）
                        Picasso.get().load(realPath).memoryPolicy(NO_CACHE, NO_STORE).into(driverImgFidcard);
                        break;
                    case "19"://代传驾驶证正面照
                        Picasso.get().load(realPath).memoryPolicy(NO_CACHE, NO_STORE).into(driverImgJiashizzhao);
                        break;
                    case "20"://代传行驶证正面照
                        Picasso.get().load(realPath).memoryPolicy(NO_CACHE, NO_STORE).into(driverImgJshifanz);
                        break;
                    case "21"://代传车辆全身照
                        Picasso.get().load(realPath).memoryPolicy(NO_CACHE, NO_STORE).into(driverImgQzhao);
                        break;
                }
            } else if (msg.what == 6) {
                String realPath = "file://" + iconPath;
                if (firstImgUploadSuccess && !first) {
                    first = true;
                    Picasso.get().load(realPath).memoryPolicy(NO_CACHE, NO_STORE).into(report_img_img1);
                } else if (secondImgUploadSuccess && !second) {
                    second = true;
                    Picasso.get().load(realPath).memoryPolicy(NO_CACHE, NO_STORE).into(report_img_img2);
                } else if (thirdImgUploadSuccess && !third) {
                    third = true;
                    Picasso.get().load(realPath).memoryPolicy(NO_CACHE, NO_STORE).into(report_img_img3);
                }
            }
        }
    };


    private void showHideView(boolean isRegister) {
        if (!isRegister) {
            againcarLayoutShow.setVisibility(View.VISIBLE);
            allandmylayoutcentercontent1.setVisibility(View.GONE);
        } else {
            againcarLayoutShow.setVisibility(View.GONE);
            allandmylayoutcentercontent1.setVisibility(View.VISIBLE);
            setOnlyValue();
        }
    }



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getTakePhoto().onCreate(savedInstanceState);
        setContentView(R.layout.activity_againcar);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        orderId = intent.getStringExtra("orderid");
        initView();
        initData();
    }

    private String imgUrl[] = new String[5];//上传图片的

    @Override
    public void onSuccess(String data) {

        if (neType == 0) {//司机信息
            driver = JSON.parseObject(data, Driver.class);
            mHandler.sendEmptyMessage(0);
        } else if (neType == 2) {//查询用户是否存在
            motoList = parseArray(data, DriverLeaderInfo.class);
//            ProjectUtil.show(this, "motoList:" + motoList.size());
            if (motoList.size() == 0) {
                isRegister = false;
            } else {
                isRegister = true;
            }
            mHandler.sendEmptyMessage(2);
        } else if (neType == 3) {//上传图片
            takephoto = 0;
            JSONObject jsonObject = JSONObject.parseObject(data);
            String state = jsonObject.getString("state");
            if (state.equals("1")) {
                ProjectUtil.show(this, jsonObject.getString("msg"));
                JSONObject object = jsonObject.getJSONObject("data");
                String pId = object.getString("Id");
                String pUrl = object.getString("Url");
                switch (imgType) {
                    case "17"://车队长代传司机身份证正面
                        imgUrl[0] = pUrl;
                        mHandler.sendEmptyMessage(3);
                        break;
                    case "18"://代传身份证背面照（国徽面）
                        imgUrl[1] = pUrl;
                        mHandler.sendEmptyMessage(3);
                        break;
                    case "19"://代传驾驶证正面照
                        imgUrl[2] = pUrl;
                        mHandler.sendEmptyMessage(3);
                        break;
                    case "20"://代传行驶证正面照
                        imgUrl[3] = pUrl;
                        mHandler.sendEmptyMessage(3);
                        break;
                    case "21"://代传车辆全身照
                        imgUrl[4] = pUrl;
                        mHandler.sendEmptyMessage(3);
                        break;
                }
            }
        } else if (neType == 4) {//重新派车(未注册用户)

            JSONObject jsonObject = JSONObject.parseObject(data);
            String state = jsonObject.getString("state");
            String msg = jsonObject.getString("msg");
            ProjectUtil.show(this, "state:" + state + ",msg:" + msg);
        } else if (neType == 5) {//举报
            HashMap<String, String> map = parseJsonMsg(data);
            String msg = map.get("msg");
            ProjectUtil.show(this, msg);
            String state = map.get("state");
            if (state.equals("1")) {
                snapDialog.dismiss();
            }
        } else if (neType == 6) {//
            takephoto = 0;
            JSONObject jo = JSONObject.parseObject(data);
            ProjectUtil.show(this, jo.getString("msg"));
            JSONObject object = jo.getJSONObject("data");
            String pId = object.getString("Id");
            String pUrl = object.getString("Url");
            if (!firstImgUploadSuccess) {
                firstImgUploadSuccess = true;
                imgUrl1 = pUrl;
            } else if (!secondImgUploadSuccess) {
                secondImgUploadSuccess = true;
                imgUrl2 = pUrl;
            } else if (!thirdImgUploadSuccess) {
                thirdImgUploadSuccess = true;
                imgUrl3 = pUrl;
            }
            mHandler.sendEmptyMessage(6);
        }
    }

    public HashMap<String, String> parseJsonMsg(String data) {
        HashMap<String, String> map = new HashMap<>();
        JSONObject jo = JSONObject.parseObject(data);
        String msg = jo.getString("msg");
        String state = jo.getString("state");
        map.put("msg", msg);
        map.put("state", state);
        return map;
    }

    private void initData() {
        neType = 0;//司机信息
        GetDriverInfoApi gcia = new GetDriverInfoApi();
        gcia.setHeader(app.getAuthorization());
        gcia.setUserHeader(app.getUserHeader());
        gcia.setOrderId(orderId);
        pClass.startHttpRequest(this, gcia);
    }

    private void setDriverInfo() {//司机信息
        driverDetailName.setText(driver.getName());
        driverDetailPlateno.setText(driver.getPhone());
        ratingbar.setStar(driver.getStar());
        //Picasso.with(WayBillDetailsActivity.this).
        // load(driver.getHeaderUrl()).memoryPolicy(NO_CACHE, NO_STORE).into(orderdetailImgHead);
    }

    private void setOnlyValue() {
        DriverLeaderInfo motorcade=motoList.get(0);
        driverdetailname1.setText(motorcade.getName());
        ratingbar1.setStar(motorcade.getStar());
//        driverdetailvehicle1.setText(motorcade.getVehicleTypeName()+"/"+motorcade.getVehicleLength()+"米/"+
//                motorcade.getVehicleWeight()+"吨");
//        driverdetailplateno1.setText(motorcade.getPlateNo());
    }

    private void initView() {
        textContent.setText("重新派车");
        againcarLayoutShow.setVisibility(View.GONE);
        this.setResultPhoneListener(this);
        requestWritePermission();
        cartypeLsit = app.getTrucktypeList();
        CarTypeAdapter cartype = new CarTypeAdapter(this, cartypeLsit);
        againcarSpinnerStyle.setAdapter(cartype);
        //车辆类型
        againcarSpinnerStyle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                vModel = cartypeLsit.get(position).getId();
//                ProjectUtil.show1(mActivity, "allOrdervModelID:" + vModel, app.iShow());
//                getData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getTakePhoto().onSaveInstanceState(outState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
    }

    @OnClick({R.id.img_return, R.id.addriver_ll_phone, R.id.driver_detail_zidcard,
            R.id.driver_detail_fidcard, R.id.driver_detail_zjiashiz,
            R.id.driver_detail_fjiashiz, R.id.driver_detail_qzhao,
            R.id.addriver_btn_search, R.id.againcar_txt_jubao, R.id.againcar_btn_paiche})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.img_return:
                this.finish();
                break;
            case R.id.addriver_ll_phone:
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_PICK);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
                startActivityForResult(intent, 1);
                break;
            case R.id.addriver_btn_search:
                String searchPhone = addriverEditSearchphone.getText().toString().trim();
                temPhone = searchPhone.replaceAll(" ", "");
//                ProjectUtil.show(this,temPhone);
                if (searchPhone.equals("") || !ProjectUtil.isMobileNO(temPhone)) {
                    ProjectUtil.show(this, "请填写正确的手机号码!");
                } else {
                    neType = 2;
                    DriverLeaderListApi qdva = new DriverLeaderListApi();
                    qdva.setHeader(app.getAuthorization());
                    qdva.setUserHeader(app.getUserHeader());

                    qdva.setRows(10);
                    qdva.setPage(1);
                    pClass.startHttpRequest(this, qdva);
                }
                break;
            case R.id.driver_detail_zidcard:
                takephoto = 1;
                uplodImg(3, "17");
                break;
            case R.id.driver_detail_fidcard:
                takephoto = 1;
                uplodImg(3, "18");
                break;
            case R.id.driver_detail_zjiashiz:
                takephoto = 1;
                uplodImg(3, "19");
                break;
            case R.id.driver_detail_fjiashiz:
                takephoto = 1;
                uplodImg(3, "20");
                break;
            case R.id.driver_detail_qzhao:
                takephoto = 1;
                uplodImg(3, "21");
                break;
            case R.id.againcar_btn_paiche:
                String againcarName = againcarEditName.getText().toString().trim();
                String platNo = againcarEditCard.getText().toString().trim();
                if (againcarName.equals("") || platNo.equals("") || imgUrl[0].equals("") ||
                        imgUrl[1].equals("") || imgUrl[2].equals("") || imgUrl[3].equals("") ||
                        imgUrl[4].equals("")) {
                    ProjectUtil.show(this, "请填写完整资料!");
                } else {
                    neType = 4;
                    InvitedDriverRVehiclesApi idrva = new InvitedDriverRVehiclesApi();
                    idrva.setHeader(app.getAuthorization());
                    idrva.setUserHeader(app.getUserHeader());
                    idrva.setIdUrl(imgUrl[0]);
                    idrva.setIdUrl2(imgUrl[1]);
                    idrva.setLlicUrl(imgUrl[2]);
                    idrva.setVehicleType(vModel);
                    idrva.setVehicleLength(10);
                    idrva.setLicUrl(imgUrl[3]);
                    idrva.setVPicUrl(imgUrl[4]);
                    idrva.setInvitationID("112211");
                    idrva.setCreateId(app.getUserId());
                    idrva.setCreateUser(app.getUserName());
                    idrva.setInviteCode("123");
                    idrva.setDriversPhone("15711974101");
                    idrva.setDriversName(againcarName);
                    idrva.setOrderId(orderId);
                    idrva.setInviteURL("qweasdzxc");
                    idrva.setFileIDList("1qwe2");
                    pClass.startHttpRequest(this, idrva);
                }
                break;
            case R.id.againcar_txt_jubao:
                showReportDialog();
                break;
        }
    }

    public void uplodImg(int neType, String imgType) {
        this.neType = neType;
        this.imgType = imgType;
        ProjectUtil.showUploadFileDialog(this, this);
    }

    @OnClick()
    public void onViewClicked() {
    }

    @Override
    public void success(String phoneNum) {
        this.phoneNum = phoneNum;
        mHandler.sendEmptyMessage(1);
    }

    @Override
    public void takeSuccess(TResult result) {
        iconPath = result.getImage().getOriginalPath();
//        ProjectUtil.show(MotorcadeActivity.this, iconPath);
//        mHandler.sendEmptyMessage(1);
        if (neType == 6) {
            File imgFile = new File(iconPath);
            UploadFileApi uploadImgApi = new UploadFileApi();
            uploadImgApi.setHeader(app.getAuthorization());
            uploadImgApi.setUserHeader(app.getUserHeader());
            uploadImgApi.setImg(imgFile);
            pClass.startHttpRequest(AgainCarActivity.this, uploadImgApi);
        } else {
            File imgFile = new File(iconPath);
            UploadImgApi uploadImgApi = new UploadImgApi();
            uploadImgApi.setHeader(app.getAuthorization());
            uploadImgApi.setUserHeader(app.getUserHeader());
            uploadImgApi.setImg(imgFile);
            uploadImgApi.setFileType(imgType);
            pClass.startHttpRequest(AgainCarActivity.this, uploadImgApi);
        }
    }

    public void showReportDialog() {
        snapDialog = new Dialog(AgainCarActivity.this,
                R.style.AppTheme);
        snapDialog.setContentView(R.layout.reportpage);
        Window window = snapDialog.getWindow();
        window.setGravity(Gravity.CENTER);
        DisplayMetrics dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay()
                .getMetrics(dm);
        WindowManager.LayoutParams lp = snapDialog.getWindow().getAttributes();
        lp.width = (int) (dm.widthPixels);
        lp.height = (int) (dm.heightPixels - 550);
        snapDialog.getWindow().setAttributes(lp);
        snapDialog.show();
        ImageView report_img_delete = (ImageView) snapDialog.findViewById(R.id.report_img_delete);
        final EditText report_edit_msg = (EditText) snapDialog.findViewById(R.id.report_edit_msg);
        Button report_btn_takephoto = (Button) snapDialog.findViewById(R.id.report_btn_takephoto);
        Button report_btn_slbum = (Button) snapDialog.findViewById(R.id.report_btn_slbum);
        report_img_img1 = (ImageView) snapDialog.findViewById(R.id.report_img_img1);
        report_img_img2 = (ImageView) snapDialog.findViewById(R.id.report_img_img2);
        report_img_img3 = (ImageView) snapDialog.findViewById(R.id.report_img_img3);
        Button report_btn_submit = (Button) snapDialog.findViewById(R.id.report_btn_submit);
        ReportClickListener listener = new ReportClickListener(snapDialog);
        report_btn_takephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takephoto = 1;
                neType = 6;
                initPhotoData(true);
            }
        });
        report_btn_slbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takephoto = 1;
                neType = 6;
                initPhotoData(false);
            }
        });
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
                    abl.setDriverUserId(driver.getId());
                    abl.setReason(rMsg);
                    pClass.startHttpRequest(AgainCarActivity.this, abl);
                } else {
                    ProjectUtil.show(AgainCarActivity.this, "请填写举报内容!");
                }
            }
        });
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
    public void takeFail(TResult result, String msg) {

    }

    @Override
    public void takeCancel() {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //以下代码为处理Android6.0、7.0动态权限所需
        PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handlePermissionsResult(this, type, invokeParam, this);
    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type = PermissionManager.checkPermission(TContextWrap.of(this), invokeParam.getMethod());
        if (PermissionManager.TPermissionType.WAIT.equals(type)) {
            this.invokeParam = invokeParam;
        }
        return type;
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
