package com.mobilemoney.transaction.presentation.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mobilemoney.transaction.application.dto.TransactionHistoryResponse;
import com.mobilemoney.transaction.application.usecase.ListTransactionAgentUseCase;

@Controller
@RequestMapping("/transactions")
public class TransactionHistoryAgentController {

	//injection de dependance par constructeur
	     private final  ListTransactionAgentUseCase listTransactionAgentUseCase;
	     
	     public TransactionHistoryAgentController(ListTransactionAgentUseCase listTransactionAgentUseCase) {
	    	 this.listTransactionAgentUseCase=listTransactionAgentUseCase;
	     }
	
	     
	     // Afficher la page de l'historique
	     @GetMapping("/history")
	     public String afficherHistorique() {

	         return "transactions/history";
	     }

	     // Rechercher l'historique de l'agent
	     @GetMapping("/history/search")
	     public String rechercherHistorique(
	             @RequestParam String numeroAgent,
	             Model model) {

	         List<TransactionHistoryResponse> historique =
	                 listTransactionAgentUseCase.historique(
	                         numeroAgent);

	         model.addAttribute(
	                 "numeroAgent",
	                 numeroAgent);

	         model.addAttribute(
	                 "historique",
	                 historique);

	         return "transactions/history";
	     }
}


