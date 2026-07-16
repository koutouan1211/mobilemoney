package com.mobilemoney.transaction.application.usecase;

import com.mobilemoney.transaction.application.dto.CreateTransfertRequest;
import com.mobilemoney.transaction.application.dto.TransfertResponse;

public interface CreateTransfertUseCase {

    TransfertResponse effectuerTransaction(CreateTransfertRequest request);

}