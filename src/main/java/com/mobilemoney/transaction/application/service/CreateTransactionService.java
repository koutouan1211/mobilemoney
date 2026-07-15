package com.mobilemoney.transaction.application.service;

import org.springframework.stereotype.Service;

import com.mobilemoney.account.domain.entity.Compte;
import com.mobilemoney.account.domain.repository.CompteRepository;
import com.mobilemoney.account.domain.valueobject.Money;
import com.mobilemoney.account.domain.valueobject.NumeroTelephone;
import com.mobilemoney.transaction.application.dto.CreateTransactionRequest;
import com.mobilemoney.transaction.application.dto.TransactionResponse;
import com.mobilemoney.transaction.application.usecase.CreateTransactionUseCase;
import com.mobilemoney.transaction.domain.entity.Transaction;
import com.mobilemoney.transaction.domain.repository.TransactionRepository;
import com.mobilemoney.transaction.domain.valueobject.ReferenceTransaction;

@Service
public class CreateTransactionService implements CreateTransactionUseCase {

	 private final CompteRepository compteRepository;
	    private final TransactionRepository transactionRepository;

	    public CreateTransactionService(
	            CompteRepository compteRepository,
	            TransactionRepository transactionRepository) {

	        this.compteRepository = compteRepository;
	        this.transactionRepository = transactionRepository;
	    }
	    
	    //operation de transfert 
	
    @Override
    public TransactionResponse effectuerTransaction(CreateTransactionRequest request) {

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
    	ReferenceTransaction reference =
    	        ReferenceTransaction.generer();

    	Transaction transaction =
    	        Transaction.creer(
    	                reference,
    	                request.getTypeTransaction(),
    	                montant,
    	                compteSource.getNumeroTelephone(),
    	                compteDestination.getNumeroTelephone(),
    	                request.getMotif());
    	
    	transaction = transactionRepository.save(transaction);
    	
    	 return new TransactionResponse(
    	            transaction.getReference().getValue(),
    	            "Transaction effectuée avec succès.");
    }

}