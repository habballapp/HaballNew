package com.example.haball.Payment;

public class DistributorPaymentRequestModel {
    String CompanyName;
    String PrePaidNumber;
    String PaidAmount;
    String Status;
    String ID;
    String PrepaidStatusValue;

    public DistributorPaymentRequestModel() {
    }

    public DistributorPaymentRequestModel(String companyName, String prePaidNumber, String paidAmount, String status, String ID, String PrepaidStatusValue) {
        CompanyName = companyName;
        PrePaidNumber = prePaidNumber;
        PaidAmount = paidAmount;
        Status = status;
        this.ID = ID;
        this.PrepaidStatusValue = PrepaidStatusValue;

    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public String getPrePaidNumber() {
        return PrePaidNumber;
    }

    public void setPrePaidNumber(String prePaidNumber) {
        PrePaidNumber = prePaidNumber;
    }

    public String getPaidAmount() {
        return PaidAmount;
    }

    public void setPaidAmount(String paidAmount) {
        PaidAmount = paidAmount;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getPrepaidStatusValue() {
        return PrepaidStatusValue;
    }

    public void setPrepaidStatusValue(String prepaidStatusValue) {
        PrepaidStatusValue = prepaidStatusValue;
    }

}
