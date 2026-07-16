package com.mobilemoney.transaction.domain.valueobjet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.mobilemoney.transaction.domain.valueobject.ReferenceTransfert;

public class ReferenceTransfertTest {

	
    @Test
    void should_create_valid_reference() {

        ReferenceTransfert reference =
                ReferenceTransfert.of("TX202607120001");

        assertEquals(
                "TX202607120001",
                reference.getValue());
    }

    @Test
    void should_throw_exception_when_reference_is_null() {

        assertThrows(
                NullPointerException.class,
                () -> ReferenceTransfert.of(null));
    }

    @Test
    void should_throw_exception_when_reference_is_blank() {

        assertThrows(
                IllegalArgumentException.class,
                () -> ReferenceTransfert.of(""));
    }

    @Test
    void should_consider_same_reference_equal() {

        ReferenceTransfert first =
                ReferenceTransfert.of("TX202607120001");

        ReferenceTransfert second =
                ReferenceTransfert.of("TX202607120001");

        assertEquals(first, second);
    }
    
    @Test
    void should_throw_exception_when_reference_format_is_invalid() {

        assertThrows(
                IllegalArgumentException.class,
                () -> ReferenceTransfert.of("bonjour"));
    }

    @Test
    void should_accept_valid_transaction_reference() {

        ReferenceTransfert reference =
                ReferenceTransfert.of("TX202607130001");

        assertEquals(
                "TX202607130001",
                reference.getValue());
    }
}
