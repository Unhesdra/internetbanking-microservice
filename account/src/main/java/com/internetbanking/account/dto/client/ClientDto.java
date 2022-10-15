package com.internetbanking.account.dto.client;

import com.internetbanking.account.entity.BankClient;

import javax.validation.constraints.NotBlank;

public class ClientDto {

    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String documentId;

    public ClientDto(String firstName, String lastName, String documentId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.documentId = documentId;
    }

    public ClientDto(BankClient client) {
        this.firstName = client.getFirstName();
        this.lastName = client.getLastName();
        this.documentId = client.getDocumentId();
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDocumentId() {
        return documentId;
    }
}
