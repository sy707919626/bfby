package com.lulian.driver.entity.server.resulte;

import java.util.List;

/**
 * Created by MARK on 2018/7/12.
 */

public class HistoryRemark {
    private String Id;
    private List<String> list;
    private String Content;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}
