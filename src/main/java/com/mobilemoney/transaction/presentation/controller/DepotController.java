package com.mobilemoney.transaction.presentation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mobilemoney.transaction.application.dto.CreateDepotRequest;
import com.mobilemoney.transaction.application.dto.DepotResponse;
import com.mobilemoney.transaction.application.usecase.CreateDepotUseCase;
import com.mobilemoney.transaction.domain.enums.TypeTransaction;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/deposits")
public class DepotController {

    private final CreateDepotUseCase createDepotUseCase;

    public DepotController(CreateDepotUseCase createDepotUseCase) {
        this.createDepotUseCase = createDepotUseCase;
    }

    // afficher le formulaire
    @GetMapping("/create")
    public String afficherFormulaireDepot(Model model) {

        CreateDepotRequest request = new CreateDepotRequest();
        request.setTypeTransaction(TypeTransaction.DEPOT);

        model.addAttribute("depot", request);

        return "deposits/create";
    }

    //effectuer depot 
    @PostMapping("/create")
    public String effectuerDepot(
            @Valid CreateDepotRequest request,
            BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            return "deposits/create";
        }

        DepotResponse response =
                createDepotUseCase.effectuerDepot(request);

        model.addAttribute("recu", response);

        return "deposits/recu";
    }

}