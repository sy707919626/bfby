package com.lulian.driver.entity.server.req;

/**
 * 报价请求参数对象
 * @author hxb
 */
public class ReqOfferBean {

    private String OrderId;
    private int Price;

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String OrderId) {
        this.OrderId = OrderId;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int Price) {
        this.Price = Price;
    }
}
