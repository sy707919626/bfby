package com.lulian.driver.view.activity;

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
import com.lulian.driver.entity.api.UploadFileApi;
import com.lulian.driver.entity.server.req.SaveDriverInfoBean;
import com.lulian.driver.entity.server.resulte.UploadFileResult;
import com.lulian.driver.utils.ActivityStorer;
import com.lulian.driver.utils.ChoiceDialogTool;
import com.lulian.driver.utils.IDCard;
import com.lulian.driver.utils.ProjectUtil;
import com.lulian.driver.view.wheel.ClearEditText;
import com.lulian.driver.view.dialog.PhotoDisplayDialog;
import com.rxretrofitlibrary.retrofit_rx.utils.GlobalValue;
import com.rxretrofitlibrary.retrofit_rx.utils.GsonUtil;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.ParseException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 注册 个人信息填写界面
 * @author hxb
 */

public class PersonalInfoFillActivity extends BaseActivity implements AdapterView.OnItemClickListener,
        TakePhoto.TakeResultListener, InvokeListener {
    @BindView(R.id.img_return)
    ImageView imgReturn;
    @BindView(R.id.text_content)
    TextView textContent;
    @BindView(R.id.title_txt_rightbottom)
    TextView titleTxtRightBottom;


    @BindView(R.id.cet_invite_code)
    ClearEditText cetInviteCode;//邀请码
    @BindView(R.id.cet_name)
    ClearEditText cetName;//姓名
    @BindView(R.id.cet_mobile)
    ClearEditText cetMobile;//(联系方式)手机号
    @BindView(R.id.cet_idcard_num)
    ClearEditText cetIdCardNum;//身份证号


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


    @Override
    public void onSuccess(String data) {
        switch (neType) {
            case 1: //上传图片成功
                UploadFileResult result= GsonUtil.get().fromJson(data, UploadFileResult.class);

                //将上传成功返回的图片url记录下来
                uploadedPhotoUrl.put(currentClickPhotoStubId,result.getUrl());

                //将上传成功的图片,显示到对应的iv上
                ImageView iv = ivArrayPhoto.get(currentClickPhotoStubId);
                iv.setVisibility(View.VISIBLE);
                Picasso.get().load("file://"+currentTookPhotoPath).into(iv);

                break;
        }
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getTakePhoto().onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info_fill);
        ButterKnife.bind(this);

        ActivityStorer.add(this);

        initPhotoDialog();
        initView();
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

    private void initView() {
        textContent.setText("完善资料");
        titleTxtRightBottom.setText("跳过");
        titleTxtRightBottom.setVisibility(View.VISIBLE);

        initPhotoIvArray();
    }


    private void initPhotoIvArray() {
        ivArrayPhoto.put(R.id.vg_hand_idcard_take_click_stub, (ImageView) findViewById(R.id.iv_hand_idcard));
        ivArrayPhoto.put(R.id.vg_idcard_front_take_click_stub, (ImageView) findViewById(R.id.iv_idcard_front));
        ivArrayPhoto.put(R.id.vg_idcard_back_take_click_stub, (ImageView) findViewById(R.id.iv_idcard_back));
        ivArrayPhoto.put(R.id.vg_drivers_lic_front_take_click_stub, (ImageView) findViewById(R.id.iv_drivers_lic_front));
        ivArrayPhoto.put(R.id.vg_drivers_lic_back_take_click_stub, (ImageView) findViewById(R.id.iv_drivers_lic_back));

    }


    @OnClick({R.id.img_return, R.id.per_btn_next,
            R.id.title_txt_rightbottom})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_return:
                this.finish();
                break;
            case R.id.per_btn_next://点击下一步
                if(checkInputIsCorrect()){
                    jumpToTruckInfoFill();
                }

//                //测试代码
//                jumpToTruckInfoFill();

                break;
            case R.id.title_txt_rightbottom://点击跳过
                showSkipDialog();
                break;
        }
    }


    /**
     * 用当前界面填写的信息创建一个保存司机信息的请求参数bean
     */
    private SaveDriverInfoBean buildSaveDriverReqBean(){
        SaveDriverInfoBean bean = new SaveDriverInfoBean();

        String inviteCode = cetInviteCode.getText().toString().trim();
        String name = cetName.getText().toString().trim();
        String mobile = cetMobile.getText().toString().trim();
        String idCardNum = cetIdCardNum.getText().toString().trim();

        bean.setInviteCode(inviteCode);
        bean.setName(name);
        bean.setPhone(mobile);
        bean.setIdCard(idCardNum);

        bean.setHandUdentityURL(uploadedPhotoUrl.get(R.id.vg_hand_idcard_take_click_stub));
        bean.setIdUrl(uploadedPhotoUrl.get(R.id.vg_idcard_front_take_click_stub));
        bean.setIdUrl2(uploadedPhotoUrl.get(R.id.vg_idcard_back_take_click_stub));
        bean.setLlicUrl(uploadedPhotoUrl.get(R.id.vg_drivers_lic_front_take_click_stub));
        bean.setLlicUrl2(uploadedPhotoUrl.get(R.id.vg_drivers_lic_back_take_click_stub));

        return bean;
    }


    /**
     * 跳转到车辆信息完善界面
     */
    private void jumpToTruckInfoFill(){
        SaveDriverInfoBean bean = buildSaveDriverReqBean();

        Intent intent = new Intent(this,TruckInfoFillActivity.class);
        intent.putExtra("saveDriverInfoBean", bean);
        startActivity(intent);
    }


    /**
     * 弹出跳过提示框
     */
    private void showSkipDialog(){
        String tip = getResources().getString(R.string.skip_complete_info_tip);
        ChoiceDialogTool.showDialog(this,tip,"取消","去登陆",new ChoiceDialogTool.Callback(){
            @Override
            public void onLeftBtnClick() {
            }

            @Override
            public void onRightBtnClick() {//确定
                //跳转到登录界面
                startActivity(new Intent(PersonalInfoFillActivity.this, LoginActivity.class));
            }
        });
    }


    private boolean checkInputIsCorrect(){
        String name = cetName.getText().toString().trim();
        String mobile = cetMobile.getText().toString().trim();
        String idCardNum = cetIdCardNum.getText().toString().trim();

        if(TextUtils.isEmpty(name) || TextUtils.isEmpty(mobile) || TextUtils.isEmpty(idCardNum)){
            ProjectUtil.show(this,"请将必填项填写完整");
            return false;
        }

        if(!ProjectUtil.isMobileNO(mobile)){
            ProjectUtil.show(this,"请填写正确的手机号");
            return false;
        }


        boolean idCardValidate=false;
        try {
            String result = IDCard.IDCardValidate(idCardNum);
            if(result.isEmpty()){
                idCardValidate=true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(!idCardValidate){
            ProjectUtil.show(this, "请填写正确的身份证号码!");
            return false;
        }

        return true;
    }

    /**
     * 照片框框点击监听
     */
    @OnClick({R.id.vg_hand_idcard_take_click_stub,
            R.id.vg_idcard_front_take_click_stub,
            R.id.vg_idcard_back_take_click_stub,
            R.id.vg_drivers_lic_front_take_click_stub,
            R.id.vg_drivers_lic_back_take_click_stub})
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
            ProjectUtil.showUploadFileDialog(PersonalInfoFillActivity.this, PersonalInfoFillActivity.this);
        }
    };

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
     *
     * @return
     */
    public TakePhoto getTakePhoto() {
        if (takePhoto == null) {
            takePhoto = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this, this));
        }
        return takePhoto;
    }


}
