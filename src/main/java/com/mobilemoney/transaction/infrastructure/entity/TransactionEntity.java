package com.mobilemoney.transaction.infrastructure.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.mobilemoney.transaction.domain.enums.StatutTransaction;
import com.mobilemoney.transaction.domain.enums.TypeTransaction;

import jakarta.persistence.*;

@Entity
@Table(name = "transaction")
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reference_transaction", nullable = false, unique = true)
    private String referenceTransaction;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypeTransaction typeTransaction;

    @Column(name = "montant", nullable = false)
    private BigDecimal montant;

    @Column(name = "compte_source", nullable = false)
    private String compteSource;

    @Column(name = "compte_destination", nullable = false)
    private String compteDestination;

    @Column(name = "date_transaction", nullable = false)
    private LocalDateTime dateTransaction;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutTransaction statut;

    @Column(length = 255)
    private String motif;

    public TransactionEntity() {
    }

    // Getters et Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getReferenceTransaction() {
		return referenceTransaction;
	}

	public void setReferenceTransaction(String referenceTransaction) {
		this.referenceTransaction = referenceTransaction;
	}

	public TypeTransaction getTypeTransaction() {
		return typeTransaction;
	}

	public void setTypeTransaction(TypeTransaction typeTransaction) {
		this.typeTransaction = typeTransaction;
	}

	public BigDecimal getMontant() {
		return montant;
	}

	public void setMontant(BigDecimal montant) {
		this.montant = montant;
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