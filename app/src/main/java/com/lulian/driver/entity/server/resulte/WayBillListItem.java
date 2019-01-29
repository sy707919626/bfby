package com.lulian.driver.entity.server.resulte;

import java.io.Serializable;

/**
 * 运单列表条目数据对象
 * @author hxb
 */

public class WayBillListItem implements Serializable{
    private String Id;
    private String Number;
    private String OnLoadAreaID;
    private String OnLoadAdress;
    private String UnLoadAreaID;
    private String OnLoadArea;
    private String UnLoadAddress;
    private String UnLoadArea;
    private int Status;
    private String GoodsName;
    private int Weight;
    private int Volume;
    private String AutomobileTypName;
    private String Remark;

    private String Usertype;
    private String CreateTime;
    private String UserName;
    private String Phone;
    private int Star;
    private double AutomobileLength;


    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String Number) {
        this.Number = Number;
    }

    public String getOnLoadAreaID() {
        return OnLoadAreaID;
    }

    public void setOnLoadAreaID(String OnLoadAreaID) {
        this.OnLoadAreaID = OnLoadAreaID;
    }

    public String getOnLoadAdress() {
        return OnLoadAdress;
    }

    public void setOnLoadAdress(String OnLoadAdress) {
        this.OnLoadAdress = OnLoadAdress;
    }

    public String getUnLoadAreaID() {
        return UnLoadAreaID;
    }

    public void setUnLoadAreaID(String UnLoadAreaID) {
        this.UnLoadAreaID = UnLoadAreaID;
    }

    public String getOnLoadArea() {
        return OnLoadArea;
    }

    public void setOnLoadArea(String OnLoadArea) {
        this.OnLoadArea = OnLoadArea;
    }

    public String getUnLoadAddress() {
        return UnLoadAddress;
    }

    public void setUnLoadAddress(String UnLoadAddress) {
        this.UnLoadAddress = UnLoadAddress;
    }

    public String getUnLoadArea() {
        return UnLoadArea;
    }

    public void setUnLoadArea(String UnLoadArea) {
        this.UnLoadArea = UnLoadArea;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int Status) {
        this.Status = Status;
    }

    public String getGoodsName() {
        return GoodsName;
    }

    public void setGoodsName(String GoodsName) {
        this.GoodsName = GoodsName;
    }

    public int getWeight() {
        return Weight;
    }

    public void setWeight(int Weight) {
        this.Weight = Weight;
    }

    public int getVolume() {
        return Volume;
    }

    public void setVolume(int Volume) {
        this.Volume = Volume;
    }

    public String getAutomobileTypName() {
        return AutomobileTypName;
    }

    public void setAutomobileTypName(String AutomobileTypName) {
        this.AutomobileTypName = AutomobileTypName;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String Remark) {
        this.Remark = Remark;
    }

    public String getUsertype() {
        return Usertype;
    }

    public void setUsertype(String Usertype) {
        this.Usertype = Usertype;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String CreateTime) {
        this.CreateTime = CreateTime;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String Phone) {
        this.Phone = Phone;
    }

    public int getStar() {
        return Star;
    }

    public void setStar(int Star) {
        this.Star = Star;
    }

    public double getAutomobileLength() {
        return AutomobileLength;
    }

    public void setAutomobileLength(double AutomobileLength) {
        this.AutomobileLength = AutomobileLength;
    }
}
