package com.example.haball.Distributor.ui.shipments;

public class ShipmentModel {
    String DeliveryNumber, CompanyName, DeliveryDate, ReceivingDate, Quantity, ShipmentStatusValue, ID;

    public ShipmentModel() {
    }

    public ShipmentModel(String deliveryNumber, String companyName, String deliveryDate, String receivingDate, String quantity, String shipmentStatusValue, String ID) {
        DeliveryNumber = deliveryNumber;
        CompanyName = companyName;
        DeliveryDate = deliveryDate;
        ReceivingDate = receivingDate;
        Quantity = quantity;
        ShipmentStatusValue = shipmentStatusValue;
        this.ID = ID;
    }

    public String getDeliveryNumber() {
        return DeliveryNumber;
    }

    public void setDeliveryNumber(String deliveryNumber) {
        DeliveryNumber = deliveryNumber;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
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

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getShipmentStatusValue() {
        return ShipmentStatusValue;
    }

    public void setShipmentStatusValue(String shipmentStatusValue) {
        ShipmentStatusValue = shipmentStatusValue;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}
