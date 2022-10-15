package com.internetbanking.account.dto.account;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class DebitDto {

    @NotNull
    private Long accountId;
    @NotNull
    private Long clientId;
    @NotNull
    private BigDecimal debitValue;

    public DebitDto(Long accountId, Long clientId, BigDecimal debitValue) {
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
