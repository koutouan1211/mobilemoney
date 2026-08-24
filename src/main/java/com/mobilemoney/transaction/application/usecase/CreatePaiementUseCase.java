package com.mobilemoney.transaction.application.usecase;

import com.mobilemoney.transaction.application.dto.CreatePaiementRequest;
import com.mobilemoney.transaction.application.dto.PaiementResponse;

public interface CreatePaiementUseCase {

    PaiementResponse effectuerPaiement(
            CreatePaiementRequest request);
}