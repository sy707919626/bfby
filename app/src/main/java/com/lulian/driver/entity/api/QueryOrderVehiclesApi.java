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

public class QueryOrderVehiclesApi extends BaseApi {
    private boolean IsTaked;//是否已抢订单
    private String StartAreaId; //起运地区ID
    private String EndAreaId; //目的地区ID
    private String VehicleType; //车型ID
    private int OrderState; //订单状态
    private String VehicleLength; // 车长
    private String DriverLaderId; //我的车长
    private int Rows; //分页行数
    private int Page; //页码
    private String Sidx; //排序列名
    private String Sord; //排序方向
    private int SendType; //查询条件 1,2为全部订单； 3为任务订单

    public int getSendType() {
        return SendType;
    }

    public void setSendType(int sendType) {
        SendType = sendType;
    }

    public String getVehicleLength() {
        return VehicleLength;
    }

    public void setVehicleLength(String vehicleLength) {
        VehicleLength = vehicleLength;
    }

    public String getDriverLaderId() {
        return DriverLaderId;
    }

    public void setDriverLaderId(String driverLaderId) {
        DriverLaderId = driverLaderId;
    }

    public boolean isTaked() {
        return IsTaked;
    }

    public void setTaked(boolean taked) {
        IsTaked = taked;
    }

    public String getStartAreaId() {
        return StartAreaId;
    }

    public void setStartAreaId(String startAreaId) {
        StartAreaId = startAreaId;
    }

    public String getEndAreaId() {
        return EndAreaId;
    }

    public void setEndAreaId(String endAreaId) {
        EndAreaId = endAreaId;
    }

    public String getVehicleType() {
        return VehicleType;
    }

    public void setVehicleType(String vehicleType) {
        VehicleType = vehicleType;
    }

    public int getOrderState() {
        return OrderState;
    }

    public void setOrderState(int orderState) {
        OrderState = orderState;
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

    @Override
    public Observable getObservable(Retrofit retrofit) {
        HttpService httpService = retrofit.create(HttpService.class);

        JSONObject root = new JSONObject();
        JSONObject param=new JSONObject();
        try {
            param.put("IsTaked",isTaked());

            if(isTaked()){
                param.put("StartAreaId",getStartAreaId());
                param.put("EndAreaId",getEndAreaId());
                param.put("VehicleType",getVehicleType());
                param.put("DriverLaderId", getDriverLaderId());
                param.put("VehicleLength", getVehicleLength());

                param.put("OrderState", getOrderState());

            }else{

            }
            root.put("Param",param);
            root.put("Rows",getRows());
            root.put("Page",getPage());
            root.put("Sidx",getSidx());
            root.put("Sord",getSord());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("text/json; charset=utf-8"),
                root.toString());

//        JSONObject user = new JSONObject();
//        try {
//            user.put("userid", "71CA273B-638B-4783-846D-CE65D3AD38D3");
//            user.put("name", "");
//            user.put("roleid", "");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        RequestBody userHead=RequestBody.create(okhttp3.MediaType.parse("text/json; charset=utf-8"),
//                user.toString());
        return httpService.queryOrder(getHeader(),getUserHeader(),body, getSendType());

//        return httpService.queryOrder(getHeader(),user.toString(),body, getSendType());
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
