package tech.ioco.discovery.bank.client.account;

import tech.ioco.discovery.bank.client.Client;
import tech.ioco.discovery.bank.currency.Currency;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Optional;

@Entity
public class ClientAccount {
    @Id
    @Size(max = 10)
    private final String clientAccountNumber;
    @ManyToOne
    @NotNull
    @JoinColumn(name = "client_id")
    private final Client client;
    @ManyToOne
    @NotNull
    @JoinColumn(name = "account_type_code")
    private final AccountType accountType;
    @ManyToOne
    @NotNull
    @JoinColumn(name = "currency_code")
    private final Currency currency;
    @NotNull
    private final BigDecimal displayBalance;
    @Transient
    private CurrencyDisplayBalance currencyDisplayBalance;

    public ClientAccount(String clientAccountNumber, Client client, AccountType accountType, Currency currency, BigDecimal displayBalance) {
        this.clientAccountNumber = clientAccountNumber;
        this.client = client;
        this.accountType = accountType;
        this.currency = currency;
        this.displayBalance = displayBalance;
    }

    void currencyBalance(CurrencyDisplayBalance currencyDisplayBalance) {
        this.currencyDisplayBalance = currencyDisplayBalance;
    }

    public Optional<CurrencyDisplayBalance> getCurrencyDisplayBalance() {
        return Optional.ofNullable(currencyDisplayBalance);
    }

    public BigDecimal getDisplayBalance() {
        return displayBalance;
    }

    public String getClientAccountNumber() {
        return clientAccountNumber;
    }

    public Currency getCurrency() {
        return currency;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public Client getClient() {
        return client;
    }

    //Pleasing the JPA Gods
    private ClientAccount() {
        this.clientAccountNumber = null;
        this.client = null;
        this.accountType = null;
        this.currency = null;
        this.displayBalance = null;
    }
}
