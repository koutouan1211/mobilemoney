package com.mobilemoney.transaction.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.mobilemoney.account.domain.valueobject.Money;

class CalculFraisTransfertServiceTest {

    private final CalculFraisTransfertService service =
            new CalculFraisTransfertService();

    @Test
    void doitCalculerUnPourcent() {

        Money montant = Money.of(20000);

        Money frais = service.calculer(montant);

        assertEquals(
                Money.of(200),
                frais);
    }

    @Test
    void doitCalculerLesFraisSurCentMille() {

        Money montant = Money.of(100000);

        Money frais = service.calculer(montant);

        assertEquals(
                Money.of(1000),
                frais);
    }
}