/**
package com.mobilemoney.transaction.application.service

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.mobilemoney.account.domain.entity.Compte;
import com.mobilemoney.account.domain.enums.Profil;
import com.mobilemoney.account.domain.enums.StatutCompte;
import com.mobilemoney.account.domain.enums.TypePersonne;
import com.mobilemoney.account.domain.repository.CompteRepository;
import com.mobilemoney.account.domain.valueobject.Money;
import com.mobilemoney.account.domain.valueobject.NumeroTelephone;
import com.mobilemoney.transaction.application.dto.CreateTransfertRequest;
import com.mobilemoney.transaction.application.dto.TransfertResponse;
import com.mobilemoney.transaction.application.service.CreateTransfertService;
import com.mobilemoney.transaction.domain.entity.Transfert;
import com.mobilemoney.transaction.domain.enums.TypeTransaction;
import com.mobilemoney.transaction.domain.repository.TransfertRepository;
import com.mobilemoney.transaction.domain.service.CalculFraisTransfertService;

class CreateTransfertServiceTest {

    @Mock
    private CompteRepository compteRepository;

    @Mock
    private TransfertRepository transfertRepository;

    private CalculFraisTransfertService calculFraisService;

    private CreateTransfertService service;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);

        calculFraisService = new CalculFraisTransfertService();

        service = new CreateTransfertService(
                compteRepository,
                transfertRepository,
                calculFraisService);
    }
    
    
    @Test
    void doitEffectuerUnTransfertAvecSucces() {

        Compte source = Compte.creer(
                "KOUTOUAN",
                "Lynda",
                NumeroTelephone.of("0707070707"),
                Profil.CLIENT,
                TypePersonne.PHYSIQUE);

        source.crediter(Money.of(100000));

        Compte destination = Compte.creer(
                "YAO",
                "Paul",
                NumeroTelephone.of("0101010101"),
                Profil.CLIENT,
                TypePersonne.PHYSIQUE);

        when(compteRepository.findByNumeroTelephone(source.getNumeroTelephone()))
                .thenReturn(Optional.of(source));

        when(compteRepository.findByNumeroTelephone(destination.getNumeroTelephone()))
                .thenReturn(Optional.of(destination));

        when(transfertRepository.save(any(Transfert.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        CreateTransfertRequest request = new CreateTransfertRequest();

        request.setCompteSource("0707070707");
        request.setCompteDestination("0101010101");
        request.setMontant(20000); 
        request.setTypeTransaction(TypeTransaction.TRANSFERT_DOMESTIQUE);
        request.setMotif("Test");

        TransfertResponse response =
                service.effectuerTransaction(request);

        assertNotNull(response);

        assertEquals(
                "Transfert effectué avec succès",
                response.getMessage());

        verify(compteRepository, times(2)).save(any(Compte.class));

        verify(transfertRepository).save(any(Transfert.class));
    }

    
    
    @Test
    void doitRefuserSiCompteSourceIntrouvable() {

        CreateTransfertRequest request = new CreateTransfertRequest();

        request.setCompteSource("0707070707");

        when(compteRepository.findByNumeroTelephone(any()))
                .thenReturn(Optional.empty());

        assertThrows(
                IllegalArgumentException.class,
                () -> service.effectuerTransaction(request));
    }
    
    
    @Test
    void doitRefuserSiCompteDestinationIntrouvable() {

        Compte source = ...;

        // 1. D'abord la règle générale : par défaut, tout numéro renvoie un Optional vide
        when(compteRepository.findByNumeroTelephone(any(NumeroTelephone.class)))
                .thenReturn(Optional.empty());

        // 2. Ensuite la règle spécifique : LE numéro de la source renvoie la source
        when(compteRepository.findByNumeroTelephone(source.getNumeroTelephone()))
                .thenReturn(Optional.of(source));

        // 3. Exécution & Assertion
        assertThrows(
                IllegalArgumentException.class,
                () -> service.effectuerTransaction(request));
    }
    
}
*/