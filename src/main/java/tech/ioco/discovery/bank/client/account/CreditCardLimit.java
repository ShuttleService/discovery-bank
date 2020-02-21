package tech.ioco.discovery.bank.client.account;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
public class CreditCardLimit {
    @Id
    private final String clientAccountNumber;
    @NotNull
    private BigDecimal accountLimit;

    public CreditCardLimit(String clientAccountNumber, BigDecimal accountLimit) {
        this.clientAccountNumber = clientAccountNumber;
        this.accountLimit = accountLimit;
    }

    //Pleasing the JPA gods
    CreditCardLimit() {
        this.clientAccountNumber = null;
        this.accountLimit = null;
    }
}
