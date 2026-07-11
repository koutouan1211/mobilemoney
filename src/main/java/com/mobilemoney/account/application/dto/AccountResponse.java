package com.mobilemoney.account.application.dto;

public class AccountResponse {

    private Long id;

    private String numeroTelephone;

    private String message;

    public AccountResponse() {
    }

    public AccountResponse(Long id, String numeroTelephone, String message) {
        this.id = id;
        this.numeroTelephone = numeroTelephone;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public String getNumeroTelephone() {
        return numeroTelephone;
    }

    public String getMessage() {
        return message;
    }
}