package com.mobilemoney.transaction.infrastructure.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mobilemoney.transaction.infrastructure.entity.TransactionEntity;

public interface JpaTransactionRepository
        extends JpaRepository<TransactionEntity, Long> {

    Optional<TransactionEntity> findByReferenceTransaction(String referenceTransaction);

}