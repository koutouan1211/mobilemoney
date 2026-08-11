package com.mobilemoney.transaction.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mobilemoney.account.domain.valueobject.NumeroTelephone;
import com.mobilemoney.transaction.application.dto.TransactionHistoryResponse;
import com.mobilemoney.transaction.application.usecase.ListTransactionAgentUseCase;
import com.mobilemoney.transaction.domain.entity.Transfert;
import com.mobilemoney.transaction.domain.repository.TransfertRepository;

@Service
public class ListTransactionAgentService
        implements ListTransactionAgentUseCase {

    private final TransfertRepository transfertRepository;

    public ListTransactionAgentService(
            TransfertRepository transfertRepository) {

        this.transfertRepository = transfertRepository;
    }

    @Override
    public List<TransactionHistoryResponse> historique(
            String numeroAgent) {

        List<Transfert> transactions =
                transfertRepository
                        .findHistoriqueParNumeroAgent(
                                NumeroTelephone.of(numeroAgent));

        return transactions.stream()
                .map(this::toResponse)
                .toList();
    }

    private TransactionHistoryResponse toResponse(
            Transfert transaction) {

        return new TransactionHistoryResponse(

                transaction.getReference().getValue(),

                transaction.getTypeTransaction().name(),

                transaction.getCompteSource().getValue(),

                transaction.getCompteDestination().getValue(),

                transaction.getMontant().toString(),

                transaction.getFrais().toString(),

                transaction.getStatut().name(),

                transaction.getMotif(),

                transaction.getDateTransaction()
        );
    }
}