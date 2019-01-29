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
 * Created by Administrator on 2018/8/13.
 */

public class AddInviteDriverLeaderApi extends BaseApi {
    private String PhoneNo;
    private String Name;
    private String InviteCode;
    private String IdentityCode;
    private ArrayList<ExceptionFile> fileList;

    public String getPhoneNo() {
        return PhoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        PhoneNo = phoneNo;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getInviteCode() {
        return InviteCode;
    }

    public void setInviteCode(String inviteCode) {
        InviteCode = inviteCode;
    }

    public String getIdentityCode() {
        return IdentityCode;
    }

    public void setIdentityCode(String identityCode) {
        IdentityCode = identityCode;
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
            root.put("PhoneNo",getPhoneNo());
            root.put("Name",getName());
            root.put("InviteCode",getInviteCode());
            root.put("IdentityCode", getIdentityCode());

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

        return httpService.addInviteDriverLeader(getHeader(),getUserHeader(),body);
    }

    @Override
    protected boolean isNeedData() {
        return false;
    }
}
