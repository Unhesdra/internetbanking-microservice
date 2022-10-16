package com.internetbanking.account.dto.client;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CreateClientDto {

    @NotNull
    private Long clientId;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String documentId;
    @NotNull
    private Integer branch;

    public CreateClientDto(Long clientId, String firstName, String lastName, String documentId, Integer branch) {
        this.clientId = clientId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.documentId = documentId;
        this.branch = branch;
    }

    public Long getClientId() { return clientId; }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDocumentId() {
        return documentId;
    }

    public Integer getBranch() {
        return branch;
    }
}
