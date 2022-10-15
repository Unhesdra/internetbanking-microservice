package com.internetbanking.user.dto;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class RequestDebitDto {

    @NotNull
    private Long accountId;
    @NotNull
    private Long clientId;
    @NotNull
    private BigDecimal debitValue;

    public RequestDebitDto(Long accountId, Long clientId, BigDecimal debitValue) {
        this.accountId = accountId;
        this.clientId = clientId;
        this.debitValue = debitValue;
    }

    public Long getAccountId() {
        return accountId;
    }

    public Long getClientId() {
        return clientId;
    }

    public BigDecimal getDebitValue() {
        return debitValue;
    }
}
