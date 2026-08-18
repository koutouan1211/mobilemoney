package com.mobilemoney.account.domain.valueobject;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MotDePasseTest {

    @Test
    void should_create_valid_password() {

        MotDePasse motDePasse =
                MotDePasse.of("1234");

        assertEquals(
                "1234",
                motDePasse.getValeur()
        );
    }


    @Test
    void should_throw_exception_when_password_is_empty() {

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> MotDePasse.of("")
                );

        assertEquals(
                "Le mot de passe est obligatoire.",
                exception.getMessage()
        );
    }


    @Test
    void should_throw_exception_when_password_is_blank() {

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> MotDePasse.of("    ")
                );

        assertEquals(
                "Le mot de passe est obligatoire.",
                exception.getMessage()
        );
    }


    @Test
    void should_throw_exception_when_password_does_not_contain_exactly_four_digits() {

        assertThrows(
                IllegalArgumentException.class,
                () -> MotDePasse.of("123")
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> MotDePasse.of("12345")
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> MotDePasse.of("12ab")
        );
    }


    @Test
    void should_create_password_from_hash() {

        String hash =
                "$2a$10$abcdefghijklmnopqrstuv";

        MotDePasse motDePasse =
                MotDePasse.depuisHash(hash);

        assertEquals(
                hash,
                motDePasse.getValeur()
        );
    }


    @Test
    void should_hide_password_when_converted_to_string() {

        MotDePasse motDePasse =
                MotDePasse.of("1234");

        assertEquals(
                "****",
                motDePasse.toString()
        );
    }


    @Test
    void should_be_equal_when_passwords_have_same_value() {

        MotDePasse motDePasse1 =
                MotDePasse.of("1234");

        MotDePasse motDePasse2 =
                MotDePasse.of("1234");

        assertEquals(
                motDePasse1,
                motDePasse2
        );
    }


    @Test
    void should_not_be_equal_when_passwords_have_different_values() {

        MotDePasse motDePasse1 =
                MotDePasse.of("1234");

        MotDePasse motDePasse2 =
                MotDePasse.of("5678");

        assertNotEquals(
                motDePasse1,
                motDePasse2
        );
    }
}