package com.mobilemoney.transaction.presentation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.mobilemoney.transaction.application.dto.CreateTransfertRequest;
import com.mobilemoney.transaction.application.dto.TransfertResponse;
import com.mobilemoney.transaction.application.usecase.CreateTransfertUseCase;

import jakarta.validation.Valid;

@Controller
public class TransfertController {

	private final CreateTransfertUseCase createTransferUseCase;
	
	public TransfertController(CreateTransfertUseCase createTransferUseCase) {
		this.createTransferUseCase=createTransferUseCase;
	}
	
	
	//permet d'afficher le formulaire sur la page
    @GetMapping("/transfers/create")
    public String afficherFormulaire(Model model) {

        model.addAttribute(
                "transferRequest",
                new CreateTransfertRequest());

        return "transfers/create";
    }

    
    @PostMapping("/transfers/create")
    public String effectuerTransfert(
            @Valid
            @ModelAttribute("transferRequest")
            CreateTransfertRequest request,
            BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            return "transfers/create";
        }

        try {

            TransfertResponse response =
                    createTransferUseCase.effectuerTransaction(request);

            model.addAttribute("recu", response);

            return "transfers/recu";

        } catch (IllegalArgumentException exception) {

            model.addAttribute("erreur", exception.getMessage());

            return "transfers/create";
        }
    }
}