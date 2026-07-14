package com.mobilemoney.transaction.application.dto;

public class TransactionResponse {

	
	private String reference;

    private String message;

    public TransactionResponse() {
    }

    
    public TransactionResponse(
            String reference,
            String message) {

        this.reference = reference;
        this.message = message;
    }

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
    
    
}
