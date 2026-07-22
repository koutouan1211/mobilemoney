package com.mobilemoney.transaction.application.service;

import com.mobilemoney.account.domain.entity.Compte;
import com.mobilemoney.account.domain.repository.CompteRepository;
import com.mobilemoney.account.domain.valueobject.Money;
import com.mobilemoney.account.domain.valueobject.NumeroTelephone;
import com.mobilemoney.transaction.application.dto.CreateDepotRequest;
import com.mobilemoney.transaction.application.dto.DepotResponse;
import com.mobilemoney.transaction.application.usecase.CreateDepotUseCase;
import com.mobilemoney.transaction.domain.entity.Transfert;
import com.mobilemoney.transaction.domain.enums.TypeTransaction;
import com.mobilemoney.transaction.domain.repository.TransfertRepository;
import com.mobilemoney.transaction.domain.valueobject.ReferenceTransfert;


public class CreateDepotService implements CreateDepotUseCase{

	 private final CompteRepository compteRepository;
	    private final TransfertRepository transactionRepository;

	    public CreateDepotService(
	            CompteRepository compteRepository,
	            TransfertRepository transactionRepository) {

	        this.compteRepository = compteRepository;
	        this.transactionRepository = transactionRepository;
	    }

	    
	    @Override
	    public DepotResponse effectuerDepot(CreateDepotRequest request) {

	    	// Recherche du compte Agent
	    	Compte compteAgent =
	    	        compteRepository.findByNumeroTelephone(
	    	                NumeroTelephone.of(request.getNumeroAgent()))
	    	        .orElseThrow(() ->
	    	                new IllegalArgumentException(
	    	                        "Compte agent introuvable."));
	    	
	    	// Recherche du compte Client
	    	Compte compteClient =
	    	        compteRepository.findByNumeroTelephone(
	    	                NumeroTelephone.of(request.getNumeroClient()))
	    	        .orElseThrow(() ->
	    	                new IllegalArgumentException(
	    	                        "Compte client introuvable."));
		    
	    	//interdire a un agent de se faire le depot lui meme
	    	if (compteAgent.getNumeroTelephone()
	    	        .equals(compteClient.getNumeroTelephone())) {

	    	    throw new IllegalArgumentException(
	    	            "Impossible d'effectuer un dépôt sur le même compte.");
	    	}
	    	
	    	//autoriser seulement le type depot
	    	if (request.getTypeTransaction() != TypeTransaction.DEPOT) {

	    	    throw new IllegalArgumentException(
	    	            "Type de transaction invalide pour un dépôt.");
	    	}

	    	// on recupere le montant 
	    	Money montant =
	    	        Money.of(request.getMontant());
	    	
	    	//on debite et on credite le montant
	    	compteAgent.debiter(montant);

	    	compteClient.crediter(montant);
	    	
	    	//sauvegarder les deux comptes
	    	compteRepository.save(compteAgent);

	    	compteRepository.save(compteClient);
	    	
	    	
	    	// créer la transaction
	    	ReferenceTransfert reference =
	    	        ReferenceTransfert.generer();

	    	Transfert depot =
	    	        Transfert.creer(
	    	                reference,
	    	                request.getTypeTransaction(),
	    	                montant,
	    	                Money.zero(),
	    	                compteAgent.getNumeroTelephone(),
	    	                compteClient.getNumeroTelephone(),
	    	                request.getMotif());
	    	
	    	//sauvegarder la transaction
	    	depot = transactionRepository.save(depot);
	    	
	    	return new DepotResponse(
	    	        reference.getValue(),
	    	        compteAgent.getNumeroTelephone().getValue(),
	    	        compteClient.getNumeroTelephone().getValue(),
	    	        montant.toString(),
	    	        compteClient.getSolde().toString(),
	    	        depot.getStatut().name(),
	    	        depot.getDateTransaction()
	    	);
	    }
	    
}
