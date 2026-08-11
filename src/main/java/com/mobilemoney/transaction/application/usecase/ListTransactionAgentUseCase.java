package com.mobilemoney.transaction.application.usecase;

import java.util.List;

import com.mobilemoney.transaction.application.dto.TransactionHistoryResponse;

public interface ListTransactionAgentUseCase {

	 List<TransactionHistoryResponse> historique(
	            String numeroAgent);
}
