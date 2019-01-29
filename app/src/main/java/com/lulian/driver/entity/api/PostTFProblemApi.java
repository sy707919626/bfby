package com.lulian.driver.entity.api;

import com.lulian.driver.HttpService;
import com.lulian.driver.entity.server.resulte.ExceptionFile;
import com.rxretrofitlibrary.retrofit_rx.Api.BaseApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.RequestBody;
import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by MARK on 2018/6/14.
 */

public class PostTFProblemApi extends BaseApi {
    //
    private String tfid;
    private String ProblemDiscript; //异常说明
    private String HappenTime; //异常时间
    private String HappenLocation; //异常地点
    private ArrayList<ExceptionFile> fileList;
    private int recordtype; //请求人类型 1、司机

    public String getTfid() {
        return tfid;
    }

    public void setTfid(String tfid) {
        this.tfid = tfid;
    }

    public String getProblemDiscript() {
        return ProblemDiscript;
    }

    public void setProblemDiscript(String problemDiscript) {
        ProblemDiscript = problemDiscript;
    }

    public String getHappenTime() {
        return HappenTime;
    }

    public void setHappenTime(String happenTime) {
        HappenTime = happenTime;
    }

    public String getHappenLocation() {
        return HappenLocation;
    }

    public void setHappenLocation(String happenLocation) {
        HappenLocation = happenLocation;
    }

    public ArrayList<ExceptionFile> getFileList() {
        return fileList;
    }

    public void setFileList(ArrayList<ExceptionFile> fileList) {
        this.fileList = fileList;
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        HttpService httpService = retrofit.create(HttpService.class);

        JSONObject root = new JSONObject();
        try {
            root.put("ProblemDiscript",getProblemDiscript());
            root.put("HappenTime",getHappenTime());
            root.put("HappenLocation",getHappenLocation());
            JSONArray jsonArray = new JSONArray();

            for(ExceptionFile file:getFileList()){
                JSONObject jsArObj=new JSONObject();
                jsArObj.put("Id",file.getId());
                jsArObj.put("FileName",file.getFileName());
                jsArObj.put("Url",file.getUrl());
                jsonArray.put(jsArObj);
            }
            root.put("Files", jsonArray.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body=RequestBody.create(okhttp3.MediaType.parse("text/json; charset=utf-8"),
                root.toString());
        return httpService.postTFProblem(getHeader(),getUserHeader(),getTfid(),1, body);
    }

    @Override
    public String call(String httpResult) {
        return httpResult;
    }

    @Override
    protected boolean isNeedData() {
        return false;
    }

}
