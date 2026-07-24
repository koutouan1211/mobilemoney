package com.mobilemoney.transaction.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import com.mobilemoney.account.domain.valueobject.Money;
import com.mobilemoney.account.domain.valueobject.NumeroTelephone;
import com.mobilemoney.transaction.application.dto.CreateDepotRequest;
import com.mobilemoney.transaction.application.dto.DepotResponse;
import com.mobilemoney.transaction.domain.entity.Transfert;
import com.mobilemoney.transaction.domain.enums.TypeTransaction;
import com.mobilemoney.transaction.domain.repository.TransfertRepository;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
public class CreateDepotServiceTest {

	//permet de simuler la classe qu'on souhaite tester
	@Mock
    private CompteRepository compteRepository;

    @Mock
    private TransfertRepository transactionRepository;

    
    @InjectMocks
    private CreateDepotService createDepotService;
	
    
    @Test
    void effectuerDepot_avecSucces() {

        // ========= ARRANGE =========

        CreateDepotRequest request = new CreateDepotRequest();
        request.setNumeroAgent("0700000001");
        request.setNumeroClient("0700000002");
        request.setMontant(new BigDecimal("50000"));
        request.setTypeTransaction(TypeTransaction.DEPOT);
        request.setMotif("Dépôt en espèces");

        Compte agent = Compte.reconstituer(
                1L,
                "YAO",
                "Jean",
                NumeroTelephone.of("0700000001"),
                Profil.AGENT,
                TypePersonne.PERSONNE_PHYSIQUE,
                Money.of("500000"),
                Money.of("100000000"),
                StatutCompte.ACTIF,
                LocalDateTime.now()
        );

        Compte client = Compte.reconstituer(
                2L,
                "KOFFI",
                "Paul",
                NumeroTelephone.of("0700000002"),
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

        when(compteRepository.save(any(Compte.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        when(transactionRepository.save(any(Transfert.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // ========= ACT =========

        DepotResponse response =
                createDepotService.effectuerDepot(request);

        // ========= ASSERT =========

        assertNotNull(response);

        assertEquals(
                "450000.00 FCFA",
                agent.getSolde().toString());

        assertEquals(
                "150000.00 FCFA",
                client.getSolde().toString());

        assertEquals(
                "2250700000001",
                response.getNumeroAgent());

        assertEquals(
                "2250700000002",
                response.getNumeroClient());

        verify(compteRepository, times(2))
                .save(any(Compte.class));

        verify(transactionRepository)
                .save(any(Transfert.class));

    }
    
    
    
    @Test
    void effectuerDepot_compteAgentIntrouvable() {

        // ========= ARRANGE =========

        CreateDepotRequest request = new CreateDepotRequest();
        request.setNumeroAgent("0700000001");
        request.setNumeroClient("0700000002");
        request.setMontant(new BigDecimal("50000"));
        request.setTypeTransaction(TypeTransaction.DEPOT);
        request.setMotif("Dépôt en espèces");

        when(compteRepository.findByNumeroTelephone(
                NumeroTelephone.of("0700000001")))
                .thenReturn(Optional.empty());

        // ========= ACT + ASSERT =========

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> createDepotService.effectuerDepot(request)
                );

        assertEquals(
                "Compte agent introuvable.",
                exception.getMessage());

        verify(compteRepository, never())
                .save(any());

        verify(transactionRepository, never())
                .save(any());

    }
    
    
    @Test
    void effectuerDepot_compteClientIntrouvable() {

        // ========= ARRANGE =========

        CreateDepotRequest request = new CreateDepotRequest();
        request.setNumeroAgent("0700000001");
        request.setNumeroClient("0700000002");
        request.setMontant(new BigDecimal("50000"));
        request.setTypeTransaction(TypeTransaction.DEPOT);
        request.setMotif("Dépôt en espèces");

        Compte agent = Compte.reconstituer(
                1L,
                "YAO",
                "Jean",
                NumeroTelephone.of("0700000001"),
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
                        () -> createDepotService.effectuerDepot(request)
                );

        assertEquals(
                "Compte client introuvable.",
                exception.getMessage());

        verify(compteRepository, never())
                .save(any());

        verify(transactionRepository, never())
                .save(any());

    }
}
