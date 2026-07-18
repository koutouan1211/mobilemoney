package com.mobilemoney.transaction.application.dto;

import java.time.LocalDateTime;

public class RecuTransfertResponse {

    private String reference;
    private String montant;
    private String frais;
    private String nouveauSolde;
    private String statut;
    private LocalDateTime dateTransaction;

    public RecuTransfertResponse() {
    }

    public RecuTransfertResponse(
            String reference,
            String montant,
            String frais,
            String nouveauSolde,
            String statut,
            LocalDateTime dateTransaction) {

        this.reference = reference;
        this.montant = montant;
        this.frais = frais;
        this.nouveauSolde = nouveauSolde;
        this.statut = statut;
        this.nouveauSolde = nouveauSolde;
        this.dateTransaction = dateTransaction;
    }

    
 // Getters et Setters
	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getMontant() {
		return montant;
	}

	public void setMontant(String montant) {
		this.montant = montant;
	}

	public String getFrais() {
		return frais;
	}

	public void setFrais(String frais) {
		this.frais = frais;
	}

	public String getNouveauSolde() {
		return nouveauSolde;
	}

	public void setNouveauSolde(String nouveauSolde) {
		this.nouveauSolde = nouveauSolde;
	}

	public String getStatut() {
		return statut;
	}

	public void setStatut(String statut) {
		this.statut = statut;
	}

	public LocalDateTime getDateTransaction() {
		return dateTransaction;
	}

	public void setDateTransaction(LocalDateTime dateTransaction) {
		this.dateTransaction = dateTransaction;
	}

    
    
    
}