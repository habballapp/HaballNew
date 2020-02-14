package com.example.haball.Shipment.ui.main.Models;

import androidx.lifecycle.ViewModel;

public class Distributor_InvoiceModel extends ViewModel {
   private String DeliveryNumber;
    private String CreatedDate;
    private String NetPrice;
    private String Status;
    public Distributor_InvoiceModel() {
    }

    public Distributor_InvoiceModel(String deliveryNumber, String createdDate, String netPrice, String status) {
        DeliveryNumber = deliveryNumber;
        CreatedDate = createdDate;
        NetPrice = netPrice;
        Status = status;
    }

    public String getDeliveryNumber() {
        return DeliveryNumber;
    }

    public void setDeliveryNumber(String deliveryNumber) {
        DeliveryNumber = deliveryNumber;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }

    public String getNetPrice() {
        return NetPrice;
    }

    public void setNetPrice(String netPrice) {
        NetPrice = netPrice;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
