package com.example.haball.Distributor;

public class DistributorInvoicesModel {
    private String InvoiceNumber,CompanyName,TotalPrice,InvoiceStatusValue,State ,ID;

    public DistributorInvoicesModel(String invoiceNumber, String companyName, String totalPrice, String invoiceStatusValue, String state, String ID) {
        InvoiceNumber = invoiceNumber;
        CompanyName = companyName;
        TotalPrice = totalPrice;
        InvoiceStatusValue = invoiceStatusValue;
        State = state;
        this.ID = ID;
    }

    public String getInvoiceNumber() {
        return InvoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        InvoiceNumber = invoiceNumber;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public String getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        TotalPrice = totalPrice;
    }

    public String getInvoiceStatusValue() {
        return InvoiceStatusValue;
    }

    public void setInvoiceStatusValue(String invoiceStatusValue) {
        InvoiceStatusValue = invoiceStatusValue;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}

