package com.lulian.driver.entity.server.resulte;

import java.io.Serializable;

/**
 * 保存个人信息接口 {@link com.lulian.driver.entity.api.SaveDriverInfoStep1Api}
 * 保存车辆信息接口 {@link com.lulian.driver.entity.api.SaveDriverInfoStep2Api}
 * 的返回数据对象
 * @author hxb
 */
public class SaveDriverInfoStepResult implements Serializable {

    //这个字段用来标识完善信息的情况
    private String IsComplete;

    public String getIsComplete() {
        return IsComplete;
    }
}
