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
 * Created by MARK on 2018/6/26.
 */

public class CheckOnLoadApi extends BaseApi {
    private String tfid;
    private String dealstatus;

    public ArrayList<ExceptionFile> getFileList() {
        return fileList;
    }

    public void setFileList(ArrayList<ExceptionFile> fileList) {
        this.fileList = fileList;
    }

    private ArrayList<ExceptionFile> fileList;

    public String getDealstatus() {
        return dealstatus;
    }

    public void setDealstatus(String dealstatus) {
        this.dealstatus = dealstatus;
    }

    public String getTfid() {
        return tfid;
    }

    public void setTfid(String tfid) {
        this.tfid = tfid;
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        HttpService httpService = retrofit.create(HttpService.class);

        JSONObject root = new JSONObject();
        try {
            JSONArray jsonArray = new JSONArray();

            JSONObject jsArObj = new JSONObject();
            jsArObj.put("Id", "");
            jsArObj.put("FileName", "");
            jsArObj.put("Url", "");

            jsonArray.put(jsArObj);

            root.put("", jsonArray);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("text/json; charset=utf-8"),
                root.toString());
        return httpService.checkOnLoad(getHeader(),getUserHeader(), getTfid(), getDealstatus(),body);
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
