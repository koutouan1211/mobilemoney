package com.mobilemoney.transaction.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.never;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import com.mobilemoney.common.exception.CompteClientInactifException;
import com.mobilemoney.common.exception.MotDePasseIncorrectException;

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
import com.mobilemoney.transaction.application.dto.CreatePaiementRequest;
import com.mobilemoney.transaction.application.dto.PaiementResponse;
import com.mobilemoney.transaction.domain.entity.Transfert;
import com.mobilemoney.transaction.domain.enums.TypeTransaction;
import com.mobilemoney.transaction.domain.repository.TransfertRepository;

@ExtendWith(MockitoExtension.class)
class CreatePaiementServiceTest {

    @Mock
    private CompteRepository compteRepository;

    @Mock
    private TransfertRepository transactionRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private CreatePaiementService createPaiementService;


    private static final MotDePasse MOT_DE_PASSE =
            MotDePasse.depuisHash("hash-test");


    @Test
    void effectuerPaiement_avecSucces() {

        // ========= ARRANGE =========

        CreatePaiementRequest request =
                new CreatePaiementRequest();

        request.setNumeroClient("0700000001");

        request.setNumeroMarchand("0700000002");

        request.setMotDePasse("1234");

        request.setMontant(
                new BigDecimal("5000"));

        request.setTypeTransaction(
                TypeTransaction.PAIEMENT_MARCHANT);

        request.setMotif(
                "Paiement achat");


        Compte client =
                Compte.reconstituer(
                        1L,
                        "KOFFI",
                        "Paul",
                        NumeroTelephone.of("0700000001"),
                        MOT_DE_PASSE,
                        Profil.SUBSCRIBER,
                        TypePersonne.PERSONNE_PHYSIQUE,
                        Money.of("100000"),
                        Money.of("200000"),
                        StatutCompte.ACTIF,
                        LocalDateTime.now()
                );


        Compte marchand =
                Compte.reconstituer(
                        2L,
                        "COMMERCE",
                        "ABC",
                        NumeroTelephone.of("0700000002"),
                        MOT_DE_PASSE,
                        Profil.MARCHAND,
                        TypePersonne.PERSONNE_MORALE,
                        Money.of("50000"),
                        Money.of("1000000"),
                        StatutCompte.ACTIF,
                        LocalDateTime.now()
                );


        when(compteRepository.findByNumeroTelephone(
                NumeroTelephone.of("0700000001")))
                .thenReturn(Optional.of(client));


        when(compteRepository.findByNumeroTelephone(
                NumeroTelephone.of("0700000002")))
                .thenReturn(Optional.of(marchand));


        // PIN correct
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

        PaiementResponse response =
                createPaiementService.effectuerPaiement(
                        request);


        // ========= ASSERT =========

        assertNotNull(response);


        // Solde du client
        assertEquals(
                Money.of("95000"),
                client.getSolde());


        // Solde du marchand
        assertEquals(
                Money.of("55000"),
                marchand.getSolde());


        // Vérifier que les deux comptes
        // ont été sauvegardés
        verify(compteRepository, times(2))
                .save(any(Compte.class));


        // Vérifier que la transaction
        // a été sauvegardée
        verify(transactionRepository)
                .save(any(Transfert.class));
    }
    
    
    @Test
    void effectuerPaiement_compteClientIntrouvable() {

        CreatePaiementRequest request = new CreatePaiementRequest();

        request.setNumeroClient("0700000001");
        request.setNumeroMarchand("0700000002");
        request.setMotDePasse("1234");
        request.setMontant(new BigDecimal("5000"));
        request.setTypeTransaction(TypeTransaction.PAIEMENT_MARCHANT);
        request.setMotif("Paiement achat");

        when(compteRepository.findByNumeroTelephone(
                NumeroTelephone.of("0700000001")))
                .thenReturn(Optional.empty());

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> createPaiementService.effectuerPaiement(request)
                );

        assertEquals(
                "Compte client introuvable.",
                exception.getMessage());

