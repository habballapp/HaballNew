package com.haball.Distributor.ui.Network.Models;

public class Netwok_Model {
    private String CompanyName;
    private String CreatedDate;
    private String KYCNumber;
    private String KycStatusValue;
    private String DistributorMobile;
    public Netwok_Model() {
    }

    public Netwok_Model(String companyName, String createdDate, String KYCNumber, String kycStatusValue, String distributorMobile) {
        CompanyName = companyName;
        CreatedDate = createdDate;
        this.KYCNumber = KYCNumber;
        KycStatusValue = kycStatusValue;
        DistributorMobile = distributorMobile;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }

    public String getKYCNumber() {
        return KYCNumber;
    }

    public void setKYCNumber(String KYCNumber) {
        this.KYCNumber = KYCNumber;
    }

    public String getKycStatusValue() {
        return KycStatusValue;
    }

    public void setKycStatusValue(String kycStatusValue) {
        KycStatusValue = kycStatusValue;
    }

    public String getDistributorMobile() {
        return DistributorMobile;
    }

    public void setDistributorMobile(String distributorMobile) {
        DistributorMobile = distributorMobile;
    }
}
