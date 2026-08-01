package com.mobilemoney.transaction.application.service;

import com.mobilemoney.account.domain.entity.Compte;
import com.mobilemoney.account.domain.enums.StatutCompte;
import com.mobilemoney.account.domain.repository.CompteRepository;
import com.mobilemoney.account.domain.valueobject.Money;
import com.mobilemoney.account.domain.valueobject.NumeroTelephone;
import com.mobilemoney.common.exception.CompteAgentInactifException;
import com.mobilemoney.common.exception.CompteClientInactifException;
import com.mobilemoney.transaction.application.dto.CreateRetraitRequest;
import com.mobilemoney.transaction.application.dto.RetraitResponse;
import com.mobilemoney.transaction.domain.enums.TypeTransaction;
import com.mobilemoney.transaction.domain.repository.TransfertRepository;

public class CreateRetraitService {

	//création de constructeur et injections des classes 
	public final CompteRepository compteRepository;
	public final TransfertRepository transactionRepository;
	
	public CreateRetraitService(CompteRepository compteRepository,TransfertRepository transactionRepository) {
		this.compteRepository=compteRepository;
		this.transactionRepository=transactionRepository;
	}
	
	//la methode du use case 
	
	@Override
	public RetraitResponse effectuerRetrait(
	        CreateRetraitRequest request) {

		//recherche du compte de l'agent
		Compte compteAgent =
		        compteRepository.findByNumeroTelephone(
		                NumeroTelephone.of(
		                        request.getNumeroAgent()))
		        .orElseThrow(() ->
		                new IllegalArgumentException(
		                        "Compte agent introuvable."));
		
		//recherche du compte du client
		Compte compteClient = compteRepository.findByNumeroTelephone(NumeroTelephone.of(request.getNumeroClient()))
				.orElseThrow(()->  new IllegalArgumentException(
                        "Compte agent introuvable."));
		
		//l'agent ne peux pas s'envoyer de l'argent a lui meme(sur le meme numero) 
		if (compteAgent.getNumeroTelephone()
		        .equals(compteClient.getNumeroTelephone())) {

		    throw new IllegalArgumentException(
		            "Impossible d'effectuer un retrait sur le même compte.");
		}
		
		//on verifier le type de transaction
		if (request.getTypeTransaction() != TypeTransaction.RETRAIT) {

		    throw new IllegalArgumentException(
		            "Type de transaction invalide pour un retrait.");
		}
		
		
		//verifier le solde du client
				Money montant =
				        Money.of(request.getMontant());
		
		//verifie que le compte agent est actif
		if (compteAgent.getStatut() != StatutCompte.ACTIF) {
		    throw new CompteAgentInactifException();
		}
		
		//verife que le compte client est actif
		if (compteClient.getStatut() != StatutCompte.ACTIF) {
		    throw new CompteClientInactifException();
		}
		
		
		//debiter le compte du client 
		compteClient.debiter(montant);
	}

}