        verify(compteRepository, never()).save(any());
        verify(transactionRepository, never()).save(any());
    }
    
    @Test
    void effectuerPaiement_compteMarchandIntrouvable() {

        CreatePaiementRequest request = new CreatePaiementRequest();

        request.setNumeroClient("0700000001");
        request.setNumeroMarchand("0700000002");
        request.setMotDePasse("1234");
        request.setMontant(new BigDecimal("5000"));
        request.setTypeTransaction(TypeTransaction.PAIEMENT_MARCHANT);
        request.setMotif("Paiement achat");

        Compte client = Compte.reconstituer(
                1L,
                "KOFFI",
                "Paul",
                NumeroTelephone.of("0700000001"),
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
                .thenReturn(Optional.of(client));

        when(compteRepository.findByNumeroTelephone(
                NumeroTelephone.of("0700000002")))
                .thenReturn(Optional.empty());

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> createPaiementService.effectuerPaiement(request)
                );

        assertEquals(
                "Compte marchand introuvable.",
                exception.getMessage());

        verify(compteRepository, never()).save(any());
        verify(transactionRepository, never()).save(any());
    }
    
    
    @Test
    void effectuerPaiement_memeCompte() {

        CreatePaiementRequest request = new CreatePaiementRequest();

        request.setNumeroClient("0700000001");
        request.setNumeroMarchand("0700000001");
        request.setMotDePasse("1234");
        request.setMontant(new BigDecimal("5000"));
        request.setTypeTransaction(TypeTransaction.PAIEMENT_MARCHANT);
        request.setMotif("Paiement achat");

        Compte compte = Compte.reconstituer(
                1L,
                "KOFFI",
                "Paul",
                NumeroTelephone.of("0700000001"),
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
                .thenReturn(Optional.of(compte));

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> createPaiementService.effectuerPaiement(request)
                );

        assertEquals(
                "Impossible d'effectuer un paiement sur le même compte.",
                exception.getMessage());

        verify(compteRepository, never()).save(any());
        verify(transactionRepository, never()).save(any());
    }
    
    
    @Test
    void effectuerPaiement_typeTransactionInvalide() {

        CreatePaiementRequest request = new CreatePaiementRequest();

        request.setNumeroClient("0700000001");
        request.setNumeroMarchand("0700000002");
        request.setMotDePasse("1234");
        request.setMontant(new BigDecimal("5000"));
        request.setTypeTransaction(TypeTransaction.RETRAIT);
        request.setMotif("Paiement achat");

        Compte client = creerClientActif();
        Compte marchand = creerMarchandActif();

        when(compteRepository.findByNumeroTelephone(
                NumeroTelephone.of("0700000001")))
                .thenReturn(Optional.of(client));

        when(compteRepository.findByNumeroTelephone(
                NumeroTelephone.of("0700000002")))
                .thenReturn(Optional.of(marchand));

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> createPaiementService.effectuerPaiement(request)
                );

        assertEquals(
                "Type de transaction invalide pour un paiement.",
                exception.getMessage());

        verify(compteRepository, never()).save(any());
        verify(transactionRepository, never()).save(any());
    }
    
    
    @Test
    void effectuerPaiement_clientInactif() {

        CreatePaiementRequest request = creerRequestPaiement();

        Compte client = Compte.reconstituer(
                1L,
                "KOFFI",
                "Paul",
                NumeroTelephone.of("0700000001"),
                MOT_DE_PASSE,
                Profil.SUBSCRIBER,
                TypePersonne.PERSONNE_PHYSIQUE,
                Money.of("100000"),
                Money.of("200000"),
                StatutCompte.EN_ATTENTE,
                LocalDateTime.now()
        );

        Compte marchand = creerMarchandActif();

        when(compteRepository.findByNumeroTelephone(
                NumeroTelephone.of("0700000001")))
                .thenReturn(Optional.of(client));

        when(compteRepository.findByNumeroTelephone(
                NumeroTelephone.of("0700000002")))
                .thenReturn(Optional.of(marchand));

        assertThrows(
                CompteClientInactifException.class,
                () -> createPaiementService.effectuerPaiement(request)
        );

        verify(compteRepository, never()).save(any());
        verify(transactionRepository, never()).save(any());
    }
    
    
    @Test
    void effectuerPaiement_marchandInactif() {

        CreatePaiementRequest request = creerRequestPaiement();

        Compte client = creerClientActif();

        Compte marchand = Compte.reconstituer(
                2L,
                "COMMERCE",
                "ABC",
                NumeroTelephone.of("0700000002"),
                MOT_DE_PASSE,
                Profil.MARCHAND,
                TypePersonne.PERSONNE_MORALE,
                Money.of("50000"),
                Money.of("1000000"),
                StatutCompte.EN_ATTENTE,
                LocalDateTime.now()
        );

        when(compteRepository.findByNumeroTelephone(
                NumeroTelephone.of("0700000001")))
                .thenReturn(Optional.of(client));

        when(compteRepository.findByNumeroTelephone(
                NumeroTelephone.of("0700000002")))
                .thenReturn(Optional.of(marchand));

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> createPaiementService.effectuerPaiement(request)
                );

        assertEquals(
                "Le compte marchand est inactif.",
                exception.getMessage());

        verify(compteRepository, never()).save(any());
        verify(transactionRepository, never()).save(any());
    }
    
    
    @Test
    void effectuerPaiement_profilClientInvalide() {

        CreatePaiementRequest request = creerRequestPaiement();

        Compte client = Compte.reconstituer(
                1L,
                "AGENT",
                "Test",
                NumeroTelephone.of("0700000001"),
                MOT_DE_PASSE,
                Profil.AGENT,
                TypePersonne.PERSONNE_PHYSIQUE,
                Money.of("100000"),
                Money.of("1000000"),
                StatutCompte.ACTIF,
                LocalDateTime.now()
        );

        Compte marchand = creerMarchandActif();

        when(compteRepository.findByNumeroTelephone(
                NumeroTelephone.of("0700000001")))
                .thenReturn(Optional.of(client));

        when(compteRepository.findByNumeroTelephone(
                NumeroTelephone.of("0700000002")))
                .thenReturn(Optional.of(marchand));

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> createPaiementService.effectuerPaiement(request)
                );

        assertEquals(
                "Le compte utilisé pour le paiement doit être un client.",
                exception.getMessage());

        verify(compteRepository, never()).save(any());
        verify(transactionRepository, never()).save(any());
    }
    
    
    @Test
    void effectuerPaiement_profilMarchandInvalide() {

        CreatePaiementRequest request = creerRequestPaiement();

        Compte client = creerClientActif();

        Compte marchand = Compte.reconstituer(
                2L,
                "KOUASSI",
                "Jean",
                NumeroTelephone.of("0700000002"),
                MOT_DE_PASSE,
                Profil.SUBSCRIBER,
                TypePersonne.PERSONNE_PHYSIQUE,
                Money.of("50000"),
                Money.of("200000"),
                StatutCompte.ACTIF,
                LocalDateTime.now()
        );

        when(compteRepository.findByNumeroTelephone(
                NumeroTelephone.of("0700000001")))
                .thenReturn(Optional.of(client));

        when(compteRepository.findByNumeroTelephone(
                NumeroTelephone.of("0700000002")))
                .thenReturn(Optional.of(marchand));

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> createPaiementService.effectuerPaiement(request)
                );

        assertEquals(
                "Le compte destination doit être un marchand.",
                exception.getMessage());

        verify(compteRepository, never()).save(any());
        verify(transactionRepository, never()).save(any());
    }
    
    
    @Test
    void effectuerPaiement_motDePasseIncorrect() {

        CreatePaiementRequest request = creerRequestPaiement();

        request.setMotDePasse("9999");

        Compte client = creerClientActif();
        Compte marchand = creerMarchandActif();

        when(compteRepository.findByNumeroTelephone(
                NumeroTelephone.of("0700000001")))
                .thenReturn(Optional.of(client));

        when(compteRepository.findByNumeroTelephone(
                NumeroTelephone.of("0700000002")))
                .thenReturn(Optional.of(marchand));

        when(passwordEncoder.matches(
                "9999",
                "hash-test"))
                .thenReturn(false);

        assertThrows(
                MotDePasseIncorrectException.class,
                () -> createPaiementService.effectuerPaiement(request)
        );

        assertEquals(Money.of("100000"), client.getSolde());
        assertEquals(Money.of("50000"), marchand.getSolde());

        verify(compteRepository, never()).save(any());
        verify(transactionRepository, never()).save(any());
    }
    
    
    @Test
    void effectuerPaiement_soldeInsuffisant() {

        CreatePaiementRequest request = creerRequestPaiement();

        request.setMontant(new BigDecimal("50000"));

        Compte client = Compte.reconstituer(
                1L,
                "KOFFI",
                "Paul",
                NumeroTelephone.of("0700000001"),
                MOT_DE_PASSE,
                Profil.SUBSCRIBER,
                TypePersonne.PERSONNE_PHYSIQUE,
                Money.of("10000"),
                Money.of("200000"),
                StatutCompte.ACTIF,
                LocalDateTime.now()
        );

        Compte marchand = creerMarchandActif();

        when(compteRepository.findByNumeroTelephone(
                NumeroTelephone.of("0700000001")))
                .thenReturn(Optional.of(client));

        when(compteRepository.findByNumeroTelephone(
                NumeroTelephone.of("0700000002")))
                .thenReturn(Optional.of(marchand));

        when(passwordEncoder.matches(
                "1234",
                "hash-test"))
                .thenReturn(true);

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> createPaiementService.effectuerPaiement(request)
                );

        assertEquals(
                "Solde insuffisant.",
                exception.getMessage());

        verify(compteRepository, never()).save(any());
        verify(transactionRepository, never()).save(any());
    }
    
    
    //pour eviter de recopier les meme objets dans tous le test
    
    private CreatePaiementRequest creerRequestPaiement() {

        CreatePaiementRequest request = new CreatePaiementRequest();

        request.setNumeroClient("0700000001");
        request.setNumeroMarchand("0700000002");
        request.setMotDePasse("1234");
        request.setMontant(new BigDecimal("5000"));
        request.setTypeTransaction(TypeTransaction.PAIEMENT_MARCHANT);
        request.setMotif("Paiement achat");

        return request;
    }


    private Compte creerClientActif() {

        return Compte.reconstituer(
                1L,
                "KOFFI",
                "Paul",
                NumeroTelephone.of("0700000001"),
                MOT_DE_PASSE,
                Profil.SUBSCRIBER,
                TypePersonne.PERSONNE_PHYSIQUE,
                Money.of("100000"),
                Money.of("200000"),
                StatutCompte.ACTIF,
                LocalDateTime.now()
        );
    }


    private Compte creerMarchandActif() {

        return Compte.reconstituer(
                2L,
                "COMMERCE",
                "ABC",
                NumeroTelephone.of("0700000002"),
                MOT_DE_PASSE,
                Profil.MARCHAND,
                TypePersonne.PERSONNE_MORALE,
                Money.of("50000"),
                Money.of("1000000"),
                StatutCompte.ACTIF,
                LocalDateTime.now()
        );
    }
}