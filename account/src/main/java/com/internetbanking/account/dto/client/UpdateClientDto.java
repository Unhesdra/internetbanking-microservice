package com.internetbanking.account.dto.client;

import javax.validation.constraints.NotBlank;

public class UpdateClientDto {

    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;

    public UpdateClientDto(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
