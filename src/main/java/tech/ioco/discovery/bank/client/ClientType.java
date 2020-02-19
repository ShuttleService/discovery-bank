package tech.ioco.discovery.bank.client;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class ClientType {
    @Id
    @NotNull
    @Size(max = 2, min = 1)
    private final String clientTypeCode;
    @NotNull
    @Size(min = 1, max = 255)
    private final String description;

    public ClientType(String clientTypeCode, String description) {
        this.clientTypeCode = clientTypeCode;
        this.description = description;
    }
}
