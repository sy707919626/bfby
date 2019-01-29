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

public class SubmitCommentResultApi extends BaseApi {

    private String OrderId;
    private String Commenteduid;
    private int StarGrade;
    private String additionalProp1;
    private String additionalProp2;
    private String additionalProp3;
    private String OtherContent;
    private String Category;
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

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }

    public String getCommenteduid() {
        return Commenteduid;
    }

    public void setCommenteduid(String commenteduid) {
        Commenteduid = commenteduid;
    }

    public int getStarGrade() {
        return StarGrade;
    }

    public void setStarGrade(int starGrade) {
        StarGrade = starGrade;
    }

    public String getAdditionalProp1() {
        return additionalProp1;
    }

    public void setAdditionalProp1(String additionalProp1) {
        this.additionalProp1 = additionalProp1;
    }

    public String getAdditionalProp2() {
        return additionalProp2;
    }

    public void setAdditionalProp2(String additionalProp2) {
        this.additionalProp2 = additionalProp2;
    }

    public String getAdditionalProp3() {
        return additionalProp3;
    }

    public void setAdditionalProp3(String additionalProp3) {
        this.additionalProp3 = additionalProp3;
    }

    public String getOtherContent() {
        return OtherContent;
    }

    public void setOtherContent(String otherContent) {
        OtherContent = otherContent;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        HttpService httpService = retrofit.create(HttpService.class);
        JSONObject root = new JSONObject();
        try {
            root.put("OrderId", getOrderId());
            root.put("Commenteduid", getCommenteduid());
            root.put("StarGrade", getStarGrade());
            JSONObject jsArObj = new JSONObject();
            jsArObj.put("additionalProp1", getAdditionalProp1());
            jsArObj.put("additionalProp1", getAdditionalProp2());
            jsArObj.put("additionalProp3", getAdditionalProp3());
            root.put("Tags", jsArObj);
            root.put("OtherContent", getOtherContent());
            root.put("Category", getCategory());
            root.put("Rows", getRows());
            root.put("Page", getPage());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("text/json; charset=utf-8"),
                root.toString());
        return httpService.SubmitCommentResutl(getHeader(), getUserHeader(), body);
    }

    @Override
    protected boolean isNeedData() {
        return false;
    }
}
