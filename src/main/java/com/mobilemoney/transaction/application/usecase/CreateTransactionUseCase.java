package com.mobilemoney.transaction.application.usecase;

import com.mobilemoney.transaction.application.dto.CreateTransactionRequest;
import com.mobilemoney.transaction.application.dto.TransactionResponse;

public interface CreateTransactionUseCase {

    TransactionResponse effectuerTransaction(CreateTransactionRequest request);

}