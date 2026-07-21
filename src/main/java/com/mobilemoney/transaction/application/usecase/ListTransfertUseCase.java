package com.mobilemoney.transaction.application.usecase;

import java.util.List;

import com.mobilemoney.transaction.application.dto.TransfertHistoryResponse;

public interface ListTransfertUseCase {


    List<TransfertHistoryResponse> historique(String numeroTelephone);
}
