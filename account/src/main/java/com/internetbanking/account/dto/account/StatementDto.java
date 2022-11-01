package com.internetbanking.account.dto.account;

import javax.validation.constraints.NotNull;

public class StatementDto {

    @NotNull
    private Long accountId;
    @NotNull
    private Long clientId;

    public StatementDto(Long accountId, Long clientId) {
        this.accountId = accountId;
        this.clientId = clientId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public Long getClientId() {
        return clientId;
    }
}
