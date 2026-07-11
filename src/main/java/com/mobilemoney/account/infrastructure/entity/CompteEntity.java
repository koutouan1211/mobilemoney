package com.mobilemoney.account.infrastructure.entity;

import com.mobilemoney.account.domain.enums.Profil;
import com.mobilemoney.account.domain.enums.StatutCompte;
import com.mobilemoney.account.domain.enums.TypePersonne;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "compte")
public class CompteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String prenom;

    @Column(nullable = false, unique = true)
    private String numeroTelephone;

    @Enumerated(EnumType.STRING)
    private Profil profil;

    @Enumerated(EnumType.STRING)
    private TypePersonne typePersonne;

    @Column(nullable = false)
    private BigDecimal solde;

    @Column(nullable = false)
    private BigDecimal plafond;

    @Enumerated(EnumType.STRING)
    private StatutCompte statut;

    @Column(nullable = false)
    private LocalDateTime dateCreation;

    
    // Getters et Setters
    public CompteEntity() {
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getNumeroTelephone() {
		return numeroTelephone;
	}

	public void setNumeroTelephone(String numeroTelephone) {
		this.numeroTelephone = numeroTelephone;
	}

	public Profil getProfil() {
		return profil;
	}

	public void setProfil(Profil profil) {
		this.profil = profil;
	}

	public TypePersonne getTypePersonne() {
		return typePersonne;
	}

	public void setTypePersonne(TypePersonne typePersonne) {
		this.typePersonne = typePersonne;
	}

	public BigDecimal getSolde() {
		return solde;
	}

	public void setSolde(BigDecimal solde) {
		this.solde = solde;
	}

	public BigDecimal getPlafond() {
		return plafond;
	}

	public void setPlafond(BigDecimal plafond) {
		this.plafond = plafond;
	}

	public StatutCompte getStatut() {
		return statut;
	}

	public void setStatut(StatutCompte statut) {
		this.statut = statut;
	}

	public LocalDateTime getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(LocalDateTime dateCreation) {
		this.dateCreation = dateCreation;
	}
    
    
    
}