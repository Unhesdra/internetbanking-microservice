package com.internetbanking.account.dto.client;

import javax.validation.constraints.NotBlank;

public class UpdateClientDto {

    @NotBlank
    private String documentId;

    public UpdateClientDto(String documentId) {
        this.documentId = documentId;
    }

    public String getDocumentId() {
        return documentId;
    }
}
