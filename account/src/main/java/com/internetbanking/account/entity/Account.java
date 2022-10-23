package com.internetbanking.account.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;
    private BigDecimal balance;
    private Integer accountNumber;
    private Integer checkDigit;
    private Integer branch;
    @OneToMany(mappedBy = "account")
    private List<Transaction> transactions;
    public Account() {
    }

    public Account(BigDecimal balance, Integer accountNumber, Integer checkDigit, Integer branch) {
        this.balance = balance;
        this.accountNumber = accountNumber;
        this.checkDigit = checkDigit;
        this.branch = branch;
    }

    public Long getAccountId() {
        return accountId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public Integer getAccountNumber() {
        return accountNumber;
    }

    public Integer getCheckDigit() {
        return checkDigit;
    }

    public Integer getBranch() {
        return branch;
    }

    public Boolean debit(BigDecimal debitValue) {
        if(balance.compareTo(debitValue) < 0) {
            return false;
        }

        balance = balance.subtract(debitValue);
        return true;
    }

    public void deposit(BigDecimal creditValue) {
        balance = balance.add(creditValue);
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }
}
