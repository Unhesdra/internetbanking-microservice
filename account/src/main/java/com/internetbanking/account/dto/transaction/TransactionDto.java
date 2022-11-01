package com.internetbanking.account.dto.transaction;

import com.internetbanking.account.entity.Transaction;
import com.internetbanking.account.entity.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionDto {

    private BigDecimal value;
    private LocalDateTime transactionDate;
    private TransactionType transactionType;
    private Integer linkedAccountNumber;
    private Integer linkedBranch;

    public TransactionDto(Transaction transaction) {
        this.value = transaction.getValue();
        this.transactionDate = transaction.getTransactionDate();
        this.transactionType = transaction.getTransactionType();
        if(transaction.getLinkedTransaction() != null) {
            this.linkedAccountNumber = transaction
                    .getLinkedTransaction()
                    .getAccount()
                    .getAccountNumber();
            this.linkedBranch = transaction
                    .getLinkedTransaction()
                    .getAccount()
                    .getBranch();
        }
    }

    public BigDecimal getValue() {
        return value;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public Integer getLinkedAccountNumber() {
        return linkedAccountNumber;
    }

    public Integer getLinkedBranch() {
        return linkedBranch;
    }
}
