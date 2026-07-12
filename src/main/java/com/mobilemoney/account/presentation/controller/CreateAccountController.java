package com.mobilemoney.account.presentation.controller;

import com.mobilemoney.account.application.dto.AccountResponse;
import com.mobilemoney.account.application.dto.CreateAccountRequest;
import com.mobilemoney.account.application.usecase.CreateAccountUseCase;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
            @Valid @ModelAttribute("createAccountRequest") CreateAccountRequest request,
            BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            return "account/create-account";
        }

        try {

            AccountResponse response =
                    createAccountUseCase.createAccount(request);

            model.addAttribute("response", response);

            return "account/account-created";

        } catch (IllegalArgumentException e) {

            bindingResult.rejectValue(
                    "numeroTelephone",
                    "duplicate",
                    e.getMessage());

            return "account/create-account";
        }
    }
}