package com.lulian.driver.entity.server.resulte;

/**
 * Created by Administrator on 2018/8/27.
 */

public class MyWalletDetail {

    /**
     * PayerUserId : 2a71a907-0e93-4ebd-a639-a5f4e126e76f
     * Money : 100
     * PayerAccountId : 746e8274-9c12-42bc-aa2d-4bccc041db85
     * CreateTime : 2018-08-27 17:40:17
     * RunningNo : TR0000000001
     * Remark :
     * ReceiverUserId : 2a71a907-0e93-4ebd-a639-a5f4e126e76f
     * ReceiverAccountId : 746e8274-9c12-42bc-aa2d-4bccc041db85
     * PayerAccountNo : string
     * ReceiverAccountNo : string
     * Id : ecbbee5a-f9fb-4a29-b5c3-faa552ab4cf0
     */

    private String PayerUserId;
    private int Money;
    private String PayerAccountId;
    private String CreateTime;
    private String RunningNo;
    private String Remark;
    private String ReceiverUserId;
    private String ReceiverAccountId;
    private String PayerAccountNo;
    private String ReceiverAccountNo;
    private String Id;

    public String getPayerUserId() {
        return PayerUserId;
    }

    public void setPayerUserId(String PayerUserId) {
        this.PayerUserId = PayerUserId;
    }

    public int getMoney() {
        return Money;
    }

    public void setMoney(int Money) {
        this.Money = Money;
    }

    public String getPayerAccountId() {
        return PayerAccountId;
    }

    public void setPayerAccountId(String PayerAccountId) {
        this.PayerAccountId = PayerAccountId;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String CreateTime) {
        this.CreateTime = CreateTime;
    }

    public String getRunningNo() {
        return RunningNo;
    }

    public void setRunningNo(String RunningNo) {
        this.RunningNo = RunningNo;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String Remark) {
        this.Remark = Remark;
    }

    public String getReceiverUserId() {
        return ReceiverUserId;
    }

    public void setReceiverUserId(String ReceiverUserId) {
        this.ReceiverUserId = ReceiverUserId;
    }

    public String getReceiverAccountId() {
        return ReceiverAccountId;
    }

    public void setReceiverAccountId(String ReceiverAccountId) {
        this.ReceiverAccountId = ReceiverAccountId;
    }

    public String getPayerAccountNo() {
        return PayerAccountNo;
    }

    public void setPayerAccountNo(String PayerAccountNo) {
        this.PayerAccountNo = PayerAccountNo;
    }

    public String getReceiverAccountNo() {
        return ReceiverAccountNo;
    }

    public void setReceiverAccountNo(String ReceiverAccountNo) {
        this.ReceiverAccountNo = ReceiverAccountNo;
    }

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }
}
