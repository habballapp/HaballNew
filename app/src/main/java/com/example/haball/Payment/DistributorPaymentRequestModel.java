package com.example.haball.Payment;

public class DistributorPaymentRequestModel {
    String CompanyName, PrePaidNumber, PaidAmount, Status, ID;

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

    public DistributorPaymentRequestModel(String companyName, String prePaidNumber, String paidAmount, String status, String ID) {
        CompanyName = companyName;
        PrePaidNumber = prePaidNumber;
        PaidAmount = paidAmount;
        Status = status;
        this.ID = ID;
    }
}
