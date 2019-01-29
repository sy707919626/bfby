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

public class ReturnOrderApi extends BaseApi {
    private String orderId;
    private double ConsignorPrice;//货主出价
    private double MarketPrice;//市场价格
    private String Remark;//其他原因

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public double getConsignorPrice() {
        return ConsignorPrice;
    }

    public void setConsignorPrice(double consignorPrice) {
        ConsignorPrice = consignorPrice;
    }

    public double getMarketPrice() {
        return MarketPrice;
    }

    public void setMarketPrice(double marketPrice) {
        MarketPrice = marketPrice;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        HttpService httpService = retrofit.create(HttpService.class);
        JSONObject root = new JSONObject();
        JSONObject param=new JSONObject();
        try {
            root.put("ConsignorPrice",getConsignorPrice());
            root.put("MarketPrice",getMarketPrice());
            root.put("Remark",getRemark());
            root.put("orderId",getOrderId());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("text/json; charset=utf-8"),
                root.toString());

        return httpService.returnOrder(getHeader(),getUserHeader(),body);
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
