package com.mobilemoney.shared.constant;

public final class TelephoneConstants {

	
	//contrainte sur la longueur 
    /**
     * Indicatif téléphonique de la Côte d'Ivoire.
     */
    public static final String COUNTRY_CODE = "225";

    /**
     * Longueur d'un numéro local.
     * Exemple : 0707070707
     */
    public static final int LOCAL_NUMBER_LENGTH = 10;

    /**
     * Longueur d'un numéro international sans le '+'.
     * Exemple : 2250707070707
     */
    public static final int INTERNATIONAL_NUMBER_LENGTH = 13;

    private TelephoneConstants() {
        throw new IllegalStateException("Utility class");
    }
}