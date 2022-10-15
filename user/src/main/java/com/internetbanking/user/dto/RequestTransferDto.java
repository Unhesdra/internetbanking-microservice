package com.internetbanking.user.dto;

import java.math.BigDecimal;

public class RequestTransferDto {

    private Long debitAccountId;
    private Integer depositAccountNumber;
    private Integer depositBranch;
    private String depositDocumentId;
    private BigDecimal transferValue;
    private Long debitClientId;

    public RequestTransferDto(Long debitAccountId,
                              Integer depositAccountNumber,
                              Integer depositBranch,
                              String depositDocumentId,
                              BigDecimal transferValue,
                              Long debitClientId) {
    }

    public Long getDebitAccountId() {
        return debitAccountId;
    }

    public Integer getDepositAccountNumber() {
        return depositAccountNumber;
    }

    public Integer getDepositBranch() {
        return depositBranch;
    }

    public String getDepositDocumentId() {
        return depositDocumentId;
    }

    public BigDecimal getTransferValue() {
        return transferValue;
    }

    public Long getDebitClientId() {
        return debitClientId;
    }
}
