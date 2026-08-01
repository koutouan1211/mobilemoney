package com.mobilemoney.transaction.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mobilemoney.account.domain.valueobject.NumeroTelephone;
import com.mobilemoney.transaction.application.dto.DepotHistoryResponse;
import com.mobilemoney.transaction.application.usecase.ListDepotUseCase;
import com.mobilemoney.transaction.domain.entity.Transfert;
import com.mobilemoney.transaction.domain.repository.TransfertRepository;

@Service
public class ListDepotService implements ListDepotUseCase {

    private final TransfertRepository transfertRepository;

    public ListDepotService(
            TransfertRepository transfertRepository) {

        this.transfertRepository = transfertRepository;
    }

    @Override
    public List<DepotHistoryResponse> historique(
            String numeroAgent) {

        List<Transfert> depots =
                transfertRepository.findDepotByNumeroAgent(
                        NumeroTelephone.of(numeroAgent));

        return depots.stream()

                .map(this::toResponse)

                .toList();
    }

    private DepotHistoryResponse toResponse(
            Transfert depot) {

        return new DepotHistoryResponse(

                depot.getReference().getValue(),

                depot.getCompteSource().getValue(),

                depot.getCompteDestination().getValue(),

                depot.getMontant().toString(),

                depot.getStatut().name(),

                depot.getDateTransaction()
        );
    }

}