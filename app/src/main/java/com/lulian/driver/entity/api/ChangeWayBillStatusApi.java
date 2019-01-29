package com.lulian.driver.entity.api;

import com.lulian.driver.HttpService;
import com.lulian.driver.entity.server.ProofPhotoBean;
import com.rxretrofitlibrary.retrofit_rx.Api.BaseApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * 改变运单状态接口
 * @author hxb
 */
public class ChangeWayBillStatusApi extends BaseApi {

    private String id;
    private int changeToStatus;//想要将运单状态改成哪个值

    private List<ProofPhotoBean> proofPhotoList=new ArrayList<>();//证明图片列表

    public void setProofPhotoList(List<ProofPhotoBean> proofPhotoList) {
        this.proofPhotoList = proofPhotoList;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setChangeToStatus(int changeToStatus) {
        this.changeToStatus = changeToStatus;
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        HttpService httpService = retrofit.create(HttpService.class);
        return httpService.changeTransportForm(header,userHeader,id,changeToStatus,proofPhotoList);
    }

    @Override
    protected boolean isNeedData() {
        return false;
    }
}
