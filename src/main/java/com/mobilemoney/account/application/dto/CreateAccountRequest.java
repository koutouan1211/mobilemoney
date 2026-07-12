package com.mobilemoney.account.application.dto;

import com.mobilemoney.account.domain.enums.Profil;
import com.mobilemoney.account.domain.enums.TypePersonne;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateAccountRequest {

    @NotBlank(message = "Le nom est obligatoire.")
    private String nom;

    @NotBlank(message = "Le prénom est obligatoire.")
    private String prenom;

    @NotBlank(message = "Le numéro de téléphone est obligatoire.")
    private String numeroTelephone;

    @NotNull(message = "Le profil est obligatoire.")
    private Profil profil;

    @NotNull(message = "Le type de personne est obligatoire.")
    private TypePersonne typePersonne;

    public CreateAccountRequest() {
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
    
    
}