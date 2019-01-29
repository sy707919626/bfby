package com.lulian.driver.entity.server.resulte;

/**
 * 订单列表一个条目的数据对象
 * @author
 */
public class OrderListItem {

    private String Id;
    private String Phone;
    private String OnloadareaId;
    private String OnLoadArea;
    private String UnLoadAreaId;
    private String UnLoadArea;
    private int Star;
    private String AutomobileTypID;
    private double AutomobileLength;
    private String AutomobileTypName;
    private String GoodsName;
    private int Weight;
    private int Volume;
    private String Name;
    private String HeaderUrl;
    private String CreateTime;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getOnloadareaId() {
        return OnloadareaId;
    }

    public void setOnloadareaId(String onloadareaId) {
        OnloadareaId = onloadareaId;
    }

    public String getOnLoadArea() {
        return OnLoadArea;
    }

    public void setOnLoadArea(String onLoadArea) {
        OnLoadArea = onLoadArea;
    }

    public String getUnLoadAreaId() {
        return UnLoadAreaId;
    }

    public void setUnLoadAreaId(String unLoadAreaId) {
        UnLoadAreaId = unLoadAreaId;
    }

    public String getUnLoadArea() {
        return UnLoadArea;
    }

    public void setUnLoadArea(String unLoadArea) {
        UnLoadArea = unLoadArea;
    }

    public int getStar() {
        return Star;
    }

    public void setStar(int star) {
        Star = star;
    }

    public String getAutomobileTypID() {
        return AutomobileTypID;
    }

    public void setAutomobileTypID(String automobileTypID) {
        AutomobileTypID = automobileTypID;
    }

    public double getAutomobileLength() {
        return AutomobileLength;
    }

    public void setAutomobileLength(double automobileLength) {
        AutomobileLength = automobileLength;
    }

    public String getAutomobileTypName() {
        return AutomobileTypName;
    }

    public void setAutomobileTypName(String automobileTypName) {
        AutomobileTypName = automobileTypName;
    }

    public String getGoodsName() {
        return GoodsName;
    }

    public void setGoodsName(String goodsName) {
        GoodsName = goodsName;
    }

    public int getWeight() {
        return Weight;
    }

    public void setWeight(int weight) {
        Weight = weight;
    }

    public int getVolume() {
        return Volume;
    }

    public void setVolume(int volume) {
        Volume = volume;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getHeaderUrl() {
        return HeaderUrl;
    }

    public void setHeaderUrl(String headerUrl) {
        HeaderUrl = headerUrl;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }
}
