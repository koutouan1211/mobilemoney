package com.mobilemoney.account.domain.valueobject;

import java.math.BigDecimal;
import java.util.Objects;

import com.mobilemoney.shared.constant.MoneyConstants;

// permet de mettre cette class de meniere imuable avec le mot cle FINAL
public final class Money {

   //on declare une entite qui sera pas modifiable 
    private final BigDecimal amount;

    // constructeur 
    private Money(BigDecimal amount) {

    	//le montant ne peux pas etre vide 
        Objects.requireNonNull(amount, "Le montant est obligatoire.");

        //le montant ne peux pas etre negatif
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Le montant ne peut pas être négatif.");
        }
        
        //permet de definir les decimal et arrondir les decimals 
        this.amount=amount.setScale(MoneyConstants.SCALE, MoneyConstants.ROUNDING_MODE);
    }

    // les routes de sortie 
    public static Money of(BigDecimal amount) {
        return new Money(amount);
    }

    public static Money of(String amount) {
        return new Money(new BigDecimal(amount));
    }

    public static Money zero() {
        return new Money(BigDecimal.ZERO);
    }

    public BigDecimal getAmount() {
        return amount;
    }
    
    //cette methode nous permet d'additionné deux montants
    public Money add(Money other) {

        Objects.requireNonNull(other, "Le montant à ajouter est obligatoire.");

        return new Money(this.amount.add(other.amount));
    }
    
    //cette methode nous permet de soustrait deux montant(solde du montant) et de ne pas permettre que un montant soit negatif
    public Money subtract(Money other) {

        Objects.requireNonNull(other, "Le montant à soustraire est obligatoire.");

        BigDecimal result = this.amount.subtract(other.amount);

        if (result.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Le montant ne peut pas être négatif.");
        }

        return new Money(result);
    }
    
    //compraraisons des differents montant
    public boolean isGreaterThan(Money other) {
        return this.amount.compareTo(other.amount) > 0;
    }

    public boolean isGreaterThanOrEqual(Money other) {
        return this.amount.compareTo(other.amount) >= 0;
    }

    public boolean isLessThan(Money other) {
        return this.amount.compareTo(other.amount) < 0;
    }

    public boolean isZero() {
        return this.amount.compareTo(BigDecimal.ZERO) == 0;
    }
    
    //permet de dire a java que deux methode sont egaux a cause de leur nombre
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Money money)) return false;
        return amount.compareTo(money.amount) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }
    
    //defini la devise de la money
    @Override
    public String toString() {
        return amount + " FCFA";
    }
}