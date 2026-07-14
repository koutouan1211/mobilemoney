package com.mobilemoney.transaction.domain.valueobjet;


import java.util.Objects;

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

}
