package com.internetbanking.account.dto.account;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class DepositDto {

    @NotNull
    private Long accountId;
    @NotNull
    private String documentId;
    @NotNull
    private BigDecimal depositValue;

    public DepositDto(Long accountId, String documentId, BigDecimal depositValue) {
        this.accountId = accountId;
        this.documentId = documentId;
        this.depositValue = depositValue;
    }

    public Long getAccountId() {
        return accountId;
    }

    public String getDocumentId() {
        return documentId;
    }

    public BigDecimal getDepositValue() {
        return depositValue;
    }
}
