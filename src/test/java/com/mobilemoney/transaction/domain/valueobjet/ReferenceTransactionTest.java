package com.mobilemoney.transaction.domain.valueobjet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class ReferenceTransactionTest {

	
    @Test
    void should_create_valid_reference() {

        ReferenceTransaction reference =
                ReferenceTransaction.of("TX202607120001");

        assertEquals(
                "TX202607120001",
                reference.getValue());
    }

    @Test
    void should_throw_exception_when_reference_is_null() {

        assertThrows(
                NullPointerException.class,
                () -> ReferenceTransaction.of(null));
    }

    @Test
    void should_throw_exception_when_reference_is_blank() {

        assertThrows(
                IllegalArgumentException.class,
                () -> ReferenceTransaction.of(""));
    }

    @Test
    void should_consider_same_reference_equal() {

        ReferenceTransaction first =
                ReferenceTransaction.of("TX202607120001");

        ReferenceTransaction second =
                ReferenceTransaction.of("TX202607120001");

        assertEquals(first, second);
    }
    
    @Test
    void should_throw_exception_when_reference_format_is_invalid() {

        assertThrows(
                IllegalArgumentException.class,
                () -> ReferenceTransaction.of("bonjour"));
    }

    @Test
    void should_accept_valid_transaction_reference() {

        ReferenceTransaction reference =
                ReferenceTransaction.of("TX202607130001");

        assertEquals(
                "TX202607130001",
                reference.getValue());
    }
}
