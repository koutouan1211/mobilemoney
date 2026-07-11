package com.mobilemoney.account.domain.valueobject;

import com.mobilemoney.shared.constant.TelephoneConstants;

import java.util.Objects;

public final class NumeroTelephone {

    private final String value;

    private NumeroTelephone(String value) {
        this.value = value;
    }

    public static NumeroTelephone of(String numero) {

        Objects.requireNonNull(numero,
                "Le numéro de téléphone est obligatoire.");

        String numeroNettoye = nettoyer(numero);

        String numeroNormalise = normaliser(numeroNettoye);

        valider(numeroNormalise);

        return new NumeroTelephone(numeroNormalise);
    }

    public String getValue() {
        return value;
    }
    
    //nettoyer le numero 
    private static String nettoyer(String numero) {

        return numero
                .replace(" ", "")
                .replace("-", "")
                .replace("+", "");

    }
    
    
    //
    private static String normaliser(String numero) {

        if (numero.length() ==
                TelephoneConstants.LOCAL_NUMBER_LENGTH) {

            return TelephoneConstants.COUNTRY_CODE + numero;

        }

        return numero;
    }
    
    
    //
    private static void valider(String numero) {

        if (numero.length() !=
                TelephoneConstants.INTERNATIONAL_NUMBER_LENGTH) {

            throw new IllegalArgumentException(
                    "Numéro de téléphone invalide."
            );
        }

    }
    
   
    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;

        if (!(o instanceof NumeroTelephone other))
            return false;

        return value.equals(other.value);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
    
}