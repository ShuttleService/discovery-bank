package tech.ioco.discovery.bank.client.account;

import tech.ioco.discovery.bank.client.Client;
import tech.ioco.discovery.bank.currency.Currency;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
public class ClientAccount {
    @Id
    private final String clientAccountNumber;
    @ManyToOne
    @NotNull
    private final Client client;
    @ManyToOne
    @NotNull
    private final AccountType accountType;
    @ManyToOne
    @NotNull
    private final Currency currency;
    @NotNull
    private final BigDecimal displayBalance;

    public ClientAccount(String clientAccountNumber, Client client, AccountType accountType, Currency currency, BigDecimal displayBalance) {
        this.clientAccountNumber = clientAccountNumber;
        this.client = client;
        this.accountType = accountType;
        this.currency = currency;
        this.displayBalance = displayBalance;
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
