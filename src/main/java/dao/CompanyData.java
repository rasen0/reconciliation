package dao;

import java.math.BigDecimal;

public class CompanyData {
    private short month;
    private short day;
    private String voucherNo;
    private String summary;
    private String companyName;
    private BigDecimal debit; // 借款
    private BigDecimal credit; // 贷款
    private BigDecimal sum; // 借贷差额
    private BigDecimal balance;
    private String direction;
    private int debitNum;
    private int creditNum;
    private int status;
    private int rowNum;
    private int colNum;

    public int getRowNum() {
        return rowNum;
    }

    public int getColNum() {
        return colNum;
    }

    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }

    public void setColNum(int colNum) {
        this.colNum = colNum;
    }
    public void setDebit(BigDecimal debit) {
        this.debit = debit;
    }
    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }
    public BigDecimal getSum() {
        return this.sum;
    }
    public void setCredit(BigDecimal credit) {
        this.credit = credit;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public void setDebitNum(int debitNum) {
        this.debitNum = debitNum;
    }

    public void setCreditNum(int creditNum) {
        this.creditNum = creditNum;
    }
    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    public String getCompanyName() {
        return companyName;
    }
}
