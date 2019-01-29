package com.lulian.driver.entity;

/**
 * 简单的数据对象类
 * 如果数据的形式是这种code和文字的,都共用这个类
 * @author
 */
public class SimpleBean {

    private String code;
    private String text;

    public SimpleBean(String code, String text) {
        this.code = code;
        this.text = text;
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
}
