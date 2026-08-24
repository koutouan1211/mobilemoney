package com.mobilemoney.transaction.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;

import com.mobilemoney.common.exception.CompteAgentInactifException;
import com.mobilemoney.common.exception.CompteClientInactifException;
import com.mobilemoney.common.exception.MotDePasseIncorrectException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mobilemoney.account.domain.entity.Compte;
import com.mobilemoney.account.domain.enums.Profil;
import com.mobilemoney.account.domain.enums.StatutCompte;
import com.mobilemoney.account.domain.enums.TypePersonne;
import com.mobilemoney.account.domain.repository.CompteRepository;
import com.mobilemoney.account.domain.service.PasswordEncoder;
import com.mobilemoney.account.domain.valueobject.Money;
import com.mobilemoney.account.domain.valueobject.MotDePasse;
import com.mobilemoney.account.domain.valueobject.NumeroTelephone;
import com.mobilemoney.transaction.application.dto.CreateRetraitRequest;
import com.mobilemoney.transaction.application.dto.RetraitResponse;
import com.mobilemoney.transaction.domain.entity.Transfert;
import com.mobilemoney.transaction.domain.enums.TypeTransaction;
import com.mobilemoney.transaction.domain.repository.TransfertRepository;

@ExtendWith(MockitoExtension.class)
class CreateRetraitServiceTest {

    @Mock
    private CompteRepository compteRepository;

    @Mock
    private TransfertRepository transactionRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private CreateRetraitService createRetraitService;


    private static final MotDePasse MOT_DE_PASSE =
            MotDePasse.depuisHash("hash-test");


    @Test
    void effectuerRetrait_avecSucces() {

        // ========= ARRANGE =========

        CreateRetraitRequest request =
                new CreateRetraitRequest();

        request.setNumeroAgent("0700000001");

        request.setNumeroClient("0700000002");

        request.setMotDePasse("1234");

        request.setMontant(
                new BigDecimal("5000"));

        request.setTypeTransaction(
                TypeTransaction.RETRAIT);

        request.setMotif("Retrait en espèces");


        Compte agent =
                Compte.reconstituer(
                        1L,
                        "YAO",
                        "Jean",
                        NumeroTelephone.of("0700000001"),
                        MOT_DE_PASSE,
                        Profil.AGENT,
                        TypePersonne.PERSONNE_PHYSIQUE,
                        Money.of("500000"),
                        Money.of("100000000"),
                        StatutCompte.ACTIF,
                        LocalDateTime.now()
                );


        Compte client =
                Compte.reconstituer(
                        2L,
                        "KOFFI",
                        "Paul",
                        NumeroTelephone.of("0700000002"),
                        MOT_DE_PASSE,
                        Profil.SUBSCRIBER,
                        TypePersonne.PERSONNE_PHYSIQUE,
                        Money.of("100000"),
                        Money.of("200000"),
                        StatutCompte.ACTIF,
                        LocalDateTime.now()
                );


        when(compteRepository.findByNumeroTelephone(
                NumeroTelephone.of("0700000001")))
                .thenReturn(Optional.of(agent));


        when(compteRepository.findByNumeroTelephone(
                NumeroTelephone.of("0700000002")))
                .thenReturn(Optional.of(client));


        // Le PIN est correct
        when(passwordEncoder.matches(
                "1234",
                "hash-test"))
                .thenReturn(true);


        when(compteRepository.save(
                any(Compte.class)))
                .thenAnswer(invocation ->
                        invocation.getArgument(0));


        when(transactionRepository.save(
                any(Transfert.class)))
                .thenAnswer(invocation ->
                        invocation.getArgument(0));


        // ========= ACT =========

        RetraitResponse response =
                createRetraitService.effectuerRetrait(
                        request);


        // ========= ASSERT =========

        assertNotNull(response);


        // Vérifier le nouveau solde du client
        assertEquals(
                Money.of("95000"),
                client.getSolde());


        // Vérifier le nouveau solde de l'agent
        assertEquals(
                Money.of("505000"),
                agent.getSolde());


        // Vérifier les sauvegardes
        verify(compteRepository, times(2))
                .save(any(Compte.class));


        verify(transactionRepository)
                .save(any(Transfert.class));
    }
    
    
    @Test
    void effectuerRetrait_compteAgentIntrouvable() {

        // ========= ARRANGE =========

        CreateRetraitRequest request =
                new CreateRetraitRequest();

        request.setNumeroAgent("0700000001");
        request.setNumeroClient("0700000002");
        request.setMotDePasse("1234");
        request.setMontant(new BigDecimal("5000"));
        request.setTypeTransaction(TypeTransaction.RETRAIT);
        request.setMotif("Retrait en espèces");


        when(compteRepository.findByNumeroTelephone(
                NumeroTelephone.of("0700000001")))
                .thenReturn(Optional.empty());


        // ========= ACT + ASSERT =========

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> createRetraitService.effectuerRetrait(request)
                );


