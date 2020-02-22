package com.example.haball.Invoice.Models;

public class ProductDetails_Model {
    private String DeliveryNumber;
    private String DeliveryDate;
    private String ReceivingDate;
    private String ShipmentStatus;

    public ProductDetails_Model() {
    }

    public ProductDetails_Model(String deliveryNumber, String deliveryDate, String receivingDate, String shipmentStatus) {
        DeliveryNumber = deliveryNumber;
        DeliveryDate = deliveryDate;
        ReceivingDate = receivingDate;
        ShipmentStatus = shipmentStatus;
    }

    public String getDeliveryNumber() {
        return DeliveryNumber;
    }

    public void setDeliveryNumber(String deliveryNumber) {
        DeliveryNumber = deliveryNumber;
    }

    public String getDeliveryDate() {
        return DeliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        DeliveryDate = deliveryDate;
    }

    public String getReceivingDate() {
        return ReceivingDate;
    }

    public void setReceivingDate(String receivingDate) {
        ReceivingDate = receivingDate;
    }

    public String getShipmentStatus() {
        return ShipmentStatus;
    }

    public void setShipmentStatus(String shipmentStatus) {
        ShipmentStatus = shipmentStatus;
    }
}
