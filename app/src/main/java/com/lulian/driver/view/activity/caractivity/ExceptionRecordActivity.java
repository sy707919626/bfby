package com.lulian.driver.view.activity.caractivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.lulian.driver.entity.api.PostTFProblemApi;
import com.lulian.driver.entity.api.UploadFileApi;
import com.lulian.driver.entity.server.resulte.ExceptionFile;
import com.lulian.driver.utils.L;
import com.lulian.driver.utils.ProjectUtil;
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

public class ExceptionRecordActivity extends BaseActivity implements TakePhoto.TakeResultListener, InvokeListener {

    @BindView(R.id.notestxtstartstr)
    TextView notestxtstartstr;
    @BindView(R.id.notestxtendstr)
    TextView notestxtendstr;
    @BindView(R.id.reportimgdelete)
    ImageView reportimgdelete;
    @BindView(R.id.reporteditmsg)
    EditText reporteditmsg;
    @BindView(R.id.reportbtntakephoto)
    Button reportbtntakephoto;
    @BindView(R.id.reportbtnslbum)
    Button reportbtnslbum;
    @BindView(R.id.reportimgimg1)
    ImageView reportimgimg1;
    @BindView(R.id.reportimgimg2)
    ImageView reportimgimg2;
    @BindView(R.id.reportimgimg3)
    ImageView reportimgimg3;
    @BindView(R.id.reportbtnsubmit)
    Button reportbtnsubmit;


    private String iconPath = "";
    private InvokeParam invokeParam;
    private TakePhoto takePhoto;
    public boolean first;
    public boolean second;
    public boolean third;
    private ExceptionFile excFile1;
    private ExceptionFile excFile2;
    private ExceptionFile excFile3;
    private boolean firstImgUploadSuccess = false;
    private boolean secondImgUploadSuccess = false;
    private boolean thirdImgUploadSuccess = false;
    private String tfid = "";
    ArrayList<ExceptionFile> exFileList;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 6) {
                String realPath = "file://" + iconPath;
                if (firstImgUploadSuccess && !first) {
                    first = true;
                    Picasso.get().load(realPath).memoryPolicy(NO_CACHE, NO_STORE).into(reportimgimg1);
                    L.e("test", "firstImgUploadSuccess:" + firstImgUploadSuccess);
                } else if (secondImgUploadSuccess && !second) {
                    second = true;
                    Picasso.get().load(realPath).memoryPolicy(NO_CACHE, NO_STORE).into(reportimgimg2);
                    L.e("test", "secondImgUploadSuccess:" + secondImgUploadSuccess);
                } else if (thirdImgUploadSuccess && !third) {
                    third = true;
                    Picasso.get().load(realPath).memoryPolicy(NO_CACHE, NO_STORE).into(reportimgimg3);
                    L.e("test", "thirdImgUploadSuccess:" + thirdImgUploadSuccess);
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
        setContentView(R.layout.activity_exceptionrecord);
        Intent intent = getIntent();
        tfid = intent.getStringExtra("tfid");
        ButterKnife.bind(this);
        initView();

    }

    @Override
    public void onSuccess(String data) {
        L.e("test1", data);
        if (neType == 0) { //异常上传
            ProjectUtil.show(this, data);
            JSONObject jsonObject = JSONObject.parseObject(data);
            String state = jsonObject.getString("state");
            String msg = jsonObject.getString("msg");

            if (state.equals("1")) {
                finish();
            }

        } else if (neType == 6) {//图片上传
            JSONObject jo = JSONObject.parseObject(data);
            ProjectUtil.show(this, jo.getString("msg"));
            JSONObject object = jo.getJSONObject("data");
            String pId = object.getString("Id");
            String pUrl = object.getString("Url");
            String FileName = object.getString("FileName");
            if (!firstImgUploadSuccess) {
                firstImgUploadSuccess = true;
                excFile1 = new ExceptionFile();
                excFile1.setFileName(FileName);
                excFile1.setId(pId);
                excFile1.setUrl(pUrl);
                exFileList.add(excFile1);
            } else if (!secondImgUploadSuccess) {
                secondImgUploadSuccess = true;
                excFile2 = new ExceptionFile();
                excFile2.setFileName(FileName);
                excFile2.setId(pId);
                excFile2.setUrl(pUrl);
                exFileList.add(excFile2);
            } else if (!thirdImgUploadSuccess) {
                thirdImgUploadSuccess = true;
                excFile3 = new ExceptionFile();
                excFile3.setFileName(FileName);
                excFile3.setId(pId);
                excFile3.setUrl(pUrl);
                exFileList.add(excFile3);
            }
            mHandler.sendEmptyMessage(6);
        }
    }

    private void initView() {
        exFileList = new ArrayList<>();
    }


    @OnClick({R.id.reportbtntakephoto, R.id.reportbtnslbum, R.id.reportbtnsubmit, R.id.reportimgdelete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.reportbtntakephoto:
                initPhotoData(true);
                break;
            case R.id.reportbtnslbum:
                initPhotoData(false);
                break;
            case R.id.reportbtnsubmit:
                String exStr = reporteditmsg.getText().toString().trim();
                if (exStr.equals("")) {
                    ProjectUtil.show(this, "请填写异常内容!");
                } else {
                    neType = 0;
                    PostTFProblemApi ptfp = new PostTFProblemApi();
                    ptfp.setTfid(tfid);
                    ptfp.setHeader(app.getAuthorization());
                    ptfp.setUserHeader(app.getUserHeader());
                    ptfp.setFileList(exFileList);
                    ptfp.setProblemDiscript(exStr);
                    ptfp.setHappenTime("2018-07-23T01:40:39.301Z");
                    ptfp.setHappenLocation(exStr);
                    pClass.startHttpRequest(this, ptfp);
                }
                break;
            case R.id.reportimgdelete:
                this.finish();
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
//        ProjectUtil.show(MotorcadeActivity.this, iconPath);
//        mHandler.sendEmptyMessage(1);
        neType = 6;
        File imgFile = new File(iconPath);
        UploadFileApi uploadImgApi = new UploadFileApi();
        uploadImgApi.setHeader(app.getAuthorization());
        uploadImgApi.setUserHeader(app.getUserHeader());
        uploadImgApi.setImg(imgFile);
        pClass.startHttpRequest(ExceptionRecordActivity.this, uploadImgApi);
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
