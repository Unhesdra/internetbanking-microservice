package com.internetbanking.account.dto.account;

import com.internetbanking.account.entity.Account;

import java.math.BigDecimal;

public class AccountDto {

    private BigDecimal balance;
    private Integer accountNumber;
    private Integer branch;

    public AccountDto(Account account) {
        this.balance = account.getBalance();
        this.accountNumber = account.getAccountNumber();
        this.branch = account.getBranch();
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public Integer getAccountNumber() {
        return accountNumber;
    }

    public Integer getBranch() {
        return branch;
    }
}
