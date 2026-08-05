package com.mobilemoney.account.infrastructure.mapper;

import org.springframework.stereotype.Component;

import com.mobilemoney.account.domain.entity.Compte;
import com.mobilemoney.account.domain.valueobject.Money;
import com.mobilemoney.account.domain.valueobject.MotDePasse;
import com.mobilemoney.account.domain.valueobject.NumeroTelephone;
import com.mobilemoney.account.infrastructure.entity.CompteEntity;

@Component
public class CompteMapper {

//traduit le domain vers la base de donnée(DBB)
    public CompteEntity toEntity(Compte compte) {

        CompteEntity entity = new CompteEntity();

        entity.setId(compte.getId());
        entity.setNom(compte.getNom());
        entity.setPrenom(compte.getPrenom());

        entity.setNumeroTelephone(
                compte.getNumeroTelephone().getValue());

        entity.setMotDePasse(
                compte.getMotDePasse().getValeur());
        
        entity.setProfil(compte.getProfil());

        entity.setTypePersonne(
                compte.getTypePersonne());

        entity.setSolde(
                compte.getSolde().getAmount());

        entity.setPlafond(
                compte.getPlafond().getAmount());

        entity.setStatut(compte.getStatut());

        entity.setDateCreation(
                compte.getDateCreation());

        return entity;
    }

    //traduit la base de donnée vers le domain
    public Compte toDomain(CompteEntity entity) {

        return Compte.reconstituer(

                entity.getId(),

                entity.getNom(),

                entity.getPrenom(),

                NumeroTelephone.of(
                        entity.getNumeroTelephone()),

                MotDePasse.depuisHash(
                        entity.getMotDePasse()),
                
                entity.getProfil(),

                entity.getTypePersonne(),

                Money.of(entity.getSolde()),

                Money.of(entity.getPlafond()),

                entity.getStatut(),

                entity.getDateCreation()

        );
    }
}