        assertEquals(
                "Compte agent introuvable.",
                exception.getMessage());


        verify(compteRepository, never())
                .save(any(Compte.class));

        verify(transactionRepository, never())
                .save(any(Transfert.class));
    }
    
    
    @Test
    void effectuerRetrait_compteClientIntrouvable() {

        // ========= ARRANGE =========

        CreateRetraitRequest request =
                new CreateRetraitRequest();

        request.setNumeroAgent("0700000001");
        request.setNumeroClient("0700000002");
        request.setMotDePasse("1234");
        request.setMontant(new BigDecimal("5000"));
        request.setTypeTransaction(TypeTransaction.RETRAIT);
        request.setMotif("Retrait en espèces");


        Compte agent =
                Compte.reconstituer(
                        1L,
                        "YAO",
                        "Jean",
                        NumeroTelephone.of("0700000001"),
                        MOT_DE_PASSE,
                        Profil.AGENT,
                        TypePersonne.PERSONNE_PHYSIQUE,
                        Money.of("500000"),
                        Money.of("100000000"),
                        StatutCompte.ACTIF,
                        LocalDateTime.now()
                );


        when(compteRepository.findByNumeroTelephone(
                NumeroTelephone.of("0700000001")))
                .thenReturn(Optional.of(agent));


        when(compteRepository.findByNumeroTelephone(
                NumeroTelephone.of("0700000002")))
                .thenReturn(Optional.empty());


        // ========= ACT + ASSERT =========

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> createRetraitService.effectuerRetrait(request)
                );


        assertEquals(
                "Compte client introuvable.",
                exception.getMessage());


        verify(compteRepository, never())
                .save(any(Compte.class));

        verify(transactionRepository, never())
                .save(any(Transfert.class));
    }
    
    
    
    @Test
    void effectuerRetrait_memeCompte() {

        // ========= ARRANGE =========

        CreateRetraitRequest request =
                new CreateRetraitRequest();

        request.setNumeroAgent("0700000001");
        request.setNumeroClient("0700000001");
        request.setMotDePasse("1234");
        request.setMontant(new BigDecimal("5000"));
        request.setTypeTransaction(TypeTransaction.RETRAIT);
        request.setMotif("Retrait en espèces");


        Compte compte =
                Compte.reconstituer(
                        1L,
                        "YAO",
                        "Jean",
                        NumeroTelephone.of("0700000001"),
                        MOT_DE_PASSE,
                        Profil.AGENT,
                        TypePersonne.PERSONNE_PHYSIQUE,
                        Money.of("500000"),
                        Money.of("100000000"),
                        StatutCompte.ACTIF,
                        LocalDateTime.now()
                );


        when(compteRepository.findByNumeroTelephone(
                NumeroTelephone.of("0700000001")))
                .thenReturn(Optional.of(compte));


        // ========= ACT + ASSERT =========

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> createRetraitService.effectuerRetrait(request)
                );


        assertEquals(
                "Impossible d'effectuer un retrait sur le même compte.",
                exception.getMessage());


        verify(compteRepository, never())
                .save(any(Compte.class));

        verify(transactionRepository, never())
                .save(any(Transfert.class));
    }
    
    
    @Test
    void effectuerRetrait_typeTransactionInvalide() {

        // ========= ARRANGE =========

        CreateRetraitRequest request =
                new CreateRetraitRequest();

        request.setNumeroAgent("0700000001");
        request.setNumeroClient("0700000002");
        request.setMotDePasse("1234");
        request.setMontant(new BigDecimal("5000"));

        request.setTypeTransaction(
                TypeTransaction.DEPOT);

        request.setMotif("Retrait en espèces");


        Compte agent =
                Compte.reconstituer(
                        1L,
                        "YAO",
                        "Jean",
                        NumeroTelephone.of("0700000001"),
                        MOT_DE_PASSE,
                        Profil.AGENT,
                        TypePersonne.PERSONNE_PHYSIQUE,
                        Money.of("500000"),
                        Money.of("100000000"),
                        StatutCompte.ACTIF,
                        LocalDateTime.now()
                );


        Compte client =
                Compte.reconstituer(
                        2L,
                        "KOFFI",
                        "Paul",
                        NumeroTelephone.of("0700000002"),
                        MOT_DE_PASSE,
                        Profil.SUBSCRIBER,
                        TypePersonne.PERSONNE_PHYSIQUE,
                        Money.of("100000"),
                        Money.of("200000"),
                        StatutCompte.ACTIF,
                        LocalDateTime.now()
                );


        when(compteRepository.findByNumeroTelephone(
                NumeroTelephone.of("0700000001")))
                .thenReturn(Optional.of(agent));


        when(compteRepository.findByNumeroTelephone(
                NumeroTelephone.of("0700000002")))
                .thenReturn(Optional.of(client));


        // ========= ACT + ASSERT =========

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> createRetraitService.effectuerRetrait(request)
                );


        assertEquals(
                "Type de transaction invalide pour un retrait.",
                exception.getMessage());


        verify(compteRepository, never())
                .save(any(Compte.class));

        verify(transactionRepository, never())
                .save(any(Transfert.class));
    }
    
    
    @Test
    void effectuerRetrait_compteAgentInactif() {

        // ========= ARRANGE =========

        CreateRetraitRequest request =
                new CreateRetraitRequest();

        request.setNumeroAgent("0700000001");
        request.setNumeroClient("0700000002");
        request.setMotDePasse("1234");
        request.setMontant(new BigDecimal("5000"));
        request.setTypeTransaction(TypeTransaction.RETRAIT);
        request.setMotif("Retrait en espèces");


        Compte agent =
                Compte.reconstituer(
                        1L,
                        "YAO",
                        "Jean",
                        NumeroTelephone.of("0700000001"),
                        MOT_DE_PASSE,
                        Profil.AGENT,
                        TypePersonne.PERSONNE_PHYSIQUE,
                        Money.of("500000"),
                        Money.of("100000000"),
                        StatutCompte.EN_ATTENTE,
                        LocalDateTime.now()
                );


        Compte client =
                Compte.reconstituer(
                        2L,
                        "KOFFI",
                        "Paul",
                        NumeroTelephone.of("0700000002"),
                        MOT_DE_PASSE,
                        Profil.SUBSCRIBER,
                        TypePersonne.PERSONNE_PHYSIQUE,
                        Money.of("100000"),
                        Money.of("200000"),
                        StatutCompte.ACTIF,
                        LocalDateTime.now()
                );


        when(compteRepository.findByNumeroTelephone(
                NumeroTelephone.of("0700000001")))
                .thenReturn(Optional.of(agent));


        when(compteRepository.findByNumeroTelephone(
                NumeroTelephone.of("0700000002")))
                .thenReturn(Optional.of(client));


        // ========= ACT + ASSERT =========

        assertThrows(
                CompteAgentInactifException.class,
                () -> createRetraitService.effectuerRetrait(request)
        );


        verify(compteRepository, never())
                .save(any(Compte.class));

        verify(transactionRepository, never())
                .save(any(Transfert.class));
    }
    
    
    
    @Test
    void effectuerRetrait_compteClientInactif() {

        // ========= ARRANGE =========

        CreateRetraitRequest request =
                new CreateRetraitRequest();

        request.setNumeroAgent("0700000001");
        request.setNumeroClient("0700000002");
        request.setMotDePasse("1234");
        request.setMontant(new BigDecimal("5000"));
        request.setTypeTransaction(TypeTransaction.RETRAIT);
        request.setMotif("Retrait en espèces");


        Compte agent =
                Compte.reconstituer(
                        1L,
                        "YAO",
                        "Jean",
                        NumeroTelephone.of("0700000001"),
                        MOT_DE_PASSE,
                        Profil.AGENT,
                        TypePersonne.PERSONNE_PHYSIQUE,
                        Money.of("500000"),
                        Money.of("100000000"),
                        StatutCompte.ACTIF,
                        LocalDateTime.now()
                );


        Compte client =
                Compte.reconstituer(
                        2L,
                        "KOFFI",
                        "Paul",
                        NumeroTelephone.of("0700000002"),
                        MOT_DE_PASSE,
                        Profil.SUBSCRIBER,
                        TypePersonne.PERSONNE_PHYSIQUE,
                        Money.of("100000"),
                        Money.of("200000"),
                        StatutCompte.EN_ATTENTE,
                        LocalDateTime.now()
                );


        when(compteRepository.findByNumeroTelephone(
                NumeroTelephone.of("0700000001")))
                .thenReturn(Optional.of(agent));


        when(compteRepository.findByNumeroTelephone(
                NumeroTelephone.of("0700000002")))
                .thenReturn(Optional.of(client));


        // ========= ACT + ASSERT =========

        assertThrows(
                CompteClientInactifException.class,
                () -> createRetraitService.effectuerRetrait(request)
        );


        verify(compteRepository, never())
                .save(any(Compte.class));

        verify(transactionRepository, never())
                .save(any(Transfert.class));
    }
    
    
    @Test
    void effectuerRetrait_motDePasseIncorrect() {

        // ========= ARRANGE =========

        CreateRetraitRequest request =
                new CreateRetraitRequest();

        request.setNumeroAgent("0700000001");
        request.setNumeroClient("0700000002");

        request.setMotDePasse("9999");

        request.setMontant(new BigDecimal("5000"));
        request.setTypeTransaction(TypeTransaction.RETRAIT);
        request.setMotif("Retrait en espèces");


        Compte agent =
                Compte.reconstituer(
                        1L,
                        "YAO",
                        "Jean",
                        NumeroTelephone.of("0700000001"),
                        MOT_DE_PASSE,
                        Profil.AGENT,
                        TypePersonne.PERSONNE_PHYSIQUE,
                        Money.of("500000"),
                        Money.of("100000000"),
                        StatutCompte.ACTIF,
                        LocalDateTime.now()
                );


        Compte client =
                Compte.reconstituer(
                        2L,
                        "KOFFI",
                        "Paul",
                        NumeroTelephone.of("0700000002"),
                        MOT_DE_PASSE,
                        Profil.SUBSCRIBER,
                        TypePersonne.PERSONNE_PHYSIQUE,
                        Money.of("100000"),
                        Money.of("200000"),
                        StatutCompte.ACTIF,
                        LocalDateTime.now()
                );


        when(compteRepository.findByNumeroTelephone(
                NumeroTelephone.of("0700000001")))
                .thenReturn(Optional.of(agent));


        when(compteRepository.findByNumeroTelephone(
                NumeroTelephone.of("0700000002")))
                .thenReturn(Optional.of(client));


        when(passwordEncoder.matches(
                "9999",
                "hash-test"))
                .thenReturn(false);


        // ========= ACT + ASSERT =========

        assertThrows(
                MotDePasseIncorrectException.class,
                () -> createRetraitService.effectuerRetrait(request)
        );


        // Le solde ne doit pas avoir changé
        assertEquals(
                Money.of("100000"),
                client.getSolde());

        assertEquals(
                Money.of("500000"),
                agent.getSolde());


        verify(compteRepository, never())
                .save(any(Compte.class));

        verify(transactionRepository, never())
                .save(any(Transfert.class));
    }
    
    
    @Test
    void effectuerRetrait_reglesFinancieres() {

        // =====================================================
        // CAS 1 : SOLDE CLIENT INSUFFISANT
        // =====================================================

        CreateRetraitRequest request =
                new CreateRetraitRequest();

        request.setNumeroAgent("0700000001");
        request.setNumeroClient("0700000002");
        request.setMotDePasse("1234");
        request.setMontant(new BigDecimal("50000"));
        request.setTypeTransaction(TypeTransaction.RETRAIT);
        request.setMotif("Retrait en espèces");


        Compte agent =
                Compte.reconstituer(
                        1L,
                        "YAO",
                        "Jean",
                        NumeroTelephone.of("0700000001"),
                        MOT_DE_PASSE,
                        Profil.AGENT,
                        TypePersonne.PERSONNE_PHYSIQUE,
                        Money.of("500000"),
                        Money.of("100000000"),
                        StatutCompte.ACTIF,
                        LocalDateTime.now()
                );


        Compte client =
                Compte.reconstituer(
                        2L,
                        "KOFFI",
                        "Paul",
                        NumeroTelephone.of("0700000002"),
                        MOT_DE_PASSE,
                        Profil.SUBSCRIBER,
                        TypePersonne.PERSONNE_PHYSIQUE,
                        Money.of("10000"),
                        Money.of("200000"),
                        StatutCompte.ACTIF,
                        LocalDateTime.now()
                );


        when(compteRepository.findByNumeroTelephone(
                NumeroTelephone.of("0700000001")))
                .thenReturn(Optional.of(agent));


        when(compteRepository.findByNumeroTelephone(
                NumeroTelephone.of("0700000002")))
                .thenReturn(Optional.of(client));


        when(passwordEncoder.matches(
                "1234",
                "hash-test"))
                .thenReturn(true);


        // ========= ACT + ASSERT =========

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> createRetraitService.effectuerRetrait(request)
                );


        assertEquals(
                "Solde insuffisant.",
                exception.getMessage());


        assertEquals(
                Money.of("10000"),
                client.getSolde());


        // =====================================================
        // CAS 2 : PLAFOND DE L'AGENT
        // =====================================================

        Compte agentPlafond =
                Compte.reconstituer(
                        3L,
                        "YAO",
                        "Jean",
                        NumeroTelephone.of("0700000003"),
                        MOT_DE_PASSE,
                        Profil.AGENT,
                        TypePersonne.PERSONNE_PHYSIQUE,
                        Money.of("99000"),
                        Money.of("100000"),
                        StatutCompte.ACTIF,
                        LocalDateTime.now()
                );


        Compte clientPlafond =
                Compte.reconstituer(
                        4L,
                        "KOFFI",
                        "Paul",
                        NumeroTelephone.of("0700000004"),
                        MOT_DE_PASSE,
                        Profil.SUBSCRIBER,
                        TypePersonne.PERSONNE_PHYSIQUE,
                        Money.of("100000"),
                        Money.of("200000"),
                        StatutCompte.ACTIF,
                        LocalDateTime.now()
                );


        CreateRetraitRequest requestPlafond =
                new CreateRetraitRequest();

        requestPlafond.setNumeroAgent("0700000003");
        requestPlafond.setNumeroClient("0700000004");
        requestPlafond.setMotDePasse("1234");
        requestPlafond.setMontant(new BigDecimal("5000"));
        requestPlafond.setTypeTransaction(TypeTransaction.RETRAIT);
        requestPlafond.setMotif("Retrait en espèces");


        when(compteRepository.findByNumeroTelephone(
                NumeroTelephone.of("0700000003")))
                .thenReturn(Optional.of(agentPlafond));


        when(compteRepository.findByNumeroTelephone(
                NumeroTelephone.of("0700000004")))
                .thenReturn(Optional.of(clientPlafond));


        when(passwordEncoder.matches(
                "1234",
                "hash-test"))
                .thenReturn(true);


        IllegalArgumentException exceptionPlafond =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> createRetraitService.effectuerRetrait(
                                requestPlafond)
                );


        assertEquals(
                "Le plafond du compte de l'agent sera dépassé.",
                exceptionPlafond.getMessage());
    }
}