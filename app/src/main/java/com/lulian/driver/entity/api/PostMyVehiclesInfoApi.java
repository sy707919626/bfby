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
 * Created by Administrator on 2018/8/17.
 */

public class PostMyVehiclesInfoApi extends BaseApi {
    /**
     * Id : 1
     * PlateNo : string //车牌号
     * VehicleType : string //车型
     * VehicleLength : 0
     * VehicleHeight : 0
     * VehicleWidth : 0
     * VehicleWeight : 0
     * VehicleVolume : 0
     * VehicleOwner : string  //车主
     * LicUrl : string  //行驶证正面Url
     * InsuranceUrl : string  //保险卡正面Url
     * VPicUrl : string  //车辆全身照Url
     * TaxUrl : string  //车辆购置税Url
     * BizLicUrl : string  //运营证Url
     * RegState : 0  //认证状态
     * UseStatus : 0  //启用状态
     * ProductDate : 2018-08-17T03:44:01.288Z //出厂日期
     */

    private String Id;
    private String PlateNo;
    private String VehicleType;
    private int VehicleLength;
    private int VehicleHeight;
    private int VehicleWidth;
    private int VehicleWeight;
    private int VehicleVolume;
    private String VehicleOwner;
    private String LicUrl;
    private String InsuranceUrl;
    private String VPicUrl;
    private String TaxUrl;
    private String BizLicUrl;
    private int RegState;
    private int UseStatus;
    private String ProductDate;
    private ArrayList<ExceptionFile> fileList;

    public ArrayList<ExceptionFile> getFileList() {
        return fileList;
    }

    public void setFileList(ArrayList<ExceptionFile> fileList) {
        this.fileList = fileList;
    }

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getPlateNo() {
        return PlateNo;
    }

    public void setPlateNo(String PlateNo) {
        this.PlateNo = PlateNo;
    }

    public String getVehicleType() {
        return VehicleType;
    }

    public void setVehicleType(String VehicleType) {
        this.VehicleType = VehicleType;
    }

    public int getVehicleLength() {
        return VehicleLength;
    }

    public void setVehicleLength(int VehicleLength) {
        this.VehicleLength = VehicleLength;
    }

    public int getVehicleHeight() {
        return VehicleHeight;
    }

    public void setVehicleHeight(int VehicleHeight) {
        this.VehicleHeight = VehicleHeight;
    }

    public int getVehicleWidth() {
        return VehicleWidth;
    }

    public void setVehicleWidth(int VehicleWidth) {
        this.VehicleWidth = VehicleWidth;
    }

    public int getVehicleWeight() {
        return VehicleWeight;
    }

    public void setVehicleWeight(int VehicleWeight) {
        this.VehicleWeight = VehicleWeight;
    }

    public int getVehicleVolume() {
        return VehicleVolume;
    }

    public void setVehicleVolume(int VehicleVolume) {
        this.VehicleVolume = VehicleVolume;
    }

    public String getVehicleOwner() {
        return VehicleOwner;
    }

    public void setVehicleOwner(String VehicleOwner) {
        this.VehicleOwner = VehicleOwner;
    }

    public String getLicUrl() {
        return LicUrl;
    }

    public void setLicUrl(String LicUrl) {
        this.LicUrl = LicUrl;
    }

    public String getInsuranceUrl() {
        return InsuranceUrl;
    }

    public void setInsuranceUrl(String InsuranceUrl) {
        this.InsuranceUrl = InsuranceUrl;
    }

    public String getVPicUrl() {
        return VPicUrl;
    }

    public void setVPicUrl(String VPicUrl) {
        this.VPicUrl = VPicUrl;
    }

    public String getTaxUrl() {
        return TaxUrl;
    }

    public void setTaxUrl(String TaxUrl) {
        this.TaxUrl = TaxUrl;
    }

    public String getBizLicUrl() {
        return BizLicUrl;
    }

    public void setBizLicUrl(String BizLicUrl) {
        this.BizLicUrl = BizLicUrl;
    }

    public int getRegState() {
        return RegState;
    }

    public void setRegState(int RegState) {
        this.RegState = RegState;
    }

    public int getUseStatus() {
        return UseStatus;
    }

    public void setUseStatus(int UseStatus) {
        this.UseStatus = UseStatus;
    }

    public String getProductDate() {
        return ProductDate;
    }

    public void setProductDate(String ProductDate) {
        this.ProductDate = ProductDate;
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        HttpService httpService = retrofit.create(HttpService.class);

        JSONObject root = new JSONObject();
        try {
            root.put("Id",getId());
            root.put("PlateNo",getPlateNo());
            root.put("VehicleType",getVehicleType());
            root.put("VehicleLength", getVehicleLength());
            root.put("VehicleHeight",getVehicleHeight());
            root.put("VehicleWidth",getVehicleWidth());
            root.put("VehicleWeight",getVehicleWeight());
            root.put("VehicleVolume", getVehicleVolume());
            root.put("VehicleOwner",getVehicleOwner());
            root.put("LicUrl",getLicUrl());
            root.put("InsuranceUrl",getInsuranceUrl());
            root.put("VPicUrl", getVPicUrl());
            root.put("TaxUrl",getTaxUrl());
            root.put("BizLicUrl",getBizLicUrl());
            root.put("RegState",getRegState());
            root.put("UseStatus",getUseStatus());
            root.put("ProductDate",getProductDate());

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

        return httpService.postMyVehiclesInfo(getHeader(),getUserHeader(),body);
    }


    @Override
    protected boolean isNeedData() {
        return false;
    }
}
