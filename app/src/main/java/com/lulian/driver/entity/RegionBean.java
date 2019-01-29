package com.lulian.driver.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 *
 */
public class RegionBean {

    private String id;//地区编码
    private String name;//名称
    private boolean isMunicipal;//是否是直辖市
    private boolean isFullScopeOp;//是否为全境选项(全省,全市等)
    private int grade;//地区的级别

    @SerializedName("cityList")
    private List<RegionBean> childRegions;//子地区列表

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public boolean isFullScopeOp() {
        return isFullScopeOp;
    }

    public void setFullScopeOp(boolean fullScopeOp) {
        isFullScopeOp = fullScopeOp;
    }

    public boolean isMunicipal() {
        return isMunicipal;
    }
    public void setMunicipal(boolean municipal) {
        isMunicipal = municipal;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<RegionBean> getChildRegions() {
        return childRegions;
    }

    public void setChildRegions(List<RegionBean> childRegions) {
        this.childRegions = childRegions;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof RegionBean){
            RegionBean bean = (RegionBean) obj;
            return bean.id.equals(this.id);
        }else{
            return false;
        }
    }
}
