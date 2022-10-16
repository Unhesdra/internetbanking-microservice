package com.internetbanking.account.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class BankClient {

    @Id
    private Long clientId;
    private String firstName;
    private String lastName;
    private String documentId;
    @OneToMany
    private List<Account> accounts = new ArrayList<>();

    public BankClient() {
    }

    public BankClient(Long clientId, String firstName, String lastName, String documentId) {
        this.clientId = clientId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.documentId = documentId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Long getClientId() {
        return clientId;
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

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void addAccount (Account account) {
        accounts.add(account);
    }
}
