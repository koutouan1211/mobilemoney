package com.mobilemoney.account.domain.entity;

import com.mobilemoney.account.domain.enums.Profil;
import com.mobilemoney.account.domain.enums.StatutCompte;
import com.mobilemoney.account.domain.enums.TypePersonne;
import com.mobilemoney.account.domain.valueobject.Money;
import com.mobilemoney.account.domain.valueobject.NumeroTelephone;

import java.time.LocalDateTime;
import java.util.Objects;


public class Compte {

    private Long id;

    private String nom;

    private String prenom;

    private NumeroTelephone numeroTelephone;

    private Profil profil;

    private TypePersonne typePersonne;

    private Money solde;

    private Money plafond;

    private StatutCompte statut;

    private LocalDateTime dateCreation;

    private Compte() {
    }

    
    public static Compte creer(
            Profil profil,
            String nom,
            String prenom,
            NumeroTelephone numeroTelephone,
            TypePersonne typePersonne) {

        Objects.requireNonNull(profil, "Le profil est obligatoire.");

        return switch (profil) {
            case SUBSCRIBER ->
                    creerSubscriber(
                            nom,
                            prenom,
                            numeroTelephone,
                            typePersonne
                    );

            case AGENT ->
                    creerAgent(
                            nom,
                            prenom,
                            numeroTelephone,
                            typePersonne
                    );
        };
    }
    
    
    //cretion d'un compte suscriber
    public static Compte creerSubscriber(
            String nom,
            String prenom,
            NumeroTelephone numeroTelephone,
            TypePersonne typePersonne) {

        Objects.requireNonNull(nom);
        Objects.requireNonNull(prenom);
        Objects.requireNonNull(numeroTelephone);
        Objects.requireNonNull(typePersonne);

        if (typePersonne != TypePersonne.PERSONNE_PHYSIQUE) {
            throw new IllegalArgumentException(
                    "Un subscriber doit être une personne physique."
            );
        }

        Compte compte = new Compte();

        compte.nom = nom;
        compte.prenom = prenom;
        compte.numeroTelephone = numeroTelephone;
        compte.profil = Profil.SUBSCRIBER;
        compte.typePersonne = typePersonne;

        compte.solde = Money.zero();
        compte.plafond = Money.of("200000");

        compte.statut = StatutCompte.ACTIF;

        compte.dateCreation = LocalDateTime.now();

        return compte;
    }
    
    
    //creation d'un compte agent
    public static Compte creerAgent(
            String nom,
            String prenom,
            NumeroTelephone numeroTelephone,
            TypePersonne typePersonne) {

        Objects.requireNonNull(nom);
        Objects.requireNonNull(prenom);
        Objects.requireNonNull(numeroTelephone);
        Objects.requireNonNull(typePersonne);

        Compte compte = new Compte();

        compte.nom = nom;
        compte.prenom = prenom;
        compte.numeroTelephone = numeroTelephone;
        compte.profil = Profil.AGENT;
        compte.typePersonne = typePersonne;

        compte.solde = Money.zero();
        compte.plafond = Money.zero();

        compte.statut = StatutCompte.EN_ATTENTE;

        compte.dateCreation = LocalDateTime.now();

        return compte;
    }
    
    
    //reconstituer le compte 
    public static Compte reconstituer(
            Long id,
            String nom,
            String prenom,
            NumeroTelephone numeroTelephone,
            Profil profil,
            TypePersonne typePersonne,
            Money solde,
            Money plafond,
            StatutCompte statut,
            LocalDateTime dateCreation) {

        Compte compte = new Compte();

        compte.id = id;
        compte.nom = nom;
        compte.prenom = prenom;
        compte.numeroTelephone = numeroTelephone;
        compte.profil = profil;
        compte.typePersonne = typePersonne;
        compte.solde = solde;
        compte.plafond = plafond;
        compte.statut = statut;
        compte.dateCreation = dateCreation;

        return compte;
    }
    
    
    public void crediter(Money montant) {

        verifierCompteActif();

        if (montant == null) {
            throw new IllegalArgumentException(
                    "Le montant est obligatoire.");
        }

        Money nouveauSolde = this.solde.add(montant);

        if (nouveauSolde.isGreaterThan(this.plafond)) {
            throw new IllegalArgumentException(
                    "Le plafond du compte serait dépassé.");
        }

        this.solde = nouveauSolde;
    }
    
    
    
    public void debiter(Money montant) {

    	 verifierCompteActif();
    	
        if (montant == null) {
            throw new IllegalArgumentException("Le montant est obligatoire.");
        }

        if (this.solde.isLessThan(montant)) {
            throw new IllegalArgumentException(
                    "Solde insuffisant.");
        }

        this.solde = this.solde.subtract(montant);
    }
   
    // le compte verifie son statut seule 
    
    private void verifierCompteActif() {

        if (this.statut != StatutCompte.ACTIF) {
            throw new IllegalArgumentException(
                    "Le compte n'est pas actif.");
        }
    }
    
    
  //getter
	public Long getId() {
		return id;
	}


	public String getNom() {
		return nom;
	}


	public String getPrenom() {
		return prenom;
	}


	public NumeroTelephone getNumeroTelephone() {
		return numeroTelephone;
	}


	public Profil getProfil() {
		return profil;
	}


	public TypePersonne getTypePersonne() {
		return typePersonne;
	}


	public Money getSolde() {
		return solde;
	}


	public Money getPlafond() {
		return plafond;
	}


	public StatutCompte getStatut() {
		return statut;
	}


	public LocalDateTime getDateCreation() {
		return dateCreation;
	}
    

    
}