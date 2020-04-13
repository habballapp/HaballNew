package com.example.haball.Distributor.ui.retailer.Payment.Models;

public class Dist_Retailer_Dashboard_Model {
   private String InvoiceNumber;
    private String CompanyName;
    private String TotalPrice;
    private String RetailerInvoiceId;
    private String InvoiceStatusValue;
    private String IsEditable;
    String PrepaidStatusValue;

    public Dist_Retailer_Dashboard_Model() {
    }

    public Dist_Retailer_Dashboard_Model(String invoiceNumber, String companyName, String totalPrice, String retailerInvoiceId, String invoiceStatusValue, String isEditable, String prepaidStatusValue) {
        InvoiceNumber = invoiceNumber;
        CompanyName = companyName;
        TotalPrice = totalPrice;
        RetailerInvoiceId = retailerInvoiceId;
        InvoiceStatusValue = invoiceStatusValue;
        IsEditable = isEditable;
        PrepaidStatusValue = prepaidStatusValue;
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

    public String getRetailerInvoiceId() {
        return RetailerInvoiceId;
    }

    public void setRetailerInvoiceId(String retailerInvoiceId) {
        RetailerInvoiceId = retailerInvoiceId;
    }

    public String getInvoiceStatusValue() {
        return InvoiceStatusValue;
    }

    public void setInvoiceStatusValue(String invoiceStatusValue) {
        InvoiceStatusValue = invoiceStatusValue;
    }

    public String getIsEditable() {
        return IsEditable;
    }

    public void setIsEditable(String isEditable) {
        IsEditable = isEditable;
    }

    public String getPrepaidStatusValue() {
        return PrepaidStatusValue;
    }

    public void setPrepaidStatusValue(String prepaidStatusValue) {
        PrepaidStatusValue = prepaidStatusValue;
    }
}
