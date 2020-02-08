package com.example.haball.Payment;

public class PaymentLedgerModel {
    private String CompanyName, DocumentNumber, DocumentType, CreditAmount, DebitAmount, BalanceAmount;

    public PaymentLedgerModel(String companyName, String documentNumber, String documentType, String creditAmount, String debitAmount, String balanceAmount) {
        CompanyName = companyName;
        DocumentNumber = documentNumber;
        DocumentType = documentType;
        CreditAmount = creditAmount;
        DebitAmount = debitAmount;
        BalanceAmount = balanceAmount;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public String getDocumentNumber() {
        return DocumentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        DocumentNumber = documentNumber;
    }

    public String getDocumentType() {
        return DocumentType;
    }

    public void setDocumentType(String documentType) {
        DocumentType = documentType;
    }

    public String getCreditAmount() {
        return CreditAmount;
    }

    public void setCreditAmount(String creditAmount) {
        CreditAmount = creditAmount;
    }

    public String getDebitAmount() {
        return DebitAmount;
    }

    public void setDebitAmount(String debitAmount) {
        DebitAmount = debitAmount;
    }

    public String getBalanceAmount() {
        return BalanceAmount;
    }

    public void setBalanceAmount(String balanceAmount) {
        BalanceAmount = balanceAmount;
    }
}
