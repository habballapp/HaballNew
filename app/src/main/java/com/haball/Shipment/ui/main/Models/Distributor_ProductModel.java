package com.haball.Shipment.ui.main.Models;

import androidx.lifecycle.ViewModel;

public class Distributor_ProductModel {
    private String ProductCode;
    private String ProductName;
    private String DeliveredQty;
    private String UnitPrice;
    private String Discount;
    private String TotalPrice;

    public Distributor_ProductModel() {
    }

    public Distributor_ProductModel(String productCode, String productName, String deliveredQty, String unitPrice, String discount, String totalPrice) {
        ProductCode = productCode;
        ProductName = productName;
        DeliveredQty = deliveredQty;
        UnitPrice = unitPrice;
        Discount = discount;
        TotalPrice = totalPrice;
    }

    public String getProductCode() {
        return ProductCode;
    }

    public void setProductCode(String productCode) {
        ProductCode = productCode;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getDeliveredQty() {
        return DeliveredQty;
    }

    public void setDeliveredQty(String deliveredQty) {
        DeliveredQty = deliveredQty;
    }

    public String getUnitPrice() {
        return UnitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        UnitPrice = unitPrice;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }

    public String getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        TotalPrice = totalPrice;
    }
}
