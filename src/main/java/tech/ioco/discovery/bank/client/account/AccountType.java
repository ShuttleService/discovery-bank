package tech.ioco.discovery.bank.client.account;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class AccountType {
    @Id
    @NotNull
    @Size(min = 1, max = 10)
    private final String accountTypeCode;
    @NotNull
    @Size(min = 1, max = 50)
    private final String description;
    private boolean transactional;

    public AccountType(String accountTypeCode, String description, boolean transactional) {
        this.accountTypeCode = accountTypeCode;
        this.description = description;
        this.transactional = transactional;
    }

    //Please the JPA Gods
    private AccountType(){
        this.accountTypeCode = null;
        this.description = null;
    }
}
