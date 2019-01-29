package com.lulian.driver.entity.server.resulte;

/**
 * Created by MARK on 2018/7/9.
 */

public class OrderNew {
/*   "id": "87b869a7-0004-48b7-939e-63fa8979cd7d",
      "Phone": null,
      "OnloadareaId": "350102",
      "OnLoadArea": "福建 福州市 鼓楼区",
      "UnLoadAreaId": "320102",
      "UnLoadArea": "江苏 南京市 玄武区",
      "Star": 10,
      "AutomobileTypID": "b10409cb-db98-4277-a63a-a66a8388c682",
      "AutomobileTypName": "高栏车",
      "AutomobileLength": 96,
      "DriverLeaderID": "49ad954c-df3e-4431-8c54-054c7b4cf3b6",
      "goods": "Orange",
      "Name": "zzz",
      "HeaderUrl": null,
      "CreateTime": "2018-08-04 17:11:27"*/

//<Number>10.00</Number><Nunit>吨</Nunit>

    private String Id;
    private String Phone;
    private String OnloadareaId;
    private String OnLoadArea;
    private String UnLoadAreaId;
    private String UnLoadArea;
    private int Star;
    private String AutomobileTypID;
    private String AutomobileTypName;
    private int AutomobileLength;
    private String DriverLeaderID;
    private String goods;
    private String Name;
    private String HeaderUrl;
    private String CreateTime;

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public int getStar() {
        return Star;
    }

    public void setStar(int star) {
        Star = star;
    }

    public String getOnloadareaId() {
        return OnloadareaId;
    }

    public void setOnloadareaId(String onloadareaId) {
        OnloadareaId = onloadareaId;
    }

    public String getUnLoadAreaId() {
        return UnLoadAreaId;
    }

    public void setUnLoadAreaId(String unLoadAreaId) {
        UnLoadAreaId = unLoadAreaId;
    }

    public String getAutomobileTypID() {
        return AutomobileTypID;
    }

    public void setAutomobileTypID(String automobileTypID) {
        AutomobileTypID = automobileTypID;
    }

    public String getDriverLeaderID() {
        return DriverLeaderID;
    }

    public void setDriverLeaderID(String driverLeaderID) {
        DriverLeaderID = driverLeaderID;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }


    public int getAutomobileLength() {
        return AutomobileLength;
    }

    public void setAutomobileLength(int automobileLength) {
        AutomobileLength = automobileLength;
    }


    public String getAutomobileTypName() {
        return AutomobileTypName;
    }

    public void setAutomobileTypName(String automobileTypName) {
        AutomobileTypName = automobileTypName;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getHeaderUrl() {
        return HeaderUrl;
    }

    public void setHeaderUrl(String headerUrl) {
        HeaderUrl = headerUrl;
    }

    public String getGoods() {
        return goods;
    }

    public void setGoods(String goods) {
        this.goods = goods;
    }

    public String getOnLoadArea() {
        return OnLoadArea;
    }

    public void setOnLoadArea(String onLoadArea) {
        OnLoadArea = onLoadArea;
    }

    public String getUnLoadArea() {
        return UnLoadArea;
    }

    public void setUnLoadArea(String unLoadArea) {
        UnLoadArea = unLoadArea;
    }

}
