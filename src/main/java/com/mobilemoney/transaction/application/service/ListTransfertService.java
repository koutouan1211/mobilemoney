package com.mobilemoney.transaction.application.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.mobilemoney.account.domain.valueobject.NumeroTelephone;
import com.mobilemoney.transaction.application.dto.TransfertHistoryResponse;
import com.mobilemoney.transaction.application.usecase.ListTransfertUseCase;
import com.mobilemoney.transaction.domain.entity.Transfert;
import com.mobilemoney.transaction.domain.repository.TransfertRepository;

@Service
public class ListTransfertService implements ListTransfertUseCase {

    private final TransfertRepository transfertRepository;

    public ListTransfertService(TransfertRepository transfertRepository) {
        this.transfertRepository = transfertRepository;
    }

    @Override
    public List<TransfertHistoryResponse> historique(String numeroTelephone) {

        List<Transfert> transferts =
                transfertRepository.findHistoriqueParNumero(
                        NumeroTelephone.of(numeroTelephone));

        return transferts.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private TransfertHistoryResponse toResponse(Transfert transfert) {

        return new TransfertHistoryResponse(
                transfert.getReference().getValue(),
                transfert.getTypeTransaction().name(),
                transfert.getCompteSource().getValue(),
                transfert.getCompteDestination().getValue(),
                transfert.getMontant().toString(),
                transfert.getFrais().toString(),
                transfert.getStatut().name(),
                transfert.getDateTransaction()
        );
    }

}