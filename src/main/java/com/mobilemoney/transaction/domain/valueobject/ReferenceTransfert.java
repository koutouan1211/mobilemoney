package com.mobilemoney.transaction.domain.valueobject;


import java.util.Objects;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public final class ReferenceTransfert {

    private final String value;

    private ReferenceTransfert(String value) {

        Objects.requireNonNull(value,
                "La référence est obligatoire.");

        if (value.isBlank()) {
            throw new IllegalArgumentException(
                    "La référence est obligatoire.");
        }

        this.value = value;
    }

    public static ReferenceTransfert of(String value) {
        return new ReferenceTransfert(value);
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;

        if (!(o instanceof ReferenceTransfert))
            return false;

        ReferenceTransfert that =
                (ReferenceTransfert) o;

        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
        
    }
    
    //generer automatiquement une reference
    public static ReferenceTransfert generer() {

        String date = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

        String random = UUID.randomUUID()
                .toString()
                .substring(0, 6)
                .toUpperCase();

        return new ReferenceTransfert(
                "TX-" + date + "-" + random);
    }

}
