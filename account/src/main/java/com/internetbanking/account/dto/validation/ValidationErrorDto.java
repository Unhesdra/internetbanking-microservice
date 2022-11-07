package com.internetbanking.account.dto.validation;

public class ValidationErrorDto {

    private String field;
    private String erroMessage;

    public ValidationErrorDto(String field, String errorMessage) {
        this.field = field;
        this.erroMessage = errorMessage;
    }

    public String getField() {
        return field;
    }

    public String getErroMessage() {
        return erroMessage;
    }
}
