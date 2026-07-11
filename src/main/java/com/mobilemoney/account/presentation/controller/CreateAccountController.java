package com.mobilemoney.account.presentation.controller;

import com.mobilemoney.account.application.dto.CreateAccountRequest;
import com.mobilemoney.account.application.usecase.CreateAccountUseCase;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/accounts")
public class CreateAccountController {

    private final CreateAccountUseCase createAccountUseCase;

    public CreateAccountController(CreateAccountUseCase createAccountUseCase) {
        this.createAccountUseCase = createAccountUseCase;
    }

    @GetMapping("/create")
    public String showForm(Model model) {

        model.addAttribute(
                "createAccountRequest",
                new CreateAccountRequest());

        return "account/create-account";
    }

    @PostMapping("/create")
    public String createAccount(
            @ModelAttribute CreateAccountRequest request,
            Model model) {

        model.addAttribute(
                "response",
                createAccountUseCase.createAccount(request));

        return "account/account-created";
    }

}