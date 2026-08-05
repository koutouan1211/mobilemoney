package com.mobilemoney.common.exception;

public class MotDePasseIncorrectException
        extends IllegalArgumentException {

    public MotDePasseIncorrectException() {

        super("Le mot de passe est incorrect.");

    }

}