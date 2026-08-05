package com.mobilemoney.account.domain.valueobject;

import java.util.Objects;

public final class MotDePasse {

    private final String valeur;

    private MotDePasse(
            String valeur,
            boolean hash) {

        Objects.requireNonNull(
                valeur,
                "Le mot de passe est obligatoire.");

        if (valeur.isBlank()) {
            throw new IllegalArgumentException(
                    "Le mot de passe est obligatoire.");
        }

        // Validation uniquement pour un nouveau PIN
        if (!hash && !valeur.matches("\\d{4}")) {

            throw new IllegalArgumentException(
                    "Le mot de passe doit contenir exactement 4 chiffres.");
        }

        this.valeur = valeur;
    }

    public static MotDePasse of(String valeur) {

        return new MotDePasse(
                valeur,
                false);
    }
    
    //permet de ne pas valider le mot de passe a 4 chiffre lorsque celui ci provien directement de la base de donnée
    public static MotDePasse depuisHash(String hash) {

        return new MotDePasse(
                hash,
                true);
    }

    public String getValeur() {
        return valeur;
    }

    @Override
    public String toString() {
        return "****";
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (!(obj instanceof MotDePasse other)) {
            return false;
        }

        return valeur.equals(other.valeur);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valeur);
    }

}