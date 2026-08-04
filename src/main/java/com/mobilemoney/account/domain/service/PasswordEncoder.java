package com.mobilemoney.account.domain.service;

public interface PasswordEncoder {

    /**
     * Hache un mot de passe.
     */
    String encoder(String motDePasse);

    /**
     * Vérifie qu'un mot de passe correspond
     * au hash enregistré.
     * matches=verifier
     */
    boolean matches(
            String motDePasse,
            String motDePasseHache);

}