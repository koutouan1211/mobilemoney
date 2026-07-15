package com.mobilemoney.transaction.domain.repository;

import java.util.Optional;

import com.mobilemoney.transaction.domain.entity.Transaction;
import com.mobilemoney.transaction.domain.valueobject.ReferenceTransaction;

public interface TransactionRepository {

	 Transaction save(Transaction transaction);

	    Optional<Transaction> findByReference(
	            ReferenceTransaction reference);
}

