package com.mobilemoney.transaction.domain.entity;

import java.time.LocalDateTime;

import com.mobilemoney.account.domain.valueobject.Money;
import com.mobilemoney.account.domain.valueobject.NumeroTelephone;
import com.mobilemoney.transaction.domain.enums.StatutTransaction;
import com.mobilemoney.transaction.domain.enums.TypeTransaction;
import com.mobilemoney.transaction.domain.valueobject.ReferenceTransfert;


public class Transfert {

    private Long id;

    private ReferenceTransfert reference;

    private TypeTransaction typeTransaction;

    private Money montant;

    private NumeroTelephone compteSource;

    private NumeroTelephone compteDestination;

    private LocalDateTime dateTransaction;

    private StatutTransaction statut;

    private String motif;

    private Transfert() {}
    
    
    
    public static Transfert creer(
            ReferenceTransfert reference,
            TypeTransaction typeTransaction,
            Money montant,
            NumeroTelephone compteSource,
            NumeroTelephone compteDestination,
            String motif) {

        Transfert transaction = new Transfert();

        transaction.reference = reference;
        transaction.typeTransaction = typeTransaction;
        transaction.montant = montant;
        transaction.compteSource = compteSource;
        transaction.compteDestination = compteDestination;
        transaction.motif = motif;

        transaction.dateTransaction = LocalDateTime.now();

        transaction.statut = StatutTransaction.EN_ATTENTE;

        return transaction;
    }
    

    public static Transfert reconstruire(
            Long id,
            ReferenceTransfert reference,
            TypeTransaction typeTransaction,
            Money montant,
            NumeroTelephone compteSource,
            NumeroTelephone compteDestination,
            LocalDateTime dateTransaction,
            StatutTransaction statut,
            String motif) {

        Transfert transaction = new Transfert();

        transaction.id = id;
        transaction.reference = reference;
        transaction.typeTransaction = typeTransaction;
        transaction.montant = montant;
        transaction.compteSource = compteSource;
        transaction.compteDestination = compteDestination;
        transaction.dateTransaction = dateTransaction;
        transaction.statut = statut;
        transaction.motif = motif;

        return transaction;
    }
    

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ReferenceTransfert getReference() {
		return reference;
	}

	public void setReference(ReferenceTransfert reference) {
		this.reference = reference;
	}

	public TypeTransaction getTypeTransaction() {
		return typeTransaction;
	}

	public void setTypeTransaction(TypeTransaction typeTransaction) {
		this.typeTransaction = typeTransaction;
	}

	public Money getMontant() {
		return montant;
	}

	public void setMontant(Money montant) {
		this.montant = montant;
	}

	public NumeroTelephone getCompteSource() {
		return compteSource;
	}

	public void setCompteSource(NumeroTelephone compteSource) {
		this.compteSource = compteSource;
	}

	public NumeroTelephone getCompteDestination() {
		return compteDestination;
	}

	public void setCompteDestination(NumeroTelephone compteDestination) {
		this.compteDestination = compteDestination;
	}

	public LocalDateTime getDateTransaction() {
		return dateTransaction;
	}

	public void setDateTransaction(LocalDateTime dateTransaction) {
		this.dateTransaction = dateTransaction;
	}

	public StatutTransaction getStatut() {
		return statut;
	}

	public void setStatut(StatutTransaction statut) {
		this.statut = statut;
	}

	public String getMotif() {
		return motif;
	}

	public void setMotif(String motif) {
		this.motif = motif;
	}
   
    
} 
