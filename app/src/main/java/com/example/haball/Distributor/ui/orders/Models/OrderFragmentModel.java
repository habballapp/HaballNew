package com.example.haball.Distributor.ui.orders.Models;

public class OrderFragmentModel {
    String ID, OrderNumber, TotalPrice, OrderStatusValue, CompanyName;
    public OrderFragmentModel(String ID, String orderNumber, String totalPrice, String orderStatusValue, String companyName) {
        this.ID = ID;
        OrderNumber = orderNumber;
        TotalPrice = totalPrice;
        OrderStatusValue = orderStatusValue;
        CompanyName = companyName;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getOrderNumber() {
        return OrderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        OrderNumber = orderNumber;
    }

    public String getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        TotalPrice = totalPrice;
    }

    public String getOrderStatusValue() {
        return OrderStatusValue;
    }

    public void setOrderStatusValue(String orderStatusValue) {
        OrderStatusValue = orderStatusValue;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }
}
