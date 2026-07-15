package com.mobilemoney.transaction.infrastructure.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.mobilemoney.transaction.domain.entity.Transaction;
import com.mobilemoney.transaction.domain.repository.TransactionRepository;
import com.mobilemoney.transaction.domain.valueobject.ReferenceTransaction;
import com.mobilemoney.transaction.infrastructure.entity.TransactionEntity;
import com.mobilemoney.transaction.infrastructure.mapper.TransactionMapper;

@Repository
public class TransactionRepositoryAdapter  implements TransactionRepository {

    private final JpaTransactionRepository jpaRepository;
    private final TransactionMapper mapper;

    public TransactionRepositoryAdapter(
            JpaTransactionRepository jpaRepository,
            TransactionMapper mapper) {

        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Transaction save(Transaction transaction) {

        TransactionEntity entity =
                mapper.toEntity(transaction);

        TransactionEntity saved =
                jpaRepository.save(entity);

        return mapper.toDomain(saved);
    }
    
    @Override
    public Optional<Transaction> findByReference(
            ReferenceTransaction reference) {

        return jpaRepository
                .findByReferenceTransaction(reference.getValue())
                .map(mapper::toDomain);
    }

}