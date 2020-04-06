package com.example.haball.Distributor.ui.retailer.RetailerOrder.RetailerOrdersModel;

public class RetailerOrdersModel {
    private String ID;
    String OrderNumber;
    String Retailer;
    String OrderId;
    String TotalAmount;
    String CreatedDate;
    String Submitter;
    String OrderStatus;
    String InvoiceStatus;

    public RetailerOrdersModel(String orderNumber, String retailer, String orderId, String totalAmount, String createdDate, String submitter, String orderStatus, String invoiceStatus) {
        OrderNumber = orderNumber;
        Retailer = retailer;
        OrderId = orderId;
        TotalAmount = totalAmount;
        CreatedDate = createdDate;
        Submitter = submitter;
        OrderStatus = orderStatus;
        InvoiceStatus = invoiceStatus;
    }

    public String getOrderNumber() {
        return OrderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        OrderNumber = orderNumber;
    }

    public String getRetailer() {
        return Retailer;
    }

    public void setRetailer(String retailer) {
        Retailer = retailer;
    }

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }

    public String getTotalAmount() {
        return TotalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        TotalAmount = totalAmount;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }

    public String getSubmitter() {
        return Submitter;
    }

    public void setSubmitter(String submitter) {
        Submitter = submitter;
    }

    public String getOrderStatus() {
        return OrderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        OrderStatus = orderStatus;
    }

    public String getInvoiceStatus() {
        return InvoiceStatus;
    }

    public void setInvoiceStatus(String invoiceStatus) {
        InvoiceStatus = invoiceStatus;
    }
    public void setID(String ID) {
        this.ID = ID;
    }


    public String getID() {
        return ID;
    }
}
