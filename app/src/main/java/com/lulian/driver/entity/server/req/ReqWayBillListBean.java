package com.lulian.driver.entity.server.req;

import java.io.Serializable;
import java.util.List;

/**
 * 运单列表请求参数对象
 * @author hxb
 */
public class ReqWayBillListBean implements Serializable {
    private List<Integer> Status;
    private int PageIndex;
    private int PageSize=10;

    public void setStatus(List<Integer> status) {
        Status = status;
    }

    public void setPageIndex(int pageIndex) {
        PageIndex = pageIndex;
    }

    public void setPageSize(int pageSize) {
        PageSize = pageSize;
    }
}
