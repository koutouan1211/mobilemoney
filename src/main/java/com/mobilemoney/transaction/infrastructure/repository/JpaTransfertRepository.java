package com.mobilemoney.transaction.infrastructure.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mobilemoney.transaction.infrastructure.entity.TransfertEntity;

public interface JpaTransfertRepository
        extends JpaRepository<TransfertEntity, Long> {

    Optional<TransfertEntity> findByReferenceTransaction(String referenceTransaction);

    List<TransfertEntity> findByCompteSourceOrCompteDestination(
            String compteSource,
            String compteDestination);
}