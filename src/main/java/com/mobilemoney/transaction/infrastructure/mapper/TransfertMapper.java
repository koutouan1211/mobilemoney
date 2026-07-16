package com.mobilemoney.transaction.infrastructure.mapper;

import org.springframework.stereotype.Component;

import com.mobilemoney.account.domain.valueobject.Money;
import com.mobilemoney.account.domain.valueobject.NumeroTelephone;
import com.mobilemoney.transaction.domain.entity.Transfert;
import com.mobilemoney.transaction.domain.valueobject.ReferenceTransfert;
import com.mobilemoney.transaction.infrastructure.entity.TransfertEntity;

@Component
public class TransfertMapper {

	
	public TransfertEntity toEntity(Transfert transaction) {

	    TransfertEntity entity = new TransfertEntity();

	    entity.setId(transaction.getId());

	    entity.setReferenceTransaction(
	            transaction.getReference().getValue());

	    entity.setTypeTransaction(
	            transaction.getTypeTransaction());

	    entity.setMontant(
	            transaction.getMontant().getAmount());

	    entity.setCompteSource(
	            transaction.getCompteSource().getValue());

	    entity.setCompteDestination(
	            transaction.getCompteDestination().getValue());

	    entity.setDateTransaction(
	            transaction.getDateTransaction());

	    entity.setStatut(
	            transaction.getStatut());

	    entity.setMotif(
	            transaction.getMotif());

	    return entity;
	}
	
	//reconstruction
	
	public Transfert toDomain(TransfertEntity entity) {

	    return Transfert.reconstruire(

	            entity.getId(),

	            ReferenceTransfert.of(
	                    entity.getReferenceTransaction()),

	            entity.getTypeTransaction(),

	            Money.of(
	                    entity.getMontant()),

	            NumeroTelephone.of(
	                    entity.getCompteSource()),

	            NumeroTelephone.of(
	                    entity.getCompteDestination()),

	            entity.getDateTransaction(),

	            entity.getStatut(),

	            entity.getMotif()
	    );
	}
}