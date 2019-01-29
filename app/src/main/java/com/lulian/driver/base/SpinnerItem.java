package com.lulian.driver.base;

/**
 * 通用的spinner条目数据对象
 * @author hxb
 */
public class SpinnerItem {

    private String value;
    private String text;


    public SpinnerItem(String text, String value) {
        this.text = text;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
