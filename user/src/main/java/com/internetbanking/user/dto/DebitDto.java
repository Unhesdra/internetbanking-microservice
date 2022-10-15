package com.internetbanking.user.dto;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class DebitDto {

    @NotNull
    private Long accountId;
    @NotNull
    private BigDecimal debitValue;

    public DebitDto(Long accountId, BigDecimal debitValue) {
        this.accountId = accountId;
        this.debitValue = debitValue;
    }

    public Long getAccountId() {
        return accountId;
    }

    public BigDecimal getDebitValue() {
        return debitValue;
    }
}
