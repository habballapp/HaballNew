package com.example.haball.Distributor.ui.retailer.RetailerOrder.RetailerOrdersModel;

public class RetailerViewOrderProductModel {
    String ProductId;
    String ProductCode;
    String ProductTitle;
    String OrderedQty;
    String ProductUnitPrice;
    String DiscountedAmount;
    String Totalamount;
    String UOM;
    String PackSize;

    public RetailerViewOrderProductModel(String productId, String productCode, String productTitle, String orderedQty, String productUnitPrice, String discountedAmount, String totalamount, String UOM, String packSize) {
        ProductId = productId;
        ProductCode = productCode;
        ProductTitle = productTitle;
        OrderedQty = orderedQty;
        ProductUnitPrice = productUnitPrice;
        DiscountedAmount = discountedAmount;
        Totalamount = totalamount;
        this.UOM = UOM;
        PackSize = packSize;
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

    public String getProductTitle() {
        return ProductTitle;
    }

    public void setProductTitle(String productTitle) {
        ProductTitle = productTitle;
    }

    public String getOrderedQty() {
        return OrderedQty;
    }

    public void setOrderedQty(String orderedQty) {
        OrderedQty = orderedQty;
    }

    public String getProductUnitPrice() {
        return ProductUnitPrice;
    }

    public void setProductUnitPrice(String productUnitPrice) {
        ProductUnitPrice = productUnitPrice;
    }

    public String getDiscountedAmount() {
        return DiscountedAmount;
    }

    public void setDiscountedAmount(String discountedAmount) {
        DiscountedAmount = discountedAmount;
    }

    public String getTotalamount() {
        return Totalamount;
    }

    public void setTotalamount(String totalamount) {
        Totalamount = totalamount;
    }

    public String getUOM() {
        return UOM;
    }

    public void setUOM(String UOM) {
        this.UOM = UOM;
    }

    public String getPackSize() {
        return PackSize;
    }

    public void setPackSize(String packSize) {
        PackSize = packSize;
    }
}
