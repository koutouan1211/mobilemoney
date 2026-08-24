package com.mobilemoney.transaction.application.service;

import org.springframework.stereotype.Service;

import com.mobilemoney.account.domain.entity.Compte;
import com.mobilemoney.account.domain.enums.Profil;
import com.mobilemoney.account.domain.enums.StatutCompte;
import com.mobilemoney.account.domain.repository.CompteRepository;
import com.mobilemoney.account.domain.service.PasswordEncoder;
import com.mobilemoney.account.domain.valueobject.Money;
import com.mobilemoney.account.domain.valueobject.NumeroTelephone;
import com.mobilemoney.common.exception.CompteClientInactifException;
import com.mobilemoney.transaction.application.dto.CreatePaiementRequest;
import com.mobilemoney.transaction.application.dto.PaiementResponse;
import com.mobilemoney.transaction.application.usecase.CreatePaiementUseCase;
import com.mobilemoney.transaction.domain.entity.Transfert;
import com.mobilemoney.transaction.domain.enums.TypeTransaction;
import com.mobilemoney.transaction.domain.repository.TransfertRepository;
import com.mobilemoney.transaction.domain.valueobject.ReferenceTransfert;

@Service
public class CreatePaiementService
        implements CreatePaiementUseCase {

    private final CompteRepository compteRepository;

    private final TransfertRepository transactionRepository;

    private final PasswordEncoder passwordEncoder;


    public CreatePaiementService(
            CompteRepository compteRepository,
            TransfertRepository transactionRepository,
            PasswordEncoder passwordEncoder) {

        this.compteRepository = compteRepository;

        this.transactionRepository =
                transactionRepository;

        this.passwordEncoder =
                passwordEncoder;
    }


    @Override
    public PaiementResponse effectuerPaiement(
            CreatePaiementRequest request) {

        // Recherche du compte client
        Compte compteClient =
                compteRepository.findByNumeroTelephone(
                        NumeroTelephone.of(
                                request.getNumeroClient()))
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Compte client introuvable."));


        // Recherche du compte marchand
        Compte compteMarchand =
                compteRepository.findByNumeroTelephone(
                        NumeroTelephone.of(
                                request.getNumeroMarchand()))
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Compte marchand introuvable."));


        // Vérifier que le client et le marchand
        // sont différents
        if (compteClient.getNumeroTelephone()
                .equals(compteMarchand.getNumeroTelephone())) {

            throw new IllegalArgumentException(
                    "Impossible d'effectuer un paiement sur le même compte.");
        }


        // Vérifier le type de transaction
        if (request.getTypeTransaction()
                != TypeTransaction.PAIEMENT_MARCHANT) {

            throw new IllegalArgumentException(
                    "Type de transaction invalide pour un paiement.");
        }


        // Vérifier que le compte client est actif
        if (compteClient.getStatut()
                != StatutCompte.ACTIF) {

            throw new CompteClientInactifException();
        }


        // Vérifier que le compte marchand est actif
        if (compteMarchand.getStatut()
                != StatutCompte.ACTIF) {

            throw new IllegalArgumentException(
                    "Le compte marchand est inactif.");
        }


        // Vérifier que le compte client est bien
        // un subscriber
        if (compteClient.getProfil()
                != Profil.SUBSCRIBER) {

            throw new IllegalArgumentException(
                    "Le compte utilisé pour le paiement doit être un client.");
        }


        // Vérifier que le compte destination
        // est bien un marchand
        if (compteMarchand.getProfil()
                != Profil.MARCHAND) {

            throw new IllegalArgumentException(
                    "Le compte destination doit être un marchand.");
        }


        // Vérifier le mot de passe du client
        compteClient.verifierMotDePasse(
                request.getMotDePasse(),
                passwordEncoder);


        // Création du montant
        Money montant =
                Money.of(request.getMontant());


        // Débiter le client
        compteClient.debiter(montant);


        // Créditer le marchand
        compteMarchand.crediter(montant);


        // Sauvegarder les comptes
        compteRepository.save(compteClient);

        compteRepository.save(compteMarchand);


        // Générer une référence
        ReferenceTransfert reference =
                ReferenceTransfert.generer();


        // Créer la transaction
        Transfert paiement =
                Transfert.creer(
                        reference,
                        request.getTypeTransaction(),
                        montant,
                        Money.zero(),
                        compteClient.getNumeroTelephone(),
                        compteMarchand.getNumeroTelephone(),
                        request.getMotif());


        // Sauvegarder la transaction
        paiement =
                transactionRepository.save(
                        paiement);


        // Retourner le reçu
        return new PaiementResponse(

                reference.getValue(),

                compteClient.getNumeroTelephone()
                        .getValue(),

                compteMarchand.getNumeroTelephone()
                        .getValue(),

                montant.toString(),

                compteClient.getSolde()
                        .toString(),

                paiement.getStatut().name(),

                paiement.getDateTransaction()
        );
    }
}