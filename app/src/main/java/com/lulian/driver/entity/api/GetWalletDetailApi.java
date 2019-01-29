package com.lulian.driver.entity.api;


import com.lulian.driver.HttpService;
import com.rxretrofitlibrary.retrofit_rx.Api.BaseApi;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.RequestBody;
import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by MARK on 2018/6/14.
 * 获取钱包明细接口
 */

public class GetWalletDetailApi extends BaseApi {
    private int rows;
    private int page;

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        HttpService httpService = retrofit.create(HttpService.class);
        JSONObject root = new JSONObject();
        try {
            root.put("Rows",rows);
            root.put("Page", page);
            root.put("Sidx","CreateTime");
            root.put("Sord","DESC");

            String whereRaw="PayerUserId ='userid' or ReceiverUserId ='userid'";
            String where = whereRaw.replaceAll("userid", getUserId());
            root.put("Where", where);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        RequestBody body=RequestBody.create(okhttp3.MediaType.parse("text/json; charset=utf-8"),
                root.toString());
        return httpService.getWalletDetail(getHeader(),getUserHeader(),body);
    }


    @Override
    protected boolean isNeedData() {
        return true;
    }

}
