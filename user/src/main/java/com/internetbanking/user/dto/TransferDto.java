package com.internetbanking.user.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class TransferDto {

    @NotNull
    private Long debitAccountId;
    @NotNull
    private Integer depositAccountNumber;
    @NotNull
    private Integer depositBranch;
    @NotBlank
    private String depositDocumentId;
    @NotNull
    private BigDecimal transferValue;

    public TransferDto(Long debitAccountId,
                       Integer depositAccountNumber,
                       Integer depositBranch,
                       String depositDocumentId,
                       BigDecimal transferValue) {
        this.debitAccountId = debitAccountId;
        this.depositAccountNumber = depositAccountNumber;
        this.depositBranch = depositBranch;
        this.depositDocumentId = depositDocumentId;
        this.transferValue = transferValue;
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
}
