package com.mobilemoney.transaction.domain.repository;

import java.util.Optional;

import com.mobilemoney.transaction.domain.entity.Transfert;
import com.mobilemoney.transaction.domain.valueobject.ReferenceTransfert;

public interface TransfertRepository {

	 Transfert save(Transfert transaction);

	    Optional<Transfert> findByReference(
	            ReferenceTransfert reference);
}

