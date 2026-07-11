package com.mobilemoney.shared.constant;

import java.math.RoundingMode;

public final class MoneyConstants {
	
	//permet de gerer les devises les decimals et les modes d'arrondi

    public static final String CURRENCY = "FCFA";

    public static final int SCALE = 2;

    public static final RoundingMode ROUNDING_MODE =
            RoundingMode.HALF_UP;

    private MoneyConstants() {
        throw new IllegalStateException("Utility class");
    }
}