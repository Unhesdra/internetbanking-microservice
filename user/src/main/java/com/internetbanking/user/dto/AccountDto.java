package com.internetbanking.user.dto;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class AccountDto {

    private BigDecimal balance;
    private Integer accountNumber;
    private Integer branch;

    public AccountDto() {
    }

    public AccountDto(BigDecimal balance, Integer accountNumber, Integer branch) {
        this.balance = balance;
        this.accountNumber = accountNumber;
        this.branch = branch;
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
