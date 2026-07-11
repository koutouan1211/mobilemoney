package com.mobilemoney.account.domain.repository;

import com.mobilemoney.account.domain.entity.Compte;
import com.mobilemoney.account.domain.valueobject.NumeroTelephone;

import java.util.Optional;

public interface CompteRepository {

	//sauvegarder le compte 
    Compte save(Compte compte);

    //compte peut ne pas exister
    Optional<Compte> findByNumeroTelephone(NumeroTelephone numeroTelephone);

    boolean existsByNumeroTelephone(NumeroTelephone numeroTelephone);

}