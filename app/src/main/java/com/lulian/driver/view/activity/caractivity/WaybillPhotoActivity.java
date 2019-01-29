package com.lulian.driver.view.activity.caractivity;

import android.Manifest;
import android.content.Context;
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
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.lulian.driver.entity.api.CheckOnLoadApi;
import com.lulian.driver.entity.api.UploadFileApi;
import com.lulian.driver.entity.server.resulte.ExceptionFile;
import com.lulian.driver.utils.ProjectUtil;
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

public class WaybillPhotoActivity extends BaseActivity implements TakePhoto.TakeResultListener, InvokeListener,
        AdapterView.OnItemClickListener{

    @BindView(R.id.car_photo_delete)
    ImageView carPhotoDelete;
    @BindView(R.id.car_tail_reverse_photo)
    TextView carTailReversePhoto;
    @BindView(R.id.car_tail_photo)
    LinearLayout carTailPhoto;
    @BindView(R.id.car_tail_front_photo)
    ImageView carTailFrontPhoto;
    @BindView(R.id.delivery_note_reverse_photo)
    TextView deliveryNoteReversePhoto;
    @BindView(R.id.delivery_note_photo)
    LinearLayout deliveryNotePhoto;
    @BindView(R.id.delivery_note_front_photo)
    ImageView deliveryNoteFrontPhoto;
    @BindView(R.id.car_photo_submission)
    Button carPhotoSubmission;

    private TakePhoto takePhoto;
    private String iconPath;

    private boolean carTailImgUploadSuccess = false;
    private boolean deliveryImgUploadSuccess = false;
    public boolean carTail;
    public boolean delivery;

    private int netType;//0:上传图片;1:提交
    private String tfid;
    private int type; //1为确认发车， 2为确认收货

    ArrayList<ExceptionFile> exFileList;
    private ExceptionFile excFile1;
    private ExceptionFile excFile2;

    InvokeParam invokeParam;
    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 6) {
                String realPath = "file://" + iconPath;
                if (carTailImgUploadSuccess && !carTail) {
                    carTail = true;
                    Picasso.get().load(realPath).memoryPolicy(NO_CACHE, NO_STORE).into(carTailFrontPhoto);
                } else if (deliveryImgUploadSuccess && !delivery) {
                    delivery = true;
                    Picasso.get().load(realPath).memoryPolicy(NO_CACHE, NO_STORE).into(deliveryNoteFrontPhoto);
                }
            }
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);

        getTakePhoto().onCreate(savedInstanceState);
        setContentView(R.layout.activity_waybillphoto);

        tfid = getIntent().getStringExtra("tfid");
        type = getIntent().getIntExtra("type", 0);

        ButterKnife.bind(this);
        initView();
    }


    private void initView() {
        exFileList=new ArrayList<>();
    }

    public static void start(Context context, String tfid, int Type) {
        Intent intent = new Intent(context, WaybillPhotoActivity.class);
        intent.putExtra("tfid", tfid);
        intent.putExtra("type", Type);
        context.startActivity(intent);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        getTakePhoto().onSaveInstanceState(outState);
    }


    @OnClick({R.id.car_photo_delete, R.id.car_tail_photo,
            R.id.delivery_note_photo,
            R.id.car_photo_submission})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.car_photo_delete:
                this.finish();
                break;
            case R.id.car_tail_photo:
                ProjectUtil.showUploadFileDialog(this, this);
                break;

            case R.id.delivery_note_photo:
                ProjectUtil.showUploadFileDialog(this, this);
                break;

            case R.id.car_photo_submission:

                if (excFile1.getUrl().equals("")){
                    ProjectUtil.show(this,"请上传车尾45度角照片");
                } else if (excFile2.getUrl().equals("")){
                    ProjectUtil.show(this,"上传手持送货单和车牌照的组合照片");
                } else {
                    netType = 1;
                    CheckOnLoadApi cola = new CheckOnLoadApi();
                    cola.setHeader(app.getAuthorization());
                    cola.setUserHeader(app.getUserHeader());
                    cola.setFileList(exFileList);
                    cola.setTfid(tfid);
                    if (type == 1) {
                        //更新运单状态
                        cola.setDealstatus("2");  //3=确认到达
                    } else {
                        cola.setDealstatus("4"); // 6= 申请支付
                    }
                    pClass.startHttpRequest(this, cola);
                }

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
        //图片上传
        if (netType == 0) {
            JSONObject jsonObject = JSONObject.parseObject(data);
            String state = jsonObject.getString("state");

            if (state.equals("1")) {
                ProjectUtil.show(this, jsonObject.getString("msg"));
                JSONObject object = jsonObject.getJSONObject("data");
                String pId = object.getString("Id");
                String pUrl = object.getString("Url");
                String FileName=object.getString("FileName");

               if (!carTailImgUploadSuccess) {
                   carTailImgUploadSuccess = true;

                   excFile1=new ExceptionFile();
                   excFile1.setFileName(FileName);
                   excFile1.setId(pId);
                   excFile1.setUrl(pUrl);
                   exFileList.add(excFile1);

               }else if (!deliveryImgUploadSuccess){
                   deliveryImgUploadSuccess = true;

                   excFile2=new ExceptionFile();
                   excFile2.setFileName(FileName);
                   excFile2.setId(pId);
                   excFile2.setUrl(pUrl);
                   exFileList.add(excFile2);
               }

                mHandler.sendEmptyMessage(6);
            }

        } else if(netType == 1) {
            ProjectUtil.show(this, data);

            JSONObject jsonObject = JSONObject.parseObject(data);
            String state = jsonObject.getString("state");

            if (state.equals("1")){
                ProjectUtil.show(this, "点击了提交");
                //提交
                finish();
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
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getTakePhoto().onSaveInstanceState(outState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void takeSuccess(TResult result) {
        iconPath = result.getImage().getOriginalPath();
        netType = 0;
        File imgFile = new File(iconPath);
        UploadFileApi uploadImgApi = new UploadFileApi();
        uploadImgApi.setHeader(app.getAuthorization());
        uploadImgApi.setUserHeader(app.getUserHeader());
        uploadImgApi.setImg(imgFile);
        pClass.startHttpRequest(WaybillPhotoActivity.this, uploadImgApi);
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
