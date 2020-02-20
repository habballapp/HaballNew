package com.example.haball.Retailor.ui.Dashboard;

public class RetailerPaymentModel {
    String ID, PrePaidNumber, CompanyName, Status, PaidAmount;

    public RetailerPaymentModel(String ID, String prePaidNumber, String companyName, String status, String paidAmount) {
        this.ID = ID;
        PrePaidNumber = prePaidNumber;
        CompanyName = companyName;
        Status = status;
        PaidAmount = paidAmount;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getPrePaidNumber() {
        return PrePaidNumber;
    }

    public void setPrePaidNumber(String prePaidNumber) {
        PrePaidNumber = prePaidNumber;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getPaidAmount() {
        return PaidAmount;
    }

    public void setPaidAmount(String paidAmount) {
        PaidAmount = paidAmount;
    }
}
