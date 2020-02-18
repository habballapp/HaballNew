package com.example.haball.Distributor.ui.orders.Models;

public class Company_Fragment_Model {
    String ID, OrderNumber, TotalPrice, OrderStatusValue, CompanyName;
    public Company_Fragment_Model(String ID, String companyName) {
        this.ID = ID;
        CompanyName = companyName;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }
}
