package com.lulian.driver.entity.api;

import com.lulian.driver.HttpService;
import com.rxretrofitlibrary.retrofit_rx.Api.BaseApi;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by MARK on 2018/6/14.
 */

public class GetCommentsTagsApi extends BaseApi {
    private int stargrade;
    private int category;

    public int getStargrade() {
        return stargrade;
    }

    public void setStargrade(int stargrade) {
        this.stargrade = stargrade;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        HttpService httpService = retrofit.create(HttpService.class);

        return httpService.getCommentsTags(getHeader(), getUserHeader(),getStargrade(), getCategory());
    }

    @Override
    protected boolean isNeedData() {
        return true;
    }

}
