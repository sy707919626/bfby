package com.lulian.driver.entity.server.resulte;

/**
 {
 "UserId": "94ba1b22-050d-4ddd-8d2c-505de6902a67",
 "Name": "张姆斯",
 "Level": null,
 "UserState": null, 是否认证
 "HeadIco": null,  头像
 "WorkStatus": 1, 接单状态
 "Star": null,  星级
 "VehicleRegState": null,  车辆认证
 "CreateTime": "2018-08-02 15:59:19",  注册时间
 "UpdateTime": null,
 "logintime": null,  登录时间
 "VipTime": "2068-08-02 15:59:19",VIP
 "Balance": 0,  余额
 "ShouldPay": 0,
 "ShouldCash": 0, 应收
 "DL_Transport": 0,
 "DL_Rest": 0,
 "DL_Wait": 0,
 "OrderDQH": 0,  待取获运单
 "OrderYSZ": 0,  运输中运单
 "OrderDPJ": 0 待评价运单
 }
 */

public class MeBean {
    private String UserId;
    private String Name;
    private int Level;
    private int UserState; //是否实名认证
    private String HeadIco; //头像
    private int WorkStatus; //接单状态
    private int Star; //星级
    private int VehicleRegState; //车辆认证
    private String CreateTime; //注册时间
    private String UpdateTime; //修改时间
    private String logintime; //登录时间
    private String VipTime;
    private int Balance; //余额
    private int ShouldPay;
    private int ShouldCash; //应收
    private int DL_Transport;
    private int DL_Rest;
    private int DL_Wait;
    private int OrderDQH;//待取获运单
    private int OrderYSZ;//运输中运单
    private int OrderDPJ;//待评价运单

    public String getHeadIco() {
        return HeadIco;
    }

    public void setHeadIco(String headIco) {
        HeadIco = headIco;
    }

    public int getWorkStatus() {
        return WorkStatus;
    }

    public void setWorkStatus(int workStatus) {
        WorkStatus = workStatus;
    }

    public int getStar() {
        return Star;
    }

    public void setStar(int star) {
        Star = star;
    }

    public int getVehicleRegState() {
        return VehicleRegState;
    }

    public void setVehicleRegState(int vehicleRegState) {
        VehicleRegState = vehicleRegState;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public String getUpdateTime() {
        return UpdateTime;
    }

    public void setUpdateTime(String updateTime) {
        UpdateTime = updateTime;
    }

    public String getLogintime() {
        return logintime;
    }

    public void setLogintime(String logintime) {
        this.logintime = logintime;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getLevel() {
        return Level;
    }

    public void setLevel(int level) {
        Level = level;
    }

    public int getUserState() {
        return UserState;
    }

    public void setUserState(int userState) {
        UserState = userState;
    }

    public String getVipTime() {
        return VipTime;
    }

    public void setVipTime(String vipTime) {
        VipTime = vipTime;
    }

    public int getBalance() {
        return Balance;
    }

    public void setBalance(int balance) {
        Balance = balance;
    }

    public int getShouldPay() {
        return ShouldPay;
    }

    public void setShouldPay(int shouldPay) {
        ShouldPay = shouldPay;
    }

    public int getShouldCash() {
        return ShouldCash;
    }

    public void setShouldCash(int shouldCash) {
        ShouldCash = shouldCash;
    }

    public int getDL_Transport() {
        return DL_Transport;
    }

    public void setDL_Transport(int DL_Transport) {
        this.DL_Transport = DL_Transport;
    }

    public int getDL_Rest() {
        return DL_Rest;
    }

    public void setDL_Rest(int DL_Rest) {
        this.DL_Rest = DL_Rest;
    }

    public int getDL_Wait() {
        return DL_Wait;
    }

    public void setDL_Wait(int DL_Wait) {
        this.DL_Wait = DL_Wait;
    }

    public int getOrderDQH() {
        return OrderDQH;
    }

    public void setOrderDQH(int orderDQH) {
        OrderDQH = orderDQH;
    }

    public int getOrderYSZ() {
        return OrderYSZ;
    }

    public void setOrderYSZ(int orderYSZ) {
        OrderYSZ = orderYSZ;
    }

    public int getOrderDPJ() {
        return OrderDPJ;
    }

    public void setOrderDPJ(int orderDPJ) {
        OrderDPJ = orderDPJ;
    }
}
