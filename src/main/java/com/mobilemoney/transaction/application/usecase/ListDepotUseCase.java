package com.mobilemoney.transaction.application.usecase;

import java.util.List;

import com.mobilemoney.transaction.application.dto.DepotHistoryResponse;

public interface ListDepotUseCase {

    List<DepotHistoryResponse> historique(String numeroAgent);

}
