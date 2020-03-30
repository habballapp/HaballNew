package com.example.haball.Retailor.ui.Dashboard;

public class RetailerPaymentModel {
    String RetailerInvoiceId;
    String InvoiceNumber;
    String CompanyName;
    String Status;
    String TotalPrice;
    String IsEditable;
    String PrepaidStatusValue;

    public RetailerPaymentModel(String ID, String prePaidNumber, String companyName, String status, String paidAmount, String isEditable) {
        this.RetailerInvoiceId = ID;
        InvoiceNumber = prePaidNumber;
        CompanyName = companyName;
        Status = status;
        TotalPrice = paidAmount;
        IsEditable = isEditable;
    }

    public String getIsEditable() {
        return IsEditable;
    }

    public void setIsEditable(String isEditable) {
        IsEditable = isEditable;
    }

    public String getID() {
        return RetailerInvoiceId;
    }

    public void setID(String ID) {
        this.RetailerInvoiceId = ID;
    }

    public String getPrePaidNumber() {
        return InvoiceNumber;
    }

    public void setPrePaidNumber(String prePaidNumber) {
        InvoiceNumber = prePaidNumber;
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
        return TotalPrice;
    }

    public void setPaidAmount(String paidAmount) {
        TotalPrice = paidAmount;
    }

    public String getPrepaidStatusValue() {

       return PrepaidStatusValue;
    }

    public void setPrepaidStatusValue(String prepaidStatusValue) {
        PrepaidStatusValue = prepaidStatusValue;
    }
}
