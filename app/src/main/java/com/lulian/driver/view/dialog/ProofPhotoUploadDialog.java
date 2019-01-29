package com.lulian.driver.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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
import com.lulian.driver.base.BaseFragment;
import com.lulian.driver.entity.server.ProofPhotoBean;
import com.lulian.driver.utils.ProjectUtil;
import com.lulian.driver.utils.ScreenUtil;
import com.rxretrofitlibrary.retrofit_rx.utils.GlobalValue;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 司机进行改变状态操作时进行上传确认拍照的dialog
 * @author hxb
 */
public class ProofPhotoUploadDialog extends Dialog implements TakePhoto.TakeResultListener, InvokeListener {

    private BaseFragment fragment;
    private BaseActivity activity;
    private Context context;

    private TakePhoto takePhoto;
    InvokeParam invokeParam;

    public static final int PHOTO_CATEGORY_CONFIRM_START_OFF=100;//确认发车证明拍照 种类常量
    public static final int PHOTO_CATEGORY_CONFIRM_GOODS_ARRIVE=101;//确认到货证明拍照 种类常量
    private int photoCategory;//要上传的照片的种类

    private List<PanelViewHolder> panelViewHolders = new ArrayList<>();
    private int[] samplePhotoImgResIds;//外部设置进来的示例图片资源id
    private String[] photoDescriptions;//外部设置进来的图片描述文字
    private String[] uploadedPhotoUrls;//保存上传完成的图片url

    @BindViews({R.id.include_photo_panel_1,R.id.include_photo_panel_2})
    View[] panelViews;

    private Callback callback;

    public interface Callback{
        void onNotifyUploadFile(File photoFile);
        void onCommit(List<ProofPhotoBean> list);
    }

    private ProofPhotoUploadDialog(@NonNull Context context) {
        super(context);
        this.context = context;
        init();
    }

    public ProofPhotoUploadDialog(@NonNull Context context, BaseFragment fragment) {
        this(context);
        this.fragment = fragment;
    }


    public ProofPhotoUploadDialog(@NonNull Context context, BaseActivity activity) {
        this(context);
        this.activity = activity;
    }

