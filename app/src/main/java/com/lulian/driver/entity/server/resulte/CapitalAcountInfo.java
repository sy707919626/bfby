package com.lulian.driver.entity.server.resulte;

/**
 * Created by MARK on 2018/7/27.
 */

public class CapitalAcountInfo {
    private String UserId;
    private String Balance;
    private String ShouldPay;
    private String ShouldCash;
    private String Blocked;
    private int AccountType;
    private String AccountNo;
    private int Status;
    private String CompanysId;
    private String Password;
    private String Id;

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getBalance() {
        return Balance;
    }

    public void setBalance(String balance) {
        Balance = balance;
    }

    public String getShouldPay() {
        return ShouldPay;
    }

    public void setShouldPay(String shouldPay) {
        ShouldPay = shouldPay;
    }

    public String getShouldCash() {
        return ShouldCash;
    }

    public void setShouldCash(String shouldCash) {
        ShouldCash = shouldCash;
    }

    public String getBlocked() {
        return Blocked;
    }

    public void setBlocked(String blocked) {
        Blocked = blocked;
    }

    public int getAccountType() {
        return AccountType;
    }

    public void setAccountType(int accountType) {
        AccountType = accountType;
    }

    public String getAccountNo() {
        return AccountNo;
    }

    public void setAccountNo(String accountNo) {
        AccountNo = accountNo;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public String getCompanysId() {
        return CompanysId;
    }

    public void setCompanysId(String companysId) {
        CompanysId = companysId;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }
}
