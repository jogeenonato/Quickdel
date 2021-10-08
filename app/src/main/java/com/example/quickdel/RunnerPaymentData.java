package com.example.quickdel;

public class RunnerPaymentData {

    String accountName;
    String accountBSB;
    String accountNo;


    public RunnerPaymentData(String accountName, String accountBSB, String accountNo) {
        this.accountName = accountName;
        this.accountBSB = accountBSB;
        this.accountNo = accountNo;
    }

    public String getAccountName() {
        return accountName;
    }

    public String getAccountBSB() {
        return accountBSB;
    }

    public String getAccountNo() {
        return accountNo;
    }
}
