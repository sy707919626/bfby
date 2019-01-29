package com.lulian.driver.entity.server.resulte;

import java.io.Serializable;

/**
 * Created by MARK on 2018/6/19.
 */

public class Lines implements Serializable {
    private String startAreaId;
    private String endAreaId;

    public Lines(String startAreaId, String endAreaId) {
        setStartAreaId(startAreaId);
        setEndAreaId(endAreaId);
    }

    public String getStartAreaId() {
        return startAreaId;
    }

    public void setStartAreaId(String startAreaId) {
        this.startAreaId = startAreaId;
    }

    public String getEndAreaId() {
        return endAreaId;
    }

    public void setEndAreaId(String endAreaId) {
        this.endAreaId = endAreaId;
    }
}