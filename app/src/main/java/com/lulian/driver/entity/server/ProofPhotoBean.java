package com.lulian.driver.entity.server;

/**
 * 表示运单状态各个节点转换时上传的 照片证明  的数据对象
 * @author hxb
 */
public class ProofPhotoBean {

    private int F_Type;
    private int F_Location;
    private String F_Url;

    public int getF_Location() {
        return F_Location;
    }

    public void setF_Location(int F_Location) {
        this.F_Location = F_Location;
    }

    public String getF_Url() {
        return F_Url;
    }

    public void setF_Url(String F_Url) {
        this.F_Url = F_Url;
    }

    public int getF_Type() {
        return F_Type;
    }

    public void setF_Type(int F_Type) {
        this.F_Type = F_Type;
    }
}
