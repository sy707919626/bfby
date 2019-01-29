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
 */

public class DriverLeaderListApi extends BaseApi {

    private Integer Favorite; //是否收藏
    private String Starareaid; //起始地
    private String Endareaid;  //目的地
    private Integer Star;   //星级
    private int rows=10;
    private int page;
    private String Sidx;
    private String Sord;

    public Integer getStar() {
        return Star;
    }

    public void setStar(Integer star) {
        Star = star;
    }

    public Integer getFavorite() {
        return Favorite;
    }

    public void setFavorite(Integer favorite) {
        Favorite = favorite;
    }

    public String getStarareaid() {
        return Starareaid;
    }

    public void setStarareaid(String starareaid) {
        Starareaid = starareaid;
    }

    public String getEndareaid() {
        return Endareaid;
    }

    public void setEndareaid(String endareaid) {
        Endareaid = endareaid;
    }

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

    public String getSidx() {
        return Sidx;
    }

    public void setSidx(String sidx) {
        Sidx = sidx;
    }

    public String getSord() {
        return Sord;
    }

    public void setSord(String sord) {
        Sord = sord;
    }

    public Observable getObservable(Retrofit retrofit) {
        HttpService httpService = retrofit.create(HttpService.class);

        JSONObject root = new JSONObject();
        JSONObject param=new JSONObject();

        try {
            param.put("Starareaid",getStarareaid());
            param.put("Endareaid",getEndareaid());
            param.put("Star",Star);
            param.put("Favorite", Favorite);

            root.put("Param",param);
            root.put("Rows",getRows());
            root.put("Page",getPage());
            root.put("Sidx",getSidx());
            root.put("Sord",getSord());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body=RequestBody.create(okhttp3.MediaType.parse("text/json; charset=utf-8"),
                root.toString());

        return httpService.getDriverLeaderList(getHeader(),getUserHeader(),body);
    }

    @Override
    public String call(String httpResult) {
        return super.call(httpResult);
    }

    @Override
    protected boolean isNeedData() {
        return true;
    }
}
