package com.mobilemoney.common.exception;

public class CompteClientInactifException extends RuntimeException{

	  public CompteClientInactifException() {
	        super("Le compte du client n'est pas actif.");
	    }
}
