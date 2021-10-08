package com.example.quickdel;

public class CustomerPaymentData {
    String cName, cNumber, cMonth, cYear, cCVC;

    public CustomerPaymentData(String cName, String cNumber, String cMonth, String cYear, String cCVC) {
        this.cName = cName;
        this.cNumber = cNumber;
        this.cMonth = cMonth;
        this.cYear = cYear;
        this.cCVC = cCVC;
    }

    public String getcName() {
        return cName;
    }

    public String getcNumber() {
        return cNumber;
    }

    public String getcMonth() {
        return cMonth;
    }

    public String getcYear() {
        return cYear;
    }

    public String getcCVC() {
        return cCVC;
    }






}
