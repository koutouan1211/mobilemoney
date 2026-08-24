package com.mobilemoney.transaction.application.dto;

import java.time.LocalDateTime;

public class PaiementResponse {

    private String reference;

    private String numeroClient;

    private String numeroMarchand;

    private String montant;

    private String nouveauSoldeClient;

    private String statut;

    private LocalDateTime dateTransaction;


    public PaiementResponse(
            String reference,
            String numeroClient,
            String numeroMarchand,
            String montant,
            String nouveauSoldeClient,
            String statut,
            LocalDateTime dateTransaction) {

        this.reference = reference;
        this.numeroClient = numeroClient;
        this.numeroMarchand = numeroMarchand;
        this.montant = montant;
        this.nouveauSoldeClient = nouveauSoldeClient;
        this.statut = statut;
        this.dateTransaction = dateTransaction;
    }


    public String getReference() {
        return reference;
    }

    public String getNumeroClient() {
        return numeroClient;
    }

    public String getNumeroMarchand() {
        return numeroMarchand;
    }

    public String getMontant() {
        return montant;
    }

    public String getNouveauSoldeClient() {
        return nouveauSoldeClient;
    }

    public String getStatut() {
        return statut;
    }

    public LocalDateTime getDateTransaction() {
        return dateTransaction;
    }
}