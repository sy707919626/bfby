package com.lulian.driver.utils.feature;

import java.io.Serializable;

/**
 * 定义运单操作按钮的操作类型
 * @author hxb
 */
public enum WayBillBtnOp implements Serializable {

    CONFIRM_ARRIVE_LOAD_POINT("到达装货地"),
    CONFIRM_START_OFF("确认发车"),
    CONFIRM_ARRIVE_DEST("到达目的地"),
    CONFIRM_GOODS_ARRIVE("确认到货"),
    EXCEPTION_RECORD("异常记录"),
    LOAD_POINT_NAVIGATION("装货地导航"),
    DEST_POINT_NAVIGATION("目的地导航"),
    DROP_ORDER("退单"),
    APPRAISE("评价"),
    REMIND_PAY("提醒付款");

    private String text;

    WayBillBtnOp(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
