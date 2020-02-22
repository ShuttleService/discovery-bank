package tech.ioco.discovery.bank.client.account;

import tech.ioco.discovery.bank.currency.Currency;

import java.math.BigDecimal;

public class CurrencyDisplayBalance {
    private final Currency currency;
    private final BigDecimal rate;
    private final BigDecimal displayBalance;

    CurrencyDisplayBalance(Currency currency, BigDecimal rate, BigDecimal displayBalance) {
        this.currency = currency;
        this.rate = rate;
        this.displayBalance = displayBalance;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public Currency getCurrency() {
        return currency;
    }

    public BigDecimal getDisplayBalance() {
        return displayBalance;
    }
}
