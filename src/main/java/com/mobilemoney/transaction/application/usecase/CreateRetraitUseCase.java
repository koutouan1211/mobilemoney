package com.mobilemoney.transaction.application.usecase;

import com.mobilemoney.transaction.application.dto.CreateRetraitRequest;
import com.mobilemoney.transaction.application.dto.RetraitResponse;

public interface CreateRetraitUseCase {

	 RetraitResponse effectuerRetrait(
	            CreateRetraitRequest request);
}
