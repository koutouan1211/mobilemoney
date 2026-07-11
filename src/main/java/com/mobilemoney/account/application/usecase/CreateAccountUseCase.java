package com.mobilemoney.account.application.usecase;

import com.mobilemoney.account.application.dto.AccountResponse;
import com.mobilemoney.account.application.dto.CreateAccountRequest;

public interface CreateAccountUseCase {

    AccountResponse createAccount(CreateAccountRequest request);

}