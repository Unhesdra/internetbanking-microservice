package com.internetbanking.account.dto.account;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class TransferDto {

    @NotNull
    private Long debitAccountId;

    @NotNull
    private Integer receivingAccountNumber;
    @NotNull
    private Integer receivingBranch;
    @NotNull
    private Long debitClientId;
    @NotNull
    private String receivingDocumentId;
    @NotNull
    private BigDecimal transferValue;

    public TransferDto(Long debitAccountId,
                       Integer receivingAccountNumber,
                       Long debitClientId,
                       String receivingDocumentId,
                       Integer receivingBranch,
                       BigDecimal transferValue) {
        this.debitAccountId = debitAccountId;
        this.receivingAccountNumber = receivingAccountNumber;
        this.debitClientId = debitClientId;
        this.receivingDocumentId = receivingDocumentId;
        this.receivingBranch = receivingBranch;
        this.transferValue = transferValue;
    }

    public Long getDebitAccountId() {
        return debitAccountId;
    }

    public Integer getReceivingAccountNumber() {
        return receivingAccountNumber;
    }

    public Integer getReceivingBranch() {
        return receivingBranch;
    }

    public Long getDebitClientId() {
        return debitClientId;
    }

    public String getReceivingDocumentId() {
        return receivingDocumentId;
    }

    public BigDecimal getTransferValue() {
        return transferValue;
    }
}
