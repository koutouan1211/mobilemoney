package com.mobilemoney.transaction.application.dto;

import java.time.LocalDateTime;

public class TransactionHistoryResponse {

	   private String reference;

	    private String typeTransaction;

	    private String compteSource;

	    private String compteDestination;

	    private String montant;

	    private String frais;

	    private String statut;

	    private String motif;

	    private LocalDateTime dateTransaction;


	    public TransactionHistoryResponse(
	            String reference,
	            String typeTransaction,
	            String compteSource,
	            String compteDestination,
	            String montant,
	            String frais,
	            String statut,
	            String motif,
	            LocalDateTime dateTransaction) {

	        this.reference = reference;
	        this.typeTransaction = typeTransaction;
	        this.compteSource = compteSource;
	        this.compteDestination = compteDestination;
	        this.montant = montant;
	        this.frais = frais;
	        this.statut = statut;
	        this.motif = motif;
	        this.dateTransaction = dateTransaction;
	    }


		public String getReference() {
			return reference;
		}


		public String getTypeTransaction() {
			return typeTransaction;
		}


		public String getCompteSource() {
			return compteSource;
		}


		public String getCompteDestination() {
			return compteDestination;
		}


		public String getMontant() {
			return montant;
		}


		public String getFrais() {
			return frais;
		}


		public String getStatut() {
			return statut;
		}


		public String getMotif() {
			return motif;
		}


		public LocalDateTime getDateTransaction() {
			return dateTransaction;
		}

	    
}
