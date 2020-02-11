package com.example.haball.Distributor;

public class DistributorInvoicesModel {
    private String InvoiceNumber,CompanyName,TotalPrice,InvoiceStatusValue,State;

    public DistributorInvoicesModel(String invoiceNumber, String companyName, String totalPrice, String invoiceStatusValue, String state) {
        InvoiceNumber = invoiceNumber;
        CompanyName = companyName;
        TotalPrice = totalPrice;
        InvoiceStatusValue = invoiceStatusValue;
        State = state;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getInvoiceStatusValue() {
        return InvoiceStatusValue;
    }

    public void setInvoiceStatusValue(String invoiceStatusValue) {
        InvoiceStatusValue = invoiceStatusValue;
    }

    public String getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        TotalPrice = totalPrice;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public String getInvoiceNumber() {
        return InvoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        InvoiceNumber = invoiceNumber;
    }
}

