package com.mobilemoney.transaction.application.dto;

import com.mobilemoney.transaction.domain.enums.TypeTransaction;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class CreateTransfertRequest {

	@NotBlank(message = "Le compte source est obligatoire.")
	private String compteSource;

	@NotBlank(message = "Le compte destination est obligatoire.")
	private String compteDestination;

	@NotNull(message = "Le montant est obligatoire.")
	@Positive(message = "Le montant doit être supérieur à zéro.")
	private Long montant;

	@NotNull(message = "Le type de transaction est obligatoire.")
	private TypeTransaction typeTransaction;

	@NotBlank(message = "Le motif est obligatoire.")
	private String motif;
    
    public CreateTransfertRequest() {
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

	public static void main(String[] args) {
	
	}
    
    
}
