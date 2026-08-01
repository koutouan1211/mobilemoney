package com.mobilemoney.transaction.application.dto;

import java.time.LocalDateTime;

public class DepotHistoryResponse {

    private final String reference;

    private final String numeroAgent;

    private final String numeroClient;

    private final String montant;

    private final String statut;

    private final LocalDateTime dateOperation;

    public DepotHistoryResponse(
            String reference,
            String numeroAgent,
            String numeroClient,
            String montant,
            String statut,
            LocalDateTime dateOperation) {

        this.reference = reference;
        this.numeroAgent = numeroAgent;
        this.numeroClient = numeroClient;
        this.montant = montant;
        this.statut = statut;
        this.dateOperation = dateOperation;
    }

    public String getReference() {
        return reference;
    }

    public String getNumeroAgent() {
        return numeroAgent;
    }

    public String getNumeroClient() {
        return numeroClient;
    }

    public String getMontant() {
        return montant;
    }

    public String getStatut() {
        return statut;
    }

    public LocalDateTime getDateOperation() {
        return dateOperation;
    }
}