package com.mobilemoney.transaction.application.usecase;

import com.mobilemoney.transaction.application.dto.CreateDepotRequest;
import com.mobilemoney.transaction.application.dto.DepotResponse;

public interface CreateDepotUseCase {

	 DepotResponse effectuerDepot(CreateDepotRequest request);
}
