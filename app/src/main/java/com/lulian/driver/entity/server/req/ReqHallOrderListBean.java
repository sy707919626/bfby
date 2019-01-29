package com.lulian.driver.entity.server.req;

import com.lulian.driver.utils.BooleanConvertor;

import java.io.Serializable;
import java.util.List;

/**
 * @author hxb
 * 配货大厅列表请求参数对象
 */
public class ReqHallOrderListBean implements Serializable {

    private int PageIndex;
    private int PageSize=10;
    private String StarTtion; //起始地
    private List<String> EndTion;//目的地
    private List<String> LoadingTime;//装货时间范围
    private List<String> AutomobileLength;//车长
    private List<String> AutomobileTypName;//字段名虽然是name,但实际上要传车型id
    private int IsQuotation;//0:未报价订单,1:已报价订单

    public boolean getIsQuotation() {
        return BooleanConvertor.intToBoolean(IsQuotation);
    }

    public void setIsQuotation(boolean isQuotation) {
        IsQuotation = BooleanConvertor.booleanToInt(isQuotation);
    }

    public int getPageIndex() {
        return PageIndex;
    }

    public void setPageIndex(int pageIndex) {
        PageIndex = pageIndex;
    }

    public int getPageSize() {
        return PageSize;
    }

    public void setPageSize(int pageSize) {
        PageSize = pageSize;
    }

    public String getStarTtion() {
        return StarTtion;
    }

    public void setStarTtion(String starTtion) {
        StarTtion = starTtion;
    }

    public List<String> getEndTion() {
        return EndTion;
    }

    public void setEndTion(List<String> endTion) {
        EndTion = endTion;
    }

    public List<String> getLoadingTime() {
        return LoadingTime;
    }

    public void setLoadingTime(List<String> loadingTime) {
        LoadingTime = loadingTime;
    }

    public List<String> getAutomobileLength() {
        return AutomobileLength;
    }

    public void setAutomobileLength(List<String> automobileLength) {
        AutomobileLength = automobileLength;
    }

    public List<String> getAutomobileTypName() {
        return AutomobileTypName;
    }

    public void setAutomobileTypName(List<String> automobileTypName) {
        AutomobileTypName = automobileTypName;
    }
}
