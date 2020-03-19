package com.example.haball.Distributor.ui.retailer.RetailerPlaceOrder.ui.main.Models;

public class OrderChildlist_Model {

    public String option1;
    public String Option2;

    public OrderChildlist_Model() {
    }

    public OrderChildlist_Model(String option1, String option2) {
        this.option1 = option1;
        Option2 = option2;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return Option2;
    }

    public void setOption2(String option2) {
        Option2 = option2;
    }
}
