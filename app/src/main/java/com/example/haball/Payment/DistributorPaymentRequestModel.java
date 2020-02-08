package com.example.haball.Payment;

public class DistributorPaymentRequestModel {
    String CompanyName, PrePaidNumber, PaidAmount, Status;

    public DistributorPaymentRequestModel(String companyName, String prePaidNumber, String paidAmount, String status) {
        CompanyName = companyName;
        PrePaidNumber = prePaidNumber;
        PaidAmount = paidAmount;
        Status = status;
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
}
