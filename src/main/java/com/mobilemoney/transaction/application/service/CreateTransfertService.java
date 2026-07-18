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
import com.mobilemoney.transaction.domain.enums.TypeTransaction;
import com.mobilemoney.transaction.domain.repository.TransfertRepository;
import com.mobilemoney.transaction.domain.service.CalculFraisTransfertService;
import com.mobilemoney.transaction.domain.valueobject.ReferenceTransfert;

@Service
public class CreateTransfertService implements CreateTransfertUseCase {

	    private final CompteRepository compteRepository;
	    private final TransfertRepository transactionRepository;
	    private final CalculFraisTransfertService calculFraisTransfertService;

	    public CreateTransfertService(
	            CompteRepository compteRepository,
	            TransfertRepository transactionRepository,
	            CalculFraisTransfertService calculFraisTransfertService) {

	        this.compteRepository = compteRepository;
	        this.transactionRepository = transactionRepository;
	        this.calculFraisTransfertService=calculFraisTransfertService;
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
    	
    	
    	//interdit au meme numero de s'envoyer de l'argent
    	if (compteSource.getNumeroTelephone()
    	        .equals(compteDestination.getNumeroTelephone())) {

    	    throw new IllegalArgumentException(
    	            "Impossible d'effectuer un transfert vers le même compte.");
    	}
    	
    	//permet de choisir uniquement que le transfert comme transation
    	if (request.getTypeTransaction() != TypeTransaction.TRANSFERT_DOMESTIQUE
    	        && request.getTypeTransaction() != TypeTransaction.TRANSFERT_INTERNATIONAL) {

    	    throw new IllegalArgumentException(
    	            "Type de transaction invalide pour un transfert.");
    	}
    	
    	Money montant =
    	        Money.of(request.getMontant());

    	Money frais =
    	        calculFraisTransfertService.calculer(montant);

    	Money montantTotal =
    	        montant.add(frais);

    	compteSource.debiter(montantTotal);

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
    	                frais,
    	                compteSource.getNumeroTelephone(),
    	                compteDestination.getNumeroTelephone(),
    	                request.getMotif());
    	
    	transaction = transactionRepository.save(transaction);
    	
    	return new TransfertResponse(
    	        reference.getValue(),
    	        "Transfert effectué avec succès",
    	        montant.toString(),
    	        frais.toString(),
    	        compteSource.getSolde().toString(),
    	        transaction.getStatut().name(),
    	        transaction.getDateTransaction()
    	);
    }

    
}