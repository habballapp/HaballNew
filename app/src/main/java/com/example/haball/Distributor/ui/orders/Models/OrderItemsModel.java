package com.example.haball.Distributor.ui.orders.Models;

public class OrderItemsModel {
    private String ID, CompanyId, Code, Title, UnitPrice, DiscountId, EffectiveDate, ExpiryDate, IsPercentage, DiscountValue, DiscountAmount, CategoryTitle;

    public OrderItemsModel(String ID, String companyId, String code, String title, String unitPrice, String discountId, String effectiveDate, String expiryDate, String isPercentage, String discountValue, String discountAmount, String categoryTitle) {
        this.ID = ID;
        CompanyId = companyId;
        Code = code;
        Title = title;
        UnitPrice = unitPrice;
        DiscountId = discountId;
        EffectiveDate = effectiveDate;
        ExpiryDate = expiryDate;
        IsPercentage = isPercentage;
        DiscountValue = discountValue;
        DiscountAmount = discountAmount;
        CategoryTitle = categoryTitle;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getCompanyId() {
        return CompanyId;
    }

    public void setCompanyId(String companyId) {
        CompanyId = companyId;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getUnitPrice() {
        return UnitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        UnitPrice = unitPrice;
    }

    public String getDiscountId() {
        return DiscountId;
    }

    public void setDiscountId(String discountId) {
        DiscountId = discountId;
    }

    public String getEffectiveDate() {
        return EffectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        EffectiveDate = effectiveDate;
    }

    public String getExpiryDate() {
        return ExpiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        ExpiryDate = expiryDate;
    }

    public String getIsPercentage() {
        return IsPercentage;
    }

    public void setIsPercentage(String isPercentage) {
        IsPercentage = isPercentage;
    }

    public String getDiscountValue() {
        return DiscountValue;
    }

    public void setDiscountValue(String discountValue) {
        DiscountValue = discountValue;
    }

    public String getDiscountAmount() {
        return DiscountAmount;
    }

    public void setDiscountAmount(String discountAmount) {
        DiscountAmount = discountAmount;
    }

    public String getCategoryTitle() {
        return CategoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        CategoryTitle = categoryTitle;
    }
}
