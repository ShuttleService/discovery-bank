package tech.ioco.discovery.bank.client;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ClientSubType {
    @Id
    private final String clientSubTypeCode;
    private final String clientTypeCode;
    private final String description;

    public ClientSubType(String clientSubTypeCode, String clientTypeCode, String description) {
        this.clientSubTypeCode = clientSubTypeCode;
        this.clientTypeCode = clientTypeCode;
        this.description = description;
    }

    private ClientSubType() {
        this.clientSubTypeCode = null;
        this.clientTypeCode = null;
        this.description = null;
    }
}
