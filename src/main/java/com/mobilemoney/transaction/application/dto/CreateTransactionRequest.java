package com.mobilemoney.transaction.application.dto;

import com.mobilemoney.transaction.domain.enums.TypeTransaction;

public class CreateTransactionRequest {

	
	private String compteSource;

    private String compteDestination;

    private Long montant;

    private TypeTransaction typeTransaction;

    private String motif;
    
    public CreateTransactionRequest() {
    }

	public String getCompteSource() {
		return compteSource;
	}

	public void setCompteSource(String compteSource) {
		this.compteSource = compteSource;
	}

	public String getCompteDestination() {
		return compteDestination;
	}

	public void setCompteDestination(String compteDestination) {
		this.compteDestination = compteDestination;
	}

	public Long getMontant() {
		return montant;
	}

	public void setMontant(Long montant) {
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
