package com.lulian.driver.entity.server.resulte;

/**
 * Created by MARK on 2018/7/10.
 */

public class OrderReturnHistory {
    /**
     * tobackuser : null
     * CreateTime : 2018-07-09 18:10:31
     * AcceptTime : 2018-07-09 18:10:31
     * ConsignorPrice : 100
     * MarketPrice : 120
     * Remark : StringXXXX
     */

    private String tobackuser;
    private String CreateTime;
    private String AcceptTime;
    private int ConsignorPrice;
    private int MarketPrice;
    private String Remark;

    public String getTobackuser() {
        return tobackuser;
    }

    public void setTobackuser(String tobackuser) {
        this.tobackuser = tobackuser;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String CreateTime) {
        this.CreateTime = CreateTime;
    }

    public String getAcceptTime() {
        return AcceptTime;
    }

    public void setAcceptTime(String AcceptTime) {
        this.AcceptTime = AcceptTime;
    }

    public int getConsignorPrice() {
        return ConsignorPrice;
    }

    public void setConsignorPrice(int ConsignorPrice) {
        this.ConsignorPrice = ConsignorPrice;
    }

    public int getMarketPrice() {
        return MarketPrice;
    }

    public void setMarketPrice(int MarketPrice) {
        this.MarketPrice = MarketPrice;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String Remark) {
        this.Remark = Remark;
    }

}
