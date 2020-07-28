package com.haball.Distributor.ui.retailer.RetailerOrder.RetailerOrdersModel;

public class RetailerViewOrderProductModel {
    String ProductId;
    String ProductCode;
    String ProductName;
    String OrderQty;
    String UnitPrice;
    String Discount;
    String TotalPrice;
    String UOM;
    String TaxValue;
    String UOMTitle;

    String DiscountedAmount;
    String OrderedQty;
    String PackSize;
    String ProductTitle;
    String ProductUnitPrice;
    String Totalamount;
    String UOMId;
    String UnitOFMeasure;

    public RetailerViewOrderProductModel(String productId, String productCode, String productName, String orderQty, String unitPrice, String discount, String totalPrice, String UOM, String taxValue, String UOMTitle, String discountedAmount, String orderedQty, String packSize, String productTitle, String productUnitPrice, String totalamount, String UOMId, String unitOFMeasure) {
        ProductId = productId;
        ProductCode = productCode;
        ProductName = productName;
        OrderQty = orderQty;
        UnitPrice = unitPrice;
        Discount = discount;
        TotalPrice = totalPrice;
        this.UOM = UOM;
        TaxValue = taxValue;
        this.UOMTitle = UOMTitle;
        DiscountedAmount = discountedAmount;
        OrderedQty = orderedQty;
        PackSize = packSize;
        ProductTitle = productTitle;
        ProductUnitPrice = productUnitPrice;
        Totalamount = totalamount;
        this.UOMId = UOMId;
        UnitOFMeasure = unitOFMeasure;
    }

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
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

    public String getOrderQty() {
        return OrderQty;
    }

    public void setOrderQty(String orderQty) {
        OrderQty = orderQty;
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

    public String getUOM() {
        return UOM;
    }

    public void setUOM(String UOM) {
        this.UOM = UOM;
    }

    public String getTaxValue() {
        return TaxValue;
    }

    public void setTaxValue(String taxValue) {
        TaxValue = taxValue;
    }

    public String getUOMTitle() {
        return UOMTitle;
    }

    public void setUOMTitle(String UOMTitle) {
        this.UOMTitle = UOMTitle;
    }

    public String getDiscountedAmount() {
        return DiscountedAmount;
    }

    public void setDiscountedAmount(String discountedAmount) {
        DiscountedAmount = discountedAmount;
    }

    public String getOrderedQty() {
        return OrderedQty;
    }

    public void setOrderedQty(String orderedQty) {
        OrderedQty = orderedQty;
    }

    public String getPackSize() {
        return PackSize;
    }

    public void setPackSize(String packSize) {
        PackSize = packSize;
    }

    public String getProductTitle() {
        return ProductTitle;
    }

    public void setProductTitle(String productTitle) {
        ProductTitle = productTitle;
    }

    public String getProductUnitPrice() {
        return ProductUnitPrice;
    }

    public void setProductUnitPrice(String productUnitPrice) {
        ProductUnitPrice = productUnitPrice;
    }

    public String getTotalamount() {
        return Totalamount;
    }

    public void setTotalamount(String totalamount) {
        Totalamount = totalamount;
    }

    public String getUOMId() {
        return UOMId;
    }

    public void setUOMId(String UOMId) {
        this.UOMId = UOMId;
    }

    public String getUnitOFMeasure() {
        return UnitOFMeasure;
    }

    public void setUnitOFMeasure(String unitOFMeasure) {
        UnitOFMeasure = unitOFMeasure;
    }
}