    public void setPhotoCategory(int photoCategory) {
        this.photoCategory = photoCategory;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    /**
     * 设置示例图片的资源文件id
     * @param samplePhotoImgResIds
     */
    public void setSamplePhotoImgResIds(int[] samplePhotoImgResIds) {
        this.samplePhotoImgResIds = samplePhotoImgResIds;
    }
    /**
     * 设置图片的描述文字
     * @param photoDescriptions
     */
    public void setPhotoDescriptions(String[] photoDescriptions) {
        this.photoDescriptions = photoDescriptions;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void init() {
        setContentView(R.layout.dialog_status_change_upload_photo);
        ButterKnife.bind(this);

        //设置宽高,位置
        setCanceledOnTouchOutside(false);
        int screenWidth = ScreenUtil.getScreenWidth(getContext());
        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = screenWidth;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);

    }


    private void initView(){
        initPhotoDialog();

        for (View pv : panelViews) {
            pv.setVisibility(View.GONE);
        }

        for(int i=0;i<photoDescriptions.length;i++){
            View panelView = panelViews[i];
            panelView.setVisibility(View.VISIBLE);

            PanelViewHolder holder = new PanelViewHolder(panelView);
            String photoDescription = photoDescriptions[i];
            int samplePhotoImgResId = samplePhotoImgResIds[i];

            holder.tvPhotoDescribe.setText(photoDescription);
            holder.ivPhotoSample.setImageResource(samplePhotoImgResId);

            holder.setAddPhotoClickListener(addPhotoClickStubListener);
            panelViewHolders.add(holder);

        }

        uploadedPhotoUrls = new String[photoDescriptions.length];

    }



    @OnClick({R.id.btn_commit,
            R.id.iv_close})
    public void onViewClicked(View v){
        switch (v.getId()) {
            case R.id.btn_commit:
                if(checkPhotoIsComplete() && callback!=null){
                    callback.onCommit(buildReturnData());
                    dismiss();
                }
                break;
            case R.id.iv_close:
                dismiss();
                break;
        }
    }


    private List<ProofPhotoBean> buildReturnData(){
        List<ProofPhotoBean> list = new ArrayList<>();

        for(int i=0;i<uploadedPhotoUrls.length;i++){
            ProofPhotoBean bean = new ProofPhotoBean();
            bean.setF_Type(photoCategory);
            bean.setF_Location(i+1);
            bean.setF_Url(uploadedPhotoUrls[i]);
            list.add(bean);
        }

        return list;
    }

    /**
     * 检查照片是否拍摄完整
     */
    private boolean checkPhotoIsComplete(){
        for (String url : uploadedPhotoUrls) {
            if(url==null){
                ProjectUtil.show(context,"请将照片添加完整");
                return false;
            }
        }
        return true;
    }


    private int currentClickPanelIndex;//当前点击进行的图片添加框的index

    /**
     * 添加照片点击框点击监听
     */
    private View.OnClickListener addPhotoClickStubListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            PanelViewHolder holder= (PanelViewHolder) v.getTag();
            currentClickPanelIndex = panelViewHolders.indexOf(holder);

            String url = uploadedPhotoUrls[currentClickPanelIndex];
            if(url==null){
                showUploadFileDialog();
            }else{
                photoDialog.setImgUrl(GlobalValue.BASEURL + url).show();
            }

        }
    };


    private PhotoDisplayDialog photoDialog;//显示完成照片的dialog

    private void initPhotoDialog() {
        photoDialog = new PhotoDisplayDialog(context);
        photoDialog.setCallback(photoDialogCallback);
    }

    /**
     * 图片展示dialog的回调
     */
    private PhotoDisplayDialog.Callback photoDialogCallback = new PhotoDisplayDialog.Callback() {
        @Override
        public void onShootAgainClicked() {//点击了重新选择/拍摄 按钮
            showUploadFileDialog();
        }
    };

    public void showUploadFileDialog(){
        FragmentActivity context = (FragmentActivity) this.context;
        ProjectUtil.showUploadFileDialog(context, new AdapterView.OnItemClickListener() {
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
        });
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
    }

    private String currentTookPhotoPath;

    @Override
    public void takeSuccess(TResult result) {
        /**
         * 图片选择/拍摄成功后,通知外部调用接口进行上传图片
         */
        currentTookPhotoPath = result.getImage().getOriginalPath();
        if(callback!=null){
            File photoFile = new File(currentTookPhotoPath);
            callback.onNotifyUploadFile(photoFile);
        }
    }

    /**
     * 外部上传每上传一张图片成功,调用此方法来进行通知
     */
    public void oneItemPhotoUploadSuccess(String photoUrl){
        uploadedPhotoUrls[currentClickPanelIndex] = photoUrl;

        //将上传成功的图片显示到照片框中(使用本地路径来显示)
        PanelViewHolder holder = panelViewHolders.get(currentClickPanelIndex);
        holder.ivPhoto.setVisibility(View.VISIBLE);
        Picasso.get().load("file://"+currentTookPhotoPath).into(holder.ivPhoto);
    }


    @Override
    public void takeFail(TResult result, String msg) {
    }

    @Override
    public void takeCancel() {

    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        TContextWrap contextWrap;
        if(activity!=null){
            contextWrap = TContextWrap.of(activity);
        }else{
            contextWrap = TContextWrap.of(fragment);
        }
        PermissionManager.TPermissionType type = PermissionManager.checkPermission(contextWrap, invokeParam.getMethod());
        if (PermissionManager.TPermissionType.WAIT.equals(type)) {
            this.invokeParam = invokeParam;
        }
        return type;
    }

    /**
     * 获取TakePhoto实例
     *
     * @return
     */
    public TakePhoto getTakePhoto() {
        if (takePhoto == null) {
            TakePhotoImpl tpImpl;
            if(activity!=null){
                tpImpl = new TakePhotoImpl(activity, this);
            }else{
                tpImpl = new TakePhotoImpl(fragment, this);
            }
            takePhoto = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(tpImpl);
        }
        return takePhoto;
    }

    private void goToTakePhoto(boolean isFromCamera) {
        ////获取TakePhoto实例
        takePhoto = getTakePhoto();
        //设置裁剪参数
        CropOptions cropOptions = new CropOptions.Builder().create();
        //设置压缩参数
        CompressConfig compressConfig = new CompressConfig.Builder().setMaxPixel(500).create();
        takePhoto.onEnableCompress(compressConfig, true);  //设置为需要压缩
        if (isFromCamera) {
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

    protected static class PanelViewHolder{
        @BindView(R.id.vg_add_photo_click_stub)
        View clickStub;
        @BindView(R.id.tv_photo_describe)
        TextView tvPhotoDescribe;
        @BindView(R.id.iv_photo)
        ImageView ivPhoto;
        @BindView(R.id.iv_photo_sample)
        ImageView ivPhotoSample;

        public PanelViewHolder(View panelView) {
            ButterKnife.bind(this, panelView);
        }

        public void setAddPhotoClickListener(View.OnClickListener listener){
            clickStub.setOnClickListener(listener);
            clickStub.setTag(this);
        }
    }


}
