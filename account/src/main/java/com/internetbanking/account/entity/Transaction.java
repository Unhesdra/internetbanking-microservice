package com.internetbanking.account.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;
    private BigDecimal value;
    private LocalDateTime transactionDate = LocalDateTime.now();
    @ManyToOne
    private Account account;
    private TransactionType transactionType;
    @OneToOne
    private Transaction linkedTransaction;

    public Transaction() {
    }

    public Transaction(BigDecimal value,
                       Account account,
                       TransactionType transactionType) {
        this.value = value;
        this.account = account;
        this.transactionType = transactionType;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public BigDecimal getValue() {
        return value;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public Account getAccount() {
        return account;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public Transaction getLinkedTransaction() {
        return linkedTransaction;
    }

    public void setLinkedTransaction(Transaction linkedTransaction) {
        this.linkedTransaction = linkedTransaction;
    }
}
