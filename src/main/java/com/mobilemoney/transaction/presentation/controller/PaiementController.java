package com.mobilemoney.transaction.presentation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mobilemoney.transaction.application.dto.CreatePaiementRequest;
import com.mobilemoney.transaction.application.dto.PaiementResponse;
import com.mobilemoney.transaction.application.usecase.CreatePaiementUseCase;
import com.mobilemoney.transaction.domain.enums.TypeTransaction;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/payments")
public class PaiementController {

    private final CreatePaiementUseCase createPaiementUseCase;


    public PaiementController(
            CreatePaiementUseCase createPaiementUseCase) {

        this.createPaiementUseCase =
                createPaiementUseCase;
    }


    // Afficher le formulaire
    @GetMapping("/create")
    public String afficherFormulairePaiement(
            Model model) {

        CreatePaiementRequest request =
                new CreatePaiementRequest();

        request.setTypeTransaction(
                TypeTransaction.PAIEMENT_MARCHANT);

        model.addAttribute(
                "paiement",
                request);

        return "payments/create";
    }


    // Effectuer le paiement
    @PostMapping("/create")
    public String effectuerPaiement(

            @Valid
            @ModelAttribute("paiement")
            CreatePaiementRequest request,

            BindingResult bindingResult,

            Model model) {

        if (bindingResult.hasErrors()) {

            return "payments/create";
        }


        try {

            PaiementResponse response =
                    createPaiementUseCase
                            .effectuerPaiement(request);


            model.addAttribute(
                    "recu",
                    response);

            return "payments/recu";


        } catch (Exception e) {

            String message = e.getMessage();


            if (message.contains("client")) {

                bindingResult.rejectValue(
                        "numeroClient",
                        "error.numeroClient",
                        message);


            } else if (message.contains("marchand")) {

                bindingResult.rejectValue(
                        "numeroMarchand",
                        "error.numeroMarchand",
                        message);


            } else if (message.contains("mot de passe")) {

                bindingResult.rejectValue(
                        "motDePasse",
                        "error.motDePasse",
                        message);


            } else if (message.contains("Solde")) {

                bindingResult.rejectValue(
                        "montant",
                        "error.montant",
                        message);


            } else if (message.contains("même compte")) {

                bindingResult.rejectValue(
                        "numeroClient",
                        "error.numeroClient",
                        message);


            } else {

                bindingResult.reject(
                        "error.global",
                        message);
            }


            return "payments/create";
        }
    }
}