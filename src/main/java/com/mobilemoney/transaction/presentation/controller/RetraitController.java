package com.mobilemoney.transaction.presentation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mobilemoney.transaction.application.dto.CreateRetraitRequest;
import com.mobilemoney.transaction.application.dto.RetraitResponse;
import com.mobilemoney.transaction.application.usecase.CreateRetraitUseCase;
import com.mobilemoney.transaction.domain.enums.TypeTransaction;

import jakarta.validation.Valid;



@Controller
@RequestMapping("/withdrawals")
public class RetraitController {

    private final CreateRetraitUseCase createRetraitUseCase;

    public RetraitController(
            CreateRetraitUseCase createRetraitUseCase) {

        this.createRetraitUseCase = createRetraitUseCase;
    }

    // Afficher le formulaire
    @GetMapping("/create")
    public String afficherFormulaireRetrait(Model model) {

        CreateRetraitRequest request =
                new CreateRetraitRequest();

        request.setTypeTransaction(
                TypeTransaction.RETRAIT);

        model.addAttribute(
                "retrait",
                request);

        return "withdrawals/create";
    }

    // Effectuer le retrait
    @PostMapping("/create")
    public String effectuerRetrait(

    		@Valid
    		@ModelAttribute
    		("retrait")
    		CreateRetraitRequest request,

            BindingResult bindingResult,

            Model model) {
    	
    	System.out.println("PIN reçu par Spring = [" + request.getMotDePasse() + "]");
        System.out.println("Erreurs de validation = " + bindingResult.getAllErrors());

        if (bindingResult.hasErrors()) {
            return "withdrawals/create";
        }

        try {

            RetraitResponse response =
                    createRetraitUseCase.effectuerRetrait(request);

            model.addAttribute(
                    "recu",
                    response);

            return "withdrawals/recu";

        } catch (Exception e) {

            String message = e.getMessage();

            System.out.println("Exception attrapée : " + e.getClass().getName());
            System.out.println("Message exact : [" + e.getMessage() + "]");
            e.printStackTrace();

            String messages = e.getMessage();
            
            
            return "withdrawals/create";
        }

    }

}