package tech.ioco.discovery.bank.atm;

import tech.ioco.discovery.bank.client.Client;

import java.io.Serializable;
import java.math.BigDecimal;

public class WithdrawalRequest implements Serializable {
    private final String atmId;
    private final Client client;
    private final String clientAccountNumber;
    private final BigDecimal amount;

    public WithdrawalRequest(String atmId, Client client, String clientAccountNumber, BigDecimal amount) {
        this.atmId = atmId;
        this.client = client;
        this.clientAccountNumber = clientAccountNumber;
        this.amount = amount;
    }


    public String getAtmId() {
        return atmId;
    }

    public Client getClient() {
        return client;
    }

    public String getClientAccountNumber() {
        return clientAccountNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
