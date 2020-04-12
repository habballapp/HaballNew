package com.example.haball.Payment;

public class DistributorPaymentRequestModel {
    String ID;
    String PrePaidNumber;
    String PaidAmount;
    String PaidDate;
    String DistributorId;
    String CompanyId;
    String ReferenceID;
    String Status;
    String IsTransmitted;
    String CreatedBy;
    String CreatedDate;
    String LastChangedBy;
    String LastChangedDate;
    String State;
    String CompanyName;
    String employeesName;
    String DistributorName;
    String PrepaidStatusValue;

    public DistributorPaymentRequestModel(String ID, String prePaidNumber, String paidAmount, String paidDate, String distributorId, String companyId, String referenceID, String status, String isTransmitted, String createdBy, String createdDate, String lastChangedBy, String lastChangedDate, String state, String companyName, String employeesName, String distributorName, String prepaidStatusValue) {
        this.ID = ID;
        PrePaidNumber = prePaidNumber;
        PaidAmount = paidAmount;
        PaidDate = paidDate;
        DistributorId = distributorId;
        CompanyId = companyId;
        ReferenceID = referenceID;
        Status = status;
        IsTransmitted = isTransmitted;
        CreatedBy = createdBy;
        CreatedDate = createdDate;
        LastChangedBy = lastChangedBy;
        LastChangedDate = lastChangedDate;
        State = state;
        CompanyName = companyName;
        this.employeesName = employeesName;
        DistributorName = distributorName;
        PrepaidStatusValue = prepaidStatusValue;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getPrePaidNumber() {
        return PrePaidNumber;
    }

    public void setPrePaidNumber(String prePaidNumber) {
        PrePaidNumber = prePaidNumber;
    }

    public String getPaidAmount() {
        return PaidAmount;
    }

    public void setPaidAmount(String paidAmount) {
        PaidAmount = paidAmount;
    }

    public String getPaidDate() {
        return PaidDate;
    }

    public void setPaidDate(String paidDate) {
        PaidDate = paidDate;
    }

    public String getDistributorId() {
        return DistributorId;
    }

    public void setDistributorId(String distributorId) {
        DistributorId = distributorId;
    }

    public String getCompanyId() {
        return CompanyId;
    }

    public void setCompanyId(String companyId) {
        CompanyId = companyId;
    }

    public String getReferenceID() {
        return ReferenceID;
    }

    public void setReferenceID(String referenceID) {
        ReferenceID = referenceID;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getIsTransmitted() {
        return IsTransmitted;
    }

    public void setIsTransmitted(String isTransmitted) {
        IsTransmitted = isTransmitted;
    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String createdBy) {
        CreatedBy = createdBy;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }

    public String getLastChangedBy() {
        return LastChangedBy;
    }

    public void setLastChangedBy(String lastChangedBy) {
        LastChangedBy = lastChangedBy;
    }

    public String getLastChangedDate() {
        return LastChangedDate;
    }

    public void setLastChangedDate(String lastChangedDate) {
        LastChangedDate = lastChangedDate;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public String getEmployeesName() {
        return employeesName;
    }

    public void setEmployeesName(String employeesName) {
        this.employeesName = employeesName;
    }

    public String getDistributorName() {
        return DistributorName;
    }

    public void setDistributorName(String distributorName) {
        DistributorName = distributorName;
    }

    public String getPrepaidStatusValue() {
        return PrepaidStatusValue;
    }

    public void setPrepaidStatusValue(String prepaidStatusValue) {
        PrepaidStatusValue = prepaidStatusValue;
    }
}
