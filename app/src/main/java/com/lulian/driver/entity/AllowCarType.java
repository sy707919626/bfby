package com.lulian.driver.entity;


/**
 * 驾驶证准驾车型对象
 * @author hxb
 */
public class AllowCarType {
    private String code;
    private String text;
    private String detail;

    public AllowCarType(String code, String text, String detail) {
        this.code = code;
        this.text = text;
        this.detail = detail;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
