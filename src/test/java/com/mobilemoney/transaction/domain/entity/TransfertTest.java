package com.mobilemoney.transaction.domain.entity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.mobilemoney.account.domain.valueobject.Money;
import com.mobilemoney.account.domain.valueobject.NumeroTelephone;
import com.mobilemoney.transaction.domain.enums.StatutTransaction;
import com.mobilemoney.transaction.domain.enums.TypeTransaction;
import com.mobilemoney.transaction.domain.valueobject.ReferenceTransfert;


class TransfertTest {

    @Test
    void should_create_transaction() {

        Transfert transaction = Transfert.creer(
                ReferenceTransfert.of("TX202607130001"),
                TypeTransaction.TRANSFERT_DOMESTIQUE,
                Money.of(10000),
                Money.of(100),
                NumeroTelephone.of("0707070707"),
                NumeroTelephone.of("2250102030405"),
                "Premier dépôt"
        );

        assertEquals(
                TypeTransaction.TRANSFERT_DOMESTIQUE,
                transaction.getTypeTransaction());

        assertEquals(
                StatutTransaction.SUCCES,
                transaction.getStatut());

        assertEquals(
                Money.of(10000),
                transaction.getMontant());

        assertEquals(
                NumeroTelephone.of("2250707070707"),
                transaction.getCompteSource());

        assertEquals(
                NumeroTelephone.of("2250102030405"),
                transaction.getCompteDestination());

        assertNotNull(transaction.getDateTransaction());
    }

}
