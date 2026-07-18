package com.mobilemoney.transaction.domain.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.mobilemoney.account.domain.valueobject.Money;

@Service
public class CalculFraisTransfertService {

    public Money calculer(Money montant) {

        BigDecimal frais =
                montant.getAmount()
                       .multiply(BigDecimal.valueOf(0.01));

        return Money.of(frais);
    }

}