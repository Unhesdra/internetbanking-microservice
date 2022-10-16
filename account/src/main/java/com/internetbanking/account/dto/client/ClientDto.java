package com.internetbanking.account.dto.client;

import com.internetbanking.account.entity.Account;
import com.internetbanking.account.entity.BankClient;

import java.util.List;

public class ClientDto {

    private String firstName;
    private String lastName;
    private String documentId;
    private List<Account> accounts;

    public ClientDto(BankClient client) {
        this.firstName = client.getFirstName();
        this.lastName = client.getLastName();
        this.documentId = client.getDocumentId();
        this.accounts = client.getAccounts();
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

    public List<Account> getAccounts() { return accounts; }
}
