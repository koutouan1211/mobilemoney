package com.mobilemoney.account.domain.valueobject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class NumeroTelephoneTest {

	//le numero local doit etre accepté
	@Test
	void should_accept_local_number() {

	    NumeroTelephone numero =
	            NumeroTelephone.of("0707070707");

	    assertEquals(
	            "2250707070707",
	            numero.getValue()
	    );
	}
	
	//le formation international doit etre accepté 
	@Test
	void should_accept_international_number() {

	    NumeroTelephone numero =
	            NumeroTelephone.of("2250707070707");

	    assertEquals(
	            "2250707070707",
	            numero.getValue()
	    );
	}
	
		//le plus + devant l'indicatif est supprimer
	@Test
	void should_remove_plus_sign() {

	    NumeroTelephone numero =
	            NumeroTelephone.of("+2250707070707");

	    assertEquals(
	            "2250707070707",
	            numero.getValue()
	    );
	}
	
	//le numero est invalide 
	@Test
	void should_throw_exception_when_number_is_invalid() {

	    assertThrows(
	            IllegalArgumentException.class,
	            () -> NumeroTelephone.of("123")
	    );

	}
	
	// egalité 
	@Test
	void should_consider_same_phone_numbers_equal() {

	    NumeroTelephone first =
	            NumeroTelephone.of("0707070707");

	    NumeroTelephone second =
	            NumeroTelephone.of("2250707070707");

	    assertEquals(first, second);

	}
	
}
