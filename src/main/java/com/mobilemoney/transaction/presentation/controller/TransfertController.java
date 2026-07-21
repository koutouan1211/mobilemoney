package com.mobilemoney.transaction.presentation.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mobilemoney.transaction.application.dto.CreateTransfertRequest;
import com.mobilemoney.transaction.application.dto.TransfertHistoryResponse;
import com.mobilemoney.transaction.application.dto.TransfertResponse;
import com.mobilemoney.transaction.application.usecase.CreateTransfertUseCase;
import com.mobilemoney.transaction.application.usecase.ListTransfertUseCase;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/transfers")
public class TransfertController {

	private final CreateTransfertUseCase createTransferUseCase;
	private final  ListTransfertUseCase listTransfertUseCase;
	
	public TransfertController(CreateTransfertUseCase createTransferUseCase,ListTransfertUseCase listTransfertUseCase) {
		this.createTransferUseCase=createTransferUseCase;
		this.listTransfertUseCase=listTransfertUseCase;
	}
	
	
	//permet d'afficher le formulaire sur la page
    @GetMapping("/create")
    public String afficherFormulaire(Model model) {

        model.addAttribute(
                "transferRequest",
                new CreateTransfertRequest());

        return "transfers/create";
    }

    
    @PostMapping("/create")
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
    
    
    // pour consulter l'historique des transfert 
    
    //affiche la page tymeleaf
    @GetMapping("/history")
    public String afficherHistorique() {

        return "transfers/history";
    }
    
    //recupere le numero de telephone et affiche  le resultat 
    
    @GetMapping("/history/search")
    public String rechercherHistorique(
            @RequestParam String numeroTelephone,
            Model model) {

    	List<TransfertHistoryResponse> historique =
    	        listTransfertUseCase.historique(numeroTelephone);

        model.addAttribute("numeroTelephone", numeroTelephone);
        model.addAttribute("historique", historique);

        return "transfers/history";
    }
}