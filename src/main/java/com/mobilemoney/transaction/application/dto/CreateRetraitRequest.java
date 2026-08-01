package com.mobilemoney.transaction.application.dto;

import java.math.BigDecimal;

import com.mobilemoney.transaction.domain.enums.TypeTransaction;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateRetraitRequest {

    @NotBlank(message = "Le numéro de l'agent est obligatoire.")
    private String numeroAgent;

    @NotBlank(message = "Le numéro du client est obligatoire.")
    private String numeroClient;

    @NotNull(message = "Le montant est obligatoire.")
    @DecimalMin(value = "100", message = "Le montant minimum est de 100 FCFA.")
    private BigDecimal montant;

    @NotNull(message = "Le type de transaction est obligatoire.")
    private TypeTransaction typeTransaction;

    @NotBlank(message = "Le motif est obligatoire.")
    private String motif;

    public CreateRetraitRequest() {
    }

    // Getters & Setters

    public String getNumeroAgent() {
        return numeroAgent;
    }

    public void setNumeroAgent(String numeroAgent) {
        this.numeroAgent = numeroAgent;
    }

    public String getNumeroClient() {
        return numeroClient;
    }

    public void setNumeroClient(String numeroClient) {
        this.numeroClient = numeroClient;
    }

    public BigDecimal getMontant() {
        return montant;
    }

    public void setMontant(BigDecimal montant) {
        this.montant = montant;
    }

    public TypeTransaction getTypeTransaction() {
        return typeTransaction;
    }

    public void setTypeTransaction(TypeTransaction typeTransaction) {
        this.typeTransaction = typeTransaction;
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

}