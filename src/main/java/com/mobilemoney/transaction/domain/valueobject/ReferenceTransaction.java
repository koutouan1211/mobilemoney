package com.mobilemoney.transaction.domain.valueobject;


import java.util.Objects;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public final class ReferenceTransaction {

    private final String value;

    private ReferenceTransaction(String value) {

        Objects.requireNonNull(value,
                "La référence est obligatoire.");

        if (value.isBlank()) {
            throw new IllegalArgumentException(
                    "La référence est obligatoire.");
        }

        this.value = value;
    }

    public static ReferenceTransaction of(String value) {
        return new ReferenceTransaction(value);
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

        if (!(o instanceof ReferenceTransaction))
            return false;

        ReferenceTransaction that =
                (ReferenceTransaction) o;

        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
        
    }
    
    //generer automatiquement une reference
    public static ReferenceTransaction generer() {

        String date = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

        String random = UUID.randomUUID()
                .toString()
                .substring(0, 6)
                .toUpperCase();

        return new ReferenceTransaction(
                "TX-" + date + "-" + random);
    }

}
