package com.mobilemoney.account.domain.entity;

import com.mobilemoney.account.domain.enums.Profil;
import com.mobilemoney.account.domain.enums.StatutCompte;
import com.mobilemoney.account.domain.enums.TypePersonne;
import com.mobilemoney.account.domain.valueobject.Money;
import com.mobilemoney.account.domain.valueobject.NumeroTelephone;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CompteTest {

    @Test
    void should_create_subscriber_account() {

        Compte compte = Compte.creerSubscriber(
                "KOUTOUAN",
                "Lynda",
                NumeroTelephone.of("0707070707"),
                TypePersonne.PERSONNE_PHYSIQUE
        );

        assertEquals(Profil.SUBSCRIBER, compte.getProfil());
        assertEquals(StatutCompte.ACTIF, compte.getStatut());
        assertEquals(Money.of("200000"), compte.getPlafond());
        assertEquals(Money.zero(), compte.getSolde());
    }

    
    
    @Test
    void should_create_agent_account() {

        Compte compte = Compte.creerAgent(
                "Orange",
                "Money",
                NumeroTelephone.of("0700000001"),
                TypePersonne.PERSONNE_MORALE
        );

        assertEquals(Profil.AGENT, compte.getProfil());
        assertEquals(StatutCompte.EN_ATTENTE, compte.getStatut());
        assertEquals(Money.zero(), compte.getPlafond());
        assertEquals(Money.zero(), compte.getSolde());

    }
    
    
    @Test
    void should_throw_exception_when_subscriber_is_company() {

        assertThrows(
                IllegalArgumentException.class,
                () -> Compte.creerSubscriber(
                        "Entreprise",
                        "XYZ",
                        NumeroTelephone.of("0707070707"),
                        TypePersonne.PERSONNE_MORALE
                )
        );

    }
    
    
    @Test
    void should_create_company_agent() {

        Compte compte = Compte.creerAgent(
                "Entreprise",
                "XYZ",
                NumeroTelephone.of("0700000002"),
                TypePersonne.PERSONNE_MORALE
        );

        assertEquals(
                TypePersonne.PERSONNE_MORALE,
                compte.getTypePersonne()
        );

    }
    
    
    @Test
    void should_create_physical_agent() {

        Compte compte = Compte.creerAgent(
                "Jean",
                "Kouassi",
                NumeroTelephone.of("0700000003"),
                TypePersonne.PERSONNE_PHYSIQUE
        );

        assertEquals(
                TypePersonne.PERSONNE_PHYSIQUE,
                compte.getTypePersonne()
        );

    }
}