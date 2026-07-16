package com.mobilemoney.transaction.application.service;

import org.springframework.stereotype.Service;

import com.mobilemoney.account.domain.entity.Compte;
import com.mobilemoney.account.domain.repository.CompteRepository;
import com.mobilemoney.account.domain.valueobject.Money;
import com.mobilemoney.account.domain.valueobject.NumeroTelephone;
import com.mobilemoney.transaction.application.dto.CreateTransfertRequest;
import com.mobilemoney.transaction.application.dto.TransfertResponse;
import com.mobilemoney.transaction.application.usecase.CreateTransfertUseCase;
import com.mobilemoney.transaction.domain.entity.Transfert;
import com.mobilemoney.transaction.domain.repository.TransfertRepository;
import com.mobilemoney.transaction.domain.valueobject.ReferenceTransfert;

@Service
public class CreateTransfertService implements CreateTransfertUseCase {

	 private final CompteRepository compteRepository;
	    private final TransfertRepository transactionRepository;

	    public CreateTransfertService(
	            CompteRepository compteRepository,
	            TransfertRepository transactionRepository) {

	        this.compteRepository = compteRepository;
	        this.transactionRepository = transactionRepository;
	    }
	    
	    //operation de transfert 
	
    @Override
    public TransfertResponse effectuerTransaction(CreateTransfertRequest request) {

    	Compte compteSource =
    	        compteRepository.findByNumeroTelephone(
    	                NumeroTelephone.of(
    	                        request.getCompteSource()))
    	        .orElseThrow(() ->
    	                new IllegalArgumentException(
    	                        "Compte source introuvable."));
    	
    	
    	Compte compteDestination =
    	        compteRepository.findByNumeroTelephone(
    	                NumeroTelephone.of(
    	                        request.getCompteDestination()))
    	        .orElseThrow(() ->
    	                new IllegalArgumentException(
    	                        "Compte destination introuvable."));
    	
    	Money montant =
    	        Money.of(request.getMontant()); 
    	
    	
    	compteSource.debiter(montant);

    	compteDestination.crediter(montant);
    	
    	
    	compteRepository.save(compteSource);

    	compteRepository.save(compteDestination);
    	
    	
    //creation de la transaction
    	ReferenceTransfert reference =
    	        ReferenceTransfert.generer();

    	Transfert transaction =
    	        Transfert.creer(
    	                reference,
    	                request.getTypeTransaction(),
    	                montant,
    	                compteSource.getNumeroTelephone(),
    	                compteDestination.getNumeroTelephone(),
    	                request.getMotif());
    	
    	transaction = transactionRepository.save(transaction);
    	
    	 return new TransfertResponse(
    	            transaction.getReference().getValue(),
    	            "Transaction effectuée avec succès.");
    }

}