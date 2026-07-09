package com.mobilemoney.account.domain.valueobject;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class MoneyTest {

	//premier test verifie que le montant est non vide 
    @Test
    void should_create_money_with_valid_amount() {
    	//test
        Money money = Money.of("1000");

        //resultat attendu
        assertEquals(new BigDecimal("1000.00"), money.getAmount());
    }

    //deuxieme test verifie que le montant n'es pas zero 
    @Test
    void should_create_zero_money() {
    	//test
        Money money = Money.zero();

        //resultat attendu 
        assertEquals(new BigDecimal("0.00"), money.getAmount());
    }

    // troisieme test verifie que le montant n'ai pas negatif 
    @Test
    void should_throw_exception_when_amount_is_negative() {
        assertThrows(
        		
        		//test et resultat
                IllegalArgumentException.class,
                () -> Money.of("-500")
        );
    }
    
    //additionné deux montant
    @Test
    void should_add_two_money_values() {

        // Arrange(donnée)
        Money first = Money.of("1000");
        Money second = Money.of("500");

        // Act(addition)
        Money result = first.add(second);

        // Assert(resultat)
        assertEquals(new BigDecimal("1500.00"), result.getAmount());
    }
    
    //soustraire deux montant
    @Test
    void should_subtract_two_money_values() {

        // Arrange
        Money first = Money.of("1000");
        Money second = Money.of("400");

        // Act
        Money result = first.subtract(second);

        // Assert
        assertEquals(new BigDecimal("600.00"), result.getAmount());
    }
    
    //le montant ne peux jamais devenir negatif
    @Test
    void should_throw_exception_when_subtraction_results_in_negative_amount() {

        Money first = Money.of("500");
        Money second = Money.of("1000");

        assertThrows(
                IllegalArgumentException.class,
                () -> first.subtract(second)
        );
    }
    
    //comparer les deux montants 
    @Test
    void should_return_true_when_amount_is_greater() {

        Money first = Money.of("1000");
        Money second = Money.of("500");

        assertTrue(first.isGreaterThan(second));
    }
    
    //envoie vrai lorsque le montant est inferieur
    @Test
    void should_return_true_when_amount_is_less() {

        Money first = Money.of("300");
        Money second = Money.of("800");

        assertTrue(first.isLessThan(second));
    }
    
    //compare si les deux montants sont supperieurs
    @Test
    void should_return_true_when_amounts_are_equal() {

        Money first = Money.of("700");
        Money second = Money.of("700");

        assertTrue(first.isGreaterThanOrEqual(second));
    }
}