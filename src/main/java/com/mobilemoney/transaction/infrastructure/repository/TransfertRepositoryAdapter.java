package com.mobilemoney.transaction.infrastructure.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.mobilemoney.transaction.domain.entity.Transfert;
import com.mobilemoney.transaction.domain.repository.TransfertRepository;
import com.mobilemoney.transaction.domain.valueobject.ReferenceTransfert;
import com.mobilemoney.transaction.infrastructure.entity.TransfertEntity;
import com.mobilemoney.transaction.infrastructure.mapper.TransfertMapper;

@Repository
public class TransfertRepositoryAdapter  implements TransfertRepository {

    private final JpaTransfertRepository jpaRepository;
    private final TransfertMapper mapper;

    public TransfertRepositoryAdapter(
            JpaTransfertRepository jpaRepository,
            TransfertMapper mapper) {

        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Transfert save(Transfert transaction) {

        TransfertEntity entity =
                mapper.toEntity(transaction);

        TransfertEntity saved =
                jpaRepository.save(entity);

        return mapper.toDomain(saved);
    }
    
    @Override
    public Optional<Transfert> findByReference(
            ReferenceTransfert reference) {

        return jpaRepository
                .findByReferenceTransaction(reference.getValue())
                .map(mapper::toDomain);
    }

}