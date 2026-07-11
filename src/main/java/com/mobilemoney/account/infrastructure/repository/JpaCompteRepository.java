package com.mobilemoney.account.infrastructure.repository;

import com.mobilemoney.account.infrastructure.entity.CompteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaCompteRepository extends JpaRepository<CompteEntity, Long> {

    Optional<CompteEntity> findByNumeroTelephone(String numeroTelephone);

    boolean existsByNumeroTelephone(String numeroTelephone);

}