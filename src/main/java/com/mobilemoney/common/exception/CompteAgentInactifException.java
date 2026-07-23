package com.mobilemoney.common.exception;

public class CompteAgentInactifException extends RuntimeException{

	public CompteAgentInactifException() {
        super("Le compte de l'agent n'est pas encore actif");
    }
}
