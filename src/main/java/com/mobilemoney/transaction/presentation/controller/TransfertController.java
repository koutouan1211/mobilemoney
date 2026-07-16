package com.mobilemoney.transaction.presentation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.mobilemoney.transaction.application.dto.CreateTransfertRequest;
import com.mobilemoney.transaction.application.dto.TransfertResponse;
import com.mobilemoney.transaction.application.usecase.CreateTransfertUseCase;

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

            @ModelAttribute("transferRequest")
            CreateTransfertRequest request,

            Model model) {

        TransfertResponse response =
                createTransferUseCase.effectuerTransaction(request);

        model.addAttribute(
                "message",
                response.getMessage());

        model.addAttribute(
                "reference",
                response.getReference());

        model.addAttribute(
                "transferRequest",
                new CreateTransfertRequest());

        return "transfers/create";
    }
    
    
}