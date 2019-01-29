package com.lulian.driver.entity.server.resulte;

/**
 * Created by MARK on 2018/6/21.
 */

public class DriverVeDetail {
    private String HeaderUrl;
    private String UserId;
    private String Name;
    private int OrderCount;
    private int DoWorkStatus;
    private String Phone;
    private int Star;
    private int UserState;
    private int UserLevel;
    private String PlateNo;
    private int VehicleHeight;
    private int VehicleWeight;
    private int VehicleLength;
    private String VehicleAge;
    private String VehicleTypeName;
    private int VehicleState;
    private String Remark;
    private String OurOrderCount;
    private int MyCrew;
    private String Comments;
    private String Tags;

    public String getHeaderUrl() {
        return HeaderUrl;
    }

    public void setHeaderUrl(String headerUrl) {
        HeaderUrl = headerUrl;
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

    public int getOrderCount() {
        return OrderCount;
    }

    public void setOrderCount(int orderCount) {
        OrderCount = orderCount;
    }

    public int getDoWorkStatus() {
        return DoWorkStatus;
    }

    public void setDoWorkStatus(int doWorkStatus) {
        DoWorkStatus = doWorkStatus;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public int getStar() {
        return Star;
    }

    public void setStar(int star) {
        Star = star;
    }

    public int getUserState() {
        return UserState;
    }

    public void setUserState(int userState) {
        UserState = userState;
    }

    public int getUserLevel() {
        return UserLevel;
    }

    public void setUserLevel(int userLevel) {
        UserLevel = userLevel;
    }

    public String getPlateNo() {
        return PlateNo;
    }

    public void setPlateNo(String plateNo) {
        PlateNo = plateNo;
    }

    public int getVehicleHeight() {
        return VehicleHeight;
    }

    public void setVehicleHeight(int vehicleHeight) {
        VehicleHeight = vehicleHeight;
    }

    public int getVehicleWeight() {
        return VehicleWeight;
    }

    public void setVehicleWeight(int vehicleWeight) {
        VehicleWeight = vehicleWeight;
    }

    public int getVehicleLength() {
        return VehicleLength;
    }

    public void setVehicleLength(int vehicleLength) {
        VehicleLength = vehicleLength;
    }

    public String getVehicleAge() {
        return VehicleAge;
    }

    public void setVehicleAge(String vehicleAge) {
        VehicleAge = vehicleAge;
    }

    public String getVehicleTypeName() {
        return VehicleTypeName;
    }

    public void setVehicleTypeName(String vehicleTypeName) {
        VehicleTypeName = vehicleTypeName;
    }

    public int getVehicleState() {
        return VehicleState;
    }

    public void setVehicleState(int vehicleState) {
        VehicleState = vehicleState;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public String getOurOrderCount() {
        return OurOrderCount;
    }

    public void setOurOrderCount(String ourOrderCount) {
        OurOrderCount = ourOrderCount;
    }

    public int getMyCrew() {
        return MyCrew;
    }

    public void setMyCrew(int myCrew) {
        MyCrew = myCrew;
    }

    public String getComments() {
        return Comments;
    }

    public void setComments(String comments) {
        Comments = comments;
    }

    public String getTags() {
        return Tags;
    }

    public void setTags(String tags) {
        Tags = tags;
    }

/*"state": 1,
            "msg": "操作成功！",
            "data": {
        "DriverUserId": "司机04",
                "HeaderUrl": "string",
                "DriverName": "司机04",
                "Star": null,
                "DriverRegState": 0,
                "DriverOrderCount": 0,
                "DriverPhone": "string",
                "VehicleId": "V4",
                "PlateNo": "湘A00004",
                "VLength": 30,
                "VWeight": 60,
                "VehicleRegState": 0,
                "VModel": null,
                "Remark": null,
                "OrderCount": null,
                "Comments": {
            "Score": 100,
                    "Count": 0,
                    "CountA": 0,
                    "CountB": 0,
                    "CountC": 0
        },
        "Tags": []
    }*/
}
