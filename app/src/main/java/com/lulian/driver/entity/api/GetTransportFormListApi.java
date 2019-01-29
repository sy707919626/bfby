package com.lulian.driver.entity.api;

import com.lulian.driver.HttpService;
import com.rxretrofitlibrary.retrofit_rx.Api.BaseApi;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.RequestBody;
import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by MARK on 2018/6/26.
 */

public class GetTransportFormListApi extends BaseApi {
    private int Param;
    private int Rows;
    private int Page;

    public int getParam() {
        return Param;
    }

    public void setParam(int param) {
        Param = param;
    }

    public int getRows() {
        return Rows;
    }

    public void setRows(int rows) {
        Rows = rows;
    }

    public int getPage() {
        return Page;
    }

    public void setPage(int page) {
        Page = page;
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        HttpService httpService = retrofit.create(HttpService.class);
        JSONObject root = new JSONObject();
        try {
            root.put("Param",getParam());
            root.put("Rows",getRows());
            root.put("Page",getPage());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body=RequestBody.create(okhttp3.MediaType.parse("text/json; charset=utf-8"),
                root.toString());
        return httpService.getTransportFormList(getHeader(),getUserHeader(),body);
    }


    @Override
    protected boolean isNeedData() {
        return true;
    }

}
