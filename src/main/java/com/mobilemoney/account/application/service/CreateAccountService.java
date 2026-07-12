package com.mobilemoney.account.application.service;

import com.mobilemoney.account.application.dto.AccountResponse;
import com.mobilemoney.account.application.dto.CreateAccountRequest;
import com.mobilemoney.account.application.usecase.CreateAccountUseCase;
import com.mobilemoney.account.domain.entity.Compte;
import com.mobilemoney.account.domain.repository.CompteRepository;
import com.mobilemoney.account.domain.valueobject.NumeroTelephone;
import org.springframework.stereotype.Service;

@Service
public class CreateAccountService implements CreateAccountUseCase {

    private final CompteRepository compteRepository;

    public CreateAccountService(CompteRepository compteRepository) {
        this.compteRepository = compteRepository;
    }

    @Override
    public AccountResponse createAccount(CreateAccountRequest request) {

        NumeroTelephone numeroTelephone =
                NumeroTelephone.of(request.getNumeroTelephone());

        if (compteRepository.existsByNumeroTelephone(numeroTelephone)) {
            throw new IllegalArgumentException(
                    "Ce numéro possède déjà un compte."
            );
        }

        Compte compte = Compte.creer(
                request.getProfil(),
                request.getNom(),
                request.getPrenom(),
                numeroTelephone,
                request.getTypePersonne()
        );

        Compte compteSauvegarde =
                compteRepository.save(compte);

        return new AccountResponse(
                compteSauvegarde.getId(),
                compteSauvegarde.getNumeroTelephone().getValue(),
                "Compte créé avec succès."
        );

    }

}