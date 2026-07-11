package com.mobilemoney.account.infrastructure.repository;

import com.mobilemoney.account.domain.entity.Compte;
import com.mobilemoney.account.domain.repository.CompteRepository;
import com.mobilemoney.account.domain.valueobject.NumeroTelephone;
import com.mobilemoney.account.infrastructure.entity.CompteEntity;
import com.mobilemoney.account.infrastructure.mapper.CompteMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class CompteRepositoryAdapter implements CompteRepository {

    private final JpaCompteRepository jpaRepository;
    private final CompteMapper mapper;

    public CompteRepositoryAdapter(JpaCompteRepository jpaRepository,
                                   CompteMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Compte save(Compte compte) {

        CompteEntity entity = mapper.toEntity(compte);

        CompteEntity saved = jpaRepository.save(entity);

        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Compte> findByNumeroTelephone(NumeroTelephone numeroTelephone) {

        return jpaRepository
                .findByNumeroTelephone(numeroTelephone.getValue())
                .map(mapper::toDomain);
    }

    @Override
    public boolean existsByNumeroTelephone(NumeroTelephone numeroTelephone) {

        return jpaRepository
                .existsByNumeroTelephone(numeroTelephone.getValue());
    }
}