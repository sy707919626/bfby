package com.lulian.driver.entity.api;

import com.lulian.driver.HttpService;
import com.rxretrofitlibrary.retrofit_rx.Api.BaseApi;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by MARK on 2018/6/15.
 */

public class UploadImgApi extends BaseApi {
     private File img;
    private String fileType;

    public File getImg() {
        return img;
    }

    public void setImg(File img) {
        this.img = img;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {

        HttpService httpService = retrofit.create(HttpService.class);
        MultipartBody.Builder builder = new MultipartBody.Builder();

//        JSONObject root = new JSONObject();
//        root.put("file", getImg());
//        RequestBody body=RequestBody.create(okhttp3.MediaType.parse("image/*"),
//                root.toString());


        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), getImg());
        builder.addFormDataPart("file", getImg().getName(), requestBody);
        builder.setType(MultipartBody.FORM);
        MultipartBody multipartBody = builder.build();
        return httpService.uploadImage(getHeader(),getUserHeader(),multipartBody,getFileType());

//        Map<String, RequestBody> map = new HashMap<>();
//        map.put("Id", RequestBody.create(MediaType. parse("text/plain"), ""));
//
//        map.put(parseImageMapKey("head",getImg().getName()),RequestBody.create(MediaType.parse("image/*"), getImg()));
//
//        return httpService.uploadHeadImage(getHeader(), map);

    }

    @Override
    public String call(String httpResult) {
        return httpResult;
    }

    @Override
    protected boolean isNeedData() {
        return true;
    }
}
